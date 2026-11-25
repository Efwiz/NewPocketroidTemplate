package org.catrobat.catroid.content.bricks.brickspinner;

import android.R;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/BrickSpinner.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BrickSpinner<T extends Nameable> implements AdapterView.OnItemSelectedListener {
    private BrickSpinner<T>.BrickSpinnerAdapter adapter;
    private OnItemSelectedListener<T> onItemSelectedListener;
    private T previousItem;
    private Spinner spinner;
    private Integer spinnerid;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/BrickSpinner$OnItemSelectedListener.class
      classes14.dex
     */
    public interface OnItemSelectedListener<T> {
        void onEditOptionSelected(Integer spinnerId);

        void onItemSelected(Integer spinnerId, T item);

        void onNewOptionSelected(Integer spinnerId);

        void onStringOptionSelected(Integer spinnerId, String string);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public BrickSpinner(Integer spinnerId, View parent, int spinnerLayout, List<Nameable> items) {
        this.spinnerid = spinnerId;
        this.adapter = new BrickSpinnerAdapter(parent.getContext(), spinnerLayout, items);
        Spinner spinner = (Spinner) parent.findViewById(spinnerId.intValue());
        this.spinner = spinner;
        spinner.setAdapter((SpinnerAdapter) this.adapter);
        this.spinner.setSelection(0);
        this.spinner.setOnItemSelectedListener(this);
    }

    public BrickSpinner(Integer spinnerId, View parent, List<Nameable> items) {
        this(spinnerId, parent, R.layout.simple_spinner_item, items);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
        T item = this.adapter.getItem(position);
        if (this.onItemSelectedListener == null || item == null || item.getClass().equals(NewOption.class) || item.getClass().equals(EditOption.class)) {
            return;
        }
        onSelectionChanged(view, item);
        if (item.getClass().equals(StringOption.class)) {
            this.onItemSelectedListener.onStringOptionSelected(this.spinnerid, item.getName());
        } else {
            this.onItemSelectedListener.onItemSelected(this.spinnerid, item);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void onSelectionChanged(View view, Nameable item) {
        T t = this.previousItem;
        if (t != null && t != item) {
            showUndo(view);
        }
        this.previousItem = item;
    }

    public void add(T item) {
        this.adapter.add(item);
    }

    public List<T> getItems() {
        return (List<T>) this.adapter.getItems();
    }

    public void setSelection(int position) {
        this.spinner.setSelection(position);
    }

    public void setSelection(String itemName) {
        this.spinner.setOnItemSelectedListener(null);
        int iConsolidateSpinnerSelection = consolidateSpinnerSelection(this.adapter.getPosition(itemName));
        this.spinner.setSelection(iConsolidateSpinnerSelection);
        onSelectionSet(this.adapter.getItem(iConsolidateSpinnerSelection));
        this.spinner.setOnItemSelectedListener(this);
    }

    public void setSelection(T item) {
        this.spinner.setOnItemSelectedListener(null);
        int iConsolidateSpinnerSelection = consolidateSpinnerSelection(this.adapter.getPosition(item));
        if (iConsolidateSpinnerSelection < this.adapter.getCount()) {
            this.spinner.setSelection(iConsolidateSpinnerSelection);
            onSelectionSet(this.adapter.getItem(iConsolidateSpinnerSelection));
            this.spinner.setOnItemSelectedListener(this);
        }
    }

    public Object getSelection() {
        return this.spinner.getSelectedItem();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0026, code lost:
    
        if (r2.adapter.getCount() > 1) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:?, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x001b, code lost:
    
        if (r2.adapter.getCount() > 2) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int consolidateSpinnerSelection(int r3) {
        /*
            r2 = this;
            r0 = -1
            if (r3 != r0) goto L2a
            org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner<T>$BrickSpinnerAdapter r3 = r2.adapter
            boolean r3 = r3.containsNewOption()
            r0 = 0
            if (r3 == 0) goto L29
            org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner<T>$BrickSpinnerAdapter r3 = r2.adapter
            boolean r3 = r3.containsEditOption()
            if (r3 == 0) goto L1f
            org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner<T>$BrickSpinnerAdapter r3 = r2.adapter
            int r3 = r3.getCount()
            r1 = 2
            if (r3 <= r1) goto L29
        L1d:
            r3 = r1
            goto L2a
        L1f:
            org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner<T>$BrickSpinnerAdapter r3 = r2.adapter
            int r3 = r3.getCount()
            r1 = 1
            if (r3 <= r1) goto L29
            goto L1d
        L29:
            r3 = r0
        L2a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.consolidateSpinnerSelection(int):int");
    }

    private void onSelectionSet(Nameable selectedItem) {
        if (this.onItemSelectedListener != null) {
            if (selectedItem.getClass().equals(NewOption.class) || selectedItem.getClass().equals(EditOption.class)) {
                this.onItemSelectedListener.onItemSelected(this.spinnerid, null);
            } else if (selectedItem.getClass().equals(StringOption.class)) {
                this.onItemSelectedListener.onStringOptionSelected(this.spinnerid, selectedItem.getName());
            } else {
                this.onItemSelectedListener.onItemSelected(this.spinnerid, selectedItem);
            }
        }
    }

    private void showUndo(View view) {
        ScriptFragment scriptFragment = getScriptFragment(view);
        if (scriptFragment.copyProjectForUndoOption()) {
            scriptFragment.showUndo(true);
            OnItemSelectedListener<T> onItemSelectedListener = this.onItemSelectedListener;
            if (onItemSelectedListener instanceof Brick) {
                scriptFragment.setUndoBrickPosition((Brick) onItemSelectedListener);
            }
        }
    }

    private ScriptFragment getScriptFragment(View view) {
        AppCompatActivity activityFromView = view != null ? UiUtils.getActivityFromView(view) : null;
        if (activityFromView == null) {
            return null;
        }
        Fragment fragmentFindFragmentByTag = activityFromView.getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        if (fragmentFindFragmentByTag instanceof ScriptFragment) {
            return (ScriptFragment) fragmentFindFragmentByTag;
        }
        Fragment fragmentFindFragmentById = activityFromView.getSupportFragmentManager().findFragmentById(org.DanVexTeam.NewCatroidApkTemplate.R.id.fragment_container);
        if (fragmentFindFragmentById instanceof ScriptFragment) {
            return (ScriptFragment) fragmentFindFragmentById;
        }
        return null;
    }

    public void setSpinnerFontColor(Context context, int color) {
        this.spinner.getBackground().setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/BrickSpinner$BrickSpinnerAdapter.class
      classes14.dex
     */
    public class BrickSpinnerAdapter extends ArrayAdapter<Nameable> {
        private List<Nameable> items;
        private int resource;

        BrickSpinnerAdapter(Context context, int resource, List<Nameable> items) {
            super(context, resource, items);
            this.resource = resource;
            this.items = items;
        }

        @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_spinner_dropdown_item, parent, false);
            }
            final Nameable item = getItem(position);
            ((TextView) convertView).setText(item.getName());
            convertView.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.BrickSpinnerAdapter.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getActionIndex() != 0) {
                        return false;
                    }
                    if (item.getClass().equals(NewOption.class)) {
                        BrickSpinner.this.onItemSelectedListener.onNewOptionSelected(BrickSpinner.this.spinnerid);
                        return false;
                    }
                    if (!item.getClass().equals(EditOption.class)) {
                        return false;
                    }
                    BrickSpinner.this.onItemSelectedListener.onEditOptionSelected(BrickSpinner.this.spinnerid);
                    return false;
                }
            });
            return convertView;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(this.resource, parent, false);
            }
            ((TextView) convertView).setText(getItem(position).getName());
            return convertView;
        }

        int getPosition(String itemName) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).getName().equals(itemName)) {
                    return i;
                }
            }
            return -1;
        }

        List<Nameable> getItems() {
            return this.items;
        }

        boolean containsNewOption() {
            Iterator<Nameable> it = this.items.iterator();
            while (it.hasNext()) {
                if (it.next() instanceof NewOption) {
                    return true;
                }
            }
            return false;
        }

        boolean containsEditOption() {
            Iterator<Nameable> it = this.items.iterator();
            while (it.hasNext()) {
                if (it.next() instanceof EditOption) {
                    return true;
                }
            }
            return false;
        }
    }
}
