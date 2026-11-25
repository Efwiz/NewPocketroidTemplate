package org.jetbrains.kotlin.daemon.common;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0003J7\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020\u0003HÖ\u0001R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR&\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0013\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R\u0019\u0010\u001a\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "Lorg/jetbrains/kotlin/daemon/common/OptionsGroup;", "maxMemory", "", "maxMetaspaceSize", "reservedCodeCacheSize", "jvmParams", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Collection;)V", "getJvmParams", "()Ljava/util/Collection;", "setJvmParams", "(Ljava/util/Collection;)V", "mappers", "", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "getMappers", "()Ljava/util/List;", "getMaxMemory", "()Ljava/lang/String;", "setMaxMemory", "(Ljava/lang/String;)V", "getMaxMetaspaceSize", "setMaxMetaspaceSize", "getReservedCodeCacheSize", "setReservedCodeCacheSize", "restMapper", "Lorg/jetbrains/kotlin/daemon/common/RestPropMapper;", "getRestMapper", "()Lorg/jetbrains/kotlin/daemon/common/RestPropMapper;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DaemonJVMOptions implements OptionsGroup {
    private Collection<String> jvmParams;
    private String maxMemory;
    private String maxMetaspaceSize;
    private String reservedCodeCacheSize;

    public DaemonJVMOptions() {
        this(null, null, null, null, 15, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DaemonJVMOptions copy$default(DaemonJVMOptions daemonJVMOptions, String str, String str2, String str3, Collection collection, int i, Object obj) {
        if ((i & 1) != 0) {
            str = daemonJVMOptions.maxMemory;
        }
        if ((i & 2) != 0) {
            str2 = daemonJVMOptions.maxMetaspaceSize;
        }
        if ((i & 4) != 0) {
            str3 = daemonJVMOptions.reservedCodeCacheSize;
        }
        if ((i & 8) != 0) {
            collection = daemonJVMOptions.jvmParams;
        }
        return daemonJVMOptions.copy(str, str2, str3, collection);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getMaxMemory() {
        return this.maxMemory;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMaxMetaspaceSize() {
        return this.maxMetaspaceSize;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getReservedCodeCacheSize() {
        return this.reservedCodeCacheSize;
    }

    public final Collection<String> component4() {
        return this.jvmParams;
    }

    public final DaemonJVMOptions copy(String maxMemory, String maxMetaspaceSize, String reservedCodeCacheSize, Collection<String> jvmParams) {
        Intrinsics.checkNotNullParameter(maxMemory, "maxMemory");
        Intrinsics.checkNotNullParameter(maxMetaspaceSize, "maxMetaspaceSize");
        Intrinsics.checkNotNullParameter(reservedCodeCacheSize, "reservedCodeCacheSize");
        Intrinsics.checkNotNullParameter(jvmParams, "jvmParams");
        return new DaemonJVMOptions(maxMemory, maxMetaspaceSize, reservedCodeCacheSize, jvmParams);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DaemonJVMOptions)) {
            return false;
        }
        DaemonJVMOptions daemonJVMOptions = (DaemonJVMOptions) other;
        return Intrinsics.areEqual(this.maxMemory, daemonJVMOptions.maxMemory) && Intrinsics.areEqual(this.maxMetaspaceSize, daemonJVMOptions.maxMetaspaceSize) && Intrinsics.areEqual(this.reservedCodeCacheSize, daemonJVMOptions.reservedCodeCacheSize) && Intrinsics.areEqual(this.jvmParams, daemonJVMOptions.jvmParams);
    }

    public int hashCode() {
        return (((((this.maxMemory.hashCode() * 31) + this.maxMetaspaceSize.hashCode()) * 31) + this.reservedCodeCacheSize.hashCode()) * 31) + this.jvmParams.hashCode();
    }

    public String toString() {
        return "DaemonJVMOptions(maxMemory=" + this.maxMemory + ", maxMetaspaceSize=" + this.maxMetaspaceSize + ", reservedCodeCacheSize=" + this.reservedCodeCacheSize + ", jvmParams=" + this.jvmParams + ')';
    }

    public DaemonJVMOptions(String maxMemory, String maxMetaspaceSize, String reservedCodeCacheSize, Collection<String> jvmParams) {
        Intrinsics.checkNotNullParameter(maxMemory, "maxMemory");
        Intrinsics.checkNotNullParameter(maxMetaspaceSize, "maxMetaspaceSize");
        Intrinsics.checkNotNullParameter(reservedCodeCacheSize, "reservedCodeCacheSize");
        Intrinsics.checkNotNullParameter(jvmParams, "jvmParams");
        this.maxMemory = maxMemory;
        this.maxMetaspaceSize = maxMetaspaceSize;
        this.reservedCodeCacheSize = reservedCodeCacheSize;
        this.jvmParams = jvmParams;
    }

    public /* synthetic */ DaemonJVMOptions(String str, String str2, String str3, ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? new ArrayList() : arrayList);
    }

    public final String getMaxMemory() {
        return this.maxMemory;
    }

    public final void setMaxMemory(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.maxMemory = str;
    }

    public final String getMaxMetaspaceSize() {
        return this.maxMetaspaceSize;
    }

    public final void setMaxMetaspaceSize(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.maxMetaspaceSize = str;
    }

    public final String getReservedCodeCacheSize() {
        return this.reservedCodeCacheSize;
    }

    public final void setReservedCodeCacheSize(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.reservedCodeCacheSize = str;
    }

    public final Collection<String> getJvmParams() {
        return this.jvmParams;
    }

    public final void setJvmParams(Collection<String> collection) {
        Intrinsics.checkNotNullParameter(collection, "<set-?>");
        this.jvmParams = collection;
    }

    @Override // org.jetbrains.kotlin.daemon.common.OptionsGroup
    public List<PropMapper<?, ?, ?>> getMappers() {
        Function1 function1 = null;
        return CollectionsKt.listOf((Object[]) new PropMapper[]{new StringPropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonJVMOptions$mappers$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getMaxMemory()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((DaemonJVMOptions) obj).getMaxMemory();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "maxMemory";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonJVMOptions) obj).setMaxMemory((String) obj2);
            }
        }, CollectionsKt.listOf("Xmx"), null, null, function1, "", 56, null), new StringPropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonJVMOptions$mappers$2
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getMaxMetaspaceSize()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((DaemonJVMOptions) obj).getMaxMetaspaceSize();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "maxMetaspaceSize";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonJVMOptions) obj).setMaxMetaspaceSize((String) obj2);
            }
        }, CollectionsKt.listOf("XX:MaxMetaspaceSize"), null, null, null, ContainerUtils.KEY_VALUE_DELIMITER, 56, null), new StringPropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonJVMOptions$mappers$3
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getReservedCodeCacheSize()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((DaemonJVMOptions) obj).getReservedCodeCacheSize();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "reservedCodeCacheSize";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonJVMOptions) obj).setReservedCodeCacheSize((String) obj2);
            }
        }, CollectionsKt.listOf("XX:ReservedCodeCacheSize"), function1, null, null, ContainerUtils.KEY_VALUE_DELIMITER, 56, null), getRestMapper()});
    }

    public final RestPropMapper<?, ?> getRestMapper() {
        return new RestPropMapper<>(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonJVMOptions$restMapper$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getJvmParams()Ljava/util/Collection;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((DaemonJVMOptions) obj).getJvmParams();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "jvmParams";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonJVMOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonJVMOptions) obj).setJvmParams((Collection) obj2);
            }
        });
    }
}
