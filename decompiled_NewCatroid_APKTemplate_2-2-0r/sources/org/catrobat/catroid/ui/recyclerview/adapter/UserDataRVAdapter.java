package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.recyclerview.viewholder.VariableViewHolder;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/UserDataRVAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDataRVAdapter<T extends UserData> extends RVAdapter<T> {
    public boolean showSettings;

    UserDataRVAdapter(List<T> items) {
        super(items);
        this.showSettings = true;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VariableViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CheckableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        T t = this.items.get(position);
        VariableViewHolder variableViewHolder = (VariableViewHolder) holder;
        variableViewHolder.title.setText(t.getName());
        String strConvertObjectToString = ShowTextUtils.convertObjectToString(t.getValue());
        if (strConvertObjectToString != null && strConvertObjectToString.length() > 150) {
            strConvertObjectToString = strConvertObjectToString.substring(0, 150) + "...";
        }
        variableViewHolder.value.setText(strConvertObjectToString);
        ImageButton imageButton = (ImageButton) holder.itemView.findViewById(R.id.settings_button);
        if (imageButton != null && this.showSettings) {
            imageButton.setVisibility(0);
        } else {
            if (imageButton == null || this.showSettings) {
                return;
            }
            imageButton.setVisibility(8);
        }
    }
}
