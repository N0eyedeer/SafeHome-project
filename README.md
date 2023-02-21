# Safehome

> 整体思路：传感器和摄像头直接连接电脑，电脑上传至服务器，服务器转发这些信息至手机端

## 文件说明
1. 运行服务器django项目接收传感器数据，运行命令：`python manage.py runserver；
2. 运行服务器poseVideo.py文件，接收摄像头数据，运行命令：`python poseVideo.py；
3. 运行本地电脑Call_Ui_SerialPort.py文件，修改波特率为115200，刷新，获取端口号并打开串口获取传感器数据；
4. 运行本地postSensordata.py文件，将传感器数据上传至服务器；
5. 运行本地postCamera.py文件，将摄像头数据上传至服务器；
6. 运行安卓项目，接收服务器数据并显示。

## 开发环境
1. Python 3.6 (Python 解释器, 运行 Python 必备)
2. Qt Designer (用于设计界面，生成 ui 文件)
3. Android Studio (用于开发安卓应用)
4. PyCharm (用于开发 Python 应用)
5. Django (用于开发服务器)
6. PySerial (用于读取串口数据)
7. OpenCV (用于读取摄像头数据)
8. PyQt5 (用于开发桌面应用)

