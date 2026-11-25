package org.jetbrains.kotlin.resolve.constants;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: IntegerLiteralTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 +2\u00020\u0001:\u0001+B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB%\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0001J\u0006\u0010\u001a\u001a\u00020\u000bJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0010H\u0016J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0!H\u0016J\b\u0010\"\u001a\u00020\u0018H\u0002J\b\u0010#\u001a\u00020\u0018H\u0016J\b\u0010$\u001a\u00020\u0018H\u0016J\u0010\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020'H\u0017J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020)H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntegerLiteralTypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "value", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "parameters", "Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;", "(JLorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/constants/CompileTimeConstant$Parameters;)V", "possibleTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "(JLorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/util/Set;)V", "getPossibleTypes", "()Ljava/util/Set;", "supertypes", "", "getSupertypes", "()Ljava/util/List;", "supertypes$delegate", "Lkotlin/Lazy;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/SimpleType;", "checkConstructor", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "getApproximatedType", "getBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "", "isContainsOnlyUnsignedTypes", "isDenotable", "isFinal", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "toString", "", "valueToString", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntegerLiteralTypeConstructor implements TypeConstructor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ModuleDescriptor module;
    private final Set<KotlinType> possibleTypes;

    /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
    private final Lazy supertypes;
    private final SimpleType type;
    private final long value;

    public /* synthetic */ IntegerLiteralTypeConstructor(long j, ModuleDescriptor moduleDescriptor, Set set, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, moduleDescriptor, (Set<? extends KotlinType>) set);
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public ClassifierDescriptor getThis$0() {
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isFinal() {
        return true;
    }

    /* JADX INFO: compiled from: IntegerLiteralTypeConstructor.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\n\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J\"\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J&\u0010\u000b\u001a\u0004\u0018\u00010\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntegerLiteralTypeConstructor$Companion;", "", "()V", "findCommonSuperType", "Lorg/jetbrains/kotlin/types/SimpleType;", SpecTypePair.NAME_types, "", "findCommonSuperTypeOrIntersectionType", "mode", "Lorg/jetbrains/kotlin/resolve/constants/IntegerLiteralTypeConstructor$Companion$Mode;", "findIntersectionType", "fold", "left", "Lorg/jetbrains/kotlin/resolve/constants/IntegerLiteralTypeConstructor;", "right", "Mode", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: IntegerLiteralTypeConstructor.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/IntegerLiteralTypeConstructor$Companion$Mode;", "", "(Ljava/lang/String;I)V", "COMMON_SUPER_TYPE", "INTERSECTION_TYPE", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private enum Mode {
            COMMON_SUPER_TYPE,
            INTERSECTION_TYPE;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<Mode> getEntries() {
                return $ENTRIES;
            }
        }

        /* JADX INFO: compiled from: IntegerLiteralTypeConstructor.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Mode.values().length];
                try {
                    iArr[Mode.COMMON_SUPER_TYPE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Mode.INTERSECTION_TYPE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SimpleType findCommonSuperType(Collection<? extends SimpleType> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return findCommonSuperTypeOrIntersectionType(types, Mode.COMMON_SUPER_TYPE);
        }

        public final SimpleType findIntersectionType(Collection<? extends SimpleType> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return findCommonSuperTypeOrIntersectionType(types, Mode.INTERSECTION_TYPE);
        }

        private final SimpleType findCommonSuperTypeOrIntersectionType(Collection<? extends SimpleType> types, Mode mode) {
            if (types.isEmpty()) {
                return null;
            }
            Iterator<T> it = types.iterator();
            if (!it.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            Object next = it.next();
            while (it.hasNext()) {
                SimpleType simpleType = (SimpleType) it.next();
                next = IntegerLiteralTypeConstructor.INSTANCE.fold((SimpleType) next, simpleType, mode);
            }
            return (SimpleType) next;
        }

        private final SimpleType fold(SimpleType left, SimpleType right, Mode mode) {
            if (left == null || right == null) {
                return null;
            }
            TypeConstructor constructor = left.getConstructor();
            TypeConstructor constructor2 = right.getConstructor();
            boolean z = constructor instanceof IntegerLiteralTypeConstructor;
            if (z && (constructor2 instanceof IntegerLiteralTypeConstructor)) {
                return fold((IntegerLiteralTypeConstructor) constructor, (IntegerLiteralTypeConstructor) constructor2, mode);
            }
            if (z) {
                return fold((IntegerLiteralTypeConstructor) constructor, right);
            }
            if (constructor2 instanceof IntegerLiteralTypeConstructor) {
                return fold((IntegerLiteralTypeConstructor) constructor2, left);
            }
            return null;
        }

        private final SimpleType fold(IntegerLiteralTypeConstructor left, IntegerLiteralTypeConstructor right, Mode mode) {
            Set setIntersect;
            int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
            if (i == 1) {
                setIntersect = CollectionsKt.intersect(left.getPossibleTypes(), right.getPossibleTypes());
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                setIntersect = CollectionsKt.union(left.getPossibleTypes(), right.getPossibleTypes());
            }
            return KotlinTypeFactory.integerLiteralType(TypeAttributes.INSTANCE.getEmpty(), new IntegerLiteralTypeConstructor(left.value, left.module, setIntersect, null), false);
        }

        private final SimpleType fold(IntegerLiteralTypeConstructor left, SimpleType right) {
            if (left.getPossibleTypes().contains(right)) {
                return right;
            }
            return null;
        }
    }

    public final Set<KotlinType> getPossibleTypes() {
        return this.possibleTypes;
    }

    public IntegerLiteralTypeConstructor(long j, ModuleDescriptor module, CompileTimeConstant.Parameters parameters) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.type = KotlinTypeFactory.integerLiteralType(TypeAttributes.INSTANCE.getEmpty(), this, false);
        this.supertypes = LazyKt.lazy(new Function0<List<SimpleType>>() { // from class: org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor$supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<SimpleType> invoke() {
                SimpleType defaultType = this.this$0.getBuiltIns().getComparable().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                List<SimpleType> listMutableListOf = CollectionsKt.mutableListOf(TypeSubstitutionKt.replace$default(defaultType, CollectionsKt.listOf(new TypeProjectionImpl(Variance.IN_VARIANCE, this.this$0.type)), null, 2, null));
                if (!this.this$0.isContainsOnlyUnsignedTypes()) {
                    listMutableListOf.add(this.this$0.getBuiltIns().getNumberType());
                }
                return listMutableListOf;
            }
        });
        this.value = j;
        this.module = module;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        boolean zIsUnsignedNumberLiteral = parameters.isUnsignedNumberLiteral();
        boolean zIsConvertableConstVal = parameters.isConvertableConstVal();
        if (zIsUnsignedNumberLiteral || zIsConvertableConstVal) {
            CompileTimeConstantKt.hasUnsignedTypesInModuleDependencies(module);
        }
        if (zIsConvertableConstVal) {
            _init_$addSignedPossibleTypes(j, this, linkedHashSet);
            _init_$addUnsignedPossibleTypes(j, module, linkedHashSet);
        } else if (zIsUnsignedNumberLiteral) {
            _init_$addUnsignedPossibleTypes(j, module, linkedHashSet);
        } else {
            _init_$addSignedPossibleTypes(j, this, linkedHashSet);
        }
        this.possibleTypes = linkedHashSet;
    }

    private static final void _init_$checkBoundsAndAddPossibleType(Set<KotlinType> set, long j, KotlinType kotlinType) {
        long jMinValue = PrimitiveTypeUtilKt.minValue(kotlinType);
        if (j > PrimitiveTypeUtilKt.maxValue(kotlinType) || jMinValue > j) {
            return;
        }
        set.add(kotlinType);
    }

    private static final void _init_$addSignedPossibleTypes(long j, IntegerLiteralTypeConstructor integerLiteralTypeConstructor, Set<KotlinType> set) {
        SimpleType intType = integerLiteralTypeConstructor.getBuiltIns().getIntType();
        Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
        _init_$checkBoundsAndAddPossibleType(set, j, intType);
        SimpleType longType = integerLiteralTypeConstructor.getBuiltIns().getLongType();
        Intrinsics.checkNotNullExpressionValue(longType, "getLongType(...)");
        set.add(longType);
        SimpleType byteType = integerLiteralTypeConstructor.getBuiltIns().getByteType();
        Intrinsics.checkNotNullExpressionValue(byteType, "getByteType(...)");
        _init_$checkBoundsAndAddPossibleType(set, j, byteType);
        SimpleType shortType = integerLiteralTypeConstructor.getBuiltIns().getShortType();
        Intrinsics.checkNotNullExpressionValue(shortType, "getShortType(...)");
        _init_$checkBoundsAndAddPossibleType(set, j, shortType);
    }

    private static final void _init_$addUnsignedPossibleTypes(long j, ModuleDescriptor moduleDescriptor, Set<KotlinType> set) {
        _init_$checkBoundsAndAddPossibleType(set, j, PrimitiveTypeUtilKt.getUIntType(moduleDescriptor));
        set.add(PrimitiveTypeUtilKt.getULongType(moduleDescriptor));
        _init_$checkBoundsAndAddPossibleType(set, j, PrimitiveTypeUtilKt.getUByteType(moduleDescriptor));
        _init_$checkBoundsAndAddPossibleType(set, j, PrimitiveTypeUtilKt.getUShortType(moduleDescriptor));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private IntegerLiteralTypeConstructor(long j, ModuleDescriptor moduleDescriptor, Set<? extends KotlinType> set) {
        this.type = KotlinTypeFactory.integerLiteralType(TypeAttributes.INSTANCE.getEmpty(), this, false);
        this.supertypes = LazyKt.lazy(new Function0<List<SimpleType>>() { // from class: org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor$supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<SimpleType> invoke() {
                SimpleType defaultType = this.this$0.getBuiltIns().getComparable().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                List<SimpleType> listMutableListOf = CollectionsKt.mutableListOf(TypeSubstitutionKt.replace$default(defaultType, CollectionsKt.listOf(new TypeProjectionImpl(Variance.IN_VARIANCE, this.this$0.type)), null, 2, null));
                if (!this.this$0.isContainsOnlyUnsignedTypes()) {
                    listMutableListOf.add(this.this$0.getBuiltIns().getNumberType());
                }
                return listMutableListOf;
            }
        });
        this.value = j;
        this.module = moduleDescriptor;
        this.possibleTypes = set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isContainsOnlyUnsignedTypes() {
        Collection<KotlinType> allSignedLiteralTypes = PrimitiveTypeUtilKt.getAllSignedLiteralTypes(this.module);
        if ((allSignedLiteralTypes instanceof Collection) && allSignedLiteralTypes.isEmpty()) {
            return true;
        }
        Iterator<T> it = allSignedLiteralTypes.iterator();
        while (it.hasNext()) {
            if (this.possibleTypes.contains((KotlinType) it.next())) {
                return false;
            }
        }
        return true;
    }

    private final List<KotlinType> getSupertypes() {
        return (List) this.supertypes.getValue();
    }

    public final KotlinType getApproximatedType() {
        if (this.possibleTypes.contains(getBuiltIns().getIntType())) {
            SimpleType intType = getBuiltIns().getIntType();
            Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
            return intType;
        }
        if (this.possibleTypes.contains(getBuiltIns().getLongType())) {
            SimpleType longType = getBuiltIns().getLongType();
            Intrinsics.checkNotNullExpressionValue(longType, "getLongType(...)");
            return longType;
        }
        if (this.possibleTypes.contains(getBuiltIns().getByteType())) {
            SimpleType byteType = getBuiltIns().getByteType();
            Intrinsics.checkNotNullExpressionValue(byteType, "getByteType(...)");
            return byteType;
        }
        if (this.possibleTypes.contains(getBuiltIns().getShortType())) {
            SimpleType shortType = getBuiltIns().getShortType();
            Intrinsics.checkNotNullExpressionValue(shortType, "getShortType(...)");
            return shortType;
        }
        if (this.possibleTypes.contains(PrimitiveTypeUtilKt.getUIntType(this.module))) {
            return PrimitiveTypeUtilKt.getUIntType(this.module);
        }
        if (this.possibleTypes.contains(PrimitiveTypeUtilKt.getULongType(this.module))) {
            return PrimitiveTypeUtilKt.getULongType(this.module);
        }
        if (this.possibleTypes.contains(PrimitiveTypeUtilKt.getUByteType(this.module))) {
            return PrimitiveTypeUtilKt.getUByteType(this.module);
        }
        if (this.possibleTypes.contains(PrimitiveTypeUtilKt.getUShortType(this.module))) {
            return PrimitiveTypeUtilKt.getUShortType(this.module);
        }
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes, reason: collision with other method in class */
    public Collection<KotlinType> mo7438getSupertypes() {
        return getSupertypes();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        return this.module.getBuiltIns();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public String toString() {
        return "IntegerLiteralType" + valueToString();
    }

    public final boolean checkConstructor(TypeConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Set<KotlinType> set = this.possibleTypes;
        if ((set instanceof Collection) && set.isEmpty()) {
            return false;
        }
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((KotlinType) it.next()).getConstructor(), constructor)) {
                return true;
            }
        }
        return false;
    }

    private final String valueToString() {
        return "[" + CollectionsKt.joinToString$default(this.possibleTypes, ",", null, null, 0, null, new Function1<KotlinType, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor.valueToString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.toString();
            }
        }, 30, null) + ']';
    }
}
