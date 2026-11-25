package org.catrobat.catroid.content;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/GroupItemSprite.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GroupItemSprite extends Sprite {
    private static final long serialVersionUID = 1;
    private transient boolean collapsed;

    public GroupItemSprite() {
        this.collapsed = true;
    }

    public GroupItemSprite(String name2) {
        super(name2);
        this.collapsed = true;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }
}
