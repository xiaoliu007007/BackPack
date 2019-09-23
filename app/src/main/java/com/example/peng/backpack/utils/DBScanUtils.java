package com.example.peng.backpack.utils;

import java.io.*;
import java.util.*;
/**
 * @description: DBSCAN的Java实现
 * @author: lyj
 * @create: 2019/09/23
 **/
public class DBScanUtils {
    private static int minpts = 44;
    private static double radius = 0.2;
    private static List<double[]> cores;
    private static int interval = 250;

    /**
     * @description: 计算距离
     * @author: lyj
     * @create: 2019/09/23
     **/
    private static double countEurDistance(double[] point1, double[] point2) {
        double eurDistance = 0.0;
        for (int i = 0; i < point1.length; i++) {
            eurDistance += (point1[i] - point2[i]) * (point1[i] - point2[i]);
        }
        return Math.sqrt(eurDistance);
    }

    /**
     * @description: 寻找核心点
     * @author: lyj
     * @create: 2019/09/23
     **/
    private static List<double[]> findCores(ArrayList<double[]> points, int minpts, double radius) {
        List<double[]> cores = new ArrayList<double[]>();
        for (int i = 0; i < points.size(); i++) {
            int pts = 0;
            for (int j = 0; j < points.size(); j++) {
                for (int k = 0; k < points.get(i).length; k++) {
                    if (countEurDistance(points.get(i), points.get(j)) < radius) {
                        pts++;
                    }
                }
            }
            if (pts >= minpts) {
                cores.add(points.get(i));
            }
        }
        return cores;
    }

    /**
     * @description: 确定圆心坐标
     * @author: lyj
     * @create: 2019/09/23
     **/
    private static double[] getCircle(double[] p1, double[] p2, double[] p3) {
        double[] circle = { 0.0, 0.0 };
        double x21 = p2[0] - p1[0];
        double y21 = p2[1] - p1[1];
        double x32 = p3[0] - p2[0];
        double y32 = p3[1] - p2[1];
        if (x21 * y32 - x32 * y21 == 0 || x21 == 0) {
            return circle;
        }
        double xy21 = p2[0] * p2[0] - p1[0] * p1[0] + p2[1] * p2[1] - p1[1] * p1[1];
        double xy32 = p3[0] * p3[0] - p2[0] * p2[0] + p3[1] * p3[1] - p2[1] * p2[1];
        double y0 = (x32 * xy21 - x21 * xy32) / (2 * (y21 * x32 - y32 * x21));
        double x0 = (xy21 - 2 * y0 * y21) / (2.0 * x21);
        circle[0] = x0;
        circle[1] = y0;
        return circle;
    }

    /**
     * @description: 等辐射值三点划分
     * @author: lyj
     * @create: 2019/09/23
     **/
    private static double[][] MySort(ArrayList<ArrayList<Double>> list) {
        double[][] data = new double[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).get(0);
            data[i][1] = list.get(i).get(1);
            data[i][2] = list.get(i).get(2);
        }
        Arrays.sort(data, new MyComparator());
        return data;
    }

    private static class MyComparator implements Comparator<double[]> {
        @Override
        public int compare(double[] n1, double[] n2) {
            // TODO Auto-generated method stub
            return n1[2] - n2[2] > 0 ? 1 : -1;
        }

    }

    /**
     * @description: DBSCAN计算过程
     * @author: lyj
     * @create: 2019/09/23
     **/
    private static double[] DBSCAN(ArrayList<ArrayList<Double>> list) {
        double[][] data = MySort(list);
        ArrayList<double[]> circle = new ArrayList<double[]>();
        for (int i = 0; i < data.length - 2; i++) {
            for (int j = i + 1; j < data.length - 1; j++) {
                if (data[j][2] - data[i][2] > interval) {
                    break;
                }
                for (int n = j + 1; n < data.length; n++) {
                    if (data[n][2] - data[i][2] > interval) {
                        break;
                    }
                    double[] res = getCircle(data[i], data[j], data[n]);
                    if (res[0] != 0.0 || res[1] != 0.0) {
                        circle.add(res);
                    }
                }
            }
        }
        cores = findCores(circle, minpts, radius);
        double[] ans = { 0.0, 0.0 };
        for (double[] core : cores) {
            ans[0] += core[0];
            ans[1] += core[1];
        }
        ans[0] /= cores.size();
        ans[1] /= cores.size();
        return ans;
    }

    public static double[] getTestMsg() {
        double[] ans={0,0};
        try {
            ans = DBSCAN(read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * @description: 读取MCNP5部分
     * @author: lyj
     * @create: 2019/09/23
     **/
    private static ArrayList<ArrayList<Double>> read() throws IOException {
        String base="/assets/test_";
        ArrayList<ArrayList<Double>> data=new ArrayList<ArrayList<Double>>();
        for(int i=1;i<12;i++) {
            String file=base+i;
            InputStream inputStream = DBScanUtils.class.getResourceAsStream(file);
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            for(int j=0;j<20;j++) {
                ArrayList<Double> list=new ArrayList<Double>();
                data.add(list);
            }
            String s;
            int pos=1;
            int latitude_pos=(i-1)*20;
            int value_pos=(i-1)*20;
            while ((s = br.readLine()) != null) {
                String[] str=s.trim().split("\\s+");
                if(pos>=100&&pos<=119){
                    Double longitude=Double.parseDouble(str[2]);
                    Double latitude=Double.parseDouble(str[3]);
                    ArrayList<Double> list=data.get(latitude_pos++);
                    list.add(longitude);
                    list.add(latitude);
                }
                pos++;
                if(str.length==16&&str[0].equals("10000000")){
                    ArrayList<Double> list1=data.get(value_pos++);
                    list1.add((Double.valueOf(str[1])*Math.pow(10, 10)));
                    ArrayList<Double> list2=data.get(value_pos++);
                    list2.add((Double.valueOf(str[6])*Math.pow(10, 10)));
                    ArrayList<Double> list3=data.get(value_pos++);
                    list3.add((Double.valueOf(str[11])*Math.pow(10, 10)));
                }
                if(str.length==11&&str[0].equals("10000000")){
                    ArrayList<Double> list1=data.get(value_pos++);
                    list1.add((Double.valueOf(str[1])*Math.pow(10, 10)));
                    ArrayList<Double> list2=data.get(value_pos++);
                    list2.add((Double.valueOf(str[6])*Math.pow(10, 10)));
                }
            }
        }
        return data;
    }
}

