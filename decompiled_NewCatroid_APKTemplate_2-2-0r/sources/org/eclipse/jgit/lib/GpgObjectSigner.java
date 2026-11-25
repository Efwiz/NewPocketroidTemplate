package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.UnsupportedSigningFormatException;
import org.eclipse.jgit.transport.CredentialsProvider;

/* JADX INFO: loaded from: classes13.dex */
public interface GpgObjectSigner {
    boolean canLocateSigningKey(@Nullable String str, PersonIdent personIdent, CredentialsProvider credentialsProvider, GpgConfig gpgConfig) throws CanceledException, UnsupportedSigningFormatException;

    void signObject(ObjectBuilder objectBuilder, @Nullable String str, PersonIdent personIdent, CredentialsProvider credentialsProvider, GpgConfig gpgConfig) throws CanceledException, UnsupportedSigningFormatException;
}
