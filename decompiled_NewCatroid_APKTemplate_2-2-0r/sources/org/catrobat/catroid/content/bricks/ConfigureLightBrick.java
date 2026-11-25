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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ConfigureLightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ConfigureLightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int configTypeSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_http_configure_light;
    }

    public ConfigureLightBrick() {
        this.configTypeSelection = 0;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_http_configure_light_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_http_configure_light_value_field);
    }

    public ConfigureLightBrick(String lightId, int configTypeSelection, String value) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(lightId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(value));
        this.configTypeSelection = configTypeSelection;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_http_configure_light_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_http_configure_light_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.ConfigureLightBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                ConfigureLightBrick.this.configTypeSelection = position;
            }
        });
        spinner.setSelection(this.configTypeSelection);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createConfigureLightAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.configTypeSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
