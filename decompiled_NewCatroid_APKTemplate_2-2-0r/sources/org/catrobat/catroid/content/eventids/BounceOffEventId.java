package org.catrobat.catroid.content.eventids;

import com.google.common.base.Objects;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/BounceOffEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BounceOffEventId extends EventId {
    public final Sprite bouncingSprite;
    public final Sprite staticSprite;

    public BounceOffEventId(Sprite bouncingSprite, Sprite staticSprite) {
        this.bouncingSprite = bouncingSprite;
        this.staticSprite = staticSprite;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BounceOffEventId) || !super.equals(o)) {
            return false;
        }
        BounceOffEventId bounceOffEventId = (BounceOffEventId) o;
        return Objects.equal(this.bouncingSprite, bounceOffEventId.bouncingSprite) && Objects.equal(this.staticSprite, bounceOffEventId.staticSprite);
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(super.hashCode()), this.bouncingSprite, this.staticSprite);
    }
}
