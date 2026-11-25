package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;

/* JADX INFO: compiled from: SamTypeAliasConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamTypeAliasConstructorDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "Lorg/jetbrains/kotlin/resolve/sam/SamTypeAliasConstructorDescriptor;", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "expandedConstructorDescriptor", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;)V", "baseDescriptorForSynthetic", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getBaseDescriptorForSynthetic", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getExpandedConstructorDescriptor", "()Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "getTypeAliasDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getSingleAbstractMethod", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamTypeAliasConstructorDescriptorImpl extends SimpleFunctionDescriptorImpl implements SamTypeAliasConstructorDescriptor {
    private final SamConstructorDescriptor expandedConstructorDescriptor;
    private final TypeAliasDescriptor typeAliasDescriptor;

    @Override // org.jetbrains.kotlin.resolve.sam.SamTypeAliasConstructorDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithNavigationSubstitute
    public DeclarationDescriptor getSubstitute() {
        return SamTypeAliasConstructorDescriptor.DefaultImpls.getSubstitute(this);
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamTypeAliasConstructorDescriptor
    public TypeAliasDescriptor getTypeAliasDescriptor() {
        return this.typeAliasDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamTypeAliasConstructorDescriptor
    public SamConstructorDescriptor getExpandedConstructorDescriptor() {
        return this.expandedConstructorDescriptor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamTypeAliasConstructorDescriptorImpl(TypeAliasDescriptor typeAliasDescriptor, SamConstructorDescriptor expandedConstructorDescriptor) {
        super(typeAliasDescriptor.getPackageFragment(), null, expandedConstructorDescriptor.getBaseDescriptorForSynthetic().getAnnotations(), typeAliasDescriptor.getShortName(), CallableMemberDescriptor.Kind.SYNTHESIZED, typeAliasDescriptor.getSourceElement());
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        Intrinsics.checkNotNullParameter(expandedConstructorDescriptor, "expandedConstructorDescriptor");
        this.typeAliasDescriptor = typeAliasDescriptor;
        this.expandedConstructorDescriptor = expandedConstructorDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor
    public CallableMemberDescriptor getSingleAbstractMethod() {
        return getExpandedConstructorDescriptor().getSingleAbstractMethod();
    }

    @Override // org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor
    public ClassDescriptor getBaseDescriptorForSynthetic() {
        return getExpandedConstructorDescriptor().getBaseDescriptorForSynthetic();
    }
}
