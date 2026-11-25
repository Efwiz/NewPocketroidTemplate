package kotlin.script.experimental.location;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptLocation_deprecated.kt */
/* JADX INFO: loaded from: classes12.dex */
@Deprecated(message = "Experimental API")
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lkotlin/script/experimental/location/ScriptExpectedLocation;", "", "(Ljava/lang/String;I)V", "SourcesOnly", "TestsOnly", "Libraries", "Project", "Everywhere", PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ScriptExpectedLocation {
    SourcesOnly,
    TestsOnly,
    Libraries,
    Project,
    Everywhere;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ScriptExpectedLocation> getEntries() {
        return $ENTRIES;
    }
}
