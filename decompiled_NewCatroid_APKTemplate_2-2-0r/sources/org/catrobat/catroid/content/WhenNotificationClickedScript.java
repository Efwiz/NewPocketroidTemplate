package org.catrobat.catroid.content;

import java.util.Iterator;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenNotificationClickedBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenNotificationClickedScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenNotificationClickedScript extends Script {
    private static final long serialVersionUID = 1;
    private ConcurrentFormulaHashMap formulaMap;

    public WhenNotificationClickedScript() {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        this.formulaMap = concurrentFormulaHashMap;
        concurrentFormulaHashMap.putIfAbsent(Brick.BrickField.VALUE_1, new Formula(""));
    }

    public WhenNotificationClickedScript(Formula notificationId) {
        this();
        this.formulaMap.replace(Brick.BrickField.VALUE_1, notificationId);
    }

    public ConcurrentFormulaHashMap getFormulaMap() {
        return this.formulaMap;
    }

    @Override // org.catrobat.catroid.content.Script
    public Script clone() throws CloneNotSupportedException {
        WhenNotificationClickedScript whenNotificationClickedScript = (WhenNotificationClickedScript) super.clone();
        whenNotificationClickedScript.formulaMap = this.formulaMap.clone();
        return whenNotificationClickedScript;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenNotificationClickedBrick(this);
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
        return new EventId(15);
    }
}
