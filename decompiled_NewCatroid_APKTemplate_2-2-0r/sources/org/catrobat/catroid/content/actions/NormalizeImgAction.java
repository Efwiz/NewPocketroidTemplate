package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.NN.ImageProcessingManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NormalizeImgAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NormalizeImgAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/NormalizeImgAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getB", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setB", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "file", "getFile", "setFile", "g", "getG", "setG", "r", "getR", "setR", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NormalizeImgAction extends TemporalAction {
    private Formula b;
    private Formula file;
    private Formula g;
    private Formula r;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFile() {
        return this.file;
    }

    public final void setFile(Formula formula) {
        this.file = formula;
    }

    public final Formula getR() {
        return this.r;
    }

    public final void setR(Formula formula) {
        this.r = formula;
    }

    public final Formula getG() {
        return this.g;
    }

    public final void setG(Formula formula) {
        this.g = formula;
    }

    public final Formula getB() {
        return this.b;
    }

    public final void setB(Formula formula) {
        this.b = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        File file;
        Project project;
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            file = null;
        } else {
            Formula formula = this.file;
            file = project.getFile(formula != null ? formula.interpretString(this.scope) : null);
        }
        Formula formula2 = this.r;
        String strInterpretString = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "rTable";
        }
        Formula formula3 = this.g;
        String strInterpretString2 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "gTable";
        }
        Formula formula4 = this.b;
        String strInterpretString3 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "bTable";
        }
        ImageProcessingManager.INSTANCE.normalizeToTables(ImageProcessingManager.INSTANCE.loadBitmapFromFile(file), strInterpretString, strInterpretString2, strInterpretString3);
    }
}
