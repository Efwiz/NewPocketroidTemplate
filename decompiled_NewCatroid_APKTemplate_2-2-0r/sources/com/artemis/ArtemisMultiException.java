package com.artemis;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes8.dex */
public class ArtemisMultiException extends RuntimeException {
    private final List<Throwable> exceptions;

    public ArtemisMultiException() {
        this.exceptions = new ArrayList();
    }

    public ArtemisMultiException(List<Throwable> list) {
        ArrayList arrayList = new ArrayList();
        this.exceptions = arrayList;
        arrayList.addAll(list);
    }

    public List<Throwable> getExceptions() {
        return this.exceptions;
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Throwable th : this.exceptions) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(th.getMessage());
        }
        return sb.toString();
    }
}
