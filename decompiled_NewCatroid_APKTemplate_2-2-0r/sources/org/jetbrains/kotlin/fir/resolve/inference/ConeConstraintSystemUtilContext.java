package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeFixVariableConstraintPosition;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.PostponedArgumentInputTypesResolver;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConeConstraintSystemUtilContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\nH\u0016J)\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u000fH\u0016J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0017\u001a\u00020\u000fH\u0016J\u001a\u0010\u0018\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0018\u00010\u00192\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0014\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0019*\u00020\u001dH\u0002J\f\u0010\u001e\u001a\u00020\u0004*\u00020\u000fH\u0016J\f\u0010\u001f\u001a\u00020\u0004*\u00020\nH\u0016J\f\u0010 \u001a\u00020\u0004*\u00020\nH\u0016J\f\u0010!\u001a\u00020\u0004*\u00020\nH\u0016J\f\u0010\"\u001a\u00020\u0004*\u00020\u000fH\u0016J\f\u0010#\u001a\u00020$*\u00020$H\u0016J\f\u0010%\u001a\u00020\u0004*\u00020\u000fH\u0016J\f\u0010&\u001a\u00020$*\u00020$H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0005¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ConeConstraintSystemUtilContext;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "()V", "isForcedAllowForkingInferenceSystem", "", "()Z", "isForcedConsiderExtensionReceiverFromConstrainsInLambda", "createArgumentConstraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ArgumentConstraintPosition;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedAtomWithRevisableExpectedType;", "createFixVariableConstraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", ExifInterface.GPS_DIRECTION_TRUE, "variable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "atom", "(Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Ljava/lang/Object;)Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", "createTypeVariableForCallableReferenceParameterType", "index", "", "createTypeVariableForCallableReferenceReturnType", "createTypeVariableForLambdaParameterType", "createTypeVariableForLambdaReturnType", "extractLambdaParameterTypesFromDeclaration", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "declaration", "collectDeclaredValueParameterTypes", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "hasOnlyInputTypesAttribute", "isFunctionExpression", "isFunctionExpressionWithReceiver", "isLambda", "isReified", "refineType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "shouldBeFlexible", "unCapture", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeConstraintSystemUtilContext implements ConstraintSystemUtilContext {
    public static final ConeConstraintSystemUtilContext INSTANCE = new ConeConstraintSystemUtilContext();

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isForcedAllowForkingInferenceSystem() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isForcedConsiderExtensionReceiverFromConstrainsInLambda() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public KotlinTypeMarker refineType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return kotlinTypeMarker;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean shouldBeFlexible(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return false;
    }

    private ConeConstraintSystemUtilContext() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean hasOnlyInputTypesAttribute(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        if (!(typeVariableMarker instanceof ConeTypeParameterBasedTypeVariable)) {
            return false;
        }
        List<ClassId> resolvedAnnotationClassIds = ((ConeTypeParameterBasedTypeVariable) typeVariableMarker).getTypeParameterSymbol().getResolvedAnnotationClassIds();
        if ((resolvedAnnotationClassIds instanceof Collection) && resolvedAnnotationClassIds.isEmpty()) {
            return false;
        }
        Iterator<T> it = resolvedAnnotationClassIds.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((ClassId) it.next(), StandardClassIds.Annotations.INSTANCE.getOnlyInputTypes())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public KotlinTypeMarker unCapture(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof ConeKotlinType) {
            return kotlinTypeMarker;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isReified(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return (typeVariableMarker instanceof ConeTypeParameterBasedTypeVariable) && ((FirTypeParameter) ((ConeTypeParameterBasedTypeVariable) typeVariableMarker).getTypeParameterSymbol().getFir()).getIsReified();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public ArgumentConstraintPosition<?> createArgumentConstraintPosition(PostponedAtomWithRevisableExpectedType argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (!(argument instanceof PostponedResolvedAtom)) {
            throw new IllegalArgumentException(String.valueOf(Reflection.getOrCreateKotlinClass(argument.getClass())).toString());
        }
        return new ConeArgumentConstraintPosition(((PostponedResolvedAtom) argument).getReference());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public <T> FixVariableConstraintPosition<T> createFixVariableConstraintPosition(TypeVariableMarker variable, T atom) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        if (atom != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return new ConeFixVariableConstraintPosition(variable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public List<ConeKotlinType> extractLambdaParameterTypesFromDeclaration(PostponedAtomWithRevisableExpectedType declaration) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof PostponedResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (!(((PostponedResolvedAtom) declaration) instanceof LambdaWithTypeVariableAsExpectedTypeAtom)) {
            return null;
        }
        FirAnonymousFunction anonymousFunction = ((LambdaWithTypeVariableAsExpectedTypeAtom) declaration).getReference().getAnonymousFunction();
        if (anonymousFunction.getIsLambda()) {
            if (anonymousFunction.getValueParameters().isEmpty()) {
                return null;
            }
            return collectDeclaredValueParameterTypes(anonymousFunction);
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirReceiverParameter receiverParameter = anonymousFunction.getReceiverParameter();
        if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(typeRef)) != null) {
            listCreateListBuilder.add(coneType);
        }
        listCreateListBuilder.addAll(INSTANCE.collectDeclaredValueParameterTypes(anonymousFunction));
        return CollectionsKt.build(listCreateListBuilder);
    }

    private final List<ConeKotlinType> collectDeclaredValueParameterTypes(FirAnonymousFunction firAnonymousFunction) {
        List<FirValueParameter> valueParameters = firAnonymousFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            FirTypeRef returnTypeRef = ((FirValueParameter) it.next()).getReturnTypeRef();
            ConeKotlinType coneKotlinType = null;
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (type instanceof ConeKotlinType) {
                coneKotlinType = type;
            }
            arrayList.add(coneKotlinType);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isFunctionExpression(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        Intrinsics.checkNotNullParameter(postponedAtomWithRevisableExpectedType, "<this>");
        if (postponedAtomWithRevisableExpectedType instanceof PostponedResolvedAtom) {
            return (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeAtom) && !((LambdaWithTypeVariableAsExpectedTypeAtom) postponedAtomWithRevisableExpectedType).getReference().getAnonymousFunction().getIsLambda();
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isFunctionExpressionWithReceiver(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(postponedAtomWithRevisableExpectedType, "<this>");
        if (!(postponedAtomWithRevisableExpectedType instanceof PostponedResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
            LambdaWithTypeVariableAsExpectedTypeAtom lambdaWithTypeVariableAsExpectedTypeAtom = (LambdaWithTypeVariableAsExpectedTypeAtom) postponedAtomWithRevisableExpectedType;
            if (!lambdaWithTypeVariableAsExpectedTypeAtom.getReference().getAnonymousFunction().getIsLambda()) {
                FirReceiverParameter receiverParameter = lambdaWithTypeVariableAsExpectedTypeAtom.getReference().getAnonymousFunction().getReceiverParameter();
                if (((receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef)) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isLambda(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        Intrinsics.checkNotNullParameter(postponedAtomWithRevisableExpectedType, "<this>");
        if (postponedAtomWithRevisableExpectedType instanceof PostponedResolvedAtom) {
            return (postponedAtomWithRevisableExpectedType instanceof LambdaWithTypeVariableAsExpectedTypeAtom) && ((LambdaWithTypeVariableAsExpectedTypeAtom) postponedAtomWithRevisableExpectedType).getReference().getAnonymousFunction().getIsLambda();
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForLambdaReturnType() {
        return new ConeTypeVariableForPostponedAtom(PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_FOR_LAMBDA_RETURN_TYPE);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForLambdaParameterType(PostponedAtomWithRevisableExpectedType argument, int index) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return new ConeTypeVariableForLambdaParameterType(PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_PREFIX_FOR_LAMBDA_PARAMETER_TYPE + index, index);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForCallableReferenceParameterType(PostponedAtomWithRevisableExpectedType argument, int index) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return new ConeTypeVariableForPostponedAtom(PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_PREFIX_FOR_CR_PARAMETER_TYPE + index);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForCallableReferenceReturnType() {
        return new ConeTypeVariableForPostponedAtom(PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_FOR_LAMBDA_RETURN_TYPE);
    }
}
