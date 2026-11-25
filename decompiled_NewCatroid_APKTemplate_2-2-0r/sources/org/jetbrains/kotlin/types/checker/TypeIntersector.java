package org.jetbrains.kotlin.types.checker;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: IntersectionType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u000426\u0010\u0007\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\r0\bH\u0002J\u001b\u0010\u000e\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010H\u0000¢\u0006\u0002\b\u0011J\u0016\u0010\u0012\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013H\u0002J\u0018\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeIntersector;", "", "()V", "filterTypes", "", "Lorg/jetbrains/kotlin/types/SimpleType;", "inputTypes", "predicate", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "lower", "upper", "", "intersectTypes", SpecTypePair.NAME_types, "", "intersectTypes$descriptors", "intersectTypesWithoutIntersectionType", "", "isStrictSupertype", "subtype", "Lorg/jetbrains/kotlin/types/KotlinType;", "supertype", "ResultNullability", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeIntersector {
    public static final TypeIntersector INSTANCE = new TypeIntersector();

    private TypeIntersector() {
    }

    public final SimpleType intersectTypes$descriptors(List<? extends SimpleType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        types.size();
        ArrayList arrayList = new ArrayList();
        for (SimpleType simpleType : types) {
            if (simpleType.getConstructor() instanceof IntersectionTypeConstructor) {
                Collection<KotlinType> collectionMo7438getSupertypes = simpleType.getConstructor().mo7438getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
                Collection<KotlinType> collection = collectionMo7438getSupertypes;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                for (KotlinType kotlinType : collection) {
                    Intrinsics.checkNotNull(kotlinType);
                    SimpleType simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(kotlinType);
                    if (simpleType.getIsMarkedNullable()) {
                        simpleTypeUpperIfFlexible = simpleTypeUpperIfFlexible.makeNullableAsSpecified(true);
                    }
                    arrayList2.add(simpleTypeUpperIfFlexible);
                }
                arrayList.addAll(arrayList2);
            } else {
                arrayList.add(simpleType);
            }
        }
        ArrayList<SimpleType> arrayList3 = arrayList;
        ResultNullability resultNullabilityCombine = ResultNullability.START;
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            resultNullabilityCombine = resultNullabilityCombine.combine((UnwrappedType) it.next());
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (NewCapturedType newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default : arrayList3) {
            if (resultNullabilityCombine == ResultNullability.NOT_NULL) {
                if (newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default instanceof NewCapturedType) {
                    newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default = SpecialTypesKt.withNotNullProjection((NewCapturedType) newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default);
                }
                newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default = SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull$default(newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default, false, 1, null);
            }
            linkedHashSet.add(newCapturedTypeMakeSimpleTypeDefinitelyNotNullOrNotNull$default);
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        List<? extends SimpleType> list = types;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList4.add(((SimpleType) it2.next()).getAttributes());
        }
        Iterator it3 = arrayList4.iterator();
        if (!it3.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it3.next();
        while (it3.hasNext()) {
            next = ((TypeAttributes) next).intersect((TypeAttributes) it3.next());
        }
        return intersectTypesWithoutIntersectionType(linkedHashSet2).replaceAttributes((TypeAttributes) next);
    }

    private final SimpleType intersectTypesWithoutIntersectionType(final Set<? extends SimpleType> inputTypes) {
        if (inputTypes.size() == 1) {
            return (SimpleType) CollectionsKt.single(inputTypes);
        }
        new Function0<String>() { // from class: org.jetbrains.kotlin.types.checker.TypeIntersector$intersectTypesWithoutIntersectionType$errorMessage$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "This collections cannot be empty! input types: " + CollectionsKt.joinToString$default(inputTypes, null, null, null, 0, null, null, 63, null);
            }
        };
        Set<? extends SimpleType> set = inputTypes;
        Collection<SimpleType> collectionFilterTypes = filterTypes(set, new TypeIntersector$intersectTypesWithoutIntersectionType$filteredEqualTypes$1(this));
        collectionFilterTypes.isEmpty();
        SimpleType simpleTypeFindIntersectionType = IntegerLiteralTypeConstructor.INSTANCE.findIntersectionType(collectionFilterTypes);
        if (simpleTypeFindIntersectionType != null) {
            return simpleTypeFindIntersectionType;
        }
        Collection<SimpleType> collectionFilterTypes2 = filterTypes(collectionFilterTypes, new TypeIntersector$intersectTypesWithoutIntersectionType$filteredSuperAndEqualTypes$1(NewKotlinTypeChecker.INSTANCE.getDefault()));
        collectionFilterTypes2.isEmpty();
        return collectionFilterTypes2.size() < 2 ? (SimpleType) CollectionsKt.single(collectionFilterTypes2) : new IntersectionTypeConstructor(set).createType();
    }

    private final Collection<SimpleType> filterTypes(Collection<? extends SimpleType> inputTypes, Function2<? super SimpleType, ? super SimpleType, Boolean> predicate) {
        ArrayList arrayList = new ArrayList(inputTypes);
        Iterator it = arrayList.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            SimpleType simpleType = (SimpleType) it.next();
            ArrayList<SimpleType> arrayList2 = arrayList;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                for (SimpleType simpleType2 : arrayList2) {
                    if (simpleType2 != simpleType) {
                        Intrinsics.checkNotNull(simpleType2);
                        Intrinsics.checkNotNull(simpleType);
                        if (predicate.invoke(simpleType2, simpleType).booleanValue()) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isStrictSupertype(KotlinType subtype, KotlinType supertype) {
        NewKotlinTypeCheckerImpl newKotlinTypeCheckerImpl = NewKotlinTypeChecker.INSTANCE.getDefault();
        return newKotlinTypeCheckerImpl.isSubtypeOf(subtype, supertype) && !newKotlinTypeCheckerImpl.isSubtypeOf(supertype, subtype);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: IntersectionType.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004H&R\u0018\u0010\u0003\u001a\u00020\u0000*\u00020\u00048DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability;", "", "(Ljava/lang/String;I)V", "resultNullability", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "getResultNullability", "(Lorg/jetbrains/kotlin/types/UnwrappedType;)Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability;", "combine", "nextType", "START", "ACCEPT_NULL", GrsBaseInfo.CountryCodeSource.UNKNOWN, "NOT_NULL", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ResultNullability {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ResultNullability[] $VALUES;
        public static final ResultNullability START = new START("START", 0);
        public static final ResultNullability ACCEPT_NULL = new ACCEPT_NULL("ACCEPT_NULL", 1);
        public static final ResultNullability UNKNOWN = new UNKNOWN(GrsBaseInfo.CountryCodeSource.UNKNOWN, 2);
        public static final ResultNullability NOT_NULL = new NOT_NULL("NOT_NULL", 3);

        private static final /* synthetic */ ResultNullability[] $values() {
            return new ResultNullability[]{START, ACCEPT_NULL, UNKNOWN, NOT_NULL};
        }

        public /* synthetic */ ResultNullability(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i);
        }

        public static EnumEntries<ResultNullability> getEntries() {
            return $ENTRIES;
        }

        public static ResultNullability valueOf(String str) {
            return (ResultNullability) Enum.valueOf(ResultNullability.class, str);
        }

        public static ResultNullability[] values() {
            return (ResultNullability[]) $VALUES.clone();
        }

        public abstract ResultNullability combine(UnwrappedType nextType);

        /* JADX INFO: compiled from: IntersectionType.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability$START;", "Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability;", "combine", "nextType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        static final class START extends ResultNullability {
            START(String str, int i) {
                super(str, i, null);
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeIntersector.ResultNullability
            public ResultNullability combine(UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                return getResultNullability(nextType);
            }
        }

        private ResultNullability(String str, int i) {
            super(str, i);
        }

        static {
            ResultNullability[] resultNullabilityArr$values = $values();
            $VALUES = resultNullabilityArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(resultNullabilityArr$values);
        }

        /* JADX INFO: compiled from: IntersectionType.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability$ACCEPT_NULL;", "Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability;", "combine", "nextType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        static final class ACCEPT_NULL extends ResultNullability {
            ACCEPT_NULL(String str, int i) {
                super(str, i, null);
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeIntersector.ResultNullability
            public ResultNullability combine(UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                return getResultNullability(nextType);
            }
        }

        /* JADX INFO: compiled from: IntersectionType.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability$UNKNOWN;", "Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability;", "combine", "nextType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        static final class UNKNOWN extends ResultNullability {
            UNKNOWN(String str, int i) {
                super(str, i, null);
            }

            @Override // org.jetbrains.kotlin.types.checker.TypeIntersector.ResultNullability
            public ResultNullability combine(UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                ResultNullability resultNullability = getResultNullability(nextType);
                return resultNullability == ResultNullability.ACCEPT_NULL ? this : resultNullability;
            }
        }

        /* JADX INFO: compiled from: IntersectionType.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability$NOT_NULL;", "Lorg/jetbrains/kotlin/types/checker/TypeIntersector$ResultNullability;", "combine", "nextType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        static final class NOT_NULL extends ResultNullability {
            @Override // org.jetbrains.kotlin.types.checker.TypeIntersector.ResultNullability
            public NOT_NULL combine(UnwrappedType nextType) {
                Intrinsics.checkNotNullParameter(nextType, "nextType");
                return this;
            }

            NOT_NULL(String str, int i) {
                super(str, i, null);
            }
        }

        protected final ResultNullability getResultNullability(UnwrappedType unwrappedType) {
            Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
            if (unwrappedType.getIsMarkedNullable()) {
                return ACCEPT_NULL;
            }
            if ((unwrappedType instanceof DefinitelyNotNullType) && (((DefinitelyNotNullType) unwrappedType).getOriginal() instanceof StubTypeForBuilderInference)) {
                return NOT_NULL;
            }
            if (unwrappedType instanceof StubTypeForBuilderInference) {
                return UNKNOWN;
            }
            return NullabilityChecker.INSTANCE.isSubtypeOfAny(unwrappedType) ? NOT_NULL : UNKNOWN;
        }
    }
}
