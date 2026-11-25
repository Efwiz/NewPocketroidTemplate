package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.stubs.KotlinParameterStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtParameterElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinParameterStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003BO\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\r\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinParameterStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtParameter;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinParameterStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "fqName", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "name", "isMutable", "", "hasValOrVar", "hasDefaultValue", "functionTypeParameterName", "", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;Lcom/intellij/util/io/StringRef;ZZZLjava/lang/String;)V", "getFunctionTypeParameterName", "()Ljava/lang/String;", "getFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getName", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinParameterStubImpl extends KotlinStubBaseImpl<KtParameter> implements KotlinParameterStub {
    private final StringRef fqName;
    private final String functionTypeParameterName;
    private final boolean hasDefaultValue;
    private final boolean hasValOrVar;
    private final boolean isMutable;
    private final StringRef name;

    public /* synthetic */ KotlinParameterStubImpl(StubElement stubElement, StringRef stringRef, StringRef stringRef2, boolean z, boolean z2, boolean z3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(stubElement, stringRef, stringRef2, z, z2, z3, (i & 64) != 0 ? null : str);
    }

    public final String getFunctionTypeParameterName() {
        return this.functionTypeParameterName;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinParameterStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, StringRef stringRef2, boolean z, boolean z2, boolean z3, String str) {
        KtParameterElementType VALUE_PARAMETER = KtStubElementTypes.VALUE_PARAMETER;
        Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER, "VALUE_PARAMETER");
        super(stubElement, VALUE_PARAMETER);
        this.fqName = stringRef;
        this.name = stringRef2;
        this.isMutable = z;
        this.hasValOrVar = z2;
        this.hasDefaultValue = z3;
        this.functionTypeParameterName = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.name);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        if (this.fqName != null) {
            return new FqName(this.fqName.getString());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinParameterStub
    /* JADX INFO: renamed from: isMutable, reason: from getter */
    public boolean getIsMutable() {
        return this.isMutable;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinParameterStub
    /* JADX INFO: renamed from: hasValOrVar, reason: from getter */
    public boolean getHasValOrVar() {
        return this.hasValOrVar;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinParameterStub
    /* JADX INFO: renamed from: hasDefaultValue, reason: from getter */
    public boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }
}
