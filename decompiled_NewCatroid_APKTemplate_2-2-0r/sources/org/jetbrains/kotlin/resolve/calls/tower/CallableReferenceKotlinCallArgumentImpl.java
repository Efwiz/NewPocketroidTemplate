package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u00012\u00020\u0002BO\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\r\u0012\u0006\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013R\u0016\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\t\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u0010\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/CallableReferenceKotlinCallArgumentImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "scopeTowerForResolution", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfoAfterThisArgument", "ktCallableReferenceExpression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "lhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "rhsName", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;)V", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "getCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfoBeforeThisArgument", "getKtCallableReferenceExpression", "()Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "getLhsResult", "()Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "getRhsName", "getScopeTowerForResolution", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceKotlinCallArgumentImpl extends PSIKotlinCallArgument implements CallableReferenceKotlinCallArgument {
    private final Name argumentName;
    private final KotlinCall call;
    private final DataFlowInfo dataFlowInfoAfterThisArgument;
    private final DataFlowInfo dataFlowInfoBeforeThisArgument;
    private final KtCallableReferenceExpression ktCallableReferenceExpression;
    private final LHSResult lhsResult;
    private final Name rhsName;
    private final ImplicitScopeTower scopeTowerForResolution;
    private final ValueArgument valueArgument;

    public final ImplicitScopeTower getScopeTowerForResolution() {
        return this.scopeTowerForResolution;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public ValueArgument getValueArgument() {
        return this.valueArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoBeforeThisArgument() {
        return this.dataFlowInfoBeforeThisArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoAfterThisArgument() {
        return this.dataFlowInfoAfterThisArgument;
    }

    public final KtCallableReferenceExpression getKtCallableReferenceExpression() {
        return this.ktCallableReferenceExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public Name getArgumentName() {
        return this.argumentName;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument, org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    public LHSResult getLhsResult() {
        return this.lhsResult;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    public Name getRhsName() {
        return this.rhsName;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument, org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom
    public KotlinCall getCall() {
        return this.call;
    }

    public CallableReferenceKotlinCallArgumentImpl(ImplicitScopeTower scopeTowerForResolution, ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, DataFlowInfo dataFlowInfoAfterThisArgument, KtCallableReferenceExpression ktCallableReferenceExpression, Name name2, LHSResult lhsResult, Name rhsName, KotlinCall call) {
        Intrinsics.checkNotNullParameter(scopeTowerForResolution, "scopeTowerForResolution");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoAfterThisArgument, "dataFlowInfoAfterThisArgument");
        Intrinsics.checkNotNullParameter(ktCallableReferenceExpression, "ktCallableReferenceExpression");
        Intrinsics.checkNotNullParameter(lhsResult, "lhsResult");
        Intrinsics.checkNotNullParameter(rhsName, "rhsName");
        Intrinsics.checkNotNullParameter(call, "call");
        this.scopeTowerForResolution = scopeTowerForResolution;
        this.valueArgument = valueArgument;
        this.dataFlowInfoBeforeThisArgument = dataFlowInfoBeforeThisArgument;
        this.dataFlowInfoAfterThisArgument = dataFlowInfoAfterThisArgument;
        this.ktCallableReferenceExpression = ktCallableReferenceExpression;
        this.argumentName = name2;
        this.lhsResult = lhsResult;
        this.rhsName = rhsName;
        this.call = call;
    }
}
