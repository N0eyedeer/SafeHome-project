from django.contrib import admin
from django.urls import path, include
from . import views
from  django.conf import settings
from  django.conf.urls. static  import  static
urlpatterns = [
    path('data/', views.data, name='data'),
    path('sensor/', views.sensor, name='sensor')
]+  static (settings.STATIC_URL, document_root = settings.STATIC_ROOT)
