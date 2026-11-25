package org.jetbrains.kotlin.resolve.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtPureElement;

/* JADX INFO: compiled from: KotlinSourceElement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003"}, d2 = {"toSourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinSourceElementKt {
    public static final SourceElement toSourceElement(KtPureElement ktPureElement) {
        if (ktPureElement == null) {
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            return NO_SOURCE;
        }
        KtElement psiOrParent = ktPureElement.getPsiOrParent();
        Intrinsics.checkNotNullExpressionValue(psiOrParent, "getPsiOrParent(...)");
        return new KotlinSourceElement(psiOrParent);
    }
}
