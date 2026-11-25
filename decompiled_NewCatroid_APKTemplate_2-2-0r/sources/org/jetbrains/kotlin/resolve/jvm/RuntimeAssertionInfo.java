package org.jetbrains.kotlin.resolve.jvm;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeEnhancementKt;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: RuntimeAssertions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 \u000b2\u00020\u0001:\u0002\u000b\fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo;", "", "needNotNullAssertion", "", CrashHianalyticsData.MESSAGE, "", "(ZLjava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getNeedNotNullAssertion", "()Z", "Companion", "DataFlowExtras", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeAssertionInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String message;
    private final boolean needNotNullAssertion;

    @JvmStatic
    public static final RuntimeAssertionInfo create(KotlinType kotlinType, KotlinType kotlinType2, DataFlowExtras dataFlowExtras) {
        return INSTANCE.create(kotlinType, kotlinType2, dataFlowExtras);
    }

    /* JADX INFO: compiled from: RuntimeAssertions.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\nR\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo$DataFlowExtras;", "", "canBeNull", "", "getCanBeNull", "()Z", "presentableText", "", "getPresentableText", "()Ljava/lang/String;", "OnlyMessage", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface DataFlowExtras {
        boolean getCanBeNull();

        String getPresentableText();

        /* JADX INFO: compiled from: RuntimeAssertions.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo$DataFlowExtras$OnlyMessage;", "Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo$DataFlowExtras;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "canBeNull", "", "getCanBeNull", "()Z", "presentableText", "getPresentableText", "()Ljava/lang/String;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class OnlyMessage implements DataFlowExtras {
            private final String presentableText;

            @Override // org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo.DataFlowExtras
            public boolean getCanBeNull() {
                return true;
            }

            public OnlyMessage(String message) {
                Intrinsics.checkNotNullParameter(message, "message");
                this.presentableText = message;
            }

            @Override // org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo.DataFlowExtras
            public String getPresentableText() {
                return this.presentableText;
            }
        }
    }

    public RuntimeAssertionInfo(boolean z, String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.needNotNullAssertion = z;
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }

    public final boolean getNeedNotNullAssertion() {
        return this.needNotNullAssertion;
    }

    /* JADX INFO: compiled from: RuntimeAssertions.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionType", "dataFlowExtras", "Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo$DataFlowExtras;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private static final boolean create$assertNotNull(KotlinType kotlinType, KotlinType kotlinType2, DataFlowExtras dataFlowExtras) {
            if (KotlinTypeKt.isError(kotlinType) || KotlinTypeKt.isError(kotlinType2) || TypeUtils.isNullableType(kotlinType) || TypeEnhancementKt.hasEnhancedNullability(kotlinType) || InlineClassesUtilsKt.isNullableUnderlyingType(kotlinType)) {
                return false;
            }
            boolean zIsNullableType = TypeUtils.isNullableType(kotlinType2);
            if (zIsNullableType || TypeEnhancementKt.hasEnhancedNullability(kotlinType2)) {
                return dataFlowExtras.getCanBeNull() || !zIsNullableType;
            }
            return false;
        }

        @JvmStatic
        public final RuntimeAssertionInfo create(KotlinType expectedType, KotlinType expressionType, DataFlowExtras dataFlowExtras) {
            Intrinsics.checkNotNullParameter(expectedType, "expectedType");
            Intrinsics.checkNotNullParameter(expressionType, "expressionType");
            Intrinsics.checkNotNullParameter(dataFlowExtras, "dataFlowExtras");
            if (create$assertNotNull(expectedType, expressionType, dataFlowExtras)) {
                return new RuntimeAssertionInfo(true, dataFlowExtras.getPresentableText());
            }
            return null;
        }
    }
}
