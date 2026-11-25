package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;

/* JADX INFO: loaded from: classes3.dex */
public abstract class VirtualFileSystem {
    public static final ExtensionPointName<KeyedLazyInstance<VirtualFileSystem>> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.virtualFileSystem");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem";
        } else if (i == 2) {
            objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
        } else if (i == 3) {
            objArr[0] = "disposable";
        } else if (i == 4) {
            objArr[0] = "name";
        } else if (i != 5) {
            objArr[0] = "path";
        } else {
            objArr[0] = "file";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem";
        } else {
            objArr[1] = "extractPresentableUrl";
        }
        if (i != 1) {
            if (i == 2 || i == 3) {
                objArr[2] = "addVirtualFileListener";
            } else if (i == 4) {
                objArr[2] = "isValidName";
            } else if (i != 5) {
                objArr[2] = "extractPresentableUrl";
            } else {
                objArr[2] = "getNioPath";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract void addVirtualFileListener(VirtualFileListener virtualFileListener);

    protected abstract VirtualFile copyFile(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2, String str) throws IOException;

    protected abstract VirtualFile createChildDirectory(Object obj, VirtualFile virtualFile, String str) throws IOException;

    protected abstract VirtualFile createChildFile(Object obj, VirtualFile virtualFile, String str) throws IOException;

    protected abstract void deleteFile(Object obj, VirtualFile virtualFile) throws IOException;

    public abstract VirtualFile findFileByPath(String str);

    public Path getNioPath(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return null;
        }
        $$$reportNull$$$0(5);
        return null;
    }

    public abstract String getProtocol();

    public boolean isCaseSensitive() {
        return true;
    }

    public abstract boolean isReadOnly();

    protected abstract void moveFile(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) throws IOException;

    public abstract void refresh(boolean z);

    public abstract VirtualFile refreshAndFindFileByPath(String str);

    /* JADX INFO: renamed from: removeVirtualFileListener, reason: merged with bridge method [inline-methods] */
    public abstract void m6510xbb63dbb0(VirtualFileListener virtualFileListener);

    protected abstract void renameFile(Object obj, VirtualFile virtualFile, String str) throws IOException;

    protected VirtualFileSystem() {
    }

    public String extractPresentableUrl(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        String strReplace = str.replace('/', File.separatorChar);
        if (strReplace == null) {
            $$$reportNull$$$0(1);
        }
        return strReplace;
    }

    public final void addVirtualFileListener(final VirtualFileListener virtualFileListener, Disposable disposable) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(2);
        }
        if (disposable == null) {
            $$$reportNull$$$0(3);
        }
        addVirtualFileListener(virtualFileListener);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6510xbb63dbb0(virtualFileListener);
            }
        });
    }

    public boolean isValidName(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return !str.isEmpty() && str.indexOf(92) < 0 && str.indexOf(47) < 0;
    }
}
