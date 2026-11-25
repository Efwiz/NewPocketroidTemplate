package org.catrobat.catroid.content.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import androidx.core.content.FileProvider;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ShareAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ShareAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/ShareAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "contentFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getContentFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setContentFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "reset", "", "shareFile", "activity", "Landroid/app/Activity;", "file", "Ljava/io/File;", "shareText", "text", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShareAction extends TemporalAction {
    private Formula contentFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getContentFormula() {
        return this.contentFormula;
    }

    public final void setContentFormula(Formula formula) {
        this.contentFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        Formula formula = this.contentFormula;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        if (strInterpretString.length() == 0 || (stageActivity = StageActivity.activeStageActivity.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ShareAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ShareAction.update$lambda$0(this.f$0, strInterpretString, stageActivity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(ShareAction this$0, String value, StageActivity activity) {
        Project project;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(value, "$value");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        try {
            Scope scope = this$0.scope;
            File file = (scope == null || (project = scope.getProject()) == null) ? null : project.getFile(value);
            if (file != null && file.exists() && file.isFile()) {
                this$0.shareFile(activity, file);
            } else {
                this$0.shareText(activity, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void shareText(Activity activity, String text) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", text);
        activity.startActivity(Intent.createChooser(intent, "Share"));
    }

    private final void shareFile(Activity activity, File file) {
        Uri uriForFile = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getPath()));
        if (mimeTypeFromExtension == null) {
            mimeTypeFromExtension = "application/octet-stream";
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(mimeTypeFromExtension);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        activity.startActivity(Intent.createChooser(intent, "Share file"));
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.contentFormula = null;
    }
}
