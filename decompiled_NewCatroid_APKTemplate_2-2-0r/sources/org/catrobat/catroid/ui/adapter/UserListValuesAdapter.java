package org.catrobat.catroid.ui.adapter;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/UserListValuesAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserListValuesAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<String> userListValuesList;

    @Override // android.widget.Adapter
    public long getItemId(int index) {
        return index;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/UserListValuesAdapter$ViewHolder.class
      classes14.dex
     */
    private static class ViewHolder {
        private TextView text1;

        private ViewHolder() {
        }
    }

    public UserListValuesAdapter(Context context, List<String> userListValuesList) {
        this.context = context;
        this.userListValuesList = userListValuesList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.userListValuesList.size() == 0) {
            return 1;
        }
        return this.userListValuesList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int index) {
        if (index < this.userListValuesList.size()) {
            return this.userListValuesList.get(index);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(this.context, R.layout.simple_spinner_dropdown_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        } else if (view.getTag() instanceof ViewHolder) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        }
        viewHolder.text1.setText(view.getContext().getString(org.DanVexTeam.NewCatroidApkTemplate.R.string.formula_editor_fragment_data_current_items));
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.simple_spinner_dropdown_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        } else if (view.getTag() instanceof ViewHolder) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        }
        view.setOnClickListener(this);
        String string = getItem(i) != null ? getItem(i).toString() : null;
        if (string == null) {
            string = "";
        }
        viewHolder.text1.setText(string);
        return view;
    }
}
