package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: PackageFragmentDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassOrPackageFragmentDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageFragmentDescriptor extends ClassOrPackageFragmentDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ModuleDescriptor getContainingDeclaration();

    FqName getFqName();

    MemberScope getMemberScope();
}
