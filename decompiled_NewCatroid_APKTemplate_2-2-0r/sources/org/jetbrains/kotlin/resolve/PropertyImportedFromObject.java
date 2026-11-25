package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
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
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: importedFromObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u0092\u0001\u0010\u0015\u001a\n \u0017*\u0004\u0018\u0001H\u0016H\u0016\"\u0010\b\u0000\u0010\u0016*\n \u0017*\u0004\u0018\u00010\u00180\u0018\"\u0010\b\u0001\u0010\u0019*\n \u0017*\u0004\u0018\u00010\u00180\u00182F\u0010\u001a\u001aB\u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\u0016H\u0016\u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\u0019H\u0019 \u0017* \u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\u0016H\u0016\u0012\f\u0012\n \u0017*\u0004\u0018\u0001H\u0019H\u0019\u0018\u00010\u001b0\u001b2\u000e\u0010\u001c\u001a\n \u0017*\u0004\u0018\u0001H\u0019H\u0019H\u0096\u0001¢\u0006\u0002\u0010\u001dJQ\u0010\u001e\u001a\u00020\u001f2F\u0010\u001a\u001aB\u0012\f\u0012\n \u0017*\u0004\u0018\u00010 0 \u0012\f\u0012\n \u0017*\u0004\u0018\u00010 0  \u0017* \u0012\f\u0012\n \u0017*\u0004\u0018\u00010 0 \u0012\f\u0012\n \u0017*\u0004\u0018\u00010 0 \u0018\u00010\u001b0\u001bH\u0096\u0001J\t\u0010!\u001a\u00020\u001fH\u0096\u0001J8\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020\u000fH\u0016J+\u0010-\u001a$\u0012\f\u0012\n \u0017*\u0004\u0018\u00010/0/ \u0017*\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010/0/000.H\u0097\u0001J\u000b\u00101\u001a\u0004\u0018\u000102H\u0097\u0001J\u000f\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u000104H\u0097\u0001J\t\u00105\u001a\u00020%H\u0097\u0001J\u0017\u00106\u001a\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u0001070700H\u0097\u0001J\u000b\u00108\u001a\u0004\u0018\u000102H\u0097\u0001J\n\u00109\u001a\u0004\u0018\u000107H\u0016J\u000b\u0010:\u001a\u0004\u0018\u000107H\u0097\u0001J\u000b\u0010;\u001a\u0004\u0018\u00010<H\u0097\u0001J\t\u0010=\u001a\u00020+H\u0097\u0001J\t\u0010>\u001a\u00020'H\u0097\u0001J\t\u0010?\u001a\u00020@H\u0097\u0001J\b\u0010A\u001a\u00020\u0002H\u0016J-\u0010B\u001a&\u0012\u000e\b\u0001\u0012\n \u0017*\u0004\u0018\u00010\u00020\u0002 \u0017*\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00020\u00020D0CH\u0097\u0001J\u000b\u0010E\u001a\u0004\u0018\u00010<H\u0097\u0001J\t\u0010F\u001a\u00020GH\u0097\u0001J\t\u0010H\u001a\u00020<H\u0097\u0001J\u0017\u0010I\u001a\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010J0J00H\u0097\u0001JN\u0010K\u001a\u0004\u0018\u0001HL\"\u0010\b\u0000\u0010L*\n \u0017*\u0004\u0018\u00010\u00180\u00182*\u0010\u001a\u001a&\u0012\f\u0012\n \u0017*\u0004\u0018\u0001HLHL \u0017*\u0012\u0012\f\u0012\n \u0017*\u0004\u0018\u0001HLHL\u0018\u00010M0MH\u0097\u0001¢\u0006\u0002\u0010NJ+\u0010O\u001a$\u0012\f\u0012\n \u0017*\u0004\u0018\u00010P0P \u0017*\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010P0P000.H\u0097\u0001J\t\u0010Q\u001a\u00020)H\u0097\u0001J\t\u0010R\u001a\u00020\u000fH\u0096\u0001J\t\u0010S\u001a\u00020\u000fH\u0096\u0001J\t\u0010T\u001a\u00020\u000fH\u0096\u0001J\t\u0010U\u001a\u00020\u000fH\u0096\u0001J\t\u0010V\u001a\u00020\u000fH\u0096\u0001J\t\u0010W\u001a\u00020\u000fH\u0096\u0001J\t\u0010X\u001a\u00020\u000fH\u0096\u0001J\t\u0010Y\u001a\u00020\u000fH\u0096\u0001J\t\u0010Z\u001a\u00020\u000fH\u0096\u0001J\u0019\u0010[\u001a\u0012\u0012\u000e\b\u0001\u0012\n \u0017*\u0004\u0018\u00010\u00020\u00020\\H\u0097\u0001J7\u0010]\u001a\u00020\u001f2,\b\u0001\u0010\u001a\u001a&\u0012\u000e\b\u0001\u0012\n \u0017*\u0004\u0018\u00010^0^ \u0017*\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010^0^0D0CH\u0096\u0001J\u0012\u0010_\u001a\u0004\u0018\u00010\u00002\u0006\u0010`\u001a\u00020aH\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8WX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128WX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/PropertyImportedFromObject;", "Lorg/jetbrains/kotlin/resolve/ImportedFromObjectCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "propertyFromObject", "originalOrNull", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "getGetter", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "isDelegated", "", "()Z", "setter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "getSetter", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "getAccessors", "", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "", "getBackingField", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "getContextReceiverParameters", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDelegateField", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "", "getReturnType", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getType", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isConst", "isExpect", "isExternal", "isLateInit", "isSetterProjectedOut", "isVar", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$CopyBuilder;", "setOverriddenDescriptors", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyImportedFromObject extends ImportedFromObjectCallableDescriptor<PropertyDescriptor> implements PropertyDescriptor {
    private final /* synthetic */ PropertyDescriptor $$delegate_0;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> p0, D p1) {
        return (R) this.$$delegate_0.accept(p0, p1);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> p0) {
        this.$$delegate_0.mo7454acceptVoid(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public void cleanCompileTimeInitializerCache() {
        this.$$delegate_0.cleanCompileTimeInitializerCache();
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor
    public List<PropertyAccessorDescriptor> getAccessors() {
        return this.$$delegate_0.getAccessors();
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.$$delegate_0.getAnnotations();
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
    public DeclarationDescriptor getPackageFragment() {
        return this.$$delegate_0.getPackageFragment();
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
        return null;
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

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
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
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> p0) {
        return (V) this.$$delegate_0.getUserData(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        return this.$$delegate_0.getValueParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return this.$$delegate_0.getVisibility();
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
    public boolean isDelegated() {
        return this.$$delegate_0.isDelegated();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExpect() {
        return this.$$delegate_0.isExpect();
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
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.$$delegate_0.mo7205setOverriddenDescriptors(p0);
    }

    public /* synthetic */ PropertyImportedFromObject(PropertyDescriptor propertyDescriptor, PropertyDescriptor propertyDescriptor2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(propertyDescriptor, (i & 2) != 0 ? null : propertyDescriptor2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PropertyImportedFromObject(PropertyDescriptor propertyFromObject, PropertyDescriptor propertyDescriptor) {
        super(propertyFromObject, propertyDescriptor);
        Intrinsics.checkNotNullParameter(propertyFromObject, "propertyFromObject");
        this.$$delegate_0 = propertyFromObject;
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public PropertyImportedFromObject substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        PropertyDescriptor propertyDescriptorSubstitute = getCallableFromObject().substitute(substitutor);
        if (propertyDescriptorSubstitute != null) {
            return ImportedFromObjectKt.asImportedFromObject(propertyDescriptorSubstitute, this);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PropertyDescriptor getOriginal() {
        CallableDescriptor callableDescriptor = get_original();
        Intrinsics.checkNotNull(callableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        return (PropertyDescriptor) callableDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        throw new UnsupportedOperationException("copy() should not be called on " + getClass().getSimpleName() + ", was called for " + this);
    }
}
