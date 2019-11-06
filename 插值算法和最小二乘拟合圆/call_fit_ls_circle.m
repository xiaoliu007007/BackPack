%call_fit_ls_circle_1.m
r = 4;
theta = 0:0.01:2*pi;
theta1 = 0:0.1:pi/2;
x1 = r * sin(theta1) + 99.8;
y1 = r * cos(theta1) + 99.8;
xr1 = rand(1, length(x1));
yr1 = rand(1, length(y1));
x1 = x1 + xr1;
y1 = y1 + yr1;
[radius1 xc1 yc1] = fit_ls_circle(x1', y1');
Xfit1 = radius1 * cos(theta) + xc1;
Yfit1 = radius1 * sin(theta) + yc1;

theta2 = 0:0.1:pi;
x2 = r * sin(theta2) + 99.8;
y2 = r * cos(theta2) + 99.8;
xr2 = rand(1, length(x2));
yr2 = rand(1, length(y2));
x2 = x2 + xr2;
y2 = y2 + yr2;
[radius2 xc2 yc2] = fit_ls_circle(x2', y2');
Xfit2 = radius2 * cos(theta) + xc2;
Yfit2 = radius2 * sin(theta) + yc2;

theta3 = 0:0.1:3*pi/2;
x3 = r * sin(theta3) + 99.8;
y3 = r * cos(theta3) + 99.8;
xr3 = rand(1, length(x3));
yr3 = rand(1, length(y3));
x3 = x3 + xr3;
y3 = y3 + yr3;
[radius3 xc3 yc3] = fit_ls_circle(x3', y3');
Xfit3 = radius3 * cos(theta) + xc3;
Yfit3 = radius3 * sin(theta) + yc3;

theta4 = 0:0.1:2*pi;
x4 = r * sin(theta4) + 99.8;
y4 = r * cos(theta4) + 99.8;
xr4 = rand(1, length(x4));
yr4 = rand(1, length(y4));
x4 = x4 + xr4;
y4 = y4 + yr4;
[radius4 xc4 yc4] = fit_ls_circle(x4', y4');
Xfit4 = radius4 * cos(theta) + xc4;
Yfit4 = radius4 * sin(theta) + yc4;

subplot(2,2,4);
plot(x1, y1, '*')
axis square
hold on
plot(Xfit1, Yfit1, 'r');
axis square
plot(xc1, yc1,'o')
title('£¨4£©');
xlabel('ºá×ø±ê(m)');
ylabel('×Ý×ø±ê(m)');
axis([94 106 94 106]);

subplot(2,2,3);
plot(x2, y2, '*')
axis square
hold on
plot(Xfit2, Yfit2, 'r');
axis square
plot(xc2, yc2,'o')
title('£¨3£©');
xlabel('ºá×ø±ê(m)');
ylabel('×Ý×ø±ê(m)');
axis([94 106 94 106]);

subplot(2,2,2);
plot(x3, y3, '*')
axis square
hold on
plot(Xfit3, Yfit3, 'r');
axis square
plot(xc3, yc3,'o')
title('£¨2£©');
xlabel('ºá×ø±ê(m)');
ylabel('×Ý×ø±ê(m)');
axis([94 106 94 106]);

subplot(2,2,1);
plot(x4, y4, '*')
axis square
hold on
plot(Xfit4, Yfit4, 'r');
axis square
plot(xc4, yc4,'o')
xlabel('ºá×ø±ê(m)');
ylabel('×Ý×ø±ê(m)');
title('£¨1£©');
axis([94 106 94 106]);

suptitle('Ô²ÄâºÏ')