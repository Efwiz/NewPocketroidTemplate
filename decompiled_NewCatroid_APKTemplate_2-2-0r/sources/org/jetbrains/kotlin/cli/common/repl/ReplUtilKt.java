package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.apkeditor.refactor.RefactorUtil;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.google.common.base.Throwables;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.LineSeparator;
import org.jetbrains.kotlin.utils.repl.ReplEscapeType;

/* JADX INFO: compiled from: replUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001\u001a\u000e\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f\u001a*\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0003\"\b\b\u0000\u0010\u0011*\u00020\u0012*\b\u0012\u0004\u0012\u0002H\u00110\u00032\u0006\u0010\u0013\u001a\u00020\u0001H\u0000\u001a\u0012\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0003*\u00020\u0016H\u0000\u001a\u0012\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00150\u0003*\u00020\u0018H\u0000\u001a\n\u0010\u0019\u001a\u00020\u0001*\u00020\u0001\u001a\u000f\u0010\u001a\u001a\u00070\u0001¢\u0006\u0002\b\u001b*\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u001d\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u0010\u001e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u001f\u001a\u00020 \u001a\n\u0010!\u001a\u00020\u0001*\u00020\u0001\u001a\u000f\u0010\"\u001a\u00070\u0001¢\u0006\u0002\b\u001b*\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"END_LINE", "", "SOURCE_CHARS", "", "XML_PREAMBLE", "XML_REPLACEMENTS", "makeScriptBaseName", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "renderReplStackTrace", "cause", "", "startFromMethodName", "scriptResultFieldName", "lineNo", "", "ensureNotEmpty", ExifInterface.GPS_DIRECTION_TRUE, "", "error", "listAllUrlsAsFiles", "Ljava/io/File;", "Ljava/lang/ClassLoader;", "listLocalUrlsAsFiles", "Ljava/net/URLClassLoader;", "replAddLineBreak", "replEscapeLineBreaks", "Lorg/jetbrains/annotations/NotNull;", "replInputAsXml", "replNormalizeLineBreaks", "replOutputAsXml", "escapeType", "Lorg/jetbrains/kotlin/utils/repl/ReplEscapeType;", "replRemoveLineBreaksInTheEnd", "replUnescapeLineBreaks", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplUtilKt {
    private static final String END_LINE;
    private static final List<String> SOURCE_CHARS = CollectionsKt.listOf((Object[]) new String[]{"\r", "\n", "#"});
    private static final List<String> XML_REPLACEMENTS = CollectionsKt.listOf((Object[]) new String[]{"#r", "#n", "#diez"});

    static {
        String separatorString = LineSeparator.getSystemLineSeparator().getSeparatorString();
        Intrinsics.checkNotNullExpressionValue(separatorString, "getSeparatorString(...)");
        END_LINE = separatorString;
    }

    public static final String replUnescapeLineBreaks(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strReplace = StringUtil.replace(str, XML_REPLACEMENTS, SOURCE_CHARS);
        Intrinsics.checkNotNullExpressionValue(strReplace, "replace(...)");
        return strReplace;
    }

    public static final String replEscapeLineBreaks(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strReplace = StringUtil.replace(str, SOURCE_CHARS, XML_REPLACEMENTS);
        Intrinsics.checkNotNullExpressionValue(strReplace, "replace(...)");
        return strReplace;
    }

    public static final String replOutputAsXml(String str, ReplEscapeType escapeType) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(escapeType, "escapeType");
        String strEscapeXmlEntities = StringUtil.escapeXmlEntities(replEscapeLineBreaks(str));
        Intrinsics.checkNotNullExpressionValue(strEscapeXmlEntities, "escapeXmlEntities(...)");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><output type=\"" + escapeType + "\">" + strEscapeXmlEntities + "</output>";
    }

    public static final String replInputAsXml(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strEscapeXmlEntities = StringUtil.escapeXmlEntities(replEscapeLineBreaks(str));
        Intrinsics.checkNotNullExpressionValue(strEscapeXmlEntities, "escapeXmlEntities(...)");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><input>" + strEscapeXmlEntities + "</input>";
    }

    public static final String replAddLineBreak(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str + END_LINE;
    }

    public static final String replRemoveLineBreaksInTheEnd(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        char[] charArray = END_LINE.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
        return StringsKt.trimEnd(str, Arrays.copyOf(charArray, charArray.length));
    }

    public static final String replNormalizeLineBreaks(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return StringsKt.replace$default(str, END_LINE, "\n", false, 4, (Object) null);
    }

    public static final String makeScriptBaseName(ReplCodeLine codeLine) {
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        return "Line_" + codeLine.getNo() + (codeLine.getGeneration() > 1 ? "_gen_" + codeLine.getGeneration() : "");
    }

    public static final String scriptResultFieldName(int i) {
        return RefactorUtil.RES_DIR + i;
    }

    public static final String renderReplStackTrace(Throwable cause, String startFromMethodName) {
        Intrinsics.checkNotNullParameter(cause, "cause");
        Intrinsics.checkNotNullParameter(startFromMethodName, "startFromMethodName");
        ArrayList arrayList = new ArrayList();
        StackTraceElement[] stackTrace = cause.getStackTrace();
        Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
        List listReversed = ArraysKt.reversed(stackTrace);
        int size = listReversed.size();
        boolean z = true;
        for (int i = 0; i < size; i++) {
            StackTraceElement stackTraceElement = (StackTraceElement) listReversed.get(i);
            if (Intrinsics.areEqual(stackTraceElement.getClassName() + '.' + stackTraceElement.getMethodName(), startFromMethodName)) {
                z = false;
            }
            if (!z) {
                arrayList.add(stackTraceElement);
            }
        }
        cause.setStackTrace((StackTraceElement[]) CollectionsKt.dropLast(CollectionsKt.reversed(arrayList), 1).toArray(new StackTraceElement[0]));
        String stackTraceAsString = Throwables.getStackTraceAsString(cause);
        Intrinsics.checkNotNullExpressionValue(stackTraceAsString, "getStackTraceAsString(...)");
        return StringsKt.trimEnd((CharSequence) stackTraceAsString).toString();
    }

    public static final List<File> listAllUrlsAsFiles(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "<this>");
        Sequence sequenceFilterIsInstance = SequencesKt.filterIsInstance(SequencesKt.generateSequence(classLoader, new Function1<ClassLoader, ClassLoader>() { // from class: org.jetbrains.kotlin.cli.common.repl.ReplUtilKt$listAllUrlsAsFiles$parents$1
            @Override // kotlin.jvm.functions.Function1
            public final ClassLoader invoke(ClassLoader loader) {
                Intrinsics.checkNotNullParameter(loader, "loader");
                return loader.getParent();
            }
        }), URLClassLoader.class);
        List listEmptyList = CollectionsKt.emptyList();
        Iterator it = sequenceFilterIsInstance.iterator();
        while (it.hasNext()) {
            listEmptyList = CollectionsKt.plus((Collection) listLocalUrlsAsFiles((URLClassLoader) it.next()), (Iterable) listEmptyList);
        }
        return CollectionsKt.distinct(listEmptyList);
    }

    public static final List<File> listLocalUrlsAsFiles(URLClassLoader uRLClassLoader) {
        Intrinsics.checkNotNullParameter(uRLClassLoader, "<this>");
        URL[] uRLs = uRLClassLoader.getURLs();
        Intrinsics.checkNotNullExpressionValue(uRLs, "getURLs(...)");
        ArrayList arrayList = new ArrayList();
        for (URL url : uRLs) {
            String string = url.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String strRemovePrefix = StringsKt.removePrefix(string, (CharSequence) "file:");
            if (strRemovePrefix != null) {
                arrayList.add(strRemovePrefix);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(new File((String) it.next()));
        }
        return arrayList3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> List<T> ensureNotEmpty(List<? extends T> list, String error) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(error, "error");
        if (list.isEmpty()) {
            throw new IllegalStateException(error);
        }
        return list;
    }
}
