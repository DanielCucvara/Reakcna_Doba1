package com.company;

public class Record {
    final public Double value;
    final public String player;

    public Record(Double value, String player) {
        this.value = value;
        this.player = player;
    }

    public Double getValue() {
        return value;
    }

}
