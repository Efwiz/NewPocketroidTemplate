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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateTextLabelBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateTextLabelBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int alignMode;
    private int formatMode;
    private int scrollMode;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_text_label;
    }

    public CreateTextLabelBrick() {
        this.formatMode = 0;
        this.alignMode = 0;
        this.scrollMode = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_text_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_color_field);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_size_field);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_bg_field);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_font_field);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_radius_field);
        addAllowedBrickField(Brick.BrickField.VALUE_8, R.id.brick_spacing_field);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_x_field);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_y_field);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_w_field);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_h_field);
    }

    public CreateTextLabelBrick(String viewId, String text, String color, double size, String bg, String font, int radius, double spacing, int format, int align, int scroll, int x, int y, int w, int h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(text));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(color));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(size)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(bg));
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, new Formula(font));
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, new Formula(Integer.valueOf(radius)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_8, new Formula(Double.valueOf(spacing)));
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, new Formula(Integer.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, new Formula(Integer.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.WIDTH, new Formula(Integer.valueOf(w)));
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, new Formula(Integer.valueOf(h)));
        this.formatMode = format;
        this.alignMode = align;
        this.scrollMode = scroll;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_format_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.text_formats_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.formatMode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.CreateTextLabelBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> p) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                CreateTextLabelBrick.this.formatMode = pos;
            }
        });
        Spinner spinner2 = (Spinner) this.view.findViewById(R.id.brick_align_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource2 = ArrayAdapter.createFromResource(context, R.array.align_modes_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource2);
        spinner2.setSelection(this.alignMode);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.CreateTextLabelBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> p) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                CreateTextLabelBrick.this.alignMode = pos;
            }
        });
        Spinner spinner3 = (Spinner) this.view.findViewById(R.id.brick_scroll_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource3 = ArrayAdapter.createFromResource(context, R.array.scroll_modes_array, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource3);
        spinner3.setSelection(this.scrollMode);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.CreateTextLabelBrick.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> p) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                CreateTextLabelBrick.this.scrollMode = pos;
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCreateTextLabelAction(sprite, sequence, this.formatMode, this.alignMode, this.scrollMode, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7), getFormulaWithBrickField(Brick.BrickField.VALUE_8), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}
