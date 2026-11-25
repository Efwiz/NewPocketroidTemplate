package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.HashSet;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;

/* JADX INFO: compiled from: InliningContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\b\u0016\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u000e\u00104\u001a\u00020\f2\u0006\u00105\u001a\u00020$J\u000e\u00106\u001a\u0002072\u0006\u00105\u001a\u00020$J>\u00108\u001a\u00020\u00002\u0006\u00109\u001a\u00020\u00062\u0016\b\u0002\u0010:\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0006\u0012\u0004\u0018\u00010$0;2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007J\u000e\u0010<\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ4\u0010=\u001a\u00020>2\u0006\u00109\u001a\u00020\u00062\u0014\u0010?\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0006\u0012\u0004\u0018\u00010$0@2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020/R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0013\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0017R\u0011\u0010\u001a\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0013R\u0011\u0010\u001b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001e\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020$0#j\b\u0012\u0004\u0012\u00020$`%X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010&\u001a\u00020'8F¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b+\u0010\u0013R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0016\u0010.\u001a\u0004\u0018\u00010/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b2\u00103¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "", "parent", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "nameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "typeRemapper", "Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;", "lambdaInfo", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "classRegeneration", "", "(Lorg/jetbrains/kotlin/codegen/inline/InliningContext;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;Z)V", "callSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "getCallSiteInfo", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "getClassRegeneration", "()Z", "generateAssertField", "getGenerateAssertField", "setGenerateAssertField", "(Z)V", "isContinuation", "setContinuation", "isInliningLambda", "isRoot", "getLambdaInfo", "()Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "getNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "getParent", "()Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "regeneratedAnonymousObjects", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", "root", "Lorg/jetbrains/kotlin/codegen/inline/RootInliningContext;", "getRoot", "()Lorg/jetbrains/kotlin/codegen/inline/RootInliningContext;", "shouldReifyTypeParametersInObjects", "getShouldReifyTypeParametersInObjects", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "transformationInfo", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "getTransformationInfo", "()Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "getTypeRemapper", "()Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;", "isRegeneratedAnonymousObject", "internalName", "recordRegeneratedAnonymousObject", "", "subInline", "generator", "additionalTypeMappings", "", "subInlineLambda", "subInlineWithClassRegeneration", "Lorg/jetbrains/kotlin/codegen/inline/RegeneratedClassContext;", "newTypeMappings", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class InliningContext {
    private final boolean classRegeneration;
    private boolean generateAssertField;
    private boolean isContinuation;
    private final LambdaInfo lambdaInfo;
    private final NameGenerator nameGenerator;
    private final InliningContext parent;
    private final HashSet<String> regeneratedAnonymousObjects;
    private final GenerationState state;
    private final TypeRemapper typeRemapper;

    public TransformationInfo getTransformationInfo() {
        return null;
    }

    public final InliningContext subInline(NameGenerator generator) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        return subInline$default(this, generator, null, null, false, 14, null);
    }

    public final InliningContext subInline(NameGenerator generator, Map<String, String> additionalTypeMappings) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(additionalTypeMappings, "additionalTypeMappings");
        return subInline$default(this, generator, additionalTypeMappings, null, false, 12, null);
    }

    public final InliningContext subInline(NameGenerator generator, Map<String, String> additionalTypeMappings, LambdaInfo lambdaInfo) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(additionalTypeMappings, "additionalTypeMappings");
        return subInline$default(this, generator, additionalTypeMappings, lambdaInfo, false, 8, null);
    }

    public InliningContext(InliningContext inliningContext, GenerationState state, NameGenerator nameGenerator, TypeRemapper typeRemapper, LambdaInfo lambdaInfo, boolean z) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(nameGenerator, "nameGenerator");
        Intrinsics.checkNotNullParameter(typeRemapper, "typeRemapper");
        this.parent = inliningContext;
        this.state = state;
        this.nameGenerator = nameGenerator;
        this.typeRemapper = typeRemapper;
        this.lambdaInfo = lambdaInfo;
        this.classRegeneration = z;
        this.regeneratedAnonymousObjects = new HashSet<>();
    }

    public final InliningContext getParent() {
        return this.parent;
    }

    public final GenerationState getState() {
        return this.state;
    }

    public final NameGenerator getNameGenerator() {
        return this.nameGenerator;
    }

    public final TypeRemapper getTypeRemapper() {
        return this.typeRemapper;
    }

    public final LambdaInfo getLambdaInfo() {
        return this.lambdaInfo;
    }

    public final boolean getClassRegeneration() {
        return this.classRegeneration;
    }

    public final boolean isInliningLambda() {
        return this.lambdaInfo != null;
    }

    public final boolean getShouldReifyTypeParametersInObjects() {
        LambdaInfo lambdaInfo = this.lambdaInfo;
        return lambdaInfo == null || (lambdaInfo instanceof DefaultLambda);
    }

    public final boolean getGenerateAssertField() {
        return this.generateAssertField;
    }

    public final void setGenerateAssertField(boolean z) {
        this.generateAssertField = z;
    }

    /* JADX INFO: renamed from: isContinuation, reason: from getter */
    public final boolean getIsContinuation() {
        return this.isContinuation;
    }

    public final void setContinuation(boolean z) {
        this.isContinuation = z;
    }

    public final boolean isRoot() {
        return this.parent == null;
    }

    public final RootInliningContext getRoot() {
        if (isRoot()) {
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.RootInliningContext");
            return (RootInliningContext) this;
        }
        InliningContext inliningContext = this.parent;
        Intrinsics.checkNotNull(inliningContext);
        return inliningContext.getRoot();
    }

    public final boolean isRegeneratedAnonymousObject(String internalName) {
        InliningContext inliningContext;
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return this.regeneratedAnonymousObjects.contains(internalName) || ((inliningContext = this.parent) != null && inliningContext.isRegeneratedAnonymousObject(internalName));
    }

    public final void recordRegeneratedAnonymousObject(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        this.regeneratedAnonymousObjects.add(internalName);
    }

    public final InliningContext subInlineLambda(LambdaInfo lambdaInfo) {
        Intrinsics.checkNotNullParameter(lambdaInfo, "lambdaInfo");
        NameGenerator nameGeneratorSubGenerator = this.nameGenerator.subGenerator("lambda");
        Intrinsics.checkNotNullExpressionValue(nameGeneratorSubGenerator, "subGenerator(...)");
        return subInline(nameGeneratorSubGenerator, MapsKt.hashMapOf(TuplesKt.to(lambdaInfo.getLambdaClassType().getInternalName(), null)), lambdaInfo, this.state.getIsIrBackend() ? false : this.classRegeneration);
    }

    public final RegeneratedClassContext subInlineWithClassRegeneration(NameGenerator generator, Map<String, String> newTypeMappings, InlineCallSiteInfo callSiteInfo, TransformationInfo transformationInfo) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(newTypeMappings, "newTypeMappings");
        Intrinsics.checkNotNullParameter(callSiteInfo, "callSiteInfo");
        Intrinsics.checkNotNullParameter(transformationInfo, "transformationInfo");
        return new RegeneratedClassContext(this, this.state, generator, TypeRemapper.Companion.createFrom$default(TypeRemapper.INSTANCE, this.typeRemapper, newTypeMappings, false, 4, null), this.lambdaInfo, callSiteInfo, transformationInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InliningContext subInline$default(InliningContext inliningContext, NameGenerator nameGenerator, Map map, LambdaInfo lambdaInfo, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: subInline");
        }
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 4) != 0) {
            lambdaInfo = inliningContext.lambdaInfo;
        }
        if ((i & 8) != 0) {
            z = inliningContext.classRegeneration;
        }
        return inliningContext.subInline(nameGenerator, map, lambdaInfo, z);
    }

    public final InliningContext subInline(NameGenerator generator, Map<String, String> additionalTypeMappings, LambdaInfo lambdaInfo, boolean classRegeneration) {
        Intrinsics.checkNotNullParameter(generator, "generator");
        Intrinsics.checkNotNullParameter(additionalTypeMappings, "additionalTypeMappings");
        return new InliningContext(this, this.state, generator, TypeRemapper.INSTANCE.createFrom(this.typeRemapper, additionalTypeMappings, (lambdaInfo != null) && !isInliningLambda()), lambdaInfo, classRegeneration);
    }

    public InlineCallSiteInfo getCallSiteInfo() {
        InliningContext inliningContext = this.parent;
        Intrinsics.checkNotNull(inliningContext);
        return inliningContext.getCallSiteInfo();
    }
}
