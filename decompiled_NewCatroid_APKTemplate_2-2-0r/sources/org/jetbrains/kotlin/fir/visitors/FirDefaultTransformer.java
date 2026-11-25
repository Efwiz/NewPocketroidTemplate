package org.jetbrains.kotlin.fir.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.expressions.FirBreakExpression;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirComponentCall;
import org.jetbrains.kotlin.fir.expressions.FirContinueExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorLoop;
import org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirDefaultTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001d\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\rJ\u001d\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0011J\u001d\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001fJ\u001d\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010#J\u001d\u0010$\u001a\u00020\u00052\u0006\u0010%\u001a\u00020&2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010'J\u001d\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010+J\u001d\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00100J\u001d\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u0002032\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00104J\u001d\u00105\u001a\u00020\u001c2\u0006\u00106\u001a\u0002072\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00108J\u001d\u00109\u001a\u00020\u001c2\u0006\u0010:\u001a\u00020;2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010<J\u001d\u0010=\u001a\u00020\u00052\u0006\u0010>\u001a\u00020?2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010@J\u001d\u0010A\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020C2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010DJ\u001d\u0010E\u001a\u00020\u001c2\u0006\u0010F\u001a\u00020G2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010HJ\u001d\u0010I\u001a\u00020\u00052\u0006\u0010J\u001a\u00020K2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010LJ\u001d\u0010M\u001a\u00020\u00052\u0006\u0010N\u001a\u00020O2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010PJ\u001d\u0010Q\u001a\u00020\u00132\u0006\u0010R\u001a\u00020S2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010TJ\u001d\u0010U\u001a\u00020\u001c2\u0006\u0010V\u001a\u00020W2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010XJ\u001d\u0010Y\u001a\u00020\u00052\u0006\u0010Z\u001a\u00020[2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\\J\u001d\u0010]\u001a\u00020\u00052\u0006\u0010^\u001a\u00020_2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010`J\u001d\u0010a\u001a\u00020\u001c2\u0006\u0010b\u001a\u00020c2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010dJ\u001d\u0010e\u001a\u00020\u001c2\u0006\u0010f\u001a\u00020g2\u0006\u0010\b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010h¨\u0006i"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/FirDefaultTransformer;", "D", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "()V", "transformBreakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "breakExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;", "data", "(Lorg/jetbrains/kotlin/fir/expressions/FirBreakExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformComponentCall", "componentCall", "Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirComponentCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformConstructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "constructedClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructedClassTypeParameterRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "transformContinueExpression", "continueExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirContinueExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformDynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "dynamicTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirDynamicTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformErrorExpression", "errorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorFunction", "errorFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirErrorFunction;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorLoop", "errorLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "errorNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;", "(Lorg/jetbrains/kotlin/fir/references/FirErrorNamedReference;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "transformErrorResolvedQualifier", "errorResolvedQualifier", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirErrorResolvedQualifier;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformErrorTypeRef", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformFunctionTypeRef", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformImplicitInvokeCall", "implicitInvokeCall", "Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirImplicitInvokeCall;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformIntersectionTypeRef", "intersectionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirIntersectionTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformLambdaArgumentExpression", "lambdaArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirLambdaArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformNamedArgumentExpression", "namedArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirNamedArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformOuterClassTypeParameterRef", "outerClassTypeParameterRef", "Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;", "(Lorg/jetbrains/kotlin/fir/declarations/FirOuterClassTypeParameterRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "transformResolvedTypeRef", "resolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformSpreadArgumentExpression", "spreadArgumentExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirSpreadArgumentExpression;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformTypeRefWithNullability", "typeRefWithNullability", "Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRefWithNullability;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformUserTypeRef", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "(Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDefaultTransformer<D> extends FirTransformer<D> {
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, D data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        return transformTypeRef(implicitTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, D data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        return transformTypeRef(resolvedTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformErrorTypeRef(FirErrorTypeRef errorTypeRef, D data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        return transformResolvedTypeRef(errorTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformTypeRefWithNullability(FirTypeRefWithNullability typeRefWithNullability, D data) {
        Intrinsics.checkNotNullParameter(typeRefWithNullability, "typeRefWithNullability");
        return transformTypeRef(typeRefWithNullability, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformDynamicTypeRef(FirDynamicTypeRef dynamicTypeRef, D data) {
        Intrinsics.checkNotNullParameter(dynamicTypeRef, "dynamicTypeRef");
        return transformTypeRefWithNullability(dynamicTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformFunctionTypeRef(FirFunctionTypeRef functionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        return transformTypeRefWithNullability(functionTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformUserTypeRef(FirUserTypeRef userTypeRef, D data) {
        Intrinsics.checkNotNullParameter(userTypeRef, "userTypeRef");
        return transformTypeRefWithNullability(userTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformIntersectionTypeRef(FirIntersectionTypeRef intersectionTypeRef, D data) {
        Intrinsics.checkNotNullParameter(intersectionTypeRef, "intersectionTypeRef");
        return transformTypeRef(intersectionTypeRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, D data) {
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        return transformQualifiedAccessExpression(callableReferenceAccess, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformComponentCall(FirComponentCall componentCall, D data) {
        Intrinsics.checkNotNullParameter(componentCall, "componentCall");
        return transformFunctionCall(componentCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformReturnExpression(FirReturnExpression returnExpression, D data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        return transformJump(returnExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformContinueExpression(FirContinueExpression continueExpression, D data) {
        Intrinsics.checkNotNullParameter(continueExpression, "continueExpression");
        return transformJump(continueExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBreakExpression(FirBreakExpression breakExpression, D data) {
        Intrinsics.checkNotNullParameter(breakExpression, "breakExpression");
        return transformJump(breakExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformLambdaArgumentExpression(FirLambdaArgumentExpression lambdaArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(lambdaArgumentExpression, "lambdaArgumentExpression");
        return transformWrappedArgumentExpression(lambdaArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSpreadArgumentExpression(FirSpreadArgumentExpression spreadArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(spreadArgumentExpression, "spreadArgumentExpression");
        return transformWrappedArgumentExpression(spreadArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformNamedArgumentExpression(FirNamedArgumentExpression namedArgumentExpression, D data) {
        Intrinsics.checkNotNullParameter(namedArgumentExpression, "namedArgumentExpression");
        return transformWrappedArgumentExpression(namedArgumentExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorExpression(FirErrorExpression errorExpression, D data) {
        Intrinsics.checkNotNullParameter(errorExpression, "errorExpression");
        return transformExpression(errorExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorLoop(FirErrorLoop errorLoop, D data) {
        Intrinsics.checkNotNullParameter(errorLoop, "errorLoop");
        return transformLoop(errorLoop, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirReference transformErrorNamedReference(FirErrorNamedReference errorNamedReference, D data) {
        Intrinsics.checkNotNullParameter(errorNamedReference, "errorNamedReference");
        return transformNamedReference(errorNamedReference, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorFunction(FirErrorFunction errorFunction, D data) {
        Intrinsics.checkNotNullParameter(errorFunction, "errorFunction");
        return transformFunction(errorFunction, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorResolvedQualifier(FirErrorResolvedQualifier errorResolvedQualifier, D data) {
        Intrinsics.checkNotNullParameter(errorResolvedQualifier, "errorResolvedQualifier");
        return transformResolvedQualifier(errorResolvedQualifier, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformImplicitInvokeCall(FirImplicitInvokeCall implicitInvokeCall, D data) {
        Intrinsics.checkNotNullParameter(implicitInvokeCall, "implicitInvokeCall");
        return transformFunctionCall(implicitInvokeCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeParameterRef transformConstructedClassTypeParameterRef(FirConstructedClassTypeParameterRef constructedClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(constructedClassTypeParameterRef, "constructedClassTypeParameterRef");
        return transformTypeParameterRef(constructedClassTypeParameterRef, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeParameterRef transformOuterClassTypeParameterRef(FirOuterClassTypeParameterRef outerClassTypeParameterRef, D data) {
        Intrinsics.checkNotNullParameter(outerClassTypeParameterRef, "outerClassTypeParameterRef");
        return transformTypeParameterRef(outerClassTypeParameterRef, data);
    }
}
