package org.catrobat.catroid.ide;

import android.content.Context;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeSettings.class
  classes14.dex
 */
/* JADX INFO: compiled from: IdeSettings.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ide/IdeSettings;", "", "()V", "getAndroidJar", "Ljava/io/File;", "context", "Landroid/content/Context;", "apiLevel", "", "getSdkDir", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeSettings {
    public static final IdeSettings INSTANCE = new IdeSettings();

    private IdeSettings() {
    }

    public final File getSdkDir(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File file = new File(context.getFilesDir(), "AndroidSDK");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public final File getAndroidJar(Context context, int apiLevel) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new File(getSdkDir(context), "platforms/android-" + apiLevel + "/android.jar");
    }
}
