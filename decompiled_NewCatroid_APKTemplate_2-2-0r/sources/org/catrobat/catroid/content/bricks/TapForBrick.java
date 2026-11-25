package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TapForBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: TapForBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B%\b\u0016\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\fB\u0005¢\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0003H\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u001c\u001a\u00020\u0003H\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u001e\u001a\u00020\u0003H\u0016¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/content/bricks/TapForBrick;", "Lorg/catrobat/catroid/content/bricks/VisualPlacementBrick;", "xPositionValue", "", "yPositionValue", "durationInSecondsValue", "", "(IID)V", "xPosition", "Lorg/catrobat/catroid/formulaeditor/Formula;", "yPosition", "durationInSeconds", "(Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;)V", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getDefaultBrickField", "Lorg/catrobat/catroid/content/bricks/Brick$BrickField;", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "getXBrickField", "getXEditTextId", "getYBrickField", "getYEditTextId", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TapForBrick extends VisualPlacementBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_tap_for;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return R.id.brick_tap_for_edit_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return R.id.brick_tap_for_edit_y;
    }

    public TapForBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_tap_for_edit_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_tap_for_edit_y);
        addAllowedBrickField(Brick.BrickField.DURATION_IN_SECONDS, R.id.brick_tap_for_edit_duration);
    }

    public TapForBrick(int i, int i2, double d) {
        this(new Formula(Integer.valueOf(i)), new Formula(Integer.valueOf(i2)), new Formula(Double.valueOf(d)));
    }

    public TapForBrick(Formula formula, Formula formula2, Formula formula3) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, formula);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, formula2);
        setFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS, formula3);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.getView(context);
        setSecondsLabel(this.view, Brick.BrickField.DURATION_IN_SECONDS);
        View view = this.view;
        Intrinsics.checkNotNullExpressionValue(view, "view");
        return view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createTapForAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS)));
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getXBrickField() {
        return Brick.BrickField.X_POSITION;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getYBrickField() {
        return Brick.BrickField.Y_POSITION;
    }
}
