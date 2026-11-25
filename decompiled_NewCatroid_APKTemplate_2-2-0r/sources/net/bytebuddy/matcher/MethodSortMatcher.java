package net.bytebuddy.matcher;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class MethodSortMatcher<T extends MethodDescription> extends ElementMatcher.Junction.ForNonNullValues<T> {
    private final Sort sort;

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.sort.equals(((MethodSortMatcher) obj).sort);
    }

    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public int hashCode() {
        return (super.hashCode() * 31) + this.sort.hashCode();
    }

    public static <T extends MethodDescription> ElementMatcher.Junction<T> of(Sort sort) {
        return sort.getMatcher();
    }

    public MethodSortMatcher(Sort sort) {
        this.sort = sort;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.bytebuddy.matcher.ElementMatcher.Junction.ForNonNullValues
    public boolean doMatch(T t) {
        return this.sort.isSort(t);
    }

    public String toString() {
        return this.sort.getDescription();
    }

    public enum Sort {
        METHOD("isMethod()") { // from class: net.bytebuddy.matcher.MethodSortMatcher.Sort.1
            @Override // net.bytebuddy.matcher.MethodSortMatcher.Sort
            protected boolean isSort(MethodDescription methodDescription) {
                return methodDescription.isMethod();
            }
        },
        CONSTRUCTOR("isConstructor()") { // from class: net.bytebuddy.matcher.MethodSortMatcher.Sort.2
            @Override // net.bytebuddy.matcher.MethodSortMatcher.Sort
            protected boolean isSort(MethodDescription methodDescription) {
                return methodDescription.isConstructor();
            }
        },
        TYPE_INITIALIZER("isTypeInitializer()") { // from class: net.bytebuddy.matcher.MethodSortMatcher.Sort.3
            @Override // net.bytebuddy.matcher.MethodSortMatcher.Sort
            protected boolean isSort(MethodDescription methodDescription) {
                return methodDescription.isTypeInitializer();
            }
        },
        VIRTUAL("isVirtual()") { // from class: net.bytebuddy.matcher.MethodSortMatcher.Sort.4
            @Override // net.bytebuddy.matcher.MethodSortMatcher.Sort
            protected boolean isSort(MethodDescription methodDescription) {
                return methodDescription.isVirtual();
            }
        },
        DEFAULT_METHOD("isDefaultMethod()") { // from class: net.bytebuddy.matcher.MethodSortMatcher.Sort.5
            @Override // net.bytebuddy.matcher.MethodSortMatcher.Sort
            protected boolean isSort(MethodDescription methodDescription) {
                return methodDescription.isDefaultMethod();
            }
        };

        private final String description;
        private final MethodSortMatcher<?> matcher;

        protected abstract boolean isSort(MethodDescription methodDescription);

        Sort(String str) {
            this.description = str;
            this.matcher = new MethodSortMatcher<>(this);
        }

        protected String getDescription() {
            return this.description;
        }

        protected MethodSortMatcher<?> getMatcher() {
            return this.matcher;
        }
    }
}
