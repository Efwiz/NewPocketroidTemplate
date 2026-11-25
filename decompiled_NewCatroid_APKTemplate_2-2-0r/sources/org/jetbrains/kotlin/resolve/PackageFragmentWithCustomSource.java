package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0092\u0001\u0010\u000e\u001a\n \u0010*\u0004\u0018\u0001H\u000fH\u000f\"\u0010\b\u0000\u0010\u000f*\n \u0010*\u0004\u0018\u00010\u00110\u0011\"\u0010\b\u0001\u0010\u0012*\n \u0010*\u0004\u0018\u00010\u00110\u00112F\u0010\u0013\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000fH\u000f\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u0012H\u0012 \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u000fH\u000f\u0012\f\u0012\n \u0010*\u0004\u0018\u0001H\u0012H\u0012\u0018\u00010\u00140\u00142\u000e\u0010\u0015\u001a\n \u0010*\u0004\u0018\u0001H\u0012H\u0012H\u0096\u0001¢\u0006\u0002\u0010\u0016JQ\u0010\u0017\u001a\u00020\u00182F\u0010\u0013\u001aB\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00190\u0019\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00190\u0019 \u0010* \u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00190\u0019\u0012\f\u0012\n \u0010*\u0004\u0018\u00010\u00190\u0019\u0018\u00010\u00140\u0014H\u0096\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u0096\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u0096\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u0097\u0001J\t\u0010 \u001a\u00020!H\u0097\u0001J\b\u0010\"\u001a\u00020\u0004H\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/PackageFragmentWithCustomSource;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "original", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "D", "p0", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "p1", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "getSource", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PackageFragmentWithCustomSource implements PackageFragmentDescriptor {
    private final PackageFragmentDescriptor original;
    private final SourceElement source;

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> p0, D p1) {
        return (R) this.original.accept(p0, p1);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> p0) {
        this.original.mo7454acceptVoid(p0);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.original.getAnnotations();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public ModuleDescriptor getContainingModule() {
        return this.original.getContainingModule();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public FqName getFqName() {
        return this.original.getFqName();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public MemberScope getMemberScope() {
        return this.original.getMemberScope();
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return this.original.getShortName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptorWithSource getOriginal() {
        return this.original.getOriginal();
    }

    public PackageFragmentWithCustomSource(PackageFragmentDescriptor original, SourceElement source) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(source, "source");
        this.original = original;
        this.source = source;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        return this.source;
    }
}
