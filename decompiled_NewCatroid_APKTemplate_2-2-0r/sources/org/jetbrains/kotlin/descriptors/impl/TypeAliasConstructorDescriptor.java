package org.jetbrains.kotlin.descriptors.impl;

import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: TypeAliasConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J0\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&J\b\u0010\u0017\u001a\u00020\u0000H&J\b\u0010\u0018\u001a\u00020\u0019H&J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u001b\u001a\u00020\u001cH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/DescriptorDerivedFromTypeAlias;", "underlyingConstructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getUnderlyingConstructorDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "withDispatchReceiver", "getWithDispatchReceiver", "()Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "copyOverrides", "", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getOriginal", "getReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeAliasConstructorDescriptor extends ConstructorDescriptor, DescriptorDerivedFromTypeAlias {
    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    TypeAliasConstructorDescriptor copy(DeclarationDescriptor newOwner, Modality modality, DescriptorVisibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides);

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    TypeAliasDescriptor getDeclarationDescriptor();

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    TypeAliasConstructorDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    KotlinType getReturnType();

    ClassConstructorDescriptor getUnderlyingConstructorDescriptor();

    TypeAliasConstructorDescriptor getWithDispatchReceiver();

    @Override // org.jetbrains.kotlin.descriptors.ConstructorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    TypeAliasConstructorDescriptor substitute(TypeSubstitutor substitutor);
}
