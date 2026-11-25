package org.catrobat.catroid.formulaeditor;

import org.catrobat.catroid.utils.EnumUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/Operators.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum Operators {
    LOGICAL_AND(2, true),
    LOGICAL_OR(1, true),
    EQUAL(3, true),
    NOT_EQUAL(4, true),
    SMALLER_OR_EQUAL(4, true),
    GREATER_OR_EQUAL(4, true),
    SMALLER_THAN(4, true),
    GREATER_THAN(4, true),
    PLUS(5),
    MINUS(5),
    MULT(6),
    DIVIDE(6),
    MOD(6),
    POW(7),
    LOGICAL_NOT(4, true);

    private static final String TAG = "Operators";
    public final boolean isLogicalOperator;
    private final int priority;

    Operators(int priority) {
        this.priority = priority;
        this.isLogicalOperator = false;
    }

    Operators(int priority, boolean isLogical) {
        this.priority = priority;
        this.isLogicalOperator = isLogical;
    }

    public int compareOperatorTo(Operators operator) {
        return Integer.compare(this.priority, operator.priority);
    }

    public static Operators getOperatorByValue(String value) {
        return (Operators) EnumUtils.getEnum(Operators.class, value);
    }

    public static boolean isOperator(String value) {
        return EnumUtils.isValidEnum(Operators.class, value);
    }
}
