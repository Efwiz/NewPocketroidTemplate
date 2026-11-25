package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: compiled from: KotlinTypeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/DelegatingSimpleTypeImpl;", "Lorg/jetbrains/kotlin/types/DelegatingSimpleType;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/types/SimpleType;", "(Lorg/jetbrains/kotlin/types/SimpleType;)V", "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "makeNullableAsSpecified", "newNullability", "", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DelegatingSimpleTypeImpl extends DelegatingSimpleType {
    private final SimpleType delegate;

    public DelegatingSimpleTypeImpl(SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType
    protected SimpleType getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        if (newAttributes != getAttributes()) {
            return new SimpleTypeWithAttributes(this, newAttributes);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability == getIsMarkedNullable() ? this : getDelegate().makeNullableAsSpecified(newNullability).replaceAttributes(getAttributes());
    }
}
