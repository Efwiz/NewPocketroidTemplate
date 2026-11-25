package org.mozilla.javascript;

import java.io.Closeable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes4.dex */
public class IteratorLikeIterable implements Iterable<Object>, Closeable {
    private boolean closed;
    private final Context cx;
    private final Scriptable iterator;
    private final Callable next;
    private final Callable returnFunc;
    private final Scriptable scope;

    public IteratorLikeIterable(Context context, Scriptable scriptable, Object obj) {
        this.cx = context;
        this.scope = scriptable;
        this.next = ScriptRuntime.getPropFunctionAndThis(obj, ES6Iterator.NEXT_METHOD, context, scriptable);
        this.iterator = ScriptRuntime.lastStoredScriptable(context);
        Object objectPropNoWarn = ScriptRuntime.getObjectPropNoWarn(obj, "return", context, scriptable);
        if (objectPropNoWarn != null && !Undefined.isUndefined(objectPropNoWarn)) {
            if (!(objectPropNoWarn instanceof Callable)) {
                throw ScriptRuntime.notFunctionError(obj, objectPropNoWarn, "return");
            }
            this.returnFunc = (Callable) objectPropNoWarn;
            return;
        }
        this.returnFunc = null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        Callable callable = this.returnFunc;
        if (callable != null) {
            callable.call(this.cx, this.scope, this.iterator, ScriptRuntime.emptyArgs);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<Object> iterator() {
        return new Itr();
    }

    public final class Itr implements Iterator<Object> {
        private boolean isDone;
        private Object nextVal;

        public Itr() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Object objCall = IteratorLikeIterable.this.next.call(IteratorLikeIterable.this.cx, IteratorLikeIterable.this.scope, IteratorLikeIterable.this.iterator, ScriptRuntime.emptyArgs);
            Object property = ScriptableObject.getProperty(ScriptableObject.ensureScriptable(objCall), ES6Iterator.DONE_PROPERTY);
            if (property == Scriptable.NOT_FOUND) {
                property = Undefined.instance;
            }
            if (!ScriptRuntime.toBoolean(property)) {
                this.nextVal = ScriptRuntime.getObjectPropNoWarn(objCall, "value", IteratorLikeIterable.this.cx, IteratorLikeIterable.this.scope);
                return true;
            }
            this.isDone = true;
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.isDone) {
                throw new NoSuchElementException();
            }
            return this.nextVal;
        }
    }
}
