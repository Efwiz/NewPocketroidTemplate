package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
public final class Object2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    public static class EmptyFunction<K, V> extends AbstractObject2ObjectFunction<K, V> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public boolean containsKey(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public V get(Object obj) {
            return null;
        }

        public V getOrDefault(Object obj, V v) {
            return v;
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
            return Object2ObjectFunctions.EMPTY_FUNCTION;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Function) && ((Function) obj).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return Object2ObjectFunctions.EMPTY_FUNCTION;
        }
    }
}
