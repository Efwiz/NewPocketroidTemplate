package org.jetbrains.kotlin.js.translate.general;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;

/* JADX INFO: compiled from: AstGenerationResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/DeserializedFileTranslationResult;", "Lorg/jetbrains/kotlin/js/translate/general/FileTranslationResult;", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "inlineFunctionTags", "", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;Ljava/util/Set;)V", "getFragment", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "getInlineFunctionTags", "()Ljava/util/Set;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializedFileTranslationResult extends FileTranslationResult {
    private final JsProgramFragment fragment;
    private final Set<String> inlineFunctionTags;

    @Override // org.jetbrains.kotlin.js.translate.general.FileTranslationResult
    public JsProgramFragment getFragment() {
        return this.fragment;
    }

    @Override // org.jetbrains.kotlin.js.translate.general.FileTranslationResult
    public Set<String> getInlineFunctionTags() {
        return this.inlineFunctionTags;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeserializedFileTranslationResult(JsProgramFragment fragment, Set<String> inlineFunctionTags) {
        super(null);
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(inlineFunctionTags, "inlineFunctionTags");
        this.fragment = fragment;
        this.inlineFunctionTags = inlineFunctionTags;
    }
}
