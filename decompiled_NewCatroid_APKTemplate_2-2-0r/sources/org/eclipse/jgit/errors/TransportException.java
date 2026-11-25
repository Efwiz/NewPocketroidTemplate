package org.eclipse.jgit.errors;

import java.io.IOException;
import org.eclipse.jgit.transport.URIish;

/* JADX INFO: loaded from: classes13.dex */
public class TransportException extends IOException {
    private static final long serialVersionUID = 1;

    public TransportException(URIish uRIish, String str) {
        super(uRIish.setPass(null) + ": " + str);
    }

    public TransportException(URIish uRIish, String str, Throwable th) {
        this(uRIish.setPass(null) + ": " + str, th);
    }

    public TransportException(String str) {
        super(str);
    }

    public TransportException(String str, Throwable th) {
        super(str);
        initCause(th);
    }
}
