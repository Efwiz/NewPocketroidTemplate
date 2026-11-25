package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: PackageMemberDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\"\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\nH&J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "containsFile", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getAllDeclaredSubPackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "getPackageFiles", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageMemberDeclarationProvider extends DeclarationProvider {
    boolean containsFile(KtFile file);

    Collection<FqName> getAllDeclaredSubPackages(Function1<? super Name, Boolean> nameFilter);

    Collection<KtFile> getPackageFiles();
}
