package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.state.GenerationState;

/* JADX INFO: compiled from: InliningContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/RegeneratedClassContext;", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "parent", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "nameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "typeRemapper", "Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;", "lambdaInfo", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "callSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "transformationInfo", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "(Lorg/jetbrains/kotlin/codegen/inline/InliningContext;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;)V", "getCallSiteInfo", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "continuationBuilders", "", "", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "getContinuationBuilders", "()Ljava/util/Map;", "getTransformationInfo", "()Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RegeneratedClassContext extends InliningContext {
    private final InlineCallSiteInfo callSiteInfo;
    private final Map<String, ClassBuilder> continuationBuilders;
    private final TransformationInfo transformationInfo;

    @Override // org.jetbrains.kotlin.codegen.inline.InliningContext
    public InlineCallSiteInfo getCallSiteInfo() {
        return this.callSiteInfo;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.InliningContext
    public TransformationInfo getTransformationInfo() {
        return this.transformationInfo;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RegeneratedClassContext(InliningContext parent, GenerationState state, NameGenerator nameGenerator, TypeRemapper typeRemapper, LambdaInfo lambdaInfo, InlineCallSiteInfo callSiteInfo, TransformationInfo transformationInfo) {
        super(parent, state, nameGenerator, typeRemapper, lambdaInfo, true);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(nameGenerator, "nameGenerator");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        Intrinsics.checkNotNullParameter(callSiteInfo, "callSiteInfo");
        Intrinsics.checkNotNullParameter(transformationInfo, "transformationInfo");
        this.callSiteInfo = callSiteInfo;
        this.transformationInfo = transformationInfo;
        this.continuationBuilders = new HashMap();
    }

    public final Map<String, ClassBuilder> getContinuationBuilders() {
        return this.continuationBuilders;
    }
}
