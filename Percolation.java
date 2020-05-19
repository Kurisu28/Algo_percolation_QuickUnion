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
    private int nopen;
    // uf is the object of UF;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("input is not correct: " + Integer.toString(n));
        }
        else {
            // 0 means blocked, 1 means open, 2 means full
            this.num = n;
            this.grid = new int[n + 2][n + 2];
            this.uf = new WeightedQuickUnionUF(n * n + 2);
            this.nopen = 0;
        }
    }

    // helper function to union the current opensite and four sites around it
    private boolean helper(int rowoffset, int coloffset, int ufposition) {
        if (grid[rowoffset][coloffset] > 0) {
            this.uf.union((rowoffset - 1) * num + coloffset, ufposition);
        }
        if (grid[rowoffset][coloffset] == 2) {
            return true;
        }
        return false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.num || col > this.num) {
            throw new IllegalArgumentException("input is less than zero");
        }
        int ufposition = (row - 1) * num + col;
        if (!this.isOpen(row, col)) {
            this.nopen++;
            if (row == 1) {
                this.uf.union(0, ufposition);
                this.grid[row][col] = 2;
            }
            else {
                this.grid[row][col] = 1;
            }
            if (helper(row + 1, col, ufposition)) {
                this.grid[row][col] = 2;
            }
            if (helper(row - 1, col, ufposition)) {
                this.grid[row][col] = 2;
            }
            if (helper(row, col + 1, ufposition)) {
                this.grid[row][col] = 2;
            }
            if (helper(row, col - 1, ufposition)) {
                this.grid[row][col] = 2;
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
        return this.grid[row][col] > 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.num || col > this.num) {
            throw new IllegalArgumentException("input is not correct");
        }
        if (grid[row][col] == 2) {
            return true;
        }
        if (grid[row][col] == 1 && (grid[row - 1][col] == 2 ||
                grid[row + 1][col] == 2 ||
                grid[row][col + 1] == 2 ||
                grid[row][col - 1] == 2)) {
            grid[row][col] = 2;
            return true;
        }
        return false;
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
