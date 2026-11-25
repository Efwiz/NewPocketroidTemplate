package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.CollectionLiteralKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B7\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\t\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/CollectionLiteralKotlinCallArgumentImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/CollectionLiteralKotlinCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCallArgument;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "dataFlowInfoAfterThisArgument", "collectionLiteralExpression", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "outerCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "(Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;)V", "getArgumentName", "()Lorg/jetbrains/kotlin/name/Name;", "getCollectionLiteralExpression", "()Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "getDataFlowInfoAfterThisArgument", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getDataFlowInfoBeforeThisArgument", "isSpread", "", "()Z", "getOuterCallContext", "()Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "getValueArgument", "()Lorg/jetbrains/kotlin/psi/ValueArgument;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionLiteralKotlinCallArgumentImpl extends PSIKotlinCallArgument implements CollectionLiteralKotlinCallArgument {
    private final Name argumentName;
    private final KtCollectionLiteralExpression collectionLiteralExpression;
    private final DataFlowInfo dataFlowInfoAfterThisArgument;
    private final DataFlowInfo dataFlowInfoBeforeThisArgument;
    private final BasicCallResolutionContext outerCallContext;
    private final ValueArgument valueArgument;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public ValueArgument getValueArgument() {
        return this.valueArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public Name getArgumentName() {
        return this.argumentName;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoBeforeThisArgument() {
        return this.dataFlowInfoBeforeThisArgument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIKotlinCallArgument
    public DataFlowInfo getDataFlowInfoAfterThisArgument() {
        return this.dataFlowInfoAfterThisArgument;
    }

    public final KtCollectionLiteralExpression getCollectionLiteralExpression() {
        return this.collectionLiteralExpression;
    }

    public final BasicCallResolutionContext getOuterCallContext() {
        return this.outerCallContext;
    }

    public CollectionLiteralKotlinCallArgumentImpl(ValueArgument valueArgument, Name name2, DataFlowInfo dataFlowInfoBeforeThisArgument, DataFlowInfo dataFlowInfoAfterThisArgument, KtCollectionLiteralExpression collectionLiteralExpression, BasicCallResolutionContext outerCallContext) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoAfterThisArgument, "dataFlowInfoAfterThisArgument");
        Intrinsics.checkNotNullParameter(collectionLiteralExpression, "collectionLiteralExpression");
        Intrinsics.checkNotNullParameter(outerCallContext, "outerCallContext");
        this.valueArgument = valueArgument;
        this.argumentName = name2;
        this.dataFlowInfoBeforeThisArgument = dataFlowInfoBeforeThisArgument;
        this.dataFlowInfoAfterThisArgument = dataFlowInfoAfterThisArgument;
        this.collectionLiteralExpression = collectionLiteralExpression;
        this.outerCallContext = outerCallContext;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument
    public boolean isSpread() {
        return getValueArgument().getSpreadElement() != null;
    }
}
