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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreatePulleyJointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreatePulleyJointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0011\u001a\u00020\u000f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0013\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0014\u001a\u00020\u000f2\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0015\u001a\u00020\u000f2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010\u0016\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0017\u001a\u00020\u000f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreatePulleyJointAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "groundAnchorAx", "Lorg/catrobat/catroid/formulaeditor/Formula;", "groundAnchorAy", "groundAnchorBx", "groundAnchorBy", "jointId", "ratio", "scope", "Lorg/catrobat/catroid/content/Scope;", "spriteAName", "spriteBName", "setGroundAnchorAx", "", "setGroundAnchorAy", "setGroundAnchorBx", "setGroundAnchorBy", "setJointId", "setRatio", "setScope", "setSpriteAName", "setSpriteBName", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreatePulleyJointAction extends TemporalAction {
    private Formula groundAnchorAx;
    private Formula groundAnchorAy;
    private Formula groundAnchorBx;
    private Formula groundAnchorBy;
    private Formula jointId;
    private Formula ratio;
    private Scope scope;
    private Formula spriteAName;
    private Formula spriteBName;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        Formula formula = this.jointId;
        Intrinsics.checkNotNull(formula);
        String strInterpretString = formula.interpretString(this.scope);
        Formula formula2 = this.spriteAName;
        Intrinsics.checkNotNull(formula2);
        String strInterpretString2 = formula2.interpretString(this.scope);
        Formula formula3 = this.spriteBName;
        Intrinsics.checkNotNull(formula3);
        String strInterpretString3 = formula3.interpretString(this.scope);
        if (strInterpretString == null || strInterpretString.length() == 0 || strInterpretString2 == null || strInterpretString3 == null) {
            return;
        }
        Formula formula4 = this.groundAnchorAx;
        Intrinsics.checkNotNull(formula4);
        Float fInterpretFloat = formula4.interpretFloat(this.scope);
        Formula formula5 = this.groundAnchorAy;
        Intrinsics.checkNotNull(formula5);
        Float fInterpretFloat2 = formula5.interpretFloat(this.scope);
        Formula formula6 = this.groundAnchorBx;
        Intrinsics.checkNotNull(formula6);
        Float fInterpretFloat3 = formula6.interpretFloat(this.scope);
        Formula formula7 = this.groundAnchorBy;
        Intrinsics.checkNotNull(formula7);
        Float fInterpretFloat4 = formula7.interpretFloat(this.scope);
        Formula formula8 = this.ratio;
        Intrinsics.checkNotNull(formula8);
        Float fInterpretFloat5 = formula8.interpretFloat(this.scope);
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null) {
            return;
        }
        Sprite spriteAll = currentlyPlayingScene.getSpriteAll(strInterpretString2);
        Intrinsics.checkNotNullExpressionValue(spriteAll, "getSpriteAll(...)");
        Sprite spriteAll2 = currentlyPlayingScene.getSpriteAll(strInterpretString3);
        Intrinsics.checkNotNullExpressionValue(spriteAll2, "getSpriteAll(...)");
        PhysicsWorld physicsWorld = currentlyPlayingScene.getPhysicsWorld();
        Intrinsics.checkNotNull(fInterpretFloat);
        float fFloatValue = fInterpretFloat.floatValue();
        Intrinsics.checkNotNull(fInterpretFloat2);
        Vector2 vector2 = new Vector2(fFloatValue, fInterpretFloat2.floatValue());
        Intrinsics.checkNotNull(fInterpretFloat3);
        float fFloatValue2 = fInterpretFloat3.floatValue();
        Intrinsics.checkNotNull(fInterpretFloat4);
        Vector2 vector3 = new Vector2(fFloatValue2, fInterpretFloat4.floatValue());
        Intrinsics.checkNotNull(fInterpretFloat5);
        physicsWorld.createPulleyJoint(strInterpretString, spriteAll, spriteAll2, vector2, vector3, fInterpretFloat5.floatValue());
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setJointId(Formula jointId) {
        this.jointId = jointId;
    }

    public final void setSpriteAName(Formula spriteAName) {
        this.spriteAName = spriteAName;
    }

    public final void setSpriteBName(Formula spriteBName) {
        this.spriteBName = spriteBName;
    }

    public final void setGroundAnchorAx(Formula groundAnchorAx) {
        this.groundAnchorAx = groundAnchorAx;
    }

    public final void setGroundAnchorAy(Formula groundAnchorAy) {
        this.groundAnchorAy = groundAnchorAy;
    }

    public final void setGroundAnchorBx(Formula groundAnchorBx) {
        this.groundAnchorBx = groundAnchorBx;
    }

    public final void setGroundAnchorBy(Formula groundAnchorBy) {
        this.groundAnchorBy = groundAnchorBy;
    }

    public final void setRatio(Formula ratio) {
        this.ratio = ratio;
    }
}
