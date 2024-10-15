package com.tushuoit;

import java.util.ArrayList;

public class Fence {

    public static String decode(String s) {
        String s1 = s.replaceAll(" ", "");
        System.out.println("密文：" + s1);//测试
        int len = s1.length();
        System.out.println("密文共有" + len + "位");//测试
        int[] N = new int[len + 1];
        ArrayList<Integer> lenList = new ArrayList<>();
        for (int i = 2; i <= len - 1; i++) {
            if (len % i == 0) {
                lenList.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Integer j : lenList) {
            sb.append(String.format("%d栏\n", j));
            int c = len / j; //c=7
            int n = 0;
            for (int k = 1; k <= c; k++) {
                n++;
                int sum = n;
                for (int p = 1; p <= j; p++) {
                    sb.append(s1.charAt(sum - 1));
                    sum = sum + c;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
