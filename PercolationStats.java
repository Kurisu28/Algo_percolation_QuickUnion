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
    private double average;
    // ratio array to keep a record of all ratios
    private double ratio[];
    // stdres is the result of standard deviation
    private double stdres;
    // the numbre of opensites
    private int opensites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "input is not correct: " + Integer.toString(n) + Integer.toString(trials));
        }
        ratio = new double[trials];
        stdres = 0;
        double total = n * n;
        opensites = 0;
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < trials; i++) {
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(n);
                int y = StdRandom.uniform(n);
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    opensites++;
                }
            }
            ratio[i] = opensites / total;
        }
        average = StdStats.mean(ratio);
        stdres = StdStats.stddev(ratio);
    }

    // sample mean of percolation threshold
    public double mean() {
        return average;
    }

    // sample standard deviation of percolation threshold
    public double stddev(int trials) {
        return stdres;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double templo = average - (1.96 * stdres) / Math.sqrt(opensites);
        return templo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double temphi = average + (1.96 * stdres) / Math.sqrt(opensites);
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
        System.out.print("\nstddev is                   " + ps.stddev(trails));
        System.out.print("\n95% confidence interval is " + "[" + ps.confidenceLo() + "," + ps
                .confidenceHi() + "]");
    }
}
