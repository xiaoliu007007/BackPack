%fit_ls_circle.m
function[radius,xc,yc] = fit_ls_circle(x, y)
%使用最小二乘法求解元圆的拟合参数中的a,b,c
%使用左除
abc = [x y ones(length(x), 1)]\ (-(x.^2 + y.^2));
a = abc(1);b = abc(2);c = abc(3);
xc = -a / 2;
yc = -b / 2;
radius = sqrt((xc^2 + yc^2) - c);
