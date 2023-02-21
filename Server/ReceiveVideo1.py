import threading
import time

#import cv2
import socket
#import numpy as np
buffer = []
def server():
    # Create a TCP/IP socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # Bind the socket to the port
    server_address = ('172.27.26.67', 8888)
    print('starting up on {} port {}'.format(*server_address))
    sock.bind(server_address)
    sock.listen(2)
    print("Waiting for two connections, carema and android...")
    conn, addr = sock.accept()
    print("Connected to carema!")
    print("Waiting for android...")
    conn1, addr = sock.accept()
    print("Connected to android!")
    # conn.send(b"hello")
    while True:
        # 判断conn是否断开连接，如果断开连接则重新连接
        try:
            data = conn.recv(1024)
        except:
            print("waiting for camera!")
            conn, addr = sock.accept()
            print("Connected to camera!")
            data = conn.recv(1024)
        # 判断conn1是否断开连接，如果断开连接则重新连接
        try:
            conn1.send(data)
        except:
            print("waiting for android!")
            conn1, addr = sock.accept()
            print("Connected to android!")
            conn1.send(data)



    conn.close()
    conn1.close()
    sock.close()




if __name__ == "__main__":
    server()