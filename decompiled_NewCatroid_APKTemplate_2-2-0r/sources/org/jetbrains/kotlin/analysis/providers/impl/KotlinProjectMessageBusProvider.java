package org.jetbrains.kotlin.analysis.providers.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinMessageBusProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: compiled from: KotlinProjectMessageBusProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinProjectMessageBusProvider;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinMessageBusProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "getMessageBus", "Lorg/jetbrains/kotlin/com/intellij/util/messages/MessageBus;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinProjectMessageBusProvider implements KotlinMessageBusProvider {
    private final Project project;

    public KotlinProjectMessageBusProvider(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinMessageBusProvider
    public MessageBus getMessageBus() {
        MessageBus messageBus = this.project.getMessageBus();
        Intrinsics.checkNotNullExpressionValue(messageBus, "getMessageBus(...)");
        return messageBus;
    }
}
