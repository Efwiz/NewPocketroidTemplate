package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/KtRealPsiSourceElement;", "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/KtSourceElementKind;", "getKind", "()Lorg/jetbrains/kotlin/KtSourceElementKind;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtRealPsiSourceElement extends KtPsiSourceElement {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtRealPsiSourceElement(PsiElement psi) {
        super(psi, null);
        Intrinsics.checkNotNullParameter(psi, "psi");
    }

    @Override // org.jetbrains.kotlin.KtSourceElement
    public KtSourceElementKind getKind() {
        return KtRealSourceElementKind.INSTANCE;
    }
}
