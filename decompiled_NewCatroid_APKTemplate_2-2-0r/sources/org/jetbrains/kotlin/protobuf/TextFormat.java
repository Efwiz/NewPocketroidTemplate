package org.jetbrains.kotlin.protobuf;

import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;
import org.luaj.vm2.LuaDouble;

/* JADX INFO: loaded from: classes4.dex */
public final class TextFormat {
    private static final Printer DEFAULT_PRINTER;
    private static final Printer SINGLE_LINE_PRINTER;
    private static final Printer UNICODE_PRINTER;
    private static final Logger logger = Logger.getLogger(TextFormat.class.getName());
    private static final Parser PARSER = Parser.newBuilder().build();

    private interface ByteSequence {
        byte byteAt(int i);

        int size();
    }

    private static int digitValue(byte b) {
        if (48 > b || b > 57) {
            return (97 > b || b > 122) ? b + Opcodes.OPC_jsr_w : b + Opcodes.OPC_ret;
        }
        return b - 48;
    }

    private static boolean isHex(byte b) {
        return (48 <= b && b <= 57) || (97 <= b && b <= 102) || (65 <= b && b <= 70);
    }

    private static boolean isOctal(byte b) {
        return 48 <= b && b <= 55;
    }

    private TextFormat() {
    }

    static {
        DEFAULT_PRINTER = new Printer();
        SINGLE_LINE_PRINTER = new Printer().setSingleLineMode(true);
        UNICODE_PRINTER = new Printer().setEscapeNonAscii(false);
    }

    public static void print(MessageOrBuilder messageOrBuilder, Appendable appendable) throws IOException {
        DEFAULT_PRINTER.print(messageOrBuilder, new TextGenerator(appendable));
    }

    public static void print(UnknownFieldSet unknownFieldSet, Appendable appendable) throws IOException {
        DEFAULT_PRINTER.printUnknownFields(unknownFieldSet, new TextGenerator(appendable));
    }

    public static void printUnicode(MessageOrBuilder messageOrBuilder, Appendable appendable) throws IOException {
        UNICODE_PRINTER.print(messageOrBuilder, new TextGenerator(appendable));
    }

    public static void printUnicode(UnknownFieldSet unknownFieldSet, Appendable appendable) throws IOException {
        UNICODE_PRINTER.printUnknownFields(unknownFieldSet, new TextGenerator(appendable));
    }

    public static String shortDebugString(MessageOrBuilder messageOrBuilder) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.print(messageOrBuilder, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String shortDebugString(UnknownFieldSet unknownFieldSet) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.printUnknownFields(unknownFieldSet, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(MessageOrBuilder messageOrBuilder) {
        try {
            StringBuilder sb = new StringBuilder();
            print(messageOrBuilder, sb);
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(UnknownFieldSet unknownFieldSet) {
        try {
            StringBuilder sb = new StringBuilder();
            print(unknownFieldSet, sb);
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(MessageOrBuilder messageOrBuilder) {
        try {
            StringBuilder sb = new StringBuilder();
            UNICODE_PRINTER.print(messageOrBuilder, new TextGenerator(sb));
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(UnknownFieldSet unknownFieldSet) {
        try {
            StringBuilder sb = new StringBuilder();
            UNICODE_PRINTER.printUnknownFields(unknownFieldSet, new TextGenerator(sb));
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
        DEFAULT_PRINTER.printField(fieldDescriptor, obj, new TextGenerator(appendable));
    }

    public static String printFieldToString(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
        try {
            StringBuilder sb = new StringBuilder();
            printField(fieldDescriptor, obj, sb);
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
        DEFAULT_PRINTER.printFieldValue(fieldDescriptor, obj, new TextGenerator(appendable));
    }

    public static void printUnknownFieldValue(int i, Object obj, Appendable appendable) throws IOException {
        printUnknownFieldValue(i, obj, new TextGenerator(appendable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void printUnknownFieldValue(int i, Object obj, TextGenerator textGenerator) throws IOException {
        int tagWireType = WireFormat.getTagWireType(i);
        if (tagWireType == 0) {
            textGenerator.print(unsignedToString(((Long) obj).longValue()));
            return;
        }
        if (tagWireType == 1) {
            textGenerator.print(String.format(null, "0x%016x", (Long) obj));
            return;
        }
        if (tagWireType == 2) {
            textGenerator.print("\"");
            textGenerator.print(escapeBytes((ByteString) obj));
            textGenerator.print("\"");
        } else if (tagWireType == 3) {
            DEFAULT_PRINTER.printUnknownFields((UnknownFieldSet) obj, textGenerator);
        } else {
            if (tagWireType == 5) {
                textGenerator.print(String.format(null, "0x%08x", (Integer) obj));
                return;
            }
            throw new IllegalArgumentException(new StringBuilder(20).append("Bad tag: ").append(i).toString());
        }
    }

    private static final class Printer {
        boolean escapeNonAscii;
        boolean singleLineMode;

        private Printer() {
            this.singleLineMode = false;
            this.escapeNonAscii = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Printer setSingleLineMode(boolean z) {
            this.singleLineMode = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Printer setEscapeNonAscii(boolean z) {
            this.escapeNonAscii = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void print(MessageOrBuilder messageOrBuilder, TextGenerator textGenerator) throws IOException {
            for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : messageOrBuilder.getAllFields().entrySet()) {
                printField(entry.getKey(), entry.getValue(), textGenerator);
            }
            printUnknownFields(messageOrBuilder.getUnknownFields(), textGenerator);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, TextGenerator textGenerator) throws IOException {
            if (fieldDescriptor.isRepeated()) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    printSingleField(fieldDescriptor, it.next(), textGenerator);
                }
                return;
            }
            printSingleField(fieldDescriptor, obj, textGenerator);
        }

        private void printSingleField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, TextGenerator textGenerator) throws IOException {
            if (fieldDescriptor.isExtension()) {
                textGenerator.print("[");
                if (fieldDescriptor.getContainingType().getOptions().getMessageSetWireFormat() && fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && fieldDescriptor.isOptional() && fieldDescriptor.getExtensionScope() == fieldDescriptor.getMessageType()) {
                    textGenerator.print(fieldDescriptor.getMessageType().getFullName());
                } else {
                    textGenerator.print(fieldDescriptor.getFullName());
                }
                textGenerator.print("]");
            } else if (fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP) {
                textGenerator.print(fieldDescriptor.getMessageType().getName());
            } else {
                textGenerator.print(fieldDescriptor.getName());
            }
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (this.singleLineMode) {
                    textGenerator.print(" { ");
                } else {
                    textGenerator.print(" {\n");
                    textGenerator.indent();
                }
            } else {
                textGenerator.print(": ");
            }
            printFieldValue(fieldDescriptor, obj, textGenerator);
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (this.singleLineMode) {
                    textGenerator.print("} ");
                    return;
                } else {
                    textGenerator.outdent();
                    textGenerator.print("}\n");
                    return;
                }
            }
            if (this.singleLineMode) {
                textGenerator.print(" ");
            } else {
                textGenerator.print("\n");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, TextGenerator textGenerator) throws IOException {
            switch (AnonymousClass3.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[fieldDescriptor.getType().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    textGenerator.print(((Integer) obj).toString());
                    break;
                case 4:
                case 5:
                case 6:
                    textGenerator.print(((Long) obj).toString());
                    break;
                case 7:
                    textGenerator.print(((Boolean) obj).toString());
                    break;
                case 8:
                    textGenerator.print(((Float) obj).toString());
                    break;
                case 9:
                    textGenerator.print(((Double) obj).toString());
                    break;
                case 10:
                case 11:
                    textGenerator.print(TextFormat.unsignedToString(((Integer) obj).intValue()));
                    break;
                case 12:
                case 13:
                    textGenerator.print(TextFormat.unsignedToString(((Long) obj).longValue()));
                    break;
                case 14:
                    textGenerator.print("\"");
                    String str = (String) obj;
                    textGenerator.print(this.escapeNonAscii ? TextFormat.escapeText(str) : TextFormat.escapeDoubleQuotesAndBackslashes(str));
                    textGenerator.print("\"");
                    break;
                case 15:
                    textGenerator.print("\"");
                    if (obj instanceof ByteString) {
                        textGenerator.print(TextFormat.escapeBytes((ByteString) obj));
                    } else {
                        textGenerator.print(TextFormat.escapeBytes((byte[]) obj));
                    }
                    textGenerator.print("\"");
                    break;
                case 16:
                    textGenerator.print(((Descriptors.EnumValueDescriptor) obj).getName());
                    break;
                case 17:
                case 18:
                    print((Message) obj, textGenerator);
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printUnknownFields(UnknownFieldSet unknownFieldSet, TextGenerator textGenerator) throws IOException {
            for (Map.Entry<Integer, UnknownFieldSet.Field> entry : unknownFieldSet.asMap().entrySet()) {
                int iIntValue = entry.getKey().intValue();
                UnknownFieldSet.Field value = entry.getValue();
                printUnknownField(iIntValue, 0, value.getVarintList(), textGenerator);
                printUnknownField(iIntValue, 5, value.getFixed32List(), textGenerator);
                printUnknownField(iIntValue, 1, value.getFixed64List(), textGenerator);
                printUnknownField(iIntValue, 2, value.getLengthDelimitedList(), textGenerator);
                for (UnknownFieldSet unknownFieldSet2 : value.getGroupList()) {
                    textGenerator.print(entry.getKey().toString());
                    if (this.singleLineMode) {
                        textGenerator.print(" { ");
                    } else {
                        textGenerator.print(" {\n");
                        textGenerator.indent();
                    }
                    printUnknownFields(unknownFieldSet2, textGenerator);
                    if (this.singleLineMode) {
                        textGenerator.print("} ");
                    } else {
                        textGenerator.outdent();
                        textGenerator.print("}\n");
                    }
                }
            }
        }

        private void printUnknownField(int i, int i2, List<?> list, TextGenerator textGenerator) throws IOException {
            for (Object obj : list) {
                textGenerator.print(String.valueOf(i));
                textGenerator.print(": ");
                TextFormat.printUnknownFieldValue(i2, obj, textGenerator);
                textGenerator.print(this.singleLineMode ? " " : "\n");
            }
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.protobuf.TextFormat$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.Type.values().length];
            $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.Type.INT32.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.SINT32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.SFIXED32.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.INT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.SINT64.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.SFIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.BOOL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.DOUBLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.UINT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.FIXED32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.UINT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.FIXED64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.STRING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.ENUM.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.MESSAGE.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.GROUP.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    public static String unsignedToString(int i) {
        if (i >= 0) {
            return Integer.toString(i);
        }
        return Long.toString(((long) i) & 4294967295L);
    }

    public static String unsignedToString(long j) {
        if (j >= 0) {
            return Long.toString(j);
        }
        return BigInteger.valueOf(j & Long.MAX_VALUE).setBit(63).toString();
    }

    private static final class TextGenerator {
        private boolean atStartOfLine;
        private final StringBuilder indent;
        private final Appendable output;

        private TextGenerator(Appendable appendable) {
            this.indent = new StringBuilder();
            this.atStartOfLine = true;
            this.output = appendable;
        }

        public void indent() {
            this.indent.append("  ");
        }

        public void outdent() {
            int length = this.indent.length();
            if (length == 0) {
                throw new IllegalArgumentException(" Outdent() without matching Indent().");
            }
            this.indent.delete(length - 2, length);
        }

        public void print(CharSequence charSequence) throws IOException {
            int length = charSequence.length();
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (charSequence.charAt(i2) == '\n') {
                    int i3 = i2 + 1;
                    write(charSequence.subSequence(i, i3));
                    this.atStartOfLine = true;
                    i = i3;
                }
            }
            write(charSequence.subSequence(i, length));
        }

        private void write(CharSequence charSequence) throws IOException {
            if (charSequence.length() == 0) {
                return;
            }
            if (this.atStartOfLine) {
                this.atStartOfLine = false;
                this.output.append(this.indent);
            }
            this.output.append(charSequence);
        }
    }

    private static final class Tokenizer {
        private int column;
        private String currentToken;
        private int line;
        private final Matcher matcher;
        private int pos;
        private int previousColumn;
        private int previousLine;
        private final CharSequence text;
        private static final Pattern WHITESPACE = Pattern.compile("(\\s|(#.*$))++", 8);
        private static final Pattern TOKEN = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_+-]*+|[.]?[0-9+-][0-9a-zA-Z_.+-]*+|\"([^\"\n\\\\]|\\\\.)*+(\"|\\\\?$)|'([^'\n\\\\]|\\\\.)*+('|\\\\?$)", 8);
        private static final Pattern DOUBLE_INFINITY = Pattern.compile("-?inf(inity)?", 2);
        private static final Pattern FLOAT_INFINITY = Pattern.compile("-?inf(inity)?f?", 2);
        private static final Pattern FLOAT_NAN = Pattern.compile("nanf?", 2);

        private Tokenizer(CharSequence charSequence) {
            this.pos = 0;
            this.line = 0;
            this.column = 0;
            this.previousLine = 0;
            this.previousColumn = 0;
            this.text = charSequence;
            this.matcher = WHITESPACE.matcher(charSequence);
            skipWhitespace();
            nextToken();
        }

        public boolean atEnd() {
            return this.currentToken.length() == 0;
        }

        public void nextToken() {
            this.previousLine = this.line;
            this.previousColumn = this.column;
            while (this.pos < this.matcher.regionStart()) {
                if (this.text.charAt(this.pos) == '\n') {
                    this.line++;
                    this.column = 0;
                } else {
                    this.column++;
                }
                this.pos++;
            }
            if (this.matcher.regionStart() == this.matcher.regionEnd()) {
                this.currentToken = "";
                return;
            }
            this.matcher.usePattern(TOKEN);
            if (this.matcher.lookingAt()) {
                this.currentToken = this.matcher.group();
                Matcher matcher = this.matcher;
                matcher.region(matcher.end(), this.matcher.regionEnd());
            } else {
                this.currentToken = String.valueOf(this.text.charAt(this.pos));
                Matcher matcher2 = this.matcher;
                matcher2.region(this.pos + 1, matcher2.regionEnd());
            }
            skipWhitespace();
        }

        private void skipWhitespace() {
            this.matcher.usePattern(WHITESPACE);
            if (this.matcher.lookingAt()) {
                Matcher matcher = this.matcher;
                matcher.region(matcher.end(), this.matcher.regionEnd());
            }
        }

        public boolean tryConsume(String str) {
            if (!this.currentToken.equals(str)) {
                return false;
            }
            nextToken();
            return true;
        }

        public void consume(String str) throws ParseException {
            if (tryConsume(str)) {
                return;
            }
            String strValueOf = String.valueOf(String.valueOf(str));
            throw parseException(new StringBuilder(strValueOf.length() + 12).append("Expected \"").append(strValueOf).append("\".").toString());
        }

        public boolean lookingAtInteger() {
            if (this.currentToken.length() == 0) {
                return false;
            }
            char cCharAt = this.currentToken.charAt(0);
            return ('0' <= cCharAt && cCharAt <= '9') || cCharAt == '-' || cCharAt == '+';
        }

        public boolean lookingAt(String str) {
            return this.currentToken.equals(str);
        }

        public String consumeIdentifier() throws ParseException {
            for (int i = 0; i < this.currentToken.length(); i++) {
                char cCharAt = this.currentToken.charAt(i);
                if (('a' > cCharAt || cCharAt > 'z') && (('A' > cCharAt || cCharAt > 'Z') && !(('0' <= cCharAt && cCharAt <= '9') || cCharAt == '_' || cCharAt == '.'))) {
                    String strValueOf = String.valueOf(String.valueOf(this.currentToken));
                    throw parseException(new StringBuilder(strValueOf.length() + 29).append("Expected identifier. Found '").append(strValueOf).append(FormatHelper.QUOTE).toString());
                }
            }
            String str = this.currentToken;
            nextToken();
            return str;
        }

        public boolean tryConsumeIdentifier() {
            try {
                consumeIdentifier();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public int consumeInt32() throws ParseException {
            try {
                int int32 = TextFormat.parseInt32(this.currentToken);
                nextToken();
                return int32;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public int consumeUInt32() throws ParseException {
            try {
                int uInt32 = TextFormat.parseUInt32(this.currentToken);
                nextToken();
                return uInt32;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public long consumeInt64() throws ParseException {
            try {
                long int64 = TextFormat.parseInt64(this.currentToken);
                nextToken();
                return int64;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeInt64() {
            try {
                consumeInt64();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public long consumeUInt64() throws ParseException {
            try {
                long uInt64 = TextFormat.parseUInt64(this.currentToken);
                nextToken();
                return uInt64;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeUInt64() {
            try {
                consumeUInt64();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public double consumeDouble() throws ParseException {
            if (DOUBLE_INFINITY.matcher(this.currentToken).matches()) {
                boolean zStartsWith = this.currentToken.startsWith(Parameters.DEFAULT_OPTION_PREFIXES);
                nextToken();
                return zStartsWith ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            if (this.currentToken.equalsIgnoreCase(LuaDouble.JSTR_NAN)) {
                nextToken();
                return Double.NaN;
            }
            try {
                double d = Double.parseDouble(this.currentToken);
                nextToken();
                return d;
            } catch (NumberFormatException e) {
                throw floatParseException(e);
            }
        }

        public boolean tryConsumeDouble() {
            try {
                consumeDouble();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public float consumeFloat() throws ParseException {
            if (FLOAT_INFINITY.matcher(this.currentToken).matches()) {
                boolean zStartsWith = this.currentToken.startsWith(Parameters.DEFAULT_OPTION_PREFIXES);
                nextToken();
                return zStartsWith ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            }
            if (FLOAT_NAN.matcher(this.currentToken).matches()) {
                nextToken();
                return Float.NaN;
            }
            try {
                float f = Float.parseFloat(this.currentToken);
                nextToken();
                return f;
            } catch (NumberFormatException e) {
                throw floatParseException(e);
            }
        }

        public boolean tryConsumeFloat() {
            try {
                consumeFloat();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public boolean consumeBoolean() throws ParseException {
            if (this.currentToken.equals("true") || this.currentToken.equals("t") || this.currentToken.equals(SchemaSymbols.ATTVAL_TRUE_1)) {
                nextToken();
                return true;
            }
            if (this.currentToken.equals("false") || this.currentToken.equals("f") || this.currentToken.equals("0")) {
                nextToken();
                return false;
            }
            throw parseException("Expected \"true\" or \"false\".");
        }

        public String consumeString() throws ParseException {
            return consumeByteString().toStringUtf8();
        }

        public boolean tryConsumeString() {
            try {
                consumeString();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public ByteString consumeByteString() throws ParseException {
            ArrayList arrayList = new ArrayList();
            consumeByteString(arrayList);
            while (true) {
                if (this.currentToken.startsWith(FormatHelper.QUOTE) || this.currentToken.startsWith("\"")) {
                    consumeByteString(arrayList);
                } else {
                    return ByteString.copyFrom(arrayList);
                }
            }
        }

        private void consumeByteString(List<ByteString> list) throws ParseException {
            char cCharAt = this.currentToken.length() > 0 ? this.currentToken.charAt(0) : (char) 0;
            if (cCharAt != '\"' && cCharAt != '\'') {
                throw parseException("Expected string.");
            }
            if (this.currentToken.length() >= 2) {
                String str = this.currentToken;
                if (str.charAt(str.length() - 1) == cCharAt) {
                    try {
                        String str2 = this.currentToken;
                        ByteString byteStringUnescapeBytes = TextFormat.unescapeBytes(str2.substring(1, str2.length() - 1));
                        nextToken();
                        list.add(byteStringUnescapeBytes);
                        return;
                    } catch (InvalidEscapeSequenceException e) {
                        throw parseException(e.getMessage());
                    }
                }
            }
            throw parseException("String missing ending quote.");
        }

        public ParseException parseException(String str) {
            return new ParseException(this.line + 1, this.column + 1, str);
        }

        public ParseException parseExceptionPreviousToken(String str) {
            return new ParseException(this.previousLine + 1, this.previousColumn + 1, str);
        }

        private ParseException integerParseException(NumberFormatException numberFormatException) {
            String strValueOf = String.valueOf(numberFormatException.getMessage());
            return parseException(strValueOf.length() != 0 ? "Couldn't parse integer: ".concat(strValueOf) : new String("Couldn't parse integer: "));
        }

        private ParseException floatParseException(NumberFormatException numberFormatException) {
            String strValueOf = String.valueOf(numberFormatException.getMessage());
            return parseException(strValueOf.length() != 0 ? "Couldn't parse number: ".concat(strValueOf) : new String("Couldn't parse number: "));
        }
    }

    public static class ParseException extends IOException {
        private static final long serialVersionUID = 3196188060225107702L;
        private final int column;
        private final int line;

        public ParseException(String str) {
            this(-1, -1, str);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public ParseException(int i, int i2, String str) {
            String strValueOf = String.valueOf(String.valueOf(Integer.toString(i)));
            String strValueOf2 = String.valueOf(String.valueOf(str));
            super(new StringBuilder(strValueOf.length() + 14 + strValueOf2.length()).append(strValueOf).append(":").append(i2).append(": ").append(strValueOf2).toString());
            this.line = i;
            this.column = i2;
        }

        public int getLine() {
            return this.line;
        }

        public int getColumn() {
            return this.column;
        }
    }

    public static Parser getParser() {
        return PARSER;
    }

    public static void merge(Readable readable, Message.Builder builder) throws IOException {
        PARSER.merge(readable, builder);
    }

    public static void merge(CharSequence charSequence, Message.Builder builder) throws ParseException {
        PARSER.merge(charSequence, builder);
    }

    public static void merge(Readable readable, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
        PARSER.merge(readable, extensionRegistry, builder);
    }

    public static void merge(CharSequence charSequence, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
        PARSER.merge(charSequence, extensionRegistry, builder);
    }

    public static class Parser {
        private final boolean allowUnknownFields;
        private final SingularOverwritePolicy singularOverwritePolicy;

        public enum SingularOverwritePolicy {
            ALLOW_SINGULAR_OVERWRITES,
            FORBID_SINGULAR_OVERWRITES
        }

        private Parser(boolean z, SingularOverwritePolicy singularOverwritePolicy) {
            this.allowUnknownFields = z;
            this.singularOverwritePolicy = singularOverwritePolicy;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {
            private boolean allowUnknownFields = false;
            private SingularOverwritePolicy singularOverwritePolicy = SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;

            public Builder setSingularOverwritePolicy(SingularOverwritePolicy singularOverwritePolicy) {
                this.singularOverwritePolicy = singularOverwritePolicy;
                return this;
            }

            public Parser build() {
                return new Parser(this.allowUnknownFields, this.singularOverwritePolicy);
            }
        }

        public void merge(Readable readable, Message.Builder builder) throws IOException {
            merge(readable, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(CharSequence charSequence, Message.Builder builder) throws ParseException {
            merge(charSequence, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(Readable readable, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
            merge(toStringBuilder(readable), extensionRegistry, builder);
        }

        private static StringBuilder toStringBuilder(Readable readable) throws IOException {
            StringBuilder sb = new StringBuilder();
            CharBuffer charBufferAllocate = CharBuffer.allocate(4096);
            while (true) {
                int i = readable.read(charBufferAllocate);
                if (i == -1) {
                    return sb;
                }
                charBufferAllocate.flip();
                sb.append((CharSequence) charBufferAllocate, 0, i);
            }
        }

        public void merge(CharSequence charSequence, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
            Tokenizer tokenizer = new Tokenizer(charSequence);
            MessageReflection.BuilderAdapter builderAdapter = new MessageReflection.BuilderAdapter(builder);
            while (!tokenizer.atEnd()) {
                mergeField(tokenizer, extensionRegistry, builderAdapter);
            }
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget) throws ParseException {
            ExtensionRegistry.ExtensionInfo extensionInfo;
            Descriptors.FieldDescriptor fieldDescriptor;
            Descriptors.Descriptor descriptorForType = mergeTarget.getDescriptorForType();
            Descriptors.FieldDescriptor fieldDescriptor2 = null;
            if (tokenizer.tryConsume("[")) {
                StringBuilder sb = new StringBuilder(tokenizer.consumeIdentifier());
                while (tokenizer.tryConsume(".")) {
                    sb.append('.');
                    sb.append(tokenizer.consumeIdentifier());
                }
                ExtensionRegistry.ExtensionInfo extensionInfoFindExtensionByName = mergeTarget.findExtensionByName(extensionRegistry, sb.toString());
                if (extensionInfoFindExtensionByName == null) {
                    if (this.allowUnknownFields) {
                        Logger logger = TextFormat.logger;
                        String strValueOf = String.valueOf(String.valueOf(sb));
                        logger.warning(new StringBuilder(strValueOf.length() + 48).append("Extension \"").append(strValueOf).append("\" not found in the ExtensionRegistry.").toString());
                    } else {
                        String strValueOf2 = String.valueOf(String.valueOf(sb));
                        throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf2.length() + 48).append("Extension \"").append(strValueOf2).append("\" not found in the ExtensionRegistry.").toString());
                    }
                } else {
                    if (extensionInfoFindExtensionByName.descriptor.getContainingType() != descriptorForType) {
                        String strValueOf3 = String.valueOf(String.valueOf(sb));
                        String strValueOf4 = String.valueOf(String.valueOf(descriptorForType.getFullName()));
                        throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf3.length() + 45 + strValueOf4.length()).append("Extension \"").append(strValueOf3).append("\" does not extend message type \"").append(strValueOf4).append("\".").toString());
                    }
                    fieldDescriptor2 = extensionInfoFindExtensionByName.descriptor;
                }
                tokenizer.consume("]");
                fieldDescriptor = fieldDescriptor2;
                extensionInfo = extensionInfoFindExtensionByName;
            } else {
                String strConsumeIdentifier = tokenizer.consumeIdentifier();
                Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName(strConsumeIdentifier);
                if (fieldDescriptorFindFieldByName == null && (fieldDescriptorFindFieldByName = descriptorForType.findFieldByName(strConsumeIdentifier.toLowerCase(Locale.US))) != null && fieldDescriptorFindFieldByName.getType() != Descriptors.FieldDescriptor.Type.GROUP) {
                    fieldDescriptorFindFieldByName = null;
                }
                if (fieldDescriptorFindFieldByName != null && fieldDescriptorFindFieldByName.getType() == Descriptors.FieldDescriptor.Type.GROUP && !fieldDescriptorFindFieldByName.getMessageType().getName().equals(strConsumeIdentifier)) {
                    fieldDescriptorFindFieldByName = null;
                }
                if (fieldDescriptorFindFieldByName == null) {
                    if (this.allowUnknownFields) {
                        Logger logger2 = TextFormat.logger;
                        String strValueOf5 = String.valueOf(String.valueOf(descriptorForType.getFullName()));
                        String strValueOf6 = String.valueOf(String.valueOf(strConsumeIdentifier));
                        logger2.warning(new StringBuilder(strValueOf5.length() + 38 + strValueOf6.length()).append("Message type \"").append(strValueOf5).append("\" has no field named \"").append(strValueOf6).append("\".").toString());
                    } else {
                        String strValueOf7 = String.valueOf(String.valueOf(descriptorForType.getFullName()));
                        String strValueOf8 = String.valueOf(String.valueOf(strConsumeIdentifier));
                        throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf7.length() + 38 + strValueOf8.length()).append("Message type \"").append(strValueOf7).append("\" has no field named \"").append(strValueOf8).append("\".").toString());
                    }
                }
                extensionInfo = null;
                fieldDescriptor = fieldDescriptorFindFieldByName;
            }
            if (fieldDescriptor == null) {
                if (tokenizer.tryConsume(":") && !tokenizer.lookingAt("{") && !tokenizer.lookingAt("<")) {
                    skipFieldValue(tokenizer);
                    return;
                } else {
                    skipFieldMessage(tokenizer);
                    return;
                }
            }
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                tokenizer.tryConsume(":");
            } else {
                tokenizer.consume(":");
            }
            if (!fieldDescriptor.isRepeated() || !tokenizer.tryConsume("[")) {
                consumeFieldValue(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo);
                return;
            }
            while (true) {
                consumeFieldValue(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo);
                if (tokenizer.tryConsume("]")) {
                    return;
                } else {
                    tokenizer.consume(",");
                }
            }
        }

        private void consumeFieldValue(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget, Descriptors.FieldDescriptor fieldDescriptor, ExtensionRegistry.ExtensionInfo extensionInfo) throws ParseException {
            String str;
            Object objValueOf = null;
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (tokenizer.tryConsume("<")) {
                    str = ">";
                } else {
                    tokenizer.consume("{");
                    str = "}";
                }
                MessageReflection.MergeTarget mergeTargetNewMergeTargetForField = mergeTarget.newMergeTargetForField(fieldDescriptor, extensionInfo != null ? extensionInfo.defaultInstance : null);
                while (!tokenizer.tryConsume(str)) {
                    if (tokenizer.atEnd()) {
                        String strValueOf = String.valueOf(String.valueOf(str));
                        throw tokenizer.parseException(new StringBuilder(strValueOf.length() + 12).append("Expected \"").append(strValueOf).append("\".").toString());
                    }
                    mergeField(tokenizer, extensionRegistry, mergeTargetNewMergeTargetForField);
                }
                objValueOf = mergeTargetNewMergeTargetForField.finish();
            } else {
                switch (AnonymousClass3.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[fieldDescriptor.getType().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        objValueOf = Integer.valueOf(tokenizer.consumeInt32());
                        break;
                    case 4:
                    case 5:
                    case 6:
                        objValueOf = Long.valueOf(tokenizer.consumeInt64());
                        break;
                    case 7:
                        objValueOf = Boolean.valueOf(tokenizer.consumeBoolean());
                        break;
                    case 8:
                        objValueOf = Float.valueOf(tokenizer.consumeFloat());
                        break;
                    case 9:
                        objValueOf = Double.valueOf(tokenizer.consumeDouble());
                        break;
                    case 10:
                    case 11:
                        objValueOf = Integer.valueOf(tokenizer.consumeUInt32());
                        break;
                    case 12:
                    case 13:
                        objValueOf = Long.valueOf(tokenizer.consumeUInt64());
                        break;
                    case 14:
                        objValueOf = tokenizer.consumeString();
                        break;
                    case 15:
                        objValueOf = tokenizer.consumeByteString();
                        break;
                    case 16:
                        Descriptors.EnumDescriptor enumType = fieldDescriptor.getEnumType();
                        if (tokenizer.lookingAtInteger()) {
                            int iConsumeInt32 = tokenizer.consumeInt32();
                            objValueOf = enumType.findValueByNumber(iConsumeInt32);
                            if (objValueOf == null) {
                                String strValueOf2 = String.valueOf(String.valueOf(enumType.getFullName()));
                                throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf2.length() + 50).append("Enum type \"").append(strValueOf2).append("\" has no value with number ").append(iConsumeInt32).append(".").toString());
                            }
                        } else {
                            String strConsumeIdentifier = tokenizer.consumeIdentifier();
                            objValueOf = enumType.findValueByName(strConsumeIdentifier);
                            if (objValueOf == null) {
                                String strValueOf3 = String.valueOf(String.valueOf(enumType.getFullName()));
                                String strValueOf4 = String.valueOf(String.valueOf(strConsumeIdentifier));
                                throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf3.length() + 35 + strValueOf4.length()).append("Enum type \"").append(strValueOf3).append("\" has no value named \"").append(strValueOf4).append("\".").toString());
                            }
                        }
                    case 17:
                    case 18:
                        throw new RuntimeException("Can't get here.");
                }
            }
            if (fieldDescriptor.isRepeated()) {
                mergeTarget.addRepeatedField(fieldDescriptor, objValueOf);
                return;
            }
            if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && mergeTarget.hasField(fieldDescriptor)) {
                String strValueOf5 = String.valueOf(String.valueOf(fieldDescriptor.getFullName()));
                throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf5.length() + 44).append("Non-repeated field \"").append(strValueOf5).append("\" cannot be overwritten.").toString());
            }
            if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && fieldDescriptor.getContainingOneof() != null && mergeTarget.hasOneof(fieldDescriptor.getContainingOneof())) {
                Descriptors.OneofDescriptor containingOneof = fieldDescriptor.getContainingOneof();
                String strValueOf6 = String.valueOf(String.valueOf(fieldDescriptor.getFullName()));
                String strValueOf7 = String.valueOf(String.valueOf(mergeTarget.getOneofFieldDescriptor(containingOneof).getFullName()));
                String strValueOf8 = String.valueOf(String.valueOf(containingOneof.getName()));
                throw tokenizer.parseExceptionPreviousToken(new StringBuilder(strValueOf6.length() + 70 + strValueOf7.length() + strValueOf8.length()).append("Field \"").append(strValueOf6).append("\" is specified along with field \"").append(strValueOf7).append("\", another member of oneof \"").append(strValueOf8).append("\".").toString());
            }
            mergeTarget.setField(fieldDescriptor, objValueOf);
        }

        private void skipField(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsume("[")) {
                do {
                    tokenizer.consumeIdentifier();
                } while (tokenizer.tryConsume("."));
                tokenizer.consume("]");
            } else {
                tokenizer.consumeIdentifier();
            }
            if (tokenizer.tryConsume(":") && !tokenizer.lookingAt("<") && !tokenizer.lookingAt("{")) {
                skipFieldValue(tokenizer);
            } else {
                skipFieldMessage(tokenizer);
            }
            if (tokenizer.tryConsume(";")) {
                return;
            }
            tokenizer.tryConsume(",");
        }

        private void skipFieldMessage(Tokenizer tokenizer) throws ParseException {
            String str;
            if (tokenizer.tryConsume("<")) {
                str = ">";
            } else {
                tokenizer.consume("{");
                str = "}";
            }
            while (!tokenizer.lookingAt(">") && !tokenizer.lookingAt("}")) {
                skipField(tokenizer);
            }
            tokenizer.consume(str);
        }

        private void skipFieldValue(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsumeString()) {
                while (tokenizer.tryConsumeString()) {
                }
                return;
            }
            if (tokenizer.tryConsumeIdentifier() || tokenizer.tryConsumeInt64() || tokenizer.tryConsumeUInt64() || tokenizer.tryConsumeDouble() || tokenizer.tryConsumeFloat()) {
                return;
            }
            String strValueOf = String.valueOf(tokenizer.currentToken);
            throw tokenizer.parseException(strValueOf.length() != 0 ? "Invalid field value: ".concat(strValueOf) : new String("Invalid field value: "));
        }
    }

    private static String escapeBytes(ByteSequence byteSequence) {
        StringBuilder sb = new StringBuilder(byteSequence.size());
        for (int i = 0; i < byteSequence.size(); i++) {
            byte bByteAt = byteSequence.byteAt(i);
            if (bByteAt == 34) {
                sb.append("\\\"");
            } else if (bByteAt == 39) {
                sb.append("\\'");
            } else if (bByteAt != 92) {
                switch (bByteAt) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bByteAt >= 32) {
                            sb.append((char) bByteAt);
                        } else {
                            sb.append('\\');
                            sb.append((char) (((bByteAt >>> 6) & 3) + 48));
                            sb.append((char) (((bByteAt >>> 3) & 7) + 48));
                            sb.append((char) ((bByteAt & 7) + 48));
                        }
                        break;
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }

    static String escapeBytes(final ByteString byteString) {
        return escapeBytes(new ByteSequence() { // from class: org.jetbrains.kotlin.protobuf.TextFormat.1
            @Override // org.jetbrains.kotlin.protobuf.TextFormat.ByteSequence
            public int size() {
                return byteString.size();
            }

            @Override // org.jetbrains.kotlin.protobuf.TextFormat.ByteSequence
            public byte byteAt(int i) {
                return byteString.byteAt(i);
            }
        });
    }

    static String escapeBytes(final byte[] bArr) {
        return escapeBytes(new ByteSequence() { // from class: org.jetbrains.kotlin.protobuf.TextFormat.2
            @Override // org.jetbrains.kotlin.protobuf.TextFormat.ByteSequence
            public int size() {
                return bArr.length;
            }

            @Override // org.jetbrains.kotlin.protobuf.TextFormat.ByteSequence
            public byte byteAt(int i) {
                return bArr[i];
            }
        });
    }

    static ByteString unescapeBytes(CharSequence charSequence) throws InvalidEscapeSequenceException {
        int i;
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(charSequence.toString());
        byte[] bArr = new byte[byteStringCopyFromUtf8.size()];
        int i2 = 0;
        int i3 = 0;
        while (i2 < byteStringCopyFromUtf8.size()) {
            byte bByteAt = byteStringCopyFromUtf8.byteAt(i2);
            if (bByteAt == 92) {
                int i4 = i2 + 1;
                if (i4 < byteStringCopyFromUtf8.size()) {
                    byte bByteAt2 = byteStringCopyFromUtf8.byteAt(i4);
                    if (isOctal(bByteAt2)) {
                        int iDigitValue = digitValue(bByteAt2);
                        int i5 = i2 + 2;
                        if (i5 < byteStringCopyFromUtf8.size() && isOctal(byteStringCopyFromUtf8.byteAt(i5))) {
                            iDigitValue = (iDigitValue * 8) + digitValue(byteStringCopyFromUtf8.byteAt(i5));
                            i4 = i5;
                        }
                        i2 = i4 + 1;
                        if (i2 >= byteStringCopyFromUtf8.size() || !isOctal(byteStringCopyFromUtf8.byteAt(i2))) {
                            i2 = i4;
                        } else {
                            iDigitValue = (iDigitValue * 8) + digitValue(byteStringCopyFromUtf8.byteAt(i2));
                        }
                        bArr[i3] = (byte) iDigitValue;
                        i3++;
                    } else {
                        if (bByteAt2 == 34) {
                            i = i3 + 1;
                            bArr[i3] = 34;
                        } else if (bByteAt2 == 39) {
                            i = i3 + 1;
                            bArr[i3] = 39;
                        } else if (bByteAt2 == 92) {
                            i = i3 + 1;
                            bArr[i3] = 92;
                        } else if (bByteAt2 == 102) {
                            i = i3 + 1;
                            bArr[i3] = 12;
                        } else if (bByteAt2 == 110) {
                            i = i3 + 1;
                            bArr[i3] = 10;
                        } else if (bByteAt2 == 114) {
                            i = i3 + 1;
                            bArr[i3] = 13;
                        } else if (bByteAt2 == 116) {
                            i = i3 + 1;
                            bArr[i3] = 9;
                        } else if (bByteAt2 == 118) {
                            i = i3 + 1;
                            bArr[i3] = 11;
                        } else if (bByteAt2 == 120) {
                            i4 = i2 + 2;
                            if (i4 < byteStringCopyFromUtf8.size() && isHex(byteStringCopyFromUtf8.byteAt(i4))) {
                                int iDigitValue2 = digitValue(byteStringCopyFromUtf8.byteAt(i4));
                                int i6 = i2 + 3;
                                if (i6 < byteStringCopyFromUtf8.size() && isHex(byteStringCopyFromUtf8.byteAt(i6))) {
                                    iDigitValue2 = (iDigitValue2 * 16) + digitValue(byteStringCopyFromUtf8.byteAt(i6));
                                    i4 = i6;
                                }
                                i = i3 + 1;
                                bArr[i3] = (byte) iDigitValue2;
                            } else {
                                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
                            }
                        } else if (bByteAt2 == 97) {
                            i = i3 + 1;
                            bArr[i3] = 7;
                        } else if (bByteAt2 == 98) {
                            i = i3 + 1;
                            bArr[i3] = 8;
                        } else {
                            throw new InvalidEscapeSequenceException(new StringBuilder(29).append("Invalid escape sequence: '\\").append((char) bByteAt2).append(FormatHelper.QUOTE).toString());
                        }
                        i3 = i;
                        i2 = i4;
                    }
                } else {
                    throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
                }
            } else {
                bArr[i3] = bByteAt;
                i3++;
            }
            i2++;
        }
        return ByteString.copyFrom(bArr, 0, i3);
    }

    static class InvalidEscapeSequenceException extends IOException {
        private static final long serialVersionUID = -8164033650142593304L;

        InvalidEscapeSequenceException(String str) {
            super(str);
        }
    }

    static String escapeText(String str) {
        return escapeBytes(ByteString.copyFromUtf8(str));
    }

    public static String escapeDoubleQuotesAndBackslashes(String str) {
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    static int parseInt32(String str) throws NumberFormatException {
        return (int) parseInteger(str, true, false);
    }

    static int parseUInt32(String str) throws NumberFormatException {
        return (int) parseInteger(str, false, false);
    }

    static long parseInt64(String str) throws NumberFormatException {
        return parseInteger(str, true, true);
    }

    static long parseUInt64(String str) throws NumberFormatException {
        return parseInteger(str, false, true);
    }

    private static long parseInteger(String str, boolean z, boolean z2) throws NumberFormatException {
        int i;
        int i2 = 0;
        if (str.startsWith(Parameters.DEFAULT_OPTION_PREFIXES, 0)) {
            if (!z) {
                String strValueOf = String.valueOf(str);
                throw new NumberFormatException(strValueOf.length() != 0 ? "Number must be positive: ".concat(strValueOf) : new String("Number must be positive: "));
            }
            i2 = 1;
        }
        int i3 = i2;
        if (str.startsWith(PsiLiteralUtil.HEX_PREFIX, i2)) {
            i2 += 2;
            i = 16;
        } else {
            i = str.startsWith("0", i2) ? 8 : 10;
        }
        String strSubstring = str.substring(i2);
        if (strSubstring.length() < 16) {
            long j = Long.parseLong(strSubstring, i);
            if (i3 != 0) {
                j = -j;
            }
            if (z2) {
                return j;
            }
            if (z) {
                if (j <= 2147483647L && j >= -2147483648L) {
                    return j;
                }
                String strValueOf2 = String.valueOf(str);
                throw new NumberFormatException(strValueOf2.length() != 0 ? "Number out of range for 32-bit signed integer: ".concat(strValueOf2) : new String("Number out of range for 32-bit signed integer: "));
            }
            if (j < 4294967296L && j >= 0) {
                return j;
            }
            String strValueOf3 = String.valueOf(str);
            throw new NumberFormatException(strValueOf3.length() != 0 ? "Number out of range for 32-bit unsigned integer: ".concat(strValueOf3) : new String("Number out of range for 32-bit unsigned integer: "));
        }
        BigInteger bigInteger = new BigInteger(strSubstring, i);
        if (i3 != 0) {
            bigInteger = bigInteger.negate();
        }
        if (z2) {
            if (z) {
                if (bigInteger.bitLength() > 63) {
                    String strValueOf4 = String.valueOf(str);
                    throw new NumberFormatException(strValueOf4.length() != 0 ? "Number out of range for 64-bit signed integer: ".concat(strValueOf4) : new String("Number out of range for 64-bit signed integer: "));
                }
            } else if (bigInteger.bitLength() > 64) {
                String strValueOf5 = String.valueOf(str);
                throw new NumberFormatException(strValueOf5.length() != 0 ? "Number out of range for 64-bit unsigned integer: ".concat(strValueOf5) : new String("Number out of range for 64-bit unsigned integer: "));
            }
        } else if (z) {
            if (bigInteger.bitLength() > 31) {
                String strValueOf6 = String.valueOf(str);
                throw new NumberFormatException(strValueOf6.length() != 0 ? "Number out of range for 32-bit signed integer: ".concat(strValueOf6) : new String("Number out of range for 32-bit signed integer: "));
            }
        } else if (bigInteger.bitLength() > 32) {
            String strValueOf7 = String.valueOf(str);
            throw new NumberFormatException(strValueOf7.length() != 0 ? "Number out of range for 32-bit unsigned integer: ".concat(strValueOf7) : new String("Number out of range for 32-bit unsigned integer: "));
        }
        return bigInteger.longValue();
    }
}
