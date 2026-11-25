package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPhysicsStateBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPhysicsStateBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int shapeSelection;
    private int stateSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_physics_state;
    }

    public SetPhysicsStateBrick() {
        this.stateSelection = 0;
        this.shapeSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_physics_state_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_physics_state_mass_value);
    }

    public SetPhysicsStateBrick(String objectId, int state, int shape, double mass) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(objectId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(mass)));
        this.stateSelection = state;
        this.shapeSelection = shape;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_set_physics_state_spinner);
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.brick_set_physics_shape_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_physics_states, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.brick_physics_shapes, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.SetPhysicsStateBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                SetPhysicsStateBrick.this.stateSelection = position;
                SetPhysicsStateBrick.this.updateVisibility();
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.SetPhysicsStateBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                SetPhysicsStateBrick.this.shapeSelection = position;
            }
        });
        spinner.setSelection(this.stateSelection);
        spinner2.setSelection(this.shapeSelection);
        updateVisibility();
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVisibility() {
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.brick_set_physics_shape_layout);
        LinearLayout linearLayout2 = (LinearLayout) this.view.findViewById(R.id.brick_set_physics_state_mass_layout);
        int i = this.stateSelection;
        linearLayout.setVisibility((i == 1 || i == 2) ? 0 : 8);
        linearLayout2.setVisibility(this.stateSelection == 2 ? 0 : 8);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPhysicsStateAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.stateSelection, this.shapeSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
