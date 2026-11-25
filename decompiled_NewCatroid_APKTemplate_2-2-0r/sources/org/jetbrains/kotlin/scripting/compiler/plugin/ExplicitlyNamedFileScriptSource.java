package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.io.File;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.FileScriptSource;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: AbstractScriptEvaluationExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000 \u000b2\u00020\u00012\u00020\u0002:\u0001\u000bB!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ExplicitlyNamedFileScriptSource;", "Lkotlin/script/experimental/host/FileScriptSource;", "Ljava/io/Serializable;", "name", "", "file", "Ljava/io/File;", "preloadedText", "(Ljava/lang/String;Ljava/io/File;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ExplicitlyNamedFileScriptSource extends FileScriptSource implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 0;
    private final String name;

    public /* synthetic */ ExplicitlyNamedFileScriptSource(String str, File file, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, file, (i & 4) != 0 ? null : str2);
    }

    @Override // kotlin.script.experimental.host.FileScriptSource, kotlin.script.experimental.api.SourceCode
    public String getName() {
        return this.name;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExplicitlyNamedFileScriptSource(String name2, File file, String str) {
        super(file, str);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(file, "file");
        this.name = name2;
    }

    /* JADX INFO: compiled from: AbstractScriptEvaluationExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ExplicitlyNamedFileScriptSource$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        private static /* synthetic */ void getSerialVersionUID$annotations() {
        }

        private Companion() {
        }
    }
}
