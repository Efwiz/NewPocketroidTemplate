package kotlin.script.experimental.host;

import java.io.Serializable;
import java.net.URL;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ExternalSourceCode;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptHostUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00182\u00020\u00012\u00020\u0002:\u0001\u0018B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u001b\u0010\u000e\u001a\u00020\t8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u0019"}, d2 = {"Lkotlin/script/experimental/host/UrlScriptSource;", "Lkotlin/script/experimental/api/ExternalSourceCode;", "Ljava/io/Serializable;", "externalLocation", "Ljava/net/URL;", "(Ljava/net/URL;)V", "getExternalLocation", "()Ljava/net/URL;", "locationId", "", "getLocationId", "()Ljava/lang/String;", "name", "getName", "text", "getText", "text$delegate", "Lkotlin/Lazy;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class UrlScriptSource implements ExternalSourceCode, Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 0;
    private final URL externalLocation;

    /* JADX INFO: renamed from: text$delegate, reason: from kotlin metadata */
    private final Lazy text;

    public UrlScriptSource(URL externalLocation) {
        Intrinsics.checkNotNullParameter(externalLocation, "externalLocation");
        this.externalLocation = externalLocation;
        this.text = LazyKt.lazy(new Function0<String>() { // from class: kotlin.script.experimental.host.UrlScriptSource$text$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return ScriptHostUtilKt.readTextSkipUtf8Bom(this.this$0.getExternalLocation());
            }
        });
    }

    @Override // kotlin.script.experimental.api.ExternalSourceCode
    public URL getExternalLocation() {
        return this.externalLocation;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getText() {
        return (String) this.text.getValue();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getName() {
        return getExternalLocation().getFile();
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getLocationId() {
        return getExternalLocation().toString();
    }

    public boolean equals(Object other) {
        if (this != other) {
            UrlScriptSource urlScriptSource = other instanceof UrlScriptSource ? (UrlScriptSource) other : null;
            if (urlScriptSource == null || !Intrinsics.areEqual(getExternalLocation(), urlScriptSource.getExternalLocation()) || !Intrinsics.areEqual(ScriptHostUtilKt.getTextSafe(this), ScriptHostUtilKt.getTextSafe(urlScriptSource))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int iHashCode = getExternalLocation().hashCode();
        String textSafe = ScriptHostUtilKt.getTextSafe(this);
        return iHashCode + ((textSafe != null ? textSafe.hashCode() : 0) * 17);
    }

    /* JADX INFO: compiled from: scriptHostUtil.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lkotlin/script/experimental/host/UrlScriptSource$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
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
