package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: ErrorExpressionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ErrorExpressionException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "cause", "", "(Lorg/jetbrains/kotlin/psi/KtElement;Ljava/lang/Throwable;)V", "getKtElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorExpressionException extends RuntimeException {
    private final KtElement ktElement;

    public final KtElement getKtElement() {
        return this.ktElement;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ErrorExpressionException(KtElement ktElement, Throwable th) {
        super((th != null ? th.getMessage() : null) + ": " + ktElement.getClass().getSimpleName() + ":\n" + ktElement.getText(), th);
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        this.ktElement = ktElement;
    }
}
