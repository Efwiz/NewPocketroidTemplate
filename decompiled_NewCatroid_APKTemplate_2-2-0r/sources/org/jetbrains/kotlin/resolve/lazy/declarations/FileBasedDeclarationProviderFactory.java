package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: loaded from: classes6.dex */
public class FileBasedDeclarationProviderFactory extends AbstractDeclarationProviderFactory {
    private final NotNullLazyValue<Index> index;
    private final StorageManager storageManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "files";
                break;
            case 3:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/declarations/FileBasedDeclarationProviderFactory";
                break;
            case 4:
                objArr[0] = "index";
                break;
            case 5:
                objArr[0] = "name";
                break;
            case 6:
            case 8:
                objArr[0] = "packageFqName";
                break;
            case 7:
                objArr[0] = "parent";
                break;
            case 9:
                objArr[0] = "classLikeInfo";
                break;
            default:
                objArr[0] = "storageManager";
                break;
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/resolve/lazy/declarations/FileBasedDeclarationProviderFactory";
        } else {
            objArr[1] = "computeFilesByPackage";
        }
        switch (i) {
            case 2:
                objArr[2] = "computeFilesByPackage";
                break;
            case 3:
                break;
            case 4:
            case 5:
                objArr[2] = "addMeAndParentPackages";
                break;
            case 6:
                objArr[2] = "packageExists";
                break;
            case 7:
                objArr[2] = "getAllDeclaredSubPackagesOf";
                break;
            case 8:
                objArr[2] = "createPackageMemberDeclarationProvider";
                break;
            case 9:
                objArr[2] = "getClassMemberDeclarationProvider";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Index {
        private final Set<FqName> declaredPackages;
        private final Multimap<FqName, KtFile> filesByPackage;

        private Index() {
            this.filesByPackage = LinkedHashMultimap.create();
            this.declaredPackages = new HashSet();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileBasedDeclarationProviderFactory(StorageManager storageManager, final Collection<KtFile> collection) {
        super(storageManager);
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        this.storageManager = storageManager;
        this.index = storageManager.createLazyValue(new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FileBasedDeclarationProviderFactory.computeFilesByPackage(collection);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Index computeFilesByPackage(Collection<KtFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(2);
        }
        Index index = new Index();
        for (KtFile ktFile : collection) {
            FqName packageFqName = ktFile.getPackageFqName();
            addMeAndParentPackages(index, packageFqName);
            index.filesByPackage.put(packageFqName, ktFile);
        }
        return index;
    }

    private static void addMeAndParentPackages(Index index, FqName fqName) {
        if (index == null) {
            $$$reportNull$$$0(4);
        }
        if (fqName == null) {
            $$$reportNull$$$0(5);
        }
        index.declaredPackages.add(fqName);
        if (fqName.isRoot()) {
            return;
        }
        addMeAndParentPackages(index, fqName.parent());
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.AbstractDeclarationProviderFactory
    public boolean packageExists(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(6);
        }
        return this.index.invoke().declaredPackages.contains(fqName);
    }

    Collection<FqName> getAllDeclaredSubPackagesOf(final FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(7);
        }
        return CollectionsKt.filter(this.index.invoke().declaredPackages, new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                FqName fqName2 = (FqName) obj;
                return Boolean.valueOf(!fqName2.isRoot() && fqName2.parent().equals(fqName));
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.AbstractDeclarationProviderFactory
    protected PackageMemberDeclarationProvider createPackageMemberDeclarationProvider(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(8);
        }
        if (packageExists(fqName)) {
            return new FileBasedPackageMemberDeclarationProvider(this.storageManager, fqName, this, this.index.invoke().filesByPackage.get(fqName));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
    public ClassMemberDeclarationProvider getClassMemberDeclarationProvider(KtClassLikeInfo ktClassLikeInfo) {
        if (ktClassLikeInfo == null) {
            $$$reportNull$$$0(9);
        }
        if (!this.index.invoke().filesByPackage.containsKey(ktClassLikeInfo.getContainingPackageFqName())) {
            throw new IllegalStateException("This factory doesn't know about this class: " + ktClassLikeInfo);
        }
        return new PsiBasedClassMemberDeclarationProvider(this.storageManager, ktClassLikeInfo);
    }
}
