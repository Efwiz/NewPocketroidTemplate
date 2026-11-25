package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.BroadcastWithParamsReceiverScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BroadcastWithParamsReceiverBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastWithParamsReceiverBrick extends UserVariableBrickWithFormula implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private BroadcastWithParamsReceiverScript script;

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

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_broadcast_with_params_spinner_variable;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_broadcast_with_params_receive;
    }

    public BroadcastWithParamsReceiverBrick() {
        this(new BroadcastWithParamsReceiverScript());
    }

    public BroadcastWithParamsReceiverBrick(BroadcastWithParamsReceiverScript script) {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_broadcast_with_params_receive_edit_signal);
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
        this.formulaMap = script.getFormulaMap();
    }

    public BroadcastWithParamsReceiverBrick(String signal, UserVariable variable) {
        this(new BroadcastWithParamsReceiverScript(new Formula(signal), variable));
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        this.userVariable = this.script.getUserVariable();
        return super.getView(context);
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, UserVariable item) {
        super.onItemSelected(spinnerId, item);
        this.script.setUserVariable(item);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        BroadcastWithParamsReceiverBrick broadcastWithParamsReceiverBrick = (BroadcastWithParamsReceiverBrick) super.clone();
        BroadcastWithParamsReceiverScript broadcastWithParamsReceiverScript = (BroadcastWithParamsReceiverScript) this.script.clone();
        broadcastWithParamsReceiverBrick.script = broadcastWithParamsReceiverScript;
        broadcastWithParamsReceiverScript.setScriptBrick(broadcastWithParamsReceiverBrick);
        broadcastWithParamsReceiverBrick.formulaMap = broadcastWithParamsReceiverBrick.script.getFormulaMap();
        return broadcastWithParamsReceiverBrick;
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
