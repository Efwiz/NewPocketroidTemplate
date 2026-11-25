package com.github.difflib.algorithm;

import java.util.function.BiPredicate;

/* JADX INFO: loaded from: classes.dex */
public interface DiffAlgorithmFactory {
    <T> DiffAlgorithmI<T> create();

    <T> DiffAlgorithmI<T> create(BiPredicate<T, T> biPredicate);
}
