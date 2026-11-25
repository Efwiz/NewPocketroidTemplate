package org.catrobat.catroid.ui;

import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ExportEmbroideryFileLauncher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ExportLaunchers.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/ExportEmbroideryFileLauncher;", "Lorg/catrobat/catroid/ui/ExportLauncher;", "activity", "Lorg/catrobat/catroid/stage/StageActivity;", "file", "Ljava/io/File;", "(Lorg/catrobat/catroid/stage/StageActivity;Ljava/io/File;)V", "startActivity", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportEmbroideryFileLauncher implements ExportLauncher {
    private final StageActivity activity;
    private final File file;

    public ExportEmbroideryFileLauncher(StageActivity activity, File file) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(file, "file");
        this.activity = activity;
        this.file = file;
    }

    @Override // org.catrobat.catroid.ui.ExportLauncher
    public void startActivity() {
        StageActivity stageActivity = this.activity;
        Uri uriForFile = FileProvider.getUriForFile(stageActivity, stageActivity.getPackageName() + ".fileProvider", this.file);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/*");
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.putExtra("android.intent.extra.SUBJECT", this.file.getName());
        Intent intent2 = new Intent("android.intent.action.CHOOSER");
        intent2.putExtra("android.intent.extra.INTENT", intent);
        intent2.putExtra("android.intent.extra.TITLE", "Share embroidery file");
        this.activity.startActivity(intent2);
    }
}
