package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiRecordComponentStubImpl extends StubBase<PsiRecordComponent> implements PsiRecordComponentStub {
    private final byte myFlags;
    private final String myName;
    private final TypeInfo myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiRecordComponentStubImpl";
        } else {
            objArr[0] = ModuleXmlParser.TYPE;
        }
        if (i == 1) {
            objArr[1] = "getType";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiRecordComponentStubImpl";
        } else {
            objArr[1] = "getName";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDocComment() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean isDeprecated() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRecordComponentStubImpl(StubElement stubElement, String str, TypeInfo typeInfo, byte b) {
        super(stubElement, JavaStubElementTypes.RECORD_COMPONENT);
        if (typeInfo == null) {
            $$$reportNull$$$0(0);
        }
        this.myName = str;
        this.myType = typeInfo;
        this.myFlags = b;
    }

    public PsiRecordComponentStubImpl(StubElement stubElement, String str, TypeInfo typeInfo, boolean z, boolean z2) {
        this(stubElement, str, typeInfo, packFlags(z, z2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub
    public TypeInfo getType() {
        TypeInfo typeInfo = this.myType;
        if (typeInfo == null) {
            $$$reportNull$$$0(1);
        }
        return typeInfo;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub, org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDeprecatedAnnotation() {
        return BitUtil.isSet(this.myFlags, (byte) 2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub
    public boolean isVararg() {
        return BitUtil.isSet(this.myFlags, (byte) 1);
    }

    public byte getFlags() {
        return this.myFlags;
    }

    public static byte packFlags(boolean z, boolean z2) {
        return BitUtil.set(BitUtil.set((byte) 0, (byte) 1, z), (byte) 2, z2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sb = new StringBuilder("PsiRecordComponentStub[");
        if (hasDeprecatedAnnotation()) {
            sb.append("deprecated ");
        }
        sb.append(this.myName).append(':').append(this.myType);
        sb.append(']');
        return sb.toString();
    }
}
