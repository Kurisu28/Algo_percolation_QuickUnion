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
    // grid is the 2d array; num is the number of opensites; uf is the object of UF
    int[][] grid;
    int num;
    WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // 0 means blocked, 1 means open
        this.grid = new int[n + 1][n + 1];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.num = 0;
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(n * n + 1, n * i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.num++;
        this.grid[row][col] = 1;
        int ufposition = (row - 1) * n + col;
        if (grid[row + 1][col] == 1) {
            uf.union(row * n + col, ufposition);
        }
        if (grid[row - 1][col] == 1) {
            uf.union((row - 2) * n + col, ufposition);
        }
        if (grid[row][col + 1] == 1) {
            uf.union(row * n + col + 1, ufposition);
        }
        if (grid[row][col - 1] == 1) {
            uf.union(row * n + col - 1, ufposition);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int ufposition = (row - 1) * n + col;
        return connected(ufposition, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.num;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(0, n * n + 1);
    }
}
