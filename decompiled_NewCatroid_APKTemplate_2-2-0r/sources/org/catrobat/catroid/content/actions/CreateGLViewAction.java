package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateGLViewAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateGLViewAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateGLViewAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "height", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getHeight", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setHeight", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "viewName", "getViewName", "setViewName", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateGLViewAction extends TemporalAction {
    private Formula height;
    private Scope scope;
    private Formula viewName;
    private Formula width;
    private Formula x;
    private Formula y;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewName() {
        return this.viewName;
    }

    public final void setViewName(Formula formula) {
        this.viewName = formula;
    }

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
    }

    public final Formula getWidth() {
        return this.width;
    }

    public final void setWidth(Formula formula) {
        this.width = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageActivity stageActivity;
        Formula formula = this.viewName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "gl_view";
        }
        String str = strInterpretString;
        Formula formula2 = this.x;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula3 = this.y;
        Integer numInterpretInteger2 = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula4 = this.width;
        Integer numInterpretInteger3 = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        int iIntValue3 = numInterpretInteger3 == null ? 100 : numInterpretInteger3.intValue();
        Formula formula5 = this.height;
        Integer numInterpretInteger4 = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        int iIntValue4 = numInterpretInteger4 == null ? 100 : numInterpretInteger4.intValue();
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.createGLSurfaceView(str, iIntValue, iIntValue2, iIntValue3, iIntValue4);
    }
}
