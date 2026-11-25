package org.catrobat.catroid.content.eventids;

import com.google.common.base.Objects;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/NfcEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NfcEventId extends EventId {
    final String tag;

    public NfcEventId(String tag) {
        this.tag = tag;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof NfcEventId) && super.equals(o)) {
            return Objects.equal(this.tag, ((NfcEventId) o).tag);
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(super.hashCode()), this.tag);
    }
}
