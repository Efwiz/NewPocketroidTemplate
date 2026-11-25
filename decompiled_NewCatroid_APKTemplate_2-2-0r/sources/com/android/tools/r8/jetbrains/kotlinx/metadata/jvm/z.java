package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.C0844Au;
import com.android.tools.r8.internal.C1659b10;
import com.android.tools.r8.internal.C2377kL;
import com.android.tools.r8.internal.C3394xu;
import com.android.tools.r8.internal.C3534zu;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class z extends o {
    private final C3534zu a;

    public z() {
        super(0);
        this.a = C0844Au.r();
    }

    public final void a(String str, List list, LinkedHashMap linkedHashMap) {
        AbstractC0936Ds.c(str, "fqName");
        AbstractC0936Ds.c(list, "fileFacades");
        C2377kL c2377kL = new C2377kL(str);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            c2377kL.a((String) it.next(), null);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            c2377kL.a((String) entry.getKey(), (String) entry.getValue());
        }
        C3534zu c3534zu = this.a;
        AbstractC0936Ds.b(c3534zu, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
        c2377kL.a(c3534zu);
    }

    public final B a(int[] iArr) throws IOException {
        AbstractC0936Ds.c(iArr, "metadataVersion");
        C0844Au c0844AuG = this.a.g();
        if (c0844AuG.a()) {
            C3394xu c3394xu = new C3394xu(Arrays.copyOf(iArr, iArr.length));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            int[] iArrC = c3394xu.c();
            dataOutputStream.writeInt(iArrC.length);
            for (int i : iArrC) {
                dataOutputStream.writeInt(i);
            }
            if (c3394xu.a() == 1 && c3394xu.b() >= 4) {
                dataOutputStream.writeInt(0);
            }
            c0844AuG.a(dataOutputStream);
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            AbstractC0936Ds.b(byteArray, "moduleMapping.toByteArray()");
            return new B(byteArray);
        }
        throw new C1659b10();
    }
}
