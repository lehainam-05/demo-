package ATM.exception;

/**
 * Exception ném ra khi thông tin đăng nhập không hợp lệ
 */
public class InvalidCredentialsException extends Exception {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}