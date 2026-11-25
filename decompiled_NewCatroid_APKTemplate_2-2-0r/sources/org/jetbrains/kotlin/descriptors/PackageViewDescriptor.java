package org.jetbrains.kotlin.descriptors;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: PackageViewDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0000H&J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "fragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getFragments", "()Ljava/util/List;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getContainingDeclaration", "isEmpty", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageViewDescriptor extends DeclarationDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    PackageViewDescriptor getModuleDescriptor();

    FqName getFqName();

    List<PackageFragmentDescriptor> getFragments();

    MemberScope getMemberScope();

    ModuleDescriptor getModule();

    boolean isEmpty();

    /* JADX INFO: compiled from: PackageViewDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isEmpty(PackageViewDescriptor packageViewDescriptor) {
            return packageViewDescriptor.getFragments().isEmpty();
        }
    }
}
