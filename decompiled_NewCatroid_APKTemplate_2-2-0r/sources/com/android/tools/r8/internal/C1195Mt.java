package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1195Mt implements FZ {
    private final C1854dd a;

    public C1195Mt(C1854dd c1854dd) {
        this.a = c1854dd;
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        InterfaceC1167Lt interfaceC1167Lt = (InterfaceC1167Lt) i00.a().getAnnotation(InterfaceC1167Lt.class);
        if (interfaceC1167Lt == null) {
            return null;
        }
        return a(this.a, c1862dk, i00, interfaceC1167Lt);
    }

    static EZ a(C1854dd c1854dd, C1862dk c1862dk, I00 i00, InterfaceC1167Lt interfaceC1167Lt) {
        EZ ezA;
        Object objA = c1854dd.a(I00.a(interfaceC1167Lt.value())).a();
        if (objA instanceof EZ) {
            ezA = (EZ) objA;
        } else if (objA instanceof FZ) {
            ezA = ((FZ) objA).a(c1862dk, i00);
        } else {
            throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer reference.");
        }
        return ezA != null ? ezA.a() : ezA;
    }
}
