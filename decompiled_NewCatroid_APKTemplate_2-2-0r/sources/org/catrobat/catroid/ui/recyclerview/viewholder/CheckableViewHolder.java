package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/viewholder/CheckableViewHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CheckableViewHolder extends ViewHolder {
    public CheckBox checkBox;
    public ImageButton settings;

    public CheckableViewHolder(View itemView) {
        super(itemView);
        this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        this.settings = (ImageButton) itemView.findViewById(R.id.settings_button);
    }
}
