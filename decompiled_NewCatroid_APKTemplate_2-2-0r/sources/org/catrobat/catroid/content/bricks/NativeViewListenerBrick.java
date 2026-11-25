package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NativeViewListenerBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NativeViewListenerBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;
    private int eventSelection;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_native_view_listener;
    }

    public NativeViewListenerBrick() {
        this.eventSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
    }

    public NativeViewListenerBrick(String viewId, int eventSelection, UserVariable userVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        this.userVariable = userVariable;
        this.eventSelection = eventSelection;
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_event_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.native_view_events_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.eventSelection);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.NativeViewListenerBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                NativeViewListenerBrick.this.eventSelection = position;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNativeViewListenerAction(sprite, sequence, this.eventSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.userVariable));
    }
}
