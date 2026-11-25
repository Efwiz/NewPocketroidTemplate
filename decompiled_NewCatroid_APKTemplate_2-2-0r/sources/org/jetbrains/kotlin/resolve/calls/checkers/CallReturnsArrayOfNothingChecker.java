package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CallReturnsArrayOfNothingChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u000b\u001a\u00020\f*\u0004\u0018\u00010\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CallReturnsArrayOfNothingChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "containsArrayOfNothing", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallReturnsArrayOfNothingChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (containsArrayOfNothing(resolvedCall.getResultingDescriptor().getReturnType())) {
            context.getTrace().report(Errors.UNSUPPORTED.on(reportOn, "Array<Nothing> in return type is illegal"));
        }
    }

    private final boolean containsArrayOfNothing(KotlinType kotlinType) {
        if (kotlinType == null || CallCheckerKt.isComputingDeferredType(this, kotlinType)) {
            return false;
        }
        if (!TypeUtilsKt.isArrayOfNothing(kotlinType)) {
            List<TypeProjection> arguments = kotlinType.getArguments();
            if ((arguments instanceof Collection) && arguments.isEmpty()) {
                return false;
            }
            for (TypeProjection typeProjection : arguments) {
                if (typeProjection.isStarProjection() || !containsArrayOfNothing(typeProjection.getNullableAnyType())) {
                }
            }
            return false;
        }
        return true;
    }
}
