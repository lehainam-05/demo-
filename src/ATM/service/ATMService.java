package ATM.service;

import ATM.entity.Account;
import ATM.entity.Transaction;
import ATM.exception.InsufficientFundsException;
import ATM.exception.InvalidAmountException;
import ATM.exception.InvalidCredentialsException;
import ATM.repository.AccountRepository;

import java.util.List;

/**
 * Service xử lý logic nghiệp vụ cho ATM
 */
public class ATMService {
    
    private final AccountRepository accountRepository;
    
    public ATMService() {
        this.accountRepository = new AccountRepository();
    }
    
    /**
     * Đăng nhập vào ATM
     */
    public Account login(String accountNumber, String pin) throws InvalidCredentialsException {
        return accountRepository.login(accountNumber, pin);
    }
    
    /**
     * Kiểm tra số dư
     */
    public double checkBalance(String accountNumber) {
        return accountRepository.checkBalance(accountNumber);
    }
    
    /**
     * Rút tiền
     */
    public void withdraw(Account account, double amount) throws InsufficientFundsException, InvalidAmountException {
        account.withdraw(amount);
        accountRepository.saveData(); // Lưu ngay sau khi rút tiền
    }
    
    /**
     * Nạp tiền
     */
    public void deposit(Account account, double amount) throws InvalidAmountException {
        account.deposit(amount);
        accountRepository.saveData(); // Lưu ngay sau khi nạp tiền
    }
    
    /**
     * Xem lịch sử giao dịch
     */
    public List<Transaction> getTransactionHistory(String accountNumber, int limit) {
        return accountRepository.getRecentTransactions(accountNumber, limit);
    }

    /**
     * Lấy thông tin tài khoản
     */
    public Account getAccount(String accountNumber) {
        return accountRepository.getAccount(accountNumber);
    }

    /**
     * Lưu dữ liệu
     */
    public void saveData() {
        accountRepository.saveData();
    }
}