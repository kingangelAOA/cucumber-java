package junyan.cucumber.support.models;

import java.util.List;

/**
 * Created by kingangeltot on 15/7/23.
 */
public class Case {
    private String caseId;
    private String caseName;
    private List<Step> steps;
    private List<Assert> asserts;


    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Assert> getAsserts() {
        return asserts;
    }

    public void setAsserts(List<Assert> asserts) {
        this.asserts = asserts;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
