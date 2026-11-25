package org.jetbrains.kotlin.cfg.pseudocode.instructions.eval;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: accessInstructions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "", "()V", "BlackBox", "Call", "Declaration", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget$BlackBox;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget$Call;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget$Declaration;", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AccessTarget {
    public /* synthetic */ AccessTarget(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: accessInstructions.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget$Declaration;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Declaration extends AccessTarget {
        private final VariableDescriptor descriptor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Declaration(VariableDescriptor descriptor) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
        }

        public final VariableDescriptor getDescriptor() {
            return this.descriptor;
        }

        public boolean equals(Object other) {
            return (other instanceof Declaration) && Intrinsics.areEqual(this.descriptor, ((Declaration) other).descriptor);
        }

        public int hashCode() {
            return this.descriptor.hashCode();
        }
    }

    private AccessTarget() {
    }

    /* JADX INFO: compiled from: accessInstructions.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget$Call;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Call extends AccessTarget {
        private final ResolvedCall<?> resolvedCall;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Call(ResolvedCall<?> resolvedCall) {
            super(null);
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            this.resolvedCall = resolvedCall;
        }

        public final ResolvedCall<?> getResolvedCall() {
            return this.resolvedCall;
        }

        public boolean equals(Object other) {
            return (other instanceof Call) && Intrinsics.areEqual(this.resolvedCall, ((Call) other).resolvedCall);
        }

        public int hashCode() {
            return this.resolvedCall.hashCode();
        }
    }

    /* JADX INFO: compiled from: accessInstructions.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget$BlackBox;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/AccessTarget;", "()V", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BlackBox extends AccessTarget {
        public static final BlackBox INSTANCE = new BlackBox();

        private BlackBox() {
            super(null);
        }
    }
}
