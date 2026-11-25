package org.catrobat.catroid.content.eventids;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/BroadcastEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastEventId extends EventId {
    public final String message;

    public BroadcastEventId(String message) {
        this.message = message;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof BroadcastEventId) {
            return this.message.equals(((BroadcastEventId) o).message);
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        String str = this.message;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }
}
