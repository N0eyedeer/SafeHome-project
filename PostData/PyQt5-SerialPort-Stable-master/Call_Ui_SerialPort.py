# 逻辑文件
import json
import re
import sys
import binascii
import time
from PyQt5.QtCore import QTimer, QUrl
from PyQt5.QtWidgets import *
from PyQt5.QtSerialPort import QSerialPort, QSerialPortInfo
from PyQt5.QtWidgets import QApplication, QMainWindow
#from PyQt5.QtWebEngineWidgets import *
from Ui_SerialPort import Ui_Form
from PyQt5.QtCore import QDate

class MyMainWindow(QMainWindow, Ui_Form):
    def __init__(self, parent=None):
        super(MyMainWindow, self).__init__(parent)
        self.setupUi(self)
        # 设置实例
        self.CreateItems()
        # 设置信号与槽
        self.CreateSignalSlot()
        
    # 设置实例 
    def CreateItems(self):
        # Qt 串口类
        self.com = QSerialPort()
        # Qt 定时器类
        self.timer = QTimer(self) #初始化一个定时器
        self.timer.timeout.connect(self.ShowTime) #计时结束调用operate()方法
        self.timer.start(10000) #设置计时间隔 100ms 并启动
    
    # 设置信号与槽
    def CreateSignalSlot(self):
        self.Com_Open_Button.clicked.connect(self.Com_Open_Button_clicked) 
        self.Com_Close_Button.clicked.connect(self.Com_Close_Button_clicked) 
        self.Send_Button.clicked.connect(self.SendButton_clicked) 
        self.Com_Refresh_Button.clicked.connect(self.Com_Refresh_Button_Clicked) 
        self.com.readyRead.connect(self.Com_Receive_Data) # 接收数据
        self.hexSending_checkBox.stateChanged.connect(self.hexShowingClicked)
        self.hexSending_checkBox.stateChanged.connect(self.hexSendingClicked)
        self.About_Button.clicked.connect(self.Goto_GitHub)
        
    # 跳转到 GitHub 查看源代码
    def Goto_GitHub(self):
       # self.browser = QWebEngineView()
        self.browser.load(QUrl('https://github.com/Oslomayor/PyQt5-SerialPort-Stable'))
        self.setCentralWidget(self.browser)
    
    # 显示时间
    def ShowTime(self):
        self.Time_Label.setText(time.strftime("%B %d, %H:%M:%S", time.localtime()))
        
    # 串口发送数据
    def Com_Send_Data(self):
        txData = self.textEdit_Send.toPlainText()
        if len(txData) == 0 :
            return
        if self.hexSending_checkBox.isChecked() == False:
            self.com.write(txData.encode('UTF-8'))
        else:
            Data = txData.replace(' ', '')
            # 如果16进制不是偶数个字符, 去掉最后一个, [ ]左闭右开
            if len(Data)%2 == 1:
                Data = Data[0:len(Data)-1]
            # 如果遇到非16进制字符
            if Data.isalnum() is False:
                QMessageBox.critical(self, '错误', '包含非十六进制数')
            try:
                hexData = binascii.a2b_hex(Data)
            except:
                QMessageBox.critical(self, '错误', '转换编码错误')
                return
            # 发送16进制数据, 发送格式如 ‘31 32 33 41 42 43’, 代表'123ABC'
            try:
                self.com.write(hexData) 
            except:
                QMessageBox.critical(self, '异常', '十六进制发送错误')
                return

    # 串口接收数据
    def Com_Receive_Data(self):
        try:
            rxData = bytes(self.com.readAll())
        except:
            QMessageBox.critical(self, '严重错误', '串口接收数据错误')
        if self.hexShowing_checkBox.isChecked() == False:
            try:
                self.textEdit_Recive.insertPlainText(rxData.decode('UTF-8'))
            except:
                pass
        else:
            Data = binascii.b2a_hex(rxData).decode('ascii')
            print(Data)
            # re 正则表达式 (.{2}) 匹配两个字母
            hexStr = ' 0x'.join(re.findall('(.{2})', Data))
            # 补齐第一个 0x
            hexStr = '0x' + hexStr
            print(hexStr)
            self.textEdit_Recive.insertPlainText(hexStr)
            self.textEdit_Recive.insertPlainText(' ')
        # 获取textEdit_Recive里的文本
        text = self.textEdit_Recive.toPlainText()
        self.toJson(text)
        print(text + '@@@')
        # 储存文本到data.txt
        with open('data.txt', 'w') as f:
            f.write(text)

        # 清空文本框
        self.textEdit_Recive.clear()
    def toJson(self,text):
        # 如果text含有yan_wu:，则将其后面的值替换data.json中的yan_wu的值
        if 'yan_wu:' in text:
            # 获取text中的yan_wu后面的值
            yan_wu = text.split('yan_wu:')[1].split(' ')[0]
            # 将data.json中的yan_wu的值替换为text中的yan_wu的值
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'r') as f:
                data = json.load(f)
                data['yan_wu'] = yan_wu.replace('\n', '')
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'w') as f:
                json.dump(data, f)
        if 'yu_di:' in text:
            yu_di = text.split('yu_di:')[1].split(' ')[0]
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'r') as f:
                data = json.load(f)
                data['yu_di'] = yu_di.replace('\n', '')
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'w') as f:
                json.dump(data, f)
        if 'shi_du:' in text and 'wen_du:' in text:
            shi_du = text.split(':')[1].split('wen_du')[0]
            wen_du = text.split(':')[2].split('yudi')[0]
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'r') as f:
                data = json.load(f)
                data['shi_du'] = shi_du.replace('\n', '')
                data['wen_du'] = wen_du.replace('\n', '')
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'w') as f:
                json.dump(data, f)
        if 'ren' in text:
            #=0
            #ren=1
            #zhen=0
            #huo=0
            #yan=0
            # 5个等号后面的值分别代表yudi ren zhen huo yan，更新data.json中相对应的值
            yudi = text.split('=')[1].split('ren')[0]
            ren = text.split('=')[2].split('zhen')[0]
            zhen = text.split('=')[3].split('huo')[0]
            huo = text.split('=')[4].split('yan')[0]
            yan = text.split('=')[5].split(' ')[0]
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'r') as f:
                data = json.load(f)
                data['yudi'] = yudi.replace('\n', '')
                data['ren'] = ren.replace('\n', '')
                data['zhen'] = zhen.replace('\n', '')
                data['huo'] = huo.replace('\n', '')
                data['yan'] = yan.replace('\n', '')
            with open('C:\\Users\\jun\\PycharmProjects\\djangoProject2\\templates\\data.json', 'w') as f:
                json.dump(data, f)

    # 串口刷新
    def Com_Refresh_Button_Clicked(self):
        self.Com_Name_Combo.clear()
        com = QSerialPort()
        com_list = QSerialPortInfo.availablePorts()
        for info in com_list:
            com.setPort(info)
            if com.open(QSerialPort.ReadWrite):
                self.Com_Name_Combo.addItem(info.portName())
                com.close()
    
    # 16进制显示按下   
    def hexShowingClicked(self):
        if self.hexShowing_checkBox.isChecked() == True:
            # 接收区换行
            self.textEdit_Recive.insertPlainText('\n')
    
    # 16进制发送按下   
    def hexSendingClicked(self):
        if self.hexSending_checkBox.isChecked() == True:
            pass
    
    # 发送按钮按下
    def SendButton_clicked(self):
        self.Com_Send_Data()

        
    # 串口刷新按钮按下
    def Com_Open_Button_clicked(self):
        #### com Open Code here ####
        comName = self.Com_Name_Combo.currentText()
        comBaud = int(self.Com_Baud_Combo.currentText())
        self.com.setPortName(comName)
        try:
            if self.com.open(QSerialPort.ReadWrite) == False:
                QMessageBox.critical(self, '严重错误', '串口打开失败')
                return
        except:
            QMessageBox.critical(self, '严重错误', '串口打开失败')
            return
        self.Com_Close_Button.setEnabled(True)
        self.Com_Open_Button.setEnabled(False)
        self.Com_Refresh_Button.setEnabled(False)
        self.Com_Name_Combo.setEnabled(False)
        self.Com_Baud_Combo.setEnabled(False)
        self.Com_isOpenOrNot_Label.setText('  已打开')
        self.com.setBaudRate(comBaud)
    
    def Com_Close_Button_clicked(self):
        self.com.close()
        self.Com_Close_Button.setEnabled(False)
        self.Com_Open_Button.setEnabled(True)
        self.Com_Refresh_Button.setEnabled(True)
        self.Com_Name_Combo.setEnabled(True)
        self.Com_Baud_Combo.setEnabled(True)
        self.Com_isOpenOrNot_Label.setText('  已关闭')
    
if __name__ == '__main__':
    app = QApplication(sys.argv)
    myWin = MyMainWindow()
    myWin.show()
    sys.exit(app.exec_())
    
