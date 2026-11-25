package org.eclipse.jdt.internal.compiler.parser.diagnose;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.parser.Scanner;
import org.eclipse.jdt.internal.compiler.parser.TerminalTokens;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class LexStream implements TerminalTokens {
    public static final int IS_AFTER_JUMP = 1;
    public static final int LBRACE_MISSING = 2;
    private boolean awaitingColonColon;
    private int[] intervalEndToSkip;
    private int[] intervalFlagsToSkip;
    private int[] intervalStartToSkip;
    private Scanner scanner;
    private Token[] tokenCache;
    private int currentIndex = -1;
    private int previousInterval = -1;
    private int currentInterval = -1;
    private int tokenCacheIndex = 0;
    private int tokenCacheEOFIndex = Integer.MAX_VALUE;

    public int badtoken() {
        return 0;
    }

    public int previous(int i) {
        if (i > 0) {
            return i - 1;
        }
        return 0;
    }

    public static class Token {
        int end;
        int flags;
        int kind;
        int line;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        char[] f630name;
        int start;

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.f630name).append('[').append(this.kind).append("]{");
            stringBuffer.append(this.start).append(',').append(this.end).append('}').append(this.line);
            return stringBuffer.toString();
        }
    }

    public LexStream(int i, Scanner scanner, int[] iArr, int[] iArr2, int[] iArr3, int i2, int i3, int i4) {
        Token[] tokenArr = new Token[i];
        this.tokenCache = tokenArr;
        tokenArr[0] = new Token();
        this.tokenCache[0].kind = i2;
        this.tokenCache[0].f630name = CharOperation.NO_CHAR;
        this.tokenCache[0].start = i3;
        this.tokenCache[0].end = i3;
        this.tokenCache[0].line = 0;
        this.intervalStartToSkip = iArr;
        this.intervalEndToSkip = iArr2;
        this.intervalFlagsToSkip = iArr3;
        this.awaitingColonColon = false;
        scanner.resetTo(i3, i4);
        this.scanner = scanner;
    }

    private void readTokenFromScanner() {
        int length = this.tokenCache.length;
        boolean z = true;
        while (z) {
            try {
                int nextToken = this.scanner.getNextToken();
                if (nextToken == 83) {
                    this.awaitingColonColon = true;
                } else if (nextToken == 7) {
                    this.awaitingColonColon = false;
                }
                if (nextToken != 60) {
                    int currentTokenStartPosition = this.scanner.getCurrentTokenStartPosition();
                    int currentTokenEndPosition = this.scanner.getCurrentTokenEndPosition();
                    int i = this.currentInterval;
                    int i2 = i + 1;
                    int[] iArr = this.intervalStartToSkip;
                    if (iArr.length == 0 || i2 >= iArr.length || currentTokenStartPosition < iArr[i2]) {
                        Token token = new Token();
                        token.kind = nextToken;
                        token.f630name = this.scanner.getCurrentTokenSource();
                        token.start = currentTokenStartPosition;
                        token.end = currentTokenEndPosition;
                        token.line = Util.getLineNumber(currentTokenEndPosition, this.scanner.lineEnds, 0, this.scanner.linePtr);
                        int i3 = this.currentInterval;
                        if (i3 != this.previousInterval && (this.intervalFlagsToSkip[i3] & 2) == 0) {
                            token.flags = 1;
                            if ((this.intervalFlagsToSkip[this.currentInterval] & 1) != 0) {
                                token.flags |= 2;
                            }
                        }
                        this.previousInterval = this.currentInterval;
                        Token[] tokenArr = this.tokenCache;
                        int i4 = this.tokenCacheIndex + 1;
                        this.tokenCacheIndex = i4;
                        tokenArr[i4 % length] = token;
                    } else {
                        Scanner scanner = this.scanner;
                        int[] iArr2 = this.intervalEndToSkip;
                        int i5 = i + 1;
                        this.currentInterval = i5;
                        scanner.resetTo(iArr2[i5] + 1, scanner.eofPosition - 1);
                    }
                } else {
                    int currentTokenStartPosition2 = this.scanner.getCurrentTokenStartPosition();
                    int currentTokenEndPosition2 = this.scanner.getCurrentTokenEndPosition();
                    Token token2 = new Token();
                    token2.kind = nextToken;
                    token2.f630name = CharOperation.NO_CHAR;
                    token2.start = currentTokenStartPosition2;
                    token2.end = currentTokenEndPosition2;
                    token2.line = Util.getLineNumber(currentTokenEndPosition2, this.scanner.lineEnds, 0, this.scanner.linePtr);
                    Token[] tokenArr2 = this.tokenCache;
                    int i6 = this.tokenCacheIndex + 1;
                    this.tokenCacheIndex = i6;
                    tokenArr2[i6 % length] = token2;
                    this.tokenCacheEOFIndex = i6;
                }
                z = false;
            } catch (InvalidInputException unused) {
            }
        }
    }

    public Token token(int i) {
        if (i < 0) {
            Token token = new Token();
            token.kind = 60;
            token.f630name = CharOperation.NO_CHAR;
            return token;
        }
        int i2 = this.tokenCacheEOFIndex;
        if (i2 >= 0 && i > i2) {
            return token(i2);
        }
        int length = this.tokenCache.length;
        int i3 = this.tokenCacheIndex;
        if (i > i3) {
            int i4 = i - i3;
            while (true) {
                int i5 = i4 - 1;
                if (i4 == 0) {
                    break;
                }
                readTokenFromScanner();
                i4 = i5;
            }
        } else if (i3 - length >= i) {
            return null;
        }
        return this.tokenCache[i % length];
    }

    public int getToken() {
        int next = next(this.currentIndex);
        this.currentIndex = next;
        return next;
    }

    public int next(int i) {
        int i2 = this.tokenCacheEOFIndex;
        return i < i2 ? i + 1 : i2;
    }

    public boolean afterEol(int i) {
        return i < 1 || line(i + (-1)) < line(i);
    }

    public void reset() {
        this.currentIndex = -1;
    }

    public void reset(int i) {
        this.currentIndex = previous(i);
    }

    public int kind(int i) {
        return token(i).kind;
    }

    public char[] name(int i) {
        return token(i).f630name;
    }

    public int line(int i) {
        return token(i).line;
    }

    public int start(int i) {
        return token(i).start;
    }

    public int end(int i) {
        return token(i).end;
    }

    public int flags(int i) {
        return token(i).flags;
    }

    public boolean isInsideStream(int i) {
        int i2 = this.tokenCacheEOFIndex;
        if (i2 >= 0 && i > i2) {
            return false;
        }
        int i3 = this.tokenCacheIndex;
        return i > i3 || i3 - this.tokenCache.length < i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = new String(this.scanner.source);
        int i = this.currentIndex;
        int i2 = 0;
        int i3 = -1;
        if (i < 0) {
            while (true) {
                int[] iArr = this.intervalStartToSkip;
                if (i2 >= iArr.length) {
                    break;
                }
                int i4 = iArr[i2];
                int i5 = this.intervalEndToSkip[i2];
                stringBuffer.append(str.substring(i3 + 1, i4));
                stringBuffer.append("<@");
                stringBuffer.append(str.substring(i4, i5 + 1));
                stringBuffer.append("@>");
                i2++;
                i3 = i5;
            }
            stringBuffer.append(str.substring(i3 + 1));
        } else {
            Token token = token(i);
            int i6 = token.kind;
            int i7 = token.start;
            int i8 = token.end;
            while (true) {
                int[] iArr2 = this.intervalStartToSkip;
                if (i2 >= iArr2.length) {
                    break;
                }
                int i9 = iArr2[i2];
                int i10 = this.intervalEndToSkip[i2];
                if (i7 >= i3 && i8 <= i9) {
                    stringBuffer.append(str.substring(i3 + 1, i7));
                    stringBuffer.append("<#");
                    int i11 = i8 + 1;
                    stringBuffer.append(str.substring(i7, i11));
                    stringBuffer.append("#>");
                    stringBuffer.append(str.substring(i11, i9));
                } else {
                    stringBuffer.append(str.substring(i3 + 1, i9));
                }
                stringBuffer.append("<@");
                stringBuffer.append(str.substring(i9, i10 + 1));
                stringBuffer.append("@>");
                i2++;
                i3 = i10;
            }
            if (i7 >= i3) {
                stringBuffer.append(str.substring(i3 + 1, i7));
                stringBuffer.append("<#");
                if (i6 == 60) {
                    stringBuffer.append("EOF#>");
                } else {
                    int i12 = i8 + 1;
                    stringBuffer.append(str.substring(i7, i12));
                    stringBuffer.append("#>");
                    stringBuffer.append(str.substring(i12));
                }
            } else {
                stringBuffer.append(str.substring(i3 + 1));
            }
        }
        return stringBuffer.toString();
    }

    public boolean awaitingColonColon() {
        return this.awaitingColonColon;
    }
}
