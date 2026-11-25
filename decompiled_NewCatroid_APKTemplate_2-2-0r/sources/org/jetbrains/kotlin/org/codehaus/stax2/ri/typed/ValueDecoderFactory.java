package org.jetbrains.kotlin.org.codehaus.stax2.ri.typed;

import java.math.BigInteger;
import org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder;

/* JADX INFO: loaded from: classes4.dex */
public final class ValueDecoderFactory {
    protected BooleanDecoder mBooleanDecoder = null;
    protected IntDecoder mIntDecoder = null;
    protected LongDecoder mLongDecoder = null;
    protected FloatDecoder mFloatDecoder = null;
    protected DoubleDecoder mDoubleDecoder = null;

    public BooleanDecoder getBooleanDecoder() {
        if (this.mBooleanDecoder == null) {
            this.mBooleanDecoder = new BooleanDecoder();
        }
        return this.mBooleanDecoder;
    }

    public IntDecoder getIntDecoder() {
        if (this.mIntDecoder == null) {
            this.mIntDecoder = new IntDecoder();
        }
        return this.mIntDecoder;
    }

    public static abstract class DecoderBase extends TypedValueDecoder {
        protected int mNextPtr;
        static final BigInteger BD_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        static final BigInteger BD_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);

        public abstract String getType();

        protected DecoderBase() {
        }

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder
        public void handleEmptyValue() {
            throw new IllegalArgumentException("Empty value (all white space) not a valid lexical representation of " + getType());
        }

        protected void verifyDigits(char[] cArr, int i, int i2, int i3) {
            while (i3 < i2) {
                char c = cArr[i3];
                if (c > '9' || c < '0') {
                    throw constructInvalidValue(cArr, i, i2);
                }
                i3++;
            }
        }

        protected int skipSignAndZeroes(char[] cArr, char c, boolean z, int i, int i2) {
            int i3 = i + 1;
            if (z) {
                if (i3 >= i2) {
                    throw constructInvalidValue(cArr, i, i2);
                }
                char c2 = cArr[i3];
                i3 = i + 2;
                c = c2;
            }
            int i4 = c - '0';
            if (i4 < 0 || i4 > 9) {
                throw constructInvalidValue(cArr, i, i2);
            }
            while (i4 == 0 && i3 < i2) {
                int i5 = cArr[i3] - '0';
                if (i5 < 0 || i5 > 9) {
                    break;
                }
                i3++;
                i4 = i5;
            }
            this.mNextPtr = i3;
            return i4;
        }

        protected static final int parseInt(char[] cArr, int i, int i2) {
            int i3 = cArr[i] - '0';
            int i4 = i + 1;
            if (i4 >= i2) {
                return i3;
            }
            int i5 = (i3 * 10) + (cArr[i4] - '0');
            int i6 = i + 2;
            if (i6 >= i2) {
                return i5;
            }
            int i7 = (i5 * 10) + (cArr[i6] - '0');
            int i8 = i + 3;
            if (i8 >= i2) {
                return i7;
            }
            int i9 = (i7 * 10) + (cArr[i8] - '0');
            int i10 = i + 4;
            if (i10 >= i2) {
                return i9;
            }
            int i11 = (i9 * 10) + (cArr[i10] - '0');
            int i12 = i + 5;
            if (i12 >= i2) {
                return i11;
            }
            int i13 = (i11 * 10) + (cArr[i12] - '0');
            int i14 = i + 6;
            if (i14 >= i2) {
                return i13;
            }
            int i15 = (i13 * 10) + (cArr[i14] - '0');
            int i16 = i + 7;
            if (i16 >= i2) {
                return i15;
            }
            int i17 = (i15 * 10) + (cArr[i16] - '0');
            int i18 = i + 8;
            return i18 < i2 ? (i17 * 10) + (cArr[i18] - '0') : i17;
        }

        protected static final int parseInt(int i, char[] cArr, int i2, int i3) {
            int i4 = (i * 10) + (cArr[i2] - '0');
            int i5 = i2 + 1;
            if (i5 >= i3) {
                return i4;
            }
            int i6 = (i4 * 10) + (cArr[i5] - '0');
            int i7 = i2 + 2;
            if (i7 >= i3) {
                return i6;
            }
            int i8 = (i6 * 10) + (cArr[i7] - '0');
            int i9 = i2 + 3;
            if (i9 >= i3) {
                return i8;
            }
            int i10 = (i8 * 10) + (cArr[i9] - '0');
            int i11 = i2 + 4;
            if (i11 >= i3) {
                return i10;
            }
            int i12 = (i10 * 10) + (cArr[i11] - '0');
            int i13 = i2 + 5;
            if (i13 >= i3) {
                return i12;
            }
            int i14 = (i12 * 10) + (cArr[i13] - '0');
            int i15 = i2 + 6;
            if (i15 >= i3) {
                return i14;
            }
            int i16 = (i14 * 10) + (cArr[i15] - '0');
            int i17 = i2 + 7;
            return i17 < i3 ? (i16 * 10) + (cArr[i17] - '0') : i16;
        }

        protected static final long parseLong(char[] cArr, int i, int i2) {
            int i3 = i2 - 9;
            return (((long) parseInt(cArr, i, i3)) * 1000000000) + ((long) parseInt(cArr, i3, i2));
        }

        protected IllegalArgumentException constructInvalidValue(String str) {
            return new IllegalArgumentException("Value \"" + str + "\" not a valid lexical representation of " + getType());
        }

        protected IllegalArgumentException constructInvalidValue(char[] cArr, int i, int i2) {
            return new IllegalArgumentException("Value \"" + lexicalDesc(cArr, i, i2) + "\" not a valid lexical representation of " + getType());
        }

        protected String lexicalDesc(char[] cArr, int i, int i2) {
            return _clean(new String(cArr, i, i2 - i));
        }

        protected String lexicalDesc(String str) {
            return _clean(str);
        }

        protected String _clean(String str) {
            return str.trim();
        }
    }

    public static final class BooleanDecoder extends DecoderBase {
        protected boolean mValue;

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecoderBase
        public String getType() {
            return "boolean";
        }

        public boolean getValue() {
            return this.mValue;
        }

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder
        public void decode(char[] cArr, int i, int i2) throws IllegalArgumentException {
            int i3 = i2 - i;
            char c = cArr[i];
            if (c == 't') {
                if (i3 == 4 && cArr[i + 1] == 'r' && cArr[i + 2] == 'u' && cArr[i + 3] == 'e') {
                    this.mValue = true;
                    return;
                }
            } else if (c == 'f') {
                if (i3 == 5 && cArr[i + 1] == 'a' && cArr[i + 2] == 'l' && cArr[i + 3] == 's' && cArr[i + 4] == 'e') {
                    this.mValue = false;
                    return;
                }
            } else if (c == '0') {
                if (i3 == 1) {
                    this.mValue = false;
                    return;
                }
            } else if (c == '1' && i3 == 1) {
                this.mValue = true;
                return;
            }
            throw constructInvalidValue(cArr, i, i2);
        }
    }

    public static final class IntDecoder extends DecoderBase {
        protected int mValue;

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecoderBase
        public String getType() {
            return "int";
        }

        public int getValue() {
            return this.mValue;
        }

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder
        public void decode(char[] cArr, int i, int i2) throws IllegalArgumentException {
            int iSkipSignAndZeroes;
            char c = cArr[i];
            boolean z = c == '-';
            if (z || c == '+') {
                iSkipSignAndZeroes = skipSignAndZeroes(cArr, c, true, i, i2);
            } else {
                iSkipSignAndZeroes = skipSignAndZeroes(cArr, c, false, i, i2);
            }
            int i3 = this.mNextPtr;
            int i4 = i2 - i3;
            if (i4 == 0) {
                if (z) {
                    iSkipSignAndZeroes = -iSkipSignAndZeroes;
                }
                this.mValue = iSkipSignAndZeroes;
                return;
            }
            verifyDigits(cArr, i, i2, i3);
            if (i4 <= 8) {
                int i5 = parseInt(iSkipSignAndZeroes, cArr, i3, i4 + i3);
                if (z) {
                    i5 = -i5;
                }
                this.mValue = i5;
                return;
            }
            if (i4 == 9 && iSkipSignAndZeroes < 3) {
                long j = (iSkipSignAndZeroes == 2 ? 2000000000L : 1000000000L) + ((long) parseInt(cArr, i3, i4 + i3));
                if (z) {
                    long j2 = -j;
                    if (j2 >= -2147483648L) {
                        this.mValue = (int) j2;
                        return;
                    }
                } else if (j <= 2147483647L) {
                    this.mValue = (int) j;
                    return;
                }
            }
            throw new IllegalArgumentException("value \"" + lexicalDesc(cArr, i, i2) + "\" not a valid 32-bit integer: overflow.");
        }
    }

    public static final class LongDecoder extends DecoderBase {
        protected long mValue;

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecoderBase
        public String getType() {
            return "long";
        }

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder
        public void decode(char[] cArr, int i, int i2) throws IllegalArgumentException {
            int iSkipSignAndZeroes;
            char c = cArr[i];
            boolean z = c == '-';
            if (z || c == '+') {
                iSkipSignAndZeroes = skipSignAndZeroes(cArr, c, true, i, i2);
            } else {
                iSkipSignAndZeroes = skipSignAndZeroes(cArr, c, false, i, i2);
            }
            int i3 = this.mNextPtr;
            int i4 = i2 - i3;
            if (i4 == 0) {
                if (z) {
                    iSkipSignAndZeroes = -iSkipSignAndZeroes;
                }
                this.mValue = iSkipSignAndZeroes;
                return;
            }
            verifyDigits(cArr, i, i2, i3);
            if (i4 <= 8) {
                int i5 = parseInt(iSkipSignAndZeroes, cArr, i3, i4 + i3);
                if (z) {
                    i5 = -i5;
                }
                this.mValue = i5;
                return;
            }
            int i6 = i3 - 1;
            int i7 = i4 + 1;
            if (i7 <= 18) {
                long j = parseLong(cArr, i6, i7 + i6);
                if (z) {
                    j = -j;
                }
                this.mValue = j;
                return;
            }
            this.mValue = parseUsingBD(new String(cArr, i6, i7), z);
        }

        private long parseUsingBD(String str, boolean z) {
            BigInteger bigInteger = new BigInteger(str);
            if (z) {
                BigInteger bigIntegerNegate = bigInteger.negate();
                if (bigIntegerNegate.compareTo(BD_MIN_LONG) >= 0) {
                    return bigIntegerNegate.longValue();
                }
            } else if (bigInteger.compareTo(BD_MAX_LONG) <= 0) {
                return bigInteger.longValue();
            }
            throw new IllegalArgumentException("value \"" + lexicalDesc(str) + "\" not a valid long: overflow.");
        }
    }

    public static final class FloatDecoder extends DecoderBase {
        protected float mValue;

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecoderBase
        public String getType() {
            return "float";
        }

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder
        public void decode(char[] cArr, int i, int i2) throws IllegalArgumentException {
            int i3 = i2 - i;
            if (i3 == 3) {
                char c = cArr[i];
                if (c == 'I') {
                    if (cArr[i + 1] == 'N' && cArr[i + 2] == 'F') {
                        this.mValue = Float.POSITIVE_INFINITY;
                        return;
                    }
                } else if (c == 'N' && cArr[i + 1] == 'a' && cArr[i + 2] == 'N') {
                    this.mValue = Float.NaN;
                    return;
                }
            } else if (i3 == 4 && cArr[i] == '-' && cArr[i + 1] == 'I' && cArr[i + 2] == 'N' && cArr[i + 3] == 'F') {
                this.mValue = Float.NEGATIVE_INFINITY;
                return;
            }
            String str = new String(cArr, i, i3);
            try {
                this.mValue = Float.parseFloat(str);
            } catch (NumberFormatException unused) {
                throw constructInvalidValue(str);
            }
        }
    }

    public static final class DoubleDecoder extends DecoderBase {
        protected double mValue;

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecoderBase
        public String getType() {
            return "double";
        }

        @Override // org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedValueDecoder
        public void decode(char[] cArr, int i, int i2) throws IllegalArgumentException {
            int i3 = i2 - i;
            if (i3 == 3) {
                char c = cArr[i];
                if (c == 'I') {
                    if (cArr[i + 1] == 'N' && cArr[i + 2] == 'F') {
                        this.mValue = Double.POSITIVE_INFINITY;
                        return;
                    }
                } else if (c == 'N' && cArr[i + 1] == 'a' && cArr[i + 2] == 'N') {
                    this.mValue = Double.NaN;
                    return;
                }
            } else if (i3 == 4 && cArr[i] == '-' && cArr[i + 1] == 'I' && cArr[i + 2] == 'N' && cArr[i + 3] == 'F') {
                this.mValue = Double.NEGATIVE_INFINITY;
                return;
            }
            String str = new String(cArr, i, i3);
            try {
                this.mValue = Double.parseDouble(str);
            } catch (NumberFormatException unused) {
                throw constructInvalidValue(str);
            }
        }
    }
}
