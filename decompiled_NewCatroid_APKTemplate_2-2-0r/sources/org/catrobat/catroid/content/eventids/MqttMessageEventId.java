package org.catrobat.catroid.content.eventids;

import java.util.UUID;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/MqttMessageEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MqttMessageEventId extends EventId {
    private static final long serialVersionUID = 1;
    private final String uniqueId;

    public MqttMessageEventId() {
        super(19);
        this.uniqueId = UUID.randomUUID().toString();
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof MqttMessageEventId) {
            return this.uniqueId.equals(((MqttMessageEventId) o).uniqueId);
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        return this.uniqueId.hashCode();
    }
}
