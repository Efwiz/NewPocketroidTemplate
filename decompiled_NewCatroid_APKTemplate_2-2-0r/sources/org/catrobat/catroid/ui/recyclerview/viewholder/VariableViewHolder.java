package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/viewholder/VariableViewHolder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VariableViewHolder extends CheckableViewHolder {
    public TextView value;

    public VariableViewHolder(View itemView) {
        super(itemView);
        this.value = (TextView) itemView.findViewById(R.id.value_view);
    }
}
