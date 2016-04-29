import tornado.httpserver
import tornado.websocket
import tornado.ioloop
import tornado.web
import socket


class Broadcast1Handler(tornado.web.RequestHandler):
    def get(self):
        self.render('templates/homepass.html')

class Broadcast2Handler(tornado.web.RequestHandler):
    def get(self):
        self.render('templates/hometaxi.html')

class BroadcastHandler(tornado.web.RequestHandler):
    def post(self):
        message = self.get_argument('message')
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