// import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// import edu.princeton.cs.algs4.StdOut;
import java.lang.IllegalArgumentException;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF idArray;
    private int [][] grid;
    private int [][] ids;
    private int size;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N is less than or equal to 0");
        } else {
            size = n;
            grid = new int[n+1][n+1];
            ids = new int[n+1][n+1];
            idArray = new WeightedQuickUnionUF((n*n)+2);
            int id = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    id += 1;
                    grid[i][j] = 'X';
                    ids[i][j] = id;
                    if (id <= size) {
                        idArray.union(0, id);
                        idArray.union(size*size + 1, size*size + 1 - id);
                    }
                }
            }
            // Ex: n = 5
            // 0 - 26
            // StdOut.println("Arrays successfully initialized");
            // for (int i = 1; i <= size; i++) {
            //     id_array.union(0, i);
            //     id_array.union(size*size + 1, size*size - i);
            // }
            // int set_count = id_array.count();
            // StdOut.println("Set count: " + set_count);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("Row is out of prescribed range");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("Col is out of prescribed range");
        }
        if (grid[row][col] == 'X') {
            // System.out.println("row " + row);
            // System.out.println("col " + col);
            grid[row][col] = 'O';
            if (row != 1 && grid[row - 1][col] == 'O') {
                // System.out.println("Connected to bottom");
                int topSet = idArray.find(ids[row-1][col]);
                int bottomSet = idArray.find(ids[row][col]);
                idArray.union(topSet, bottomSet);
            }
            if (row != size && grid[row + 1][col] == 'O') {
                // System.out.println("Connected to top");
                int topSet = idArray.find(ids[row][col]);
                int bottomSet = idArray.find(ids[row+1][col]);
                idArray.union(topSet, bottomSet);
            }
            if (col != 1 && grid[row][col - 1] == 'O') {
                // System.out.println("Connected to left");
                int topSet = idArray.find(ids[row][col-1]);
                int bottomSet = idArray.find(ids[row][col]);
                idArray.union(topSet, bottomSet);
            }
            if (col != size && grid[row][col + 1] == 'O') {
                // System.out.println("Connected to right");
                int topSet = idArray.find(ids[row][col]);
                int bottomSet = idArray.find(ids[row][col+1]);
                idArray.union(topSet, bottomSet);
            }
            openSites += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("Row is out of prescribed range");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("Col is out of prescribed range");
        }
        return grid[row][col] == 'O';
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException("Row is out of prescribed range");
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException("Col is out of prescribed range");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        return idArray.find(0) == idArray.find(ids[row][col]);
        // return true;
    } 

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // StdOut.println("Top set: " + top_set);
        // StdOut.println("Bottom set: " + bottom_set);
        return idArray.find(0) == idArray.find(size*size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation perc = new Percolation(5);
        // for (int i = 1; i <= 5; i++) {
        //     perc.open(1, i);
        // }
        // boolean perc_success = perc.percolates();
        // if (perc_success) {
        //     StdOut.println("There is percolation");
        // } else {
        //     StdOut.println("There is not percolation");
        // }
    }
}
