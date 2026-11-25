package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

/* JADX INFO: loaded from: classes13.dex */
class InternalFetchConnection<C> extends BasePackFetchConnection {
    private Thread worker;

    public InternalFetchConnection(PackTransport packTransport, final UploadPackFactory<C> uploadPackFactory, final C c, final Repository repository) throws TransportException {
        super(packTransport);
        try {
            PipedInputStream pipedInputStream = new PipedInputStream();
            final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
            final PipedInputStream pipedInputStream2 = new PipedInputStream() { // from class: org.eclipse.jgit.transport.InternalFetchConnection.1
                {
                    this.buffer = new byte[2952];
                }
            };
            PipedOutputStream pipedOutputStream2 = new PipedOutputStream(pipedInputStream2);
            Thread thread = new Thread("JGit-Upload-Pack") { // from class: org.eclipse.jgit.transport.InternalFetchConnection.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    PipedOutputStream pipedOutputStream3;
                    try {
                        try {
                            try {
                                try {
                                    uploadPackFactory.create(c, repository).upload(pipedInputStream2, pipedOutputStream, null);
                                    try {
                                        pipedInputStream2.close();
                                    } catch (IOException unused) {
                                    }
                                    pipedOutputStream3 = pipedOutputStream;
                                } catch (IOException unused2) {
                                }
                            } catch (IOException unused3) {
                                pipedOutputStream3 = pipedOutputStream;
                            }
                        } catch (IOException e) {
                            e = e;
                            e.printStackTrace();
                            try {
                                pipedInputStream2.close();
                            } catch (IOException unused4) {
                            }
                            pipedOutputStream3 = pipedOutputStream;
                        } catch (RuntimeException e2) {
                            e = e2;
                            e.printStackTrace();
                            pipedInputStream2.close();
                            pipedOutputStream3 = pipedOutputStream;
                        } catch (ServiceNotAuthorizedException | ServiceNotEnabledException unused5) {
                            pipedInputStream2.close();
                            pipedOutputStream3 = pipedOutputStream;
                            pipedOutputStream3.close();
                        }
                        pipedOutputStream3.close();
                    } finally {
                        try {
                            pipedInputStream2.close();
                        } catch (IOException unused6) {
                        }
                        try {
                            pipedOutputStream.close();
                        } catch (IOException unused7) {
                        }
                        repository.close();
                    }
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

    @Override // org.eclipse.jgit.transport.BasePackFetchConnection, org.eclipse.jgit.transport.BasePackConnection, org.eclipse.jgit.transport.BaseConnection, org.eclipse.jgit.transport.Connection, java.lang.AutoCloseable
    public void close() {
        super.close();
        try {
            Thread thread = this.worker;
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException unused) {
        } finally {
            this.worker = null;
        }
    }
}
