package ATM.readfile;

import ATM.entity.Account;
import ATM.entity.Transaction;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * Tool để xem nội dung file accounts.dat
 * Chạy class này để kiểm tra dữ liệu đã lưu
 */
public class DataViewer {
    
    public static void main(String[] args) {
        String filename = "accounts.dat";
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          XEM DỮ LIỆU TRONG FILE: " + filename + "          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            
            @SuppressWarnings("unchecked")
            Map<String, Account> accounts = (Map<String, Account>) ois.readObject();
            
            System.out.println("✓ Đọc file thành công!");
            System.out.println("✓ Tổng số tài khoản: " + accounts.size() + "\n");
            
            System.out.println("┌─────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                         DANH SÁCH TÀI KHOẢN                             │");
            System.out.println("└─────────────────────────────────────────────────────────────────────────┘\n");
            
            int index = 1;
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                Account acc = entry.getValue();
                
                System.out.println("═══════════════════════════════════════════════════════════════");
                System.out.println("TÀI KHOẢN #" + index);
                System.out.println("═══════════════════════════════════════════════════════════════");
                System.out.println("  Số tài khoản       : " + acc.getAccountNumber());
                System.out.println("  Chủ tài khoản      : " + acc.getAccountHolderName());
                System.out.println("  Mã PIN             : " + acc.getPin());
                System.out.printf  ("  Số dư hiện tại     : %,15.0f VND\n", acc.getBalance());
                System.out.println("  Số giao dịch       : " + acc.getTransactions().size());
                
                // Hiển thị lịch sử giao dịch nếu có
                if (!acc.getTransactions().isEmpty()) {
                    System.out.println("\n  ┌─── LỊCH SỬ GIAO DỊCH ───────────────────────────────────┐");
                    System.out.println("  │ Thời gian         Loại      Số tiền           Số dư sau │");
                    System.out.println("  ├──────────────────────────────────────────────────────────┤");
                    
                    for (Transaction t : acc.getTransactions()) {
                        String type = t.getTransactionType().equals("RUT_TIEN") ? "Rút" : "Nạp";
                        System.out.printf("  │ %s   %-8s %,12.0f   %,12.0f │\n",
                            t.getTransactionDateTime().toString().substring(0, 16),
                            type,
                            t.getAmount(),
                            t.getBalanceAfter());
                    }
                    System.out.println("  └──────────────────────────────────────────────────────────┘");
                } else {
                    System.out.println("\n  ℹ Chưa có giao dịch nào");
                }
                
                System.out.println();
                index++;
            }
            
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                    THỐNG KÊ TỔNG QUAN                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            
            double totalBalance = accounts.values().stream()
                    .mapToDouble(Account::getBalance)
                    .sum();
            
            int totalTransactions = accounts.values().stream()
                    .mapToInt(acc -> acc.getTransactions().size())
                    .sum();
            
            System.out.println("  Tổng số tài khoản  : " + accounts.size());
            System.out.printf  ("  Tổng số dư         : %,15.0f VND\n", totalBalance);
            System.out.println("  Tổng giao dịch     : " + totalTransactions);
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("✗ Lỗi khi đọc file: " + e.getMessage());
            System.err.println("\nNguyên nhân có thể:");
            System.err.println("  1. File không tồn tại");
            System.err.println("  2. File bị hỏng");
            System.err.println("  3. Cấu trúc class đã thay đổi");
            e.printStackTrace();
        }
    }
}