package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

/* JADX INFO: loaded from: classes13.dex */
public class PackProtocolException extends TransportException {
    private static final long serialVersionUID = 1;

    public PackProtocolException(URIish uRIish, String str) {
        super(uRIish + ": " + str);
    }

    public PackProtocolException(URIish uRIish, String str, Throwable th) {
        this(uRIish + ": " + str, th);
    }

    public PackProtocolException(String str) {
        super(str);
    }

    public PackProtocolException(String str, Throwable th) {
        super(str);
        initCause(th);
    }
}
