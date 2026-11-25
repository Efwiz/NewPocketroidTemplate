package org.jetbrains.kotlin.com.intellij.mock;

import org.jetbrains.kotlin.com.intellij.diagnostic.ActivityCategory;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.org.picocontainer.PicoContainer;

/* JADX INFO: loaded from: classes3.dex */
public class MockProject extends MockComponentManager implements Project {
    private static final Logger LOG = Logger.getInstance((Class<?>) MockProject.class);
    private VirtualFile myBaseDir;

    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockProject";
        } else if (i == 2) {
            objArr[0] = "componentType";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockProject";
        } else {
            objArr[0] = "parentDisposable";
        }
        if (i == 1) {
            objArr[1] = "getDisposed";
        } else if (i == 3) {
            objArr[1] = "getComponentInstancesOfType";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/mock/MockProject";
        } else {
            objArr[1] = "getActivityCategory";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "getComponentInstancesOfType";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public String getBasePath() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public VirtualFile getProjectFile() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public String getProjectFilePath() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public VirtualFile getWorkspaceFile() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public boolean isInitialized() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public boolean isOpen() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public void save() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MockProject(PicoContainer picoContainer, Disposable disposable) {
        super(picoContainer, disposable);
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.mock.MockComponentManager, org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public Condition<?> getDisposed() {
        return new Condition() { // from class: org.jetbrains.kotlin.com.intellij.mock.MockProject$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return this.f$0.m6469x31de1b44(obj);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$getDisposed$0$org-jetbrains-kotlin-com-intellij-mock-MockProject, reason: not valid java name */
    /* synthetic */ boolean m6469x31de1b44(Object obj) {
        return isDisposed();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public String getName() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public String getLocationHash() {
        return "mock";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.project.Project
    public VirtualFile getBaseDir() {
        return this.myBaseDir;
    }

    @Override // org.jetbrains.kotlin.com.intellij.mock.MockComponentManager, org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public final ActivityCategory getActivityCategory(boolean z) {
        ActivityCategory activityCategory = z ? ActivityCategory.PROJECT_EXTENSION : ActivityCategory.PROJECT_SERVICE;
        if (activityCategory == null) {
            $$$reportNull$$$0(4);
        }
        return activityCategory;
    }
}
