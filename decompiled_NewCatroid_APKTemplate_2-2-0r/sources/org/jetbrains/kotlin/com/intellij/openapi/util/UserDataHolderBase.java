package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.concurrent.atomic.AtomicReference;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Transient;

/* JADX INFO: loaded from: classes3.dex */
@Transient
public class UserDataHolderBase extends AtomicReference<KeyFMap> implements UserDataHolderEx {
    private static final Key<KeyFMap> COPYABLE_USER_MAP_KEY = Key.create("COPYABLE_USER_MAP_KEY");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 11 || i == 12) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 11 || i == 12) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 2:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/UserDataHolderBase";
                break;
            case 4:
                objArr[0] = "oldMap";
                break;
            case 5:
                objArr[0] = "newMap";
                break;
            case 10:
                objArr[0] = "value";
                break;
            case 13:
                objArr[0] = "clone";
                break;
            case 14:
                objArr[0] = "map";
                break;
            default:
                objArr[0] = "other";
                break;
        }
        if (i == 2) {
            objArr[1] = "getUserMap";
        } else if (i == 11 || i == 12) {
            objArr[1] = "putUserDataIfAbsent";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/UserDataHolderBase";
        }
        switch (i) {
            case 1:
                objArr[2] = "getUserData";
                break;
            case 2:
            case 11:
            case 12:
                break;
            case 3:
                objArr[2] = "putUserData";
                break;
            case 4:
            case 5:
                objArr[2] = "changeUserMap";
                break;
            case 6:
                objArr[2] = "getCopyableUserData";
                break;
            case 7:
                objArr[2] = "putCopyableUserData";
                break;
            case 8:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 9:
            case 10:
                objArr[2] = "putUserDataIfAbsent";
                break;
            case 13:
                objArr[2] = "copyCopyableDataTo";
                break;
            case 14:
                objArr[2] = "setUserMap";
                break;
            default:
                objArr[2] = "copyUserDataTo";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 11 && i != 12) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public UserDataHolderBase() {
        set(KeyFMap.EMPTY_MAP);
    }

    protected Object clone() {
        try {
            UserDataHolderBase userDataHolderBase = (UserDataHolderBase) super.clone();
            userDataHolderBase.setUserMap(KeyFMap.EMPTY_MAP);
            copyCopyableDataTo(userDataHolderBase);
            return userDataHolderBase;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserDataString() {
        KeyFMap userMap = getUserMap();
        KeyFMap keyFMap = (KeyFMap) getUserData(COPYABLE_USER_MAP_KEY);
        return userMap + (keyFMap == null ? "" : keyFMap.toString());
    }

    public void copyUserDataTo(UserDataHolderBase userDataHolderBase) {
        if (userDataHolderBase == null) {
            $$$reportNull$$$0(0);
        }
        userDataHolderBase.setUserMap(getUserMap());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(1);
        }
        T t = (T) getUserMap().get(key);
        return (t == null && (key instanceof KeyWithDefaultValue)) ? (T) putUserDataIfAbsent(key, ((KeyWithDefaultValue) key).getDefaultValue()) : t;
    }

    protected KeyFMap getUserMap() {
        KeyFMap keyFMap = get();
        if (keyFMap == null) {
            $$$reportNull$$$0(2);
        }
        return keyFMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> key, T t) {
        KeyFMap userMap;
        KeyFMap keyFMapMinus;
        if (key == null) {
            $$$reportNull$$$0(3);
        }
        do {
            userMap = getUserMap();
            keyFMapMinus = t == null ? userMap.minus(key) : userMap.plus(key, t);
            if (keyFMapMinus == userMap) {
                return;
            }
        } while (!changeUserMap(userMap, keyFMapMinus));
    }

    protected boolean changeUserMap(KeyFMap keyFMap, KeyFMap keyFMap2) {
        if (keyFMap == null) {
            $$$reportNull$$$0(4);
        }
        if (keyFMap2 == null) {
            $$$reportNull$$$0(5);
        }
        return compareAndSet(keyFMap, keyFMap2);
    }

    public <T> T getCopyableUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(6);
        }
        KeyFMap keyFMap = (KeyFMap) getUserData(COPYABLE_USER_MAP_KEY);
        if (keyFMap == null) {
            return null;
        }
        return (T) keyFMap.get(key);
    }

    public <T> void putCopyableUserData(Key<T> key, T t) {
        KeyFMap userMap;
        KeyFMap keyFMapMinus;
        if (key == null) {
            $$$reportNull$$$0(7);
        }
        do {
            userMap = getUserMap();
            Key<KeyFMap> key2 = COPYABLE_USER_MAP_KEY;
            KeyFMap keyFMap = (KeyFMap) userMap.get(key2);
            if (keyFMap == null) {
                keyFMap = KeyFMap.EMPTY_MAP;
            }
            KeyFMap keyFMapMinus2 = t == null ? keyFMap.minus(key) : keyFMap.plus(key, t);
            keyFMapMinus = keyFMapMinus2.isEmpty() ? userMap.minus(key2) : userMap.plus(key2, keyFMapMinus2);
            if (keyFMapMinus == userMap) {
                return;
            }
        } while (!changeUserMap(userMap, keyFMapMinus));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx
    public <T> boolean replace(Key<T> key, T t, T t2) {
        KeyFMap userMap;
        KeyFMap keyFMapMinus;
        if (key == null) {
            $$$reportNull$$$0(8);
        }
        do {
            userMap = getUserMap();
            if (userMap.get(key) != t) {
                return false;
            }
            keyFMapMinus = t2 == null ? userMap.minus(key) : userMap.plus(key, t2);
            if (keyFMapMinus == userMap) {
                return true;
            }
        } while (!changeUserMap(userMap, keyFMapMinus));
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx
    public <T> T putUserDataIfAbsent(Key<T> key, T t) {
        KeyFMap userMap;
        KeyFMap keyFMapPlus;
        if (key == null) {
            $$$reportNull$$$0(9);
        }
        if (t == null) {
            $$$reportNull$$$0(10);
        }
        do {
            userMap = getUserMap();
            T t2 = (T) userMap.get(key);
            if (t2 == null) {
                keyFMapPlus = userMap.plus(key, t);
                if (keyFMapPlus == userMap) {
                    break;
                }
            } else {
                if (t2 == null) {
                    $$$reportNull$$$0(11);
                }
                return t2;
            }
        } while (!changeUserMap(userMap, keyFMapPlus));
        if (t == null) {
            $$$reportNull$$$0(12);
        }
        return t;
    }

    public void copyCopyableDataTo(UserDataHolderBase userDataHolderBase) {
        if (userDataHolderBase == null) {
            $$$reportNull$$$0(13);
        }
        Key<KeyFMap> key = COPYABLE_USER_MAP_KEY;
        userDataHolderBase.putUserData(key, (KeyFMap) getUserData(key));
    }

    protected void clearUserData() {
        setUserMap(KeyFMap.EMPTY_MAP);
    }

    protected void setUserMap(KeyFMap keyFMap) {
        if (keyFMap == null) {
            $$$reportNull$$$0(14);
        }
        set(keyFMap);
    }

    public boolean isUserDataEmpty() {
        return getUserMap().isEmpty();
    }
}
