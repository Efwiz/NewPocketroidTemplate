package org.catrobat.catroid.content;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/EasingFunctions.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EasingFunctions {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/EasingFunctions$EasingType.class
      classes14.dex
     */
    public enum EasingType {
        LINEAR,
        QUAD_IN,
        QUAD_OUT,
        QUAD_IN_OUT,
        CUBIC_IN,
        CUBIC_OUT,
        CUBIC_IN_OUT,
        QUART_IN,
        QUART_OUT,
        QUART_IN_OUT,
        QUINT_IN,
        QUINT_OUT,
        QUINT_IN_OUT,
        SINE_IN,
        SINE_OUT,
        SINE_IN_OUT,
        EXPO_IN,
        EXPO_OUT,
        EXPO_IN_OUT,
        CIRC_IN,
        CIRC_OUT,
        CIRC_IN_OUT,
        ELASTIC_IN,
        ELASTIC_OUT,
        ELASTIC_IN_OUT,
        BACK_IN,
        BACK_OUT,
        BACK_IN_OUT,
        BOUNCE_IN,
        BOUNCE_OUT,
        BOUNCE_IN_OUT,
        SMOOTH_STEP,
        SMOOTHER_STEP
    }

    private static float calculateBounceOut(float t, float c) {
        float f;
        float f2;
        float f3;
        if (t < 0.36363637f) {
            f3 = 7.5625f * t * t;
        } else {
            if (t < 0.72727275f) {
                float f4 = t - 0.54545456f;
                f = 7.5625f * f4 * f4;
                f2 = 0.75f;
            } else if (t < 0.9090909090909091d) {
                float f5 = t - 0.8181818f;
                f = 7.5625f * f5 * f5;
                f2 = 0.9375f;
            } else {
                float f6 = t - 0.95454544f;
                f = 7.5625f * f6 * f6;
                f2 = 0.984375f;
            }
            f3 = f + f2;
        }
        return c * f3;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/EasingFunctions$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.EasingFunctions$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType;

        static {
            int[] iArr = new int[EasingType.values().length];
            $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType = iArr;
            try {
                iArr[EasingType.LINEAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUAD_IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUAD_OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUAD_IN_OUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.CUBIC_IN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.CUBIC_OUT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.CUBIC_IN_OUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUART_IN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUART_OUT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUART_IN_OUT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUINT_IN.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUINT_OUT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.QUINT_IN_OUT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.SINE_IN.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.SINE_OUT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.SINE_IN_OUT.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.EXPO_IN.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.EXPO_OUT.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.EXPO_IN_OUT.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.CIRC_IN.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.CIRC_OUT.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.CIRC_IN_OUT.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.BACK_IN.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.BACK_OUT.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.BACK_IN_OUT.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.ELASTIC_IN.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.ELASTIC_OUT.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.ELASTIC_IN_OUT.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.BOUNCE_OUT.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.BOUNCE_IN.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.BOUNCE_IN_OUT.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.SMOOTH_STEP.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[EasingType.SMOOTHER_STEP.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static float calculate(EasingType type, float time, float duration, float start, float end) {
        float f;
        float f2;
        float f3;
        float f4;
        float fSqrt;
        float f5;
        float f6;
        double dSin;
        float f7;
        float fPow;
        if (duration <= 0.0f || time >= duration) {
            return end;
        }
        if (time <= 0.0f) {
            return start;
        }
        float fPow2 = end - start;
        float f8 = time / duration;
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$content$EasingFunctions$EasingType[type.ordinal()]) {
            case 1:
                return start + (fPow2 * f8);
            case 2:
                f5 = fPow2 * f8 * f8;
                return f5 + start;
            case 3:
                return ((-fPow2) * f8 * (f8 - 2.0f)) + start;
            case 4:
                f8 *= 2.0f;
                if (f8 >= 1.0f) {
                    float f9 = f8 - 1.0f;
                    return (((-fPow2) / 2.0f) * ((f9 * (f9 - 2.0f)) - 1.0f)) + start;
                }
                fPow2 /= 2.0f;
                f5 = fPow2 * f8 * f8;
                return f5 + start;
            case 5:
                fPow2 *= f8;
                f5 = fPow2 * f8 * f8;
                return f5 + start;
            case 6:
                f = f8 - 1.0f;
                f2 = f * f;
                fSqrt = f2 * f;
                f7 = fSqrt + 1.0f;
                f5 = fPow2 * f7;
                return f5 + start;
            case 7:
                f8 *= 2.0f;
                if (f8 < 1.0f) {
                    fPow2 /= 2.0f;
                    fPow2 *= f8;
                    f5 = fPow2 * f8 * f8;
                    return f5 + start;
                }
                f3 = f8 - 2.0f;
                fPow2 /= 2.0f;
                f4 = f3 * f3;
                f6 = f4 * f3;
                f7 = f6 + 2.0f;
                f5 = fPow2 * f7;
                return f5 + start;
            case 8:
                fPow2 *= f8;
                fPow2 *= f8;
                f5 = fPow2 * f8 * f8;
                return f5 + start;
            case 9:
                float f10 = f8 - 1.0f;
                return ((-fPow2) * ((((f10 * f10) * f10) * f10) - 1.0f)) + start;
            case 10:
                f8 *= 2.0f;
                if (f8 >= 1.0f) {
                    float f11 = f8 - 2.0f;
                    return (((-fPow2) / 2.0f) * ((((f11 * f11) * f11) * f11) - 2.0f)) + start;
                }
                fPow2 /= 2.0f;
                fPow2 *= f8;
                fPow2 *= f8;
                f5 = fPow2 * f8 * f8;
                return f5 + start;
            case 11:
                fPow2 *= f8;
                fPow2 *= f8;
                fPow2 *= f8;
                f5 = fPow2 * f8 * f8;
                return f5 + start;
            case 12:
                f = f8 - 1.0f;
                f2 = f * f * f * f;
                fSqrt = f2 * f;
                f7 = fSqrt + 1.0f;
                f5 = fPow2 * f7;
                return f5 + start;
            case 13:
                f8 *= 2.0f;
                if (f8 < 1.0f) {
                    fPow2 /= 2.0f;
                    fPow2 *= f8;
                    fPow2 *= f8;
                    fPow2 *= f8;
                    f5 = fPow2 * f8 * f8;
                    return f5 + start;
                }
                f3 = f8 - 2.0f;
                fPow2 /= 2.0f;
                f4 = f3 * f3 * f3 * f3;
                f6 = f4 * f3;
                f7 = f6 + 2.0f;
                f5 = fPow2 * f7;
                return f5 + start;
            case 14:
                return ((-fPow2) * ((float) Math.cos(((double) f8) * 1.5707963267948966d))) + fPow2 + start;
            case 15:
                dSin = Math.sin(((double) f8) * 1.5707963267948966d);
                f7 = (float) dSin;
                f5 = fPow2 * f7;
                return f5 + start;
            case 16:
                return (((-fPow2) / 2.0f) * ((float) (Math.cos(((double) f8) * 3.141592653589793d) - 1.0d))) + start;
            case 17:
                return f8 == 0.0f ? start : (fPow2 * ((float) Math.pow(2.0d, (f8 - 1.0f) * 10.0f))) + start;
            case 18:
                return f8 == 1.0f ? start + fPow2 : (fPow2 * ((float) ((-Math.pow(2.0d, f8 * (-10.0f))) + 1.0d))) + start;
            case 19:
                if (f8 == 0.0f) {
                    return start;
                }
                if (f8 == 1.0f) {
                    return start + fPow2;
                }
                float f12 = f8 * 2.0f;
                if (f12 < 1.0f) {
                    fPow2 /= 2.0f;
                    dSin = Math.pow(2.0d, (f12 - 1.0f) * 10.0f);
                } else {
                    fPow2 /= 2.0f;
                    dSin = (-Math.pow(2.0d, (f12 - 1.0f) * (-10.0f))) + 2.0d;
                }
                f7 = (float) dSin;
                f5 = fPow2 * f7;
                return f5 + start;
            case 20:
                return ((-fPow2) * (((float) Math.sqrt(1.0f - (f8 * f8))) - 1.0f)) + start;
            case 21:
                float f13 = f8 - 1.0f;
                dSin = Math.sqrt(1.0f - (f13 * f13));
                f7 = (float) dSin;
                f5 = fPow2 * f7;
                return f5 + start;
            case 22:
                float f14 = f8 * 2.0f;
                if (f14 < 1.0f) {
                    return (((-fPow2) / 2.0f) * (((float) Math.sqrt(1.0f - (f14 * f14))) - 1.0f)) + start;
                }
                float f15 = f14 - 2.0f;
                fPow2 /= 2.0f;
                fSqrt = (float) Math.sqrt(1.0f - (f15 * f15));
                f7 = fSqrt + 1.0f;
                f5 = fPow2 * f7;
                return f5 + start;
            case 23:
                f5 = fPow2 * f8 * f8 * ((2.70158f * f8) - 1.70158f);
                return f5 + start;
            case 24:
                float f16 = f8 - 1.0f;
                fSqrt = f16 * f16 * ((2.70158f * f16) + 1.70158f);
                f7 = fSqrt + 1.0f;
                f5 = fPow2 * f7;
                return f5 + start;
            case 25:
                float f17 = f8 * 2.0f;
                if (f17 < 1.0f) {
                    fPow2 /= 2.0f;
                    f7 = f17 * f17 * ((3.5949094f * f17) - 2.5949094f);
                } else {
                    float f18 = f17 - 2.0f;
                    fPow2 /= 2.0f;
                    f6 = f18 * f18 * ((3.5949094f * f18) + 2.5949094f);
                    f7 = f6 + 2.0f;
                }
                f5 = fPow2 * f7;
                return f5 + start;
            case 26:
                if (f8 == 0.0f) {
                    return start;
                }
                if (f8 == 1.0f) {
                    return start + fPow2;
                }
                float f19 = duration * 0.3f;
                float f20 = f8 - 1.0f;
                return (-(fPow2 * ((float) Math.pow(2.0d, 10.0f * f20)) * ((float) Math.sin((((double) ((f20 * duration) - (f19 / 4.0f))) * 6.283185307179586d) / ((double) f19))))) + start;
            case 27:
                if (f8 == 0.0f) {
                    return start;
                }
                if (f8 == 1.0f) {
                    return start + fPow2;
                }
                float f21 = duration * 0.3f;
                fPow = ((float) Math.pow(2.0d, (-10.0f) * f8)) * fPow2 * ((float) Math.sin((((double) ((f8 * duration) - (f21 / 4.0f))) * 6.283185307179586d) / ((double) f21)));
                return fPow + fPow2 + start;
            case 28:
                if (f8 == 0.0f) {
                    return start;
                }
                float f22 = f8 * 2.0f;
                if (f22 == 2.0f) {
                    return start + fPow2;
                }
                float f23 = 0.45000002f * duration;
                float f24 = f23 / 4.0f;
                if (f22 < 1.0f) {
                    float f25 = f22 - 1.0f;
                    fPow2 = fPow2 * ((float) Math.pow(2.0d, 10.0f * f25)) * ((float) Math.sin((((double) ((f25 * duration) - f24)) * 6.283185307179586d) / ((double) f23)));
                    f7 = -0.5f;
                    f5 = fPow2 * f7;
                    return f5 + start;
                }
                float f26 = f22 - 1.0f;
                fPow = ((float) Math.pow(2.0d, (-10.0f) * f26)) * fPow2 * ((float) Math.sin((((double) ((f26 * duration) - f24)) * 6.283185307179586d) / ((double) f23))) * 0.5f;
                return fPow + fPow2 + start;
            case 29:
                return start + calculateBounceOut(f8, fPow2);
            case 30:
                return start + (fPow2 - calculateBounceOut(1.0f - f8, fPow2));
            case 31:
                if (f8 < 0.5f) {
                    return start + ((fPow2 - calculateBounceOut(1.0f - (f8 * 2.0f), fPow2)) * 0.5f);
                }
                return start + (calculateBounceOut((f8 * 2.0f) - 1.0f, fPow2) * 0.5f) + (fPow2 * 0.5f);
            case 32:
                return start + (fPow2 * f8 * f8 * (3.0f - (f8 * 2.0f)));
            case 33:
                return start + (fPow2 * f8 * f8 * f8 * ((f8 * ((6.0f * f8) - 15.0f)) + 10.0f));
            default:
                return start + (fPow2 * f8);
        }
    }
}
