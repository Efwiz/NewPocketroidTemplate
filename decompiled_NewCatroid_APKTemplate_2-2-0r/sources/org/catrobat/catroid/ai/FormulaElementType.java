package org.catrobat.catroid.ai;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/FormulaElementType.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveModelConverter.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ai/FormulaElementType;", "", "(Ljava/lang/String;I)V", "OPERATOR", "FUNCTION", "NUMBER", "SENSOR", "USER_VARIABLE", "USER_LIST", "USER_DEFINED_BRICK_INPUT", "BRACKET", "STRING", "COLLISION_FORMULA", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum FormulaElementType {
    OPERATOR,
    FUNCTION,
    NUMBER,
    SENSOR,
    USER_VARIABLE,
    USER_LIST,
    USER_DEFINED_BRICK_INPUT,
    BRACKET,
    STRING,
    COLLISION_FORMULA;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<FormulaElementType> getEntries() {
        return $ENTRIES;
    }
}
