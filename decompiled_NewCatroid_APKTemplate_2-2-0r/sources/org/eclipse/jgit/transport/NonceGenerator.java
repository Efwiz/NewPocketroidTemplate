package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;

/* JADX INFO: loaded from: classes13.dex */
public interface NonceGenerator {
    String createNonce(Repository repository, long j) throws IllegalStateException;

    PushCertificate.NonceStatus verify(String str, String str2, Repository repository, boolean z, int i);
}
