package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiMethodStubImpl extends StubBase<PsiMethod> implements PsiMethodStub {
    private String myDefaultValueText;
    private final byte myFlags;
    private final String myName;
    private final TypeInfo myReturnType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "returnType";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiMethodStubImpl";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiMethodStubImpl";
        } else {
            objArr[1] = "getReturnTypeText";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public static byte packFlags(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        byte b = z ? (byte) 1 : (byte) 0;
        if (z2) {
            b = (byte) (b | 4);
        }
        if (z3) {
            b = (byte) (b | 2);
        }
        if (z4) {
            b = (byte) (b | 8);
        }
        if (z5) {
            b = (byte) (b | 16);
        }
        return z6 ? (byte) (b | 32) : b;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiMethodStubImpl(StubElement stubElement, String str, TypeInfo typeInfo, byte b, String str2) {
        super(stubElement, isAnnotationMethod(b) ? JavaStubElementTypes.ANNOTATION_METHOD : JavaStubElementTypes.METHOD);
        if (typeInfo == null) {
            $$$reportNull$$$0(0);
        }
        this.myReturnType = typeInfo;
        this.myFlags = b;
        this.myName = str;
        this.myDefaultValueText = str2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub
    public boolean isConstructor() {
        return BitUtil.isSet(this.myFlags, 1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub
    public boolean isVarArgs() {
        return BitUtil.isSet(this.myFlags, 2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub
    public boolean isAnnotationMethod() {
        return isAnnotationMethod(this.myFlags);
    }

    public static boolean isAnnotationMethod(byte b) {
        return BitUtil.isSet(b, 4);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub
    public String getDefaultValueText() {
        return this.myDefaultValueText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub
    public TypeInfo getReturnTypeText() {
        TypeInfo typeInfo = this.myReturnType;
        if (typeInfo == null) {
            $$$reportNull$$$0(1);
        }
        return typeInfo;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean isDeprecated() {
        return BitUtil.isSet(this.myFlags, 8);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDeprecatedAnnotation() {
        return BitUtil.isSet(this.myFlags, 16);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDocComment() {
        return BitUtil.isSet(this.myFlags, 32);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001e  */
    /* JADX WARN: Code duplicated, block: B:12:0x0029  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub
    public PsiParameterStub findParameter(int i) {
        PsiParameterListStub psiParameterListStub;
        for (StubElement stubElement : getChildrenStubs()) {
            if (stubElement instanceof PsiParameterListStub) {
                psiParameterListStub = (PsiParameterListStub) stubElement;
                if (psiParameterListStub != null) {
                    return (PsiParameterStub) psiParameterListStub.getChildrenStubs().get(i);
                }
                throw new RuntimeException("No parameter(s) [yet?]");
            }
        }
        psiParameterListStub = null;
        if (psiParameterListStub != null) {
            return (PsiParameterStub) psiParameterListStub.getChildrenStubs().get(i);
        }
        throw new RuntimeException("No parameter(s) [yet?]");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return this.myName;
    }

    public byte getFlags() {
        return this.myFlags;
    }

    public void setDefaultValueText(String str) {
        this.myDefaultValueText = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sb = new StringBuilder("PsiMethodStub[");
        if (isConstructor()) {
            sb.append("cons ");
        }
        if (isAnnotationMethod()) {
            sb.append("annotation ");
        }
        if (isVarArgs()) {
            sb.append("varargs ");
        }
        if (isDeprecated() || hasDeprecatedAnnotation()) {
            sb.append("deprecated ");
        }
        sb.append(this.myName).append(":").append(this.myReturnType);
        String defaultValueText = getDefaultValueText();
        if (defaultValueText != null) {
            sb.append(" default=").append(defaultValueText);
        }
        sb.append("]");
        return sb.toString();
    }
}
