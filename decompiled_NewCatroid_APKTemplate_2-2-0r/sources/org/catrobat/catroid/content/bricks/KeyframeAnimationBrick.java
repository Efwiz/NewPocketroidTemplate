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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/KeyframeAnimationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class KeyframeAnimationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int actionSelection = 0;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_keyframe_animation;
    }

    public KeyframeAnimationBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_keyframe_anim_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_keyframe_anim_time_value);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_keyframe_anim_spinner);
        final LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.layout_keyframe_time_input);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.keyframe_actions, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.KeyframeAnimationBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                KeyframeAnimationBrick.this.actionSelection = position;
                linearLayout.setVisibility(position == 2 ? 0 : 8);
            }
        });
        spinner.setSelection(this.actionSelection);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createKeyframeAnimationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), this.actionSelection, getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
