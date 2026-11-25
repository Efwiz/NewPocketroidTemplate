package com.github.difflib.algorithm;

import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface DiffAlgorithmI<T> {
    List<Change> computeDiff(List<T> list, List<T> list2, DiffAlgorithmListener diffAlgorithmListener);

    default List<Change> computeDiff(T[] tArr, T[] tArr2, DiffAlgorithmListener diffAlgorithmListener) {
        return computeDiff(Arrays.asList(tArr), Arrays.asList(tArr2), diffAlgorithmListener);
    }
}
