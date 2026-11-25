package org.jetbrains.kotlin.analysis.providers.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinGlobalModificationService;
import org.jetbrains.kotlin.analysis.providers.KotlinMessageBusProviderKt;
import org.jetbrains.kotlin.analysis.providers.topics.KotlinGlobalModuleStateModificationListener;
import org.jetbrains.kotlin.analysis.providers.topics.KotlinGlobalSourceModuleStateModificationListener;
import org.jetbrains.kotlin.analysis.providers.topics.KotlinGlobalSourceOutOfBlockModificationListener;
import org.jetbrains.kotlin.analysis.providers.topics.KotlinTopics;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: KotlinGlobalModificationServiceBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH$J\b\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0016J\b\u0010\u000b\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinGlobalModificationServiceBase;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinGlobalModificationService;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "incrementModificationTrackers", "", "includeBinaryTrackers", "", "publishGlobalModuleStateModification", "publishGlobalSourceModuleStateModification", "publishGlobalSourceOutOfBlockModification", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinGlobalModificationServiceBase extends KotlinGlobalModificationService {
    private final Project project;

    protected abstract void incrementModificationTrackers(boolean includeBinaryTrackers);

    public KotlinGlobalModificationServiceBase(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinGlobalModificationService
    public void publishGlobalModuleStateModification() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        incrementModificationTrackers(true);
        ((KotlinGlobalModuleStateModificationListener) KotlinMessageBusProviderKt.getAnalysisMessageBus(this.project).syncPublisher(KotlinTopics.INSTANCE.getGLOBAL_MODULE_STATE_MODIFICATION())).onModification();
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinGlobalModificationService
    public void publishGlobalSourceModuleStateModification() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        incrementModificationTrackers(false);
        ((KotlinGlobalSourceModuleStateModificationListener) KotlinMessageBusProviderKt.getAnalysisMessageBus(this.project).syncPublisher(KotlinTopics.INSTANCE.getGLOBAL_SOURCE_MODULE_STATE_MODIFICATION())).onModification();
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinGlobalModificationService
    public void publishGlobalSourceOutOfBlockModification() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        incrementModificationTrackers(false);
        ((KotlinGlobalSourceOutOfBlockModificationListener) KotlinMessageBusProviderKt.getAnalysisMessageBus(this.project).syncPublisher(KotlinTopics.INSTANCE.getGLOBAL_SOURCE_OUT_OF_BLOCK_MODIFICATION())).onModification();
    }
}
