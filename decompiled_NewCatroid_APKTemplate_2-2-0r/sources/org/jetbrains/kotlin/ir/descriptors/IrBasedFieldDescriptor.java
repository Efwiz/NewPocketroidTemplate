package org.jetbrains.kotlin.ir.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J?\u0010\u0011\u001a\n \u0013*\u0004\u0018\u0001H\u0012H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u0018J\u001e\u0010\u0019\u001a\u00020\u001a2\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u0016H\u0016J\b\u0010\u001c\u001a\u00020\u001aH\u0016J8\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020\u000bH\u0016J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)H\u0016J\n\u0010+\u001a\u0004\u0018\u00010,H\u0016J\u000e\u0010-\u001a\b\u0012\u0002\b\u0003\u0018\u00010.H\u0016J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020100H\u0016J\n\u00102\u001a\u0004\u0018\u00010,H\u0016J\n\u00103\u001a\u0004\u0018\u000101H\u0016J\n\u00104\u001a\u0004\u0018\u000101H\u0016J\n\u00105\u001a\u0004\u0018\u000106H\u0016J\b\u00107\u001a\u00020&H\u0016J\b\u00108\u001a\u00020\"H\u0016J\b\u00109\u001a\u00020:H\u0016J\b\u0010;\u001a\u00020\u0000H\u0016J\u0010\u0010<\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010=H\u0016J\b\u0010>\u001a\u000206H\u0016J\u0010\u0010?\u001a\n \u0013*\u0004\u0018\u00010@0@H\u0016J\b\u0010A\u001a\u000206H\u0016J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020C00H\u0016J%\u0010D\u001a\u0004\u0018\u0001HE\"\u0004\b\u0000\u0010E2\u000e\u0010F\u001a\n\u0012\u0004\u0012\u0002HE\u0018\u00010GH\u0016¢\u0006\u0002\u0010HJ\u000e\u0010I\u001a\b\u0012\u0004\u0012\u00020J0)H\u0016J\b\u0010K\u001a\u00020$H\u0016J\b\u0010L\u001a\u00020\u000bH\u0016J\b\u0010M\u001a\u00020\u000bH\u0016J\b\u0010N\u001a\u00020\u000bH\u0016J\b\u0010O\u001a\u00020\u000bH\u0016J\b\u0010P\u001a\u00020\u000bH\u0016J\b\u0010Q\u001a\u00020\u000bH\u0016J\b\u0010R\u001a\u00020\u000bH\u0016J\b\u0010S\u001a\u00020\u000bH\u0016J\b\u0010T\u001a\u00020\u000bH\u0016J\u0010\u0010U\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010VH\u0016J\u0018\u0010W\u001a\u00020\u001a2\u000e\u0010X\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001e0=H\u0016J\u0010\u0010Y\u001a\u00020\u00012\u0006\u0010Z\u001a\u00020[H\u0016R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedFieldDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)V", "getter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "getGetter", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "isDelegated", "", "()Z", "setter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "getSetter", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "getAccessors", "", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "getBackingField", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContextReceiverParameters", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDelegateField", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "getReturnType", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getType", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isConst", "isExpect", "isExternal", "isLateInit", "isSetterProjectedOut", "isVar", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$CopyBuilder;", "setOverriddenDescriptors", "overriddenDescriptors", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedFieldDescriptor extends IrBasedDeclarationDescriptor<IrField> implements PropertyDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public FieldDescriptor getBackingField() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public FieldDescriptor getDelegateField() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public PropertyGetterDescriptor getGetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedFieldDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public PropertySetterDescriptor getSetter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasSynthesizedParameterNames() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return false;
    }

    public boolean isDelegated() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExpect() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedFieldDescriptor(IrField owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return getOwner().getIsFinal() ? Modality.FINAL : Modality.OPEN;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    /* JADX INFO: renamed from: setOverriddenDescriptors */
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        Intrinsics.checkNotNullParameter(overriddenDescriptors, "overriddenDescriptors");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.Kind getKind() {
        return CallableMemberDescriptor.Kind.SYNTHESIZED;
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return getOwner().getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        return SourceElement.NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<? extends PropertyDescriptor> getOverriddenDescriptors() {
        return new ArrayList();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        return new ArrayList();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public boolean isSetterProjectedOut() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public List<PropertyAccessorDescriptor> getAccessors() {
        return new ArrayList();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return getOwner().getVisibility();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public PropertyDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("IrBased descriptors SHOULD NOT be substituted");
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return toIrBasedKotlinType(getOwner().getType());
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasStableParameterNames() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        return toIrBasedKotlinType(getOwner().getType());
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return !getOwner().getIsFinal();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        if (getOwner().getIsStatic()) {
            return null;
        }
        IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(getOwner()).getThisReceiver();
        CallableDescriptor irBasedDescriptor = thisReceiver != null ? IrBasedDescriptorsKt.toIrBasedDescriptor(thisReceiver) : null;
        Intrinsics.checkNotNull(irBasedDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor");
        return (ReceiverParameterDescriptor) irBasedDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        IrProperty owner;
        IrPropertySymbol correspondingPropertySymbol = getOwner().getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return false;
        }
        return owner.getIsLateinit();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        IrProperty owner;
        IrBasedPropertyDescriptor irBasedDescriptor;
        IrPropertySymbol correspondingPropertySymbol = getOwner().getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || (irBasedDescriptor = IrBasedDescriptorsKt.toIrBasedDescriptor(owner)) == null) {
            return null;
        }
        return irBasedDescriptor.getExtensionReceiverParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ReceiverParameterDescriptor> getContextReceiverParameters() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return getOwner().getIsExternal();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        return visitor.visitPropertyDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitPropertyDescriptor(this, null);
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.CopyBuilder<? extends PropertyDescriptor> newCopyBuilder() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public KotlinType getInType() {
        List<ValueParameterDescriptor> valueParameters;
        ValueParameterDescriptor valueParameterDescriptor;
        PropertySetterDescriptor setter = getSetter();
        if (setter == null || (valueParameters = setter.getValueParameters()) == null || (valueParameterDescriptor = valueParameters.get(0)) == null) {
            return null;
        }
        return valueParameterDescriptor.getType();
    }
}
