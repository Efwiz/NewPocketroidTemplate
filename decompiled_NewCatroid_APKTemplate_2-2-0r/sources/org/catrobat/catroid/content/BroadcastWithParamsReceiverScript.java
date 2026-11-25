package org.catrobat.catroid.content;

import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastWithParamsReceiverBrick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.eventids.BroadcastEventId;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/BroadcastWithParamsReceiverScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastWithParamsReceiverScript extends Script {
    private static final long serialVersionUID = 1;
    private ConcurrentFormulaHashMap formulaMap;
    private UserVariable userVariable;

    public BroadcastWithParamsReceiverScript() {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        this.formulaMap = concurrentFormulaHashMap;
        concurrentFormulaHashMap.putIfAbsent(Brick.BrickField.VALUE_1, new Formula("signal"));
    }

    public BroadcastWithParamsReceiverScript(Formula signalFormula, UserVariable userVariable) {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        this.formulaMap = concurrentFormulaHashMap;
        concurrentFormulaHashMap.putIfAbsent(Brick.BrickField.VALUE_1, signalFormula);
        this.userVariable = userVariable;
    }

    public ConcurrentFormulaHashMap getFormulaMap() {
        return this.formulaMap;
    }

    @Override // org.catrobat.catroid.content.Script
    public Script clone() throws CloneNotSupportedException {
        BroadcastWithParamsReceiverScript broadcastWithParamsReceiverScript = (BroadcastWithParamsReceiverScript) super.clone();
        broadcastWithParamsReceiverScript.formulaMap = this.formulaMap.clone();
        return broadcastWithParamsReceiverScript;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new BroadcastWithParamsReceiverBrick(this);
        }
        return this.scriptBrick;
    }

    public UserVariable getUserVariable() {
        return this.userVariable;
    }

    public void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) throws InterpretationException {
        String strInterpretString = "";
        try {
            Formula formula = this.formulaMap.get(Brick.BrickField.VALUE_1);
            if (formula != null) {
                strInterpretString = formula.interpretString(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BroadcastEventId(strInterpretString);
    }

    @Override // org.catrobat.catroid.content.Script
    public void run(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.commentedOut) {
            return;
        }
        String strInterpretString = "";
        try {
            Formula formula = this.formulaMap.get(Brick.BrickField.VALUE_1);
            if (formula != null) {
                strInterpretString = formula.interpretString(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sequence.addAction(sprite.getActionFactory().createSaveBroadcastParamsAction(sprite, sequence, strInterpretString, getUserVariable()));
        super.run(sprite, sequence);
    }

    @Override // org.catrobat.catroid.content.Script
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        Iterator<Formula> it = this.formulaMap.values().iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
        Iterator<Brick> it2 = this.brickList.iterator();
        while (it2.hasNext()) {
            it2.next().addRequiredResources(requiredResourcesSet);
        }
    }
}
