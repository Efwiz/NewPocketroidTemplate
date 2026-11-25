package org.jetbrains.kotlin.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: typeParameterUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0092\u0001\u0010\f\u001a\n \u000e*\u0004\u0018\u0001H\rH\r\"\u0010\b\u0000\u0010\r*\n \u000e*\u0004\u0018\u00010\u000f0\u000f\"\u0010\b\u0001\u0010\u0010*\n \u000e*\u0004\u0018\u00010\u000f0\u000f2F\u0010\u0011\u001aB\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\rH\r\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0010H\u0010 \u000e* \u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\rH\r\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0010H\u0010\u0018\u00010\u00120\u00122\u000e\u0010\u0013\u001a\n \u000e*\u0004\u0018\u0001H\u0010H\u0010H\u0096\u0001¢\u0006\u0002\u0010\u0014JQ\u0010\u0015\u001a\u00020\u00162F\u0010\u0011\u001aB\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017 \u000e* \u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00170\u0017\u0018\u00010\u00120\u0012H\u0096\u0001J\b\u0010\u0018\u001a\u00020\u0004H\u0016J\t\u0010\u0019\u001a\u00020\u001aH\u0097\u0001J\b\u0010\u001b\u001a\u00020\u0006H\u0016J\t\u0010\u001c\u001a\u00020\u001dH\u0097\u0001J\b\u0010\u001e\u001a\u00020\u0001H\u0016J\t\u0010\u001f\u001a\u00020 H\u0097\u0001J\t\u0010!\u001a\u00020\"H\u0097\u0001J\t\u0010#\u001a\u00020$H\u0097\u0001J+\u0010%\u001a$\u0012\f\u0012\n \u000e*\u0004\u0018\u00010'0' \u000e*\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010'0'0(0&H\u0097\u0001J\t\u0010)\u001a\u00020*H\u0097\u0001J\b\u0010+\u001a\u00020,H\u0016J\t\u0010-\u001a\u00020,H\u0096\u0001J\b\u0010.\u001a\u00020/H\u0016R\u0012\u0010\b\u001a\u00020\tX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/CapturedTypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "originalDescriptor", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaredTypeParametersCount", "", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;I)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getContainingDeclaration", "getDefaultType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getIndex", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStorageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUpperBounds", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "", "getVariance", "Lorg/jetbrains/kotlin/types/Variance;", "isCapturedFromOuterDeclaration", "", "isReified", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CapturedTypeParameterDescriptor implements TypeParameterDescriptor {
    private final DeclarationDescriptor declarationDescriptor;
    private final int declaredTypeParametersCount;
    private final TypeParameterDescriptor originalDescriptor;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        return (R) this.originalDescriptor.accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        this.originalDescriptor.mo7454acceptVoid(visitor);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.originalDescriptor.getAnnotations();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public SimpleType getDefaultType() {
        return this.originalDescriptor.getDefaultType();
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return this.originalDescriptor.getShortName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return this.originalDescriptor.getSourceElement();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public StorageManager getStorageManager() {
        return this.originalDescriptor.getStorageManager();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.originalDescriptor.getTypeConstructor();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public List<KotlinType> getUpperBounds() {
        return this.originalDescriptor.getUpperBounds();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public Variance getVariance() {
        return this.originalDescriptor.getVariance();
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public boolean isCapturedFromOuterDeclaration() {
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public boolean isReified() {
        return this.originalDescriptor.isReified();
    }

    public CapturedTypeParameterDescriptor(TypeParameterDescriptor originalDescriptor, DeclarationDescriptor declarationDescriptor, int i) {
        Intrinsics.checkNotNullParameter(originalDescriptor, "originalDescriptor");
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        this.originalDescriptor = originalDescriptor;
        this.declarationDescriptor = declarationDescriptor;
        this.declaredTypeParametersCount = i;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public TypeParameterDescriptor getOriginal() {
        TypeParameterDescriptor original = this.originalDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return original;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration, reason: from getter */
    public DeclarationDescriptor getDeclarationDescriptor() {
        return this.declarationDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
    public int getIndex() {
        return this.declaredTypeParametersCount + this.originalDescriptor.getIndex();
    }

    public String toString() {
        return this.originalDescriptor + "[inner-copy]";
    }
}
