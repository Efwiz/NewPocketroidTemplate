package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/* JADX INFO: loaded from: classes3.dex */
public interface Resource {
    InputStream getInputStream() throws IOException;

    URL getURL();
}
