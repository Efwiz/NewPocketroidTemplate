package org.catrobat.catroid.editor;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.SceneManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/HierarchyDragCallback.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HierarchyDragCallback extends ItemTouchHelper.SimpleCallback {
    private final EditorActivity activity;
    private final HierarchyAdapter adapter;
    private final DraggableLinearLayoutManager layoutManager;
    private final SceneManager sceneManager;

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    public HierarchyDragCallback(HierarchyAdapter adapter, SceneManager sceneManager, EditorActivity activity, DraggableLinearLayoutManager layoutManager) {
        super(3, 0);
        this.adapter = adapter;
        this.sceneManager = sceneManager;
        this.activity = activity;
        this.layoutManager = layoutManager;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == 2) {
            this.layoutManager.setScrollEnabled(false);
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int adapterPosition = viewHolder.getAdapterPosition();
        int adapterPosition2 = target.getAdapterPosition();
        if (adapterPosition >= 0 && adapterPosition2 >= 0 && adapterPosition < this.adapter.getItemCount() && adapterPosition2 < this.adapter.getItemCount()) {
            GameObject gameObject = this.adapter.getItems().get(adapterPosition).gameObject;
            GameObject gameObject2 = this.adapter.getItems().get(adapterPosition2).gameObject;
            if (gameObject.parentId != null && gameObject.parentId.equals(gameObject2.f591id)) {
                this.sceneManager.setParent(gameObject, null);
            } else if (gameObject != gameObject2 && !isDescendant(gameObject2, gameObject)) {
                this.sceneManager.setParent(gameObject, gameObject2);
            }
            this.adapter.getItems().add(adapterPosition2, this.adapter.getItems().remove(adapterPosition));
            this.adapter.notifyItemMoved(adapterPosition, adapterPosition2);
            return true;
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        this.layoutManager.setScrollEnabled(true);
        recyclerView.post(new Runnable() { // from class: org.catrobat.catroid.editor.HierarchyDragCallback$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5070x9f1a0cec();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$clearView$0$org-catrobat-catroid-editor-HierarchyDragCallback, reason: not valid java name */
    /* synthetic */ void m5070x9f1a0cec() {
        this.activity.updateHierarchy();
    }

    private boolean isDescendant(GameObject p, GameObject c) {
        if (c == null || p == null || c.parentId == null) {
            return false;
        }
        if (c.parentId.equals(p.f591id)) {
            return true;
        }
        return isDescendant(p, this.sceneManager.findGameObject(c.parentId));
    }
}
