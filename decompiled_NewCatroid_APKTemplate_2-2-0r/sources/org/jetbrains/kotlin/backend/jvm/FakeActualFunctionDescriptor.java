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
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: OrphanedExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0092\u0001\u0010\b\u001a\n \n*\u0004\u0018\u0001H\tH\t\"\u0010\b\u0000\u0010\t*\n \n*\u0004\u0018\u00010\u000b0\u000b\"\u0010\b\u0001\u0010\f*\n \n*\u0004\u0018\u00010\u000b0\u000b2F\u0010\r\u001aB\u0012\f\u0012\n \n*\u0004\u0018\u0001H\tH\t\u0012\f\u0012\n \n*\u0004\u0018\u0001H\fH\f \n* \u0012\f\u0012\n \n*\u0004\u0018\u0001H\tH\t\u0012\f\u0012\n \n*\u0004\u0018\u0001H\fH\f\u0018\u00010\u000e0\u000e2\u000e\u0010\u000f\u001a\n \n*\u0004\u0018\u0001H\fH\fH\u0096\u0001¢\u0006\u0002\u0010\u0010JQ\u0010\u0011\u001a\u00020\u00122F\u0010\r\u001aB\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013 \n* \u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013\u0018\u00010\u000e0\u000eH\u0096\u0001JQ\u0010\u0014\u001a\u00020\u00012\u000e\u0010\r\u001a\n \n*\u0004\u0018\u00010\u00150\u00152\u000e\u0010\u000f\u001a\n \n*\u0004\u0018\u00010\u00160\u00162\u000e\u0010\u0017\u001a\n \n*\u0004\u0018\u00010\u00180\u00182\u000e\u0010\u0019\u001a\n \n*\u0004\u0018\u00010\u001a0\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0097\u0001J\t\u0010\u001d\u001a\u00020\u0015H\u0097\u0001J\u0017\u0010\u001e\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010 0 0\u001fH\u0097\u0001J\u000b\u0010!\u001a\u0004\u0018\u00010 H\u0097\u0001J\u000b\u0010\"\u001a\u0004\u0018\u00010 H\u0097\u0001J\u000b\u0010#\u001a\u0004\u0018\u00010\u0001H\u0097\u0001J\t\u0010$\u001a\u00020\u001aH\u0097\u0001J\t\u0010%\u001a\u00020\u0016H\u0097\u0001J\t\u0010&\u001a\u00020'H\u0097\u0001J\b\u0010(\u001a\u00020\u0001H\u0016J-\u0010)\u001a&\u0012\u000e\b\u0001\u0012\n \n*\u0004\u0018\u00010\u00010\u0001 \n*\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00010\u00010+0*H\u0097\u0001J\u000b\u0010,\u001a\u0004\u0018\u00010-H\u0097\u0001J\b\u0010.\u001a\u00020/H\u0016J\u0017\u00100\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u000101010\u001fH\u0097\u0001JN\u00102\u001a\u0004\u0018\u0001H3\"\u0010\b\u0000\u00103*\n \n*\u0004\u0018\u00010\u000b0\u000b2*\u0010\r\u001a&\u0012\f\u0012\n \n*\u0004\u0018\u0001H3H3 \n*\u0012\u0012\f\u0012\n \n*\u0004\u0018\u0001H3H3\u0018\u00010404H\u0097\u0001¢\u0006\u0002\u00105J+\u00106\u001a$\u0012\f\u0012\n \n*\u0004\u0018\u00010808 \n*\u0010\u0012\f\u0012\n \n*\u0004\u0018\u000108080\u001f07H\u0097\u0001J\t\u00109\u001a\u00020\u0018H\u0097\u0001J\t\u0010:\u001a\u00020\u001cH\u0096\u0001J\t\u0010;\u001a\u00020\u001cH\u0096\u0001J\b\u0010<\u001a\u00020\u001cH\u0016J\b\u0010=\u001a\u00020\u001cH\u0016J\t\u0010>\u001a\u00020\u001cH\u0096\u0001J\t\u0010?\u001a\u00020\u001cH\u0096\u0001J\t\u0010@\u001a\u00020\u001cH\u0096\u0001J\t\u0010A\u001a\u00020\u001cH\u0096\u0001J\b\u0010B\u001a\u00020\u001cH\u0016J\t\u0010C\u001a\u00020\u001cH\u0096\u0001J\t\u0010D\u001a\u00020\u001cH\u0096\u0001J\t\u0010E\u001a\u00020\u001cH\u0096\u0001J\u0019\u0010F\u001a\u0012\u0012\u000e\b\u0001\u0012\n \n*\u0004\u0018\u00010\u00010\u00010GH\u0097\u0001J7\u0010H\u001a\u00020\u00122,\b\u0001\u0010\r\u001a&\u0012\u000e\b\u0001\u0012\n \n*\u0004\u0018\u00010I0I \n*\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010I0I0+0*H\u0096\u0001J\u0015\u0010J\u001a\u0004\u0018\u00010\u00012\b\b\u0001\u0010K\u001a\u00020LH\u0097\u0001R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/FakeActualFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "original", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/Modality;", "p2", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "p3", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "p4", "", "getContainingDeclaration", "getContextReceiverParameters", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInitialSignatureDescriptor", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getValueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isExpect", "isExternal", "isHiddenForResolutionEverywhereBesideSupercalls", "isHiddenToOvercomeSignatureClash", "isInfix", "isInline", "isOperator", "isSuspend", "isTailrec", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor$CopyBuilder;", "setOverriddenDescriptors", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FakeActualFunctionDescriptor implements FunctionDescriptor {
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

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor copy(DeclarationDescriptor p0, Modality p1, DescriptorVisibility p2, CallableMemberDescriptor.Kind p3, boolean p4) {
        return this.$$delegate_0.copy(p0, p1, p2, p3, p4);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.$$delegate_0.getAnnotations();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        return this.$$delegate_0.getContainingDeclaration();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ReceiverParameterDescriptor> getContextReceiverParameters() {
        return this.$$delegate_0.getContextReceiverParameters();
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

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        return this.$$delegate_0.getOverriddenDescriptors();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return this.$$delegate_0.getReturnType();
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

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return false;
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
        return false;
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

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public FunctionDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this.$$delegate_0.substitute(substitutor);
    }

    public FakeActualFunctionDescriptor(FunctionDescriptor original) {
        Intrinsics.checkNotNullParameter(original, "original");
        this.$$delegate_0 = original;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public FunctionDescriptor getOriginal() {
        return this;
    }
}
