package net.bytebuddy.matcher;

import java.lang.Iterable;
import java.util.Collection;
import java.util.Iterator;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class CollectionSizeMatcher<T extends Iterable<?>> extends ElementMatcher.Junction.ForNonNullValues<T> {
    private final int size;

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.size == ((CollectionSizeMatcher) obj).size;
    }

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public int hashCode() {
        return (super.hashCode() * 31) + this.size;
    }

    public CollectionSizeMatcher(int i) {
        this.size = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean doMatch(T t) {
        if (t instanceof Collection) {
            return ((Collection) t).size() == this.size;
        }
        Iterator it = t.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        return i == this.size;
    }

    public String toString() {
        return "ofSize(" + this.size + ')';
    }
}
