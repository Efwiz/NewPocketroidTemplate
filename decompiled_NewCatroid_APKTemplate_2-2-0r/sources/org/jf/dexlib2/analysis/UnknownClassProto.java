package org.jf.dexlib2.analysis;

import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;

/* JADX INFO: loaded from: classes6.dex */
public class UnknownClassProto implements TypeProto {
    protected final ClassPath classPath;

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

    public UnknownClassProto(ClassPath classPath) {
        this.classPath = classPath;
    }

    public String toString() {
        return "Ujava/lang/Object;";
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public ClassPath getClassPath() {
        return this.classPath;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public TypeProto getCommonSuperclass(TypeProto typeProto) {
        if (typeProto.getType().equals(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR)) {
            return typeProto;
        }
        return typeProto instanceof ArrayProto ? this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR) : this;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getType() {
        return "Ujava/lang/Object;";
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public FieldReference getFieldByOffset(int i) {
        return this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR).getFieldByOffset(i);
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public Method getMethodByVtableIndex(int i) {
        return this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR).getMethodByVtableIndex(i);
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public int findMethodIndexInVtable(MethodReference methodReference) {
        return this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR).findMethodIndexInVtable(methodReference);
    }
}
