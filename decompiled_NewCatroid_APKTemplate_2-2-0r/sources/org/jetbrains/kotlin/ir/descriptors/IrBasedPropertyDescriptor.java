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
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ô\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J?\u0010\u0016\u001a\n \u0018*\u0004\u0018\u0001H\u0017H\u0017\"\u0004\b\u0000\u0010\u0017\"\u0004\b\u0001\u0010\u00192\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u0019\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\u001dJ\u001e\u0010\u001e\u001a\u00020\u001f2\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 \u0018\u00010\u001bH\u0016J\b\u0010!\u001a\u00020\u001fH\u0016J8\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020\u0010H\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.H\u0016J\n\u00100\u001a\u0004\u0018\u000101H\u0016J\u000e\u00102\u001a\b\u0012\u0002\b\u0003\u0018\u000103H\u0016J\u000e\u00104\u001a\b\u0012\u0004\u0012\u0002050.H\u0016J\n\u00106\u001a\u0004\u0018\u000101H\u0016J\n\u00107\u001a\u0004\u0018\u000105H\u0016J\n\u00108\u001a\u0004\u0018\u000105H\u0016J\n\u00109\u001a\u0004\u0018\u00010:H\u0016J\b\u0010;\u001a\u00020+H\u0016J\b\u0010<\u001a\u00020'H\u0016J\b\u0010=\u001a\u00020>H\u0016J\b\u0010?\u001a\u00020\u0000H\u0016J\u0010\u0010@\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010AH\u0016J\b\u0010B\u001a\u00020:H\u0016J\u0010\u0010C\u001a\n \u0018*\u0004\u0018\u00010D0DH\u0016J\b\u0010E\u001a\u00020:H\u0016J\u000e\u0010F\u001a\b\u0012\u0004\u0012\u00020G0.H\u0016J%\u0010H\u001a\u0004\u0018\u0001HI\"\u0004\b\u0000\u0010I2\u000e\u0010J\u001a\n\u0012\u0004\u0012\u0002HI\u0018\u00010KH\u0016¢\u0006\u0002\u0010LJ\u000e\u0010M\u001a\b\u0012\u0004\u0012\u00020O0NH\u0016J\b\u0010P\u001a\u00020)H\u0016J\b\u0010Q\u001a\u00020\u0010H\u0016J\b\u0010R\u001a\u00020\u0010H\u0016J\b\u0010S\u001a\u00020\u0010H\u0016J\b\u0010T\u001a\u00020\u0010H\u0016J\b\u0010U\u001a\u00020\u0010H\u0016J\b\u0010V\u001a\u00020\u0010H\u0016J\b\u0010W\u001a\u00020\u0010H\u0016J\b\u0010X\u001a\u00020\u0010H\u0016J\b\u0010Y\u001a\u00020\u0010H\u0016J\u0010\u0010Z\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010[H\u0016J\u0018\u0010\\\u001a\u00020\u001f2\u000e\u0010]\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0AH\u0016J\u0010\u0010^\u001a\u00020\u00012\u0006\u0010_\u001a\u00020`H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006a"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DescriptorWithContainerSource;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "getGetter", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "isDelegated", "", "()Z", "setter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "getSetter", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "getAccessors", "", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "getBackingField", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContextReceiverParameters", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDelegateField", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "getReturnType", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getType", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isConst", "isExpect", "isExternal", "isLateInit", "isSetterProjectedOut", "isVar", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$CopyBuilder;", "setOverriddenDescriptors", "overriddenDescriptors", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedPropertyDescriptor extends IrBasedDeclarationDescriptor<IrProperty> implements PropertyDescriptor, DescriptorWithContainerSource {
    private final DeserializedContainerSource containerSource;

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedPropertyDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasStableParameterNames() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedPropertyDescriptor(IrProperty owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.containerSource = owner.getContainerSource();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return getOwner().getModality();
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
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return SourceElement.NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasSynthesizedParameterNames() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
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

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public boolean isSetterProjectedOut() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public List<PropertyAccessorDescriptor> getAccessors() {
        return CollectionsKt.listOfNotNull((Object[]) new PropertyAccessorDescriptor[]{getGetter(), getSetter()});
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        PropertyGetterDescriptor getter = getGetter();
        List<TypeParameterDescriptor> typeParameters = getter != null ? getter.getTypeParameters() : null;
        return typeParameters == null ? CollectionsKt.emptyList() : typeParameters;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        return getOwner().getVisibility();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public PropertySetterDescriptor getSetter() {
        IrSimpleFunction setter = getOwner().getSetter();
        IrBasedSimpleFunctionDescriptor irBasedDescriptor = setter != null ? IrBasedDescriptorsKt.toIrBasedDescriptor(setter) : null;
        if (irBasedDescriptor instanceof PropertySetterDescriptor) {
            return (PropertySetterDescriptor) irBasedDescriptor;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return getOwner().getIsExpect();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public PropertyDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException("IrBased descriptors SHOULD NOT be substituted");
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        IrType type;
        IrSimpleFunction getter = getOwner().getGetter();
        if (getter == null || (type = getter.getReturnType()) == null) {
            IrField backingField = getOwner().getBackingField();
            type = backingField != null ? backingField.getType() : null;
            Intrinsics.checkNotNull(type);
        }
        return toIrBasedKotlinType(type);
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        return getReturnType();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return getOwner().getIsVar();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        IrValueParameter dispatchReceiverParameter;
        IrSimpleFunction getter = getOwner().getGetter();
        CallableDescriptor irBasedDescriptor = (getter == null || (dispatchReceiverParameter = getter.getDispatchReceiverParameter()) == null) ? null : IrBasedDescriptorsKt.toIrBasedDescriptor(dispatchReceiverParameter);
        if (irBasedDescriptor instanceof ReceiverParameterDescriptor) {
            return (ReceiverParameterDescriptor) irBasedDescriptor;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return getOwner().getIsConst();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return getOwner().getIsLateinit();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        IrValueParameter extensionReceiverParameter;
        IrSimpleFunction getter = getOwner().getGetter();
        CallableDescriptor irBasedDescriptor = (getter == null || (extensionReceiverParameter = getter.getExtensionReceiverParameter()) == null) ? null : IrBasedDescriptorsKt.toIrBasedDescriptor(extensionReceiverParameter);
        if (irBasedDescriptor instanceof ReceiverParameterDescriptor) {
            return (ReceiverParameterDescriptor) irBasedDescriptor;
        }
        return null;
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

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public PropertyGetterDescriptor getGetter() {
        IrSimpleFunction getter = getOwner().getGetter();
        IrBasedSimpleFunctionDescriptor irBasedDescriptor = getter != null ? IrBasedDescriptorsKt.toIrBasedDescriptor(getter) : null;
        if (irBasedDescriptor instanceof PropertyGetterDescriptor) {
            return (PropertyGetterDescriptor) irBasedDescriptor;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.CopyBuilder<? extends PropertyDescriptor> newCopyBuilder() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    /* JADX INFO: renamed from: isDelegated */
    public boolean getIsDelegated() {
        return getOwner().getIsDelegated();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public FieldDescriptor getBackingField() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public FieldDescriptor getDelegateField() {
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
