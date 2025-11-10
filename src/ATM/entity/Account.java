package ATM.entity;

import ATM.exception.InsufficientFundsException;
import ATM.exception.InvalidAmountException;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class đại diện cho một tài khoản ngân hàng trong hệ thống ATM
 */
public class Account implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String accountNumber;      // Số tài khoản (6-10 chữ số)
    private String accountHolderName;  // Tên chủ tài khoản
    private String pin;                // Mã PIN (4-6 chữ số)
    private double balance;            // Số dư
    private List<Transaction> transactions; // Lịch sử giao dịch

    public Account(String accountNumber, String accountHolderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    /**
     * Kiểm tra PIN có đúng không
     */
    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    /**
     * Rút tiền từ tài khoản
     * @throws InsufficientFundsException nếu số dư không đủ
     * @throws InvalidAmountException nếu số tiền không phải bội số 50,000
     */
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        // Kiểm tra số tiền có phải bội số của 50,000 không
        if (amount % 50000 != 0) {
            throw new InvalidAmountException("Số tiền rút phải là bội số của 50,000 VND", amount);
        }
        
        // Kiểm tra số dư có đủ không
        if (balance < amount) {
            throw new InsufficientFundsException("Số dư không đủ để thực hiện giao dịch", amount, balance);
        }
        
        // Thực hiện rút tiền
        balance -= amount;
        
        // Lưu giao dịch
        Transaction transaction = new Transaction(
            LocalDateTime.now(),
            "RUT_TIEN",
            amount,
            balance
        );
        transactions.add(transaction);
    }

    /**
     * Nạp tiền vào tài khoản
     */
    public void deposit(double amount) throws InvalidAmountException {
        // Kiểm tra số tiền hợp lệ
        if (amount <= 0) {
            throw new InvalidAmountException("Số tiền nạp phải lớn hơn 0", amount);
        }
        
        // Thực hiện nạp tiền
        balance += amount;
        
        // Lưu giao dịch
        Transaction transaction = new Transaction(
            LocalDateTime.now(),
            "NAP_TIEN",
            amount,
            balance
        );
        transactions.add(transaction);
    }

    /**
     * Lấy 5 giao dịch gần nhất
     */
    public List<Transaction> getRecentTransactions(int limit) {
        int size = transactions.size();
        if (size == 0) {
            return new ArrayList<>();
        }
        
        int fromIndex = Math.max(0, size - limit);
        return new ArrayList<>(transactions.subList(fromIndex, size));
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", balance=" + balance +
                ", totalTransactions=" + transactions.size() +
                '}';
    }

//    @Override
//    /**
//     * equals() mặc định chỉ so sánh xem các obj có nằm cùng 1 ô nhớ ko
//     * nên khi dùng equals() mặc định sẽ gây sai
//     * => Khi đó ta override lại pthuc equals() để cho Java hiểu đc là "nếu 2 tk nếu cùng accountNumber thì đc coi là = nhau"!
//     **/
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//
//        Account account = (Account) obj;
//        return accountNumber != null ? accountNumber.equals(account.accountNumber) : account.accountNumber == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return accountNumber != null ? accountNumber.hashCode() : 0;
//    }
}