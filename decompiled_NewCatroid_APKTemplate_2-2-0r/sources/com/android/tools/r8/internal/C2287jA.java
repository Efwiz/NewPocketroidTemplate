package com.android.tools.r8.internal;

import com.android.tools.r8.utils.InterfaceC3905h3;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.jetbrains.kotlin.codegen.inline.SMAP;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2287jA implements Closeable {
    static final /* synthetic */ boolean c = true;
    private final BufferedReader a;
    private String b;

    C2287jA(String str) {
        this.a = new BufferedReader(new StringReader(str));
    }

    final void a(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        final String str = SMAP.LINE_SECTION;
        a(new Predicate() { // from class: com.android.tools.r8.internal.jA$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equals((String) obj);
            }
        }, 2, interfaceC3905h3);
    }

    final boolean c() {
        return this.b == null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.a.close();
    }

    final void d() throws C2362kA {
        if (e().equals(SMAP.FILE_SECTION)) {
            return;
        }
        throw new C2362kA("The string " + this.b + " does not match the expected string *F");
    }

    final String e() throws IOException {
        String line = this.a.readLine();
        this.b = line;
        return line;
    }

    final String a(Predicate predicate, int i, InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        if (predicate.test(this.b)) {
            return this.b;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(e());
        int i2 = i;
        while (!predicate.test(this.b) && !c()) {
            if (i2 != 1) {
                i2--;
            } else {
                if (!c && arrayList.size() != i) {
                    throw new AssertionError();
                }
                interfaceC3905h3.accept(arrayList);
                arrayList = new ArrayList();
                i2 = i;
            }
            arrayList.add(e());
        }
        if (!arrayList.isEmpty() && !predicate.test((String) arrayList.get(0))) {
            throw new C2362kA("Block size does not match linesInBlock = " + i);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return (String) arrayList.get(0);
    }
}
