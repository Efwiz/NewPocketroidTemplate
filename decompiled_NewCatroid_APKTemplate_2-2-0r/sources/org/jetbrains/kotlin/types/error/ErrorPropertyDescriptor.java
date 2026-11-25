package org.jetbrains.kotlin.types.error;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
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
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: ErrorPropertyDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ö\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0092\u0001\u0010\u0012\u001a\n \u0014*\u0004\u0018\u0001H\u0013H\u0013\"\u0010\b\u0000\u0010\u0013*\n \u0014*\u0004\u0018\u00010\u00150\u0015\"\u0010\b\u0001\u0010\u0016*\n \u0014*\u0004\u0018\u00010\u00150\u00152F\u0010\u0017\u001aB\u0012\f\u0012\n \u0014*\u0004\u0018\u0001H\u0013H\u0013\u0012\f\u0012\n \u0014*\u0004\u0018\u0001H\u0016H\u0016 \u0014* \u0012\f\u0012\n \u0014*\u0004\u0018\u0001H\u0013H\u0013\u0012\f\u0012\n \u0014*\u0004\u0018\u0001H\u0016H\u0016\u0018\u00010\u00180\u00182\u000e\u0010\u0019\u001a\n \u0014*\u0004\u0018\u0001H\u0016H\u0016H\u0096\u0001¢\u0006\u0002\u0010\u001aJQ\u0010\u001b\u001a\u00020\u001c2F\u0010\u0017\u001aB\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u001d0\u001d\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u001d0\u001d \u0014* \u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u001d0\u001d\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u001d0\u001d\u0018\u00010\u00180\u0018H\u0096\u0001J\t\u0010\u001e\u001a\u00020\u001cH\u0096\u0001JQ\u0010\u001f\u001a\u00020 2\u000e\u0010!\u001a\n \u0014*\u0004\u0018\u00010\"0\"2\u000e\u0010#\u001a\n \u0014*\u0004\u0018\u00010$0$2\u000e\u0010%\u001a\n \u0014*\u0004\u0018\u00010&0&2\u000e\u0010'\u001a\n \u0014*\u0004\u0018\u00010(0(2\u0006\u0010)\u001a\u00020\fH\u0097\u0001J+\u0010*\u001a$\u0012\f\u0012\n \u0014*\u0004\u0018\u00010,0, \u0014*\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010,0,0-0+H\u0097\u0001J\u000b\u0010.\u001a\u0004\u0018\u00010/H\u0097\u0001J\u000f\u00100\u001a\b\u0012\u0002\b\u0003\u0018\u000101H\u0097\u0001J\t\u00102\u001a\u00020\"H\u0097\u0001J\u0017\u00103\u001a\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u000104040-H\u0097\u0001J\u000b\u00105\u001a\u0004\u0018\u00010/H\u0097\u0001J\u000b\u00106\u001a\u0004\u0018\u000104H\u0097\u0001J\u000b\u00107\u001a\u0004\u0018\u000104H\u0097\u0001J\u000b\u00108\u001a\u0004\u0018\u000109H\u0097\u0001J\t\u0010:\u001a\u00020(H\u0097\u0001J\t\u0010;\u001a\u00020$H\u0097\u0001J\t\u0010<\u001a\u00020=H\u0097\u0001J\t\u0010>\u001a\u00020\u0001H\u0097\u0001J-\u0010?\u001a&\u0012\u000e\b\u0001\u0012\n \u0014*\u0004\u0018\u00010\u00010\u0001 \u0014*\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u00010\u00010A0@H\u0097\u0001J\u000b\u0010B\u001a\u0004\u0018\u000109H\u0097\u0001J\t\u0010C\u001a\u00020DH\u0097\u0001J\t\u0010E\u001a\u000209H\u0097\u0001J\u0017\u0010F\u001a\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010G0G0-H\u0097\u0001JN\u0010H\u001a\u0004\u0018\u0001HI\"\u0010\b\u0000\u0010I*\n \u0014*\u0004\u0018\u00010\u00150\u00152*\u0010J\u001a&\u0012\f\u0012\n \u0014*\u0004\u0018\u0001HIHI \u0014*\u0012\u0012\f\u0012\n \u0014*\u0004\u0018\u0001HIHI\u0018\u00010K0KH\u0097\u0001¢\u0006\u0002\u0010LJ+\u0010M\u001a$\u0012\f\u0012\n \u0014*\u0004\u0018\u00010N0N \u0014*\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010N0N0-0+H\u0097\u0001J\t\u0010O\u001a\u00020&H\u0097\u0001J\t\u0010P\u001a\u00020\fH\u0096\u0001J\t\u0010Q\u001a\u00020\fH\u0096\u0001J\t\u0010R\u001a\u00020\fH\u0096\u0001J\t\u0010S\u001a\u00020\fH\u0096\u0001J\t\u0010T\u001a\u00020\fH\u0096\u0001J\t\u0010U\u001a\u00020\fH\u0096\u0001J\t\u0010V\u001a\u00020\fH\u0096\u0001J\t\u0010W\u001a\u00020\fH\u0096\u0001J\t\u0010X\u001a\u00020\fH\u0096\u0001J\u0019\u0010Y\u001a\u0012\u0012\u000e\b\u0001\u0012\n \u0014*\u0004\u0018\u00010\u00010\u00010ZH\u0097\u0001J7\u0010[\u001a\u00020\u001c2,\b\u0001\u0010\\\u001a&\u0012\u000e\b\u0001\u0012\n \u0014*\u0004\u0018\u00010 0  \u0014*\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010 0 0A0@H\u0096\u0001J\u001b\u0010]\u001a\n \u0014*\u0004\u0018\u00010\u00010\u00012\b\b\u0001\u0010^\u001a\u00020_H\u0096\u0001R\u0012\u0010\u0003\u001a\u00020\u0004X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8WX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8WX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006`"}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "()V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "getGetter", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "isDelegated", "", "()Z", "setter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "getSetter", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "getAccessors", "", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "", "getBackingField", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "getContextReceiverParameters", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDelegateField", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "", "getReturnType", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getType", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isConst", "isExpect", "isExternal", "isLateInit", "isSetterProjectedOut", "isVar", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$CopyBuilder;", "setOverriddenDescriptors", "overriddenDescriptors", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorPropertyDescriptor implements PropertyDescriptor {
    private final /* synthetic */ PropertyDescriptorImpl $$delegate_0;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        return (R) this.$$delegate_0.accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        this.$$delegate_0.mo7454acceptVoid(visitor);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
        this.$$delegate_0.cleanCompileTimeInitializerCache();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        return this.$$delegate_0.copy(newOwner, modality, visibility, kind, copyOverrides);
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public List<PropertyAccessorDescriptor> getAccessors() {
        return this.$$delegate_0.getAccessors();
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        Annotations annotations = this.$$delegate_0.getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations, "<get-annotations>(...)");
        return annotations;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public FieldDescriptor getBackingField() {
        return this.$$delegate_0.getBackingField();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    /* JADX INFO: renamed from: getCompileTimeInitializer */
    public ConstantValue<?> mo6754getCompileTimeInitializer() {
        return this.$$delegate_0.mo6754getCompileTimeInitializer();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public DeclarationDescriptor getDeclarationDescriptor() {
        return this.$$delegate_0.getDeclarationDescriptor();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ReceiverParameterDescriptor> getContextReceiverParameters() {
        return this.$$delegate_0.getContextReceiverParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public FieldDescriptor getDelegateField() {
        return this.$$delegate_0.getDelegateField();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        return this.$$delegate_0.mo6753getDispatchReceiverParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.$$delegate_0.getExtensionReceiverParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public PropertyGetterDescriptor getGetter() {
        return this.$$delegate_0.getGetter();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public KotlinType getInType() {
        return this.$$delegate_0.getInType();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.Kind getKind() {
        return this.$$delegate_0.getKind();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return this.$$delegate_0.getModality();
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return this.$$delegate_0.getShortName();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PropertyDescriptor getOriginal() {
        return this.$$delegate_0.getOriginal();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<? extends PropertyDescriptor> getOverriddenDescriptors() {
        return this.$$delegate_0.getOverriddenDescriptors();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return this.$$delegate_0.getReturnType();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public PropertySetterDescriptor getSetter() {
        return this.$$delegate_0.getSetter();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return this.$$delegate_0.getSourceElement();
    }

    @Override // org.jetbrains.kotlin.descriptors.ValueDescriptor
    public KotlinType getType() {
        return this.$$delegate_0.getType();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        return this.$$delegate_0.getTypeParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return (V) this.$$delegate_0.getUserData(key);
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        return this.$$delegate_0.getValueParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: getVisibility */
    public DescriptorVisibility getVisibilityImpl() {
        return this.$$delegate_0.getVisibilityImpl();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasStableParameterNames() {
        return this.$$delegate_0.hasStableParameterNames();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasSynthesizedParameterNames() {
        return this.$$delegate_0.hasSynthesizedParameterNames();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return this.$$delegate_0.isActual();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isConst() {
        return this.$$delegate_0.isConst();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    /* JADX INFO: renamed from: isDelegated */
    public boolean getIsDelegated() {
        return this.$$delegate_0.getIsDelegated();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return this.$$delegate_0.get$isExpect();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return this.$$delegate_0.isExternal();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return this.$$delegate_0.isLateInit();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public boolean isSetterProjectedOut() {
        return this.$$delegate_0.isSetterProjectedOut();
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isVar() {
        return this.$$delegate_0.isVar();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.CopyBuilder<? extends PropertyDescriptor> newCopyBuilder() {
        return this.$$delegate_0.newCopyBuilder();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    /* JADX INFO: renamed from: setOverriddenDescriptors */
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        Intrinsics.checkNotNullParameter(overriddenDescriptors, "overriddenDescriptors");
        this.$$delegate_0.mo7205setOverriddenDescriptors(overriddenDescriptors);
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public PropertyDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this.$$delegate_0.substitute(substitutor);
    }

    public ErrorPropertyDescriptor() {
        PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(ErrorUtils.INSTANCE.getErrorClass(), Annotations.INSTANCE.getEMPTY(), Modality.OPEN, DescriptorVisibilities.PUBLIC, true, Name.special(ErrorEntity.ERROR_PROPERTY.getDebugText()), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE, false, false, false, false, false, false);
        propertyDescriptorImplCreate.setType(ErrorUtils.INSTANCE.getErrorPropertyType(), CollectionsKt.emptyList(), null, null, CollectionsKt.emptyList());
        this.$$delegate_0 = propertyDescriptorImplCreate;
    }
}
