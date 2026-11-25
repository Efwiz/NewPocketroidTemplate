package org.jetbrains.kotlin.javac;

import com.sun.tools.javac.main.Option;
import com.sun.tools.javac.util.Options;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.transport.WalkEncryption;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: JavacOptionsMapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0014\u0010\u000e\u001a\u00020\u0007*\u00020\t2\u0006\u0010\u000f\u001a\u00020\fH\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/javac/JavacOptionsMapper;", "", "()V", "optionPattern", "Ljava/util/regex/Pattern;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "map", "", "options", "Lcom/sun/tools/javac/util/Options;", "arguments", "", "", "setUTF8Encoding", "putOption", "option", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacOptionsMapper {
    public static final JavacOptionsMapper INSTANCE = new JavacOptionsMapper();
    private static final Pattern optionPattern = Pattern.compile(WalkEncryption.Vals.REGEX_WS);

    private JavacOptionsMapper() {
    }

    public final void map(Options options, List<String> arguments) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            INSTANCE.putOption(options, (String) it.next());
        }
    }

    public final void setUTF8Encoding(Options options) {
        Intrinsics.checkNotNullParameter(options, "options");
        options.put(Option.ENCODING, "UTF8");
    }

    private final void putOption(Options options, String str) {
        Pattern optionPattern2 = optionPattern;
        Intrinsics.checkNotNullExpressionValue(optionPattern2, "optionPattern");
        List listSplit$default = StringsKt.split$default(str, optionPattern2, 0, 2, (Object) null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            if (((String) obj).length() > 0) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        if (size == 1) {
            options.put((String) arrayList2.get(0), (String) arrayList2.get(0));
        } else {
            if (size != 2) {
                return;
            }
            options.put((String) arrayList2.get(0), (String) arrayList2.get(1));
        }
    }
}
