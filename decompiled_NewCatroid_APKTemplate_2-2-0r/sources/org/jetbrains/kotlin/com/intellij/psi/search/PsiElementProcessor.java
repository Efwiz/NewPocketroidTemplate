package org.jetbrains.kotlin.com.intellij.psi.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiElementProcessor<T extends PsiElement> {
    boolean execute(T t);

    public static class CollectElements<T extends PsiElement> implements PsiElementProcessor<T> {
        private final Collection<T> myCollection;

        /* JADX WARN: Code duplicated, block: B:26:0x003f  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
            if (i == 1 || i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/search/PsiElementProcessor$CollectElements";
            } else if (i != 4) {
                objArr[0] = "collection";
            } else {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            }
            if (i == 1) {
                objArr[1] = "toArray";
            } else if (i == 2) {
                objArr[1] = "getCollection";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/PsiElementProcessor$CollectElements";
            } else {
                objArr[1] = "toArray";
            }
            if (i != 1 && i != 2 && i != 3) {
                if (i != 4) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "execute";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public CollectElements() {
            this(new ArrayList());
        }

        public CollectElements(Collection<T> collection) {
            if (collection == null) {
                $$$reportNull$$$0(0);
            }
            this.myCollection = Collections.synchronizedCollection(collection);
        }

        public PsiElement[] toArray() {
            PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(this.myCollection);
            if (psiElementArray == null) {
                $$$reportNull$$$0(1);
            }
            return psiElementArray;
        }

        public Collection<T> getCollection() {
            Collection<T> collection = this.myCollection;
            if (collection == null) {
                $$$reportNull$$$0(2);
            }
            return collection;
        }

        public T[] toArray(T[] tArr) {
            T[] tArr2 = (T[]) ((PsiElement[]) this.myCollection.toArray(tArr));
            if (tArr2 == null) {
                $$$reportNull$$$0(3);
            }
            return tArr2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
        public boolean execute(T t) {
            if (t == null) {
                $$$reportNull$$$0(4);
            }
            this.myCollection.add(t);
            return true;
        }
    }

    @Deprecated
    public static class CollectFilteredElements<T extends PsiElement> extends CollectElements<T> {
        private final PsiElementFilter myFilter;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "collection";
            } else if (i != 3) {
                objArr[0] = "filter";
            } else {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/PsiElementProcessor$CollectFilteredElements";
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "execute";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CollectFilteredElements(PsiElementFilter psiElementFilter, Collection<T> collection) {
            super(collection);
            if (psiElementFilter == null) {
                $$$reportNull$$$0(0);
            }
            if (collection == null) {
                $$$reportNull$$$0(1);
            }
            this.myFilter = psiElementFilter;
        }

        public CollectFilteredElements(PsiElementFilter psiElementFilter) {
            if (psiElementFilter == null) {
                $$$reportNull$$$0(2);
            }
            this.myFilter = psiElementFilter;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor.CollectElements, org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
        public boolean execute(T t) {
            if (t == null) {
                $$$reportNull$$$0(3);
            }
            return !this.myFilter.isAccepted(t) || super.execute(t);
        }
    }

    public static class CollectElementsWithLimit<T extends PsiElement> extends CollectElements<T> {
        private final AtomicInteger myCount;
        private final int myLimit;
        private volatile boolean myOverflow;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "collection";
            } else {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/search/PsiElementProcessor$CollectElementsWithLimit";
            if (i != 1) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "execute";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public CollectElementsWithLimit(int i) {
            this.myCount = new AtomicInteger(0);
            this.myLimit = i;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CollectElementsWithLimit(int i, Collection<T> collection) {
            super(collection);
            if (collection == null) {
                $$$reportNull$$$0(0);
            }
            this.myCount = new AtomicInteger(0);
            this.myLimit = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor.CollectElements, org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
        public boolean execute(T t) {
            if (t == null) {
                $$$reportNull$$$0(1);
            }
            if (this.myCount.get() == this.myLimit) {
                this.myOverflow = true;
                return false;
            }
            this.myCount.incrementAndGet();
            return super.execute(t);
        }

        public boolean isOverflow() {
            return this.myOverflow;
        }
    }

    public static class FindElement<T extends PsiElement> implements PsiElementProcessor<T> {
        private volatile T myFoundElement;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/search/PsiElementProcessor$FindElement", "execute"));
        }

        public boolean isFound() {
            return this.myFoundElement != null;
        }

        public T getFoundElement() {
            return this.myFoundElement;
        }

        public boolean setFound(T t) {
            this.myFoundElement = t;
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
        public boolean execute(T t) {
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            return setFound(t);
        }
    }
}
