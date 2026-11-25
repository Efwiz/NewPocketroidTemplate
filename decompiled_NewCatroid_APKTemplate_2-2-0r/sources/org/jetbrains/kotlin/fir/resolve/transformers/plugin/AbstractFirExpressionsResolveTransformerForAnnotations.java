package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;

/* JADX INFO: compiled from: FirAnnotationArgumentsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH$J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020)2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020,2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010-\u001a\u00020\u00062\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u0002022\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u0002052\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u00106\u001a\u00020\u00062\u0006\u00107\u001a\u0002082\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u00109\u001a\u00020\u00062\u0006\u0010:\u001a\u00020;2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020>2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010?\u001a\u00020\u00062\u0006\u0010@\u001a\u00020A2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\f\u0010B\u001a\u00020\n*\u00020\bH\u0014¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirExpressionsResolveTransformerForAnnotations;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "resolveQualifiedAccessAndSelectCandidate", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "isUsedAsReceiver", "", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "shouldComputeTypeOfGetClassCallWithNotQualifierInLhs", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "transformAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "transformArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "transformAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "transformBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "transformBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "transformCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "transformComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "transformDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "transformErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "transformExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "transformFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "transformThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "transformTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "transformVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "isAcceptableResolvedQualifiedAccess", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirExpressionsResolveTransformerForAnnotations extends FirExpressionsResolveTransformer {
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer
    protected abstract FirStatement resolveQualifiedAccessAndSelectCandidate(FirQualifiedAccessExpression qualifiedAccessExpression, boolean isUsedAsReceiver, FirElement callSite);

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer
    protected boolean shouldComputeTypeOfGetClassCallWithNotQualifierInLhs(FirGetClassCall getClassCall) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractFirExpressionsResolveTransformerForAnnotations(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotation(FirAnnotation annotation, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        AbstractFirExpressionsResolveTransformerForAnnotations abstractFirExpressionsResolveTransformerForAnnotations = this;
        abstractFirExpressionsResolveTransformerForAnnotations.getComponents().getDataFlowAnalyzer().enterAnnotation();
        annotation.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
        abstractFirExpressionsResolveTransformerForAnnotations.getComponents().getDataFlowAnalyzer().exitAnnotation();
        return annotation;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return transformAnnotation((FirAnnotation) annotationCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return transformAnnotation((FirAnnotation) errorAnnotationCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformExpression(FirExpression expression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirElement firElementTransformChildren = expression.transformChildren(getTransformer(), data);
        Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
        return (FirStatement) firElementTransformChildren;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer
    protected boolean isAcceptableResolvedQualifiedAccess(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        return !(firQualifiedAccessExpression.getCalleeReference() instanceof FirErrorNamedReference);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunctionCall(FirFunctionCall functionCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return functionCall;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBlock(FirBlock block, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(data, "data");
        return block;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return thisReceiverExpression;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformComparisonExpression(FirComparisonExpression comparisonExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return comparisonExpression;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return typeOperatorCall;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return checkNotNullCall;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return binaryLogicExpression;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformVariableAssignment(FirVariableAssignment variableAssignment, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        Intrinsics.checkNotNullParameter(data, "data");
        return variableAssignment;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(data, "data");
        return callableReferenceAccess;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return delegatedConstructorCall;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return augmentedArraySetCall;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformArrayLiteral(FirArrayLiteral arrayLiteral, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        arrayLiteral.transformChildren(getTransformer(), data);
        return arrayLiteral;
    }
}
