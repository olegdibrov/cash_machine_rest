package com.machine.project.utils;

public enum Status {
    CREATED(1), FINISHED(2), REFUND(3);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
