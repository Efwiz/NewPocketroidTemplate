package org.catrobat.catroid.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ImportFromLocalProjectListLauncher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ImportLaunchers.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromLocalProjectListLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "title", "", "(Landroidx/appcompat/app/AppCompatActivity;Ljava/lang/String;)V", "startActivityForResult", "", "requestCode", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportFromLocalProjectListLauncher implements ImportLauncher {
    private final AppCompatActivity activity;
    private final String title;

    public ImportFromLocalProjectListLauncher(AppCompatActivity activity, String title) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(title, "title");
        this.activity = activity;
        this.title = title;
    }

    @Override // org.catrobat.catroid.ui.ImportLauncher
    public void startActivityForResult(int requestCode) {
        Intent intent = new Intent(this.activity, (Class<?>) ProjectListActivity.class);
        intent.putExtra("merge", this.title);
        this.activity.startActivityForResult(intent, requestCode);
    }
}
