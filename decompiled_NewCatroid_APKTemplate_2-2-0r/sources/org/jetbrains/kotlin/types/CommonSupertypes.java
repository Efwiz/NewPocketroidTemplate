package org.jetbrains.kotlin.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: loaded from: classes6.dex */
public class CommonSupertypes {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:14:0x0028 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 14 && i != 17 && i != 33 && i != 6 && i != 7 && i != 20 && i != 21) {
            switch (i) {
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 9:
                case 10:
                case 11:
                case 12:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 14 && i != 17 && i != 33 && i != 6 && i != 7 && i != 20 && i != 21) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 33:
                objArr[0] = "org/jetbrains/kotlin/types/CommonSupertypes";
                break;
            case 3:
            case 5:
            case 8:
            case 16:
            case 19:
            default:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 4:
            case 13:
            case 15:
            case 30:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 18:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 22:
                objArr[0] = "parameterDescriptor";
                break;
            case 23:
                objArr[0] = "typeProjections";
                break;
            case 28:
                objArr[0] = "typeConstructor";
                break;
            case 29:
                objArr[0] = "markerSet";
                break;
            case 31:
                objArr[0] = "constructorToAllInstances";
                break;
            case 32:
                objArr[0] = "visited";
                break;
        }
        if (i == 2) {
            objArr[1] = "commonSupertype";
        } else if (i == 14) {
            objArr[1] = "renderTypeFully";
        } else if (i == 17) {
            objArr[1] = "computeCommonRawSupertypes";
        } else if (i == 33) {
            objArr[1] = "topologicallySortSuperclassesAndRecordAllInstances";
        } else if (i != 6 && i != 7) {
            if (i != 20 && i != 21) {
                switch (i) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        objArr[1] = "commonSuperTypeForInflexible";
                        break;
                    default:
                        switch (i) {
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                                objArr[1] = "computeSupertypeProjection";
                                break;
                            default:
                                objArr[1] = "org/jetbrains/kotlin/types/CommonSupertypes";
                                break;
                        }
                        break;
                }
            } else {
                objArr[1] = "computeSupertypeProjections";
            }
        } else {
            objArr[1] = "findCommonSupertype";
        }
        switch (i) {
            case 1:
                objArr[2] = "commonSupertype";
                break;
            case 2:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 17:
            case 20:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 33:
                break;
            case 3:
                objArr[2] = "maxDepth";
                break;
            case 4:
                objArr[2] = ConfigConstants.CONFIG_KEY_DEPTH;
                break;
            case 5:
                objArr[2] = "findCommonSupertype";
                break;
            case 8:
                objArr[2] = "commonSuperTypeForInflexible";
                break;
            case 13:
                objArr[2] = "renderTypeFully";
                break;
            case 15:
                objArr[2] = "classOfDeclarationDescriptor";
                break;
            case 16:
                objArr[2] = "computeCommonRawSupertypes";
                break;
            case 18:
            case 19:
                objArr[2] = "computeSupertypeProjections";
                break;
            case 22:
            case 23:
                objArr[2] = "computeSupertypeProjection";
                break;
            case 28:
            case 29:
                objArr[2] = "markAll";
                break;
            case 30:
            case 31:
            case 32:
                objArr[2] = "topologicallySortSuperclassesAndRecordAllInstances";
                break;
            default:
                objArr[2] = "commonSupertypeForNonDenotableTypes";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 14 && i != 17 && i != 33 && i != 6 && i != 7 && i != 20 && i != 21) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    switch (i) {
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public static KotlinType commonSupertypeForNonDenotableTypes(Collection<KotlinType> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        if (collection.isEmpty()) {
            return null;
        }
        if (collection.size() == 1) {
            KotlinType next = collection.iterator().next();
            if (next.getConstructor() instanceof IntersectionTypeConstructor) {
                return commonSupertypeForNonDenotableTypes(next.getConstructor().mo7438getSupertypes());
            }
        }
        return commonSupertype(collection);
    }

    public static KotlinType commonSupertype(Collection<KotlinType> collection) {
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        if (collection.size() != 1) {
            return findCommonSupertype(collection, 0, maxDepth(collection) + 3);
        }
        KotlinType next = collection.iterator().next();
        if (next == null) {
            $$$reportNull$$$0(2);
        }
        return next;
    }

    private static int maxDepth(Collection<KotlinType> collection) {
        if (collection == null) {
            $$$reportNull$$$0(3);
        }
        Iterator<KotlinType> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            int iDepth = depth(it.next());
            if (i < iDepth) {
                i = iDepth;
            }
        }
        return i;
    }

    private static int depth(final KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(4);
        }
        return maxDepth(CollectionsKt.map(kotlinType.getArguments(), new Function1() { // from class: org.jetbrains.kotlin.types.CommonSupertypes$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CommonSupertypes.lambda$depth$0(kotlinType, (TypeProjection) obj);
            }
        })) + 1;
    }

    static /* synthetic */ KotlinType lambda$depth$0(KotlinType kotlinType, TypeProjection typeProjection) {
        if (typeProjection.isStarProjection()) {
            return kotlinType.getConstructor().getBuiltIns().getAnyType();
        }
        return typeProjection.getNullableAnyType();
    }

    private static KotlinType findCommonSupertype(Collection<KotlinType> collection, int i, int i2) {
        if (collection == null) {
            $$$reportNull$$$0(5);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        ArrayList arrayList2 = new ArrayList(collection.size());
        Iterator<KotlinType> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            UnwrappedType unwrappedTypeUnwrap = it.next().unwrap();
            if (unwrappedTypeUnwrap instanceof FlexibleType) {
                if (DynamicTypesKt.isDynamic(unwrappedTypeUnwrap)) {
                    if (unwrappedTypeUnwrap == null) {
                        $$$reportNull$$$0(6);
                    }
                    return unwrappedTypeUnwrap;
                }
                FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
                arrayList.add(flexibleType.getUpperBound());
                arrayList2.add(flexibleType.getLowerBound());
                z = true;
            } else {
                SimpleType simpleType = (SimpleType) unwrappedTypeUnwrap;
                arrayList.add(simpleType);
                arrayList2.add(simpleType);
            }
        }
        if (!z) {
            return commonSuperTypeForInflexible(arrayList, i, i2);
        }
        UnwrappedType unwrappedTypeFlexibleType = KotlinTypeFactory.flexibleType(commonSuperTypeForInflexible(arrayList2, i, i2), commonSuperTypeForInflexible(arrayList, i, i2));
        if (unwrappedTypeFlexibleType == null) {
            $$$reportNull$$$0(7);
        }
        return unwrappedTypeFlexibleType;
    }

    private static SimpleType commonSuperTypeForInflexible(Collection<SimpleType> collection, int i, int i2) {
        if (collection == null) {
            $$$reportNull$$$0(8);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(collection);
        Iterator it = linkedHashSet.iterator();
        boolean isMarkedNullable = false;
        while (it.hasNext()) {
            KotlinType kotlinType = (KotlinType) it.next();
            if (KotlinBuiltIns.isNothingOrNullableNothing(kotlinType)) {
                it.remove();
            }
            if (KotlinTypeKt.isError(kotlinType)) {
                ErrorType errorTypeCreateErrorType = org.jetbrains.kotlin.types.error.ErrorUtils.createErrorType(ErrorTypeKind.SUPER_TYPE_FOR_ERROR_TYPE, kotlinType.toString());
                if (errorTypeCreateErrorType == null) {
                    $$$reportNull$$$0(9);
                }
                return errorTypeCreateErrorType;
            }
            isMarkedNullable |= kotlinType.getIsMarkedNullable();
        }
        if (linkedHashSet.isEmpty()) {
            KotlinBuiltIns builtIns = collection.iterator().next().getConstructor().getBuiltIns();
            SimpleType nullableNothingType = isMarkedNullable ? builtIns.getNullableNothingType() : builtIns.getNothingType();
            if (nullableNothingType == null) {
                $$$reportNull$$$0(10);
            }
            return nullableNothingType;
        }
        if (linkedHashSet.size() == 1) {
            SimpleType simpleTypeMakeNullableIfNeeded = TypeUtils.makeNullableIfNeeded((SimpleType) linkedHashSet.iterator().next(), isMarkedNullable);
            if (simpleTypeMakeNullableIfNeeded == null) {
                $$$reportNull$$$0(11);
            }
            return simpleTypeMakeNullableIfNeeded;
        }
        Map<TypeConstructor, Set<SimpleType>> mapComputeCommonRawSupertypes = computeCommonRawSupertypes(linkedHashSet);
        while (mapComputeCommonRawSupertypes.size() > 1) {
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            Iterator<Set<SimpleType>> it2 = mapComputeCommonRawSupertypes.values().iterator();
            while (it2.hasNext()) {
                linkedHashSet2.addAll(it2.next());
            }
            mapComputeCommonRawSupertypes = computeCommonRawSupertypes(linkedHashSet2);
        }
        if (mapComputeCommonRawSupertypes.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (SimpleType simpleType : collection) {
                sb.append("Info about ").append(renderTypeFully(simpleType)).append(": \n- Supertypes: \n").append((String) TypeUtils.getAllSupertypes(simpleType).stream().map(new Function() { // from class: org.jetbrains.kotlin.types.CommonSupertypes$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return CommonSupertypes.lambda$commonSuperTypeForInflexible$1((KotlinType) obj);
                    }
                }).collect(Collectors.joining("\n"))).append("\n- DeclarationDescriptor class: ").append(classOfDeclarationDescriptor(simpleType)).append("\n\n");
            }
            throw new IllegalStateException("[Report version 3] There is no common supertype for: " + collection + " \n" + sb.toString());
        }
        Map.Entry<TypeConstructor, Set<SimpleType>> next = mapComputeCommonRawSupertypes.entrySet().iterator().next();
        SimpleType simpleTypeMakeNullableIfNeeded2 = TypeUtils.makeNullableIfNeeded(computeSupertypeProjections(next.getKey(), next.getValue(), i, i2), isMarkedNullable);
        if (simpleTypeMakeNullableIfNeeded2 == null) {
            $$$reportNull$$$0(12);
        }
        return simpleTypeMakeNullableIfNeeded2;
    }

    static /* synthetic */ String lambda$commonSuperTypeForInflexible$1(KotlinType kotlinType) {
        return "-- " + renderTypeFully(kotlinType);
    }

    private static String renderTypeFully(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(13);
        }
        String str = DescriptorRenderer.FQ_NAMES_IN_TYPES.renderType(kotlinType) + ", typeConstructor debug: " + renderTypeConstructorVerboseDebugInformation(kotlinType.getConstructor());
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        return str;
    }

    private static String renderTypeConstructorVerboseDebugInformation(TypeConstructor typeConstructor) {
        String string;
        if (!(typeConstructor instanceof AbstractTypeConstructor)) {
            return typeConstructor.toString() + "[" + typeConstructor.getClass().getName() + "]";
        }
        AbstractTypeConstructor abstractTypeConstructor = (AbstractTypeConstructor) typeConstructor;
        ClassifierDescriptor declarationDescriptor = abstractTypeConstructor.getThis$0();
        if (declarationDescriptor == null) {
            string = "<no module for null descriptor>";
        } else {
            string = Objects.toString(DescriptorUtils.getContainingModule(declarationDescriptor));
        }
        return "descriptor=" + declarationDescriptor + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + Integer.toHexString(Objects.hashCode(declarationDescriptor)) + ", moduleDescriptor=" + string + ", " + abstractTypeConstructor.renderAdditionalDebugInformation();
    }

    private static Class<?> classOfDeclarationDescriptor(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(15);
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null) {
            return this$0.getClass();
        }
        return null;
    }

    private static Map<TypeConstructor, Set<SimpleType>> computeCommonRawSupertypes(Collection<SimpleType> collection) {
        if (collection == null) {
            $$$reportNull$$$0(16);
        }
        HashMap map = new HashMap();
        Set set = null;
        List<TypeConstructor> list = null;
        for (SimpleType simpleType : collection) {
            HashSet hashSet = new HashSet();
            list = topologicallySortSuperclassesAndRecordAllInstances(simpleType, map, hashSet);
            if (set == null) {
                set = hashSet;
            } else {
                set.retainAll(hashSet);
            }
        }
        HashSet hashSet2 = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (TypeConstructor typeConstructor : list) {
            if (set.contains(typeConstructor) && !hashSet2.contains(typeConstructor)) {
                linkedHashMap.put(typeConstructor, map.get(typeConstructor));
                markAll(typeConstructor, hashSet2);
            }
        }
        return linkedHashMap;
    }

    private static SimpleType computeSupertypeProjections(TypeConstructor typeConstructor, Set<SimpleType> set, int i, int i2) {
        MemberScope memberScopeCreateErrorScope;
        if (typeConstructor == null) {
            $$$reportNull$$$0(18);
        }
        if (set == null) {
            $$$reportNull$$$0(19);
        }
        if (set.size() == 1) {
            SimpleType next = set.iterator().next();
            if (next == null) {
                $$$reportNull$$$0(20);
            }
            return next;
        }
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        ArrayList arrayList = new ArrayList(parameters.size());
        for (TypeParameterDescriptor typeParameterDescriptor : parameters) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<SimpleType> it = set.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(it.next().getArguments().get(typeParameterDescriptor.getIndex()));
            }
            arrayList.add(computeSupertypeProjection(typeParameterDescriptor, linkedHashSet, i, i2));
        }
        Iterator<SimpleType> it2 = set.iterator();
        boolean isMarkedNullable = false;
        while (it2.hasNext()) {
            isMarkedNullable |= it2.next().getIsMarkedNullable();
        }
        ClassifierDescriptor this$0 = typeConstructor.getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            memberScopeCreateErrorScope = ((ClassDescriptor) this$0).getMemberScope(arrayList);
        } else if (this$0 instanceof TypeParameterDescriptor) {
            memberScopeCreateErrorScope = this$0.getDefaultType().getMemberScope();
        } else {
            memberScopeCreateErrorScope = org.jetbrains.kotlin.types.error.ErrorUtils.createErrorScope(ErrorScopeKind.NON_CLASSIFIER_SUPER_TYPE_SCOPE, true, new String[0]);
        }
        SimpleType simpleTypeSimpleTypeWithNonTrivialMemberScope = KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(TypeAttributes.INSTANCE.getEmpty(), typeConstructor, arrayList, isMarkedNullable, memberScopeCreateErrorScope);
        if (simpleTypeSimpleTypeWithNonTrivialMemberScope == null) {
            $$$reportNull$$$0(21);
        }
        return simpleTypeSimpleTypeWithNonTrivialMemberScope;
    }

    private static TypeProjection computeSupertypeProjection(TypeParameterDescriptor typeParameterDescriptor, Set<TypeProjection> set, int i, int i2) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(22);
        }
        if (set == null) {
            $$$reportNull$$$0(23);
        }
        TypeProjection typeProjectionM7472singleBestRepresentative = FlexibleTypesKt.m7472singleBestRepresentative((Collection<? extends TypeProjection>) set);
        if (typeProjectionM7472singleBestRepresentative != null) {
            if (typeProjectionM7472singleBestRepresentative == null) {
                $$$reportNull$$$0(24);
            }
            return typeProjectionM7472singleBestRepresentative;
        }
        if (i >= i2) {
            TypeProjection typeProjectionMakeStarProjection = TypeUtils.makeStarProjection(typeParameterDescriptor);
            if (typeProjectionMakeStarProjection == null) {
                $$$reportNull$$$0(25);
            }
            return typeProjectionMakeStarProjection;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        Variance variance = typeParameterDescriptor.getVariance();
        int i3 = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$types$Variance[variance.ordinal()];
        if (i3 == 2) {
            linkedHashSet2 = null;
        } else if (i3 == 3) {
            linkedHashSet = null;
        }
        for (TypeProjection typeProjection : set) {
            Variance projectionKind = typeProjection.getProjectionKind();
            if (!projectionKind.getAllowsInPosition()) {
                linkedHashSet = null;
            } else if (linkedHashSet != null) {
                linkedHashSet.add(typeProjection.getNullableAnyType());
            }
            if (!projectionKind.getAllowsOutPosition()) {
                linkedHashSet2 = null;
            } else if (linkedHashSet2 != null) {
                linkedHashSet2.add(typeProjection.getNullableAnyType());
            }
        }
        if (linkedHashSet2 != null) {
            Variance variance2 = variance == Variance.OUT_VARIANCE ? Variance.INVARIANT : Variance.OUT_VARIANCE;
            KotlinType kotlinTypeFindCommonSupertype = findCommonSupertype(linkedHashSet2, i + 1, i2);
            Iterator<KotlinType> it = typeParameterDescriptor.getUpperBounds().iterator();
            while (it.hasNext()) {
                if (!TypeUtilsKt.isSubtypeOf(kotlinTypeFindCommonSupertype, it.next())) {
                    return new StarProjectionImpl(typeParameterDescriptor);
                }
            }
            return new TypeProjectionImpl(variance2, kotlinTypeFindCommonSupertype);
        }
        if (linkedHashSet != null) {
            KotlinType kotlinTypeIntersectTypes = TypeIntersector.intersectTypes(linkedHashSet);
            if (kotlinTypeIntersectTypes == null) {
                TypeProjection typeProjectionMakeStarProjection2 = TypeUtils.makeStarProjection(typeParameterDescriptor);
                if (typeProjectionMakeStarProjection2 == null) {
                    $$$reportNull$$$0(26);
                }
                return typeProjectionMakeStarProjection2;
            }
            return new TypeProjectionImpl(variance == Variance.IN_VARIANCE ? Variance.INVARIANT : Variance.IN_VARIANCE, kotlinTypeIntersectTypes);
        }
        TypeProjection typeProjectionMakeStarProjection3 = TypeUtils.makeStarProjection(typeParameterDescriptor);
        if (typeProjectionMakeStarProjection3 == null) {
            $$$reportNull$$$0(27);
        }
        return typeProjectionMakeStarProjection3;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.CommonSupertypes$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$types$Variance;

        static {
            int[] iArr = new int[Variance.values().length];
            $SwitchMap$org$jetbrains$kotlin$types$Variance = iArr;
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$Variance[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$Variance[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static void markAll(TypeConstructor typeConstructor, Set<TypeConstructor> set) {
        if (typeConstructor == null) {
            $$$reportNull$$$0(28);
        }
        if (set == null) {
            $$$reportNull$$$0(29);
        }
        set.add(typeConstructor);
        Iterator<KotlinType> it = typeConstructor.mo7438getSupertypes().iterator();
        while (it.hasNext()) {
            markAll(it.next().getConstructor(), set);
        }
    }

    public static List<TypeConstructor> topologicallySortSuperclassesAndRecordAllInstances(SimpleType simpleType, Map<TypeConstructor, Set<SimpleType>> map, final Set<TypeConstructor> set) {
        if (simpleType == null) {
            $$$reportNull$$$0(30);
        }
        if (map == null) {
            $$$reportNull$$$0(31);
        }
        if (set == null) {
            $$$reportNull$$$0(32);
        }
        List<TypeConstructor> list = (List) DFS.dfs(Collections.singletonList(simpleType), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.types.CommonSupertypes$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return CommonSupertypes.lambda$topologicallySortSuperclassesAndRecordAllInstances$2(set, (SimpleType) obj);
            }
        }, new DFS.Visited() { // from class: org.jetbrains.kotlin.types.CommonSupertypes$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.utils.DFS.Visited
            public final boolean checkAndMarkVisited(Object obj) {
                return set.add(((SimpleType) obj).getConstructor());
            }
        }, new AnonymousClass1(map));
        if (list == null) {
            $$$reportNull$$$0(33);
        }
        return list;
    }

    static /* synthetic */ Iterable lambda$topologicallySortSuperclassesAndRecordAllInstances$2(Set set, SimpleType simpleType) {
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(simpleType);
        Collection<KotlinType> collectionMo7438getSupertypes = simpleType.getConstructor().mo7438getSupertypes();
        ArrayList arrayList = new ArrayList(collectionMo7438getSupertypes.size());
        for (KotlinType kotlinType : collectionMo7438getSupertypes) {
            if (!set.contains(kotlinType.getConstructor())) {
                arrayList.add(FlexibleTypesKt.lowerIfFlexible(typeSubstitutorCreate.safeSubstitute(kotlinType, Variance.INVARIANT)));
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.CommonSupertypes$1, reason: invalid class name */
    static class AnonymousClass1 extends DFS.NodeHandlerWithListResult<SimpleType, TypeConstructor> {
        final /* synthetic */ Map val$constructorToAllInstances;

        AnonymousClass1(Map map) {
            this.val$constructorToAllInstances = map;
        }

        @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
        public boolean beforeChildren(SimpleType simpleType) {
            ((Set) this.val$constructorToAllInstances.computeIfAbsent(simpleType.getConstructor(), new Function() { // from class: org.jetbrains.kotlin.types.CommonSupertypes$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return CommonSupertypes.AnonymousClass1.lambda$beforeChildren$0((TypeConstructor) obj);
                }
            })).add(simpleType);
            return true;
        }

        static /* synthetic */ Set lambda$beforeChildren$0(TypeConstructor typeConstructor) {
            return new LinkedHashSet();
        }

        @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
        public void afterChildren(SimpleType simpleType) {
            ((LinkedList) this.result).addFirst(simpleType.getConstructor());
        }
    }
}
