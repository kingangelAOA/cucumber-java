package junyan.cucumber.support.models;

//import org.apache.xpath.operations.String;

/**
 * Created by kingangeltot on 15/7/23.
 */
public class Element {
    private String amount;
    private String by;
    private String value;
    private boolean isSenior;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSenior() {
        return isSenior;
    }

    public void setIsSenior(boolean isSenior) {
        this.isSenior = isSenior;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
