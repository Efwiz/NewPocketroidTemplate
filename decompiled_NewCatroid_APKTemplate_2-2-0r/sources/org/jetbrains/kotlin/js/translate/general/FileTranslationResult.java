package org.jetbrains.kotlin.js.translate.general;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;

/* JADX INFO: compiled from: AstGenerationResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0001\u0002\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/FileTranslationResult;", "", "()V", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "getFragment", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "inlineFunctionTags", "", "", "getInlineFunctionTags", "()Ljava/util/Set;", "Lorg/jetbrains/kotlin/js/translate/general/DeserializedFileTranslationResult;", "Lorg/jetbrains/kotlin/js/translate/general/SourceFileTranslationResult;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FileTranslationResult {
    public /* synthetic */ FileTranslationResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract JsProgramFragment getFragment();

    public abstract Set<String> getInlineFunctionTags();

    private FileTranslationResult() {
    }
}
