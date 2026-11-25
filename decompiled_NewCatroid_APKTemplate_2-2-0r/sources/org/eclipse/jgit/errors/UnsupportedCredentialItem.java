package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

/* JADX INFO: loaded from: classes13.dex */
public class UnsupportedCredentialItem extends RuntimeException {
    private static final long serialVersionUID = 1;

    public UnsupportedCredentialItem(URIish uRIish, String str) {
        super(uRIish.setPass(null) + ": " + str);
    }
}
