package org.catrobat.catroid.content.actions;

import android.graphics.Bitmap;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.NN.ImageProcessingManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ResizeImgAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ResizeImgAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/ResizeImgAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "file", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFile", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFile", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "h", "getH", "setH", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "w", "getW", "setW", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResizeImgAction extends TemporalAction {
    private Formula file;
    private Formula h;
    private Scope scope;
    private Formula w;

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

    public final Formula getW() {
        return this.w;
    }

    public final void setW(Formula formula) {
        this.w = formula;
    }

    public final Formula getH() {
        return this.h;
    }

    public final void setH(Formula formula) {
        this.h = formula;
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
        Formula formula2 = this.w;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        Formula formula3 = this.h;
        Bitmap bitmapResizeBitmap = ImageProcessingManager.INSTANCE.resizeBitmap(ImageProcessingManager.INSTANCE.loadBitmapFromFile(file), numInterpretInteger, formula3 != null ? formula3.interpretInteger(this.scope) : null);
        if (bitmapResizeBitmap == null) {
            return;
        }
        ImageProcessingManager.INSTANCE.saveBitmapToProject(file, bitmapResizeBitmap);
    }
}
