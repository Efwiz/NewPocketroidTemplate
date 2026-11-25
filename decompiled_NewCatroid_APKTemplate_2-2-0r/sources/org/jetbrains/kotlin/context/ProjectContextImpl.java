package org.jetbrains.kotlin.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.storage.ExceptionTracker;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0005\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/context/ProjectContextImpl;", "Lorg/jetbrains/kotlin/context/ProjectContext;", "Lorg/jetbrains/kotlin/context/GlobalContext;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "globalContext", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/context/GlobalContext;)V", "exceptionTracker", "Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "getExceptionTracker", "()Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "getProject", "()Lcom/intellij/openapi/project/Project;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectContextImpl implements GlobalContext, ProjectContext {
    private final GlobalContext globalContext;
    private final Project project;

    @Override // org.jetbrains.kotlin.context.GlobalContext
    public ExceptionTracker getExceptionTracker() {
        return this.globalContext.getExceptionTracker();
    }

    @Override // org.jetbrains.kotlin.context.GlobalContext
    public StorageManager getStorageManager() {
        return this.globalContext.getStorageManager();
    }

    public ProjectContextImpl(Project project, GlobalContext globalContext) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(globalContext, "globalContext");
        this.project = project;
        this.globalContext = globalContext;
    }

    @Override // org.jetbrains.kotlin.context.ProjectContext
    public Project getProject() {
        return this.project;
    }
}
