package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.CameraPositioner;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetCameraFocusPointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetCameraFocusPointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetCameraFocusPointAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "horizontal", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getHorizontal", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setHorizontal", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "vertical", "getVertical", "setVertical", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetCameraFocusPointAction extends Action {
    private Formula horizontal;
    private Scope scope;
    private Sprite sprite;
    private Formula vertical;

    public final Sprite getSprite() {
        return this.sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getHorizontal() {
        return this.horizontal;
    }

    public final void setHorizontal(Formula formula) {
        this.horizontal = formula;
    }

    public final Formula getVertical() {
        return this.vertical;
    }

    public final void setVertical(Formula formula) {
        this.vertical = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        StageListener stageListener;
        StageListener stageListener2;
        StageListener stageListener3;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        CameraPositioner cameraPositioner = null;
        CameraPositioner cameraPositioner2 = (stageActivity == null || (stageListener3 = stageActivity.stageListener) == null) ? null : stageListener3.cameraPositioner;
        if (cameraPositioner2 != null) {
            Formula formula = this.horizontal;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            cameraPositioner2.setHorizontalFlex(fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue());
        }
        StageActivity stageActivity2 = StageActivity.activeStageActivity.get();
        CameraPositioner cameraPositioner3 = (stageActivity2 == null || (stageListener2 = stageActivity2.stageListener) == null) ? null : stageListener2.cameraPositioner;
        if (cameraPositioner3 != null) {
            Formula formula2 = this.vertical;
            Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
            cameraPositioner3.setVerticalFlex(fInterpretFloat2 != null ? fInterpretFloat2.floatValue() : 0.0f);
        }
        StageActivity stageActivity3 = StageActivity.activeStageActivity.get();
        if (stageActivity3 != null && (stageListener = stageActivity3.stageListener) != null) {
            cameraPositioner = stageListener.cameraPositioner;
        }
        if (cameraPositioner == null) {
            return true;
        }
        cameraPositioner.setSpriteToFocusOn(this.sprite);
        return true;
    }
}
