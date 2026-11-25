package org.apache.xerces.impl.xpath.regex;

import java.io.Serializable;
import java.text.CharacterIterator;
import java.util.Locale;

/* JADX INFO: loaded from: classes13.dex */
public class RegularExpression implements Serializable {
    static final int CARRIAGE_RETURN = 13;
    static final boolean DEBUG = false;
    static final int EXTENDED_COMMENT = 16;
    static final int IGNORE_CASE = 2;
    static final int LINE_FEED = 10;
    static final int LINE_SEPARATOR = 8232;
    static final int MULTIPLE_LINES = 8;
    static final int PARAGRAPH_SEPARATOR = 8233;
    static final int PROHIBIT_FIXED_STRING_OPTIMIZATION = 256;
    static final int PROHIBIT_HEAD_CHARACTER_OPTIMIZATION = 128;
    static final int SINGLE_LINE = 4;
    static final int SPECIAL_COMMA = 1024;
    static final int UNICODE_WORD_BOUNDARY = 64;
    static final int USE_UNICODE_CATEGORY = 32;
    private static final int WT_IGNORE = 0;
    private static final int WT_LETTER = 1;
    private static final int WT_OTHER = 2;
    static final int XMLSCHEMA_MODE = 512;
    private static final long serialVersionUID = 6242499334195006401L;
    transient Context context;
    transient RangeToken firstChar;
    transient String fixedString;
    transient boolean fixedStringOnly;
    transient int fixedStringOptions;
    transient BMPattern fixedStringTable;
    boolean hasBackReferences;
    transient int minlength;
    int nofparen;
    transient int numberOfClosures;
    transient Op operations;
    int options;
    String regex;
    Token tokentree;

    static final class CharArrayTarget extends ExpressionTarget {
        char[] target;

        CharArrayTarget(char[] cArr) {
            this.target = cArr;
        }

        private final boolean regionMatches(int i, int i2, int i3, int i4) {
            while (true) {
                int i5 = i4 - 1;
                if (i4 <= 0) {
                    return true;
                }
                char[] cArr = this.target;
                int i6 = i + 1;
                int i7 = i3 + 1;
                if (cArr[i] != cArr[i3]) {
                    return false;
                }
                i4 = i5;
                i = i6;
                i3 = i7;
            }
        }

        private final boolean regionMatches(int i, int i2, String str, int i3) {
            int i4 = 0;
            while (true) {
                int i5 = i3 - 1;
                if (i3 <= 0) {
                    return true;
                }
                int i6 = i + 1;
                int i7 = i4 + 1;
                if (this.target[i] != str.charAt(i4)) {
                    return false;
                }
                i4 = i7;
                i3 = i5;
                i = i6;
            }
        }

        private final boolean regionMatchesIgnoreCase(int i, int i2, int i3, int i4) {
            char upperCase;
            char upperCase2;
            while (true) {
                int i5 = i4 - 1;
                if (i4 <= 0) {
                    return true;
                }
                char[] cArr = this.target;
                int i6 = i + 1;
                char c = cArr[i];
                int i7 = i3 + 1;
                char c2 = cArr[i3];
                if (c != c2 && (upperCase = Character.toUpperCase(c)) != (upperCase2 = Character.toUpperCase(c2)) && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)) {
                    return false;
                }
                i4 = i5;
                i = i6;
                i3 = i7;
            }
        }

        private final boolean regionMatchesIgnoreCase(int i, int i2, String str, int i3) {
            char upperCase;
            char upperCase2;
            int i4 = 0;
            while (true) {
                int i5 = i3 - 1;
                if (i3 <= 0) {
                    return true;
                }
                int i6 = i + 1;
                char c = this.target[i];
                int i7 = i4 + 1;
                char cCharAt = str.charAt(i4);
                if (c != cCharAt && (upperCase = Character.toUpperCase(c)) != (upperCase2 = Character.toUpperCase(cCharAt)) && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)) {
                    return false;
                }
                i4 = i7;
                i3 = i5;
                i = i6;
            }
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        char charAt(int i) {
            return this.target[i];
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final boolean regionMatches(boolean z, int i, int i2, int i3, int i4) {
            if (i < 0 || i2 - i < i4) {
                return false;
            }
            return z ? regionMatchesIgnoreCase(i, i2, i3, i4) : regionMatches(i, i2, i3, i4);
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final boolean regionMatches(boolean z, int i, int i2, String str, int i3) {
            if (i < 0 || i2 - i < i3) {
                return false;
            }
            return z ? regionMatchesIgnoreCase(i, i2, str, i3) : regionMatches(i, i2, str, i3);
        }

        final void resetTarget(char[] cArr) {
            this.target = cArr;
        }
    }

    static final class CharacterIteratorTarget extends ExpressionTarget {
        CharacterIterator target;

        CharacterIteratorTarget(CharacterIterator characterIterator) {
            this.target = characterIterator;
        }

        private final boolean regionMatches(int i, int i2, int i3, int i4) {
            while (true) {
                int i5 = i4 - 1;
                if (i4 <= 0) {
                    return true;
                }
                int i6 = i + 1;
                int i7 = i3 + 1;
                if (this.target.setIndex(i) != this.target.setIndex(i3)) {
                    return false;
                }
                i4 = i5;
                i = i6;
                i3 = i7;
            }
        }

        private final boolean regionMatches(int i, int i2, String str, int i3) {
            int i4 = 0;
            while (true) {
                int i5 = i3 - 1;
                if (i3 <= 0) {
                    return true;
                }
                int i6 = i + 1;
                int i7 = i4 + 1;
                if (this.target.setIndex(i) != str.charAt(i4)) {
                    return false;
                }
                i4 = i7;
                i3 = i5;
                i = i6;
            }
        }

        private final boolean regionMatchesIgnoreCase(int i, int i2, int i3, int i4) {
            char upperCase;
            char upperCase2;
            while (true) {
                int i5 = i4 - 1;
                if (i4 <= 0) {
                    return true;
                }
                int i6 = i + 1;
                char index = this.target.setIndex(i);
                int i7 = i3 + 1;
                char index2 = this.target.setIndex(i3);
                if (index != index2 && (upperCase = Character.toUpperCase(index)) != (upperCase2 = Character.toUpperCase(index2)) && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)) {
                    return false;
                }
                i4 = i5;
                i = i6;
                i3 = i7;
            }
        }

        private final boolean regionMatchesIgnoreCase(int i, int i2, String str, int i3) {
            char upperCase;
            char upperCase2;
            int i4 = 0;
            while (true) {
                int i5 = i3 - 1;
                if (i3 <= 0) {
                    return true;
                }
                int i6 = i + 1;
                char index = this.target.setIndex(i);
                int i7 = i4 + 1;
                char cCharAt = str.charAt(i4);
                if (index != cCharAt && (upperCase = Character.toUpperCase(index)) != (upperCase2 = Character.toUpperCase(cCharAt)) && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)) {
                    return false;
                }
                i4 = i7;
                i3 = i5;
                i = i6;
            }
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final char charAt(int i) {
            return this.target.setIndex(i);
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final boolean regionMatches(boolean z, int i, int i2, int i3, int i4) {
            if (i < 0 || i2 - i < i4) {
                return false;
            }
            return z ? regionMatchesIgnoreCase(i, i2, i3, i4) : regionMatches(i, i2, i3, i4);
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final boolean regionMatches(boolean z, int i, int i2, String str, int i3) {
            if (i < 0 || i2 - i < i3) {
                return false;
            }
            return z ? regionMatchesIgnoreCase(i, i2, str, i3) : regionMatches(i, i2, str, i3);
        }

        final void resetTarget(CharacterIterator characterIterator) {
            this.target = characterIterator;
        }
    }

    static final class ClosureContext {
        int[] offsets = new int[4];
        int currentIndex = 0;

        ClosureContext() {
        }

        private int[] expandOffsets() {
            int[] iArr = this.offsets;
            int[] iArr2 = new int[iArr.length << 1];
            System.arraycopy(iArr, 0, iArr2, 0, this.currentIndex);
            return iArr2;
        }

        void addOffset(int i) {
            if (this.currentIndex == this.offsets.length) {
                this.offsets = expandOffsets();
            }
            int[] iArr = this.offsets;
            int i2 = this.currentIndex;
            this.currentIndex = i2 + 1;
            iArr[i2] = i;
        }

        boolean contains(int i) {
            for (int i2 = 0; i2 < this.currentIndex; i2++) {
                if (this.offsets[i2] == i) {
                    return true;
                }
            }
            return false;
        }

        void reset() {
            this.currentIndex = 0;
        }
    }

    static final class Context {
        private CharArrayTarget charArrayTarget;
        private CharacterIteratorTarget characterIteratorTarget;
        ClosureContext[] closureContexts;
        boolean inuse = false;
        int length;
        int limit;
        Match match;
        int start;
        private StringTarget stringTarget;
        ExpressionTarget target;

        Context() {
        }

        private void resetCommon(int i) {
            this.length = this.limit - this.start;
            setInUse(true);
            this.match = null;
            ClosureContext[] closureContextArr = this.closureContexts;
            if (closureContextArr == null || closureContextArr.length != i) {
                this.closureContexts = new ClosureContext[i];
            }
            for (int i2 = 0; i2 < i; i2++) {
                ClosureContext[] closureContextArr2 = this.closureContexts;
                ClosureContext closureContext = closureContextArr2[i2];
                if (closureContext == null) {
                    closureContextArr2[i2] = new ClosureContext();
                } else {
                    closureContext.reset();
                }
            }
        }

        void reset(String str, int i, int i2, int i3) {
            StringTarget stringTarget = this.stringTarget;
            if (stringTarget == null) {
                this.stringTarget = new StringTarget(str);
            } else {
                stringTarget.resetTarget(str);
            }
            this.target = this.stringTarget;
            this.start = i;
            this.limit = i2;
            resetCommon(i3);
        }

        void reset(CharacterIterator characterIterator, int i, int i2, int i3) {
            CharacterIteratorTarget characterIteratorTarget = this.characterIteratorTarget;
            if (characterIteratorTarget == null) {
                this.characterIteratorTarget = new CharacterIteratorTarget(characterIterator);
            } else {
                characterIteratorTarget.resetTarget(characterIterator);
            }
            this.target = this.characterIteratorTarget;
            this.start = i;
            this.limit = i2;
            resetCommon(i3);
        }

        void reset(char[] cArr, int i, int i2, int i3) {
            CharArrayTarget charArrayTarget = this.charArrayTarget;
            if (charArrayTarget == null) {
                this.charArrayTarget = new CharArrayTarget(cArr);
            } else {
                charArrayTarget.resetTarget(cArr);
            }
            this.target = this.charArrayTarget;
            this.start = i;
            this.limit = i2;
            resetCommon(i3);
        }

        synchronized void setInUse(boolean z) {
            this.inuse = z;
        }
    }

    static abstract class ExpressionTarget {
        ExpressionTarget() {
        }

        abstract char charAt(int i);

        abstract boolean regionMatches(boolean z, int i, int i2, int i3, int i4);

        abstract boolean regionMatches(boolean z, int i, int i2, String str, int i3);
    }

    static final class StringTarget extends ExpressionTarget {
        private String target;

        StringTarget(String str) {
            this.target = str;
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final char charAt(int i) {
            return this.target.charAt(i);
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final boolean regionMatches(boolean z, int i, int i2, int i3, int i4) {
            if (i2 - i < i4) {
                return false;
            }
            if (z) {
                String str = this.target;
                return str.regionMatches(true, i, str, i3, i4);
            }
            String str2 = this.target;
            return str2.regionMatches(i, str2, i3, i4);
        }

        @Override // org.apache.xerces.impl.xpath.regex.RegularExpression.ExpressionTarget
        final boolean regionMatches(boolean z, int i, int i2, String str, int i3) {
            if (i2 - i < i3) {
                return false;
            }
            return z ? this.target.regionMatches(true, i, str, 0, i3) : this.target.regionMatches(i, str, 0, i3);
        }

        final void resetTarget(String str) {
            this.target = str;
        }
    }

    public RegularExpression(String str) throws ParseException {
        this(str, null);
    }

    public RegularExpression(String str, String str2) throws ParseException {
        this.hasBackReferences = false;
        this.operations = null;
        this.context = null;
        this.firstChar = null;
        this.fixedString = null;
        this.fixedStringTable = null;
        this.fixedStringOnly = false;
        setPattern(str, str2);
    }

    public RegularExpression(String str, String str2, Locale locale) throws ParseException {
        this.hasBackReferences = false;
        this.operations = null;
        this.context = null;
        this.firstChar = null;
        this.fixedString = null;
        this.fixedStringTable = null;
        this.fixedStringOnly = false;
        setPattern(str, str2, locale);
    }

    RegularExpression(String str, Token token, int i, boolean z, int i2) {
        this.operations = null;
        this.context = null;
        this.firstChar = null;
        this.fixedString = null;
        this.fixedStringTable = null;
        this.fixedStringOnly = false;
        this.regex = str;
        this.tokentree = token;
        this.nofparen = i;
        this.options = i2;
        this.hasBackReferences = z;
    }

    private Op compile(Token token, Op op, boolean z) {
        Op opCreateChar;
        Op.ChildOp childOpCreateClosure;
        Op opCompile;
        Op opCompile2;
        int parenNumber;
        Op opCompile3;
        int i;
        int i2 = token.type;
        int i3 = 0;
        switch (i2) {
            case 0:
                opCreateChar = Op.createChar(token.getChar());
                break;
            case 1:
                if (z) {
                    while (i3 < token.size()) {
                        op = compile(token.getChild(i3), op, true);
                        i3++;
                    }
                    return op;
                }
                for (int size = token.size() - 1; size >= 0; size--) {
                    op = compile(token.getChild(size), op, false);
                }
                return op;
            case 2:
                Op.UnionOp unionOpCreateUnion = Op.createUnion(token.size());
                while (i3 < token.size()) {
                    unionOpCreateUnion.addElement(compile(token.getChild(i3), op, z));
                    i3++;
                }
                return unionOpCreateUnion;
            case 3:
            case 9:
                Token child = token.getChild(0);
                int min = token.getMin();
                int max = token.getMax();
                if (min >= 0 && min == max) {
                    while (i3 < min) {
                        op = compile(child, op, z);
                        i3++;
                    }
                    return op;
                }
                if (min > 0 && max > 0) {
                    max -= min;
                }
                if (max > 0) {
                    Op op2 = op;
                    int i4 = 0;
                    while (i4 < max) {
                        Op.ChildOp childOpCreateQuestion = Op.createQuestion(token.type == 9);
                        childOpCreateQuestion.next = op;
                        childOpCreateQuestion.setChild(compile(child, op2, z));
                        i4++;
                        op2 = childOpCreateQuestion;
                    }
                    opCompile = op2;
                } else {
                    if (token.type == 9) {
                        childOpCreateClosure = Op.createNonGreedyClosure();
                    } else {
                        int i5 = this.numberOfClosures;
                        this.numberOfClosures = i5 + 1;
                        childOpCreateClosure = Op.createClosure(i5);
                    }
                    childOpCreateClosure.next = op;
                    childOpCreateClosure.setChild(compile(child, childOpCreateClosure, z));
                    opCompile = childOpCreateClosure;
                }
                if (min <= 0) {
                    return opCompile;
                }
                while (i3 < min) {
                    opCompile = compile(child, opCompile, z);
                    i3++;
                }
                return opCompile;
            case 4:
            case 5:
                opCreateChar = Op.createRange(token);
                break;
            case 6:
                if (token.getParenNumber() == 0) {
                    return compile(token.getChild(0), op, z);
                }
                int parenNumber2 = token.getParenNumber();
                if (z) {
                    opCompile2 = compile(token.getChild(0), Op.createCapture(parenNumber2, op), z);
                    parenNumber = -token.getParenNumber();
                } else {
                    opCompile2 = compile(token.getChild(0), Op.createCapture(-parenNumber2, op), z);
                    parenNumber = token.getParenNumber();
                }
                return Op.createCapture(parenNumber, opCompile2);
            case 7:
                return op;
            case 8:
                opCreateChar = Op.createAnchor(token.getChar());
                break;
            case 10:
                opCreateChar = Op.createString(token.getString());
                break;
            case 11:
                opCreateChar = Op.createDot();
                break;
            case 12:
                opCreateChar = Op.createBackReference(token.getReferenceNumber());
                break;
            default:
                switch (i2) {
                    case 20:
                        opCompile3 = compile(token.getChild(0), null, false);
                        i = 20;
                        break;
                    case 21:
                        opCompile3 = compile(token.getChild(0), null, false);
                        i = 21;
                        break;
                    case 22:
                        opCompile3 = compile(token.getChild(0), null, true);
                        i = 22;
                        break;
                    case 23:
                        opCompile3 = compile(token.getChild(0), null, true);
                        i = 23;
                        break;
                    case 24:
                        return Op.createIndependent(op, compile(token.getChild(0), null, z));
                    case 25:
                        Op opCompile4 = compile(token.getChild(0), null, z);
                        Token.ModifierToken modifierToken = (Token.ModifierToken) token;
                        return Op.createModifier(op, opCompile4, modifierToken.getOptions(), modifierToken.getOptionsMask());
                    case 26:
                        Token.ConditionToken conditionToken = (Token.ConditionToken) token;
                        return Op.createCondition(op, conditionToken.refNumber, conditionToken.condition == null ? null : compile(conditionToken.condition, null, z), compile(conditionToken.yes, op, z), conditionToken.no != null ? compile(conditionToken.no, op, z) : null);
                    default:
                        throw new RuntimeException("Unknown token type: " + token.type);
                }
                return Op.createLook(i, op, opCompile3);
        }
        opCreateChar.next = op;
        return opCreateChar;
    }

    private synchronized void compile(Token token) {
        if (this.operations != null) {
            return;
        }
        this.numberOfClosures = 0;
        this.operations = compile(token, null, false);
    }

    private static final int getPreviousWordType(ExpressionTarget expressionTarget, int i, int i2, int i3, int i4) {
        int wordType;
        do {
            i3--;
            wordType = getWordType(expressionTarget, i, i2, i3, i4);
        } while (wordType == 0);
        return wordType;
    }

    private static final int getWordType(ExpressionTarget expressionTarget, int i, int i2, int i3, int i4) {
        if (i3 < i || i3 >= i2) {
            return 2;
        }
        return getWordType0(expressionTarget.charAt(i3), i4);
    }

    private static final int getWordType0(char c, int i) {
        if (!isSet(i, 64)) {
            if (isSet(i, 32)) {
                return Token.getRange("IsWord", true).match(c) ? 1 : 2;
            }
            return isWordChar(c) ? 1 : 2;
        }
        int type = Character.getType(c);
        if (type == 15) {
            switch (c) {
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                    return 2;
                default:
                    return 0;
            }
        }
        if (type != 16) {
            switch (type) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 8:
                case 9:
                case 10:
                case 11:
                    return 1;
                case 6:
                case 7:
                    break;
                default:
                    return 2;
            }
        }
        return 0;
    }

    private static final boolean isEOLChar(int i) {
        return i == 10 || i == 13 || i == LINE_SEPARATOR || i == PARAGRAPH_SEPARATOR;
    }

    private static final boolean isSet(int i, int i2) {
        return (i & i2) == i2;
    }

    private static final boolean isWordChar(int i) {
        if (i == 95) {
            return true;
        }
        if (i < 48 || i > 122) {
            return false;
        }
        if (i <= 57) {
            return true;
        }
        if (i < 65) {
            return false;
        }
        return i <= 90 || i >= 97;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:177:0x0318  */
    /* JADX WARN: Code duplicated, block: B:180:0x031f  */
    /* JADX WARN: Code duplicated, block: B:182:0x032d  */
    /* JADX WARN: Code duplicated, block: B:183:0x0330  */
    /* JADX WARN: Code duplicated, block: B:187:0x0339  */
    /* JADX WARN: Code duplicated, block: B:188:0x033c  */
    /* JADX WARN: Code duplicated, block: B:190:0x0340  */
    /* JADX WARN: Code duplicated, block: B:191:0x0343  */
    /* JADX WARN: Code duplicated, block: B:192:0x0346  */
    /* JADX WARN: Code duplicated, block: B:193:0x034a A[DONT_INVERT, FALL_THROUGH, PHI: r5
      0x034a: PHI (r5v9 int) = (r5v5 int), (r5v10 int) binds: [B:183:0x0330, B:192:0x0346] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:201:0x0360 A[PHI: r15
      0x0360: PHI (r15v4 int) = (r15v3 int), (r15v6 int) binds: [B:200:0x035e, B:197:0x0357] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:205:0x036e  */
    /* JADX WARN: Code duplicated, block: B:207:0x0376  */
    /* JADX WARN: Code duplicated, block: B:208:0x0386  */
    /* JADX WARN: Code duplicated, block: B:209:0x0389 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:211:0x0390 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:226:0x031e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:227:0x03ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:228:0x03b1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:229:0x03ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:230:0x03ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:232:0x0368 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:233:0x0353 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:234:0x035a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:235:0x03ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:236:0x034c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:237:0x038b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:238:0x0392 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:239:0x0335 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:0x0115  */
    /* JADX WARN: Code duplicated, block: B:55:0x0121  */
    /* JADX WARN: Failed to find 'out' block for switch in B:182:0x032d. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:183:0x0330. Please report as an issue. */
    /* JADX WARN: Path cross not found for [B:106:0x0206, B:110:0x0219], limit reached: 228 */
    /* JADX WARN: Path cross not found for [B:62:0x013d, B:66:0x0159], limit reached: 228 */
    /* JADX WARN: Switch 'out' block B:176:0x0316 for B:182:0x032d already processed. Defaulting to fallback option. */
    /* JADX WARN: Switch 'out' block B:176:0x0316 for B:183:0x0330 already processed. Defaulting to fallback option. */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:202:0x0364 -> B:59:0x012f). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private int match(org.apache.xerces.impl.xpath.regex.RegularExpression.Context r24, org.apache.xerces.impl.xpath.regex.Op r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instruction units count: 1020
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.xpath.regex.RegularExpression.match(org.apache.xerces.impl.xpath.regex.RegularExpression$Context, org.apache.xerces.impl.xpath.regex.Op, int, int, int):int");
    }

    private boolean matchChar(int i, int i2, boolean z) {
        if (z) {
            return matchIgnoreCase(i, i2);
        }
        return i == i2;
    }

    private static final boolean matchIgnoreCase(int i, int i2) {
        if (i == i2) {
            return true;
        }
        if (i > 65535 || i2 > 65535) {
            return false;
        }
        char upperCase = Character.toUpperCase((char) i);
        char upperCase2 = Character.toUpperCase((char) i2);
        return upperCase == upperCase2 || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2);
    }

    private void setPattern(String str, int i, Locale locale) throws ParseException {
        this.regex = str;
        this.options = i;
        RegexParser parserForXMLSchema = isSet(i, 512) ? new ParserForXMLSchema(locale) : new RegexParser(locale);
        this.tokentree = parserForXMLSchema.parse(this.regex, this.options);
        this.nofparen = parserForXMLSchema.parennumber;
        this.hasBackReferences = parserForXMLSchema.hasBackReferences;
        this.operations = null;
        this.context = null;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RegularExpression)) {
            return false;
        }
        RegularExpression regularExpression = (RegularExpression) obj;
        return this.regex.equals(regularExpression.regex) && this.options == regularExpression.options;
    }

    boolean equals(String str, int i) {
        return this.regex.equals(str) && this.options == i;
    }

    public int getNumberOfGroups() {
        return this.nofparen;
    }

    public String getOptions() {
        return REUtil.createOptionString(this.options);
    }

    public String getPattern() {
        return this.regex;
    }

    public int hashCode() {
        return (this.regex + "/" + getOptions()).hashCode();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean matchAnchor(ExpressionTarget expressionTarget, Op op, Context context, int i, int i2) {
        int i3;
        int i4;
        int wordType;
        int wordType2;
        int data = op.getData();
        if (data != 36) {
            if (data != 60) {
                if (data != 62) {
                    if (data != 90) {
                        if (data != 94) {
                            if (data != 98) {
                                if (data != 122) {
                                    switch (data) {
                                        case 64:
                                            if (i != context.start && (i <= context.start || !isEOLChar(expressionTarget.charAt(i - 1)))) {
                                                return false;
                                            }
                                            break;
                                        case 65:
                                            if (i != context.start) {
                                                return false;
                                            }
                                            break;
                                        case 66:
                                            if (context.length != 0 && (wordType2 = getWordType(expressionTarget, context.start, context.limit, i, i2)) != 0 && wordType2 != getPreviousWordType(expressionTarget, context.start, context.limit, i, i2)) {
                                                return false;
                                            }
                                            break;
                                    }
                                } else if (i != context.limit) {
                                    return false;
                                }
                            } else if (context.length == 0 || (wordType = getWordType(expressionTarget, context.start, context.limit, i, i2)) == 0 || wordType == getPreviousWordType(expressionTarget, context.start, context.limit, i, i2)) {
                                return false;
                            }
                        } else if (isSet(i2, 8)) {
                            if (i != context.start && (i <= context.start || i >= context.limit || !isEOLChar(expressionTarget.charAt(i - 1)))) {
                                return false;
                            }
                        } else if (i != context.start) {
                            return false;
                        }
                    } else if (i != context.limit && (((i4 = i + 1) != context.limit || !isEOLChar(expressionTarget.charAt(i))) && (i + 2 != context.limit || expressionTarget.charAt(i) != '\r' || expressionTarget.charAt(i4) != '\n'))) {
                        return false;
                    }
                } else if (context.length == 0 || i == context.start || getWordType(expressionTarget, context.start, context.limit, i, i2) != 2 || getPreviousWordType(expressionTarget, context.start, context.limit, i, i2) != 1) {
                    return false;
                }
            } else if (context.length == 0 || i == context.limit || getWordType(expressionTarget, context.start, context.limit, i, i2) != 1 || getPreviousWordType(expressionTarget, context.start, context.limit, i, i2) != 2) {
                return false;
            }
        } else if (isSet(i2, 8)) {
            if (i != context.limit && (i >= context.limit || !isEOLChar(expressionTarget.charAt(i)))) {
                return false;
            }
        } else if (i != context.limit && (((i3 = i + 1) != context.limit || !isEOLChar(expressionTarget.charAt(i))) && (i + 2 != context.limit || expressionTarget.charAt(i) != '\r' || expressionTarget.charAt(i3) != '\n'))) {
            return false;
        }
        return true;
    }

    public boolean matches(String str) {
        return matches(str, 0, str.length(), (Match) null);
    }

    public boolean matches(String str, int i, int i2) {
        return matches(str, i, i2, (Match) null);
    }

    public boolean matches(String str, int i, int i2, Match match) {
        Context context;
        int i3;
        int iMatch;
        int i4;
        synchronized (this) {
            if (this.operations == null) {
                prepare();
            }
            if (this.context == null) {
                this.context = new Context();
            }
        }
        synchronized (this.context) {
            context = this.context.inuse ? new Context() : this.context;
            context.reset(str, i, i2, this.numberOfClosures);
        }
        if (match != null) {
            match.setNumberOfGroups(this.nofparen);
            match.setSource(str);
        } else if (this.hasBackReferences) {
            match = new Match();
            match.setNumberOfGroups(this.nofparen);
        }
        context.match = match;
        if (isSet(this.options, 512)) {
            int iMatch2 = match(context, this.operations, context.start, 1, this.options);
            if (iMatch2 != context.limit) {
                return false;
            }
            if (context.match != null) {
                context.match.setBeginning(0, context.start);
                context.match.setEnd(0, iMatch2);
            }
            context.setInUse(false);
            return true;
        }
        if (this.fixedStringOnly) {
            int iMatches = this.fixedStringTable.matches(str, context.start, context.limit);
            if (iMatches < 0) {
                context.setInUse(false);
                return false;
            }
            if (context.match != null) {
                context.match.setBeginning(0, iMatches);
                context.match.setEnd(0, iMatches + this.fixedString.length());
            }
            context.setInUse(false);
            return true;
        }
        if (this.fixedString != null && this.fixedStringTable.matches(str, context.start, context.limit) < 0) {
            context.setInUse(false);
            return false;
        }
        int i5 = context.limit - this.minlength;
        Op op = this.operations;
        int iMatch3 = -1;
        if (op == null || op.type != 7 || this.operations.getChild().type != 0) {
            RangeToken rangeToken = this.firstChar;
            if (rangeToken != null) {
                int i6 = context.start;
                while (i6 <= i5) {
                    int iCharAt = str.charAt(i6);
                    if (REUtil.isHighSurrogate(iCharAt) && (i4 = i6 + 1) < context.limit) {
                        iCharAt = REUtil.composeFromSurrogates(iCharAt, str.charAt(i4));
                    }
                    if (rangeToken.match(iCharAt)) {
                        iMatch3 = match(context, this.operations, i6, 1, this.options);
                        if (iMatch3 >= 0) {
                            break;
                        }
                    }
                    i6++;
                }
                iMatch = iMatch3;
                i3 = i6;
            } else {
                i3 = context.start;
                while (i3 <= i5) {
                    iMatch3 = match(context, this.operations, i3, 1, this.options);
                    if (iMatch3 >= 0) {
                        break;
                    }
                    i3++;
                }
                iMatch = iMatch3;
            }
        } else if (isSet(this.options, 4)) {
            i3 = context.start;
            iMatch = match(context, this.operations, context.start, 1, this.options);
        } else {
            int i7 = context.start;
            boolean z = true;
            while (i7 <= i5) {
                if (isEOLChar(str.charAt(i7))) {
                    z = true;
                } else {
                    if (z) {
                        iMatch3 = match(context, this.operations, i7, 1, this.options);
                        if (iMatch3 >= 0) {
                            break;
                        }
                    }
                    z = false;
                }
                i7++;
            }
            iMatch = iMatch3;
            i3 = i7;
        }
        if (iMatch < 0) {
            context.setInUse(false);
            return false;
        }
        if (context.match != null) {
            context.match.setBeginning(0, i3);
            context.match.setEnd(0, iMatch);
        }
        context.setInUse(false);
        return true;
    }

    public boolean matches(String str, Match match) {
        return matches(str, 0, str.length(), match);
    }

    public boolean matches(CharacterIterator characterIterator) {
        return matches(characterIterator, (Match) null);
    }

    public boolean matches(CharacterIterator characterIterator, Match match) {
        Context context;
        int i;
        int iMatch;
        int i2;
        int beginIndex = characterIterator.getBeginIndex();
        int endIndex = characterIterator.getEndIndex();
        synchronized (this) {
            if (this.operations == null) {
                prepare();
            }
            if (this.context == null) {
                this.context = new Context();
            }
        }
        synchronized (this.context) {
            context = this.context.inuse ? new Context() : this.context;
            context.reset(characterIterator, beginIndex, endIndex, this.numberOfClosures);
        }
        if (match != null) {
            match.setNumberOfGroups(this.nofparen);
            match.setSource(characterIterator);
        } else if (this.hasBackReferences) {
            match = new Match();
            match.setNumberOfGroups(this.nofparen);
        }
        context.match = match;
        if (isSet(this.options, 512)) {
            int iMatch2 = match(context, this.operations, context.start, 1, this.options);
            if (iMatch2 != context.limit) {
                return false;
            }
            if (context.match != null) {
                context.match.setBeginning(0, context.start);
                context.match.setEnd(0, iMatch2);
            }
            context.setInUse(false);
            return true;
        }
        if (this.fixedStringOnly) {
            int iMatches = this.fixedStringTable.matches(characterIterator, context.start, context.limit);
            if (iMatches < 0) {
                context.setInUse(false);
                return false;
            }
            if (context.match != null) {
                context.match.setBeginning(0, iMatches);
                context.match.setEnd(0, iMatches + this.fixedString.length());
            }
            context.setInUse(false);
            return true;
        }
        if (this.fixedString != null && this.fixedStringTable.matches(characterIterator, context.start, context.limit) < 0) {
            context.setInUse(false);
            return false;
        }
        int i3 = context.limit - this.minlength;
        Op op = this.operations;
        int iMatch3 = -1;
        if (op == null || op.type != 7 || this.operations.getChild().type != 0) {
            RangeToken rangeToken = this.firstChar;
            if (rangeToken != null) {
                int i4 = context.start;
                while (i4 <= i3) {
                    int index = characterIterator.setIndex(i4);
                    if (REUtil.isHighSurrogate(index) && (i2 = i4 + 1) < context.limit) {
                        index = REUtil.composeFromSurrogates(index, characterIterator.setIndex(i2));
                    }
                    if (rangeToken.match(index)) {
                        iMatch3 = match(context, this.operations, i4, 1, this.options);
                        if (iMatch3 >= 0) {
                            break;
                        }
                    }
                    i4++;
                }
                iMatch = iMatch3;
                i = i4;
            } else {
                i = context.start;
                while (i <= i3) {
                    iMatch3 = match(context, this.operations, i, 1, this.options);
                    if (iMatch3 >= 0) {
                        break;
                    }
                    i++;
                }
                iMatch = iMatch3;
            }
        } else if (isSet(this.options, 4)) {
            i = context.start;
            iMatch = match(context, this.operations, context.start, 1, this.options);
        } else {
            int i5 = context.start;
            boolean z = true;
            while (i5 <= i3) {
                if (isEOLChar(characterIterator.setIndex(i5))) {
                    z = true;
                } else {
                    if (z) {
                        iMatch3 = match(context, this.operations, i5, 1, this.options);
                        if (iMatch3 >= 0) {
                            break;
                        }
                    }
                    z = false;
                }
                i5++;
            }
            iMatch = iMatch3;
            i = i5;
        }
        if (iMatch < 0) {
            context.setInUse(false);
            return false;
        }
        if (context.match != null) {
            context.match.setBeginning(0, i);
            context.match.setEnd(0, iMatch);
        }
        context.setInUse(false);
        return true;
    }

    public boolean matches(char[] cArr) {
        return matches(cArr, 0, cArr.length, (Match) null);
    }

    public boolean matches(char[] cArr, int i, int i2) {
        return matches(cArr, i, i2, (Match) null);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public boolean matches(char[] cArr, int i, int i2, Match match) {
        Context context;
        int i3;
        int iMatch;
        int iComposeFromSurrogates;
        int i4;
        synchronized (this) {
            if (this.operations == null) {
                prepare();
            }
            if (this.context == null) {
                this.context = new Context();
            }
        }
        synchronized (this.context) {
            context = this.context.inuse ? new Context() : this.context;
            context.reset(cArr, i, i2, this.numberOfClosures);
        }
        if (match != null) {
            match.setNumberOfGroups(this.nofparen);
            match.setSource(cArr);
        } else if (this.hasBackReferences) {
            match = new Match();
            match.setNumberOfGroups(this.nofparen);
        }
        context.match = match;
        if (isSet(this.options, 512)) {
            int iMatch2 = match(context, this.operations, context.start, 1, this.options);
            if (iMatch2 != context.limit) {
                return false;
            }
            if (context.match != null) {
                context.match.setBeginning(0, context.start);
                context.match.setEnd(0, iMatch2);
            }
            context.setInUse(false);
            return true;
        }
        if (this.fixedStringOnly) {
            int iMatches = this.fixedStringTable.matches(cArr, context.start, context.limit);
            if (iMatches < 0) {
                context.setInUse(false);
                return false;
            }
            if (context.match != null) {
                context.match.setBeginning(0, iMatches);
                context.match.setEnd(0, iMatches + this.fixedString.length());
            }
            context.setInUse(false);
            return true;
        }
        if (this.fixedString != null && this.fixedStringTable.matches(cArr, context.start, context.limit) < 0) {
            context.setInUse(false);
            return false;
        }
        int i5 = context.limit - this.minlength;
        Op op = this.operations;
        int iMatch3 = -1;
        if (op == null || op.type != 7 || this.operations.getChild().type != 0) {
            RangeToken rangeToken = this.firstChar;
            if (rangeToken != null) {
                int i6 = context.start;
                while (i6 <= i5) {
                    char c = cArr[i6];
                    if (REUtil.isHighSurrogate(c) && (i4 = i6 + 1) < context.limit) {
                        iComposeFromSurrogates = c;
                        iComposeFromSurrogates = c;
                        iComposeFromSurrogates = REUtil.composeFromSurrogates(c, cArr[i4]);
                    }
                    iComposeFromSurrogates = c;
                    iComposeFromSurrogates = c;
                    iComposeFromSurrogates = c;
                    if (rangeToken.match(iComposeFromSurrogates)) {
                        iMatch3 = match(context, this.operations, i6, 1, this.options);
                        if (iMatch3 >= 0) {
                            break;
                        }
                    }
                    i6++;
                }
                iMatch = iMatch3;
                i3 = i6;
            } else {
                i3 = context.start;
                while (i3 <= i5) {
                    iMatch3 = match(context, this.operations, i3, 1, this.options);
                    if (iMatch3 >= 0) {
                        break;
                    }
                    i3++;
                }
                iMatch = iMatch3;
            }
        } else if (isSet(this.options, 4)) {
            i3 = context.start;
            iMatch = match(context, this.operations, context.start, 1, this.options);
        } else {
            int i7 = context.start;
            boolean z = true;
            while (i7 <= i5) {
                if (isEOLChar(cArr[i7])) {
                    z = true;
                } else {
                    if (z) {
                        iMatch3 = match(context, this.operations, i7, 1, this.options);
                        if (iMatch3 >= 0) {
                            break;
                        }
                    }
                    z = false;
                }
                i7++;
            }
            iMatch = iMatch3;
            i3 = i7;
        }
        if (iMatch < 0) {
            context.setInUse(false);
            return false;
        }
        if (context.match != null) {
            context.match.setBeginning(0, i3);
            context.match.setEnd(0, iMatch);
        }
        context.setInUse(false);
        return true;
    }

    public boolean matches(char[] cArr, Match match) {
        return matches(cArr, 0, cArr.length, match);
    }

    void prepare() {
        BMPattern bMPattern;
        String strDecomposeToSurrogates;
        compile(this.tokentree);
        this.minlength = this.tokentree.getMinLength();
        this.firstChar = null;
        if (!isSet(this.options, 128) && !isSet(this.options, 512)) {
            RangeToken rangeTokenCreateRange = Token.createRange();
            if (this.tokentree.analyzeFirstCharacter(rangeTokenCreateRange, this.options) == 1) {
                rangeTokenCreateRange.compactRanges();
                this.firstChar = rangeTokenCreateRange;
            }
        }
        Op op = this.operations;
        if (op != null && ((op.type == 6 || this.operations.type == 1) && this.operations.next == null)) {
            this.fixedStringOnly = true;
            if (this.operations.type == 6) {
                strDecomposeToSurrogates = this.operations.getString();
            } else {
                strDecomposeToSurrogates = this.operations.getData() >= 65536 ? REUtil.decomposeToSurrogates(this.operations.getData()) : new String(new char[]{(char) this.operations.getData()});
            }
            this.fixedString = strDecomposeToSurrogates;
            this.fixedStringOptions = this.options;
            bMPattern = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
        } else {
            if (isSet(this.options, 256) || isSet(this.options, 512)) {
                return;
            }
            Token.FixedStringContainer fixedStringContainer = new Token.FixedStringContainer();
            this.tokentree.findFixedString(fixedStringContainer, this.options);
            this.fixedString = fixedStringContainer.token == null ? null : fixedStringContainer.token.getString();
            this.fixedStringOptions = fixedStringContainer.options;
            String str = this.fixedString;
            if (str != null && str.length() < 2) {
                this.fixedString = null;
            }
            if (this.fixedString == null) {
                return;
            } else {
                bMPattern = new BMPattern(this.fixedString, 256, isSet(this.fixedStringOptions, 2));
            }
        }
        this.fixedStringTable = bMPattern;
    }

    public void setPattern(String str) throws ParseException {
        setPattern(str, Locale.getDefault());
    }

    public void setPattern(String str, String str2) throws ParseException {
        setPattern(str, str2, Locale.getDefault());
    }

    public void setPattern(String str, String str2, Locale locale) throws ParseException {
        setPattern(str, REUtil.parseOptions(str2), locale);
    }

    public void setPattern(String str, Locale locale) throws ParseException {
        setPattern(str, this.options, locale);
    }

    public String toString() {
        return this.tokentree.toString(this.options);
    }
}
