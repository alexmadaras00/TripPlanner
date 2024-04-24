package org.example.servicedestinationrecommender.data.enums;

public enum Budget {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String label;

    Budget(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}