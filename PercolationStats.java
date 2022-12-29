import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double[] trialResults;
    private int numTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n<=0 || trials <= 0) throw new IllegalArgumentException();
        trialResults = new double[trials];
        numTrials = trials;

        for(int i = 0; i < trials; i++){
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()){
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                percolation.open(row, col);
            }
            int openSites = percolation.numberOfOpenSites();
            double results = (double) openSites / (n*n);
            trialResults[i] = results;
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){

        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return StdStats.mean(trialResults) - ((1.96 * StdStats.stddev(trialResults)) / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return StdStats.mean(trialResults) + ((1.96 * StdStats.stddev(trialResults)) / Math.sqrt(numTrials));
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats p = new PercolationStats(200, 100);
        System.out.println("mean \t\t\t\t    = " + p.mean());
        System.out.println("stddev \t\t\t\t    = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }

}