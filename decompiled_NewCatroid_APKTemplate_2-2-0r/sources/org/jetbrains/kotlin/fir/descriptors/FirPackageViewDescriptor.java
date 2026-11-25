package org.jetbrains.kotlin.fir.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: FirPackageViewDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J7\u0010\u001a\u001a\u0002H\u001b\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u001c2\u0014\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u001c\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u0002H\u001cH\u0016¢\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\"2\u0014\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020#\u0018\u00010\u001eH\u0016J\n\u0010$\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/descriptors/FirPackageViewDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "fragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getFragments", "()Ljava/util/List;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "module", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getContainingDeclaration", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPackageViewDescriptor implements PackageViewDescriptor {
    private final FqName fqName;
    private final ModuleDescriptor moduleDescriptor;

    public FirPackageViewDescriptor(FqName fqName, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.fqName = fqName;
        this.moduleDescriptor = moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public FqName getFqName() {
        return this.fqName;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public boolean isEmpty() {
        return PackageViewDescriptor.DefaultImpls.isEmpty(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public PackageViewDescriptor getPackageFragment() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public MemberScope getMemberScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    /* JADX INFO: renamed from: getModule, reason: from getter */
    public ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageViewDescriptor
    public List<PackageFragmentDescriptor> getFragments() {
        return CollectionsKt.listOf(new FirPackageFragmentDescriptor(getFqName(), this.moduleDescriptor));
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getOriginal() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return Annotations.INSTANCE.getEMPTY();
    }
}
