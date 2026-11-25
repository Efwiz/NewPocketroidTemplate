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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/EasePropertyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EasePropertyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int propertySelectionIndex;
    private int typeSelectionIndex;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ease_property;
    }

    public EasePropertyBrick() {
        this.propertySelectionIndex = 0;
        this.typeSelectionIndex = 0;
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_ease_property_start_edit);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_ease_property_end_edit);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_ease_property_duration_edit);
    }

    public EasePropertyBrick(int propertyIndex, int typeIndex, float start, float end, float duration) {
        this();
        this.propertySelectionIndex = propertyIndex;
        this.typeSelectionIndex = typeIndex;
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(Float.valueOf(start)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(Float.valueOf(end)));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(Float.valueOf(duration)));
    }

    public EasePropertyBrick(int propertyIndex, int typeIndex, Formula start, Formula end, Formula duration) {
        this();
        this.propertySelectionIndex = propertyIndex;
        this.typeSelectionIndex = typeIndex;
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, start);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, end);
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, duration);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.ease_property_target_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_ease_properties, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.propertySelectionIndex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.EasePropertyBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                EasePropertyBrick.this.propertySelectionIndex = position;
            }
        });
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.ease_property_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.brick_easing_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner2.setSelection(this.typeSelectionIndex);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.EasePropertyBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                EasePropertyBrick.this.typeSelectionIndex = position;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createEasePropertyAction(sprite, sequence, this.propertySelectionIndex, this.typeSelectionIndex, getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE)));
    }
}
