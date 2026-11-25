package org.jetbrains.kotlin.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.load.java.lazy.JavaResolverSettings;
import org.jetbrains.kotlin.load.java.lazy.types.RawTypeImpl;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J*\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000fJD\u0010\u0010\u001a\u00020\u0011*\u00020\u00062\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u000f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0002J2\u0010\u0016\u001a\u00020\u0017*\u00020\u00182\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b2\u0006\u0010\u0012\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement;", "", "javaResolverSettings", "Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;", "(Lorg/jetbrains/kotlin/load/java/lazy/JavaResolverSettings;)V", "notNullTypeParameter", "Lorg/jetbrains/kotlin/types/SimpleType;", "enhancedType", "enhance", "Lorg/jetbrains/kotlin/types/KotlinType;", "qualifiers", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "isSuperTypesEnhancement", "", "enhanceInflexible", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement$SimpleResult;", "index", "position", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeComponentPosition;", "isBoundOfRawType", "enhancePossiblyFlexible", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement$Result;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Result", "SimpleResult", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeEnhancement {
    private final JavaResolverSettings javaResolverSettings;

    /* JADX INFO: compiled from: typeEnhancement.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement$Result;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "subtreeSize", "", "(Lorg/jetbrains/kotlin/types/KotlinType;I)V", "getSubtreeSize", "()I", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Result {
        private final int subtreeSize;
        private final KotlinType type;

        public Result(KotlinType kotlinType, int i) {
            this.type = kotlinType;
            this.subtreeSize = i;
        }

        public final int getSubtreeSize() {
            return this.subtreeSize;
        }

        public final KotlinType getType() {
            return this.type;
        }
    }

    public JavaTypeEnhancement(JavaResolverSettings javaResolverSettings) {
        Intrinsics.checkNotNullParameter(javaResolverSettings, "javaResolverSettings");
        this.javaResolverSettings = javaResolverSettings;
    }

    /* JADX INFO: compiled from: typeEnhancement.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement$SimpleResult;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/SimpleType;", "subtreeSize", "", "forWarnings", "", "(Lorg/jetbrains/kotlin/types/SimpleType;IZ)V", "getForWarnings", "()Z", "getSubtreeSize", "()I", "getType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SimpleResult {
        private final boolean forWarnings;
        private final int subtreeSize;
        private final SimpleType type;

        public SimpleResult(SimpleType simpleType, int i, boolean z) {
            this.type = simpleType;
            this.subtreeSize = i;
            this.forWarnings = z;
        }

        public final boolean getForWarnings() {
            return this.forWarnings;
        }

        public final int getSubtreeSize() {
            return this.subtreeSize;
        }

        public final SimpleType getType() {
            return this.type;
        }
    }

    public static /* synthetic */ KotlinType enhance$default(JavaTypeEnhancement javaTypeEnhancement, KotlinType kotlinType, Function1 function1, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return javaTypeEnhancement.enhance(kotlinType, function1, z);
    }

    public final KotlinType enhance(KotlinType kotlinType, Function1<? super Integer, JavaTypeQualifiers> qualifiers, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
        return enhancePossiblyFlexible(kotlinType.unwrap(), qualifiers, 0, z).getType();
    }

    /* JADX WARN: Code duplicated, block: B:40:0x009e  */
    private final Result enhancePossiblyFlexible(UnwrappedType unwrappedType, Function1<? super Integer, JavaTypeQualifiers> function1, int i, boolean z) {
        SimpleType simpleTypeFlexibleType;
        RawTypeImpl rawTypeImplWrapEnhancement = null;
        if (KotlinTypeKt.isError(unwrappedType)) {
            return new Result(null, 1);
        }
        if (unwrappedType instanceof FlexibleType) {
            boolean z2 = unwrappedType instanceof RawType;
            FlexibleType flexibleType = (FlexibleType) unwrappedType;
            SimpleResult simpleResultEnhanceInflexible = enhanceInflexible(flexibleType.getLowerBound(), function1, i, TypeComponentPosition.FLEXIBLE_LOWER, z2, z);
            SimpleResult simpleResultEnhanceInflexible2 = enhanceInflexible(flexibleType.getUpperBound(), function1, i, TypeComponentPosition.FLEXIBLE_UPPER, z2, z);
            simpleResultEnhanceInflexible.getSubtreeSize();
            simpleResultEnhanceInflexible2.getSubtreeSize();
            if (simpleResultEnhanceInflexible.getType() != null || simpleResultEnhanceInflexible2.getType() != null) {
                if (simpleResultEnhanceInflexible.getForWarnings() || simpleResultEnhanceInflexible2.getForWarnings()) {
                    SimpleType type = simpleResultEnhanceInflexible2.getType();
                    if (type == null) {
                        SimpleType type2 = simpleResultEnhanceInflexible.getType();
                        Intrinsics.checkNotNull(type2);
                        simpleTypeFlexibleType = type2;
                    } else {
                        SimpleType type3 = simpleResultEnhanceInflexible.getType();
                        if (type3 == null) {
                            type3 = type;
                        }
                        simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(type3, type);
                        if (simpleTypeFlexibleType == null) {
                            SimpleType type4 = simpleResultEnhanceInflexible.getType();
                            Intrinsics.checkNotNull(type4);
                            simpleTypeFlexibleType = type4;
                        }
                    }
                    rawTypeImplWrapEnhancement = TypeWithEnhancementKt.wrapEnhancement(unwrappedType, simpleTypeFlexibleType);
                } else if (z2) {
                    SimpleType type5 = simpleResultEnhanceInflexible.getType();
                    if (type5 == null) {
                        type5 = flexibleType.getLowerBound();
                    }
                    SimpleType type6 = simpleResultEnhanceInflexible2.getType();
                    if (type6 == null) {
                        type6 = flexibleType.getUpperBound();
                    }
                    rawTypeImplWrapEnhancement = new RawTypeImpl(type5, type6);
                } else {
                    SimpleType type7 = simpleResultEnhanceInflexible.getType();
                    if (type7 == null) {
                        type7 = flexibleType.getLowerBound();
                    }
                    SimpleType type8 = simpleResultEnhanceInflexible2.getType();
                    if (type8 == null) {
                        type8 = flexibleType.getUpperBound();
                    }
                    rawTypeImplWrapEnhancement = KotlinTypeFactory.flexibleType(type7, type8);
                }
            }
            return new Result(rawTypeImplWrapEnhancement, simpleResultEnhanceInflexible.getSubtreeSize());
        }
        if (!(unwrappedType instanceof SimpleType)) {
            throw new NoWhenBranchMatchedException();
        }
        SimpleResult simpleResultEnhanceInflexible$default = enhanceInflexible$default(this, (SimpleType) unwrappedType, function1, i, TypeComponentPosition.INFLEXIBLE, false, z, 8, null);
        return new Result(simpleResultEnhanceInflexible$default.getForWarnings() ? TypeWithEnhancementKt.wrapEnhancement(unwrappedType, simpleResultEnhanceInflexible$default.getType()) : simpleResultEnhanceInflexible$default.getType(), simpleResultEnhanceInflexible$default.getSubtreeSize());
    }

    static /* synthetic */ SimpleResult enhanceInflexible$default(JavaTypeEnhancement javaTypeEnhancement, SimpleType simpleType, Function1 function1, int i, TypeComponentPosition typeComponentPosition, boolean z, boolean z2, int i2, Object obj) {
        return javaTypeEnhancement.enhanceInflexible(simpleType, function1, i, typeComponentPosition, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? false : z2);
    }

    private final SimpleResult enhanceInflexible(SimpleType simpleType, Function1<? super Integer, JavaTypeQualifiers> function1, int i, TypeComponentPosition typeComponentPosition, boolean z, boolean z2) {
        TypeConstructor constructor;
        boolean z3;
        Result result;
        TypeProjection typeProjectionMakeStarProjection;
        Function1<? super Integer, JavaTypeQualifiers> function2 = function1;
        boolean zShouldEnhance = TypeComponentPositionKt.shouldEnhance(typeComponentPosition);
        boolean z4 = (z2 && z) ? false : true;
        KotlinType kotlinType = null;
        if (!zShouldEnhance && simpleType.getArguments().isEmpty()) {
            return new SimpleResult(null, 1, false);
        }
        ClassifierDescriptor this$0 = simpleType.getConstructor().getThis$0();
        if (this$0 == null) {
            return new SimpleResult(null, 1, false);
        }
        JavaTypeQualifiers javaTypeQualifiersInvoke = function2.invoke(Integer.valueOf(i));
        ClassifierDescriptor classifierDescriptorEnhanceMutability = TypeEnhancementKt.enhanceMutability(this$0, javaTypeQualifiersInvoke, typeComponentPosition);
        Boolean enhancedNullability = TypeEnhancementKt.getEnhancedNullability(javaTypeQualifiersInvoke, typeComponentPosition);
        if (classifierDescriptorEnhanceMutability == null || (constructor = classifierDescriptorEnhanceMutability.getTypeConstructor()) == null) {
            constructor = simpleType.getConstructor();
        }
        TypeConstructor typeConstructor = constructor;
        Intrinsics.checkNotNull(typeConstructor);
        int subtreeSize = i + 1;
        List<TypeProjection> arguments = simpleType.getArguments();
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> list = parameters;
        Iterator<T> it = arguments.iterator();
        Iterator<T> it2 = list.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(arguments, 10), CollectionsKt.collectionSizeOrDefault(list, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) it2.next();
            TypeProjection typeProjection = (TypeProjection) next;
            if (!z4) {
                z3 = z4;
                result = new Result(kotlinType, 0);
            } else {
                z3 = z4;
                if (!typeProjection.isStarProjection()) {
                    result = enhancePossiblyFlexible(typeProjection.getNullableAnyType().unwrap(), function2, subtreeSize, z2);
                } else if (function2.invoke(Integer.valueOf(subtreeSize)).getNullability() == NullabilityQualifier.FORCE_FLEXIBILITY) {
                    UnwrappedType unwrappedTypeUnwrap = typeProjection.getNullableAnyType().unwrap();
                    result = new Result(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(unwrappedTypeUnwrap).makeNullableAsSpecified(false), FlexibleTypesKt.upperIfFlexible(unwrappedTypeUnwrap).makeNullableAsSpecified(true)), 1);
                } else {
                    result = new Result(null, 1);
                }
            }
            subtreeSize += result.getSubtreeSize();
            if (result.getType() != null) {
                KotlinType type = result.getType();
                Variance projectionKind = typeProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
                typeProjectionMakeStarProjection = TypeUtilsKt.createProjection(type, projectionKind, typeParameterDescriptor);
            } else if (classifierDescriptorEnhanceMutability == null || typeProjection.isStarProjection()) {
                typeProjectionMakeStarProjection = classifierDescriptorEnhanceMutability != null ? TypeUtils.makeStarProjection(typeParameterDescriptor) : null;
            } else {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                Variance projectionKind2 = typeProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind2, "getProjectionKind(...)");
                typeProjectionMakeStarProjection = TypeUtilsKt.createProjection(nullableAnyType, projectionKind2, typeParameterDescriptor);
            }
            arrayList.add(typeProjectionMakeStarProjection);
            function2 = function1;
            z4 = z3;
            kotlinType = null;
        }
        ArrayList arrayList2 = arrayList;
        int i2 = subtreeSize - i;
        if (classifierDescriptorEnhanceMutability == null && enhancedNullability == null) {
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it3 = arrayList3.iterator();
                do {
                    if (it3.hasNext()) {
                    }
                } while (((TypeProjection) it3.next()) == null);
            }
            return new SimpleResult(null, i2, false);
        }
        Annotations[] annotationsArr = new Annotations[3];
        annotationsArr[0] = simpleType.getAnnotations();
        EnhancedTypeAnnotations enhancedTypeAnnotations = TypeEnhancementKt.ENHANCED_MUTABILITY_ANNOTATIONS;
        if (classifierDescriptorEnhanceMutability == null) {
            enhancedTypeAnnotations = null;
        }
        annotationsArr[1] = enhancedTypeAnnotations;
        annotationsArr[2] = enhancedNullability != null ? TypeEnhancementKt.getENHANCED_NULLABILITY_ANNOTATIONS() : null;
        TypeAttributes defaultAttributes = TypeAttributesKt.toDefaultAttributes(TypeEnhancementKt.compositeAnnotationsOrSingle(CollectionsKt.listOfNotNull((Object[]) annotationsArr)));
        ArrayList arrayList4 = arrayList2;
        List<TypeProjection> arguments2 = simpleType.getArguments();
        Iterator it4 = arrayList4.iterator();
        Iterator<T> it5 = arguments2.iterator();
        ArrayList arrayList5 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(arrayList4, 10), CollectionsKt.collectionSizeOrDefault(arguments2, 10)));
        while (it4.hasNext() && it5.hasNext()) {
            Object next2 = it4.next();
            TypeProjection typeProjection2 = (TypeProjection) it5.next();
            TypeProjection typeProjection3 = (TypeProjection) next2;
            if (typeProjection3 != null) {
                typeProjection2 = typeProjection3;
            }
            arrayList5.add(typeProjection2);
        }
        SimpleType simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(defaultAttributes, typeConstructor, arrayList5, enhancedNullability != null ? enhancedNullability.booleanValue() : simpleType.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null);
        if (javaTypeQualifiersInvoke.getDefinitelyNotNull()) {
            simpleTypeSimpleType$default = notNullTypeParameter(simpleTypeSimpleType$default);
        }
        return new SimpleResult(simpleTypeSimpleType$default, i2, enhancedNullability != null && javaTypeQualifiersInvoke.getIsNullabilityQualifierForWarning());
    }

    private final SimpleType notNullTypeParameter(SimpleType enhancedType) {
        if (this.javaResolverSettings.get$correctNullabilityForNotNullTypeParameter()) {
            return SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull(enhancedType, true);
        }
        return new NotNullTypeParameterImpl(enhancedType);
    }
}
