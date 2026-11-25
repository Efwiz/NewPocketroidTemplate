package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.backend.js.utils.serialization.JsIrAstDeserializerKt;

/* JADX INFO: compiled from: ModuleArtifact.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0006\u0010\f\u001a\u00020\rJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "", "srcFilePath", "", "fragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "astArtifact", "Ljava/io/File;", "(Ljava/lang/String;Ljava/util/List;Ljava/io/File;)V", "getSrcFilePath", "()Ljava/lang/String;", "isModified", "", "loadJsIrFragments", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SrcFileArtifact {
    private final File astArtifact;
    private final List<JsIrProgramFragment> fragments;
    private final String srcFilePath;

    public SrcFileArtifact(String srcFilePath, List<JsIrProgramFragment> fragments, File file) {
        Intrinsics.checkNotNullParameter(srcFilePath, "srcFilePath");
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        this.srcFilePath = srcFilePath;
        this.fragments = fragments;
        this.astArtifact = file;
    }

    public /* synthetic */ SrcFileArtifact(String str, List list, File file, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i & 4) != 0 ? null : file);
    }

    public final String getSrcFilePath() {
        return this.srcFilePath;
    }

    public final List<JsIrProgramFragment> loadJsIrFragments() {
        List<JsIrProgramFragment> listDeserializeJsIrProgramFragment;
        if (!this.fragments.isEmpty()) {
            return this.fragments;
        }
        File file = this.astArtifact;
        if (file != null) {
            byte[] bytes = file.exists() ? FilesKt.readBytes(file) : null;
            if (bytes != null && (listDeserializeJsIrProgramFragment = JsIrAstDeserializerKt.deserializeJsIrProgramFragment(bytes)) != null) {
                return listDeserializeJsIrProgramFragment;
            }
        }
        return CollectionsKt.emptyList();
    }

    public final boolean isModified() {
        return !this.fragments.isEmpty();
    }
}
