package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CameraBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: CameraBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0006H\u0002J\u0018\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/bricks/CameraBrick;", "Lorg/catrobat/catroid/content/bricks/BrickBaseType;", "spinnerSelectionON", "", "(Z)V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "addRequiredResources", "requiredResourcesSet", "Lorg/catrobat/catroid/content/bricks/Brick$ResourcesSet;", "assignVideoSpinnerProperties", "createArrayAdapter", "Landroid/widget/ArrayAdapter;", "", "context", "Landroid/content/Context;", "getView", "Landroid/view/View;", "getViewResource", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CameraBrick extends BrickBaseType {
    private boolean spinnerSelectionON;

    public CameraBrick() {
        this(false, 1, null);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_video;
    }

    public CameraBrick(boolean z) {
        this.spinnerSelectionON = z;
    }

    public /* synthetic */ CameraBrick(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        assignVideoSpinnerProperties();
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    private final void assignVideoSpinnerProperties() {
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_video_spinner);
        Context context = spinner.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        spinner.setAdapter((SpinnerAdapter) createArrayAdapter(context));
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1<Integer, Unit>() { // from class: org.catrobat.catroid.content.bricks.CameraBrick$assignVideoSpinnerProperties$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                this.this$0.spinnerSelectionON = i == 1;
            }
        }));
        spinner.setSelection(this.spinnerSelectionON ? 1 : 0);
    }

    private final ArrayAdapter<String> createArrayAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, new String[]{context.getString(R.string.video_brick_camera_off), context.getString(R.string.video_brick_camera_on)});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(Brick.ResourcesSet requiredResourcesSet) {
        Intrinsics.checkNotNullParameter(requiredResourcesSet, "requiredResourcesSet");
        requiredResourcesSet.add((Integer) 17);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createUpdateCameraPreviewAction(this.spinnerSelectionON));
    }
}
