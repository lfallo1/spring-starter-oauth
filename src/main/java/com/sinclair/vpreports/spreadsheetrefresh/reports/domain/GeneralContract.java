package com.sinclair.vpreports.spreadsheetrefresh.reports.domain;

public class GeneralContract {

    private int generalContractID;
    private String name;
    private String syndicatorName;
    private String programType;

    public GeneralContract() {
    }

    public int getGeneralContractID() {
        return generalContractID;
    }

    public void setGeneralContractID(int generalContractID) {
        this.generalContractID = generalContractID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSyndicatorName() {
        return syndicatorName;
    }

    public void setSyndicatorName(String syndicatorName) {
        this.syndicatorName = syndicatorName;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }
}
