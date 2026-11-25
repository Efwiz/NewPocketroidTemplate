package org.catrobat.catroid.common;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.ScriptSequenceActionWithWaiter;
import org.catrobat.catroid.utils.PerformanceTracker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ThreadScheduler.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class ThreadScheduler {
    public static final int RUNNING = 0;
    public static final int SUSPENDED = 1;
    private Actor actor;
    private Array<ScriptSequenceAction> startQueue = new Array<>();
    private Array<Action> stopQueue = new Array<>();
    private int state = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ThreadScheduler$SchedulerState.class
      classes12.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface SchedulerState {
    }

    public ThreadScheduler(Actor actor) {
        this.actor = actor;
    }

    public void tick(float delta) {
        Array<Action> actions = this.actor.getActions();
        PerformanceTracker.activeThreads += (long) actions.size;
        startThreadsInStartQueue();
        runThreadsForOneTick(actions, delta);
        stopThreadsInStopQueue(actions);
    }

    private void startThreadsInStartQueue() {
        Array.ArrayIterator<ScriptSequenceAction> it = this.startQueue.iterator();
        while (it.hasNext()) {
            ScriptSequenceAction next = it.next();
            next.restart();
            this.actor.addAction(next);
        }
        this.startQueue.clear();
    }

    private void runThreadsForOneTick(Array<Action> actions, float delta) {
        for (int i = 0; i < actions.size; i++) {
            Action action = actions.get(i);
            if (this.state == 0 && action.act(delta)) {
                this.stopQueue.add(action);
            }
        }
    }

    private void stopThreadsInStopQueue(Array<Action> actions) {
        actions.removeAll(this.stopQueue, true);
        Array.ArrayIterator<Action> it = this.stopQueue.iterator();
        while (it.hasNext()) {
            Action next = it.next();
            if (next instanceof ScriptSequenceActionWithWaiter) {
                ((ScriptSequenceActionWithWaiter) next).notifyWaiter();
            }
        }
        this.stopQueue.clear();
    }

    public void startThread(ScriptSequenceAction sequenceAction) {
        removeThreadsWithEqualScriptFromStartQueue(sequenceAction);
        this.startQueue.add(sequenceAction);
    }

    private void removeThreadsWithEqualScriptFromStartQueue(ScriptSequenceAction sequenceAction) {
        Array.ArrayIterator<ScriptSequenceAction> it = this.startQueue.iterator();
        while (it.hasNext()) {
            ScriptSequenceAction next = it.next();
            if (next.getScript() == sequenceAction.getScript()) {
                if (next instanceof ScriptSequenceActionWithWaiter) {
                    ((ScriptSequenceActionWithWaiter) next).notifyWaiter();
                }
                it.remove();
            }
        }
    }

    public void stopThreadsWithScript(Script script) {
        Array.ArrayIterator<Action> it = this.actor.getActions().iterator();
        while (it.hasNext()) {
            Action next = it.next();
            if ((next instanceof ScriptSequenceAction) && ((ScriptSequenceAction) next).getScript() == script) {
                this.stopQueue.add(next);
            }
        }
    }

    public void stopThreads(Array<Action> actions) {
        this.stopQueue.addAll(actions);
    }

    public boolean haveAllThreadsFinished() {
        return this.startQueue.size + this.actor.getActions().size == 0;
    }

    public void setState(int schedulerState) {
        this.state = schedulerState;
    }
}
