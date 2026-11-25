package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.AudioRecordingManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.soundrecorder.SoundRecorder;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopRecordingAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: StopRecordingAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/StopRecordingAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "fileNameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StopRecordingAction extends TemporalAction {
    private Formula fileNameFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFileNameFormula() {
        return this.fileNameFormula;
    }

    public final void setFileNameFormula(Formula formula) {
        this.fileNameFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Project project;
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        Formula formula = this.fileNameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            Log.e("StopRecordingAction", "File name is empty.");
            return;
        }
        if (!StringsKt.contains$default((CharSequence) str, '.', false, 2, (Object) null)) {
            strInterpretString = strInterpretString + SoundRecorder.RECORDING_EXTENSION;
        }
        File filesDir = project.getFilesDir();
        if (filesDir.exists() || filesDir.mkdirs()) {
            AudioRecordingManager.getInstance().stopRecordingAndSave(new File(filesDir, strInterpretString));
        } else {
            Log.e("StopRecordingAction", "Failed to create directory: " + filesDir.getAbsolutePath());
        }
    }
}
