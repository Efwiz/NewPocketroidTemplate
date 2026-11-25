package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateDistanceJointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateDistanceJointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\r\u001a\u00020\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000e\u001a\u00020\f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000f\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0010\u001a\u00020\f2\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0010\u0010\u0011\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateDistanceJointAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "damping", "Lorg/catrobat/catroid/formulaeditor/Formula;", "frequency", "jointId", "length", "scope", "Lorg/catrobat/catroid/content/Scope;", "spriteBName", "setDamping", "", "setFrequency", "setJointId", "setLength", "setScope", "setSpriteBName", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateDistanceJointAction extends TemporalAction {
    private Formula damping;
    private Formula frequency;
    private Formula jointId;
    private Formula length;
    private Scope scope;
    private Formula spriteBName;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        Scene currentlyPlayingScene;
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
        Formula formula3 = this.length;
        Intrinsics.checkNotNull(formula3);
        Float fInterpretFloat = formula3.interpretFloat(this.scope);
        Formula formula4 = this.frequency;
        Intrinsics.checkNotNull(formula4);
        Float fInterpretFloat2 = formula4.interpretFloat(this.scope);
        Formula formula5 = this.damping;
        Intrinsics.checkNotNull(formula5);
        Float fInterpretFloat3 = formula5.interpretFloat(this.scope);
        Scope scope = this.scope;
        Intrinsics.checkNotNull(scope);
        if (scope.getSprite().look.getStage() == null || (currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene()) == null) {
            return;
        }
        Scope scope2 = this.scope;
        Intrinsics.checkNotNull(scope2);
        Sprite sprite = scope2.getSprite();
        Sprite sprite2 = currentlyPlayingScene.getSprite(strInterpretString2);
        if (sprite2 == null) {
            return;
        }
        currentlyPlayingScene.getPhysicsWorld().createDistanceJoint(strInterpretString, sprite, sprite2, fInterpretFloat, fInterpretFloat2, fInterpretFloat3);
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

    public final void setLength(Formula length) {
        this.length = length;
    }

    public final void setFrequency(Formula frequency) {
        this.frequency = frequency;
    }

    public final void setDamping(Formula damping) {
        this.damping = damping;
    }
}
