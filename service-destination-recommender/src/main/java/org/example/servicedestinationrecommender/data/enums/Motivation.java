package org.example.servicedestinationrecommender.data.enums;

public enum Motivation {
    FUN("Fun"),
    REMOTE_WORK("Remote Work"),
    EXPLORE("Explore"),
    HIKING("Hiking"),
    BEACH("Beach"),
    NOMAD("Nomad"),
    CAMPING("Camping"),
    FAMILY("Family"),
    FIND_LOVE("Find Love"),
    FIND_FRIENDS("Find Friends"),
    ADVENTURE("Adventure"),
    RELAX("Relax"),
    RESTORE_HOPE("Restore Hope"),
    RECHARGE("Recharge");

    private final String label;

    Motivation(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
