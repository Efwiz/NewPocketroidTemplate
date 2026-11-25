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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/FlashBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FlashBrick extends BrickBaseType {
    private static final int FLASH_OFF = 0;
    private static final int FLASH_ON = 1;
    private int spinnerSelectionID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_flash;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_flash_spinner);
        spinner.setAdapter((SpinnerAdapter) createArrayAdapter(context));
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.FlashBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4919lambda$getView$0$orgcatrobatcatroidcontentbricksFlashBrick((Integer) obj);
            }
        }));
        spinner.setSelection(this.spinnerSelectionID);
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-FlashBrick, reason: not valid java name */
    /* synthetic */ Unit m4919lambda$getView$0$orgcatrobatcatroidcontentbricksFlashBrick(Integer num) {
        this.spinnerSelectionID = num.intValue();
        return Unit.INSTANCE;
    }

    private ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new String[]{context.getString(R.string.brick_flash_off), context.getString(R.string.brick_flash_on)});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 8);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFlashAction(this.spinnerSelectionID == 1));
    }
}
