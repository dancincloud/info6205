package edu.neu.coe.info6205.union_find_alternatives;

import edu.neu.coe.info6205.threesum.Source;
import edu.neu.coe.info6205.union_find.UF_HWQUPC;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class UF_Benchmark {
/*    OUTPUT
    n          UF_DEPTH   UF_LOOPS   UF_HWQUPC
    1000       0.23       0.34       0.2
    2000       0.35       0.48       0.28
    4000       0.56       1.36       0.46
    8000       1.08       2.61       1.03
    16000      2.53       4.68       2.31
    32000      5.36       9.67       5.2
    64000      11.86      21.9       11.62
    128000     28.58      47.39      28.6
    256000     64.12      102.71     64.07

    Conclusion:
    1. For weighted quick union, no matter storing the depth or the size, these two algorithms have similar running time.
       Because both two algorithms connect smaller/shallower tree to the bigger/deeper one, making the tree flatten.
    2. For path compression, if do two loops, there'll be a poor performance than doing the normal path compression.
 */

    @Test
    public static void main(String[] args){
        Source source = new Source(20, 10, 0L);
        Supplier<int[]> supplier = source.intsSupplier(10);
        int[] ints = supplier.get();

        Iterator<Double> iterator_depth = UF_DEPTH_Benchmark().iterator();
        Iterator<Double> iterator_loops = UF_LOOPS_Benchmark().iterator();
        Iterator<Double> iterator_hwqupc = UF_HWQUPC_Benchmark().iterator();

        System.out.printf("%-10s %-10s %-10s %-10s\n", "n", "UF_DEPTH", "UF_LOOPS",  "UF_HWQUPC");
        for(int p = 0; p < 9; p++) {
            int size = 1000 * (int) Math.pow(2, p);
            System.out.printf("%-10s %-10s %-10s %-10s\n", size, iterator_depth.next(), iterator_loops.next(), iterator_hwqupc.next());
        }
    }

    @Test
    public static List<Double> UF_DEPTH_Benchmark(){
        List<Double> list = new LinkedList<>();

        for(int p = 0; p < 9; p++) {
            int size = 1000 * (int) Math.pow(2, p);
            Benchmark<UF_DEPTH> bm = new Benchmark_Timer<>("UF_DEPTH_Benchmark", null, uf -> {
                int n = uf.size();
                Random random = new Random();
                while (uf.components() > 1) {
                    int i = random.nextInt(n);
                    int j = random.nextInt(n);

                    if (!uf.connected(i, j)) {
                        uf.union(i, j);
                    }
                }
            }, null);

            double x = bm.runFromSupplier(() -> new UF_DEPTH(size), 100);
            list.add(x);
        }

        return list;
    }

    @Test
    public static List<Double> UF_LOOPS_Benchmark(){
        List<Double> list = new LinkedList<>();

        for(int p = 0; p < 9; p++) {
            int size = 1000 * (int) Math.pow(2, p);
            Benchmark<UF_LOOPS> bm = new Benchmark_Timer<>("UF_LOOP_Benchmark", null, uf -> {
                int n = uf.size();
                Random random = new Random();
                while (uf.components() > 1) {
                    int i = random.nextInt(n);
                    int j = random.nextInt(n);

                    if (!uf.connected(i, j)) {
                        uf.union(i, j);
                    }
                }
            }, null);

            double x = bm.runFromSupplier(() -> new UF_LOOPS(size), 100);
            list.add(x);
        }

        return list;
    }

    @Test
    public static List<Double> UF_HWQUPC_Benchmark(){
        List<Double> list = new LinkedList<>();

        for(int p = 0; p < 9; p++) {
            int size = 1000 * (int) Math.pow(2, p);
            Benchmark<UF_HWQUPC> bm = new Benchmark_Timer<>("UF_LOOP_Benchmark", null, uf -> {
                int n = uf.size();
                Random random = new Random();
                while (uf.components() > 1) {
                    int i = random.nextInt(n);
                    int j = random.nextInt(n);

                    if (!uf.connected(i, j)) {
                        uf.union(i, j);
                    }
                }
            }, null);

            double x = bm.runFromSupplier(() -> new UF_HWQUPC(size), 100);
            list.add(x);
        }

        return list;
    }
}
