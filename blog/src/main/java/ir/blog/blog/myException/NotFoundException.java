package ir.blog.blog.myException;

public class NotFoundException extends RuntimeException  {
        public NotFoundException(String message) {
            super(message);
        }
}

