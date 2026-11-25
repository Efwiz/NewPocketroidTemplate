package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreatePrismaticJointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreatePrismaticJointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000e\u001a\u00020\r2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000f\u001a\u00020\r2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0010\u001a\u00020\r2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0011\u001a\u00020\r2\b\u0010\b\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0012\u001a\u00020\r2\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u0013\u001a\u00020\r2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreatePrismaticJointAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "anchorX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "anchorY", "axisX", "axisY", "jointId", "scope", "Lorg/catrobat/catroid/content/Scope;", "spriteBName", "setAnchorX", "", "setAnchorY", "setAxisX", "setAxisY", "setJointId", "setScope", "setSpriteBName", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreatePrismaticJointAction extends TemporalAction {
    private Formula anchorX;
    private Formula anchorY;
    private Formula axisX;
    private Formula axisY;
    private Formula jointId;
    private Scope scope;
    private Formula spriteBName;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        Sprite spriteAll;
        Formula formula = this.jointId;
        Intrinsics.checkNotNull(formula);
        String strInterpretString = formula.interpretString(this.scope);
        if (strInterpretString == null || strInterpretString.length() == 0) {
            return;
        }
        Formula formula2 = this.spriteBName;
        Intrinsics.checkNotNull(formula2);
        String strInterpretString2 = formula2.interpretString(this.scope);
        if (strInterpretString2 == null || strInterpretString2.length() == 0) {
            return;
        }
        Formula formula3 = this.anchorX;
        Intrinsics.checkNotNull(formula3);
        Float fInterpretFloat = formula3.interpretFloat(this.scope);
        Formula formula4 = this.anchorY;
        Intrinsics.checkNotNull(formula4);
        Float fInterpretFloat2 = formula4.interpretFloat(this.scope);
        Formula formula5 = this.axisX;
        Intrinsics.checkNotNull(formula5);
        Float fInterpretFloat3 = formula5.interpretFloat(this.scope);
        Formula formula6 = this.axisY;
        Intrinsics.checkNotNull(formula6);
        Float fInterpretFloat4 = formula6.interpretFloat(this.scope);
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null) {
            return;
        }
        Scope scope = this.scope;
        Intrinsics.checkNotNull(scope);
        Sprite sprite = scope.getSprite();
        if (sprite == null || (spriteAll = currentlyPlayingScene.getSpriteAll(strInterpretString2)) == null) {
            return;
        }
        PhysicsWorld physicsWorld = currentlyPlayingScene.getPhysicsWorld();
        Intrinsics.checkNotNull(fInterpretFloat);
        float fFloatValue = fInterpretFloat.floatValue();
        Intrinsics.checkNotNull(fInterpretFloat2);
        Vector2 vector2 = new Vector2(fFloatValue, fInterpretFloat2.floatValue());
        Intrinsics.checkNotNull(fInterpretFloat3);
        float fFloatValue2 = fInterpretFloat3.floatValue();
        Intrinsics.checkNotNull(fInterpretFloat4);
        physicsWorld.createPrismaticJoint(strInterpretString, sprite, spriteAll, vector2, new Vector2(fFloatValue2, fInterpretFloat4.floatValue()));
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setJointId(Formula jointId) {
        this.jointId = jointId;
    }

    public final void setSpriteBName(Formula spriteBName) {
        this.spriteBName = spriteBName;
    }

    public final void setAnchorX(Formula anchorX) {
        this.anchorX = anchorX;
    }

    public final void setAnchorY(Formula anchorY) {
        this.anchorY = anchorY;
    }

    public final void setAxisX(Formula axisX) {
        this.axisX = axisX;
    }

    public final void setAxisY(Formula axisY) {
        this.axisY = axisY;
    }
}
