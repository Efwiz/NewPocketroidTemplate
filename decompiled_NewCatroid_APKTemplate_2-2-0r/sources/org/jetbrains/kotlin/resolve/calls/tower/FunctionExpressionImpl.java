package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.FunctionExpression;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0011\u0018\u00002\u00020\u00012\u00020\u0002Bk\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0012\u0012\u000e\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0012\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0012X\u0096\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0017R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001e\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0012X\u0096\u0004¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u001f\u0010\u0019R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010!¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/FunctionExpressionImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/FunctionExpression;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIFunctionKotlinCallArgument;", "outerCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "containingBlockForFunction", "Lorg/jetbrains/kotlin/psi/KtExpression;", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "receiverType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "contextReceiversTypes", "", "parametersTypes", "returnType", "(Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/psi/KtNamedFunction;Lorg/jetbrains/kotlin/types/UnwrappedType;[Lorg/jetbrains/kotlin/types/UnwrappedType;[Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getContainingBlockForFunction", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getContextReceiversTypes", "()[Lorg/jetbrains/kotlin/types/UnwrappedType;", "[Lorg/jetbrains/kotlin/types/UnwrappedType;", "expression", "getExpression", "getKtFunction", "()Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getParametersTypes", "getReceiverType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getReturnType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionExpressionImpl extends PSIFunctionKotlinCallArgument implements FunctionExpression {
    private final KtExpression containingBlockForFunction;
    private final UnwrappedType[] contextReceiversTypes;
    private final KtNamedFunction ktFunction;
    private final UnwrappedType[] parametersTypes;
    private final UnwrappedType receiverType;
    private final UnwrappedType returnType;

    public final KtExpression getContainingBlockForFunction() {
        return this.containingBlockForFunction;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIFunctionKotlinCallArgument
    public KtNamedFunction getKtFunction() {
        return this.ktFunction;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.FunctionExpression
    public UnwrappedType getReceiverType() {
        return this.receiverType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.FunctionExpression
    public UnwrappedType[] getContextReceiversTypes() {
        return this.contextReceiversTypes;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    public UnwrappedType[] getParametersTypes() {
        return this.parametersTypes;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.FunctionExpression
    public UnwrappedType getReturnType() {
        return this.returnType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionExpressionImpl(BasicCallResolutionContext outerCallContext, ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, Name name2, KtExpression containingBlockForFunction, KtNamedFunction ktFunction, UnwrappedType unwrappedType, UnwrappedType[] contextReceiversTypes, UnwrappedType[] parametersTypes, UnwrappedType unwrappedType2) {
        super(outerCallContext, valueArgument, dataFlowInfoBeforeThisArgument, name2);
        Intrinsics.checkNotNullParameter(outerCallContext, "outerCallContext");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(containingBlockForFunction, "containingBlockForFunction");
        Intrinsics.checkNotNullParameter(ktFunction, "ktFunction");
        Intrinsics.checkNotNullParameter(contextReceiversTypes, "contextReceiversTypes");
        Intrinsics.checkNotNullParameter(parametersTypes, "parametersTypes");
        this.containingBlockForFunction = containingBlockForFunction;
        this.ktFunction = ktFunction;
        this.receiverType = unwrappedType;
        this.contextReceiversTypes = contextReceiversTypes;
        this.parametersTypes = parametersTypes;
        this.returnType = unwrappedType2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIFunctionKotlinCallArgument
    public KtExpression getExpression() {
        return this.containingBlockForFunction;
    }
}
