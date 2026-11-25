package org.jetbrains.kotlin.build;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: JsBuildMetaInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/build/JsBuildMetaInfo;", "Lorg/jetbrains/kotlin/build/BuildMetaInfo;", "()V", "argumentsListForSpecialCheck", "", "", "getArgumentsListForSpecialCheck", "()Ljava/util/List;", "checkIfPlatformSpecificCompilerArgumentWasChanged", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "currentValue", "previousValue", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Boolean;", "createPropertiesMapFromCompilerArguments", "", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsBuildMetaInfo extends BuildMetaInfo {
    @Override // org.jetbrains.kotlin.build.BuildMetaInfo
    public Boolean checkIfPlatformSpecificCompilerArgumentWasChanged(String key, String currentValue, String previousValue) {
        int[] versionArray;
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(currentValue, "currentValue");
        Intrinsics.checkNotNullParameter(previousValue, "previousValue");
        if (Intrinsics.areEqual(key, "METADATA_VERSION_STRING") && (versionArray = BinaryVersion.INSTANCE.parseVersionArray(currentValue)) != null && versionArray.length == 3) {
            JsMetadataVersion jsMetadataVersion = new JsMetadataVersion(versionArray[0], versionArray[1], versionArray[2]);
            int[] versionArray2 = BinaryVersion.INSTANCE.parseVersionArray(previousValue);
            if (versionArray2 != null && versionArray2.length == 3) {
                return Boolean.valueOf(Intrinsics.areEqual(jsMetadataVersion, new JsMetadataVersion(versionArray2[0], versionArray2[1], versionArray2[2])));
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
        linkedHashMap.put("METADATA_VERSION_STRING", String.valueOf((versionArray != null ? new JsMetadataVersion(versionArray) : JsMetadataVersion.INSTANCE).toInteger()));
        return MapsKt.plus(super.createPropertiesMapFromCompilerArguments(args), linkedHashMap);
    }

    @Override // org.jetbrains.kotlin.build.BuildMetaInfo
    public List<String> getArgumentsListForSpecialCheck() {
        return CollectionsKt.plus((Collection) super.getArgumentsListForSpecialCheck(), (Iterable) CollectionsKt.listOf((Object[]) new String[]{"sourceMap", "metaInfopartialLinkagewasmDebug"}));
    }
}
