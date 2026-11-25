package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
public final class Object2ShortFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    public static class EmptyFunction<K> extends AbstractObject2ShortFunction<K> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
        public boolean containsKey(Object obj) {
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObject2ShortFunction, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunction
        public short defaultReturnValue() {
            return (short) 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunction
        public short getShort(Object obj) {
            return (short) 0;
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
            return Object2ShortFunctions.EMPTY_FUNCTION;
        }

        public boolean equals(Object obj) {
            return (obj instanceof Function) && ((Function) obj).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return Object2ShortFunctions.EMPTY_FUNCTION;
        }
    }
}
