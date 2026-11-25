package org.catrobat.catroid.content.bricks;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetCameraFocusPointBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetCameraFocusPointBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/content/bricks/SetCameraFocusPointBrick;", "Lorg/catrobat/catroid/content/bricks/FormulaBrick;", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "getDefaultBrickField", "Lorg/catrobat/catroid/content/bricks/Brick$FormulaField;", "getViewResource", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetCameraFocusPointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_camera_focus;
    }

    public SetCameraFocusPointBrick() {
        addAllowedBrickField(Brick.BrickField.HORIZONTAL_FLEXIBILITY, R.id.brick_set_camera_focus_horizontal_input);
        addAllowedBrickField(Brick.BrickField.VERTICAL_FLEXIBILITY, R.id.brick_set_camera_focus_vertical_input);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.FormulaField getDefaultBrickField() {
        return Brick.BrickField.HORIZONTAL_FLEXIBILITY;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createSetCameraFocusPointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.HORIZONTAL_FLEXIBILITY), getFormulaWithBrickField(Brick.BrickField.VERTICAL_FLEXIBILITY)));
    }
}
