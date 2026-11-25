package org.jetbrains.kotlin.resolve.calls.context;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;

/* JADX INFO: compiled from: CallPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition;", "", "()V", "CallableReferenceRhs", "ExtensionReceiverPosition", "PropertyAssignment", "Unknown", "ValueArgumentPosition", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$CallableReferenceRhs;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$ExtensionReceiverPosition;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$PropertyAssignment;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$Unknown;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$ValueArgumentPosition;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallPosition {
    public /* synthetic */ CallPosition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: CallPosition.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$Unknown;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Unknown extends CallPosition {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
            super(null);
        }
    }

    private CallPosition() {
    }

    /* JADX INFO: compiled from: CallPosition.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$ExtensionReceiverPosition;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExtensionReceiverPosition extends CallPosition {
        private final ResolvedCall<?> resolvedCall;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ExtensionReceiverPosition(ResolvedCall<?> resolvedCall) {
            super(null);
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.resolvedCall = resolvedCall;
        }

        public final ResolvedCall<?> getResolvedCall() {
            return this.resolvedCall;
        }
    }

    /* JADX INFO: compiled from: CallPosition.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B!\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$ValueArgumentPosition;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;Lorg/jetbrains/kotlin/psi/ValueArgument;)V", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "getValueParameter", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ValueArgumentPosition extends CallPosition {
        private final ResolvedCall<?> resolvedCall;
        private final ValueArgument valueArgument;
        private final ValueParameterDescriptor valueParameter;

        public final ResolvedCall<?> getResolvedCall() {
            return this.resolvedCall;
        }

        public final ValueParameterDescriptor getValueParameter() {
            return this.valueParameter;
        }

        public final ValueArgument getValueArgument() {
            return this.valueArgument;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueArgumentPosition(ResolvedCall<?> resolvedCall, ValueParameterDescriptor valueParameter, ValueArgument valueArgument) {
            super(null);
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
            Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
            this.resolvedCall = resolvedCall;
            this.valueParameter = valueParameter;
            this.valueArgument = valueArgument;
        }
    }

    /* JADX INFO: compiled from: CallPosition.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$PropertyAssignment;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition;", "leftPart", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isLeft", "", "(Lorg/jetbrains/kotlin/psi/KtExpression;Z)V", "()Z", "getLeftPart", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyAssignment extends CallPosition {
        private final boolean isLeft;
        private final KtExpression leftPart;

        public PropertyAssignment(KtExpression ktExpression, boolean z) {
            super(null);
            this.leftPart = ktExpression;
            this.isLeft = z;
        }

        public final KtExpression getLeftPart() {
            return this.leftPart;
        }

        /* JADX INFO: renamed from: isLeft, reason: from getter */
        public final boolean getIsLeft() {
            return this.isLeft;
        }
    }

    /* JADX INFO: compiled from: CallPosition.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition$CallableReferenceRhs;", "Lorg/jetbrains/kotlin/resolve/calls/context/CallPosition;", "lhs", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "(Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;)V", "getLhs", "()Lorg/jetbrains/kotlin/types/expressions/DoubleColonLHS;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CallableReferenceRhs extends CallPosition {
        private final DoubleColonLHS lhs;

        public CallableReferenceRhs(DoubleColonLHS doubleColonLHS) {
            super(null);
            this.lhs = doubleColonLHS;
        }

        public final DoubleColonLHS getLhs() {
            return this.lhs;
        }
    }
}
