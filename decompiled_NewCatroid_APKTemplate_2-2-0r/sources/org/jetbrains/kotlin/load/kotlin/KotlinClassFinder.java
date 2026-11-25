package org.jetbrains.kotlin.load.kotlin;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder;

/* JADX INFO: compiled from: KotlinClassFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\nJ\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "findKotlinClassOrContent", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Result", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinClassFinder extends KotlinMetadataFinder {
    Result findKotlinClassOrContent(JavaClass javaClass, JvmMetadataVersion jvmMetadataVersion);

    Result findKotlinClassOrContent(ClassId classId, JvmMetadataVersion jvmMetadataVersion);

    /* JADX INFO: compiled from: KotlinClassFinder.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "", "()V", "toKotlinJvmBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "ClassFileContent", "KotlinClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result$ClassFileContent;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result$KotlinClass;", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Result {
        public /* synthetic */ Result(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Result() {
        }

        public final KotlinJvmBinaryClass toKotlinJvmBinaryClass() {
            KotlinClass kotlinClass = this instanceof KotlinClass ? (KotlinClass) this : null;
            if (kotlinClass != null) {
                return kotlinClass.getKotlinJvmBinaryClass();
            }
            return null;
        }

        /* JADX INFO: compiled from: KotlinClassFinder.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u0086\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u0086\u0002R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result$KotlinClass;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "kotlinJvmBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "byteContent", "", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;[B)V", "getByteContent", "()[B", "getKotlinJvmBinaryClass", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "component1", "component2", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class KotlinClass extends Result {
            private final byte[] byteContent;
            private final KotlinJvmBinaryClass kotlinJvmBinaryClass;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public KotlinClass(KotlinJvmBinaryClass kotlinJvmBinaryClass, byte[] bArr) {
                super(null);
                Intrinsics.checkNotNullParameter(kotlinJvmBinaryClass, "kotlinJvmBinaryClass");
                this.kotlinJvmBinaryClass = kotlinJvmBinaryClass;
                this.byteContent = bArr;
            }

            public /* synthetic */ KotlinClass(KotlinJvmBinaryClass kotlinJvmBinaryClass, byte[] bArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(kotlinJvmBinaryClass, (i & 2) != 0 ? null : bArr);
            }

            public final byte[] getByteContent() {
                return this.byteContent;
            }

            public final KotlinJvmBinaryClass getKotlinJvmBinaryClass() {
                return this.kotlinJvmBinaryClass;
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final KotlinJvmBinaryClass getKotlinJvmBinaryClass() {
                return this.kotlinJvmBinaryClass;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final byte[] getByteContent() {
                return this.byteContent;
            }
        }

        /* JADX INFO: compiled from: KotlinClassFinder.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result$ClassFileContent;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", FirebaseAnalytics.Param.CONTENT, "", "([B)V", "getContent", "()[B", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ClassFileContent extends Result {
            private final byte[] content;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ClassFileContent(byte[] content) {
                super(null);
                Intrinsics.checkNotNullParameter(content, "content");
                this.content = content;
            }

            public final byte[] getContent() {
                return this.content;
            }
        }
    }
}
