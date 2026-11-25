package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptorKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;

/* JADX INFO: compiled from: KotlinTypeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001)B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0007J \u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J(\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0003J&\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0007J>\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u00062\b\b\u0002\u0010#\u001a\u00020\u00182\b\b\u0002\u0010\f\u001a\u00020\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0007J:\u0010!\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u0007J6\u0010$\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u000bH\u0007JP\u0010$\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u000b2\u0018\u0010&\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004j\u0002`\u0007H\u0007J\u001a\u0010'\u001a\u00020\u0006*\u00020(2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0007R#\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004j\u0002`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/types/KotlinTypeFactory;", "", "()V", "EMPTY_REFINED_TYPE_FACTORY", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/types/RefinedTypeFactory;", "getEMPTY_REFINED_TYPE_FACTORY", "()Lkotlin/jvm/functions/Function1;", "computeMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "kotlinTypeRefiner", "flexibleType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "lowerBound", "upperBound", "integerLiteralType", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "Lorg/jetbrains/kotlin/resolve/constants/IntegerLiteralTypeConstructor;", "nullable", "", "refineConstructor", "Lorg/jetbrains/kotlin/types/KotlinTypeFactory$ExpandedTypeOrRefinedConstructor;", "simpleNotNullType", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "simpleType", "baseType", "annotations", "simpleTypeWithNonTrivialMemberScope", "memberScope", "refinedTypeFactory", "computeExpandedType", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "ExpandedTypeOrRefinedConstructor", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeFactory {
    public static final KotlinTypeFactory INSTANCE = new KotlinTypeFactory();
    private static final Function1<KotlinTypeRefiner, SimpleType> EMPTY_REFINED_TYPE_FACTORY = new Function1() { // from class: org.jetbrains.kotlin.types.KotlinTypeFactory$EMPTY_REFINED_TYPE_FACTORY$1
        @Override // kotlin.jvm.functions.Function1
        public final Void invoke(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "<anonymous parameter 0>");
            return null;
        }
    };

    @JvmStatic
    public static final SimpleType simpleType(TypeAttributes attributes, TypeConstructor constructor, List<? extends TypeProjection> arguments, boolean z) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return simpleType$default(attributes, constructor, arguments, z, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    private KotlinTypeFactory() {
    }

    public final Function1<KotlinTypeRefiner, SimpleType> getEMPTY_REFINED_TYPE_FACTORY() {
        return EMPTY_REFINED_TYPE_FACTORY;
    }

    private final MemberScope computeMemberScope(TypeConstructor constructor, List<? extends TypeProjection> arguments, KotlinTypeRefiner kotlinTypeRefiner) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = constructor.getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            return ((TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).getDefaultType().getMemberScope();
        }
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) {
            if (kotlinTypeRefiner == null) {
                kotlinTypeRefiner = DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtilsKt.getModule(classifierDescriptorMo7434getDeclarationDescriptor));
            }
            if (arguments.isEmpty()) {
                return ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor, kotlinTypeRefiner);
            }
            return ModuleAwareClassDescriptorKt.getRefinedMemberScopeIfPossible((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor, TypeConstructorSubstitution.INSTANCE.create(constructor, arguments), kotlinTypeRefiner);
        }
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeAliasDescriptor) {
            ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ABBREVIATION_TYPE;
            String string = ((TypeAliasDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).getShortName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return org.jetbrains.kotlin.types.error.ErrorUtils.createErrorScope(errorScopeKind, true, string);
        }
        if (constructor instanceof IntersectionTypeConstructor) {
            return ((IntersectionTypeConstructor) constructor).createScopeForKotlinType();
        }
        throw new IllegalStateException("Unsupported classifier: " + classifierDescriptorMo7434getDeclarationDescriptor + " for constructor: " + constructor);
    }

    public static /* synthetic */ SimpleType simpleType$default(TypeAttributes typeAttributes, TypeConstructor typeConstructor, List list, boolean z, KotlinTypeRefiner kotlinTypeRefiner, int i, Object obj) {
        if ((i & 16) != 0) {
            kotlinTypeRefiner = null;
        }
        return simpleType(typeAttributes, typeConstructor, (List<? extends TypeProjection>) list, z, kotlinTypeRefiner);
    }

    @JvmStatic
    public static final SimpleType simpleType(final TypeAttributes attributes, final TypeConstructor constructor, final List<? extends TypeProjection> arguments, final boolean nullable, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (attributes.isEmpty() && arguments.isEmpty() && !nullable && constructor.getThis$0() != null) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = constructor.getThis$0();
            Intrinsics.checkNotNull(classifierDescriptorMo7434getDeclarationDescriptor);
            SimpleType defaultType = classifierDescriptorMo7434getDeclarationDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            return defaultType;
        }
        return simpleTypeWithNonTrivialMemberScope(attributes, constructor, arguments, nullable, INSTANCE.computeMemberScope(constructor, arguments, kotlinTypeRefiner), new Function1<KotlinTypeRefiner, SimpleType>() { // from class: org.jetbrains.kotlin.types.KotlinTypeFactory.simpleType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner refiner) {
                Intrinsics.checkNotNullParameter(refiner, "refiner");
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructorRefineConstructor = KotlinTypeFactory.INSTANCE.refineConstructor(constructor, refiner, arguments);
                if (expandedTypeOrRefinedConstructorRefineConstructor == null) {
                    return null;
                }
                SimpleType expandedType = expandedTypeOrRefinedConstructorRefineConstructor.getExpandedType();
                if (expandedType != null) {
                    return expandedType;
                }
                TypeAttributes typeAttributes = attributes;
                TypeConstructor refinedConstructor = expandedTypeOrRefinedConstructorRefineConstructor.getRefinedConstructor();
                Intrinsics.checkNotNull(refinedConstructor);
                return KotlinTypeFactory.simpleType(typeAttributes, refinedConstructor, arguments, nullable, refiner);
            }
        });
    }

    @JvmStatic
    public static final SimpleType computeExpandedType(TypeAliasDescriptor typeAliasDescriptor, List<? extends TypeProjection> arguments) {
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new TypeAliasExpander(TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false).expand(TypeAliasExpansion.INSTANCE.create(null, typeAliasDescriptor, arguments), TypeAttributes.INSTANCE.getEmpty());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpandedTypeOrRefinedConstructor refineConstructor(TypeConstructor constructor, KotlinTypeRefiner kotlinTypeRefiner, List<? extends TypeProjection> arguments) {
        ClassifierDescriptor classifierDescriptorRefineDescriptor;
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = constructor.getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null || (classifierDescriptorRefineDescriptor = kotlinTypeRefiner.refineDescriptor(classifierDescriptorMo7434getDeclarationDescriptor)) == null) {
            return null;
        }
        if (classifierDescriptorRefineDescriptor instanceof TypeAliasDescriptor) {
            return new ExpandedTypeOrRefinedConstructor(computeExpandedType((TypeAliasDescriptor) classifierDescriptorRefineDescriptor, arguments), null);
        }
        TypeConstructor typeConstructorRefine = classifierDescriptorRefineDescriptor.getTypeConstructor().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeConstructorRefine, "refine(...)");
        return new ExpandedTypeOrRefinedConstructor(null, typeConstructorRefine);
    }

    /* JADX INFO: compiled from: KotlinTypeFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/KotlinTypeFactory$ExpandedTypeOrRefinedConstructor;", "", "expandedType", "Lorg/jetbrains/kotlin/types/SimpleType;", "refinedConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/SimpleType;Lorg/jetbrains/kotlin/types/TypeConstructor;)V", "getExpandedType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "getRefinedConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ExpandedTypeOrRefinedConstructor {
        private final SimpleType expandedType;
        private final TypeConstructor refinedConstructor;

        public ExpandedTypeOrRefinedConstructor(SimpleType simpleType, TypeConstructor typeConstructor) {
            this.expandedType = simpleType;
            this.refinedConstructor = typeConstructor;
        }

        public final SimpleType getExpandedType() {
            return this.expandedType;
        }

        public final TypeConstructor getRefinedConstructor() {
            return this.refinedConstructor;
        }
    }

    @JvmStatic
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(final TypeAttributes attributes, final TypeConstructor constructor, final List<? extends TypeProjection> arguments, final boolean nullable, final MemberScope memberScope) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments, nullable, memberScope, new Function1<KotlinTypeRefiner, SimpleType>() { // from class: org.jetbrains.kotlin.types.KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                ExpandedTypeOrRefinedConstructor expandedTypeOrRefinedConstructorRefineConstructor = KotlinTypeFactory.INSTANCE.refineConstructor(constructor, kotlinTypeRefiner, arguments);
                if (expandedTypeOrRefinedConstructorRefineConstructor == null) {
                    return null;
                }
                SimpleType expandedType = expandedTypeOrRefinedConstructorRefineConstructor.getExpandedType();
                if (expandedType != null) {
                    return expandedType;
                }
                TypeAttributes typeAttributes = attributes;
                TypeConstructor refinedConstructor = expandedTypeOrRefinedConstructorRefineConstructor.getRefinedConstructor();
                Intrinsics.checkNotNull(refinedConstructor);
                return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(typeAttributes, refinedConstructor, arguments, nullable, memberScope);
            }
        });
        if (attributes.isEmpty()) {
            return simpleTypeImpl;
        }
        return new SimpleTypeWithAttributes(simpleTypeImpl, attributes);
    }

    @JvmStatic
    public static final SimpleType simpleTypeWithNonTrivialMemberScope(TypeAttributes attributes, TypeConstructor constructor, List<? extends TypeProjection> arguments, boolean nullable, MemberScope memberScope, Function1<? super KotlinTypeRefiner, ? extends SimpleType> refinedTypeFactory) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(refinedTypeFactory, "refinedTypeFactory");
        SimpleTypeImpl simpleTypeImpl = new SimpleTypeImpl(constructor, arguments, nullable, memberScope, refinedTypeFactory);
        if (attributes.isEmpty()) {
            return simpleTypeImpl;
        }
        return new SimpleTypeWithAttributes(simpleTypeImpl, attributes);
    }

    @JvmStatic
    public static final SimpleType simpleNotNullType(TypeAttributes attributes, ClassDescriptor descriptor, List<? extends TypeProjection> arguments) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        TypeConstructor typeConstructor = descriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        return simpleType$default(attributes, typeConstructor, (List) arguments, false, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    public static /* synthetic */ SimpleType simpleType$default(SimpleType simpleType, TypeAttributes typeAttributes, TypeConstructor typeConstructor, List list, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            typeAttributes = simpleType.getAttributes();
        }
        if ((i & 4) != 0) {
            typeConstructor = simpleType.getConstructor();
        }
        if ((i & 8) != 0) {
            list = simpleType.getArguments();
        }
        if ((i & 16) != 0) {
            z = simpleType.getIsMarkedNullable();
        }
        return simpleType(simpleType, typeAttributes, typeConstructor, (List<? extends TypeProjection>) list, z);
    }

    @JvmStatic
    public static final SimpleType simpleType(SimpleType baseType, TypeAttributes annotations, TypeConstructor constructor, List<? extends TypeProjection> arguments, boolean nullable) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return simpleType$default(annotations, constructor, arguments, nullable, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    @JvmStatic
    public static final UnwrappedType flexibleType(SimpleType lowerBound, SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return Intrinsics.areEqual(lowerBound, upperBound) ? lowerBound : new FlexibleTypeImpl(lowerBound, upperBound);
    }

    @JvmStatic
    public static final SimpleType integerLiteralType(TypeAttributes attributes, IntegerLiteralTypeConstructor constructor, boolean nullable) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return simpleTypeWithNonTrivialMemberScope(attributes, constructor, CollectionsKt.emptyList(), nullable, org.jetbrains.kotlin.types.error.ErrorUtils.createErrorScope(ErrorScopeKind.INTEGER_LITERAL_TYPE_SCOPE, true, "unknown integer literal type"));
    }
}
