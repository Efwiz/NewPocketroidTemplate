package org.catrobat.catroid.userbrick;

import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/userbrick/InputFormulaField.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InputFormulaField implements Brick.FormulaField {
    String input;

    public InputFormulaField(String input) {
        this.input = input;
    }

    public InputFormulaField() {
        this.input = null;
    }

    public int hashCode() {
        return this.input.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof InputFormulaField) {
            return this.input.equals(((InputFormulaField) obj).input);
        }
        return false;
    }

    public String toString() {
        return this.input;
    }
}
