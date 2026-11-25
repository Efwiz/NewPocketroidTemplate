package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.indexing.ID;

/* JADX INFO: loaded from: classes3.dex */
public final class StubIndexKey<K, Psi extends PsiElement> extends ID<K, Psi> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubIndexKey";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubIndexKey";
        } else {
            objArr[1] = "createIndexKey";
        }
        if (i != 1) {
            objArr[2] = "createIndexKey";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    private StubIndexKey(String str, PluginId pluginId) {
        super(str, pluginId);
    }

    /* JADX WARN: In static synchronized method top region not synchronized by class const: (wrap java.lang.Class:0x0002: CONST_CLASS  A[WRAPPED] org.jetbrains.kotlin.com.intellij.psi.stubs.StubIndexKey.class) */
    public static synchronized <K, Psi extends PsiElement> StubIndexKey<K, Psi> createIndexKey(String str) {
        synchronized (StubIndexKey.class) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            PluginId callerPluginId = getCallerPluginId();
            ID idFindByName = findByName(str, true, callerPluginId);
            if (idFindByName != null) {
                if (idFindByName instanceof StubIndexKey) {
                    StubIndexKey<K, Psi> stubIndexKey = (StubIndexKey) idFindByName;
                    if (stubIndexKey == null) {
                        $$$reportNull$$$0(1);
                    }
                    return stubIndexKey;
                }
                throw new IllegalStateException("key with id " + str + " is already registered", idFindByName.getRegistrationTrace());
            }
            return new StubIndexKey<>(str, callerPluginId);
        }
    }
}
