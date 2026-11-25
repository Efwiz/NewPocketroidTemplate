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
import org.catrobat.catroid.content.actions.ThreedCreateFixedConstraintAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ThreedCreateFixedConstraintBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreedCreateFixedConstraintBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int modeSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_threed_create_fixed_constraint;
    }

    public ThreedCreateFixedConstraintBrick() {
        this.modeSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_fixed_joint_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_fixed_joint_a);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_fixed_joint_b);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_fixed_joint_ax);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_fixed_joint_ay);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_fixed_joint_az);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_fixed_joint_bx);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_fixed_joint_by);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_fixed_joint_bz);
    }

    public ThreedCreateFixedConstraintBrick(String id2, String a, String b) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(id2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(a));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(b));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_fixed_joint_spinner);
        final LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.brick_fixed_joint_manual_layout);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_threed_joint_modes, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.ThreedCreateFixedConstraintBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                ThreedCreateFixedConstraintBrick.this.modeSelection = position;
                linearLayout.setVisibility(ThreedCreateFixedConstraintBrick.this.modeSelection == 1 ? 0 : 8);
            }
        });
        spinner.setSelection(this.modeSelection);
        linearLayout.setVisibility(this.modeSelection == 1 ? 0 : 8);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ThreedCreateFixedConstraintAction threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction = sprite.getActionFactory().createThreedCreateFixedConstraintAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3));
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.mode = this.modeSelection;
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.ax = getFormulaWithBrickField(Brick.BrickField.X);
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.ay = getFormulaWithBrickField(Brick.BrickField.Y);
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.az = getFormulaWithBrickField(Brick.BrickField.Z);
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.bx = getFormulaWithBrickField(Brick.BrickField.VALUE_4);
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.by = getFormulaWithBrickField(Brick.BrickField.VALUE_5);
        threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction.bz = getFormulaWithBrickField(Brick.BrickField.VALUE_6);
        sequence.addAction(threedCreateFixedConstraintActionCreateThreedCreateFixedConstraintAction);
    }
}
