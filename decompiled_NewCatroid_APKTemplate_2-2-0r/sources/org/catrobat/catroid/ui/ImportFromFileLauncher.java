package org.catrobat.catroid.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ImportFromFileLauncher.class
  classes14.dex
 */
/* JADX INFO: compiled from: ImportLaunchers.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromFileLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroidx/appcompat/app/AppCompatActivity;", ModuleXmlParser.TYPE, "", "title", "(Landroidx/appcompat/app/AppCompatActivity;Ljava/lang/String;Ljava/lang/String;)V", "startActivityForResult", "", "requestCode", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportFromFileLauncher implements ImportLauncher {
    private final AppCompatActivity activity;
    private final String title;
    private final String type;

    public ImportFromFileLauncher(AppCompatActivity activity, String type, String title) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(title, "title");
        this.activity = activity;
        this.type = type;
        this.title = title;
    }

    @Override // org.catrobat.catroid.ui.ImportLauncher
    public void startActivityForResult(int requestCode) {
        Intent type = new Intent("android.intent.action.GET_CONTENT").setType(this.type);
        Intrinsics.checkNotNullExpressionValue(type, "setType(...)");
        this.activity.startActivityForResult(Intent.createChooser(type, this.title), requestCode);
    }
}
