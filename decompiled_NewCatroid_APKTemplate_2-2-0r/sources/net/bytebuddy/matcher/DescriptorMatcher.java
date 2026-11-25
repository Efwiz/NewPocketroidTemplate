package net.bytebuddy.matcher;

import com.huawei.hms.ml.language.common.utils.Constant;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.NamedElement.WithDescriptor;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class DescriptorMatcher<T extends NamedElement.WithDescriptor> extends ElementMatcher.Junction.ForNonNullValues<T> {
    private final ElementMatcher<String> matcher;

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.matcher.equals(((DescriptorMatcher) obj).matcher);
    }

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public int hashCode() {
        return (super.hashCode() * 31) + this.matcher.hashCode();
    }

    public DescriptorMatcher(ElementMatcher<String> elementMatcher) {
        this.matcher = elementMatcher;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean doMatch(T t) {
        return this.matcher.matches(t.getDescriptor());
    }

    public String toString() {
        return "hasDescriptor(" + this.matcher + Constant.AFTER_QUTO;
    }
}
