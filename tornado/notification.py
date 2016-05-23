import tornado.httpserver
import tornado.websocket
import tornado.ioloop
from urllib import urlencode
import pickle
import tornado.web
import socket

url_for_frontend = 'http://localhost:8000/'

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

class Broadcast1Handler(MyHandler):
    def get(self):
        token = self.get_cookie('my_session')
        if token is not None:
            body2 = urlencode({'token': token})
            client = tornado.httpclient.HTTPClient()
            response = client.fetch(url_for_frontend + 'check_session', method='POST',
                                    body=body2)
            if response.body != 'unregistered':
                if self.get_flash('error'):
                    flash = self.get_flash('error')
                    self.render('templates/homepass.html', form=flash.data, flash_msg=flash.message, login=True)
                else:
                    self.render('templates/homepass.html', flash_msg=None, login=True)
            else:
                flash = Flash('You must sign in')
                self.set_flash(flash)
                self.redirect('/')
        else:
            flash = Flash('You must sign in')
            self.set_flash(flash)
            self.redirect(url_for_frontend)

class Broadcast2Handler(MyHandler):
    def get(self):
        token = self.get_cookie('my_session')
        if token is not None:
            body2 = urlencode({'token': token})
            client = tornado.httpclient.HTTPClient()
            response = client.fetch(url_for_frontend + 'check_session', method='POST',
                                    body=body2)
            if response.body != 'unregistered':
                if self.get_flash('error'):
                    flash = self.get_flash('error')
                    self.render('templates/hometaxi.html', form=flash.data, flash_msg=flash.message, login=True)
                else:
                    self.render('templates/hometaxi.html', flash_msg=None, login=True)
            else:
                flash = Flash('You must sign in')
                self.set_flash(flash)
                self.redirect('/')
        else:
            flash = Flash('You must sign in')
            self.set_flash(flash)
            self.redirect(url_for_frontend)


class BroadcastHandler(tornado.web.RequestHandler):
    def post(self):
        message = self.get_argument('message')
        print(message)
        token = self.get_argument('token')
        for client in WSHandler.clients:
            if client.token == token:
                client.write_message(message)

class WSHandler(tornado.websocket.WebSocketHandler):
    clients = set()

    def __init__(self, *args, **kwargs):
        self.token = None
        super(WSHandler, self).__init__(*args, **kwargs)

    def open(self):
        if not self.token:
            self.token = self.get_cookie('my_session')
        self.clients.add(self)

    def on_close(self):
        self.clients.remove(self)


application = tornado.web.Application([
    (r'/', BroadcastHandler), (r'/taxi', Broadcast2Handler), (r'/pass', Broadcast1Handler), (r'/ws', WSHandler)])

if __name__ == "__main__":
    http_server = tornado.httpserver.HTTPServer(application)
    http_server.listen(8888)
    myIP = socket.gethostbyname(socket.gethostname())
    print ('*** Websocket Server Started at %s***' % myIP)
    tornado.ioloop.IOLoop.instance().start()