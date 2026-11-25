package org.jetbrains.kotlin.konan.target;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Windows.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0006\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Msvc;", "", "()V", "compilerFlags", "", "", "CustomPath", "DefaultPath", "Lorg/jetbrains/kotlin/konan/target/Msvc$CustomPath;", "Lorg/jetbrains/kotlin/konan/target/Msvc$DefaultPath;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class Msvc {
    public /* synthetic */ Msvc(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract List<String> compilerFlags();

    private Msvc() {
    }

    /* JADX INFO: compiled from: Windows.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Msvc$DefaultPath;", "Lorg/jetbrains/kotlin/konan/target/Msvc;", "()V", "compilerFlags", "", "", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultPath extends Msvc {
        public static final DefaultPath INSTANCE = new DefaultPath();

        private DefaultPath() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.konan.target.Msvc
        public List<String> compilerFlags() {
            return CollectionsKt.emptyList();
        }
    }

    /* JADX INFO: compiled from: Windows.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/Msvc$CustomPath;", "Lorg/jetbrains/kotlin/konan/target/Msvc;", "includeDirectories", "", "Ljava/nio/file/Path;", "libraryDirectories", "(Ljava/util/List;Ljava/util/List;)V", "compilerFlags", "", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class CustomPath extends Msvc {
        private final List<Path> includeDirectories;
        private final List<Path> libraryDirectories;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public CustomPath(List<? extends Path> includeDirectories, List<? extends Path> libraryDirectories) {
            super(null);
            Intrinsics.checkNotNullParameter(includeDirectories, "includeDirectories");
            Intrinsics.checkNotNullParameter(libraryDirectories, "libraryDirectories");
            this.includeDirectories = includeDirectories;
            this.libraryDirectories = libraryDirectories;
        }

        @Override // org.jetbrains.kotlin.konan.target.Msvc
        public List<String> compilerFlags() {
            List<Path> list = this.includeDirectories;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, CollectionsKt.listOf((Object[]) new String[]{"-isystem", ((Path) it.next()).toAbsolutePath().toString()}));
            }
            ArrayList arrayList2 = arrayList;
            List<Path> list2 = this.libraryDirectories;
            ArrayList arrayList3 = new ArrayList();
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                CollectionsKt.addAll(arrayList3, CollectionsKt.listOf((Object[]) new String[]{"-L", ((Path) it2.next()).toAbsolutePath().toString()}));
            }
            return CollectionsKt.plus((Collection) arrayList2, (Iterable) arrayList3);
        }
    }
}
