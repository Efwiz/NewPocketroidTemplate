package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeParameterStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTypeParameterElementType;

/* JADX INFO: compiled from: KotlinTypeParameterStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B1\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fJ\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010H\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeParameterStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinTypeParameterStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "name", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "isInVariance", "", "isOutVariance", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;ZZ)V", "getFqName", "", "getName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeParameterStubImpl extends KotlinStubBaseImpl<KtTypeParameter> implements KotlinTypeParameterStub {
    private final boolean isInVariance;
    private final boolean isOutVariance;
    private final StringRef name;

    public Void getFqName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    /* JADX INFO: renamed from: getFqName */
    public /* bridge */ /* synthetic */ FqName mo7402getFqName() {
        return (FqName) getFqName();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinTypeParameterStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, boolean z, boolean z2) {
        KtTypeParameterElementType TYPE_PARAMETER = KtStubElementTypes.TYPE_PARAMETER;
        Intrinsics.checkNotNullExpressionValue(TYPE_PARAMETER, "TYPE_PARAMETER");
        super(stubElement, TYPE_PARAMETER);
        this.name = stringRef;
        this.isInVariance = z;
        this.isOutVariance = z2;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinTypeParameterStub
    /* JADX INFO: renamed from: isInVariance, reason: from getter */
    public boolean getIsInVariance() {
        return this.isInVariance;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinTypeParameterStub
    /* JADX INFO: renamed from: isOutVariance, reason: from getter */
    public boolean getIsOutVariance() {
        return this.isOutVariance;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.name);
    }
}
