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
    private boolean[][] grid;
    // num is the number of row element;
    private int num;
    // N is the number of open sites;
    private int nopen;
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
            this.grid = new boolean[n + 2][n + 2];
            this.uf = new WeightedQuickUnionUF(n * n + 2);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= this.num || col >= this.num) {
            throw new IllegalArgumentException("input is less than zero");
        }
        else {
            this.nopen++;
            this.grid[row + 1][col + 1] = true;
            int ufposition = (row) * num + col + 1;
            if (grid[row + 2][col + 1]) {
                uf.union((row + 1) * num + col + 1, ufposition);
            }
            if (grid[row][col + 1]) {
                uf.union((row - 1) * num + col + 1, ufposition);
            }
            if (grid[row + 1][col + 2]) {
                uf.union(row * num + col + 2, ufposition);
            }
            if (grid[row + 1][col]) {
                uf.union(row * num + col, ufposition);
            }
            if (row == 0) {
                uf.union(0, ufposition);
            }
            if (row == this.num - 1) {
                uf.union(this.num * this.num + 1, ufposition);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= this.num || col >= this.num) {
            throw new IllegalArgumentException("input is not correct");
        }
        return this.grid[row + 1][col + 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= this.num || col >= this.num) {
            throw new IllegalArgumentException("input is not correct");
        }
        int ufposition = (row) * num + col + 1;
        boolean res = (uf.find(0) == uf.find(ufposition));
        return this.isOpen(row, col) && res;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.nopen;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean res = (uf.find(0) == uf.find(num * num + 1));
        return res;
    }
}
