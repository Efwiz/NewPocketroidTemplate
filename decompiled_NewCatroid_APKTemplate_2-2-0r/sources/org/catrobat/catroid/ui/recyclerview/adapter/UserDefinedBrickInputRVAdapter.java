package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.recyclerview.viewholder.VariableViewHolder;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/UserDefinedBrickInputRVAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDefinedBrickInputRVAdapter extends RVAdapter<UserDefinedBrickInput> {
    UserDefinedBrickInputRVAdapter(List<UserDefinedBrickInput> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VariableViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CheckableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position == 0) {
            ((TextView) holder.itemView.findViewById(R.id.headline)).setText(holder.itemView.getResources().getQuantityText(R.plurals.user_defined_brick_input_headline, getItemCount()));
        }
        UserDefinedBrickInput userDefinedBrickInput = (UserDefinedBrickInput) this.items.get(position);
        VariableViewHolder variableViewHolder = (VariableViewHolder) holder;
        variableViewHolder.title.setText(userDefinedBrickInput.getName());
        variableViewHolder.value.setText(ShowTextUtils.convertStringToMetricRepresentation(userDefinedBrickInput.getValue().getUserFriendlyString(new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext()), null)));
    }
}
