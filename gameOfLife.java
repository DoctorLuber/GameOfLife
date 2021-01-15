class GameOfLife{
    public static void main(String[] args) {
        boolean[][] board = generate2DArrays(10);
        draw(board);
    }

    // Returns a boolean dimention * dimention 2D array
    //
    public static boolean[][] generate2DArrays(int dimention) {
        boolean[][] array2D = new boolean[dimention][dimention];
        return array2D;
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

        System.out.println("dimention: "+dimention);
        
        // Generate borders
        String topBorder = generateTopBorder(dimention);
        String bottomBorder = generateBottomBorder(dimention);
        String seperatorBorder = generateSeperatorBorder(dimention);

        System.out.println(topBorder);
        for (int i = 0; i <= dimention-1; i++){
            System.out.print(" ┃");
            for (int z = 0; z <= dimention-1; z++){
                
                String lookChange = (board[i][z]) ? greenBG : redBG;
                
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