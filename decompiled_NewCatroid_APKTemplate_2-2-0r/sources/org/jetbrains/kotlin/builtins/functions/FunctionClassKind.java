package org.jetbrains.kotlin.builtins.functions;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: FunctionClassKind.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0087\u0081\u0002\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionClassKind;", "", "(Ljava/lang/String;I)V", "Function", "SuspendFunction", StandardNames.K_FUNCTION_PREFIX, StandardNames.K_SUSPEND_FUNCTION_PREFIX, GrsBaseInfo.CountryCodeSource.UNKNOWN, "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
@IDEAPluginsCompatibilityAPI(message = "Please migrate to the FunctionTypeKind", plugins = "android", usedIn = {IDEAPlatforms._223})
public enum FunctionClassKind {
    Function,
    SuspendFunction,
    KFunction,
    KSuspendFunction,
    UNKNOWN;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<FunctionClassKind> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: FunctionClassKind.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionClassKind$Companion;", "", "()V", "getFunctionClassKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassKind;", "functionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FunctionClassKind getFunctionClassKind(FunctionTypeKind functionTypeKind) {
            Intrinsics.checkNotNullParameter(functionTypeKind, "functionTypeKind");
            if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE)) {
                return FunctionClassKind.Function;
            }
            if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE)) {
                return FunctionClassKind.SuspendFunction;
            }
            if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KFunction.INSTANCE)) {
                return FunctionClassKind.KFunction;
            }
            return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KSuspendFunction.INSTANCE) ? FunctionClassKind.KSuspendFunction : FunctionClassKind.UNKNOWN;
        }
    }
}
