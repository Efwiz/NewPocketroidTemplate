package org.jetbrains.kotlin.com.intellij.openapi.roots;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ProjectRootModificationTracker implements ModificationTracker {
    private static final ProjectRootModificationTracker NEVER_CHANGED = new ProjectRootModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.openapi.roots.ProjectRootModificationTracker.1
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
        public long getModificationCount() {
            return 0L;
        }
    };

    public static ProjectRootModificationTracker getInstance(Project project) {
        ProjectRootModificationTracker projectRootModificationTracker = (ProjectRootModificationTracker) project.getService(ProjectRootModificationTracker.class);
        return projectRootModificationTracker == null ? NEVER_CHANGED : projectRootModificationTracker;
    }
}
