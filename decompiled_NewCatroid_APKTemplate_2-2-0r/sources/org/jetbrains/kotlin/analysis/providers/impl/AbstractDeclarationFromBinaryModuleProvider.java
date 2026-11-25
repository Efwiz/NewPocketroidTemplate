package org.jetbrains.kotlin.analysis.providers.impl;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.analysis.project.structure.KtBinaryModule;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.roots.ContentIterator;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: AbstractDeclarationFromBinaryModuleProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J&\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000f0\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u0015H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\"À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/AbstractDeclarationFromBinaryModuleProvider;", "", "jarFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarFileSystem;", "getJarFileSystem", "()Lcom/intellij/openapi/vfs/impl/jar/CoreJarFileSystem;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "getPackagePartProvider", "()Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "findRoot", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "rootPath", "Ljava/nio/file/Path;", "fs", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "isCompiledFile", "", "virtualFile", "relativeFqName", "", "root", "virtualFilesFromKotlinModule", "", "binaryModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtBinaryModule;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "virtualFilesFromModule", "isPackageName", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AbstractDeclarationFromBinaryModuleProvider {
    CoreJarFileSystem getJarFileSystem();

    PackagePartProvider getPackagePartProvider();

    GlobalSearchScope getScope();

    default Set<VirtualFile> virtualFilesFromKotlinModule(KtBinaryModule binaryModule, FqName fqName) {
        Set setEmptySet;
        Intrinsics.checkNotNullParameter(binaryModule, "binaryModule");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<String> listFindPackageParts = getPackagePartProvider().findPackageParts(strAsString);
        if (!listFindPackageParts.isEmpty()) {
            Collection<Path> binaryRoots = binaryModule.getBinaryRoots();
            ArrayList arrayList = new ArrayList();
            for (Path path : binaryRoots) {
                if (!Files.isRegularFile(path, new LinkOption[0]) || !StringsKt.contains$default((CharSequence) path.toString(), (CharSequence) ".jar", false, 2, (Object) null)) {
                    setEmptySet = SetsKt.emptySet();
                } else {
                    Set setCreateSetBuilder = SetsKt.createSetBuilder();
                    Iterator<T> it = listFindPackageParts.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            setEmptySet = SetsKt.build(setCreateSetBuilder);
                            break;
                        }
                        VirtualFile virtualFileRefreshAndFindFileByPath = getJarFileSystem().refreshAndFindFileByPath(path.toAbsolutePath() + "!/" + ((String) it.next()) + ".class");
                        if (virtualFileRefreshAndFindFileByPath != null) {
                            Intrinsics.checkNotNull(virtualFileRefreshAndFindFileByPath);
                            setCreateSetBuilder.add(virtualFileRefreshAndFindFileByPath);
                        } else {
                            setEmptySet = SetsKt.emptySet();
                            break;
                        }
                    }
                }
                CollectionsKt.addAll(arrayList, setEmptySet);
            }
            return CollectionsKt.toSet(arrayList);
        }
        return SetsKt.emptySet();
    }

    default Set<VirtualFile> virtualFilesFromModule(KtBinaryModule binaryModule, FqName fqName, final boolean isPackageName) {
        Set setEmptySet;
        Intrinsics.checkNotNullParameter(binaryModule, "binaryModule");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        final String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        VirtualFileSystem virtualFileSystemLocal = StandardFileSystems.local();
        Collection<Path> binaryRoots = binaryModule.getBinaryRoots();
        ArrayList arrayList = new ArrayList();
        for (Path path : binaryRoots) {
            Intrinsics.checkNotNull(virtualFileSystemLocal);
            final VirtualFile virtualFileFindRoot = findRoot(path, virtualFileSystemLocal);
            if (virtualFileFindRoot == null) {
                setEmptySet = SetsKt.emptySet();
            } else {
                final LinkedHashSet linkedHashSet = new LinkedHashSet();
                VfsUtilCore.iterateChildrenRecursively(virtualFileFindRoot, new VirtualFileFilter() { // from class: org.jetbrains.kotlin.analysis.providers.impl.AbstractDeclarationFromBinaryModuleProvider$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter
                    public final boolean accept(VirtualFile virtualFile) {
                        return AbstractDeclarationFromBinaryModuleProvider.virtualFilesFromModule$lambda$5$lambda$3(virtualFileFindRoot, this, strAsString, isPackageName, virtualFile);
                    }
                }, new ContentIterator() { // from class: org.jetbrains.kotlin.analysis.providers.impl.AbstractDeclarationFromBinaryModuleProvider$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.ContentIterator
                    public final boolean processFile(VirtualFile virtualFile) {
                        return AbstractDeclarationFromBinaryModuleProvider.virtualFilesFromModule$lambda$5$lambda$4(this.f$0, linkedHashSet, virtualFile);
                    }
                });
                setEmptySet = linkedHashSet;
            }
            CollectionsKt.addAll(arrayList, setEmptySet);
        }
        return CollectionsKt.toSet(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static boolean virtualFilesFromModule$lambda$5$lambda$3(VirtualFile root, AbstractDeclarationFromBinaryModuleProvider this$0, String fqNameString, boolean z, VirtualFile it) {
        Intrinsics.checkNotNullParameter(root, "$root");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(fqNameString, "$fqNameString");
        Intrinsics.checkNotNullParameter(it, "it");
        if (Intrinsics.areEqual(it, root)) {
            return true;
        }
        String strRelativeFqName = this$0.relativeFqName(root, it);
        if (it.isDirectory() && StringsKt.startsWith$default(fqNameString, strRelativeFqName, false, 2, (Object) null)) {
            return true;
        }
        if (z) {
            VirtualFile parent = it.getParent();
            Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
            return StringsKt.endsWith$default(this$0.relativeFqName(root, parent), fqNameString, false, 2, (Object) null);
        }
        return Intrinsics.areEqual(strRelativeFqName, fqNameString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static boolean virtualFilesFromModule$lambda$5$lambda$4(AbstractDeclarationFromBinaryModuleProvider this$0, Set files, VirtualFile it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(files, "$files");
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.isDirectory() || !this$0.isCompiledFile(it) || !this$0.getScope().contains(it)) {
            return true;
        }
        files.add(it);
        return true;
    }

    private default VirtualFile findRoot(Path rootPath, VirtualFileSystem fs) {
        if (Files.isRegularFile(rootPath, new LinkOption[0]) && StringsKt.contains$default((CharSequence) rootPath.toString(), (CharSequence) ".jar", false, 2, (Object) null)) {
            return getJarFileSystem().refreshAndFindFileByPath(rootPath.toAbsolutePath() + "!/");
        }
        return fs.findFileByPath(rootPath.toAbsolutePath().toString());
    }

    private default String relativeFqName(VirtualFile root, VirtualFile virtualFile) {
        String strReplace$default;
        if (root.isDirectory()) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            while (!Intrinsics.areEqual(virtualFile, root)) {
                listCreateListBuilder.add(virtualFile.getNameWithoutExtension());
                virtualFile = virtualFile.getParent();
                Intrinsics.checkNotNullExpressionValue(virtualFile, "getParent(...)");
            }
            return CollectionsKt.joinToString$default(CollectionsKt.reversed(CollectionsKt.build(listCreateListBuilder)), ".", null, null, 0, null, null, 62, null);
        }
        String path = virtualFile.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        String str = (String) CollectionsKt.lastOrNull(StringsKt.split$default((CharSequence) path, new String[]{"!/"}, false, 0, 6, (Object) null));
        return (str == null || (strReplace$default = StringsKt.replace$default(str, "/", ".", false, 4, (Object) null)) == null) ? "!/" : strReplace$default;
    }

    private default boolean isCompiledFile(VirtualFile virtualFile) {
        String extension = virtualFile.getExtension();
        if (extension == null) {
            return false;
        }
        String defaultExtension = JavaClassFileType.INSTANCE.getDefaultExtension();
        Intrinsics.checkNotNullExpressionValue(defaultExtension, "getDefaultExtension(...)");
        return StringsKt.endsWith$default(extension, defaultExtension, false, 2, (Object) null);
    }
}
