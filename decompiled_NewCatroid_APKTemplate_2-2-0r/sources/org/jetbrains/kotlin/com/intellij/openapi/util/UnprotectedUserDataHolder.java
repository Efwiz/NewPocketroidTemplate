package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: loaded from: classes3.dex */
public class UnprotectedUserDataHolder implements UserDataHolder, UserDataHolderUnprotected {
    private Map<Key, Object> myUserData;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/UnprotectedUserDataHolder";
        if (i == 1) {
            objArr[2] = "putUserData";
        } else if (i == 2) {
            objArr[2] = "getUserDataUnprotected";
        } else if (i != 3) {
            objArr[2] = "getUserData";
        } else {
            objArr[2] = "putUserDataUnprotected";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(0);
        }
        Map<Key, Object> map = this.myUserData;
        T t = map != null ? (T) map.get(key) : null;
        if (t != null || !(key instanceof KeyWithDefaultValue)) {
            return t;
        }
        T t2 = (T) ((KeyWithDefaultValue) key).getDefaultValue();
        putUserData(key, t2);
        return t2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(1);
        }
        if (this.myUserData == null) {
            this.myUserData = new HashMap();
        }
        this.myUserData.put(key, t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderUnprotected
    public <T> T getUserDataUnprotected(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(2);
        }
        return (T) getUserData(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderUnprotected
    public <T> void putUserDataUnprotected(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(3);
        }
        putUserData(key, t);
    }
}
