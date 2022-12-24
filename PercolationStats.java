/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Last modified:     October 16, 1842
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import java.lang.Math;


public class PercolationStats {
    private double[] thresholds;
    private int trials;
    public PercolationStats(int N, int T) {
        // perform T independent experiments on an N-by-N grid
        // Stopwatch time = new Stopwatch();
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                // StdOut.println("Checking for percolation");
                int randomRow = StdRandom.uniform(1, N + 1);
                int randomCol = StdRandom.uniform(1, N + 1);
                perc.open(randomRow, randomCol);
            }
            int x = perc.numberOfOpenSites();
            double threshold = (double) x / (N*N);
            thresholds[i] = threshold;
        }
        trials = T;
        // double experiment_time = time.elapsedTime();
        // StdOut.println("Experiment time: " + experiment_time);
    }
    public double mean() {
        // sample mean of percolation threshold
        double mean = StdStats.mean(thresholds);
        return mean;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        double stddev = StdStats.stddev(thresholds);
        return stddev;
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        double mean = mean();
        double stddev = stddev();
        return mean - ((1.96 * stddev) / Math.sqrt(trials));
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        double mean = mean();
        double stddev = stddev();
        return mean + ((1.96 * stddev) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        // Not required for the API, but useful to test if
        // your code is doing reasonable things
        PercolationStats st = new PercolationStats(20,10);
        StdOut.println("Mean: " + st.mean());
        StdOut.println("Std Dev: " + st.stddev());
        StdOut.println("Confidence interval: " + "[" + st.confidenceHi() + ", " + st.confidenceLo() + "]");
    }
}