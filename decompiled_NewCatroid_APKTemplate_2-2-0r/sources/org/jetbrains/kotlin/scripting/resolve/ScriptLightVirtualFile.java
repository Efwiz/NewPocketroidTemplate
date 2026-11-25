package org.jetbrains.kotlin.scripting.resolve;

import java.nio.charset.StandardCharsets;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: refineCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0003H\u0016J\b\u0010\b\u001a\u00020\u0003H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptLightVirtualFile;", "Lorg/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFile;", "name", "", "_path", "text", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCanonicalPath", "getPath", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptLightVirtualFile extends LightVirtualFile {
    private final String _path;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptLightVirtualFile(String name2, String str, String text) {
        super(name2, KotlinLanguage.INSTANCE, StringUtil.convertLineSeparators(text));
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(text, "text");
        this._path = str;
        setCharset(StandardCharsets.UTF_8);
    }

    @Override // org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFileBase, org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        String str = this._path;
        if (str != null) {
            return str;
        }
        if (getParent() != null) {
            return getParent().getPath() + '/' + getName();
        }
        String name2 = getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getCanonicalPath() {
        return getPath();
    }
}
