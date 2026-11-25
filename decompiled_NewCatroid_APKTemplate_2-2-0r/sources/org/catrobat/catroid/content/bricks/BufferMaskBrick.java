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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BufferMaskBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BufferMaskBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int modeSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_buffer_mask;
    }

    public BufferMaskBrick() {
        this.modeSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_buffer_mask_name_field);
    }

    public BufferMaskBrick(String bufferName, int mode) {
        this(new Formula(bufferName), mode);
    }

    public BufferMaskBrick(Formula bufferName, int mode) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, bufferName);
        this.modeSelection = mode;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_buffer_mask_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_buffer_mask_modes, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.BufferMaskBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                BufferMaskBrick.this.modeSelection = position;
            }
        });
        spinner.setSelection(this.modeSelection);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBufferMaskAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.modeSelection));
    }
}
