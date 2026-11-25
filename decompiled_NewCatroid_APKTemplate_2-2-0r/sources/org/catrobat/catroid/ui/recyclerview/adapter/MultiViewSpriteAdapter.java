package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.content.res.ResourcesCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/MultiViewSpriteAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MultiViewSpriteAdapter extends SpriteAdapter {
    private static final int BACKGROUND = 0;
    private static final int SPRITE_GROUP = 2;
    private static final int SPRITE_GROUP_ITEM = 3;
    private static final int SPRITE_SINGLE = 1;
    public static final String TAG = "MultiViewSpriteAdapter";
    private View backgroundView;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/MultiViewSpriteAdapter$ViewType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
    }

    public MultiViewSpriteAdapter(List<Sprite> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            this.backgroundView = layoutInflaterFrom.inflate(R.layout.view_holder_background_sprite, parent, false);
            return new ExtendedViewHolder(this.backgroundView);
        }
        if (viewType == 1) {
            return new ExtendedViewHolder(layoutInflaterFrom.inflate(R.layout.view_holder_with_checkbox, parent, false));
        }
        if (viewType == 2) {
            return new ExtendedViewHolder(layoutInflaterFrom.inflate(R.layout.view_holder_sprite_group, parent, false));
        }
        if (viewType == 3) {
            return new ExtendedViewHolder(layoutInflaterFrom.inflate(R.layout.view_holder_sprite_group_item, parent, false));
        }
        throw new IllegalArgumentException(TAG + ": viewType was not defined correctly.");
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.SpriteAdapter, org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        Drawable drawable;
        Context context = holder.itemView.getContext();
        Sprite sprite = (Sprite) this.items.get(position);
        holder.title.setText(sprite.getName());
        if (holder.getItemViewType() == 2) {
            if (((GroupSprite) sprite).isCollapsed()) {
                drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_play, context.getTheme());
            } else {
                drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_play_down, context.getTheme());
            }
            holder.image.setImageDrawable(drawable);
            holder.checkBox.setVisibility(8);
            return;
        }
        if (holder.getItemViewType() == 0) {
            holder.itemView.setOnLongClickListener(null);
            holder.checkBox.setVisibility(8);
        }
        if (holder.getItemViewType() == 3) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            holder.itemView.setLayoutParams(layoutParams);
            if (((GroupItemSprite) sprite).isCollapsed()) {
                layoutParams.height = 0;
                holder.itemView.setLayoutParams(layoutParams);
            }
        }
        holder.image.setImageBitmap(sprite.getLookList().isEmpty() ? null : sprite.getLookList().get(0).getThumbnailBitmap());
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.sprite_details), Integer.valueOf(sprite.getNumberOfScripts() + sprite.getNumberOfBricks()), Integer.valueOf(sprite.getLookList().size()), Integer.valueOf(sprite.getSoundList().size())));
            holder.details.setVisibility(0);
        } else {
            holder.details.setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (this.items.get(position) instanceof GroupSprite) {
            return 2;
        }
        return this.items.get(position) instanceof GroupItemSprite ? 3 : 1;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperAdapterInterface
    public boolean onItemMove(int sourcePosition, int targetPosition) {
        if (sourcePosition == 0 || targetPosition == 0) {
            return true;
        }
        Sprite sprite = (Sprite) this.items.get(sourcePosition);
        Sprite sprite2 = (Sprite) this.items.get(targetPosition);
        if (sprite instanceof GroupSprite) {
            return true;
        }
        if (sprite2 instanceof GroupSprite) {
            GroupSprite groupSprite = (GroupSprite) sprite2;
            if (targetPosition > sourcePosition) {
                targetPosition += groupSprite.getNumberOfItems();
                sprite.setConvertToGroupItemSprite(true);
            } else {
                sprite.setConvertToSprite(true);
            }
            return super.onItemMove(sourcePosition, targetPosition);
        }
        boolean z = sprite instanceof GroupItemSprite;
        if (!z && (sprite2 instanceof GroupItemSprite)) {
            sprite.setConvertToGroupItemSprite(true);
            return super.onItemMove(sourcePosition, targetPosition);
        }
        if (z && !(sprite2 instanceof GroupItemSprite)) {
            sprite.setConvertToSprite(true);
            return super.onItemMove(sourcePosition, targetPosition);
        }
        sprite.setConvertToGroupItemSprite(false);
        sprite.setConvertToSprite(false);
        return super.onItemMove(sourcePosition, targetPosition);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
    public boolean setSelection(Sprite item, boolean selection) {
        if (this.items.indexOf(item) == 0) {
            throw new IllegalArgumentException("You should never select the Background Sprite for any ActionMode operation. Modifying it via ActionMode is not supported.");
        }
        return super.setSelection(item, selection);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
    public void selectAll() {
        for (T t : this.items) {
            if (this.items.indexOf(t) != 0 && !(t instanceof GroupSprite)) {
                this.selectionManager.setSelectionTo(true, this.items.indexOf(t));
            }
        }
        notifyDataSetChanged();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
    public int getSelectableItemCount() {
        Iterator it = this.items.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((Sprite) it.next()) instanceof GroupSprite) {
                i++;
            }
        }
        return (this.items.size() - 1) - i;
    }

    public void setBackgroundVisible(int visible) {
        this.backgroundView.setVisibility(visible);
        if (visible == 8) {
            this.backgroundView.getLayoutParams().height = 0;
        } else {
            this.backgroundView.getLayoutParams().height = -2;
        }
    }
}
