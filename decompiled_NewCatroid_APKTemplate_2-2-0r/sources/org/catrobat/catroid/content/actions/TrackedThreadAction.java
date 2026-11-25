package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.reandroid.arsc.chunk.Overlayable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.utils.ActionThreadRegistry;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TrackedThreadAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: TrackedThreadAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/content/actions/TrackedThreadAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "threadId", "", "innerAction", "(Ljava/lang/String;Lcom/badlogic/gdx/scenes/scene2d/Action;)V", "act", "", "delta", "", "restart", "", "setActor", Overlayable.NAME_actor, "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrackedThreadAction extends Action {
    private final Action innerAction;
    private final String threadId;

    public TrackedThreadAction(String threadId, Action innerAction) {
        Intrinsics.checkNotNullParameter(threadId, "threadId");
        Intrinsics.checkNotNullParameter(innerAction, "innerAction");
        this.threadId = threadId;
        this.innerAction = innerAction;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        boolean zAct = this.innerAction.act(delta);
        if (zAct) {
            ActionThreadRegistry.unregister(this.threadId, this);
        }
        return zAct;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setActor(Actor actor) {
        super.setActor(actor);
        this.innerAction.setActor(actor);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.innerAction.restart();
        super.restart();
    }
}
