package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AdditionalTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016J,\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u0012H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "", "checkReceiver", "", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "safeAccess", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/CallResolutionContext;", "checkType", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionTypeWithSmartCast", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AdditionalTypeChecker {
    default void checkReceiver(ReceiverParameterDescriptor receiverParameter, ReceiverValue receiverArgument, boolean safeAccess, CallResolutionContext<?> c) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
        Intrinsics.checkNotNullParameter(c, "c");
    }

    void checkType(KtExpression expression, KotlinType expressionType, KotlinType expressionTypeWithSmartCast, ResolutionContext<?> c);

    /* JADX INFO: compiled from: AdditionalTypeChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static void checkReceiver(AdditionalTypeChecker additionalTypeChecker, ReceiverParameterDescriptor receiverParameter, ReceiverValue receiverArgument, boolean z, CallResolutionContext<?> c) {
            Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
            Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
            Intrinsics.checkNotNullParameter(c, "c");
            AdditionalTypeChecker.super.checkReceiver(receiverParameter, receiverArgument, z, c);
        }
    }
}
