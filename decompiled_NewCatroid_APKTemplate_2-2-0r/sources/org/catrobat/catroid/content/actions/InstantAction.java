package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.reandroid.arsc.chunk.Overlayable;
import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/InstantAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: InstantAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u0012\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/content/actions/InstantAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "action", "getAction", "()Lcom/badlogic/gdx/scenes/scene2d/Action;", "setAction", "(Lcom/badlogic/gdx/scenes/scene2d/Action;)V", "act", "", "delta", "", "restart", "", "setActor", Overlayable.NAME_actor, "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InstantAction extends Action {
    private Action action;

    public final Action getAction() {
        return this.action;
    }

    public final void setAction(Action action) {
        this.action = action;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Action action = this.action;
        if (action == null) {
            return true;
        }
        while (!action.act(delta)) {
        }
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setActor(Actor actor) {
        super.setActor(actor);
        Action action = this.action;
        if (action == null) {
            return;
        }
        action.setActor(actor);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        Action action = this.action;
        if (action != null) {
            action.restart();
        }
        super.restart();
    }
}
