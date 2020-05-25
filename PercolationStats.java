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
    // ratio is the result experiment
    private final double[] ratio;
    // 1.96 constant for cacl deviation
    private static final double CONSTANT_96 = 1.96;
    // the numbre of trails
    private final int xtrails;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "input is not correct: " + Integer.toString(n) + Integer.toString(trials));
        }
        ratio = new double[trials];
        double total = n * n;
        double[] tempsites = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    tempsites[i]++;
                }
            }
            ratio[i] = tempsites[i] / total;
        }
        xtrails = trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(ratio);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ratio);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double templo = this.mean() - (CONSTANT_96 * this.stddev()) / Math.sqrt(xtrails);
        return templo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double temphi = this.mean() + (CONSTANT_96 * this.stddev()) / Math.sqrt(xtrails);
        return temphi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        Stopwatch st = new Stopwatch();
        PercolationStats ps = new PercolationStats(number, trails);
        double time = st.elapsedTime();
        System.out.print("\ntime   is                  " + time);
        System.out.print("\nmean   is                  " + ps.mean());
        System.out.print("\nstddev is                  " + ps.stddev());
        System.out.print("\n95% confidence interval is " + "[" + ps.confidenceLo() + "," + ps
                .confidenceHi() + "]");
    }
}
