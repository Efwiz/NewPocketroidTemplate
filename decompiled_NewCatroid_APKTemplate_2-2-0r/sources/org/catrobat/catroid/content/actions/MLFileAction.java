package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ml.MLBridge;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MLFileAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: MLFileAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/actions/MLFileAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "isSave", "", "(Z)V", "fileNameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFileNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFileNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MLFileAction extends TemporalAction {
    private Formula fileNameFormula;
    private final boolean isSave;
    private Scope scope;

    public MLFileAction(boolean z) {
        this.isSave = z;
    }

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
        File parentFile;
        Project project;
        Log.d("TEST_123", "called");
        Formula formula = this.fileNameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Scope scope = this.scope;
        File file = (scope == null || (project = scope.getProject()) == null) ? null : project.getFile(strInterpretString);
        Log.d("TEST_123", "interpreted");
        if (!this.isSave) {
            Log.d("TEST_123", "loading");
            if (file != null) {
                if (file.exists()) {
                    Log.d("TEST_123", "native load");
                    MLBridge.nativeLoadModel(file.getAbsolutePath());
                }
                Log.d("TEST_123", "end loading");
                return;
            }
            return;
        }
        if (file != null && (parentFile = file.getParentFile()) != null) {
            parentFile.mkdirs();
        }
        Log.d("TEST_123", "saving native call: " + (file != null ? file.getAbsolutePath() : null));
        MLBridge.nativeSaveModel(file != null ? file.getAbsolutePath() : null);
    }
}
