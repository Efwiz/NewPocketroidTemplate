package org.catrobat.catroid.ui.recyclerview.adapter;

import android.widget.ImageView;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/NfcTagAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NfcTagAdapter extends ExtendedRVAdapter<NfcTagData> {
    public NfcTagAdapter(List<NfcTagData> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        holder.title.setText(((NfcTagData) this.items.get(position)).getName());
        holder.image.setImageResource(R.drawable.ic_program_menu_nfc);
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.ic_ripples);
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }
}
