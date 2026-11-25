package org.jetbrains.kotlin.js.parser.sourcemaps;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: JSON.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\"\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\u0082\b¢\u0006\u0002\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0003H\u0002J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0003H\u0002J\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u001bH\u0002J\b\u0010\"\u001a\u00020\u001bH\u0002J\b\u0010#\u001a\u00020\u001eH\u0002J\b\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020\u0003H\u0002J\b\u0010)\u001a\u00020\u0010H\u0002J\u0014\u0010*\u001a\u00020\u00102\n\u0010+\u001a\u00060,j\u0002`-H\u0002J\u0014\u0010.\u001a\u00020\u00102\n\u0010+\u001a\u00060,j\u0002`-H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonParser;", "", FirebaseAnalytics.Param.CONTENT, "", "(Ljava/lang/String;)V", "charCode", "", "col", "getContent", "()Ljava/lang/String;", "index", Constant.LINE, TypedValues.CycleType.S_WAVE_OFFSET, "wasCR", "", "advance", "", "advanceAndThen", ExifInterface.GPS_DIRECTION_TRUE, "f", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "error", "", "text", "expectCharAndAdvance", "expected", "", "expectString", "parse", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonNode;", "parseArray", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonArray;", "parseEscapeSequence", "parseHexEscapeSequence", "parseNode", "parseNumber", "", "parseObject", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/JsonObject;", "parseString", "skipSpaces", "takeExponentTo", "buffer", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "takeIntegerDigitsTo", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsonParser {
    private int charCode;
    private int col;
    private final String content;
    private int index;
    private int line;
    private int offset;
    private boolean wasCR;

    public JsonParser(String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        this.content = content;
        this.index = -1;
        int i = (-1) + 1;
        this.index = i;
        Character orNull = StringsKt.getOrNull(content, i);
        this.charCode = orNull != null ? orNull.charValue() : -1;
    }

    public final String getContent() {
        return this.content;
    }

    public final JsonNode parse() {
        JsonNode node = parseNode();
        skipSpaces();
        if (this.charCode == -1) {
            return node;
        }
        error("End of input expected");
        throw null;
    }

    private final void skipSpaces() {
        while (true) {
            int i = this.charCode;
            if (i != 9 && i != 10 && i != 13 && i != 32) {
                return;
            } else {
                advance();
            }
        }
    }

    private final JsonNode parseNode() {
        skipSpaces();
        int i = this.charCode;
        if (i == 34) {
            return new JsonString(parseString());
        }
        if (i == 45) {
            advance();
            return new JsonNumber(-parseNumber());
        }
        if (i == 91) {
            return parseArray();
        }
        if (i == 102) {
            expectString("false");
            return JsonBoolean.INSTANCE.getFALSE();
        }
        if (i == 110) {
            expectString("null");
            return JsonNull.INSTANCE;
        }
        if (i == 116) {
            expectString("true");
            return JsonBoolean.INSTANCE.getTRUE();
        }
        if (i == 123) {
            return parseObject();
        }
        if (48 <= i && i < 58) {
            return new JsonNumber(parseNumber());
        }
        error("Unexpected char");
        throw null;
    }

    private final JsonArray parseArray() {
        advance();
        JsonArray jsonArray = new JsonArray(new JsonNode[0]);
        while (true) {
            skipSpaces();
            if (this.charCode == 93) {
                advance();
                return jsonArray;
            }
            if (!jsonArray.getElements().isEmpty()) {
                expectCharAndAdvance(',');
            }
            jsonArray.getElements().add(parseNode());
        }
    }

    private final JsonObject parseObject() {
        advance();
        JsonObject jsonObject = new JsonObject((Pair<String, ? extends JsonNode>[]) new Pair[0]);
        while (true) {
            skipSpaces();
            if (this.charCode == 125) {
                advance();
                return jsonObject;
            }
            if (!jsonObject.getProperties().isEmpty()) {
                expectCharAndAdvance(',');
            }
            skipSpaces();
            String string = parseString();
            if (jsonObject.getProperties().containsKey(string)) {
                error("Duplicate property name: " + string);
                throw null;
            }
            skipSpaces();
            expectCharAndAdvance(':');
            jsonObject.getProperties().put(string, parseNode());
        }
    }

    private final String parseString() {
        expectCharAndAdvance('\"');
        StringBuilder sb = new StringBuilder();
        int i = this.index;
        while (this.index < this.content.length()) {
            char cCharAt = this.content.charAt(this.index);
            this.charCode = cCharAt;
            if (cCharAt < ' ') {
                error("Invalid character in string literal");
                throw null;
            }
            if (cCharAt == '\"') {
                sb.append((CharSequence) this.content, i, this.index);
                advance();
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
            if (cCharAt == '\\') {
                sb.append((CharSequence) this.content, i, this.index);
                sb.append(parseEscapeSequence());
                i = this.index;
            } else {
                this.index++;
            }
        }
        error("Unexpected end of file");
        throw null;
    }

    private final char parseEscapeSequence() {
        advance();
        int i = this.charCode;
        if (i == 34) {
            advance();
            return '\"';
        }
        if (i == 47) {
            advance();
            return '/';
        }
        if (i == 92) {
            advance();
            return '\\';
        }
        if (i == 98) {
            advance();
            return '\b';
        }
        if (i == 102) {
            advance();
            return '\f';
        }
        if (i == 110) {
            advance();
            return '\n';
        }
        if (i == 114) {
            advance();
            return '\r';
        }
        if (i == 116) {
            advance();
            return '\t';
        }
        if (i == 117) {
            return parseHexEscapeSequence();
        }
        error("Invalid escape sequence");
        throw null;
    }

    private final char parseHexEscapeSequence() {
        int i;
        advance();
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = i2 * 16;
            int i5 = this.charCode;
            if (48 <= i5 && i5 < 58) {
                i = i5 - 48;
            } else if (97 <= i5 && i5 < 103) {
                i = i5 - 87;
            } else {
                if (65 > i5 || i5 >= 71) {
                    error("Invalid escape sequence, hexadecimal char expected");
                    throw null;
                }
                i = i5 - 55;
            }
            i2 = i4 + i;
            advance();
        }
        return (char) i2;
    }

    private final double parseNumber() {
        StringBuilder sb = new StringBuilder();
        takeIntegerDigitsTo(sb);
        if (StringsKt.startsWith$default((CharSequence) sb, ExternalAnnotationProvider.NULLABLE, false, 2, (Object) null) && sb.length() > 1) {
            error("Number must not start with zero");
            throw null;
        }
        int i = this.charCode;
        if (i != 46) {
            if (i == 69 || i == 101) {
                takeExponentTo(sb);
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return Double.parseDouble(string);
            }
            String string2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            return Double.parseDouble(string2);
        }
        sb.append('.');
        advance();
        takeIntegerDigitsTo(sb);
        int i2 = this.charCode;
        if (i2 == 101 || i2 == 69) {
            takeExponentTo(sb);
        }
        String string3 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
        return Double.parseDouble(string3);
    }

    private final void takeIntegerDigitsTo(StringBuilder buffer) {
        int i = 0;
        while (true) {
            int i2 = this.charCode;
            if (48 > i2 || i2 >= 58) {
                break;
            }
            buffer.append((char) i2);
            advance();
            i++;
        }
        if (i != 0) {
            return;
        }
        error("Invalid char, decimal digit expected");
        throw null;
    }

    private final void takeExponentTo(StringBuilder buffer) {
        buffer.append('e');
        advance();
        int i = this.charCode;
        if (i == 45 || i == 43) {
            buffer.append((char) i);
            advance();
        }
        takeIntegerDigitsTo(buffer);
    }

    private final void expectString(String expected) {
        int length = expected.length();
        for (int i = 0; i < length; i++) {
            if (this.charCode != expected.charAt(i)) {
                error("Unexpected char, '" + expected + "' expected here");
                throw null;
            }
            advance();
        }
    }

    private final void expectCharAndAdvance(char expected) {
        if (this.charCode != expected) {
            error("Unexpected char, '" + expected + "' expected here");
            throw null;
        }
        advance();
    }

    private final void advance() {
        int i = this.charCode;
        if (i == -1) {
            return;
        }
        if (i != 10) {
            if (i == 13) {
                this.line++;
                this.col = 0;
                this.wasCR = true;
            } else {
                this.col++;
                this.wasCR = false;
            }
        } else if (!this.wasCR) {
            this.line++;
            this.col = 0;
            this.wasCR = false;
        }
        String str = this.content;
        int i2 = this.index + 1;
        this.index = i2;
        Character orNull = StringsKt.getOrNull(str, i2);
        this.charCode = orNull != null ? orNull.charValue() : -1;
        this.offset++;
    }

    private final Void error(String text) {
        throw new JsonSyntaxException(this.offset, this.line, this.col, text);
    }
}
