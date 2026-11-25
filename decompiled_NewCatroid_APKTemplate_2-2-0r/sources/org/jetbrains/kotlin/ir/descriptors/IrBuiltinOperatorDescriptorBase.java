package org.jetbrains.kotlin.ir.descriptors;

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
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: IrBuiltinFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J5\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000eJ8\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001cH\u0016J\n\u0010\u001e\u001a\u0004\u0018\u00010\u001cH\u0016J\n\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u0017H\u0016J\b\u0010\"\u001a\u00020\u0013H\u0016J\b\u0010#\u001a\u00020$H\u0016J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00020 0&H\u0016J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u001bH\u0016J)\u0010)\u001a\u0004\u0018\u0001H*\"\b\b\u0000\u0010**\u00020+2\u000e\u0010,\u001a\n\u0012\u0004\u0012\u0002H*\u0018\u00010-H\u0016¢\u0006\u0002\u0010.J\b\u0010/\u001a\u00020\u0015H\u0016J\b\u00100\u001a\u00020\u0019H\u0016J\b\u00101\u001a\u00020\u0019H\u0016J\b\u00102\u001a\u00020\u0019H\u0016J\b\u00103\u001a\u00020\u0019H\u0016J\b\u00104\u001a\u00020\u0019H\u0016J\b\u00105\u001a\u00020\u0019H\u0016J\b\u00106\u001a\u00020\u0019H\u0016J\b\u00107\u001a\u00020\u0019H\u0016J\b\u00108\u001a\u00020\u0019H\u0016J\b\u00109\u001a\u00020\u0019H\u0016J\b\u0010:\u001a\u00020\u0019H\u0016J\b\u0010;\u001a\u00020\u0019H\u0016J\b\u0010<\u001a\u00020\u0010H\u0016J\u0016\u0010=\u001a\u00020\u00102\f\u0010>\u001a\b\u0012\u0004\u0012\u00020?0&H\u0016J\u0010\u0010@\u001a\u00020 2\u0006\u0010A\u001a\u00020BH\u0016¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinOperatorDescriptorBase;", "Lorg/jetbrains/kotlin/descriptors/impl/DeclarationDescriptorNonRootImpl;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinOperatorDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "", "newOwner", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "getContextReceiverParameters", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDispatchReceiverParameter", "getExtensionReceiverParameter", "getInitialSignatureDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getKind", "getModality", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getOverriddenDescriptors", "", "getTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getVisibility", "hasStableParameterNames", "hasSynthesizedParameterNames", "isActual", "isExpect", "isExternal", "isHiddenForResolutionEverywhereBesideSupercalls", "isHiddenToOvercomeSignatureClash", "isInfix", "isInline", "isOperator", "isSuspend", "isTailrec", "newCopyBuilder", "setOverriddenDescriptors", "overriddenDescriptors", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBuiltinOperatorDescriptorBase extends DeclarationDescriptorNonRootImpl implements IrBuiltinOperatorDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public FunctionDescriptor getInitialSignatureDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasStableParameterNames() {
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public boolean hasSynthesizedParameterNames() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExpect() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isHiddenForResolutionEverywhereBesideSupercalls() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isHiddenToOvercomeSignatureClash() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInfix() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isOperator() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isSuspend() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public /* bridge */ /* synthetic */ CallableMemberDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z) {
        return (CallableMemberDescriptor) copy(declarationDescriptor, modality, descriptorVisibility, kind, z);
    }

    @Override // org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public /* bridge */ /* synthetic */ FunctionDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z) {
        return (FunctionDescriptor) copy(declarationDescriptor, modality, descriptorVisibility, kind, z);
    }

    @Override // org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public /* bridge */ /* synthetic */ SimpleFunctionDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z) {
        return (SimpleFunctionDescriptor) copy(declarationDescriptor, modality, descriptorVisibility, kind, z);
    }

    @Override // org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public /* bridge */ /* synthetic */ CallableMemberDescriptor.CopyBuilder newCopyBuilder() {
        return (CallableMemberDescriptor.CopyBuilder) newCopyBuilder();
    }

    @Override // org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public /* bridge */ /* synthetic */ FunctionDescriptor.CopyBuilder newCopyBuilder() {
        return (FunctionDescriptor.CopyBuilder) newCopyBuilder();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    /* JADX INFO: renamed from: setOverriddenDescriptors, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ void mo7205setOverriddenDescriptors(Collection collection) {
        setOverriddenDescriptors((Collection<? extends CallableMemberDescriptor>) collection);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBuiltinOperatorDescriptorBase(DeclarationDescriptor containingDeclaration, Name name2) {
        super(containingDeclaration, Annotations.INSTANCE.getEMPTY(), name2, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ReceiverParameterDescriptor> getContextReceiverParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public SimpleFunctionDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public FunctionDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<FunctionDescriptor> getOverriddenDescriptors() {
        return CollectionsKt.emptyList();
    }

    public Void setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        Intrinsics.checkNotNullParameter(overriddenDescriptors, "overriddenDescriptors");
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<TypeParameterDescriptor> getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        return PUBLIC;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return Modality.FINAL;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.Kind getKind() {
        return CallableMemberDescriptor.Kind.SYNTHESIZED;
    }

    public Void copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        throw new UnsupportedOperationException();
    }

    public Void newCopyBuilder() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFunctionDescriptor(this, data);
    }
}
