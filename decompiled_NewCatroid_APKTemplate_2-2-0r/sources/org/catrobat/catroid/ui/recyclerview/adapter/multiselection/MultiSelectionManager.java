package org.catrobat.catroid.ui.recyclerview.adapter.multiselection;

import java.util.SortedSet;
import java.util.TreeSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MultiSelectionManager {
    private SortedSet<Integer> selectedPositions = new TreeSet();

    public void toggleSelection(int position) {
        if (isPositionSelected(position)) {
            removeSelection(position);
        } else {
            setSelection(position);
        }
    }

    public void setSelectionTo(boolean selection, int position) {
        if (selection) {
            setSelection(position);
        } else {
            removeSelection(position);
        }
    }

    public boolean isPositionSelected(int position) {
        return this.selectedPositions.contains(Integer.valueOf(position));
    }

    private void setSelection(int position) {
        this.selectedPositions.add(Integer.valueOf(position));
    }

    private void removeSelection(int position) {
        this.selectedPositions.remove(Integer.valueOf(position));
    }

    public SortedSet<Integer> getSelectedPositions() {
        return this.selectedPositions;
    }

    public void updateSelection(int fromPosition, int toPosition) {
        if (this.selectedPositions.contains(Integer.valueOf(fromPosition)) && !this.selectedPositions.contains(Integer.valueOf(toPosition))) {
            this.selectedPositions.remove(Integer.valueOf(fromPosition));
            this.selectedPositions.add(Integer.valueOf(toPosition));
        } else {
            if (!this.selectedPositions.contains(Integer.valueOf(toPosition)) || this.selectedPositions.contains(Integer.valueOf(fromPosition))) {
                return;
            }
            this.selectedPositions.remove(Integer.valueOf(toPosition));
            this.selectedPositions.add(Integer.valueOf(fromPosition));
        }
    }

    public boolean isSelectionActive() {
        return !this.selectedPositions.isEmpty();
    }

    public void clearSelection() {
        this.selectedPositions.clear();
    }
}
