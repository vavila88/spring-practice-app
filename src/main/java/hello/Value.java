package hello;

        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object representation of a JSON payload with fields
 *      id:<int> and
 *      quote:<string>
 */
// Annotation used to specify to ignore properties that we are not aware of
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    private Long id;
    private String quote;

    public Value() {
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Value{" +
                "id=" + this.id + "'" +
                ", quote=" + this.quote +
                "'}";
    }
}
