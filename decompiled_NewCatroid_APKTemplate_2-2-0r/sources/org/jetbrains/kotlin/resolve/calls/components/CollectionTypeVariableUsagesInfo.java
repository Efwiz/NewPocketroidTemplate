package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionPart;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.WrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002JF\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e0\u000b*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000f2\u001c\b\u0002\u0010\u0013\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e0\u000bH\u0002J\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000b*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0015H\u0002J\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u0015*\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u000fH\u0002J&\u0010\u0018\u001a\u00020\u0004*\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u0004H\u0002J0\u0010\u001c\u001a\u00020\u0004*\u00020\u00112\u0006\u0010\b\u001a\u00020\u000f2\u001a\u0010\u001d\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e0\u000bH\u0002J\u001e\u0010\u001e\u001a\u00020\u0004*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\t2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0002J\u0014\u0010!\u001a\u00020\"*\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\f\u0010&\u001a\u00020\"*\u00020\tH\u0002R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CollectionTypeVariableUsagesInfo;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "()V", "isComputed", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)Z", "isContainedInInvariantOrContravariantPositionsAmongTypeParameters", "checkingType", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableFromCallableDescriptor;", "typeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getDependentTypeParameters", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "variable", "dependentTypeParametersSeen", "getDependingOnTypeParameter", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getTypeParameterByVariable", "typeConstructor", "isContainedInInvariantOrContravariantPositions", "variableTypeConstructor", "baseType", "wasOutVariance", "isContainedInInvariantOrContravariantPositionsAmongUpperBound", "dependentTypeParameters", "isContainedInInvariantOrContravariantPositionsWithDependencies", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "process", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "workIndex", "", "recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionTypeVariableUsagesInfo extends ResolutionPart {
    public static final CollectionTypeVariableUsagesInfo INSTANCE = new CollectionTypeVariableUsagesInfo();

    private CollectionTypeVariableUsagesInfo() {
    }

    private final boolean isComputed(KotlinType kotlinType) {
        return !(kotlinType instanceof WrappedType) || ((WrappedType) kotlinType).isComputed();
    }

    static /* synthetic */ boolean isContainedInInvariantOrContravariantPositions$default(CollectionTypeVariableUsagesInfo collectionTypeVariableUsagesInfo, NewConstraintSystem newConstraintSystem, TypeConstructorMarker typeConstructorMarker, KotlinTypeMarker kotlinTypeMarker, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return collectionTypeVariableUsagesInfo.isContainedInInvariantOrContravariantPositions(newConstraintSystem, typeConstructorMarker, kotlinTypeMarker, z);
    }

    private final boolean isContainedInInvariantOrContravariantPositions(NewConstraintSystem newConstraintSystem, TypeConstructorMarker typeConstructorMarker, KotlinTypeMarker kotlinTypeMarker, boolean z) {
        TypeConstructor typeParameterByVariable;
        if (!(kotlinTypeMarker instanceof KotlinType) || (typeParameterByVariable = getTypeParameterByVariable(newConstraintSystem, typeConstructorMarker)) == null) {
            return false;
        }
        KotlinType kotlinType = (KotlinType) kotlinTypeMarker;
        List<TypeParameterDescriptor> parameters = kotlinType.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        if (parameters.size() < kotlinType.getArguments().size()) {
            return false;
        }
        int i = 0;
        for (TypeProjection typeProjection : kotlinType.getArguments()) {
            int i2 = i + 1;
            if (!typeProjection.isStarProjection() && !typeProjection.getNullableAnyType().getIsMarkedNullable()) {
                boolean z2 = z && (parameters.get(i).getVariance() == Variance.OUT_VARIANCE || typeProjection.getProjectionKind() == Variance.OUT_VARIANCE);
                if ((Intrinsics.areEqual(typeProjection.getNullableAnyType().getConstructor(), typeParameterByVariable) || Intrinsics.areEqual(typeProjection.getNullableAnyType().getConstructor(), typeConstructorMarker)) && !z2) {
                    return true;
                }
                KotlinType type = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (isContainedInInvariantOrContravariantPositions(newConstraintSystem, typeConstructorMarker, type, z2)) {
                    return true;
                }
            }
            i = i2;
        }
        return false;
    }

    private final boolean isContainedInInvariantOrContravariantPositionsAmongTypeParameters(TypeVariableFromCallableDescriptor checkingType, List<? extends TypeParameterDescriptor> typeParameters) {
        List<? extends TypeParameterDescriptor> list = typeParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            if (typeParameterDescriptor.getVariance() != Variance.OUT_VARIANCE && Intrinsics.areEqual(typeParameterDescriptor.getTypeConstructor(), checkingType.getOriginalTypeParameter().getTypeConstructor())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ List getDependentTypeParameters$default(CollectionTypeVariableUsagesInfo collectionTypeVariableUsagesInfo, NewConstraintSystem newConstraintSystem, TypeConstructorMarker typeConstructorMarker, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return collectionTypeVariableUsagesInfo.getDependentTypeParameters(newConstraintSystem, typeConstructorMarker, list);
    }

    private final List<Pair<TypeConstructorMarker, KotlinTypeMarker>> getDependentTypeParameters(NewConstraintSystem newConstraintSystem, final TypeConstructorMarker typeConstructorMarker, final List<? extends Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>> list) {
        List<Pair<TypeConstructorMarker, KotlinTypeMarker>> listEmptyList;
        final ConstraintSystemCompletionContext constraintSystemCompletionContextAsConstraintSystemCompleterContext = newConstraintSystem.asConstraintSystemCompleterContext();
        List list2 = SequencesKt.toList(SequencesKt.filter(SequencesKt.flatMapIterable(MapsKt.asSequence(newConstraintSystem.getBuilder().currentStorage().getNotFixedTypeVariables()), new Function1<Map.Entry<? extends TypeConstructorMarker, ? extends VariableWithConstraints>, List<? extends Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>>>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CollectionTypeVariableUsagesInfo$getDependentTypeParameters$dependentTypeParameters$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<Pair<TypeConstructorMarker, KotlinTypeMarker>> invoke(Map.Entry<? extends TypeConstructorMarker, ? extends VariableWithConstraints> entry) {
                Object next;
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                TypeConstructorMarker key = entry.getKey();
                List<Constraint> constraints = entry.getValue().getConstraints();
                ArrayList arrayList = new ArrayList();
                for (Object obj : constraints) {
                    Constraint constraint = (Constraint) obj;
                    if ((constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPositionImpl) && constraint.getKind() == ConstraintKind.UPPER) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                final ConstraintSystemCompletionContext constraintSystemCompletionContext = constraintSystemCompletionContextAsConstraintSystemCompleterContext;
                final TypeConstructorMarker typeConstructorMarker2 = typeConstructorMarker;
                ArrayList arrayList3 = new ArrayList();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    Pair pair = null;
                    if (!Intrinsics.areEqual(TypeSystemContextHelpersKt.typeConstructor(((Constraint) it.next()).getType(), constraintSystemCompletionContext), typeConstructorMarker2)) {
                        Iterator it2 = arrayList2.iterator();
                        do {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                        } while (!constraintSystemCompletionContext.contains(((Constraint) next).getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CollectionTypeVariableUsagesInfo$getDependentTypeParameters$dependentTypeParameters$1$1$suitableUpperBound$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(KotlinTypeMarker it3) {
                                Intrinsics.checkNotNullParameter(it3, "it");
                                return Boolean.valueOf(Intrinsics.areEqual(constraintSystemCompletionContext.typeConstructor(it3), typeConstructorMarker2));
                            }
                        }));
                        Constraint constraint2 = (Constraint) next;
                        KotlinTypeMarker type = constraint2 != null ? constraint2.getType() : null;
                        if (type != null) {
                            pair = TuplesKt.to(key, type);
                        }
                    } else {
                        pair = TuplesKt.to(key, null);
                    }
                    if (pair != null) {
                        arrayList3.add(pair);
                    }
                }
                return arrayList3;
            }
        }), new Function1<Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CollectionTypeVariableUsagesInfo$getDependentTypeParameters$dependentTypeParameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((list.contains(it) || Intrinsics.areEqual(it.getFirst(), typeConstructorMarker)) ? false : true);
            }
        }));
        List list3 = list2;
        SmartList smartList = new SmartList();
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            TypeConstructorMarker typeConstructorMarker2 = (TypeConstructorMarker) ((Pair) it.next()).component1();
            if (!Intrinsics.areEqual(typeConstructorMarker2, typeConstructorMarker)) {
                listEmptyList = INSTANCE.getDependentTypeParameters(newConstraintSystem, typeConstructorMarker2, CollectionsKt.plus((Collection) list3, (Iterable) list));
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(smartList, listEmptyList);
        }
        return CollectionsKt.plus((Collection) list3, (Iterable) smartList);
    }

    private final boolean isContainedInInvariantOrContravariantPositionsAmongUpperBound(NewConstraintSystem newConstraintSystem, TypeConstructorMarker typeConstructorMarker, List<? extends Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>> list) {
        List<? extends Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        TypeConstructorMarker typeConstructorMarker2 = typeConstructorMarker;
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            TypeConstructorMarker typeConstructorMarker3 = (TypeConstructorMarker) pair.component1();
            KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) pair.component2();
            if (kotlinTypeMarker == null || isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystem, typeConstructorMarker2, kotlinTypeMarker, false, 4, null)) {
                return true;
            }
            typeConstructorMarker2 = typeConstructorMarker3;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TypeConstructor getTypeParameterByVariable(NewConstraintSystem newConstraintSystem, TypeConstructorMarker typeConstructorMarker) {
        TypeParameterDescriptor originalTypeParameter;
        TypeVariableMarker typeVariableMarker = newConstraintSystem.getBuilder().currentStorage().getAllTypeVariables().get(typeConstructorMarker);
        TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = typeVariableMarker instanceof TypeVariableFromCallableDescriptor ? (TypeVariableFromCallableDescriptor) typeVariableMarker : null;
        if (typeVariableFromCallableDescriptor == null || (originalTypeParameter = typeVariableFromCallableDescriptor.getOriginalTypeParameter()) == null) {
            return null;
        }
        return originalTypeParameter.getTypeConstructor();
    }

    private final List<TypeConstructorMarker> getDependingOnTypeParameter(NewConstraintSystem newConstraintSystem, TypeConstructor typeConstructor) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = newConstraintSystem.getBuilder().currentStorage().getNotFixedTypeVariables().get(typeConstructor);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Constraint constraint : constraints) {
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = ((constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPositionImpl) && constraint.getKind() == ConstraintKind.UPPER) ? TypeSystemContextHelpersKt.typeConstructor(constraint.getType(), newConstraintSystem.asConstraintSystemCompleterContext()) : null;
            if (typeConstructorMarkerTypeConstructor != null) {
                arrayList.add(typeConstructorMarkerTypeConstructor);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:42:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:46:0x00ed A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:50:0x00ef A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:? A[LOOP:0: B:40:0x00c7->B:51:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:58:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    private final boolean isContainedInInvariantOrContravariantPositionsWithDependencies(final NewConstraintSystem newConstraintSystem, TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor, DeclarationDescriptor declarationDescriptor) {
        KotlinType returnType;
        boolean z;
        Iterator<T> it;
        if (!(declarationDescriptor instanceof CallableDescriptor) || (returnType = ((CallableDescriptor) declarationDescriptor).getReturnType()) == null || !isComputed(returnType)) {
            return false;
        }
        TypeVariableTypeConstructor freshTypeConstructor = typeVariableFromCallableDescriptor.getFreshTypeConstructor();
        TypeVariableTypeConstructor typeVariableTypeConstructor = freshTypeConstructor;
        List<? extends Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>> dependentTypeParameters$default = getDependentTypeParameters$default(this, newConstraintSystem, typeVariableTypeConstructor, null, 2, null);
        List<TypeConstructorMarker> dependingOnTypeParameter = getDependingOnTypeParameter(newConstraintSystem, freshTypeConstructor);
        boolean zIsContainedInInvariantOrContravariantPositionsAmongUpperBound = isContainedInInvariantOrContravariantPositionsAmongUpperBound(newConstraintSystem, typeVariableTypeConstructor, dependentTypeParameters$default);
        List<? extends Pair<? extends TypeConstructorMarker, ? extends KotlinTypeMarker>> list = dependentTypeParameters$default;
        boolean z2 = list instanceof Collection;
        if (!z2 || !list.isEmpty()) {
            Iterator<T> it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                final TypeConstructorMarker typeConstructorMarker = (TypeConstructorMarker) ((Pair) it2.next()).component1();
                if (TypeUtilsKt.contains(returnType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CollectionTypeVariableUsagesInfo$isContainedInInvariantOrContravariantPositionsWithDependencies$isContainedAnyDependentTypeInReturnType$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it3) {
                        Intrinsics.checkNotNullParameter(it3, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(TypeSystemContextHelpersKt.typeConstructor(it3, newConstraintSystem.asConstraintSystemCompleterContext()), CollectionTypeVariableUsagesInfo.INSTANCE.getTypeParameterByVariable(newConstraintSystem, typeConstructorMarker)) && !it3.getIsMarkedNullable());
                    }
                })) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        KotlinType kotlinType = returnType;
        if (!isContainedInInvariantOrContravariantPositions$default(this, newConstraintSystem, typeVariableTypeConstructor, kotlinType, false, 4, null)) {
            List<TypeConstructorMarker> list2 = dependingOnTypeParameter;
            if ((list2 instanceof Collection) && list2.isEmpty()) {
                if (!z2) {
                    it = list.iterator();
                    while (it.hasNext()) {
                        if (isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystem, (TypeConstructorMarker) ((Pair) it.next()).getFirst(), kotlinType, false, 4, null)) {
                        }
                    }
                    if (z) {
                        return false;
                    }
                    return false;
                }
                it = list.iterator();
                while (it.hasNext()) {
                    if (isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystem, (TypeConstructorMarker) ((Pair) it.next()).getFirst(), kotlinType, false, 4, null)) {
                    }
                }
                if (z) {
                    return false;
                }
                return false;
            }
            Iterator<T> it3 = list2.iterator();
            while (it3.hasNext()) {
                if (isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystem, (TypeConstructorMarker) it3.next(), kotlinType, false, 4, null)) {
                }
            }
            if (!z2 && list.isEmpty()) {
                if (z) {
                    return false;
                }
                return false;
            }
            it = list.iterator();
            while (it.hasNext()) {
                if (isContainedInInvariantOrContravariantPositions$default(INSTANCE, newConstraintSystem, (TypeConstructorMarker) ((Pair) it.next()).getFirst(), kotlinType, false, 4, null)) {
                }
            }
            if (z || !zIsContainedInInvariantOrContravariantPositionsAmongUpperBound) {
                return false;
            }
        }
        return true;
    }

    private final void recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter(TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor) {
        typeVariableFromCallableDescriptor.getFreshTypeConstructor().setContainedInInvariantOrContravariantPositions(true);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
    public void process(ResolutionCandidate resolutionCandidate, int i) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        for (TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor : resolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor().getFreshVariables()) {
            CallableDescriptor callableDescriptor = resolutionCandidate.getResolvedCall().get_candidateDescriptor();
            if (callableDescriptor instanceof ClassConstructorDescriptor) {
                List<TypeParameterDescriptor> declaredTypeParameters = ((ClassConstructorDescriptor) callableDescriptor).getContainingModule().getDeclaredTypeParameters();
                Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
                if (isContainedInInvariantOrContravariantPositionsAmongTypeParameters(typeVariableFromCallableDescriptor, declaredTypeParameters)) {
                    recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter(typeVariableFromCallableDescriptor);
                }
            } else if (isContainedInInvariantOrContravariantPositionsWithDependencies(resolutionCandidate.getSystem(), typeVariableFromCallableDescriptor, resolutionCandidate.getResolvedCall().get_candidateDescriptor())) {
                recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter(typeVariableFromCallableDescriptor);
            }
        }
    }
}
