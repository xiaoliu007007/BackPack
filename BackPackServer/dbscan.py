import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets
from sklearn.cluster import DBSCAN

class Solution():
    x1=[]
    x2=[]
    ans=[]
    def getCircle(self,p1, p2, p3):
        circle=[]
        x21 = p2[0] - p1[0]
        y21 = p2[1] - p1[1]
        x32 = p3[0] - p2[0]
        y32 = p3[1] - p2[1]
        # three colinear
        if (x21 * y32 - x32 * y21 == 0 or x21==0):
            return None
        xy21 = p2[0] * p2[0] - p1[0] * p1[0] + p2[1]* p2[1]- p1[1] * p1[1]
        xy32 = p3[0] * p3[0] - p2[0] * p2[0] + p3[1] * p3[1] - p2[1] * p2[1]
        y0 = (x32 * xy21 - x21 * xy32) / (2 * (y21 * x32 - y32 * x21))
        x0 = (xy21 - 2 * y0 * y21) / (2.0 * x21)
        circle.append(x0)
        circle.append(y0)
        return circle

    def dbscan(self,num):
        num.sort(key=lambda k:k[2],reverse=False)
        res=[]
        interval=370; #误差
        for i in range(len(num)):
            for j in range(i+1,len(num)):
                if num[j][2]-num[i][2]>interval:
                    break
                for n in range(j+1,len(num)):
                    if num[n][2]-num[i][2]>interval:
                        break
                    ans=self.getCircle(num[i],num[j],num[n])
                    if ans:
                        self.x1.append(ans[0])
                        self.x2.append(ans[1])


        x_=np.array(self.x1)
        x_.reshape(-1,1)
        y_=np.array(self.x2)
        y_.reshape(-1,1)

        xn=np.array([x_,y_])
        Xn=np.transpose(xn)
        dataLen = len(Xn)

        minNum=len(num)//5
        y_pred = DBSCAN(eps = 0.3, min_samples = minNum).fit_predict(Xn)

        y_pred = y_pred.tolist()

        X=[]
        Y = []
        num = 0
        for i in y_pred:
            if(i == 0):
                X.append(Xn[num][0])
                Y.append(Xn[num][1])
            num = num + 1

        x_sum = 0
        y_sum = 0
        for i in X:
            x_sum = x_sum + i
        for j in Y:
            y_sum = y_sum + j

        self.ans.append(x_sum/len(X))
        self.ans.append(y_sum/len(Y))
        return self.ans