from django.shortcuts import render
from django.shortcuts import HttpResponse
from django.db import connection
from django import forms
from django.forms import fields
from django.http import FileResponse
import json

class UploadForm(forms.Form):
    user = fields.CharField()
    img = fields.FileField()
def upload(request):
    print(11)
    if request.method == 'GET':
        return render(request,'upload.html')
    else:
        print(1111)
        # obj = UploadForm(request.POST,request.FILES)
        # if obj.is_valid():
        #     user = obj.cleaned_data['user']
        #     img = obj.cleaned_data['img']
        #user = request.POST.get('user')
        print(request.FILES)
        img  = request.FILES.get('file')
        # img是对象（文件大小，文件名称,文件内容。。。）
        #print(img.name)
        print(img.size)
        f = open("file",'wb')
        for line in img.chunks():
            f.write(line)
        f.close()
        ans={"data":"ok","msg":"成功"}
        return HttpResponse(json.dumps(ans),status=200,content_type='application/json; charset=utf-8')

def download(request):
    f = open("file",'wb')
    SQL="select * from model_backpack "
    cur = connection.cursor()
    cur.execute(SQL)
    rows = cur.fetchall()
    for row in rows:
        s = ""
        s += str(row[0])
        s += " " + str(row[1])
        s += " " + str(row[2])
        s += " " + str(row[3])
        s += " " + str(row[4])
        s += " " + str(row[5])
        s += " " + str(row[6])
        s += " " + str(row[7])
        s += " " + str(row[8])
        s += " " + str(row[9])
        s += " " + str(row[10])
        s += " " + str(row[11])
        s += "\n"
        print(s)
        f.write(bytes(s,'UTF-8'))
    f.close()

    file = open('file', 'rb')
    response = FileResponse(file)
    response['Content-Type'] = 'application/octet-stream'
    response['Content-Disposition'] = 'attachment;filename="shuju.txt"'
    return response