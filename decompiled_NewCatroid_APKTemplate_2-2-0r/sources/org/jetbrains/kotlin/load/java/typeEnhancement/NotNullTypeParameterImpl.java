package org.jetbrains.kotlin.load.java.typeEnhancement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.types.DelegatingSimpleType;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.NotNullTypeParameter;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\tH\u0016J\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H\u0017J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\f\u0010\u0015\u001a\u00020\u0004*\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/NotNullTypeParameterImpl;", "Lorg/jetbrains/kotlin/types/NotNullTypeParameter;", "Lorg/jetbrains/kotlin/types/DelegatingSimpleType;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/types/SimpleType;", "(Lorg/jetbrains/kotlin/types/SimpleType;)V", "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "isMarkedNullable", "", "()Z", "isTypeParameter", "makeNullableAsSpecified", "newNullability", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "replaceDelegate", "substitutionResult", "Lorg/jetbrains/kotlin/types/KotlinType;", "replacement", "prepareReplacement", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotNullTypeParameterImpl extends DelegatingSimpleType implements NotNullTypeParameter {
    private final SimpleType delegate;

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType, org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable */
    public boolean getIsMarkedNullable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.CustomTypeParameter
    public boolean isTypeParameter() {
        return true;
    }

    public NotNullTypeParameterImpl(SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.types.CustomTypeParameter
    public KotlinType substitutionResult(KotlinType replacement) {
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        UnwrappedType unwrappedTypeUnwrap = replacement.unwrap();
        UnwrappedType unwrappedType = unwrappedTypeUnwrap;
        if (!TypeUtilsKt.isTypeParameter(unwrappedType) && !TypeUtils.isNullableType(unwrappedType)) {
            return unwrappedType;
        }
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return prepareReplacement((SimpleType) unwrappedTypeUnwrap);
        }
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            return TypeWithEnhancementKt.wrapEnhancement(KotlinTypeFactory.flexibleType(prepareReplacement(flexibleType.getLowerBound()), prepareReplacement(flexibleType.getUpperBound())), TypeWithEnhancementKt.getEnhancement(unwrappedType));
        }
        throw new IllegalStateException(("Incorrect type: " + unwrappedTypeUnwrap).toString());
    }

    private final SimpleType prepareReplacement(SimpleType simpleType) {
        SimpleType simpleTypeMakeNullableAsSpecified = simpleType.makeNullableAsSpecified(false);
        return !TypeUtilsKt.isTypeParameter(simpleType) ? simpleTypeMakeNullableAsSpecified : new NotNullTypeParameterImpl(simpleTypeMakeNullableAsSpecified);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public NotNullTypeParameterImpl replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new NotNullTypeParameterImpl(getDelegate().replaceAttributes(newAttributes));
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability ? getDelegate().makeNullableAsSpecified(true) : this;
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType
    public NotNullTypeParameterImpl replaceDelegate(SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new NotNullTypeParameterImpl(delegate);
    }
}
