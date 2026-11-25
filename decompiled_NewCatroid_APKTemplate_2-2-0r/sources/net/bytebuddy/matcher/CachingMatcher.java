package net.bytebuddy.matcher;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance(permitSubclassEquality = true)
public class CachingMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {
    private static final Object NULL_VALUE = new Object();

    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.IGNORE)
    protected final ConcurrentMap<? super T, Boolean> map;
    private final ElementMatcher<? super T> matcher;

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CachingMatcher) && this.matcher.equals(((CachingMatcher) obj).matcher);
    }

    public int hashCode() {
        return (CachingMatcher.class.hashCode() * 31) + this.matcher.hashCode();
    }

    public CachingMatcher(ElementMatcher<? super T> elementMatcher, ConcurrentMap<? super T, Boolean> concurrentMap) {
        this.matcher = elementMatcher;
        this.map = concurrentMap;
    }

    @Override // net.bytebuddy.matcher.ElementMatcher
    public boolean matches(@MaybeNull T t) {
        Boolean boolValueOf = this.map.get(t == null ? NULL_VALUE : t);
        if (boolValueOf == null) {
            boolValueOf = Boolean.valueOf(onCacheMiss(t));
        }
        return boolValueOf.booleanValue();
    }

    protected boolean onCacheMiss(@MaybeNull T t) {
        boolean zMatches = this.matcher.matches(t);
        ConcurrentMap<? super T, Boolean> concurrentMap = this.map;
        if (t == null) {
            t = (Object) NULL_VALUE;
        }
        concurrentMap.put(t, Boolean.valueOf(zMatches));
        return zMatches;
    }

    public String toString() {
        return "cached(" + this.matcher + Constant.AFTER_QUTO;
    }

    public static class WithInlineEviction<S> extends CachingMatcher<S> {
        private final int evictionSize;

        public WithInlineEviction(ElementMatcher<? super S> elementMatcher, ConcurrentMap<? super S, Boolean> concurrentMap, int i) {
            super(elementMatcher, concurrentMap);
            this.evictionSize = i;
        }

        @Override // net.bytebuddy.matcher.CachingMatcher
        protected boolean onCacheMiss(@MaybeNull S s) {
            if (this.map.size() >= this.evictionSize) {
                Iterator<Map.Entry<? super T, Boolean>> it = this.map.entrySet().iterator();
                it.next();
                it.remove();
            }
            return super.onCacheMiss(s);
        }
    }
}
