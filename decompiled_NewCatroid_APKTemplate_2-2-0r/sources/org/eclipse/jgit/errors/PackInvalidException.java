package org.eclipse.jgit.errors;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;

/* JADX INFO: loaded from: classes13.dex */
public class PackInvalidException extends IOException {
    private static final long serialVersionUID = 1;

    @Deprecated
    public PackInvalidException(File file) {
        this(file, (Throwable) null);
    }

    public PackInvalidException(File file, Throwable th) {
        this(file.getAbsolutePath(), th);
    }

    @Deprecated
    public PackInvalidException(String str) {
        this(str, (Throwable) null);
    }

    public PackInvalidException(String str, Throwable th) {
        super(MessageFormat.format(JGitText.get().packFileInvalid, str), th);
    }
}
