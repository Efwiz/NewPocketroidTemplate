package org.catrobat.catroid.content.bricks;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReportBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReportBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_report;
    }

    public ReportBrick() {
        addAllowedBrickField(Brick.BrickField.REPORT_BRICK, R.id.brick_report_edit_text);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createReportAction(sprite, new SequenceAction(), sequence.getScript(), getFormulaWithBrickField(Brick.BrickField.REPORT_BRICK)));
    }
}
