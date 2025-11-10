package ATM.exception;

/**
 * Exception ném ra khi số dư tài khoản không đủ
 */
public class InsufficientFundsException extends Exception {
    
    private double requestedAmount;
    private double availableBalance;
    
    public InsufficientFundsException(String message, double requestedAmount, double availableBalance) {
        super(message);
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }
    
    public double getRequestedAmount() {
        return requestedAmount;
    }
    
    public double getAvailableBalance() {
        return availableBalance;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + 
               String.format(" (Yêu cầu: %,.0f VND, Số dư khả dụng: %,.0f VND)", 
                           requestedAmount, availableBalance);
    }
}