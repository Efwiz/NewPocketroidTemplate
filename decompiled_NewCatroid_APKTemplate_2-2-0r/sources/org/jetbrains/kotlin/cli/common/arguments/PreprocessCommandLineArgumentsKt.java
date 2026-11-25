package org.jetbrains.kotlin.cli.common.arguments;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: preprocessCommandLineArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\f\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a(\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014\u001a\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\u0006\u0010\u0017\u001a\u00020\u0001\u001a \u0010\u0018\u001a\u00020\u0019*\u00020\u001a2\n\u0010\u001b\u001a\u00060\u001cj\u0002`\u001d2\u0006\u0010\u001e\u001a\u00020\u0003H\u0002\u001a\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0011*\u00020 2\u0006\u0010\u0013\u001a\u00020\u0015H\u0002\u001a\u0013\u0010!\u001a\u0004\u0018\u00010\u0003*\u00020\u001aH\u0002¢\u0006\u0002\u0010\"\u001a\u000e\u0010#\u001a\u0004\u0018\u00010\u0001*\u00020\u001aH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0018\u0010\n\u001a\u00020\u0001*\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\"\u0018\u0010\f\u001a\u00020\r*\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000e\"\u0018\u0010\u000f\u001a\u00020\r*\u00020\u00018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000e¨\u0006$"}, d2 = {"ARGFILE_ARGUMENT", "", "BACKSLASH", "", "DOUBLE_QUOTE", "EXPERIMENTAL_ARGFILE_ARGUMENT", "SINGLE_QUOTE", "argfilePath", "getArgfilePath", "(Ljava/lang/String;)Ljava/lang/String;", "deprecatedArgfilePath", "getDeprecatedArgfilePath", "isArgfileArgument", "", "(Ljava/lang/String;)Z", "isDeprecatedArgfileArgument", "preprocessCommandLineArguments", "", "args", "errors", "Lkotlin/Lazy;", "Lorg/jetbrains/kotlin/cli/common/arguments/ArgumentParseErrors;", "readArgumentsFromArgFile", FirebaseAnalytics.Param.CONTENT, "consumeRestOfQuotedSequence", "", "Ljava/io/Reader;", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "quote", "expand", "Ljava/io/File;", "nextChar", "(Ljava/io/Reader;)Ljava/lang/Character;", "parseNextArgument", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PreprocessCommandLineArgumentsKt {
    public static final String ARGFILE_ARGUMENT = "@";

    public static final List<String> preprocessCommandLineArguments(List<String> args, Lazy<ArgumentParseErrors> errors) {
        List<String> listListOf;
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(errors, "errors");
        ArrayList arrayList = new ArrayList();
        for (String str : args) {
            if (isArgfileArgument(str)) {
                listListOf = expand(new File(getArgfilePath(str)), errors.getValue());
            } else if (isDeprecatedArgfileArgument(str)) {
                errors.getValue().getDeprecatedArguments().put("-Xargfile=", ARGFILE_ARGUMENT);
                listListOf = expand(new File(getDeprecatedArgfilePath(str)), errors.getValue());
            } else {
                listListOf = CollectionsKt.listOf(str);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        return arrayList;
    }

    public static final List<String> readArgumentsFromArgFile(String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        final StringReader stringReader = new StringReader(content);
        return SequencesKt.toList(SequencesKt.generateSequence(new Function0<String>() { // from class: org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt.readArgumentsFromArgFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return PreprocessCommandLineArgumentsKt.parseNextArgument(stringReader);
            }
        }));
    }

    private static final List<String> expand(File file, ArgumentParseErrors argumentParseErrors) {
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(file), Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                final BufferedReader bufferedReader2 = bufferedReader;
                List<String> list = SequencesKt.toList(SequencesKt.generateSequence(new Function0<String>() { // from class: org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt$expand$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return PreprocessCommandLineArgumentsKt.parseNextArgument(bufferedReader2);
                    }
                }));
                CloseableKt.closeFinally(bufferedReader, null);
                return list;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        } catch (FileNotFoundException unused) {
            argumentParseErrors.getArgfileErrors().add("Argfile not found: " + file.getAbsolutePath());
            return CollectionsKt.emptyList();
        } catch (IOException e) {
            argumentParseErrors.getArgfileErrors().add("Error while reading argfile: " + e);
            return CollectionsKt.emptyList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String parseNextArgument(Reader reader) {
        StringBuilder sb = new StringBuilder();
        Character chNextChar = nextChar(reader);
        while (chNextChar != null && CharsKt.isWhitespace(chNextChar.charValue())) {
            chNextChar = nextChar(reader);
        }
        while (chNextChar != null && !CharsKt.isWhitespace(chNextChar.charValue())) {
            if (chNextChar.charValue() == '\"' || chNextChar.charValue() == '\'') {
                consumeRestOfQuotedSequence(reader, sb, chNextChar.charValue());
                return sb.toString();
            }
            sb.append(chNextChar.charValue());
            chNextChar = nextChar(reader);
        }
        String string = sb.toString();
        Intrinsics.checkNotNull(string);
        if (string.length() > 0) {
            return string;
        }
        return null;
    }

    private static final void consumeRestOfQuotedSequence(Reader reader, StringBuilder sb, char c) {
        Character chNextChar = nextChar(reader);
        while (chNextChar != null && chNextChar.charValue() != c) {
            if (chNextChar.charValue() != '\\' || (chNextChar = nextChar(reader)) != null) {
                sb.append(chNextChar.charValue());
            }
            chNextChar = nextChar(reader);
        }
    }

    private static final Character nextChar(Reader reader) {
        Integer numValueOf = Integer.valueOf(reader.read());
        if (numValueOf.intValue() == -1) {
            numValueOf = null;
        }
        if (numValueOf != null) {
            return Character.valueOf((char) numValueOf.intValue());
        }
        return null;
    }

    private static final String getArgfilePath(String str) {
        return StringsKt.removePrefix(str, (CharSequence) ARGFILE_ARGUMENT);
    }

    private static final boolean isArgfileArgument(String str) {
        return StringsKt.startsWith$default(str, ARGFILE_ARGUMENT, false, 2, (Object) null);
    }

    private static final String getDeprecatedArgfilePath(String str) {
        return StringsKt.removePrefix(str, (CharSequence) "-Xargfile=");
    }

    private static final boolean isDeprecatedArgfileArgument(String str) {
        return StringsKt.startsWith$default(str, "-Xargfile=", false, 2, (Object) null);
    }
}
