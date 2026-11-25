package org.catrobat.catroid.physics;

import com.google.common.base.Objects;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.BounceOffEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicalCollision.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicalCollision {
    private int contactCounter = 0;
    private CollidingSprites objects;

    PhysicalCollision(CollidingSprites objects) {
        this.objects = objects;
    }

    void increaseContactCounter() {
        this.contactCounter++;
    }

    void decreaseContactCounter() {
        int i = this.contactCounter;
        if (i > 0) {
            this.contactCounter = i - 1;
        }
    }

    public int getContactCounter() {
        return this.contactCounter;
    }

    void sendBounceOffEvents() {
        sendBounceOffEvent(this.objects.sprite1, this.objects.sprite2);
        sendBounceOffEvent(this.objects.sprite2, this.objects.sprite1);
    }

    private void sendBounceOffEvent(Sprite spriteBouncingOff, Sprite otherSprite) {
        if (spriteBouncingOff.isClone) {
            fireBounceOffEvent(spriteBouncingOff.myOriginal, otherSprite);
        }
        if (otherSprite.isClone) {
            fireBounceOffEvent(spriteBouncingOff, otherSprite.myOriginal);
        }
        fireBounceOffEvent(spriteBouncingOff, otherSprite);
        fireBounceOffEvent(spriteBouncingOff, null);
    }

    public static void fireBounceOffEvent(Sprite bouncingSprite, Sprite staticSprite) {
        ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new BounceOffEventId(bouncingSprite, staticSprite), false));
    }

    public String toString() {
        return String.format("PhysicalCollision:\n     sprite1: %s\n     sprite2: %s\n     contactCounter: %s\n", this.objects.sprite1, this.objects.sprite2, String.valueOf(this.contactCounter));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof PhysicalCollision) {
            return Objects.equal(this.objects, ((PhysicalCollision) o).objects);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.objects);
    }
}
