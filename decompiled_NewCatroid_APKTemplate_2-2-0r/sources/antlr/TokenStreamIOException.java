package antlr;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class TokenStreamIOException extends TokenStreamException {

    /* JADX INFO: renamed from: io, reason: collision with root package name */
    public IOException f57io;

    public TokenStreamIOException(IOException iOException) {
        super(iOException.getMessage());
        this.f57io = iOException;
    }
}
