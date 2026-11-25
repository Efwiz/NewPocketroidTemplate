package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ProjectScopeBuilder {
    public abstract GlobalSearchScope buildAllScope();

    public abstract GlobalSearchScope buildContentScope();

    public abstract GlobalSearchScope buildEverythingScope();

    public abstract GlobalSearchScope buildLibrariesScope();

    public abstract GlobalSearchScope buildProjectScope();

    public static ProjectScopeBuilder getInstance(Project project) {
        return (ProjectScopeBuilder) project.getService(ProjectScopeBuilder.class);
    }
}
