package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.revwalk.RevObject;

/* JADX INFO: loaded from: classes13.dex */
public interface VerificationResult {
    Throwable getException();

    RevObject getObject();

    GpgSignatureVerifier.SignatureVerification getVerification();
}
