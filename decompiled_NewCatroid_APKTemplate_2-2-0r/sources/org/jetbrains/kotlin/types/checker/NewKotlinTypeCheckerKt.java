package org.jetbrains.kotlin.types.checker;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedType;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001e\u0010\u0007\u001a\u00020\u0001*\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\n\u001a\u0012\u0010\f\u001a\u00020\u0001*\u00020\b2\u0006\u0010\r\u001a\u00020\u000b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003\"\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0003¨\u0006\u000e"}, d2 = {"isClassType", "", "Lorg/jetbrains/kotlin/types/SimpleType;", "(Lorg/jetbrains/kotlin/types/SimpleType;)Z", "isIntegerLiteralType", "isIntersectionType", "isSingleClassifierType", "anySuperTypeConstructor", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "hasSupertypeWithGivenTypeConstructor", "typeConstructor", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NewKotlinTypeCheckerKt {
    public static final boolean hasSupertypeWithGivenTypeConstructor(UnwrappedType unwrappedType, TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        TypeCheckerState typeCheckerStateCreateClassicTypeCheckerState$default = ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(false, false, null, null, null, 30, null);
        SimpleType simpleTypeLowerIfFlexible = FlexibleTypesKt.lowerIfFlexible(unwrappedType);
        if (!(simpleTypeLowerIfFlexible instanceof SimpleType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (Intrinsics.areEqual(simpleTypeLowerIfFlexible.getConstructor(), typeConstructor)) {
            return true;
        }
        typeCheckerStateCreateClassicTypeCheckerState$default.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = typeCheckerStateCreateClassicTypeCheckerState$default.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = typeCheckerStateCreateClassicTypeCheckerState$default.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(simpleTypeLowerIfFlexible);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeLowerIfFlexible + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext = typeCheckerStateCreateClassicTypeCheckerState$default.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext.supertypes(typeSystemContext.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = lowerIfFlexible.mo7474transformType(typeCheckerStateCreateClassicTypeCheckerState$default, it.next());
                        if (!(simpleTypeMarkerMo7474transformType instanceof SimpleType)) {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                        if (Intrinsics.areEqual(((SimpleType) simpleTypeMarkerMo7474transformType).getConstructor(), typeConstructor)) {
                            typeCheckerStateCreateClassicTypeCheckerState$default.clear();
                            return true;
                        }
                        supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                    }
                }
            }
        }
        typeCheckerStateCreateClassicTypeCheckerState$default.clear();
        return false;
    }

    public static final boolean anySuperTypeConstructor(UnwrappedType unwrappedType, Function1<? super TypeConstructor, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        TypeCheckerState typeCheckerStateCreateClassicTypeCheckerState$default = ClassicTypeCheckerStateKt.createClassicTypeCheckerState$default(false, false, null, null, null, 30, null);
        SimpleType simpleTypeLowerIfFlexible = FlexibleTypesKt.lowerIfFlexible(unwrappedType);
        if (!(simpleTypeLowerIfFlexible instanceof SimpleType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (predicate.invoke(simpleTypeLowerIfFlexible.getConstructor()).booleanValue()) {
            return true;
        }
        typeCheckerStateCreateClassicTypeCheckerState$default.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = typeCheckerStateCreateClassicTypeCheckerState$default.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = typeCheckerStateCreateClassicTypeCheckerState$default.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(simpleTypeLowerIfFlexible);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeLowerIfFlexible + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext = typeCheckerStateCreateClassicTypeCheckerState$default.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext.supertypes(typeSystemContext.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = lowerIfFlexible.mo7474transformType(typeCheckerStateCreateClassicTypeCheckerState$default, it.next());
                        if (!(simpleTypeMarkerMo7474transformType instanceof SimpleType)) {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        }
                        if (predicate.invoke(((SimpleType) simpleTypeMarkerMo7474transformType).getConstructor()).booleanValue()) {
                            typeCheckerStateCreateClassicTypeCheckerState$default.clear();
                            return true;
                        }
                        supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                    }
                }
            }
        }
        typeCheckerStateCreateClassicTypeCheckerState$default.clear();
        return false;
    }

    public static final boolean isClassType(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        return simpleType.getConstructor().getThis$0() instanceof ClassDescriptor;
    }

    public static final boolean isSingleClassifierType(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        return (KotlinTypeKt.isError(simpleType) || (simpleType.getConstructor().getThis$0() instanceof TypeAliasDescriptor) || (simpleType.getConstructor().getThis$0() == null && !(simpleType instanceof CapturedType) && !(simpleType instanceof NewCapturedType) && !(simpleType instanceof DefinitelyNotNullType))) ? false : true;
    }

    public static final boolean isIntersectionType(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        return simpleType.getConstructor() instanceof IntersectionTypeConstructor;
    }

    public static final boolean isIntegerLiteralType(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        return simpleType.getConstructor() instanceof IntegerLiteralTypeConstructor;
    }
}
