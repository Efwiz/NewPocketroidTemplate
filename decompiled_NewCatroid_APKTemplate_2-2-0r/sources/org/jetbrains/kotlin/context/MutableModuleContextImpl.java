package org.jetbrains.kotlin.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.storage.ExceptionTracker;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/context/MutableModuleContextImpl;", "Lorg/jetbrains/kotlin/context/MutableModuleContext;", "Lorg/jetbrains/kotlin/context/ProjectContext;", "module", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "projectContext", "(Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;Lorg/jetbrains/kotlin/context/ProjectContext;)V", "exceptionTracker", "Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "getExceptionTracker", "()Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getProject", "()Lcom/intellij/openapi/project/Project;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableModuleContextImpl implements MutableModuleContext, ProjectContext {
    private final /* synthetic */ ProjectContext $$delegate_0;
    private final ModuleDescriptorImpl module;

    @Override // org.jetbrains.kotlin.context.GlobalContext
    public ExceptionTracker getExceptionTracker() {
        return this.$$delegate_0.getExceptionTracker();
    }

    @Override // org.jetbrains.kotlin.context.ProjectContext
    public Project getProject() {
        return this.$$delegate_0.getProject();
    }

    @Override // org.jetbrains.kotlin.context.GlobalContext
    public StorageManager getStorageManager() {
        return this.$$delegate_0.getStorageManager();
    }

    public MutableModuleContextImpl(ModuleDescriptorImpl module, ProjectContext projectContext) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(projectContext, "projectContext");
        this.module = module;
        this.$$delegate_0 = projectContext;
    }

    @Override // org.jetbrains.kotlin.context.ModuleContext
    public ModuleDescriptorImpl getModule() {
        return this.module;
    }
}
