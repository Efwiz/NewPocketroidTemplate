package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public interface IntStream {
    int LA(int i);

    void consume();

    String getSourceName();

    int index();

    int mark();

    void release(int i);

    void rewind();

    void rewind(int i);

    void seek(int i);

    int size();
}
