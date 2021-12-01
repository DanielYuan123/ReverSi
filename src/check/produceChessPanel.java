package check;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class produceChessPanel {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Path: ");
        String path = input.next();
        
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            
            bufferedWriter.write("CurrentPlayer: ");
            bufferedWriter.write(input.next());
            bufferedWriter.write("\n");
            bufferedWriter.write("GameScore: \nBlack: \n");
            bufferedWriter.write(input.nextInt());
            bufferedWriter.newLine();
            bufferedWriter.write("White: \n");
            bufferedWriter.write(input.nextInt());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("ChessBoardPanel: ");
            bufferedWriter.close();
            /*
            for (int i = 0; i < 64; i++) {
                if (input.nextInt() == 1) {
                    bufferedWriter.write("BLACK\n");
                } else if (input.nextInt() == 2) {
                    bufferedWriter.write("WHITE\n");
                } else if (input.nextInt() == 0) {
                    bufferedWriter.write("NULL\n");
                } else {
                    System.exit(404);
                }
            }
            */
            
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
