package kotlin.script.experimental.host;

import io.netty.util.internal.StringUtil;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ExternalSourceCode;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptSourceNamedFragment;
import kotlin.script.experimental.api.SourceCode;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptHostUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a&\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f*\u00020\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000fH\u0002\u001a\f\u0010\u0011\u001a\u00020\u0001*\u00020\u0012H\u0002\u001a\f\u0010\u0011\u001a\u00020\u0001*\u00020\u0013H\u0002\u001a\n\u0010\u0014\u001a\u00020\b*\u00020\u0012\u001a\u0016\u0010\u0014\u001a\u00020\b*\u00020\u00012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0016"}, d2 = {"UTF8_BOM", "", "textSafe", "Lkotlin/script/experimental/api/ExternalSourceCode;", "getTextSafe", "(Lkotlin/script/experimental/api/ExternalSourceCode;)Ljava/lang/String;", "getMergedScriptText", "script", "Lkotlin/script/experimental/api/SourceCode;", "configuration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "cleanContentPreservingLinesLayout", "", "", "start", "", "end", "readTextSkipUtf8Bom", "Ljava/io/File;", "Ljava/net/URL;", "toScriptSource", "name", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptHostUtilKt {
    private static final String UTF8_BOM = "\ufeff";

    public static final String getMergedScriptText(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration) {
        SourceCode.Range range;
        SourceCode.Position end;
        Intrinsics.checkNotNullParameter(script, "script");
        String text = script.getText();
        List list = scriptCompilationConfiguration != null ? (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getSourceFragments(ScriptCompilationConfiguration.INSTANCE)) : null;
        if (list == null || list.isEmpty()) {
            return text;
        }
        StringBuilder sb = new StringBuilder(text.length());
        Iterator it = list.iterator();
        ScriptSourceNamedFragment scriptSourceNamedFragment = null;
        while (true) {
            int iIntValue = 0;
            if (it.hasNext()) {
                ScriptSourceNamedFragment scriptSourceNamedFragment2 = (ScriptSourceNamedFragment) it.next();
                Integer absolutePos = scriptSourceNamedFragment2.getRange().getStart().getAbsolutePos();
                Integer absolutePos2 = scriptSourceNamedFragment2.getRange().getEnd().getAbsolutePos();
                if (absolutePos == null || absolutePos2 == null) {
                    throw new RuntimeException("Script fragments require absolute positions (received: " + scriptSourceNamedFragment2 + ')');
                }
                if (scriptSourceNamedFragment != null) {
                    Integer absolutePos3 = scriptSourceNamedFragment.getRange().getEnd().getAbsolutePos();
                    Intrinsics.checkNotNull(absolutePos3);
                    iIntValue = absolutePos3.intValue();
                }
                if (scriptSourceNamedFragment != null) {
                    Integer absolutePos4 = scriptSourceNamedFragment.getRange().getEnd().getAbsolutePos();
                    Intrinsics.checkNotNull(absolutePos4);
                    if (absolutePos4.intValue() > absolutePos.intValue()) {
                        throw new RuntimeException("Unsorted or overlapping fragments: previous: " + scriptSourceNamedFragment + ", current: " + scriptSourceNamedFragment2);
                    }
                }
                if (iIntValue < absolutePos.intValue()) {
                    Iterator<T> it2 = cleanContentPreservingLinesLayout(text, iIntValue, absolutePos.intValue()).iterator();
                    while (it2.hasNext()) {
                        sb.append(((Character) it2.next()).charValue());
                    }
                }
                sb.append(text.subSequence(absolutePos.intValue(), absolutePos2.intValue()));
                scriptSourceNamedFragment = scriptSourceNamedFragment2;
            } else {
                Integer absolutePos5 = (scriptSourceNamedFragment == null || (range = scriptSourceNamedFragment.getRange()) == null || (end = range.getEnd()) == null) ? null : end.getAbsolutePos();
                if (absolutePos5 != null && absolutePos5.intValue() < text.length()) {
                    Iterator it3 = cleanContentPreservingLinesLayout$default(text, absolutePos5.intValue(), 0, 2, null).iterator();
                    while (it3.hasNext()) {
                        sb.append(((Character) it3.next()).charValue());
                    }
                }
                String string = sb.toString();
                Intrinsics.checkNotNull(string);
                return string;
            }
        }
    }

    static /* synthetic */ List cleanContentPreservingLinesLayout$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return cleanContentPreservingLinesLayout(str, i, i2);
    }

    private static final List<Character> cleanContentPreservingLinesLayout(String str, int i, int i2) {
        CharSequence charSequenceSubSequence = str.subSequence(i, i2);
        ArrayList arrayList = new ArrayList(charSequenceSubSequence.length());
        for (int i3 = 0; i3 < charSequenceSubSequence.length(); i3++) {
            char cCharAt = charSequenceSubSequence.charAt(i3);
            if (cCharAt != '\r' && cCharAt != '\n') {
                cCharAt = StringUtil.SPACE;
            }
            arrayList.add(Character.valueOf(cCharAt));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SourceCode toScriptSource(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new FileScriptSource(file, null, 2, 0 == true ? 1 : 0);
    }

    public static final SourceCode toScriptSource(String str, String str2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return new StringScriptSource(str, str2);
    }

    public static /* synthetic */ SourceCode toScriptSource$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = null;
        }
        return toScriptSource(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getTextSafe(ExternalSourceCode externalSourceCode) {
        try {
            return externalSourceCode.getText();
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String readTextSkipUtf8Bom(File file) {
        return StringsKt.removePrefix(FilesKt.readText$default(file, null, 1, null), (CharSequence) UTF8_BOM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String readTextSkipUtf8Bom(URL url) {
        return StringsKt.removePrefix(new String(TextStreamsKt.readBytes(url), Charsets.UTF_8), (CharSequence) UTF8_BOM);
    }
}
