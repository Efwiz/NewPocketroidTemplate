package org.catrobat.catroid.ui.recyclerview.adapter;

import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ScriptAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScriptAdapter extends ExtendedRVAdapter<String> {
    public ScriptAdapter(List<String> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        holder.title.setText((CharSequence) this.items.get(position));
        holder.image.setImageResource(R.drawable.ic_program_menu_scripts);
    }
}
