package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.mpp.ConstructorSymbolMarker;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: ClassConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J0\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u0000H&J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0012\u001a\u00020\u0013H&¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/mpp/ConstructorSymbolMarker;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getOriginal", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassConstructorDescriptor extends ConstructorDescriptor, ConstructorSymbolMarker {
    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    ClassConstructorDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides);

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ClassDescriptor getContainingDeclaration();

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ClassConstructorDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    ClassConstructorDescriptor substitute(TypeSubstitutor substitutor);
}
