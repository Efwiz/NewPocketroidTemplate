package net.mgsx.gltf.loaders.exceptions;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8571720960735308661L;

    public GLTFRuntimeException(String str) {
        super(str);
    }

    public GLTFRuntimeException(Throwable th) {
        super(th);
    }

    public GLTFRuntimeException(String str, Throwable th) {
        super(str, th);
    }
}
