package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UncheckedIOException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

/* JADX INFO: loaded from: classes13.dex */
class InternalPushConnection<C> extends BasePackPushConnection {
    private Thread worker;

    public InternalPushConnection(PackTransport packTransport, final ReceivePackFactory<C> receivePackFactory, final C c, final Repository repository) throws TransportException {
        super(packTransport);
        try {
            PipedInputStream pipedInputStream = new PipedInputStream();
            final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
            final PipedInputStream pipedInputStream2 = new PipedInputStream();
            PipedOutputStream pipedOutputStream2 = new PipedOutputStream(pipedInputStream2);
            Thread thread = new Thread("JGit-Receive-Pack") { // from class: org.eclipse.jgit.transport.InternalPushConnection.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    PipedOutputStream pipedOutputStream3;
                    try {
                        try {
                            try {
                                try {
                                    receivePackFactory.create(c, repository).receive(pipedInputStream2, pipedOutputStream, System.err);
                                    try {
                                        pipedInputStream2.close();
                                    } catch (IOException unused) {
                                    }
                                    pipedOutputStream3 = pipedOutputStream;
                                } catch (IOException unused2) {
                                    pipedOutputStream3 = pipedOutputStream;
                                }
                            } catch (Throwable th) {
                                try {
                                    pipedInputStream2.close();
                                } catch (IOException unused3) {
                                }
                                try {
                                    pipedOutputStream.close();
                                } catch (IOException unused4) {
                                }
                                repository.close();
                                throw th;
                            }
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        } catch (ServiceNotAuthorizedException | ServiceNotEnabledException unused5) {
                            pipedInputStream2.close();
                            pipedOutputStream3 = pipedOutputStream;
                            pipedOutputStream3.close();
                            repository.close();
                        }
                        pipedOutputStream3.close();
                    } catch (IOException unused6) {
                    }
                    repository.close();
                }
            };
            this.worker = thread;
            thread.start();
            init(pipedInputStream, pipedOutputStream2);
            readAdvertisedRefs();
        } catch (IOException e) {
            repository.close();
            throw new TransportException(this.uri, JGitText.get().cannotConnectPipes, e);
        }
    }

    @Override // org.eclipse.jgit.transport.BasePackPushConnection, org.eclipse.jgit.transport.BasePackConnection, org.eclipse.jgit.transport.BaseConnection, org.eclipse.jgit.transport.Connection, java.lang.AutoCloseable
    public void close() {
        super.close();
        Thread thread = this.worker;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException unused) {
            } finally {
                this.worker = null;
            }
        }
    }
}
