package net.bytebuddy.matcher;

import com.huawei.hms.ml.language.common.utils.Constant;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class FailSafeMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {
    private final boolean fallback;
    private final ElementMatcher<? super T> matcher;

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FailSafeMatcher failSafeMatcher = (FailSafeMatcher) obj;
        return this.fallback == failSafeMatcher.fallback && this.matcher.equals(failSafeMatcher.matcher);
    }

    public int hashCode() {
        return (((getClass().hashCode() * 31) + this.matcher.hashCode()) * 31) + (this.fallback ? 1 : 0);
    }

    public FailSafeMatcher(ElementMatcher<? super T> elementMatcher, boolean z) {
        this.matcher = elementMatcher;
        this.fallback = z;
    }

    @Override // net.bytebuddy.matcher.ElementMatcher
    public boolean matches(@MaybeNull T t) {
        try {
            return this.matcher.matches(t);
        } catch (Exception unused) {
            return this.fallback;
        }
    }

    public String toString() {
        return "failSafe(try(" + this.matcher + ") or " + this.fallback + Constant.AFTER_QUTO;
    }
}
