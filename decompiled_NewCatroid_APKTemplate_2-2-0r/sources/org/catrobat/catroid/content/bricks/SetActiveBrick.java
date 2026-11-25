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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetActiveBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetActiveBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int activeStateSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_active;
    }

    public SetActiveBrick() {
        this.activeStateSelection = 0;
        addAllowedBrickField(Brick.BrickField.OBJECT_NAME, R.id.brick_set_active_object_name);
    }

    public SetActiveBrick(String str, boolean z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_NAME, new Formula(str));
        this.activeStateSelection = !z ? 1 : 0;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_set_active_state_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_set_active_states, R.layout.simple_spinner_item_white_text);
        arrayAdapterCreateFromResource.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.activeStateSelection);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.SetActiveBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                SetActiveBrick.this.activeStateSelection = position;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetActiveAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_NAME), this.activeStateSelection == 0));
    }
}
