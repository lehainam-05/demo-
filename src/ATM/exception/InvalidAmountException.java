package ATM.exception;

/**
 * Exception ném ra khi số tiền không hợp lệ (không phải bội số 50,000)
 */
public class InvalidAmountException extends Exception {
    
    private double invalidAmount;
    
    public InvalidAmountException(String message, double invalidAmount) {
        super(message);
        this.invalidAmount = invalidAmount;
    }
    
    public double getInvalidAmount() {
        return invalidAmount;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + 
               String.format(" (Số tiền: %,.0f VND - phải là bội số của 50,000 VND)", invalidAmount);
    }
}