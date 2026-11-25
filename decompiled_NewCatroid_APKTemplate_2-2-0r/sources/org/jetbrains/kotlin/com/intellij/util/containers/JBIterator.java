package org.jetbrains.kotlin.com.intellij.util.containers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JBIterator<E> implements Iterator<E> {
    private static final Function.Mono CURSOR_NEXT = new Function.Mono<JBIterator<?>>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.2
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public JBIterator<?> fun(JBIterator<?> jBIterator) {
            Objects.requireNonNull(jBIterator);
            return (JBIterator) jBIterator.addOp(false, new CursorOp());
        }
    };
    private Op myFirstOp;
    private Op myLastOp;
    private Object myCurrent = Do.INIT;
    private Object myNext = Do.INIT;

    private enum Do {
        INIT,
        STOP,
        SKIP
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0011  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 15 && i != 16) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 15 && i != 16) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/JBIterator";
                break;
            case 2:
            case 3:
                objArr[0] = "it";
                break;
            case 4:
            case 6:
                objArr[0] = "function";
                break;
            case 5:
            case 7:
            case 8:
                objArr[0] = "condition";
                break;
            case 9:
                objArr[0] = "op";
                break;
            case 14:
                objArr[0] = "o";
                break;
            default:
                objArr[0] = "iterator";
                break;
        }
        if (i == 1) {
            objArr[1] = "cursor";
        } else if (i != 15 && i != 16) {
            switch (i) {
                case 10:
                    objArr[1] = "addOp";
                    break;
                case 11:
                    objArr[1] = "toList";
                    break;
                case 12:
                    objArr[1] = "getTransformations";
                    break;
                case 13:
                    objArr[1] = "operationsImpl";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/JBIterator";
                    break;
            }
        } else {
            objArr[1] = "toShortString";
        }
        switch (i) {
            case 1:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
                break;
            case 2:
                objArr[2] = TypedValues.TransitionType.S_FROM;
                break;
            case 3:
                objArr[2] = "wrap";
                break;
            case 4:
                objArr[2] = "map";
                break;
            case 5:
                objArr[2] = "filter";
                break;
            case 6:
                objArr[2] = "filterMap";
                break;
            case 7:
                objArr[2] = "takeWhile";
                break;
            case 8:
                objArr[2] = "skipWhile";
                break;
            case 9:
                objArr[2] = "addOp";
                break;
            case 14:
                objArr[2] = "toShortString";
                break;
            default:
                objArr[2] = "cursor";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 15 && i != 16) {
            switch (i) {
                case 10:
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    protected void currentChanged() {
    }

    protected abstract E nextImpl();

    public JBIterator() {
        NextOp nextOp = new NextOp();
        this.myFirstOp = nextOp;
        this.myLastOp = nextOp;
    }

    public static <E> JBIterator<E> from(Iterator<? extends E> it) {
        if (it == null) {
            $$$reportNull$$$0(2);
        }
        return it instanceof JBIterator ? (JBIterator) it : wrap(it);
    }

    static <E> JBIterator<E> wrap(final Iterator<? extends E> it) {
        if (it == null) {
            $$$reportNull$$$0(3);
        }
        return new JBIterator<E>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator
            protected E nextImpl() {
                return it.hasNext() ? (E) it.next() : stop();
            }
        };
    }

    protected final E stop() {
        this.myNext = Do.STOP;
        return null;
    }

    protected final E skip() {
        this.myNext = Do.SKIP;
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        peekNext();
        return this.myNext != Do.STOP;
    }

    @Override // java.util.Iterator
    public final E next() {
        advance();
        return current();
    }

    public final boolean advance() {
        this.myCurrent = Do.INIT;
        peekNext();
        if (this.myNext == Do.STOP) {
            return false;
        }
        this.myCurrent = this.myNext;
        this.myNext = Do.INIT;
        Op op = this.myFirstOp;
        if (op instanceof CursorOp) {
            ((CursorOp) op).advance(this.myCurrent);
        }
        currentChanged();
        return true;
    }

    public final E current() {
        if (this.myCurrent == Do.INIT) {
            throw new NoSuchElementException();
        }
        return (E) this.myCurrent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void peekNext() {
        if (this.myNext != Do.INIT) {
            return;
        }
        Object objApply = Do.INIT;
        while (true) {
            Op op = this.myFirstOp;
            while (true) {
                if (op != null) {
                    if (op.impl == 0) {
                        objApply = nextImpl();
                    }
                    objApply = op.apply(objApply);
                    if (this.myNext == Do.STOP) {
                        return;
                    }
                    if (this.myNext == Do.SKIP) {
                        objApply = Do.INIT;
                        this.myNext = objApply;
                        if (op.impl == 0) {
                            for (Op op2 = this.myFirstOp; op2.impl instanceof CountDown; op2 = op2.nextOp) {
                                ((CountDown) op2.impl).cur++;
                            }
                        }
                        op = null;
                    }
                    if (op == null) {
                        break;
                    } else {
                        op = op.nextOp;
                    }
                } else {
                    this.myNext = objApply;
                    return;
                }
            }
        }
    }

    public final <T> JBIterator<T> map(Function<? super E, ? extends T> function) {
        if (function == null) {
            $$$reportNull$$$0(4);
        }
        return (JBIterator) addOp(true, new MapOp(function));
    }

    public final JBIterator<E> filter(Condition<? super E> condition) {
        if (condition == null) {
            $$$reportNull$$$0(5);
        }
        return (JBIterator) addOp(true, new FilterOp(condition));
    }

    public final <T> JBIterator<T> filterMap(Function<? super E, ? extends T> function) {
        if (function == null) {
            $$$reportNull$$$0(6);
        }
        return (JBIterator) addOp(true, new FilterMapOp(function));
    }

    public final JBIterator<E> take(int i) {
        return (JBIterator) addOp(!(this.myLastOp instanceof NextOp), new WhileOp(new CountDown(i)));
    }

    public final JBIterator<E> takeWhile(Condition<? super E> condition) {
        if (condition == null) {
            $$$reportNull$$$0(7);
        }
        return (JBIterator) addOp(true, new WhileOp(condition));
    }

    public final JBIterator<E> skip(int i) {
        return skipWhile(new CountDown(i));
    }

    public final JBIterator<E> skipWhile(Condition<? super E> condition) {
        if (condition == null) {
            $$$reportNull$$$0(8);
        }
        return (JBIterator) addOp(true, new SkipOp(condition));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public <T> T addOp(boolean z, Op op) {
        if (op == null) {
            $$$reportNull$$$0(9);
        }
        if (op.impl == null) {
            this.myLastOp = op;
            this.myFirstOp = op;
        } else if (z) {
            this.myLastOp.nextOp = op;
            this.myLastOp = this.myLastOp.nextOp;
        } else {
            op.nextOp = this.myFirstOp;
            this.myFirstOp = op;
        }
        return this;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        List<Op> list = operationsImpl().toList();
        return "{cur=" + this.myCurrent + "; next=" + this.myNext + (list.size() < 2 ? "" : "; ops=" + list) + "}";
    }

    private JBIterable<Op> operationsImpl() {
        JBIterable<Op> jBIterableGenerate = JBIterable.generate(this.myFirstOp, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.JBIterator$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((JBIterator.Op) obj).nextOp;
            }
        });
        if (jBIterableGenerate == null) {
            $$$reportNull$$$0(13);
        }
        return jBIterableGenerate;
    }

    static String toShortString(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(14);
        }
        String name2 = obj.getClass().getName();
        int iLastIndexOf = name2.lastIndexOf(36);
        if (iLastIndexOf > 0 && iLastIndexOf < name2.length()) {
            int i = iLastIndexOf + 1;
            if (StringUtil.isJavaIdentifierStart(name2.charAt(i))) {
                String strSubstring = name2.substring(i);
                if (strSubstring == null) {
                    $$$reportNull$$$0(15);
                }
                return strSubstring;
            }
        }
        String strSubstring2 = name2.substring(name2.lastIndexOf(46) + 1);
        if (strSubstring2 == null) {
            $$$reportNull$$$0(16);
        }
        return strSubstring2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Op<T> {
        final T impl;
        Op nextOp;

        Op(T t) {
            this.impl = t;
        }

        Object apply(Object obj) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            Object obj = this.impl;
            if (obj == null) {
                obj = this;
            }
            return JBIterator.toShortString(obj);
        }
    }

    private static class CountDown<A> implements Condition<A> {
        int cur;

        CountDown(int i) {
            this.cur = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public boolean value(A a) {
            int i = this.cur;
            if (i > 0) {
                this.cur = i - 1;
                if (i != 0) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class MapOp<E, T> extends Op<Function<? super E, ? extends T>> {
        MapOp(Function<? super E, ? extends T> function) {
            super(function);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            return ((Function) this.impl).fun(obj);
        }
    }

    private class FilterOp<E> extends Op<Condition<? super E>> {
        FilterOp(Condition<? super E> condition) {
            super(condition);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            return ((Condition) this.impl).value(obj) ? obj : JBIterator.this.skip();
        }
    }

    private class FilterMapOp<E, T> extends Op<Function<? super E, ? extends T>> {
        FilterMapOp(Function<? super E, ? extends T> function) {
            super(function);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            Object objFun = ((Function) this.impl).fun(obj);
            return objFun != null ? objFun : JBIterator.this.skip();
        }
    }

    private class WhileOp<E> extends Op<Condition<? super E>> {
        WhileOp(Condition<? super E> condition) {
            super(condition);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            return ((Condition) this.impl).value(obj) ? obj : JBIterator.this.stop();
        }
    }

    private class SkipOp<E> extends Op<Condition<? super E>> {
        boolean active;

        SkipOp(Condition<? super E> condition) {
            super(condition);
            this.active = true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            if (this.active && ((Condition) this.impl).value(obj)) {
                return JBIterator.this.skip();
            }
            this.active = false;
            return obj;
        }
    }

    private static class NextOp extends Op<Void> {
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            return obj;
        }

        NextOp() {
            super(null);
        }
    }

    private class CursorOp extends Op<Void> {
        boolean advanced;

        CursorOp() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.JBIterator.Op
        Object apply(Object obj) {
            JBIterator jBIterator = (JBIterator) obj;
            boolean z = this.nextOp != null;
            this.advanced = z;
            if (z) {
                if (jBIterator.advance()) {
                    return jBIterator;
                }
            } else if (jBIterator.hasNext()) {
                return jBIterator;
            }
            return JBIterator.this.stop();
        }

        void advance(Object obj) {
            if (this.advanced || !(obj instanceof JBIterator)) {
                return;
            }
            ((JBIterator) obj).advance();
            this.advanced = true;
        }
    }
}
