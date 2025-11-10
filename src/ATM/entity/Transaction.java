package ATM.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class đại diện cho một giao dịch ATM
 */
public class Transaction implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime transactionDateTime;
    private String transactionType;  // "RUT_TIEN", "NAP_TIEN"
    private double amount;
    private double balanceAfter;
    
    public Transaction(LocalDateTime transactionDateTime, String transactionType, 
                      double amount, double balanceAfter) {
        this.transactionDateTime = transactionDateTime;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("%-20s | %-15s | %,15.0f | %,15.0f", 
                           transactionDateTime.format(formatter),
                           transactionType,
                           amount,
                           balanceAfter);
    }
    
    /**
     * Format để hiển thị trong bảng lịch sử
     */
    public String toTableRow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String typeDisplay = transactionType.equals("RUT_TIEN") ? "Rút tiền" : "Nạp tiền";
        return String.format("%-17s |  %-10s | %,12.0f VND | %,15.0f VND",
                           transactionDateTime.format(formatter),
                           typeDisplay,
                           amount,
                           balanceAfter);
    }
}