package org.jetbrains.kotlin.analysis.providers.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.providers.KotlinModificationTrackerFactory;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.SimpleModificationTracker;

/* JADX INFO: compiled from: KotlinStaticModificationTrackerFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016J\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0000¢\u0006\u0002\b\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticModificationTrackerFactory;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinModificationTrackerFactory;", "()V", "librariesWide", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/SimpleModificationTracker;", "projectWide", "createLibrariesWideModificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "createProjectWideOutOfBlockModificationTracker", "incrementModificationsCount", "", "includeBinaryTrackers", "", "incrementModificationsCount$analysis_api_providers", "Companion", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticModificationTrackerFactory extends KotlinModificationTrackerFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final SimpleModificationTracker projectWide = new SimpleModificationTracker();
    private final SimpleModificationTracker librariesWide = new SimpleModificationTracker();

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinModificationTrackerFactory
    public ModificationTracker createProjectWideOutOfBlockModificationTracker() {
        return this.projectWide;
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinModificationTrackerFactory
    public ModificationTracker createLibrariesWideModificationTracker() {
        return this.librariesWide;
    }

    public final void incrementModificationsCount$analysis_api_providers(boolean includeBinaryTrackers) {
        this.projectWide.incModificationCount();
        if (includeBinaryTrackers) {
            this.librariesWide.incModificationCount();
        }
    }

    /* JADX INFO: compiled from: KotlinStaticModificationTrackerFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticModificationTrackerFactory$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticModificationTrackerFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinStaticModificationTrackerFactory getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            KotlinModificationTrackerFactory companion = KotlinModificationTrackerFactory.INSTANCE.getInstance(project);
            Intrinsics.checkNotNull(companion, "null cannot be cast to non-null type org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticModificationTrackerFactory");
            return (KotlinStaticModificationTrackerFactory) companion;
        }
    }
}
