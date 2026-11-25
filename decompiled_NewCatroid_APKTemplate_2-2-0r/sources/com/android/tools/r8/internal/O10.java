package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class O10 {
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
    
        if (r10[r8] > (-65)) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x004d, code lost:
    
        if (r10[r8] > (-65)) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0099, code lost:
    
        if (r10[r7] > (-65)) goto L63;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int a(int r7, int r8, int r9, byte[] r10) {
        /*
            if (r7 == 0) goto L9c
            if (r8 < r9) goto L5
            return r7
        L5:
            byte r0 = (byte) r7
            r1 = -32
            r2 = -1
            r3 = -65
            if (r0 >= r1) goto L1c
            r7 = -62
            if (r0 < r7) goto L1b
            int r7 = r8 + 1
            r8 = r10[r8]
            if (r8 <= r3) goto L18
            goto L1b
        L18:
            r8 = r7
            goto L9c
        L1b:
            return r2
        L1c:
            r4 = -16
            r5 = -12
            if (r0 >= r4) goto L50
            int r7 = r7 >> 8
            int r7 = ~r7
            byte r7 = (byte) r7
            if (r7 != 0) goto L3b
            int r7 = r8 + 1
            r8 = r10[r8]
            if (r7 < r9) goto L38
            if (r0 > r5) goto L37
            if (r8 <= r3) goto L33
            goto L37
        L33:
            int r7 = r8 << 8
            r2 = r0 ^ r7
        L37:
            return r2
        L38:
            r6 = r8
            r8 = r7
            r7 = r6
        L3b:
            if (r7 > r3) goto L4f
            r4 = -96
            if (r0 != r1) goto L43
            if (r7 < r4) goto L4f
        L43:
            r1 = -19
            if (r0 != r1) goto L49
            if (r7 >= r4) goto L4f
        L49:
            int r7 = r8 + 1
            r8 = r10[r8]
            if (r8 <= r3) goto L18
        L4f:
            return r2
        L50:
            int r1 = r7 >> 8
            int r1 = ~r1
            byte r1 = (byte) r1
            if (r1 != 0) goto L68
            int r7 = r8 + 1
            r1 = r10[r8]
            if (r7 < r9) goto L66
            if (r0 > r5) goto L65
            if (r1 <= r3) goto L61
            goto L65
        L61:
            int r7 = r1 << 8
            r2 = r0 ^ r7
        L65:
            return r2
        L66:
            r8 = 0
            goto L6e
        L68:
            int r7 = r7 >> 16
            byte r7 = (byte) r7
            r6 = r8
            r8 = r7
            r7 = r6
        L6e:
            if (r8 != 0) goto L88
            int r8 = r7 + 1
            r7 = r10[r7]
            if (r8 < r9) goto L85
            if (r0 > r5) goto L84
            if (r1 > r3) goto L84
            if (r7 <= r3) goto L7d
            goto L84
        L7d:
            int r8 = r1 << 8
            r8 = r8 ^ r0
            int r7 = r7 << 16
            r2 = r8 ^ r7
        L84:
            return r2
        L85:
            r6 = r8
            r8 = r7
            r7 = r6
        L88:
            if (r1 > r3) goto L9b
            int r0 = r0 << 28
            int r1 = r1 + 112
            int r1 = r1 + r0
            int r0 = r1 >> 30
            if (r0 != 0) goto L9b
            if (r8 > r3) goto L9b
            int r8 = r7 + 1
            r7 = r10[r7]
            if (r7 <= r3) goto L9c
        L9b:
            return r2
        L9c:
            int r7 = a(r10, r8, r9)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.O10.a(int, int, int, byte[]):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:?, code lost:
    
        return (r9 << 16) ^ ((r11 << 8) ^ r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:?, code lost:
    
        return (r9 << 8) ^ r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x004d, code lost:
    
        if (r9 <= (-65)) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x005b, code lost:
    
        if (r9 <= (-65)) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x005f, code lost:
    
        if (r1 > (-12)) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0091, code lost:
    
        if (r9 <= (-65)) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00a5, code lost:
    
        if (r9 <= (-65)) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ac, code lost:
    
        if (r1 > (-12)) goto L82;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int a(byte[] r9, int r10, int r11) {
        /*
            Method dump skipped, instruction units count: 207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.O10.a(byte[], int, int):int");
    }
}
