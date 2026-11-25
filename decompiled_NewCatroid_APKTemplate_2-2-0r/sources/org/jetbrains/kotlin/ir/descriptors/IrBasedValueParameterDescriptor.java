package org.jetbrains.kotlin.ir.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J<\u0010\u0012\u001a\u0004\b\u0002H\u0013\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0018J\u001e\u0010\u0019\u001a\u00020\u001a2\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u0016H\u0016J\b\u0010\u001c\u001a\u00020\u001aH\u0016J \u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0007H\u0016J\b\u0010$\u001a\u00020\u000bH\u0016J\u000e\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010&H\u0016J\b\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020\"H\u0016J\b\u0010*\u001a\u00020\u0000H\u0016J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00010,H\u0016J\n\u0010-\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010.\u001a\u00020\u000fH\u0016J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020100H\u0016J\u000e\u00102\u001a\b\u0012\u0004\u0012\u00020\u000100H\u0016J\b\u00103\u001a\u00020\u000bH\u0016J\b\u00104\u001a\u00020\u000bH\u0016J\u0010\u00105\u001a\u00020\u00012\u0006\u00106\u001a\u000207H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0014\u0010\r\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u0004\n\u0002\b9¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedCallableDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "index", "", "getIndex", "()I", "isCrossinline", "", "()Z", "isNoinline", "varargElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getVarargElementType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "", "newOwner", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "newName", "Lorg/jetbrains/kotlin/name/Name;", "newIndex", "declaresDefaultValue", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getName", "getOriginal", "getOverriddenDescriptors", "", "getReturnType", "getType", "getTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getValueParameters", "isConst", "isVar", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedValueParameterDescriptor extends IrBasedCallableDescriptor<IrValueParameter> implements ValueParameterDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedValueParameterDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: copy, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ValueParameterDescriptor mo7204copy(CallableDescriptor callableDescriptor, Name name2, int i) {
        return (ValueParameterDescriptor) copy(callableDescriptor, name2, i);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return ValueParameterDescriptor.DefaultImpls.isLateInit(this);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedValueParameterDescriptor(IrValueParameter owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public int getIndex() {
        return ((IrValueParameter) getOwner()).getIndex();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: isCrossinline */
    public boolean getIsCrossinline() {
        return ((IrValueParameter) getOwner()).getIsCrossinline();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: isNoinline */
    public boolean getIsNoinline() {
        return ((IrValueParameter) getOwner()).getIsNoinline();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public KotlinType getVarargElementType() {
        IrType varargElementType = ((IrValueParameter) getOwner()).getVarargElementType();
        if (varargElementType != null) {
            return toIrBasedKotlinType(varargElementType);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public FunctionDescriptor getDeclarationDescriptor() {
        IrDeclarationParent parent = ((IrValueParameter) getOwner()).getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
        return IrBasedDescriptorsKt.toIrBasedDescriptor((IrFunction) parent);
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public boolean declaresDefaultValue() {
        return ((IrValueParameter) getOwner()).getDefaultValue() != null;
    }

    public Void copy(CallableDescriptor newOwner, Name newName, int newIndex) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(newName, "newName");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<ValueParameterDescriptor> getOverriddenDescriptors() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public ValueParameterDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("IrBased descriptors SHOULD NOT be substituted");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return toIrBasedKotlinType(((IrValueParameter) getOwner()).getType());
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        R rVisitValueParameterDescriptor = visitor.visitValueParameterDescriptor(this, data);
        Intrinsics.checkNotNull(rVisitValueParameterDescriptor);
        return rVisitValueParameterDescriptor;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitValueParameterDescriptor(this, null);
    }
}
