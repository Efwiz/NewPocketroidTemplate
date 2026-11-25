package org.jf.dexlib2.analysis;

import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class PrimitiveProto implements TypeProto {
    protected final ClassPath classPath;
    protected final String type;

    @Override // org.jf.dexlib2.analysis.TypeProto
    public int findMethodIndexInVtable(MethodReference methodReference) {
        return -1;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public FieldReference getFieldByOffset(int i) {
        return null;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public Method getMethodByVtableIndex(int i) {
        return null;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getSuperclass() {
        return null;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public boolean implementsInterface(String str) {
        return false;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public boolean isInterface() {
        return false;
    }

    public PrimitiveProto(ClassPath classPath, String str) {
        this.classPath = classPath;
        this.type = str;
    }

    public String toString() {
        return this.type;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public ClassPath getClassPath() {
        return this.classPath;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public TypeProto getCommonSuperclass(TypeProto typeProto) {
        throw new ExceptionWithContext("Cannot call getCommonSuperclass on PrimitiveProto", new Object[0]);
    }
}
