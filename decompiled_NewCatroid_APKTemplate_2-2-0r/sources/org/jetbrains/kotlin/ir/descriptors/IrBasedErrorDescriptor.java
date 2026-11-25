package org.jetbrains.kotlin.ir.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J7\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u00072\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\t2\u0006\u0010\n\u001a\u0002H\u0007H\u0016¢\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\r2\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0018\u00010\tH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedErrorDescriptor;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedDeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBasedErrorDescriptor extends IrBasedDeclarationDescriptor<IrErrorDeclaration> {
    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedErrorDescriptor(IrErrorDeclaration owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        throw new IllegalStateException("IrBasedErrorDescriptor.getName: Should not be reached".toString());
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptorWithSource getOriginal() {
        throw new IllegalStateException("IrBasedErrorDescriptor.getOriginal: Should not be reached".toString());
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedDeclarationDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    public DeclarationDescriptor getDeclarationDescriptor() {
        throw new IllegalStateException("IrBasedErrorDescriptor.getContainingDeclaration: Should not be reached".toString());
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        throw new IllegalStateException("IrBasedErrorDescriptor.accept: Should not be reached".toString());
    }
}
