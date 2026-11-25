package org.jetbrains.kotlin.scripting;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0001H\u0000\u001a\u001a\u0010\u0004\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"withCorrectExtension", "", "name", "mainExtension", "scriptFileName", "Lkotlin/script/experimental/api/SourceCode;", "mainScript", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final String scriptFileName(SourceCode sourceCode, SourceCode mainScript, ScriptCompilationConfiguration scriptCompilationConfiguration) throws Exception {
        Intrinsics.checkNotNullParameter(sourceCode, "<this>");
        Intrinsics.checkNotNullParameter(mainScript, "mainScript");
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        String str = (String) scriptCompilationConfiguration.get(ScriptCompilationKt.getFileExtension(ScriptCompilationConfiguration.INSTANCE));
        if (sourceCode.getName() != null) {
            String name2 = sourceCode.getName();
            Intrinsics.checkNotNull(name2);
            return withCorrectExtension(name2, str);
        }
        if (Intrinsics.areEqual(mainScript, sourceCode)) {
            return withCorrectExtension("script", str);
        }
        throw new Exception("Unexpected script without name: " + sourceCode);
    }

    public static final String withCorrectExtension(String name2, String str) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if ((str != null && StringsKt.endsWith$default(name2, "." + str, false, 2, (Object) null)) || StringsKt.endsWith$default(name2, ".kts", false, 2, (Object) null)) {
            return name2;
        }
        StringBuilder sbAppend = new StringBuilder().append(name2).append('.');
        if (str == null) {
            str = "kts";
        }
        return sbAppend.append(str).toString();
    }
}
