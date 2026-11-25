package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.DeviceListAccessor;
import org.catrobat.catroid.io.DeviceVariableAccessor;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/DataListAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DataListAdapter extends RecyclerView.Adapter<CheckableViewHolder> implements RVAdapter.SelectionListener {
    private static final int LIST_GLOBAL = 4;
    private static final int LIST_LOCAL = 5;
    private static final int USER_DEFINED_BRICK_INPUTS = 0;
    private static final int VAR_GLOBAL = 2;
    private static final int VAR_LOCAL = 3;
    private static final int VAR_MULTIPLAYER = 1;
    public boolean allowMultiSelection = true;
    private final ListRVAdapter globalListAdapter;
    private final UserDataRVAdapter<UserVariable> globalVarAdapter;
    private final ListRVAdapter localListAdapter;
    private final UserDataRVAdapter<UserVariable> localVarAdapter;
    private final UserDataRVAdapter<UserVariable> multiplayerVarAdapter;
    private RVAdapter.SelectionListener selectionListener;
    private final UserDefinedBrickInputRVAdapter userDefinedBrickInputAdapter;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/DataListAdapter$DataType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface DataType {
    }

    public DataListAdapter(List<UserDefinedBrickInput> userDefinedBrickInputs, List<UserVariable> multiplayerVars, List<UserVariable> globalVars, List<UserVariable> localVars, List<UserList> globalLists, List<UserList> localLists) {
        UserDefinedBrickInputRVAdapter userDefinedBrickInputRVAdapter = new UserDefinedBrickInputRVAdapter(userDefinedBrickInputs);
        this.userDefinedBrickInputAdapter = userDefinedBrickInputRVAdapter;
        userDefinedBrickInputRVAdapter.setSelectionListener(this);
        UserDataRVAdapter<UserVariable> userDataRVAdapter = new UserDataRVAdapter<UserVariable>(multiplayerVars) { // from class: org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter.1
            @Override // org.catrobat.catroid.ui.recyclerview.adapter.UserDataRVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(CheckableViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.multiplayer_vars_headline);
                }
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 1));
            }
        };
        this.multiplayerVarAdapter = userDataRVAdapter;
        userDataRVAdapter.setSelectionListener(this);
        UserDataRVAdapter<UserVariable> userDataRVAdapter2 = new UserDataRVAdapter<UserVariable>(globalVars) { // from class: org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter.2
            @Override // org.catrobat.catroid.ui.recyclerview.adapter.UserDataRVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(CheckableViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.global_vars_headline);
                }
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 2));
            }
        };
        this.globalVarAdapter = userDataRVAdapter2;
        userDataRVAdapter2.setSelectionListener(this);
        UserDataRVAdapter<UserVariable> userDataRVAdapter3 = new UserDataRVAdapter<UserVariable>(localVars) { // from class: org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter.3
            @Override // org.catrobat.catroid.ui.recyclerview.adapter.UserDataRVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(CheckableViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.local_vars_headline);
                }
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 3));
            }
        };
        this.localVarAdapter = userDataRVAdapter3;
        userDataRVAdapter3.setSelectionListener(this);
        ListRVAdapter listRVAdapter = new ListRVAdapter(globalLists) { // from class: org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter.4
            @Override // org.catrobat.catroid.ui.recyclerview.adapter.ListRVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(CheckableViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.global_lists_headline);
                }
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 4));
            }
        };
        this.globalListAdapter = listRVAdapter;
        listRVAdapter.setSelectionListener(this);
        ListRVAdapter listRVAdapter2 = new ListRVAdapter(localLists) { // from class: org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter.5
            @Override // org.catrobat.catroid.ui.recyclerview.adapter.ListRVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(CheckableViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    ((TextView) holder.itemView.findViewById(R.id.headline)).setText(R.string.local_lists_headline);
                }
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter
            protected void onCheckBoxClick(int position) {
                super.onCheckBoxClick(DataListAdapter.this.getRelativeItemPosition(position, 5));
            }
        };
        this.localListAdapter = listRVAdapter2;
        listRVAdapter2.setSelectionListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRelativeItemPosition(int position, int dataType) {
        int itemCount;
        int itemCount2;
        int itemCount3;
        if (dataType == 0) {
            return position;
        }
        if (dataType != 1) {
            if (dataType == 2) {
                itemCount2 = this.userDefinedBrickInputAdapter.getItemCount();
                itemCount3 = this.multiplayerVarAdapter.getItemCount();
            } else if (dataType == 3) {
                itemCount2 = this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount();
                itemCount3 = this.globalVarAdapter.getItemCount();
            } else if (dataType == 4) {
                itemCount2 = this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount();
                itemCount3 = this.localVarAdapter.getItemCount();
            } else if (dataType == 5) {
                itemCount2 = this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount();
                itemCount3 = this.globalListAdapter.getItemCount();
            } else {
                throw new IllegalArgumentException("DataType is not specified: this would throw an index out of bounds exception.");
            }
            itemCount = itemCount2 + itemCount3;
        } else {
            itemCount = this.userDefinedBrickInputAdapter.getItemCount();
        }
        return position - itemCount;
    }

    private int getDataType(int position) {
        if (position < this.userDefinedBrickInputAdapter.getItemCount()) {
            return 0;
        }
        if (position < this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount()) {
            return 1;
        }
        if (position < this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount()) {
            return 2;
        }
        if (position < this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount()) {
            return 3;
        }
        if (position < this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount() + this.globalListAdapter.getItemCount()) {
            return 4;
        }
        if (position < this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount() + this.globalListAdapter.getItemCount() + this.localListAdapter.getItemCount()) {
            return 5;
        }
        throw new IndexOutOfBoundsException("None of the sub adapters provides this position. size:" + getItemCount() + "index: " + position);
    }

    public void showCheckBoxes(boolean visible) {
        this.multiplayerVarAdapter.showCheckBoxes = visible;
        this.multiplayerVarAdapter.showSettings = !visible;
        this.globalVarAdapter.showCheckBoxes = visible;
        this.globalVarAdapter.showSettings = !visible;
        this.localVarAdapter.showCheckBoxes = visible;
        this.localVarAdapter.showSettings = !visible;
        this.globalListAdapter.showCheckBoxes = visible;
        this.globalListAdapter.showSettings = !visible;
        this.localListAdapter.showCheckBoxes = visible;
        this.localListAdapter.showSettings = !visible;
    }

    public void setSelectionListener(RVAdapter.SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void setOnItemClickListener(RVAdapter.OnItemClickListener onItemClickListener) {
        this.userDefinedBrickInputAdapter.setOnItemClickListener(onItemClickListener);
        this.multiplayerVarAdapter.setOnItemClickListener(onItemClickListener);
        this.globalVarAdapter.setOnItemClickListener(onItemClickListener);
        this.localVarAdapter.setOnItemClickListener(onItemClickListener);
        this.globalListAdapter.setOnItemClickListener(onItemClickListener);
        this.localListAdapter.setOnItemClickListener(onItemClickListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.view_holder_list /* 2131559425 */:
            case R.layout.view_holder_list_with_headline /* 2131559426 */:
                return this.globalListAdapter.onCreateViewHolder(parent, viewType);
            case R.layout.view_holder_sprite_group /* 2131559427 */:
            case R.layout.view_holder_sprite_group_item /* 2131559428 */:
            default:
                throw new IllegalArgumentException("ViewType was not defined correctly.");
            case R.layout.view_holder_variable /* 2131559429 */:
            case R.layout.view_holder_variable_with_headline /* 2131559430 */:
                return this.globalVarAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        int dataType = getDataType(position);
        int relativeItemPosition = getRelativeItemPosition(position, dataType);
        if (dataType == 0 || dataType == 1 || dataType == 2 || dataType == 3) {
            return relativeItemPosition == 0 ? R.layout.view_holder_variable_with_headline : R.layout.view_holder_variable;
        }
        if (dataType == 4 || dataType == 5) {
            return relativeItemPosition == 0 ? R.layout.view_holder_list_with_headline : R.layout.view_holder_list;
        }
        throw new ArrayIndexOutOfBoundsException("position is not within any of the adapters");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CheckableViewHolder holder, int position) {
        int dataType = getDataType(position);
        int relativeItemPosition = getRelativeItemPosition(position, dataType);
        switch (holder.getItemViewType()) {
            case R.layout.view_holder_list /* 2131559425 */:
            case R.layout.view_holder_list_with_headline /* 2131559426 */:
                if (dataType == 4) {
                    this.globalListAdapter.onBindViewHolder(holder, relativeItemPosition);
                } else {
                    this.localListAdapter.onBindViewHolder(holder, relativeItemPosition);
                }
                break;
            case R.layout.view_holder_variable /* 2131559429 */:
            case R.layout.view_holder_variable_with_headline /* 2131559430 */:
                if (dataType == 2) {
                    this.globalVarAdapter.onBindViewHolder(holder, relativeItemPosition);
                } else if (dataType == 3) {
                    this.localVarAdapter.onBindViewHolder(holder, relativeItemPosition);
                } else if (dataType == 1) {
                    this.multiplayerVarAdapter.onBindViewHolder(holder, relativeItemPosition);
                } else if (dataType == 0) {
                    this.userDefinedBrickInputAdapter.onBindViewHolder(holder, relativeItemPosition);
                }
                break;
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        this.selectionListener.onSelectionChanged(this.multiplayerVarAdapter.getSelectedItems().size() + this.globalVarAdapter.getSelectedItems().size() + this.localVarAdapter.getSelectedItems().size() + this.globalListAdapter.getSelectedItems().size() + this.localListAdapter.getSelectedItems().size());
    }

    public void updateDataSet() {
        this.userDefinedBrickInputAdapter.notifyDataSetChanged();
        this.multiplayerVarAdapter.notifyDataSetChanged();
        this.globalVarAdapter.notifyDataSetChanged();
        this.localVarAdapter.notifyDataSetChanged();
        this.globalListAdapter.notifyDataSetChanged();
        this.localListAdapter.notifyDataSetChanged();
        notifyDataSetChanged();
    }

    public void clearSelection() {
        this.multiplayerVarAdapter.clearSelection();
        this.globalVarAdapter.clearSelection();
        this.localVarAdapter.clearSelection();
        this.globalListAdapter.clearSelection();
        this.localListAdapter.clearSelection();
        notifyDataSetChanged();
    }

    public void selectAll() {
        this.multiplayerVarAdapter.selectAll();
        this.globalVarAdapter.selectAll();
        this.localVarAdapter.selectAll();
        this.globalListAdapter.selectAll();
        this.localListAdapter.selectAll();
        notifyDataSetChanged();
    }

    public void remove(UserData item) {
        if (item instanceof UserVariable) {
            UserVariable userVariable = (UserVariable) item;
            if (!this.globalVarAdapter.remove(userVariable) && !this.localVarAdapter.remove(userVariable)) {
                this.multiplayerVarAdapter.remove(userVariable);
            }
            new DeviceVariableAccessor(ProjectManager.getInstance().getCurrentProject().getDirectory()).removeDeviceValue(item);
        } else {
            UserList userList = (UserList) item;
            if (!this.globalListAdapter.remove(userList)) {
                this.localListAdapter.remove(userList);
            }
            new DeviceListAccessor(ProjectManager.getInstance().getCurrentProject().getDirectory()).removeDeviceValue(item);
        }
        notifyDataSetChanged();
    }

    public List<UserData> getItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.userDefinedBrickInputAdapter.getItems());
        arrayList.addAll(this.multiplayerVarAdapter.getItems());
        arrayList.addAll(this.globalVarAdapter.getItems());
        arrayList.addAll(this.localVarAdapter.getItems());
        arrayList.addAll(this.globalListAdapter.getItems());
        arrayList.addAll(this.localListAdapter.getItems());
        return arrayList;
    }

    public List<UserVariable> getVariables() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.multiplayerVarAdapter.getItems());
        arrayList.addAll(this.globalVarAdapter.getItems());
        arrayList.addAll(this.localVarAdapter.getItems());
        return arrayList;
    }

    public List<UserList> getLists() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.globalListAdapter.getItems());
        arrayList.addAll(this.localListAdapter.getItems());
        return arrayList;
    }

    public List<UserData> getSelectedItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.multiplayerVarAdapter.getSelectedItems());
        arrayList.addAll(this.globalVarAdapter.getSelectedItems());
        arrayList.addAll(this.localVarAdapter.getSelectedItems());
        arrayList.addAll(this.globalListAdapter.getSelectedItems());
        arrayList.addAll(this.localListAdapter.getSelectedItems());
        return arrayList;
    }

    public void setSelection(UserData item, boolean selection) {
        if (item instanceof UserVariable) {
            UserVariable userVariable = (UserVariable) item;
            if (this.globalVarAdapter.setSelection(userVariable, selection) || this.localVarAdapter.setSelection(userVariable, selection)) {
                return;
            }
            this.multiplayerVarAdapter.setSelection(userVariable, selection);
            return;
        }
        UserList userList = (UserList) item;
        if (this.globalListAdapter.setSelection(userList, selection)) {
            return;
        }
        this.localListAdapter.setSelection(userList, selection);
    }

    public void toggleSelection(UserData item) {
        if (item instanceof UserVariable) {
            UserVariable userVariable = (UserVariable) item;
            if (!this.globalVarAdapter.toggleSelection(userVariable) && !this.localVarAdapter.toggleSelection(userVariable)) {
                this.multiplayerVarAdapter.toggleSelection(userVariable);
            }
        } else {
            UserList userList = (UserList) item;
            if (!this.globalListAdapter.toggleSelection(userList)) {
                this.localListAdapter.toggleSelection(userList);
            }
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.userDefinedBrickInputAdapter.getItemCount() + this.multiplayerVarAdapter.getItemCount() + this.globalVarAdapter.getItemCount() + this.localVarAdapter.getItemCount() + this.globalListAdapter.getItemCount() + this.localListAdapter.getItemCount();
    }

    public int getSelectedItemCount() {
        return this.multiplayerVarAdapter.getSelectedItemCount() + this.globalVarAdapter.getSelectedItemCount() + this.localVarAdapter.getSelectedItemCount() + this.globalListAdapter.getSelectedItemCount() + this.localListAdapter.getSelectedItemCount();
    }
}
