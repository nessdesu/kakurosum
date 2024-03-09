import java.util.*;
public class KakuroSolver {
    private static int[][] board;
    private static int[][] clues;
    private static int nRows, nCols;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        nRows = scanner.nextInt();
        nCols = scanner.nextInt();
        scanner.nextLine();

        board = new int[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < nCols; j++) {
                if (line.charAt(j) == 'x') {
                    board[i][j] = -1;
                } else if (line.charAt(j) == '0') {
                    board[i][j] = 0;
                } else {
                    board[i][j] = line.charAt(j) - '0';
                }
            }
        }

        clues = new int[nRows + nCols][2];
        for (int i = 0; i < nRows + nCols; i++) {
            String line = scanner.nextLine();
            clues[i][0] = Integer.parseInt(line.split(" ")[0]);
            clues[i][1] = Integer.parseInt(line.split(" ")[1]);
        }

        solveKakuro();
        paint();

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValid(int row, int col, int num) {
        for (int i = 0; i < nRows; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        for (int j = 0; j < nCols; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void solveKakuro() {
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(row, col, num)) {
                            board[row][col] = num;
                            if (solve(row, col)) {
                                return;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return;
                }
            }
        }
    }

    private static boolean solve(int row, int col) {
        if (row == nRows - 1 && col == nCols) {
            return checkClues();
        }

        if (col == nCols) {
            row++;
            col = 0;
        }

        if (board[row][col] != 0) {
            return solve(row, col + 1);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (solve(row, col + 1)) {
                    return true;
                }
            }
        }

        board[row][col] = 0;
        return false;
    }

    private static boolean checkClues() {
        for (int i = 0; i < nRows + nCols; i++) {
            int sum = 0, count = 0;
            int row = clues[i][1] - 1, col = clues[i][0] - 1;
            if (clues[i][0] == 0) {
                for (int j = row; j < nRows && board[j][col] != -1; j++) {
                    sum += board[j][col];
                    count++;
                }
            } else {
                for (int j = col; j < nCols && board[row][j] != -1; j++) {
                    sum += board[row][j];
                    count++;
                }
            }
            if (sum != clues[i][1] && sum != clues[i][1] + 9) {
                return false;
            }
            if (count != clues[i][0] && count != clues[i][0] + 1) {
                return false;
            }
        }
        return true;
    }

    private static void paint() {
        System.out.println("Solved Kakuro:");
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (board[i][j] == -1) {
                    System.out.print("x ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}