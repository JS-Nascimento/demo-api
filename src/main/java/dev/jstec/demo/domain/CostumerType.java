package dev.jstec.demo.domain;

public enum CostumerType {
    F("Fisica"),
    J("Juridica");

    private final String abbreviation;

    CostumerType ( String abbreviation ) {

        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
