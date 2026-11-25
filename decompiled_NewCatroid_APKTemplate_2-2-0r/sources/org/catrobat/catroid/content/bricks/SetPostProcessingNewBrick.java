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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPostProcessingNewBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPostProcessingNewBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int effectIndex = 0;
    private int paramIndex = 0;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_post_processing_new;
    }

    public SetPostProcessingNewBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_pp_value_edit);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(final Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_pp_effect_spinner);
        final Spinner spinner2 = (Spinner) this.view.findViewById(R.id.brick_pp_param_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.pp_new_effect_types, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setSelection(this.effectIndex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.SetPostProcessingNewBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id2) {
                boolean z = SetPostProcessingNewBrick.this.effectIndex != position;
                SetPostProcessingNewBrick.this.effectIndex = position;
                SetPostProcessingNewBrick.this.updateParamSpinner(context, spinner2, position);
                if (z) {
                    SetPostProcessingNewBrick.this.paramIndex = 0;
                    spinner2.setSelection(0);
                } else {
                    spinner2.setSelection(SetPostProcessingNewBrick.this.paramIndex);
                }
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.SetPostProcessingNewBrick.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id2) {
                SetPostProcessingNewBrick.this.paramIndex = position;
            }
        });
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateParamSpinner(Context context, Spinner paramSpinner, int effectIdx) {
        int i = R.array.pp_params_global;
        switch (effectIdx) {
            case 1:
                i = R.array.pp_params_bloom;
                break;
            case 2:
                i = R.array.pp_params_vignette;
                break;
            case 3:
                i = R.array.pp_params_levels;
                break;
            case 4:
                i = R.array.pp_params_grain;
                break;
            case 5:
                i = R.array.pp_params_fxaa;
                break;
            case 6:
                i = R.array.pp_params_chromatic;
                break;
            case 7:
                i = R.array.pp_params_radial;
                break;
            case 8:
                i = R.array.pp_params_oldtv;
                break;
            case 9:
                i = R.array.pp_params_crt;
                break;
            case 10:
                i = R.array.pp_params_fisheye;
                break;
            case 11:
                i = R.array.pp_params_water;
                break;
            case 12:
                i = R.array.pp_params_motionblur;
                break;
            case 13:
                i = R.array.pp_params_lensflare;
                break;
            case 14:
                i = R.array.pp_params_gaussian;
                break;
            case 15:
                i = R.array.pp_params_zoom;
                break;
            case 16:
                i = R.array.pp_params_aces;
                break;
            case 17:
                i = R.array.pp_params_eyeadapt;
                break;
            case 18:
                i = R.array.pp_params_ssr;
                break;
            case 19:
                i = R.array.pp_params_ssao;
                break;
            case 20:
                i = R.array.pp_params_heightfog;
                break;
            case 21:
                i = R.array.pp_params_dof;
                break;
            case 22:
                i = R.array.pp_params_godrays;
                break;
            case 23:
                i = R.array.pp_params_volfog;
                break;
            case 24:
                i = R.array.pp_params_fsr;
                break;
            case 25:
                i = R.array.pp_params_ssgi;
                break;
        }
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, i, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paramSpinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPostProcessingNewAction(sprite, sequence, this.effectIndex, this.paramIndex, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
