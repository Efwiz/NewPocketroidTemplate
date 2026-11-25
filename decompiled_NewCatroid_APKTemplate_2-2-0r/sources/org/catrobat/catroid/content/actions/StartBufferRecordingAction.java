package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.BufferVideoRecorder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StartBufferRecordingAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: StartBufferRecordingAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/StartBufferRecordingAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "bitrateFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBitrateFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBitrateFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "bufferName", "getBufferName", "setBufferName", PsiTreeChangeEvent.PROP_FILE_NAME, "getFileName", "setFileName", "fpsFormula", "getFpsFormula", "setFpsFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "reset", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StartBufferRecordingAction extends TemporalAction {
    private Formula bitrateFormula;
    private Formula bufferName;
    private Formula fileName;
    private Formula fpsFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getBufferName() {
        return this.bufferName;
    }

    public final void setBufferName(Formula formula) {
        this.bufferName = formula;
    }

    public final Formula getFileName() {
        return this.fileName;
    }

    public final void setFileName(Formula formula) {
        this.fileName = formula;
    }

    public final Formula getFpsFormula() {
        return this.fpsFormula;
    }

    public final void setFpsFormula(Formula formula) {
        this.fpsFormula = formula;
    }

    public final Formula getBitrateFormula() {
        return this.bitrateFormula;
    }

    public final void setBitrateFormula(Formula formula) {
        this.bitrateFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope = this.scope;
        if (scope == null) {
            return;
        }
        Formula formula = this.bufferName;
        String strInterpretString = formula != null ? formula.interpretString(scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.fileName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "record.mp4";
        }
        Formula formula3 = this.fpsFormula;
        Integer numInterpretInteger = formula3 != null ? formula3.interpretInteger(scope) : null;
        int iIntValue = numInterpretInteger == null ? 30 : numInterpretInteger.intValue();
        Formula formula4 = this.bitrateFormula;
        Integer numInterpretInteger2 = formula4 != null ? formula4.interpretInteger(scope) : null;
        int iIntValue2 = numInterpretInteger2 == null ? 2000000 : numInterpretInteger2.intValue();
        Project project = scope.getProject();
        File file = project != null ? project.getFile(strInterpretString2) : null;
        if (file == null) {
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        BufferVideoRecorder.INSTANCE.startRecording(strInterpretString, file, iIntValue, iIntValue2);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.bufferName = null;
        this.fileName = null;
        this.fpsFormula = null;
        this.bitrateFormula = null;
    }
}
