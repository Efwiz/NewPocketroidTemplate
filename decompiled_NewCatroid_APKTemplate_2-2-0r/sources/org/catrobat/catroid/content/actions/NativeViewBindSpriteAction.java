package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewBindSpriteAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewBindSpriteAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000e¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/NativeViewBindSpriteAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "alignMode", "", "getAlignMode", "()I", "setAlignMode", "(I)V", "offsetX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getOffsetX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setOffsetX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "offsetY", "getOffsetY", "setOffsetY", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "spriteName", "getSpriteName", "setSpriteName", "viewId", "getViewId", "setViewId", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewBindSpriteAction extends TemporalAction {
    private int alignMode = 1;
    private Formula offsetX;
    private Formula offsetY;
    private Scope scope;
    private Formula spriteName;
    private Formula viewId;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewId() {
        return this.viewId;
    }

    public final void setViewId(Formula formula) {
        this.viewId = formula;
    }

    public final Formula getSpriteName() {
        return this.spriteName;
    }

    public final void setSpriteName(Formula formula) {
        this.spriteName = formula;
    }

    public final Formula getOffsetX() {
        return this.offsetX;
    }

    public final void setOffsetX(Formula formula) {
        this.offsetX = formula;
    }

    public final Formula getOffsetY() {
        return this.offsetY;
    }

    public final void setOffsetY(Formula formula) {
        this.offsetY = formula;
    }

    public final int getAlignMode() {
        return this.alignMode;
    }

    public final void setAlignMode(int i) {
        this.alignMode = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null) {
            return;
        }
        Formula formula = this.viewId;
        Object obj = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.spriteName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        Formula formula3 = this.offsetX;
        Float fInterpretFloat = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula4 = this.offsetY;
        Float fInterpretFloat2 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 != null ? fInterpretFloat2.floatValue() : 0.0f;
        if (strInterpretString2.length() == 0) {
            NativeViewBindingManager.unbind(strInterpretString);
            return;
        }
        List<Sprite> spritesFromStage = activeStageListener.getSpritesFromStage();
        Intrinsics.checkNotNullExpressionValue(spritesFromStage, "getSpritesFromStage(...)");
        for (Object obj2 : spritesFromStage) {
            if (Intrinsics.areEqual(((Sprite) obj2).getName(), strInterpretString2)) {
                obj = obj2;
                break;
            }
        }
        Sprite sprite = (Sprite) obj;
        if (sprite != null) {
            NativeViewBindingManager.bind(strInterpretString, sprite, fFloatValue, fFloatValue2, this.alignMode);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
