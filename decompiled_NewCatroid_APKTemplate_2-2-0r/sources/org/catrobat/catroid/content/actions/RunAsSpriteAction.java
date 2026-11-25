package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunAsSpriteAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RunAsSpriteAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunAsSpriteAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "script", "Lorg/catrobat/catroid/content/Script;", "nestedBricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "(Lorg/catrobat/catroid/content/Script;Ljava/util/List;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "spriteName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getSpriteName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setSpriteName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RunAsSpriteAction extends Action {
    private final List<Brick> nestedBricks;
    private Scope scope;
    private final Script script;
    private Formula spriteName;

    /* JADX WARN: Multi-variable type inference failed */
    public RunAsSpriteAction(Script script, List<? extends Brick> nestedBricks) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(nestedBricks, "nestedBricks");
        this.script = script;
        this.nestedBricks = nestedBricks;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getSpriteName() {
        return this.spriteName;
    }

    public final void setSpriteName(Formula formula) {
        this.spriteName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        StageListener activeStageListener;
        Formula formula = this.spriteName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null || (activeStageListener = StageActivity.getActiveStageListener()) == null) {
            return true;
        }
        List<Sprite> spritesFromStage = activeStageListener.getSpritesFromStage();
        Intrinsics.checkNotNullExpressionValue(spritesFromStage, "getSpritesFromStage(...)");
        ArrayList<Sprite> arrayList = new ArrayList();
        for (Object obj : spritesFromStage) {
            if (Intrinsics.areEqual(((Sprite) obj).getName(), strInterpretString)) {
                arrayList.add(obj);
            }
        }
        for (Sprite sprite : arrayList) {
            ScriptSequenceAction scriptSequenceActionCreateSequenceAction = sprite.createSequenceAction(new StartScript());
            for (Brick brick : this.nestedBricks) {
                if (!brick.isCommentedOut()) {
                    brick.addActionToSequence(sprite, scriptSequenceActionCreateSequenceAction);
                }
            }
            sprite.look.addAction(scriptSequenceActionCreateSequenceAction);
        }
        return true;
    }
}
