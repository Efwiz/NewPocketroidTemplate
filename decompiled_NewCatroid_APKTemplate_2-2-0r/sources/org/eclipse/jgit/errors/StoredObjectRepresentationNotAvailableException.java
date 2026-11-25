package org.eclipse.jgit.errors;

import org.eclipse.jgit.internal.storage.pack.ObjectToPack;

/* JADX INFO: loaded from: classes13.dex */
public class StoredObjectRepresentationNotAvailableException extends Exception {
    private static final long serialVersionUID = 1;

    public StoredObjectRepresentationNotAvailableException(ObjectToPack objectToPack, Throwable th) {
        super(th);
    }
}
