package org.jetbrains.kotlin.compilerRunner;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.build.GeneratedFile;
import org.jetbrains.kotlin.build.GeneratedJvmClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;

/* JADX INFO: compiled from: SimpleOutputItem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"toGeneratedFile", "Lorg/jetbrains/kotlin/build/GeneratedFile;", "Lorg/jetbrains/kotlin/compilerRunner/SimpleOutputItem;", "jvmMetadataVersionFromLanguageVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SimpleOutputItemKt {
    public static final GeneratedFile toGeneratedFile(SimpleOutputItem simpleOutputItem, JvmMetadataVersion jvmMetadataVersionFromLanguageVersion) {
        Intrinsics.checkNotNullParameter(simpleOutputItem, "<this>");
        Intrinsics.checkNotNullParameter(jvmMetadataVersionFromLanguageVersion, "jvmMetadataVersionFromLanguageVersion");
        String name2 = simpleOutputItem.getOutputFile().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return StringsKt.endsWith$default(name2, ".class", false, 2, (Object) null) ? new GeneratedJvmClass(simpleOutputItem.getSourceFiles(), simpleOutputItem.getOutputFile(), jvmMetadataVersionFromLanguageVersion) : new GeneratedFile(simpleOutputItem.getSourceFiles(), simpleOutputItem.getOutputFile());
    }
}
