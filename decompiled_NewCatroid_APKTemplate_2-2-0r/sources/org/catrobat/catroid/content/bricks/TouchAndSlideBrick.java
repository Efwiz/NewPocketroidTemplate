package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TouchAndSlideBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: TouchAndSlideBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 '2\u00020\u0001:\u0001'B/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB9\b\u0016\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\u0010B\u0005¢\u0006\u0002\u0010\u0011J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0003H\u0016J\n\u0010!\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\"\u001a\u00020\u0003H\u0016J\n\u0010#\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010$\u001a\u00020\u0003H\u0016J\u0012\u0010%\u001a\u00020\u00152\b\u0010&\u001a\u0004\u0018\u00010\u001dH\u0016R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/catrobat/catroid/content/bricks/TouchAndSlideBrick;", "Lorg/catrobat/catroid/content/bricks/VisualPlacementBrick;", "xPositionValue", "", "yPositionValue", "xChangeValue", "yChangeValue", "durationInSecondsValue", "", "(IIIID)V", "xPosition", "Lorg/catrobat/catroid/formulaeditor/Formula;", "yPosition", "xChange", "yChange", "durationInSeconds", "(Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;)V", "()V", "startCoordinates", "", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getDefaultBrickField", "Lorg/catrobat/catroid/content/bricks/Brick$BrickField;", "getView", "Landroid/view/View;", "context", "Landroid/content/Context;", "getViewResource", "getXBrickField", "getXEditTextId", "getYBrickField", "getYEditTextId", "showFormulaEditorToEditFormula", Promotion.ACTION_VIEW, "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TouchAndSlideBrick extends VisualPlacementBrick {
    private static final long serialVersionUID = 1;
    private boolean startCoordinates;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_touch_slide;
    }

    public TouchAndSlideBrick() {
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_touch_slide_edit_from_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_touch_slide_edit_from_y);
        addAllowedBrickField(Brick.BrickField.X_POSITION_CHANGE, R.id.brick_touch_slide_edit_to_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION_CHANGE, R.id.brick_touch_slide_edit_to_y);
        addAllowedBrickField(Brick.BrickField.DURATION_IN_SECONDS, R.id.brick_tap_for_edit_duration);
        this.startCoordinates = true;
    }

    public TouchAndSlideBrick(int i, int i2, int i3, int i4, double d) {
        this(new Formula(Integer.valueOf(i)), new Formula(Integer.valueOf(i2)), new Formula(Integer.valueOf(i3)), new Formula(Integer.valueOf(i4)), new Formula(Double.valueOf(d)));
    }

    public TouchAndSlideBrick(Formula formula, Formula formula2, Formula formula3, Formula formula4, Formula formula5) {
        this();
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, formula);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, formula2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION_CHANGE, formula3);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION_CHANGE, formula4);
        setFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS, formula5);
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
        sequence.addAction(sprite.getActionFactory().createTouchAndSlideAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.X_POSITION_CHANGE), getFormulaWithBrickField(Brick.BrickField.Y_POSITION_CHANGE), getFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS)));
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick, org.catrobat.catroid.content.bricks.FormulaBrick
    public void showFormulaEditorToEditFormula(View view) {
        this.startCoordinates = (view != null && view.getId() == R.id.brick_touch_slide_edit_from_x) || (view != null && view.getId() == R.id.brick_touch_slide_edit_from_y);
        super.showFormulaEditorToEditFormula(view);
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getXBrickField() {
        return this.startCoordinates ? Brick.BrickField.X_POSITION : Brick.BrickField.X_POSITION_CHANGE;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public Brick.BrickField getYBrickField() {
        return this.startCoordinates ? Brick.BrickField.Y_POSITION : Brick.BrickField.Y_POSITION_CHANGE;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getXEditTextId() {
        return this.startCoordinates ? R.id.brick_touch_slide_edit_from_x : R.id.brick_touch_slide_edit_to_x;
    }

    @Override // org.catrobat.catroid.content.bricks.VisualPlacementBrick
    public int getYEditTextId() {
        return this.startCoordinates ? R.id.brick_touch_slide_edit_from_y : R.id.brick_touch_slide_edit_to_y;
    }
}
