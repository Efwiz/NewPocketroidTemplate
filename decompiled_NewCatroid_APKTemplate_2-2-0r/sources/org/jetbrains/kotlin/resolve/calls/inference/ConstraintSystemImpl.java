package org.jetbrains.kotlin.resolve.calls.inference;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SubstitutionWithCapturedTypeApproximation;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ConstraintSystemImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001Bc\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n\u0012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u0003¢\u0006\u0002\u0010\u0011JD\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020$0\u00032\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020(0'2\u0006\u0010)\u001a\u00020*H\u0002J$\u0010+\u001a\u00020\u00102\u0006\u0010)\u001a\u00020*2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020(0'H\u0002J\u0010\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0004H\u0016J\b\u0010/\u001a\u00020*H\u0002J\u001c\u00100\u001a\u0002012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020*0'H\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0014R\u0014\u0010\u001a\u001a\u00020\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00040\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "allTypeParameterBounds", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBoundsImpl;", "usedInBounds", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", "errors", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintError;", "initialConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Constraint;", "typeVariableSubstitutors", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V", "currentSubstitutor", "getCurrentSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "localTypeParameterBounds", "getLocalTypeParameterBounds", "()Ljava/util/Map;", "resultingSubstitutor", "getResultingSubstitutor", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemStatus;", "getStatus", "()Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemStatus;", "typeVariables", "", "getTypeVariables", "()Ljava/util/Set;", "getParameterToInferredValueMap", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeParameterBounds", "getDefaultType", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/KotlinType;", "substituteOriginal", "", "getSubstitutor", "getDefaultValue", "getTypeBounds", "typeVariable", "satisfyInitialConstraints", "toBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "filterConstraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemImpl implements ConstraintSystem {
    private final Map<TypeVariable, TypeBoundsImpl> allTypeParameterBounds;
    private final List<ConstraintError> errors;
    private final List<ConstraintSystemBuilderImpl.Constraint> initialConstraints;
    private final ConstraintSystemStatus status;
    private final Map<CallHandle, TypeSubstitutor> typeVariableSubstitutors;
    private final Map<TypeVariable, List<TypeBounds.Bound>> usedInBounds;

    /* JADX INFO: compiled from: ConstraintSystemImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintSystemBuilderImpl.ConstraintKind.values().length];
            try {
                iArr[ConstraintSystemBuilderImpl.ConstraintKind.SUB_TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintSystemBuilderImpl.ConstraintKind.EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConstraintSystemImpl(Map<TypeVariable, TypeBoundsImpl> allTypeParameterBounds, Map<TypeVariable, ? extends List<TypeBounds.Bound>> usedInBounds, List<? extends ConstraintError> errors, List<ConstraintSystemBuilderImpl.Constraint> initialConstraints, Map<CallHandle, ? extends TypeSubstitutor> typeVariableSubstitutors) {
        Intrinsics.checkNotNullParameter(allTypeParameterBounds, "allTypeParameterBounds");
        Intrinsics.checkNotNullParameter(usedInBounds, "usedInBounds");
        Intrinsics.checkNotNullParameter(errors, "errors");
        Intrinsics.checkNotNullParameter(initialConstraints, "initialConstraints");
        Intrinsics.checkNotNullParameter(typeVariableSubstitutors, "typeVariableSubstitutors");
        this.allTypeParameterBounds = allTypeParameterBounds;
        this.usedInBounds = usedInBounds;
        this.errors = errors;
        this.initialConstraints = initialConstraints;
        this.typeVariableSubstitutors = typeVariableSubstitutors;
        this.status = new ConstraintSystemStatus() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemImpl$status$1
            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean isSuccessful() {
                return (hasContradiction() || hasUnknownParameters() || !this.this$0.satisfyInitialConstraints()) ? false : true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasContradiction() {
                if (!hasParameterConstraintError() && !hasConflictingConstraints() && !hasCannotCaptureTypesError()) {
                    List list = this.this$0.errors;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (((ConstraintError) it.next()) instanceof TypeInferenceError) {
                            }
                        }
                    }
                    return false;
                }
                return true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasViolatedUpperBound() {
                return !isSuccessful() && ConstraintSystemUtilsKt.filterConstraintsOut(this.this$0, ConstraintPositionKind.TYPE_BOUND_POSITION).getStatus().isSuccessful();
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasConflictingConstraints() {
                Collection collectionValues = this.this$0.getLocalTypeParameterBounds().values();
                if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                    return false;
                }
                Iterator it = collectionValues.iterator();
                while (it.hasNext()) {
                    if (((TypeBoundsImpl) it.next()).getValues().size() > 1) {
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Code duplicated, block: B:16:0x003d A[ORIG_RETURN, RETURN] */
            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasUnknownParameters() {
                Collection collectionValues = this.this$0.getLocalTypeParameterBounds().values();
                if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
                    Iterator it = collectionValues.iterator();
                    while (it.hasNext()) {
                        if (((TypeBoundsImpl) it.next()).getValues().isEmpty()) {
                        }
                    }
                    if (hasTypeParameterWithUnsatisfiedOnlyInputTypesError()) {
                        return false;
                    }
                } else if (hasTypeParameterWithUnsatisfiedOnlyInputTypesError()) {
                    return false;
                }
                return true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasParameterConstraintError() {
                List list = this.this$0.errors;
                if ((list instanceof Collection) && list.isEmpty()) {
                    return false;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((ConstraintError) it.next()) instanceof ParameterConstraintError) {
                        return true;
                    }
                }
                return false;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasOnlyErrorsDerivedFrom(ConstraintPositionKind kind) {
                Intrinsics.checkNotNullParameter(kind, "kind");
                if (isSuccessful()) {
                    return false;
                }
                if (ConstraintSystemUtilsKt.filterConstraintsOut(this.this$0, kind).getStatus().isSuccessful()) {
                    return true;
                }
                if (this.this$0.errors.isEmpty()) {
                    return false;
                }
                List list = this.this$0.errors;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (!ConstraintPositionKt.derivedFrom(((ConstraintError) it.next()).getConstraintPosition(), kind)) {
                            return false;
                        }
                    }
                }
                return true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasErrorInConstrainingTypes() {
                List list = this.this$0.errors;
                if ((list instanceof Collection) && list.isEmpty()) {
                    return false;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((ConstraintError) it.next()) instanceof ErrorInConstrainingType) {
                        return true;
                    }
                }
                return false;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasCannotCaptureTypesError() {
                List list = this.this$0.errors;
                if ((list instanceof Collection) && list.isEmpty()) {
                    return false;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((ConstraintError) it.next()) instanceof CannotCapture) {
                        return true;
                    }
                }
                return false;
            }

            /* JADX WARN: Code duplicated, block: B:16:0x0035 A[ORIG_RETURN, RETURN] */
            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasTypeInferenceIncorporationError() {
                List list = this.this$0.errors;
                if ((list instanceof Collection) && list.isEmpty()) {
                    if (!this.this$0.satisfyInitialConstraints()) {
                        return false;
                    }
                } else {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (((ConstraintError) it.next()) instanceof TypeInferenceError) {
                        }
                    }
                    if (!this.this$0.satisfyInitialConstraints()) {
                        return false;
                    }
                }
                return true;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public boolean hasTypeParameterWithUnsatisfiedOnlyInputTypesError() {
                Collection<TypeBoundsImpl> collectionValues = this.this$0.getLocalTypeParameterBounds().values();
                if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                    return false;
                }
                for (TypeBoundsImpl typeBoundsImpl : collectionValues) {
                    if (typeBoundsImpl.getTypeVariable().hasOnlyInputTypesAnnotation() && typeBoundsImpl.getValue() == null) {
                        return true;
                    }
                }
                return false;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus
            public List<ConstraintError> getConstraintErrors() {
                return this.this$0.errors;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<TypeVariable, TypeBoundsImpl> getLocalTypeParameterBounds() {
        Map<TypeVariable, TypeBoundsImpl> map = this.allTypeParameterBounds;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<TypeVariable, TypeBoundsImpl> entry : map.entrySet()) {
            if (!entry.getKey().getIsExternal()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem
    public ConstraintSystemStatus getStatus() {
        return this.status;
    }

    private final Map<TypeConstructor, TypeProjection> getParameterToInferredValueMap(Map<TypeVariable, TypeBoundsImpl> typeParameterBounds, Function1<? super TypeVariable, ? extends KotlinType> getDefaultType, boolean substituteOriginal) {
        TypeConstructor constructor;
        HashMap map = new HashMap();
        for (Map.Entry<TypeVariable, TypeBoundsImpl> entry : typeParameterBounds.entrySet()) {
            TypeVariable key = entry.getKey();
            KotlinType value = entry.getValue().getValue();
            if (substituteOriginal) {
                constructor = key.getOriginalTypeParameter().getTypeConstructor();
            } else {
                constructor = key.getType().getConstructor();
            }
            Intrinsics.checkNotNull(constructor);
            if (value == null || TypeUtils.contains(value, TypeUtils.DONT_CARE)) {
                value = getDefaultType.invoke(key);
            }
            map.put(constructor, new TypeProjectionImpl(value));
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem
    public Set<TypeVariable> getTypeVariables() {
        return this.allTypeParameterBounds.keySet();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem
    public TypeBoundsImpl getTypeBounds(TypeVariable typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        TypeBoundsImpl typeBoundsImpl = this.allTypeParameterBounds.get(typeVariable);
        if (typeBoundsImpl != null) {
            return typeBoundsImpl;
        }
        throw new IllegalArgumentException("TypeParameterDescriptor is not a type variable for constraint system: " + typeVariable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem
    public TypeSubstitutor getResultingSubstitutor() {
        return getSubstitutor(true, new Function1<TypeVariable, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemImpl$resultingSubstitutor$1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(TypeVariable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ErrorTypeKind errorTypeKind = ErrorTypeKind.UNINFERRED_TYPE_VARIABLE;
                String strAsString = it.getOriginalTypeParameter().getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return ErrorUtils.createErrorType(errorTypeKind, strAsString);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem
    public TypeSubstitutor getCurrentSubstitutor() {
        return getSubstitutor(true, new Function1<TypeVariable, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemImpl$currentSubstitutor$1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(TypeVariable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SimpleType DONT_CARE = TypeUtils.DONT_CARE;
                Intrinsics.checkNotNullExpressionValue(DONT_CARE, "DONT_CARE");
                return DONT_CARE;
            }
        });
    }

    private final TypeSubstitutor getSubstitutor(boolean substituteOriginal, Function1<? super TypeVariable, ? extends KotlinType> getDefaultValue) {
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new SubstitutionWithCapturedTypeApproximation(new SubstitutionFilteringInternalResolveAnnotations(TypeConstructorSubstitution.Companion.createByConstructorsMap$default(TypeConstructorSubstitution.INSTANCE, getParameterToInferredValueMap(this.allTypeParameterBounds, getDefaultValue, substituteOriginal), false, 2, null))));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        return typeSubstitutorCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean satisfyInitialConstraints() {
        KotlinType kotlinTypeSatisfyInitialConstraints$substitute;
        boolean zIsSubtypeOf;
        TypeSubstitutor substitutor = getSubstitutor(false, new Function1<TypeVariable, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemImpl$satisfyInitialConstraints$substitutor$1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(TypeVariable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ErrorTypeKind errorTypeKind = ErrorTypeKind.UNINFERRED_TYPE_VARIABLE;
                String strAsString = it.getOriginalTypeParameter().getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return ErrorUtils.createErrorType(errorTypeKind, strAsString);
            }
        });
        List<ConstraintSystemBuilderImpl.Constraint> list = this.initialConstraints;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (ConstraintSystemBuilderImpl.Constraint constraint : list) {
                ConstraintSystemBuilderImpl.ConstraintKind kind = constraint.getKind();
                KotlinType subtype = constraint.getSubtype();
                KotlinType superType = constraint.getSuperType();
                ConstraintPosition position = constraint.getPosition();
                KotlinType kotlinTypeSatisfyInitialConstraints$substitute2 = satisfyInitialConstraints$substitute(subtype, substitutor);
                if (kotlinTypeSatisfyInitialConstraints$substitute2 == null) {
                    return false;
                }
                if (position.getKind() == ConstraintPositionKind.RECEIVER_POSITION) {
                    kotlinTypeSatisfyInitialConstraints$substitute2 = TypeUtilsKt.makeNotNullable(kotlinTypeSatisfyInitialConstraints$substitute2);
                }
                if (kotlinTypeSatisfyInitialConstraints$substitute2 == null || (kotlinTypeSatisfyInitialConstraints$substitute = satisfyInitialConstraints$substitute(superType, substitutor)) == null) {
                    return false;
                }
                int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
                if (i == 1) {
                    zIsSubtypeOf = KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinTypeSatisfyInitialConstraints$substitute2, kotlinTypeSatisfyInitialConstraints$substitute);
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    zIsSubtypeOf = KotlinTypeChecker.DEFAULT.equalTypes(kotlinTypeSatisfyInitialConstraints$substitute2, kotlinTypeSatisfyInitialConstraints$substitute);
                }
                if (!zIsSubtypeOf) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final KotlinType satisfyInitialConstraints$substitute(KotlinType kotlinType, TypeSubstitutor typeSubstitutor) {
        return typeSubstitutor.substitute(kotlinType, Variance.INVARIANT);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem
    public ConstraintSystem.Builder toBuilder(Function1<? super ConstraintPosition, Boolean> filterConstraintPosition) {
        Intrinsics.checkNotNullParameter(filterConstraintPosition, "filterConstraintPosition");
        ConstraintSystemBuilderImpl constraintSystemBuilderImpl = new ConstraintSystemBuilderImpl(null, 1, null);
        for (Map.Entry<TypeVariable, TypeBoundsImpl> entry : this.allTypeParameterBounds.entrySet()) {
            constraintSystemBuilderImpl.getAllTypeParameterBounds$frontend().put(entry.getKey(), entry.getValue().filter(filterConstraintPosition));
        }
        HashMap<TypeVariable, List<TypeBounds.Bound>> usedInBounds$frontend = constraintSystemBuilderImpl.getUsedInBounds$frontend();
        Map<TypeVariable, List<TypeBounds.Bound>> map = this.usedInBounds;
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<TypeVariable, List<TypeBounds.Bound>> entry2 : map.entrySet()) {
            TypeVariable key = entry2.getKey();
            List<TypeBounds.Bound> value = entry2.getValue();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : value) {
                if (filterConstraintPosition.invoke(((TypeBounds.Bound) obj).getPosition()).booleanValue()) {
                    arrayList2.add(obj);
                }
            }
            arrayList.add(TuplesKt.to(key, arrayList2));
        }
        usedInBounds$frontend.putAll(MapsKt.toMap(arrayList));
        ArrayList<ConstraintError> errors$frontend = constraintSystemBuilderImpl.getErrors$frontend();
        List<ConstraintError> list = this.errors;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (filterConstraintPosition.invoke(((ConstraintError) obj2).getConstraintPosition()).booleanValue()) {
                arrayList3.add(obj2);
            }
        }
        errors$frontend.addAll(arrayList3);
        ArrayList<ConstraintSystemBuilderImpl.Constraint> initialConstraints$frontend = constraintSystemBuilderImpl.getInitialConstraints$frontend();
        List<ConstraintSystemBuilderImpl.Constraint> list2 = this.initialConstraints;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : list2) {
            if (filterConstraintPosition.invoke(((ConstraintSystemBuilderImpl.Constraint) obj3).getPosition()).booleanValue()) {
                arrayList4.add(obj3);
            }
        }
        initialConstraints$frontend.addAll(arrayList4);
        constraintSystemBuilderImpl.getTypeVariableSubstitutors().putAll(this.typeVariableSubstitutors);
        return constraintSystemBuilderImpl;
    }
}
