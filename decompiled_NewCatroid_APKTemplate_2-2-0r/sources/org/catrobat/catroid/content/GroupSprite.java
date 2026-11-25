package org.catrobat.catroid.content;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/GroupSprite.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GroupSprite extends Sprite {
    private static final long serialVersionUID = 1;
    private transient boolean collapsed;

    public GroupSprite() {
        this.collapsed = true;
    }

    public GroupSprite(String name2) {
        super(name2);
        this.collapsed = true;
    }

    public List<GroupItemSprite> getGroupItems() {
        List<Sprite> spriteList = ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList();
        ArrayList arrayList = new ArrayList();
        for (Sprite sprite : spriteList.subList(spriteList.indexOf(this) + 1, spriteList.size())) {
            if (!(sprite instanceof GroupItemSprite)) {
                break;
            }
            arrayList.add((GroupItemSprite) sprite);
        }
        return arrayList;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
        Iterator<GroupItemSprite> it = getGroupItems().iterator();
        while (it.hasNext()) {
            it.next().setCollapsed(collapsed);
        }
    }

    public int getNumberOfItems() {
        return getGroupItems().size();
    }

    public static List<Sprite> getSpritesFromGroupWithGroupName(String groupName, List<Sprite> sprites) {
        ArrayList arrayList = new ArrayList();
        Iterator<Sprite> it = sprites.iterator();
        int i = 0;
        while (it.hasNext() && !groupName.equals(it.next().getName())) {
            i++;
        }
        while (true) {
            i++;
            if (i >= sprites.size()) {
                break;
            }
            Sprite sprite = sprites.get(i);
            if (!(sprite instanceof GroupItemSprite)) {
                break;
            }
            arrayList.add(sprite);
        }
        return arrayList;
    }

    @Override // org.catrobat.catroid.content.Sprite
    public void createCollisionPolygons() {
        Log.i("GroupSprite", "Creating Collision Polygons for all Sprites of group!");
        Iterator<Sprite> it = getSpritesFromGroupWithGroupName(getName(), ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList()).iterator();
        while (it.hasNext()) {
            Iterator<LookData> it2 = it.next().getLookList().iterator();
            while (it2.hasNext()) {
                it2.next().getCollisionInformation().calculate();
            }
        }
    }
}
