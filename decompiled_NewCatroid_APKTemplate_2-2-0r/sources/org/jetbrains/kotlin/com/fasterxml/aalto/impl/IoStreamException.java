package org.jetbrains.kotlin.com.fasterxml.aalto.impl;

import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public class IoStreamException extends StreamExceptionBase {
    public IoStreamException(IOException iOException) {
        super(iOException);
    }

    public IoStreamException(String str) {
        super(str);
    }
}
