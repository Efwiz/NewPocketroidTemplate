package org.jetbrains.kotlin.com.intellij.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public class MockFileIndexFacade extends FileIndexFacade {
    private final List<VirtualFile> myLibraryRoots;
    private final Module myModule;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 8:
                objArr[0] = "baseDir";
                break;
            case 9:
                objArr[0] = "child";
                break;
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockFileIndexFacade";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i == 10) {
            objArr[1] = "getRootModificationTracker";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/mock/MockFileIndexFacade";
        } else {
            objArr[1] = "getUnloadedModuleDescriptions";
        }
        switch (i) {
            case 1:
                objArr[2] = "isInSource";
                break;
            case 2:
                objArr[2] = "isInSourceContent";
                break;
            case 3:
                objArr[2] = "isInLibraryClasses";
                break;
            case 4:
                objArr[2] = "isInLibrarySource";
                break;
            case 5:
                objArr[2] = "isExcludedFile";
                break;
            case 6:
                objArr[2] = "isUnderIgnored";
                break;
            case 7:
                objArr[2] = "getModuleForFile";
                break;
            case 8:
            case 9:
                objArr[2] = "isValidAncestor";
                break;
            case 10:
            case 11:
                break;
            default:
                objArr[2] = "isInContent";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isExcludedFile(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return false;
        }
        $$$reportNull$$$0(5);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isInContent(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return true;
        }
        $$$reportNull$$$0(0);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isInLibrarySource(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return false;
        }
        $$$reportNull$$$0(4);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isInSource(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isInSourceContent(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return true;
        }
        $$$reportNull$$$0(2);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isUnderIgnored(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return false;
        }
        $$$reportNull$$$0(6);
        return false;
    }

    public MockFileIndexFacade(Project project) {
        super(project);
        this.myLibraryRoots = new ArrayList();
        this.myModule = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public boolean isInLibraryClasses(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        Iterator<VirtualFile> it = this.myLibraryRoots.iterator();
        while (it.hasNext()) {
            if (VfsUtilCore.isAncestor(it.next(), virtualFile, false)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public Module getModuleForFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(7);
        }
        return this.myModule;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public ModificationTracker getRootModificationTracker() {
        ModificationTracker modificationTracker = ModificationTracker.NEVER_CHANGED;
        if (modificationTracker == null) {
            $$$reportNull$$$0(10);
        }
        return modificationTracker;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade
    public Collection<Object> getUnloadedModuleDescriptions() {
        Set setEmptySet = Collections.emptySet();
        if (setEmptySet == null) {
            $$$reportNull$$$0(11);
        }
        return setEmptySet;
    }

    public void addLibraryRoot(VirtualFile virtualFile) {
        this.myLibraryRoots.add(virtualFile);
    }
}
