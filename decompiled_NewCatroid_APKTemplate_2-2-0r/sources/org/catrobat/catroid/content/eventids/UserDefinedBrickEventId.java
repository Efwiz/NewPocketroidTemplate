package org.catrobat.catroid.content.eventids;

import java.util.List;
import java.util.UUID;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/UserDefinedBrickEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDefinedBrickEventId extends EventId {
    public List<Object> userBrickParameters;
    public final UUID userDefinedBrickID;

    public UserDefinedBrickEventId(UUID userDefinedBrickID, List<Object> userBrickParameters) {
        this.userDefinedBrickID = userDefinedBrickID;
        this.userBrickParameters = userBrickParameters;
    }

    public UserDefinedBrickEventId(UUID userDefinedBrickID) {
        this.userDefinedBrickID = userDefinedBrickID;
        this.userBrickParameters = null;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UserDefinedBrickEventId) {
            return this.userDefinedBrickID.equals(((UserDefinedBrickEventId) o).userDefinedBrickID);
        }
        return false;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        UUID uuid = this.userDefinedBrickID;
        if (uuid != null) {
            return uuid.hashCode();
        }
        return 0;
    }
}
