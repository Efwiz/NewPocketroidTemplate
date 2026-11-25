package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StopScriptBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopScriptBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private int spinnerSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_stop_script;
    }

    public StopScriptBrick() {
    }

    public StopScriptBrick(int spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_stop_script_spinner);
        spinner.setAdapter((SpinnerAdapter) createArrayAdapter(context));
        spinner.setSelection(this.spinnerSelection);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.StopScriptBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4989xb70f5314((Integer) obj);
            }
        }));
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-StopScriptBrick, reason: not valid java name */
    /* synthetic */ Unit m4989xb70f5314(Integer num) {
        this.spinnerSelection = num.intValue();
        return Unit.INSTANCE;
    }

    private ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new String[]{context.getString(R.string.brick_stop_this_script), context.getString(R.string.brick_stop_all_scripts), context.getString(R.string.brick_stop_other_scripts)});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStopScriptAction(this.spinnerSelection, sequence.getScript(), sprite));
    }
}
