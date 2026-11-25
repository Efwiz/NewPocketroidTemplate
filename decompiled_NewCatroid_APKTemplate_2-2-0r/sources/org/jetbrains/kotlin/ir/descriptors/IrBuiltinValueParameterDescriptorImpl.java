package org.jetbrains.kotlin.ir.descriptors;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBuiltinFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ5\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u0016\"\u0004\b\u0001\u0010\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001a\u001a\u0002H\u0017H\u0016¢\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J \u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\bH\u0016J\b\u0010#\u001a\u00020\u000fH\u0016J\u0013\u0010$\u001a\u00020\u000f2\b\u0010%\u001a\u0004\u0018\u00010&H\u0096\u0002J\u000e\u0010'\u001a\b\u0012\u0002\b\u0003\u0018\u00010(H\u0016J\b\u0010)\u001a\u00020\u0004H\u0016J\b\u0010*\u001a\u00020\u001fH\u0016J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001f0,H\u0016J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020\bH\u0016J\b\u00100\u001a\u00020\u000fH\u0016J\u0010\u00101\u001a\u00020\u001f2\u0006\u00102\u001a\u000203H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinValueParameterDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/impl/VariableDescriptorImpl;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinValueParameterDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "index", "", "outType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/name/Name;ILorg/jetbrains/kotlin/types/KotlinType;)V", "getIndex", "()I", "isCrossinline", "", "()Z", "isNoinline", "varargElementType", "getVarargElementType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "cleanCompileTimeInitializerCache", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "newOwner", "newName", "newIndex", "declaresDefaultValue", Namer.EQUALS_METHOD_NAME, "other", "", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "getOriginal", "getOverriddenDescriptors", "", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "hashCode", "isVar", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBuiltinValueParameterDescriptorImpl extends VariableDescriptorImpl implements IrBuiltinValueParameterDescriptor {
    private final CallableDescriptor containingDeclaration;
    private final int index;

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public boolean declaresDefaultValue() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public KotlinType getVarargElementType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: isCrossinline */
    public boolean getIsCrossinline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: isNoinline */
    public boolean getIsNoinline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return IrBuiltinValueParameterDescriptor.DefaultImpls.isLateInit(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    public int getIndex() {
        return this.index;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBuiltinValueParameterDescriptorImpl(CallableDescriptor containingDeclaration, Name name2, int i, KotlinType outType) {
        super(containingDeclaration, Annotations.INSTANCE.getEMPTY(), name2, outType, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(outType, "outType");
        this.containingDeclaration = containingDeclaration;
        this.index = i;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public CallableDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public ValueParameterDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<ValueParameterDescriptor> getOverriddenDescriptors() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        return LOCAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
    /* JADX INFO: renamed from: copy */
    public ValueParameterDescriptor mo7204copy(CallableDescriptor newOwner, Name newName, int newIndex) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(newName, "newName");
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public ValueParameterDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitValueParameterDescriptor(this, data);
    }

    public boolean equals(Object other) {
        if (this != other) {
            if (other instanceof IrBuiltinValueParameterDescriptorImpl) {
                IrBuiltinValueParameterDescriptorImpl irBuiltinValueParameterDescriptorImpl = (IrBuiltinValueParameterDescriptorImpl) other;
                if (!Intrinsics.areEqual(getShortName(), irBuiltinValueParameterDescriptorImpl.getShortName()) || getIndex() != irBuiltinValueParameterDescriptorImpl.getIndex() || !Intrinsics.areEqual(getType(), irBuiltinValueParameterDescriptorImpl.getType()) || !Intrinsics.areEqual(this.containingDeclaration, irBuiltinValueParameterDescriptorImpl.containingDeclaration)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((getShortName().hashCode() * 31) + getIndex()) * 31) + getType().hashCode();
    }
}
