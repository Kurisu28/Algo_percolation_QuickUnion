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
            this.nopen = 0;
        }
    }

    // helper function to union the current opensite and four sites around it
    private void helper(int rowoffset, int coloffset, int ufposition) {
        if (grid[rowoffset][coloffset]) {
            this.uf.union((rowoffset - 1) * num + coloffset, ufposition);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.num || col > this.num) {
            throw new IllegalArgumentException("input is less than zero");
        }
        else if (!this.isOpen(row, col)) {
            this.nopen++;
            this.grid[row][col] = true;
            int ufposition = (row - 1) * num + col;
            helper(row + 1, col, ufposition);
            helper(row - 1, col, ufposition);
            helper(row, col + 1, ufposition);
            helper(row, col - 1, ufposition);
            if (row == 1) {
                this.uf.union(0, ufposition);
            }
            if (row == this.num) {
                this.uf.union(this.num * this.num + 1, ufposition);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.num || col > this.num) {
            throw new IllegalArgumentException("input is not correct");
        }
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.num || col > this.num) {
            throw new IllegalArgumentException("input is not correct");
        }
        int ufposition = (row - 1) * num + col;
        return this.isOpen(row, col) && (this.uf.find(0) == this.uf.find(ufposition));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.nopen;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean res = (this.uf.find(0) == this.uf.find(num * num + 1));
        return res;
    }
}
