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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ApplyImpulseAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ApplyImpulseAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\n\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000b\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/content/actions/ApplyImpulseAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "impulseX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "impulseY", "scope", "Lorg/catrobat/catroid/content/Scope;", "setImpulseX", "", "setImpulseY", "setScope", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApplyImpulseAction extends TemporalAction {
    private Formula impulseX;
    private Formula impulseY;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        Formula formula = this.impulseX;
        Intrinsics.checkNotNull(formula);
        Float fInterpretFloat = formula.interpretFloat(this.scope);
        Formula formula2 = this.impulseY;
        Intrinsics.checkNotNull(formula2);
        Float fInterpretFloat2 = formula2.interpretFloat(this.scope);
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null) {
            return;
        }
        Scope scope = this.scope;
        Intrinsics.checkNotNull(scope);
        Sprite sprite = scope.getSprite();
        if (sprite == null) {
            return;
        }
        PhysicsWorld physicsWorld = currentlyPlayingScene.getPhysicsWorld();
        Intrinsics.checkNotNull(fInterpretFloat);
        float fFloatValue = fInterpretFloat.floatValue();
        Intrinsics.checkNotNull(fInterpretFloat2);
        physicsWorld.applyImpulse(sprite, new Vector2(fFloatValue, fInterpretFloat2.floatValue()), sprite.look.getPosition());
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setImpulseX(Formula impulseX) {
        this.impulseX = impulseX;
    }

    public final void setImpulseY(Formula impulseY) {
        this.impulseY = impulseY;
    }
}
