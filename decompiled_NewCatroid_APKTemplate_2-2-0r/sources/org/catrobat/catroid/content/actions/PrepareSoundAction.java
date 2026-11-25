package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.io.SoundCacheManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PrepareSoundAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PrepareSoundAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/PrepareSoundAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "cacheNameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCacheNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCacheNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "fileNameFormula", "getFileNameFormula", "setFileNameFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrepareSoundAction extends TemporalAction {
    private Formula cacheNameFormula;
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

    public final Formula getCacheNameFormula() {
        return this.cacheNameFormula;
    }

    public final void setCacheNameFormula(Formula formula) {
        this.cacheNameFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        Formula formula = this.fileNameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.cacheNameFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        Scope scope = this.scope;
        Project project = scope != null ? scope.getProject() : null;
        String str2 = strInterpretString;
        if (str2 == null || str2.length() == 0 || (str = strInterpretString2) == null || str.length() == 0 || project == null) {
            return;
        }
        try {
            File file = project.getFile(strInterpretString);
            if (file == null || !file.exists()) {
                Log.e("PrepareSoundAction", "Sound file not found: " + strInterpretString);
            } else {
                SoundCacheManager.getInstance().loadSound(strInterpretString2, file.getAbsolutePath());
            }
        } catch (Exception e) {
            Log.e("PrepareSoundAction", "Error preparing sound", e);
        }
    }
}
