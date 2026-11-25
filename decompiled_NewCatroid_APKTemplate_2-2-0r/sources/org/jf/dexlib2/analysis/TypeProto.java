package org.jf.dexlib2.analysis;

import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;

/* JADX INFO: loaded from: classes6.dex */
public interface TypeProto {
    int findMethodIndexInVtable(MethodReference methodReference);

    ClassPath getClassPath();

    TypeProto getCommonSuperclass(TypeProto typeProto);

    FieldReference getFieldByOffset(int i);

    Method getMethodByVtableIndex(int i);

    String getSuperclass();

    String getType();

    boolean implementsInterface(String str);

    boolean isInterface();
}
