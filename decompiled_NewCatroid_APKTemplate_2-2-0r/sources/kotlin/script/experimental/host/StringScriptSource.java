package kotlin.script.experimental.host;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptHostUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00142\u00020\u00012\u00020\u0002:\u0001\u0014B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\t¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/host/StringScriptSource;", "Lkotlin/script/experimental/api/SourceCode;", "Ljava/io/Serializable;", FirebaseAnalytics.Param.SOURCE, "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "locationId", "getLocationId", "()Ljava/lang/String;", "getName", "getSource", "text", "getText", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StringScriptSource implements SourceCode, Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 0;
    private final String name;
    private final String source;

    public StringScriptSource(String source, String str) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
        this.name = str;
    }

    public /* synthetic */ StringScriptSource(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2);
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getName() {
        return this.name;
    }

    public final String getSource() {
        return this.source;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getText() {
        return this.source;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getLocationId() {
        return getName();
    }

    public boolean equals(Object other) {
        if (this != other) {
            StringScriptSource stringScriptSource = other instanceof StringScriptSource ? (StringScriptSource) other : null;
            if (stringScriptSource == null || !Intrinsics.areEqual(getText(), stringScriptSource.getText()) || !Intrinsics.areEqual(getName(), stringScriptSource.getName()) || !Intrinsics.areEqual(getLocationId(), stringScriptSource.getLocationId())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int iHashCode = getText().hashCode();
        String name2 = getName();
        int iHashCode2 = iHashCode + ((name2 != null ? name2.hashCode() : 0) * 17);
        String locationId = getLocationId();
        return iHashCode2 + ((locationId != null ? locationId.hashCode() : 0) * 23);
    }

    /* JADX INFO: compiled from: scriptHostUtil.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lkotlin/script/experimental/host/StringScriptSource$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
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
