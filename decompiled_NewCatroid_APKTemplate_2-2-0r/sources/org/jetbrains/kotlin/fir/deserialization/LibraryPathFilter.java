package org.jetbrains.kotlin.fir.deserialization;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/LibraryPathFilter;", "", "()V", "accepts", "", "path", "Ljava/nio/file/Path;", "LibraryList", "TakeAll", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LibraryPathFilter {
    public abstract boolean accepts(Path path);

    /* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/LibraryPathFilter$TakeAll;", "Lorg/jetbrains/kotlin/fir/deserialization/LibraryPathFilter;", "()V", "accepts", "", "path", "Ljava/nio/file/Path;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TakeAll extends LibraryPathFilter {
        public static final TakeAll INSTANCE = new TakeAll();

        @Override // org.jetbrains.kotlin.fir.deserialization.LibraryPathFilter
        public boolean accepts(Path path) {
            return true;
        }

        private TakeAll() {
        }
    }

    /* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b²\u0006\u0012\u0010\f\u001a\n \r*\u0004\u0018\u00010\u00040\u0004X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/LibraryPathFilter$LibraryList;", "Lorg/jetbrains/kotlin/fir/deserialization/LibraryPathFilter;", "libs", "", "Ljava/nio/file/Path;", "(Ljava/util/Set;)V", "getLibs", "()Ljava/util/Set;", "accepts", "", "path", "fir-deserialization", "absolutePath", JvmProtoBufUtil.PLATFORM_TYPE_ID}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LibraryList extends LibraryPathFilter {
        private final Set<Path> libs;

        public LibraryList(Set<? extends Path> libs) {
            Intrinsics.checkNotNullParameter(libs, "libs");
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<T> it = libs.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(((Path) it.next()).normalize());
            }
            this.libs = linkedHashSet;
        }

        public final Set<Path> getLibs() {
            return this.libs;
        }

        @Override // org.jetbrains.kotlin.fir.deserialization.LibraryPathFilter
        public boolean accepts(final Path path) {
            boolean zStartsWith;
            if (path == null) {
                return false;
            }
            boolean zIsAbsolute = path.isAbsolute();
            Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Path>() { // from class: org.jetbrains.kotlin.fir.deserialization.LibraryPathFilter$LibraryList$accepts$absolutePath$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Path invoke() {
                    return path.toAbsolutePath();
                }
            });
            Set<Path> set = this.libs;
            if ((set instanceof Collection) && set.isEmpty()) {
                return false;
            }
            for (Path path2 : set) {
                if (!path2.isAbsolute() || zIsAbsolute) {
                    zStartsWith = (path2.isAbsolute() || !zIsAbsolute) ? path.startsWith(path2) : path.startsWith(path2.toAbsolutePath());
                } else {
                    zStartsWith = accepts$lambda$1(lazy).startsWith(path2);
                }
                if (zStartsWith) {
                    return true;
                }
            }
            return false;
        }

        private static final Path accepts$lambda$1(Lazy<? extends Path> lazy) {
            return lazy.getValue();
        }
    }
}
