package org.apache.xerces.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.net.HttpStatus;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaError;
import java.util.Random;

/* JADX INFO: loaded from: classes13.dex */
final class PrimeNumberSequenceGenerator {
    private static int[] PRIMES = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, XKeySym.XK_atilde, XKeySym.XK_aring, XKeySym.XK_eacute, XKeySym.XK_idiaeresis, XKeySym.XK_ntilde, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, TypedValues.AttributesType.TYPE_EASING, MediaError.DetailedErrorCode.SMOOTH_NETWORK, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, HttpStatus.SC_CONFLICT, 419, 421, 431, XKeySym.XK_aogonek, XKeySym.XK_caron, 443, 449, 457, 461, XKeySym.XK_Dcaron, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, TypedValues.MotionType.TYPE_PATHMOTION_ARC, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, XKeySym.XK_Hstroke, 677, XKeySym.XK_Gbreve, 691, 701, XKeySym.XK_Cabovedot, 719, 727};

    PrimeNumberSequenceGenerator() {
    }

    static void generateSequence(int[] iArr) {
        Random random = new Random();
        for (int i = 0; i < iArr.length; i++) {
            int[] iArr2 = PRIMES;
            iArr[i] = iArr2[random.nextInt(iArr2.length)];
        }
    }
}
