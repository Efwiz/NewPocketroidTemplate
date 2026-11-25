package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public interface DataEnumerator<Data> {
    int enumerate(Data data) throws IOException;

    Data valueOf(int i) throws IOException;
}
