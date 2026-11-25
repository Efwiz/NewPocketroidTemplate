package org.catrobat.catroid.content.bricks;

import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenNotificationClickedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenNotificationClickedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenNotificationClickedBrick extends FormulaBrick implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private WhenNotificationClickedScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInDragAndDropTargetList() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInScript() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_notification_clicked;
    }

    public WhenNotificationClickedBrick() {
        this(new WhenNotificationClickedScript());
    }

    public WhenNotificationClickedBrick(WhenNotificationClickedScript script) {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_when_notification_clicked_id);
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
        this.formulaMap = script.getFormulaMap();
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenNotificationClickedBrick whenNotificationClickedBrick = (WhenNotificationClickedBrick) super.clone();
        WhenNotificationClickedScript whenNotificationClickedScript = (WhenNotificationClickedScript) this.script.clone();
        whenNotificationClickedBrick.script = whenNotificationClickedScript;
        whenNotificationClickedScript.setScriptBrick(whenNotificationClickedBrick);
        whenNotificationClickedBrick.formulaMap = whenNotificationClickedBrick.script.getFormulaMap();
        return whenNotificationClickedBrick;
    }

    public Formula getNotificationIdFormula() {
        return getFormulaWithBrickField(Brick.BrickField.VALUE_1);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        super.addToFlatList(bricks);
        Iterator<Brick> it = getScript().getBrickList().iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return getScript().getBrickList();
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
