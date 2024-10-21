import java.sql.*;
import java.util.Scanner;

public class EmployeeManagement {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeedb";
    private static final String USER = "root";
    private static final String PASSWORD = "anil1234";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            while (true) {
                System.out.println("\n--- Employee Management System ---");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employee");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        addEmployee(conn, scanner);
                        break;
                    case 2:
                        viewEmployee(conn, scanner);
                        break;
                    case 3:
                        updateEmployee(conn, scanner);
                        break;
                    case 4:
                        deleteEmployee(conn, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add an employee
    private static void addEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Employee Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Employee Department: ");
        String department = scanner.nextLine();

        String sql = "INSERT INTO employees (name, email, department) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, department);

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Employee added successfully!");
        }
        pstmt.close();
    }

    // Method to view an employee
    private static void viewEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Employee ID to view: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String sql = "SELECT * FROM employees WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Department: " + rs.getString("department"));
        } else {
            System.out.println("No employee found with ID: " + id);
        }
        rs.close();
        pstmt.close();
    }

    // Method to update an employee
    private static void updateEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter new Employee Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new Employee Department: ");
        String department = scanner.nextLine();

        String sql = "UPDATE employees SET email = ?, department = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, department);
        pstmt.setInt(3, id);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("No employee found with ID: " + id);
        }
        pstmt.close();
    }

    // Method to delete an employee
    private static void deleteEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        String sql = "DELETE FROM employees WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);

        int rowsDeleted = pstmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("No employee found with ID: " + id);
        }
        pstmt.close();
    }
}
