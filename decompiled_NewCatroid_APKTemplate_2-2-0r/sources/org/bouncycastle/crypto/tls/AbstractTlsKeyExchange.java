package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/* JADX INFO: loaded from: classes13.dex */
public abstract class AbstractTlsKeyExchange implements TlsKeyExchange {
    protected TlsContext context;
    protected int keyExchange;
    protected Vector supportedSignatureAlgorithms;

    protected AbstractTlsKeyExchange(int i, Vector vector) {
        this.keyExchange = i;
        this.supportedSignatureAlgorithms = vector;
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public byte[] generateServerKeyExchange() throws IOException {
        if (requiresServerKeyExchange()) {
            throw new TlsFatalAlert((short) 80);
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:21:0x0035  */
    /* JADX WARN: Code duplicated, block: B:22:0x003a  */
    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void init(TlsContext tlsContext) {
        Vector defaultRSASignatureAlgorithms;
        this.context = tlsContext;
        ProtocolVersion clientVersion = tlsContext.getClientVersion();
        if (!TlsUtils.isSignatureAlgorithmsExtensionAllowed(clientVersion)) {
            if (this.supportedSignatureAlgorithms != null) {
                throw new IllegalStateException("supported_signature_algorithms not allowed for " + clientVersion);
            }
            return;
        }
        if (this.supportedSignatureAlgorithms == null) {
            int i = this.keyExchange;
            if (i == 1) {
                defaultRSASignatureAlgorithms = TlsUtils.getDefaultRSASignatureAlgorithms();
            } else if (i == 3) {
                defaultRSASignatureAlgorithms = TlsUtils.getDefaultDSSSignatureAlgorithms();
            } else if (i == 5) {
                defaultRSASignatureAlgorithms = TlsUtils.getDefaultRSASignatureAlgorithms();
            } else if (i == 7) {
                defaultRSASignatureAlgorithms = TlsUtils.getDefaultDSSSignatureAlgorithms();
            } else if (i != 9) {
                switch (i) {
                    case 13:
                    case 14:
                        return;
                    case 15:
                    case 18:
                    case 19:
                        defaultRSASignatureAlgorithms = TlsUtils.getDefaultRSASignatureAlgorithms();
                        break;
                    case 16:
                    case 17:
                        defaultRSASignatureAlgorithms = TlsUtils.getDefaultECDSASignatureAlgorithms();
                        break;
                    default:
                        switch (i) {
                            case 21:
                            case 24:
                                return;
                            case 22:
                                defaultRSASignatureAlgorithms = TlsUtils.getDefaultDSSSignatureAlgorithms();
                                break;
                            case 23:
                                defaultRSASignatureAlgorithms = TlsUtils.getDefaultRSASignatureAlgorithms();
                                break;
                            default:
                                throw new IllegalStateException("unsupported key exchange algorithm");
                        }
                        break;
                }
            } else {
                defaultRSASignatureAlgorithms = TlsUtils.getDefaultRSASignatureAlgorithms();
            }
            this.supportedSignatureAlgorithms = defaultRSASignatureAlgorithms;
        }
    }

    protected DigitallySigned parseSignature(InputStream inputStream) throws IOException {
        DigitallySigned digitallySigned = DigitallySigned.parse(this.context, inputStream);
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm != null) {
            TlsUtils.verifySupportedSignatureAlgorithm(this.supportedSignatureAlgorithms, algorithm);
        }
        return digitallySigned;
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processClientCertificate(Certificate certificate) throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) throws IOException {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerCredentials(TlsCredentials tlsCredentials) throws IOException {
        processServerCertificate(tlsCredentials.getCertificate());
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        if (!requiresServerKeyExchange()) {
            throw new TlsFatalAlert((short) 10);
        }
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        return false;
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipClientCredentials() throws IOException {
    }

    @Override // org.bouncycastle.crypto.tls.TlsKeyExchange
    public void skipServerKeyExchange() throws IOException {
        if (requiresServerKeyExchange()) {
            throw new TlsFatalAlert((short) 10);
        }
    }
}
