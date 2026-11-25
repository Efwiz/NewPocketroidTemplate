package org.jf.util;

import java.util.List;
import java.util.function.Function;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class CharSequenceUtils {
    private static final Function<Object, String> TO_STRING = new Function<Object, String>() { // from class: org.jf.util.CharSequenceUtils.1
        @Override // java.util.function.Function
        public String apply(Object obj) {
            return String.valueOf(obj);
        }
    };

    public static int listHashCode(List<? extends CharSequence> list) {
        return ListUtil.transform(list, TO_STRING).hashCode();
    }

    public static boolean listEquals(List<? extends CharSequence> list, List<? extends CharSequence> list2) {
        if (list == list2) {
            return true;
        }
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).toString().equals(list2.get(i).toString())) {
                return false;
            }
        }
        return true;
    }
}
