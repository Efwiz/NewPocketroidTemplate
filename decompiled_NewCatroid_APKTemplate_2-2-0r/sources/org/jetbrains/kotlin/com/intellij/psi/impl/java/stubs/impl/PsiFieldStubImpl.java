package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiFieldStubImpl extends StubBase<PsiField> implements PsiFieldStub {
    private final byte myFlags;
    private final String myInitializer;
    private final String myName;
    private final TypeInfo myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiFieldStubImpl";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiFieldStubImpl";
        } else {
            objArr[1] = "getType";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public static byte packFlags(boolean z, boolean z2, boolean z3, boolean z4) {
        byte b = z ? (byte) 1 : (byte) 0;
        if (z2) {
            b = (byte) (b | 2);
        }
        if (z3) {
            b = (byte) (b | 4);
        }
        return z4 ? (byte) (b | 8) : b;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiFieldStubImpl(StubElement stubElement, String str, TypeInfo typeInfo, String str2, byte b) {
        super(stubElement, isEnumConst(b) ? JavaStubElementTypes.ENUM_CONSTANT : JavaStubElementTypes.FIELD);
        if (typeInfo == null) {
            $$$reportNull$$$0(0);
        }
        this.myName = str;
        this.myType = typeInfo;
        this.myInitializer = str2;
        this.myFlags = b;
    }

    public byte getFlags() {
        return this.myFlags;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub
    public TypeInfo getType() {
        TypeInfo typeInfo = this.myType;
        if (typeInfo == null) {
            $$$reportNull$$$0(1);
        }
        return typeInfo;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub
    public String getInitializerText() {
        return this.myInitializer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub
    public boolean isEnumConstant() {
        return isEnumConst(this.myFlags);
    }

    private static boolean isEnumConst(byte b) {
        return BitUtil.isSet(b, (byte) 1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean isDeprecated() {
        return BitUtil.isSet(this.myFlags, (byte) 2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDeprecatedAnnotation() {
        return BitUtil.isSet(this.myFlags, (byte) 4);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDocComment() {
        return BitUtil.isSet(this.myFlags, (byte) 8);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sb = new StringBuilder("PsiFieldStub[");
        if (isDeprecated() || hasDeprecatedAnnotation()) {
            sb.append("deprecated ");
        }
        if (isEnumConstant()) {
            sb.append("enumconst ");
        }
        sb.append(this.myName).append(':').append(this.myType);
        if (this.myInitializer != null) {
            sb.append('=').append(this.myInitializer);
        }
        sb.append(']');
        return sb.toString();
    }
}
