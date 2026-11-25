package com.android.tools.r8.dex;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.H0;
import com.android.tools.r8.utils.Y2;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.util.Optional;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C extends AbstractC0575m {
    private final H0 d;

    public C(ProgramResource programResource) {
        super(programResource);
        this.d = a(this.b, 0);
    }

    private H0 a(C0581s c0581s, int i) {
        try {
            c0581s.e();
            c0581s.k();
            byte[] bArr = C0582t.a;
            int length = bArr.length;
            int i2 = 0;
            while (i2 < length) {
                byte b = bArr[i2];
                int i3 = i + 1;
                byte bA = c0581s.a(i);
                if (bA != b) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Dex file has invalid header, expected " + ((int) b) + " got " + ((int) bA) + ". Next bytes are ");
                    for (int i4 = 0; i4 < 10; i4++) {
                        if (c0581s.i()) {
                            sb.append(Y2.a((int) c0581s.e(), 2));
                            sb.append(",");
                        }
                    }
                    throw new C1367Tb(sb.toString(), this.a);
                }
                i2++;
                i = i3;
            }
            char cA = (char) c0581s.a(i);
            int i5 = i + 2;
            char cA2 = (char) c0581s.a(i + 1);
            int i6 = i + 3;
            char cA3 = (char) c0581s.a(i5);
            Optional<H0> optionalA = H0.a(cA, cA2, cA3);
            if (!optionalA.isPresent()) {
                throw new C1367Tb("Unsupported DEX file version: " + cA + cA2 + cA3, this.a);
            }
            if (c0581s.a(i6) == 0) {
                return optionalA.get();
            }
            throw new C1367Tb("Dex file has invalid header", this.a);
        } catch (BufferUnderflowException unused) {
            throw new C1367Tb("Dex file is empty", this.a);
        }
    }

    final H0 j() {
        return this.d;
    }

    final void k() {
        this.b.a(ByteOrder.LITTLE_ENDIAN);
        int iB = this.b.b(40);
        if (iB == 2018915346) {
            this.b.a(ByteOrder.BIG_ENDIAN);
        } else if (iB != 305419896) {
            throw new C1367Tb("Unable to determine endianess for reading dex file.");
        }
    }

    C(Origin origin, byte[] bArr) {
        super(origin, bArr);
        this.d = a(this.b, 0);
    }

    C(Origin origin, byte[] bArr, int i) {
        super(origin, bArr);
        this.d = a(this.b, i);
    }
}
