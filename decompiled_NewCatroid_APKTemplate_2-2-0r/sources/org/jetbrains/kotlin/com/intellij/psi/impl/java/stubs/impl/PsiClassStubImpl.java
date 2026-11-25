package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiClassStubImpl<T extends PsiClass> extends StubBase<T> implements PsiClassStub<T> {
    private final String myBaseRefText;
    private final short myFlags;
    private final String myName;
    private final String myQualifiedName;
    private String mySourceFileName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ModuleXmlParser.TYPE, "org/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiClassStubImpl", "<init>"));
    }

    public static short packFlags(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12) {
        short s = z ? (short) 1 : (short) 0;
        if (z2) {
            s = (short) (s | 2);
        }
        if (z3) {
            s = (short) (s | 4);
        }
        if (z4) {
            s = (short) (s | 8);
        }
        if (z5) {
            s = (short) (s | 16);
        }
        if (z6) {
            s = (short) (s | 32);
        }
        if (z7) {
            s = (short) (s | 64);
        }
        if (z8) {
            s = (short) (s | 128);
        }
        if (z9) {
            s = (short) (s | 256);
        }
        if (z10) {
            s = (short) (s | XSSimpleTypeDefinition.FACET_TOTALDIGITS);
        }
        if (z11) {
            s = (short) (s | 1024);
        }
        return z12 ? (short) (s | 2048) : s;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiClassStubImpl(JavaClassElementType javaClassElementType, StubElement stubElement, String str, String str2, String str3, short s) {
        super(stubElement, javaClassElementType);
        if (javaClassElementType == null) {
            $$$reportNull$$$0(0);
        }
        this.myQualifiedName = str;
        this.myName = str2;
        this.myBaseRefText = str3;
        this.myFlags = s;
        if (StubBasedPsiElementBase.ourTraceStubAstBinding) {
            putUserData(StubBasedPsiElementBase.CREATION_TRACE, "Stub creation thread: " + Thread.currentThread() + "\n" + DebugUtil.currentStackTrace());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public String getQualifiedName() {
        return this.myQualifiedName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public String getBaseClassReferenceText() {
        return this.myBaseRefText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean isDeprecated() {
        return BitUtil.isSet(this.myFlags, 1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDeprecatedAnnotation() {
        return BitUtil.isSet(this.myFlags, 128);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isInterface() {
        return BitUtil.isSet(this.myFlags, 2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isEnum() {
        return BitUtil.isSet(this.myFlags, 4);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isRecord() {
        return BitUtil.isSet(this.myFlags, 2048);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isEnumConstantInitializer() {
        return isEnumConstInitializer(this.myFlags);
    }

    public static boolean isEnumConstInitializer(short s) {
        return BitUtil.isSet(s, 8);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isAnonymous() {
        return isAnonymous(this.myFlags);
    }

    public static boolean isAnonymous(short s) {
        return BitUtil.isSet(s, 16);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isAnnotationType() {
        return BitUtil.isSet(this.myFlags, 32);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub
    public boolean hasDocComment() {
        return BitUtil.isSet(this.myFlags, 1024);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public String getSourceFileName() {
        return this.mySourceFileName;
    }

    public void setSourceFileName(String str) {
        this.mySourceFileName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub
    public boolean isAnonymousInQualifiedNew() {
        return BitUtil.isSet(this.myFlags, 64);
    }

    public short getFlags() {
        return this.myFlags;
    }

    public static short packFlags(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11) {
        return packFlags(z, z2, z3, z4, z5, z6, z7, z8, z9, z10, z11, false);
    }

    public boolean isAnonymousInner() {
        return BitUtil.isSet(this.myFlags, 256);
    }

    public boolean isLocalClassInner() {
        return BitUtil.isSet(this.myFlags, 512);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sb = new StringBuilder("PsiClassStub[");
        if (isInterface()) {
            sb.append("interface ");
        }
        if (isAnonymous()) {
            sb.append("anonymous ");
        }
        if (isEnum()) {
            sb.append("enum ");
        }
        if (isRecord()) {
            sb.append("record ");
        }
        if (isAnnotationType()) {
            sb.append("annotation ");
        }
        if (isEnumConstantInitializer()) {
            sb.append("enumInit ");
        }
        if (isDeprecated()) {
            sb.append("deprecated ");
        }
        if (hasDeprecatedAnnotation()) {
            sb.append("deprecatedA ");
        }
        sb.append("name=").append(getName()).append(" fqn=").append(getQualifiedName());
        if (getBaseClassReferenceText() != null) {
            sb.append(" baseref=").append(getBaseClassReferenceText());
        }
        if (isAnonymousInQualifiedNew()) {
            sb.append(" inqualifnew");
        }
        if (isAnonymousInner()) {
            sb.append(" jvmAnonymousInner");
        }
        if (isLocalClassInner()) {
            sb.append(" jvmLocalClassInner");
        }
        sb.append("]");
        return sb.toString();
    }
}
