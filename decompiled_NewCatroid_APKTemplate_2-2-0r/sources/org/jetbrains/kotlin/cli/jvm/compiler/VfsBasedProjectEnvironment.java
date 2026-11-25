package org.jetbrains.kotlin.cli.jvm.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtIoFileSourceFile;
import org.jetbrains.kotlin.KtPsiSourceFile;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtVirtualFileSourceFile;
import org.jetbrains.kotlin.asJava.finder.JavaElementFinder;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.java.FirJavaElementFinder;
import org.jetbrains.kotlin.fir.java.FirJavaFacadeForSource;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.load.java.JavaClassFinderImplKt;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinderFactory;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;

/* JADX INFO: compiled from: VfsBasedProjectEnvironment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0016\u0010\u001c\u001a\u00020\u00162\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u001e\u0010 \u001a\u00020\u00162\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\"\u001a\u00020#H\u0016J\u001e\u0010$\u001a\u00020\u00162\f\u0010!\u001a\b\u0012\u0004\u0012\u00020%0\u001e2\b\b\u0002\u0010\"\u001a\u00020#J\u001e\u0010&\u001a\u00020\u00162\f\u0010!\u001a\b\u0012\u0004\u0012\u00020'0\u001e2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010(\u001a\u00020\u0016H\u0016J\b\u0010)\u001a\u00020\u0016H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J,\u0010,\u001a\u0014 .*\t\u0018\u00010\b¢\u0006\u0002\b-0\b¢\u0006\u0002\b-*\b\u0012\u0004\u0012\u0002000/2\u0006\u0010\"\u001a\u00020#H\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/VfsBasedProjectEnvironment;", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "localFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "getPackagePartProviderFn", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "(Lcom/intellij/openapi/project/Project;Lcom/intellij/openapi/vfs/VirtualFileSystem;Lkotlin/jvm/functions/Function1;)V", "getLocalFileSystem", "()Lcom/intellij/openapi/vfs/VirtualFileSystem;", "getProject", "()Lcom/intellij/openapi/project/Project;", "getFirJavaFacade", "Lorg/jetbrains/kotlin/fir/java/FirJavaFacadeForSource;", "firSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "baseModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "fileSearchScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "getJavaModuleResolver", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "getKotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "getPackagePartProvider", "getSearchScopeByDirectories", "directories", "", "Ljava/io/File;", "getSearchScopeByIoFiles", "files", "allowOutOfProjectRoots", "", "getSearchScopeByPsiFiles", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getSearchScopeBySourceFiles", "Lorg/jetbrains/kotlin/KtSourceFile;", "getSearchScopeForProjectJavaSources", "getSearchScopeForProjectLibraries", "registerAsJavaElementFinder", "", "toSearchScope", "Lorg/jetbrains/annotations/NotNull;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class VfsBasedProjectEnvironment implements AbstractProjectEnvironment {
    private final Function1<GlobalSearchScope, PackagePartProvider> getPackagePartProviderFn;
    private final VirtualFileSystem localFileSystem;
    private final Project project;

    /* JADX WARN: Multi-variable type inference failed */
    public VfsBasedProjectEnvironment(Project project, VirtualFileSystem localFileSystem, Function1<? super GlobalSearchScope, ? extends PackagePartProvider> getPackagePartProviderFn) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(localFileSystem, "localFileSystem");
        Intrinsics.checkNotNullParameter(getPackagePartProviderFn, "getPackagePartProviderFn");
        this.project = project;
        this.localFileSystem = localFileSystem;
        this.getPackagePartProviderFn = getPackagePartProviderFn;
    }

    public final Project getProject() {
        return this.project;
    }

    public final VirtualFileSystem getLocalFileSystem() {
        return this.localFileSystem;
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public KotlinClassFinder getKotlinClassFinder(AbstractProjectFileSearchScope fileSearchScope) {
        Intrinsics.checkNotNullParameter(fileSearchScope, "fileSearchScope");
        VirtualFileFinderFactory companion = VirtualFileFinderFactory.INSTANCE.getInstance(this.project);
        GlobalSearchScope globalSearchScopeAsPsiSearchScope = VfsBasedProjectEnvironmentKt.asPsiSearchScope(fileSearchScope);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAsPsiSearchScope, "access$asPsiSearchScope(...)");
        return companion.create(globalSearchScopeAsPsiSearchScope);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public JavaModuleResolver getJavaModuleResolver() {
        return JavaModuleResolver.INSTANCE.getInstance(this.project);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public PackagePartProvider getPackagePartProvider(AbstractProjectFileSearchScope fileSearchScope) {
        Intrinsics.checkNotNullParameter(fileSearchScope, "fileSearchScope");
        Function1<GlobalSearchScope, PackagePartProvider> function1 = this.getPackagePartProviderFn;
        GlobalSearchScope globalSearchScopeAsPsiSearchScope = VfsBasedProjectEnvironmentKt.asPsiSearchScope(fileSearchScope);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAsPsiSearchScope, "access$asPsiSearchScope(...)");
        return function1.invoke(globalSearchScopeAsPsiSearchScope);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public void registerAsJavaElementFinder(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "firSession");
        ExtensionPoint<PsiElementFinder> point = PsiElementFinder.EP.getPoint(this.project);
        Intrinsics.checkNotNullExpressionValue(point, "getPoint(...)");
        List<PsiElementFinder> extensionList = point.getExtensionList();
        Intrinsics.checkNotNullExpressionValue(extensionList, "getExtensionList(...)");
        List<PsiElementFinder> list = extensionList;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((PsiElementFinder) it.next()) instanceof JavaElementFinder) {
                    point.unregisterExtension(JavaElementFinder.class);
                    break;
                }
            }
        }
        point.registerExtension(new FirJavaElementFinder(firSession, this.project), this.project);
    }

    private final GlobalSearchScope toSearchScope(List<? extends VirtualFile> list, boolean z) {
        GlobalSearchScope globalSearchScopeFilesWithoutLibrariesScope;
        if (list.isEmpty()) {
            list = null;
        }
        if (list != null) {
            if (z) {
                globalSearchScopeFilesWithoutLibrariesScope = GlobalSearchScope.filesWithLibrariesScope(this.project, list);
            } else {
                globalSearchScopeFilesWithoutLibrariesScope = GlobalSearchScope.filesWithoutLibrariesScope(this.project, list);
            }
            if (globalSearchScopeFilesWithoutLibrariesScope != null) {
                return globalSearchScopeFilesWithoutLibrariesScope;
            }
        }
        return GlobalSearchScope.EMPTY_SCOPE;
    }

    public static /* synthetic */ AbstractProjectFileSearchScope getSearchScopeByPsiFiles$default(VfsBasedProjectEnvironment vfsBasedProjectEnvironment, Iterable iterable, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSearchScopeByPsiFiles");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return vfsBasedProjectEnvironment.getSearchScopeByPsiFiles(iterable, z);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public AbstractProjectFileSearchScope getSearchScopeForProjectLibraries() {
        GlobalSearchScope librariesScope = ProjectScope.getLibrariesScope(this.project);
        Intrinsics.checkNotNullExpressionValue(librariesScope, "getLibrariesScope(...)");
        return new PsiBasedProjectFileSearchScope(librariesScope);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public AbstractProjectFileSearchScope getSearchScopeForProjectJavaSources() {
        return new PsiBasedProjectFileSearchScope(new TopDownAnalyzerFacadeForJVM.AllJavaSourcesInProjectScope(this.project));
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public FirJavaFacadeForSource getFirJavaFacade(FirSession firSession, FirModuleData baseModuleData, AbstractProjectFileSearchScope fileSearchScope) {
        Intrinsics.checkNotNullParameter(firSession, "firSession");
        Intrinsics.checkNotNullParameter(baseModuleData, "baseModuleData");
        Intrinsics.checkNotNullParameter(fileSearchScope, "fileSearchScope");
        Project project = this.project;
        GlobalSearchScope globalSearchScopeAsPsiSearchScope = VfsBasedProjectEnvironmentKt.asPsiSearchScope(fileSearchScope);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAsPsiSearchScope, "access$asPsiSearchScope(...)");
        return new FirJavaFacadeForSource(firSession, baseModuleData, JavaClassFinderImplKt.createJavaClassFinder(project, globalSearchScopeAsPsiSearchScope));
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public AbstractProjectFileSearchScope getSearchScopeByIoFiles(Iterable<? extends File> files, boolean allowOutOfProjectRoots) {
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = files.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindFileByPath = this.localFileSystem.findFileByPath(it.next().getAbsolutePath());
            if (virtualFileFindFileByPath != null) {
                arrayList.add(virtualFileFindFileByPath);
            }
        }
        GlobalSearchScope searchScope = toSearchScope(arrayList, allowOutOfProjectRoots);
        Intrinsics.checkNotNullExpressionValue(searchScope, "toSearchScope(...)");
        return new PsiBasedProjectFileSearchScope(searchScope);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public AbstractProjectFileSearchScope getSearchScopeBySourceFiles(Iterable<? extends KtSourceFile> files, boolean allowOutOfProjectRoots) {
        VirtualFile virtualFileFindFileByPath;
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList();
        for (KtSourceFile ktSourceFile : files) {
            if (ktSourceFile instanceof KtPsiSourceFile) {
                virtualFileFindFileByPath = ((KtPsiSourceFile) ktSourceFile).getPsiFile().getVirtualFile();
            } else if (ktSourceFile instanceof KtVirtualFileSourceFile) {
                virtualFileFindFileByPath = ((KtVirtualFileSourceFile) ktSourceFile).getVirtualFile();
            } else {
                virtualFileFindFileByPath = ktSourceFile instanceof KtIoFileSourceFile ? this.localFileSystem.findFileByPath(((KtIoFileSourceFile) ktSourceFile).getFile().getAbsolutePath()) : null;
            }
            if (virtualFileFindFileByPath != null) {
                arrayList.add(virtualFileFindFileByPath);
            }
        }
        GlobalSearchScope searchScope = toSearchScope(arrayList, allowOutOfProjectRoots);
        Intrinsics.checkNotNullExpressionValue(searchScope, "toSearchScope(...)");
        return new PsiBasedProjectFileSearchScope(searchScope);
    }

    @Override // org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment
    public AbstractProjectFileSearchScope getSearchScopeByDirectories(Iterable<? extends File> directories) {
        KotlinToJVMBytecodeCompiler.DirectoriesScope directoriesScope;
        Intrinsics.checkNotNullParameter(directories, "directories");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = directories.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindFileByPath = this.localFileSystem.findFileByPath(it.next().getAbsolutePath());
            if (virtualFileFindFileByPath != null) {
                arrayList.add(virtualFileFindFileByPath);
            }
        }
        Set set = CollectionsKt.toSet(arrayList);
        if (set.isEmpty()) {
            set = null;
        }
        if (set != null) {
            directoriesScope = new KotlinToJVMBytecodeCompiler.DirectoriesScope(this.project, set);
        } else {
            directoriesScope = GlobalSearchScope.EMPTY_SCOPE;
        }
        Intrinsics.checkNotNull(directoriesScope);
        return new PsiBasedProjectFileSearchScope(directoriesScope);
    }

    public final AbstractProjectFileSearchScope getSearchScopeByPsiFiles(Iterable<? extends PsiFile> files, boolean allowOutOfProjectRoots) {
        GlobalSearchScope globalSearchScopeFilesWithoutLibrariesScope;
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(files, 10));
        Iterator<? extends PsiFile> it = files.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getVirtualFile());
        }
        ArrayList arrayList2 = arrayList;
        if (allowOutOfProjectRoots) {
            globalSearchScopeFilesWithoutLibrariesScope = GlobalSearchScope.filesWithLibrariesScope(this.project, arrayList2);
        } else {
            globalSearchScopeFilesWithoutLibrariesScope = GlobalSearchScope.filesWithoutLibrariesScope(this.project, arrayList2);
        }
        Intrinsics.checkNotNull(globalSearchScopeFilesWithoutLibrariesScope);
        return new PsiBasedProjectFileSearchScope(globalSearchScopeFilesWithoutLibrariesScope);
    }
}
