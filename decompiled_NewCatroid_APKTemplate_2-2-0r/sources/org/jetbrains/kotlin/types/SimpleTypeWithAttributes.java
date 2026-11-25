package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: compiled from: KotlinTypeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0003H\u0017R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/types/SimpleTypeWithAttributes;", "Lorg/jetbrains/kotlin/types/DelegatingSimpleTypeImpl;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/types/SimpleType;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "(Lorg/jetbrains/kotlin/types/SimpleType;Lorg/jetbrains/kotlin/types/TypeAttributes;)V", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "replaceDelegate", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SimpleTypeWithAttributes extends DelegatingSimpleTypeImpl {
    private final TypeAttributes attributes;

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType, org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return this.attributes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleTypeWithAttributes(SimpleType delegate, TypeAttributes attributes) {
        super(delegate);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.attributes = attributes;
    }

    @Override // org.jetbrains.kotlin.types.DelegatingSimpleType
    public SimpleTypeWithAttributes replaceDelegate(SimpleType delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        return new SimpleTypeWithAttributes(delegate, getAttributes());
    }
}
