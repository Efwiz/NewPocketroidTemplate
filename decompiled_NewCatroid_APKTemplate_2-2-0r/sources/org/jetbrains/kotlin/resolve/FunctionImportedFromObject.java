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
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: importedFromObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u0092\u0001\u0010\n\u001a\n \f*\u0004\u0018\u0001H\u000bH\u000b\"\u0010\b\u0000\u0010\u000b*\n \f*\u0004\u0018\u00010\r0\r\"\u0010\b\u0001\u0010\u000e*\n \f*\u0004\u0018\u00010\r0\r2F\u0010\u000f\u001aB\u0012\f\u0012\n \f*\u0004\u0018\u0001H\u000bH\u000b\u0012\f\u0012\n \f*\u0004\u0018\u0001H\u000eH\u000e \f* \u0012\f\u0012\n \f*\u0004\u0018\u0001H\u000bH\u000b\u0012\f\u0012\n \f*\u0004\u0018\u0001H\u000eH\u000e\u0018\u00010\u00100\u00102\u000e\u0010\u0011\u001a\n \f*\u0004\u0018\u0001H\u000eH\u000eH\u0096\u0001¢\u0006\u0002\u0010\u0012JQ\u0010\u0013\u001a\u00020\u00142F\u0010\u000f\u001aB\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00150\u0015\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00150\u0015 \f* \u0012\f\u0012\n \f*\u0004\u0018\u00010\u00150\u0015\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00150\u0015\u0018\u00010\u00100\u0010H\u0096\u0001J8\u0010\u0016\u001a\u00020\u00022\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\t\u0010!\u001a\u00020\u0018H\u0097\u0001J\u0017\u0010\"\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010$0$0#H\u0097\u0001J\n\u0010%\u001a\u0004\u0018\u00010$H\u0016J\u000b\u0010&\u001a\u0004\u0018\u00010$H\u0097\u0001J\u000b\u0010'\u001a\u0004\u0018\u00010\u0002H\u0097\u0001J\t\u0010(\u001a\u00020\u001eH\u0097\u0001J\t\u0010)\u001a\u00020\u001aH\u0097\u0001J\t\u0010*\u001a\u00020+H\u0097\u0001J\b\u0010,\u001a\u00020\u0002H\u0016J-\u0010-\u001a&\u0012\u000e\b\u0001\u0012\n \f*\u0004\u0018\u00010\u00020\u0002 \f*\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00020\u00020/0.H\u0097\u0001J\u000b\u00100\u001a\u0004\u0018\u000101H\u0097\u0001J\t\u00102\u001a\u000203H\u0097\u0001J\u0017\u00104\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u000105050#H\u0097\u0001JN\u00106\u001a\u0004\u0018\u0001H7\"\u0010\b\u0000\u00107*\n \f*\u0004\u0018\u00010\r0\r2*\u0010\u000f\u001a&\u0012\f\u0012\n \f*\u0004\u0018\u0001H7H7 \f*\u0012\u0012\f\u0012\n \f*\u0004\u0018\u0001H7H7\u0018\u00010808H\u0097\u0001¢\u0006\u0002\u00109J+\u0010:\u001a$\u0012\f\u0012\n \f*\u0004\u0018\u00010<0< \f*\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010<0<0#0;H\u0097\u0001J\t\u0010=\u001a\u00020\u001cH\u0097\u0001J\t\u0010>\u001a\u00020 H\u0096\u0001J\t\u0010?\u001a\u00020 H\u0096\u0001J\t\u0010@\u001a\u00020 H\u0096\u0001J\t\u0010A\u001a\u00020 H\u0096\u0001J\t\u0010B\u001a\u00020 H\u0096\u0001J\t\u0010C\u001a\u00020 H\u0096\u0001J\t\u0010D\u001a\u00020 H\u0096\u0001J\t\u0010E\u001a\u00020 H\u0096\u0001J\t\u0010F\u001a\u00020 H\u0096\u0001J\t\u0010G\u001a\u00020 H\u0096\u0001J\t\u0010H\u001a\u00020 H\u0096\u0001J\t\u0010I\u001a\u00020 H\u0096\u0001J\u0019\u0010J\u001a\u0012\u0012\u000e\b\u0001\u0012\n \f*\u0004\u0018\u00010\u00020\u00020KH\u0097\u0001J7\u0010L\u001a\u00020\u00142,\b\u0001\u0010\u000f\u001a&\u0012\u000e\b\u0001\u0012\n \f*\u0004\u0018\u00010M0M \f*\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010M0M0/0.H\u0096\u0001J\u0012\u0010N\u001a\u0004\u0018\u00010\u00002\u0006\u0010O\u001a\u00020PH\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006Q"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FunctionImportedFromObject;", "Lorg/jetbrains/kotlin/resolve/ImportedFromObjectCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "functionFromObject", "originalOrNull", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "getContainingDeclaration", "getContextReceiverParameters", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInitialSignatureDescriptor", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isExpect", "isExternal", "isHiddenForResolutionEverywhereBesideSupercalls", "isHiddenToOvercomeSignatureClash", "isInfix", "isInline", "isOperator", "isSuspend", "isTailrec", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor$CopyBuilder;", "setOverriddenDescriptors", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionImportedFromObject extends ImportedFromObjectCallableDescriptor<FunctionDescriptor> implements FunctionDescriptor {
    private final /* synthetic */ FunctionDescriptor $$delegate_0;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> p0, D p1) {
        return (R) this.$$delegate_0.accept(p0, p1);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> p0) {
        this.$$delegate_0.mo7454acceptVoid(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.$$delegate_0.getAnnotations();
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

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.$$delegate_0.getExtensionReceiverParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public FunctionDescriptor getInitialSignatureDescriptor() {
        return this.$$delegate_0.getInitialSignatureDescriptor();
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
    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        return this.$$delegate_0.getOverriddenDescriptors();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return this.$$delegate_0.getReturnType();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return this.$$delegate_0.getSourceElement();
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

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExpect() {
        return this.$$delegate_0.isExpect();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return this.$$delegate_0.isExternal();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isHiddenForResolutionEverywhereBesideSupercalls() {
        return this.$$delegate_0.isHiddenForResolutionEverywhereBesideSupercalls();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isHiddenToOvercomeSignatureClash() {
        return this.$$delegate_0.isHiddenToOvercomeSignatureClash();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInfix() {
        return this.$$delegate_0.isInfix();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInline() {
        return this.$$delegate_0.isInline();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isOperator() {
        return this.$$delegate_0.isOperator();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isSuspend() {
        return this.$$delegate_0.isSuspend();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return this.$$delegate_0.isTailrec();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> newCopyBuilder() {
        return this.$$delegate_0.newCopyBuilder();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    /* JADX INFO: renamed from: setOverriddenDescriptors */
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        this.$$delegate_0.mo7205setOverriddenDescriptors(p0);
    }

    public /* synthetic */ FunctionImportedFromObject(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(functionDescriptor, (i & 2) != 0 ? null : functionDescriptor2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionImportedFromObject(FunctionDescriptor functionFromObject, FunctionDescriptor functionDescriptor) {
        super(functionFromObject, functionDescriptor);
        Intrinsics.checkNotNullParameter(functionFromObject, "functionFromObject");
        this.$$delegate_0 = functionFromObject;
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public FunctionImportedFromObject substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        FunctionDescriptor functionDescriptorSubstitute = getCallableFromObject().substitute(substitutor);
        if (functionDescriptorSubstitute != null) {
            return ImportedFromObjectKt.asImportedFromObject(functionDescriptorSubstitute, this);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public FunctionDescriptor getOriginal() {
        CallableDescriptor callableDescriptor = get_original();
        Intrinsics.checkNotNull(callableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
        return (FunctionDescriptor) callableDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        throw new UnsupportedOperationException("copy() should not be called on " + getClass().getSimpleName() + ", was called for " + this);
    }
}
