package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder;

/* JADX INFO: compiled from: DirectoryBasedDataFinder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DirectoryBasedDataFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "classFinder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DirectoryBasedClassFinder;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DirectoryBasedClassFinder;Lcom/intellij/openapi/diagnostic/Logger;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)V", "getClassFinder", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DirectoryBasedClassFinder;", "getLog", "()Lcom/intellij/openapi/diagnostic/Logger;", "findClassData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DirectoryBasedDataFinder implements ClassDataFinder {
    private final DirectoryBasedClassFinder classFinder;
    private final JvmMetadataVersion jvmMetadataVersion;
    private final Logger log;

    public DirectoryBasedDataFinder(DirectoryBasedClassFinder classFinder, Logger log, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(classFinder, "classFinder");
        Intrinsics.checkNotNullParameter(log, "log");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        this.classFinder = classFinder;
        this.log = log;
        this.jvmMetadataVersion = jvmMetadataVersion;
    }

    public final DirectoryBasedClassFinder getClassFinder() {
        return this.classFinder;
    }

    public final Logger getLog() {
        return this.log;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder
    public ClassData findClassData(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(this.classFinder, classId, this.jvmMetadataVersion);
        if (kotlinJvmBinaryClassFindKotlinClass == null) {
            return null;
        }
        KotlinClassHeader classHeader = kotlinJvmBinaryClassFindKotlinClass.getClassHeader();
        String[] data = classHeader.getData();
        if (data == null) {
            this.log.error("Annotation data missing for " + kotlinJvmBinaryClassFindKotlinClass.getClassId());
            return null;
        }
        String[] strings = classHeader.getStrings();
        if (strings == null) {
            this.log.error("String table not found in class " + kotlinJvmBinaryClassFindKotlinClass.getClassId());
            return null;
        }
        Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom = JvmProtoBufUtil.readClassDataFrom(data, strings);
        return new ClassData(classDataFrom.component1(), classDataFrom.component2(), classHeader.getMetadataVersion(), new KotlinJvmBinarySourceElement(kotlinJvmBinaryClassFindKotlinClass, null, false, null, 14, null));
    }
}
