package org.eclipse.jgit.transport;

import com.android.dex.DexFormat;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

/* JADX INFO: loaded from: classes13.dex */
public class DaemonClient {
    private final Daemon daemon;
    private InetAddress peer;
    private InputStream rawIn;
    private OutputStream rawOut;

    DaemonClient(Daemon daemon) {
        this.daemon = daemon;
    }

    void setRemoteAddress(InetAddress inetAddress) {
        this.peer = inetAddress;
    }

    public Daemon getDaemon() {
        return this.daemon;
    }

    public InetAddress getRemoteAddress() {
        return this.peer;
    }

    public InputStream getInputStream() {
        return this.rawIn;
    }

    public OutputStream getOutputStream() {
        return this.rawOut;
    }

    void execute(Socket socket) throws ServiceNotEnabledException, IOException, ServiceNotAuthorizedException {
        this.rawIn = new BufferedInputStream(socket.getInputStream());
        this.rawOut = new BufferedOutputStream(socket.getOutputStream());
        if (this.daemon.getTimeout() > 0) {
            socket.setSoTimeout(this.daemon.getTimeout() * 1000);
        }
        String stringRaw = new PacketLineIn(this.rawIn).readStringRaw();
        int iIndexOf = stringRaw.indexOf(TarConstants.VERSION_ANT);
        List listAsList = iIndexOf != -1 ? Arrays.asList(stringRaw.substring(iIndexOf + 2).split(DexFormat.MAGIC_SUFFIX)) : null;
        int iIndexOf2 = stringRaw.indexOf(0);
        if (iIndexOf2 >= 0) {
            stringRaw = stringRaw.substring(0, iIndexOf2);
        }
        DaemonService daemonServiceMatchService = getDaemon().matchService(stringRaw);
        if (daemonServiceMatchService == null) {
            return;
        }
        socket.setSoTimeout(0);
        daemonServiceMatchService.execute(this, stringRaw, listAsList);
    }
}
