package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
public final class Double2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    public static class EmptyFunction<V> extends AbstractDouble2ObjectFunction<V> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
        public boolean containsKey(double d) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDouble2ObjectFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
        public V defaultReturnValue() {
            return null;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
        public V get(double d) {
            return null;
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
            return Double2ObjectFunctions.EMPTY_FUNCTION;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Function) && ((Function) obj).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return Double2ObjectFunctions.EMPTY_FUNCTION;
        }
    }
}
