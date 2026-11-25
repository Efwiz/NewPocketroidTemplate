package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.ActionThreadRegistry;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SpawnThreadAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpawnThreadAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/actions/SpawnThreadAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "script", "Lorg/catrobat/catroid/content/Script;", "getScript", "()Lorg/catrobat/catroid/content/Script;", "setScript", "(Lorg/catrobat/catroid/content/Script;)V", "threadBricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "getThreadBricks", "()Ljava/util/List;", "setThreadBricks", "(Ljava/util/List;)V", "threadIdFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getThreadIdFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setThreadIdFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "act", "", "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpawnThreadAction extends Action {
    private Scope scope;
    private Script script;
    private List<? extends Brick> threadBricks;
    private Formula threadIdFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getThreadIdFormula() {
        return this.threadIdFormula;
    }

    public final void setThreadIdFormula(Formula formula) {
        this.threadIdFormula = formula;
    }

    public final List<Brick> getThreadBricks() {
        return this.threadBricks;
    }

    public final void setThreadBricks(List<? extends Brick> list) {
        this.threadBricks = list;
    }

    public final Script getScript() {
        return this.script;
    }

    public final void setScript(Script script) {
        this.script = script;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Scope scope;
        List<? extends Brick> list;
        Script script;
        Actor actor = this.actor;
        if (actor == null || (scope = this.scope) == null || (list = this.threadBricks) == null || (script = this.script) == null) {
            return true;
        }
        Formula formula = this.threadIdFormula;
        String strInterpretString = formula != null ? formula.interpretString(scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "default_thread";
        }
        ScriptSequenceAction scriptSequenceAction = new ScriptSequenceAction(script);
        for (Brick brick : list) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(scope.getSprite(), scriptSequenceAction);
            }
        }
        TrackedThreadAction trackedThreadAction = new TrackedThreadAction(strInterpretString, scriptSequenceAction);
        ActionThreadRegistry.register(strInterpretString, trackedThreadAction);
        actor.addAction(trackedThreadAction);
        return true;
    }
}
