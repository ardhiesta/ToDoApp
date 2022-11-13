import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    static Connection cxn = null;
    static Statement stmt = null;
    static final String DB_URL = "jdbc:sqlite:sqlitedb/todo.db";

    // method untuk menampilkan menu
    public void showMenu() {
        System.out.println("Selamat datang di Program ToDoApp, silakan pilih menu");
        System.out.println("1. Insert Task\n"
                + "2. Edit Task\n"
                + "3. Search Task\n");
        System.out.println("Ketik pilihan menu (1, 2, atau 3) : ");
    }

    // method untuk menghubungkan program ke database sqlite
    public void connectDb() {
        try {
            cxn = DriverManager.getConnection(DB_URL);
            System.out.println("Koneksi ke database SQLite: " + (cxn != null) + "\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    // method untuk tampilan insert task
    public void insertTask(){
        Scanner scanner = new Scanner(System.in);
        // user diminta mengetik task yg akan dikerjakan
        System.out.println("Tulis tugas yang akan dikerjakan: ");
        String task = scanner.nextLine();

        // user diminta mengetik deadline task dalam format YYYY-MM-DD
        // TODO : 
        // checking format deadline yang diinput user, kalau tidak sesuai diminta mengulangi
        System.out.println("Tuliskan deadline tugas (YYYY-MM-DD): ");
        String deadline = scanner.nextLine();

        // panggil method untuk simpan data ke db
        saveData(task, deadline);
        scanner.close();
    } 

    // method untuk menyimpan task ke database 
    public static void saveData(String task, String deadline){
        Connection conn = null;
        Statement stmt = null;
        try {
            // create connection to db
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = conn.createStatement();
            // perintah SQL untuk insert data
            String sql = "INSERT INTO todo (tugas, deadline) " +
                    "VALUES ('"+task+"', '"+deadline+"');";
            // eksekusi printah SQL
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.connectDb();
        app.showMenu();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.charAt(0) == '1') {
            // System.out.println("\nAnda memilih Insert Task");
            app.insertTask();
        } else if (input.charAt(0) == '2')
            System.out.println("\nAnda memilih Edit Task");
        else if (input.charAt(0) == '3')
            System.out.println("\nAnda memilih Search Task");
        else
            System.out.println("\nInput invalid!");

        scanner.close();
        cxn.close();

        // TODO:
        // menambahkan edit, dan search tasks
    }
}