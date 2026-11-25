package org.apache.xerces.impl.xpath;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes13.dex */
public class XPath {
    private static final boolean DEBUG_ALL = false;
    private static final boolean DEBUG_ANY = false;
    private static final boolean DEBUG_XPATH_PARSE = false;
    protected final String fExpression;
    protected final LocationPath[] fLocationPaths;
    protected final SymbolTable fSymbolTable;

    public static class Axis implements Cloneable {
        public static final short ATTRIBUTE = 2;
        public static final short CHILD = 1;
        public static final short DESCENDANT = 4;
        public static final short SELF = 3;
        public final short type;

        protected Axis(Axis axis) {
            this.type = axis.type;
        }

        public Axis(short s) {
            this.type = s;
        }

        public Object clone() {
            return new Axis(this);
        }

        public String toString() {
            short s = this.type;
            if (s == 1) {
                return "child";
            }
            if (s == 2) {
                return "attribute";
            }
            if (s != 3) {
                return s != 4 ? "???" : "descendant";
            }
            return "self";
        }
    }

    public static class LocationPath implements Cloneable {
        public final Step[] steps;

        protected LocationPath(LocationPath locationPath) {
            this.steps = new Step[locationPath.steps.length];
            int i = 0;
            while (true) {
                Step[] stepArr = this.steps;
                if (i >= stepArr.length) {
                    return;
                }
                stepArr[i] = (Step) locationPath.steps[i].clone();
                i++;
            }
        }

        public LocationPath(Step[] stepArr) {
            this.steps = stepArr;
        }

        public Object clone() {
            return new LocationPath(this);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            int i = 0;
            while (true) {
                Step[] stepArr = this.steps;
                if (i >= stepArr.length) {
                    return stringBuffer.toString();
                }
                if (i > 0 && stepArr[i - 1].axis.type != 4 && this.steps[i].axis.type != 4) {
                    stringBuffer.append('/');
                }
                stringBuffer.append(this.steps[i].toString());
                i++;
            }
        }
    }

    public static class NodeTest implements Cloneable {
        public static final short NAMESPACE = 4;
        public static final short NODE = 3;
        public static final short QNAME = 1;
        public static final short WILDCARD = 2;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final QName f478name;
        public final short type;

        public NodeTest(String str, String str2) {
            QName qName = new QName();
            this.f478name = qName;
            this.type = (short) 4;
            qName.setValues(str, null, null, str2);
        }

        public NodeTest(NodeTest nodeTest) {
            QName qName = new QName();
            this.f478name = qName;
            this.type = nodeTest.type;
            qName.setValues(nodeTest.f478name);
        }

        public NodeTest(QName qName) {
            QName qName2 = new QName();
            this.f478name = qName2;
            this.type = (short) 1;
            qName2.setValues(qName);
        }

        public NodeTest(short s) {
            this.f478name = new QName();
            this.type = s;
        }

        public Object clone() {
            return new NodeTest(this);
        }

        public String toString() {
            short s = this.type;
            if (s == 1) {
                if (this.f478name.prefix.length() != 0) {
                    return this.f478name.uri != null ? this.f478name.prefix + ':' + this.f478name.localpart : "{" + this.f478name.uri + '}' + this.f478name.prefix + ':' + this.f478name.localpart;
                }
                return this.f478name.localpart;
            }
            if (s == 2) {
                return "*";
            }
            if (s == 3) {
                return "node()";
            }
            if (s != 4) {
                return "???";
            }
            if (this.f478name.prefix.length() != 0) {
                return this.f478name.uri != null ? this.f478name.prefix + ":*" : "{" + this.f478name.uri + '}' + this.f478name.prefix + ":*";
            }
            return "???:*";
        }
    }

    private static class Scanner {
        private static final byte CHARTYPE_ATSIGN = 19;
        private static final byte CHARTYPE_CLOSE_BRACKET = 22;
        private static final byte CHARTYPE_CLOSE_PAREN = 7;
        private static final byte CHARTYPE_COLON = 15;
        private static final byte CHARTYPE_COMMA = 10;
        private static final byte CHARTYPE_DIGIT = 14;
        private static final byte CHARTYPE_DOLLAR = 5;
        private static final byte CHARTYPE_EQUAL = 17;
        private static final byte CHARTYPE_EXCLAMATION = 3;
        private static final byte CHARTYPE_GREATER = 18;
        private static final byte CHARTYPE_INVALID = 0;
        private static final byte CHARTYPE_LESS = 16;
        private static final byte CHARTYPE_LETTER = 20;
        private static final byte CHARTYPE_MINUS = 11;
        private static final byte CHARTYPE_NONASCII = 25;
        private static final byte CHARTYPE_OPEN_BRACKET = 21;
        private static final byte CHARTYPE_OPEN_PAREN = 6;
        private static final byte CHARTYPE_OTHER = 1;
        private static final byte CHARTYPE_PERIOD = 12;
        private static final byte CHARTYPE_PLUS = 9;
        private static final byte CHARTYPE_QUOTE = 4;
        private static final byte CHARTYPE_SLASH = 13;
        private static final byte CHARTYPE_STAR = 8;
        private static final byte CHARTYPE_UNDERSCORE = 23;
        private static final byte CHARTYPE_UNION = 24;
        private static final byte CHARTYPE_WHITESPACE = 2;
        private SymbolTable fSymbolTable;
        private static final byte[] fASCIICharMap = {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 1, 5, 1, 1, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 1, 16, 17, 18, 1, 19, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 1, 22, 1, 23, 1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 24, 1, 1, 1};
        private static final String fAndSymbol = "and".intern();
        private static final String fOrSymbol = "or".intern();
        private static final String fModSymbol = "mod".intern();
        private static final String fDivSymbol = "div".intern();
        private static final String fCommentSymbol = "comment".intern();
        private static final String fTextSymbol = "text".intern();
        private static final String fPISymbol = "processing-instruction".intern();
        private static final String fNodeSymbol = "node".intern();
        private static final String fAncestorSymbol = "ancestor".intern();
        private static final String fAncestorOrSelfSymbol = "ancestor-or-self".intern();
        private static final String fAttributeSymbol = "attribute".intern();
        private static final String fChildSymbol = "child".intern();
        private static final String fDescendantSymbol = "descendant".intern();
        private static final String fDescendantOrSelfSymbol = "descendant-or-self".intern();
        private static final String fFollowingSymbol = "following".intern();
        private static final String fFollowingSiblingSymbol = "following-sibling".intern();
        private static final String fNamespaceSymbol = "namespace".intern();
        private static final String fParentSymbol = "parent".intern();
        private static final String fPrecedingSymbol = "preceding".intern();
        private static final String fPrecedingSiblingSymbol = "preceding-sibling".intern();
        private static final String fSelfSymbol = "self".intern();

        public Scanner(SymbolTable symbolTable) {
            this.fSymbolTable = symbolTable;
        }

        private int scanNumber(Tokens tokens, String str, int i, int i2) {
            char cCharAt = str.charAt(i2);
            int i3 = 0;
            int i4 = 0;
            while (cCharAt >= '0' && cCharAt <= '9') {
                i4 = (i4 * 10) + (cCharAt - '0');
                i2++;
                if (i2 == i) {
                    break;
                }
                cCharAt = str.charAt(i2);
            }
            if (cCharAt == '.' && (i2 = i2 + 1) < i) {
                do {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 < '0' || cCharAt2 > '9') {
                        break;
                    }
                    i3 = (i3 * 10) + (cCharAt2 - '0');
                    i2++;
                } while (i2 != i);
                if (i3 != 0) {
                    throw new RuntimeException("find a solution!");
                }
            }
            tokens.addToken(i4);
            tokens.addToken(i3);
            return i2;
        }

        protected void addToken(Tokens tokens, int i) throws XPathException {
            tokens.addToken(i);
        }

        /* JADX WARN: Code duplicated, block: B:103:0x013b A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:104:0x013d A[ADDED_TO_REGION] */
        /* JADX WARN: Code duplicated, block: B:116:0x0162  */
        /* JADX WARN: Code duplicated, block: B:118:0x0168  */
        /* JADX WARN: Code duplicated, block: B:120:0x016c  */
        /* JADX WARN: Code duplicated, block: B:121:0x016f  */
        /* JADX WARN: Code duplicated, block: B:123:0x0173  */
        /* JADX WARN: Code duplicated, block: B:124:0x0176  */
        /* JADX WARN: Code duplicated, block: B:126:0x017a  */
        /* JADX WARN: Code duplicated, block: B:127:0x017d  */
        /* JADX WARN: Code duplicated, block: B:129:0x0181  */
        /* JADX WARN: Code duplicated, block: B:130:0x0184  */
        /* JADX WARN: Code duplicated, block: B:132:0x0188  */
        /* JADX WARN: Code duplicated, block: B:133:0x018b  */
        /* JADX WARN: Code duplicated, block: B:135:0x018f  */
        /* JADX WARN: Code duplicated, block: B:136:0x0192  */
        /* JADX WARN: Code duplicated, block: B:138:0x0196  */
        /* JADX WARN: Code duplicated, block: B:139:0x019a  */
        /* JADX WARN: Code duplicated, block: B:141:0x019e  */
        /* JADX WARN: Code duplicated, block: B:142:0x01a1  */
        /* JADX WARN: Code duplicated, block: B:144:0x01a5  */
        /* JADX WARN: Code duplicated, block: B:145:0x01a9  */
        /* JADX WARN: Code duplicated, block: B:147:0x01ad  */
        /* JADX WARN: Code duplicated, block: B:148:0x01b0  */
        /* JADX WARN: Code duplicated, block: B:150:0x01b4  */
        /* JADX WARN: Code duplicated, block: B:151:0x01b7  */
        /* JADX WARN: Code duplicated, block: B:153:0x01bb  */
        /* JADX WARN: Code duplicated, block: B:156:0x01c1  */
        /* JADX WARN: Code duplicated, block: B:158:0x01c8  */
        /* JADX WARN: Code duplicated, block: B:160:0x01cd A[LOOP:5: B:57:0x00c8->B:160:0x01cd, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:16:0x002d  */
        /* JADX WARN: Code duplicated, block: B:18:0x0031  */
        /* JADX WARN: Code duplicated, block: B:193:0x0243  */
        /* JADX WARN: Code duplicated, block: B:195:0x0248  */
        /* JADX WARN: Code duplicated, block: B:197:0x0250  */
        /* JADX WARN: Code duplicated, block: B:198:0x0257  */
        /* JADX WARN: Code duplicated, block: B:19:0x0034  */
        /* JADX WARN: Code duplicated, block: B:221:0x028d  */
        /* JADX WARN: Code duplicated, block: B:239:0x02c2  */
        /* JADX WARN: Code duplicated, block: B:240:0x02c9  */
        /* JADX WARN: Code duplicated, block: B:252:0x02f1  */
        /* JADX WARN: Code duplicated, block: B:255:0x0301  */
        /* JADX WARN: Code duplicated, block: B:256:0x030c  */
        /* JADX WARN: Code duplicated, block: B:263:0x031a  */
        /* JADX WARN: Code duplicated, block: B:269:0x0337  */
        /* JADX WARN: Code duplicated, block: B:271:0x033e  */
        /* JADX WARN: Code duplicated, block: B:274:0x0343 A[LOOP:3: B:270:0x033c->B:274:0x0343, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:286:0x002c A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:287:0x003f A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:288:0x01d8 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:289:0x01f4 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:290:0x020b A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:291:0x0360 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:292:0x0058 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:293:0x0074 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:294:0x00a5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:295:0x0101 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:298:0x01cc A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:299:0x01c0 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:307:0x0336 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:308:0x0342 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:309:0x0040 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:310:0x0050 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:311:0x01d5 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:312:0x01f0 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:313:0x02b2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:314:0x02b9 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:315:0x02bc A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:316:0x02dd A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:31:0x0059 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:32:0x005b  */
        /* JADX WARN: Code duplicated, block: B:331:0x0226 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:332:0x02c0 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:333:0x02d4 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:334:0x02e2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:335:0x0331 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:336:0x00d8 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:338:0x015e A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:33:0x0060  */
        /* JADX WARN: Code duplicated, block: B:340:0x0048 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:341:0x021a A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:342:0x023e A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:346:0x0052 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:358:0x029d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:367:0x00d6 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:36:0x0070  */
        /* JADX WARN: Code duplicated, block: B:39:0x0075  */
        /* JADX WARN: Code duplicated, block: B:41:0x007b  */
        /* JADX WARN: Code duplicated, block: B:43:0x007f  */
        /* JADX WARN: Code duplicated, block: B:45:0x008c A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:46:0x008e  */
        /* JADX WARN: Code duplicated, block: B:48:0x0092  */
        /* JADX WARN: Code duplicated, block: B:50:0x009f  */
        /* JADX WARN: Code duplicated, block: B:53:0x00a6 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:54:0x00a8  */
        /* JADX WARN: Code duplicated, block: B:56:0x00c1  */
        /* JADX WARN: Code duplicated, block: B:62:0x00d2  */
        /* JADX WARN: Code duplicated, block: B:67:0x00dc  */
        /* JADX WARN: Code duplicated, block: B:69:0x00e2  */
        /* JADX WARN: Code duplicated, block: B:71:0x00e6  */
        /* JADX WARN: Code duplicated, block: B:72:0x00e9  */
        /* JADX WARN: Code duplicated, block: B:74:0x00ed  */
        /* JADX WARN: Code duplicated, block: B:75:0x00f0  */
        /* JADX WARN: Code duplicated, block: B:77:0x00f4  */
        /* JADX WARN: Code duplicated, block: B:84:0x0102  */
        public boolean scanExpr(SymbolTable symbolTable, Tokens tokens, String str, int i, int i2) throws XPathException {
            char cCharAt;
            char c;
            byte b;
            int iScanNCName;
            byte bCharAt;
            String strAddSymbol;
            String str2;
            boolean z;
            int i3;
            String str3;
            boolean z2;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            char cCharAt2;
            int iScanNCName2;
            int i9;
            int i10;
            int iScanNumber;
            char cCharAt3;
            boolean z3;
            int i11;
            char cCharAt4;
            int i12;
            int iScanNCName3;
            int i13;
            int iScanNCName4;
            byte bCharAt2;
            String strAddSymbol2;
            int i14;
            String strAddSymbol3;
            int i15;
            boolean z4;
            int i16;
            int i17;
            int i18;
            int i19;
            int i20;
            int i21;
            int i22;
            int iScanNumber2 = i;
            while (true) {
                boolean z5 = false;
                while (iScanNumber2 != i2) {
                    do {
                        cCharAt = str.charAt(iScanNumber2);
                        c = '\t';
                        if (cCharAt == ' ' || cCharAt == '\n' || cCharAt == '\t' || cCharAt == '\r') {
                            iScanNumber2++;
                        }
                        if (iScanNumber2 == i2) {
                            if (cCharAt >= 128) {
                                b = 25;
                            } else {
                                b = fASCIICharMap[cCharAt];
                            }
                            switch (b) {
                                case 3:
                                    i19 = iScanNumber2 + 1;
                                    if (i19 != i2 || str.charAt(i19) != '=') {
                                        return false;
                                    }
                                    i17 = 27;
                                    addToken(tokens, i17);
                                    iScanNumber2 += 2;
                                    break;
                                    break;
                                case 4:
                                    z3 = true;
                                    i11 = iScanNumber2 + 1;
                                    if (i11 == i2) {
                                        return false;
                                    }
                                    cCharAt4 = str.charAt(i11);
                                    i12 = i11;
                                    while (cCharAt4 != cCharAt) {
                                        i12++;
                                        if (i12 == i2) {
                                            return false;
                                        }
                                        cCharAt4 = str.charAt(i12);
                                    }
                                    addToken(tokens, 46);
                                    tokens.addToken(symbolTable.addSymbol(str.substring(i11, (i12 - i11) + i11)));
                                    iScanNCName3 = i12 + 1;
                                    iScanNumber2 = iScanNCName3;
                                    z5 = z3;
                                    break;
                                case 5:
                                    z3 = true;
                                    i13 = iScanNumber2 + 1;
                                    if (i13 != i2 || (iScanNCName4 = scanNCName(str, i2, i13)) == i13) {
                                        return false;
                                    }
                                    bCharAt2 = iScanNCName4 < i2 ? str.charAt(iScanNCName4) : (byte) -1;
                                    strAddSymbol2 = symbolTable.addSymbol(str.substring(i13, iScanNCName4));
                                    if (bCharAt2 != 58) {
                                        i15 = 48;
                                        strAddSymbol3 = strAddSymbol2;
                                        strAddSymbol2 = XMLSymbols.EMPTY_STRING;
                                        iScanNCName3 = iScanNCName4;
                                    } else {
                                        i14 = iScanNCName4 + 1;
                                        if (i14 != i2 || (iScanNCName3 = scanNCName(str, i2, i14)) == i14) {
                                            return false;
                                        }
                                        if (iScanNCName3 < i2) {
                                            str.charAt(iScanNCName3);
                                        }
                                        strAddSymbol3 = symbolTable.addSymbol(str.substring(i14, iScanNCName3));
                                        i15 = 48;
                                    }
                                    addToken(tokens, i15);
                                    tokens.addToken(strAddSymbol2);
                                    tokens.addToken(strAddSymbol3);
                                    iScanNumber2 = iScanNCName3;
                                    z5 = z3;
                                    break;
                                    break;
                                case 6:
                                    addToken(tokens, 0);
                                    iScanNumber2++;
                                    break;
                                case 7:
                                    z3 = true;
                                    addToken(tokens, 1);
                                    iScanNumber2++;
                                    z5 = z3;
                                    break;
                                case 8:
                                    if (z5) {
                                        addToken(tokens, 20);
                                        z4 = false;
                                    } else {
                                        addToken(tokens, 9);
                                        z4 = true;
                                    }
                                    iScanNumber2++;
                                    z5 = z4;
                                    break;
                                case 9:
                                    i18 = 24;
                                    addToken(tokens, i18);
                                    iScanNumber2++;
                                    break;
                                case 10:
                                    i18 = 7;
                                    addToken(tokens, i18);
                                    iScanNumber2++;
                                    break;
                                case 11:
                                    addToken(tokens, 25);
                                    iScanNumber2++;
                                    break;
                                case 12:
                                    iScanNumber = iScanNumber2 + 1;
                                    if (iScanNumber != i2) {
                                        cCharAt3 = str.charAt(iScanNumber);
                                        if (cCharAt3 == '.') {
                                            addToken(tokens, 5);
                                            iScanNumber = iScanNumber2 + 2;
                                        } else if (cCharAt3 < '0' && cCharAt3 <= '9') {
                                            addToken(tokens, 47);
                                            iScanNumber = scanNumber(tokens, str, i2, iScanNumber2);
                                        } else if (cCharAt3 == '/' || cCharAt3 == '|') {
                                            addToken(tokens, 4);
                                        } else {
                                            if (cCharAt3 == ' ' && cCharAt3 != '\n' && cCharAt3 != '\t' && cCharAt3 != '\r') {
                                                throw new XPathException("c-general-xpath");
                                            }
                                            while (true) {
                                                iScanNumber2++;
                                                if (iScanNumber2 != i2) {
                                                    cCharAt3 = str.charAt(iScanNumber2);
                                                    if (cCharAt3 == ' ' && cCharAt3 != '\n' && cCharAt3 != '\t') {
                                                        if (cCharAt3 != '\r') {
                                                        }
                                                    }
                                                }
                                            }
                                            if (iScanNumber2 == i2 && cCharAt3 != '|') {
                                                throw new XPathException("c-general-xpath");
                                            }
                                            addToken(tokens, 4);
                                        }
                                        iScanNumber2 = iScanNumber;
                                    } else {
                                        addToken(tokens, 4);
                                        iScanNumber2 = iScanNumber;
                                    }
                                    z5 = true;
                                    break;
                                case 13:
                                    i16 = iScanNumber2 + 1;
                                    if (i16 == i2 || str.charAt(i16) != '/') {
                                        addToken(tokens, 21);
                                        z5 = false;
                                        iScanNumber2 = i16;
                                    } else {
                                        i17 = 22;
                                        addToken(tokens, i17);
                                        iScanNumber2 += 2;
                                    }
                                    break;
                                case 14:
                                    addToken(tokens, 47);
                                    iScanNumber2 = scanNumber(tokens, str, i2, iScanNumber2);
                                    z5 = true;
                                    break;
                                case 15:
                                    i20 = iScanNumber2 + 1;
                                    if (i20 != i2 || str.charAt(i20) != ':') {
                                        return false;
                                    }
                                    i17 = 8;
                                    addToken(tokens, i17);
                                    iScanNumber2 += 2;
                                    break;
                                    break;
                                case 16:
                                    i21 = iScanNumber2 + 1;
                                    i22 = 28;
                                    if (i21 == i2 || str.charAt(i21) != '=') {
                                        addToken(tokens, i22);
                                        iScanNumber2 = i21;
                                    } else {
                                        i17 = 29;
                                        addToken(tokens, i17);
                                        iScanNumber2 += 2;
                                    }
                                    break;
                                case 17:
                                    i18 = 26;
                                    addToken(tokens, i18);
                                    iScanNumber2++;
                                    break;
                                case 18:
                                    i21 = iScanNumber2 + 1;
                                    i22 = 30;
                                    if (i21 == i2 || str.charAt(i21) != '=') {
                                        addToken(tokens, i22);
                                        iScanNumber2 = i21;
                                    } else {
                                        i17 = 31;
                                        addToken(tokens, i17);
                                        iScanNumber2 += 2;
                                    }
                                    break;
                                case 19:
                                    i18 = 6;
                                    addToken(tokens, i18);
                                    iScanNumber2++;
                                    break;
                                case 20:
                                case 23:
                                case 25:
                                    iScanNCName = scanNCName(str, i2, iScanNumber2);
                                    if (iScanNCName == iScanNumber2) {
                                        return false;
                                    }
                                    if (iScanNCName < i2) {
                                        bCharAt = str.charAt(iScanNCName);
                                    } else {
                                        bCharAt = -1;
                                    }
                                    strAddSymbol = symbolTable.addSymbol(str.substring(iScanNumber2, iScanNCName));
                                    str2 = XMLSymbols.EMPTY_STRING;
                                    if (bCharAt == 58) {
                                        i8 = iScanNCName + 1;
                                        if (i8 == i2) {
                                            return false;
                                        }
                                        cCharAt2 = str.charAt(i8);
                                        if (cCharAt2 == '*') {
                                            i10 = iScanNCName + 2;
                                            if (i10 < i2) {
                                                cCharAt2 = str.charAt(i10);
                                            }
                                            bCharAt = cCharAt2;
                                            z = false;
                                            i3 = i10;
                                            str3 = str2;
                                            z2 = true;
                                        } else if (cCharAt2 == ':') {
                                            i9 = iScanNCName + 2;
                                            if (i9 < i2) {
                                                cCharAt2 = str.charAt(i9);
                                            }
                                            bCharAt = cCharAt2;
                                            i3 = i9;
                                            str3 = str2;
                                            z = true;
                                            z2 = false;
                                        } else {
                                            iScanNCName2 = scanNCName(str, i2, i8);
                                            if (iScanNCName2 == i8) {
                                                return false;
                                            }
                                            byte bCharAt3 = iScanNCName2 < i2 ? str.charAt(iScanNCName2) : (byte) -1;
                                            String strAddSymbol4 = symbolTable.addSymbol(str.substring(i8, iScanNCName2));
                                            z2 = false;
                                            bCharAt = bCharAt3;
                                            z = false;
                                            str3 = strAddSymbol;
                                            strAddSymbol = strAddSymbol4;
                                            i3 = iScanNCName2;
                                        }
                                    } else {
                                        z = false;
                                        i3 = iScanNCName;
                                        str3 = str2;
                                        z2 = false;
                                    }
                                    while (true) {
                                        if (bCharAt != 32 || bCharAt == 10 || bCharAt == c || bCharAt == 13) {
                                            i3++;
                                            if (i3 != i2) {
                                                bCharAt = str.charAt(i3);
                                                c = '\t';
                                            }
                                        }
                                    }
                                    if (!z5) {
                                        if (bCharAt != 40 && !z2 && !z) {
                                            if (strAddSymbol == fCommentSymbol) {
                                                i7 = 12;
                                            } else {
                                                if (strAddSymbol == fTextSymbol) {
                                                    addToken(tokens, 13);
                                                } else if (strAddSymbol == fPISymbol) {
                                                    i7 = 14;
                                                } else if (strAddSymbol == fNodeSymbol) {
                                                    i7 = 15;
                                                } else {
                                                    addToken(tokens, 32);
                                                    tokens.addToken(str3);
                                                    tokens.addToken(strAddSymbol);
                                                }
                                                addToken(tokens, 0);
                                                i3++;
                                            }
                                            addToken(tokens, i7);
                                            addToken(tokens, 0);
                                            i3++;
                                        } else if (z && !(bCharAt == 58 && (i6 = i3 + 1) < i2 && str.charAt(i6) == ':')) {
                                            if (z2) {
                                                addToken(tokens, 10);
                                            } else {
                                                addToken(tokens, 11);
                                                tokens.addToken(str3);
                                            }
                                            tokens.addToken(strAddSymbol);
                                            iScanNumber2 = i3;
                                            z5 = true;
                                        } else {
                                            if (strAddSymbol == fAncestorSymbol) {
                                                i5 = 33;
                                            } else if (strAddSymbol == fAncestorOrSelfSymbol) {
                                                i5 = 34;
                                            } else if (strAddSymbol == fAttributeSymbol) {
                                                i5 = 35;
                                            } else if (strAddSymbol == fChildSymbol) {
                                                i5 = 36;
                                            } else if (strAddSymbol == fDescendantSymbol) {
                                                i5 = 37;
                                            } else if (strAddSymbol == fDescendantOrSelfSymbol) {
                                                i5 = 38;
                                            } else if (strAddSymbol == fFollowingSymbol) {
                                                i5 = 39;
                                            } else {
                                                if (strAddSymbol == fFollowingSiblingSymbol) {
                                                    addToken(tokens, 40);
                                                } else if (strAddSymbol == fNamespaceSymbol) {
                                                    i5 = 41;
                                                } else if (strAddSymbol == fParentSymbol) {
                                                    addToken(tokens, 42);
                                                } else if (strAddSymbol == fPrecedingSymbol) {
                                                    i5 = 43;
                                                } else if (strAddSymbol == fPrecedingSiblingSymbol) {
                                                    i5 = 44;
                                                } else {
                                                    if (strAddSymbol != fSelfSymbol) {
                                                        return false;
                                                    }
                                                    i5 = 45;
                                                }
                                                if (z2) {
                                                    return false;
                                                }
                                                addToken(tokens, 8);
                                                if (!z) {
                                                    i3 += 2;
                                                }
                                            }
                                            addToken(tokens, i5);
                                            if (z2) {
                                                return false;
                                            }
                                            addToken(tokens, 8);
                                            if (!z) {
                                                i3 += 2;
                                            }
                                        }
                                        break;
                                    } else {
                                        if (strAddSymbol == fAndSymbol) {
                                            i4 = 16;
                                        } else if (strAddSymbol == fOrSymbol) {
                                            i4 = 17;
                                        } else if (strAddSymbol == fModSymbol) {
                                            i4 = 18;
                                        } else {
                                            if (strAddSymbol == fDivSymbol) {
                                                return false;
                                            }
                                            i4 = 19;
                                        }
                                        addToken(tokens, i4);
                                        if (!z2 || z) {
                                        }
                                    }
                                    z5 = false;
                                    iScanNumber2 = i3;
                                    break;
                                case 21:
                                    i18 = 2;
                                    addToken(tokens, i18);
                                    iScanNumber2++;
                                    break;
                                case 22:
                                    addToken(tokens, 3);
                                    iScanNumber2++;
                                    z5 = true;
                                    break;
                                case 24:
                                    i18 = 23;
                                    addToken(tokens, i18);
                                    iScanNumber2++;
                                    break;
                                default:
                                    return false;
                            }
                        }
                    } while (iScanNumber2 != i2);
                    if (iScanNumber2 == i2) {
                        if (cCharAt >= 128) {
                            b = 25;
                        } else {
                            b = fASCIICharMap[cCharAt];
                        }
                        switch (b) {
                            case 3:
                                i19 = iScanNumber2 + 1;
                                if (i19 != i2) {
                                    return false;
                                }
                                i17 = 27;
                                addToken(tokens, i17);
                                iScanNumber2 += 2;
                                break;
                            case 4:
                                z3 = true;
                                i11 = iScanNumber2 + 1;
                                if (i11 == i2) {
                                    return false;
                                }
                                cCharAt4 = str.charAt(i11);
                                i12 = i11;
                                while (cCharAt4 != cCharAt) {
                                    i12++;
                                    if (i12 == i2) {
                                        return false;
                                    }
                                    cCharAt4 = str.charAt(i12);
                                }
                                addToken(tokens, 46);
                                tokens.addToken(symbolTable.addSymbol(str.substring(i11, (i12 - i11) + i11)));
                                iScanNCName3 = i12 + 1;
                                iScanNumber2 = iScanNCName3;
                                z5 = z3;
                                break;
                            case 5:
                                z3 = true;
                                i13 = iScanNumber2 + 1;
                                if (i13 != i2) {
                                    return false;
                                }
                                bCharAt2 = iScanNCName4 < i2 ? str.charAt(iScanNCName4) : (byte) -1;
                                strAddSymbol2 = symbolTable.addSymbol(str.substring(i13, iScanNCName4));
                                if (bCharAt2 != 58) {
                                    i15 = 48;
                                    strAddSymbol3 = strAddSymbol2;
                                    strAddSymbol2 = XMLSymbols.EMPTY_STRING;
                                    iScanNCName3 = iScanNCName4;
                                } else {
                                    i14 = iScanNCName4 + 1;
                                    if (i14 != i2) {
                                        return false;
                                    }
                                    if (iScanNCName3 < i2) {
                                        str.charAt(iScanNCName3);
                                    }
                                    strAddSymbol3 = symbolTable.addSymbol(str.substring(i14, iScanNCName3));
                                    i15 = 48;
                                }
                                addToken(tokens, i15);
                                tokens.addToken(strAddSymbol2);
                                tokens.addToken(strAddSymbol3);
                                iScanNumber2 = iScanNCName3;
                                z5 = z3;
                                break;
                            case 6:
                                addToken(tokens, 0);
                                iScanNumber2++;
                                break;
                            case 7:
                                z3 = true;
                                addToken(tokens, 1);
                                iScanNumber2++;
                                z5 = z3;
                                break;
                            case 8:
                                if (z5) {
                                    addToken(tokens, 20);
                                    z4 = false;
                                } else {
                                    addToken(tokens, 9);
                                    z4 = true;
                                }
                                iScanNumber2++;
                                z5 = z4;
                                break;
                            case 9:
                                i18 = 24;
                                addToken(tokens, i18);
                                iScanNumber2++;
                                break;
                            case 10:
                                i18 = 7;
                                addToken(tokens, i18);
                                iScanNumber2++;
                                break;
                            case 11:
                                addToken(tokens, 25);
                                iScanNumber2++;
                                break;
                            case 12:
                                iScanNumber = iScanNumber2 + 1;
                                if (iScanNumber != i2) {
                                    cCharAt3 = str.charAt(iScanNumber);
                                    if (cCharAt3 == '.') {
                                        addToken(tokens, 5);
                                        iScanNumber = iScanNumber2 + 2;
                                    } else {
                                        if (cCharAt3 < '0') {
                                        }
                                        if (cCharAt3 == '/') {
                                            if (cCharAt3 == ' ') {
                                            }
                                            while (true) {
                                                iScanNumber2++;
                                                if (iScanNumber2 != i2) {
                                                    cCharAt3 = str.charAt(iScanNumber2);
                                                    if (cCharAt3 == ' ') {
                                                    }
                                                }
                                            }
                                            if (iScanNumber2 == i2) {
                                            }
                                            addToken(tokens, 4);
                                        }
                                        addToken(tokens, 4);
                                    }
                                    iScanNumber2 = iScanNumber;
                                } else {
                                    addToken(tokens, 4);
                                    iScanNumber2 = iScanNumber;
                                }
                                z5 = true;
                                break;
                            case 13:
                                i16 = iScanNumber2 + 1;
                                if (i16 == i2) {
                                    i17 = 22;
                                    addToken(tokens, i17);
                                    iScanNumber2 += 2;
                                }
                                addToken(tokens, 21);
                                z5 = false;
                                iScanNumber2 = i16;
                                break;
                            case 14:
                                addToken(tokens, 47);
                                iScanNumber2 = scanNumber(tokens, str, i2, iScanNumber2);
                                z5 = true;
                                break;
                            case 15:
                                i20 = iScanNumber2 + 1;
                                if (i20 != i2) {
                                    return false;
                                }
                                i17 = 8;
                                addToken(tokens, i17);
                                iScanNumber2 += 2;
                                break;
                            case 16:
                                i21 = iScanNumber2 + 1;
                                i22 = 28;
                                if (i21 == i2) {
                                    i17 = 29;
                                    addToken(tokens, i17);
                                    iScanNumber2 += 2;
                                    break;
                                }
                                addToken(tokens, i22);
                                iScanNumber2 = i21;
                                break;
                            case 17:
                                i18 = 26;
                                addToken(tokens, i18);
                                iScanNumber2++;
                                break;
                            case 18:
                                i21 = iScanNumber2 + 1;
                                i22 = 30;
                                if (i21 == i2) {
                                    i17 = 31;
                                    addToken(tokens, i17);
                                    iScanNumber2 += 2;
                                    break;
                                }
                                addToken(tokens, i22);
                                iScanNumber2 = i21;
                                break;
                            case 19:
                                i18 = 6;
                                addToken(tokens, i18);
                                iScanNumber2++;
                                break;
                            case 20:
                            case 23:
                            case 25:
                                iScanNCName = scanNCName(str, i2, iScanNumber2);
                                if (iScanNCName == iScanNumber2) {
                                    return false;
                                }
                                if (iScanNCName < i2) {
                                    bCharAt = str.charAt(iScanNCName);
                                } else {
                                    bCharAt = -1;
                                }
                                strAddSymbol = symbolTable.addSymbol(str.substring(iScanNumber2, iScanNCName));
                                str2 = XMLSymbols.EMPTY_STRING;
                                if (bCharAt == 58) {
                                    i8 = iScanNCName + 1;
                                    if (i8 == i2) {
                                        return false;
                                    }
                                    cCharAt2 = str.charAt(i8);
                                    if (cCharAt2 == '*') {
                                        i10 = iScanNCName + 2;
                                        if (i10 < i2) {
                                            cCharAt2 = str.charAt(i10);
                                        }
                                        bCharAt = cCharAt2;
                                        z = false;
                                        i3 = i10;
                                        str3 = str2;
                                        z2 = true;
                                    } else if (cCharAt2 == ':') {
                                        i9 = iScanNCName + 2;
                                        if (i9 < i2) {
                                            cCharAt2 = str.charAt(i9);
                                        }
                                        bCharAt = cCharAt2;
                                        i3 = i9;
                                        str3 = str2;
                                        z = true;
                                        z2 = false;
                                    } else {
                                        iScanNCName2 = scanNCName(str, i2, i8);
                                        if (iScanNCName2 == i8) {
                                            return false;
                                        }
                                        if (iScanNCName2 < i2) {
                                        }
                                        String strAddSymbol5 = symbolTable.addSymbol(str.substring(i8, iScanNCName2));
                                        z2 = false;
                                        bCharAt = bCharAt3;
                                        z = false;
                                        str3 = strAddSymbol;
                                        strAddSymbol = strAddSymbol5;
                                        i3 = iScanNCName2;
                                    }
                                } else {
                                    z = false;
                                    i3 = iScanNCName;
                                    str3 = str2;
                                    z2 = false;
                                }
                                while (true) {
                                    if (bCharAt != 32) {
                                        i3++;
                                        if (i3 != i2) {
                                            bCharAt = str.charAt(i3);
                                            c = '\t';
                                        }
                                    } else {
                                        i3++;
                                        if (i3 != i2) {
                                            bCharAt = str.charAt(i3);
                                            c = '\t';
                                        }
                                    }
                                }
                                if (!z5) {
                                    if (strAddSymbol == fAndSymbol) {
                                        i4 = 16;
                                    } else if (strAddSymbol == fOrSymbol) {
                                        i4 = 17;
                                    } else if (strAddSymbol == fModSymbol) {
                                        i4 = 18;
                                    } else {
                                        if (strAddSymbol == fDivSymbol) {
                                            return false;
                                        }
                                        i4 = 19;
                                    }
                                    addToken(tokens, i4);
                                    return !z2 ? false : false;
                                }
                                if (bCharAt != 40) {
                                    if (z) {
                                    }
                                    if (strAddSymbol == fAncestorSymbol) {
                                        i5 = 33;
                                    } else if (strAddSymbol == fAncestorOrSelfSymbol) {
                                        i5 = 34;
                                    } else if (strAddSymbol == fAttributeSymbol) {
                                        i5 = 35;
                                    } else if (strAddSymbol == fChildSymbol) {
                                        i5 = 36;
                                    } else if (strAddSymbol == fDescendantSymbol) {
                                        i5 = 37;
                                    } else if (strAddSymbol == fDescendantOrSelfSymbol) {
                                        i5 = 38;
                                    } else if (strAddSymbol == fFollowingSymbol) {
                                        i5 = 39;
                                    } else {
                                        if (strAddSymbol == fFollowingSiblingSymbol) {
                                            addToken(tokens, 40);
                                        } else if (strAddSymbol == fNamespaceSymbol) {
                                            i5 = 41;
                                        } else if (strAddSymbol == fParentSymbol) {
                                            addToken(tokens, 42);
                                        } else if (strAddSymbol == fPrecedingSymbol) {
                                            i5 = 43;
                                        } else if (strAddSymbol == fPrecedingSiblingSymbol) {
                                            i5 = 44;
                                        } else {
                                            if (strAddSymbol != fSelfSymbol) {
                                                return false;
                                            }
                                            i5 = 45;
                                        }
                                        if (z2) {
                                            return false;
                                        }
                                        addToken(tokens, 8);
                                        if (!z) {
                                            i3 += 2;
                                        }
                                    }
                                    addToken(tokens, i5);
                                    if (z2) {
                                        return false;
                                    }
                                    addToken(tokens, 8);
                                    if (!z) {
                                        i3 += 2;
                                    }
                                } else {
                                    if (z) {
                                    }
                                    if (strAddSymbol == fAncestorSymbol) {
                                        i5 = 33;
                                    } else if (strAddSymbol == fAncestorOrSelfSymbol) {
                                        i5 = 34;
                                    } else if (strAddSymbol == fAttributeSymbol) {
                                        i5 = 35;
                                    } else if (strAddSymbol == fChildSymbol) {
                                        i5 = 36;
                                    } else if (strAddSymbol == fDescendantSymbol) {
                                        i5 = 37;
                                    } else if (strAddSymbol == fDescendantOrSelfSymbol) {
                                        i5 = 38;
                                    } else if (strAddSymbol == fFollowingSymbol) {
                                        i5 = 39;
                                    } else {
                                        if (strAddSymbol == fFollowingSiblingSymbol) {
                                            addToken(tokens, 40);
                                        } else if (strAddSymbol == fNamespaceSymbol) {
                                            i5 = 41;
                                        } else if (strAddSymbol == fParentSymbol) {
                                            addToken(tokens, 42);
                                        } else if (strAddSymbol == fPrecedingSymbol) {
                                            i5 = 43;
                                        } else if (strAddSymbol == fPrecedingSiblingSymbol) {
                                            i5 = 44;
                                        } else {
                                            if (strAddSymbol != fSelfSymbol) {
                                                return false;
                                            }
                                            i5 = 45;
                                        }
                                        if (z2) {
                                            return false;
                                        }
                                        addToken(tokens, 8);
                                        if (!z) {
                                            i3 += 2;
                                        }
                                    }
                                    addToken(tokens, i5);
                                    if (z2) {
                                        return false;
                                    }
                                    addToken(tokens, 8);
                                    if (!z) {
                                        i3 += 2;
                                    }
                                }
                                z5 = false;
                                iScanNumber2 = i3;
                                break;
                            case 21:
                                i18 = 2;
                                addToken(tokens, i18);
                                iScanNumber2++;
                                break;
                            case 22:
                                addToken(tokens, 3);
                                iScanNumber2++;
                                z5 = true;
                                break;
                            case 24:
                                i18 = 23;
                                addToken(tokens, i18);
                                iScanNumber2++;
                                break;
                            default:
                                return false;
                        }
                    }
                }
            }
            return true;
        }

        int scanNCName(String str, int i, int i2) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                byte b = fASCIICharMap[cCharAt];
                if (b != 20 && b != 23) {
                    return i2;
                }
            } else if (!XMLChar.isNameStart(cCharAt)) {
                return i2;
            }
            while (true) {
                i2++;
                if (i2 < i) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 < 128) {
                        byte b2 = fASCIICharMap[cCharAt2];
                        if (b2 != 20 && b2 != 14 && b2 != 12 && b2 != 11 && b2 != 23) {
                            break;
                        }
                    } else if (!XMLChar.isName(cCharAt2)) {
                        break;
                    }
                } else {
                    break;
                }
            }
            return i2;
        }
    }

    public static class Step implements Cloneable {
        public final Axis axis;
        public final NodeTest nodeTest;

        public Step(Axis axis, NodeTest nodeTest) {
            this.axis = axis;
            this.nodeTest = nodeTest;
        }

        protected Step(Step step) {
            this.axis = (Axis) step.axis.clone();
            this.nodeTest = (NodeTest) step.nodeTest.clone();
        }

        public Object clone() {
            return new Step(this);
        }

        public String toString() {
            if (this.axis.type == 3) {
                return ".";
            }
            if (this.axis.type == 2) {
                return PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + this.nodeTest.toString();
            }
            if (this.axis.type == 1) {
                return this.nodeTest.toString();
            }
            return this.axis.type == 4 ? "//" : "??? (" + ((int) this.axis.type) + ')';
        }
    }

    private static final class Tokens {
        static final boolean DUMP_TOKENS = false;
        public static final int EXPRTOKEN_ATSIGN = 6;
        public static final int EXPRTOKEN_AXISNAME_ANCESTOR = 33;
        public static final int EXPRTOKEN_AXISNAME_ANCESTOR_OR_SELF = 34;
        public static final int EXPRTOKEN_AXISNAME_ATTRIBUTE = 35;
        public static final int EXPRTOKEN_AXISNAME_CHILD = 36;
        public static final int EXPRTOKEN_AXISNAME_DESCENDANT = 37;
        public static final int EXPRTOKEN_AXISNAME_DESCENDANT_OR_SELF = 38;
        public static final int EXPRTOKEN_AXISNAME_FOLLOWING = 39;
        public static final int EXPRTOKEN_AXISNAME_FOLLOWING_SIBLING = 40;
        public static final int EXPRTOKEN_AXISNAME_NAMESPACE = 41;
        public static final int EXPRTOKEN_AXISNAME_PARENT = 42;
        public static final int EXPRTOKEN_AXISNAME_PRECEDING = 43;
        public static final int EXPRTOKEN_AXISNAME_PRECEDING_SIBLING = 44;
        public static final int EXPRTOKEN_AXISNAME_SELF = 45;
        public static final int EXPRTOKEN_CLOSE_BRACKET = 3;
        public static final int EXPRTOKEN_CLOSE_PAREN = 1;
        public static final int EXPRTOKEN_COMMA = 7;
        public static final int EXPRTOKEN_DOUBLE_COLON = 8;
        public static final int EXPRTOKEN_DOUBLE_PERIOD = 5;
        public static final int EXPRTOKEN_FUNCTION_NAME = 32;
        public static final int EXPRTOKEN_LITERAL = 46;
        public static final int EXPRTOKEN_NAMETEST_ANY = 9;
        public static final int EXPRTOKEN_NAMETEST_NAMESPACE = 10;
        public static final int EXPRTOKEN_NAMETEST_QNAME = 11;
        public static final int EXPRTOKEN_NODETYPE_COMMENT = 12;
        public static final int EXPRTOKEN_NODETYPE_NODE = 15;
        public static final int EXPRTOKEN_NODETYPE_PI = 14;
        public static final int EXPRTOKEN_NODETYPE_TEXT = 13;
        public static final int EXPRTOKEN_NUMBER = 47;
        public static final int EXPRTOKEN_OPEN_BRACKET = 2;
        public static final int EXPRTOKEN_OPEN_PAREN = 0;
        public static final int EXPRTOKEN_OPERATOR_AND = 16;
        public static final int EXPRTOKEN_OPERATOR_DIV = 19;
        public static final int EXPRTOKEN_OPERATOR_DOUBLE_SLASH = 22;
        public static final int EXPRTOKEN_OPERATOR_EQUAL = 26;
        public static final int EXPRTOKEN_OPERATOR_GREATER = 30;
        public static final int EXPRTOKEN_OPERATOR_GREATER_EQUAL = 31;
        public static final int EXPRTOKEN_OPERATOR_LESS = 28;
        public static final int EXPRTOKEN_OPERATOR_LESS_EQUAL = 29;
        public static final int EXPRTOKEN_OPERATOR_MINUS = 25;
        public static final int EXPRTOKEN_OPERATOR_MOD = 18;
        public static final int EXPRTOKEN_OPERATOR_MULT = 20;
        public static final int EXPRTOKEN_OPERATOR_NOT_EQUAL = 27;
        public static final int EXPRTOKEN_OPERATOR_OR = 17;
        public static final int EXPRTOKEN_OPERATOR_PLUS = 24;
        public static final int EXPRTOKEN_OPERATOR_SLASH = 21;
        public static final int EXPRTOKEN_OPERATOR_UNION = 23;
        public static final int EXPRTOKEN_PERIOD = 4;
        public static final int EXPRTOKEN_VARIABLE_REFERENCE = 48;
        private static final int INITIAL_TOKEN_COUNT = 256;
        private static final String[] fgTokenNames = {"EXPRTOKEN_OPEN_PAREN", "EXPRTOKEN_CLOSE_PAREN", "EXPRTOKEN_OPEN_BRACKET", "EXPRTOKEN_CLOSE_BRACKET", "EXPRTOKEN_PERIOD", "EXPRTOKEN_DOUBLE_PERIOD", "EXPRTOKEN_ATSIGN", "EXPRTOKEN_COMMA", "EXPRTOKEN_DOUBLE_COLON", "EXPRTOKEN_NAMETEST_ANY", "EXPRTOKEN_NAMETEST_NAMESPACE", "EXPRTOKEN_NAMETEST_QNAME", "EXPRTOKEN_NODETYPE_COMMENT", "EXPRTOKEN_NODETYPE_TEXT", "EXPRTOKEN_NODETYPE_PI", "EXPRTOKEN_NODETYPE_NODE", "EXPRTOKEN_OPERATOR_AND", "EXPRTOKEN_OPERATOR_OR", "EXPRTOKEN_OPERATOR_MOD", "EXPRTOKEN_OPERATOR_DIV", "EXPRTOKEN_OPERATOR_MULT", "EXPRTOKEN_OPERATOR_SLASH", "EXPRTOKEN_OPERATOR_DOUBLE_SLASH", "EXPRTOKEN_OPERATOR_UNION", "EXPRTOKEN_OPERATOR_PLUS", "EXPRTOKEN_OPERATOR_MINUS", "EXPRTOKEN_OPERATOR_EQUAL", "EXPRTOKEN_OPERATOR_NOT_EQUAL", "EXPRTOKEN_OPERATOR_LESS", "EXPRTOKEN_OPERATOR_LESS_EQUAL", "EXPRTOKEN_OPERATOR_GREATER", "EXPRTOKEN_OPERATOR_GREATER_EQUAL", "EXPRTOKEN_FUNCTION_NAME", "EXPRTOKEN_AXISNAME_ANCESTOR", "EXPRTOKEN_AXISNAME_ANCESTOR_OR_SELF", "EXPRTOKEN_AXISNAME_ATTRIBUTE", "EXPRTOKEN_AXISNAME_CHILD", "EXPRTOKEN_AXISNAME_DESCENDANT", "EXPRTOKEN_AXISNAME_DESCENDANT_OR_SELF", "EXPRTOKEN_AXISNAME_FOLLOWING", "EXPRTOKEN_AXISNAME_FOLLOWING_SIBLING", "EXPRTOKEN_AXISNAME_NAMESPACE", "EXPRTOKEN_AXISNAME_PARENT", "EXPRTOKEN_AXISNAME_PRECEDING", "EXPRTOKEN_AXISNAME_PRECEDING_SIBLING", "EXPRTOKEN_AXISNAME_SELF", "EXPRTOKEN_LITERAL", "EXPRTOKEN_NUMBER", "EXPRTOKEN_VARIABLE_REFERENCE"};
        private int fCurrentTokenIndex;
        private SymbolTable fSymbolTable;
        private int[] fTokens = new int[256];
        private int fTokenCount = 0;
        private Hashtable fSymbolMapping = new Hashtable();
        private Hashtable fTokenNames = new Hashtable();

        public Tokens(SymbolTable symbolTable) {
            this.fSymbolTable = symbolTable;
            String[] strArr = {"ancestor", "ancestor-or-self", "attribute", "child", "descendant", "descendant-or-self", "following", "following-sibling", "namespace", "parent", "preceding", "preceding-sibling", "self"};
            int i = 0;
            for (int i2 = 13; i < i2; i2 = 13) {
                this.fSymbolMapping.put(this.fSymbolTable.addSymbol(strArr[i]), new Integer(i));
                i++;
            }
            this.fTokenNames.put(new Integer(0), "EXPRTOKEN_OPEN_PAREN");
            this.fTokenNames.put(new Integer(1), "EXPRTOKEN_CLOSE_PAREN");
            this.fTokenNames.put(new Integer(2), "EXPRTOKEN_OPEN_BRACKET");
            this.fTokenNames.put(new Integer(3), "EXPRTOKEN_CLOSE_BRACKET");
            this.fTokenNames.put(new Integer(4), "EXPRTOKEN_PERIOD");
            this.fTokenNames.put(new Integer(5), "EXPRTOKEN_DOUBLE_PERIOD");
            this.fTokenNames.put(new Integer(6), "EXPRTOKEN_ATSIGN");
            this.fTokenNames.put(new Integer(7), "EXPRTOKEN_COMMA");
            this.fTokenNames.put(new Integer(8), "EXPRTOKEN_DOUBLE_COLON");
            this.fTokenNames.put(new Integer(9), "EXPRTOKEN_NAMETEST_ANY");
            this.fTokenNames.put(new Integer(10), "EXPRTOKEN_NAMETEST_NAMESPACE");
            this.fTokenNames.put(new Integer(11), "EXPRTOKEN_NAMETEST_QNAME");
            this.fTokenNames.put(new Integer(12), "EXPRTOKEN_NODETYPE_COMMENT");
            this.fTokenNames.put(new Integer(13), "EXPRTOKEN_NODETYPE_TEXT");
            this.fTokenNames.put(new Integer(14), "EXPRTOKEN_NODETYPE_PI");
            this.fTokenNames.put(new Integer(15), "EXPRTOKEN_NODETYPE_NODE");
            this.fTokenNames.put(new Integer(16), "EXPRTOKEN_OPERATOR_AND");
            this.fTokenNames.put(new Integer(17), "EXPRTOKEN_OPERATOR_OR");
            this.fTokenNames.put(new Integer(18), "EXPRTOKEN_OPERATOR_MOD");
            this.fTokenNames.put(new Integer(19), "EXPRTOKEN_OPERATOR_DIV");
            this.fTokenNames.put(new Integer(20), "EXPRTOKEN_OPERATOR_MULT");
            this.fTokenNames.put(new Integer(21), "EXPRTOKEN_OPERATOR_SLASH");
            this.fTokenNames.put(new Integer(22), "EXPRTOKEN_OPERATOR_DOUBLE_SLASH");
            this.fTokenNames.put(new Integer(23), "EXPRTOKEN_OPERATOR_UNION");
            this.fTokenNames.put(new Integer(24), "EXPRTOKEN_OPERATOR_PLUS");
            this.fTokenNames.put(new Integer(25), "EXPRTOKEN_OPERATOR_MINUS");
            this.fTokenNames.put(new Integer(26), "EXPRTOKEN_OPERATOR_EQUAL");
            this.fTokenNames.put(new Integer(27), "EXPRTOKEN_OPERATOR_NOT_EQUAL");
            this.fTokenNames.put(new Integer(28), "EXPRTOKEN_OPERATOR_LESS");
            this.fTokenNames.put(new Integer(29), "EXPRTOKEN_OPERATOR_LESS_EQUAL");
            this.fTokenNames.put(new Integer(30), "EXPRTOKEN_OPERATOR_GREATER");
            this.fTokenNames.put(new Integer(31), "EXPRTOKEN_OPERATOR_GREATER_EQUAL");
            this.fTokenNames.put(new Integer(32), "EXPRTOKEN_FUNCTION_NAME");
            this.fTokenNames.put(new Integer(33), "EXPRTOKEN_AXISNAME_ANCESTOR");
            this.fTokenNames.put(new Integer(34), "EXPRTOKEN_AXISNAME_ANCESTOR_OR_SELF");
            this.fTokenNames.put(new Integer(35), "EXPRTOKEN_AXISNAME_ATTRIBUTE");
            this.fTokenNames.put(new Integer(36), "EXPRTOKEN_AXISNAME_CHILD");
            this.fTokenNames.put(new Integer(37), "EXPRTOKEN_AXISNAME_DESCENDANT");
            this.fTokenNames.put(new Integer(38), "EXPRTOKEN_AXISNAME_DESCENDANT_OR_SELF");
            this.fTokenNames.put(new Integer(39), "EXPRTOKEN_AXISNAME_FOLLOWING");
            this.fTokenNames.put(new Integer(40), "EXPRTOKEN_AXISNAME_FOLLOWING_SIBLING");
            this.fTokenNames.put(new Integer(41), "EXPRTOKEN_AXISNAME_NAMESPACE");
            this.fTokenNames.put(new Integer(42), "EXPRTOKEN_AXISNAME_PARENT");
            this.fTokenNames.put(new Integer(43), "EXPRTOKEN_AXISNAME_PRECEDING");
            this.fTokenNames.put(new Integer(44), "EXPRTOKEN_AXISNAME_PRECEDING_SIBLING");
            this.fTokenNames.put(new Integer(45), "EXPRTOKEN_AXISNAME_SELF");
            this.fTokenNames.put(new Integer(46), "EXPRTOKEN_LITERAL");
            this.fTokenNames.put(new Integer(47), "EXPRTOKEN_NUMBER");
            this.fTokenNames.put(new Integer(48), "EXPRTOKEN_VARIABLE_REFERENCE");
        }

        public void addToken(int i) {
            try {
                this.fTokens[this.fTokenCount] = i;
            } catch (ArrayIndexOutOfBoundsException unused) {
                int[] iArr = this.fTokens;
                int i2 = this.fTokenCount;
                int[] iArr2 = new int[i2 << 1];
                this.fTokens = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                this.fTokens[this.fTokenCount] = i;
            }
            this.fTokenCount++;
        }

        public void addToken(String str) {
            Integer num = (Integer) this.fTokenNames.get(str);
            if (num == null) {
                num = new Integer(this.fTokenNames.size());
                this.fTokenNames.put(num, str);
            }
            addToken(num.intValue());
        }

        public void dumpTokens() {
            PrintStream printStream;
            String str;
            PrintStream printStream2;
            StringBuilder sb;
            int i;
            StringBuilder sb2;
            int i2;
            StringBuilder sbAppend;
            int i3 = 0;
            while (i3 < this.fTokenCount) {
                switch (this.fTokens[i3]) {
                    case 0:
                        printStream = System.out;
                        str = "<OPEN_PAREN/>";
                        printStream.print(str);
                        break;
                    case 1:
                        printStream = System.out;
                        str = "<CLOSE_PAREN/>";
                        printStream.print(str);
                        break;
                    case 2:
                        printStream = System.out;
                        str = "<OPEN_BRACKET/>";
                        printStream.print(str);
                        break;
                    case 3:
                        printStream = System.out;
                        str = "<CLOSE_BRACKET/>";
                        printStream.print(str);
                        break;
                    case 4:
                        printStream = System.out;
                        str = "<PERIOD/>";
                        printStream.print(str);
                        break;
                    case 5:
                        printStream = System.out;
                        str = "<DOUBLE_PERIOD/>";
                        printStream.print(str);
                        break;
                    case 6:
                        printStream = System.out;
                        str = "<ATSIGN/>";
                        printStream.print(str);
                        break;
                    case 7:
                        printStream = System.out;
                        str = "<COMMA/>";
                        printStream.print(str);
                        break;
                    case 8:
                        printStream = System.out;
                        str = "<DOUBLE_COLON/>";
                        printStream.print(str);
                        break;
                    case 9:
                        printStream = System.out;
                        str = "<NAMETEST_ANY/>";
                        printStream.print(str);
                        break;
                    case 10:
                        System.out.print("<NAMETEST_NAMESPACE");
                        printStream2 = System.out;
                        sb = new StringBuilder(" prefix=\"");
                        i3++;
                        i = this.fTokens[i3];
                        sbAppend = sb.append(getTokenString(i));
                        printStream2.print(sbAppend.append("\"").toString());
                        System.out.print("/>");
                        break;
                    case 11:
                        System.out.print("<NAMETEST_QNAME");
                        int i4 = i3 + 1;
                        if (this.fTokens[i4] != -1) {
                            System.out.print(" prefix=\"" + getTokenString(this.fTokens[i4]) + "\"");
                        }
                        printStream2 = System.out;
                        sb2 = new StringBuilder(" localpart=\"");
                        i3 += 2;
                        i2 = this.fTokens[i3];
                        sbAppend = sb2.append(getTokenString(i2));
                        printStream2.print(sbAppend.append("\"").toString());
                        System.out.print("/>");
                        break;
                    case 12:
                        printStream = System.out;
                        str = "<NODETYPE_COMMENT/>";
                        printStream.print(str);
                        break;
                    case 13:
                        printStream = System.out;
                        str = "<NODETYPE_TEXT/>";
                        printStream.print(str);
                        break;
                    case 14:
                        printStream = System.out;
                        str = "<NODETYPE_PI/>";
                        printStream.print(str);
                        break;
                    case 15:
                        printStream = System.out;
                        str = "<NODETYPE_NODE/>";
                        printStream.print(str);
                        break;
                    case 16:
                        printStream = System.out;
                        str = "<OPERATOR_AND/>";
                        printStream.print(str);
                        break;
                    case 17:
                        printStream = System.out;
                        str = "<OPERATOR_OR/>";
                        printStream.print(str);
                        break;
                    case 18:
                        printStream = System.out;
                        str = "<OPERATOR_MOD/>";
                        printStream.print(str);
                        break;
                    case 19:
                        printStream = System.out;
                        str = "<OPERATOR_DIV/>";
                        printStream.print(str);
                        break;
                    case 20:
                        printStream = System.out;
                        str = "<OPERATOR_MULT/>";
                        printStream.print(str);
                        break;
                    case 21:
                        System.out.print("<OPERATOR_SLASH/>");
                        if (i3 + 1 < this.fTokenCount) {
                            System.out.println();
                            printStream = System.out;
                            str = "  ";
                            printStream.print(str);
                        }
                        break;
                    case 22:
                        printStream = System.out;
                        str = "<OPERATOR_DOUBLE_SLASH/>";
                        printStream.print(str);
                        break;
                    case 23:
                        printStream = System.out;
                        str = "<OPERATOR_UNION/>";
                        printStream.print(str);
                        break;
                    case 24:
                        printStream = System.out;
                        str = "<OPERATOR_PLUS/>";
                        printStream.print(str);
                        break;
                    case 25:
                        printStream = System.out;
                        str = "<OPERATOR_MINUS/>";
                        printStream.print(str);
                        break;
                    case 26:
                        printStream = System.out;
                        str = "<OPERATOR_EQUAL/>";
                        printStream.print(str);
                        break;
                    case 27:
                        printStream = System.out;
                        str = "<OPERATOR_NOT_EQUAL/>";
                        printStream.print(str);
                        break;
                    case 28:
                        printStream = System.out;
                        str = "<OPERATOR_LESS/>";
                        printStream.print(str);
                        break;
                    case 29:
                        printStream = System.out;
                        str = "<OPERATOR_LESS_EQUAL/>";
                        printStream.print(str);
                        break;
                    case 30:
                        printStream = System.out;
                        str = "<OPERATOR_GREATER/>";
                        printStream.print(str);
                        break;
                    case 31:
                        printStream = System.out;
                        str = "<OPERATOR_GREATER_EQUAL/>";
                        printStream.print(str);
                        break;
                    case 32:
                        System.out.print("<FUNCTION_NAME");
                        int i5 = i3 + 1;
                        if (this.fTokens[i5] != -1) {
                            System.out.print(" prefix=\"" + getTokenString(this.fTokens[i5]) + "\"");
                        }
                        printStream2 = System.out;
                        sb2 = new StringBuilder(" localpart=\"");
                        i3 += 2;
                        i2 = this.fTokens[i3];
                        sbAppend = sb2.append(getTokenString(i2));
                        printStream2.print(sbAppend.append("\"").toString());
                        System.out.print("/>");
                        break;
                    case 33:
                        printStream = System.out;
                        str = "<AXISNAME_ANCESTOR/>";
                        printStream.print(str);
                        break;
                    case 34:
                        printStream = System.out;
                        str = "<AXISNAME_ANCESTOR_OR_SELF/>";
                        printStream.print(str);
                        break;
                    case 35:
                        printStream = System.out;
                        str = "<AXISNAME_ATTRIBUTE/>";
                        printStream.print(str);
                        break;
                    case 36:
                        printStream = System.out;
                        str = "<AXISNAME_CHILD/>";
                        printStream.print(str);
                        break;
                    case 37:
                        printStream = System.out;
                        str = "<AXISNAME_DESCENDANT/>";
                        printStream.print(str);
                        break;
                    case 38:
                        printStream = System.out;
                        str = "<AXISNAME_DESCENDANT_OR_SELF/>";
                        printStream.print(str);
                        break;
                    case 39:
                        printStream = System.out;
                        str = "<AXISNAME_FOLLOWING/>";
                        printStream.print(str);
                        break;
                    case 40:
                        printStream = System.out;
                        str = "<AXISNAME_FOLLOWING_SIBLING/>";
                        printStream.print(str);
                        break;
                    case 41:
                        printStream = System.out;
                        str = "<AXISNAME_NAMESPACE/>";
                        printStream.print(str);
                        break;
                    case 42:
                        printStream = System.out;
                        str = "<AXISNAME_PARENT/>";
                        printStream.print(str);
                        break;
                    case 43:
                        printStream = System.out;
                        str = "<AXISNAME_PRECEDING/>";
                        printStream.print(str);
                        break;
                    case 44:
                        printStream = System.out;
                        str = "<AXISNAME_PRECEDING_SIBLING/>";
                        printStream.print(str);
                        break;
                    case 45:
                        printStream = System.out;
                        str = "<AXISNAME_SELF/>";
                        printStream.print(str);
                        break;
                    case 46:
                        System.out.print("<LITERAL");
                        printStream2 = System.out;
                        sb = new StringBuilder(" value=\"");
                        i3++;
                        i = this.fTokens[i3];
                        sbAppend = sb.append(getTokenString(i));
                        printStream2.print(sbAppend.append("\"").toString());
                        System.out.print("/>");
                        break;
                    case 47:
                        System.out.print("<NUMBER");
                        System.out.print(" whole=\"" + getTokenString(this.fTokens[i3 + 1]) + "\"");
                        printStream2 = System.out;
                        sb = new StringBuilder(" part=\"");
                        i3 += 2;
                        i = this.fTokens[i3];
                        sbAppend = sb.append(getTokenString(i));
                        printStream2.print(sbAppend.append("\"").toString());
                        System.out.print("/>");
                        break;
                    case 48:
                        System.out.print("<VARIABLE_REFERENCE");
                        int i6 = i3 + 1;
                        if (this.fTokens[i6] != -1) {
                            System.out.print(" prefix=\"" + getTokenString(this.fTokens[i6]) + "\"");
                        }
                        printStream2 = System.out;
                        sb2 = new StringBuilder(" localpart=\"");
                        i3 += 2;
                        i2 = this.fTokens[i3];
                        sbAppend = sb2.append(getTokenString(i2));
                        printStream2.print(sbAppend.append("\"").toString());
                        System.out.print("/>");
                        break;
                    default:
                        System.out.println("<???/>");
                        break;
                }
                i3++;
            }
            System.out.println();
        }

        public String getTokenString(int i) {
            return (String) this.fTokenNames.get(new Integer(i));
        }

        public boolean hasMore() {
            return this.fCurrentTokenIndex < this.fTokenCount;
        }

        public int nextToken() throws XPathException {
            int i = this.fCurrentTokenIndex;
            if (i == this.fTokenCount) {
                throw new XPathException("c-general-xpath");
            }
            int[] iArr = this.fTokens;
            this.fCurrentTokenIndex = i + 1;
            return iArr[i];
        }

        public String nextTokenAsString() throws XPathException {
            String tokenString = getTokenString(nextToken());
            if (tokenString != null) {
                return tokenString;
            }
            throw new XPathException("c-general-xpath");
        }

        public int peekToken() throws XPathException {
            int i = this.fCurrentTokenIndex;
            if (i != this.fTokenCount) {
                return this.fTokens[i];
            }
            throw new XPathException("c-general-xpath");
        }

        public void rewind() {
            this.fCurrentTokenIndex = 0;
        }
    }

    public XPath(String str, SymbolTable symbolTable, NamespaceContext namespaceContext) throws XPathException {
        this.fExpression = str;
        this.fSymbolTable = symbolTable;
        this.fLocationPaths = parseExpression(namespaceContext);
    }

    private LocationPath buildLocationPath(Vector vector) throws XPathException {
        int size = vector.size();
        check(size != 0);
        Step[] stepArr = new Step[size];
        vector.copyInto(stepArr);
        vector.removeAllElements();
        return new LocationPath(stepArr);
    }

    private static void check(boolean z) throws XPathException {
        if (!z) {
            throw new XPathException("c-general-xpath");
        }
    }

    public static void main(String[] strArr) throws Exception {
        for (String str : strArr) {
            System.out.println("# XPath expression: \"" + str + '\"');
            try {
                System.out.println("expanded xpath: \"" + new XPath(str, new SymbolTable(), null).toString() + '\"');
            } catch (XPathException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }

    private LocationPath[] parseExpression(NamespaceContext namespaceContext) throws XPathException {
        int iNextToken;
        Step step;
        Tokens tokens = new Tokens(this.fSymbolTable);
        if (!new Scanner(this.fSymbolTable) { // from class: org.apache.xerces.impl.xpath.XPath.1
            @Override // org.apache.xerces.impl.xpath.XPath.Scanner
            protected void addToken(Tokens tokens2, int i) throws XPathException {
                if (i != 6 && i != 11 && i != 21 && i != 4 && i != 9 && i != 10 && i != 22 && i != 23 && i != 36 && i != 35 && i != 8) {
                    throw new XPathException("c-general-xpath");
                }
                super.addToken(tokens2, i);
            }
        }.scanExpr(this.fSymbolTable, tokens, this.fExpression, 0, this.fExpression.length())) {
            throw new XPathException("c-general-xpath");
        }
        Vector vector = new Vector();
        ArrayList arrayList = new ArrayList();
        while (true) {
            boolean z = true;
            while (true) {
                if (!tokens.hasMore()) {
                    check(!z);
                    arrayList.add(buildLocationPath(vector));
                    return (LocationPath[]) arrayList.toArray(new LocationPath[arrayList.size()]);
                }
                iNextToken = tokens.nextToken();
                boolean z2 = false;
                if (iNextToken != 4) {
                    if (iNextToken == 6) {
                        check(z);
                        step = new Step(new Axis((short) 2), parseNodeTest(tokens.nextToken(), tokens, namespaceContext));
                    } else if (iNextToken == 35) {
                        check(z);
                        if (tokens.nextToken() != 8) {
                            throw new XPathException("c-general-xpath");
                        }
                        step = new Step(new Axis((short) 2), parseNodeTest(tokens.nextToken(), tokens, namespaceContext));
                    } else if (iNextToken != 36) {
                        switch (iNextToken) {
                            case 8:
                                throw new XPathException("c-general-xpath");
                            case 9:
                            case 10:
                            case 11:
                                check(z);
                                step = new Step(new Axis((short) 1), parseNodeTest(iNextToken, tokens, namespaceContext));
                                break;
                        }
                    } else {
                        check(z);
                        if (tokens.nextToken() != 8) {
                            throw new XPathException("c-general-xpath");
                        }
                        step = new Step(new Axis((short) 1), parseNodeTest(tokens.nextToken(), tokens, namespaceContext));
                    }
                    vector.addElement(step);
                } else {
                    check(z);
                    if (vector.size() == 0) {
                        vector.addElement(new Step(new Axis((short) 3), new NodeTest((short) 3)));
                        if (tokens.hasMore() && tokens.peekToken() == 22) {
                            tokens.nextToken();
                            vector.addElement(new Step(new Axis((short) 4), new NodeTest((short) 3)));
                            z2 = true;
                        }
                    }
                }
                z = z2;
            }
            switch (iNextToken) {
                case 21:
                    check(!z);
                    break;
                case 22:
                    throw new XPathException("c-general-xpath");
                case 23:
                    check(!z);
                    arrayList.add(buildLocationPath(vector));
                    break;
                default:
                    throw new InternalError();
            }
        }
    }

    private NodeTest parseNodeTest(int i, Tokens tokens, NamespaceContext namespaceContext) throws XPathException {
        switch (i) {
            case 9:
                return new NodeTest((short) 2);
            case 10:
            case 11:
                String strNextTokenAsString = tokens.nextTokenAsString();
                String uri = (namespaceContext == null || strNextTokenAsString == XMLSymbols.EMPTY_STRING) ? null : namespaceContext.getURI(strNextTokenAsString);
                if (strNextTokenAsString != XMLSymbols.EMPTY_STRING && namespaceContext != null && uri == null) {
                    throw new XPathException("c-general-xpath-ns");
                }
                if (i == 10) {
                    return new NodeTest(strNextTokenAsString, uri);
                }
                String strNextTokenAsString2 = tokens.nextTokenAsString();
                return new NodeTest(new QName(strNextTokenAsString, strNextTokenAsString2, strNextTokenAsString != XMLSymbols.EMPTY_STRING ? this.fSymbolTable.addSymbol(strNextTokenAsString + ':' + strNextTokenAsString2) : strNextTokenAsString2, uri));
            default:
                throw new XPathException("c-general-xpath");
        }
    }

    public LocationPath getLocationPath() {
        return (LocationPath) this.fLocationPaths[0].clone();
    }

    public LocationPath[] getLocationPaths() {
        LocationPath[] locationPathArr = new LocationPath[this.fLocationPaths.length];
        int i = 0;
        while (true) {
            LocationPath[] locationPathArr2 = this.fLocationPaths;
            if (i >= locationPathArr2.length) {
                return locationPathArr;
            }
            locationPathArr[i] = (LocationPath) locationPathArr2[i].clone();
            i++;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.fLocationPaths.length; i++) {
            if (i > 0) {
                stringBuffer.append('|');
            }
            stringBuffer.append(this.fLocationPaths[i].toString());
        }
        return stringBuffer.toString();
    }
}
