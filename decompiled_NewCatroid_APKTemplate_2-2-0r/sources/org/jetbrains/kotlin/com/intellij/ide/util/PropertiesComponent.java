package org.jetbrains.kotlin.com.intellij.ide.util;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.SimpleModificationTracker;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PropertiesComponent extends SimpleModificationTracker {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "project";
        } else if (i == 5) {
            objArr[0] = "defaultValue";
        } else if (i == 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/ide/util/PropertiesComponent";
        } else if (i == 12 || i == 13) {
            objArr[0] = "object";
        } else {
            objArr[0] = "name";
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/ide/util/PropertiesComponent";
        } else {
            objArr[1] = "getValue";
        }
        switch (i) {
            case 1:
                objArr[2] = "getInstance";
                break;
            case 2:
            case 3:
                objArr[2] = "getBoolean";
                break;
            case 4:
            case 5:
                objArr[2] = "getValue";
                break;
            case 6:
                break;
            case 7:
                objArr[2] = "getOrInitInt";
                break;
            case 8:
                objArr[2] = "getInt";
                break;
            case 9:
                objArr[2] = "getLong";
                break;
            case 10:
                objArr[2] = "getOrInitLong";
                break;
            case 11:
                objArr[2] = "getOrInit";
                break;
            case 12:
                objArr[2] = "saveFields";
                break;
            case 13:
                objArr[2] = "loadFields";
                break;
            case 14:
                objArr[2] = "getFloat";
                break;
            default:
                objArr[2] = "setValue";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract String getValue(String str);

    public abstract boolean isValueSet(String str);

    public abstract void setValue(String str, boolean z, boolean z2);

    public static PropertiesComponent getInstance() {
        return (PropertiesComponent) ApplicationManager.getApplication().getService(PropertiesComponent.class);
    }

    public final boolean isTrueValue(String str) {
        return Boolean.parseBoolean(getValue(str));
    }

    public final boolean getBoolean(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return isValueSet(str) ? isTrueValue(str) : z;
    }
}
