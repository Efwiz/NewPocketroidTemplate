package net.bytebuddy.matcher;

import java.lang.ClassLoader;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class ClassLoaderParentMatcher<T extends ClassLoader> extends ElementMatcher.Junction.AbstractBase<T> {

    @MaybeNull
    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
    private final ClassLoader classLoader;

    /* JADX WARN: Code duplicated, block: B:19:0x0027 A[RETURN] */
    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassLoader classLoader = this.classLoader;
        ClassLoader classLoader2 = ((ClassLoaderParentMatcher) obj).classLoader;
        if (classLoader2 == null) {
            if (classLoader != null) {
                return false;
            }
            return true;
        }
        if (classLoader == null || !classLoader.equals(classLoader2)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int iHashCode = getClass().hashCode() * 31;
        ClassLoader classLoader = this.classLoader;
        return classLoader != null ? iHashCode + classLoader.hashCode() : iHashCode;
    }

    public ClassLoaderParentMatcher(@MaybeNull ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override // net.bytebuddy.matcher.ElementMatcher
    public boolean matches(@MaybeNull T t) {
        for (ClassLoader parent = this.classLoader; parent != null; parent = parent.getParent()) {
            if (parent == t) {
                return true;
            }
        }
        return t == null;
    }

    public String toString() {
        return "isParentOf(" + this.classLoader + ')';
    }
}
