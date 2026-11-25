package org.jetbrains.kotlin.js.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.backend.ast.JsNode;

/* JADX INFO: loaded from: classes5.dex */
public final class AstUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/util/AstUtil", "deepCopy"));
    }

    private AstUtil() {
    }

    public static <T extends JsNode> T deepCopy(T t) {
        if (t == null) {
            return null;
        }
        return (T) t.deepCopy();
    }

    public static <T extends JsNode> List<T> deepCopy(List<T> list) {
        if (list == null) {
            return new SmartList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(deepCopy(it.next()));
        }
        return arrayList;
    }
}
