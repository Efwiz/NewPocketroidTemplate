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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/NativeViewBindSpriteBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NativeViewBindSpriteBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int alignMode;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_native_view_bind;
    }

    public NativeViewBindSpriteBrick() {
        this.alignMode = 1;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_sprite_field);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_x_field);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_y_field);
    }

    public NativeViewBindSpriteBrick(String viewId, String spriteName, int alignMode, int x, int y) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(spriteName));
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, new Formula(Integer.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, new Formula(Integer.valueOf(y)));
        this.alignMode = alignMode;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_align_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.align_anchor_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.alignMode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.NativeViewBindSpriteBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                NativeViewBindSpriteBrick.this.alignMode = pos;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createNativeViewBindSpriteAction(sprite, sequence, this.alignMode, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}
