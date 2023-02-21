#post the json file to the server
#
import time

import requests
import json

url = "http://47.115.226.124/sensor/"
jsonurl = "C:\\Users\\jun\\PycharmProjects\\SafeHomeServer\\templates\\data.json"

#continue to post the json file to the server
while True:
    with open(jsonurl, 'r') as f:
        data = json.load(f)
        print(data)
        r = requests.post(url, data=data)
        print(r.status_code)
        print(r.text)
        #sleep for 1 second
        time.sleep(1)

#
#
