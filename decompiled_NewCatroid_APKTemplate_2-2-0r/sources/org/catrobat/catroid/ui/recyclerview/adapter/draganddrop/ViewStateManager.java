package org.catrobat.catroid.ui.recyclerview.adapter.draganddrop;

import java.util.SortedSet;
import java.util.TreeSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/draganddrop/ViewStateManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ViewStateManager {
    private SortedSet<Integer> invisiblePositions = new TreeSet();
    private SortedSet<Integer> disabledPositions = new TreeSet();

    public boolean isVisible(int position) {
        return !this.invisiblePositions.contains(Integer.valueOf(position));
    }

    public void setVisible(int position, boolean visible) {
        if (visible) {
            this.invisiblePositions.remove(Integer.valueOf(position));
        } else {
            this.invisiblePositions.add(Integer.valueOf(position));
        }
    }

    public void setAllPositionsVisible() {
        this.invisiblePositions.clear();
    }

    public boolean isEnabled(int position) {
        return !this.disabledPositions.contains(Integer.valueOf(position));
    }

    public void setEnabled(boolean enabled, int position) {
        if (enabled) {
            this.disabledPositions.remove(Integer.valueOf(position));
        } else {
            this.disabledPositions.add(Integer.valueOf(position));
        }
    }

    public void clearDisabledPositions() {
        this.disabledPositions.clear();
    }
}
