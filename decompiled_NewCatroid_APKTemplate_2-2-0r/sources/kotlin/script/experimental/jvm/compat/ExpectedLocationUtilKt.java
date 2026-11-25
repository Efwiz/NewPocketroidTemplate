package kotlin.script.experimental.jvm.compat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptAcceptedLocation;
import kotlin.script.experimental.location.ScriptExpectedLocation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: expectedLocationUtil.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0001\u001a\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0001*\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0005"}, d2 = {"mapLegacyExpectedLocations", "", "Lkotlin/script/experimental/api/ScriptAcceptedLocation;", "Lkotlin/script/experimental/location/ScriptExpectedLocation;", "mapToLegacyExpectedLocations", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectedLocationUtilKt {

    /* JADX INFO: compiled from: expectedLocationUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ScriptAcceptedLocation.values().length];
            try {
                iArr[ScriptAcceptedLocation.Sources.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScriptAcceptedLocation.Tests.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ScriptAcceptedLocation.Libraries.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ScriptAcceptedLocation.Project.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ScriptAcceptedLocation.Everywhere.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ScriptExpectedLocation.values().length];
            try {
                iArr2[ScriptExpectedLocation.SourcesOnly.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ScriptExpectedLocation.TestsOnly.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[ScriptExpectedLocation.Libraries.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[ScriptExpectedLocation.Project.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[ScriptExpectedLocation.Everywhere.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final List<ScriptExpectedLocation> mapToLegacyExpectedLocations(List<? extends ScriptAcceptedLocation> list) {
        ScriptExpectedLocation scriptExpectedLocation;
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<? extends ScriptAcceptedLocation> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            int i = WhenMappings.$EnumSwitchMapping$0[((ScriptAcceptedLocation) it.next()).ordinal()];
            if (i == 1) {
                scriptExpectedLocation = ScriptExpectedLocation.SourcesOnly;
            } else if (i == 2) {
                scriptExpectedLocation = ScriptExpectedLocation.TestsOnly;
            } else if (i == 3) {
                scriptExpectedLocation = ScriptExpectedLocation.Libraries;
            } else if (i == 4) {
                scriptExpectedLocation = ScriptExpectedLocation.Project;
            } else {
                if (i != 5) {
                    throw new NoWhenBranchMatchedException();
                }
                scriptExpectedLocation = ScriptExpectedLocation.Everywhere;
            }
            arrayList.add(scriptExpectedLocation);
        }
        return arrayList;
    }

    public static final List<ScriptAcceptedLocation> mapLegacyExpectedLocations(List<? extends ScriptExpectedLocation> list) {
        ScriptAcceptedLocation scriptAcceptedLocation;
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<? extends ScriptExpectedLocation> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            int i = WhenMappings.$EnumSwitchMapping$1[((ScriptExpectedLocation) it.next()).ordinal()];
            if (i == 1) {
                scriptAcceptedLocation = ScriptAcceptedLocation.Sources;
            } else if (i == 2) {
                scriptAcceptedLocation = ScriptAcceptedLocation.Tests;
            } else if (i == 3) {
                scriptAcceptedLocation = ScriptAcceptedLocation.Libraries;
            } else if (i == 4) {
                scriptAcceptedLocation = ScriptAcceptedLocation.Project;
            } else {
                if (i != 5) {
                    throw new NoWhenBranchMatchedException();
                }
                scriptAcceptedLocation = ScriptAcceptedLocation.Everywhere;
            }
            arrayList.add(scriptAcceptedLocation);
        }
        return arrayList;
    }
}
