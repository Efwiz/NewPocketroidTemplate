package org.jetbrains.kotlin.resolve.calls.inference;

import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstructor;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: TypeBoundsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0007J\u001e\u0010\u0019\u001a\u00020\u000b2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f2\u0006\u0010\u001a\u001a\u00020\u0010H\u0002J\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u00102\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002J\u001a\u0010\u001e\u001a\u00020\u00002\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u000b0 J6\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00100#2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f2\u0006\u0010$\u001a\u00020%2\u0010\b\u0002\u0010&\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010'H\u0002J\u001c\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00100#2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002J\u0006\u0010*\u001a\u00020\u0017J \u0010+\u001a\u00020\u000b2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010H\u0002R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBoundsImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds;", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;)V", "bounds", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", "getBounds", "()Ljava/util/ArrayList;", "<set-?>", "", "isFixed", "()Z", "resultValues", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeVariable", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", CompoundEntry.NAME_values, "getValues", "()Ljava/util/Collection;", "addBound", "", "bound", "checkOnlyInputTypes", "possibleAnswer", "commonSupertypeForNumberTypes", "numberLowerBounds", "computeValues", "filter", "condition", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "filterBounds", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$BoundKind;", "errorValues", "", "getIntersectionOfSupertypes", SpecTypePair.NAME_types, "setFixed", "tryPossibleAnswer", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeBoundsImpl implements TypeBounds {
    private final ArrayList<TypeBounds.Bound> bounds;
    private boolean isFixed;
    private Collection<? extends KotlinType> resultValues;
    private final TypeVariable typeVariable;

    /* JADX INFO: compiled from: TypeBoundsImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeBounds.BoundKind.values().length];
            try {
                iArr[TypeBounds.BoundKind.LOWER_BOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeBounds.BoundKind.UPPER_BOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeBounds.BoundKind.EXACT_BOUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TypeBoundsImpl(TypeVariable typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        this.typeVariable = typeVariable;
        this.bounds = new ArrayList<>();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.TypeBounds
    public TypeVariable getTypeVariable() {
        return this.typeVariable;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.TypeBounds
    public ArrayList<TypeBounds.Bound> getBounds() {
        return this.bounds;
    }

    /* JADX INFO: renamed from: isFixed, reason: from getter */
    public final boolean getIsFixed() {
        return this.isFixed;
    }

    public final void setFixed() {
        this.isFixed = true;
    }

    public final void addBound(TypeBounds.Bound bound) {
        Intrinsics.checkNotNullParameter(bound, "bound");
        this.resultValues = null;
        Intrinsics.areEqual(bound.getTypeVariable(), getTypeVariable());
        getBounds().add(bound);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Set filterBounds$default(TypeBoundsImpl typeBoundsImpl, Collection collection, TypeBounds.BoundKind boundKind, Collection collection2, int i, Object obj) {
        if ((i & 4) != 0) {
            collection2 = null;
        }
        return typeBoundsImpl.filterBounds(collection, boundKind, collection2);
    }

    private final Set<KotlinType> filterBounds(Collection<TypeBounds.Bound> bounds, TypeBounds.BoundKind kind, Collection<KotlinType> errorValues) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (TypeBounds.Bound bound : bounds) {
            if (bound.getKind() == kind) {
                if (!ErrorUtils.INSTANCE.containsErrorType(bound.getConstrainingType())) {
                    linkedHashSet.add(bound.getConstrainingType());
                } else if (errorValues != null) {
                    errorValues.add(bound.getConstrainingType());
                }
            }
        }
        return linkedHashSet;
    }

    public final TypeBoundsImpl filter(Function1<? super ConstraintPosition, Boolean> condition) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        TypeBoundsImpl typeBoundsImpl = new TypeBoundsImpl(getTypeVariable());
        ArrayList<TypeBounds.Bound> bounds = typeBoundsImpl.getBounds();
        ArrayList<TypeBounds.Bound> bounds2 = getBounds();
        ArrayList arrayList = new ArrayList();
        for (Object obj : bounds2) {
            if (condition.invoke(((TypeBounds.Bound) obj).getPosition()).booleanValue()) {
                arrayList.add(obj);
            }
        }
        bounds.addAll(arrayList);
        return typeBoundsImpl;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.TypeBounds
    public Collection<KotlinType> getValues() {
        if (this.resultValues == null) {
            this.resultValues = computeValues();
        }
        Collection collection = this.resultValues;
        Intrinsics.checkNotNull(collection);
        return collection;
    }

    private final Collection<KotlinType> computeValues() {
        KotlinType kotlinTypeIntersectTypes;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList<TypeBounds.Bound> bounds = getBounds();
        ArrayList arrayList = new ArrayList();
        for (Object obj : bounds) {
            if (((TypeBounds.Bound) obj).getIsProper()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList3 = arrayList2;
        if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                if (((TypeBounds.Bound) it.next()).getPosition().isStrong()) {
                    ArrayList arrayList4 = arrayList2;
                    LinkedHashSet linkedHashSet2 = linkedHashSet;
                    Set<KotlinType> setFilterBounds = filterBounds(arrayList4, TypeBounds.BoundKind.EXACT_BOUND, linkedHashSet2);
                    KotlinType kotlinTypeSingleBestRepresentative = FlexibleTypesKt.singleBestRepresentative((Collection<? extends KotlinType>) setFilterBounds);
                    if (kotlinTypeSingleBestRepresentative != null && tryPossibleAnswer(arrayList4, kotlinTypeSingleBestRepresentative)) {
                        return CollectionsKt.listOf(kotlinTypeSingleBestRepresentative);
                    }
                    linkedHashSet.addAll(setFilterBounds);
                    Set<KotlinType> setFilterBounds2 = filterBounds(arrayList4, TypeBounds.BoundKind.LOWER_BOUND, linkedHashSet2);
                    ArrayList arrayList5 = new ArrayList();
                    ArrayList arrayList6 = new ArrayList();
                    for (Object obj2 : setFilterBounds2) {
                        if (((KotlinType) obj2).getConstructor() instanceof IntegerValueTypeConstructor) {
                            arrayList5.add(obj2);
                        } else {
                            arrayList6.add(obj2);
                        }
                    }
                    Pair pair = new Pair(arrayList5, arrayList6);
                    List list = (List) pair.component1();
                    KotlinType kotlinTypeCommonSupertypeForNonDenotableTypes = CommonSupertypes.commonSupertypeForNonDenotableTypes((List) pair.component2());
                    if (tryPossibleAnswer(arrayList4, kotlinTypeCommonSupertypeForNonDenotableTypes)) {
                        Intrinsics.checkNotNull(kotlinTypeCommonSupertypeForNonDenotableTypes);
                        return SetsKt.setOf(kotlinTypeCommonSupertypeForNonDenotableTypes);
                    }
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet2, kotlinTypeCommonSupertypeForNonDenotableTypes);
                    KotlinType kotlinTypeCommonSupertypeForNumberTypes = commonSupertypeForNumberTypes(list);
                    if (tryPossibleAnswer(arrayList4, kotlinTypeCommonSupertypeForNumberTypes)) {
                        Intrinsics.checkNotNull(kotlinTypeCommonSupertypeForNumberTypes);
                        return SetsKt.setOf(kotlinTypeCommonSupertypeForNumberTypes);
                    }
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet2, kotlinTypeCommonSupertypeForNumberTypes);
                    if (kotlinTypeCommonSupertypeForNonDenotableTypes != null && kotlinTypeCommonSupertypeForNumberTypes != null) {
                        KotlinType kotlinTypeCommonSupertypeForNonDenotableTypes2 = CommonSupertypes.commonSupertypeForNonDenotableTypes(CollectionsKt.listOf((Object[]) new KotlinType[]{kotlinTypeCommonSupertypeForNonDenotableTypes, kotlinTypeCommonSupertypeForNumberTypes}));
                        if (tryPossibleAnswer(arrayList4, kotlinTypeCommonSupertypeForNonDenotableTypes2)) {
                            Intrinsics.checkNotNull(kotlinTypeCommonSupertypeForNonDenotableTypes2);
                            return SetsKt.setOf(kotlinTypeCommonSupertypeForNonDenotableTypes2);
                        }
                    }
                    Set<KotlinType> setFilterBounds3 = filterBounds(arrayList4, TypeBounds.BoundKind.UPPER_BOUND, linkedHashSet2);
                    if (!setFilterBounds3.isEmpty() && (kotlinTypeIntersectTypes = TypeIntersector.intersectTypes(setFilterBounds3)) != null && tryPossibleAnswer(arrayList4, kotlinTypeIntersectTypes)) {
                        return SetsKt.setOf(kotlinTypeIntersectTypes);
                    }
                    linkedHashSet.addAll(filterBounds$default(this, arrayList4, TypeBounds.BoundKind.UPPER_BOUND, null, 4, null));
                    return (linkedHashSet.size() == 1 && getTypeVariable().hasOnlyInputTypesAnnotation() && !tryPossibleAnswer(arrayList4, (KotlinType) CollectionsKt.first(linkedHashSet))) ? CollectionsKt.emptyList() : linkedHashSet2;
                }
            }
        }
        return CollectionsKt.emptyList();
    }

    private final boolean checkOnlyInputTypes(Collection<TypeBounds.Bound> bounds, KotlinType possibleAnswer) {
        if (!getTypeVariable().hasOnlyInputTypesAnnotation()) {
            return true;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : bounds) {
            TypeBounds.Bound bound = (TypeBounds.Bound) obj;
            if (bound.getIsProper() && bound.getConstrainingType().getConstructor().isDenotable()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((TypeBounds.Bound) it.next()).getConstrainingType());
        }
        Set set = CollectionsKt.toSet(arrayList3);
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                if (KotlinTypeChecker.DEFAULT.equalTypes((KotlinType) it2.next(), possibleAnswer)) {
                    return true;
                }
            }
        }
        Set setFilterBounds$default = filterBounds$default(this, bounds, TypeBounds.BoundKind.LOWER_BOUND, null, 4, null);
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : setFilterBounds$default) {
            if (((KotlinType) obj2).getConstructor() instanceof IntegerValueTypeConstructor) {
                arrayList4.add(obj2);
            }
        }
        return Intrinsics.areEqual(possibleAnswer, commonSupertypeForNumberTypes(arrayList4));
    }

    private final boolean tryPossibleAnswer(Collection<TypeBounds.Bound> bounds, KotlinType possibleAnswer) {
        if (possibleAnswer == null) {
            return false;
        }
        if ((!possibleAnswer.getConstructor().isDenotable() && !CapturedTypeConstructorKt.isCaptured(possibleAnswer)) || !checkOnlyInputTypes(bounds, possibleAnswer)) {
            return false;
        }
        for (TypeBounds.Bound bound : bounds) {
            int i = WhenMappings.$EnumSwitchMapping$0[bound.getKind().ordinal()];
            if (i == 1) {
                if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(bound.getConstrainingType(), possibleAnswer)) {
                    return false;
                }
            } else if (i == 2) {
                if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(possibleAnswer, bound.getConstrainingType())) {
                    return false;
                }
            } else if (i == 3 && !KotlinTypeChecker.DEFAULT.equalTypes(bound.getConstrainingType(), possibleAnswer)) {
                return false;
            }
        }
        return true;
    }

    private final KotlinType commonSupertypeForNumberTypes(Collection<? extends KotlinType> numberLowerBounds) {
        if (numberLowerBounds.isEmpty()) {
            return null;
        }
        KotlinType defaultPrimitiveNumberType = TypeUtils.getDefaultPrimitiveNumberType(getIntersectionOfSupertypes(numberLowerBounds));
        return defaultPrimitiveNumberType == null ? CommonSupertypes.commonSupertype(numberLowerBounds) : defaultPrimitiveNumberType;
    }

    private final Set<KotlinType> getIntersectionOfSupertypes(Collection<? extends KotlinType> types) {
        HashSet hashSet = new HashSet();
        Iterator<? extends KotlinType> it = types.iterator();
        while (it.hasNext()) {
            Collection<KotlinType> collectionMo7438getSupertypes = it.next().getConstructor().mo7438getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
            if (hashSet.isEmpty()) {
                hashSet.addAll(collectionMo7438getSupertypes);
            } else {
                hashSet.retainAll(collectionMo7438getSupertypes);
            }
        }
        return hashSet;
    }
}
