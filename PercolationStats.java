/* *****************************************************************************
 *  Name:    Kurisu
 *  NetID:   Naussica
 *  Precept: P28
 *
 *  Description:  Algorithm Learning Code
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // average value of x
    private final double average;
    // stdres is the result of standard deviation
    private final double stdres;
    // the numbre of opensites
    private final int opensites;
    // 1.96 constant for cacl deviation
    private static final double CONSTANT_96 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "input is not correct: " + Integer.toString(n) + Integer.toString(trials));
        }
        double[] ratio = new double[trials];
        double total = n * n;
        int tempsites = 0;
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < trials; i++) {
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    tempsites++;
                }
            }
            ratio[i] = tempsites / total;
        }
        average = StdStats.mean(ratio);
        stdres = StdStats.stddev(ratio);
        opensites = tempsites;
    }

    // sample mean of percolation threshold
    public double mean() {
        return average;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdres;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double templo = average - (CONSTANT_96 * stdres) / Math.sqrt(opensites);
        return templo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double temphi = average + (CONSTANT_96 * stdres) / Math.sqrt(opensites);
        return temphi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        Stopwatch st = new Stopwatch();
        PercolationStats ps = new PercolationStats(number, trails);
        double time = st.elapsedTime();
        System.out.print("\ntime   is                   " + time);
        System.out.print("\nmean   is                   " + ps.mean());
        System.out.print("\nstddev is                   " + ps.stddev());
        System.out.print("\n95% confidence interval is " + "[" + ps.confidenceLo() + "," + ps
                .confidenceHi() + "]");
    }
}
