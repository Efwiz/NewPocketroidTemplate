package com.badlogic.gdx.utils.async;

import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/async/AsyncResult.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AsyncResult<T> {
    private final Future<T> future;

    AsyncResult(Future<T> future) {
        this.future = future;
    }

    public boolean isDone() {
        return this.future.isDone();
    }

    public T get() {
        try {
            return this.future.get();
        } catch (InterruptedException unused) {
            return null;
        } catch (ExecutionException e) {
            throw new GdxRuntimeException(e.getCause());
        }
    }
}
