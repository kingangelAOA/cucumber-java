package junyan.cucumber.support.models;

/**
 * Created by kingangeltot on 15/7/23.
 */
public class Trans {
    private String transId;
    private String transType;
    private String transValue;
    private Element element;
    private String keyWord;


    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }


    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransValue() {
        return transValue;
    }

    public void setTransValue(String transValue) {
        this.transValue = transValue;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
