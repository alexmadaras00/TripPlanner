package org.example.servicedestinationrecommender.data.enums;

public enum GroupType {
    FAMILY("Family"),
    FRIENDS("Friends"),
    SCHOOL("School"),
    COWORKERS("Coworkers"),
    COUPLE("Couple");

    private final String label;

    GroupType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
