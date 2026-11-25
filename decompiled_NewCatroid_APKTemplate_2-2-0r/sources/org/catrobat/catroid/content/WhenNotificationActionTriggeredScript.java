package org.catrobat.catroid.content;

import java.util.Iterator;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenNotificationActionTriggeredBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenNotificationActionTriggeredScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenNotificationActionTriggeredScript extends Script {
    private static final long serialVersionUID = 1;
    private ConcurrentFormulaHashMap formulaMap;

    public WhenNotificationActionTriggeredScript() {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        this.formulaMap = concurrentFormulaHashMap;
        concurrentFormulaHashMap.putIfAbsent(Brick.BrickField.VALUE_1, new Formula(""));
    }

    public WhenNotificationActionTriggeredScript(Formula actionId) {
        this();
        this.formulaMap.replace(Brick.BrickField.VALUE_1, actionId);
    }

    public ConcurrentFormulaHashMap getFormulaMap() {
        return this.formulaMap;
    }

    @Override // org.catrobat.catroid.content.Script
    public Script clone() throws CloneNotSupportedException {
        WhenNotificationActionTriggeredScript whenNotificationActionTriggeredScript = (WhenNotificationActionTriggeredScript) super.clone();
        whenNotificationActionTriggeredScript.formulaMap = this.formulaMap.clone();
        return whenNotificationActionTriggeredScript;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenNotificationActionTriggeredBrick(this);
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
        return new EventId(0);
    }
}
