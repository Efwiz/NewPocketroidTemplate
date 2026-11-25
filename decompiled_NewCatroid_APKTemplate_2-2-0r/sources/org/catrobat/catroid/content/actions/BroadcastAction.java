package org.catrobat.catroid.content.actions;

import kotlin.Metadata;
import org.catrobat.catroid.content.eventids.BroadcastEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/BroadcastAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: BroadcastAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0016R(\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/content/actions/BroadcastAction;", "Lorg/catrobat/catroid/content/actions/MultiSpriteEventAction;", "()V", "value", "", "broadcastMessage", "getBroadcastMessage", "()Ljava/lang/String;", "setBroadcastMessage", "(Ljava/lang/String;)V", "cachedEventId", "Lorg/catrobat/catroid/content/eventids/BroadcastEventId;", "getEventId", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BroadcastAction extends MultiSpriteEventAction {
    private String broadcastMessage;
    private BroadcastEventId cachedEventId;

    public final String getBroadcastMessage() {
        return this.broadcastMessage;
    }

    public final void setBroadcastMessage(String str) {
        this.broadcastMessage = str;
        this.cachedEventId = null;
    }

    @Override // org.catrobat.catroid.content.actions.EventAction
    public BroadcastEventId getEventId() {
        if (this.cachedEventId == null && this.broadcastMessage != null) {
            this.cachedEventId = new BroadcastEventId(this.broadcastMessage);
        }
        return this.cachedEventId;
    }
}
