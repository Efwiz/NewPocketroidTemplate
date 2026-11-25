package com.github.difflib.algorithm;

/* JADX INFO: loaded from: classes.dex */
public interface DiffAlgorithmListener {
    void diffEnd();

    void diffStart();

    void diffStep(int i, int i2);
}
