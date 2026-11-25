package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
public final class Int2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    public static class EmptyFunction extends AbstractInt2IntFunction implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
        public boolean containsKey(int i) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractInt2IntFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
        public int defaultReturnValue() {
            return 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2IntFunction
        public int get(int i) {
            return 0;
        }

        public int hashCode() {
            return 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public int size() {
            return 0;
        }

        protected EmptyFunction() {
        }

        public Object clone() {
            return Int2IntFunctions.EMPTY_FUNCTION;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Function) && ((Function) obj).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return Int2IntFunctions.EMPTY_FUNCTION;
        }
    }
}
