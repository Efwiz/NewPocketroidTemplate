package org.jf.dexlib2.util;

import java.util.function.Predicate;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.iface.Field;

/* JADX INFO: loaded from: classes6.dex */
public final class FieldUtil {
    public static Predicate<Field> FIELD_IS_STATIC = new Predicate<Field>() { // from class: org.jf.dexlib2.util.FieldUtil.1
        @Override // java.util.function.Predicate
        public boolean test(Field field) {
            return field != null && FieldUtil.isStatic(field);
        }
    };
    public static Predicate<Field> FIELD_IS_INSTANCE = new Predicate<Field>() { // from class: org.jf.dexlib2.util.FieldUtil.2
        @Override // java.util.function.Predicate
        public boolean test(Field field) {
            return (field == null || FieldUtil.isStatic(field)) ? false : true;
        }
    };

    public static boolean isStatic(Field field) {
        return AccessFlags.STATIC.isSet(field.getAccessFlags());
    }

    private FieldUtil() {
    }
}
