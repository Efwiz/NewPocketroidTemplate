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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateScrollViewBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateScrollViewBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int overScrollMode;
    private int scrollMode;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_scroll_view;
    }

    public CreateScrollViewBrick() {
        this.scrollMode = 1;
        this.overScrollMode = 1;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_bg_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_bars_field);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_padding_field);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_x_field);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_y_field);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_w_field);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_h_field);
    }

    public CreateScrollViewBrick(String viewId, int mode, String bg, boolean showBars, int padding, int overScroll, int x, int y, int w, int h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(bg));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(showBars));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Integer.valueOf(padding)));
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, new Formula(Integer.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, new Formula(Integer.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.WIDTH, new Formula(Integer.valueOf(w)));
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, new Formula(Integer.valueOf(h)));
        this.scrollMode = mode;
        this.overScrollMode = overScroll;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_mode_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.scroll_modes_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.scrollMode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.CreateScrollViewBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                CreateScrollViewBrick.this.scrollMode = pos;
            }
        });
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.brick_overscroll_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.overscroll_modes_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner2.setSelection(this.overScrollMode);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.CreateScrollViewBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                CreateScrollViewBrick.this.overScrollMode = pos;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCreateScrollViewAction(sprite, sequence, this.scrollMode, this.overScrollMode, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
