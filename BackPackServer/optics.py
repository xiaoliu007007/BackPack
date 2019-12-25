from sklearn.cluster import OPTICS
import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets

class optics:
    x1=[]
    x2=[]
    #误差设计
    interval=370;
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

    def optics(self,num):
        num.sort(key=lambda k:k[2],reverse=False)
        #print(num)
        res=[]
        for i in range(len(num)):
            for j in range(i+1,len(num)):
                if num[j][2]-num[i][2]>self.interval:
                    break
                for n in range(j+1,len(num)):
                    if num[n][2]-num[i][2]>self.interval:
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
        minNum=len(num)//6
        clust = OPTICS(min_samples=minNum)
        y_pred=clust.fit_predict(Xn)

        y_pred = y_pred.tolist()
        X=[]
        Y = []
        count = 0
        for i in y_pred:
            if(i == 0):
                X.append(Xn[count][0])
                Y.append(Xn[count][1])
            count = count + 1

        x_sum = 0
        y_sum = 0
        for i in X:
            x_sum = x_sum + i
        for j in Y:
            y_sum = y_sum + j
        res.append(x_sum/len(X))
        res.append(y_sum/len(Y))
        return res