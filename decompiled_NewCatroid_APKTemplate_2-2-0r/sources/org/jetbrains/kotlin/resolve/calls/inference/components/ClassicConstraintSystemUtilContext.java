package org.jetbrains.kotlin.resolve.calls.inference.components;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.resolve.calls.components.CreateFreshVariablesSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForCallableReferenceParameterType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForCallableReferenceReturnType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaParameterType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.FunctionExpression;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.PostponableKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ClassicConstraintSystemUtilContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J)\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015J\u0018\u0010\u0016\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0013H\u0016J\u0018\u0010\u001a\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u0013H\u0016J\u001a\u0010\u001c\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001e\u0018\u00010\u001d2\u0006\u0010\u001f\u001a\u00020\u000eH\u0016J\f\u0010 \u001a\u00020!*\u00020\u0013H\u0016J\f\u0010\"\u001a\u00020!*\u00020\u000eH\u0016J\f\u0010#\u001a\u00020!*\u00020\u000eH\u0016J\f\u0010$\u001a\u00020!*\u00020\u000eH\u0016J\f\u0010%\u001a\u00020!*\u00020\u0013H\u0016J\f\u0010&\u001a\u00020\u001e*\u00020\u001eH\u0016J\f\u0010'\u001a\u00020!*\u00020\u0013H\u0016J\f\u0010(\u001a\u00020\u001e*\u00020\u001eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ClassicConstraintSystemUtilContext;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getKotlinTypeRefiner", "()Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "createArgumentConstraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ArgumentConstraintPosition;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedAtomWithRevisableExpectedType;", "createFixVariableConstraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", ExifInterface.GPS_DIRECTION_TRUE, "variable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "atom", "(Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Ljava/lang/Object;)Lorg/jetbrains/kotlin/resolve/calls/inference/model/FixVariableConstraintPosition;", "createTypeVariableForCallableReferenceParameterType", "index", "", "createTypeVariableForCallableReferenceReturnType", "createTypeVariableForLambdaParameterType", "createTypeVariableForLambdaReturnType", "extractLambdaParameterTypesFromDeclaration", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "declaration", "hasOnlyInputTypesAttribute", "", "isFunctionExpression", "isFunctionExpressionWithReceiver", "isLambda", "isReified", "refineType", "shouldBeFlexible", "unCapture", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassicConstraintSystemUtilContext implements ConstraintSystemUtilContext {
    private final KotlinBuiltIns builtIns;
    private final KotlinTypeRefiner kotlinTypeRefiner;

    public ClassicConstraintSystemUtilContext(KotlinTypeRefiner kotlinTypeRefiner, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.builtIns = builtIns;
    }

    public final KotlinTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean shouldBeFlexible(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        return (typeVariableMarker instanceof TypeVariableFromCallableDescriptor) && CreateFreshVariablesSubstitutor.shouldBeFlexible$default(CreateFreshVariablesSubstitutor.INSTANCE, ((TypeVariableFromCallableDescriptor) typeVariableMarker).getOriginalTypeParameter(), null, 1, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean hasOnlyInputTypesAttribute(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        if (!(typeVariableMarker instanceof NewTypeVariable)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((NewTypeVariable) typeVariableMarker).hasOnlyInputTypesAnnotation();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public KotlinTypeMarker unCapture(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof KotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return TypeUtilsKt.unCapture((KotlinType) kotlinTypeMarker).unwrap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isReified(TypeVariableMarker typeVariableMarker) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        if (typeVariableMarker instanceof TypeVariableFromCallableDescriptor) {
            return ((TypeVariableFromCallableDescriptor) typeVariableMarker).getOriginalTypeParameter().isReified();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public KotlinTypeMarker refineType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (!(kotlinTypeMarker instanceof KotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return this.kotlinTypeRefiner.refineType(kotlinTypeMarker);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public ArgumentConstraintPosition<?> createArgumentConstraintPosition(PostponedAtomWithRevisableExpectedType argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (!(argument instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ResolutionAtom atom = ((ResolvedAtom) argument).getAtom();
        Intrinsics.checkNotNull(atom, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument");
        return new ArgumentConstraintPositionImpl((KotlinCallArgument) atom);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public <T> FixVariableConstraintPosition<T> createFixVariableConstraintPosition(TypeVariableMarker variable, T atom) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        if (!(atom instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return new FixVariableConstraintPositionImpl(variable, (ResolvedAtom) atom);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public List<KotlinTypeMarker> extractLambdaParameterTypesFromDeclaration(PostponedAtomWithRevisableExpectedType declaration) {
        UnwrappedType[] parametersTypes;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ResolutionAtom atom = ((ResolvedAtom) declaration).getAtom();
        if (atom instanceof FunctionExpression) {
            FunctionExpression functionExpression = (FunctionExpression) atom;
            UnwrappedType receiverType = functionExpression.getReceiverType();
            return receiverType != null ? CollectionsKt.plus((Collection) CollectionsKt.listOf(receiverType), (Object[]) functionExpression.getParametersTypes()) : ArraysKt.toList(functionExpression.getParametersTypes());
        }
        if (!(atom instanceof LambdaKotlinCallArgument) || (parametersTypes = ((LambdaKotlinCallArgument) atom).getParametersTypes()) == null) {
            return null;
        }
        return ArraysKt.toList(parametersTypes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isFunctionExpression(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        Intrinsics.checkNotNullParameter(postponedAtomWithRevisableExpectedType, "<this>");
        if (!(postponedAtomWithRevisableExpectedType instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ((ResolvedAtom) postponedAtomWithRevisableExpectedType).getAtom() instanceof FunctionExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isFunctionExpressionWithReceiver(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        Intrinsics.checkNotNullParameter(postponedAtomWithRevisableExpectedType, "<this>");
        if (!(postponedAtomWithRevisableExpectedType instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ResolutionAtom atom = ((ResolvedAtom) postponedAtomWithRevisableExpectedType).getAtom();
        return (atom instanceof FunctionExpression) && ((FunctionExpression) atom).getReceiverType() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public boolean isLambda(PostponedAtomWithRevisableExpectedType postponedAtomWithRevisableExpectedType) {
        Intrinsics.checkNotNullParameter(postponedAtomWithRevisableExpectedType, "<this>");
        if (!(postponedAtomWithRevisableExpectedType instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ResolutionAtom atom = ((ResolvedAtom) postponedAtomWithRevisableExpectedType).getAtom();
        return (atom instanceof LambdaKotlinCallArgument) && !(atom instanceof FunctionExpression);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForLambdaReturnType() {
        return new TypeVariableForLambdaReturnType(this.builtIns, PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_FOR_LAMBDA_RETURN_TYPE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForLambdaParameterType(PostponedAtomWithRevisableExpectedType argument, int index) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (!(argument instanceof ResolvedAtom)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ResolutionAtom atom = ((ResolvedAtom) argument).getAtom();
        Intrinsics.checkNotNull(atom, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.PostponableKotlinCallArgument");
        return new TypeVariableForLambdaParameterType((PostponableKotlinCallArgument) atom, index, this.builtIns, PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_PREFIX_FOR_LAMBDA_PARAMETER_TYPE + (index + 1));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForCallableReferenceParameterType(PostponedAtomWithRevisableExpectedType argument, int index) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return new TypeVariableForCallableReferenceParameterType(this.builtIns, PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_PREFIX_FOR_CR_PARAMETER_TYPE + (index + 1));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemUtilContext
    public TypeVariableMarker createTypeVariableForCallableReferenceReturnType() {
        return new TypeVariableForCallableReferenceReturnType(this.builtIns, PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_FOR_CR_RETURN_TYPE);
    }
}
