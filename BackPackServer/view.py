from django.http import HttpResponse
from django.shortcuts import render
from model.models import BackPack
from BackPackServer.dbscan import Solution
import datetime
def hello(request):
    context = {}
    context['hello'] = 'Hello World!'
    return render(request, 'hello.html', context)

def testdb(request):
    backpack=BackPack()
    backpack.values=111.111
    backpack.latitude=38.9987989
    backpack.longitude=48.2323221
    backpack.time=datetime.datetime.now()
    backpack.save()
    return HttpResponse("<p>数据添加成功！</p>")

def dbscan(request):
    num=[[90, 90, 583490], [91, 90, 584970], [92, 90, 586080], [93, 90, 587042], [94, 90, 588004], [95, 90, 588929], [96, 90, 589817], [97, 90, 590335], [98, 90, 590742], [99, 90, 590927], [100, 90, 591038], [101, 90, 591593], [102, 90, 590853], [103, 90, 590261], [104, 90, 589669], [105, 90, 588929], [106, 90, 588855], [107, 90, 587153], [108, 90, 586043], [109, 90, 584674], [90, 89, 581381], [91, 89, 582861], [92, 89, 584082], [93, 89, 585414], [94, 89, 586450], [95, 89, 587449], [96, 89, 588078], [97, 89, 588855], [98, 89, 589188], [99, 89, 589336], [100, 89, 589262], [101, 89, 589225], [102, 89, 589188], [103, 89, 588744], [104, 89, 587893], [105, 89, 587116], [106, 89, 586265], [107, 89, 585303], [108, 89, 584341], [109, 89, 582972], [90, 88, 579531], [91, 88, 581011], [92, 88, 582232], [93, 88, 583453], [94, 88, 584600], [95, 88, 585488], [96, 88, 586413], [97, 88, 587264], [98, 88, 587671], [99, 88, 587486], [100, 88, 587375], [101, 88, 587301], [102, 88, 587227], [103, 88, 586782], [104, 88, 586043], [105, 88, 585229], [106, 88, 584341], [107, 88, 583342], [108, 88, 582306], [109, 88, 581270]]
    s=Solution()
    res=s.dbscan(num)
    context = {}
    context['hello'] = res
    return render(request, 'hello.html', context)