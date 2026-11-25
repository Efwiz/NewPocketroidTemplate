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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HttpConfigBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HttpConfigBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int configTypeSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_http_config;
    }

    public HttpConfigBrick() {
        this.configTypeSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_http_config_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_http_config_key_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_http_config_value_field);
    }

    public HttpConfigBrick(String requestId, int configTypeSelection, String key, String value) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(requestId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(key));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(value));
        this.configTypeSelection = configTypeSelection;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_http_config_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_http_config_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.HttpConfigBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                HttpConfigBrick.this.configTypeSelection = position;
                HttpConfigBrick.this.updateKeyVisibility();
            }
        });
        spinner.setSelection(this.configTypeSelection);
        updateKeyVisibility();
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateKeyVisibility() {
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.brick_http_config_key_layout);
        if (linearLayout != null) {
            int i = this.configTypeSelection;
            if (i == 0 || i == 1) {
                linearLayout.setVisibility(0);
            } else {
                linearLayout.setVisibility(8);
            }
        }
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createHttpConfigAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.configTypeSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
