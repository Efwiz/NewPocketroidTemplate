package com.android.tools.r8.references;

import com.android.tools.r8.utils.C3970u0;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class PackageReference {
    private final String a;

    PackageReference(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Package name cannot be null.");
        }
        if (!str.isEmpty() && !C3970u0.E(str)) {
            throw new IllegalArgumentException("Package name '" + str + "' is not valid.");
        }
        this.a = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PackageReference) {
            return this.a.equals(((PackageReference) obj).a);
        }
        return false;
    }

    public String getPackageName() {
        return this.a;
    }

    public int hashCode() {
        return Objects.hash(this.a);
    }
}
