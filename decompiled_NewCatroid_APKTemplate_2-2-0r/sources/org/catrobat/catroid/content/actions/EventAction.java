package org.catrobat.catroid.content.actions;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.EventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/EventAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: EventAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H&J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X¤\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/actions/EventAction;", "Lorg/catrobat/catroid/content/actions/AsynchronousAction;", "()V", "event", "Lorg/catrobat/catroid/content/EventWrapper;", "receivingSprites", "", "Lorg/catrobat/catroid/content/Sprite;", "getReceivingSprites", "()Ljava/util/List;", "wait", "", "getWait", "()Z", "setWait", "(Z)V", "getEventId", "Lorg/catrobat/catroid/content/eventids/EventId;", "initialize", "", "isFinished", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class EventAction extends AsynchronousAction {
    private EventWrapper event;
    private boolean wait;

    public abstract EventId getEventId();

    protected abstract List<Sprite> getReceivingSprites();

    public final boolean getWait() {
        return this.wait;
    }

    public final void setWait(boolean z) {
        this.wait = z;
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public void initialize() {
        EventId eventId = getEventId();
        EventWrapper eventWrapper = eventId != null ? new EventWrapper(eventId, this.wait) : null;
        this.event = eventWrapper;
        if (eventWrapper != null) {
            Iterator<T> it = getReceivingSprites().iterator();
            while (it.hasNext()) {
                ((Sprite) it.next()).look.fire(this.event);
            }
        }
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public boolean isFinished() {
        EventWrapper eventWrapper = this.event;
        boolean z = false;
        if (eventWrapper != null && eventWrapper.isWaitingForSprite()) {
            z = true;
        }
        return !z;
    }
}
