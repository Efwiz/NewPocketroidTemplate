package org.jetbrains.kotlin.build;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: CommonBuildMetaInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/build/CommonBuildMetaInfo;", "Lorg/jetbrains/kotlin/build/BuildMetaInfo;", "()V", "checkIfPlatformSpecificCompilerArgumentWasChanged", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "currentValue", "previousValue", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Boolean;", "createPropertiesMapFromCompilerArguments", "", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonBuildMetaInfo extends BuildMetaInfo {
    @Override // org.jetbrains.kotlin.build.BuildMetaInfo
    public Boolean checkIfPlatformSpecificCompilerArgumentWasChanged(String key, String currentValue, String previousValue) {
        int[] versionArray;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(currentValue, "currentValue");
        Intrinsics.checkNotNullParameter(previousValue, "previousValue");
        if (Intrinsics.areEqual(key, "METADATA_VERSION_STRING") && (versionArray = BinaryVersion.INSTANCE.parseVersionArray(currentValue)) != null && versionArray.length == 3) {
            JvmMetadataVersion jvmMetadataVersion = new JvmMetadataVersion(versionArray[0], versionArray[1], versionArray[2]);
            int[] versionArray2 = BinaryVersion.INSTANCE.parseVersionArray(previousValue);
            if (versionArray2 != null && versionArray2.length == 3) {
                return Boolean.valueOf(Intrinsics.areEqual(jvmMetadataVersion, new JvmMetadataVersion(versionArray2[0], versionArray2[1], versionArray2[2])));
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.build.BuildMetaInfo
    public Map<String, String> createPropertiesMapFromCompilerArguments(CommonCompilerArguments args) {
        Intrinsics.checkNotNullParameter(args, "args");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String metadataVersion = args.getMetadataVersion();
        int[] versionArray = metadataVersion != null ? BinaryVersion.INSTANCE.parseVersionArray(metadataVersion) : null;
        linkedHashMap.put("METADATA_VERSION_STRING", (versionArray != null ? new JvmMetadataVersion(versionArray) : JvmMetadataVersion.INSTANCE).toString());
        return MapsKt.plus(super.createPropertiesMapFromCompilerArguments(args), linkedHashMap);
    }
}
