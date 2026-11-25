package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenMouseButtonClickedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenMouseButtonClickedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenMouseButtonClickedBrick extends ScriptBrickBaseType {
    private static final long serialVersionUID = 1;
    private WhenMouseButtonClickedScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_mouse_button_clicked;
    }

    public WhenMouseButtonClickedBrick() {
        this(new WhenMouseButtonClickedScript(0));
    }

    public WhenMouseButtonClickedBrick(WhenMouseButtonClickedScript script) {
        this.script = script;
        script.setScriptBrick(this);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.mouse_button_spinner);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.mouse_buttons, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        int buttonCode = this.script.getButtonCode();
        if (buttonCode == 1) {
            spinner.setSelection(1);
        } else if (buttonCode == 2) {
            spinner.setSelection(2);
        } else {
            spinner.setSelection(0);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.content.bricks.WhenMouseButtonClickedBrick.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                int i = 1;
                if (position != 1) {
                    i = 2;
                    if (position != 2) {
                        i = 0;
                    }
                }
                WhenMouseButtonClickedBrick.this.script.setButtonCode(i);
            }
        });
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenMouseButtonClickedBrick whenMouseButtonClickedBrick = (WhenMouseButtonClickedBrick) super.clone();
        WhenMouseButtonClickedScript whenMouseButtonClickedScript = (WhenMouseButtonClickedScript) this.script.clone();
        whenMouseButtonClickedBrick.script = whenMouseButtonClickedScript;
        whenMouseButtonClickedScript.setScriptBrick(whenMouseButtonClickedBrick);
        return whenMouseButtonClickedBrick;
    }
}
