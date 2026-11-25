package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: TypeParameterUpperBoundEraser.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\u0018\u0000 #2\u00020\u0001:\u0002#$B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0016\u0010\r\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0018\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J(\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\u001f*\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\"2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR*\u0010\r\u001a\u001e\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u000f0\u000f\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00110\u00110\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser;", "", "projectionComputer", "Lorg/jetbrains/kotlin/types/ErasureProjectionComputer;", "options", "Lorg/jetbrains/kotlin/types/TypeParameterErasureOptions;", "(Lorg/jetbrains/kotlin/types/ErasureProjectionComputer;Lorg/jetbrains/kotlin/types/TypeParameterErasureOptions;)V", "erroneousErasedBound", "Lorg/jetbrains/kotlin/types/error/ErrorType;", "getErroneousErasedBound", "()Lorg/jetbrains/kotlin/types/error/ErrorType;", "erroneousErasedBound$delegate", "Lkotlin/Lazy;", "getErasedUpperBound", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser$DataToEraseUpperBound;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/types/KotlinType;", "getOptions", "()Lorg/jetbrains/kotlin/types/TypeParameterErasureOptions;", "getProjectionComputer", "()Lorg/jetbrains/kotlin/types/ErasureProjectionComputer;", "storage", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "getDefaultType", "typeAttr", "Lorg/jetbrains/kotlin/types/ErasureTypeAttributes;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getErasedUpperBoundInternal", "substituteErasedUpperBounds", "", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "upperBounds", "", "Companion", "DataToEraseUpperBound", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeParameterUpperBoundEraser {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: erroneousErasedBound$delegate, reason: from kotlin metadata */
    private final Lazy erroneousErasedBound;
    private final MemoizedFunctionToNotNull<DataToEraseUpperBound, KotlinType> getErasedUpperBound;
    private final TypeParameterErasureOptions options;
    private final ErasureProjectionComputer projectionComputer;
    private final LockBasedStorageManager storage;

    public TypeParameterUpperBoundEraser(ErasureProjectionComputer projectionComputer, TypeParameterErasureOptions options) {
        Intrinsics.checkNotNullParameter(projectionComputer, "projectionComputer");
        Intrinsics.checkNotNullParameter(options, "options");
        this.projectionComputer = projectionComputer;
        this.options = options;
        LockBasedStorageManager lockBasedStorageManager = new LockBasedStorageManager("Type parameter upper bound erasure results");
        this.storage = lockBasedStorageManager;
        this.erroneousErasedBound = LazyKt.lazy(new Function0<ErrorType>() { // from class: org.jetbrains.kotlin.types.TypeParameterUpperBoundEraser$erroneousErasedBound$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ErrorType invoke() {
                return org.jetbrains.kotlin.types.error.ErrorUtils.createErrorType(ErrorTypeKind.CANNOT_COMPUTE_ERASED_BOUND, this.this$0.toString());
            }
        });
        MemoizedFunctionToNotNull<DataToEraseUpperBound, KotlinType> memoizedFunctionToNotNullCreateMemoizedFunction = lockBasedStorageManager.createMemoizedFunction(new Function1<DataToEraseUpperBound, KotlinType>() { // from class: org.jetbrains.kotlin.types.TypeParameterUpperBoundEraser.getErasedUpperBound.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(DataToEraseUpperBound dataToEraseUpperBound) {
                return TypeParameterUpperBoundEraser.this.getErasedUpperBoundInternal(dataToEraseUpperBound.getTypeParameter(), dataToEraseUpperBound.getTypeAttr());
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction, "createMemoizedFunction(...)");
        this.getErasedUpperBound = memoizedFunctionToNotNullCreateMemoizedFunction;
    }

    public final ErasureProjectionComputer getProjectionComputer() {
        return this.projectionComputer;
    }

    public /* synthetic */ TypeParameterUpperBoundEraser(ErasureProjectionComputer erasureProjectionComputer, TypeParameterErasureOptions typeParameterErasureOptions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(erasureProjectionComputer, (i & 2) != 0 ? new TypeParameterErasureOptions(false, false) : typeParameterErasureOptions);
    }

    public final TypeParameterErasureOptions getOptions() {
        return this.options;
    }

    private final ErrorType getErroneousErasedBound() {
        return (ErrorType) this.erroneousErasedBound.getValue();
    }

    /* JADX INFO: compiled from: TypeParameterUpperBoundEraser.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser$DataToEraseUpperBound;", "", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "typeAttr", "Lorg/jetbrains/kotlin/types/ErasureTypeAttributes;", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/types/ErasureTypeAttributes;)V", "getTypeAttr", "()Lorg/jetbrains/kotlin/types/ErasureTypeAttributes;", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class DataToEraseUpperBound {
        private final ErasureTypeAttributes typeAttr;
        private final TypeParameterDescriptor typeParameter;

        public static /* synthetic */ DataToEraseUpperBound copy$default(DataToEraseUpperBound dataToEraseUpperBound, TypeParameterDescriptor typeParameterDescriptor, ErasureTypeAttributes erasureTypeAttributes, int i, Object obj) {
            if ((i & 1) != 0) {
                typeParameterDescriptor = dataToEraseUpperBound.typeParameter;
            }
            if ((i & 2) != 0) {
                erasureTypeAttributes = dataToEraseUpperBound.typeAttr;
            }
            return dataToEraseUpperBound.copy(typeParameterDescriptor, erasureTypeAttributes);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final TypeParameterDescriptor getTypeParameter() {
            return this.typeParameter;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ErasureTypeAttributes getTypeAttr() {
            return this.typeAttr;
        }

        public final DataToEraseUpperBound copy(TypeParameterDescriptor typeParameter, ErasureTypeAttributes typeAttr) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            Intrinsics.checkNotNullParameter(typeAttr, "typeAttr");
            return new DataToEraseUpperBound(typeParameter, typeAttr);
        }

        public String toString() {
            return "DataToEraseUpperBound(typeParameter=" + this.typeParameter + ", typeAttr=" + this.typeAttr + ')';
        }

        public DataToEraseUpperBound(TypeParameterDescriptor typeParameter, ErasureTypeAttributes typeAttr) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            Intrinsics.checkNotNullParameter(typeAttr, "typeAttr");
            this.typeParameter = typeParameter;
            this.typeAttr = typeAttr;
        }

        public final TypeParameterDescriptor getTypeParameter() {
            return this.typeParameter;
        }

        public final ErasureTypeAttributes getTypeAttr() {
            return this.typeAttr;
        }

        public boolean equals(Object other) {
            if (!(other instanceof DataToEraseUpperBound)) {
                return false;
            }
            DataToEraseUpperBound dataToEraseUpperBound = (DataToEraseUpperBound) other;
            return Intrinsics.areEqual(dataToEraseUpperBound.typeParameter, this.typeParameter) && Intrinsics.areEqual(dataToEraseUpperBound.typeAttr, this.typeAttr);
        }

        public int hashCode() {
            int iHashCode = this.typeParameter.hashCode();
            return iHashCode + (iHashCode * 31) + this.typeAttr.hashCode();
        }
    }

    public final KotlinType getErasedUpperBound(TypeParameterDescriptor typeParameter, ErasureTypeAttributes typeAttr) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(typeAttr, "typeAttr");
        KotlinType kotlinTypeInvoke = this.getErasedUpperBound.invoke(new DataToEraseUpperBound(typeParameter, typeAttr));
        Intrinsics.checkNotNullExpressionValue(kotlinTypeInvoke, "invoke(...)");
        return kotlinTypeInvoke;
    }

    private final KotlinType getDefaultType(ErasureTypeAttributes typeAttr) {
        KotlinType kotlinTypeReplaceArgumentsWithStarProjections;
        SimpleType defaultType = typeAttr.getDefaultType();
        return (defaultType == null || (kotlinTypeReplaceArgumentsWithStarProjections = TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType)) == null) ? getErroneousErasedBound() : kotlinTypeReplaceArgumentsWithStarProjections;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType getErasedUpperBoundInternal(TypeParameterDescriptor typeParameter, ErasureTypeAttributes typeAttr) {
        TypeProjection typeProjectionComputeProjection;
        Set<TypeParameterDescriptor> visitedTypeParameters = typeAttr.getVisitedTypeParameters();
        if (visitedTypeParameters != null && visitedTypeParameters.contains(typeParameter.getOriginal())) {
            return getDefaultType(typeAttr);
        }
        SimpleType defaultType = typeParameter.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        Set<TypeParameterDescriptor> setExtractTypeParametersFromUpperBounds = TypeUtilsKt.extractTypeParametersFromUpperBounds(defaultType, visitedTypeParameters);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setExtractTypeParametersFromUpperBounds, 10)), 16));
        for (TypeParameterDescriptor typeParameterDescriptor : setExtractTypeParametersFromUpperBounds) {
            if (visitedTypeParameters == null || !visitedTypeParameters.contains(typeParameterDescriptor)) {
                typeProjectionComputeProjection = this.projectionComputer.computeProjection(typeParameterDescriptor, typeAttr, this, getErasedUpperBound(typeParameterDescriptor, typeAttr.withNewVisitedTypeParameter(typeParameter)));
            } else {
                typeProjectionComputeProjection = TypeUtils.makeStarProjection(typeParameterDescriptor, typeAttr);
                Intrinsics.checkNotNullExpressionValue(typeProjectionComputeProjection, "makeStarProjection(...)");
            }
            Pair pair = TuplesKt.to(typeParameterDescriptor.getTypeConstructor(), typeProjectionComputeProjection);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(TypeConstructorSubstitution.Companion.createByConstructorsMap$default(TypeConstructorSubstitution.INSTANCE, linkedHashMap, false, 2, null));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        List<KotlinType> upperBounds = typeParameter.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        Set<KotlinType> setSubstituteErasedUpperBounds = substituteErasedUpperBounds(typeSubstitutorCreate, upperBounds, typeAttr);
        if (!setSubstituteErasedUpperBounds.isEmpty()) {
            if (!this.options.getIntersectUpperBounds()) {
                if (setSubstituteErasedUpperBounds.size() != 1) {
                    throw new IllegalArgumentException("Should only be one computed upper bound if no need to intersect all bounds".toString());
                }
                return (KotlinType) CollectionsKt.single(setSubstituteErasedUpperBounds);
            }
            List list = CollectionsKt.toList(setSubstituteErasedUpperBounds);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((KotlinType) it.next()).unwrap());
            }
            return IntersectionTypeKt.m7477intersectTypes((List<? extends UnwrappedType>) arrayList);
        }
        return getDefaultType(typeAttr);
    }

    private final Set<KotlinType> substituteErasedUpperBounds(TypeSubstitutor typeSubstitutor, List<? extends KotlinType> list, ErasureTypeAttributes erasureTypeAttributes) {
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        for (KotlinType kotlinType : list) {
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            if (this$0 instanceof ClassDescriptor) {
                setCreateSetBuilder.add(INSTANCE.replaceArgumentsOfUpperBound(kotlinType, typeSubstitutor, erasureTypeAttributes.getVisitedTypeParameters(), this.options.getLeaveNonTypeParameterTypes()));
            } else if (this$0 instanceof TypeParameterDescriptor) {
                Set<TypeParameterDescriptor> visitedTypeParameters = erasureTypeAttributes.getVisitedTypeParameters();
                if (visitedTypeParameters != null && visitedTypeParameters.contains(this$0)) {
                    setCreateSetBuilder.add(getDefaultType(erasureTypeAttributes));
                } else {
                    List<KotlinType> upperBounds = ((TypeParameterDescriptor) this$0).getUpperBounds();
                    Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                    setCreateSetBuilder.addAll(substituteErasedUpperBounds(typeSubstitutor, upperBounds, erasureTypeAttributes));
                }
            }
            if (!this.options.getIntersectUpperBounds()) {
                break;
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    /* JADX INFO: compiled from: TypeParameterUpperBoundEraser.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser$Companion;", "", "()V", "replaceArgumentsOfUpperBound", "Lorg/jetbrains/kotlin/types/KotlinType;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "visitedTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "leaveNonTypeParameterTypes", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ KotlinType replaceArgumentsOfUpperBound$default(Companion companion, KotlinType kotlinType, TypeSubstitutor typeSubstitutor, Set set, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return companion.replaceArgumentsOfUpperBound(kotlinType, typeSubstitutor, set, z);
        }

        /* JADX WARN: Code duplicated, block: B:20:0x008b  */
        /* JADX WARN: Code duplicated, block: B:24:0x0095  */
        /* JADX WARN: Code duplicated, block: B:29:0x00ab  */
        /* JADX WARN: Code duplicated, block: B:48:0x0128  */
        /* JADX WARN: Code duplicated, block: B:52:0x0132  */
        /* JADX WARN: Code duplicated, block: B:57:0x0148  */
        /* JADX WARN: Code duplicated, block: B:79:0x01cc  */
        /* JADX WARN: Code duplicated, block: B:83:0x01d6  */
        /* JADX WARN: Code duplicated, block: B:88:0x01ec  */
        public final KotlinType replaceArgumentsOfUpperBound(KotlinType kotlinType, TypeSubstitutor substitutor, Set<? extends TypeParameterDescriptor> set, boolean z) {
            SimpleType simpleTypeFlexibleType;
            boolean z2;
            KotlinType type;
            boolean z3;
            KotlinType type2;
            boolean z4;
            KotlinType type3;
            Intrinsics.checkNotNullParameter(kotlinType, "<this>");
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
            if (unwrappedTypeUnwrap instanceof FlexibleType) {
                FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
                SimpleType lowerBound = flexibleType.getLowerBound();
                if (!lowerBound.getConstructor().getParameters().isEmpty() && lowerBound.getConstructor().getThis$0() != null) {
                    List<TypeParameterDescriptor> parameters = lowerBound.getConstructor().getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                    List<TypeParameterDescriptor> list = parameters;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (TypeParameterDescriptor typeParameterDescriptor : list) {
                        StarProjectionImpl starProjectionImpl = (TypeProjection) CollectionsKt.getOrNull(kotlinType.getArguments(), typeParameterDescriptor.getIndex());
                        if (!z || starProjectionImpl == null || (type3 = starProjectionImpl.getType()) == null) {
                            if (set == null && set.contains(typeParameterDescriptor)) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (starProjectionImpl != null || z4) {
                                starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
                            } else {
                                TypeSubstitution substitution = substitutor.getSubstitution();
                                KotlinType type4 = starProjectionImpl.getType();
                                Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
                                if (substitution.mo7475get(type4) == null) {
                                    starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
                                }
                            }
                        } else {
                            Intrinsics.checkNotNull(type3);
                            if (TypeUtilsKt.containsTypeParameter(type3)) {
                                if (set == null) {
                                    z4 = false;
                                } else {
                                    z4 = false;
                                }
                                if (starProjectionImpl != null) {
                                    starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
                                } else {
                                    starProjectionImpl = new StarProjectionImpl(typeParameterDescriptor);
                                }
                            }
                        }
                        arrayList.add(starProjectionImpl);
                    }
                    lowerBound = TypeSubstitutionKt.replace$default(lowerBound, arrayList, null, 2, null);
                }
                SimpleType upperBound = flexibleType.getUpperBound();
                if (!upperBound.getConstructor().getParameters().isEmpty() && upperBound.getConstructor().getThis$0() != null) {
                    List<TypeParameterDescriptor> parameters2 = upperBound.getConstructor().getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                    List<TypeParameterDescriptor> list2 = parameters2;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    for (TypeParameterDescriptor typeParameterDescriptor2 : list2) {
                        StarProjectionImpl starProjectionImpl2 = (TypeProjection) CollectionsKt.getOrNull(kotlinType.getArguments(), typeParameterDescriptor2.getIndex());
                        if (!z || starProjectionImpl2 == null || (type2 = starProjectionImpl2.getType()) == null) {
                            if (set == null && set.contains(typeParameterDescriptor2)) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            if (starProjectionImpl2 != null || z3) {
                                starProjectionImpl2 = new StarProjectionImpl(typeParameterDescriptor2);
                            } else {
                                TypeSubstitution substitution2 = substitutor.getSubstitution();
                                KotlinType type5 = starProjectionImpl2.getType();
                                Intrinsics.checkNotNullExpressionValue(type5, "getType(...)");
                                if (substitution2.mo7475get(type5) == null) {
                                    starProjectionImpl2 = new StarProjectionImpl(typeParameterDescriptor2);
                                }
                            }
                        } else {
                            Intrinsics.checkNotNull(type2);
                            if (TypeUtilsKt.containsTypeParameter(type2)) {
                                if (set == null) {
                                    z3 = false;
                                } else {
                                    z3 = false;
                                }
                                if (starProjectionImpl2 != null) {
                                    starProjectionImpl2 = new StarProjectionImpl(typeParameterDescriptor2);
                                } else {
                                    starProjectionImpl2 = new StarProjectionImpl(typeParameterDescriptor2);
                                }
                            }
                        }
                        arrayList2.add(starProjectionImpl2);
                    }
                    upperBound = TypeSubstitutionKt.replace$default(upperBound, arrayList2, null, 2, null);
                }
                simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(lowerBound, upperBound);
            } else {
                if (!(unwrappedTypeUnwrap instanceof SimpleType)) {
                    throw new NoWhenBranchMatchedException();
                }
                SimpleType simpleTypeReplace$default = (SimpleType) unwrappedTypeUnwrap;
                if (!simpleTypeReplace$default.getConstructor().getParameters().isEmpty() && simpleTypeReplace$default.getConstructor().getThis$0() != null) {
                    List<TypeParameterDescriptor> parameters3 = simpleTypeReplace$default.getConstructor().getParameters();
                    Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
                    List<TypeParameterDescriptor> list3 = parameters3;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    for (TypeParameterDescriptor typeParameterDescriptor3 : list3) {
                        StarProjectionImpl starProjectionImpl3 = (TypeProjection) CollectionsKt.getOrNull(kotlinType.getArguments(), typeParameterDescriptor3.getIndex());
                        if (!z || starProjectionImpl3 == null || (type = starProjectionImpl3.getType()) == null) {
                            if (set == null && set.contains(typeParameterDescriptor3)) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (starProjectionImpl3 != null || z2) {
                                starProjectionImpl3 = new StarProjectionImpl(typeParameterDescriptor3);
                            } else {
                                TypeSubstitution substitution3 = substitutor.getSubstitution();
                                KotlinType type6 = starProjectionImpl3.getType();
                                Intrinsics.checkNotNullExpressionValue(type6, "getType(...)");
                                if (substitution3.mo7475get(type6) == null) {
                                    starProjectionImpl3 = new StarProjectionImpl(typeParameterDescriptor3);
                                }
                            }
                        } else {
                            Intrinsics.checkNotNull(type);
                            if (TypeUtilsKt.containsTypeParameter(type)) {
                                if (set == null) {
                                    z2 = false;
                                } else {
                                    z2 = false;
                                }
                                if (starProjectionImpl3 != null) {
                                    starProjectionImpl3 = new StarProjectionImpl(typeParameterDescriptor3);
                                } else {
                                    starProjectionImpl3 = new StarProjectionImpl(typeParameterDescriptor3);
                                }
                            }
                        }
                        arrayList3.add(starProjectionImpl3);
                    }
                    simpleTypeReplace$default = TypeSubstitutionKt.replace$default(simpleTypeReplace$default, arrayList3, null, 2, null);
                }
                simpleTypeFlexibleType = simpleTypeReplace$default;
            }
            KotlinType kotlinTypeSafeSubstitute = substitutor.safeSubstitute(TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, unwrappedTypeUnwrap), Variance.OUT_VARIANCE);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
            return kotlinTypeSafeSubstitute;
        }
    }
}
