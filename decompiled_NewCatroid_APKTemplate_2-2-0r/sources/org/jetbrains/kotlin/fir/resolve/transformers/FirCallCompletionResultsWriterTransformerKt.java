package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\n\u0010\u0005\u001a\u00020\u0002*\u00020\u0001¨\u0006\u0006"}, d2 = {"getExpectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;", "argument", "Lorg/jetbrains/kotlin/fir/FirElement;", "toExpectedType", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCallCompletionResultsWriterTransformerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ConeKotlinType getExpectedType(ExpectedArgumentType expectedArgumentType, FirElement firElement) {
        if (expectedArgumentType instanceof ExpectedArgumentType.ArgumentsMap) {
            return ((ExpectedArgumentType.ArgumentsMap) expectedArgumentType).getMap().get(firElement);
        }
        if (expectedArgumentType instanceof ExpectedArgumentType.ExpectedType) {
            return ((ExpectedArgumentType.ExpectedType) expectedArgumentType).getType();
        }
        if (Intrinsics.areEqual(expectedArgumentType, ExpectedArgumentType.NoApproximation.INSTANCE)) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ExpectedArgumentType toExpectedType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return new ExpectedArgumentType.ExpectedType(coneKotlinType);
    }
}
