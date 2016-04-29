# -*- coding: utf-8 -*-
import os
import base64
import uuid
import tornado.httpserver
import tornado.httpclient
import tornado.ioloop
import tornado.options
import tornado.gen
from urllib import urlencode
import simplejson
from tornado import web, escape
import pickle
from tornado.escape import to_unicode
from wtforms_tornado import Form
from wtforms import StringField, PasswordField, SubmitField
from wtforms.validators import Required, Length, Email, Regexp, EqualTo

from tornado.options import define, options
define("port", default=8000, help="run on the given port", type=int)

url_for_logics = r'http://localhost:8001/'
url_for_notification = r'http://localhost:8888/'

class LoginForm(Form):
	email = StringField('Email', validators=[Required(), Length(1, 64), Email()])
	password = PasswordField('Password', validators=[Required()])
	submit = SubmitField('Sign in')

class RegistrationForm(Form):
	email = StringField('Email', validators=[Required(), Length(1, 64), Email()])
	username = StringField('Username', validators=[Required(), Length(1, 64), Regexp('^[A-Za-z][A-Za-z0-9_.]*$', 0,
												'Usernames must have only letters, numbers, dots or underscores.')])
	password = PasswordField('Password', validators=[Required(), EqualTo('password2', message='Passwords must match.')])
	password2 = PasswordField('Confirm password', validators=[Required()])
	submit = SubmitField('Sign up')

class RegistrationForm2(Form):
	email = StringField('Email', validators=[Required(), Length(1, 64), Email()])
	username = StringField('Username', validators=[Required(), Length(1, 64), Regexp('^[A-Za-z][A-Za-z0-9_.]*$', 0,
												'Usernames must have only letters, numbers, dots or underscores.')])
	mark = StringField('Car make', validators=[Required(), Length(1, 64), Regexp('^[A-Za-z]*$', 0,
													'Car make must have only letters.')])
	model = StringField('Car model', validators=[Required(), Length(1, 64), Regexp('^[A-Za-z0-9]*$', 0,
															'Car make must have only letters or numbers.')])
	statenumber = StringField('State number', validators=[Required(), Length(1, 64), Regexp('^[A-Z]{1}[0-9]{3}[A-Z]{2}$', 0,
																						'State number must be as A000AA.')])
	region = StringField('Region', validators=[Required(), Length(1, 64), Regexp('^\d{2,3}$', 0, 'Region as 77 or 177')])
	password = PasswordField('Password', validators=[Required(), EqualTo('password2', message='Passwords must match.')])
	password2 = PasswordField('Confirm password', validators=[Required()])
	submit = SubmitField('Sign up')

class Flash(object):
	def __init__(self, message, data=None):
		self.message = message
		self.data = data

class MyHandler(tornado.web.RequestHandler):
	def cookie_name(self, key):
		return key + 'flash_cookie'

	def get_flash_cookie(self, key):
		return self.get_cookie(self.cookie_name(key))

	def has_flash(self, key):
		return self.get_flash_cookie(key) is not None

	def get_flash(self, key):
		if not self.has_flash(key):
			return None
		flash = tornado.escape.url_unescape(self.get_flash_cookie(key))
		try:
			flash_data = pickle.loads(flash)
			self.clear_cookie(self.cookie_name(key))
			return flash_data
		except:
			return None

	def set_flash(self, flash, key='error'):
		flash = pickle.dumps(flash)
		self.set_cookie(self.cookie_name(key), tornado.escape.url_escape(flash))

class IndexHandler(MyHandler):
	def get(self):
		if self.get_flash('error'):
			flash = self.get_flash('error')
			self.render('index.html', form=flash.data, flash_msg=flash.message)
		else:
			self.render('index.html',flash_msg=None)

class Type_choiceHandler(tornado.web.RequestHandler):
	def get(self):
		self.render("type.html")

class RegistrationHandler(MyHandler):
	def get(self):
		form = RegistrationForm()
		if self.get_flash('error'):
			flash = self.get_flash('error')
			self.render('register.html', form=form, flash_msg=flash.message)
		else:
			self.render('register.html', form=form, flash_msg=None)

	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		form = RegistrationForm(self.request.arguments)
		if form.validate():
			new_user = {
				'email': form.email.data,
				'password': form.password.data,
				'username': form.username.data,
				'type': 'passenger'
			}
			try:
				client = tornado.httpclient.AsyncHTTPClient()
				response = yield tornado.gen.Task (client.fetch, url_for_logics + 'register', method='POST',
												   headers={'Content-Type': 'application/json; charset=UTF-8'},
												   body=simplejson.dumps(new_user))
				if response.error:
					self.write("Error: %s" % response.error)
				elif response.code == 201:
					flash = Flash('You can now login.')
					self.set_flash(flash)
					self.redirect('/')
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
				self.redirect("/")
		else:
			flash = Flash(form.errors)
			self.set_flash(flash)
			self.redirect('/register')
		self.finish()

class Registration2Handler(MyHandler):
	def get(self):
		form = RegistrationForm2()
		if self.get_flash('error'):
			flash = self.get_flash('error')
			self.render('register2.html', form=form, flash_msg=flash.message)
		else:
			self.render('register2.html', form=form, flash_msg=None)

	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		form = RegistrationForm2(self.request.arguments)
		if form.validate():
			new_user = {
            	'email': form.email.data,
                'password': form.password.data,
                'username': form.username.data,
				'mark': form.mark.data,
				'model': form.model.data,
				'statenumber': form.statenumber.data,
				'region': form.region.data,
                'type': 'taxi'
            }
			try:
				client = tornado.httpclient.AsyncHTTPClient()
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'register', method='POST',
												  headers={'Content-Type': 'application/json; charset=UTF-8'},
												  body=simplejson.dumps(new_user))
				if response.error:
					self.write("Error: %s" % response.error)
				elif response.code == 201:
					flash = Flash('You can now login.')
					self.set_flash(flash)
					self.redirect('/')
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
				self.redirect("/")
		else:
			flash = Flash(form.errors)
			self.set_flash(flash)
			self.redirect('/register2')
		self.finish()

class LoginHandler (MyHandler):
	def get(self):
		form = LoginForm()
		self.render('login.html', form=form)

	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		form = LoginForm(self.request.arguments)
		if form.validate():
			login_information = {
            	'email': form.email.data,
            	'password': form.password.data
        	}
			try:
				client = tornado.httpclient.AsyncHTTPClient()
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'login', method='POST',
												  headers={"Content-Type": "application/json"},
												  body=simplejson.dumps(login_information))
				if response.error:
					self.write("Error: %s" % response.error)
				elif response.code == 404:
					flash = Flash('Invalid email or password')
					self.set_flash(flash)
					self.redirect("/login")
				elif response.code == 200:
					token = simplejson.loads(response.body)['token']
					user_type = simplejson.loads(response.body)['user_type']
					self.set_cookie('my_session', value=token)
					self.set_cookie('my_type', value='free')
					if user_type == 'taxi':
						self.redirect(url_for_notification + 'taxi')
					else:
						self.redirect(url_for_notification + 'pass')
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
				self.redirect("/")
		else:
			self.render('login.html', form=form)
		self.finish()

class LogoutHandler (tornado.web.RequestHandler):
	def get(self):
		self.clear_cookie('my_session')
		self.clear_cookie('my_type')
		self.redirect('/')

class Check_sessionHandler(tornado.web.RequestHandler):
	def check_session(self):
		token = self.get_cookie('my_session')
		client = tornado.httpclient.HTTPClient()
		response = client.fetch(url_for_logics + 'token',method='POST',
								headers={"Content-Type": "application/json"},
								body=simplejson.dumps(dict(token=token)))
		if response.code == 200:
			return simplejson.loads(response.body)['user_id']
		else:
			return 'unregistered'

class TokenHandler(tornado.web.RequestHandler):
	def get_token(self, user_id, user_type):
		client = tornado.httpclient.HTTPClient()
		response = client.fetch(url_for_logics + 'get_token', method='POST',
								headers={"Content-Type": "application/json"},
								body=simplejson.dumps(dict(user_id=user_id, user_type=user_type)))
		if response.code == 200:
			return simplejson.loads(response.body)['token']
		elif response.code == 404:
			self.set_status(404)

class Make_orderHandler(Check_sessionHandler, MyHandler, TokenHandler):
	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		try:
			pass_id = self.check_session()
		except:
			pass_id = 'unregistered'
		if pass_id != 'unregistered':
			try:
				x = self.get_argument('x')
				y = self.get_argument('y')
				client = tornado.httpclient.HTTPClient()
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'make_order', method='POST',
										headers={"Content-Type": "application/json"},
										body=simplejson.dumps(dict(pass_id=pass_id, coordinates={'x':x,'y':y})))
				result = simplejson.loads(response.body)
				token_taxi = self.get_token(result['taxi_id'], 'taxi')
				token_pass = self.get_cookie('my_session')
				marc = result['mark']
				model = result['model']
				statenumber = result['state_number']
				region = str(result['region'])
				body1 = urlencode({'message': 'Expect ' + marc + ' ' + model + ' with state number ' + statenumber + region,
                                 'token': token_pass})
				client.fetch(url_for_notification, method='POST',body=body1)
				body2 = urlencode({'message': 'Received an order x:'+ x + 'y:' + y,'token': token_taxi})
				client.fetch(url_for_notification, method='POST', body=body2)
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
			self.render('homepass.html')
		else:
			flash = Flash('You must sign in')
			self.set_flash(flash)
			self.redirect('/')
		self.finish()

#class Get_infoHandler(Check_sessionHandler):
#	def post(self):
#		try:
#			user_name = self.check_session()
#		except:
#			user_name = 'unregistered'
#		if user_name != 'unregistered':
#			try:
#				client = tornado.httpclient.HTTPClient()
#				response = client.fetch(url_for_logics + 'get_info', method='POST',
	#           	headers={"Content-Type": "application/json"}, body={'user_name': user_name})
#				result = json.loads(response.body)
#				username = result['username']
#				mark = result['mark']
#				model = result['model']
#				state_number = result['state_number']
#				region = result['region']
 #               ###############
#			except:
#				self.flash('Serves is temporarily unavailable')
#				self.redirect('/')

class Check_statusHandler(Check_sessionHandler, MyHandler):
	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		client = tornado.httpclient.AsyncHTTPClient()
		try:
			user_id = yield tornado.gen.Task(self.check_session())
		except:
			user_id = 'unregistered'
		if user_id != 'unregistered':
			try:
				user_type = self.get_cookie('my_type')
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'status', method='POST',
											    headers={"Content-Type": "application/json"},
                                                body=json.dumps(dict(user_type=user_type, user_id=user_id)))
				status = json.loads(response.body)['status']
				self.write(status)
			except:
				self.send_error(response.error)
		else:
			flash = Flash('You must sign in')
			self.set_flash(flash)
			self.redirect('/')
		self.finish()

class Concel_orderHandler(Check_sessionHandler, MyHandler, TokenHandler):
	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		try:
			user_id = self.check_session()
		except:
			user_id = 'unregistered'
		if user_id != 'unregistered':
			try:
				client = tornado.httpclient.AsyncHTTPClient()
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'concel', method='POST',
											    headers={"Content-Type": "application/json"},
                                                body=simplejson.dumps(dict(pass_id=user_id)))
				if response.code == 200:
					result = simplejson.loads(response.body)
					token_pass = self.get_cookie('my_session')
					body1 = urlencode({'message': 'Your order has been canceled',
						 'token': token_pass})
					client.fetch(url_for_notification, method='POST', body=body1)
					if result['taxi_id'] is not None:
						token_taxi = self.get_token(result['taxi_id'], 'taxi')
						body2 = urlencode({'message': 'Unfortunately order canceled',
							   'token': token_taxi})
						client.fetch(url_for_notification, method='POST', body=body2)
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
				self.redirect('/homepass')
		else:
			flash = Flash('You must sign in')
			self.set_flash(flash)
			self.redirect('/')
		self.finish()

class Start_calculationHandler(Check_sessionHandler, MyHandler):
	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		try:
			user_id = self.check_session()
		except:
			user_id = 'unregistered'
		if user_id != 'unregistered':
			try:
				client = tornado.httpclient.AsyncHTTPClient()
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'start_calc', method='POST',
											    headers={"Content-Type": "application/json"},
                                                body=simplejson.dumps(dict(taxi_id=user_id)))
				if response.code == 200:
					token_taxi = self.get_cookie('my_session')
					body = urlencode(
						{'message': 'Have a nice trip', 'token': token_taxi})
					client.fetch(url_for_notification, method='POST', body=body)
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
				self.redirect('/hometaxi')
		else:
			flash = Flash('You must sign in')
			self.set_flash(flash)
			self.redirect('/')
		self.finish()

class Stop_calculationHandler(Check_sessionHandler, MyHandler, TokenHandler):
	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		try:
			user_id = self.check_session()
		except:
			user_id = 'unregistered'
		if user_id != 'unregistered':
			try:
				client = tornado.httpclient.AsyncHTTPClient()
				response = yield tornado.gen.Task(client.fetch, url_for_logics + 'stop_calc', method='POST',
											    headers={"Content-Type": "application/json"},
                                                body=simplejson.dumps(dict(taxi_id=user_id)))
				if response.code == 200:
					result = simplejson.loads(response.body)
					token_taxi = self.get_cookie('my_session')
					token_pass = self.get_token(result['pass_id'], 'passenger')
					cost = str(result['cost'])
					body1 = urlencode({'message': 'Trip cost is ' + cost, 'token': token_pass})
					client.fetch(url_for_notification, method='POST', body=body1)
					body2 = urlencode({'message': 'Trip cost is ' + cost, 'token': token_taxi})
					client.fetch(url_for_notification, method='POST', body=body2)
			except:
				flash = Flash('Serves is temporarily unavailable')
				self.set_flash(flash)
				self.redirect('/hometaxi')
		else:
			flash = Flash('You must sign in')
			self.set_flash(flash)
			self.redirect('/')
		self.finish()

class Post_coordinatesHandler(Check_sessionHandler):
	@tornado.web.asynchronous
	@tornado.gen.engine
	def post(self):
		try:
			taxi_id = self.check_session()
			x = self.get_argument('x')
			y = self.get_argument('y')
			client = tornado.httpclient.HTTPClient()
			response = yield tornado.gen.Task(client.fetch, url_for_logics + 'coordinates', method='POST',
										headers={"Content-Type": "application/json"},
										body=simplejson.dumps(dict(user_id=taxi_id, coordinates={'x': x, 'y': y})))
		except:
			token = self.get_cookie('my_session')
			body = urlencode({'message': 'Connection to the server is lost', 'token': token})
			client = tornado.httpclient.HTTPClient()
			client.fetch(url_for_notification, method='POST', body=body)
		self.finish()

if __name__ == "__main__":
	tornado.options.parse_command_line()
	app = tornado.web.Application(
		handlers=[
			(r'/register', RegistrationHandler), (r'/', IndexHandler), (r'/type', Type_choiceHandler),
			(r'/register2', Registration2Handler), (r'/login', LoginHandler), (r'/logout', LogoutHandler),
			(r'/make_order', Make_orderHandler), (r'/concel_order', Concel_orderHandler),
			(r'/start_calc', Start_calculationHandler), (r'/stop_calc', Stop_calculationHandler),
			(r'/coordinates', Post_coordinatesHandler)],
	template_path = os.path.join(os.path.dirname(__file__), "templates"),
	static_path = os.path.join(os.path.dirname(__file__), "static"))
	http_server = tornado.httpserver.HTTPServer(app)
	http_server.listen(options.port)
	tornado.ioloop.IOLoop.instance().start()