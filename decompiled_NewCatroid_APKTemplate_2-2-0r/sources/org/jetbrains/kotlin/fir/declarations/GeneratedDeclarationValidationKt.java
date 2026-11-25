package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.Constants;
import org.jetbrains.kotlin.fir.FirElement;

/* JADX INFO: compiled from: GeneratedDeclarationValidation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {Constants.DOM_VALIDATE, "", "Lorg/jetbrains/kotlin/fir/FirElement;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GeneratedDeclarationValidationKt {
    public static final void validate(FirElement firElement) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        firElement.accept(FirGeneratedElementsValidator.INSTANCE, null);
    }
}
