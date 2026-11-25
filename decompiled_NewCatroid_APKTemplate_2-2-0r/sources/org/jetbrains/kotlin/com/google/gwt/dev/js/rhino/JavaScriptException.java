package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

/* JADX INFO: loaded from: classes3.dex */
public class JavaScriptException extends Exception {
    Object value;

    public JavaScriptException(Object obj) {
        super(obj.toString());
        this.value = obj;
    }
}
