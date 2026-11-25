package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaxq implements zzanw {
    static final zzanw zza = new zzaxq();

    private zzaxq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanw
    public final boolean zza(int i) {
        if (i == 200 || i == 300 || i == 302 || i == 312 || i == 15000 || i == 304 || i == 305) {
            return true;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                switch (i) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        return true;
                    default:
                        switch (i) {
                            case 43:
                            case 44:
                            case 45:
                                return true;
                            default:
                                switch (i) {
                                    case 220:
                                    case 221:
                                    case 222:
                                    case 223:
                                    case 224:
                                    case 225:
                                    case 226:
                                    case XKeySym.XK_atilde /* 227 */:
                                        return true;
                                    default:
                                        switch (i) {
                                            case XKeySym.XK_icircumflex /* 238 */:
                                            case XKeySym.XK_idiaeresis /* 239 */:
                                            case 240:
                                            case XKeySym.XK_ntilde /* 241 */:
                                            case 242:
                                            case 243:
                                                return true;
                                            default:
                                                switch (i) {
                                                    case 314:
                                                    case 315:
                                                    case 316:
                                                        return true;
                                                    default:
                                                        return false;
                                                }
                                        }
                                }
                        }
                }
        }
    }
}
