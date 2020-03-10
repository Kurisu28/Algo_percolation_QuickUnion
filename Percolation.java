/* *****************************************************************************
 *  Name:    Kurisu
 *  NetID:   Naussica
 *  Precept: P28
 *
 *  Description:  Algorithm Learning Code
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // grid is the 2d array;
    private int[][] grid;
    // num is the number of row element;
    private int num;
    // N is the number of open sites;
    private int N;
    // uf is the object of UF;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("input is not correct: " + Integer.toString(n));
        }
        else {
            // 0 means blocked, 1 means open
            this.num = n;
            this.grid = new int[n + 2][n + 2];
            this.uf = new WeightedQuickUnionUF(n * n + 2);
            for (int i = 1; i <= n; i++) {
                uf.union(0, i);
                uf.union(n * n + 1, n * (n - 1) + i);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row + 1 <= 0 || col + 1 <= 0) {
            throw new IllegalArgumentException("input is less than zero");
        }
        else {
            this.N++;
            this.grid[row + 1][col + 1] = 1;
            int ufposition = (row) * num + col + 1;
            if (grid[row + 2][col + 1] == 1) {
                uf.union((row + 1) * num + col + 1, ufposition);
            }
            if (grid[row][col + 1] == 1) {
                uf.union((row - 1) * num + col + 1, ufposition);
            }
            if (grid[row + 1][col + 2] == 1) {
                uf.union(row * num + col + 2, ufposition);
            }
            if (grid[row + 1][col] == 1) {
                uf.union(row * num + col, ufposition);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row + 1 <= 0 || col + 1 <= 0) {
            throw new IllegalArgumentException("input is not correct");
        }
        return this.grid[row + 1][col + 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        //return false;
        if (row + 1 <= 0 || col + 1 <= 0) {
            throw new IllegalArgumentException("input is not correct");
        }
        int ufposition = (row) * num + col + 1;
        return this.isOpen(row, col) && uf.connected(ufposition, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.N;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, num * num + 1);
    }
}
