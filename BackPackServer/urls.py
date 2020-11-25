"""BackPackServer URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.conf.urls import url
from BackPackServer import view
from BackPackServer import UploadForm

urlpatterns = [
    url(r'^hello$',view.hello),
    url(r'^getMsg/$',view.getMsg),
    url(r'^getData$',view.getData),
    url(r'^insertData$',view.insertData),
    url(r'^updateData$',view.updateData),
    url(r'^deleteData$',view.deleteData),
    url(r'^getType$',view.getType),
    url(r'^login$',view.login),
    url(r'^draw',view.draw),
    url(r'^upload',UploadForm.upload),
    url(r'^download',UploadForm.download),
]
