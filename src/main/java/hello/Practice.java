package hello;

public class Practice {

    // final is analogous to const
    private final String message;

    // Greeting object constructor
    public Practice(String content) {
        this.message = content;
    }

    /**
     * NOTE: domain objects MUST have a getter for all member fields.
     */
    public String getMessage() {
        return this.message;
    }
}
