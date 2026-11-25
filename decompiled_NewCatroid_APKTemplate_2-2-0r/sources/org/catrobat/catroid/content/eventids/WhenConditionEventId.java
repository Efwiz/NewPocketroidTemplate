package org.catrobat.catroid.content.eventids;

import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/eventids/WhenConditionEventId.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenConditionEventId extends EventId {
    final Formula formula;

    public WhenConditionEventId(Formula formula) {
        this.formula = formula;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhenConditionEventId) || !super.equals(o)) {
            return false;
        }
        Formula formula = this.formula;
        Formula formula2 = ((WhenConditionEventId) o).formula;
        if (formula != null) {
            return formula.equals(formula2);
        }
        return formula2 == null;
    }

    @Override // org.catrobat.catroid.content.eventids.EventId
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        Formula formula = this.formula;
        return iHashCode + (formula != null ? formula.hashCode() : 0);
    }
}
