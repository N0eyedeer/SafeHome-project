from django.http import HttpResponse, StreamingHttpResponse
from django.shortcuts import render


# return the sensor.json file
def sensor(request):
    #receive the json data posted
    if request.method == 'POST':
        print(request.body)
        #save the json data to the file
        with open("/\\templates\\data.json", 'w') as f:
            f.write(request.body.decode())

        return HttpResponse("ok")
    #return no data message
    else:
        return HttpResponse("no data")


def data(request):
    return render(request, "data.json")























