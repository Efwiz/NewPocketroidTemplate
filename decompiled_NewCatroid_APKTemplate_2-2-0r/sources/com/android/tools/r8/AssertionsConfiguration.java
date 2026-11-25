package com.android.tools.r8;

import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.O2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class AssertionsConfiguration {
    static final /* synthetic */ boolean e = true;
    private final int a;
    private final MethodReference b;
    private final a c;
    private final String d;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        O2 a;
        private int b;
        private MethodReference c;
        private a d;
        private String e;

        private Builder(O2 o2) {
            this.a = o2;
        }

        public static AssertionsConfiguration compileTimeDisableAllAssertions(Builder builder) {
            return builder.setCompileTimeDisable().setScopeAll().build();
        }

        public static AssertionsConfiguration compileTimeEnableAllAssertions(Builder builder) {
            return builder.setCompileTimeEnable().setScopeAll().build();
        }

        public static AssertionsConfiguration enableAllAssertions(Builder builder) {
            return compileTimeEnableAllAssertions(builder);
        }

        public static AssertionsConfiguration passthroughAllAssertions(Builder builder) {
            return builder.setPassthrough().setScopeAll().build();
        }

        public AssertionsConfiguration build() {
            if (this.b == 0 && this.c == null) {
                this.a.a("No transformation or assertion handler specified for building AssertionConfiguration");
            }
            if (this.d == null) {
                this.a.a("No scope specified for building AssertionConfiguration");
            }
            if (this.d == a.PACKAGE && this.e == null) {
                this.a.a("No package name specified for building AssertionConfiguration");
            }
            if (this.d == a.CLASS && this.e == null) {
                this.a.a("No class name specified for building AssertionConfiguration");
            }
            return new AssertionsConfiguration(this.b, this.c, this.d, this.e);
        }

        public Builder setAssertionHandler(MethodReference methodReference) {
            this.b = 0;
            this.c = methodReference;
            return this;
        }

        public Builder setCompileTimeDisable() {
            this.b = 2;
            this.c = null;
            return this;
        }

        public Builder setCompileTimeEnable() {
            this.b = 1;
            this.c = null;
            return this;
        }

        public Builder setPassthrough() {
            this.b = 3;
            this.c = null;
            return this;
        }

        public Builder setScopeAll() {
            this.d = a.ALL;
            this.e = null;
            return this;
        }

        public Builder setScopeClass(String str) {
            this.d = a.CLASS;
            this.e = str;
            return this;
        }

        public Builder setScopePackage(String str) {
            this.d = a.PACKAGE;
            this.e = str;
            return this;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        ALL,
        PACKAGE,
        CLASS
    }

    AssertionsConfiguration(int i, MethodReference methodReference, a aVar, String str) {
        this.a = i;
        this.b = methodReference;
        this.c = aVar;
        this.d = str;
        if (e) {
            return;
        }
        if (!com.android.tools.r8.utils.M.a(i != 0, methodReference != null)) {
            throw new AssertionError();
        }
    }

    static Builder a(O2 o2) {
        return new Builder(o2);
    }

    public MethodReference getAssertionHandler() {
        return this.b;
    }

    public a getScope() {
        return this.c;
    }

    public String getValue() {
        return this.d;
    }

    public boolean isAssertionHandler() {
        return this.b != null;
    }

    public boolean isCompileTimeDisabled() {
        return this.a == 2;
    }

    public boolean isCompileTimeEnabled() {
        return this.a == 1;
    }

    public boolean isPassthrough() {
        return this.a == 3;
    }
}
