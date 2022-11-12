import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    static Connection cxn = null;
    static Statement stmt = null;

    // method untuk menampilkan menu
    public void showMenu() {
        System.out.println("Selamat datang di Program ToDoApp, silakan pilih menu");
        System.out.println("1. Insert Task\n"
                + "2. Edit Task\n"
                + "3. Search Task\n");
    }

    // method untuk menghubungkan program ke database sqlite
    public void connectDb() {
        try {
            cxn = DriverManager.getConnection("jdbc:sqlite:sqlitedb/todo.db");
            System.out.println("Koneksi ke database SQLite: " + (cxn != null) + "\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.connectDb();
        app.showMenu();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.charAt(0) == '1')
            System.out.println("\nAnda memilih Insert Task");
        else if (input.charAt(0) == '2')
            System.out.println("\nAnda memilih Edit Task");
        else if (input.charAt(0) == '3')
            System.out.println("\nAnda memilih Search Task");
        else
            System.out.println("\nInput invalid!");

        scanner.close();
        cxn.close();

        // TODO:
        // menambahkan input, edit, dan search tasks
    }
}