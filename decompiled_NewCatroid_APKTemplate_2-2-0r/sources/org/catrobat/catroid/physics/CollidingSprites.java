package org.catrobat.catroid.physics;

import java.util.Objects;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/CollidingSprites.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CollidingSprites {
    public final Sprite sprite1;
    public final Sprite sprite2;

    public CollidingSprites(Sprite sprite1, Sprite sprite2) {
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollidingSprites)) {
            return false;
        }
        CollidingSprites collidingSprites = (CollidingSprites) o;
        if (Objects.equals(this.sprite1, collidingSprites.sprite1) && Objects.equals(this.sprite2, collidingSprites.sprite2)) {
            return true;
        }
        return Objects.equals(this.sprite2, collidingSprites.sprite1) && Objects.equals(this.sprite1, collidingSprites.sprite2);
    }

    public int hashCode() {
        return Objects.hash(this.sprite1) + Objects.hash(this.sprite2);
    }
}
