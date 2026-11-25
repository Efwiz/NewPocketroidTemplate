package org.catrobat.catroid.ui.filepicker;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/SelectActionModeCallback.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SelectActionModeCallback implements ActionMode.Callback {
    private ActionModeClickListener clickListener;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/SelectActionModeCallback$ActionModeClickListener.class
      classes14.dex
     */
    public interface ActionModeClickListener {
        void endMultiSelectionMode();

        MenuInflater getMenuInflater();

        boolean hasUnselectedItems();

        void onConfirm();

        void onToggleSelection();
    }

    public SelectActionModeCallback(ActionModeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        this.clickListener.getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.findItem(R.id.overflow).setVisible(true);
        menu.findItem(R.id.toggle_selection).setVisible(true);
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.overflow).setVisible(true);
        MenuItem menuItemFindItem = menu.findItem(R.id.toggle_selection);
        menuItemFindItem.setVisible(true);
        if (this.clickListener.hasUnselectedItems()) {
            menuItemFindItem.setTitle(R.string.select_all);
        } else {
            menuItemFindItem.setTitle(R.string.deselect_all);
        }
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.confirm) {
            this.clickListener.onConfirm();
            return true;
        }
        if (itemId != R.id.toggle_selection) {
            return true;
        }
        this.clickListener.onToggleSelection();
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode mode) {
        this.clickListener.endMultiSelectionMode();
    }
}
