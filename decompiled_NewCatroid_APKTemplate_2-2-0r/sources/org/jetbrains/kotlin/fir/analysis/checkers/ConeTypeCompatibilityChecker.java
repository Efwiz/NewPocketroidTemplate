package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.PrimitivesKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralConstantType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010#\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0005;<=>?B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J$\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0002JD\u0010\u0016\u001a\u00020\u0017*\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u00182\u0006\u0010\u001b\u001a\u00020\b2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000e*\u0004\u0018\u00010#H\u0002J0\u0010$\u001a\u00020\u0017*\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u00182\u0006\u0010%\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\u001a\u0010&\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d*\u00020\"2\u0006\u0010\u001b\u001a\u00020\bH\u0002J\u001a\u0010'\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020#0\u00142\u0006\u0010\u001b\u001a\u00020\bH\u0002J@\u0010'\u001a\u00020\u0012*\u00020\b2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\"0\u000e2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0\u000e2\u0006\u0010\u0015\u001a\u00020\u00122\u000e\b\u0002\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00190+H\u0002J\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0-*\u0006\u0012\u0002\b\u00030\u001dH\u0002J\u001a\u0010.\u001a\u0004\u0018\u00010\u0019*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010/\u001a\u000200H\u0002J\u001a\u00101\u001a\u00020\u0012*\u00020\b2\u0006\u00102\u001a\u00020#2\u0006\u00103\u001a\u00020#J\f\u00104\u001a\u00020\u0007*\u00020#H\u0002J\u0016\u00105\u001a\u0004\u0018\u00010\u000f*\u0002062\u0006\u0010\u001b\u001a\u00020\bH\u0002J\u0016\u00105\u001a\u0004\u0018\u00010\u000f*\u00020\"2\u0006\u0010\u001b\u001a\u00020\bH\u0002J,\u00107\u001a\u0004\u0018\u000108*\u00020\"2\u0006\u0010\u001b\u001a\u00020\b2\u0014\b\u0002\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020 0:H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\u0007*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker;", "", "()V", "javaClassClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "kotlinClassClassId", "prohibitComparisonOfIncompatibleClasses", "", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "getProhibitComparisonOfIncompatibleClasses", "(Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;)Z", "prohibitComparisonOfIncompatibleEnums", "getProhibitComparisonOfIncompatibleEnums", "computeLeafClassesOrInterfaces", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$FirClassWithSuperClasses;", "upperBoundClasses", "areClassesOrInterfacesCompatible", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$Compatibility;", "classesOrInterfaces", "", "compatibilityUpperBound", "collect", "", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$BoundTypeArguments;", "ctx", "typeParameterOwner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "parameter", "boundTypeArgument", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$BoundTypeArgument;", "collectLowerBounds", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "collectTypeArgumentMapping", "coneType", "getClassLikeElement", "getCompatibility", "upperBounds", "lowerBounds", "checkedTypeParameters", "", "getSuperTypes", "", "getTypeParameter", "index", "", "isCompatible", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "isConcreteType", "toFirClassWithSuperClasses", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "toTypeArgumentMapping", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$TypeArgumentMapping;", "envMapping", "", "BoundTypeArgument", "BoundTypeArguments", "Compatibility", "FirClassWithSuperClasses", "TypeArgumentMapping", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeCompatibilityChecker {
    public static final ConeTypeCompatibilityChecker INSTANCE = new ConeTypeCompatibilityChecker();
    private static final ClassId javaClassClassId;
    private static final ClassId kotlinClassClassId;

    /* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000f\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$Compatibility;", "", "", "(Ljava/lang/String;I)V", "COMPATIBLE", "SOFT_INCOMPATIBLE", "HARD_INCOMPATIBLE", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Compatibility implements Comparable<Compatibility> {
        COMPATIBLE,
        SOFT_INCOMPATIBLE,
        HARD_INCOMPATIBLE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Compatibility> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ConeTypeCompatibilityChecker() {
    }

    static {
        ClassId classIdFromString = ClassId.fromString(TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME);
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        javaClassClassId = classIdFromString;
        ClassId classIdFromString2 = ClassId.fromString("kotlin/reflect/KClass");
        Intrinsics.checkNotNullExpressionValue(classIdFromString2, "fromString(...)");
        kotlinClassClassId = classIdFromString2;
    }

    public final Compatibility isCompatible(ConeInferenceContext coneInferenceContext, ConeKotlinType a, ConeKotlinType b) {
        Intrinsics.checkNotNullParameter(coneInferenceContext, "<this>");
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (ConeBuiltinTypeUtilsKt.isNothing(a) || ConeBuiltinTypeUtilsKt.isNothing(b)) {
            return Compatibility.COMPATIBLE;
        }
        if (a instanceof ConeIntersectionType) {
            Iterator<T> it = ((ConeIntersectionType) a).getIntersectedTypes().iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            Compatibility compatibilityIsCompatible = INSTANCE.isCompatible(coneInferenceContext, (ConeKotlinType) it.next(), b);
            while (it.hasNext()) {
                Compatibility compatibilityIsCompatible2 = INSTANCE.isCompatible(coneInferenceContext, (ConeKotlinType) it.next(), b);
                if (compatibilityIsCompatible.compareTo(compatibilityIsCompatible2) > 0) {
                    compatibilityIsCompatible = compatibilityIsCompatible2;
                }
            }
            return compatibilityIsCompatible;
        }
        if (b instanceof ConeIntersectionType) {
            Iterator<T> it2 = ((ConeIntersectionType) b).getIntersectedTypes().iterator();
            if (!it2.hasNext()) {
                throw new NoSuchElementException();
            }
            Compatibility compatibilityIsCompatible3 = INSTANCE.isCompatible(coneInferenceContext, a, (ConeKotlinType) it2.next());
            while (it2.hasNext()) {
                Compatibility compatibilityIsCompatible4 = INSTANCE.isCompatible(coneInferenceContext, a, (ConeKotlinType) it2.next());
                if (compatibilityIsCompatible3.compareTo(compatibilityIsCompatible4) > 0) {
                    compatibilityIsCompatible3 = compatibilityIsCompatible4;
                }
            }
            return compatibilityIsCompatible3;
        }
        ConeKotlinType coneKotlinTypeIntersectTypesOrNull = TypeUtilsKt.intersectTypesOrNull(coneInferenceContext, CollectionsKt.listOf((Object[]) new ConeKotlinType[]{a, b}));
        if (coneKotlinTypeIntersectTypesOrNull instanceof ConeIntersectionType) {
            return getCompatibility(((ConeIntersectionType) coneKotlinTypeIntersectTypesOrNull).getIntersectedTypes(), coneInferenceContext);
        }
        return (coneKotlinTypeIntersectTypesOrNull == null || !ConeBuiltinTypeUtilsKt.isNothing(coneKotlinTypeIntersectTypesOrNull)) ? Compatibility.COMPATIBLE : Compatibility.HARD_INCOMPATIBLE;
    }

    private final Compatibility getCompatibility(Collection<? extends ConeKotlinType> collection, ConeInferenceContext coneInferenceContext) {
        Compatibility compatibility;
        Collection<? extends ConeKotlinType> collection2 = collection;
        boolean z = collection2 instanceof Collection;
        if (!z || !collection2.isEmpty()) {
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                if (!coneInferenceContext.isNullableType((ConeKotlinType) it.next())) {
                    if (z && collection2.isEmpty()) {
                        compatibility = Compatibility.HARD_INCOMPATIBLE;
                        break;
                    }
                    Iterator<T> it2 = collection2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (!INSTANCE.isConcreteType((ConeKotlinType) it2.next())) {
                                compatibility = Compatibility.SOFT_INCOMPATIBLE;
                                break;
                            }
                        } else {
                            compatibility = Compatibility.HARD_INCOMPATIBLE;
                            break;
                        }
                    }
                    Compatibility compatibility2 = compatibility;
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it3 = collection2.iterator();
                    while (it3.hasNext()) {
                        CollectionsKt.addAll(arrayList, FirHelpersKt.collectUpperBounds((ConeKotlinType) it3.next()));
                    }
                    return getCompatibility$default(this, coneInferenceContext, CollectionsKt.toSet(arrayList), SetsKt.emptySet(), compatibility2, null, 8, null);
                }
            }
        }
        return Compatibility.COMPATIBLE;
    }

    private final boolean isConcreteType(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            return true;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return isConcreteType(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
        }
        if (coneKotlinType instanceof ConeIntersectionType) {
            Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
            if ((intersectedTypes instanceof Collection) && intersectedTypes.isEmpty()) {
                return true;
            }
            Iterator<T> it = intersectedTypes.iterator();
            while (it.hasNext()) {
                if (!INSTANCE.isConcreteType((ConeKotlinType) it.next())) {
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Compatibility getCompatibility$default(ConeTypeCompatibilityChecker coneTypeCompatibilityChecker, ConeInferenceContext coneInferenceContext, Set set, Set set2, Compatibility compatibility, Set set3, int i, Object obj) {
        if ((i & 8) != 0) {
            set3 = new LinkedHashSet();
        }
        return coneTypeCompatibilityChecker.getCompatibility(coneInferenceContext, set, set2, compatibility, set3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Compatibility getCompatibility(final ConeInferenceContext coneInferenceContext, Set<? extends ConeClassLikeType> set, Set<? extends ConeClassLikeType> set2, Compatibility compatibility, final Set<FirTypeParameterSymbol> set3) {
        Set<FirClassWithSuperClasses> setEmptySet;
        Set<? extends ConeClassLikeType> set4 = set;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = set4.iterator();
        while (it.hasNext()) {
            FirClassWithSuperClasses firClassWithSuperClasses = INSTANCE.toFirClassWithSuperClasses((ConeClassLikeType) it.next(), coneInferenceContext);
            if (firClassWithSuperClasses != null) {
                arrayList.add(firClassWithSuperClasses);
            }
        }
        Set<FirClassWithSuperClasses> set5 = CollectionsKt.toSet(arrayList);
        if (set2.isEmpty() && (set.size() < 2 || areClassesOrInterfacesCompatible(coneInferenceContext, set5, compatibility) == Compatibility.COMPATIBLE)) {
            return Compatibility.COMPATIBLE;
        }
        if (!(set4 instanceof Collection) || !set4.isEmpty()) {
            for (ConeClassLikeType coneClassLikeType : set4) {
                if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneClassLikeType), javaClassClassId) || Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneClassLikeType), kotlinClassClassId)) {
                    return Compatibility.COMPATIBLE;
                }
            }
        }
        Set<FirClassWithSuperClasses> setComputeLeafClassesOrInterfaces = computeLeafClassesOrInterfaces(set5);
        Compatibility compatibilityAreClassesOrInterfacesCompatible = areClassesOrInterfacesCompatible(coneInferenceContext, setComputeLeafClassesOrInterfaces, compatibility);
        if (compatibilityAreClassesOrInterfacesCompatible != null) {
            return compatibilityAreClassesOrInterfacesCompatible;
        }
        Set<? extends ConeClassLikeType> set6 = set2;
        if (!(set6 instanceof Collection) || !set6.isEmpty()) {
            Iterator<T> it2 = set6.iterator();
            while (it2.hasNext()) {
                FirClassWithSuperClasses firClassWithSuperClasses2 = INSTANCE.toFirClassWithSuperClasses((ConeClassLikeType) it2.next(), coneInferenceContext);
                if (firClassWithSuperClasses2 == null || (setEmptySet = firClassWithSuperClasses2.getThisAndAllSuperClasses()) == null) {
                    setEmptySet = SetsKt.emptySet();
                }
                Set<FirClassWithSuperClasses> set7 = setComputeLeafClassesOrInterfaces;
                if (!(set7 instanceof Collection) || !set7.isEmpty()) {
                    Iterator<T> it3 = set7.iterator();
                    while (it3.hasNext()) {
                        if (!setEmptySet.contains((FirClassWithSuperClasses) it3.next())) {
                            return compatibility;
                        }
                    }
                }
            }
        }
        if (set.size() < 2) {
            return Compatibility.COMPATIBLE;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<? extends ConeClassLikeType> it4 = set.iterator();
        while (it4.hasNext()) {
            INSTANCE.collectTypeArgumentMapping(linkedHashMap, it4.next(), coneInferenceContext, compatibility);
        }
        Compatibility compatibility2 = Compatibility.COMPATIBLE;
        for (Compatibility compatibility3 : SequencesKt.map(MapsKt.asSequence(linkedHashMap), new Function1<Map.Entry<? extends FirTypeParameterSymbol, ? extends BoundTypeArguments>, Compatibility>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.ConeTypeCompatibilityChecker$getCompatibility$typeArgsCompatibility$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ ConeTypeCompatibilityChecker.Compatibility invoke(Map.Entry<? extends FirTypeParameterSymbol, ? extends ConeTypeCompatibilityChecker.BoundTypeArguments> entry) {
                return invoke2((Map.Entry<FirTypeParameterSymbol, ConeTypeCompatibilityChecker.BoundTypeArguments>) entry);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final ConeTypeCompatibilityChecker.Compatibility invoke2(Map.Entry<FirTypeParameterSymbol, ConeTypeCompatibilityChecker.BoundTypeArguments> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                FirTypeParameterSymbol key = entry.getKey();
                ConeTypeCompatibilityChecker.BoundTypeArguments value = entry.getValue();
                Set<ConeClassLikeType> setComponent1 = value.component1();
                Set<ConeClassLikeType> setComponent2 = value.component2();
                ConeTypeCompatibilityChecker.Compatibility compatibilityUpperBound = value.getCompatibilityUpperBound();
                if (set3.contains(key)) {
                    return ConeTypeCompatibilityChecker.Compatibility.COMPATIBLE;
                }
                set3.add(key);
                return ConeTypeCompatibilityChecker.INSTANCE.getCompatibility(coneInferenceContext, setComponent1, setComponent2, compatibilityUpperBound, set3);
            }
        })) {
            if (compatibility3 == compatibility) {
                return compatibility3;
            }
            if (compatibility3.compareTo(compatibility2) > 0) {
                compatibility2 = compatibility3;
            }
        }
        return compatibility2;
    }

    private final Set<FirClassWithSuperClasses> computeLeafClassesOrInterfaces(Set<FirClassWithSuperClasses> upperBoundClasses) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : upperBoundClasses) {
            linkedHashMap.put(obj, true);
        }
        ArrayDeque arrayDeque = new ArrayDeque(upperBoundClasses);
        while (!arrayDeque.isEmpty()) {
            for (FirClassWithSuperClasses firClassWithSuperClasses : ((FirClassWithSuperClasses) arrayDeque.removeFirst()).getSuperClasses()) {
                Boolean bool = (Boolean) linkedHashMap.get(firClassWithSuperClasses);
                if (Intrinsics.areEqual((Object) bool, (Object) true)) {
                    linkedHashMap.put(firClassWithSuperClasses, false);
                } else if (!Intrinsics.areEqual((Object) bool, (Object) false)) {
                    linkedHashMap.put(firClassWithSuperClasses, false);
                    arrayDeque.addLast(firClassWithSuperClasses);
                }
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (((Boolean) entry.getValue()).booleanValue()) {
                linkedHashMap2.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap2.keySet();
    }

    private final Compatibility areClassesOrInterfacesCompatible(ConeInferenceContext coneInferenceContext, Collection<FirClassWithSuperClasses> collection, Compatibility compatibility) {
        Object next;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (!((FirClassWithSuperClasses) obj).isInterface()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() >= 2) {
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    if (((FirClassWithSuperClasses) it.next()).getHasPredefinedEqualityContract(coneInferenceContext)) {
                        return compatibility;
                    }
                }
            }
            return Compatibility.SOFT_INCOMPATIBLE;
        }
        Iterator it2 = arrayList2.iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (!((FirClassWithSuperClasses) next).isFinal());
        FirClassWithSuperClasses firClassWithSuperClasses = (FirClassWithSuperClasses) next;
        if (firClassWithSuperClasses != null && collection.size() > arrayList2.size()) {
            return firClassWithSuperClasses.getHasPredefinedEqualityContract(coneInferenceContext) ? compatibility : Compatibility.SOFT_INCOMPATIBLE;
        }
        return null;
    }

    private final Set<ConeClassLikeType> collectLowerBounds(ConeKotlinType coneKotlinType) {
        Set<ConeClassLikeType> set;
        if (coneKotlinType == null) {
            return SetsKt.emptySet();
        }
        if (coneKotlinType instanceof ConeErrorType) {
            return SetsKt.emptySet();
        }
        if (coneKotlinType instanceof ConeLookupTagBasedType) {
            if (coneKotlinType instanceof ConeClassLikeType) {
                return SetsKt.setOf(coneKotlinType);
            }
            if (!(coneKotlinType instanceof ConeTypeVariableType) && !(coneKotlinType instanceof ConeTypeParameterType)) {
                throw new IllegalStateException("missing branch for " + coneKotlinType.getClass().getName());
            }
            return SetsKt.emptySet();
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return collectLowerBounds(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
        }
        if (!(coneKotlinType instanceof ConeIntersectionType)) {
            if (coneKotlinType instanceof ConeFlexibleType) {
                return collectLowerBounds(((ConeFlexibleType) coneKotlinType).getLowerBound());
            }
            if (!(coneKotlinType instanceof ConeCapturedType)) {
                if (coneKotlinType instanceof ConeIntegerConstantOperatorType) {
                    return SetsKt.setOf(ConeIntegerLiteralType.getApproximatedType$default((ConeIntegerLiteralType) coneKotlinType, null, 1, null));
                }
                if (coneKotlinType instanceof ConeStubType ? true : coneKotlinType instanceof ConeIntegerLiteralConstantType) {
                    throw new IllegalStateException(coneKotlinType + " should not reach here");
                }
                throw new NoWhenBranchMatchedException();
            }
            List<ConeKotlinType> supertypes = ((ConeCapturedType) coneKotlinType).getConstructor().getSupertypes();
            if (supertypes != null) {
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = supertypes.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, INSTANCE.collectLowerBounds((ConeKotlinType) it.next()));
                }
                set = CollectionsKt.toSet(arrayList);
            } else {
                set = null;
            }
            return set == null ? SetsKt.emptySet() : set;
        }
        Collection<ConeKotlinType> intersectedTypes = ((ConeIntersectionType) coneKotlinType).getIntersectedTypes();
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = intersectedTypes.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, INSTANCE.collectLowerBounds((ConeKotlinType) it2.next()));
        }
        return CollectionsKt.toSet(arrayList2);
    }

    private final void collectTypeArgumentMapping(Map<FirTypeParameterSymbol, BoundTypeArguments> map, ConeClassLikeType coneClassLikeType, ConeInferenceContext coneInferenceContext, Compatibility compatibility) {
        ArrayDeque arrayDeque = new ArrayDeque();
        TypeArgumentMapping typeArgumentMapping$default = toTypeArgumentMapping$default(this, coneClassLikeType, coneInferenceContext, null, 2, null);
        if (typeArgumentMapping$default == null) {
            return;
        }
        arrayDeque.addLast(typeArgumentMapping$default);
        while (!arrayDeque.isEmpty()) {
            TypeArgumentMapping typeArgumentMapping = (TypeArgumentMapping) arrayDeque.removeFirst();
            FirClassLikeSymbol<?> firClassLikeSymbolComponent1 = typeArgumentMapping.component1();
            Map<FirTypeParameterSymbol, BoundTypeArgument> mapComponent2 = typeArgumentMapping.component2();
            Iterator<ConeClassLikeType> it = getSuperTypes(firClassLikeSymbolComponent1).iterator();
            while (it.hasNext()) {
                TypeArgumentMapping typeArgumentMapping2 = toTypeArgumentMapping(it.next(), coneInferenceContext, mapComponent2);
                if (typeArgumentMapping2 != null) {
                    arrayDeque.addLast(typeArgumentMapping2);
                }
            }
            for (Map.Entry<FirTypeParameterSymbol, BoundTypeArgument> entry : mapComponent2.entrySet()) {
                collect(map, coneInferenceContext, firClassLikeSymbolComponent1, entry.getKey(), entry.getValue(), compatibility);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ TypeArgumentMapping toTypeArgumentMapping$default(ConeTypeCompatibilityChecker coneTypeCompatibilityChecker, ConeClassLikeType coneClassLikeType, ConeInferenceContext coneInferenceContext, Map map, int i, Object obj) {
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        return coneTypeCompatibilityChecker.toTypeArgumentMapping(coneClassLikeType, coneInferenceContext, map);
    }

    private final TypeArgumentMapping toTypeArgumentMapping(ConeClassLikeType coneClassLikeType, ConeInferenceContext coneInferenceContext, Map<FirTypeParameterSymbol, BoundTypeArgument> map) {
        BoundTypeArgument boundTypeArgument;
        BoundTypeArgument boundTypeArgument2;
        FirClassLikeSymbol<?> classLikeElement = getClassLikeElement(coneClassLikeType, coneInferenceContext);
        if (classLikeElement == null) {
            return null;
        }
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        int length = typeArguments.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            ConeTypeProjection coneTypeProjection = typeArguments[i];
            int i3 = i2 + 1;
            FirTypeParameterSymbol typeParameter = INSTANCE.getTypeParameter(classLikeElement, i2);
            if (typeParameter != null && !Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE)) {
                if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
                    boundTypeArgument = new BoundTypeArgument(((ConeKotlinTypeProjectionIn) coneTypeProjection).getType(), Variance.IN_VARIANCE);
                } else if (coneTypeProjection instanceof ConeKotlinTypeProjectionOut) {
                    boundTypeArgument = new BoundTypeArgument(((ConeKotlinTypeProjectionOut) coneTypeProjection).getType(), Variance.OUT_VARIANCE);
                } else if (coneTypeProjection instanceof ConeKotlinTypeConflictingProjection) {
                    boundTypeArgument = new BoundTypeArgument(((ConeKotlinTypeConflictingProjection) coneTypeProjection).getType(), Variance.INVARIANT);
                } else {
                    if (!(coneTypeProjection instanceof ConeKotlinType)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i4 = WhenMappings.$EnumSwitchMapping$0[typeParameter.getVariance().ordinal()];
                    if (i4 == 1) {
                        boundTypeArgument = new BoundTypeArgument(((ConeKotlinType) coneTypeProjection).getType(), Variance.IN_VARIANCE);
                    } else if (i4 == 2) {
                        boundTypeArgument = new BoundTypeArgument(((ConeKotlinType) coneTypeProjection).getType(), Variance.OUT_VARIANCE);
                    } else {
                        boundTypeArgument = new BoundTypeArgument(((ConeKotlinType) coneTypeProjection).getType(), Variance.INVARIANT);
                    }
                }
                ConeKotlinType type = boundTypeArgument.getType();
                if ((type instanceof ConeTypeParameterType) && (boundTypeArgument2 = map.get(((ConeTypeParameterType) type).getLookupTag().getTypeParameterSymbol())) != null) {
                    boundTypeArgument = boundTypeArgument2;
                }
                mapCreateMapBuilder.put(typeParameter, boundTypeArgument);
            }
            i++;
            i2 = i3;
        }
        return new TypeArgumentMapping(classLikeElement, MapsKt.build(mapCreateMapBuilder));
    }

    private final void collect(Map<FirTypeParameterSymbol, BoundTypeArguments> map, final ConeInferenceContext coneInferenceContext, final FirClassLikeSymbol<?> firClassLikeSymbol, FirTypeParameterSymbol firTypeParameterSymbol, BoundTypeArgument boundTypeArgument, final Compatibility compatibility) {
        final Function1<FirTypeParameterSymbol, BoundTypeArguments> function1 = new Function1<FirTypeParameterSymbol, BoundTypeArguments>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.ConeTypeCompatibilityChecker.collect.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final BoundTypeArguments invoke(FirTypeParameterSymbol it) {
                Compatibility compatibility2;
                Intrinsics.checkNotNullParameter(it, "it");
                if ((ConeTypeCompatibilityChecker.INSTANCE.getProhibitComparisonOfIncompatibleEnums(coneInferenceContext) && Intrinsics.areEqual(firClassLikeSymbol.getClassId(), StandardClassIds.INSTANCE.getEnum())) || (ConeTypeCompatibilityChecker.INSTANCE.getProhibitComparisonOfIncompatibleClasses(coneInferenceContext) && Intrinsics.areEqual(firClassLikeSymbol.getClassId(), StandardClassIds.INSTANCE.getKClass()))) {
                    compatibility2 = compatibility;
                } else {
                    compatibility2 = Compatibility.SOFT_INCOMPATIBLE;
                }
                return new BoundTypeArguments(new LinkedHashSet(), new LinkedHashSet(), compatibility2);
            }
        };
        BoundTypeArguments boundTypeArgumentsComputeIfAbsent = map.computeIfAbsent(firTypeParameterSymbol, new Function() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.ConeTypeCompatibilityChecker$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ConeTypeCompatibilityChecker.collect$lambda$22(function1, obj);
            }
        });
        ConeKotlinType type = boundTypeArgument.getType();
        if (boundTypeArgument.getVariance().getAllowsInPosition()) {
            CollectionsKt.addAll(boundTypeArgumentsComputeIfAbsent.getLower(), INSTANCE.collectLowerBounds(type));
        }
        if (boundTypeArgument.getVariance().getAllowsOutPosition()) {
            CollectionsKt.addAll(boundTypeArgumentsComputeIfAbsent.getUpper(), FirHelpersKt.collectUpperBounds(type));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final BoundTypeArguments collect$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (BoundTypeArguments) tmp0.invoke(obj);
    }

    private final List<ConeClassLikeType> getSuperTypes(FirClassLikeSymbol<?> firClassLikeSymbol) {
        if (!(firClassLikeSymbol instanceof FirTypeAliasSymbol)) {
            if (!(firClassLikeSymbol instanceof FirClassSymbol)) {
                return CollectionsKt.emptyList();
            }
            List<FirResolvedTypeRef> resolvedSuperTypeRefs = ((FirClassSymbol) firClassLikeSymbol).getResolvedSuperTypeRefs();
            ArrayList arrayList = new ArrayList();
            for (FirResolvedTypeRef firResolvedTypeRef : resolvedSuperTypeRefs) {
                FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef instanceof FirResolvedTypeRef ? firResolvedTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
                if (!(type instanceof ConeClassLikeType)) {
                    type = null;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (coneClassLikeType != null) {
                    arrayList.add(coneClassLikeType);
                }
            }
            return arrayList;
        }
        FirResolvedTypeRef resolvedExpandedTypeRef = ((FirTypeAliasSymbol) firClassLikeSymbol).getResolvedExpandedTypeRef();
        FirResolvedTypeRef firResolvedTypeRef3 = resolvedExpandedTypeRef instanceof FirResolvedTypeRef ? resolvedExpandedTypeRef : null;
        ConeKotlinType type2 = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
        return CollectionsKt.listOfNotNull((ConeClassLikeType) (type2 instanceof ConeClassLikeType ? type2 : null));
    }

    private final FirClassLikeSymbol<?> getClassLikeElement(ConeClassLikeType coneClassLikeType, ConeInferenceContext coneInferenceContext) {
        return LookupTagUtilsKt.getSymbolByLookupTag(coneInferenceContext.getSymbolProvider(), coneClassLikeType.getLookupTag());
    }

    private final FirTypeParameterSymbol getTypeParameter(FirClassLikeSymbol<?> firClassLikeSymbol, int i) {
        if ((firClassLikeSymbol instanceof FirTypeAliasSymbol) || (firClassLikeSymbol instanceof FirClassSymbol)) {
            return firClassLikeSymbol.getTypeParameterSymbols().get(i);
        }
        return null;
    }

    /* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\r\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u0015\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005HÆ\u0003J-\u0010\u000f\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$TypeArgumentMapping;", "", "typeParameterOwner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "mapping", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$BoundTypeArgument;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;Ljava/util/Map;)V", "getMapping", "()Ljava/util/Map;", "getTypeParameterOwner", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class TypeArgumentMapping {
        private final Map<FirTypeParameterSymbol, BoundTypeArgument> mapping;
        private final FirClassLikeSymbol<?> typeParameterOwner;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TypeArgumentMapping copy$default(TypeArgumentMapping typeArgumentMapping, FirClassLikeSymbol firClassLikeSymbol, Map map, int i, Object obj) {
            if ((i & 1) != 0) {
                firClassLikeSymbol = typeArgumentMapping.typeParameterOwner;
            }
            if ((i & 2) != 0) {
                map = typeArgumentMapping.mapping;
            }
            return typeArgumentMapping.copy(firClassLikeSymbol, map);
        }

        public final FirClassLikeSymbol<?> component1() {
            return this.typeParameterOwner;
        }

        public final Map<FirTypeParameterSymbol, BoundTypeArgument> component2() {
            return this.mapping;
        }

        public final TypeArgumentMapping copy(FirClassLikeSymbol<?> typeParameterOwner, Map<FirTypeParameterSymbol, BoundTypeArgument> mapping) {
            Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
            Intrinsics.checkNotNullParameter(mapping, "mapping");
            return new TypeArgumentMapping(typeParameterOwner, mapping);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TypeArgumentMapping)) {
                return false;
            }
            TypeArgumentMapping typeArgumentMapping = (TypeArgumentMapping) other;
            return Intrinsics.areEqual(this.typeParameterOwner, typeArgumentMapping.typeParameterOwner) && Intrinsics.areEqual(this.mapping, typeArgumentMapping.mapping);
        }

        public int hashCode() {
            return (this.typeParameterOwner.hashCode() * 31) + this.mapping.hashCode();
        }

        public String toString() {
            return "TypeArgumentMapping(typeParameterOwner=" + this.typeParameterOwner + ", mapping=" + this.mapping + ')';
        }

        public TypeArgumentMapping(FirClassLikeSymbol<?> typeParameterOwner, Map<FirTypeParameterSymbol, BoundTypeArgument> mapping) {
            Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
            Intrinsics.checkNotNullParameter(mapping, "mapping");
            this.typeParameterOwner = typeParameterOwner;
            this.mapping = mapping;
        }

        public final FirClassLikeSymbol<?> getTypeParameterOwner() {
            return this.typeParameterOwner;
        }

        public final Map<FirTypeParameterSymbol, BoundTypeArgument> getMapping() {
            return this.mapping;
        }
    }

    /* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$BoundTypeArgument;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/types/Variance;)V", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class BoundTypeArgument {
        private final ConeKotlinType type;
        private final Variance variance;

        public static /* synthetic */ BoundTypeArgument copy$default(BoundTypeArgument boundTypeArgument, ConeKotlinType coneKotlinType, Variance variance, int i, Object obj) {
            if ((i & 1) != 0) {
                coneKotlinType = boundTypeArgument.type;
            }
            if ((i & 2) != 0) {
                variance = boundTypeArgument.variance;
            }
            return boundTypeArgument.copy(coneKotlinType, variance);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ConeKotlinType getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Variance getVariance() {
            return this.variance;
        }

        public final BoundTypeArgument copy(ConeKotlinType type, Variance variance) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(variance, "variance");
            return new BoundTypeArgument(type, variance);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BoundTypeArgument)) {
                return false;
            }
            BoundTypeArgument boundTypeArgument = (BoundTypeArgument) other;
            return Intrinsics.areEqual(this.type, boundTypeArgument.type) && this.variance == boundTypeArgument.variance;
        }

        public int hashCode() {
            return (this.type.hashCode() * 31) + this.variance.hashCode();
        }

        public String toString() {
            return "BoundTypeArgument(type=" + this.type + ", variance=" + this.variance + ')';
        }

        public BoundTypeArgument(ConeKotlinType type, Variance variance) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(variance, "variance");
            this.type = type;
            this.variance = variance;
        }

        public final ConeKotlinType getType() {
            return this.type;
        }

        public final Variance getVariance() {
            return this.variance;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0007HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$BoundTypeArguments;", "", "upper", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "lower", "compatibilityUpperBound", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$Compatibility;", "(Ljava/util/Set;Ljava/util/Set;Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$Compatibility;)V", "getCompatibilityUpperBound", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$Compatibility;", "getLower", "()Ljava/util/Set;", "getUpper", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class BoundTypeArguments {
        private final Compatibility compatibilityUpperBound;
        private final Set<ConeClassLikeType> lower;
        private final Set<ConeClassLikeType> upper;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ BoundTypeArguments copy$default(BoundTypeArguments boundTypeArguments, Set set, Set set2, Compatibility compatibility, int i, Object obj) {
            if ((i & 1) != 0) {
                set = boundTypeArguments.upper;
            }
            if ((i & 2) != 0) {
                set2 = boundTypeArguments.lower;
            }
            if ((i & 4) != 0) {
                compatibility = boundTypeArguments.compatibilityUpperBound;
            }
            return boundTypeArguments.copy(set, set2, compatibility);
        }

        public final Set<ConeClassLikeType> component1() {
            return this.upper;
        }

        public final Set<ConeClassLikeType> component2() {
            return this.lower;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Compatibility getCompatibilityUpperBound() {
            return this.compatibilityUpperBound;
        }

        public final BoundTypeArguments copy(Set<ConeClassLikeType> upper, Set<ConeClassLikeType> lower, Compatibility compatibilityUpperBound) {
            Intrinsics.checkNotNullParameter(upper, "upper");
            Intrinsics.checkNotNullParameter(lower, "lower");
            Intrinsics.checkNotNullParameter(compatibilityUpperBound, "compatibilityUpperBound");
            return new BoundTypeArguments(upper, lower, compatibilityUpperBound);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BoundTypeArguments)) {
                return false;
            }
            BoundTypeArguments boundTypeArguments = (BoundTypeArguments) other;
            return Intrinsics.areEqual(this.upper, boundTypeArguments.upper) && Intrinsics.areEqual(this.lower, boundTypeArguments.lower) && this.compatibilityUpperBound == boundTypeArguments.compatibilityUpperBound;
        }

        public int hashCode() {
            return (((this.upper.hashCode() * 31) + this.lower.hashCode()) * 31) + this.compatibilityUpperBound.hashCode();
        }

        public String toString() {
            return "BoundTypeArguments(upper=" + this.upper + ", lower=" + this.lower + ", compatibilityUpperBound=" + this.compatibilityUpperBound + ')';
        }

        public BoundTypeArguments(Set<ConeClassLikeType> upper, Set<ConeClassLikeType> lower, Compatibility compatibilityUpperBound) {
            Intrinsics.checkNotNullParameter(upper, "upper");
            Intrinsics.checkNotNullParameter(lower, "lower");
            Intrinsics.checkNotNullParameter(compatibilityUpperBound, "compatibilityUpperBound");
            this.upper = upper;
            this.lower = lower;
            this.compatibilityUpperBound = compatibilityUpperBound;
        }

        public final Set<ConeClassLikeType> getUpper() {
            return this.upper;
        }

        public final Set<ConeClassLikeType> getLower() {
            return this.lower;
        }

        public final Compatibility getCompatibilityUpperBound() {
            return this.compatibilityUpperBound;
        }
    }

    private final FirClassWithSuperClasses toFirClassWithSuperClasses(ConeClassLikeType coneClassLikeType, ConeInferenceContext coneInferenceContext) {
        return toFirClassWithSuperClasses(coneClassLikeType.getLookupTag(), coneInferenceContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirClassWithSuperClasses toFirClassWithSuperClasses(ConeClassLikeLookupTag coneClassLikeLookupTag, ConeInferenceContext coneInferenceContext) {
        FirClassLikeSymbol<?> symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(coneInferenceContext.getSymbolProvider(), coneClassLikeLookupTag);
        if (!(symbolByLookupTag instanceof FirTypeAliasSymbol)) {
            if (symbolByLookupTag instanceof FirClassSymbol) {
                return new FirClassWithSuperClasses((FirClassSymbol) symbolByLookupTag, coneInferenceContext);
            }
            return null;
        }
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(symbolByLookupTag, coneInferenceContext.get$session());
        if (firRegularClassSymbolFullyExpandedClass != null) {
            return new FirClassWithSuperClasses(firRegularClassSymbolFullyExpandedClass, coneInferenceContext);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ConeTypeCompatibilityChecker.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u000f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J!\u0010\u001b\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001c\u001a\u00020\f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u000e\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\t\u0010!\u001a\u00020\"HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\u000e\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00000\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00000\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0012R\u001c\u0010\u000b\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0018¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ConeTypeCompatibilityChecker$FirClassWithSuperClasses;", "", "firClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "ctx", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;)V", "getCtx", "()Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "getFirClass", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "isFinal", "", "()Z", "isInterface", "superClasses", "", "getSuperClasses", "()Ljava/util/Set;", "superClasses$delegate", "Lkotlin/Lazy;", "thisAndAllSuperClasses", "getThisAndAllSuperClasses", "thisAndAllSuperClasses$delegate", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;)Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "getHasPredefinedEqualityContract", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class FirClassWithSuperClasses {
        private final ConeInferenceContext ctx;
        private final FirClassSymbol<?> firClass;

        /* JADX INFO: renamed from: superClasses$delegate, reason: from kotlin metadata */
        private final Lazy superClasses;

        /* JADX INFO: renamed from: thisAndAllSuperClasses$delegate, reason: from kotlin metadata */
        private final Lazy thisAndAllSuperClasses;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ FirClassWithSuperClasses copy$default(FirClassWithSuperClasses firClassWithSuperClasses, FirClassSymbol firClassSymbol, ConeInferenceContext coneInferenceContext, int i, Object obj) {
            if ((i & 1) != 0) {
                firClassSymbol = firClassWithSuperClasses.firClass;
            }
            if ((i & 2) != 0) {
                coneInferenceContext = firClassWithSuperClasses.ctx;
            }
            return firClassWithSuperClasses.copy(firClassSymbol, coneInferenceContext);
        }

        public final FirClassSymbol<?> component1() {
            return this.firClass;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ConeInferenceContext getCtx() {
            return this.ctx;
        }

        public final FirClassWithSuperClasses copy(FirClassSymbol<?> firClass, ConeInferenceContext ctx) {
            Intrinsics.checkNotNullParameter(firClass, "firClass");
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            return new FirClassWithSuperClasses(firClass, ctx);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FirClassWithSuperClasses)) {
                return false;
            }
            FirClassWithSuperClasses firClassWithSuperClasses = (FirClassWithSuperClasses) other;
            return Intrinsics.areEqual(this.firClass, firClassWithSuperClasses.firClass) && Intrinsics.areEqual(this.ctx, firClassWithSuperClasses.ctx);
        }

        public int hashCode() {
            return (this.firClass.hashCode() * 31) + this.ctx.hashCode();
        }

        public String toString() {
            return "FirClassWithSuperClasses(firClass=" + this.firClass + ", ctx=" + this.ctx + ')';
        }

        public FirClassWithSuperClasses(FirClassSymbol<?> firClass, ConeInferenceContext ctx) {
            Intrinsics.checkNotNullParameter(firClass, "firClass");
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            this.firClass = firClass;
            this.ctx = ctx;
            this.superClasses = LazyKt.lazy(new Function0<Set<? extends FirClassWithSuperClasses>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.ConeTypeCompatibilityChecker$FirClassWithSuperClasses$superClasses$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends ConeTypeCompatibilityChecker.FirClassWithSuperClasses> invoke() {
                    ConeClassLikeLookupTag lookupTag;
                    List<ConeKotlinType> resolvedSuperTypes = this.this$0.getFirClass().getResolvedSuperTypes();
                    ConeTypeCompatibilityChecker.FirClassWithSuperClasses firClassWithSuperClasses = this.this$0;
                    ArrayList arrayList = new ArrayList();
                    for (ConeKotlinType coneKotlinType : resolvedSuperTypes) {
                        ConeTypeCompatibilityChecker.FirClassWithSuperClasses firClassWithSuperClasses2 = null;
                        ConeClassLikeType coneClassLikeType = coneKotlinType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinType : null;
                        if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                            firClassWithSuperClasses2 = ConeTypeCompatibilityChecker.INSTANCE.toFirClassWithSuperClasses(lookupTag, firClassWithSuperClasses.getCtx());
                        }
                        if (firClassWithSuperClasses2 != null) {
                            arrayList.add(firClassWithSuperClasses2);
                        }
                    }
                    return CollectionsKt.toSet(arrayList);
                }
            });
            this.thisAndAllSuperClasses = LazyKt.lazy(new Function0<Set<? extends FirClassWithSuperClasses>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.ConeTypeCompatibilityChecker$FirClassWithSuperClasses$thisAndAllSuperClasses$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<? extends ConeTypeCompatibilityChecker.FirClassWithSuperClasses> invoke() {
                    ArrayDeque arrayDeque = new ArrayDeque();
                    arrayDeque.addLast(this.this$0);
                    ConeTypeCompatibilityChecker.FirClassWithSuperClasses firClassWithSuperClasses = this.this$0;
                    Set setCreateSetBuilder = SetsKt.createSetBuilder();
                    setCreateSetBuilder.add(firClassWithSuperClasses);
                    while (true) {
                        ArrayDeque arrayDeque2 = arrayDeque;
                        if (arrayDeque2.isEmpty()) {
                            return SetsKt.build(setCreateSetBuilder);
                        }
                        Set<ConeTypeCompatibilityChecker.FirClassWithSuperClasses> superClasses = ((ConeTypeCompatibilityChecker.FirClassWithSuperClasses) arrayDeque.removeFirst()).getSuperClasses();
                        for (Object obj : superClasses) {
                            if (!setCreateSetBuilder.contains((ConeTypeCompatibilityChecker.FirClassWithSuperClasses) obj)) {
                                arrayDeque2.add(obj);
                            }
                        }
                        setCreateSetBuilder.addAll(superClasses);
                    }
                }
            });
        }

        public final ConeInferenceContext getCtx() {
            return this.ctx;
        }

        public final FirClassSymbol<?> getFirClass() {
            return this.firClass;
        }

        public final boolean isInterface() {
            return this.firClass.getClassKind() == ClassKind.INTERFACE;
        }

        public final Set<FirClassWithSuperClasses> getSuperClasses() {
            return (Set) this.superClasses.getValue();
        }

        public final Set<FirClassWithSuperClasses> getThisAndAllSuperClasses() {
            return (Set) this.thisAndAllSuperClasses.getValue();
        }

        public final boolean isFinal() {
            return isFinal(this.firClass);
        }

        public final boolean getHasPredefinedEqualityContract(ConeInferenceContext ctx) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            if ((!ConeTypeCompatibilityChecker.INSTANCE.getProhibitComparisonOfIncompatibleEnums(ctx) || (this.firClass.getClassKind() != ClassKind.ENUM_CLASS && !Intrinsics.areEqual(this.firClass.getClassId(), StandardClassIds.INSTANCE.getEnum()))) && !PrimitivesKt.isPrimitiveType(this.firClass) && ((!ConeTypeCompatibilityChecker.INSTANCE.getProhibitComparisonOfIncompatibleClasses(ctx) || !Intrinsics.areEqual(this.firClass.getClassId(), StandardClassIds.INSTANCE.getKClass())) && !Intrinsics.areEqual(this.firClass.getClassId(), StandardClassIds.INSTANCE.getString()) && !Intrinsics.areEqual(this.firClass.getClassId(), StandardClassIds.INSTANCE.getUnit()))) {
                FirClassSymbol<?> firClassSymbol = this.firClass;
                if (!(firClassSymbol instanceof FirRegularClassSymbol) || (!firClassSymbol.getRawStatus().isData() && !this.firClass.getRawStatus().isInline())) {
                    return false;
                }
            }
            return true;
        }

        private final boolean isFinal(FirClassSymbol<?> firClassSymbol) {
            if (firClassSymbol instanceof FirAnonymousObjectSymbol) {
                return true;
            }
            if (firClassSymbol instanceof FirRegularClassSymbol) {
                return firClassSymbol.getResolvedStatus().getModality() == Modality.FINAL;
            }
            throw new IllegalStateException(("unknown type of FirClass " + firClassSymbol).toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getProhibitComparisonOfIncompatibleEnums(ConeInferenceContext coneInferenceContext) {
        return FirLanguageSettingsComponentKt.getLanguageVersionSettings(coneInferenceContext.get$session()).supportsFeature(LanguageFeature.ProhibitComparisonOfIncompatibleEnums);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getProhibitComparisonOfIncompatibleClasses(ConeInferenceContext coneInferenceContext) {
        return FirLanguageSettingsComponentKt.getLanguageVersionSettings(coneInferenceContext.get$session()).supportsFeature(LanguageFeature.ProhibitComparisonOfIncompatibleClasses);
    }
}
