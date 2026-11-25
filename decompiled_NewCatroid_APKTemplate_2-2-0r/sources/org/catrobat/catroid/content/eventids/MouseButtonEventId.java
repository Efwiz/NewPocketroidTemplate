package org.catrobat.catroid.content.eventids;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/MouseButtonEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MouseButtonEventId extends EventId {
    public final int buttonCode;

    public MouseButtonEventId(int buttonCode) {
        super(8);
        this.buttonCode = buttonCode;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass() && super.equals(o) && this.buttonCode == ((MouseButtonEventId) o).buttonCode;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        return (super.hashCode() * 31) + this.buttonCode;
    }
}
