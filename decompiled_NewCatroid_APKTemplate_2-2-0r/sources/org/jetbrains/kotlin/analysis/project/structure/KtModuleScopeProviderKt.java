package org.jetbrains.kotlin.analysis.project.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: KtModuleScopeProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"moduleScopeProvider", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModuleScopeProvider;", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getModuleScopeProvider", "(Lcom/intellij/openapi/project/Project;)Lorg/jetbrains/kotlin/analysis/project/structure/KtModuleScopeProvider;", "project-structure"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtModuleScopeProviderKt {
    public static final KtModuleScopeProvider getModuleScopeProvider(Project project) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        Object service = project.getService(KtModuleScopeProvider.class);
        Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
        return (KtModuleScopeProvider) service;
    }
}
