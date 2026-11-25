package org.jetbrains.kotlin.ir.descriptors;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J<\u0010\u0006\u001a\u0004\b\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b\u0018\u00010\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\u00020\u000e2\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0000H\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!H\u0016\u0082\u0002\u0004\n\u0002\b9¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedReceiverParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedCallableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "getValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedReceiverParameterDescriptor extends IrBasedCallableDescriptor<IrValueParameter> implements ReceiverParameterDescriptor {
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedReceiverParameterDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor
    /* JADX INFO: renamed from: copy, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ReceiverParameterDescriptor mo7203copy(DeclarationDescriptor declarationDescriptor) {
        return (ReceiverParameterDescriptor) copy(declarationDescriptor);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedReceiverParameterDescriptor(IrValueParameter owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor
    public ReceiverValue getValue() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        return toIrBasedKotlinType(((IrValueParameter) getOwner()).getType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return ((IrValueParameter) getOwner()).getName();
    }

    public Void copy(DeclarationDescriptor newOwner) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<ValueParameterDescriptor> getOverriddenDescriptors() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public ReceiverParameterDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new NotImplementedError("An operation is not implemented: ");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return toIrBasedKotlinType(((IrValueParameter) getOwner()).getType());
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        R rVisitReceiverParameterDescriptor = visitor.visitReceiverParameterDescriptor(this, data);
        Intrinsics.checkNotNull(rVisitReceiverParameterDescriptor);
        return rVisitReceiverParameterDescriptor;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitReceiverParameterDescriptor(this, null);
    }
}
