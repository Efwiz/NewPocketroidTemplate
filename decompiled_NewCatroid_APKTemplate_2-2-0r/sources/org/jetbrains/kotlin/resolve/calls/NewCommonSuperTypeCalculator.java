package org.jetbrains.kotlin.resolve.calls;

import com.badlogic.gdx.net.HttpStatus;
import com.gaurav.avnc.vnc.XKeySym;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.AbstractFlexibilityChecker;
import org.jetbrains.kotlin.types.AbstractNullabilityChecker;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: NewCommonSuperTypeCalculator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J*\u0010\u000b\u001a\u00020\f*\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J0\u0010\u0012\u001a\u00020\u0013*\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0002J,\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0017j\b\u0012\u0004\u0012\u00020\u0005`\u0018*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u001a\u001a\u00020\u001b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0004J,\u0010\u001a\u001a\u00020\u001b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001c\u001a\u00020\u0013H\u0002J\"\u0010\u001d\u001a\u00020\b*\u00020\u00062\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u001f\u001a\u00020\nH\u0002J2\u0010 \u001a\u00020\b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0002J2\u0010!\u001a\u00020\b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\u0002J(\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u0004*\u00020\u00062\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u001f\u001a\u00020\nH\u0002J$\u0010$\u001a\u0004\u0018\u00010\b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\t\u001a\u00020\nH\u0002J*\u0010%\u001a\u00020\b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0002J\u0014\u0010&\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0002J\u0014\u0010'\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0002J\u0014\u0010(\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0002J\u0014\u0010)\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0002J\u0014\u0010*\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0002J$\u0010+\u001a\u0004\u0018\u00010\b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u001a\u001a\u00020\bH\u0002J*\u0010,\u001a\u00020\b*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010-\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001c\u0010.\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010/*\u00020\u00062\u0006\u0010\u0019\u001a\u00020\bH\u0002J \u00100\u001a\b\u0012\u0004\u0012\u00020\u000501*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b02H\u0002J\u0014\u00103\u001a\u00020\f*\u00020\u00062\u0006\u00104\u001a\u00020\fH\u0002J(\u00105\u001a\b\u0012\u0004\u0012\u00020\b0\u0004*\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\u001f\u001a\u00020\nH\u0002¨\u00066²\u0006\u0010\u00107\u001a\b\u0012\u0004\u0012\u00020\u000502X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/NewCommonSuperTypeCalculator;", "", "()V", "allCommonSuperTypeConstructors", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeSystemCommonSuperTypesContext;", SpecTypePair.NAME_types, "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "stateStubTypesEqualToAnything", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "calculateArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "parameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "arguments", ConfigConstants.CONFIG_KEY_DEPTH, "", "checkRecursion", "", "originalTypesForCst", "typeArgumentsForSuperConstructorParameter", "collectAllSupertypes", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", ModuleXmlParser.TYPE, "commonSuperType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isTopLevelType", "commonSuperTypeForBuilderInferenceStubTypes", "stubTypes", "stateStubTypesNotEqual", "commonSuperTypeForNotNullTypes", "commonSuperTypeForSimpleTypes", "filterSupertypes", SchemaSymbols.ATTVAL_LIST, "findErrorTypeInSupertypes", "findSuperTypeConstructorsAndIntersectResult", "isCapturedStarProjection", "isCapturedStubTypeForVariableInSubtyping", "isCapturedTypeVariable", "isNotNullStubTypeForBuilderInference", "isTypeVariable", "refineNullabilityForUndefinedNullability", "superTypeWithGivenConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "supertypesIfCapturedStarProjection", "", "typeConstructorsWithExpandedStarProjections", "Lkotlin/sequences/Sequence;", "", "uncaptureFromSubtyping", "typeArgument", "uniquify", "resolution.common", "originalTypeConstructorSet"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCommonSuperTypeCalculator {
    public static final NewCommonSuperTypeCalculator INSTANCE = new NewCommonSuperTypeCalculator();

    private NewCommonSuperTypeCalculator() {
    }

    public final KotlinTypeMarker commonSuperType(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends KotlinTypeMarker> types) {
        Integer num;
        Intrinsics.checkNotNullParameter(typeSystemCommonSuperTypesContext, "<this>");
        Intrinsics.checkNotNullParameter(types, "types");
        Iterator<T> it = types.iterator();
        if (it.hasNext()) {
            Integer numValueOf = Integer.valueOf(typeSystemCommonSuperTypesContext.typeDepth((KotlinTypeMarker) it.next()));
            while (it.hasNext()) {
                Integer numValueOf2 = Integer.valueOf(typeSystemCommonSuperTypesContext.typeDepth((KotlinTypeMarker) it.next()));
                if (numValueOf.compareTo(numValueOf2) < 0) {
                    numValueOf = numValueOf2;
                }
            }
            num = numValueOf;
        } else {
            num = null;
        }
        Integer num2 = num;
        return typeSystemCommonSuperTypesContext.replaceCustomAttributes(commonSuperType(typeSystemCommonSuperTypesContext, types, -(num2 != null ? num2.intValue() : 0), true), typeSystemCommonSuperTypesContext.unionTypeAttributes(types));
    }

    static /* synthetic */ KotlinTypeMarker commonSuperType$default(NewCommonSuperTypeCalculator newCommonSuperTypeCalculator, TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List list, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return newCommonSuperTypeCalculator.commonSuperType(typeSystemCommonSuperTypesContext, list, i, z);
    }

    private final KotlinTypeMarker commonSuperType(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends KotlinTypeMarker> list, int i, boolean z) {
        SimpleTypeMarker simpleTypeMarkerLowerBound;
        if (list.isEmpty()) {
            throw new IllegalStateException("Empty collection for input");
        }
        KotlinTypeMarker kotlinTypeMarker = (KotlinTypeMarker) CollectionsKt.singleOrNull((List) list);
        if (kotlinTypeMarker != null) {
            return kotlinTypeMarker;
        }
        List<? extends KotlinTypeMarker> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        boolean z2 = false;
        for (KotlinTypeMarker kotlinTypeMarker2 : list2) {
            if (kotlinTypeMarker2 instanceof SimpleTypeMarker) {
                if (typeSystemCommonSuperTypesContext.isCapturedDynamic(kotlinTypeMarker2)) {
                    return kotlinTypeMarker2;
                }
                simpleTypeMarkerLowerBound = (SimpleTypeMarker) kotlinTypeMarker2;
            } else if (kotlinTypeMarker2 instanceof FlexibleTypeMarker) {
                if (typeSystemCommonSuperTypesContext.isDynamic(kotlinTypeMarker2)) {
                    return kotlinTypeMarker2;
                }
                simpleTypeMarkerLowerBound = typeSystemCommonSuperTypesContext.lowerBound((FlexibleTypeMarker) kotlinTypeMarker2);
                z2 = true;
            } else {
                throw new IllegalStateException("sealed".toString());
            }
            arrayList.add(simpleTypeMarkerLowerBound);
        }
        TypeCheckerState typeCheckerStateNewTypeCheckerState = typeSystemCommonSuperTypesContext.newTypeCheckerState(false, true);
        TypeCheckerState typeCheckerStateNewTypeCheckerState2 = typeSystemCommonSuperTypesContext.newTypeCheckerState(false, false);
        SimpleTypeMarker simpleTypeMarkerCommonSuperTypeForSimpleTypes = commonSuperTypeForSimpleTypes(typeSystemCommonSuperTypesContext, arrayList, i, typeCheckerStateNewTypeCheckerState, typeCheckerStateNewTypeCheckerState2);
        if (!z2) {
            return simpleTypeMarkerCommonSuperTypeForSimpleTypes;
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(typeSystemCommonSuperTypesContext.upperBoundIfFlexible((KotlinTypeMarker) it.next()));
        }
        SimpleTypeMarker simpleTypeMarkerCommonSuperTypeForSimpleTypes2 = commonSuperTypeForSimpleTypes(typeSystemCommonSuperTypesContext, arrayList2, i, typeCheckerStateNewTypeCheckerState, typeCheckerStateNewTypeCheckerState2);
        if (!z) {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : list2) {
                KotlinTypeMarker kotlinTypeMarker3 = (KotlinTypeMarker) obj;
                NewCommonSuperTypeCalculator newCommonSuperTypeCalculator = INSTANCE;
                if (!newCommonSuperTypeCalculator.isTypeVariable(typeSystemCommonSuperTypesContext, typeSystemCommonSuperTypesContext.lowerBoundIfFlexible(kotlinTypeMarker3)) && !newCommonSuperTypeCalculator.isTypeVariable(typeSystemCommonSuperTypesContext, typeSystemCommonSuperTypesContext.upperBoundIfFlexible(kotlinTypeMarker3))) {
                    arrayList3.add(obj);
                }
            }
            ArrayList arrayList4 = arrayList3;
            ArrayList arrayList5 = new ArrayList();
            for (Object obj2 : arrayList4) {
                KotlinTypeMarker kotlinTypeMarker4 = (KotlinTypeMarker) obj2;
                if (!(arrayList4 instanceof Collection) || !arrayList4.isEmpty()) {
                    Iterator it2 = arrayList4.iterator();
                    do {
                        if (it2.hasNext()) {
                        }
                    } while (AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, typeSystemCommonSuperTypesContext, (KotlinTypeMarker) it2.next(), kotlinTypeMarker4, false, 8, null));
                }
                arrayList5.add(obj2);
            }
            ArrayList arrayList6 = arrayList5;
            if (!arrayList6.isEmpty()) {
                return (KotlinTypeMarker) CollectionsKt.first((List) arrayList6);
            }
        }
        return typeSystemCommonSuperTypesContext.createFlexibleType(simpleTypeMarkerCommonSuperTypeForSimpleTypes, simpleTypeMarkerCommonSuperTypeForSimpleTypes2);
    }

    private final SimpleTypeMarker commonSuperTypeForSimpleTypes(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, int i, TypeCheckerState typeCheckerState, TypeCheckerState typeCheckerState2) {
        boolean z;
        List<? extends SimpleTypeMarker> list2;
        List<? extends SimpleTypeMarker> list3 = list;
        boolean z2 = list3 instanceof Collection;
        if (!z2 || !list3.isEmpty()) {
            Iterator<T> it = list3.iterator();
            while (it.hasNext()) {
                if (typeSystemCommonSuperTypesContext.isError((SimpleTypeMarker) it.next())) {
                    return typeSystemCommonSuperTypesContext.createErrorType("CST(" + CollectionsKt.joinToString$default(list3, null, null, null, 0, null, null, 63, null));
                }
            }
        }
        if (!z2 || !list3.isEmpty()) {
            Iterator<T> it2 = list3.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = true;
                    break;
                }
                SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) it2.next();
                NewCommonSuperTypeCalculator newCommonSuperTypeCalculator = INSTANCE;
                if (!newCommonSuperTypeCalculator.isTypeVariable(typeSystemCommonSuperTypesContext, simpleTypeMarker) && !newCommonSuperTypeCalculator.isNotNullStubTypeForBuilderInference(typeSystemCommonSuperTypesContext, simpleTypeMarker)) {
                    if (!AbstractNullabilityChecker.INSTANCE.isSubtypeOfAny(typeCheckerState, simpleTypeMarker)) {
                        z = false;
                        break;
                    }
                }
            }
        } else {
            z = true;
            break;
        }
        if (z) {
            list2 = list;
        } else {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it3 = list3.iterator();
            while (it3.hasNext()) {
                arrayList.add(typeSystemCommonSuperTypesContext.withNullability((SimpleTypeMarker) it3.next(), false));
            }
            list2 = arrayList;
        }
        SimpleTypeMarker simpleTypeMarkerCommonSuperTypeForNotNullTypes = commonSuperTypeForNotNullTypes(typeSystemCommonSuperTypesContext, list2, i, typeCheckerState, typeCheckerState2);
        if (z) {
            return simpleTypeMarkerCommonSuperTypeForNotNullTypes;
        }
        SimpleTypeMarker simpleTypeMarkerRefineNullabilityForUndefinedNullability = refineNullabilityForUndefinedNullability(typeSystemCommonSuperTypesContext, list, simpleTypeMarkerCommonSuperTypeForNotNullTypes);
        return simpleTypeMarkerRefineNullabilityForUndefinedNullability == null ? typeSystemCommonSuperTypesContext.withNullability(simpleTypeMarkerCommonSuperTypeForNotNullTypes, true) : simpleTypeMarkerRefineNullabilityForUndefinedNullability;
    }

    private final boolean isCapturedStubTypeForVariableInSubtyping(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker) {
        CapturedTypeConstructorMarker capturedTypeConstructorMarkerTypeConstructor;
        TypeArgumentMarker typeArgumentMarkerProjection;
        KotlinTypeMarker type;
        SimpleTypeMarker simpleTypeMarkerAsSimpleType;
        CapturedTypeMarker capturedTypeMarkerAsCapturedType = typeSystemCommonSuperTypesContext.asCapturedType(simpleTypeMarker);
        if (capturedTypeMarkerAsCapturedType == null || (capturedTypeConstructorMarkerTypeConstructor = typeSystemCommonSuperTypesContext.typeConstructor(capturedTypeMarkerAsCapturedType)) == null || (typeArgumentMarkerProjection = typeSystemCommonSuperTypesContext.projection(capturedTypeConstructorMarkerTypeConstructor)) == null) {
            return false;
        }
        if (typeSystemCommonSuperTypesContext.isStarProjection(typeArgumentMarkerProjection)) {
            typeArgumentMarkerProjection = null;
        }
        return (typeArgumentMarkerProjection == null || (type = typeSystemCommonSuperTypesContext.getType(typeArgumentMarkerProjection)) == null || (simpleTypeMarkerAsSimpleType = typeSystemCommonSuperTypesContext.asSimpleType(type)) == null || !typeSystemCommonSuperTypesContext.isStubTypeForVariableInSubtyping(simpleTypeMarkerAsSimpleType)) ? false : true;
    }

    private final SimpleTypeMarker refineNullabilityForUndefinedNullability(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, SimpleTypeMarker simpleTypeMarker) {
        if (!typeSystemCommonSuperTypesContext.canHaveUndefinedNullability(simpleTypeMarker)) {
            return null;
        }
        List<? extends SimpleTypeMarker> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return simpleTypeMarker;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (!AbstractNullabilityChecker.INSTANCE.hasPathByNotMarkedNullableNodes(typeSystemCommonSuperTypesContext, (SimpleTypeMarker) it.next(), typeSystemCommonSuperTypesContext.typeConstructor(simpleTypeMarker))) {
                return null;
            }
        }
        return simpleTypeMarker;
    }

    private final List<SimpleTypeMarker> uniquify(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, TypeCheckerState typeCheckerState) {
        ArrayList arrayList = new ArrayList();
        for (SimpleTypeMarker simpleTypeMarker : list) {
            ArrayList arrayList2 = arrayList;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it = arrayList2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarker2 = (SimpleTypeMarker) it.next();
                        if (AbstractTypeChecker.INSTANCE.equalTypes(typeCheckerState, simpleTypeMarker2, simpleTypeMarker) && !typeSystemCommonSuperTypesContext.isIntegerLiteralTypeConstructor(typeSystemCommonSuperTypesContext.typeConstructor(simpleTypeMarker2)) && !AbstractFlexibilityChecker.INSTANCE.hasDifferentFlexibilityAtDepth(typeSystemCommonSuperTypesContext, CollectionsKt.listOf((Object[]) new SimpleTypeMarker[]{simpleTypeMarker2, simpleTypeMarker}))) {
                            break;
                        }
                    }
                }
            }
            arrayList.add(simpleTypeMarker);
        }
        return arrayList;
    }

    private final List<SimpleTypeMarker> filterSupertypes(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, TypeCheckerState typeCheckerState) {
        List<SimpleTypeMarker> mutableList = CollectionsKt.toMutableList((Collection) list);
        Iterator<SimpleTypeMarker> it = mutableList.iterator();
        while (it.hasNext()) {
            SimpleTypeMarker next = it.next();
            List<SimpleTypeMarker> list2 = mutableList;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                for (SimpleTypeMarker simpleTypeMarker : list2) {
                    if (simpleTypeMarker != next && AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, typeCheckerState, (KotlinTypeMarker) next, (KotlinTypeMarker) simpleTypeMarker, false, 8, (Object) null) && !AbstractFlexibilityChecker.INSTANCE.hasDifferentFlexibilityAtDepth(typeSystemCommonSuperTypesContext, CollectionsKt.listOf((Object[]) new SimpleTypeMarker[]{next, simpleTypeMarker}))) {
                        it.remove();
                        break;
                    }
                }
            }
        }
        return mutableList;
    }

    private final SimpleTypeMarker commonSuperTypeForBuilderInferenceStubTypes(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, TypeCheckerState typeCheckerState) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("There should be stub types to compute common super type on them".toString());
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        boolean z = true;
        boolean z2 = false;
        for (SimpleTypeMarker simpleTypeMarker : list) {
            if (simpleTypeMarker instanceof DefinitelyNotNullTypeMarker) {
                listCreateListBuilder.add(typeSystemCommonSuperTypesContext.original((DefinitelyNotNullTypeMarker) simpleTypeMarker));
            } else {
                if (typeSystemCommonSuperTypesContext.isMarkedNullable(simpleTypeMarker)) {
                    listCreateListBuilder.add(typeSystemCommonSuperTypesContext.withNullability(simpleTypeMarker, false));
                    z2 = true;
                } else {
                    listCreateListBuilder.add(simpleTypeMarker);
                }
                z = false;
            }
        }
        SimpleTypeMarker simpleTypeMarkerWithNullability = (SimpleTypeMarker) CollectionsKt.singleOrNull((List) uniquify(typeSystemCommonSuperTypesContext, CollectionsKt.build(listCreateListBuilder), typeCheckerState));
        if (simpleTypeMarkerWithNullability != null) {
            if (z) {
                simpleTypeMarkerWithNullability = typeSystemCommonSuperTypesContext.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarkerWithNullability);
            } else if (z2) {
                simpleTypeMarkerWithNullability = typeSystemCommonSuperTypesContext.withNullability(simpleTypeMarkerWithNullability, true);
            }
            if (simpleTypeMarkerWithNullability != null) {
                return simpleTypeMarkerWithNullability;
            }
        }
        return typeSystemCommonSuperTypesContext.nullableAnyType();
    }

    private final SimpleTypeMarker commonSuperTypeForNotNullTypes(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, int i, TypeCheckerState typeCheckerState, TypeCheckerState typeCheckerState2) {
        boolean z;
        if (list.size() == 1) {
            return (SimpleTypeMarker) CollectionsKt.single((List) list);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) obj;
            if (!typeSystemCommonSuperTypesContext.isStubTypeForVariableInSubtyping(simpleTypeMarker) && !INSTANCE.isCapturedStubTypeForVariableInSubtyping(typeSystemCommonSuperTypesContext, simpleTypeMarker)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        arrayList2.isEmpty();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (typeSystemCommonSuperTypesContext.isStubTypeForBuilderInference((SimpleTypeMarker) obj2)) {
                arrayList3.add(obj2);
            } else {
                arrayList4.add(obj2);
            }
        }
        Pair pair = new Pair(arrayList3, arrayList4);
        List<? extends SimpleTypeMarker> list2 = (List) pair.component1();
        List<? extends SimpleTypeMarker> list3 = (List) pair.component2();
        if (list3.isEmpty()) {
            z = false;
        } else {
            List<? extends SimpleTypeMarker> list4 = list3;
            if (!(list4 instanceof Collection) || !list4.isEmpty()) {
                Iterator<T> it = list4.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!typeSystemCommonSuperTypesContext.isNothing((SimpleTypeMarker) it.next())) {
                            z = false;
                        }
                    }
                }
            }
            z = true;
        }
        if (!list2.isEmpty() && (list3.isEmpty() || z)) {
            return commonSuperTypeForBuilderInferenceStubTypes(typeSystemCommonSuperTypesContext, list2, typeCheckerState2);
        }
        List<SimpleTypeMarker> listUniquify = uniquify(typeSystemCommonSuperTypesContext, list3, typeCheckerState2);
        if (listUniquify.size() == 1) {
            return (SimpleTypeMarker) CollectionsKt.single((List) listUniquify);
        }
        List<SimpleTypeMarker> listFilterSupertypes = filterSupertypes(typeSystemCommonSuperTypesContext, listUniquify, typeCheckerState2);
        if (listFilterSupertypes.size() == 1) {
            return (SimpleTypeMarker) CollectionsKt.single((List) listFilterSupertypes);
        }
        SimpleTypeMarker simpleTypeMarkerFindErrorTypeInSupertypes = findErrorTypeInSupertypes(typeSystemCommonSuperTypesContext, listFilterSupertypes, typeCheckerState);
        if (simpleTypeMarkerFindErrorTypeInSupertypes != null) {
            return simpleTypeMarkerFindErrorTypeInSupertypes;
        }
        SimpleTypeMarker simpleTypeMarkerFindCommonIntegerLiteralTypesSuperType = typeSystemCommonSuperTypesContext.findCommonIntegerLiteralTypesSuperType(listFilterSupertypes);
        return simpleTypeMarkerFindCommonIntegerLiteralTypesSuperType != null ? simpleTypeMarkerFindCommonIntegerLiteralTypesSuperType : findSuperTypeConstructorsAndIntersectResult(typeSystemCommonSuperTypesContext, listFilterSupertypes, i, typeCheckerState);
    }

    private final boolean isTypeVariable(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker) {
        return typeSystemCommonSuperTypesContext.isStubTypeForVariableInSubtyping(simpleTypeMarker) || isCapturedTypeVariable(typeSystemCommonSuperTypesContext, simpleTypeMarker);
    }

    private final boolean isNotNullStubTypeForBuilderInference(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker) {
        return typeSystemCommonSuperTypesContext.isStubTypeForBuilderInference(simpleTypeMarker) && !typeSystemCommonSuperTypesContext.isMarkedNullable(simpleTypeMarker);
    }

    private final boolean isCapturedTypeVariable(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker) {
        CapturedTypeConstructorMarker capturedTypeConstructorMarkerTypeConstructor;
        TypeArgumentMarker typeArgumentMarkerProjection;
        KotlinTypeMarker type;
        SimpleTypeMarker simpleTypeMarkerAsSimpleType;
        CapturedTypeMarker capturedTypeMarkerAsCapturedType = typeSystemCommonSuperTypesContext.asCapturedType(simpleTypeMarker);
        if (capturedTypeMarkerAsCapturedType == null || (capturedTypeConstructorMarkerTypeConstructor = typeSystemCommonSuperTypesContext.typeConstructor(capturedTypeMarkerAsCapturedType)) == null || (typeArgumentMarkerProjection = typeSystemCommonSuperTypesContext.projection(capturedTypeConstructorMarkerTypeConstructor)) == null) {
            return false;
        }
        if (typeSystemCommonSuperTypesContext.isStarProjection(typeArgumentMarkerProjection)) {
            typeArgumentMarkerProjection = null;
        }
        return (typeArgumentMarkerProjection == null || (type = typeSystemCommonSuperTypesContext.getType(typeArgumentMarkerProjection)) == null || (simpleTypeMarkerAsSimpleType = typeSystemCommonSuperTypesContext.asSimpleType(type)) == null || !typeSystemCommonSuperTypesContext.isStubTypeForVariableInSubtyping(simpleTypeMarkerAsSimpleType)) ? false : true;
    }

    private final SimpleTypeMarker findErrorTypeInSupertypes(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, TypeCheckerState typeCheckerState) {
        TypeConstructorMarker typeConstructorMarker;
        Iterator<? extends SimpleTypeMarker> it = list.iterator();
        do {
            Object obj = null;
            if (!it.hasNext()) {
                return null;
            }
            for (Object obj2 : collectAllSupertypes(typeSystemCommonSuperTypesContext, it.next(), typeCheckerState)) {
                if (typeSystemCommonSuperTypesContext.isError((TypeConstructorMarker) obj2)) {
                    obj = obj2;
                    break;
                }
            }
            typeConstructorMarker = (TypeConstructorMarker) obj;
        } while (typeConstructorMarker == null);
        return typeSystemCommonSuperTypesContext.toErrorType(typeConstructorMarker);
    }

    private final SimpleTypeMarker findSuperTypeConstructorsAndIntersectResult(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, int i, TypeCheckerState typeCheckerState) {
        List<TypeConstructorMarker> listAllCommonSuperTypeConstructors = allCommonSuperTypeConstructors(typeSystemCommonSuperTypesContext, list, typeCheckerState);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAllCommonSuperTypeConstructors, 10));
        Iterator<T> it = listAllCommonSuperTypeConstructors.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.superTypeWithGivenConstructor(typeSystemCommonSuperTypesContext, list, (TypeConstructorMarker) it.next(), i));
        }
        return typeSystemCommonSuperTypesContext.intersectTypes((List<? extends KotlinTypeMarker>) arrayList);
    }

    private final List<TypeConstructorMarker> allCommonSuperTypeConstructors(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, TypeCheckerState typeCheckerState) {
        LinkedHashSet<TypeConstructorMarker> linkedHashSetCollectAllSupertypes = collectAllSupertypes(typeSystemCommonSuperTypesContext, (SimpleTypeMarker) CollectionsKt.first((List) list), typeCheckerState);
        for (SimpleTypeMarker simpleTypeMarker : list) {
            if (simpleTypeMarker != CollectionsKt.first((List) list)) {
                linkedHashSetCollectAllSupertypes.retainAll(collectAllSupertypes(typeSystemCommonSuperTypesContext, simpleTypeMarker, typeCheckerState));
            }
        }
        LinkedHashSet<TypeConstructorMarker> linkedHashSet = linkedHashSetCollectAllSupertypes;
        ArrayList arrayList = new ArrayList();
        for (Object obj : linkedHashSet) {
            TypeConstructorMarker typeConstructorMarker = (TypeConstructorMarker) obj;
            if (!(linkedHashSet instanceof Collection) || !linkedHashSet.isEmpty()) {
                Iterator<T> it = linkedHashSet.iterator();
                while (true) {
                    if (it.hasNext()) {
                        TypeConstructorMarker typeConstructorMarker2 = (TypeConstructorMarker) it.next();
                        if (!Intrinsics.areEqual(typeConstructorMarker2, typeConstructorMarker)) {
                            Collection<KotlinTypeMarker> collectionSupertypes = typeSystemCommonSuperTypesContext.supertypes(typeConstructorMarker2);
                            if (!(collectionSupertypes instanceof Collection) || !collectionSupertypes.isEmpty()) {
                                Iterator<T> it2 = collectionSupertypes.iterator();
                                while (it2.hasNext()) {
                                    if (Intrinsics.areEqual(typeSystemCommonSuperTypesContext.typeConstructor((KotlinTypeMarker) it2.next()), typeConstructorMarker)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            arrayList.add(obj);
        }
        return arrayList;
    }

    private final LinkedHashSet<TypeConstructorMarker> collectAllSupertypes(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker, TypeCheckerState typeCheckerState) {
        LinkedHashSet<TypeConstructorMarker> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(typeSystemCommonSuperTypesContext.typeConstructor(simpleTypeMarker));
        typeCheckerState.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = typeCheckerState.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = typeCheckerState.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(simpleTypeMarker);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarker + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext.supertypes(typeSystemContext.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = lowerIfFlexible.mo7474transformType(typeCheckerState, it.next());
                        linkedHashSet.add(typeSystemCommonSuperTypesContext.typeConstructor(simpleTypeMarkerMo7474transformType));
                        supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                    }
                }
            }
        }
        typeCheckerState.clear();
        return linkedHashSet;
    }

    private final SimpleTypeMarker superTypeWithGivenConstructor(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, TypeConstructorMarker typeConstructorMarker, int i) {
        TypeArgumentMarker typeArgumentMarkerCreateStarProjection;
        if (typeSystemCommonSuperTypesContext.parametersCount(typeConstructorMarker) == 0) {
            return TypeSystemTypeFactoryContext.createSimpleType$default(typeSystemCommonSuperTypesContext, typeConstructorMarker, CollectionsKt.emptyList(), false, false, null, 24, null);
        }
        boolean z = false;
        boolean z2 = true;
        TypeCheckerState typeCheckerStateNewTypeCheckerState = typeSystemCommonSuperTypesContext.newTypeCheckerState(false, true);
        List<? extends SimpleTypeMarker> list2 = list;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, AbstractTypeChecker.INSTANCE.findCorrespondingSupertypes(typeCheckerStateNewTypeCheckerState, (SimpleTypeMarker) it.next(), typeConstructorMarker));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(typeSystemCommonSuperTypesContext.parametersCount(typeConstructorMarker));
        int iParametersCount = typeSystemCommonSuperTypesContext.parametersCount(typeConstructorMarker);
        int i2 = 0;
        while (i2 < iParametersCount) {
            TypeParameterMarker parameter = typeSystemCommonSuperTypesContext.getParameter(typeConstructorMarker, i2);
            ArrayList arrayList4 = new ArrayList();
            Iterator it2 = arrayList2.iterator();
            boolean z3 = z;
            while (it2.hasNext()) {
                TypeArgumentMarker argumentOrNull = typeSystemCommonSuperTypesContext.getArgumentOrNull((SimpleTypeMarker) it2.next(), i2);
                TypeArgumentMarker typeArgumentMarker = null;
                if (argumentOrNull != null) {
                    TypeArgumentMarker typeArgumentMarkerUncaptureFromSubtyping = INSTANCE.uncaptureFromSubtyping(typeSystemCommonSuperTypesContext, argumentOrNull);
                    if (typeSystemCommonSuperTypesContext.isStarProjection(typeArgumentMarkerUncaptureFromSubtyping)) {
                        z3 = true;
                    } else if (!typeSystemCommonSuperTypesContext.isStubTypeForVariableInSubtyping(typeSystemCommonSuperTypesContext.lowerBoundIfFlexible(typeSystemCommonSuperTypesContext.getType(typeArgumentMarkerUncaptureFromSubtyping)))) {
                        typeArgumentMarker = typeArgumentMarkerUncaptureFromSubtyping;
                    }
                }
                TypeArgumentMarker typeArgumentMarker2 = typeArgumentMarker;
                if (typeArgumentMarker2 != null) {
                    arrayList4.add(typeArgumentMarker2);
                }
            }
            ArrayList arrayList5 = arrayList4;
            if (z3 || arrayList5.isEmpty() || checkRecursion(typeSystemCommonSuperTypesContext, list, arrayList5, parameter)) {
                typeArgumentMarkerCreateStarProjection = typeSystemCommonSuperTypesContext.createStarProjection(parameter);
            } else {
                typeArgumentMarkerCreateStarProjection = superTypeWithGivenConstructor$collapseRecursiveArgumentIfPossible(typeSystemCommonSuperTypesContext, typeConstructorMarker, i2, parameter, calculateArgument(typeSystemCommonSuperTypesContext, parameter, arrayList5, i));
            }
            arrayList3.add(typeArgumentMarkerCreateStarProjection);
            i2++;
            z = false;
        }
        TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext2 = typeSystemCommonSuperTypesContext;
        ArrayList arrayList6 = arrayList3;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it3 = list2.iterator();
            while (it3.hasNext()) {
                if (!typeSystemCommonSuperTypesContext.isExtensionFunction((SimpleTypeMarker) it3.next())) {
                    z2 = false;
                    break;
                }
            }
        }
        return TypeSystemTypeFactoryContext.createSimpleType$default(typeSystemCommonSuperTypesContext2, typeConstructorMarker, arrayList6, false, z2, null, 16, null);
    }

    private static final TypeArgumentMarker superTypeWithGivenConstructor$collapseRecursiveArgumentIfPossible(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, TypeConstructorMarker typeConstructorMarker, int i, TypeParameterMarker typeParameterMarker, TypeArgumentMarker typeArgumentMarker) {
        if (typeSystemCommonSuperTypesContext.isStarProjection(typeArgumentMarker)) {
            return typeArgumentMarker;
        }
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemCommonSuperTypesContext.asSimpleType(typeSystemCommonSuperTypesContext.getType(typeArgumentMarker));
        return (typeSystemCommonSuperTypesContext.getVariance(typeArgumentMarker) == TypeVariance.OUT && Intrinsics.areEqual(simpleTypeMarkerAsSimpleType != null ? typeSystemCommonSuperTypesContext.typeConstructor(simpleTypeMarkerAsSimpleType) : null, typeConstructorMarker) && typeSystemCommonSuperTypesContext.isStarProjection(typeSystemCommonSuperTypesContext.get(typeSystemCommonSuperTypesContext.asArgumentList(simpleTypeMarkerAsSimpleType), i))) ? typeSystemCommonSuperTypesContext.createStarProjection(typeParameterMarker) : typeArgumentMarker;
    }

    private final TypeArgumentMarker uncaptureFromSubtyping(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, TypeArgumentMarker typeArgumentMarker) {
        CapturedTypeMarker capturedTypeMarkerAsCapturedType;
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemCommonSuperTypesContext.asSimpleType(typeSystemCommonSuperTypesContext.getType(typeArgumentMarker));
        return (simpleTypeMarkerAsSimpleType == null || (capturedTypeMarkerAsCapturedType = typeSystemCommonSuperTypesContext.asCapturedType(simpleTypeMarkerAsSimpleType)) == null || typeSystemCommonSuperTypesContext.captureStatus(capturedTypeMarkerAsCapturedType) != CaptureStatus.FOR_SUBTYPING) ? typeArgumentMarker : typeSystemCommonSuperTypesContext.projection(typeSystemCommonSuperTypesContext.typeConstructor(capturedTypeMarkerAsCapturedType));
    }

    private final boolean checkRecursion(final TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, List<? extends SimpleTypeMarker> list, List<? extends TypeArgumentMarker> list2, TypeParameterMarker typeParameterMarker) {
        if (typeSystemCommonSuperTypesContext.getVariance(typeParameterMarker) == TypeVariance.IN) {
            return false;
        }
        final Set set = CollectionsKt.toSet(list);
        List<? extends TypeArgumentMarker> list3 = list2;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(typeSystemCommonSuperTypesContext.lowerBoundIfFlexible(typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it.next())));
        }
        Set<SimpleTypeMarker> set2 = CollectionsKt.toSet(arrayList);
        if (set.size() != set2.size()) {
            return false;
        }
        Lazy lazy = LazyKt.lazy(new Function0<Set<? extends TypeConstructorMarker>>() { // from class: org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator$checkRecursion$originalTypeConstructorSet$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends TypeConstructorMarker> invoke() {
                return SequencesKt.toSet(NewCommonSuperTypeCalculator.INSTANCE.typeConstructorsWithExpandedStarProjections(typeSystemCommonSuperTypesContext, set));
            }
        });
        for (SimpleTypeMarker simpleTypeMarker : set2) {
            if (!set.contains(simpleTypeMarker)) {
                List listSupertypesIfCapturedStarProjection = supertypesIfCapturedStarProjection(typeSystemCommonSuperTypesContext, simpleTypeMarker);
                if (listSupertypesIfCapturedStarProjection == null) {
                    listSupertypesIfCapturedStarProjection = CollectionsKt.emptyList();
                }
                Iterator<KotlinTypeMarker> it2 = listSupertypesIfCapturedStarProjection.iterator();
                boolean z = false;
                while (it2.hasNext()) {
                    if (!checkRecursion$lambda$37(lazy).contains(typeSystemCommonSuperTypesContext.typeConstructor(typeSystemCommonSuperTypesContext.lowerBoundIfFlexible(it2.next())))) {
                        return false;
                    }
                    z = true;
                }
                if (!z) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final Set<TypeConstructorMarker> checkRecursion$lambda$37(Lazy<? extends Set<? extends TypeConstructorMarker>> lazy) {
        return (Set) lazy.getValue();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator$typeConstructorsWithExpandedStarProjections$1, reason: invalid class name */
    /* JADX INFO: compiled from: NewCommonSuperTypeCalculator.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator$typeConstructorsWithExpandedStarProjections$1", f = "NewCommonSuperTypeCalculator.kt", i = {0, 1}, l = {HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, XKeySym.XK_breve}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "$this$sequence"}, s = {"L$0", "L$0"})
    static final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super TypeConstructorMarker>, Continuation<? super Unit>, Object> {
        final /* synthetic */ TypeSystemCommonSuperTypesContext $this_typeConstructorsWithExpandedStarProjections;
        final /* synthetic */ Set<SimpleTypeMarker> $types;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(Set<? extends SimpleTypeMarker> set, TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$types = set;
            this.$this_typeConstructorsWithExpandedStarProjections = typeSystemCommonSuperTypesContext;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$types, this.$this_typeConstructorsWithExpandedStarProjections, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super TypeConstructorMarker> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:13:0x0048  */
        /* JADX WARN: Code duplicated, block: B:17:0x0062  */
        /* JADX WARN: Code duplicated, block: B:21:0x0075  */
        /* JADX WARN: Code duplicated, block: B:25:0x0099  */
        /* JADX WARN: Code duplicated, block: B:30:0x00b1 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:32:0x0058 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:34:? A[LOOP:0: B:11:0x0042->B:34:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:36:0x0096 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:38:? A[LOOP:1: B:19:0x006f->B:38:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0097 -> B:11:0x0042). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:30:0x00b1
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L34
                if (r1 == r3) goto L22
                if (r1 != r2) goto L1a
                java.lang.Object r1 = r8.L$1
                java.util.Iterator r1 = (java.util.Iterator) r1
                java.lang.Object r4 = r8.L$0
                kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
                kotlin.ResultKt.throwOnFailure(r9)
                goto L42
            L1a:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L22:
                java.lang.Object r1 = r8.L$2
                java.util.Iterator r1 = (java.util.Iterator) r1
                java.lang.Object r4 = r8.L$1
                java.util.Iterator r4 = (java.util.Iterator) r4
                java.lang.Object r5 = r8.L$0
                kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r4
                r4 = r5
                goto L6f
            L34:
                kotlin.ResultKt.throwOnFailure(r9)
                java.lang.Object r9 = r8.L$0
                kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
                java.util.Set<org.jetbrains.kotlin.types.model.SimpleTypeMarker> r1 = r8.$types
                java.util.Iterator r1 = r1.iterator()
                r4 = r9
            L42:
                boolean r9 = r1.hasNext()
                if (r9 == 0) goto Lb2
                java.lang.Object r9 = r1.next()
                org.jetbrains.kotlin.types.model.SimpleTypeMarker r9 = (org.jetbrains.kotlin.types.model.SimpleTypeMarker) r9
                org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator r5 = org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator.INSTANCE
                org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext r6 = r8.$this_typeConstructorsWithExpandedStarProjections
                boolean r5 = org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator.access$isCapturedStarProjection(r5, r6, r9)
                if (r5 == 0) goto L99
                org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator r5 = org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator.INSTANCE
                org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext r6 = r8.$this_typeConstructorsWithExpandedStarProjections
                java.util.Collection r9 = org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator.access$supertypesIfCapturedStarProjection(r5, r6, r9)
                if (r9 != 0) goto L68
                java.util.List r9 = kotlin.collections.CollectionsKt.emptyList()
                java.util.Collection r9 = (java.util.Collection) r9
            L68:
                java.util.Iterator r9 = r9.iterator()
                r7 = r1
                r1 = r9
                r9 = r7
            L6f:
                boolean r5 = r1.hasNext()
                if (r5 == 0) goto L97
                java.lang.Object r5 = r1.next()
                org.jetbrains.kotlin.types.model.KotlinTypeMarker r5 = (org.jetbrains.kotlin.types.model.KotlinTypeMarker) r5
                org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext r6 = r8.$this_typeConstructorsWithExpandedStarProjections
                org.jetbrains.kotlin.types.model.SimpleTypeMarker r5 = r6.lowerBoundIfFlexible(r5)
                org.jetbrains.kotlin.types.model.TypeConstructorMarker r5 = r6.typeConstructor(r5)
                r6 = r8
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r8.L$0 = r4
                r8.L$1 = r9
                r8.L$2 = r1
                r8.label = r3
                java.lang.Object r5 = r4.yield(r5, r6)
                if (r5 != r0) goto L6f
                return r0
            L97:
                r1 = r9
                goto L42
            L99:
                org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext r5 = r8.$this_typeConstructorsWithExpandedStarProjections
                org.jetbrains.kotlin.types.model.TypeConstructorMarker r9 = r5.typeConstructor(r9)
                r5 = r8
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r8.L$0 = r4
                r8.L$1 = r1
                r6 = 0
                r8.L$2 = r6
                r8.label = r2
                java.lang.Object r9 = r4.yield(r9, r5)
                if (r9 != r0) goto L42
                return r0
            Lb2:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence<TypeConstructorMarker> typeConstructorsWithExpandedStarProjections(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, Set<? extends SimpleTypeMarker> set) {
        return SequencesKt.sequence(new AnonymousClass1(set, typeSystemCommonSuperTypesContext, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCapturedStarProjection(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker) {
        CapturedTypeConstructorMarker capturedTypeConstructorMarkerTypeConstructor;
        TypeArgumentMarker typeArgumentMarkerProjection;
        CapturedTypeMarker capturedTypeMarkerAsCapturedType = typeSystemCommonSuperTypesContext.asCapturedType(simpleTypeMarker);
        return (capturedTypeMarkerAsCapturedType == null || (capturedTypeConstructorMarkerTypeConstructor = typeSystemCommonSuperTypesContext.typeConstructor(capturedTypeMarkerAsCapturedType)) == null || (typeArgumentMarkerProjection = typeSystemCommonSuperTypesContext.projection(capturedTypeConstructorMarkerTypeConstructor)) == null || !typeSystemCommonSuperTypesContext.isStarProjection(typeArgumentMarkerProjection)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<KotlinTypeMarker> supertypesIfCapturedStarProjection(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, SimpleTypeMarker simpleTypeMarker) {
        CapturedTypeConstructorMarker capturedTypeConstructorMarkerTypeConstructor;
        CapturedTypeMarker capturedTypeMarkerAsCapturedType = typeSystemCommonSuperTypesContext.asCapturedType(simpleTypeMarker);
        if (capturedTypeMarkerAsCapturedType == null || (capturedTypeConstructorMarkerTypeConstructor = typeSystemCommonSuperTypesContext.typeConstructor(capturedTypeMarkerAsCapturedType)) == null || !typeSystemCommonSuperTypesContext.isStarProjection(typeSystemCommonSuperTypesContext.projection(capturedTypeConstructorMarkerTypeConstructor))) {
            return null;
        }
        return typeSystemCommonSuperTypesContext.supertypes(capturedTypeConstructorMarkerTypeConstructor);
    }

    /* JADX WARN: Code duplicated, block: B:136:0x0053 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:137:0x0073 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:? A[LOOP:0: B:28:0x0059->B:138:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:27:0x0055  */
    /* JADX WARN: Code duplicated, block: B:30:0x005f  */
    /* JADX WARN: Code duplicated, block: B:34:0x0076 A[RETURN] */
    private final TypeArgumentMarker calculateArgument(TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext, TypeParameterMarker typeParameterMarker, List<? extends TypeArgumentMarker> list, int i) {
        boolean z;
        boolean z2;
        boolean z3;
        Object next;
        boolean z4;
        TypeArgumentMarker typeArgumentMarker;
        Iterator<T> it;
        boolean z5;
        if (i > 0) {
            return typeSystemCommonSuperTypesContext.createStarProjection(typeParameterMarker);
        }
        boolean z6 = false;
        if (typeSystemCommonSuperTypesContext.getVariance(typeParameterMarker) == TypeVariance.INV) {
            List<? extends TypeArgumentMarker> list2 = list;
            boolean z7 = list2 instanceof Collection;
            if (z7 && list2.isEmpty()) {
                typeArgumentMarker = (TypeArgumentMarker) CollectionsKt.first((List) list);
                if (!z7) {
                    it = list2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z5 = true;
                            break;
                        }
                        if (!Intrinsics.areEqual(typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it.next()), typeSystemCommonSuperTypesContext.getType(typeArgumentMarker))) {
                            z5 = false;
                            break;
                        }
                    }
                } else {
                    it = list2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z5 = true;
                            break;
                        }
                        if (!Intrinsics.areEqual(typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it.next()), typeSystemCommonSuperTypesContext.getType(typeArgumentMarker))) {
                            z5 = false;
                            break;
                        }
                    }
                }
                if (z5) {
                    return typeArgumentMarker;
                }
            } else {
                Iterator<T> it2 = list2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (!(typeSystemCommonSuperTypesContext.getVariance((TypeArgumentMarker) it2.next()) == TypeVariance.INV)) {
                        }
                    } else {
                        typeArgumentMarker = (TypeArgumentMarker) CollectionsKt.first((List) list);
                        if (!z7 && list2.isEmpty()) {
                            z5 = true;
                            break;
                        }
                        it = list2.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z5 = true;
                                break;
                            }
                            if (!Intrinsics.areEqual(typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it.next()), typeSystemCommonSuperTypesContext.getType(typeArgumentMarker))) {
                                z5 = false;
                                break;
                            }
                        }
                        if (z5) {
                            return typeArgumentMarker;
                        }
                    }
                }
            }
        }
        if (typeSystemCommonSuperTypesContext.getVariance(typeParameterMarker) != TypeVariance.INV) {
            z3 = typeSystemCommonSuperTypesContext.getVariance(typeParameterMarker) == TypeVariance.OUT;
        } else {
            List<? extends TypeArgumentMarker> list3 = list;
            boolean z8 = list3 instanceof Collection;
            if (!z8 || !list3.isEmpty()) {
                Iterator<T> it3 = list3.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        z = false;
                        break;
                    }
                    if (typeSystemCommonSuperTypesContext.getVariance((TypeArgumentMarker) it3.next()) == TypeVariance.OUT) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            if (!z8 || !list3.isEmpty()) {
                Iterator<T> it4 = list3.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        z2 = false;
                        break;
                    }
                    if (typeSystemCommonSuperTypesContext.getVariance((TypeArgumentMarker) it4.next()) == TypeVariance.IN) {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = false;
                break;
            }
            if (z) {
                if (z2) {
                    return typeSystemCommonSuperTypesContext.createStarProjection(typeParameterMarker);
                }
            } else {
                z3 = !z2;
            }
        }
        if (z3) {
            List<? extends TypeArgumentMarker> list4 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator<T> it5 = list4.iterator();
            while (it5.hasNext()) {
                arrayList.add(typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it5.next()));
            }
            ArrayList arrayList2 = arrayList;
            if (typeSystemCommonSuperTypesContext.getVariance(typeParameterMarker) != TypeVariance.INV) {
                return typeSystemCommonSuperTypesContext.asTypeArgument(commonSuperType$default(this, typeSystemCommonSuperTypesContext, arrayList2, i + 1, false, 4, null));
            }
            Iterator<T> it6 = list4.iterator();
            do {
                if (!it6.hasNext()) {
                    next = null;
                    break;
                }
                next = it6.next();
                TypeArgumentMarker typeArgumentMarker2 = (TypeArgumentMarker) next;
                if (!(list4 instanceof Collection) || !list4.isEmpty()) {
                    Iterator<T> it7 = list4.iterator();
                    while (true) {
                        if (!it7.hasNext()) {
                            z4 = true;
                            break;
                        }
                        if (!AbstractTypeChecker.INSTANCE.equalTypes(typeSystemCommonSuperTypesContext, typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it7.next()), typeSystemCommonSuperTypesContext.getType(typeArgumentMarker2), false)) {
                            z4 = false;
                            break;
                        }
                    }
                } else {
                    z4 = true;
                    break;
                    break;
                }
            } while (!z4);
            TypeArgumentMarker typeArgumentMarker3 = (TypeArgumentMarker) next;
            if (typeArgumentMarker3 == null) {
                return typeSystemCommonSuperTypesContext.createTypeArgument(commonSuperType$default(this, typeSystemCommonSuperTypesContext, arrayList2, i + 1, false, 4, null), TypeVariance.OUT);
            }
            if (!(list4 instanceof Collection) || !list4.isEmpty()) {
                Iterator<T> it8 = list4.iterator();
                while (it8.hasNext()) {
                    if (typeSystemCommonSuperTypesContext.getVariance((TypeArgumentMarker) it8.next()) != TypeVariance.INV) {
                        z6 = true;
                        break;
                    }
                }
            }
            return typeSystemCommonSuperTypesContext.createTypeArgument(typeSystemCommonSuperTypesContext.getType(typeArgumentMarker3), z6 ? TypeVariance.OUT : TypeVariance.INV);
        }
        List<? extends TypeArgumentMarker> list5 = list;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
        Iterator<T> it9 = list5.iterator();
        while (it9.hasNext()) {
            arrayList3.add(typeSystemCommonSuperTypesContext.getType((TypeArgumentMarker) it9.next()));
        }
        KotlinTypeMarker kotlinTypeMarkerIntersectTypes = typeSystemCommonSuperTypesContext.intersectTypes((List<? extends KotlinTypeMarker>) arrayList3);
        return typeSystemCommonSuperTypesContext.getVariance(typeParameterMarker) != TypeVariance.INV ? typeSystemCommonSuperTypesContext.asTypeArgument(kotlinTypeMarkerIntersectTypes) : typeSystemCommonSuperTypesContext.createTypeArgument(kotlinTypeMarkerIntersectTypes, TypeVariance.IN);
    }
}
