package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: FileBasedPackageMemberDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\"\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00100\u0018H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\n0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/FileBasedPackageMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "factory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/FileBasedDeclarationProviderFactory;", "packageFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/resolve/lazy/declarations/FileBasedDeclarationProviderFactory;Ljava/util/Collection;)V", "allDeclaredSubPackages", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "containsFile", "", "file", "doCreateIndex", "", "index", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider$Index;", "getAllDeclaredSubPackages", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "getPackageFiles", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileBasedPackageMemberDeclarationProvider extends AbstractPsiBasedDeclarationProvider implements PackageMemberDeclarationProvider {
    private final NotNullLazyValue<Collection<FqName>> allDeclaredSubPackages;
    private final FileBasedDeclarationProviderFactory factory;
    private final FqName fqName;
    private final Collection<KtFile> packageFiles;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FileBasedPackageMemberDeclarationProvider(StorageManager storageManager, FqName fqName, FileBasedDeclarationProviderFactory factory, Collection<? extends KtFile> packageFiles) {
        super(storageManager);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(packageFiles, "packageFiles");
        this.fqName = fqName;
        this.factory = factory;
        this.packageFiles = packageFiles;
        this.allDeclaredSubPackages = storageManager.createLazyValue(new Function0<Collection<? extends FqName>>() { // from class: org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedPackageMemberDeclarationProvider$allDeclaredSubPackages$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends FqName> invoke() {
                Collection<FqName> allDeclaredSubPackagesOf = this.this$0.factory.getAllDeclaredSubPackagesOf(this.this$0.fqName);
                Intrinsics.checkNotNullExpressionValue(allDeclaredSubPackagesOf, "getAllDeclaredSubPackagesOf(...)");
                return allDeclaredSubPackagesOf;
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.AbstractPsiBasedDeclarationProvider
    protected void doCreateIndex(AbstractPsiBasedDeclarationProvider.Index index) {
        Intrinsics.checkNotNullParameter(index, "index");
        for (KtFile ktFile : this.packageFiles) {
            for (KtDeclaration ktDeclaration : ktFile.getDeclarations()) {
                Intrinsics.areEqual(this.fqName, ktFile.getPackageFqName());
                index.putToIndex(ktDeclaration);
            }
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public Collection<FqName> getAllDeclaredSubPackages(Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.allDeclaredSubPackages.invoke();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public Collection<KtFile> getPackageFiles() {
        return this.packageFiles;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
    public boolean containsFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.packageFiles.contains(file);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("Declarations for package ").append(this.fqName).append(" with files ");
        Collection<KtFile> collection = this.packageFiles;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtFile) it.next()).getName());
        }
        StringBuilder sbAppend2 = sbAppend.append(arrayList).append(" with declarations inside ");
        Collection<KtFile> collection2 = this.packageFiles;
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = collection2.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, ((KtFile) it2.next()).getDeclarations());
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            String name2 = ((KtDeclaration) it3.next()).getName();
            if (name2 == null) {
                name2 = "???";
            }
            arrayList4.add(name2);
        }
        return sbAppend2.append(arrayList4).toString();
    }
}
