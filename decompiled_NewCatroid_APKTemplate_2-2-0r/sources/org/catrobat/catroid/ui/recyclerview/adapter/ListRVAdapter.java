package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.adapter.UserListValuesAdapter;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.recyclerview.viewholder.ListViewHolder;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ListRVAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ListRVAdapter extends RVAdapter<UserList> {
    ListRVAdapter(List<UserList> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CheckableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        UserList userList = (UserList) this.items.get(position);
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        listViewHolder.title.setText(userList.getName());
        ArrayList arrayList = new ArrayList();
        List<Object> value = userList.getValue();
        if (value != null) {
            int size = value.size();
            int iMin = Math.min(size, 100);
            for (int i = 0; i < iMin; i++) {
                try {
                    String strConvertObjectToString = ShowTextUtils.convertObjectToString(value.get(i));
                    if (strConvertObjectToString != null && strConvertObjectToString.length() > 100) {
                        strConvertObjectToString = strConvertObjectToString.substring(0, 100) + "...";
                    }
                    arrayList.add(strConvertObjectToString);
                } catch (Exception unused) {
                }
            }
            if (size > 100) {
                arrayList.add("... (" + (size - 100) + " more items)");
            }
        }
        listViewHolder.spinner.setAdapter((SpinnerAdapter) new UserListValuesAdapter(holder.itemView.getContext(), arrayList));
    }
}
