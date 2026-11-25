package kotlin.script.experimental.host;

import java.io.File;
import kotlin.Metadata;
import kotlin.script.experimental.api.ExternalSourceCode;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptHostUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/script/experimental/host/FileBasedScriptSource;", "Lkotlin/script/experimental/api/ExternalSourceCode;", "()V", "file", "Ljava/io/File;", "getFile", "()Ljava/io/File;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FileBasedScriptSource implements ExternalSourceCode {
    public abstract File getFile();
}
