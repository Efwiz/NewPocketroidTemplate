package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public interface Condition<T> {

    @Deprecated
    public static final Condition<Object> NOT_NULL = new Condition<Object>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Condition.1
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public boolean value(Object obj) {
            return obj != null;
        }

        public String toString() {
            return "Condition.NOT_NULL";
        }
    };

    @Deprecated
    public static final Condition<Object> TRUE = new Condition<Object>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Condition.2
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public boolean value(Object obj) {
            return true;
        }

        public String toString() {
            return "Condition.TRUE";
        }
    };

    @Deprecated
    public static final Condition<Object> FALSE = new Condition<Object>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Condition.3
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public boolean value(Object obj) {
            return false;
        }

        public String toString() {
            return "Condition.FALSE";
        }
    };

    boolean value(T t);
}
