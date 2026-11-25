package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Configurables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/MingwConfigurables;", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "Lorg/jetbrains/kotlin/konan/target/ClangFlags;", "absoluteLinker", "", "getAbsoluteLinker", "()Ljava/lang/String;", "linker", "getLinker", "msvc", "Lorg/jetbrains/kotlin/konan/target/Msvc;", "getMsvc", "()Lorg/jetbrains/kotlin/konan/target/Msvc;", "msvcParts", "getMsvcParts", "windowsKit", "Lorg/jetbrains/kotlin/konan/target/WindowsKit;", "getWindowsKit", "()Lorg/jetbrains/kotlin/konan/target/WindowsKit;", "windowsKitParts", "getWindowsKitParts", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface MingwConfigurables extends ClangFlags, Configurables {
    Msvc getMsvc();

    WindowsKit getWindowsKit();

    default String getLinker() {
        String strHostTargetString = hostTargetString("linker");
        Intrinsics.checkNotNull(strHostTargetString);
        return strHostTargetString;
    }

    default String getAbsoluteLinker() {
        return absolute(getLinker());
    }

    default String getWindowsKitParts() {
        String strHostString = hostString("windowsKitParts");
        Intrinsics.checkNotNull(strHostString);
        return strHostString;
    }

    default String getMsvcParts() {
        String strHostString = hostString("msvcParts");
        Intrinsics.checkNotNull(strHostString);
        return strHostString;
    }
}
