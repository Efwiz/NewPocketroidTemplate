package org.jetbrains.kotlin.konan.target;

import com.reandroid.arsc.value.ResConfigBase;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Configurables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/konan/target/AppleConfigurables;", "Lorg/jetbrains/kotlin/konan/target/Configurables;", "Lorg/jetbrains/kotlin/konan/target/ClangFlags;", "absoluteAdditionalToolsDir", "", "getAbsoluteAdditionalToolsDir", "()Ljava/lang/String;", "additionalToolsDir", "getAdditionalToolsDir", "arch", "getArch", "osVersionMin", "getOsVersionMin", ResConfigBase.NAME_sdkVersion, "getSdkVersion", "stripFlags", "", "getStripFlags", "()Ljava/util/List;", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface AppleConfigurables extends ClangFlags, Configurables {
    default String getArch() {
        return getTargetTriple().getArchitecture();
    }

    default String getOsVersionMin() {
        String strTargetString = targetString("osVersionMin");
        Intrinsics.checkNotNull(strTargetString);
        return strTargetString;
    }

    default String getSdkVersion() {
        String strTargetString = targetString(ResConfigBase.NAME_sdkVersion);
        Intrinsics.checkNotNull(strTargetString);
        return strTargetString;
    }

    default List<String> getStripFlags() {
        return targetList("stripFlags");
    }

    default String getAdditionalToolsDir() {
        return hostString("additionalToolsDir");
    }

    default String getAbsoluteAdditionalToolsDir() {
        return absolute(getAdditionalToolsDir());
    }
}
