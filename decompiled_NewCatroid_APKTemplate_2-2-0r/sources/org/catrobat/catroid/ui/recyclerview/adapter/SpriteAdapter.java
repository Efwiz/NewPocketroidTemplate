package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.List;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/SpriteAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SpriteAdapter extends ExtendedRVAdapter<Sprite> {
    public SpriteAdapter(List<Sprite> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Sprite sprite = (Sprite) this.items.get(position);
        Bitmap thumbnailBitmap = !sprite.getLookList().isEmpty() ? sprite.getLookList().get(0).getThumbnailBitmap() : null;
        holder.title.setText(sprite.getName());
        holder.image.setImageBitmap(thumbnailBitmap);
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.sprite_details), Integer.valueOf(sprite.getNumberOfScripts() + sprite.getNumberOfBricks()), Integer.valueOf(sprite.getLookList().size()), Integer.valueOf(sprite.getSoundList().size())));
            holder.details.setVisibility(0);
        } else {
            holder.details.setVisibility(8);
        }
    }
}
