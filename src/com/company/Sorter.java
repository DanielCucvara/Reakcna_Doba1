package com.company;

import java.util.Comparator;

public class Sorter implements Comparator<Record> {

    @Override
    public int compare(Record t1, Record t2) {
        return t1.value.compareTo(t2.value);
    }
}
