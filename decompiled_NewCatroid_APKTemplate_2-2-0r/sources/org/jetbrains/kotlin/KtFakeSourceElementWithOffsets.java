package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0007H\u0016R\u0014\u0010\b\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementWithOffsets;", "Lorg/jetbrains/kotlin/KtFakeSourceElement;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "startOffset", "", "endOffset", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/KtFakeSourceElementKind;II)V", "getEndOffset", "()I", "getStartOffset", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KtFakeSourceElementWithOffsets extends KtFakeSourceElement {
    private final int endOffset;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.KtPsiSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.KtPsiSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public int getEndOffset() {
        return this.endOffset;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFakeSourceElementWithOffsets(PsiElement psi, KtFakeSourceElementKind kind, int i, int i2) {
        super(psi, kind);
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.startOffset = i;
        this.endOffset = i2;
    }

    @Override // org.jetbrains.kotlin.KtFakeSourceElement, org.jetbrains.kotlin.KtPsiSourceElement, org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KtFakeSourceElementWithOffsets) || !super.equals(other)) {
            return false;
        }
        KtFakeSourceElementWithOffsets ktFakeSourceElementWithOffsets = (KtFakeSourceElementWithOffsets) other;
        return Intrinsics.areEqual(getKind(), ktFakeSourceElementWithOffsets.getKind()) && getStartOffset() == ktFakeSourceElementWithOffsets.getStartOffset() && getEndOffset() == ktFakeSourceElementWithOffsets.getEndOffset();
    }

    @Override // org.jetbrains.kotlin.KtFakeSourceElement, org.jetbrains.kotlin.KtPsiSourceElement, org.jetbrains.kotlin.KtSourceElement, org.jetbrains.kotlin.AbstractKtSourceElement
    public int hashCode() {
        return (((((super.hashCode() * 31) + getKind().hashCode()) * 31) + getStartOffset()) * 31) + getEndOffset();
    }
}
