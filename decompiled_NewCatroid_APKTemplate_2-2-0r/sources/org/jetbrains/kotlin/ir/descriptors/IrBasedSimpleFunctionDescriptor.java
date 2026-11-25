package org.jetbrains.kotlin.ir.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J?\u0010\u000b\u001a\n \r*\u0004\u0018\u0001H\fH\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\u000e2\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\u000e\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0010H\u0016J8\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\n\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u001fH\u0016J\b\u0010'\u001a\u00020\u001bH\u0016J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020\u0000H\u0016J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020%0,H\u0016J\b\u0010-\u001a\u00020.H\u0016J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u0002000,H\u0016J%\u00101\u001a\u0004\u0018\u0001H2\"\u0004\b\u0000\u001022\u000e\u00103\u001a\n\u0012\u0004\u0012\u0002H2\u0018\u000104H\u0016¢\u0006\u0002\u00105J\b\u00106\u001a\u00020\u001dH\u0016J\b\u00107\u001a\u00020!H\u0016J\b\u00108\u001a\u00020!H\u0016J\b\u00109\u001a\u00020!H\u0016J\b\u0010:\u001a\u00020!H\u0016J\b\u0010;\u001a\u00020!H\u0016J\b\u0010<\u001a\u00020!H\u0016J\b\u0010=\u001a\u00020!H\u0016J\b\u0010>\u001a\u00020!H\u0016J\b\u0010?\u001a\u00020!H\u0016J\b\u0010@\u001a\u00020!H\u0016J\u0010\u0010A\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010BH\u0016J\u0018\u0010C\u001a\u00020\u00142\u000e\u0010D\u001a\n\u0012\u0006\b\u0001\u0012\u00020F0EH\u0016J\u0010\u0010G\u001a\u00020\u00012\u0006\u0010H\u001a\u00020IH\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedSimpleFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DescriptorWithContainerSource;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedFunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "getDispatchReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getInitialSignatureDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getKind", "getModality", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getOverriddenDescriptors", "", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeParameters", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedTypeParameterDescriptor;", "getUserData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;)Ljava/lang/Object;", "getVisibility", "isActual", "isExpect", "isExternal", "isHiddenForResolutionEverywhereBesideSupercalls", "isHiddenToOvercomeSignatureClash", "isInfix", "isInline", "isOperator", "isSuspend", "isTailrec", "newCopyBuilder", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor$CopyBuilder;", "setOverriddenDescriptors", "overriddenDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBasedSimpleFunctionDescriptor extends IrBasedFunctionDescriptor<IrSimpleFunction> implements SimpleFunctionDescriptor, DescriptorWithContainerSource {
    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public FunctionDescriptor getInitialSignatureDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public IrBasedSimpleFunctionDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
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
    public boolean isOperator() {
        return false;
    }

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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedSimpleFunctionDescriptor(IrSimpleFunction owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<FunctionDescriptor> getOverriddenDescriptors() {
        List<IrSimpleFunctionSymbol> overriddenSymbols = ((IrSimpleFunction) getOwner()).getOverriddenSymbols();
        ArrayList arrayList = new ArrayList(overriddenSymbols.size());
        Iterator<T> it = overriddenSymbols.iterator();
        while (it.hasNext()) {
            arrayList.add(IrBasedDescriptorsKt.toIrBasedDescriptor(((IrSimpleFunctionSymbol) it.next()).getOwner()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return ((IrSimpleFunction) getOwner()).getModality();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return ((IrSimpleFunction) getOwner()).getName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return ((IrSimpleFunction) getOwner()).getVisibility();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return toIrBasedKotlinType(((IrSimpleFunction) getOwner()).getReturnType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    public ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        if (((IrSimpleFunction) getOwner()).getDispatchReceiverParameter() == null) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = getContainingDeclaration();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return ((ClassDescriptor) containingDeclaration).getThisAsReceiverParameter();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<IrBasedTypeParameterDescriptor> getTypeParameters() {
        List<IrTypeParameter> typeParameters = ((IrSimpleFunction) getOwner()).getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(IrBasedDescriptorsKt.toIrBasedDescriptor((IrTypeParameter) it.next()));
        }
        return CollectionsKt.compactIfPossible(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isExternal() {
        return ((IrSimpleFunction) getOwner()).getIsExternal();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isSuspend() {
        return ((IrSimpleFunction) getOwner()).getIsSuspend();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return ((IrSimpleFunction) getOwner()).getIsTailrec();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isInline() {
        return ((IrSimpleFunction) getOwner()).getIsInline();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    /* JADX INFO: renamed from: isExpect */
    public boolean get$isExpect() {
        return ((IrSimpleFunction) getOwner()).getIsExpect();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource
    public DeserializedContainerSource getContainerSource() {
        return ((IrSimpleFunction) getOwner()).getContainerSource();
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public SimpleFunctionDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        throw new NotImplementedError("An operation is not implemented: ");
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    /* JADX INFO: renamed from: setOverriddenDescriptors */
    public void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        Intrinsics.checkNotNullParameter(overriddenDescriptors, "overriddenDescriptors");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public CallableMemberDescriptor.Kind getKind() {
        return Intrinsics.areEqual(((IrSimpleFunction) getOwner()).getOrigin(), IrDeclarationOrigin.FAKE_OVERRIDE.INSTANCE) ? CallableMemberDescriptor.Kind.FAKE_OVERRIDE : CallableMemberDescriptor.Kind.SYNTHESIZED;
    }

    public Void copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNull(visitor);
        return visitor.visitFunctionDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNull(visitor);
        visitor.visitFunctionDescriptor(this, null);
    }
}
