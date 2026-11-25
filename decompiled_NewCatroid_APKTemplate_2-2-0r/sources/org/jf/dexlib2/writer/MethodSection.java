package org.jf.dexlib2.writer;

import org.jf.dexlib2.iface.reference.MethodProtoReference;
import org.jf.dexlib2.iface.reference.MethodReference;

/* JADX INFO: loaded from: classes6.dex */
public interface MethodSection<StringKey, TypeKey, ProtoRefKey extends MethodProtoReference, MethodRefKey extends MethodReference, MethodKey> extends IndexSection<MethodRefKey> {
    TypeKey getDefiningClass(MethodRefKey methodrefkey);

    int getMethodIndex(MethodKey methodkey);

    MethodRefKey getMethodReference(MethodKey methodkey);

    StringKey getName(MethodRefKey methodrefkey);

    ProtoRefKey getPrototype(MethodKey methodkey);

    ProtoRefKey getPrototype(MethodRefKey methodrefkey);
}
