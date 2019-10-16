package com.itranswarp.learnjava;

/**
 * for练习
 */
public class Main {
    public static void main(String[] args) {
        double pi = 0;
        for (int i=0; i<=1000000000; i++) {
            if (i==0)
                pi += 1.;
            else
                pi += ((i%2)==1? -1:1) * (1./(float)i+2);
        }
        System.out.println(pi);
    }
}
