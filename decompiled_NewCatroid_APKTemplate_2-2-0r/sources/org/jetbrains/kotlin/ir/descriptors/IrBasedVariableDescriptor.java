package org.jetbrains.kotlin.ir.descriptors;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J7\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b\u0018\u00010\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\u00020\u000e2\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nH\u0016J\b\u0010\u0010\u001a\u00020\u000eH\u0016J\u000e\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0000H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\b\u0010 \u001a\u00020\u001eH\u0016J\u0010\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020#H\u0016¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedVariableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedCallableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "isConst", "", "isLateInit", "isVar", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedVariableDescriptor extends IrBasedCallableDescriptor<IrVariable> implements VariableDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedVariableDescriptor getOriginal() {
        return this;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedVariableDescriptor(IrVariable owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        IrDeclarationParent parent = ((IrVariable) getOwner()).getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return IrBasedDescriptorsKt.toIrBasedDescriptor((IrDeclaration) parent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        return toIrBasedKotlinType(((IrVariable) getOwner()).getType());
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return getType();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return ((IrVariable) getOwner()).getName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return ((IrVariable) getOwner()).getIsConst();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return ((IrVariable) getOwner()).getIsVar();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return ((IrVariable) getOwner()).getIsLateinit();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        throw new NotImplementedError("An operation is not implemented: ");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<VariableDescriptor> getOverriddenDescriptors() {
        throw new NotImplementedError("An operation is not implemented: Not Implemented");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public VariableDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new NotImplementedError("An operation is not implemented: ");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        return visitor.visitVariableDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitVariableDescriptor(this, null);
    }
}
