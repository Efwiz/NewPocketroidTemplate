package org.catrobat.catroid.content.bricks;

import java.util.concurrent.ConcurrentHashMap;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ConcurrentFormulaHashMap.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ConcurrentFormulaHashMap extends ConcurrentHashMap<Brick.FormulaField, Formula> implements Cloneable {
    private static final long serialVersionUID = 9030965461744658052L;

    @Override // java.util.AbstractMap
    public ConcurrentFormulaHashMap clone() throws CloneNotSupportedException {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        for (Brick.FormulaField formulaField : keySet()) {
            Formula formula = get(formulaField);
            if (formula != null) {
                concurrentFormulaHashMap.putIfAbsent(formulaField, formula.clone());
            }
        }
        return concurrentFormulaHashMap;
    }
}
