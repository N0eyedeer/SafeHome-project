import socket
import time
import cv2
import numpy as np

def client():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('47.115.226.124', 8888))
    #s.connect(('127.0.0.1', 8888))
    #s.recv(1024)
    #获取摄像头，发送给服务器
    cap = cv2.VideoCapture(0)
    # 镜像
    while True:
        #每次读取一帧，左右翻转
        ret, frame = cap.read()
        frame = cv2.flip(frame, 1)
        #降低分辨率为很小的值
        frame = cv2.resize(frame, (0, 0), fx=0.5, fy=0.5)
        #降低图像质量
        encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 40]
        #将图片编码
        result, imgencode = cv2.imencode('.jpg', frame, encode_param)
        #将图片转换为byte类型
        data = np.array(imgencode)
        stringData = data.tostring()
        s.send(data.tobytes())
        print(len(data))
        time.sleep(0.1)
        #s.recv(16)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    cap.release()
    cv2.destroyAllWindows()

if __name__ == '__main__':
    client()
