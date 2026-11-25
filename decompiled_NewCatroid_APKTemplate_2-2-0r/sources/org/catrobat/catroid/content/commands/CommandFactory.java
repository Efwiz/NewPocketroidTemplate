package org.catrobat.catroid.content.commands;

import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/commands/CommandFactory.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class CommandFactory {
    private CommandFactory() {
    }

    public static ChangeFormulaCommand makeChangeFormulaCommand(FormulaBrick formulaBrick, Brick.BrickField brickField, Formula newFormula) {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        concurrentFormulaHashMap.putIfAbsent(brickField, newFormula);
        return makeChangeFormulaCommand(formulaBrick, concurrentFormulaHashMap);
    }

    public static ChangeFormulaCommand makeChangeFormulaCommand(FormulaBrick formulaBrick, ConcurrentFormulaHashMap newFormulaMap) {
        return new ChangeFormulaCommand(formulaBrick, newFormulaMap);
    }
}
