package org.catrobat.catroid.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/RecentBricksHolder.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class RecentBricksHolder implements Serializable {
    private List<Brick> recentBricks = new ArrayList();

    public List<Brick> getRecentBricks() {
        return this.recentBricks;
    }

    public int find(Brick brick) {
        for (int i = 0; i < this.recentBricks.size(); i++) {
            Brick brick2 = this.recentBricks.get(i);
            if (brick2 != null && brick2.getClass().equals(brick.getClass())) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return this.recentBricks.size();
    }

    public void remove() {
        this.recentBricks.remove(size() - 1);
    }

    public void remove(int index) {
        this.recentBricks.remove(index);
    }

    public void insert(Brick brick) {
        this.recentBricks.add(0, brick);
    }
}
