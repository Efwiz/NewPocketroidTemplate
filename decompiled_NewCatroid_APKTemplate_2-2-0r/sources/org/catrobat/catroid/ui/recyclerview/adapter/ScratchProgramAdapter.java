package org.catrobat.catroid.ui.recyclerview.adapter;

import com.squareup.picasso.Picasso;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ScratchProgramAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchProgramAdapter extends ExtendedRVAdapter<ScratchProgramData> {
    public ScratchProgramAdapter(List<ScratchProgramData> objects) {
        super(objects);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        ScratchProgramData scratchProgramData = (ScratchProgramData) this.items.get(position);
        holder.title.setText(scratchProgramData.getTitle());
        if (scratchProgramData.getImage().getUrl() != null) {
            Picasso.get().load(Utils.changeSizeOfScratchImageURL(scratchProgramData.getImage().getUrl().toString(), holder.image.getContext().getResources().getDimensionPixelSize(R.dimen.scratch_project_thumbnail_height))).into(holder.image);
        } else {
            holder.image.setImageBitmap(null);
        }
        if (this.showDetails) {
            holder.details.setVisibility(0);
            holder.details.setText(scratchProgramData.getOwner());
        } else {
            holder.details.setVisibility(8);
        }
    }
}
