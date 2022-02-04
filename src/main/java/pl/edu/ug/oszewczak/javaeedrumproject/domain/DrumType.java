package pl.edu.ug.oszewczak.javaeedrumproject.domain;

public enum DrumType {
    SNARE("Snare"),
    BASS("Bass"),
    TOM("Tom"),
    FLOOR_TOM("Floor Tom");

    private final String displayValue;

    DrumType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}

