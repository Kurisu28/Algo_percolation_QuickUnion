/* *****************************************************************************
 *  Name:    Kurisu
 *  NetID:   Naussica
 *  Precept: P28
 *
 *  Description:  Algorithm Learning Code
 *
 **************************************************************************** */

public class PercolationStats {
    // average value of x
    private double average;
    // ratio array to keep a record of all ratios
    private double ratio[];
    // stdres is the result of standard deviation
    private double stdres;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        ratio = new double[trials];
        stdres = 0;
        double sum = 0;
        double total = n * n;
        double opensites = 0;
        Percolation percolation = new Percolation(n);
        for (int i = 0; i < trials; i++) {
            while (!percolation.percolates()) {

                opensites++;
            }
            ratio[i] = opensites / total;
            sum += ratio[i];
        }
        average = sum / trials;

        for (int i = 0; i < trials; i++) {
            stdres += (ratio[i] - average) * (ratio[i] - average);
        }
        stdres = stdres / (trials - 1);
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
        int templo = average - (1.96 * stdres) / ();
        return templo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
