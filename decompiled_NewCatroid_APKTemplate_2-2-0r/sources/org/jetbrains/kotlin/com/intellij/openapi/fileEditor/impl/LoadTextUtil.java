package org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileDecompiler;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.CharsetUtil;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.CharsetToolkit;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingRegistry;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.text.ByteArrayCharSequence;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class LoadTextUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) LoadTextUtil.class);
    private static final Charset INTERNAL_SEVEN_BIT_UTF8 = new SevenBitCharset(StandardCharsets.UTF_8);
    private static final Charset INTERNAL_SEVEN_BIT_ISO_8859_1 = new SevenBitCharset(StandardCharsets.ISO_8859_1);
    private static final Charset INTERNAL_SEVEN_BIT_WIN_1251 = new SevenBitCharset(CharsetToolkit.WIN_1251_CHARSET);
    private static final boolean GUESS_UTF = Boolean.parseBoolean(System.getProperty("idea.guess.utf.encoding", "true"));
    private static final Key<AutoDetectionReason> CHARSET_WAS_DETECTED_FROM_BYTES = Key.create("CHARSET_WAS_DETECTED_FROM_BYTES");

    public enum AutoDetectionReason {
        FROM_BOM,
        FROM_BYTES
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0016 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 68 && i != 69) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 44:
                                case 45:
                                case 46:
                                case 47:
                                case 48:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 26:
                        case 27:
                        case 28:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 10:
                case 12:
                case 20:
                case 38:
                case 53:
                case 57:
                case 59:
                case 61:
                case 66:
                case 74:
                case 79:
                case 83:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 68 && i != 69) {
            switch (i) {
                case 10:
                case 12:
                case 20:
                case 38:
                case 53:
                case 57:
                case 59:
                case 61:
                case 66:
                case 74:
                case 79:
                case 83:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 44:
                                case 45:
                                case 46:
                                case 47:
                                case 48:
                                    i2 = 2;
                                    break;
                                default:
                                    i2 = 3;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "charsAsBytes";
                break;
            case 3:
                objArr[0] = "ints";
                break;
            case 4:
            case 7:
            case 11:
            case 16:
            case 18:
            case 32:
            case 35:
            case 39:
            case 51:
            case 54:
            case 62:
            case 64:
            case 67:
            case 71:
            case 75:
            case 86:
            case 87:
            case 88:
                objArr[0] = "virtualFile";
                break;
            case 5:
            case 8:
            case 14:
            case 72:
                objArr[0] = "fileType";
                break;
            case 6:
            case 15:
                objArr[0] = "internalBuffer";
                break;
            case 9:
            case 17:
            case 19:
            case 22:
            case 25:
            case 40:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 10:
            case 12:
            case 20:
            case 26:
            case 27:
            case 28:
            case 38:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 53:
            case 57:
            case 59:
            case 61:
            case 66:
            case 68:
            case 69:
            case 74:
            case 79:
            case 83:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/LoadTextUtil";
                break;
            case 13:
            case 29:
            case 56:
            case 58:
            case 80:
                objArr[0] = "file";
                break;
            case 21:
                objArr[0] = "defaultCharset";
                break;
            case 23:
            case 63:
            case 65:
            case 70:
            case 78:
            case 82:
            case 85:
                objArr[0] = "bytes";
                break;
            case 24:
            case 49:
            case 81:
                objArr[0] = "charset";
                break;
            case 30:
                objArr[0] = "newSeparator";
                break;
            case 31:
            case 33:
                objArr[0] = "requestor";
                break;
            case 34:
            case 36:
            case 43:
            case 52:
            case 55:
                objArr[0] = "text";
                break;
            case 37:
            case 41:
                objArr[0] = "existing";
                break;
            case 42:
                objArr[0] = "specified";
                break;
            case 50:
                objArr[0] = "str";
                break;
            case 60:
                objArr[0] = "sequence";
                break;
            case 73:
                objArr[0] = "fileTextProcessor";
                break;
            case 76:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 77:
            case 84:
                objArr[0] = "internalCharset";
                break;
            default:
                objArr[0] = "buffer";
                break;
        }
        if (i != 68 && i != 69) {
            switch (i) {
                case 10:
                    objArr[1] = "detectCharsetAndSetBOM";
                    break;
                case 12:
                    objArr[1] = "getDefaultCharsetFromEncodingManager";
                    break;
                case 20:
                    objArr[1] = "guessFromContent";
                    break;
                case 38:
                    objArr[1] = "charsetForWriting";
                    break;
                case 53:
                    objArr[1] = "extractCharsetFromFileContent";
                    break;
                case 57:
                case 59:
                    objArr[1] = "loadText";
                    break;
                case 61:
                    objArr[1] = "limitCharSequence";
                    break;
                case 66:
                case 83:
                    objArr[1] = "getTextByBinaryPresentation";
                    break;
                case 74:
                    objArr[1] = "processTextFromBinaryPresentationOrNull";
                    break;
                case 79:
                    objArr[1] = "convertBytesAndSetSeparator";
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                            objArr[1] = "getOverriddenCharsetByBOM";
                            break;
                        default:
                            switch (i) {
                                case 44:
                                case 45:
                                case 46:
                                case 47:
                                case 48:
                                    objArr[1] = "chooseMostlyHarmlessCharset";
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/LoadTextUtil";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "detectAllLineSeparators";
        }
        switch (i) {
            case 2:
                objArr[2] = "convertWideCharacters";
                break;
            case 3:
                objArr[2] = "findLineBreakOrWideChar";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "detectHardCharset";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "detectCharsetAndSetBOM";
                break;
            case 10:
            case 12:
            case 20:
            case 26:
            case 27:
            case 28:
            case 38:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 53:
            case 57:
            case 59:
            case 61:
            case 66:
            case 68:
            case 69:
            case 74:
            case 79:
            case 83:
                break;
            case 11:
                objArr[2] = "getDefaultCharsetFromEncodingManager";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "detectInternalCharsetAndSetBOM";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "guessFromContent";
                break;
            case 21:
            case 22:
                objArr[2] = "guessFromBytes";
                break;
            case 23:
                objArr[2] = "getTextFromBytesOrNull";
                break;
            case 24:
            case 25:
                objArr[2] = "getOverriddenCharsetByBOM";
                break;
            case 29:
            case 30:
            case 31:
                objArr[2] = "changeLineSeparators";
                break;
            case 32:
            case 33:
            case 34:
                objArr[2] = "write";
                break;
            case 35:
            case 36:
            case 37:
                objArr[2] = "charsetForWriting";
                break;
            case 39:
            case 40:
                objArr[2] = "setDetectedFromBytesFlagBack";
                break;
            case 41:
            case 42:
            case 43:
                objArr[2] = "chooseMostlyHarmlessCharset";
                break;
            case 49:
            case 50:
                objArr[2] = "isSupported";
                break;
            case 51:
            case 52:
                objArr[2] = "extractCharsetFromFileContent";
                break;
            case 54:
            case 55:
                objArr[2] = "charsetFromContentOrNull";
                break;
            case 56:
            case 58:
                objArr[2] = "loadText";
                break;
            case 60:
                objArr[2] = "limitCharSequence";
                break;
            case 62:
            case 63:
            case 64:
            case 65:
            case 81:
            case 82:
                objArr[2] = "getTextByBinaryPresentation";
                break;
            case 67:
                objArr[2] = "detectAllLineSeparators";
                break;
            case 70:
            case 71:
            case 72:
            case 73:
                objArr[2] = "processTextFromBinaryPresentationOrNull";
                break;
            case 75:
            case 76:
            case 77:
            case 78:
                objArr[2] = "convertBytesAndSetSeparator";
                break;
            case 80:
                objArr[2] = "detectLineSeparator";
                break;
            case 84:
            case 85:
                objArr[2] = "convertBytes";
                break;
            case 86:
                objArr[2] = "getCharsetAutoDetectionReason";
                break;
            case 87:
                objArr[2] = "setCharsetAutoDetectionReason";
                break;
            case 88:
                objArr[2] = "clearCharsetAutoDetectionReason";
                break;
            default:
                objArr[2] = "convertLineSeparatorsToSlashN";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 68 && i != 69) {
            switch (i) {
                case 10:
                case 12:
                case 20:
                case 38:
                case 53:
                case 57:
                case 59:
                case 61:
                case 66:
                case 74:
                case 79:
                case 83:
                    break;
                default:
                    switch (i) {
                        case 26:
                        case 27:
                        case 28:
                            break;
                        default:
                            switch (i) {
                                case 44:
                                case 45:
                                case 46:
                                case 47:
                                case 48:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private LoadTextUtil() {
    }

    private static ConvertResult convertLineSeparatorsToSlashN(CharBuffer charBuffer) {
        int i;
        int i2;
        int i3;
        if (charBuffer == null) {
            $$$reportNull$$$0(0);
        }
        int length = charBuffer.length();
        char[] cArrFromSequenceWithoutCopying = CharArrayUtil.fromSequenceWithoutCopying(charBuffer);
        char c = StringUtil.SPACE;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i4 < length) {
            char cCharAt = cArrFromSequenceWithoutCopying != null ? cArrFromSequenceWithoutCopying[i4] : charBuffer.charAt(i4);
            if (cCharAt != '\n') {
                if (cCharAt != '\r') {
                    if (cArrFromSequenceWithoutCopying != null) {
                        i3 = i5 + 1;
                        cArrFromSequenceWithoutCopying[i5] = cCharAt;
                    } else {
                        i3 = i5 + 1;
                        charBuffer.put(i5, cCharAt);
                    }
                    i5 = i3;
                } else {
                    if (cArrFromSequenceWithoutCopying != null) {
                        i2 = i5 + 1;
                        cArrFromSequenceWithoutCopying[i5] = '\n';
                    } else {
                        i2 = i5 + 1;
                        charBuffer.put(i5, '\n');
                    }
                    i5 = i2;
                    i6++;
                }
            } else if (c == '\r') {
                i6--;
                i8++;
            } else {
                if (cArrFromSequenceWithoutCopying != null) {
                    i = i5 + 1;
                    cArrFromSequenceWithoutCopying[i5] = '\n';
                } else {
                    i = i5 + 1;
                    charBuffer.put(i5, '\n');
                }
                i5 = i;
                i7++;
            }
            i4++;
            c = cCharAt;
        }
        if (charBuffer.length() != i5) {
            charBuffer = charBuffer.subSequence(0, i5);
        }
        return new ConvertResult(charBuffer, i6, i7, i8);
    }

    private static ConvertResult convertLineSeparatorsToSlashN(byte[] bArr, int i, int i2) {
        int i3;
        if (bArr == null) {
            $$$reportNull$$$0(1);
        }
        int iFindLineBreakOrWideChar = findLineBreakOrWideChar(bArr, i, i2);
        if (!BitUtil.isSet(iFindLineBreakOrWideChar, 1) && !BitUtil.isSet(iFindLineBreakOrWideChar, 4)) {
            return new ConvertResult(new ByteArrayCharSequence(bArr, i, i2), 0, BitUtil.isSet(iFindLineBreakOrWideChar, 2) ? 1 : 0, 0);
        }
        if (BitUtil.isSet(iFindLineBreakOrWideChar, 4)) {
            return convertWideCharacters(bArr, i, i2);
        }
        byte[] bArrNewByteArray = ArrayUtil.newByteArray(i2 - i);
        char c = StringUtil.SPACE;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i < i2) {
            char c2 = (char) bArr[i];
            if (c2 == '\n') {
                if (c == '\r') {
                    i5--;
                    i7++;
                } else {
                    i3 = i4 + 1;
                    bArrNewByteArray[i4] = 10;
                    i6++;
                }
                i++;
                c = c2;
            } else if (c2 == '\r') {
                i3 = i4 + 1;
                bArrNewByteArray[i4] = 10;
                i5++;
            } else {
                i3 = i4 + 1;
                bArrNewByteArray[i4] = (byte) c2;
            }
            i4 = i3;
            i++;
            c = c2;
        }
        return new ConvertResult(new ByteArrayCharSequence(bArrNewByteArray, 0, i4), i5, i6, i7);
    }

    private static ConvertResult convertWideCharacters(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(2);
        }
        StringBuilder sb = new StringBuilder(i2 - i);
        int i3 = 0;
        int i4 = 0;
        char c = ' ';
        int i5 = 0;
        while (i < i2) {
            char c2 = (char) bArr[i];
            if (c2 >= 128) {
                sb.append((char) 65023);
            } else {
                if (c2 != '\n') {
                    if (c2 == '\r') {
                        sb.append('\n');
                        i3++;
                    } else {
                        sb.append(c2);
                    }
                } else if (c == '\r') {
                    i3--;
                    i4++;
                } else {
                    sb.append('\n');
                    i5++;
                }
                c = c2;
            }
            i++;
        }
        return new ConvertResult(sb, i3, i5, i4);
    }

    private static int findLineBreakOrWideChar(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            $$$reportNull$$$0(3);
        }
        int i3 = 0;
        while (i < i2) {
            byte b = bArr[i];
            if (b == 13) {
                i3 |= 1;
            }
            if (b == 10) {
                i3 |= 2;
            }
            if (b < 0) {
                i3 |= 4;
            }
            i++;
        }
        return i3;
    }

    private static class SevenBitCharset extends Charset {
        private final Charset myBaseCharset;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "baseCharset", "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/LoadTextUtil$SevenBitCharset", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SevenBitCharset(Charset charset) {
            super("IJ__7BIT_" + charset.name(), ArrayUtilRt.EMPTY_STRING_ARRAY);
            if (charset == null) {
                $$$reportNull$$$0(0);
            }
            this.myBaseCharset = charset;
        }

        @Override // java.nio.charset.Charset
        public boolean contains(Charset charset) {
            throw new IllegalStateException();
        }

        @Override // java.nio.charset.Charset
        public CharsetDecoder newDecoder() {
            throw new IllegalStateException();
        }

        @Override // java.nio.charset.Charset
        public CharsetEncoder newEncoder() {
            throw new IllegalStateException();
        }
    }

    public static class DetectResult {
        public final byte[] BOM;
        public final CharsetToolkit.GuessedEncoding guessed;
        public final Charset hardCodedCharset;

        DetectResult(Charset charset, CharsetToolkit.GuessedEncoding guessedEncoding, byte[] bArr) {
            this.hardCodedCharset = charset;
            this.guessed = guessedEncoding;
            this.BOM = bArr;
        }
    }

    private static DetectResult detectHardCharset(VirtualFile virtualFile, byte[] bArr, int i, FileType fileType) {
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        if (fileType == null) {
            $$$reportNull$$$0(5);
        }
        if (bArr == null) {
            $$$reportNull$$$0(6);
        }
        String charset = fileType.getCharset(virtualFile, bArr);
        Charset charsetForName = charset == null ? null : CharsetToolkit.forName(charset);
        DetectResult detectResultGuessFromContent = guessFromContent(virtualFile, bArr, i);
        if (charsetForName == null) {
            charsetForName = detectResultGuessFromContent.hardCodedCharset;
        }
        CharsetToolkit.GuessedEncoding guessedEncoding = detectResultGuessFromContent.guessed;
        byte[] bArr2 = detectResultGuessFromContent.BOM;
        if (charsetForName == null && guessedEncoding == CharsetToolkit.GuessedEncoding.VALID_UTF8) {
            charsetForName = StandardCharsets.UTF_8;
        }
        return new DetectResult(charsetForName, guessedEncoding, bArr2);
    }

    private static Charset getDefaultCharsetFromEncodingManager(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        Charset charset = (Charset) ObjectUtils.notNull(EncodingRegistry.getInstance().getEncoding(virtualFile, true), EncodingRegistry.getInstance().getDefaultCharset());
        if (charset == null) {
            $$$reportNull$$$0(12);
        }
        return charset;
    }

    private static DetectResult detectInternalCharsetAndSetBOM(VirtualFile virtualFile, byte[] bArr, int i, boolean z, FileType fileType) {
        Charset charset;
        if (virtualFile == null) {
            $$$reportNull$$$0(13);
        }
        if (fileType == null) {
            $$$reportNull$$$0(14);
        }
        if (bArr == null) {
            $$$reportNull$$$0(15);
        }
        DetectResult detectResultDetectHardCharset = detectHardCharset(virtualFile, bArr, i, fileType);
        if (detectResultDetectHardCharset.hardCodedCharset == null) {
            charset = virtualFile.isCharsetSet() ? virtualFile.getCharset() : getDefaultCharsetFromEncodingManager(virtualFile);
        } else {
            charset = detectResultDetectHardCharset.hardCodedCharset;
        }
        byte[] bArr2 = detectResultDetectHardCharset.BOM;
        if (z && bArr2 != null && bArr2.length != 0) {
            virtualFile.setBOM(bArr2);
            setCharsetAutoDetectionReason(virtualFile, AutoDetectionReason.FROM_BOM);
        }
        virtualFile.setCharset(charset);
        if (detectResultDetectHardCharset.guessed == CharsetToolkit.GuessedEncoding.SEVEN_BIT) {
            if (charset == StandardCharsets.UTF_8) {
                charset = INTERNAL_SEVEN_BIT_UTF8;
            } else if (charset == StandardCharsets.ISO_8859_1) {
                charset = INTERNAL_SEVEN_BIT_ISO_8859_1;
            } else if (charset == CharsetToolkit.WIN_1251_CHARSET) {
                charset = INTERNAL_SEVEN_BIT_WIN_1251;
            }
        }
        return new DetectResult(charset, detectResultDetectHardCharset.guessed, bArr2);
    }

    public static DetectResult guessFromContent(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(16);
        }
        if (bArr == null) {
            $$$reportNull$$$0(17);
        }
        return guessFromContent(virtualFile, bArr, bArr.length);
    }

    private static DetectResult guessFromContent(VirtualFile virtualFile, byte[] bArr, int i) {
        DetectResult detectResult;
        if (virtualFile == null) {
            $$$reportNull$$$0(18);
        }
        if (bArr == null) {
            $$$reportNull$$$0(19);
        }
        AutoDetectionReason autoDetectionReason = null;
        try {
            if (GUESS_UTF) {
                detectResult = guessFromBytes(bArr, 0, i, getDefaultCharsetFromEncodingManager(virtualFile));
                if (detectResult.BOM != null) {
                    autoDetectionReason = AutoDetectionReason.FROM_BOM;
                } else if (detectResult.guessed == CharsetToolkit.GuessedEncoding.VALID_UTF8) {
                    autoDetectionReason = AutoDetectionReason.FROM_BYTES;
                }
            } else {
                detectResult = new DetectResult(null, null, null);
            }
            setCharsetAutoDetectionReason(virtualFile, autoDetectionReason);
            if (detectResult == null) {
                $$$reportNull$$$0(20);
            }
            return detectResult;
        } catch (Throwable th) {
            setCharsetAutoDetectionReason(virtualFile, null);
            throw th;
        }
    }

    private static DetectResult guessFromBytes(byte[] bArr, int i, int i2, Charset charset) {
        if (charset == null) {
            $$$reportNull$$$0(21);
        }
        if (bArr == null) {
            $$$reportNull$$$0(22);
        }
        if (i == i2) {
            return new DetectResult(null, CharsetToolkit.GuessedEncoding.SEVEN_BIT, null);
        }
        CharsetToolkit charsetToolkit = new CharsetToolkit(bArr, charset, true);
        Charset charsetGuessFromBOM = charsetToolkit.guessFromBOM();
        if (charsetGuessFromBOM != null) {
            return new DetectResult(charsetGuessFromBOM, null, (byte[]) ObjectUtils.notNull(CharsetToolkit.getMandatoryBom(charsetGuessFromBOM), CharsetToolkit.UTF8_BOM));
        }
        CharsetToolkit.GuessedEncoding guessedEncodingGuessFromContent = charsetToolkit.guessFromContent(i, i2);
        if (guessedEncodingGuessFromContent == CharsetToolkit.GuessedEncoding.VALID_UTF8) {
            return new DetectResult(StandardCharsets.UTF_8, CharsetToolkit.GuessedEncoding.VALID_UTF8, null);
        }
        return new DetectResult(null, guessedEncodingGuessFromContent, null);
    }

    public static void write(Project project, VirtualFile virtualFile, Object obj, String str, long j) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(32);
        }
        if (obj == null) {
            $$$reportNull$$$0(33);
        }
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        Charset charset = virtualFile.getCharset();
        Pair.NonNull<Charset, byte[]> nonNullCharsetForWriting = charsetForWriting(project, virtualFile, str, charset);
        Charset charset2 = nonNullCharsetForWriting.first;
        byte[] bArr = nonNullCharsetForWriting.second;
        if (!charset2.equals(charset)) {
            virtualFile.setCharset(charset2);
        }
        setDetectedFromBytesFlagBack(virtualFile, bArr);
        OutputStream outputStream = virtualFile.getOutputStream(obj, j, -1L);
        try {
            outputStream.write(bArr);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static Pair.NonNull<Charset, byte[]> charsetForWriting(Project project, VirtualFile virtualFile, String str, Charset charset) {
        if (virtualFile == null) {
            $$$reportNull$$$0(35);
        }
        if (str == null) {
            $$$reportNull$$$0(36);
        }
        if (charset == null) {
            $$$reportNull$$$0(37);
        }
        Pair.NonNull<Charset, byte[]> nonNullChooseMostlyHarmlessCharset = chooseMostlyHarmlessCharset(charset, extractCharsetFromFileContent(project, virtualFile, str), str);
        Charset charset2 = nonNullChooseMostlyHarmlessCharset.first;
        byte[] bom = virtualFile.getBOM();
        Charset charsetGuessFromBOM = bom == null ? null : CharsetToolkit.guessFromBOM(bom);
        if (charsetGuessFromBOM != null && !charsetGuessFromBOM.equals(charset2)) {
            nonNullChooseMostlyHarmlessCharset = Pair.createNonNull(charsetGuessFromBOM, str.getBytes(charsetGuessFromBOM));
        }
        if (nonNullChooseMostlyHarmlessCharset == null) {
            $$$reportNull$$$0(38);
        }
        return nonNullChooseMostlyHarmlessCharset;
    }

    private static void setDetectedFromBytesFlagBack(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(39);
        }
        if (bArr == null) {
            $$$reportNull$$$0(40);
        }
        if (virtualFile.getBOM() == null) {
            guessFromContent(virtualFile, bArr);
        } else {
            setCharsetAutoDetectionReason(virtualFile, AutoDetectionReason.FROM_BOM);
        }
    }

    public static Pair.NonNull<Charset, byte[]> chooseMostlyHarmlessCharset(Charset charset, Charset charset2, String str) {
        if (charset == null) {
            $$$reportNull$$$0(41);
        }
        if (charset2 == null) {
            $$$reportNull$$$0(42);
        }
        if (str == null) {
            $$$reportNull$$$0(43);
        }
        try {
            if (charset2.equals(charset)) {
                Pair.NonNull<Charset, byte[]> nonNullCreateNonNull = Pair.createNonNull(charset2, str.getBytes(charset));
                if (nonNullCreateNonNull == null) {
                    $$$reportNull$$$0(44);
                }
                return nonNullCreateNonNull;
            }
            byte[] bArrIsSupported = isSupported(charset2, str);
            if (bArrIsSupported != null) {
                Pair.NonNull<Charset, byte[]> nonNullCreateNonNull2 = Pair.createNonNull(charset2, bArrIsSupported);
                if (nonNullCreateNonNull2 == null) {
                    $$$reportNull$$$0(45);
                }
                return nonNullCreateNonNull2;
            }
            byte[] bArrIsSupported2 = isSupported(charset, str);
            if (bArrIsSupported2 != null) {
                Pair.NonNull<Charset, byte[]> nonNullCreateNonNull3 = Pair.createNonNull(charset, bArrIsSupported2);
                if (nonNullCreateNonNull3 == null) {
                    $$$reportNull$$$0(46);
                }
                return nonNullCreateNonNull3;
            }
            Pair.NonNull<Charset, byte[]> nonNullCreateNonNull4 = Pair.createNonNull(charset2, str.getBytes(charset2));
            if (nonNullCreateNonNull4 == null) {
                $$$reportNull$$$0(47);
            }
            return nonNullCreateNonNull4;
        } catch (RuntimeException unused) {
            Charset charsetDefaultCharset = Charset.defaultCharset();
            Pair.NonNull<Charset, byte[]> nonNullCreateNonNull5 = Pair.createNonNull(charsetDefaultCharset, str.getBytes(charsetDefaultCharset));
            if (nonNullCreateNonNull5 == null) {
                $$$reportNull$$$0(48);
            }
            return nonNullCreateNonNull5;
        }
    }

    private static byte[] isSupported(Charset charset, String str) {
        if (charset == null) {
            $$$reportNull$$$0(49);
        }
        if (str == null) {
            $$$reportNull$$$0(50);
        }
        try {
            if (!charset.canEncode()) {
                return null;
            }
            byte[] bytes = str.getBytes(charset);
            if (str.equals(new String(bytes, charset))) {
                return bytes;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Charset extractCharsetFromFileContent(Project project, VirtualFile virtualFile, CharSequence charSequence) {
        if (virtualFile == null) {
            $$$reportNull$$$0(51);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(52);
        }
        Charset charset = (Charset) ObjectUtils.notNull(charsetFromContentOrNull(project, virtualFile, charSequence), virtualFile.getCharset());
        if (charset == null) {
            $$$reportNull$$$0(53);
        }
        return charset;
    }

    public static Charset charsetFromContentOrNull(Project project, VirtualFile virtualFile, CharSequence charSequence) {
        if (virtualFile == null) {
            $$$reportNull$$$0(54);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(55);
        }
        return CharsetUtil.extractCharsetFromFileContent(project, virtualFile, virtualFile.getFileType(), charSequence);
    }

    public static CharSequence loadText(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(56);
        }
        FileType fileType = virtualFile.getFileType();
        if (fileType.isBinary()) {
            BinaryFileDecompiler binaryFileDecompilerForFileType = BinaryFileTypeDecompilers.getInstance().forFileType(fileType);
            if (binaryFileDecompilerForFileType != null) {
                CharSequence charSequenceDecompile = binaryFileDecompilerForFileType.decompile(virtualFile);
                try {
                    org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.assertValidSeparators(charSequenceDecompile);
                } catch (AssertionError e) {
                    LOG.error((Throwable) e);
                }
                if (charSequenceDecompile == null) {
                    $$$reportNull$$$0(57);
                }
                return charSequenceDecompile;
            }
            throw new IllegalArgumentException("Attempt to load text for binary file which doesn't have a decompiler plugged in: " + virtualFile.getPresentableUrl() + ". File type: " + fileType.getName());
        }
        return loadText(virtualFile, -1);
    }

    public static CharSequence loadText(VirtualFile virtualFile, int i) {
        byte[] bArrLoadFirstAndClose;
        if (virtualFile == null) {
            $$$reportNull$$$0(58);
        }
        FileType fileType = virtualFile.getFileType();
        if (fileType.isBinary()) {
            throw new IllegalArgumentException("Attempt to load truncated text for binary file: " + virtualFile.getPresentableUrl() + ". File type: " + fileType.getName());
        }
        if (virtualFile instanceof LightVirtualFile) {
            return limitCharSequence(((LightVirtualFile) virtualFile).getContent(), i);
        }
        if (virtualFile.isDirectory()) {
            throw new AssertionError(FormatHelper.QUOTE + virtualFile.getPresentableUrl() + "' is a directory");
        }
        try {
            if (i == -1) {
                bArrLoadFirstAndClose = virtualFile.contentsToByteArray();
            } else {
                bArrLoadFirstAndClose = FileUtil.loadFirstAndClose(virtualFile.getInputStream(), i);
            }
            return getTextByBinaryPresentation(bArrLoadFirstAndClose, virtualFile);
        } catch (IOException unused) {
            CharSequence charSequence = Strings.EMPTY_CHAR_SEQUENCE;
            if (charSequence == null) {
                $$$reportNull$$$0(59);
            }
            return charSequence;
        }
    }

    private static CharSequence limitCharSequence(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(60);
        }
        if (i != -1) {
            charSequence = charSequence.subSequence(0, Math.min(i, charSequence.length()));
        }
        if (charSequence == null) {
            $$$reportNull$$$0(61);
        }
        return charSequence;
    }

    public static CharSequence getTextByBinaryPresentation(byte[] bArr, VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(62);
        }
        if (bArr == null) {
            $$$reportNull$$$0(63);
        }
        return getTextByBinaryPresentation(bArr, virtualFile, true, true);
    }

    public static CharSequence getTextByBinaryPresentation(byte[] bArr, VirtualFile virtualFile, boolean z, boolean z2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(64);
        }
        if (bArr == null) {
            $$$reportNull$$$0(65);
        }
        DetectResult detectResultDetectInternalCharsetAndSetBOM = detectInternalCharsetAndSetBOM(virtualFile, bArr, bArr.length, z2, virtualFile.getFileType());
        CharSequence charSequence = convertBytesAndSetSeparator(bArr, bArr.length, virtualFile, z, detectResultDetectInternalCharsetAndSetBOM, detectResultDetectInternalCharsetAndSetBOM.hardCodedCharset).text;
        if (charSequence == null) {
            $$$reportNull$$$0(66);
        }
        return charSequence;
    }

    private static ConvertResult convertBytesAndSetSeparator(byte[] bArr, int i, VirtualFile virtualFile, boolean z, DetectResult detectResult, Charset charset) {
        String strMajorLineSeparator;
        if (virtualFile == null) {
            $$$reportNull$$$0(75);
        }
        if (detectResult == null) {
            $$$reportNull$$$0(76);
        }
        if (charset == null) {
            $$$reportNull$$$0(77);
        }
        if (bArr == null) {
            $$$reportNull$$$0(78);
        }
        byte[] bArr2 = detectResult.BOM;
        ConvertResult convertResultConvertBytes = convertBytes(bArr, Math.min(i, bArr2 == null ? 0 : bArr2.length), i, charset);
        if (z && (strMajorLineSeparator = convertResultConvertBytes.majorLineSeparator()) != null) {
            virtualFile.setDetectedLineSeparator(strMajorLineSeparator);
        }
        if (convertResultConvertBytes == null) {
            $$$reportNull$$$0(79);
        }
        return convertResultConvertBytes;
    }

    private static ConvertResult convertBytes(byte[] bArr, int i, int i2, Charset charset) {
        CharBuffer charBufferWrap;
        if (charset == null) {
            $$$reportNull$$$0(84);
        }
        if (bArr == null) {
            $$$reportNull$$$0(85);
        }
        if ((charset instanceof SevenBitCharset) || charset == StandardCharsets.US_ASCII) {
            return convertLineSeparatorsToSlashN(bArr, i, i2);
        }
        try {
            charBufferWrap = charset.decode(ByteBuffer.wrap(bArr, i, i2 - i));
        } catch (Exception unused) {
            charBufferWrap = CharBuffer.wrap(ArrayUtilRt.EMPTY_CHAR_ARRAY);
        }
        return convertLineSeparatorsToSlashN(charBufferWrap);
    }

    private static class ConvertResult {
        private final int CRLF_count;
        private final int CR_count;
        private final int LF_count;
        private final CharSequence text;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "text";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/LoadTextUtil$ConvertResult";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/LoadTextUtil$ConvertResult";
            } else {
                objArr[1] = "allLineSeparators";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        ConvertResult(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            this.text = charSequence;
            this.CR_count = i;
            this.LF_count = i2;
            this.CRLF_count = i3;
        }

        String majorLineSeparator() {
            int i = this.CRLF_count;
            int i2 = this.CR_count;
            if (i > i2 && i > this.LF_count) {
                return "\r\n";
            }
            int i3 = this.LF_count;
            if (i2 > i3) {
                return "\r";
            }
            if (i3 > 0) {
                return "\n";
            }
            return null;
        }
    }

    private static void setCharsetAutoDetectionReason(VirtualFile virtualFile, AutoDetectionReason autoDetectionReason) {
        if (virtualFile == null) {
            $$$reportNull$$$0(87);
        }
        virtualFile.putUserData(CHARSET_WAS_DETECTED_FROM_BYTES, autoDetectionReason);
    }
}
