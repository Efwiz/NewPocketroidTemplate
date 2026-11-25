package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;

/* JADX INFO: loaded from: classes4.dex */
public interface ClassDescriptor extends ClassOrPackageFragmentDescriptor, ClassifierDescriptorWithTypeParameters, RegularClassSymbolMarker {
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    ClassDescriptor mo6346getCompanionObjectDescriptor();

    Collection<ClassConstructorDescriptor> getConstructors();

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    DeclarationDescriptor getContainingDeclaration();

    List<ReceiverParameterDescriptor> getContextReceivers();

    List<TypeParameterDescriptor> getDeclaredTypeParameters();

    SimpleType getDefaultFunctionTypeForSamInterface();

    SimpleType getDefaultType();

    ClassKind getKind();

    MemberScope getMemberScope(List<? extends TypeProjection> list);

    MemberScope getMemberScope(TypeSubstitution typeSubstitution);

    Modality getModality();

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ClassDescriptor getOriginal();

    Collection<ClassDescriptor> getSealedSubclasses();

    MemberScope getStaticScope();

    ReceiverParameterDescriptor getThisAsReceiverParameter();

    MemberScope getUnsubstitutedInnerClassesScope();

    MemberScope getUnsubstitutedMemberScope();

    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor();

    ValueClassRepresentation<SimpleType> getValueClassRepresentation();

    DescriptorVisibility getVisibility();

    boolean isCompanionObject();

    boolean isData();

    boolean isDefinitelyNotSamInterface();

    boolean isFun();

    boolean isInline();

    boolean isValue();
}
