package org.jetbrains.kotlin.analysis.providers.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: KotlinStaticGlobalModificationService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticGlobalModificationService;", "Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinGlobalModificationServiceBase;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "incrementModificationTrackers", "", "includeBinaryTrackers", "", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticGlobalModificationService extends KotlinGlobalModificationServiceBase {
    private final Project project;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinStaticGlobalModificationService(Project project) {
        super(project);
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.impl.KotlinGlobalModificationServiceBase
    protected void incrementModificationTrackers(boolean includeBinaryTrackers) {
        KotlinStaticModificationTrackerFactory.INSTANCE.getInstance(this.project).incrementModificationsCount$analysis_api_providers(includeBinaryTrackers);
    }
}
