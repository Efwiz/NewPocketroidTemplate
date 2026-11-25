package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.Spinner;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/viewholder/ListViewHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ListViewHolder extends CheckableViewHolder {
    public Spinner spinner;

    public ListViewHolder(View itemView) {
        super(itemView);
        this.spinner = (Spinner) itemView.findViewById(R.id.spinner);
    }
}
