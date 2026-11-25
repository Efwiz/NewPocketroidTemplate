package org.jetbrains.kotlin.analysis.project.structure;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: KtModule.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\rR\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\r\u0082\u0001\u0006 !\"#$%ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006&À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "contentScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getContentScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "directDependsOnDependencies", "", "getDirectDependsOnDependencies", "()Ljava/util/List;", "directFriendDependencies", "getDirectFriendDependencies", "directRegularDependencies", "getDirectRegularDependencies", "moduleDescription", "", "getModuleDescription", "()Ljava/lang/String;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getProject", "()Lcom/intellij/openapi/project/Project;", "transitiveDependsOnDependencies", "getTransitiveDependsOnDependencies", "Lorg/jetbrains/kotlin/analysis/project/structure/KtBinaryModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtLibrarySourceModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtNotUnderContentRootModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtScriptDependencyModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtScriptModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtSourceModule;", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtModule {
    PlatformDependentAnalyzerServices getAnalyzerServices();

    GlobalSearchScope getContentScope();

    List<KtModule> getDirectDependsOnDependencies();

    List<KtModule> getDirectFriendDependencies();

    List<KtModule> getDirectRegularDependencies();

    String getModuleDescription();

    TargetPlatform getPlatform();

    Project getProject();

    List<KtModule> getTransitiveDependsOnDependencies();
}
