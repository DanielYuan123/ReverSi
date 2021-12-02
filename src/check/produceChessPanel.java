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
            
            
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
