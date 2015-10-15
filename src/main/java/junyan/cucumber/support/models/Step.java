package junyan.cucumber.support.models;

import java.util.List;

/**
 * Created by kingangeltot on 15/7/23.
 */
public class Step {
    private String flowStepId;
    private String keyWord;
    private String content;
    private String stepType;
    private Element element;
    private List<Trans> trans;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public List<Trans> getTrans() {
        return trans;
    }

    public void setTrans(List<Trans> trans) {
        this.trans = trans;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getFlowStepId() {
        return flowStepId;
    }

    public void setFlowStepId(String flowStepId) {
        this.flowStepId = flowStepId;
    }
}
