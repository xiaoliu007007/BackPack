#include<iostream>
#include<cmath>

using namespace std;
int main()
{
   int x1,y1,x3,y3;
   double a,b,c,d,e,f;
   double r,k1,k2,x,y,x2,y2;
   cout<<"请输入x1,y1,x2,y2,x3,y3"<<endl;
   cin>>x1>>y1>>x2>>y2>>x3>>y3;
k1=1,k2=2;
   if((y1==y2)&&(y2==y3))
{
   cout<<"三点不构成圆!"<<endl;
   return 0;
}
   if((y1!=y2)&&(y2!=y3))
{
   k1=(x2-x1)/(y2-y1);
   k2=(x3-x2)/(y3-y2);
}
   if(k1==k2)
{
   cout<<"三点不构成圆!"<<endl;
   return 0;
}
   a=2*(x2-x1);
   b=2*(y2-y1);
   c=x2*x2+y2*y2-x1*x1-y1*y1;
   d=2*(x3-x2);
   e=2*(y3-y2);
   f=x3*x3+y3*y3-x2*x2-y2*y2;
   x=(b*f-e*c)/(b*d-e*a);
   y=(d*c-a*f)/(b*d-e*a);
   cout<<"圆心为("<<x<<","<<y<<")"<<endl;
   r=sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
   cout<<"半径为"<<r<<endl;
   return 0;
}
