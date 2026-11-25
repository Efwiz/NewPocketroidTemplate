package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateRevoluteJointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateRevoluteJointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateRevoluteJointAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "anchorX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAnchorX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAnchorX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "anchorY", "getAnchorY", "setAnchorY", "jointId", "getJointId", "setJointId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "spriteBName", "getSpriteBName", "setSpriteBName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateRevoluteJointAction extends TemporalAction {
    private Formula anchorX;
    private Formula anchorY;
    private Formula jointId;
    private Scope scope;
    private Formula spriteBName;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getJointId() {
        return this.jointId;
    }

    public final void setJointId(Formula formula) {
        this.jointId = formula;
    }

    public final Formula getSpriteBName() {
        return this.spriteBName;
    }

    public final void setSpriteBName(Formula formula) {
        this.spriteBName = formula;
    }

    public final Formula getAnchorX() {
        return this.anchorX;
    }

    public final void setAnchorX(Formula formula) {
        this.anchorX = formula;
    }

    public final Formula getAnchorY() {
        return this.anchorY;
    }

    public final void setAnchorY(Formula formula) {
        this.anchorY = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope;
        Sprite sprite;
        Sprite spriteAll;
        Formula formula = this.jointId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.spriteBName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            return;
        }
        Formula formula3 = this.anchorX;
        Float fInterpretFloat = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula4 = this.anchorY;
        Float fInterpretFloat2 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 != null ? fInterpretFloat2.floatValue() : 0.0f;
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null || (scope = this.scope) == null || (sprite = scope.getSprite()) == null || (spriteAll = currentlyPlayingScene.getSpriteAll(strInterpretString2)) == null) {
            return;
        }
        currentlyPlayingScene.getPhysicsWorld().createRevoluteJoint(strInterpretString, sprite, spriteAll, new Vector2(fFloatValue, fFloatValue2));
    }
}
