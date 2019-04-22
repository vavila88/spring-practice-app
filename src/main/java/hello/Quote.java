package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object representation of a JSON payload with fields
 *      type:<string> and
 *      value:<json>
 */
// Annotation used to specify to ignore properties that we are not aware of
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String type;
    private Value value;

    public Quote() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type=" + type + "'" +
                ", value=" + value +
                "'}";
    }
}
