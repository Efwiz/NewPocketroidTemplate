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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetVariableEasingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetVariableEasingBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;
    private int typeSelectionIndex;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.set_variable_easing_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_variable_easing;
    }

    public SetVariableEasingBrick() {
        this.typeSelectionIndex = 0;
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_easing_time_edit);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_easing_duration_edit);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_easing_start_edit);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, R.id.brick_easing_end_edit);
    }

    public SetVariableEasingBrick(UserVariable variable, int typeIndex, float time, float duration, float start, float end) {
        this();
        this.userVariable = variable;
        this.typeSelectionIndex = typeIndex;
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(Float.valueOf(time)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(Float.valueOf(duration)));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(Float.valueOf(start)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, new Formula(Float.valueOf(end)));
    }

    public SetVariableEasingBrick(int typeIndex, float time, float duration, float start, float end) {
        this();
        this.typeSelectionIndex = typeIndex;
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(Float.valueOf(time)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(Float.valueOf(duration)));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(Float.valueOf(start)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, new Formula(Float.valueOf(end)));
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.set_variable_easing_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_easing_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.SetVariableEasingBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                SetVariableEasingBrick.this.typeSelectionIndex = position;
            }
        });
        spinner.setSelection(this.typeSelectionIndex);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVariableEasingAction(sprite, sequence, this.userVariable, this.typeSelectionIndex, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX)));
    }
}
