package org.jetbrains.kotlin.analysis.providers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: compiled from: KotlinMessageBusProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinMessageBusProvider;", "", "getMessageBus", "Lorg/jetbrains/kotlin/com/intellij/util/messages/MessageBus;", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinMessageBusProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    MessageBus getMessageBus();

    /* JADX INFO: compiled from: KotlinMessageBusProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinMessageBusProvider$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/providers/KotlinMessageBusProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final KotlinMessageBusProvider getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(KotlinMessageBusProvider.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (KotlinMessageBusProvider) service;
        }
    }
}
