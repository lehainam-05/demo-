package ATM.repository;

import ATM.entity.Account;
import ATM.entity.Transaction;
import ATM.exception.InvalidCredentialsException;
import ATM.util.FileManager;
import java.util.List;
import java.util.Map;

/**
 * Repository quản lý dữ liệu tài khoản
 * Sử dụng Map<String, Account> để lưu trữ
 */
public class AccountRepository {
    
    private Map<String, Account> accounts;
    
    public AccountRepository() {
        // Đọc dữ liệu từ file khi khởi tạo
        this.accounts = FileManager.loadAccounts();
        
        // Nếu chưa có dữ liệu, tạo một số tài khoản mẫu
        if (accounts.isEmpty()) {
            initializeSampleAccounts();
        }
    }
    
    /**
     * Khởi tạo một số tài khoản mẫu để test
     */
    private void initializeSampleAccounts() {
        System.out.println("\n=== KHỞI TẠO DỮ LIỆU MẪU ===");
        
        Account acc1 = new Account("1", "Lê Hải Nam", "1", 5000000);
        Account acc2 = new Account("2", "Nguyễn Thuỳ Linh", "2", 10000000);
        Account acc3 = new Account("3", "Nguyễn Đắc Tuấn Nghĩa", "3", 2000000);
        Account acc4 = new Account("4", "Test", "4", 2000000);
        accounts.put(acc1.getAccountNumber(), acc1);
        accounts.put(acc2.getAccountNumber(), acc2);
        accounts.put(acc3.getAccountNumber(), acc3);
        accounts.put(acc4.getAccountNumber(), acc4);
        
        // Lưu ngay vào file
        saveData();

        int totalAccounts = accounts.size();
        System.out.println("✓ Đã tạo " + totalAccounts + " tài khoản mẫu:");

        // Hiển thị danh sách tài khoản
        for (Account acc : accounts.values()) {
            System.out.printf("  - Tài khoản: %s | PIN: %-5s | Số dư: %,10.0f VND%n",
                    acc.getAccountNumber(),
                    acc.getPin(),
                    acc.getBalance());
        }

        System.out.println("==========================================================\n");
    }
    
    /**
     * Xác thực đăng nhập
     * @throws InvalidCredentialsException nếu thông tin không đúng
     */
    public Account login(String accountNumber, String pin) throws InvalidCredentialsException {
        Account account = accounts.get(accountNumber);
        
        if (account == null) {
            throw new InvalidCredentialsException("Số tài khoản không tồn tại!");
        }
        
        if (!account.verifyPin(pin)) {
            throw new InvalidCredentialsException("Mã PIN không chính xác!");
        }
        
        return account;
    }
    
    /**
     * Lấy tài khoản theo số tài khoản
     */
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    /**
     * Kiểm tra số dư
     */
    public double checkBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }
    
    /**
     * Lấy 5 giao dịch gần nhất
     */
    public List<Transaction> getRecentTransactions(String accountNumber, int limit) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            return account.getRecentTransactions(limit);
        }
        return List.of();
    }
    
    /**
     * Lưu dữ liệu vào file
     */
    public void saveData() {
        FileManager.saveAccounts(accounts);
    }

}