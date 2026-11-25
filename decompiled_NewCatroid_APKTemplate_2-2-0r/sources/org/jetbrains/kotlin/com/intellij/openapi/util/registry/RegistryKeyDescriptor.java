package org.jetbrains.kotlin.com.intellij.openapi.util.registry;

import com.google.android.gms.cast.MediaTrack;

/* JADX INFO: loaded from: classes3.dex */
final class RegistryKeyDescriptor {
    private final String myDefaultValue;
    private final boolean myRestartRequired;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = MediaTrack.ROLE_DESCRIPTION;
        } else if (i == 2) {
            objArr[0] = "defaultValue";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/RegistryKeyDescriptor";
        } else {
            objArr[0] = "name";
        }
        if (i == 3) {
            objArr[1] = "getName";
        } else if (i == 4) {
            objArr[1] = "getDescription";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/RegistryKeyDescriptor";
        } else {
            objArr[1] = "getDefaultValue";
        }
        if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public String getDefaultValue() {
        String str = this.myDefaultValue;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    public boolean isRestartRequired() {
        return this.myRestartRequired;
    }
}
