package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0010\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0018\u00010\u000f¢\u0006\u0002\u0010\u0011R(\u0010\u0014\u001a\u0004\u0018\u00010\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001aR$\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0012\u001a\u00020\u001d@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R \u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0018\u00010\u000fX\u0096\u0004¢\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/LambdaKotlinCallArgumentImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIFunctionKotlinCallArgument;", "outerCallContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "dataFlowInfoBeforeThisArgument", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "argumentName", "Lorg/jetbrains/kotlin/name/Name;", "ktLambdaExpression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "containingBlockForLambda", "Lorg/jetbrains/kotlin/psi/KtExpression;", "parametersTypes", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;Lorg/jetbrains/kotlin/psi/ValueArgument;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/psi/KtLambdaExpression;Lorg/jetbrains/kotlin/psi/KtExpression;[Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "value", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "builderInferenceSession", "getBuilderInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "setBuilderInferenceSession", "(Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;)V", "getContainingBlockForLambda", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "expression", "getExpression", "", "hasBuilderInferenceAnnotation", "getHasBuilderInferenceAnnotation", "()Z", "setHasBuilderInferenceAnnotation", "(Z)V", "ktFunction", "Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "getKtFunction", "()Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "getKtLambdaExpression", "()Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "getParametersTypes", "()[Lorg/jetbrains/kotlin/types/UnwrappedType;", "[Lorg/jetbrains/kotlin/types/UnwrappedType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LambdaKotlinCallArgumentImpl extends PSIFunctionKotlinCallArgument {
    private InferenceSession builderInferenceSession;
    private final KtExpression containingBlockForLambda;
    private boolean hasBuilderInferenceAnnotation;
    private final KtLambdaExpression ktLambdaExpression;
    private final UnwrappedType[] parametersTypes;

    public final KtLambdaExpression getKtLambdaExpression() {
        return this.ktLambdaExpression;
    }

    public final KtExpression getContainingBlockForLambda() {
        return this.containingBlockForLambda;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    public UnwrappedType[] getParametersTypes() {
        return this.parametersTypes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LambdaKotlinCallArgumentImpl(BasicCallResolutionContext outerCallContext, ValueArgument valueArgument, DataFlowInfo dataFlowInfoBeforeThisArgument, Name name2, KtLambdaExpression ktLambdaExpression, KtExpression containingBlockForLambda, UnwrappedType[] unwrappedTypeArr) {
        super(outerCallContext, valueArgument, dataFlowInfoBeforeThisArgument, name2);
        Intrinsics.checkNotNullParameter(outerCallContext, "outerCallContext");
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Intrinsics.checkNotNullParameter(dataFlowInfoBeforeThisArgument, "dataFlowInfoBeforeThisArgument");
        Intrinsics.checkNotNullParameter(ktLambdaExpression, "ktLambdaExpression");
        Intrinsics.checkNotNullParameter(containingBlockForLambda, "containingBlockForLambda");
        this.ktLambdaExpression = ktLambdaExpression;
        this.containingBlockForLambda = containingBlockForLambda;
        this.parametersTypes = unwrappedTypeArr;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIFunctionKotlinCallArgument
    public KtFunctionLiteral getKtFunction() {
        KtFunctionLiteral functionLiteral = this.ktLambdaExpression.getFunctionLiteral();
        Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
        return functionLiteral;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.PSIFunctionKotlinCallArgument
    public KtExpression getExpression() {
        return this.containingBlockForLambda;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    public boolean getHasBuilderInferenceAnnotation() {
        return this.hasBuilderInferenceAnnotation;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    public void setHasBuilderInferenceAnnotation(boolean z) {
        this.hasBuilderInferenceAnnotation = z;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    public InferenceSession getBuilderInferenceSession() {
        return this.builderInferenceSession;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument
    public void setBuilderInferenceSession(InferenceSession inferenceSession) {
        this.builderInferenceSession = inferenceSession;
    }
}
