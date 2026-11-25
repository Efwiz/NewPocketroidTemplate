package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;

/* JADX INFO: compiled from: PostponedArguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002\u001a$\u0010\f\u001a\u00020\r*\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u000e2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u0010\u001aL\u0010\u0011\u001a\u00020\u0012*\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00132\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019¨\u0006\u001a"}, d2 = {"extractLambdaInfo", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "argument", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "preprocessCallableReference", "", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "preprocessLambdaArgument", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "duringCompletion", "", "returnTypeVariable", "Lorg/jetbrains/kotlin/fir/resolve/inference/ConeTypeVariableForLambdaReturnType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PostponedArgumentsKt {
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008d, code lost:
    
        if ((r2.getTypeArguments().length == 0) == false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom preprocessLambdaArgument(org.jetbrains.kotlin.fir.resolve.calls.Candidate r14, org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder r15, org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression r16, org.jetbrains.kotlin.fir.types.ConeKotlinType r17, org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext r18, org.jetbrains.kotlin.fir.resolve.calls.CheckerSink r19, boolean r20, org.jetbrains.kotlin.fir.resolve.inference.ConeTypeVariableForLambdaReturnType r21) {
        /*
            Method dump skipped, instruction units count: 320
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.resolve.inference.PostponedArgumentsKt.preprocessLambdaArgument(org.jetbrains.kotlin.fir.resolve.calls.Candidate, org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder, org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression, org.jetbrains.kotlin.fir.types.ConeKotlinType, org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext, org.jetbrains.kotlin.fir.resolve.calls.CheckerSink, boolean, org.jetbrains.kotlin.fir.resolve.inference.ConeTypeVariableForLambdaReturnType):org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom");
    }

    public static final void preprocessCallableReference(Candidate candidate, FirCallableReferenceAccess argument, ConeKotlinType coneKotlinType, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(context, "context");
        candidate.getPostponedAtoms().add(new ResolvedCallableReferenceAtom(argument, coneKotlinType, context.getBodyResolveComponents().getDoubleColonExpressionResolver().resolveDoubleColonLHS$resolve(argument), context.getSession()));
    }

    private static final ResolvedLambdaAtom extractLambdaInfo(ConeKotlinType coneKotlinType, FirAnonymousFunction firAnonymousFunction, ConstraintSystemBuilder constraintSystemBuilder, FirSession firSession, Candidate candidate) {
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible;
        FirBasedSymbol<?> symbol;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible2;
        if (((coneKotlinType == null || (coneSimpleKotlinTypeLowerBoundIfFlexible2 = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType)) == null) ? null : FunctionalTypeUtilsKt.functionTypeKind(coneSimpleKotlinTypeLowerBoundIfFlexible2, firSession)) != null) {
            throw new IllegalArgumentException(("Currently, we only extract lambda info from its shape when expected type is not function, but " + coneKotlinType).toString());
        }
        ConeTypeVariableForLambdaReturnType coneTypeVariableForLambdaReturnType = new ConeTypeVariableForLambdaReturnType(firAnonymousFunction, "_L");
        ConeKotlinType receiverType = org.jetbrains.kotlin.fir.types.InferenceUtilsKt.getReceiverType(firAnonymousFunction);
        ConeTypeVariableType returnType = org.jetbrains.kotlin.fir.types.InferenceUtilsKt.getReturnType(firAnonymousFunction);
        if (returnType == null) {
            returnType = coneTypeVariableForLambdaReturnType.getDefaultType();
        }
        ConeKotlinType coneKotlinType2 = returnType;
        ConeClassLikeType coneClassLikeTypeCreate = Intrinsics.areEqual((candidate == null || (symbol = candidate.getSymbol()) == null) ? null : symbol.getOrigin(), FirDeclarationOrigin.DynamicScope.INSTANCE) ? TypeUtilsKt.create(ConeDynamicType.INSTANCE, firSession) : firSession.getBuiltinTypes().getNothingType().getType();
        List<FirValueParameter> valueParameters = firAnonymousFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            FirTypeRef returnTypeRef = ((FirValueParameter) it.next()).getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            if (type == null) {
                type = coneClassLikeTypeCreate;
            }
            arrayList.add(type);
        }
        ArrayList arrayList2 = arrayList;
        List<FirContextReceiver> contextReceivers = firAnonymousFunction.getContextReceivers();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it2 = contextReceivers.iterator();
        while (it2.hasNext()) {
            FirTypeRef typeRef = ((FirContextReceiver) it2.next()).getTypeRef();
            FirResolvedTypeRef firResolvedTypeRef2 = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
            ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
            if (!(type2 instanceof ConeKotlinType)) {
                type2 = null;
            }
            if (type2 == null) {
                type2 = coneClassLikeTypeCreate;
            }
            arrayList3.add(type2);
        }
        ArrayList arrayList4 = arrayList3;
        boolean zAreEqual = Intrinsics.areEqual(coneKotlinType2, coneTypeVariableForLambdaReturnType.getDefaultType());
        if (zAreEqual) {
            constraintSystemBuilder.registerVariable(coneTypeVariableForLambdaReturnType);
        }
        FirTypeRef typeRef2 = firAnonymousFunction.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef3 = typeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef2 : null;
        ConeKotlinType type3 = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
        if (!(type3 instanceof ConeKotlinType)) {
            type3 = null;
        }
        return new ResolvedLambdaAtom(firAnonymousFunction, coneKotlinType, (type3 == null || (coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(type3)) == null) ? null : FunctionalTypeUtilsKt.functionTypeKind(coneSimpleKotlinTypeLowerBoundIfFlexible, firSession), receiverType, arrayList4, arrayList2, coneKotlinType2, zAreEqual ? coneTypeVariableForLambdaReturnType : null, candidate, false);
    }
}
