package org.jetbrains.kotlin.extensions;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;

/* JADX INFO: compiled from: PreprocessedVirtualFileFactoryExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000 \t2\u00020\u0001:\u0001\tJ\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H&J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0004\u001a\u0004\u0018\u00010\u0006H&J\b\u0010\u0007\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/PreprocessedVirtualFileFactoryExtension;", "", "createPreprocessedFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "file", "createPreprocessedLightFile", "Lorg/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFile;", "isPassThrough", "", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PreprocessedVirtualFileFactoryExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    VirtualFile createPreprocessedFile(VirtualFile file);

    LightVirtualFile createPreprocessedLightFile(LightVirtualFile file);

    boolean isPassThrough();

    /* JADX INFO: compiled from: PreprocessedVirtualFileFactoryExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/extensions/PreprocessedVirtualFileFactoryExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/extensions/PreprocessedVirtualFileFactoryExtension;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<PreprocessedVirtualFileFactoryExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.preprocessedVirtualFileFactoryExtension", PreprocessedVirtualFileFactoryExtension.class);
        }
    }
}
