package org.catrobat.catroid.content.eventids;

import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/SetLookEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetLookEventId extends EventId {
    public final LookData lookData;
    public final Sprite sprite;

    public SetLookEventId(Sprite sprite, LookData lookData) {
        this.sprite = sprite;
        this.lookData = lookData;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetLookEventId) || !super.equals(o)) {
            return false;
        }
        SetLookEventId setLookEventId = (SetLookEventId) o;
        return this.sprite.equals(setLookEventId.sprite) && this.lookData.equals(setLookEventId.lookData);
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        int iHashCode = ((super.hashCode() * 31) + this.sprite.hashCode()) * 31;
        LookData lookData = this.lookData;
        return iHashCode + (lookData != null ? lookData.hashCode() : 0);
    }
}
