package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b&\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bR\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0010\"\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/PSIFunctionKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "outerCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/name/Name;)V", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "dataFlowInfoAfterThisArgument", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfoBeforeThisArgument", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "getKtFunction", "()Lorg/jetbrains/kotlin/psi/KtFunction;", "lambdaInitialDataFlowInfo", "getLambdaInitialDataFlowInfo", "setLambdaInitialDataFlowInfo", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;)V", "getOuterCallContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PSIFunctionKotlinCallArgument extends PSIKotlinCallArgument implements LambdaKotlinCallArgument {
    private final Name argumentName;
    private final DataFlowInfo dataFlowInfoBeforeThisArgument;
    public DataFlowInfo lambdaInitialDataFlowInfo;
    private final BasicCallResolutionContext outerCallContext;
    private final ValueArgument valueArgument;

    public abstract KtExpression getExpression();

    public abstract KtFunction getKtFunction();

    public final BasicCallResolutionContext getOuterCallContext() {
        return this.outerCallContext;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public ValueArgument getValueArgument() {
        return this.valueArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoBeforeThisArgument() {
        return this.dataFlowInfoBeforeThisArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public Name getArgumentName() {
        return this.argumentName;
    }

    public PSIFunctionKotlinCallArgument(BasicCallResolutionContext outerCallContext, ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, Name name2) {
        Intrinsics.checkNotNullParameter(outerCallContext, "outerCallContext");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        this.outerCallContext = outerCallContext;
        this.valueArgument = valueArgument;
        this.dataFlowInfoBeforeThisArgument = dataFlowInfoBeforeThisArgument;
        this.argumentName = name2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoAfterThisArgument() {
        return getDataFlowInfoBeforeThisArgument();
    }

    public final DataFlowInfo getLambdaInitialDataFlowInfo() {
        DataFlowInfo dataFlowInfo = this.lambdaInitialDataFlowInfo;
        if (dataFlowInfo != null) {
            return dataFlowInfo;
        }
        Intrinsics.throwUninitializedPropertyAccessException("lambdaInitialDataFlowInfo");
        return null;
    }

    public final void setLambdaInitialDataFlowInfo(DataFlowInfo dataFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "<set-?>");
        this.lambdaInitialDataFlowInfo = dataFlowInfo;
    }
}
