package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/viewholder/ViewHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public ViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title_view);
    }
}
