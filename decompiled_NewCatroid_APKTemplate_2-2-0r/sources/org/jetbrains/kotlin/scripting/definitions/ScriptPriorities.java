package org.jetbrains.kotlin.scripting.definitions;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: loaded from: classes6.dex */
public class ScriptPriorities {
    public static final Key<Integer> PRIORITY_KEY = Key.create(KtScript.class.getName() + ".priority");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "script";
        objArr[1] = "org/jetbrains/kotlin/scripting/definitions/ScriptPriorities";
        if (i != 1) {
            objArr[2] = "getScriptPriority";
        } else {
            objArr[2] = "isReplScript";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static int getScriptPriority(KtScript ktScript) {
        if (ktScript == null) {
            $$$reportNull$$$0(0);
        }
        Integer num = (Integer) ktScript.getUserData(PRIORITY_KEY);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static boolean isReplScript(KtScript ktScript) {
        if (ktScript == null) {
            $$$reportNull$$$0(1);
        }
        return ktScript.getUserData(PRIORITY_KEY) != null;
    }
}
