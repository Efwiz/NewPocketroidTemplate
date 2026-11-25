package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateGearJointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateGearJointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\f\u001a\u00020\u000b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\r\u001a\u00020\u000b2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000e\u001a\u00020\u000b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000f\u001a\u00020\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateGearJointAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "jointAId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "jointBId", "jointId", "ratio", "scope", "Lorg/catrobat/catroid/content/Scope;", "setJointAId", "", "setJointBId", "setJointId", "setRatio", "setScope", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateGearJointAction extends TemporalAction {
    private Formula jointAId;
    private Formula jointBId;
    private Formula jointId;
    private Formula ratio;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        Formula formula = this.jointId;
        Intrinsics.checkNotNull(formula);
        String strInterpretString = formula.interpretString(this.scope);
        Formula formula2 = this.jointAId;
        Intrinsics.checkNotNull(formula2);
        String strInterpretString2 = formula2.interpretString(this.scope);
        Formula formula3 = this.jointBId;
        Intrinsics.checkNotNull(formula3);
        String strInterpretString3 = formula3.interpretString(this.scope);
        if (strInterpretString == null || strInterpretString.length() == 0 || strInterpretString2 == null || strInterpretString3 == null) {
            return;
        }
        Formula formula4 = this.ratio;
        Intrinsics.checkNotNull(formula4);
        Float fInterpretFloat = formula4.interpretFloat(this.scope);
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null) {
            return;
        }
        PhysicsWorld physicsWorld = currentlyPlayingScene.getPhysicsWorld();
        Intrinsics.checkNotNull(fInterpretFloat);
        physicsWorld.createGearJoint(strInterpretString, strInterpretString2, strInterpretString3, fInterpretFloat.floatValue());
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setJointId(Formula jointId) {
        this.jointId = jointId;
    }

    public final void setJointAId(Formula jointAId) {
        this.jointAId = jointAId;
    }

    public final void setJointBId(Formula jointBId) {
        this.jointBId = jointBId;
    }

    public final void setRatio(Formula ratio) {
        this.ratio = ratio;
    }
}
