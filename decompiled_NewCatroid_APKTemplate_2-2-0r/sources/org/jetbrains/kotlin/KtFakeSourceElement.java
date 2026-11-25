package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElement;", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/KtFakeSourceElementKind;)V", "getKind", "()Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtFakeSourceElement extends KtPsiSourceElement {
    private final KtFakeSourceElementKind kind;

    @Override // org.jetbrains.kotlin.KtSourceElement
    public KtFakeSourceElementKind getKind() {
        return this.kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFakeSourceElement(PsiElement psi, KtFakeSourceElementKind kind) {
        super(psi, null);
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
    }

    @Override // org.jetbrains.kotlin.KtPsiSourceElement, org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null) || !super.equals(other)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.KtFakeSourceElement");
        return Intrinsics.areEqual(getKind(), ((KtFakeSourceElement) other).getKind());
    }

    @Override // org.jetbrains.kotlin.KtPsiSourceElement, org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public int hashCode() {
        return (super.hashCode() * 31) + getKind().hashCode();
    }
}
