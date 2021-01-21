import java.util.concurrent.TimeUnit;
import java.util.Scanner;

class GameOfLife {
    public static void main(String[] args) {
        // Initialise the board
        boolean[][] board = initialiseBoard();

        // Main loop
        try {
            TimeUnit.SECONDS.sleep(1);
            while(true){
                TimeUnit.SECONDS.sleep(1);
                board = iterateBoard(board);
                draw(board);
            }
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
    }

    // Setting a board up
    //
    public static boolean[][] initialiseBoard() {
        int dimention = inputInt("Enter the size of the board that you want.", 1, 100);
        boolean[][] board = generate2DArrays(dimention);
        draw(board);

        while (true) {
            System.out.println();
            int x = inputInt("Enter what x coordinate you want to make alive. (Enter 0 to proceed to next stage)", 0, dimention);
            if (x == 0) break;
            int y = inputInt("Enter what y coordinate you want to make alive. (Enter 0 to proceed to next stage)", 0, dimention);
            if (y == 0) break;

            // Convert to coordinates
            x--; y--;
            
            String coordinateValue;
            boolean validInput = false;
            while(!validInput){
                coordinateValue = inputString("Enter true/false for ("+(x+1)+"),("+(y+1)+") ").toLowerCase();
                switch (coordinateValue) {
                    case "true":
                        System.out.println("("+(x+1)+"),("+(y+1)+") = true");
                        board[x][y] = true;
                        validInput = true;
                        break;
                    case "false":
                        System.out.println("("+(x+1)+"),("+(y+1)+") = false");
                        board[x][y] = false;
                        validInput = true;
                        break;
                    default:
                        System.out.println("You have enter an invalid option. Please try again.");
                        break;
                    }
            }
            draw(board);
        }

        return board;
    }


    // Return user input as a string after print a message
    //
    public static String inputString (String message){
        Scanner scanner = new Scanner(System.in);

        System.out.print(message);

        String userInput = scanner.nextLine();
        return userInput;

    }// END inputString


    // Get int from user that is in the interval [minValue, maxValue]
    // after printing a message
    //
    public static int inputInt(String message, int minValue, int maxValue) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        int userNumber = 0;

        // Make sure it is an int
        try {
            userNumber = Integer.parseInt( scanner.nextLine() );
        } catch (Exception e) {
            System.out.println("Invalid. Please enter a number.");
            return inputInt(message, minValue, maxValue);
        }

        if ( (minValue <= userNumber) && (userNumber <= maxValue) ){
            return userNumber;
        }else{
            System.out.println("The number should be between "+ minValue +" and "+ maxValue +".");
            return inputInt(message, minValue, maxValue);
        }
    }

    // Returns a boolean dimention * dimention 2D array
    //
    public static boolean[][] generate2DArrays(int dimention) {
        boolean[][] array2D = new boolean[dimention][dimention];
        return array2D;
    }

    // Iterate the board
    //
    public static boolean[][] iterateBoard(boolean[][] board) {
        boolean[][] newBoard = generate2DArrays(board.length);

        for (int x = 0; x <= board.length-1; x++){
            for (int y = 0; y <= board.length-1; y++){
                int numOfTrueNeigbours = getTrueNeigbours(board, x, y);

                if ((numOfTrueNeigbours <= 1) && (board[x][y] == true))         // die of loneliness
                    newBoard[x][y] = false;
                
                else if ((numOfTrueNeigbours <= 3) && (board[x][y] == true))    // survive
                    newBoard[x][y] = true;

                else if ((numOfTrueNeigbours >= 4) && (board[x][y] == true))    // die of overcrowding
                    newBoard[x][y] = false;

                else if ((numOfTrueNeigbours == 3) && (board[x][y] == false))      // comes back to life
                    newBoard[x][y] = true;
            }
        }

        return newBoard;
    }

    // Return the number of neighboring true squares
    //
    public static int getTrueNeigbours(boolean[][] board, int coordinateX, int coordinateY) {
        int numOfNeigbours = 0;
        int biggestCoordinate = board.length - 1;
        for (int x = coordinateX-1; x <= coordinateX+1; x++){
            for (int y = coordinateY-1; y <= coordinateY+1; y++){
                // Make sure that the coordinates are within the board
                if (((x >= 0) && (x <= biggestCoordinate)) && ((y >= 0) && (y <= biggestCoordinate))){
                    // Make sure we only cound the neigbouring squares
                    if ((x != coordinateX) || (y != coordinateY)){
                        if (board[x][y] == true)    numOfNeigbours++;
                    }
                }
            }
        }

        return numOfNeigbours;
    }

    // Draw a board of any square dimention
    //
    public static void draw(boolean[][] board) {
        int dimention = board.length;
        String forTrue = "  ";
        String forFalse = "  ";

        // Colour strings
        String reset = "\u001b[0m";
        String redBG = "\u001B[41m";
        String greenBG = "\u001B[42m";

        String aliveColour = greenBG;
        String deadColour = reset;

        System.out.println("dimention: "+dimention);
        
        // Generate borders
        String topBorder = generateTopBorder(dimention);
        String bottomBorder = generateBottomBorder(dimention);
        String seperatorBorder = generateSeperatorBorder(dimention);

        System.out.println(topBorder);
        for (int i = 0; i <= dimention-1; i++){
            System.out.print(" ┃");
            for (int z = 0; z <= dimention-1; z++){
                
                String lookChange = (board[i][z]) ? aliveColour : deadColour;
                
                if (z == dimention-1){
                    // If the boolean is true print 1
                    if (board[i][z])
                        System.out.print(lookChange + forTrue + reset);
                    else
                        System.out.print(lookChange + forFalse + reset);
                    System.out.print("┃ " + (i+1) + "\n");
                }else{
                    // If the boolean is true print 1
                    if (board[i][z])
                        System.out.print(lookChange + forTrue + reset);
                    else
                        System.out.print(lookChange + forFalse + reset);
                    System.out.print("┃");
                }
                

            }
            if (i != dimention-1)
                System.out.print(seperatorBorder+"\n");

        }        
        System.out.println(bottomBorder);
    }


    // Generate top border for board of size dimention
    //
    public static String generateTopBorder(int dimention) {
        String topBorder = " ";

        topBorder = topBorder + "┏";
        for (int i = 1; i <= dimention + dimention - 1; i++){
            if (i == (dimention + dimention - 1))
                topBorder = topBorder + "━━";
            else if (i%2 == 0)
                topBorder = topBorder + "┳";
            else
                topBorder = topBorder + "━━";
        }
        topBorder = topBorder + "┓ ";

        return topBorder;
    }


    // Generate seperator border for board of size dimention
    //
    public static String generateSeperatorBorder(int dimention) {
        String topBorder = " ";

        topBorder = topBorder + "┣";
        for (int i = 1; i <= dimention + dimention - 1; i++){
            if (i == (dimention + dimention - 1))
                topBorder = topBorder + "━━";
            else if (i%2 == 0)
                topBorder = topBorder + "╋";
            else
                topBorder = topBorder + "━━";
        }
        topBorder = topBorder + "┫ ";

        return topBorder;
    }


    // Generate bottom border for board of size dimention
    //
    public static String generateBottomBorder(int dimention) {
        String topBorder = " ";

        topBorder = topBorder + "┣";
        for (int i = 1; i <= dimention + dimention - 1; i++){
            if (i == (dimention + dimention - 1))
                topBorder = topBorder + "━━";
            else if (i%2 == 0)
                topBorder = topBorder + "┻";
            else
                topBorder = topBorder + "━━";
        }
        topBorder = topBorder + "┛ ";

        return topBorder;
    }
}