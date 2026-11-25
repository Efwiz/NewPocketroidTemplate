package org.jetbrains.kotlin.backend.jvm;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: OrphanedExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0092\u0001\u0010\b\u001a\n \n*\u0004\u0018\u0001H\tH\t\"\u0010\b\u0000\u0010\t*\n \n*\u0004\u0018\u00010\u000b0\u000b\"\u0010\b\u0001\u0010\f*\n \n*\u0004\u0018\u00010\u000b0\u000b2F\u0010\r\u001aB\u0012\f\u0012\n \n*\u0004\u0018\u0001H\tH\t\u0012\f\u0012\n \n*\u0004\u0018\u0001H\fH\f \n* \u0012\f\u0012\n \n*\u0004\u0018\u0001H\tH\t\u0012\f\u0012\n \n*\u0004\u0018\u0001H\fH\f\u0018\u00010\u000e0\u000e2\u000e\u0010\u000f\u001a\n \n*\u0004\u0018\u0001H\fH\fH\u0096\u0001¢\u0006\u0002\u0010\u0010JQ\u0010\u0011\u001a\u00020\u00122F\u0010\r\u001aB\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013 \n* \u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00130\u0013\u0018\u00010\u000e0\u000eH\u0096\u0001J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0097\u0001J\u0017\u0010\u0015\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00170\u00170\u0016H\u0097\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u0097\u0001J\u0017\u0010\u001a\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\u001c0\u001c0\u001bH\u0097\u0001J\u0017\u0010\u001d\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\u001e0\u001e0\u001bH\u0097\u0001J\u000b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0097\u0001J\t\u0010!\u001a\u00020 H\u0097\u0001J\t\u0010\"\u001a\u00020#H\u0097\u0001J7\u0010$\u001a\u00020%2,\b\u0001\u0010\r\u001a&\u0012\u000e\b\u0001\u0012\n \n*\u0004\u0018\u00010'0' \n*\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010'0'0\u001b0&H\u0097\u0001J\u0013\u0010$\u001a\u00020%2\b\b\u0001\u0010\r\u001a\u00020(H\u0097\u0001J\t\u0010)\u001a\u00020*H\u0097\u0001J\t\u0010+\u001a\u00020,H\u0097\u0001J\b\u0010-\u001a\u00020\u0001H\u0016J\u0017\u0010.\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\u00010\u00010\u0016H\u0097\u0001J\b\u0010/\u001a\u000200H\u0016J\t\u00101\u001a\u00020%H\u0097\u0001J\t\u00102\u001a\u00020\u001cH\u0097\u0001J\t\u00103\u001a\u000204H\u0097\u0001J\t\u00105\u001a\u00020%H\u0097\u0001J\t\u00106\u001a\u00020%H\u0097\u0001J\u000b\u00107\u001a\u0004\u0018\u00010\u0017H\u0097\u0001J\u0019\u00108\u001a\u0012\u0012\f\u0012\n \n*\u0004\u0018\u00010 0 \u0018\u000109H\u0097\u0001J\t\u0010:\u001a\u00020;H\u0097\u0001J\b\u0010<\u001a\u00020=H\u0016J\t\u0010>\u001a\u00020=H\u0096\u0001J\t\u0010?\u001a\u00020=H\u0096\u0001J\t\u0010@\u001a\u00020=H\u0096\u0001J\b\u0010A\u001a\u00020=H\u0016J\t\u0010B\u001a\u00020=H\u0096\u0001J\t\u0010C\u001a\u00020=H\u0096\u0001J\t\u0010D\u001a\u00020=H\u0096\u0001J\t\u0010E\u001a\u00020=H\u0096\u0001J\t\u0010F\u001a\u00020=H\u0096\u0001J\u0019\u0010G\u001a\n \n*\u0004\u0018\u00010H0H2\u0006\u0010I\u001a\u00020JH\u0096\u0001R\u0012\u0010\u0004\u001a\u00020\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/FakeActualClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "original", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getCompanionObjectDescriptor", "getConstructors", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getContextReceivers", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getDeclaredTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getDefaultFunctionTypeForSamInterface", "Lorg/jetbrains/kotlin/types/SimpleType;", "getDefaultType", "getKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSealedSubclasses", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStaticScope", "getThisAsReceiverParameter", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUnsubstitutedInnerClassesScope", "getUnsubstitutedMemberScope", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "", "isCompanionObject", "isData", "isDefinitelyNotSamInterface", "isExpect", "isExternal", "isFun", "isInline", "isInner", "isValue", "substitute", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FakeActualClassDescriptor implements ClassDescriptor {
    private final /* synthetic */ ClassDescriptor $$delegate_0;

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

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    public ClassDescriptor mo6346getCompanionObjectDescriptor() {
        return this.$$delegate_0.mo6346getCompanionObjectDescriptor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassConstructorDescriptor> getConstructors() {
        return this.$$delegate_0.getConstructors();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        return this.$$delegate_0.getContainingDeclaration();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ReceiverParameterDescriptor> getContextReceivers() {
        return this.$$delegate_0.getContextReceivers();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.$$delegate_0.getDeclaredTypeParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public SimpleType getDefaultFunctionTypeForSamInterface() {
        return this.$$delegate_0.getDefaultFunctionTypeForSamInterface();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        return this.$$delegate_0.getDefaultType();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        return this.$$delegate_0.getKind();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getMemberScope(List<? extends TypeProjection> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.$$delegate_0.getMemberScope(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getMemberScope(TypeSubstitution p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return this.$$delegate_0.getMemberScope(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return this.$$delegate_0.getModality();
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return this.$$delegate_0.getShortName();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public Collection<ClassDescriptor> getSealedSubclasses() {
        return this.$$delegate_0.getSealedSubclasses();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getStaticScope() {
        return this.$$delegate_0.getStaticScope();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        return this.$$delegate_0.getThisAsReceiverParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.$$delegate_0.getTypeConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getUnsubstitutedInnerClassesScope() {
        return this.$$delegate_0.getUnsubstitutedInnerClassesScope();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope getUnsubstitutedMemberScope() {
        return this.$$delegate_0.getUnsubstitutedMemberScope();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        return this.$$delegate_0.mo6347getUnsubstitutedPrimaryConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
        return this.$$delegate_0.getValueClassRepresentation();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        return this.$$delegate_0.getVisibility();
    }

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
    public boolean isActual() {
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: isCompanionObject */
    public boolean getIsCompanionObject() {
        return this.$$delegate_0.getIsCompanionObject();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isData() {
        return this.$$delegate_0.isData();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isDefinitelyNotSamInterface() {
        return this.$$delegate_0.isDefinitelyNotSamInterface();
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

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isFun() {
        return this.$$delegate_0.isFun();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isInline() {
        return this.$$delegate_0.isInline();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return this.$$delegate_0.getIsInner();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        return this.$$delegate_0.isValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    public ClassifierDescriptorWithTypeParameters substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this.$$delegate_0.substitute(substitutor);
    }

    public FakeActualClassDescriptor(ClassDescriptor original) {
        Intrinsics.checkNotNullParameter(original, "original");
        this.$$delegate_0 = original;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public ClassDescriptor getOriginal() {
        return this;
    }
}
