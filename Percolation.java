import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int gridSize;
    private int gridSquared;
    private WeightedQuickUnionUF ufGrid;
    private WeightedQuickUnionUF ufGrid2;
    private int virtualTop;
    private int virtualBottom;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n<=0) throw new IllegalArgumentException("N must be > 0");
        gridSize = n;
        gridSquared = n * n;
        grid = new boolean[gridSize][gridSize];
        ufGrid = new WeightedQuickUnionUF(gridSquared + 2);     //includes virtual top and bottom
        ufGrid2 = new WeightedQuickUnionUF(gridSquared + 1);     //includes virtual top
        virtualTop = gridSquared;
        virtualBottom = gridSquared + 1;
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        validateIndex(row, col);

        if(!isOpen(row, col)){
            grid[row-1][col-1] = true;
            openSites++;

            if (row == 1){
                ufGrid.union(getIndex(row,col), virtualTop);
                ufGrid2.union(getIndex(row,col), virtualTop);
            }
            if (row == gridSize){
                ufGrid.union(getIndex(row,col), virtualBottom);
            }

            //check above
            if (row != 1 && isOpen(row - 1, col)) {
                ufGrid.union(getIndex(row - 1, col), getIndex(row, col));
                ufGrid2.union(getIndex(row - 1, col), getIndex(row, col));
            }
            //check left
            if (col != 1 && isOpen(row, col - 1)) {
                ufGrid.union(getIndex(row, col - 1), getIndex(row, col));
                ufGrid2.union(getIndex(row, col - 1), getIndex(row, col));
            }
            //check below
            if (row != gridSize && isOpen(row + 1, col)) {
                ufGrid.union(getIndex(row + 1, col), getIndex(row, col));
                ufGrid2.union(getIndex(row + 1, col), getIndex(row, col));
            }
            //check right
            if (col != gridSize && isOpen(row, col + 1)) {
                ufGrid.union(getIndex(row, col + 1), getIndex(row, col));
                ufGrid2.union(getIndex(row, col + 1), getIndex(row, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validateIndex(row, col);
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validateIndex(row, col);
        return ufGrid2.connected(virtualTop, getIndex(row,col));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return ufGrid.connected(virtualTop, virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args){
        //System.out.println("Hello");
    }

    private int getIndex(int r, int c){
        return (gridSize * (r-1) + c)-1;
    }

    private void validateIndex(int r, int c){
        if (r <= 0 && r > gridSize && c <= 0 && c > gridSize){
            throw new IndexOutOfBoundsException("Index is Out of Bounds");
        }
    }
}
