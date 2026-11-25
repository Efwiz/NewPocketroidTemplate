package org.catrobat.catroid.content.eventids;

import com.google.common.base.Objects;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/RaspiEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiEventId extends EventId {
    private final String eventValue;
    private final String pin;

    public RaspiEventId(String pin, String eventValue) {
        this.pin = pin;
        this.eventValue = eventValue;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaspiEventId) || !super.equals(o)) {
            return false;
        }
        RaspiEventId raspiEventId = (RaspiEventId) o;
        return Objects.equal(this.pin, raspiEventId.pin) && Objects.equal(this.eventValue, raspiEventId.eventValue);
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        String str = this.pin;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.eventValue;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }
}
