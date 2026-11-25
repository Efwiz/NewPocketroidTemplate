package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;

/* JADX INFO: compiled from: InliningContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b\u0012\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r¢\u0006\u0002\u0010\u000eR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/RootInliningContext;", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "nameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "sourceCompilerForInline", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "callSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "inlineMethodReifier", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;", "typeParameterMappings", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;)V", "getCallSiteInfo", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "getInlineMethodReifier", "()Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;", "getSourceCompilerForInline", "()Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RootInliningContext extends InliningContext {
    private final InlineCallSiteInfo callSiteInfo;
    private final ReifiedTypeInliner<?> inlineMethodReifier;
    private final SourceCompilerForInline sourceCompilerForInline;

    public final SourceCompilerForInline getSourceCompilerForInline() {
        return this.sourceCompilerForInline;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.InliningContext
    public InlineCallSiteInfo getCallSiteInfo() {
        return this.callSiteInfo;
    }

    public final ReifiedTypeInliner<?> getInlineMethodReifier() {
        return this.inlineMethodReifier;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RootInliningContext(GenerationState state, NameGenerator nameGenerator, SourceCompilerForInline sourceCompilerForInline, InlineCallSiteInfo callSiteInfo, ReifiedTypeInliner<?> inlineMethodReifier, TypeParameterMappings<?> typeParameterMappings) {
        super(null, state, nameGenerator, TypeRemapper.INSTANCE.createRoot(typeParameterMappings), null, false);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(nameGenerator, "nameGenerator");
        Intrinsics.checkNotNullParameter(sourceCompilerForInline, "sourceCompilerForInline");
        Intrinsics.checkNotNullParameter(callSiteInfo, "callSiteInfo");
        Intrinsics.checkNotNullParameter(inlineMethodReifier, "inlineMethodReifier");
        Intrinsics.checkNotNullParameter(typeParameterMappings, "typeParameterMappings");
        this.sourceCompilerForInline = sourceCompilerForInline;
        this.callSiteInfo = callSiteInfo;
        this.inlineMethodReifier = inlineMethodReifier;
    }
}
