package org.jetbrains.kotlin.types;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.model.ArgumentList;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0002\u0010\u000bJ'\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0002\u0010\u000bJ&\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J&\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0010H\u0002J(\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0004H\u0002J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018J\u001e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0015J(\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\b\b\u0002\u0010 \u001a\u00020\u0004J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\"\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\tH\u0002J*\u0010$\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u0007J(\u0010$\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\b\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u00152\b\b\u0002\u0010 \u001a\u00020\u0004J\u001e\u0010%\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010J \u0010'\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002J \u0010(\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00152\b\b\u0002\u0010 \u001a\u00020\u0004J$\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\t0\u000eH\u0002J\u001e\u0010+\u001a\u0004\u0018\u00010,*\u00020-2\u0006\u0010.\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u0015H\u0002J\u0014\u00100\u001a\u00020\u0004*\u00020-2\u0006\u0010#\u001a\u00020\u0015H\u0002J\u001c\u00101\u001a\u00020\u0004*\u00020-2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0002J\u001a\u00102\u001a\u00020\u0004*\u00020\u00072\u0006\u00103\u001a\u0002042\u0006\u0010\n\u001a\u00020\tJ$\u00105\u001a\u00020\u0004*\u00020-2\u0006\u00106\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u00020\u0010H\u0002R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeChecker;", "", "()V", "RUN_SLOW_ASSERTIONS", "", "checkSubtypeForIntegerLiteralType", "state", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "subType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "superType", "(Lorg/jetbrains/kotlin/types/TypeCheckerState;Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;)Ljava/lang/Boolean;", "checkSubtypeForSpecialCases", "collectAllSupertypesWithGivenTypeConstructor", "", "superConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "collectAndFilter", "classType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "completeIsSubTypeOf", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isFromNullabilityConstraint", "effectiveVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "declared", "useSite", "equalTypes", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "context", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "stubTypesEqualToAnything", "findCorrespondingSupertypes", "hasNothingSupertype", ModuleXmlParser.TYPE, "isSubtypeOf", "isSubtypeOfClass", "typeConstructor", "isSubtypeOfForSingleClassifierType", "prepareType", "selectOnlyPureKotlinSupertypes", "supertypes", "getTypeParameterForArgumentInBaseIfItEqualToTarget", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "baseType", "targetType", "isCommonDenotableType", "isStubTypeSubtypeOfAnother", "isSubtypeForSameConstructor", "capturedSubArguments", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "isTypeVariableAgainstStarProjectionForSelfType", "subArgumentType", "superArgumentType", "selfConstructor", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbstractTypeChecker {
    public static final AbstractTypeChecker INSTANCE = new AbstractTypeChecker();
    public static boolean RUN_SLOW_ASSERTIONS;

    /* JADX INFO: compiled from: AbstractTypeChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TypeCheckerState.LowerCapturedTypePolicy.values().length];
            try {
                iArr2[TypeCheckerState.LowerCapturedTypePolicy.CHECK_ONLY_LOWER.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[TypeCheckerState.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[TypeCheckerState.LowerCapturedTypePolicy.SKIP_LOWER.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public final boolean isSubtypeOf(TypeCheckerState state, KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return isSubtypeOf$default(this, state, subType, superType, false, 8, (Object) null);
    }

    private AbstractTypeChecker() {
    }

    public static /* synthetic */ KotlinTypeMarker prepareType$default(AbstractTypeChecker abstractTypeChecker, TypeCheckerProviderContext typeCheckerProviderContext, KotlinTypeMarker kotlinTypeMarker, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return abstractTypeChecker.prepareType(typeCheckerProviderContext, kotlinTypeMarker, z);
    }

    public final KotlinTypeMarker prepareType(TypeCheckerProviderContext context, KotlinTypeMarker type, boolean stubTypesEqualToAnything) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        return context.newTypeCheckerState(true, stubTypesEqualToAnything).prepareType(type);
    }

    public static /* synthetic */ boolean isSubtypeOf$default(AbstractTypeChecker abstractTypeChecker, TypeCheckerProviderContext typeCheckerProviderContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return abstractTypeChecker.isSubtypeOf(typeCheckerProviderContext, kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    public final boolean isSubtypeOf(TypeCheckerProviderContext context, KotlinTypeMarker subType, KotlinTypeMarker superType, boolean stubTypesEqualToAnything) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return isSubtypeOf$default(this, context.newTypeCheckerState(true, stubTypesEqualToAnything), subType, superType, false, 8, (Object) null);
    }

    public final boolean isSubtypeOfClass(TypeCheckerState state, TypeConstructorMarker typeConstructor, TypeConstructorMarker superConstructor) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        Intrinsics.checkNotNullParameter(superConstructor, "superConstructor");
        if (Intrinsics.areEqual(typeConstructor, superConstructor)) {
            return true;
        }
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        Iterator<KotlinTypeMarker> it = typeSystemContext.supertypes(typeConstructor).iterator();
        while (it.hasNext()) {
            if (INSTANCE.isSubtypeOfClass(state, typeSystemContext.typeConstructor(it.next()), superConstructor)) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ boolean equalTypes$default(AbstractTypeChecker abstractTypeChecker, TypeCheckerProviderContext typeCheckerProviderContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return abstractTypeChecker.equalTypes(typeCheckerProviderContext, kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    public final boolean equalTypes(TypeCheckerProviderContext context, KotlinTypeMarker a, KotlinTypeMarker b, boolean stubTypesEqualToAnything) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return equalTypes(context.newTypeCheckerState(false, stubTypesEqualToAnything), a, b);
    }

    public static /* synthetic */ boolean isSubtypeOf$default(AbstractTypeChecker abstractTypeChecker, TypeCheckerState typeCheckerState, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return abstractTypeChecker.isSubtypeOf(typeCheckerState, kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    public final boolean isSubtypeOf(TypeCheckerState state, KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        if (subType == superType) {
            return true;
        }
        if (state.customIsSubtypeOf(subType, superType)) {
            return completeIsSubTypeOf(state, subType, superType, isFromNullabilityConstraint);
        }
        return false;
    }

    public final boolean equalTypes(TypeCheckerState state, KotlinTypeMarker a, KotlinTypeMarker b) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (a == b) {
            return true;
        }
        AbstractTypeChecker abstractTypeChecker = INSTANCE;
        if (abstractTypeChecker.isCommonDenotableType(typeSystemContext, a) && abstractTypeChecker.isCommonDenotableType(typeSystemContext, b)) {
            KotlinTypeMarker kotlinTypeMarkerPrepareType = state.prepareType(state.refineType(a));
            KotlinTypeMarker kotlinTypeMarkerPrepareType2 = state.prepareType(state.refineType(b));
            SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType);
            if (!typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(kotlinTypeMarkerPrepareType), typeSystemContext.typeConstructor(kotlinTypeMarkerPrepareType2))) {
                return false;
            }
            if (typeSystemContext.argumentsCount(simpleTypeMarkerLowerBoundIfFlexible) == 0) {
                return typeSystemContext.hasFlexibleNullability(kotlinTypeMarkerPrepareType) || typeSystemContext.hasFlexibleNullability(kotlinTypeMarkerPrepareType2) || typeSystemContext.isMarkedNullable(simpleTypeMarkerLowerBoundIfFlexible) == typeSystemContext.isMarkedNullable(typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType2));
            }
        }
        return isSubtypeOf$default(abstractTypeChecker, state, a, b, false, 8, (Object) null) && isSubtypeOf$default(abstractTypeChecker, state, b, a, false, 8, (Object) null);
    }

    private final boolean completeIsSubTypeOf(TypeCheckerState state, KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        KotlinTypeMarker kotlinTypeMarkerPrepareType = state.prepareType(state.refineType(subType));
        KotlinTypeMarker kotlinTypeMarkerPrepareType2 = state.prepareType(state.refineType(superType));
        AbstractTypeChecker abstractTypeChecker = INSTANCE;
        Boolean boolCheckSubtypeForSpecialCases = abstractTypeChecker.checkSubtypeForSpecialCases(state, typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType), typeSystemContext.upperBoundIfFlexible(kotlinTypeMarkerPrepareType2));
        if (boolCheckSubtypeForSpecialCases != null) {
            boolean zBooleanValue = boolCheckSubtypeForSpecialCases.booleanValue();
            state.addSubtypeConstraint(kotlinTypeMarkerPrepareType, kotlinTypeMarkerPrepareType2, isFromNullabilityConstraint);
            return zBooleanValue;
        }
        Boolean boolAddSubtypeConstraint = state.addSubtypeConstraint(kotlinTypeMarkerPrepareType, kotlinTypeMarkerPrepareType2, isFromNullabilityConstraint);
        return boolAddSubtypeConstraint != null ? boolAddSubtypeConstraint.booleanValue() : abstractTypeChecker.isSubtypeOfForSingleClassifierType(state, typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarkerPrepareType), typeSystemContext.upperBoundIfFlexible(kotlinTypeMarkerPrepareType2));
    }

    private final Boolean checkSubtypeForIntegerLiteralType(TypeCheckerState state, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (!typeSystemContext.isIntegerLiteralType(subType) && !typeSystemContext.isIntegerLiteralType(superType)) {
            return null;
        }
        if (checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeOrCapturedOne(typeSystemContext, subType) && checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeOrCapturedOne(typeSystemContext, superType)) {
            return true;
        }
        if (typeSystemContext.isIntegerLiteralType(subType)) {
            if (checkSubtypeForIntegerLiteralType$lambda$7$isTypeInIntegerLiteralType(typeSystemContext, state, subType, superType, false)) {
                return true;
            }
        } else if (typeSystemContext.isIntegerLiteralType(superType) && (checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeInIntersectionComponents(typeSystemContext, subType) || checkSubtypeForIntegerLiteralType$lambda$7$isTypeInIntegerLiteralType(typeSystemContext, state, superType, subType, true))) {
            return true;
        }
        return null;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isTypeInIntegerLiteralType(TypeSystemContext typeSystemContext, TypeCheckerState typeCheckerState, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2, boolean z) {
        Collection<KotlinTypeMarker> collectionPossibleIntegerTypes = typeSystemContext.possibleIntegerTypes(simpleTypeMarker);
        if ((collectionPossibleIntegerTypes instanceof Collection) && collectionPossibleIntegerTypes.isEmpty()) {
            return false;
        }
        for (KotlinTypeMarker kotlinTypeMarker : collectionPossibleIntegerTypes) {
            if (Intrinsics.areEqual(typeSystemContext.typeConstructor(kotlinTypeMarker), typeSystemContext.typeConstructor(simpleTypeMarker2)) || (z && isSubtypeOf$default(INSTANCE, typeCheckerState, (KotlinTypeMarker) simpleTypeMarker2, kotlinTypeMarker, false, 8, (Object) null))) {
                return true;
            }
        }
        return false;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeInIntersectionComponents(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker) {
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(simpleTypeMarker);
        if (typeConstructorMarkerTypeConstructor instanceof IntersectionTypeConstructorMarker) {
            Collection<KotlinTypeMarker> collectionSupertypes = typeSystemContext.supertypes(typeConstructorMarkerTypeConstructor);
            if (!(collectionSupertypes instanceof Collection) || !collectionSupertypes.isEmpty()) {
                Iterator<T> it = collectionSupertypes.iterator();
                while (it.hasNext()) {
                    SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType((KotlinTypeMarker) it.next());
                    if (simpleTypeMarkerAsSimpleType != null && typeSystemContext.isIntegerLiteralType(simpleTypeMarkerAsSimpleType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isCapturedIntegerLiteralType(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker) {
        if (!(simpleTypeMarker instanceof CapturedTypeMarker)) {
            return false;
        }
        TypeArgumentMarker typeArgumentMarkerProjection = typeSystemContext.projection(typeSystemContext.typeConstructor((CapturedTypeMarker) simpleTypeMarker));
        return !typeSystemContext.isStarProjection(typeArgumentMarkerProjection) && typeSystemContext.isIntegerLiteralType(typeSystemContext.upperBoundIfFlexible(typeSystemContext.getType(typeArgumentMarkerProjection)));
    }

    private static final boolean checkSubtypeForIntegerLiteralType$lambda$7$isIntegerLiteralTypeOrCapturedOne(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker) {
        return typeSystemContext.isIntegerLiteralType(simpleTypeMarker) || checkSubtypeForIntegerLiteralType$lambda$7$isCapturedIntegerLiteralType(typeSystemContext, simpleTypeMarker);
    }

    private final boolean hasNothingSupertype(TypeCheckerState state, SimpleTypeMarker type) {
        TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(type);
        if (typeSystemContext.isClassTypeConstructor(typeConstructorMarkerTypeConstructor)) {
            return typeSystemContext.isNothingConstructor(typeConstructorMarkerTypeConstructor);
        }
        if (typeSystemContext.isNothingConstructor(typeSystemContext.typeConstructor(type))) {
            return true;
        }
        state.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = state.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = state.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(type);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                if (typeSystemContext.isClassType(simpleTypeMarkerPop)) {
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext2 = state.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = lowerIfFlexible.mo7474transformType(state, it.next());
                        if (typeSystemContext.isNothingConstructor(typeSystemContext.typeConstructor(simpleTypeMarkerMo7474transformType))) {
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

    private final boolean isSubtypeOfForSingleClassifierType(final TypeCheckerState state, SimpleTypeMarker subType, final SimpleTypeMarker superType) {
        KotlinTypeMarker type;
        final TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (RUN_SLOW_ASSERTIONS) {
            if (!typeSystemContext.isSingleClassifierType(subType) && !typeSystemContext.isIntersection(typeSystemContext.typeConstructor(subType))) {
                state.isAllowedTypeVariable(subType);
            }
            if (!typeSystemContext.isSingleClassifierType(superType)) {
                state.isAllowedTypeVariable(superType);
            }
        }
        boolean z = false;
        if (!AbstractNullabilityChecker.INSTANCE.isPossibleSubtype(state, subType, superType)) {
            return false;
        }
        AbstractTypeChecker abstractTypeChecker = INSTANCE;
        SimpleTypeMarker simpleTypeMarker = subType;
        SimpleTypeMarker simpleTypeMarker2 = superType;
        Boolean boolCheckSubtypeForIntegerLiteralType = abstractTypeChecker.checkSubtypeForIntegerLiteralType(state, typeSystemContext.lowerBoundIfFlexible(simpleTypeMarker), typeSystemContext.upperBoundIfFlexible(simpleTypeMarker2));
        if (boolCheckSubtypeForIntegerLiteralType != null) {
            boolean zBooleanValue = boolCheckSubtypeForIntegerLiteralType.booleanValue();
            TypeCheckerState.addSubtypeConstraint$default(state, simpleTypeMarker, simpleTypeMarker2, false, 4, null);
            return zBooleanValue;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(superType);
        boolean z2 = true;
        if ((typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(subType), typeConstructorMarkerTypeConstructor) && typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor) == 0) || typeSystemContext.isAnyConstructor(typeSystemContext.typeConstructor(superType))) {
            return true;
        }
        List<SimpleTypeMarker> listFindCorrespondingSupertypes = abstractTypeChecker.findCorrespondingSupertypes(state, subType, typeConstructorMarkerTypeConstructor);
        int i = 10;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFindCorrespondingSupertypes, 10));
        for (SimpleTypeMarker simpleTypeMarker3 : listFindCorrespondingSupertypes) {
            SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType(state.prepareType(simpleTypeMarker3));
            if (simpleTypeMarkerAsSimpleType != null) {
                simpleTypeMarker3 = simpleTypeMarkerAsSimpleType;
            }
            arrayList.add(simpleTypeMarker3);
        }
        final ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        if (size == 0) {
            return INSTANCE.hasNothingSupertype(state, subType);
        }
        if (size == 1) {
            return INSTANCE.isSubtypeForSameConstructor(state, typeSystemContext.asArgumentList((SimpleTypeMarker) CollectionsKt.first((List) arrayList2)), superType);
        }
        ArgumentList argumentList = new ArgumentList(typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor));
        int iParametersCount = typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor);
        int i2 = 0;
        boolean z3 = false;
        while (i2 < iParametersCount) {
            z3 = (z3 || typeSystemContext.getVariance(typeSystemContext.getParameter(typeConstructorMarkerTypeConstructor, i2)) != TypeVariance.OUT) ? z2 : z;
            if (!z3) {
                ArrayList<SimpleTypeMarker> arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, i));
                for (SimpleTypeMarker simpleTypeMarker4 : arrayList3) {
                    TypeArgumentMarker argumentOrNull = typeSystemContext.getArgumentOrNull(simpleTypeMarker4, i2);
                    if (argumentOrNull != null) {
                        if (typeSystemContext.getVariance(argumentOrNull) != TypeVariance.INV) {
                            argumentOrNull = null;
                        }
                        if (argumentOrNull != null && (type = typeSystemContext.getType(argumentOrNull)) != null) {
                            arrayList4.add(type);
                        }
                    }
                    throw new IllegalStateException(("Incorrect type: " + simpleTypeMarker4 + ", subType: " + subType + ", superType: " + superType).toString());
                }
                argumentList.add(typeSystemContext.asTypeArgument(typeSystemContext.intersectTypes((List<? extends KotlinTypeMarker>) arrayList4)));
            }
            i2++;
            z = false;
            z2 = true;
            i = 10;
        }
        if (z3 || !INSTANCE.isSubtypeForSameConstructor(state, argumentList, superType)) {
            return state.runForkingPoint(new Function1<TypeCheckerState.ForkPointContext, Unit>() { // from class: org.jetbrains.kotlin.types.AbstractTypeChecker$isSubtypeOfForSingleClassifierType$1$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(TypeCheckerState.ForkPointContext forkPointContext) {
                    invoke2(forkPointContext);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(TypeCheckerState.ForkPointContext runForkingPoint) {
                    Intrinsics.checkNotNullParameter(runForkingPoint, "$this$runForkingPoint");
                    for (final SimpleTypeMarker simpleTypeMarker5 : arrayList2) {
                        final TypeCheckerState typeCheckerState = state;
                        final TypeSystemContext typeSystemContext2 = typeSystemContext;
                        final SimpleTypeMarker simpleTypeMarker6 = superType;
                        runForkingPoint.fork(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.types.AbstractTypeChecker$isSubtypeOfForSingleClassifierType$1$4.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            /* JADX WARN: Can't rename method to resolve collision */
                            @Override // kotlin.jvm.functions.Function0
                            public final Boolean invoke() {
                                return Boolean.valueOf(AbstractTypeChecker.INSTANCE.isSubtypeForSameConstructor(typeCheckerState, typeSystemContext2.asArgumentList(simpleTypeMarker5), simpleTypeMarker6));
                            }
                        });
                    }
                }
            });
        }
        return true;
    }

    private final boolean isTypeVariableAgainstStarProjectionForSelfType(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, TypeConstructorMarker typeConstructorMarker) {
        TypeParameterMarker typeParameter;
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemContext.asSimpleType(kotlinTypeMarker);
        if (!(simpleTypeMarkerAsSimpleType instanceof CapturedTypeMarker)) {
            return false;
        }
        CapturedTypeMarker capturedTypeMarker = (CapturedTypeMarker) simpleTypeMarkerAsSimpleType;
        if (typeSystemContext.isOldCapturedType(capturedTypeMarker) || !typeSystemContext.isStarProjection(typeSystemContext.projection(typeSystemContext.typeConstructor(capturedTypeMarker))) || typeSystemContext.captureStatus(capturedTypeMarker) != CaptureStatus.FOR_SUBTYPING) {
            return false;
        }
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(kotlinTypeMarker2);
        TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker = typeConstructorMarkerTypeConstructor instanceof TypeVariableTypeConstructorMarker ? (TypeVariableTypeConstructorMarker) typeConstructorMarkerTypeConstructor : null;
        return (typeVariableTypeConstructorMarker == null || (typeParameter = typeSystemContext.getTypeParameter(typeVariableTypeConstructorMarker)) == null || !typeSystemContext.hasRecursiveBounds(typeParameter, typeConstructorMarker)) ? false : true;
    }

    public final boolean isSubtypeForSameConstructor(TypeCheckerState typeCheckerState, TypeArgumentListMarker capturedSubArguments, SimpleTypeMarker superType) {
        boolean zEqualTypes;
        Intrinsics.checkNotNullParameter(typeCheckerState, "<this>");
        Intrinsics.checkNotNullParameter(capturedSubArguments, "capturedSubArguments");
        Intrinsics.checkNotNullParameter(superType, "superType");
        TypeSystemContext typeSystemContext = typeCheckerState.getTypeSystemContext();
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(superType);
        int size = typeSystemContext.size(capturedSubArguments);
        int iParametersCount = typeSystemContext.parametersCount(typeConstructorMarkerTypeConstructor);
        if (size == iParametersCount) {
            SimpleTypeMarker simpleTypeMarker = superType;
            if (size == typeSystemContext.argumentsCount(simpleTypeMarker)) {
                for (int i = 0; i < iParametersCount; i++) {
                    TypeArgumentMarker argument = typeSystemContext.getArgument(simpleTypeMarker, i);
                    if (!typeSystemContext.isStarProjection(argument)) {
                        KotlinTypeMarker type = typeSystemContext.getType(argument);
                        TypeArgumentMarker typeArgumentMarker = typeSystemContext.get(capturedSubArguments, i);
                        typeSystemContext.getVariance(typeArgumentMarker);
                        TypeVariance typeVariance = TypeVariance.INV;
                        KotlinTypeMarker type2 = typeSystemContext.getType(typeArgumentMarker);
                        AbstractTypeChecker abstractTypeChecker = INSTANCE;
                        TypeVariance typeVarianceEffectiveVariance = abstractTypeChecker.effectiveVariance(typeSystemContext.getVariance(typeSystemContext.getParameter(typeConstructorMarkerTypeConstructor, i)), typeSystemContext.getVariance(argument));
                        if (typeVarianceEffectiveVariance == null) {
                            return typeCheckerState.getIsErrorTypeEqualsToAnything();
                        }
                        if (typeVarianceEffectiveVariance != TypeVariance.INV || (!abstractTypeChecker.isTypeVariableAgainstStarProjectionForSelfType(typeSystemContext, type2, type, typeConstructorMarkerTypeConstructor) && !abstractTypeChecker.isTypeVariableAgainstStarProjectionForSelfType(typeSystemContext, type, type2, typeConstructorMarkerTypeConstructor))) {
                            if (typeCheckerState.argumentsDepth <= 100) {
                                typeCheckerState.argumentsDepth++;
                                int i2 = WhenMappings.$EnumSwitchMapping$0[typeVarianceEffectiveVariance.ordinal()];
                                if (i2 == 1) {
                                    zEqualTypes = abstractTypeChecker.equalTypes(typeCheckerState, type2, type);
                                } else if (i2 == 2) {
                                    zEqualTypes = isSubtypeOf$default(abstractTypeChecker, typeCheckerState, type2, type, false, 8, (Object) null);
                                } else {
                                    if (i2 != 3) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    zEqualTypes = isSubtypeOf$default(abstractTypeChecker, typeCheckerState, type, type2, false, 8, (Object) null);
                                }
                                typeCheckerState.argumentsDepth--;
                                if (!zEqualTypes) {
                                    return false;
                                }
                            } else {
                                throw new IllegalStateException(("Arguments depth is too high. Some related argument: " + type2).toString());
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private final boolean isCommonDenotableType(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
        return (!typeSystemContext.isDenotable(typeSystemContext.typeConstructor(kotlinTypeMarker)) || typeSystemContext.isDynamic(kotlinTypeMarker) || typeSystemContext.isDefinitelyNotNullType(kotlinTypeMarker) || typeSystemContext.isNotNullTypeParameter(kotlinTypeMarker) || !Intrinsics.areEqual(typeSystemContext.typeConstructor(typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarker)), typeSystemContext.typeConstructor(typeSystemContext.upperBoundIfFlexible(kotlinTypeMarker)))) ? false : true;
    }

    public final TypeVariance effectiveVariance(TypeVariance declared, TypeVariance useSite) {
        Intrinsics.checkNotNullParameter(declared, "declared");
        Intrinsics.checkNotNullParameter(useSite, "useSite");
        if (declared == TypeVariance.INV) {
            return useSite;
        }
        if (useSite == TypeVariance.INV || declared == useSite) {
            return declared;
        }
        return null;
    }

    private final boolean isStubTypeSubtypeOfAnother(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        SimpleTypeMarker simpleTypeMarkerOriginal;
        SimpleTypeMarker simpleTypeMarkerOriginal2;
        DefinitelyNotNullTypeMarker definitelyNotNullTypeMarkerAsDefinitelyNotNullType = typeSystemContext.asDefinitelyNotNullType(simpleTypeMarker);
        if (definitelyNotNullTypeMarkerAsDefinitelyNotNullType == null || (simpleTypeMarkerOriginal = typeSystemContext.original(definitelyNotNullTypeMarkerAsDefinitelyNotNullType)) == null) {
            simpleTypeMarkerOriginal = simpleTypeMarker;
        }
        DefinitelyNotNullTypeMarker definitelyNotNullTypeMarkerAsDefinitelyNotNullType2 = typeSystemContext.asDefinitelyNotNullType(simpleTypeMarker2);
        if (definitelyNotNullTypeMarkerAsDefinitelyNotNullType2 == null || (simpleTypeMarkerOriginal2 = typeSystemContext.original(definitelyNotNullTypeMarkerAsDefinitelyNotNullType2)) == null) {
            simpleTypeMarkerOriginal2 = simpleTypeMarker2;
        }
        if (typeSystemContext.typeConstructor(simpleTypeMarkerOriginal) != typeSystemContext.typeConstructor(simpleTypeMarkerOriginal2)) {
            return false;
        }
        if (typeSystemContext.isDefinitelyNotNullType(simpleTypeMarker) || !typeSystemContext.isDefinitelyNotNullType(simpleTypeMarker2)) {
            return !typeSystemContext.isMarkedNullable(simpleTypeMarker) || typeSystemContext.isMarkedNullable(simpleTypeMarker2);
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:80:0x013c  */
    /* JADX WARN: Code duplicated, block: B:82:0x0148  */
    private final Boolean checkSubtypeForSpecialCases(TypeCheckerState state, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        SimpleTypeMarker simpleTypeMarkerOriginal;
        TypeParameterMarker typeParameterForArgumentInBaseIfItEqualToTarget;
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        SimpleTypeMarker simpleTypeMarker = subType;
        boolean z = false;
        if (!typeSystemContext.isError(simpleTypeMarker)) {
            SimpleTypeMarker simpleTypeMarker2 = superType;
            if (!typeSystemContext.isError(simpleTypeMarker2)) {
                if (typeSystemContext.isStubTypeForBuilderInference(subType) && typeSystemContext.isStubTypeForBuilderInference(superType)) {
                    return Boolean.valueOf(INSTANCE.isStubTypeSubtypeOfAnother(typeSystemContext, subType, superType) || state.getIsStubTypeEqualsToAnything());
                }
                if (typeSystemContext.isStubType(subType) || typeSystemContext.isStubType(superType)) {
                    return Boolean.valueOf(state.getIsStubTypeEqualsToAnything());
                }
                DefinitelyNotNullTypeMarker definitelyNotNullTypeMarkerAsDefinitelyNotNullType = typeSystemContext.asDefinitelyNotNullType(superType);
                if (definitelyNotNullTypeMarkerAsDefinitelyNotNullType == null || (simpleTypeMarkerOriginal = typeSystemContext.original(definitelyNotNullTypeMarkerAsDefinitelyNotNullType)) == null) {
                    simpleTypeMarkerOriginal = superType;
                }
                CapturedTypeMarker capturedTypeMarkerAsCapturedType = typeSystemContext.asCapturedType(simpleTypeMarkerOriginal);
                KotlinTypeMarker kotlinTypeMarkerLowerType = capturedTypeMarkerAsCapturedType != null ? typeSystemContext.lowerType(capturedTypeMarkerAsCapturedType) : null;
                if (capturedTypeMarkerAsCapturedType != null && kotlinTypeMarkerLowerType != null) {
                    if (typeSystemContext.isMarkedNullable(superType)) {
                        kotlinTypeMarkerLowerType = typeSystemContext.withNullability(kotlinTypeMarkerLowerType, true);
                    } else if (typeSystemContext.isDefinitelyNotNullType(simpleTypeMarker2)) {
                        kotlinTypeMarkerLowerType = typeSystemContext.makeDefinitelyNotNullOrNotNull(kotlinTypeMarkerLowerType);
                    }
                    KotlinTypeMarker kotlinTypeMarker = kotlinTypeMarkerLowerType;
                    int i = WhenMappings.$EnumSwitchMapping$1[state.getLowerCapturedTypePolicy(subType, capturedTypeMarkerAsCapturedType).ordinal()];
                    if (i == 1) {
                        return Boolean.valueOf(isSubtypeOf$default(INSTANCE, state, (KotlinTypeMarker) simpleTypeMarker, kotlinTypeMarker, false, 8, (Object) null));
                    }
                    if (i == 2 && isSubtypeOf$default(INSTANCE, state, (KotlinTypeMarker) simpleTypeMarker, kotlinTypeMarker, false, 8, (Object) null)) {
                        return true;
                    }
                }
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemContext.typeConstructor(superType);
                if (typeSystemContext.isIntersection(typeConstructorMarkerTypeConstructor)) {
                    typeSystemContext.isMarkedNullable(superType);
                    Collection<KotlinTypeMarker> collectionSupertypes = typeSystemContext.supertypes(typeConstructorMarkerTypeConstructor);
                    if ((collectionSupertypes instanceof Collection) && collectionSupertypes.isEmpty()) {
                        z = true;
                    } else {
                        Iterator<T> it = collectionSupertypes.iterator();
                        while (it.hasNext()) {
                            if (!isSubtypeOf$default(INSTANCE, state, (KotlinTypeMarker) simpleTypeMarker, (KotlinTypeMarker) it.next(), false, 8, (Object) null)) {
                            }
                        }
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
                TypeConstructorMarker typeConstructorMarkerTypeConstructor2 = typeSystemContext.typeConstructor(subType);
                if (!(subType instanceof CapturedTypeMarker)) {
                    if (typeSystemContext.isIntersection(typeConstructorMarkerTypeConstructor2)) {
                        Collection<KotlinTypeMarker> collectionSupertypes2 = typeSystemContext.supertypes(typeConstructorMarkerTypeConstructor2);
                        if ((collectionSupertypes2 instanceof Collection) && collectionSupertypes2.isEmpty()) {
                            typeParameterForArgumentInBaseIfItEqualToTarget = INSTANCE.getTypeParameterForArgumentInBaseIfItEqualToTarget(state.getTypeSystemContext(), simpleTypeMarker2, simpleTypeMarker);
                            if (typeParameterForArgumentInBaseIfItEqualToTarget == null) {
                            }
                        } else {
                            Iterator<T> it2 = collectionSupertypes2.iterator();
                            while (it2.hasNext()) {
                                if (!(((KotlinTypeMarker) it2.next()) instanceof CapturedTypeMarker)) {
                                }
                            }
                            typeParameterForArgumentInBaseIfItEqualToTarget = INSTANCE.getTypeParameterForArgumentInBaseIfItEqualToTarget(state.getTypeSystemContext(), simpleTypeMarker2, simpleTypeMarker);
                            if (typeParameterForArgumentInBaseIfItEqualToTarget == null) {
                            }
                        }
                    }
                } else {
                    typeParameterForArgumentInBaseIfItEqualToTarget = INSTANCE.getTypeParameterForArgumentInBaseIfItEqualToTarget(state.getTypeSystemContext(), simpleTypeMarker2, simpleTypeMarker);
                    if (typeParameterForArgumentInBaseIfItEqualToTarget == null && typeSystemContext.hasRecursiveBounds(typeParameterForArgumentInBaseIfItEqualToTarget, typeSystemContext.typeConstructor(superType))) {
                        return true;
                    }
                }
                return null;
            }
        }
        if (state.getIsErrorTypeEqualsToAnything()) {
            return true;
        }
        if (!typeSystemContext.isMarkedNullable(subType) || typeSystemContext.isMarkedNullable(superType)) {
            return Boolean.valueOf(AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(typeSystemContext, typeSystemContext.withNullability(subType, false), typeSystemContext.withNullability(superType, false)));
        }
        return false;
    }

    private final TypeParameterMarker getTypeParameterForArgumentInBaseIfItEqualToTarget(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        KotlinTypeMarker type;
        int iArgumentsCount = typeSystemContext.argumentsCount(kotlinTypeMarker);
        int i = 0;
        while (true) {
            if (i >= iArgumentsCount) {
                return null;
            }
            TypeArgumentMarker argument = typeSystemContext.getArgument(kotlinTypeMarker, i);
            TypeArgumentMarker typeArgumentMarker = typeSystemContext.isStarProjection(argument) ? null : argument;
            if (typeArgumentMarker != null && (type = typeSystemContext.getType(typeArgumentMarker)) != null) {
                boolean z = typeSystemContext.isCapturedType(typeSystemContext.originalIfDefinitelyNotNullable(typeSystemContext.lowerBoundIfFlexible(type))) && typeSystemContext.isCapturedType(typeSystemContext.originalIfDefinitelyNotNullable(typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarker2)));
                if (Intrinsics.areEqual(type, kotlinTypeMarker2) || (z && Intrinsics.areEqual(typeSystemContext.typeConstructor(type), typeSystemContext.typeConstructor(kotlinTypeMarker2)))) {
                    return typeSystemContext.getParameter(typeSystemContext.typeConstructor(kotlinTypeMarker), i);
                }
                TypeParameterMarker typeParameterForArgumentInBaseIfItEqualToTarget = getTypeParameterForArgumentInBaseIfItEqualToTarget(typeSystemContext, type, kotlinTypeMarker2);
                if (typeParameterForArgumentInBaseIfItEqualToTarget != null) {
                    return typeParameterForArgumentInBaseIfItEqualToTarget;
                }
            }
            i++;
        }
    }

    private final List<SimpleTypeMarker> collectAllSupertypesWithGivenTypeConstructor(TypeCheckerState state, SimpleTypeMarker subType, TypeConstructorMarker superConstructor) {
        TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexibleSubstitutionSupertypePolicy;
        SimpleTypeMarker simpleTypeMarker = subType;
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        List<SimpleTypeMarker> listFastCorrespondingSupertypes = typeSystemContext.fastCorrespondingSupertypes(simpleTypeMarker, superConstructor);
        if (listFastCorrespondingSupertypes != null) {
            return listFastCorrespondingSupertypes;
        }
        if (!typeSystemContext.isClassTypeConstructor(superConstructor) && typeSystemContext.isClassType(simpleTypeMarker)) {
            return CollectionsKt.emptyList();
        }
        if (typeSystemContext.isCommonFinalClassConstructor(superConstructor)) {
            if (typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(simpleTypeMarker), superConstructor)) {
                SimpleTypeMarker simpleTypeMarkerCaptureFromArguments = typeSystemContext.captureFromArguments(simpleTypeMarker, CaptureStatus.FOR_SUBTYPING);
                if (simpleTypeMarkerCaptureFromArguments != null) {
                    simpleTypeMarker = simpleTypeMarkerCaptureFromArguments;
                }
                return CollectionsKt.listOf(simpleTypeMarker);
            }
            return CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        state.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = state.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = state.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(simpleTypeMarker);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + simpleTypeMarker + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                SimpleTypeMarker simpleTypeMarkerCaptureFromArguments2 = typeSystemContext.captureFromArguments(simpleTypeMarkerPop, CaptureStatus.FOR_SUBTYPING);
                if (simpleTypeMarkerCaptureFromArguments2 == null) {
                    simpleTypeMarkerCaptureFromArguments2 = simpleTypeMarkerPop;
                }
                if (typeSystemContext.areEqualTypeConstructors(typeSystemContext.typeConstructor(simpleTypeMarkerCaptureFromArguments2), superConstructor)) {
                    smartList.add(simpleTypeMarkerCaptureFromArguments2);
                    lowerIfFlexibleSubstitutionSupertypePolicy = TypeCheckerState.SupertypesPolicy.None.INSTANCE;
                } else if (typeSystemContext.argumentsCount(simpleTypeMarkerCaptureFromArguments2) == 0) {
                    lowerIfFlexibleSubstitutionSupertypePolicy = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                } else {
                    lowerIfFlexibleSubstitutionSupertypePolicy = state.getTypeSystemContext().substitutionSupertypePolicy(simpleTypeMarkerCaptureFromArguments2);
                }
                if (Intrinsics.areEqual(lowerIfFlexibleSubstitutionSupertypePolicy, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexibleSubstitutionSupertypePolicy = null;
                }
                if (lowerIfFlexibleSubstitutionSupertypePolicy != null) {
                    TypeSystemContext typeSystemContext2 = state.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        supertypesDeque.add(lowerIfFlexibleSubstitutionSupertypePolicy.mo7474transformType(state, it.next()));
                    }
                }
            }
        }
        state.clear();
        return smartList;
    }

    private final List<SimpleTypeMarker> collectAndFilter(TypeCheckerState state, SimpleTypeMarker classType, TypeConstructorMarker constructor) {
        return selectOnlyPureKotlinSupertypes(state, collectAllSupertypesWithGivenTypeConstructor(state, classType, constructor));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<SimpleTypeMarker> selectOnlyPureKotlinSupertypes(TypeCheckerState state, List<? extends SimpleTypeMarker> supertypes) {
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (supertypes.size() < 2) {
            return supertypes;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : supertypes) {
            TypeArgumentListMarker typeArgumentListMarkerAsArgumentList = typeSystemContext.asArgumentList((SimpleTypeMarker) obj);
            int size = typeSystemContext.size(typeArgumentListMarkerAsArgumentList);
            int i = 0;
            while (true) {
                if (i < size) {
                    if (typeSystemContext.asFlexibleType(typeSystemContext.getType(typeSystemContext.get(typeArgumentListMarkerAsArgumentList, i))) != null) {
                        break;
                    }
                    i++;
                } else {
                    arrayList.add(obj);
                    break;
                }
            }
        }
        ArrayList arrayList2 = arrayList;
        return !arrayList2.isEmpty() ? arrayList2 : supertypes;
    }

    public final List<SimpleTypeMarker> findCorrespondingSupertypes(TypeCheckerState state, SimpleTypeMarker subType, TypeConstructorMarker superConstructor) {
        TypeCheckerState.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superConstructor, "superConstructor");
        TypeSystemContext typeSystemContext = state.getTypeSystemContext();
        if (typeSystemContext.isClassType(subType)) {
            return INSTANCE.collectAndFilter(state, subType, superConstructor);
        }
        if (!typeSystemContext.isClassTypeConstructor(superConstructor) && !typeSystemContext.isIntegerLiteralTypeConstructor(superConstructor)) {
            return INSTANCE.collectAllSupertypesWithGivenTypeConstructor(state, subType, superConstructor);
        }
        SmartList<SimpleTypeMarker> smartList = new SmartList();
        state.initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = state.getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = state.getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(subType);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + subType + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                if (typeSystemContext.isClassType(simpleTypeMarkerPop)) {
                    smartList.add(simpleTypeMarkerPop);
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = TypeCheckerState.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                if (Intrinsics.areEqual(lowerIfFlexible, TypeCheckerState.SupertypesPolicy.None.INSTANCE)) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    TypeSystemContext typeSystemContext2 = state.getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext2.supertypes(typeSystemContext2.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        supertypesDeque.add(lowerIfFlexible.mo7474transformType(state, it.next()));
                    }
                }
            }
        }
        state.clear();
        ArrayList arrayList = new ArrayList();
        for (SimpleTypeMarker simpleTypeMarker : smartList) {
            AbstractTypeChecker abstractTypeChecker = INSTANCE;
            Intrinsics.checkNotNull(simpleTypeMarker);
            CollectionsKt.addAll(arrayList, abstractTypeChecker.collectAndFilter(state, simpleTypeMarker, superConstructor));
        }
        return arrayList;
    }
}
