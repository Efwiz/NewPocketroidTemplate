package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.AbstractC1660b2;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class A {
    static final /* synthetic */ boolean i = true;
    private final Path a;
    private final PathOrigin b;
    private ZipOutputStream c = null;
    private boolean d = false;
    private int e = 0;
    private int f = 0;
    private HashMap g = new HashMap();
    private TreeSet h = new TreeSet();

    public A(Path path) {
        this.a = path;
        this.b = new PathOrigin(path);
    }

    private void b(String str, DiagnosticsHandler diagnosticsHandler) {
        if (str.charAt(str.length() - 1) != '/') {
            str = str + "/";
        }
        ZipEntry zipEntry = new ZipEntry(str);
        zipEntry.setTime(0L);
        synchronized (this) {
            try {
                ZipOutputStream zipOutputStreamC = c();
                zipOutputStreamC.putNextEntry(zipEntry);
                zipOutputStreamC.closeEntry();
            } catch (IOException e) {
                ExceptionDiagnostic exceptionDiagnostic = new ExceptionDiagnostic(e, this.b);
                if ((e instanceof ZipException) && e.getMessage().startsWith("duplicate entry")) {
                    diagnosticsHandler.warning(exceptionDiagnostic);
                } else {
                    diagnosticsHandler.error(exceptionDiagnostic);
                }
            }
        }
    }

    private synchronized ZipOutputStream c() {
        ZipOutputStream zipOutputStream;
        if (!i && this.d) {
            throw new AssertionError();
        }
        zipOutputStream = this.c;
        if (zipOutputStream == null) {
            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(this.a, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)));
            this.c = zipOutputStream;
        }
        return zipOutputStream;
    }

    public final synchronized void a(DiagnosticsHandler diagnosticsHandler) {
        boolean z = i;
        if (!z && this.d) {
            throw new AssertionError();
        }
        int i2 = this.e - 1;
        this.e = i2;
        if (i2 == 0) {
            if (!z && !this.g.isEmpty()) {
                throw new AssertionError();
            }
            Iterator it = this.h.iterator();
            while (true) {
                int i3 = 0;
                if (!it.hasNext()) {
                    this.d = true;
                    try {
                        ZipOutputStream zipOutputStream = this.c;
                        if (zipOutputStream == null) {
                            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(this.a, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)));
                            this.c = zipOutputStream;
                        }
                        zipOutputStream.close();
                        this.c = null;
                        break;
                    } catch (IOException e) {
                        diagnosticsHandler.error(new ExceptionDiagnostic(e, this.b));
                        break;
                    }
                }
                C3994z c3994z = (C3994z) it.next();
                if (c3994z.c) {
                    if (!i && c3994z.b != null) {
                        throw new AssertionError();
                    }
                    b(c3994z.a, diagnosticsHandler);
                } else {
                    if (!i && c3994z.b == null) {
                        throw new AssertionError();
                    }
                    String str = c3994z.a;
                    ByteDataView byteDataView = c3994z.b;
                    try {
                        ZipOutputStream zipOutputStreamC = c();
                        int i4 = AbstractC1660b2.c;
                        if (!"resources/new_api_database.ser".equals(str)) {
                            i3 = 8;
                        }
                        int i5 = P3.b;
                        byte[] buffer = byteDataView.getBuffer();
                        int offset = byteDataView.getOffset();
                        int length = byteDataView.getLength();
                        CRC32 crc32 = new CRC32();
                        crc32.update(buffer, offset, length);
                        ZipEntry zipEntry = new ZipEntry(str);
                        zipEntry.setMethod(i3);
                        zipEntry.setSize(length);
                        zipEntry.setCrc(crc32.getValue());
                        zipEntry.setTime(0L);
                        zipOutputStreamC.putNextEntry(zipEntry);
                        zipOutputStreamC.write(buffer, offset, length);
                        zipOutputStreamC.closeEntry();
                    } catch (IOException e2) {
                        ExceptionDiagnostic exceptionDiagnostic = new ExceptionDiagnostic(e2, this.b);
                        if ((e2 instanceof ZipException) && e2.getMessage().startsWith("duplicate entry")) {
                            diagnosticsHandler.warning(exceptionDiagnostic);
                        } else {
                            diagnosticsHandler.error(exceptionDiagnostic);
                        }
                    }
                }
                throw th;
            }
        }
    }

    public final synchronized void d() {
        if (!i && this.d) {
            throw new AssertionError();
        }
        this.e++;
    }

    public final Path b() {
        return this.a;
    }

    public final synchronized void a(String str, DiagnosticsHandler diagnosticsHandler) {
        this.h.add(new C3994z(str, null, true));
    }

    public final void a(String str, DataEntryResource dataEntryResource, DiagnosticsHandler diagnosticsHandler) {
        try {
            InputStream byteStream = dataEntryResource.getByteStream();
            try {
                ByteDataView byteDataViewOf = ByteDataView.of(V5.a(byteStream));
                synchronized (this) {
                    try {
                        if (AbstractC1660b2.a(str)) {
                            try {
                                ZipOutputStream zipOutputStreamC = c();
                                int i2 = "resources/new_api_database.ser".equals(str) ? 0 : 8;
                                int i3 = P3.b;
                                byte[] buffer = byteDataViewOf.getBuffer();
                                int offset = byteDataViewOf.getOffset();
                                int length = byteDataViewOf.getLength();
                                CRC32 crc32 = new CRC32();
                                crc32.update(buffer, offset, length);
                                ZipEntry zipEntry = new ZipEntry(str);
                                zipEntry.setMethod(i2);
                                zipEntry.setSize(length);
                                zipEntry.setCrc(crc32.getValue());
                                zipEntry.setTime(0L);
                                zipOutputStreamC.putNextEntry(zipEntry);
                                zipOutputStreamC.write(buffer, offset, length);
                                zipOutputStreamC.closeEntry();
                            } catch (IOException e) {
                                ExceptionDiagnostic exceptionDiagnostic = new ExceptionDiagnostic(e, this.b);
                                if ((e instanceof ZipException) && e.getMessage().startsWith("duplicate entry")) {
                                    diagnosticsHandler.warning(exceptionDiagnostic);
                                } else {
                                    diagnosticsHandler.error(exceptionDiagnostic);
                                }
                            }
                        } else {
                            this.h.add(C3994z.a(str, byteDataViewOf));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                byteStream.close();
            } catch (Throwable th2) {
                if (byteStream != null) {
                    try {
                        byteStream.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        } catch (ResourceException e2) {
            diagnosticsHandler.error(new StringDiagnostic("Failed to open input: " + e2.getMessage(), dataEntryResource.getOrigin()));
        } catch (IOException e3) {
            ExceptionDiagnostic exceptionDiagnostic2 = new ExceptionDiagnostic(e3, this.b);
            if ((e3 instanceof ZipException) && e3.getMessage().startsWith("duplicate entry")) {
                diagnosticsHandler.warning(exceptionDiagnostic2);
            } else {
                diagnosticsHandler.error(exceptionDiagnostic2);
            }
        }
    }

    public final synchronized void a(ByteDataView byteDataView, String str, DiagnosticsHandler diagnosticsHandler) {
        this.h.add(C3994z.a(str, ByteDataView.of(byteDataView.copyByteData())));
    }

    public final synchronized void a(int i2, String str, ByteDataView byteDataView, DiagnosticsHandler diagnosticsHandler) {
        try {
            if (i2 == this.f) {
                try {
                    ZipOutputStream zipOutputStreamC = c();
                    int i3 = AbstractC1660b2.c;
                    int i4 = "resources/new_api_database.ser".equals(str) ? 0 : 8;
                    int i5 = P3.b;
                    byte[] buffer = byteDataView.getBuffer();
                    int offset = byteDataView.getOffset();
                    int length = byteDataView.getLength();
                    CRC32 crc32 = new CRC32();
                    crc32.update(buffer, offset, length);
                    ZipEntry zipEntry = new ZipEntry(str);
                    zipEntry.setMethod(i4);
                    zipEntry.setSize(length);
                    zipEntry.setCrc(crc32.getValue());
                    zipEntry.setTime(0L);
                    zipOutputStreamC.putNextEntry(zipEntry);
                    zipOutputStreamC.write(buffer, offset, length);
                    zipOutputStreamC.closeEntry();
                } catch (IOException e) {
                    ExceptionDiagnostic exceptionDiagnostic = new ExceptionDiagnostic(e, this.b);
                    if ((e instanceof ZipException) && e.getMessage().startsWith("duplicate entry")) {
                        diagnosticsHandler.warning(exceptionDiagnostic);
                    } else {
                        diagnosticsHandler.error(exceptionDiagnostic);
                    }
                }
                int i6 = this.f + 1;
                this.f = i6;
                C3994z c3994z = (C3994z) this.g.remove(Integer.valueOf(i6));
                while (c3994z != null) {
                    String str2 = c3994z.a;
                    ByteDataView byteDataView2 = c3994z.b;
                    try {
                        ZipOutputStream zipOutputStreamC2 = c();
                        int i7 = AbstractC1660b2.c;
                        int i8 = "resources/new_api_database.ser".equals(str2) ? 0 : 8;
                        int i9 = P3.b;
                        byte[] buffer2 = byteDataView2.getBuffer();
                        int offset2 = byteDataView2.getOffset();
                        int length2 = byteDataView2.getLength();
                        CRC32 crc33 = new CRC32();
                        crc33.update(buffer2, offset2, length2);
                        ZipEntry zipEntry2 = new ZipEntry(str2);
                        zipEntry2.setMethod(i8);
                        zipEntry2.setSize(length2);
                        zipEntry2.setCrc(crc33.getValue());
                        zipEntry2.setTime(0L);
                        zipOutputStreamC2.putNextEntry(zipEntry2);
                        zipOutputStreamC2.write(buffer2, offset2, length2);
                        zipOutputStreamC2.closeEntry();
                    } catch (IOException e2) {
                        ExceptionDiagnostic exceptionDiagnostic2 = new ExceptionDiagnostic(e2, this.b);
                        if ((e2 instanceof ZipException) && e2.getMessage().startsWith("duplicate entry")) {
                            diagnosticsHandler.warning(exceptionDiagnostic2);
                        } else {
                            diagnosticsHandler.error(exceptionDiagnostic2);
                        }
                    }
                    int i10 = this.f + 1;
                    this.f = i10;
                    c3994z = (C3994z) this.g.remove(Integer.valueOf(i10));
                }
            } else {
                this.g.put(Integer.valueOf(i2), new C3994z(str, ByteDataView.of(byteDataView.copyByteData()), false));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final Origin a() {
        return this.b;
    }
}
