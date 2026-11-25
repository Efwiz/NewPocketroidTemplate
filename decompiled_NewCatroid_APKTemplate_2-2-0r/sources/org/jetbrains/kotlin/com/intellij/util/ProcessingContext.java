package org.jetbrains.kotlin.com.intellij.util;

import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public class ProcessingContext {
    private Map<Object, Object> myMap;
    private SharedProcessingContext mySharedContext;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2 || i == 3) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ProcessingContext";
        } else {
            objArr[0] = "value";
        }
        if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ProcessingContext";
        } else {
            objArr[1] = "getSharedContext";
        }
        if (i == 2) {
            objArr[2] = "get";
        } else if (i == 3 || i == 4) {
            objArr[2] = "put";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public ProcessingContext() {
    }

    public ProcessingContext(SharedProcessingContext sharedProcessingContext) {
        this.mySharedContext = sharedProcessingContext;
    }

    public Object get(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(2);
        }
        Map<Object, Object> map = this.myMap;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    public void put(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(3);
        }
        if (obj2 == null) {
            $$$reportNull$$$0(4);
        }
        checkMapInitialized();
        this.myMap.put(obj, obj2);
    }

    public <T> void put(Key<T> key, T t) {
        checkMapInitialized();
        this.myMap.put(key, t);
    }

    private void checkMapInitialized() {
        if (this.myMap == null) {
            this.myMap = new HashMap(1);
        }
    }
}
