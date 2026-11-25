package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: ErrorUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/ErrorUtils;", "", "()V", "createErrorType", "Lorg/jetbrains/kotlin/types/SimpleType;", "debugMessage", "", "createUnresolvedType", "presentableName", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
@IDEAPluginsCompatibilityAPI(message = "Please migrate to the org.jetbrains.kotlin.types.error.ErrorUtils", plugins = "android/safeargs", usedIn = {IDEAPlatforms._212, IDEAPlatforms._213, IDEAPlatforms._221})
public final class ErrorUtils {
    public static final ErrorUtils INSTANCE = new ErrorUtils();

    private ErrorUtils() {
    }

    @JvmStatic
    @IDEAPluginsCompatibilityAPI(message = "Please migrate to the org.jetbrains.kotlin.types.error.ErrorUtils", plugins = "android/safeargs", usedIn = {IDEAPlatforms._212, IDEAPlatforms._213, IDEAPlatforms._221})
    public static final SimpleType createUnresolvedType(String presentableName, List<? extends TypeProjection> arguments) {
        Intrinsics.checkNotNullParameter(presentableName, "presentableName");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return org.jetbrains.kotlin.types.error.ErrorUtils.INSTANCE.createErrorTypeWithArguments(ErrorTypeKind.UNRESOLVED_TYPE, arguments, INSTANCE.toString());
    }

    @JvmStatic
    @IDEAPluginsCompatibilityAPI(message = "Please migrate to the org.jetbrains.kotlin.types.error.ErrorUtils", plugins = "mobile-ide/kotlin-ocswift", usedIn = {IDEAPlatforms._213, IDEAPlatforms._221})
    public static final SimpleType createErrorType(String debugMessage) {
        Intrinsics.checkNotNullParameter(debugMessage, "debugMessage");
        return org.jetbrains.kotlin.types.error.ErrorUtils.createErrorType(ErrorTypeKind.UNRESOLVED_TYPE, debugMessage);
    }
}
