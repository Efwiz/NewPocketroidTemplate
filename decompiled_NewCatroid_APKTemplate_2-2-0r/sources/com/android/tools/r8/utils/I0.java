package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.PathOrigin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class I0 {
    private final Path a;

    public I0(Path path) {
        this.a = path;
        new PathOrigin(path);
    }

    public final void a(String str, DiagnosticsHandler diagnosticsHandler) {
        Path pathResolve = this.a.resolve(str.replace('/', File.separatorChar));
        try {
            Files.createDirectories(pathResolve, new FileAttribute[0]);
        } catch (IOException e) {
            diagnosticsHandler.error(new ExceptionDiagnostic(e, new PathOrigin(pathResolve)));
        }
    }

    public final void a(String str, DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
        try {
            InputStream byteStream = dataEntryResource.getByteStream();
            try {
                a(ByteDataView.of(V5.a(byteStream)), str, diagnosticsHandler);
                byteStream.close();
            } catch (Throwable th) {
                if (byteStream != null) {
                    try {
                        byteStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (ResourceException e) {
            diagnosticsHandler.error(new StringDiagnostic("Failed to open input: " + e.getMessage(), dataEntryResource.getOrigin()));
        } catch (IOException e2) {
            diagnosticsHandler.error(new ExceptionDiagnostic(e2, dataEntryResource.getOrigin()));
        }
    }

    public final synchronized void a(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
        Path pathResolve = this.a.resolve(str.replace('/', File.separatorChar));
        try {
            Files.createDirectories(pathResolve.getParent(), new FileAttribute[0]);
            X0.a(pathResolve, (OutputStream) null, byteDataView);
        } catch (IOException e) {
            diagnosticsHandler.error(new ExceptionDiagnostic(e, new PathOrigin(pathResolve)));
        }
    }

    public final Path a() {
        return this.a;
    }
}
