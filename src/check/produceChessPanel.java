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
        System.out.println("CurrentPlayer: ");
        
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            
            System.out.print("CurrentPlayer: ");
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
