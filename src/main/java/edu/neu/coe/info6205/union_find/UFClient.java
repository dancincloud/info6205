package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UFClient {
    public static int count(int n){
        UF_HWQUPC uf = new UF_HWQUPC(n);

        int num = 0;
        Random random = new Random();
        while (uf.components() > 1){
            int i = random.nextInt(n);
            int j = random.nextInt(n);

            if(!uf.connected(i, j)){
                uf.union(i,j);
            }

            num++;
        }

        return num;
    }

    /* OUTPUT
        n          m          fn = 0.5 * n * ln(n)           offset = (fn - m) / m
        100        248        230                            -0.07332796132151487
        200        605        529                            -0.1264861294583884
        400        1243       1198                           -0.036357786357786394
        800        2982       2673                           -0.1037119002112464
        1600       5920       5902                           -0.003175246588298909
        3200       12992      12913                          -0.006134213827764878
        6400       31020      28044                          -0.0959497620920427
        12800      62513      60526                          -0.0317931326833404
        25600      131485     129924                         -0.011872076662737194
        51200      299726     277593                         -0.07384596495606321
        102400     614642     590676                         -0.03899195971118799
        204800     1325217    1252330                        -0.055000262975739934
        409600     2668912    2646617                        -0.00835392567513159
        819200     6026257    5577147                        -0.07452557659627308
        Relationship:
            m = f(n) = 0.5 * n * ln(n)
     */

   public static void main(String[] args){
       System.out.printf("%-10s %-10s %-30s %-20s\n", "n", "m", "fn = 0.5 * n * ln(n)",  "offset = (fn - m) / m");

        for(int i = 100; i < 1000000; i *= 2){
            int sum = 0;
            for(int j = 0; j < 10; j++){
                sum += count(i);
            }

            double m = sum / 10.0;
            int fn =  (int) (0.5 * i * Math.log(i));

            System.out.printf("%-10s %-10s %-30s %-20s\n", i, (int) m, fn,  (fn - m) / m);
        }
   }
}
