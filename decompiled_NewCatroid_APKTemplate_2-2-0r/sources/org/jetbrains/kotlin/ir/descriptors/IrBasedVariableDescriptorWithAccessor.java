package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000bH\u0016J\b\u0010\u001a\u001a\u00020\u000bH\u0016J\b\u0010\u001b\u001a\u00020\u000bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\t¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedVariableDescriptorWithAccessor;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedCallableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;)V", "getter", "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "getGetter", "()Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "isDelegated", "", "()Z", "setter", "getSetter", "cleanCompileTimeInitializerCache", "", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isConst", "isLateInit", "isVar", "substitute", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedVariableDescriptorWithAccessor extends IrBasedCallableDescriptor<IrLocalDelegatedProperty> implements VariableDescriptorWithAccessors {
    private final boolean isDelegated;

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedVariableDescriptorWithAccessor(IrLocalDelegatedProperty owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.isDelegated = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return ((IrLocalDelegatedProperty) getOwner()).getName();
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public VariableDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return ((IrLocalDelegatedProperty) getOwner()).getIsVar();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        return toIrBasedKotlinType(((IrLocalDelegatedProperty) getOwner()).getType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        IrDeclarationParent parent = ((IrLocalDelegatedProperty) getOwner()).getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        return IrBasedDescriptorsKt.toIrBasedDescriptor((IrDeclaration) parent);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public VariableAccessorDescriptor getGetter() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public VariableAccessorDescriptor getSetter() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    /* JADX INFO: renamed from: isDelegated, reason: from getter */
    public boolean getIsDelegated() {
        return this.isDelegated;
    }
}
