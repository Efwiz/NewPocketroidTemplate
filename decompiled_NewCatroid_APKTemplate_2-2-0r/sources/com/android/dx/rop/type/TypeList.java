package com.android.dx.rop.type;

/* JADX INFO: loaded from: classes7.dex */
public interface TypeList {
    Type getType(int i);

    int getWordCount();

    boolean isMutable();

    int size();

    TypeList withAddedType(Type type);
}
