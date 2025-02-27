public class GameOfLife{
    public static void main(String[] args){
        if (args.length != 2){
            System.err.println("Error: There must be 2 arguments");
            return;
        }

        int numCells = Integer.parseInt(args[0]);
        int numCopies = Integer.parseInt(args[1]);

        if (numCells <= 0 || numCopies <= 0){
            System.err.println("Error: The arguments must be positive");
            return;
        }

        int[][] grid = new int[numCells][numCells];

        for (int i = 0; i < numCopies; i++) {
            glider(grid, randomRow(numCells), randomCol(numCells), numCells);
            tenCellRow(grid, randomRow(numCells), randomCol(numCells), numCells);
            blinker(grid, randomRow(numCells), randomCol(numCells), numCells);
        }

        StdDraw.enableDoubleBuffering();

        while (true){
            drawGrid(grid, numCells);
            grid = nextStage(grid);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }
    static void glider(int[][] grid, int r, int c, int numCells){
        grid[(r + 0) % numCells][(c + 1) % numCells] = 1;
        grid[(r + 1) % numCells][(c + 2) % numCells] = 1;
        grid[(r + 2) % numCells][(c + 0) % numCells] = 1;
        grid[(r + 2) % numCells][(c + 1) % numCells] = 1;
        grid[(r + 2) % numCells][(c + 2) % numCells] = 1;
    }

    static void tenCellRow(int[][] grid, int r, int c, int numCells){
        for (int i = 0; i < 10; i++){
            grid[r % numCells][(c + i) % numCells] = 1;
        }
    }

    static void blinker(int[][] grid, int r, int c, int numCells){
        grid[(r + 0) % numCells][(c+1) % numCells] = 1;
        grid[(r + 1) % numCells][(c+1) % numCells] = 1;
        grid[(r + 2) % numCells][(c+1) % numCells] = 1;
    }

    static void drawGrid(int[][] grid, int numCells) {
        StdDraw.clear();
        double cellSize = 1.0 / numCells;

        for (int row = 0; row < numCells; row++) {
            for (int col = 0; col < numCells; col++) {
                if (grid[row][col] == 1) {
                    StdDraw.setPenColor(StdDraw.BLACK);
                } else {
                    StdDraw.setPenColor(StdDraw.WHITE);
                }
                StdDraw.filledSquare(col * cellSize + cellSize / 2, row * cellSize + cellSize / 2, cellSize / 2);
            }
        }
    }

    static int[][] nextStage(int[][] grid){
        int numCells = grid.length;
        int[][] nextGrid = new int[numCells][numCells];

        for (int r = 0; r < numCells; r++){
            for (int c = 0; c < numCells; c++){
                int aliveNeighbors = 0;

                for (int i = -1; i <= 1; i++){
                    for (int j = -1; j <= 1; j++){
                        if (i == 0 && j == 0){
                            continue;
                        }

                        int rowNeighbor = ((r + i + numCells) % numCells);
                        int colNeighbor = ((c + j + numCells) % numCells);


                        aliveNeighbors += grid[rowNeighbor][colNeighbor];

                    }
                }

                if (grid[r][c] == 1){
                    if (aliveNeighbors < 2 || aliveNeighbors > 3){
                        nextGrid[r][c] = 0;
                    } else {
                        nextGrid[r][c] = 1;
                    }
                } else {
                    if (aliveNeighbors == 3){
                        nextGrid[r][c] = 1;
                    }
                }
            }
        }
        return nextGrid;
    }

        static int randomRow(int numCells){
            return (int)(Math.random() * numCells);
        }
        static int randomCol(int numCells) {
            return (int)(Math.random() * numCells);
        }
}