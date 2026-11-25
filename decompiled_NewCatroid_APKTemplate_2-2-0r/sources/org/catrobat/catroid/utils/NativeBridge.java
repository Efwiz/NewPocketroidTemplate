package org.catrobat.catroid.utils;

import android.view.Surface;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NativeBridge.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeBridge.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0086 J\t\u0010\r\u001a\u00020\tH\u0086 J\u0011\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086 J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u000bH\u0007J!\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0086 J\u0019\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086 J\u0011\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086 J1\u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0013H\u0086 J\u0011\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u000bH\u0086 R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006!"}, d2 = {"Lorg/catrobat/catroid/utils/NativeBridge;", "", "()V", "isWorking", "", "()Z", "setWorking", "(Z)V", "attachSoToView", "", "viewName", "", "pathToSo", "cleanupAllInstances", "cleanupInstance", "getProjectFilePath", PsiTreeChangeEvent.PROP_FILE_NAME, "onSurfaceChanged", "width", "", "height", "onSurfaceCreated", "surface", "Landroid/view/Surface;", "onSurfaceDestroyed", "onTouchEvent", "action", "x", "", "y", "pointerId", "setCrashLogPath", "path", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeBridge {
    public static final NativeBridge INSTANCE = new NativeBridge();
    private static boolean isWorking;

    public final native void attachSoToView(String viewName, String pathToSo);

    public final native void cleanupAllInstances();

    public final native void cleanupInstance(String viewName);

    public final native void onSurfaceChanged(String viewName, int width, int height);

    public final native void onSurfaceCreated(String viewName, Surface surface);

    public final native void onSurfaceDestroyed(String viewName);

    public final native void onTouchEvent(String viewName, int action, float x, float y, int pointerId);

    public final native void setCrashLogPath(String path);

    private NativeBridge() {
    }

    static {
        isWorking = true;
        if (NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
            return;
        }
        isWorking = false;
    }

    public final boolean isWorking() {
        return isWorking;
    }

    public final void setWorking(boolean z) {
        isWorking = z;
    }

    @JvmStatic
    public static final String getProjectFilePath(String fileName) {
        File file;
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject == null || (file = currentProject.getFile(fileName)) == null) {
            return null;
        }
        return file.getAbsolutePath();
    }
}
