package org.jetbrains.kotlin.analysis.project.structure;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: KtModule.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0096\u0002J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J\b\u0010)\u001a\u00020*H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0013¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtBuiltinsModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtBinaryModule;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/platform/TargetPlatform;Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;Lcom/intellij/openapi/project/Project;)V", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "contentScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getContentScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "directDependsOnDependencies", "", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "getDirectDependsOnDependencies", "()Ljava/util/List;", "directFriendDependencies", "getDirectFriendDependencies", "directRegularDependencies", "getDirectRegularDependencies", "moduleDescription", "", "getModuleDescription", "()Ljava/lang/String;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getProject", "()Lcom/intellij/openapi/project/Project;", "transitiveDependsOnDependencies", "getTransitiveDependsOnDependencies", Namer.EQUALS_METHOD_NAME, "", "other", "", "getBinaryRoots", "", "Ljava/nio/file/Path;", "hashCode", "", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtBuiltinsModule implements KtBinaryModule {
    private final PlatformDependentAnalyzerServices analyzerServices;
    private final TargetPlatform platform;
    private final Project project;

    public KtBuiltinsModule(TargetPlatform platform, PlatformDependentAnalyzerServices analyzerServices, Project project) {
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(analyzerServices, "analyzerServices");
        Intrinsics.checkNotNullParameter(project, "project");
        this.platform = platform;
        this.analyzerServices = analyzerServices;
        this.project = project;
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public TargetPlatform getPlatform() {
        return this.platform;
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public PlatformDependentAnalyzerServices getAnalyzerServices() {
        return this.analyzerServices;
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public Project getProject() {
        return this.project;
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public List<KtModule> getDirectRegularDependencies() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public List<KtModule> getDirectDependsOnDependencies() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public List<KtModule> getTransitiveDependsOnDependencies() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public List<KtModule> getDirectFriendDependencies() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public GlobalSearchScope getContentScope() {
        GlobalSearchScope EMPTY_SCOPE = GlobalSearchScope.EMPTY_SCOPE;
        Intrinsics.checkNotNullExpressionValue(EMPTY_SCOPE, "EMPTY_SCOPE");
        return EMPTY_SCOPE;
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtBinaryModule
    public Collection<Path> getBinaryRoots() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    public String getModuleDescription() {
        return "Builtins for " + getPlatform();
    }

    public boolean equals(Object other) {
        return (other instanceof KtBuiltinsModule) && Intrinsics.areEqual(getPlatform(), ((KtBuiltinsModule) other).getPlatform());
    }

    public int hashCode() {
        return getPlatform().hashCode();
    }
}
