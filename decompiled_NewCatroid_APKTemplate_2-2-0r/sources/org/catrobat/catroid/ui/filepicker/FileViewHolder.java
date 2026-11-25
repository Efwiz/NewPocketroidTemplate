package org.catrobat.catroid.ui.filepicker;

import android.view.View;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/FileViewHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
class FileViewHolder extends CheckableViewHolder {
    public TextView subtitle;

    FileViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title_view);
        this.subtitle = (TextView) itemView.findViewById(R.id.subtitle_view);
    }
}
