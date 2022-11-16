import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    static Connection conn = null;
    static Statement stmt = null;
    static final String DB_URL = "jdbc:sqlite:sqlitedb/todo.db";

    // method untuk menampilkan menu
    public void showMenu() {
        System.out.println("Selamat datang di Program ToDoApp, silakan pilih menu");
        System.out.println("1. Insert Task\n"
                + "2. Edit Task\n"
                + "3. Search Task\n");
        System.out.print("Ketik pilihan menu (1, 2, atau 3) : ");
    }

    // method untuk menghubungkan program ke database sqlite
    public Connection connectDb() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // method untuk tampilan insert task
    public void insertTask(){
        Scanner scanner = new Scanner(System.in);
        // user diminta mengetik task yg akan dikerjakan
        System.out.print("Tulis tugas yang akan dikerjakan: ");
        String task = scanner.nextLine();

        // user diminta mengetik deadline task dalam format YYYY-MM-DD
        // TODO :
        // checking format deadline yang diinput user, kalau tidak sesuai diminta mengulangi
        while (true){
            System.out.print("Tuliskan deadline tugas (YYYY-MM-DD): ");
            String deadline = scanner.nextLine();
            String regex = "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$" 
      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" 
      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(deadline);
            if (matcher.matches()) {
                saveData(task, deadline);
                break;
            }
            else {
                System.out.println("Format data belum benar");
            }
        }
        // panggil method untuk simpan data ke db
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
                    " VALUES ('"+task+"', '"+deadline+"');";
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

    //method untuk search tugas berdasarkan id
    public void searchId(){
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Inputkan ID tugas yang ingin dicari : ");
        int id = myScanner.nextInt();

        selectTaskID(id);

        myScanner.close();
    }

    //method untuk select tugas berdasarkan ID di database
    public void selectTaskID(int id){
        String sql = "SELECT * FROM todo WHERE id LIKE '" + id + "'";
        try{
            Connection conn = this.connectDb();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            System.out.println("ID\tTugas\t\t\t\tDeadline");
            while (rs.next()){
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("tugas") + "\t\t" +
                        rs.getString("deadline"));
            }

        }  catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //method untuk search tugas bedasarkan nama
    public void searchName(){
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Inputkan Nama tugas yang ingin dicari : ");
        String task = myScanner.nextLine();

        selectTaskName(task);

        myScanner.close();
    }

    //method untuk select tugas berdasarkan nama di database
    public void selectTaskName(String task){
        String sql = "SELECT * FROM todo WHERE tugas LIKE '%" + task + "%'";
        try{
            Connection conn = this.connectDb();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            System.out.println("ID\tTugas\t\t\t\tDeadline");
            while (rs.next()){
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("tugas") + "\t\t" +
                        rs.getString("deadline"));
            }

        }  catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //method untuk search tugas berdasarkan deadline
    public void searchDeadline(){
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Inputkan Deadline tugas yang ingin dicari (YYYY-MM-DD) : ");
        String task = myScanner.nextLine();

        selectTaskDeadline(task);

        myScanner.close();
    }

    //method untuk select tugas berdasarkan deadline
    public void selectTaskDeadline(String deadline){
        String sql = "SELECT * FROM todo WHERE deadline LIKE '%" + deadline + "%'";
        try{
            Connection conn = this.connectDb();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            System.out.println("ID\tTugas\t\t\t\tDeadline");
            while (rs.next()){
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("tugas") + "\t\t" +
                        rs.getString("deadline"));
            }

        }  catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //method untuk memilih metode search
    public void searchTask(){
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Pilih Metode Search Task");
        System.out.println("1. Berdasarkan Nama Task");
        System.out.println("2. Berdasarkan ID Task");
        System.out.println("3. Berdasarkan Deadline Task");
        System.out.print("Inputkan Pilihan : ");
        String input = myScanner.nextLine();

        if (input.charAt(0) == '1') {
            searchName();
        } else if (input.charAt(0) == '2'){
            searchId();
        }
        else if (input.charAt(0) == '3'){
            searchDeadline();
        }
        else
            System.out.println("\nInput invalid!");

        myScanner.close();
    }

    //method untuk edit task
    public void editTask(){
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Inputkan ID Task yang ingin di edit: ");
        int id = myScanner.nextInt();
        selectTaskID(id);

        System.out.println("================================================");
        System.out.println("1. Nama Tugas\n" +
                "2. Deadline Tugas\n");
        System.out.print("Inputkan data yang ingin diubah (1/2): ");
        int input = myScanner.nextInt();

        if (input == 1){
            Scanner sc = new Scanner(System.in);
            System.out.print("Inputkan Nama Tugas yang baru: ");
            String newTugas = sc.nextLine();
            System.out.println("Sedang memperbarui Nama Task yang dipilih....");
            editNamaTask(id, newTugas);
            sc.close();
        }
        else if(input == 2){
            Scanner sc = new Scanner(System.in);
            System.out.print("Inputkan Deadline Tugas yang baru(YYYY-MM-DD): ");
            String newDeadline = sc.nextLine();
            System.out.println("Sedang memperbarui Deadline Task yang dipilih....");
            editDeadlineTask(id, newDeadline);
            sc.close();
        }
        else{
            System.out.println("Invalid!");
        }

        myScanner.close();
    }

    //method untuk edit nama tugas database
    public void editNamaTask(int id, String newTugas){
        Connection conn = null;
        Statement stmt = null;
        String sql = "UPDATE todo SET tugas = '" +newTugas+"'WHERE id ="+id+";";

        try {
            // create connection to db
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            System.out.println("Nama Tugas Berhasil diperbarui!");
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            conn.close();

        }catch (SQLException e) {
            System.out.println("Operasi Gagal " + e.getMessage());
        }
    }

    //method untuk edit deadline tugas database
    public void editDeadlineTask(int id, String newDeadline){
        Connection conn = null;
        Statement stmt = null;
        String sql = "UPDATE todo SET deadline = '" +newDeadline+"'WHERE id ="+id+";";

        try {
            // create connection to db
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            System.out.println("Deadline Tugas Berhasil diperbarui!");
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
            conn.close();

        }catch (SQLException e) {
            System.out.println("Operasi Gagal " + e.getMessage());
        }
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
        } else if (input.charAt(0) == '2'){
            //System.out.println("\nAnda memilih Edit Task");
            app.editTask();
        }
        else if (input.charAt(0) == '3'){
            //System.out.println("\nAnda memilih Search Task");
            app.searchTask();
        }
        else
            System.out.println("\nInput invalid!");

        scanner.close();
        conn.close();
    }
}
