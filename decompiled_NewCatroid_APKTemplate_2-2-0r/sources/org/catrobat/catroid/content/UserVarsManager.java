package org.catrobat.catroid.content;

import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/UserVarsManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: UserVarsManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0005J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u0005J\u0010\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u0010\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/content/UserVarsManager;", "", "()V", "variables", "Ljava/util/LinkedHashMap;", "", "Lkotlin/collections/LinkedHashMap;", "clearVars", "", "delVar", "name", "getVar", "getVarName", "id", "", "getVarValue", "setVar", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UserVarsManager {
    public static final UserVarsManager INSTANCE = new UserVarsManager();
    private static final LinkedHashMap<String, String> variables = new LinkedHashMap<>();

    private UserVarsManager() {
    }

    public final synchronized void setVar(String name2, String value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        variables.put(name2, value);
    }

    public final synchronized String getVar(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return variables.get(name2);
    }

    public final synchronized void clearVars() {
        variables.clear();
    }

    public final synchronized void delVar(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        variables.remove(name2);
    }

    public final synchronized String getVarName(int id2) {
        if (id2 >= 0) {
            LinkedHashMap<String, String> linkedHashMap = variables;
            if (id2 < linkedHashMap.size()) {
                Set<String> setKeySet = linkedHashMap.keySet();
                Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
                return (String) CollectionsKt.elementAtOrNull(setKeySet, id2);
            }
        }
        return null;
    }

    public final synchronized String getVarValue(int id2) {
        String varName = getVarName(id2);
        if (varName == null) {
            return null;
        }
        return variables.get(varName);
    }
}
