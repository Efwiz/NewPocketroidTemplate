package org.jetbrains.kotlin.com.intellij.util.io;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0004\b\t\n\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0004\f\r\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", ASTExpr.DEFAULT_MAP_KEY_NAME, "getKey", "()Ljava/lang/Object;", "AppendEvent", "CorruptionEvent", "PutEvent", "RemoveEvent", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$PutEvent;", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$RemoveEvent;", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$AppendEvent;", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$CorruptionEvent;", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public abstract class WalEvent<K, V> {
    public abstract K getKey();

    private WalEvent() {
    }

    public /* synthetic */ WalEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: writeAheadLog.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0003¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\bJ\u000e\u0010\f\u001a\u00028\u0003HÆ\u0003¢\u0006\u0002\u0010\bJ.\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00022\b\b\u0002\u0010\u0005\u001a\u00028\u0003HÆ\u0001¢\u0006\u0002\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u00028\u0003¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$PutEvent;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent;", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getValue", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/intellij/util/io/WalEvent$PutEvent;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
    public static final /* data */ class PutEvent<K, V> extends WalEvent<K, V> {
        private final K key;
        private final V value;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PutEvent)) {
                return false;
            }
            PutEvent putEvent = (PutEvent) other;
            return Intrinsics.areEqual(getKey(), putEvent.getKey()) && Intrinsics.areEqual(this.value, putEvent.value);
        }

        public int hashCode() {
            K key = getKey();
            int iHashCode = (key != null ? key.hashCode() : 0) * 31;
            V v = this.value;
            return iHashCode + (v != null ? v.hashCode() : 0);
        }

        public String toString() {
            return "PutEvent(key=" + getKey() + ", value=" + this.value + Constant.AFTER_QUTO;
        }

        public PutEvent(K k, V v) {
            super(null);
            this.key = k;
            this.value = v;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.WalEvent
        public K getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: writeAheadLog.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0002¢\u0006\u0002\u0010\u0005J\u000e\u0010\t\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\u0007J$\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u0002HÆ\u0001¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$RemoveEvent;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent;", ASTExpr.DEFAULT_MAP_KEY_NAME, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getKey", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;)Lcom/intellij/util/io/WalEvent$RemoveEvent;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
    public static final /* data */ class RemoveEvent<K, V> extends WalEvent<K, V> {
        private final K key;

        public boolean equals(Object other) {
            if (this != other) {
                return (other instanceof RemoveEvent) && Intrinsics.areEqual(getKey(), ((RemoveEvent) other).getKey());
            }
            return true;
        }

        public int hashCode() {
            K key = getKey();
            if (key != null) {
                return key.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "RemoveEvent(key=" + getKey() + Constant.AFTER_QUTO;
        }

        public RemoveEvent(K k) {
            super(null);
            this.key = k;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.WalEvent
        public K getKey() {
            return this.key;
        }
    }

    /* JADX INFO: compiled from: writeAheadLog.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\r\u001a\u00028\u0002HÆ\u0003¢\u0006\u0002\u0010\u000bJ\t\u0010\u000e\u001a\u00020\u0006HÆ\u0003J.\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$AppendEvent;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent;", ASTExpr.DEFAULT_MAP_KEY_NAME, "data", "", "(Ljava/lang/Object;[B)V", "getData", "()[B", "getKey", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;[B)Lcom/intellij/util/io/WalEvent$AppendEvent;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
    public static final /* data */ class AppendEvent<K, V> extends WalEvent<K, V> {
        private final byte[] data;
        private final K key;

        public String toString() {
            return "AppendEvent(key=" + getKey() + ", data=" + Arrays.toString(this.data) + Constant.AFTER_QUTO;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AppendEvent(K k, byte[] data) {
            super(null);
            Intrinsics.checkNotNullParameter(data, "data");
            this.key = k;
            this.data = data;
        }

        public final byte[] getData() {
            return this.data;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.WalEvent
        public K getKey() {
            return this.key;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            if (other != null) {
                AppendEvent appendEvent = (AppendEvent) other;
                return Intrinsics.areEqual(getKey(), appendEvent.getKey()) && Arrays.equals(this.data, appendEvent.data);
            }
            throw new NullPointerException("null cannot be cast to non-null type com.intellij.util.io.WalEvent.AppendEvent<*, *>");
        }

        public int hashCode() {
            K key = getKey();
            return ((key != null ? key.hashCode() : 0) * 31) + Arrays.hashCode(this.data);
        }
    }

    /* JADX INFO: compiled from: writeAheadLog.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent$CorruptionEvent;", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent;", "", "()V", ASTExpr.DEFAULT_MAP_KEY_NAME, "getKey", "()Ljava/lang/Void;", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
    public static final class CorruptionEvent extends WalEvent {
        public static final CorruptionEvent INSTANCE = new CorruptionEvent();

        private CorruptionEvent() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.WalEvent
        public Void getKey() {
            throw new UnsupportedOperationException();
        }
    }
}
