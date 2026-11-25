package org.jetbrains.kotlin.container;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;

/* JADX INFO: compiled from: PlatformSpecificExtension.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003:\u0002\r\u000eB\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u001b\u0010\t\u001a\u00028\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH&¢\u0006\u0002\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "", "applicableTo", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "getApplicableTo", "()Ljava/lang/Class;", "resolveExtensionsClash", "extensions", "", "(Ljava/util/List;)Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "FallbackToDefault", "FirstWins", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PlatformExtensionsClashResolver<E extends PlatformSpecificExtension<E>> {
    private final Class<E> applicableTo;

    public abstract E resolveExtensionsClash(List<? extends E> extensions);

    public PlatformExtensionsClashResolver(Class<E> applicableTo) {
        Intrinsics.checkNotNullParameter(applicableTo, "applicableTo");
        this.applicableTo = applicableTo;
    }

    public final Class<E> getApplicableTo() {
        return this.applicableTo;
    }

    /* JADX INFO: compiled from: PlatformSpecificExtension.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u001b\u0010\t\u001a\u00028\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u000bH\u0016¢\u0006\u0002\u0010\fR\u0010\u0010\u0004\u001a\u00028\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver$FallbackToDefault;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "defaultValue", "applicableTo", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;Ljava/lang/Class;)V", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "resolveExtensionsClash", "extensions", "", "(Ljava/util/List;)Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FallbackToDefault<E extends PlatformSpecificExtension<E>> extends PlatformExtensionsClashResolver<E> {
        private final E defaultValue;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FallbackToDefault(E defaultValue, Class<E> applicableTo) {
            super(applicableTo);
            Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
            Intrinsics.checkNotNullParameter(applicableTo, "applicableTo");
            this.defaultValue = defaultValue;
        }

        @Override // org.jetbrains.kotlin.container.PlatformExtensionsClashResolver
        public E resolveExtensionsClash(List<? extends E> extensions) {
            Intrinsics.checkNotNullParameter(extensions, "extensions");
            return this.defaultValue;
        }
    }

    /* JADX INFO: compiled from: PlatformSpecificExtension.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\u00028\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\tH\u0016¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver$FirstWins;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "applicableTo", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "resolveExtensionsClash", "extensions", "", "(Ljava/util/List;)Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FirstWins<E extends PlatformSpecificExtension<E>> extends PlatformExtensionsClashResolver<E> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirstWins(Class<E> applicableTo) {
            super(applicableTo);
            Intrinsics.checkNotNullParameter(applicableTo, "applicableTo");
        }

        @Override // org.jetbrains.kotlin.container.PlatformExtensionsClashResolver
        public E resolveExtensionsClash(List<? extends E> extensions) {
            Intrinsics.checkNotNullParameter(extensions, "extensions");
            return (E) CollectionsKt.first((List) extensions);
        }
    }
}
