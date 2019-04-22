package hello;

/**
 * this class represents the resource at a given api endpoint, in this example case, at /greeting
 * the contents of this object will be automagically converted into a JSON object when returned
 * as part of a request handler
 */
public class Greeting {

    // final is analogous to const
    private final long id;
    private final String content;

    // Greeting object constructor
    public Greeting (long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }
}
