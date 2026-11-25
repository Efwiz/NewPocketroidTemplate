package org.apache.xerces.impl.xpath.regex;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Hashtable;
import java.util.Locale;
import org.bouncycastle.crypto.tls.CipherSuite;

/* JADX INFO: loaded from: classes13.dex */
class ParserForXMLSchema extends RegexParser {
    private static final String DIGITS = "09٠٩۰۹०९০৯੦੯૦૯୦୯௧௯౦౯೦೯൦൯๐๙໐໙༠༩";
    private static final int[] DIGITS_INTS = {48, 57, 1632, 1641, XKeySym.XK_Cyrillic_PE, XKeySym.XK_Cyrillic_YERU, 2406, 2415, 2534, XKeySym.XK_horizlinescan1, 2662, 2671, XKeySym.XK_enfilledcircbullet, 2799, 2918, 2927, 3047, 3055, 3174, 3183, 3302, XKeySym.XK_hebrew_finalnun, 3430, 3439, 3664, 3673, XKeySym.XK_Hangul_YU, XKeySym.XK_Hangul_J_NieunHieuh, 3872, 3881, 4160, 4169, 4969, 4977, 6112, 6121, 6160, 6169, CipherSuite.DRAFT_TLS_PSK_WITH_AES_128_OCB, 65305, 120782, 120831};
    private static final String LETTERS = "AZazÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣";
    private static final String NAMECHARS = "-.0:AZ__az··ÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁːˑ̀͠͡ͅΆΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁ҃҆ҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆֹֻֽֿֿׁׂ֑֣֡ׄׄאתװײءغـْ٠٩ٰڷںھۀێېۓە۪ۭۨ۰۹ँःअह़्॑॔क़ॣ०९ঁঃঅঌএঐওনপরললশহ়়াৄেৈো্ৗৗড়ঢ়য়ৣ০ৱਂਂਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹ਼਼ਾੂੇੈੋ੍ਖ਼ੜਫ਼ਫ਼੦ੴઁઃઅઋઍઍએઑઓનપરલળવહ઼ૅેૉો્ૠૠ૦૯ଁଃଅଌଏଐଓନପରଲଳଶହ଼ୃେୈୋ୍ୖୗଡ଼ଢ଼ୟୡ୦୯ஂஃஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹாூெைொ்ௗௗ௧௯ఁఃఅఌఎఐఒనపళవహాౄెైొ్ౕౖౠౡ౦౯ಂಃಅಌಎಐಒನಪಳವಹಾೄೆೈೊ್ೕೖೞೞೠೡ೦೯ംഃഅഌഎഐഒനപഹാൃെൈൊ്ൗൗൠൡ൦൯กฮะฺเ๎๐๙ກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະູົຽເໄໆໆ່ໍ໐໙༘༙༠༩༹༹༵༵༷༷༾ཇཉཀྵ྄ཱ྆ྋྐྕྗྗྙྭྱྷྐྵྐྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼ⃐⃜⃡⃡ΩΩKÅ℮℮ↀↂ々々〇〇〡〯〱〵ぁゔ゙゚ゝゞァヺーヾㄅㄬ一龥가힣";
    private static final String SPACES = "\t\n\r\r  ";
    private static Hashtable ranges;
    private static Hashtable ranges2;

    public ParserForXMLSchema() {
    }

    public ParserForXMLSchema(Locale locale) {
        super(locale);
    }

    protected static synchronized RangeToken getRange(String str, boolean z) {
        if (ranges == null) {
            ranges = new Hashtable();
            ranges2 = new Hashtable();
            RangeToken rangeTokenCreateRange = Token.createRange();
            setupRange(rangeTokenCreateRange, SPACES);
            ranges.put("xml:isSpace", rangeTokenCreateRange);
            ranges2.put("xml:isSpace", Token.complementRanges(rangeTokenCreateRange));
            RangeToken rangeTokenCreateRange2 = Token.createRange();
            setupRange(rangeTokenCreateRange2, DIGITS_INTS);
            ranges.put("xml:isDigit", rangeTokenCreateRange2);
            ranges2.put("xml:isDigit", Token.complementRanges(rangeTokenCreateRange2));
            RangeToken rangeTokenCreateRange3 = Token.createRange();
            rangeTokenCreateRange3.mergeRanges(Token.getRange("P", true));
            rangeTokenCreateRange3.mergeRanges(Token.getRange("Z", true));
            rangeTokenCreateRange3.mergeRanges(Token.getRange("C", true));
            ranges2.put("xml:isWord", rangeTokenCreateRange3);
            ranges.put("xml:isWord", Token.complementRanges(rangeTokenCreateRange3));
            RangeToken rangeTokenCreateRange4 = Token.createRange();
            setupRange(rangeTokenCreateRange4, NAMECHARS);
            ranges.put("xml:isNameChar", rangeTokenCreateRange4);
            ranges2.put("xml:isNameChar", Token.complementRanges(rangeTokenCreateRange4));
            RangeToken rangeTokenCreateRange5 = Token.createRange();
            setupRange(rangeTokenCreateRange5, LETTERS);
            rangeTokenCreateRange5.addRange(95, 95);
            rangeTokenCreateRange5.addRange(58, 58);
            ranges.put("xml:isInitialNameChar", rangeTokenCreateRange5);
            ranges2.put("xml:isInitialNameChar", Token.complementRanges(rangeTokenCreateRange5));
        }
        return (RangeToken) (z ? ranges.get(str) : ranges2.get(str));
    }

    static void setupRange(Token token, String str) {
        int length = str.length();
        for (int i = 0; i < length; i += 2) {
            token.addRange(str.charAt(i), str.charAt(i + 1));
        }
    }

    static void setupRange(Token token, int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i += 2) {
            token.addRange(iArr[i], iArr[i + 1]);
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    boolean checkQuestion(int i) {
        return false;
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    int decodeEscaped() throws ParseException {
        if (read() != 10) {
            throw ex("parser.next.1", this.offset - 1);
        }
        int i = this.chardata;
        if (i == 45 || i == 46 || i == 63) {
            return i;
        }
        if (i == 110) {
            return 10;
        }
        if (i == 114) {
            return 13;
        }
        if (i == 116) {
            return 9;
        }
        switch (i) {
            case 40:
            case 41:
            case 42:
            case 43:
                return i;
            default:
                switch (i) {
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                        return i;
                    default:
                        switch (i) {
                            case 123:
                            case 124:
                            case 125:
                                return i;
                            default:
                                throw ex("parser.process.1", this.offset - 2);
                        }
                }
        }
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token getTokenForShorthand(int i) {
        if (i == 67) {
            return getRange("xml:isNameChar", false);
        }
        if (i == 68) {
            return getRange("xml:isDigit", false);
        }
        if (i == 73) {
            return getRange("xml:isInitialNameChar", false);
        }
        if (i == 83) {
            return getRange("xml:isSpace", false);
        }
        if (i == 87) {
            return getRange("xml:isWord", false);
        }
        if (i == 105) {
            return getRange("xml:isInitialNameChar", true);
        }
        if (i == 115) {
            return getRange("xml:isSpace", true);
        }
        if (i == 119) {
            return getRange("xml:isWord", true);
        }
        if (i == 99) {
            return getRange("xml:isNameChar", true);
        }
        if (i == 100) {
            return getRange("xml:isDigit", true);
        }
        throw new RuntimeException("Internal Error: shorthands: \\u" + Integer.toString(i, 16));
    }

    /* JADX WARN: Code duplicated, block: B:140:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:142:0x01b9 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:145:0x01c0  */
    /* JADX WARN: Code duplicated, block: B:156:0x00a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:158:0x010b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:159:0x0103 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:168:0x01c3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x0094  */
    /* JADX WARN: Code duplicated, block: B:50:0x009c  */
    /* JADX WARN: Code duplicated, block: B:53:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:72:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:74:0x00ee A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:75:0x00f0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:76:0x00f2  */
    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    protected RangeToken parseCharacterClass(boolean z) throws ParseException {
        RangeToken rangeTokenCreateRange;
        RangeToken rangeTokenCreateRange2;
        boolean z2;
        boolean z3;
        boolean z4;
        int i;
        RangeToken rangeTokenProcessBacksolidus_pP;
        int i2 = 1;
        setContext(1);
        next();
        boolean z5 = false;
        if (read() == 0 && this.chardata == 94) {
            next();
            rangeTokenCreateRange2 = Token.createRange();
            rangeTokenCreateRange2.addRange(0, 1114111);
            rangeTokenCreateRange = Token.createRange();
            z2 = true;
        } else {
            rangeTokenCreateRange = Token.createRange();
            rangeTokenCreateRange2 = null;
            z2 = false;
        }
        boolean z6 = true;
        while (true) {
            int i3 = read();
            if (i3 != i2) {
                if (i3 != 0 || this.chardata != 93 || z6) {
                    int iDecodeEscaped = this.chardata;
                    if (i3 != 10) {
                        if (i3 == 24 && !z6) {
                            if (z2) {
                                rangeTokenCreateRange2.subtractRanges(rangeTokenCreateRange);
                            } else {
                                rangeTokenCreateRange2 = rangeTokenCreateRange;
                            }
                            rangeTokenCreateRange2.subtractRanges(parseCharacterClass(z5));
                            if (read() == 0 && this.chardata == 93) {
                                break;
                            }
                            throw ex("parser.cc.5", this.offset);
                        }
                    } else {
                        if (iDecodeEscaped == 45) {
                            iDecodeEscaped = decodeEscaped();
                            z3 = i2;
                            z4 = z5;
                        } else if (iDecodeEscaped == 73) {
                            iDecodeEscaped = processCIinCharacterClass(rangeTokenCreateRange, iDecodeEscaped);
                            if (iDecodeEscaped < 0) {
                                z4 = i2;
                                z3 = z5;
                            }
                        } else {
                            if (iDecodeEscaped == 80) {
                                i = this.offset;
                                rangeTokenProcessBacksolidus_pP = processBacksolidus_pP(iDecodeEscaped);
                                if (rangeTokenProcessBacksolidus_pP != null) {
                                    throw ex("parser.atom.5", i);
                                }
                                rangeTokenCreateRange.mergeRanges(rangeTokenProcessBacksolidus_pP);
                            } else {
                                if (iDecodeEscaped != 83 && iDecodeEscaped != 87) {
                                    if (iDecodeEscaped == 105) {
                                        iDecodeEscaped = processCIinCharacterClass(rangeTokenCreateRange, iDecodeEscaped);
                                        if (iDecodeEscaped < 0) {
                                        }
                                    } else if (iDecodeEscaped == 112) {
                                        i = this.offset;
                                        rangeTokenProcessBacksolidus_pP = processBacksolidus_pP(iDecodeEscaped);
                                        if (rangeTokenProcessBacksolidus_pP != null) {
                                            throw ex("parser.atom.5", i);
                                        }
                                        rangeTokenCreateRange.mergeRanges(rangeTokenProcessBacksolidus_pP);
                                    } else if (iDecodeEscaped != 115 && iDecodeEscaped != 119) {
                                        if (iDecodeEscaped == 67) {
                                            iDecodeEscaped = processCIinCharacterClass(rangeTokenCreateRange, iDecodeEscaped);
                                            if (iDecodeEscaped < 0) {
                                            }
                                        } else if (iDecodeEscaped != 68) {
                                            if (iDecodeEscaped == 99) {
                                                iDecodeEscaped = processCIinCharacterClass(rangeTokenCreateRange, iDecodeEscaped);
                                                if (iDecodeEscaped < 0) {
                                                }
                                            } else if (iDecodeEscaped != 100) {
                                                iDecodeEscaped = decodeEscaped();
                                            }
                                        }
                                    }
                                }
                                rangeTokenCreateRange.mergeRanges(getTokenForShorthand(iDecodeEscaped));
                            }
                            z4 = i2;
                            z3 = z5;
                        }
                        next();
                        if (z4) {
                            if (i3 == 0) {
                                if (iDecodeEscaped != 91) {
                                    throw ex("parser.cc.6", this.offset - 2);
                                }
                                if (iDecodeEscaped != 93) {
                                    throw ex("parser.cc.7", this.offset - 2);
                                }
                                if (iDecodeEscaped == 45 && this.chardata != 93 && !z6) {
                                    throw ex("parser.cc.8", this.offset - 2);
                                }
                            }
                            if (read() != 0 && this.chardata == 45 && (iDecodeEscaped != 45 || z3 || !z6)) {
                                next();
                                int i4 = read();
                                if (i4 == 1) {
                                    throw ex("parser.cc.2", this.offset);
                                }
                                if (i4 == 0 && this.chardata == 93) {
                                    if (!isSet(2) || iDecodeEscaped > 65535) {
                                        rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped);
                                    } else {
                                        addCaseInsensitiveChar(rangeTokenCreateRange, iDecodeEscaped);
                                    }
                                    rangeTokenCreateRange.addRange(45, 45);
                                } else {
                                    if (i4 == 24) {
                                        throw ex("parser.cc.8", this.offset - 1);
                                    }
                                    int iDecodeEscaped2 = this.chardata;
                                    if (i4 == 0) {
                                        if (iDecodeEscaped2 == 91) {
                                            throw ex("parser.cc.6", this.offset - 1);
                                        }
                                        if (iDecodeEscaped2 == 93) {
                                            throw ex("parser.cc.7", this.offset - 1);
                                        }
                                        if (iDecodeEscaped2 == 45) {
                                            throw ex("parser.cc.8", this.offset - 2);
                                        }
                                    } else if (i4 == 10) {
                                        iDecodeEscaped2 = decodeEscaped();
                                    }
                                    next();
                                    if (iDecodeEscaped > iDecodeEscaped2) {
                                        throw ex("parser.ope.3", this.offset - 1);
                                    }
                                    if (!isSet(2) || (iDecodeEscaped > 65535 && iDecodeEscaped2 > 65535)) {
                                        rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped2);
                                    } else {
                                        addCaseInsensitiveCharRange(rangeTokenCreateRange, iDecodeEscaped, iDecodeEscaped2);
                                    }
                                }
                            } else if (isSet(2) || iDecodeEscaped > 65535) {
                                rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped);
                            } else {
                                addCaseInsensitiveChar(rangeTokenCreateRange, iDecodeEscaped);
                            }
                        }
                        i2 = 1;
                        z5 = false;
                        z6 = false;
                    }
                    z4 = z5;
                    z3 = z4;
                    next();
                    if (z4) {
                        if (i3 == 0) {
                            if (iDecodeEscaped != 91) {
                                throw ex("parser.cc.6", this.offset - 2);
                            }
                            if (iDecodeEscaped != 93) {
                                throw ex("parser.cc.7", this.offset - 2);
                            }
                            if (iDecodeEscaped == 45) {
                                throw ex("parser.cc.8", this.offset - 2);
                            }
                        }
                        if (read() != 0) {
                            if (isSet(2)) {
                                rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped);
                            } else {
                                rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped);
                            }
                        } else if (isSet(2)) {
                            rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped);
                        } else {
                            rangeTokenCreateRange.addRange(iDecodeEscaped, iDecodeEscaped);
                        }
                    }
                    i2 = 1;
                    z5 = false;
                    z6 = false;
                } else if (z2) {
                    rangeTokenCreateRange2.subtractRanges(rangeTokenCreateRange);
                    break;
                }
            }
            rangeTokenCreateRange2 = rangeTokenCreateRange;
            break;
        }
        if (read() == 1) {
            throw ex("parser.cc.2", this.offset);
        }
        rangeTokenCreateRange2.sortRanges();
        rangeTokenCreateRange2.compactRanges();
        setContext(0);
        next();
        return rangeTokenCreateRange2;
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    protected RangeToken parseSetOperations() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBackreference() throws ParseException {
        throw ex("parser.process.1", this.offset - 4);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_A() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_B() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_C() throws ParseException {
        next();
        return getTokenForShorthand(67);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_I() throws ParseException {
        next();
        return getTokenForShorthand(73);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_X() throws ParseException {
        throw ex("parser.process.1", this.offset - 2);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_Z() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_b() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_c() throws ParseException {
        next();
        return getTokenForShorthand(99);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_g() throws ParseException {
        throw ex("parser.process.1", this.offset - 2);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_gt() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_i() throws ParseException {
        next();
        return getTokenForShorthand(105);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_lt() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processBacksolidus_z() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    int processCIinCharacterClass(RangeToken rangeToken, int i) {
        rangeToken.mergeRanges(getTokenForShorthand(i));
        return -1;
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processCaret() throws ParseException {
        next();
        return Token.createChar(94);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processCondition() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processDollar() throws ParseException {
        next();
        return Token.createChar(36);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processIndependent() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processLookahead() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processLookbehind() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processModifiers() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processNegativelookahead() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processNegativelookbehind() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processParen() throws ParseException {
        next();
        Token.ParenToken parenTokenCreateParen = Token.createParen(parseRegex(), 0);
        if (read() != 7) {
            throw ex("parser.factor.1", this.offset - 1);
        }
        next();
        return parenTokenCreateParen;
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processParen2() throws ParseException {
        throw ex("parser.process.1", this.offset);
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processPlus(Token token) throws ParseException {
        next();
        return Token.createConcat(token, Token.createClosure(token));
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processQuestion(Token token) throws ParseException {
        next();
        Token.UnionToken unionTokenCreateUnion = Token.createUnion();
        unionTokenCreateUnion.addChild(token);
        unionTokenCreateUnion.addChild(Token.createEmpty());
        return unionTokenCreateUnion;
    }

    @Override // org.apache.xerces.impl.xpath.regex.RegexParser
    Token processStar(Token token) throws ParseException {
        next();
        return Token.createClosure(token);
    }
}
