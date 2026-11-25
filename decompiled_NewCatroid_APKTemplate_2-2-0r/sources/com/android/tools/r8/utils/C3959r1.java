package com.android.tools.r8.utils;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.Version;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.zip.ZipOutputStream;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.r1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3959r1 {
    static final /* synthetic */ boolean c = true;
    private final ProgramResource.Kind a;
    private final ArrayList b = new ArrayList();

    public C3959r1(ProgramResource.Kind kind) {
        this.a = kind;
    }

    public final byte[] a() throws IOException {
        this.b.add(new C3979v2("compilerinfo", Version.getVersionString().getBytes(StandardCharsets.UTF_8)));
        this.b.add(new C3979v2(Namer.METADATA_CLASS_KIND, this.a.toString().getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(((Integer) V1.a((List) this.b, (Object) 0, new BiFunction() { // from class: com.android.tools.r8.utils.r1$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C3959r1.a((Integer) obj, (C3979v2) obj2);
            }
        })).intValue() + 500);
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
            for (C3979v2 c3979v2 : this.b) {
                P3.a(zipOutputStream, (String) c3979v2.a(), (byte[]) c3979v2.b(), 0);
                c3979v2.b(null);
            }
            zipOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            try {
                zipOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(Integer num, C3979v2 c3979v2) {
        return Integer.valueOf(((String) c3979v2.a()).length() + num.intValue() + ((byte[]) c3979v2.b()).length + 200);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(String str) {
        if (!c && (str == null || !C3970u0.z(str))) {
            throw new AssertionError();
        }
        return C3970u0.i(str) + ".global";
    }
}
