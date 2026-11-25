package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.stubs.KotlinUserTypeStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtUserTypeElementType;

/* JADX INFO: compiled from: KotlinUserTypeStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B#\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinUserTypeStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtUserType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinUserTypeStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "upperBound", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;)V", "getUpperBound", "()Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinUserTypeStubImpl extends KotlinStubBaseImpl<KtUserType> implements KotlinUserTypeStub {
    private final KotlinTypeBean upperBound;

    public /* synthetic */ KotlinUserTypeStubImpl(StubElement stubElement, KotlinTypeBean kotlinTypeBean, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(stubElement, (i & 2) != 0 ? null : kotlinTypeBean);
    }

    public final KotlinTypeBean getUpperBound() {
        return this.upperBound;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KotlinUserTypeStubImpl(StubElement<? extends PsiElement> stubElement, KotlinTypeBean kotlinTypeBean) {
        KtUserTypeElementType USER_TYPE = KtStubElementTypes.USER_TYPE;
        Intrinsics.checkNotNullExpressionValue(USER_TYPE, "USER_TYPE");
        super(stubElement, USER_TYPE);
        this.upperBound = kotlinTypeBean;
    }
}
