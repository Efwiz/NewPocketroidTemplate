package org.jetbrains.kotlin.com.intellij.mock;

import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public class MockDumbService extends DumbService {
    private final Project myProject;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.DumbService
    public void completeJustSubmittedTasks() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.DumbService
    public boolean isAlternativeResolveEnabled() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.DumbService
    public boolean isDumb() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.DumbService
    public void waitForSmartMode() {
    }

    public MockDumbService(Project project) {
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.DumbService
    public Project getProject() {
        return this.myProject;
    }
}
