package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.types.checker.NewCapturedType;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.checker.NullabilityChecker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;

/* JADX INFO: compiled from: SpecialTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u001bB\u0017\b\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005H\u0017J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0014\u0010\t\u001a\u00020\u00058TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/types/DefinitelyNotNullType;", "Lorg/jetbrains/kotlin/types/DelegatingSimpleType;", "Lorg/jetbrains/kotlin/types/CustomTypeParameter;", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "original", "Lorg/jetbrains/kotlin/types/SimpleType;", "useCorrectedNullabilityForTypeParameters", "", "(Lorg/jetbrains/kotlin/types/SimpleType;Z)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "isMarkedNullable", "()Z", "isTypeParameter", "getOriginal", "makeNullableAsSpecified", "newNullability", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "replaceDelegate", "substitutionResult", "Lorg/jetbrains/kotlin/types/KotlinType;", "replacement", "toString", "", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefinitelyNotNullType extends DelegatingSimpleType implements CustomTypeParameter, DefinitelyNotNullTypeMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final SimpleType original;
    private final boolean useCorrectedNullabilityForTypeParameters;

    public /* synthetic */ DefinitelyNotNullType(SimpleType simpleType, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(simpleType, z);
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType, org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable */
    public boolean getIsMarkedNullable() {
        return false;
    }

    public final SimpleType getOriginal() {
        return this.original;
    }

    private DefinitelyNotNullType(SimpleType simpleType, boolean z) {
        this.original = simpleType;
        this.useCorrectedNullabilityForTypeParameters = z;
    }

    /* JADX INFO: compiled from: SpecialTypes.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u0007J\u0018\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\bH\u0002J\f\u0010\f\u001a\u00020\b*\u00020\u0006H\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/DefinitelyNotNullType$Companion;", "", "()V", "makeDefinitelyNotNull", "Lorg/jetbrains/kotlin/types/DefinitelyNotNullType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/UnwrappedType;", "useCorrectedNullabilityForTypeParameters", "", "avoidCheckingActualTypeNullability", "makesSenseToBeDefinitelyNotNull", "useCorrectedNullabilityForFlexibleTypeParameters", "canHaveUndefinedNullability", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final DefinitelyNotNullType makeDefinitelyNotNull(UnwrappedType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return makeDefinitelyNotNull$default(this, type, false, false, 6, null);
        }

        public final DefinitelyNotNullType makeDefinitelyNotNull(UnwrappedType type, boolean z) {
            Intrinsics.checkNotNullParameter(type, "type");
            return makeDefinitelyNotNull$default(this, type, z, false, 4, null);
        }

        private Companion() {
        }

        public static /* synthetic */ DefinitelyNotNullType makeDefinitelyNotNull$default(Companion companion, UnwrappedType unwrappedType, boolean z, boolean z2, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            if ((i & 4) != 0) {
                z2 = false;
            }
            return companion.makeDefinitelyNotNull(unwrappedType, z, z2);
        }

        public final DefinitelyNotNullType makeDefinitelyNotNull(UnwrappedType type, boolean useCorrectedNullabilityForTypeParameters, boolean avoidCheckingActualTypeNullability) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (type instanceof DefinitelyNotNullType) {
                return (DefinitelyNotNullType) type;
            }
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (!avoidCheckingActualTypeNullability && !makesSenseToBeDefinitelyNotNull(type, useCorrectedNullabilityForTypeParameters)) {
                return null;
            }
            if (type instanceof FlexibleType) {
                FlexibleType flexibleType = (FlexibleType) type;
                Intrinsics.areEqual(flexibleType.getLowerBound().getConstructor(), flexibleType.getUpperBound().getConstructor());
            }
            return new DefinitelyNotNullType(FlexibleTypesKt.lowerIfFlexible(type).makeNullableAsSpecified(false), useCorrectedNullabilityForTypeParameters, defaultConstructorMarker);
        }

        private final boolean makesSenseToBeDefinitelyNotNull(UnwrappedType type, boolean useCorrectedNullabilityForFlexibleTypeParameters) {
            if (!canHaveUndefinedNullability(type)) {
                return false;
            }
            if (type instanceof StubTypeForBuilderInference) {
                return TypeUtils.isNullableType(type);
            }
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            TypeParameterDescriptorImpl typeParameterDescriptorImpl = classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptorImpl ? (TypeParameterDescriptorImpl) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (typeParameterDescriptorImpl != null && !typeParameterDescriptorImpl.isInitialized()) {
                return true;
            }
            if (useCorrectedNullabilityForFlexibleTypeParameters && (type.getConstructor().getThis$0() instanceof TypeParameterDescriptor)) {
                return TypeUtils.isNullableType(type);
            }
            return !NullabilityChecker.INSTANCE.isSubtypeOfAny(type);
        }

        private final boolean canHaveUndefinedNullability(UnwrappedType unwrappedType) {
            return (unwrappedType.getConstructor() instanceof NewTypeVariableConstructor) || (unwrappedType.getConstructor().getThis$0() instanceof TypeParameterDescriptor) || (unwrappedType instanceof NewCapturedType) || (unwrappedType instanceof StubTypeForBuilderInference);
        }
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType
    protected SimpleType getDelegate() {
        return this.original;
    }

    @Override // org.jetbrains.kotlin.types.CustomTypeParameter
    public boolean isTypeParameter() {
        return (getDelegate().getConstructor() instanceof NewTypeVariableConstructor) || (getDelegate().getConstructor().getThis$0() instanceof TypeParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.types.CustomTypeParameter
    public KotlinType substitutionResult(KotlinType replacement) {
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return SpecialTypesKt.makeDefinitelyNotNullOrNotNull(replacement.unwrap(), this.useCorrectedNullabilityForTypeParameters);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new DefinitelyNotNullType(getDelegate().replaceAttributes(newAttributes), this.useCorrectedNullabilityForTypeParameters);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability ? getDelegate().makeNullableAsSpecified(newNullability) : this;
    }

    @Override // org.jetbrains.kotlin.types.SimpleType
    public String toString() {
        return getDelegate() + " & Any";
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType
    public DefinitelyNotNullType replaceDelegate(SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new DefinitelyNotNullType(delegate, this.useCorrectedNullabilityForTypeParameters);
    }
}
