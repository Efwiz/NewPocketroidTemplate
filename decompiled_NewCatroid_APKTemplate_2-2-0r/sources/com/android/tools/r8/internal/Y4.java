package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Y4 {
    static final /* synthetic */ boolean h = true;
    private final String a;
    private final AbstractC0681j0 b;
    private final AbstractC1639am c;
    private final AbstractC2178hm d;
    private final Path e;
    private ArrayList f = new ArrayList();
    private W4 g = null;

    public Y4(AbstractC0681j0 abstractC0681j0, AbstractC0681j0 abstractC0681j1, Path path) {
        this.b = abstractC0681j1;
        this.e = path;
        String strB = b(abstractC0681j0);
        this.a = strB;
        if (!strB.equals(b(abstractC0681j1))) {
            throw new C1367Tb("Bisecting application classes do not match classes in reference APK");
        }
        this.c = AbstractC1639am.a(a(abstractC0681j0));
        C1946em c1946emE = AbstractC2178hm.e();
        for (int i = 0; i < this.c.size(); i++) {
            c1946emE.a(((C0729q2) this.c.get(i)).d, Integer.valueOf(i));
        }
        this.d = c1946emE.a();
    }

    public void a(V4 v4) {
        if (this.g == null) {
            throw new C1367Tb("Invalid bisection state. Could not find information on previous runs.");
        }
        if (this.f.size() == 0) {
            if (!h && !this.g.equals(new W4(0, 0))) {
                throw new AssertionError();
            }
            if (v4 != V4.b) {
                throw new C1367Tb("Expected good state for reference application run, got " + v4);
            }
        }
        if (this.f.size() == 1) {
            if (!h && !this.g.equals(new W4(0, this.d.size()))) {
                throw new AssertionError();
            }
            if (v4 != V4.c) {
                throw new C1367Tb("Expected bad state for input application run, got " + v4);
            }
        }
        this.f.add(new X4(v4, this.g));
        System.out.println("Marked range " + this.g + ": " + v4);
        this.g = null;
    }

    public final C0729q2 b() {
        if (this.g.b() != 1) {
            return null;
        }
        return (C0729q2) this.b.c(((C0729q2) this.c.get(this.g.a)).d);
    }

    public void c() throws IOException {
        Path path = this.e;
        if (path == null) {
            return;
        }
        if (!Files.exists(path, new LinkOption[0])) {
            System.out.println("Assuming initial run for non-existent state file: " + this.e);
            return;
        }
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(this.e);
        try {
            if (!this.a.equals(bufferedReaderNewBufferedReader.readLine())) {
                throw new C1367Tb("Bisection state file does not match the reference build signature");
            }
            while (true) {
                String line = bufferedReaderNewBufferedReader.readLine();
                if (line == null) {
                    break;
                } else {
                    arrayList.add(line);
                }
            }
            bufferedReaderNewBufferedReader.close();
            if (arrayList.isEmpty()) {
                return;
            }
            this.f = new ArrayList(arrayList.size());
            for (int i = 0; i < arrayList.size() - 1; i++) {
                this.f.add(new X4((String) arrayList.get(i)));
            }
            this.g = new W4((String) arrayList.get(arrayList.size() - 1));
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

    public void d() throws IOException {
        Path path = this.e;
        if (path == null) {
            return;
        }
        BufferedWriter bufferedWriterNewBufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);
        try {
            bufferedWriterNewBufferedWriter.write(this.a);
            bufferedWriterNewBufferedWriter.write("\n");
            for (X4 x4 : this.f) {
                bufferedWriterNewBufferedWriter.write(x4.a ? "good" : "bad");
                bufferedWriterNewBufferedWriter.write(58);
                x4.b.a(bufferedWriterNewBufferedWriter);
                bufferedWriterNewBufferedWriter.write("\n");
            }
            this.g.a(bufferedWriterNewBufferedWriter);
            bufferedWriterNewBufferedWriter.write("\n");
            bufferedWriterNewBufferedWriter.flush();
            bufferedWriterNewBufferedWriter.close();
        } catch (Throwable th) {
            if (bufferedWriterNewBufferedWriter != null) {
                try {
                    bufferedWriterNewBufferedWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static String b(AbstractC0681j0 abstractC0681j0) {
        ArrayList arrayListA = a(abstractC0681j0);
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayListA.iterator();
        while (it.hasNext()) {
            sb.append(((C0729q2) it.next()).toString()).append(";");
        }
        return AbstractC0860Bk.b().a(sb.toString(), AbstractC2592n9.a).toString();
    }

    /* JADX WARN: Code duplicated, block: B:59:0x011f  */
    public final AbstractC0681j0 a() {
        C0729q2 c0729q2;
        if (!h && this.g != null) {
            throw new AssertionError();
        }
        int i = 0;
        if (this.f.isEmpty()) {
            this.g = new W4(0, 0);
        } else if (this.f.size() == 1) {
            this.g = new W4(0, this.c.size());
        } else {
            W4 w4 = new W4(0, 0);
            int size = this.f.size() - 1;
            while (true) {
                if (size >= 0) {
                    X4 x4 = (X4) this.f.get(size);
                    if (!x4.a) {
                        W4 w5 = x4.b;
                        w5.getClass();
                        if (!w4.a()) {
                            boolean z = W4.c;
                            if (!z && (w5.a > w4.a || w4.b > w5.b)) {
                                throw new AssertionError();
                            }
                            int i2 = w5.a;
                            int i3 = w4.a;
                            if (i2 == i3) {
                                w5 = new W4(w4.b, w5.b);
                            } else {
                                if (!z && w5.b != w4.b) {
                                    throw new AssertionError();
                                }
                                w5 = new W4(i2, i3);
                            }
                        }
                        if (!w5.a()) {
                            if (w5.b() == 1) {
                                this.g = w5;
                                return null;
                            }
                            System.out.println("Last bad range: " + w5);
                            this.g = w5.c();
                            break;
                        }
                        throw new C1367Tb("Bad range is empty. Cannot continue bisecting :-(");
                    }
                    w4 = w4.a(x4.b);
                    size--;
                } else {
                    throw new C3405y10("Did not find any bad range in bisection state");
                }
            }
        }
        System.out.println("Next bisection range: " + this.g);
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        for (C0729q2 c0729q3 : this.b.d()) {
            Integer num = (Integer) this.d.get(c0729q3.d);
            if (num != null) {
                W4 w6 = this.g;
                int iIntValue = num.intValue();
                if (w6.a > iIntValue || iIntValue >= w6.b) {
                    c0729q2 = (C0729q2) this.c.get(num.intValue());
                } else {
                    c0729q2 = null;
                }
            } else {
                c0729q2 = null;
            }
            if (c0729q2 != null) {
                arrayList.add(c0729q2);
                i++;
            } else {
                arrayList.add(c0729q3);
                if (!h && this.g.a()) {
                    throw new AssertionError();
                }
                i4++;
            }
        }
        System.out.println("Class split is good: " + i + ", bad: " + i4);
        return this.b.c().a(arrayList).b();
    }

    private static ArrayList a(AbstractC0681j0 abstractC0681j0) {
        ArrayList arrayList = new ArrayList(abstractC0681j0.d());
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.Y4$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Y4.a((C0729q2) obj, (C0729q2) obj2);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C0729q2 c0729q2, C0729q2 c0729q3) {
        return c0729q2.d.a((com.android.tools.r8.graph.V4) c0729q3.d, AbstractC3649o0.a());
    }
}
