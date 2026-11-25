package org.catrobat.catroid.formulaeditor;

import com.google.common.base.Objects;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/UndoState.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UndoState {
    public final Brick.FormulaField formulaField;
    public final InternFormulaState internFormulaState;

    public UndoState(InternFormulaState internFormulaState, Brick.FormulaField formulaField) {
        this.formulaField = formulaField;
        this.internFormulaState = internFormulaState;
    }

    public boolean equals(Object objectCompareTo) {
        if (this == objectCompareTo) {
            return true;
        }
        if (!(objectCompareTo instanceof UndoState)) {
            return false;
        }
        UndoState undoState = (UndoState) objectCompareTo;
        return Objects.equal(this.internFormulaState, undoState.internFormulaState) && this.formulaField == undoState.formulaField;
    }

    public int hashCode() {
        return Objects.hashCode(this.internFormulaState, this.formulaField);
    }
}
