package org.jetbrains.kotlin.fir.session.environment;

import java.io.File;
import kotlin.Metadata;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.java.FirJavaFacade;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;

/* JADX INFO: compiled from: AbstractProjectEnvironment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH&J\u0016\u0010\u0010\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H&J \u0010\u0014\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H&J \u0010\u0018\u001a\u00020\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00190\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H&J\b\u0010\u001a\u001a\u00020\tH&J\b\u0010\u001b\u001a\u00020\tH&J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "", "getFirJavaFacade", "Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;", "firSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "baseModuleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "fileSearchScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "getJavaModuleResolver", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "getKotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "getPackagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "getSearchScopeByDirectories", "directories", "", "Ljava/io/File;", "getSearchScopeByIoFiles", "files", "allowOutOfProjectRoots", "", "getSearchScopeBySourceFiles", "Lorg/jetbrains/kotlin/KtSourceFile;", "getSearchScopeForProjectJavaSources", "getSearchScopeForProjectLibraries", "registerAsJavaElementFinder", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AbstractProjectEnvironment {
    FirJavaFacade getFirJavaFacade(FirSession firSession, FirModuleData baseModuleData, AbstractProjectFileSearchScope fileSearchScope);

    JavaModuleResolver getJavaModuleResolver();

    KotlinClassFinder getKotlinClassFinder(AbstractProjectFileSearchScope fileSearchScope);

    PackagePartProvider getPackagePartProvider(AbstractProjectFileSearchScope fileSearchScope);

    AbstractProjectFileSearchScope getSearchScopeByDirectories(Iterable<? extends File> directories);

    AbstractProjectFileSearchScope getSearchScopeByIoFiles(Iterable<? extends File> files, boolean allowOutOfProjectRoots);

    AbstractProjectFileSearchScope getSearchScopeBySourceFiles(Iterable<? extends KtSourceFile> files, boolean allowOutOfProjectRoots);

    AbstractProjectFileSearchScope getSearchScopeForProjectJavaSources();

    AbstractProjectFileSearchScope getSearchScopeForProjectLibraries();

    void registerAsJavaElementFinder(FirSession firSession);

    static /* synthetic */ AbstractProjectFileSearchScope getSearchScopeByIoFiles$default(AbstractProjectEnvironment abstractProjectEnvironment, Iterable iterable, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSearchScopeByIoFiles");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return abstractProjectEnvironment.getSearchScopeByIoFiles(iterable, z);
    }

    static /* synthetic */ AbstractProjectFileSearchScope getSearchScopeBySourceFiles$default(AbstractProjectEnvironment abstractProjectEnvironment, Iterable iterable, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSearchScopeBySourceFiles");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return abstractProjectEnvironment.getSearchScopeBySourceFiles(iterable, z);
    }
}
