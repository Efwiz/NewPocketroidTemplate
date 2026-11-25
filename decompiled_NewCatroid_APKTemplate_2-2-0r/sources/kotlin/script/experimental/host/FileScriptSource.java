package kotlin.script.experimental.host;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptHostUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u001d2\u00020\u00012\u00020\u0002:\u0001\u001dB\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0014\u0010\u0010¨\u0006\u001e"}, d2 = {"Lkotlin/script/experimental/host/FileScriptSource;", "Lkotlin/script/experimental/host/FileBasedScriptSource;", "Ljava/io/Serializable;", "file", "Ljava/io/File;", "preloadedText", "", "(Ljava/io/File;Ljava/lang/String;)V", "externalLocation", "Ljava/net/URL;", "getExternalLocation", "()Ljava/net/URL;", "getFile", "()Ljava/io/File;", "locationId", "getLocationId", "()Ljava/lang/String;", "name", "getName", "text", "getText", "text$delegate", "Lkotlin/Lazy;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FileScriptSource extends FileBasedScriptSource implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 0;
    private final File file;
    private final String preloadedText;

    /* JADX INFO: renamed from: text$delegate, reason: from kotlin metadata */
    private final Lazy text;

    public FileScriptSource(File file, String str) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
        this.preloadedText = str;
        this.text = LazyKt.lazy(new Function0<String>() { // from class: kotlin.script.experimental.host.FileScriptSource$text$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                String str2 = this.this$0.preloadedText;
                return str2 == null ? ScriptHostUtilKt.readTextSkipUtf8Bom(this.this$0.getFile()) : str2;
            }
        });
    }

    public /* synthetic */ FileScriptSource(File file, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? null : str);
    }

    @Override // kotlin.script.experimental.host.FileBasedScriptSource
    public File getFile() {
        return this.file;
    }

    @Override // kotlin.script.experimental.api.ExternalSourceCode
    public URL getExternalLocation() throws MalformedURLException {
        URL url = getFile().toURI().toURL();
        Intrinsics.checkNotNullExpressionValue(url, "toURL(...)");
        return url;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getText() {
        return (String) this.text.getValue();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getName() {
        return getFile().getName();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getLocationId() {
        return getFile().getPath();
    }

    public boolean equals(Object other) {
        if (this != other) {
            FileScriptSource fileScriptSource = other instanceof FileScriptSource ? (FileScriptSource) other : null;
            if (fileScriptSource == null || !Intrinsics.areEqual(getFile().getAbsolutePath(), fileScriptSource.getFile().getAbsolutePath()) || !Intrinsics.areEqual(ScriptHostUtilKt.getTextSafe(this), ScriptHostUtilKt.getTextSafe(fileScriptSource))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int iHashCode = getFile().getAbsolutePath().hashCode();
        String textSafe = ScriptHostUtilKt.getTextSafe(this);
        return iHashCode + ((textSafe != null ? textSafe.hashCode() : 0) * 23);
    }

    /* JADX INFO: compiled from: scriptHostUtil.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lkotlin/script/experimental/host/FileScriptSource$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
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
