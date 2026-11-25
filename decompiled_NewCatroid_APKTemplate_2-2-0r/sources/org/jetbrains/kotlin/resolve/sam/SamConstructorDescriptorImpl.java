package org.jetbrains.kotlin.resolve.sam;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;

/* JADX INFO: compiled from: SamConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "samInterface", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "baseDescriptorForSynthetic", "getBaseDescriptorForSynthetic", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getSingleAbstractMethod", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamConstructorDescriptorImpl extends SimpleFunctionDescriptorImpl implements SamConstructorDescriptor {
    private final ClassDescriptor samInterface;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamConstructorDescriptorImpl(DeclarationDescriptor containingDeclaration, ClassDescriptor samInterface) {
        super(containingDeclaration, null, samInterface.getAnnotations(), samInterface.getShortName(), CallableMemberDescriptor.Kind.SYNTHESIZED, samInterface.getSourceElement());
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(samInterface, "samInterface");
        this.samInterface = samInterface;
    }

    @Override // org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor
    /* JADX INFO: renamed from: getBaseDescriptorForSynthetic, reason: from getter */
    public ClassDescriptor getSamInterface() {
        return this.samInterface;
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor
    public CallableMemberDescriptor getSingleAbstractMethod() {
        return (CallableMemberDescriptor) CollectionsKt.single((List) SamConversionResolverImplKt.getAbstractMembers(this.samInterface));
    }
}
