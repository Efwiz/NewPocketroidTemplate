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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Ease3DPropertyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Ease3DPropertyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int propertySelectionIndex;
    private int typeSelectionIndex;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ease_3d_property;
    }

    public Ease3DPropertyBrick() {
        this.propertySelectionIndex = 0;
        this.typeSelectionIndex = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_ease_3d_property_object_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_ease_3d_property_start_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_ease_3d_property_end_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_ease_3d_property_duration_edit);
    }

    public Ease3DPropertyBrick(Formula objectId, int propertyIndex, int typeIndex, Formula start, Formula end, Formula duration) {
        this();
        this.propertySelectionIndex = propertyIndex;
        this.typeSelectionIndex = typeIndex;
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, objectId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, start);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, end);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, duration);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.ease_3d_property_target_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_ease_3d_properties, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.propertySelectionIndex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.Ease3DPropertyBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                Ease3DPropertyBrick.this.propertySelectionIndex = position;
            }
        });
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.ease_3d_property_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.brick_easing_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner2.setSelection(this.typeSelectionIndex);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.Ease3DPropertyBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                Ease3DPropertyBrick.this.typeSelectionIndex = position;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createEase3DPropertyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.propertySelectionIndex, this.typeSelectionIndex, getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
