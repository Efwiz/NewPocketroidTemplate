package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: RequireKotlinConstants.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/RequireKotlinConstants;", "", "()V", "ERROR_CODE", "Lorg/jetbrains/kotlin/name/Name;", "getERROR_CODE", "()Lorg/jetbrains/kotlin/name/Name;", "FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getFQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "LEVEL", "getLEVEL", "MESSAGE", "getMESSAGE", "VERSION", "getVERSION", "VERSION_KIND", "getVERSION_KIND", "VERSION_REGEX", "Lkotlin/text/Regex;", "getVERSION_REGEX", "()Lkotlin/text/Regex;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RequireKotlinConstants {
    private static final Name ERROR_CODE;
    private static final Name LEVEL;
    private static final Name MESSAGE;
    private static final Name VERSION;
    private static final Name VERSION_KIND;
    private static final Regex VERSION_REGEX;
    public static final RequireKotlinConstants INSTANCE = new RequireKotlinConstants();
    private static final FqName FQ_NAME = new FqName("kotlin.internal.RequireKotlin");

    private RequireKotlinConstants() {
    }

    static {
        Name nameIdentifier = Name.identifier("version");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        VERSION = nameIdentifier;
        Name nameIdentifier2 = Name.identifier(CrashHianalyticsData.MESSAGE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        MESSAGE = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier(FirebaseAnalytics.Param.LEVEL);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        LEVEL = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("versionKind");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        VERSION_KIND = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("errorCode");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        ERROR_CODE = nameIdentifier5;
        VERSION_REGEX = new Regex("(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(\\.(0|[1-9][0-9]*))?");
    }

    public final FqName getFQ_NAME() {
        return FQ_NAME;
    }

    public final Name getVERSION() {
        return VERSION;
    }

    public final Name getMESSAGE() {
        return MESSAGE;
    }

    public final Name getLEVEL() {
        return LEVEL;
    }

    public final Name getVERSION_KIND() {
        return VERSION_KIND;
    }

    public final Name getERROR_CODE() {
        return ERROR_CODE;
    }

    public final Regex getVERSION_REGEX() {
        return VERSION_REGEX;
    }
}
