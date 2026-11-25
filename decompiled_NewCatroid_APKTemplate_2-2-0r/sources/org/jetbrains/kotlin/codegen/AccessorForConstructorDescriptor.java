package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: AccessorForConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u0003B'\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ0\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\tH\u0016J\b\u0010\u001e\u001a\u00020\tH\u0016J\b\u0010\u001f\u001a\u00020\u0000H\u0016J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u001cH\u0016J\u0010\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%H\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0005\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForConstructorDescriptor;", "Lorg/jetbrains/kotlin/codegen/AbstractAccessorForFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "calleeDescriptor", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "superCallTarget", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "(Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/codegen/AccessorKind;)V", "getAccessorKind", "()Lorg/jetbrains/kotlin/codegen/AccessorKind;", "getCalleeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getSuperCallTarget", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "getConstructedClass", "getContainingDeclaration", "getOriginal", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isPrimary", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AccessorForConstructorDescriptor extends AbstractAccessorForFunctionDescriptor implements AccessorForCallableDescriptor<ConstructorDescriptor>, ClassConstructorDescriptor {
    private final AccessorKind accessorKind;
    private final ClassConstructorDescriptor calleeDescriptor;
    private final ClassDescriptor superCallTarget;

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public AccessorForConstructorDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor
    public boolean isPrimary() {
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public ClassConstructorDescriptor getCalleeDescriptor() {
        return this.calleeDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public ClassDescriptor getSuperCallTarget() {
        return this.superCallTarget;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public AccessorKind getAccessorKind() {
        return this.accessorKind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessorForConstructorDescriptor(ClassConstructorDescriptor calleeDescriptor, DeclarationDescriptor containingDeclaration, ClassDescriptor classDescriptor, AccessorKind accessorKind) {
        super(containingDeclaration, SpecialNames.INIT);
        Intrinsics.checkNotNullParameter(calleeDescriptor, "calleeDescriptor");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
        this.calleeDescriptor = calleeDescriptor;
        this.superCallTarget = classDescriptor;
        this.accessorKind = accessorKind;
        ReceiverParameterDescriptor extensionReceiverParameter = getCalleeDescriptor().getExtensionReceiverParameter();
        ReceiverParameterDescriptor receiverParameterDescriptorMo7203copy = extensionReceiverParameter != null ? extensionReceiverParameter.mo7203copy(this) : null;
        ReceiverParameterDescriptor dispatchReceiverParameter = getCalleeDescriptor().mo6753getDispatchReceiverParameter();
        List<ReceiverParameterDescriptor> contextReceiverParameters = getCalleeDescriptor().getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ReceiverParameterDescriptor) it.next()).mo7203copy(this));
        }
        initialize(receiverParameterDescriptorMo7203copy, dispatchReceiverParameter, (List) arrayList, (List) copyTypeParameters(getCalleeDescriptor()), (List) copyValueParameters(getCalleeDescriptor()), getCalleeDescriptor().getReturnType(), Modality.FINAL, DescriptorVisibilities.LOCAL);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public ClassDescriptor getContainingDeclaration() {
        return getCalleeDescriptor().getContainingDeclaration();
    }

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor
    public ClassDescriptor getConstructedClass() {
        ClassDescriptor constructedClass = getCalleeDescriptor().getConstructedClass();
        Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
        return constructedClass;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        KotlinType returnType = super.getReturnType();
        Intrinsics.checkNotNull(returnType);
        return returnType;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public ClassConstructorDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        FunctionDescriptor functionDescriptorSubstitute = super.substitute(substitutor);
        Intrinsics.checkNotNull(functionDescriptorSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        return (ClassConstructorDescriptor) functionDescriptorSubstitute;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    public AccessorForConstructorDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(kind, "kind");
        throw new UnsupportedOperationException();
    }
}
