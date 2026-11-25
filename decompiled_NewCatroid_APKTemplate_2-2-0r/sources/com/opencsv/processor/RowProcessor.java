package com.opencsv.processor;

/* JADX INFO: loaded from: classes10.dex */
public interface RowProcessor {
    String processColumnItem(String str);

    void processRow(String[] strArr);
}
