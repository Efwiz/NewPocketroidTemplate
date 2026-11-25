package com.artemis.utils;

import java.util.Comparator;

/* JADX INFO: loaded from: classes8.dex */
public class Sort {
    private static Sort instance;
    private ComparableTimSort comparableTimSort;
    private TimSort timSort;

    public <T> void sort(Bag<T> bag) {
        if (this.comparableTimSort == null) {
            this.comparableTimSort = new ComparableTimSort();
        }
        this.comparableTimSort.doSort(bag.data, 0, bag.size());
    }

    public <T> void sort(T[] tArr) {
        if (this.comparableTimSort == null) {
            this.comparableTimSort = new ComparableTimSort();
        }
        this.comparableTimSort.doSort(tArr, 0, tArr.length);
    }

    public <T> void sort(T[] tArr, int i, int i2) {
        if (this.comparableTimSort == null) {
            this.comparableTimSort = new ComparableTimSort();
        }
        this.comparableTimSort.doSort(tArr, i, i2);
    }

    public <T> void sort(Bag<T> bag, Comparator<T> comparator) {
        if (this.timSort == null) {
            this.timSort = new TimSort();
        }
        this.timSort.doSort(bag.data, comparator, 0, bag.size());
    }

    public <T> void sort(T[] tArr, Comparator<T> comparator) {
        if (this.timSort == null) {
            this.timSort = new TimSort();
        }
        this.timSort.doSort(tArr, comparator, 0, tArr.length);
    }

    public <T> void sort(T[] tArr, Comparator<T> comparator, int i, int i2) {
        if (this.timSort == null) {
            this.timSort = new TimSort();
        }
        this.timSort.doSort(tArr, comparator, i, i2);
    }

    public static Sort instance() {
        if (instance == null) {
            instance = new Sort();
        }
        return instance;
    }
}
