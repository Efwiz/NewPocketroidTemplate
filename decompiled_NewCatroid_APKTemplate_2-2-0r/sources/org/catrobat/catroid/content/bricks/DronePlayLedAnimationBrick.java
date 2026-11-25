package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DronePlayLedAnimationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DronePlayLedAnimationBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String ledAnimationName = "";

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_drone_play_led_animation;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(final Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> arrayAdapterCreateFromResource = ArrayAdapter.createFromResource(context, R.array.brick_drone_play_led_animation_spinner, android.R.layout.simple_spinner_item);
        arrayAdapterCreateFromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_drone_play_led_animation_spinner);
        spinner.setAdapter((SpinnerAdapter) arrayAdapterCreateFromResource);
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.DronePlayLedAnimationBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4913x4e8f0ebc(context, (Integer) obj);
            }
        }));
        if (TextUtils.isEmpty(this.ledAnimationName)) {
            spinner.setSelection(1);
        } else {
            spinner.setSelection(Arrays.asList(context.getResources().getStringArray(R.array.brick_drone_play_led_animation_spinner)).indexOf(this.ledAnimationName));
        }
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-DronePlayLedAnimationBrick, reason: not valid java name */
    /* synthetic */ Unit m4913x4e8f0ebc(Context context, Integer num) {
        this.ledAnimationName = context.getResources().getStringArray(R.array.brick_drone_play_led_animation_spinner)[num.intValue()];
        return Unit.INSTANCE;
    }
}
