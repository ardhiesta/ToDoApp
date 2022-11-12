import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    static Connection cxn = null;
    static Statement stmt = null;
    public static void main(String[] args) throws Exception {
        try {
            cxn = DriverManager.getConnection("jdbc:sqlite:sqlitedb/todo.db");
            System.out.println("Koneksi ke database SQLite: " + (cxn != null) + "\n");

            /* Menggunakan ANSI
            System.out.print("\033[1;93m @@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@\033[0m\033[32m ToDoApp\n"
                           + "\033[0m\033[1;93m@@@@@@@@@@@@@@@@@@\033[0m\033[36m`````\033[0m\033[1;93m@@@@\n"
                           + "@@@@@@@@@@@@@@@\033[0m\033[36m```````\033[0m\033[1;93m@@@@@\n"
                           + "@@@@\033[0m\033[36m``````\033[0m\033[1;93m@@@\033[0m\033[36m``````\033[0m\033[1;93m@@@@@@@@\033[0m\033[32m 1. Insert Task\n"
                           + "\033[0m\033[1;93m@@@@@@\033[0m\033[36m```````````\033[0m\033[1;93m@@@@@@@@@@\033[0m\033[32m 2. Edit Task\n"
                           + "\033[0m\033[1;93m@@@@@@@@\033[0m\033[36m``````\033[0m\033[1;93m@@@@@@@@@@@@@\033[0m\033[32m 3. Search Task\n"
                           + "\033[0m\033[1;93m@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + " @@@@@@@@@@@@@@@@@@@@@@@@@ \033[0m \033[32mInput: \033[0m");
            */

            System.out.print(" @@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@ ToDoApp\n"
                           + "@@@@@@@@@@@@@@@@@@`````@@@@\n"
                           + "@@@@@@@@@@@@@@@```````@@@@@\n"
                           + "@@@@``````@@@``````@@@@@@@@ 1. Insert Task\n"
                           + "@@@@@@```````````@@@@@@@@@@ 2. Edit Task\n"
                           + "@@@@@@@@``````@@@@@@@@@@@@@ 3. Search Task\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + "@@@@@@@@@@@@@@@@@@@@@@@@@@@\n"
                           + " @@@@@@@@@@@@@@@@@@@@@@@@@  Input: ");
            
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if(input.charAt(0) == '1') System.out.println("\nAnda memilih Insert Task");
            else if(input.charAt(0) == '2') System.out.println("\nAnda memilih Edit Task");
            else if(input.charAt(0) == '3') System.out.println("\nAnda memilih Search Task");
            else System.out.println("\nInput invalid!");

            scanner.close();
            cxn.close();
        } catch(SQLException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}