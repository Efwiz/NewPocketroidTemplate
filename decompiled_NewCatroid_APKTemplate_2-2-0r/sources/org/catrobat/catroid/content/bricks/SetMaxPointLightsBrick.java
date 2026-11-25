package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetMaxPointLightsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetMaxPointLightsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_max_point_lights;
    }

    public SetMaxPointLightsBrick() {
        addAllowedBrickField(Brick.BrickField.MAX_LIGHTS, R.id.brick_max_lights_val);
    }

    public SetMaxPointLightsBrick(int limit) {
        this(new Formula(Integer.valueOf(limit)));
    }

    public SetMaxPointLightsBrick(Formula limit) {
        this();
        setFormulaWithBrickField(Brick.BrickField.MAX_LIGHTS, limit);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetMaxPointLightsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.MAX_LIGHTS)));
    }
}
