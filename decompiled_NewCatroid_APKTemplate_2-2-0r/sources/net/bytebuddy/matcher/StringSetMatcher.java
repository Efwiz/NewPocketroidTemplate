package net.bytebuddy.matcher;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Set;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class StringSetMatcher extends ElementMatcher.Junction.ForNonNullValues<String> {
    private final Set<String> values;

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.values.equals(((StringSetMatcher) obj).values);
    }

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public int hashCode() {
        return (super.hashCode() * 31) + this.values.hashCode();
    }

    public StringSetMatcher(Set<String> set) {
        this.values = set;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean doMatch(String str) {
        return this.values.contains(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("in(");
        boolean z = true;
        for (String str : this.values) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(str);
        }
        return sb.append(Constant.AFTER_QUTO).toString();
    }
}
