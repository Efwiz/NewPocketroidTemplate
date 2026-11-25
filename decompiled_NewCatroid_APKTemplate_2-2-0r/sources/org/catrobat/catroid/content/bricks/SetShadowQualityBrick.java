package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetShadowQualityBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetShadowQualityBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_shadow_quality;
    }

    public SetShadowQualityBrick() {
        addAllowedBrickField(Brick.BrickField.SHADOW_SIZE, R.id.brick_shadow_size_edit);
        addAllowedBrickField(Brick.BrickField.SHADOW_RESOLUTION, R.id.brick_shadow_resolution_edit);
    }

    public SetShadowQualityBrick(Formula size, Formula resolution) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SHADOW_SIZE, size);
        setFormulaWithBrickField(Brick.BrickField.SHADOW_RESOLUTION, resolution);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        return super.getView(context);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetShadowQualityAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.SHADOW_SIZE), getFormulaWithBrickField(Brick.BrickField.SHADOW_RESOLUTION)));
    }
}
