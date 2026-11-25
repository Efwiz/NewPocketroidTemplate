package antlr;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class CharStreamIOException extends CharStreamException {

    /* JADX INFO: renamed from: io, reason: collision with root package name */
    public IOException f54io;

    public CharStreamIOException(IOException iOException) {
        super(iOException.getMessage());
        this.f54io = iOException;
    }
}
