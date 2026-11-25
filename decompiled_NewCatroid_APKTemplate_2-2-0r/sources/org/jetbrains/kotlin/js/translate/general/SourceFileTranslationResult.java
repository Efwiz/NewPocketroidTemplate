package org.jetbrains.kotlin.js.translate.general;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;

/* JADX INFO: compiled from: AstGenerationResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/general/SourceFileTranslationResult;", "Lorg/jetbrains/kotlin/js/translate/general/FileTranslationResult;", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "inlineFunctionTags", "", "", "memberScope", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;Ljava/util/Set;Ljava/util/List;)V", "getFragment", "()Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "getInlineFunctionTags", "()Ljava/util/Set;", "getMemberScope", "()Ljava/util/List;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceFileTranslationResult extends FileTranslationResult {
    private final JsProgramFragment fragment;
    private final Set<String> inlineFunctionTags;
    private final List<DeclarationDescriptor> memberScope;

    @Override // org.jetbrains.kotlin.js.translate.general.FileTranslationResult
    public JsProgramFragment getFragment() {
        return this.fragment;
    }

    @Override // org.jetbrains.kotlin.js.translate.general.FileTranslationResult
    public Set<String> getInlineFunctionTags() {
        return this.inlineFunctionTags;
    }

    public final List<DeclarationDescriptor> getMemberScope() {
        return this.memberScope;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SourceFileTranslationResult(JsProgramFragment fragment, Set<String> inlineFunctionTags, List<? extends DeclarationDescriptor> memberScope) {
        super(null);
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(inlineFunctionTags, "inlineFunctionTags");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        this.fragment = fragment;
        this.inlineFunctionTags = inlineFunctionTags;
        this.memberScope = memberScope;
    }
}
