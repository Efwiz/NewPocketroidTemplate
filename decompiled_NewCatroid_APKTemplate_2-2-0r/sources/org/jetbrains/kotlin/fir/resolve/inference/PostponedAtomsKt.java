package org.jetbrains.kotlin.fir.resolve.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;

/* JADX INFO: compiled from: PostponedAtoms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006\u0006"}, d2 = {"extractInputOutputTypesFromCallableReferenceExpectedType", "Lorg/jetbrains/kotlin/fir/resolve/inference/InputOutputTypes;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PostponedAtomsKt {
    public static final InputOutputTypes extractInputOutputTypesFromCallableReferenceExpectedType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        if (coneKotlinType != null && FunctionalTypeUtilsKt.isSomeFunctionType(coneKotlinType, session)) {
            return new InputOutputTypes(FunctionalTypeUtilsKt.valueParameterTypesIncludingReceiver(coneKotlinType, session), FunctionalTypeUtilsKt.returnType(coneKotlinType, session));
        }
        return null;
    }
}
