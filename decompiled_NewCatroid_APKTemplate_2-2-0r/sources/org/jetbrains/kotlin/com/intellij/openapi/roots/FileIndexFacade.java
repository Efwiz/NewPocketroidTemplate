package org.jetbrains.kotlin.com.intellij.openapi.roots;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FileIndexFacade {
    protected final Project myProject;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "project";
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/roots/FileIndexFacade";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "isInProjectScope";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract Module getModuleForFile(VirtualFile virtualFile);

    public abstract ModificationTracker getRootModificationTracker();

    public abstract Collection<Object> getUnloadedModuleDescriptions();

    public abstract boolean isExcludedFile(VirtualFile virtualFile);

    public abstract boolean isInContent(VirtualFile virtualFile);

    public abstract boolean isInLibraryClasses(VirtualFile virtualFile);

    public abstract boolean isInLibrarySource(VirtualFile virtualFile);

    public abstract boolean isInSource(VirtualFile virtualFile);

    public abstract boolean isInSourceContent(VirtualFile virtualFile);

    public abstract boolean isUnderIgnored(VirtualFile virtualFile);

    protected FileIndexFacade(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myProject = project;
    }

    public static FileIndexFacade getInstance(Project project) {
        return (FileIndexFacade) project.getService(FileIndexFacade.class);
    }

    public boolean isInProjectScope(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        if (!isInLibraryClasses(virtualFile) || isInSourceContent(virtualFile)) {
            return isInContent(virtualFile);
        }
        return false;
    }
}
