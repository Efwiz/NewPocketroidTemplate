package org.jetbrains.kotlin.resolve.calls.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.impl.DescriptorDerivedFromTypeAlias;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FakeCallableDescriptorForTypeAliasObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\u0004H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/util/FakeCallableDescriptorForTypeAliasObject;", "Lorg/jetbrains/kotlin/resolve/calls/util/FakeCallableDescriptorForObject;", "Lorg/jetbrains/kotlin/descriptors/impl/DescriptorDerivedFromTypeAlias;", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;)V", "getTypeAliasDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getReferencedDescriptor", "hashCode", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeCallableDescriptorForTypeAliasObject extends FakeCallableDescriptorForObject implements DescriptorDerivedFromTypeAlias {
    private final TypeAliasDescriptor typeAliasDescriptor;

    @Override // org.jetbrains.kotlin.descriptors.impl.DescriptorDerivedFromTypeAlias
    public TypeAliasDescriptor getTypeAliasDescriptor() {
        return this.typeAliasDescriptor;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public FakeCallableDescriptorForTypeAliasObject(TypeAliasDescriptor typeAliasDescriptor) {
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        ClassDescriptor classDescriptor = typeAliasDescriptor.getClassDescriptor();
        Intrinsics.checkNotNull(classDescriptor);
        super(classDescriptor);
        this.typeAliasDescriptor = typeAliasDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject
    public TypeAliasDescriptor getReferencedDescriptor() {
        return getTypeAliasDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject
    public boolean equals(Object other) {
        return (other instanceof FakeCallableDescriptorForTypeAliasObject) && Intrinsics.areEqual(getTypeAliasDescriptor(), ((FakeCallableDescriptorForTypeAliasObject) other).getTypeAliasDescriptor());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject
    public int hashCode() {
        return getTypeAliasDescriptor().hashCode();
    }
}
