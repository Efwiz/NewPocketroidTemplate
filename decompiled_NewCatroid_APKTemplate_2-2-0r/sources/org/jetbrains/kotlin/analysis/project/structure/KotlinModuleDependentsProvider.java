package org.jetbrains.kotlin.analysis.project.structure;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: KotlinModuleDependentsProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KotlinModuleDependentsProvider;", "", "()V", "getDirectDependents", "", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "module", "getTransitiveDependents", "Companion", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinModuleDependentsProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract Set<KtModule> getDirectDependents(KtModule module);

    public abstract Set<KtModule> getTransitiveDependents(KtModule module);

    /* JADX INFO: compiled from: KotlinModuleDependentsProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KotlinModuleDependentsProvider$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/project/structure/KotlinModuleDependentsProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinModuleDependentsProvider getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(KotlinModuleDependentsProvider.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (KotlinModuleDependentsProvider) service;
        }
    }
}
