package org.jetbrains.kotlin.fir.types;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: ConeNullability.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "", "suffix", "", "(Ljava/lang/String;ILjava/lang/String;)V", "isNullable", "", "()Z", "getSuffix", "()Ljava/lang/String;", "NULLABLE", GrsBaseInfo.CountryCodeSource.UNKNOWN, "NOT_NULL", "Companion", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ConeNullability {
    NULLABLE(TypeDescription.Generic.OfWildcardType.SYMBOL),
    UNKNOWN("!"),
    NOT_NULL("");

    private final String suffix;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<ConeNullability> getEntries() {
        return $ENTRIES;
    }

    ConeNullability(String str) {
        this.suffix = str;
    }

    public final String getSuffix() {
        return this.suffix;
    }

    public final boolean isNullable() {
        return this != NOT_NULL;
    }

    /* JADX INFO: compiled from: ConeNullability.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeNullability$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "isNullable", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConeNullability create(boolean isNullable) {
            return isNullable ? ConeNullability.NULLABLE : ConeNullability.NOT_NULL;
        }
    }
}
