package org.catrobat.catroid.ui.fragment;

import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/SpriteFactory.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SpriteFactory {
    public static final String SPRITE_BASE = "Sprite";
    public static final String SPRITE_GROUP = "GroupSprite";
    public static final String SPRITE_GROUP_ITEM = "GroupItemSprite";

    public Sprite newInstance(String type) {
        return newInstance(type, null);
    }

    public Sprite newInstance(String type, String name2) {
        if (type.equals(SPRITE_BASE)) {
            return new Sprite(name2);
        }
        if (type.equals(SPRITE_GROUP)) {
            return new GroupSprite(name2);
        }
        if (type.equals(SPRITE_GROUP_ITEM)) {
            return new GroupItemSprite(name2);
        }
        return null;
    }
}
