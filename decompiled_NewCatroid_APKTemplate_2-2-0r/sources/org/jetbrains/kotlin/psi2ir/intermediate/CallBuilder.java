package org.jetbrains.kotlin.psi2ir.intermediate;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CallBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B9\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010(\u001a\u0004\u0018\u00010\u00172\u0006\u0010)\u001a\u00020*R\u001a\u0010\r\u001a\u00020\u000eX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0016¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u001bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001f\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/CallBuilder;", "", "original", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "typeArguments", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "isExtensionInvokeCall", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Ljava/util/Map;Z)V", "callReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "getCallReceiver", "()Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "setCallReceiver", "(Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "irValueArgumentsByIndex", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getIrValueArgumentsByIndex", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()Z", "getOriginal", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "parametersOffset", "", "superQualifier", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getSuperQualifier", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "setSuperQualifier", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "getTypeArguments", "()Ljava/util/Map;", "getValueArgument", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallBuilder {
    public CallReceiver callReceiver;
    private final CallableDescriptor descriptor;
    private final IrExpression[] irValueArgumentsByIndex;
    private final boolean isExtensionInvokeCall;
    private final ResolvedCall<?> original;
    private final int parametersOffset;
    private ClassDescriptor superQualifier;
    private final Map<TypeParameterDescriptor, KotlinType> typeArguments;

    /* JADX WARN: Multi-variable type inference failed */
    public CallBuilder(ResolvedCall<?> original, CallableDescriptor descriptor, Map<TypeParameterDescriptor, ? extends KotlinType> map, boolean z) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.original = original;
        this.descriptor = descriptor;
        this.typeArguments = map;
        this.isExtensionInvokeCall = z;
        this.parametersOffset = z ? 1 : 0;
        this.irValueArgumentsByIndex = new IrExpression[descriptor.getValueParameters().size()];
    }

    public /* synthetic */ CallBuilder(ResolvedCall resolvedCall, CallableDescriptor callableDescriptor, Map map, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolvedCall, callableDescriptor, map, (i & 8) != 0 ? false : z);
    }

    public final ResolvedCall<?> getOriginal() {
        return this.original;
    }

    public final CallableDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: isExtensionInvokeCall, reason: from getter */
    public final boolean getIsExtensionInvokeCall() {
        return this.isExtensionInvokeCall;
    }

    public final ClassDescriptor getSuperQualifier() {
        return this.superQualifier;
    }

    public final void setSuperQualifier(ClassDescriptor classDescriptor) {
        this.superQualifier = classDescriptor;
    }

    public final CallReceiver getCallReceiver() {
        CallReceiver callReceiver = this.callReceiver;
        if (callReceiver != null) {
            return callReceiver;
        }
        Intrinsics.throwUninitializedPropertyAccessException("callReceiver");
        return null;
    }

    public final void setCallReceiver(CallReceiver callReceiver) {
        Intrinsics.checkNotNullParameter(callReceiver, "<set-?>");
        this.callReceiver = callReceiver;
    }

    public final IrExpression[] getIrValueArgumentsByIndex() {
        return this.irValueArgumentsByIndex;
    }

    public final IrExpression getValueArgument(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "valueParameterDescriptor");
        return this.irValueArgumentsByIndex[valueParameterDescriptor.getIndex() + this.parametersOffset];
    }
}
