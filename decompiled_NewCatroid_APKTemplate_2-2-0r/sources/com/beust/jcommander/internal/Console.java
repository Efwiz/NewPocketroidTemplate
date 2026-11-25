package com.beust.jcommander.internal;

/* JADX INFO: loaded from: classes8.dex */
public interface Console {
    void print(String str);

    void println(String str);

    char[] readPassword(boolean z);
}
