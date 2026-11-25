package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/viewholder/ExtendedViewHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ExtendedViewHolder extends CheckableViewHolder {
    public TextView details;
    public ImageView image;

    public ExtendedViewHolder(View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.image_view);
        this.details = (TextView) itemView.findViewById(R.id.details_view);
    }
}
