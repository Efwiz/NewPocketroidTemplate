package org.catrobat.catroid.content.commands;

import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.FormulaBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/commands/ChangeFormulaCommand.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeFormulaCommand implements Command {
    private FormulaBrick formulaBrick;
    private ConcurrentFormulaHashMap newFormulaMap;
    private ConcurrentFormulaHashMap previousFormulaMap = new ConcurrentFormulaHashMap();

    public ChangeFormulaCommand(FormulaBrick formulaBrick, ConcurrentFormulaHashMap newFormulaMap) {
        this.formulaBrick = formulaBrick;
        this.newFormulaMap = newFormulaMap;
        for (Brick.FormulaField formulaField : newFormulaMap.keySet()) {
            this.previousFormulaMap.putIfAbsent(formulaField, formulaBrick.getFormulaWithBrickField(formulaField).clone());
        }
    }

    @Override // org.catrobat.catroid.content.commands.Command
    public void execute() {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = this.newFormulaMap;
        if (concurrentFormulaHashMap != null) {
            for (Brick.FormulaField formulaField : concurrentFormulaHashMap.keySet()) {
                this.formulaBrick.setFormulaWithBrickField(formulaField, this.newFormulaMap.get(formulaField));
            }
        }
    }

    @Override // org.catrobat.catroid.content.commands.Command
    public void undo() {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = this.previousFormulaMap;
        if (concurrentFormulaHashMap != null) {
            for (Brick.FormulaField formulaField : concurrentFormulaHashMap.keySet()) {
                this.formulaBrick.setFormulaWithBrickField(formulaField, this.previousFormulaMap.get(formulaField));
            }
        }
    }
}
