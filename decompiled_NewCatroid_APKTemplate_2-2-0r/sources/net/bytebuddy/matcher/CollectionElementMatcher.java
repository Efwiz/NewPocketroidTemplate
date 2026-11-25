package net.bytebuddy.matcher;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class CollectionElementMatcher<T> extends ElementMatcher.Junction.ForNonNullValues<Iterable<? extends T>> {
    private final int index;
    private final ElementMatcher<? super T> matcher;

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CollectionElementMatcher collectionElementMatcher = (CollectionElementMatcher) obj;
        return this.index == collectionElementMatcher.index && this.matcher.equals(collectionElementMatcher.matcher);
    }

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public int hashCode() {
        return (((super.hashCode() * 31) + this.index) * 31) + this.matcher.hashCode();
    }

    public CollectionElementMatcher(int i, ElementMatcher<? super T> elementMatcher) {
        this.index = i;
        this.matcher = elementMatcher;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean doMatch(Iterable<? extends T> iterable) {
        Iterator<? extends T> it = iterable.iterator();
        for (int i = 0; i < this.index; i++) {
            if (!it.hasNext()) {
                return false;
            }
            it.next();
        }
        return it.hasNext() && this.matcher.matches(it.next());
    }

    public String toString() {
        return "with(" + this.index + " matches " + this.matcher + Constant.AFTER_QUTO;
    }
}
