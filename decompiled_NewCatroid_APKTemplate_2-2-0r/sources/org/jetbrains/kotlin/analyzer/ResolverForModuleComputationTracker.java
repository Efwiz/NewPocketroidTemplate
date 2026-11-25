package org.jetbrains.kotlin.analyzer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ResolverForModuleComputationTracker;", "", "onResolverComputed", "", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ResolverForModuleComputationTracker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void onResolverComputed(ModuleInfo moduleInfo);

    /* JADX INFO: compiled from: AnalyzerFacade.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ResolverForModuleComputationTracker$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analyzer/ResolverForModuleComputationTracker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ResolverForModuleComputationTracker getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            ResolverForModuleComputationTracker resolverForModuleComputationTracker = (ResolverForModuleComputationTracker) project.getComponent(ResolverForModuleComputationTracker.class);
            if (resolverForModuleComputationTracker == null) {
                return null;
            }
            return resolverForModuleComputationTracker;
        }
    }
}
