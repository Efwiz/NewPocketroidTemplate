package org.jetbrains.kotlin.analysis.providers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: KotlinGlobalModificationService.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0004H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinGlobalModificationService;", "", "()V", "publishGlobalModuleStateModification", "", "publishGlobalSourceModuleStateModification", "publishGlobalSourceOutOfBlockModification", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinGlobalModificationService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract void publishGlobalModuleStateModification();

    public abstract void publishGlobalSourceModuleStateModification();

    public abstract void publishGlobalSourceOutOfBlockModification();

    /* JADX INFO: compiled from: KotlinGlobalModificationService.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinGlobalModificationService$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/providers/KotlinGlobalModificationService;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinGlobalModificationService getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(KotlinGlobalModificationService.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (KotlinGlobalModificationService) service;
        }
    }
}
