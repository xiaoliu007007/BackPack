from django.http import HttpResponse
from django.shortcuts import render
from model.models import BackPack
from BackPackServer.dbscan import Solution
import datetime
import json

def hello(request):
    context = {}
    context['hello'] = 'Hello World!'
    print("hello")
    return render(request, 'hello.html', context)

def calculate(num):
    num1=[[90, 90, 583490], [91, 90, 584970], [92, 90, 586080], [93, 90, 587042], [94, 90, 588004], [95, 90, 588929], [96, 90, 589817], [97, 90, 590335], [98, 90, 590742], [99, 90, 590927], [100, 90, 591038], [101, 90, 591593], [102, 90, 590853], [103, 90, 590261], [104, 90, 589669], [105, 90, 588929], [106, 90, 588855], [107, 90, 587153], [108, 90, 586043], [109, 90, 584674], [90, 89, 581381], [91, 89, 582861], [92, 89, 584082], [93, 89, 585414], [94, 89, 586450], [95, 89, 587449], [96, 89, 588078], [97, 89, 588855], [98, 89, 589188], [99, 89, 589336], [100, 89, 589262], [101, 89, 589225], [102, 89, 589188], [103, 89, 588744], [104, 89, 587893], [105, 89, 587116], [106, 89, 586265], [107, 89, 585303], [108, 89, 584341], [109, 89, 582972], [90, 88, 579531], [91, 88, 581011], [92, 88, 582232], [93, 88, 583453], [94, 88, 584600], [95, 88, 585488], [96, 88, 586413], [97, 88, 587264], [98, 88, 587671], [99, 88, 587486], [100, 88, 587375], [101, 88, 587301], [102, 88, 587227], [103, 88, 586782], [104, 88, 586043], [105, 88, 585229], [106, 88, 584341], [107, 88, 583342], [108, 88, 582306], [109, 88, 581270], [90, 87, 577496], [91, 87, 579013], [92, 87, 580382], [93, 87, 581566], [94, 87, 582528], [95, 87, 583527], [96, 87, 584266], [97, 87, 585192], [98, 87, 585488], [99, 87, 585524], [100, 87, 585377], [101, 87, 585266], [102, 87, 585081], [103, 87, 584748], [104, 87, 584156], [105, 87, 583268], [106, 87, 582343], [107, 87, 581307], [108, 87, 580382], [109, 87, 579234], [90, 86, 575461], [91, 86, 576978], [92, 86, 578384], [93, 86, 579568], [94, 86, 580567], [95, 86, 581418], [96, 86, 582084], [97, 86, 582639], [98, 86, 583008], [99, 86, 583157], [100, 86, 583231], [101, 86, 583120], [102, 86, 583008], [103, 86, 582639], [104, 86, 582084], [105, 86, 581270], [106, 86, 580234], [107, 86, 579198], [108, 86, 578088], [109, 86, 576904], [90, 85, 573167], [91, 85, 574647], [92, 85, 575979], [93, 85, 577200], [94, 85, 578310], [95, 85, 579124], [96, 85, 579716], [97, 85, 580419], [98, 85, 580826], [99, 85, 580974], [100, 85, 580900], [101, 85, 580789], [102, 85, 580715], [103, 85, 580604], [104, 85, 579975], [105, 85, 579050], [106, 85, 578162], [107, 85, 577163], [108, 85, 575979], [109, 85, 574536], [90, 84, 570799], [91, 84, 572242], [92, 84, 573500], [93, 84, 574721], [94, 84, 575720], [95, 84, 576571], [96, 84, 577237], [97, 84, 578014], [98, 84, 578791], [99, 84, 578754], [100, 84, 578643], [101, 84, 578384], [102, 84, 578199], [103, 84, 578014], [104, 84, 577459], [105, 84, 576534], [106, 84, 575683], [107, 84, 574795], [108, 84, 573611], [109, 84, 572131], [90, 83, 568357], [91, 83, 569763], [92, 83, 570984], [93, 83, 572131], [94, 83, 573093], [95, 83, 573944], [96, 83, 574610], [97, 83, 575387], [98, 83, 575942], [99, 83, 576089], [100, 83, 575905], [101, 83, 575831], [102, 83, 575794], [103, 83, 575313], [104, 83, 574721], [105, 83, 573981], [106, 83, 573352], [107, 83, 572501], [108, 83, 571317], [109, 83, 569837], [90, 82, 565841], [91, 82, 567395], [92, 82, 568468], [93, 82, 569467], [94, 82, 570429], [95, 82, 571243], [96, 82, 571909], [97, 82, 572464], [98, 82, 572908], [99, 82, 573130], [100, 82, 573389], [101, 82, 573500], [102, 82, 573426], [103, 82, 573574], [104, 82, 572205], [105, 82, 571391], [106, 82, 570725], [107, 82, 570059], [108, 82, 568949], [109, 82, 567247], [90, 81, 563769], [91, 81, 565285], [92, 81, 566026], [93, 81, 566877], [94, 81, 567691], [95, 81, 568468], [96, 81, 569134], [97, 81, 569689], [98, 81, 570096], [99, 81, 570281], [100, 81, 570429], [101, 81, 570614], [102, 81, 570540], [103, 81, 570577], [104, 81, 569689], [105, 81, 568727], [106, 81, 567765], [107, 81, 567136], [108, 81, 565952], [109, 81, 564472], [90, 80, 561327], [91, 80, 562770], [92, 80, 563436], [93, 80, 563917], [94, 80, 564768], [95, 80, 565545], [96, 80, 566211], [97, 80, 566729], [98, 80, 567172], [99, 80, 567395], [100, 80, 567580], [101, 80, 567765], [102, 80, 567691], [103, 80, 567136], [104, 80, 566692], [105, 80, 565767], [106, 80, 564953], [107, 80, 563991], [108, 80, 562992], [109, 80, 561623]]
    num.extend(num1)
    print(num)
    s=Solution()
    res=s.dbscan(num)
    return res

"""
@description: 接受安卓数据并且存入Mysql
@author: lyj
@create: 2019/09/03
"""

def getMsg(request):
    msgs=request.POST.getlist("name")
    print("msgs")
    num=[]
    for msg in msgs:
        num1=[]
        data=msg.split('#')
        backpack=BackPack()
        backpack.time=datetime.datetime.strptime(data[0], "%Y-%m-%d %H:%M:%S")
        backpack.values=data[1]
        backpack.longitude=data[2]
        backpack.latitude=data[3]
        backpack.save()
        num1.append(int(float(data[2])))
        num1.append(int(float(data[3])))
        num1.append(int(float(data[1])))
        num.append(num1)
    res=calculate(num)
    ans={}
    ans['longitude']=116.364734
    ans['latitude']=39.968584
    print(ans)
    return HttpResponse(json.dumps(ans),status=200)