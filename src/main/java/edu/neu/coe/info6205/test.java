package edu.neu.coe.info6205;

public class test {
    public static  void main(String[] args){
        int a = 21, b = 14;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.printf("%d, %d", a, b);
    }
}
