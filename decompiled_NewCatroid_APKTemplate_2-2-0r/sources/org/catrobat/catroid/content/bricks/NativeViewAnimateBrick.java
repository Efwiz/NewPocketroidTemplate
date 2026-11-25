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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NativeViewAnimateBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NativeViewAnimateBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int easingSelection;
    private int propertySelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_native_view_animate;
    }

    public NativeViewAnimateBrick() {
        this.propertySelection = 0;
        this.easingSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_value_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_duration_field);
    }

    public NativeViewAnimateBrick(String viewId, int propertySelection, double value, int durationMs, int easingSelection) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(value)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Integer.valueOf(durationMs)));
        this.propertySelection = propertySelection;
        this.easingSelection = easingSelection;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_property_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.native_view_animate_properties_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.propertySelection);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.NativeViewAnimateBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                NativeViewAnimateBrick.this.propertySelection = position;
            }
        });
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.brick_easing_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.brick_easing_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner2.setSelection(this.easingSelection);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.NativeViewAnimateBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                NativeViewAnimateBrick.this.easingSelection = position;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNativeViewAnimateAction(sprite, sequence, this.propertySelection, this.easingSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
