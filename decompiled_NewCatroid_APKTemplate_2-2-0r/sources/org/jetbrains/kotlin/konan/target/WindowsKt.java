package org.jetbrains.kotlin.konan.target;

import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: Windows.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0003H\u0002¨\u0006\u0007"}, d2 = {"createCustomMsvcPath", "Lorg/jetbrains/kotlin/konan/target/Msvc$CustomPath;", "msvcParts", "Ljava/nio/file/Path;", "createCustomWindowsKitPath", "Lorg/jetbrains/kotlin/konan/target/WindowsKit$CustomPath;", "windowsKitParts", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class WindowsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final WindowsKit.CustomPath createCustomWindowsKitPath(Path path) {
        return new WindowsKit.CustomPath(CollectionsKt.listOf(path.resolve("Include").resolve("ucrt")), CollectionsKt.listOf((Object[]) new Path[]{path.resolve("Lib").resolve("ucrt").resolve("x64"), path.resolve("Lib").resolve("um").resolve("x64")}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Msvc.CustomPath createCustomMsvcPath(Path path) {
        return new Msvc.CustomPath(CollectionsKt.listOf(path.resolve("include")), CollectionsKt.listOf(path.resolve("lib").resolve("x64")));
    }
}
