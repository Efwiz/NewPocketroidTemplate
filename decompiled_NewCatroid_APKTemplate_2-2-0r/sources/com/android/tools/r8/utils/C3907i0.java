package com.android.tools.r8.utils;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.startup.StartupClassBuilder;
import com.android.tools.r8.startup.StartupMethodBuilder;
import com.android.tools.r8.startup.StartupProfileBuilder;
import com.android.tools.r8.startup.StartupProfileProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.i0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3907i0 implements StartupProfileProvider {
    static final /* synthetic */ boolean b = true;
    final /* synthetic */ Path a;

    C3907i0(Path path) {
        this.a = path;
    }

    @Override // com.android.tools.r8.Resource
    public final Origin getOrigin() {
        return new PathOrigin(this.a);
    }

    @Override // com.android.tools.r8.startup.StartupProfileProvider
    public final void getStartupProfile(StartupProfileBuilder startupProfileBuilder) {
        try {
            BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(this.a);
            while (bufferedReaderNewBufferedReader.ready()) {
                try {
                    final String line = bufferedReaderNewBufferedReader.readLine();
                    int i = C3889e2.b;
                    int iIndexOf = line.indexOf("->");
                    final MethodReference methodReferenceA = iIndexOf >= 0 ? C3889e2.a(iIndexOf, line) : null;
                    if (methodReferenceA != null) {
                        startupProfileBuilder.addStartupMethod(new Consumer() { // from class: com.android.tools.r8.utils.i0$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((StartupMethodBuilder) obj).setMethodReference(methodReferenceA);
                            }
                        });
                    } else {
                        if (!b && !C3970u0.z(line)) {
                            throw new AssertionError();
                        }
                        startupProfileBuilder.addStartupClass(new Consumer() { // from class: com.android.tools.r8.utils.i0$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                C3907i0.a(line, (StartupClassBuilder) obj);
                            }
                        });
                    }
                } catch (Throwable th) {
                    if (bufferedReaderNewBufferedReader != null) {
                        try {
                            bufferedReaderNewBufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            bufferedReaderNewBufferedReader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, StartupClassBuilder startupClassBuilder) {
        startupClassBuilder.setClassReference(Reference.classFromDescriptor(str));
    }
}
