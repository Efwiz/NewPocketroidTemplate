package org.jetbrains.kotlin.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;

/* JADX INFO: compiled from: KotlinJavascriptMetadataUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0011\u0012\n\u0010\u0002\u001a\u00020\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0006\u0010\b\u001a\u00020\u0004¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "numbers", "", "", "([I)V", "isCompatibleWithCurrentCompilerVersion", "", "toInteger", "Companion", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsMetadataVersion extends BinaryVersion {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final JsMetadataVersion INSTANCE = new JsMetadataVersion(1, 2, 6);
    public static final JsMetadataVersion INVALID_VERSION = new JsMetadataVersion(new int[0]);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsMetadataVersion(int... numbers) {
        super(Arrays.copyOf(numbers, numbers.length));
        Intrinsics.checkNotNullParameter(numbers, "numbers");
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.BinaryVersion
    public boolean isCompatibleWithCurrentCompilerVersion() {
        return isCompatibleTo(INSTANCE);
    }

    public final int toInteger() {
        return (getPatch() << 16) + (Math.min(getMinor(), 255) << 8) + Math.min(getMajor(), 255);
    }

    /* JADX INFO: compiled from: KotlinJavascriptMetadataUtils.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/utils/JsMetadataVersion$Companion;", "", "()V", JvmAbi.INSTANCE_FIELD, "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "INVALID_VERSION", "fromInteger", "version", "", "readFrom", "stream", "Ljava/io/InputStream;", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JsMetadataVersion fromInteger(int version) {
            return new JsMetadataVersion(version & 255, (version >> 8) & 255, version >> 16);
        }

        public final JsMetadataVersion readFrom(InputStream stream) throws IOException {
            Intrinsics.checkNotNullParameter(stream, "stream");
            DataInputStream dataInputStream = new DataInputStream(stream);
            int i = dataInputStream.readInt();
            if (i != JsMetadataVersion.INSTANCE.getNumbers().length) {
                return JsMetadataVersion.INVALID_VERSION;
            }
            IntRange intRange = new IntRange(1, i);
            ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(intRange, 10));
            Iterator<Integer> it = intRange.iterator();
            while (it.hasNext()) {
                ((IntIterator) it).nextInt();
                arrayList.add(Integer.valueOf(dataInputStream.readInt()));
            }
            int[] intArray = kotlin.collections.CollectionsKt.toIntArray(arrayList);
            return new JsMetadataVersion(Arrays.copyOf(intArray, intArray.length));
        }
    }
}
