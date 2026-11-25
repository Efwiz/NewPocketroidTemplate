package org.eclipse.jdt.internal.compiler;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.env.IBinaryNestedType;

/* JADX INFO: loaded from: classes14.dex */
public final class ExtraFlags {
    public static final int HasNonPrivateStaticMemberTypes = 1;
    public static final int IsLocalType = 4;
    public static final int IsMemberType = 2;
    public static final int ParameterTypesStoredAsSignature = 16;

    public static int getExtraFlags(ClassFileReader classFileReader) {
        int i = classFileReader.isNestedType() ? 2 : 0;
        if (classFileReader.isLocal()) {
            i |= 4;
        }
        IBinaryNestedType[] memberTypes = classFileReader.getMemberTypes();
        int length = memberTypes == null ? 0 : memberTypes.length;
        if (length <= 0) {
            return i;
        }
        for (int i2 = 0; i2 < length; i2++) {
            int modifiers = memberTypes[i2].getModifiers();
            if ((modifiers & 8) != 0 && (modifiers & 2) == 0) {
                return i | 1;
            }
        }
        return i;
    }

    public static int getExtraFlags(IType iType) throws JavaModelException {
        int i = iType.isMember() ? 2 : 0;
        if (iType.isLocal()) {
            i |= 4;
        }
        IType[] types = iType.getTypes();
        int length = types == null ? 0 : types.length;
        if (length <= 0) {
            return i;
        }
        for (int i2 = 0; i2 < length; i2++) {
            int flags = types[i2].getFlags();
            if ((flags & 8) != 0 && (flags & 2) == 0) {
                return i | 1;
            }
        }
        return i;
    }

    public static int getExtraFlags(TypeDeclaration typeDeclaration) {
        int i = typeDeclaration.enclosingType != null ? 2 : 0;
        TypeDeclaration[] typeDeclarationArr = typeDeclaration.memberTypes;
        int length = typeDeclarationArr == null ? 0 : typeDeclarationArr.length;
        if (length <= 0) {
            return i;
        }
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = typeDeclarationArr[i2].modifiers;
            if ((i3 & 8) != 0 && (i3 & 2) == 0) {
                return i | 1;
            }
        }
        return i;
    }
}
