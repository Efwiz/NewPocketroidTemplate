package org.jetbrains.kotlin.extensions;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;

/* JADX INFO: compiled from: PreprocessedVirtualFileFactoryExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/extensions/PreprocessedFileCreator;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "getProject", "()Lcom/intellij/openapi/project/Project;", "validExts", "", "Lorg/jetbrains/kotlin/extensions/PreprocessedVirtualFileFactoryExtension;", "getValidExts", "()[Lorg/jetbrains/kotlin/extensions/PreprocessedVirtualFileFactoryExtension;", "validExts$delegate", "Lkotlin/Lazy;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "file", "createLight", "Lorg/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFile;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PreprocessedFileCreator {
    private final Project project;

    /* JADX INFO: renamed from: validExts$delegate, reason: from kotlin metadata */
    private final Lazy validExts;

    public PreprocessedFileCreator(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
        this.validExts = LazyKt.lazy(new Function0<PreprocessedVirtualFileFactoryExtension[]>() { // from class: org.jetbrains.kotlin.extensions.PreprocessedFileCreator$validExts$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PreprocessedVirtualFileFactoryExtension[] invoke() {
                List<PreprocessedVirtualFileFactoryExtension> instances = PreprocessedVirtualFileFactoryExtension.INSTANCE.getInstances(this.this$0.getProject());
                ArrayList arrayList = new ArrayList();
                for (Object obj : instances) {
                    if (!((PreprocessedVirtualFileFactoryExtension) obj).isPassThrough()) {
                        arrayList.add(obj);
                    }
                }
                return (PreprocessedVirtualFileFactoryExtension[]) arrayList.toArray(new PreprocessedVirtualFileFactoryExtension[0]);
            }
        });
    }

    public final Project getProject() {
        return this.project;
    }

    private final PreprocessedVirtualFileFactoryExtension[] getValidExts() {
        return (PreprocessedVirtualFileFactoryExtension[]) this.validExts.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c  */
    /* JADX WARN: Code duplicated, block: B:14:? A[RETURN, SYNTHETIC] */
    public final VirtualFile create(VirtualFile file) {
        VirtualFile virtualFileCreatePreprocessedFile;
        Intrinsics.checkNotNullParameter(file, "file");
        for (PreprocessedVirtualFileFactoryExtension preprocessedVirtualFileFactoryExtension : getValidExts()) {
            virtualFileCreatePreprocessedFile = preprocessedVirtualFileFactoryExtension.createPreprocessedFile(file);
            if (virtualFileCreatePreprocessedFile != null) {
                if (virtualFileCreatePreprocessedFile == null) {
                    return file;
                }
                return virtualFileCreatePreprocessedFile;
            }
        }
        virtualFileCreatePreprocessedFile = null;
        if (virtualFileCreatePreprocessedFile == null) {
            return file;
        }
        return virtualFileCreatePreprocessedFile;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c  */
    /* JADX WARN: Code duplicated, block: B:14:? A[RETURN, SYNTHETIC] */
    public final LightVirtualFile createLight(LightVirtualFile file) {
        LightVirtualFile lightVirtualFileCreatePreprocessedLightFile;
        Intrinsics.checkNotNullParameter(file, "file");
        for (PreprocessedVirtualFileFactoryExtension preprocessedVirtualFileFactoryExtension : getValidExts()) {
            lightVirtualFileCreatePreprocessedLightFile = preprocessedVirtualFileFactoryExtension.createPreprocessedLightFile(file);
            if (lightVirtualFileCreatePreprocessedLightFile != null) {
                if (lightVirtualFileCreatePreprocessedLightFile == null) {
                    return file;
                }
                return lightVirtualFileCreatePreprocessedLightFile;
            }
        }
        lightVirtualFileCreatePreprocessedLightFile = null;
        if (lightVirtualFileCreatePreprocessedLightFile == null) {
            return file;
        }
        return lightVirtualFileCreatePreprocessedLightFile;
    }
}
