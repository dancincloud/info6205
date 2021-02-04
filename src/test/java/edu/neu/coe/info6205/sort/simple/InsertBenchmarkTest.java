package edu.neu.coe.info6205.sort.simple;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class InsertBenchmarkTest {
    @Test
    public void measureRandom() throws Exception {
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        int base = 100;

        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();

        String xStr = "", yStr = "";

        for(int m = 0; m < 8; m++){
            int n = (int) Math.pow(2, m) * base;
            Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
            helper.init(n);
            Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
            SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);

            Benchmark<Boolean> bm = new Benchmark_Timer<>(
                    "Random Array",
                    b -> {
                        sorter.sort(xs);
                    }
            );

            xStr += n + ", ";

            double y = bm.run(true, 10);
            yStr += y + ", " ;
        }

        System.out.println("x = [" + xStr + "]");
        System.out.println("y = [" + yStr + "]");
    }

    @Test
    public void measureOrdered() throws Exception {
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        int base = 100;

        String xStr = "", yStr = "";

        for(int m = 0; m < 8; m++){
            int n = (int) Math.pow(2, m) * base;
            Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
            helper.init(n);
            Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
            Arrays.sort(xs);
            SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);

            Benchmark<Boolean> bm = new Benchmark_Timer<>(
                    "Random Array",
                    b -> {
                        sorter.sort(xs);
                    }
            );

            xStr += n + ", ";

            double y = bm.run(true, 10);
            yStr += y + ", " ;
        }

        System.out.println("x = [" + xStr + "]");
        System.out.println("y = [" + yStr + "]");
    }

    @Test
    public void measurePartiallyOrdered() throws Exception {
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        int base = 100;

        String xStr = "", yStr = "";

        for(int m = 0; m < 8; m++){
            int n = (int) Math.pow(2, m) * base;
            Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
            helper.init(n);
            Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
            Arrays.sort(xs, n / 4, n / 4 * 3);
            SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);

            Benchmark<Boolean> bm = new Benchmark_Timer<>(
                    "Random Array",
                    b -> {
                        sorter.sort(xs);
                    }
            );

            xStr += n + ", ";

            double y = bm.run(true, 10);
            yStr += y + ", " ;
        }

        System.out.println("x = [" + xStr + "]");
        System.out.println("y = [" + yStr + "]");
    }

    @Test
    public void measureReverse() throws Exception {
        final Config config = ConfigTest.setupConfig("true", "0", "1", "", "");
        int base = 100;

        String xStr = "", yStr = "";

        for(int m = 0; m < 8; m++){
            int n = (int) Math.pow(2, m) * base;
            Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
            helper.init(n);
            Integer[] xs = helper.random(Integer.class, r -> r.nextInt(1000));
            Arrays.sort(xs, Collections.reverseOrder());
            SortWithHelper<Integer> sorter = new InsertionSort<Integer>(helper);

            Benchmark<Boolean> bm = new Benchmark_Timer<>(
                    "Random Array",
                    b -> {
                        sorter.sort(xs);
                    }
            );

            xStr += n + ", ";

            double y = bm.run(true, 10);
            yStr += y + ", " ;
        }

        System.out.println("x = [" + xStr + "]");
        System.out.println("y = [" + yStr + "]");
    }
}
