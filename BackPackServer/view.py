from django.http import HttpResponse
from django.shortcuts import render
from model.models import BackPack
from BackPackServer.dbscan import Solution
from BackPackServer.optics import optics
from django.core import serializers
from django.db import connection
from model import models

import datetime
import json
from django.core.paginator import Paginator

def hello(request):
    context = {}
    context['hello'] = 'Hello World!'
    print("hello")
    #serializers.serialize("json", ans)
    return render(request, 'hello.html', context)


def draw(request):
    ans = {}
    lists = []
    if request.POST.get("type") == "2019-12-25":
        list1 = {"lng":116.364291, "lat":39.969988}
        list2 = {"lng":116.3642, "lat":39.969986}
        list3 = {"lng":116.36422, "lat":39.96992}
        lists.append(list1)
        lists.append(list2)
        lists.append(list3)
        ans["end"]={"lng":116.3641,"lat":39.9691}
    else :
        lists.append({"lng":116.427151, "lat":39.97326})
        lists.append({"lng":116.42715, "lat":39.97326})
        lists.append({"lng":116.427172, "lat":39.973261})
        lists.append({"lng":116.427189, "lat":39.973263})
        lists.append({"lng":116.427219, "lat":39.973258})
        lists.append({"lng":116.427226, "lat":39.973253})
        lists.append({"lng":116.427274, "lat":39.973213})
        lists.append({"lng":116.427277, "lat":39.973195})
        lists.append({"lng":116.427263, "lat":39.973156})
        lists.append({"lng":116.427236, "lat":39.97312})
        lists.append({"lng":116.427228, "lat":39.973118})
        lists.append({"lng":116.427218, "lat":39.973107})
        lists.append({"lng":116.427214, "lat":39.973098})
        lists.append({"lng":116.427184, "lat":39.973103})
        lists.append({"lng":116.427173, "lat":39.973116})
        lists.append({"lng":116.42717, "lat":39.973122})
        lists.append({"lng":116.427169, "lat":39.973141})
        lists.append({"lng":116.427174, "lat":39.973153})
        lists.append({"lng":116.427182, "lat":39.973166})
        lists.append({"lng":116.427188, "lat":39.973172})
        lists.append({"lng":116.427201, "lat":39.973183})
        lists.append({"lng":116.427209, "lat":39.973196})
        lists.append({"lng":116.427228, "lat":39.973208})
        lists.append({"lng":116.427238, "lat":39.973212})



        ans["end"]={"lng":116.427213,"lat":39.973171}

    data = {}
    data["list"] = lists
    data["total"] = len(lists)
    ans["data"] = data
    return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')


def deleteData(request):
    id=request.POST.get("id")
    models.BackPack.objects.filter(id=id).delete()
    ans={"data":"ok","msg":"成功"}
    return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')

def updateData(request):
    id=request.POST.get("id")
    backpack = models.BackPack.objects.get(id=id)
    backpack.time=datetime.datetime.strptime(request.POST.get("time"), "%Y-%m-%d %H:%M:%S")
    backpack.nai_jishu=request.POST.get("nai_jishu")
    backpack.nai_jiliang=request.POST.get("nai_jiliang")
    backpack.gm_jishu=request.POST.get("gm_jishu")
    backpack.gm_jiliang=request.POST.get("gm_jiliang")
    backpack.n_jishu=request.POST.get("n_jishu")
    backpack.n_jiliang=request.POST.get("n_jiliang")
    backpack.longitude=request.POST.get("longitude")
    backpack.latitude=request.POST.get("latitude")
    backpack.alarm_status=request.POST.get("alarm_status")
    backpack.type=request.POST.get("time")[:10]
    backpack.save()
    ans={"data":"ok","msg":"成功"}
    return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')

def insertData(request):
    backpack=BackPack()
    backpack.time=datetime.datetime.strptime(request.POST.get("time"), "%Y-%m-%d %H:%M:%S")
    backpack.nai_jishu=request.POST.get("nai_jishu")
    backpack.nai_jiliang=request.POST.get("nai_jiliang")
    backpack.gm_jishu=request.POST.get("gm_jishu")
    backpack.gm_jiliang=request.POST.get("gm_jiliang")
    backpack.n_jishu=request.POST.get("n_jishu")
    backpack.n_jiliang=request.POST.get("n_jiliang")
    backpack.longitude=request.POST.get("longitude")
    backpack.latitude=request.POST.get("latitude")
    backpack.alarm_status=request.POST.get("alarm_status")
    backpack.type=request.POST.get("time")[:10]
    backpack.save()
    ans={"data":"ok","msg":"成功"}
    return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')



def getData(request):
    print(request.POST.get("page"),request.POST.get("pageSize"),request.POST.get("type"))
    pageNo=int(request.POST.get("page"))
    pageSize=int(request.POST.get("pageSize"))

    if request.POST.get("type")=="":
        SQL="select * from model_backpack limit {0},{1}".format((pageNo-1)*pageSize,pageSize)
        SQL_count="select count(*) from model_backpack "
    else:
        type=request.POST.get("type")
        SQL="select * from model_backpack where type='{0}' limit {1},{2}".format(type,(pageNo-1)*pageSize,pageSize)
        SQL_count="select count(*) from model_backpack where type='{0}' ".format(type)

    lists=[]
    cur = connection.cursor()
    cur.execute(SQL)
    rows = cur.fetchall()
    for row in rows:
        list={}
        list["id"]=row[0]
        list["time"]=row[1]
        list["nai_jishu"]=row[2]
        list["nai_jiliang"]=row[3]
        list["gm_jishu"]=row[4]
        list["gm_jiliang"]=row[5]
        list["n_jishu"]=row[6]
        list["n_jiliang"]=row[7]
        list["longitude"]=row[8]
        list["latitude"]=row[9]
        list["alarm_status"]=row[10]
        list["type"]=row[11]
        lists.append(list)
    ans={}
    ans["data"]=lists
    ans["pageNo"]=pageNo
    ans["pageSize"]=pageSize

    cur.execute(SQL_count)
    ans["totalRow"]= cur.fetchone()[0]

    #print(ans)
    return HttpResponse(json.dumps(ans,cls=DateEncoder),status=200,content_type='application/json; charset=utf-8')

def getType(request):
    list=[]
    cur = connection.cursor()
    cur.execute("select distinct(type) as type from model_backpack")
    rows = cur.fetchall()
    for row in rows:
        list.extend(row)
    ans={}
    ans["type"]=list
    return HttpResponse(json.dumps(ans,cls=DateEncoder),status=200,content_type='application/json; charset=utf-8')

def login(request):
    name=request.POST.get("name")
    password=request.POST.get("password")
    print(name)
    print(password)
    user=models.User.objects.get(name=name)
    if user.password==password:
        ans={"data":"ok","msg":"成功","name":name}
        return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')
    ans={"data":"fail","msg":"密码不正确","name":name}
    return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')















def calculate(num):
    #num1=[[90, 90, 583490], [91, 90, 584970], [92, 90, 586080], [93, 90, 587042], [94, 90, 588004], [95, 90, 588929], [96, 90, 589817], [97, 90, 590335], [98, 90, 590742], [99, 90, 590927], [100, 90, 591038], [101, 90, 591593], [102, 90, 590853], [103, 90, 590261], [104, 90, 589669], [105, 90, 588929], [106, 90, 588855], [107, 90, 587153], [108, 90, 586043], [109, 90, 584674], [90, 89, 581381], [91, 89, 582861], [92, 89, 584082], [93, 89, 585414], [94, 89, 586450], [95, 89, 587449], [96, 89, 588078], [97, 89, 588855], [98, 89, 589188], [99, 89, 589336], [100, 89, 589262], [101, 89, 589225], [102, 89, 589188], [103, 89, 588744], [104, 89, 587893], [105, 89, 587116], [106, 89, 586265], [107, 89, 585303], [108, 89, 584341], [109, 89, 582972], [90, 88, 579531], [91, 88, 581011], [92, 88, 582232], [93, 88, 583453], [94, 88, 584600], [95, 88, 585488], [96, 88, 586413], [97, 88, 587264], [98, 88, 587671], [99, 88, 587486], [100, 88, 587375], [101, 88, 587301], [102, 88, 587227], [103, 88, 586782], [104, 88, 586043], [105, 88, 585229], [106, 88, 584341], [107, 88, 583342], [108, 88, 582306], [109, 88, 581270], [90, 87, 577496], [91, 87, 579013], [92, 87, 580382], [93, 87, 581566], [94, 87, 582528], [95, 87, 583527], [96, 87, 584266], [97, 87, 585192], [98, 87, 585488], [99, 87, 585524], [100, 87, 585377], [101, 87, 585266], [102, 87, 585081], [103, 87, 584748], [104, 87, 584156], [105, 87, 583268], [106, 87, 582343], [107, 87, 581307], [108, 87, 580382], [109, 87, 579234], [90, 86, 575461], [91, 86, 576978], [92, 86, 578384], [93, 86, 579568], [94, 86, 580567], [95, 86, 581418], [96, 86, 582084], [97, 86, 582639], [98, 86, 583008], [99, 86, 583157], [100, 86, 583231], [101, 86, 583120], [102, 86, 583008], [103, 86, 582639], [104, 86, 582084], [105, 86, 581270], [106, 86, 580234], [107, 86, 579198], [108, 86, 578088], [109, 86, 576904], [90, 85, 573167], [91, 85, 574647], [92, 85, 575979], [93, 85, 577200], [94, 85, 578310], [95, 85, 579124], [96, 85, 579716], [97, 85, 580419], [98, 85, 580826], [99, 85, 580974], [100, 85, 580900], [101, 85, 580789], [102, 85, 580715], [103, 85, 580604], [104, 85, 579975], [105, 85, 579050], [106, 85, 578162], [107, 85, 577163], [108, 85, 575979], [109, 85, 574536], [90, 84, 570799], [91, 84, 572242], [92, 84, 573500], [93, 84, 574721], [94, 84, 575720], [95, 84, 576571], [96, 84, 577237], [97, 84, 578014], [98, 84, 578791], [99, 84, 578754], [100, 84, 578643], [101, 84, 578384], [102, 84, 578199], [103, 84, 578014], [104, 84, 577459], [105, 84, 576534], [106, 84, 575683], [107, 84, 574795], [108, 84, 573611], [109, 84, 572131], [90, 83, 568357], [91, 83, 569763], [92, 83, 570984], [93, 83, 572131], [94, 83, 573093], [95, 83, 573944], [96, 83, 574610], [97, 83, 575387], [98, 83, 575942], [99, 83, 576089], [100, 83, 575905], [101, 83, 575831], [102, 83, 575794], [103, 83, 575313], [104, 83, 574721], [105, 83, 573981], [106, 83, 573352], [107, 83, 572501], [108, 83, 571317], [109, 83, 569837], [90, 82, 565841], [91, 82, 567395], [92, 82, 568468], [93, 82, 569467], [94, 82, 570429], [95, 82, 571243], [96, 82, 571909], [97, 82, 572464], [98, 82, 572908], [99, 82, 573130], [100, 82, 573389], [101, 82, 573500], [102, 82, 573426], [103, 82, 573574], [104, 82, 572205], [105, 82, 571391], [106, 82, 570725], [107, 82, 570059], [108, 82, 568949], [109, 82, 567247], [90, 81, 563769], [91, 81, 565285], [92, 81, 566026], [93, 81, 566877], [94, 81, 567691], [95, 81, 568468], [96, 81, 569134], [97, 81, 569689], [98, 81, 570096], [99, 81, 570281], [100, 81, 570429], [101, 81, 570614], [102, 81, 570540], [103, 81, 570577], [104, 81, 569689], [105, 81, 568727], [106, 81, 567765], [107, 81, 567136], [108, 81, 565952], [109, 81, 564472], [90, 80, 561327], [91, 80, 562770], [92, 80, 563436], [93, 80, 563917], [94, 80, 564768], [95, 80, 565545], [96, 80, 566211], [97, 80, 566729], [98, 80, 567172], [99, 80, 567395], [100, 80, 567580], [101, 80, 567765], [102, 80, 567691], [103, 80, 567136], [104, 80, 566692], [105, 80, 565767], [106, 80, 564953], [107, 80, 563991], [108, 80, 562992], [109, 80, 561623]]
    """num1=[[90, 90, 583490], [91, 90, 584970], [92, 90, 586080], [93, 90, 587042], [94, 90, 588004], [95, 90, 588929],
          [96, 90, 589817], [97, 90, 590335], [98, 90, 590742], [99, 90, 590927], [100, 90, 591038], [101, 90, 591593],
          [102, 90, 590853], [103, 90, 590261], [104, 90, 589669], [105, 90, 588929], [106, 90, 588855], [107, 90, 587153],
          [108, 90, 586043], [109, 90, 584674], [90, 89, 581381], [91, 89, 582861], [92, 89, 584082], [93, 89, 585414],
          [94, 89, 586450], [95, 89, 587449], [96, 89, 588078], [97, 89, 588855], [98, 89, 589188], [99, 89, 589336],
          [96, 88, 586413], [97, 88, 587264], [98, 88, 587671], [99, 88, 587486], [100, 88, 587375], [101, 88, 587301],
          [102, 88, 587227], [103, 88, 586782], [104, 88, 586043], [105, 88, 585229], [106, 88, 584341], [107, 88, 583342],
          [108, 88, 582306], [109, 88, 581270], [90, 87, 577496], [91, 87, 579013], [92, 87, 580382], [93, 87, 581566],
          [94, 87, 582528], [95, 87, 583527], [96, 87, 584266], [97, 87, 585192], [98, 87, 585488], [99, 87, 585524],
          [100, 87, 585377], [101, 87, 585266], [102, 87, 585081], [103, 87, 584748], [104, 87, 584156], [105, 87, 583268],
          [106, 87, 582343], [107, 87, 581307], [108, 87, 580382], [109, 87, 579234], [90, 86, 575461], [91, 86, 576978],
          [92, 86, 578384], [93, 86, 579568], [94, 86, 580567], [95, 86, 581418], [96, 86, 582084], [97, 86, 582639],
          [98, 86, 583008], [99, 86, 583157], [100, 86, 583231], [101, 86, 583120], [102, 86, 583008], [103, 86, 582639],
          [104, 86, 582084], [105, 86, 581270], [106, 86, 580234], [107, 86, 579198], [108, 86, 578088], [109, 86, 576904]]
    """

    num1=[[0.00090, 0.00090, 583490], [0.00091, 0.00090, 584970], [0.00092, 0.00090, 586080], [0.00093, 0.00090, 587042],
          [0.00094, 0.00090, 588004], [0.00095, 0.00090, 588929], [0.00096, 0.00090, 589817], [0.00097, 0.00090, 590335],
          [0.00098, 0.00090, 590742], [0.00099, 0.00090, 590927], [0.00100, 0.00090, 591038], [0.00101,0.00090, 591593],
          [0.00102, 0.00090, 590853], [0.00103, 0.00090, 590261], [0.00104, 0.00090, 589669], [0.00105, 0.00090, 588929],
          [0.00106, 0.00090, 588855], [0.00107, 0.00090, 587153], [0.00108, 0.00090, 586043], [0.00109, 0.00090, 584674],
          [0.00090, 0.00089, 581381], [0.00091, 0.00089, 582861], [0.00092, 0.00089, 584082], [0.00093, 0.00089, 585414],
          [0.00094, 0.00089, 586450], [0.00095, 0.00089, 587449], [0.00096, 0.00089, 588078], [0.00097, 0.00089, 588855],
          [0.00098, 0.00089, 589188], [0.00099, 0.00089, 589336], [0.00096, 0.00088, 586413], [0.00097, 0.00088, 587264],
          [0.00098, 0.00088, 587671], [0.00099, 0.00088, 587486], [0.00100, 0.00088, 587375], [0.00101, 0.00088, 587301],
          [0.00102, 0.00088, 587227], [0.00103, 0.00088, 586782], [0.00104, 0.00088, 586043], [0.00105, 0.00088, 585229],
          [0.00106, 0.00088, 584341], [0.00107, 0.00088, 583342], [0.00108, 0.00088, 582306], [0.00109, 0.00088, 581270],
          [0.00090, 0.00087, 577496], [0.00091, 0.00087, 579013], [0.00092, 0.00087, 580382], [0.00093, 0.00087, 581566],
          [0.00094, 0.00087, 582528], [0.00095, 0.00087, 583527], [0.00096, 0.00087, 584266], [0.00097, 0.00087, 585192],
          [0.00098, 0.00087, 585488], [0.00099, 0.00087, 585524], [0.00100, 0.00087, 585377], [0.00101, 0.00087, 585266],
          [0.00102, 0.00087, 585081], [0.00103, 0.00087, 584748], [0.00104, 0.00087, 584156], [0.00105, 0.00087, 583268],
          [0.00106, 0.00087, 582343], [0.00107, 0.00087, 581307], [0.00108, 0.00087, 580382], [0.00109, 0.00087, 579234],
          [0.00090, 0.00086, 575461], [0.00091, 0.00086, 576978], [0.00092, 0.00086, 578384], [0.00093, 0.00086, 579568],
          [0.00094, 0.00086, 580567], [0.00095, 0.00086, 581418], [0.00096, 0.00086, 582084], [0.00097, 0.00086, 582639],
          [0.00098, 0.00086, 583008], [0.00099, 0.00086, 583157], [0.00100, 0.00086, 583231], [0.00101, 0.00086, 583120],
          [0.00102, 0.00086, 583008], [0.00103, 0.00086, 582639], [0.00104, 0.00086, 582084], [0.00105, 0.00086, 581270],
          [0.00106, 0.00086, 580234], [0.00107, 0.00086, 579198], [0.00108, 0.00086, 578088], [0.00109, 0.00086, 576904]]
    num.extend(num1)
    """
    for i in range(len(num)):
        num[i][0]=int(num[i][0]*100000)
        num[i][1]=int(num[i][1]*100000)
    print(num)
    """

    #num.extend(num1)
    s=Solution()
    res=s.dbscan(num)
    print(res,len(num1))
    res[0]=int(res[0]*1000000)
    res[1]=int(res[1]*1000000)
    res[0]=res[0]/1000000
    res[1]=res[1]/1000000
    """
    s1=optics()
    res=s1.optics(num)
    print(res)
    """
    return res

"""
@description: 接受安卓数据并且存入Mysql
@author: lyj
@create: 2019/09/03
"""

def getMsg(request):
    msgs=request.POST.getlist("name")
    text=request.POST.get("msg")
    print("------------------------"+text)
    if text=="save":
        print("save")
        for msg in msgs:
            data=msg.split('#')
            backpack=BackPack()
            backpack.time=datetime.datetime.strptime(data[0], "%Y-%m-%d %H:%M:%S")
            backpack.nai_jishu=data[1]
            backpack.nai_jiliang=data[2]
            backpack.gm_jishu=data[3]
            backpack.gm_jiliang=data[4]
            backpack.n_jishu=data[5]
            backpack.n_jiliang=data[6]
            backpack.longitude=data[7]
            backpack.latitude=data[8]
            backpack.alarm_status=data[9]
            backpack.save()
        return

    #print(msgs[0])
    num=[]
    print("predict")
    for msg in msgs:
        data=msg.split('#')
        backpack=BackPack()
        backpack.time=datetime.datetime.strptime(data[0], "%Y-%m-%d %H:%M:%S")
        backpack.nai_jishu=data[1]
        backpack.nai_jiliang=data[2]
        backpack.gm_jishu=data[3]
        backpack.gm_jiliang=data[4]
        backpack.n_jishu=data[5]
        backpack.n_jiliang=data[6]
        backpack.longitude=data[7]
        backpack.latitude=data[8]
        backpack.alarm_status=data[9]
        backpack.save()
        if int(data[10])==2:
            num1=[]
            """
            num1.append(int(float(data[2])))
            num1.append(int(float(data[3])))
            num1.append(int(float(data[1])))
            """
            num1.append(float(data[7]))
            num1.append(float(data[8]))
            num1.append(float(data[1]))
            print(num1)
            num.append(num1)

    res=calculate(num)
    print(res)
    ans={}
    ans['longitude']=116.364734
    ans['latitude']=39.968584
    print(ans)
    return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')

class DateEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj,datetime.datetime):
            return obj.strftime("%Y-%m-%d %H:%M:%S")
        else:
            return json.JSONEncoder.default(self,obj)