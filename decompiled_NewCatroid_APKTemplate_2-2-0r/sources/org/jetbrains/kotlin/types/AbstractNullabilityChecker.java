package org.jetbrains.kotlin.types;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bJ\u0016\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0011J\u0016\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\u0011J \u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0002J\u001a\u0010\u0015\u001a\u00020\u0004*\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0003\u001a\u00020\u0004*\u00020\u00132\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractNullabilityChecker;", "", "()V", "hasPathByNotMarkedNullableNodes", "", "state", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "start", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "end", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "isApplicableAsEndNode", ModuleXmlParser.TYPE, "isPossibleSubtype", "subType", "superType", "isSubtypeOfAny", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "context", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "runIsPossibleSubtype", "hasNotNullSupertype", "supertypesPolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbstractNullabilityChecker {
    public static final AbstractNullabilityChecker INSTANCE = new AbstractNullabilityChecker();

    private AbstractNullabilityChecker() {
    }

    public final boolean isPossibleSubtype(TypeCheckerState state, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return runIsPossibleSubtype(state, subType, superType);
    }

    public final boolean isSubtypeOfAny(TypeCheckerProviderContext context, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        return INSTANCE.isSubtypeOfAny(context.newTypeCheckerState(false, true), type);
    }

    public final boolean isSubtypeOfAny(TypeCheckerState state, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(type, "type");
        return INSTANCE.hasNotNullSupertype(state, state.getTypeSystemContext().lowerBoundIfFlexible(type), TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE);
    }

    private final boolean runIsPossibleSubtype(TypeCheckerState state, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            if (!typeSystemContext.isSingleClassifierType(subType) && !typeSystemContext.isIntersection(typeSystemContext.typeConstructor(subType))) {
                state.isAllowedTypeVariable(subType);
            }
            if (!typeSystemContext.isSingleClassifierType(superType)) {
                state.isAllowedTypeVariable(superType);
            }
        }
        if (typeSystemContext.isMarkedNullable(superType)) {
            return true;
        }
        SimpleTypeMarker simpleTypeMarker = subType;
        if (typeSystemContext.isDefinitelyNotNullType(simpleTypeMarker) || typeSystemContext.isNotNullTypeParameter(simpleTypeMarker)) {
            return true;
        }
        if ((subType instanceof CapturedTypeMarker) && typeSystemContext.isProjectionNotNull((CapturedTypeMarker) subType)) {
            return true;
        }
        AbstractNullabilityChecker abstractNullabilityChecker = INSTANCE;
        if (abstractNullabilityChecker.hasNotNullSupertype(state, subType, TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE)) {
            return true;
        }
        if (typeSystemContext.isDefinitelyNotNullType(superType) || abstractNullabilityChecker.hasNotNullSupertype(state, superType, TypeCheckerState.SupertypesPolicy.UpperIfFlexible.INSTANCE) || typeSystemContext.isClassType(subType)) {
            return false;
        }
        return abstractNullabilityChecker.hasPathByNotMarkedNullableNodes(state, subType, typeSystemContext.typeConstructor(superType));
    }

    public final boolean hasNotNullSupertype(TypeCheckerState typeCheckerState, SimpleTypeMarker type, TypeCheckerState.SupertypesPolicy supertypesPolicy) {
        Intrinsics.checkNotNullParameter(typeCheckerState, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(supertypesPolicy, "supertypesPolicy");
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        if ((typeSystemContext.isClassType(type) && !typeSystemContext.isMarkedNullable(type)) || typeSystemContext.isDefinitelyNotNullType(type)) {
            return true;
        }
        typeCheckerState.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = typeCheckerState.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = typeCheckerState.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(type);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                TypeCheckerState.SupertypesPolicy.None none = typeSystemContext.isMarkedNullable(simpleTypeMarkerPop) ? TypeCheckerState.SupertypesPolicy.None.INSTANCE : supertypesPolicy;
                if (Intrinsics.areEqual(none, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    none = null;
                }
                if (none == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext2 = typeCheckerState.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = none.mo7474transformType(typeCheckerState, it.next());
                        if ((typeSystemContext.isClassType(simpleTypeMarkerMo7474transformType) && !typeSystemContext.isMarkedNullable(simpleTypeMarkerMo7474transformType)) || typeSystemContext.isDefinitelyNotNullType(simpleTypeMarkerMo7474transformType)) {
                            typeCheckerState.clear();
                            return true;
                        }
                        supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                    }
                }
            }
        }
        typeCheckerState.clear();
        return false;
    }

    public final boolean hasPathByNotMarkedNullableNodes(TypeCheckerProviderContext typeCheckerProviderContext, SimpleTypeMarker start, TypeConstructorMarker end) {
        Intrinsics.checkNotNullParameter(typeCheckerProviderContext, "<this>");
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        return hasPathByNotMarkedNullableNodes(typeCheckerProviderContext.newTypeCheckerState(false, true), start, end);
    }

    public final boolean hasPathByNotMarkedNullableNodes(TypeCheckerState state, SimpleTypeMarker start, TypeConstructorMarker end) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (INSTANCE.isApplicableAsEndNode(state, start, end)) {
            return true;
        }
        state.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = state.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = state.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(start);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + start + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                TypeCheckerState.SupertypesPolicy supertypesPolicy = typeSystemContext.isMarkedNullable(simpleTypeMarkerPop) ? TypeCheckerState.SupertypesPolicy.None.INSTANCE : TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (Intrinsics.areEqual(supertypesPolicy, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    supertypesPolicy = null;
                }
                if (supertypesPolicy == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext2 = state.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = supertypesPolicy.mo7474transformType(state, it.next());
                        if (INSTANCE.isApplicableAsEndNode(state, simpleTypeMarkerMo7474transformType, end)) {
                            state.clear();
                            return true;
                        }
                        supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                    }
                }
            }
        }
        state.clear();
        return false;
    }

    private final boolean isApplicableAsEndNode(TypeCheckerState state, SimpleTypeMarker type, TypeConstructorMarker end) {
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (typeSystemContext.isNothing(type)) {
            return true;
        }
        if (typeSystemContext.isMarkedNullable(type)) {
            return false;
        }
        if (state.getIsStubTypeEqualsToAnything() && typeSystemContext.isStubType(type)) {
            return true;
        }
        return typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(type), end);
    }
}
