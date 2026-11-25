package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.utils.PerformanceTracker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ScriptSequenceAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScriptSequenceAction extends SequenceAction {
    protected Script script;

    public ScriptSequenceAction(Script script) {
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Script script) {
        super(action1);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Script script) {
        super(action1, action2);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Action action3, Script script) {
        super(action1, action2, action3);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Action action3, Action action4, Script script) {
        super(action1, action2, action3, action4);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Action action3, Action action4, Action action5, Script script) {
        super(action1, action2, action3, action4, action5);
        this.script = script;
    }

    public Script getScript() {
        return this.script;
    }

    public ScriptSequenceAction clone() {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(this.script);
        Array.ArrayIterator<Action> it = getActions().iterator();
        while (it.hasNext()) {
            scriptSequenceAction.addAction(it.next());
        }
        return scriptSequenceAction;
    }

    public ScriptSequenceAction cloneAndChangeScript(Script script) {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(script);
        Array.ArrayIterator<Action> it = getActions().iterator();
        while (it.hasNext()) {
            scriptSequenceAction.addAction(it.next());
        }
        return scriptSequenceAction;
    }

    public ScriptSequenceAction deepCloneAndMakeInstant() {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(this.script);
        Array.ArrayIterator<Action> it = getActions().iterator();
        while (it.hasNext()) {
            Action next = it.next();
            if (!(next instanceof WaitAction)) {
                if (next instanceof ScriptSequenceAction) {
                    scriptSequenceAction.addAction(((ScriptSequenceAction) next).deepCloneAndMakeInstant());
                } else {
                    scriptSequenceAction.addAction(next);
                }
            }
        }
        return scriptSequenceAction;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.SequenceAction, com.badlogic.gdx.scenes.scene2d.actions.ParallelAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        long jNanoTime = System.nanoTime();
        boolean zAct = super.act(delta);
        long jNanoTime2 = System.nanoTime() - jNanoTime;
        PerformanceTracker.blocksExecuted++;
        PerformanceTracker.totalBlockTimeNs += jNanoTime2;
        return zAct;
    }
}
