package org.catrobat.catroid.content;

import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenClonedWithNameBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenClonedWithNameScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenClonedWithNameScript extends Script {
    private static final long serialVersionUID = 1;
    private ConcurrentFormulaHashMap formulaMap;

    public WhenClonedWithNameScript() {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        this.formulaMap = concurrentFormulaHashMap;
        concurrentFormulaHashMap.putIfAbsent(Brick.BrickField.VALUE_1, new Formula("clone_name"));
    }

    public WhenClonedWithNameScript(Formula nameFormula) {
        this();
        this.formulaMap.replace(Brick.BrickField.VALUE_1, nameFormula);
    }

    public ConcurrentFormulaHashMap getFormulaMap() {
        return this.formulaMap;
    }

    @Override // org.catrobat.catroid.content.Script
    public Script clone() throws CloneNotSupportedException {
        WhenClonedWithNameScript whenClonedWithNameScript = (WhenClonedWithNameScript) super.clone();
        whenClonedWithNameScript.formulaMap = this.formulaMap.clone();
        return whenClonedWithNameScript;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenClonedWithNameBrick(this);
        }
        return this.scriptBrick;
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

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new EventId(4);
    }

    @Override // org.catrobat.catroid.content.Script
    public void run(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.commentedOut) {
            return;
        }
        Formula nameFormula = ((WhenClonedWithNameBrick) getScriptBrick()).getNameFormula();
        if (nameFormula != null) {
            try {
                if (!sprite.getName().equals(nameFormula.interpretString(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, null)))) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        super.run(sprite, sequence);
    }
}
