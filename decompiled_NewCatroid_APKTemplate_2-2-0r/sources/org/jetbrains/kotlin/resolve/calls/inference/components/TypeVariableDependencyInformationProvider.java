package org.jetbrains.kotlin.resolve.calls.inference.components;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: TypeVariableDependencyInformationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B9\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ&\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0016\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J\b\u0010\u001e\u001a\u00020\u0016H\u0002J\b\u0010\u001f\u001a\u00020\u0016H\u0002J\b\u0010 \u001a\u00020\u0016H\u0002J\b\u0010!\u001a\u00020\u0016H\u0002J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040#2\u0006\u0010$\u001a\u00020\u0004H\u0002J\u0016\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00102\u0006\u0010&\u001a\u00020\u0004J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00040#2\u0006\u0010$\u001a\u00020\u0004H\u0002J\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00102\u0006\u0010&\u001a\u00020\u0004J\u0010\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020\u0004H\u0002J\u000e\u0010+\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u0004J\u000e\u0010,\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u0004J \u0010-\u001a\u00020\u001a*\u00020\n2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00160/H\u0002R \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDependencyInformationProvider;", "", "notFixedTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "postponedKtPrimitives", "", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "topLevelType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "typeSystemContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "(Ljava/util/Map;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;)V", "deepTypeVariableDependencies", "", "", "postponeArgumentsEdges", "relatedToAllOutputTypes", "relatedToTopLevelType", "shallowTypeVariableDependencies", "addAllRelatedNodes", "", "to", "node", "includePostponedEdges", "", "areVariablesDependentShallowly", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "computeConstraintEdges", "computePostponeArgumentsEdges", "computeRelatedToAllOutputTypes", "computeRelatedToTopLevelType", "getConstraintEdges", "", TypedValues.TransitionType.S_FROM, "getDeeplyDependentVariables", "variable", "getPostponeEdges", "getShallowlyDependentVariables", "isMyTypeVariable", "typeConstructor", "isVariableRelatedToAnyOutputType", "isVariableRelatedToTopLevelType", "forAllMyTypeVariables", "action", "Lkotlin/Function1;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableDependencyInformationProvider {
    private final Map<TypeConstructorMarker, Set<TypeConstructorMarker>> deepTypeVariableDependencies;
    private final Map<TypeConstructorMarker, VariableWithConstraints> notFixedTypeVariables;
    private final Map<TypeConstructorMarker, Set<TypeConstructorMarker>> postponeArgumentsEdges;
    private final List<PostponedResolvedAtomMarker> postponedKtPrimitives;
    private final Set<TypeConstructorMarker> relatedToAllOutputTypes;
    private final Set<TypeConstructorMarker> relatedToTopLevelType;
    private final Map<TypeConstructorMarker, Set<TypeConstructorMarker>> shallowTypeVariableDependencies;
    private final KotlinTypeMarker topLevelType;
    private final TypeSystemInferenceExtensionContext typeSystemContext;

    /* JADX WARN: Multi-variable type inference failed */
    public TypeVariableDependencyInformationProvider(Map<TypeConstructorMarker, ? extends VariableWithConstraints> notFixedTypeVariables, List<? extends PostponedResolvedAtomMarker> postponedKtPrimitives, KotlinTypeMarker kotlinTypeMarker, TypeSystemInferenceExtensionContext typeSystemContext) {
        Intrinsics.checkNotNullParameter(notFixedTypeVariables, "notFixedTypeVariables");
        Intrinsics.checkNotNullParameter(postponedKtPrimitives, "postponedKtPrimitives");
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        this.notFixedTypeVariables = notFixedTypeVariables;
        this.postponedKtPrimitives = postponedKtPrimitives;
        this.topLevelType = kotlinTypeMarker;
        this.typeSystemContext = typeSystemContext;
        this.deepTypeVariableDependencies = new HashMap();
        this.shallowTypeVariableDependencies = new HashMap();
        this.postponeArgumentsEdges = new HashMap();
        this.relatedToAllOutputTypes = new HashSet();
        this.relatedToTopLevelType = new HashSet();
        computeConstraintEdges();
        computePostponeArgumentsEdges();
        computeRelatedToAllOutputTypes();
        computeRelatedToTopLevelType();
    }

    public final boolean isVariableRelatedToTopLevelType(TypeConstructorMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return this.relatedToTopLevelType.contains(variable);
    }

    public final boolean isVariableRelatedToAnyOutputType(TypeConstructorMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return this.relatedToAllOutputTypes.contains(variable);
    }

    public final Set<TypeConstructorMarker> getDeeplyDependentVariables(TypeConstructorMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return this.deepTypeVariableDependencies.get(variable);
    }

    public final Set<TypeConstructorMarker> getShallowlyDependentVariables(TypeConstructorMarker variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return this.shallowTypeVariableDependencies.get(variable);
    }

    public final boolean areVariablesDependentShallowly(TypeConstructorMarker a, TypeConstructorMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        Set<TypeConstructorMarker> set = this.shallowTypeVariableDependencies.get(a);
        if (set == null) {
            return false;
        }
        Set<TypeConstructorMarker> set2 = set;
        if (!(set2 instanceof Collection) || !set2.isEmpty()) {
            Iterator<T> it = set2.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual((TypeConstructorMarker) it.next(), b)) {
                    return true;
                }
            }
        }
        Collection<Set<TypeConstructorMarker>> collectionValues = this.shallowTypeVariableDependencies.values();
        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
            Iterator<T> it2 = collectionValues.iterator();
            while (it2.hasNext()) {
                Set set3 = (Set) it2.next();
                if (set3.contains(a) && set3.contains(b)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void computeConstraintEdges$addConstraintEdgeForDeepDependency(TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
        Map<TypeConstructorMarker, Set<TypeConstructorMarker>> map = typeVariableDependencyInformationProvider.deepTypeVariableDependencies;
        LinkedHashSet linkedHashSet = map.get(typeConstructorMarker);
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet();
            map.put(typeConstructorMarker, linkedHashSet);
        }
        linkedHashSet.add(typeConstructorMarker2);
        Map<TypeConstructorMarker, Set<TypeConstructorMarker>> map2 = typeVariableDependencyInformationProvider.deepTypeVariableDependencies;
        LinkedHashSet linkedHashSet2 = map2.get(typeConstructorMarker2);
        if (linkedHashSet2 == null) {
            linkedHashSet2 = new LinkedHashSet();
            map2.put(typeConstructorMarker2, linkedHashSet2);
        }
        linkedHashSet2.add(typeConstructorMarker);
    }

    private static final void computeConstraintEdges$addConstraintEdgeForShallowDependency(TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
        Map<TypeConstructorMarker, Set<TypeConstructorMarker>> map = typeVariableDependencyInformationProvider.shallowTypeVariableDependencies;
        LinkedHashSet linkedHashSet = map.get(typeConstructorMarker);
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet();
            map.put(typeConstructorMarker, linkedHashSet);
        }
        linkedHashSet.add(typeConstructorMarker2);
        Map<TypeConstructorMarker, Set<TypeConstructorMarker>> map2 = typeVariableDependencyInformationProvider.shallowTypeVariableDependencies;
        LinkedHashSet linkedHashSet2 = map2.get(typeConstructorMarker2);
        if (linkedHashSet2 == null) {
            linkedHashSet2 = new LinkedHashSet();
            map2.put(typeConstructorMarker2, linkedHashSet2);
        }
        linkedHashSet2.add(typeConstructorMarker);
    }

    private final void computeConstraintEdges() {
        for (VariableWithConstraints variableWithConstraints : this.notFixedTypeVariables.values()) {
            final TypeConstructorMarker typeConstructorMarkerFreshTypeConstructor = MarkerExtensionsKt.freshTypeConstructor(variableWithConstraints.getTypeVariable(), this.typeSystemContext);
            for (Constraint constraint : variableWithConstraints.getConstraints()) {
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = TypeSystemContextHelpersKt.typeConstructor(constraint.getType(), this.typeSystemContext);
                forAllMyTypeVariables(constraint.getType(), new Function1<TypeConstructorMarker, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider.computeConstraintEdges.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(TypeConstructorMarker typeConstructorMarker) {
                        invoke2(typeConstructorMarker);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(TypeConstructorMarker it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (TypeVariableDependencyInformationProvider.this.isMyTypeVariable(it)) {
                            TypeVariableDependencyInformationProvider.computeConstraintEdges$addConstraintEdgeForDeepDependency(TypeVariableDependencyInformationProvider.this, typeConstructorMarkerFreshTypeConstructor, it);
                        }
                    }
                });
                if (isMyTypeVariable(typeConstructorMarkerTypeConstructor)) {
                    computeConstraintEdges$addConstraintEdgeForShallowDependency(this, typeConstructorMarkerFreshTypeConstructor, typeConstructorMarkerTypeConstructor);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void computePostponeArgumentsEdges$addPostponeArgumentsEdges(TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider, TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
        Map<TypeConstructorMarker, Set<TypeConstructorMarker>> map = typeVariableDependencyInformationProvider.postponeArgumentsEdges;
        HashSet hashSet = map.get(typeConstructorMarker);
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(typeConstructorMarker, hashSet);
        }
        hashSet.add(typeConstructorMarker2);
    }

    private final void computePostponeArgumentsEdges() {
        for (PostponedResolvedAtomMarker postponedResolvedAtomMarker : this.postponedKtPrimitives) {
            if (!postponedResolvedAtomMarker.getAnalyzed()) {
                final SmartSet smartSetCreate = SmartSet.INSTANCE.create();
                KotlinTypeMarker returnType = postponedResolvedAtomMarker.getReturnType();
                if (returnType != null) {
                    forAllMyTypeVariables(returnType, new Function1<TypeConstructorMarker, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider.computePostponeArgumentsEdges.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(TypeConstructorMarker typeConstructorMarker) {
                            invoke2(typeConstructorMarker);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(TypeConstructorMarker it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            smartSetCreate.add(it);
                        }
                    });
                    if (!smartSetCreate.isEmpty()) {
                        Iterator<KotlinTypeMarker> it = postponedResolvedAtomMarker.getInputTypes().iterator();
                        while (it.hasNext()) {
                            forAllMyTypeVariables(it.next(), new Function1<TypeConstructorMarker, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider.computePostponeArgumentsEdges.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(TypeConstructorMarker typeConstructorMarker) {
                                    invoke2(typeConstructorMarker);
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(TypeConstructorMarker from) {
                                    Intrinsics.checkNotNullParameter(from, "from");
                                    Iterator<TypeConstructorMarker> it2 = smartSetCreate.iterator();
                                    while (it2.hasNext()) {
                                        TypeVariableDependencyInformationProvider.computePostponeArgumentsEdges$addPostponeArgumentsEdges(this, from, it2.next());
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    private final void computeRelatedToAllOutputTypes() {
        KotlinTypeMarker returnType;
        for (PostponedResolvedAtomMarker postponedResolvedAtomMarker : this.postponedKtPrimitives) {
            if (!postponedResolvedAtomMarker.getAnalyzed() && (returnType = postponedResolvedAtomMarker.getReturnType()) != null) {
                forAllMyTypeVariables(returnType, new Function1<TypeConstructorMarker, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider.computeRelatedToAllOutputTypes.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(TypeConstructorMarker typeConstructorMarker) {
                        invoke2(typeConstructorMarker);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(TypeConstructorMarker it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider = TypeVariableDependencyInformationProvider.this;
                        typeVariableDependencyInformationProvider.addAllRelatedNodes(typeVariableDependencyInformationProvider.relatedToAllOutputTypes, it, false);
                    }
                });
            }
        }
    }

    private final void computeRelatedToTopLevelType() {
        KotlinTypeMarker kotlinTypeMarker = this.topLevelType;
        if (kotlinTypeMarker == null) {
            return;
        }
        forAllMyTypeVariables(kotlinTypeMarker, new Function1<TypeConstructorMarker, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider.computeRelatedToTopLevelType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(TypeConstructorMarker typeConstructorMarker) {
                invoke2(typeConstructorMarker);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(TypeConstructorMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider = TypeVariableDependencyInformationProvider.this;
                typeVariableDependencyInformationProvider.addAllRelatedNodes(typeVariableDependencyInformationProvider.relatedToTopLevelType, it, true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isMyTypeVariable(TypeConstructorMarker typeConstructor) {
        return this.notFixedTypeVariables.containsKey(typeConstructor);
    }

    private final boolean forAllMyTypeVariables(KotlinTypeMarker kotlinTypeMarker, final Function1<? super TypeConstructorMarker, Unit> function1) {
        final TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.typeSystemContext;
        return typeSystemInferenceExtensionContext.contains(kotlinTypeMarker, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDependencyInformationProvider$forAllMyTypeVariables$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemInferenceExtensionContext.typeConstructor(it);
                if (this.isMyTypeVariable(typeConstructorMarkerTypeConstructor)) {
                    function1.invoke(typeConstructorMarkerTypeConstructor);
                }
                return false;
            }
        });
    }

    private final Set<TypeConstructorMarker> getConstraintEdges(TypeConstructorMarker from) {
        Set<TypeConstructorMarker> set = this.deepTypeVariableDependencies.get(from);
        return set == null ? SetsKt.emptySet() : set;
    }

    private final Set<TypeConstructorMarker> getPostponeEdges(TypeConstructorMarker from) {
        Set<TypeConstructorMarker> set = this.postponeArgumentsEdges.get(from);
        return set == null ? SetsKt.emptySet() : set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addAllRelatedNodes(Set<TypeConstructorMarker> to, TypeConstructorMarker node, boolean includePostponedEdges) {
        if (to.add(node)) {
            Iterator<TypeConstructorMarker> it = getConstraintEdges(node).iterator();
            while (it.hasNext()) {
                addAllRelatedNodes(to, it.next(), includePostponedEdges);
            }
            if (includePostponedEdges) {
                Iterator<TypeConstructorMarker> it2 = getPostponeEdges(node).iterator();
                while (it2.hasNext()) {
                    addAllRelatedNodes(to, it2.next(), includePostponedEdges);
                }
            }
        }
    }
}
