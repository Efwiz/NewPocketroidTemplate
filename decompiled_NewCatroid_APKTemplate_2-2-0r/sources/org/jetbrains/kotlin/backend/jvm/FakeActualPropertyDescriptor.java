package org.jetbrains.kotlin.backend.jvm;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: OrphanedExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0092\u0001\u0010\u0013\u001a\n \u0015*\u0004\u0018\u0001H\u0014H\u0014\"\u0010\b\u0000\u0010\u0014*\n \u0015*\u0004\u0018\u00010\u00160\u0016\"\u0010\b\u0001\u0010\u0017*\n \u0015*\u0004\u0018\u00010\u00160\u00162F\u0010\u0018\u001aB\u0012\f\u0012\n \u0015*\u0004\u0018\u0001H\u0014H\u0014\u0012\f\u0012\n \u0015*\u0004\u0018\u0001H\u0017H\u0017 \u0015* \u0012\f\u0012\n \u0015*\u0004\u0018\u0001H\u0014H\u0014\u0012\f\u0012\n \u0015*\u0004\u0018\u0001H\u0017H\u0017\u0018\u00010\u00190\u00192\u000e\u0010\u001a\u001a\n \u0015*\u0004\u0018\u0001H\u0017H\u0017H\u0096\u0001¢\u0006\u0002\u0010\u001bJQ\u0010\u001c\u001a\u00020\u001d2F\u0010\u0018\u001aB\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u001e0\u001e\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u001e0\u001e \u0015* \u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u001e0\u001e\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u001e0\u001e\u0018\u00010\u00190\u0019H\u0096\u0001J\t\u0010\u001f\u001a\u00020\u001dH\u0096\u0001JQ\u0010 \u001a\u00020!2\u000e\u0010\u0018\u001a\n \u0015*\u0004\u0018\u00010\"0\"2\u000e\u0010\u001a\u001a\n \u0015*\u0004\u0018\u00010#0#2\u000e\u0010$\u001a\n \u0015*\u0004\u0018\u00010%0%2\u000e\u0010&\u001a\n \u0015*\u0004\u0018\u00010'0'2\u0006\u0010(\u001a\u00020\rH\u0097\u0001J+\u0010)\u001a$\u0012\f\u0012\n \u0015*\u0004\u0018\u00010+0+ \u0015*\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010+0+0,0*H\u0097\u0001J\u000b\u0010-\u001a\u0004\u0018\u00010.H\u0097\u0001J\u000f\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u000100H\u0097\u0001J\t\u00101\u001a\u00020\"H\u0097\u0001J\u0017\u00102\u001a\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u000103030,H\u0097\u0001J\u000b\u00104\u001a\u0004\u0018\u00010.H\u0097\u0001J\u000b\u00105\u001a\u0004\u0018\u000103H\u0097\u0001J\u000b\u00106\u001a\u0004\u0018\u000103H\u0097\u0001J\u000b\u00107\u001a\u0004\u0018\u000108H\u0097\u0001J\t\u00109\u001a\u00020'H\u0097\u0001J\t\u0010:\u001a\u00020#H\u0097\u0001J\t\u0010;\u001a\u00020<H\u0097\u0001J\b\u0010=\u001a\u00020\u0001H\u0016J-\u0010>\u001a&\u0012\u000e\b\u0001\u0012\n \u0015*\u0004\u0018\u00010\u00010\u0001 \u0015*\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00010\u00010@0?H\u0097\u0001J\u000b\u0010A\u001a\u0004\u0018\u000108H\u0097\u0001J\b\u0010B\u001a\u00020CH\u0016J\t\u0010D\u001a\u000208H\u0097\u0001J\u0017\u0010E\u001a\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010F0F0,H\u0097\u0001JN\u0010G\u001a\u0004\u0018\u0001HH\"\u0010\b\u0000\u0010H*\n \u0015*\u0004\u0018\u00010\u00160\u00162*\u0010\u0018\u001a&\u0012\f\u0012\n \u0015*\u0004\u0018\u0001HHHH \u0015*\u0012\u0012\f\u0012\n \u0015*\u0004\u0018\u0001HHHH\u0018\u00010I0IH\u0097\u0001¢\u0006\u0002\u0010JJ+\u0010K\u001a$\u0012\f\u0012\n \u0015*\u0004\u0018\u00010L0L \u0015*\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010L0L0,0*H\u0097\u0001J\t\u0010M\u001a\u00020%H\u0097\u0001J\t\u0010N\u001a\u00020\rH\u0096\u0001J\t\u0010O\u001a\u00020\rH\u0096\u0001J\b\u0010P\u001a\u00020\rH\u0016J\t\u0010Q\u001a\u00020\rH\u0096\u0001J\b\u0010R\u001a\u00020\rH\u0016J\t\u0010S\u001a\u00020\rH\u0096\u0001J\t\u0010T\u001a\u00020\rH\u0096\u0001J\t\u0010U\u001a\u00020\rH\u0096\u0001J\t\u0010V\u001a\u00020\rH\u0096\u0001J\u0019\u0010W\u001a\u0012\u0012\u000e\b\u0001\u0012\n \u0015*\u0004\u0018\u00010\u00010\u00010XH\u0097\u0001J7\u0010Y\u001a\u00020\u001d2,\b\u0001\u0010\u0018\u001a&\u0012\u000e\b\u0001\u0012\n \u0015*\u0004\u0018\u00010!0! \u0015*\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010!0!0@0?H\u0096\u0001J\u001b\u0010Z\u001a\n \u0015*\u0004\u0018\u00010\u00010\u00012\b\b\u0001\u0010[\u001a\u00020\\H\u0096\u0001R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8WX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108WX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006]"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/FakeActualPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "original", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "getGetter", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "isDelegated", "", "()Z", "setter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "getSetter", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "cleanCompileTimeInitializerCache", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/Modality;", "p2", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "p3", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "p4", "getAccessors", "", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "", "getBackingField", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "getCompileTimeInitializer", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getContainingDeclaration", "getContextReceiverParameters", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDelegateField", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "", "getReturnType", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getType", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isConst", "isExpect", "isExternal", "isLateInit", "isSetterProjectedOut", "isVar", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$CopyBuilder;", "setOverriddenDescriptors", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FakeActualPropertyDescriptor implements PropertyDescriptor {
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

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor copy(DeclarationDescriptor p0, Modality p1, DescriptorVisibility p2, CallableMemberDescriptor.Kind p3, boolean p4) {
        return this.$$delegate_0.copy(p0, p1, p2, p3, p4);
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
    public DeclarationDescriptor getContainingDeclaration() {
        return this.$$delegate_0.getContainingDeclaration();
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
        return true;
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
        return false;
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

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public PropertyDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this.$$delegate_0.substitute(substitutor);
    }

    public FakeActualPropertyDescriptor(PropertyDescriptor original) {
        Intrinsics.checkNotNullParameter(original, "original");
        this.$$delegate_0 = original;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PropertyDescriptor getOriginal() {
        return this;
    }
}
