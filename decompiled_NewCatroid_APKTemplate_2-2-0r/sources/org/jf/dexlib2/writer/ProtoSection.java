package org.jf.dexlib2.writer;

/* JADX INFO: loaded from: classes6.dex */
public interface ProtoSection<StringKey, TypeKey, ProtoKey, TypeListKey> extends IndexSection<ProtoKey> {
    TypeListKey getParameters(ProtoKey protokey);

    TypeKey getReturnType(ProtoKey protokey);

    StringKey getShorty(ProtoKey protokey);
}
