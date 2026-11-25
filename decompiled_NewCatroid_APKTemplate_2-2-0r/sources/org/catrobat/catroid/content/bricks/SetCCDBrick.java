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
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetCCDBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetCCDBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int ccdSelection;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_ccd;
    }

    public SetCCDBrick() {
        this.ccdSelection = 0;
        addAllowedBrickField(Brick.BrickField.THREED_OBJECT_ID, R.id.brick_set_ccd_object_id);
    }

    public SetCCDBrick(String str, boolean z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.THREED_OBJECT_ID, new Formula(str));
        this.ccdSelection = !z ? 1 : 0;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_set_ccd_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, new String[]{"Вкл.", "Выкл"});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.SetCCDBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4961lambda$getView$0$orgcatrobatcatroidcontentbricksSetCCDBrick((Integer) obj);
            }
        }));
        spinner.setSelection(this.ccdSelection);
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-SetCCDBrick, reason: not valid java name */
    /* synthetic */ Unit m4961lambda$getView$0$orgcatrobatcatroidcontentbricksSetCCDBrick(Integer num) {
        this.ccdSelection = num.intValue();
        return null;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Formula formula;
        Formula formulaWithBrickField = getFormulaWithBrickField(Brick.BrickField.THREED_OBJECT_ID);
        if (this.ccdSelection == 0) {
            formula = new Formula(Double.valueOf(1.0d));
        } else {
            formula = new Formula(Double.valueOf(0.0d));
        }
        sequence.addAction(sprite.getActionFactory().createSetCCDAction(sprite, sequence, formulaWithBrickField, formula));
    }
}
