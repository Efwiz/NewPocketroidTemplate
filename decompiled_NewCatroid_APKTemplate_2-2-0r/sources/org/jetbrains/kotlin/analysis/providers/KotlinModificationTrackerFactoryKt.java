package org.jetbrains.kotlin.analysis.providers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;

/* JADX INFO: compiled from: KotlinModificationTrackerFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0004"}, d2 = {"createAllLibrariesModificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "createProjectWideOutOfBlockModificationTracker", "analysis-api-providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinModificationTrackerFactoryKt {
    public static final ModificationTracker createProjectWideOutOfBlockModificationTracker(Project project) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        return KotlinModificationTrackerFactory.INSTANCE.getInstance(project).createProjectWideOutOfBlockModificationTracker();
    }

    public static final ModificationTracker createAllLibrariesModificationTracker(Project project) {
        Intrinsics.checkNotNullParameter(project, "<this>");
        return KotlinModificationTrackerFactory.INSTANCE.getInstance(project).createLibrariesWideModificationTracker();
    }
}
