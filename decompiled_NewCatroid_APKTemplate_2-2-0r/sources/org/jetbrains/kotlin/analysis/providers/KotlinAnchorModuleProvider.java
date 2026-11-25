package org.jetbrains.kotlin.analysis.providers;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.project.structure.KtLibraryModule;
import org.jetbrains.kotlin.analysis.project.structure.KtSourceModule;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: KotlinAnchorModuleProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinAnchorModuleProvider;", "", "getAllAnchorModules", "", "Lorg/jetbrains/kotlin/analysis/project/structure/KtSourceModule;", "getAnchorModule", "libraryModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtLibraryModule;", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinAnchorModuleProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Collection<KtSourceModule> getAllAnchorModules();

    KtSourceModule getAnchorModule(KtLibraryModule libraryModule);

    /* JADX INFO: compiled from: KotlinAnchorModuleProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinAnchorModuleProvider$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/providers/KotlinAnchorModuleProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final KotlinAnchorModuleProvider getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            return (KotlinAnchorModuleProvider) project.getService(KotlinAnchorModuleProvider.class);
        }
    }
}
