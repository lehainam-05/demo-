package ATM.util;

import ATM.entity.Account;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class quản lý việc lưu trữ và đọc dữ liệu từ file
 */
public class FileManager {

    private static final String DATA_FILE = "accounts.dat";

    /**
     * Lưu Map tài khoản vào file bằng Serialization
     */
    public static void saveAccounts(Map<String, Account> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("✓ Dữ liệu đã được lưu vào file: " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("✗ Lỗi khi lưu dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Đọc Map tài khoản từ file
     * @return Map<String, Account> hoặc Map rỗng nếu file không tồn tại
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Account> loadAccounts() {
        File file = new File(DATA_FILE);

        // Nếu file chưa tồn tại, trả về Map rỗng
        if (!file.exists()) {
            System.out.println("ℹ File dữ liệu chưa tồn tại. Khởi tạo dữ liệu mặc định...");
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            Map<String, Account> accounts = (Map<String, Account>) ois.readObject();
            System.out.println("✓ Đã tải " + accounts.size() + " tài khoản từ file");
            return accounts;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Lỗi khi đọc dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}