package com.android.tools.r8.utils;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.GlobalSyntheticsResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.Version;
import com.android.tools.r8.internal.V5;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.y1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3991y1 implements ProgramResourceProvider {
    private final List a;
    private ArrayList b = null;

    public C3991y1(ArrayList arrayList) {
        this.a = arrayList;
    }

    private static String a(String str) {
        return C3970u0.y(AbstractC0402a.a(str, 7, 0) + ".class");
    }

    @Override // com.android.tools.r8.ProgramResourceProvider
    public final Collection getProgramResources() {
        if (this.b == null) {
            a();
        }
        return this.b;
    }

    private synchronized void a() {
        if (this.b != null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (GlobalSyntheticsResourceProvider globalSyntheticsResourceProvider : this.a) {
            ArrayList arrayList2 = new ArrayList();
            try {
                ZipInputStream zipInputStream = new ZipInputStream(globalSyntheticsResourceProvider.getByteStream());
                ProgramResource.Kind kindValueOf = null;
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry != null) {
                            String name2 = nextEntry.getName();
                            if (name2.equals(Namer.METADATA_CLASS_KIND)) {
                                kindValueOf = ProgramResource.Kind.valueOf(new String(V5.a(zipInputStream), StandardCharsets.UTF_8));
                            } else if (name2.equals("compilerinfo")) {
                                String str = new String(V5.a(zipInputStream), StandardCharsets.UTF_8);
                                if (!Version.getVersionString().equals(str)) {
                                    throw new ResourceException(globalSyntheticsResourceProvider.getOrigin(), "Outdated or inconsistent global synthetics information.\nGlobal synthetics information version: " + str + "\nCompiler version: " + Version.getVersionString());
                                }
                            } else if (name2.endsWith(".global") && hashSet.add(name2)) {
                                final C3987x1 c3987x1 = new C3987x1(name2, globalSyntheticsResourceProvider.getOrigin());
                                String strA = a(name2);
                                final byte[] bArrA = V5.a(zipInputStream);
                                final Set setSingleton = Collections.singleton(strA);
                                arrayList2.add(new Function() { // from class: com.android.tools.r8.utils.y1$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return C3991y1.a(c3987x1, bArrA, setSingleton, (ProgramResource.Kind) obj);
                                    }
                                });
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            zipInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                zipInputStream.close();
                if (kindValueOf != null) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        arrayList.add((ProgramResource) ((Function) it.next()).apply(kindValueOf));
                    }
                } else {
                    throw new ResourceException(globalSyntheticsResourceProvider.getOrigin(), "Invalid global synthetics provider does not specify its content kind.");
                }
            } catch (IOException e) {
                throw new ResourceException(globalSyntheticsResourceProvider.getOrigin(), e);
            }
        }
        this.b = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ProgramResource a(C3987x1 c3987x1, byte[] bArr, Set set, ProgramResource.Kind kind) {
        return C3909i2.a(c3987x1, kind, bArr, set);
    }
}
