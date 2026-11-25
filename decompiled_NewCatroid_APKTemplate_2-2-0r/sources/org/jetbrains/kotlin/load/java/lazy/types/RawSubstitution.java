package org.jetbrains.kotlin.load.java.lazy.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.ErasureProjectionComputer;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeParameterUpperBoundEraser;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: RawSubstitution.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J,\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u00112\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0002J\u0011\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0011H\u0096\u0002J\b\u0010\u0015\u001a\u00020\nH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/types/RawSubstitution;", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "typeParameterUpperBoundEraser", "Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser;", "(Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser;)V", "projectionComputer", "Lorg/jetbrains/kotlin/load/java/lazy/types/RawProjectionComputer;", "eraseInflexibleBasedOnClassDescriptor", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/SimpleType;", "", ModuleXmlParser.TYPE, "declaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED, "Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeAttributes;", "eraseType", "Lorg/jetbrains/kotlin/types/KotlinType;", "get", "Lorg/jetbrains/kotlin/types/TypeProjectionImpl;", ASTExpr.DEFAULT_MAP_KEY_NAME, "isEmpty", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RawSubstitution extends TypeSubstitution {
    private static final JavaTypeAttributes lowerTypeAttr = JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, true, null, 5, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND);
    private static final JavaTypeAttributes upperTypeAttr = JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, true, null, 5, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND);
    private final RawProjectionComputer projectionComputer;
    private final TypeParameterUpperBoundEraser typeParameterUpperBoundEraser;

    /* JADX WARN: Multi-variable type inference failed */
    public RawSubstitution() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean isEmpty() {
        return false;
    }

    public RawSubstitution(TypeParameterUpperBoundEraser typeParameterUpperBoundEraser) {
        RawProjectionComputer rawProjectionComputer = new RawProjectionComputer();
        this.projectionComputer = rawProjectionComputer;
        this.typeParameterUpperBoundEraser = typeParameterUpperBoundEraser == null ? new TypeParameterUpperBoundEraser(rawProjectionComputer, null, 2, null) : typeParameterUpperBoundEraser;
    }

    public /* synthetic */ RawSubstitution(TypeParameterUpperBoundEraser typeParameterUpperBoundEraser, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : typeParameterUpperBoundEraser);
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjectionImpl mo7475get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return new TypeProjectionImpl(eraseType$default(this, key, null, 2, null));
    }

    static /* synthetic */ KotlinType eraseType$default(RawSubstitution rawSubstitution, KotlinType kotlinType, JavaTypeAttributes javaTypeAttributes, int i, Object obj) {
        if ((i & 2) != 0) {
            javaTypeAttributes = new JavaTypeAttributes(TypeUsage.COMMON, null, false, false, null, null, 62, null);
        }
        return rawSubstitution.eraseType(kotlinType, javaTypeAttributes);
    }

    private final KotlinType eraseType(KotlinType type, JavaTypeAttributes attr) {
        RawTypeImpl rawTypeImpl;
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 instanceof TypeParameterDescriptor) {
            return eraseType(this.typeParameterUpperBoundEraser.getErasedUpperBound((TypeParameterDescriptor) this$0, attr.markIsRaw(true)), attr);
        }
        if (this$0 instanceof ClassDescriptor) {
            ClassifierDescriptor this$1 = FlexibleTypesKt.upperIfFlexible(type).getConstructor().getThis$0();
            if (!(this$1 instanceof ClassDescriptor)) {
                throw new IllegalStateException(("For some reason declaration for upper bound is not a class but \"" + this$1 + "\" while for lower it's \"" + this$0 + '\"').toString());
            }
            Pair<SimpleType, Boolean> pairEraseInflexibleBasedOnClassDescriptor = eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.lowerIfFlexible(type), (ClassDescriptor) this$0, lowerTypeAttr);
            SimpleType simpleTypeComponent1 = pairEraseInflexibleBasedOnClassDescriptor.component1();
            boolean zBooleanValue = pairEraseInflexibleBasedOnClassDescriptor.component2().booleanValue();
            Pair<SimpleType, Boolean> pairEraseInflexibleBasedOnClassDescriptor2 = eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.upperIfFlexible(type), (ClassDescriptor) this$1, upperTypeAttr);
            SimpleType simpleTypeComponent2 = pairEraseInflexibleBasedOnClassDescriptor2.component1();
            boolean zBooleanValue2 = pairEraseInflexibleBasedOnClassDescriptor2.component2().booleanValue();
            if (zBooleanValue || zBooleanValue2) {
                rawTypeImpl = new RawTypeImpl(simpleTypeComponent1, simpleTypeComponent2);
            } else {
                rawTypeImpl = KotlinTypeFactory.flexibleType(simpleTypeComponent1, simpleTypeComponent2);
            }
            return rawTypeImpl;
        }
        throw new IllegalStateException(("Unexpected declaration kind: " + this$0).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<SimpleType, Boolean> eraseInflexibleBasedOnClassDescriptor(final SimpleType type, final ClassDescriptor declaration, final JavaTypeAttributes attr) {
        if (type.getConstructor().getParameters().isEmpty()) {
            return TuplesKt.to(type, false);
        }
        SimpleType simpleType = type;
        if (KotlinBuiltIns.isArray(simpleType)) {
            TypeProjection typeProjection = type.getArguments().get(0);
            Variance projectionKind = typeProjection.getProjectionKind();
            KotlinType nullableAnyType = typeProjection.getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            return TuplesKt.to(KotlinTypeFactory.simpleType$default(type.getAttributes(), type.getConstructor(), CollectionsKt.listOf(new TypeProjectionImpl(projectionKind, eraseType(nullableAnyType, attr))), type.getIsMarkedNullable(), (KotlinTypeRefiner) null, 16, (Object) null), false);
        }
        if (KotlinTypeKt.isError(simpleType)) {
            return TuplesKt.to(ErrorUtils.createErrorType(ErrorTypeKind.ERROR_RAW_TYPE, type.getConstructor().toString()), false);
        }
        MemberScope memberScope = declaration.getMemberScope(this);
        Intrinsics.checkNotNullExpressionValue(memberScope, "getMemberScope(...)");
        TypeAttributes attributes = type.getAttributes();
        TypeConstructor typeConstructor = declaration.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        List<TypeParameterDescriptor> parameters = declaration.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            RawProjectionComputer rawProjectionComputer = this.projectionComputer;
            Intrinsics.checkNotNull(typeParameterDescriptor);
            arrayList.add(ErasureProjectionComputer.computeProjection$default(rawProjectionComputer, typeParameterDescriptor, attr, this.typeParameterUpperBoundEraser, null, 8, null));
        }
        return TuplesKt.to(KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(attributes, typeConstructor, arrayList, type.getIsMarkedNullable(), memberScope, new Function1<KotlinTypeRefiner, SimpleType>() { // from class: org.jetbrains.kotlin.load.java.lazy.types.RawSubstitution.eraseInflexibleBasedOnClassDescriptor.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                ClassId classId;
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                ClassDescriptor classDescriptor = declaration;
                if (!(classDescriptor instanceof ClassDescriptor)) {
                    classDescriptor = null;
                }
                if (classDescriptor == null || (classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor)) == null || (classDescriptorFindClassAcrossModuleDependencies = kotlinTypeRefiner.findClassAcrossModuleDependencies(classId)) == null || Intrinsics.areEqual(classDescriptorFindClassAcrossModuleDependencies, declaration)) {
                    return null;
                }
                return (SimpleType) this.eraseInflexibleBasedOnClassDescriptor(type, classDescriptorFindClassAcrossModuleDependencies, attr).getFirst();
            }
        }), true);
    }
}
