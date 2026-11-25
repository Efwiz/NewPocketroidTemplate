package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import org.antlr.stringtemplate.StringTemplate;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes13.dex */
public class Daemon {
    private static final int BACKLOG = 5;
    public static final int DEFAULT_PORT = 9418;
    private Acceptor acceptThread;
    private InetSocketAddress myAddress;
    private PackConfig packConfig;
    private final ThreadGroup processors;
    volatile ReceivePackFactory<DaemonClient> receivePackFactory;
    private volatile RepositoryResolver<DaemonClient> repositoryResolver;
    private final DaemonService[] services;
    private int timeout;
    volatile UploadPackFactory<DaemonClient> uploadPackFactory;

    public Daemon() {
        this(null);
    }

    public Daemon(InetSocketAddress inetSocketAddress) {
        this.myAddress = inetSocketAddress;
        this.processors = new ThreadGroup("Git-Daemon");
        this.repositoryResolver = RepositoryResolver.NONE;
        this.uploadPackFactory = new UploadPackFactory() { // from class: org.eclipse.jgit.transport.Daemon$$ExternalSyntheticLambda0
            @Override // org.eclipse.jgit.transport.resolver.UploadPackFactory
            public final UploadPack create(Object obj, Repository repository) {
                return this.f$0.m6046lambda$0$orgeclipsejgittransportDaemon((DaemonClient) obj, repository);
            }
        };
        this.receivePackFactory = new ReceivePackFactory() { // from class: org.eclipse.jgit.transport.Daemon$$ExternalSyntheticLambda1
            @Override // org.eclipse.jgit.transport.resolver.ReceivePackFactory
            public final ReceivePack create(Object obj, Repository repository) {
                return this.f$0.m6047lambda$1$orgeclipsejgittransportDaemon((DaemonClient) obj, repository);
            }
        };
        this.services = new DaemonService[]{new DaemonService("upload-pack", "uploadpack") { // from class: org.eclipse.jgit.transport.Daemon.1
            {
                setEnabled(true);
            }

            @Override // org.eclipse.jgit.transport.DaemonService
            protected void execute(DaemonClient daemonClient, Repository repository, @Nullable Collection<String> collection) throws ServiceNotEnabledException, IOException, ServiceNotAuthorizedException {
                UploadPack uploadPackCreate = Daemon.this.uploadPackFactory.create(daemonClient, repository);
                InputStream inputStream = daemonClient.getInputStream();
                OutputStream outputStream = daemonClient.getOutputStream();
                if (collection != null) {
                    uploadPackCreate.setExtraParameters(collection);
                }
                uploadPackCreate.upload(inputStream, outputStream, null);
            }
        }, new DaemonService("receive-pack", "receivepack") { // from class: org.eclipse.jgit.transport.Daemon.2
            {
                setEnabled(false);
            }

            @Override // org.eclipse.jgit.transport.DaemonService
            protected void execute(DaemonClient daemonClient, Repository repository, @Nullable Collection<String> collection) throws ServiceNotEnabledException, IOException, ServiceNotAuthorizedException {
                Daemon.this.receivePackFactory.create(daemonClient, repository).receive(daemonClient.getInputStream(), daemonClient.getOutputStream(), null);
            }
        }};
    }

    /* JADX INFO: renamed from: lambda$0$org-eclipse-jgit-transport-Daemon, reason: not valid java name */
    /* synthetic */ UploadPack m6046lambda$0$orgeclipsejgittransportDaemon(DaemonClient daemonClient, Repository repository) throws ServiceNotEnabledException, ServiceNotAuthorizedException {
        UploadPack uploadPack = new UploadPack(repository);
        uploadPack.setTimeout(getTimeout());
        uploadPack.setPackConfig(getPackConfig());
        return uploadPack;
    }

    /* JADX INFO: renamed from: lambda$1$org-eclipse-jgit-transport-Daemon, reason: not valid java name */
    /* synthetic */ ReceivePack m6047lambda$1$orgeclipsejgittransportDaemon(DaemonClient daemonClient, Repository repository) throws ServiceNotEnabledException, ServiceNotAuthorizedException {
        ReceivePack receivePack = new ReceivePack(repository);
        InetAddress remoteAddress = daemonClient.getRemoteAddress();
        String canonicalHostName = remoteAddress.getCanonicalHostName();
        if (canonicalHostName == null) {
            canonicalHostName = remoteAddress.getHostAddress();
        }
        receivePack.setRefLogIdent(new PersonIdent(StringTemplate.ANONYMOUS_ST_NAME, String.valueOf(StringTemplate.ANONYMOUS_ST_NAME) + PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + canonicalHostName));
        receivePack.setTimeout(getTimeout());
        return receivePack;
    }

    public synchronized InetSocketAddress getAddress() {
        return this.myAddress;
    }

    public synchronized DaemonService getService(String str) {
        if (!str.startsWith("git-")) {
            str = "git-" + str;
        }
        for (DaemonService daemonService : this.services) {
            if (daemonService.getCommandName().equals(str)) {
                return daemonService;
            }
        }
        return null;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) {
        this.timeout = i;
    }

    public PackConfig getPackConfig() {
        return this.packConfig;
    }

    public void setPackConfig(PackConfig packConfig) {
        this.packConfig = packConfig;
    }

    public void setRepositoryResolver(RepositoryResolver<DaemonClient> repositoryResolver) {
        this.repositoryResolver = repositoryResolver;
    }

    public void setUploadPackFactory(UploadPackFactory<DaemonClient> uploadPackFactory) {
        if (uploadPackFactory != null) {
            this.uploadPackFactory = uploadPackFactory;
        } else {
            this.uploadPackFactory = UploadPackFactory.DISABLED;
        }
    }

    public ReceivePackFactory<DaemonClient> getReceivePackFactory() {
        return this.receivePackFactory;
    }

    public void setReceivePackFactory(ReceivePackFactory<DaemonClient> receivePackFactory) {
        if (receivePackFactory != null) {
            this.receivePackFactory = receivePackFactory;
        } else {
            this.receivePackFactory = ReceivePackFactory.DISABLED;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Acceptor extends Thread {
        private final ServerSocket listenSocket;
        private final AtomicBoolean running;

        public Acceptor(ThreadGroup threadGroup, String str, ServerSocket serverSocket) {
            super(threadGroup, str);
            this.running = new AtomicBoolean(true);
            this.listenSocket = serverSocket;
        }

        /* JADX INFO: renamed from: lambda$0$org-eclipse-jgit-transport-Daemon$Acceptor, reason: not valid java name */
        /* synthetic */ void m6048lambda$0$orgeclipsejgittransportDaemon$Acceptor(Thread thread, Throwable th) {
            terminate();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: org.eclipse.jgit.transport.Daemon$Acceptor$$ExternalSyntheticLambda0
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public final void uncaughtException(Thread thread, Throwable th) {
                    this.f$0.m6048lambda$0$orgeclipsejgittransportDaemon$Acceptor(thread, th);
                }
            });
            while (isRunning()) {
                try {
                    Daemon.this.startClient(this.listenSocket.accept());
                } catch (SocketException unused) {
                } catch (IOException unused2) {
                }
            }
            terminate();
        }

        private void terminate() {
            try {
                shutDown();
            } finally {
                Daemon.this.clearThread();
            }
        }

        public boolean isRunning() {
            return this.running.get();
        }

        public void shutDown() {
            this.running.set(false);
            try {
                this.listenSocket.close();
            } catch (IOException unused) {
            }
        }
    }

    public synchronized void start() throws IOException {
        if (this.acceptThread != null) {
            throw new IllegalStateException(JGitText.get().daemonAlreadyRunning);
        }
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        InetSocketAddress inetSocketAddress = this.myAddress;
        if (inetSocketAddress != null) {
            serverSocket.bind(inetSocketAddress, 5);
        } else {
            serverSocket.bind(new InetSocketAddress((InetAddress) null, 0), 5);
        }
        this.myAddress = (InetSocketAddress) serverSocket.getLocalSocketAddress();
        Acceptor acceptor = new Acceptor(this.processors, "Git-Daemon-Accept", serverSocket);
        this.acceptThread = acceptor;
        acceptor.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void clearThread() {
        this.acceptThread = null;
    }

    public synchronized boolean isRunning() {
        Acceptor acceptor = this.acceptThread;
        return acceptor != null && acceptor.isRunning();
    }

    public synchronized void stop() {
        Acceptor acceptor = this.acceptThread;
        if (acceptor != null) {
            acceptor.shutDown();
        }
    }

    public void stopAndWait() throws InterruptedException {
        Acceptor acceptor;
        synchronized (this) {
            acceptor = this.acceptThread;
            stop();
        }
        if (acceptor != null) {
            acceptor.join();
        }
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [org.eclipse.jgit.transport.Daemon$3] */
    void startClient(final Socket socket) {
        final DaemonClient daemonClient = new DaemonClient(this);
        SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
        if (remoteSocketAddress instanceof InetSocketAddress) {
            daemonClient.setRemoteAddress(((InetSocketAddress) remoteSocketAddress).getAddress());
        }
        new Thread(this.processors, "Git-Daemon-Client " + remoteSocketAddress.toString()) { // from class: org.eclipse.jgit.transport.Daemon.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Socket socket2;
                try {
                    try {
                        try {
                            try {
                                try {
                                    daemonClient.execute(socket);
                                    try {
                                        socket.getInputStream().close();
                                    } catch (IOException unused) {
                                    }
                                    socket2 = socket;
                                } catch (IOException unused2) {
                                    socket2 = socket;
                                }
                            } catch (IOException unused3) {
                                socket2 = socket;
                            }
                        } catch (IOException unused4) {
                            return;
                        }
                    } catch (IOException unused5) {
                        socket2 = socket;
                    }
                } catch (IOException unused6) {
                    socket.getInputStream().close();
                    socket2 = socket;
                    socket2.getOutputStream().close();
                } catch (ServiceNotAuthorizedException unused7) {
                    socket.getInputStream().close();
                    socket2 = socket;
                    socket2.getOutputStream().close();
                } catch (ServiceNotEnabledException unused8) {
                    socket.getInputStream().close();
                    socket2 = socket;
                    socket2.getOutputStream().close();
                } catch (Throwable th) {
                    try {
                        socket.getInputStream().close();
                    } catch (IOException unused9) {
                    }
                    try {
                        socket.getOutputStream().close();
                        throw th;
                    } catch (IOException unused10) {
                        throw th;
                    }
                }
                socket2.getOutputStream().close();
            }
        }.start();
    }

    synchronized DaemonService matchService(String str) {
        for (DaemonService daemonService : this.services) {
            if (daemonService.handles(str)) {
                return daemonService;
            }
        }
        return null;
    }

    Repository openRepository(DaemonClient daemonClient, String str) throws ServiceMayNotContinueException {
        String strReplace = str.replace('\\', '/');
        if (!strReplace.startsWith("/")) {
            return null;
        }
        try {
            return this.repositoryResolver.open(daemonClient, strReplace.substring(1));
        } catch (RepositoryNotFoundException | ServiceNotAuthorizedException | ServiceNotEnabledException unused) {
            return null;
        }
    }
}
