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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroPlayToneBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroPlayToneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String tone;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PhiroPlayToneBrick$Tone.class
      classes14.dex
     */
    public enum Tone {
        DO,
        RE,
        MI,
        FA,
        SO,
        LA,
        TI
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_phiro_play_tone;
    }

    public PhiroPlayToneBrick() {
        this.tone = Tone.DO.name();
        addAllowedBrickField(Brick.BrickField.PHIRO_DURATION_IN_SECONDS, R.id.brick_phiro_play_tone_duration_edit_text);
    }

    public PhiroPlayToneBrick(Tone toneEnum, int duration) {
        this(toneEnum, new Formula(Integer.valueOf(duration)));
    }

    public PhiroPlayToneBrick(Tone toneEnum, Formula formula) {
        this();
        this.tone = toneEnum.name();
        setFormulaWithBrickField(Brick.BrickField.PHIRO_DURATION_IN_SECONDS, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 20);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_phiro_select_tone_spinner, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_select_tone_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.PhiroPlayToneBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4949xd490fe0d((Integer) obj);
            }
        }));
        spinner.setSelection(Tone.valueOf(this.tone).ordinal());
        setSecondsLabel(this.view, Brick.BrickField.PHIRO_DURATION_IN_SECONDS);
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-PhiroPlayToneBrick, reason: not valid java name */
    /* synthetic */ Unit m4949xd490fe0d(Integer num) {
        this.tone = Tone.values()[num.intValue()].name();
        return Unit.INSTANCE;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroPlayToneActionAction(sprite, sequence, Tone.valueOf(this.tone), getFormulaWithBrickField(Brick.BrickField.PHIRO_DURATION_IN_SECONDS)));
        sequence.addAction(sprite.getActionFactory().createDelayAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PHIRO_DURATION_IN_SECONDS)));
    }
}
