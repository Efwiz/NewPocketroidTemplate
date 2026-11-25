package org.jf.dexlib2.analysis;

import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
import org.jf.dexlib2.util.TypeUtils;
import org.jf.util.ExceptionWithContext;
import org.jf.util.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ArrayProto implements TypeProto {
    private static final String BRACKETS = StringUtils.repeat("[", 256);
    protected final ClassPath classPath;
    protected final int dimensions;
    protected final String elementType;

    @Override // org.jf.dexlib2.analysis.TypeProto
    public boolean isInterface() {
        return false;
    }

    public ArrayProto(ClassPath classPath, String str) {
        this.classPath = classPath;
        int i = 0;
        while (str.charAt(i) == '[') {
            i++;
            if (i == str.length()) {
                throw new ExceptionWithContext("Invalid array type: %s", str);
            }
        }
        if (i == 0) {
            throw new ExceptionWithContext("Invalid array type: %s", str);
        }
        this.dimensions = i;
        this.elementType = str.substring(i);
    }

    public String toString() {
        return getType();
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public ClassPath getClassPath() {
        return this.classPath;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getType() {
        return makeArrayType(this.elementType, this.dimensions);
    }

    public int getDimensions() {
        return this.dimensions;
    }

    public String getElementType() {
        return this.elementType;
    }

    public String getImmediateElementType() {
        int i = this.dimensions;
        if (i > 1) {
            return makeArrayType(this.elementType, i - 1);
        }
        return this.elementType;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public boolean implementsInterface(String str) {
        return str.equals("Ljava/lang/Cloneable;") || str.equals("Ljava/io/Serializable;");
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public String getSuperclass() {
        return TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public TypeProto getCommonSuperclass(TypeProto typeProto) {
        if (typeProto instanceof ArrayProto) {
            if (!TypeUtils.isPrimitiveType(getElementType())) {
                ArrayProto arrayProto = (ArrayProto) typeProto;
                if (!TypeUtils.isPrimitiveType(arrayProto.getElementType())) {
                    int i = this.dimensions;
                    int i2 = arrayProto.dimensions;
                    if (i == i2) {
                        TypeProto typeProto2 = this.classPath.getClass(this.elementType);
                        TypeProto typeProto3 = this.classPath.getClass(arrayProto.elementType);
                        TypeProto commonSuperclass = typeProto2.getCommonSuperclass(typeProto3);
                        if (typeProto2 == commonSuperclass) {
                            return this;
                        }
                        return typeProto3 == commonSuperclass ? typeProto : this.classPath.getClass(makeArrayType(commonSuperclass.getType(), this.dimensions));
                    }
                    return this.classPath.getClass(makeArrayType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, Math.min(i, i2)));
                }
            }
            ArrayProto arrayProto2 = (ArrayProto) typeProto;
            return (this.dimensions == arrayProto2.dimensions && getElementType().equals(arrayProto2.getElementType())) ? this : this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
        }
        if (typeProto instanceof ClassProto) {
            try {
                if (typeProto.isInterface() && implementsInterface(typeProto.getType())) {
                    return typeProto;
                }
            } catch (UnresolvedClassException unused) {
            }
            return this.classPath.getClass(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
        }
        return typeProto.getCommonSuperclass(this);
    }

    private static String makeArrayType(String str, int i) {
        return BRACKETS.substring(0, i) + str;
    }

    @Override // org.jf.dexlib2.analysis.TypeProto
    public FieldReference getFieldByOffset(int i) {
        if (i == 8) {
            return new ImmutableFieldReference(getType(), "length", "int");
        }
        return null;
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
