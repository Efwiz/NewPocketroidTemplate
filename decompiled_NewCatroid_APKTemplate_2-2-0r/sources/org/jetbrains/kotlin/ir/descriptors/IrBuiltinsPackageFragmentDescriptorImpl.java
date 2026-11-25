package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: IrBuiltinsPackageFragmentDescriptorImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\u00020\u00192\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a0\u0015H\u0016J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\b\u0010\u001f\u001a\u00020\u0003H\u0016J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u0010H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBuiltinsPackageFragmentDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "containingModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/name/FqName;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getContainingModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "shortName", "Lorg/jetbrains/kotlin/name/Name;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getContainingDeclaration", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getName", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBuiltinsPackageFragmentDescriptorImpl implements PackageFragmentDescriptor {
    private final Annotations annotations;
    private final ModuleDescriptor containingModule;
    private final FqName fqName;
    private final Name shortName;

    public IrBuiltinsPackageFragmentDescriptorImpl(ModuleDescriptor containingModule, FqName fqName) {
        Intrinsics.checkNotNullParameter(containingModule, "containingModule");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.containingModule = containingModule;
        this.fqName = fqName;
        Name nameShortName = getFqName().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        this.shortName = nameShortName;
        this.annotations = Annotations.INSTANCE.getEMPTY();
    }

    public final ModuleDescriptor getContainingModule() {
        return this.containingModule;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public FqName getFqName() {
        return this.fqName;
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName, reason: from getter */
    public Name getShortName() {
        return this.shortName;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration, reason: from getter */
    public ModuleDescriptor getContainingModule() {
        return this.containingModule;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public MemberScope getMemberScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptorWithSource getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    public SourceElement getSource() {
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitPackageFragmentDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.visitPackageFragmentDescriptor(this, null);
    }

    public boolean equals(Object other) {
        if (this != other) {
            if (other instanceof IrBuiltinsPackageFragmentDescriptorImpl) {
                IrBuiltinsPackageFragmentDescriptorImpl irBuiltinsPackageFragmentDescriptorImpl = (IrBuiltinsPackageFragmentDescriptorImpl) other;
                if (!Intrinsics.areEqual(getFqName(), irBuiltinsPackageFragmentDescriptorImpl.getFqName()) || !Intrinsics.areEqual(this.containingModule, irBuiltinsPackageFragmentDescriptorImpl.containingModule)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.containingModule.hashCode() * 31) + getFqName().hashCode();
    }
}
