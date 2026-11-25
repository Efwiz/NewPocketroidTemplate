package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class DFA {
    public static final boolean debug = false;
    protected short[] accept;
    protected int decisionNumber;
    protected short[] eof;
    protected short[] eot;
    protected char[] max;
    protected char[] min;
    protected BaseRecognizer recognizer;
    protected short[] special;
    protected short[][] transition;

    protected void error(NoViableAltException noViableAltException) {
    }

    public int specialStateTransition(int i, IntStream intStream) throws NoViableAltException {
        return -1;
    }

    public int predict(IntStream intStream) throws RecognitionException {
        short s;
        int iMark = intStream.mark();
        int iSpecialStateTransition = 0;
        while (true) {
            try {
                short s2 = this.special[iSpecialStateTransition];
                if (s2 >= 0) {
                    iSpecialStateTransition = specialStateTransition(s2, intStream);
                    if (iSpecialStateTransition == -1) {
                        noViableAlt(iSpecialStateTransition, intStream);
                        intStream.rewind(iMark);
                        return 0;
                    }
                    intStream.consume();
                } else {
                    short s3 = this.accept[iSpecialStateTransition];
                    if (s3 >= 1) {
                        intStream.rewind(iMark);
                        return s3;
                    }
                    char cLA = (char) intStream.LA(1);
                    char c = this.min[iSpecialStateTransition];
                    if (cLA < c || cLA > this.max[iSpecialStateTransition]) {
                        short s4 = this.eot[iSpecialStateTransition];
                        if (s4 < 0) {
                            if (cLA != 65535 || (s = this.eof[iSpecialStateTransition]) < 0) {
                                noViableAlt(iSpecialStateTransition, intStream);
                                intStream.rewind(iMark);
                                return 0;
                            }
                            short s5 = this.accept[s];
                            intStream.rewind(iMark);
                            return s5;
                        }
                        intStream.consume();
                        iSpecialStateTransition = s4;
                    } else {
                        short s6 = this.transition[iSpecialStateTransition][cLA - c];
                        if (s6 < 0 && (s6 = this.eot[iSpecialStateTransition]) < 0) {
                            noViableAlt(iSpecialStateTransition, intStream);
                            intStream.rewind(iMark);
                            return 0;
                        }
                        intStream.consume();
                        iSpecialStateTransition = s6;
                    }
                }
            } catch (Throwable th) {
                intStream.rewind(iMark);
                throw th;
            }
        }
    }

    protected void noViableAlt(int i, IntStream intStream) throws NoViableAltException {
        if (this.recognizer.state.backtracking > 0) {
            this.recognizer.state.failed = true;
        } else {
            NoViableAltException noViableAltException = new NoViableAltException(getDescription(), this.decisionNumber, i, intStream);
            error(noViableAltException);
            throw noViableAltException;
        }
    }

    public String getDescription() {
        return "n/a";
    }

    public static short[] unpackEncodedString(String str) {
        int iCharAt = 0;
        for (int i = 0; i < str.length(); i += 2) {
            iCharAt += str.charAt(i);
        }
        short[] sArr = new short[iCharAt];
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3 += 2) {
            char cCharAt = str.charAt(i3);
            char cCharAt2 = str.charAt(i3 + 1);
            int i4 = 1;
            while (i4 <= cCharAt) {
                sArr[i2] = (short) cCharAt2;
                i4++;
                i2++;
            }
        }
        return sArr;
    }

    public static char[] unpackEncodedStringToUnsignedChars(String str) {
        int iCharAt = 0;
        for (int i = 0; i < str.length(); i += 2) {
            iCharAt += str.charAt(i);
        }
        char[] cArr = new char[iCharAt];
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3 += 2) {
            char cCharAt = str.charAt(i3);
            char cCharAt2 = str.charAt(i3 + 1);
            int i4 = 1;
            while (i4 <= cCharAt) {
                cArr[i2] = cCharAt2;
                i4++;
                i2++;
            }
        }
        return cArr;
    }
}
