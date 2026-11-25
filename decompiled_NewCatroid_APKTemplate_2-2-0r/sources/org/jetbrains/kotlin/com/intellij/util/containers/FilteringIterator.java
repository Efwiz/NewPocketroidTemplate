package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;

/* JADX INFO: loaded from: classes3.dex */
public final class FilteringIterator<Dom, E extends Dom> implements PeekableIterator<E> {
    private final Condition<? super Dom> myCondition;
    private Dom myCurrent;
    private boolean myCurrentIsValid;
    private Boolean myCurrentPassedFilter;
    private final Iterator<? extends Dom> myDelegate;
    private boolean myNextObtained;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = "condition";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/FilteringIterator";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public FilteringIterator(Iterator<? extends Dom> it, Condition<? super Dom> condition) {
        if (it == null) {
            $$$reportNull$$$0(0);
        }
        if (condition == null) {
            $$$reportNull$$$0(1);
        }
        this.myDelegate = it;
        this.myCondition = condition;
    }

    private void obtainNext() {
        if (this.myNextObtained) {
            return;
        }
        boolean zHasNext = this.myDelegate.hasNext();
        setCurrent(zHasNext ? this.myDelegate.next() : null);
        this.myCurrentIsValid = zHasNext;
        this.myNextObtained = true;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        obtainNext();
        if (!this.myCurrentIsValid) {
            return false;
        }
        boolean zIsCurrentPassesFilter = isCurrentPassesFilter();
        while (!zIsCurrentPassesFilter && this.myDelegate.hasNext()) {
            setCurrent(this.myDelegate.next());
            zIsCurrentPassesFilter = isCurrentPassesFilter();
        }
        return zIsCurrentPassesFilter;
    }

    private void setCurrent(Dom dom) {
        this.myCurrent = dom;
        this.myCurrentPassedFilter = null;
    }

    private boolean isCurrentPassesFilter() {
        Boolean bool = this.myCurrentPassedFilter;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zValue = this.myCondition.value(this.myCurrent);
        this.myCurrentPassedFilter = Boolean.valueOf(zValue);
        return zValue;
    }

    @Override // java.util.Iterator
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Dom dom = this.myCurrent;
        this.myNextObtained = false;
        return dom;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (this.myNextObtained) {
            throw new IllegalStateException();
        }
        this.myDelegate.remove();
    }
}
