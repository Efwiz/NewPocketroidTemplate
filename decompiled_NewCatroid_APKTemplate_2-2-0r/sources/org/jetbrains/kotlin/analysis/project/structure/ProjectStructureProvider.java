package org.jetbrains.kotlin.analysis.project.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: ProjectStructureProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/ProjectStructureProvider;", "", "()V", "getModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "contextualModule", "Companion", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ProjectStructureProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract KtModule getModule(PsiElement element, KtModule contextualModule);

    /* JADX INFO: compiled from: ProjectStructureProvider.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/ProjectStructureProvider$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/project/structure/ProjectStructureProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getModule", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "contextualModule", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ProjectStructureProvider getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(ProjectStructureProvider.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (ProjectStructureProvider) service;
        }

        public final KtModule getModule(Project project, PsiElement element, KtModule contextualModule) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(element, "element");
            return getInstance(project).getModule(element, contextualModule);
        }
    }
}
