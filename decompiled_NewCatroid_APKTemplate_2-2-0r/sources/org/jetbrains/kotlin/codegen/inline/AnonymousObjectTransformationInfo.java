package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TransformationInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB_\b\u0000\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u0012\u0006\u0010\u0010\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005¢\u0006\u0002\u0010\u0014J\b\u0010.\u001a\u00020\u0005H\u0016J&\u0010/\u001a\u0006\u0012\u0002\b\u0003002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00052\b\u00104\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u00105\u001a\u00020\u00052\u0006\u00103\u001a\u00020\u0005H\u0016R \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u001d0\rX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001fR\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b%\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010#\"\u0004\b+\u0010,R\u0014\u0010\u000b\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010#¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/AnonymousObjectTransformationInfo;", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "ownerInternalName", "", "needReification", "", "alreadyRegenerated", "isStaticOrigin", "nameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "(Ljava/lang/String;ZZZLorg/jetbrains/kotlin/codegen/inline/NameGenerator;)V", "oldClassName", "functionalArguments", "", "", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "capturedOuterRegenerated", "constructorDesc", "parentNameGenerator", "capturesAnonymousObjectThatMustBeRegenerated", "(Ljava/lang/String;ZLjava/util/Map;ZZLjava/lang/String;ZLorg/jetbrains/kotlin/codegen/inline/NameGenerator;Z)V", "allRecapturedParameters", "", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "getAllRecapturedParameters", "()Ljava/util/List;", "setAllRecapturedParameters", "(Ljava/util/List;)V", "capturedLambdasToInline", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "getCapturedLambdasToInline", "()Ljava/util/Map;", "setCapturedLambdasToInline", "(Ljava/util/Map;)V", "getConstructorDesc", "()Ljava/lang/String;", "getFunctionalArguments", "getNameGenerator", "()Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "nameGenerator$delegate", "Lkotlin/Lazy;", "newConstructorDescriptor", "getNewConstructorDescriptor", "setNewConstructorDescriptor", "(Ljava/lang/String;)V", "getOldClassName", "canRemoveAfterTransformation", "createTransformer", "Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "sameModule", "continuationClassName", "shouldRegenerate", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnonymousObjectTransformationInfo implements TransformationInfo {
    public List<CapturedParamDesc> allRecapturedParameters;
    private final boolean alreadyRegenerated;
    public Map<String, ? extends LambdaInfo> capturedLambdasToInline;
    private final boolean capturedOuterRegenerated;
    private final boolean capturesAnonymousObjectThatMustBeRegenerated;
    private final String constructorDesc;
    private final Map<Integer, FunctionalArgument> functionalArguments;
    private final boolean isStaticOrigin;

    /* JADX INFO: renamed from: nameGenerator$delegate, reason: from kotlin metadata */
    private final Lazy nameGenerator;
    private final boolean needReification;
    public String newConstructorDescriptor;
    private final String oldClassName;

    /* JADX WARN: Multi-variable type inference failed */
    public AnonymousObjectTransformationInfo(String oldClassName, boolean z, Map<Integer, ? extends FunctionalArgument> functionalArguments, boolean z2, boolean z3, String str, boolean z4, final NameGenerator parentNameGenerator, boolean z5) {
        Intrinsics.checkNotNullParameter(oldClassName, "oldClassName");
        Intrinsics.checkNotNullParameter(functionalArguments, "functionalArguments");
        Intrinsics.checkNotNullParameter(parentNameGenerator, "parentNameGenerator");
        this.oldClassName = oldClassName;
        this.needReification = z;
        this.functionalArguments = functionalArguments;
        this.capturedOuterRegenerated = z2;
        this.alreadyRegenerated = z3;
        this.constructorDesc = str;
        this.isStaticOrigin = z4;
        this.capturesAnonymousObjectThatMustBeRegenerated = z5;
        this.nameGenerator = LazyKt.lazy(new Function0<NameGenerator>() { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformationInfo$nameGenerator$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final NameGenerator invoke() {
                return parentNameGenerator.subGenerator(true, null);
            }
        });
    }

    public /* synthetic */ AnonymousObjectTransformationInfo(String str, boolean z, Map map, boolean z2, boolean z3, String str2, boolean z4, NameGenerator nameGenerator, boolean z5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, map, z2, z3, str2, z4, nameGenerator, (i & 256) != 0 ? false : z5);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public String getOldClassName() {
        return this.oldClassName;
    }

    public final Map<Integer, FunctionalArgument> getFunctionalArguments() {
        return this.functionalArguments;
    }

    public final String getConstructorDesc() {
        return this.constructorDesc;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public NameGenerator getNameGenerator() {
        return (NameGenerator) this.nameGenerator.getValue();
    }

    public final String getNewConstructorDescriptor() {
        String str = this.newConstructorDescriptor;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("newConstructorDescriptor");
        return null;
    }

    public final void setNewConstructorDescriptor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.newConstructorDescriptor = str;
    }

    public final List<CapturedParamDesc> getAllRecapturedParameters() {
        List<CapturedParamDesc> list = this.allRecapturedParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("allRecapturedParameters");
        return null;
    }

    public final void setAllRecapturedParameters(List<CapturedParamDesc> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.allRecapturedParameters = list;
    }

    public final Map<String, LambdaInfo> getCapturedLambdasToInline() {
        Map map = this.capturedLambdasToInline;
        if (map != null) {
            return map;
        }
        Intrinsics.throwUninitializedPropertyAccessException("capturedLambdasToInline");
        return null;
    }

    public final void setCapturedLambdasToInline(Map<String, ? extends LambdaInfo> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.capturedLambdasToInline = map;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AnonymousObjectTransformationInfo(String ownerInternalName, boolean z, boolean z2, boolean z3, NameGenerator nameGenerator) {
        this(ownerInternalName, z, new HashMap(), false, z2, null, z3, nameGenerator, false, 256, null);
        Intrinsics.checkNotNullParameter(ownerInternalName, "ownerInternalName");
        Intrinsics.checkNotNullParameter(nameGenerator, "nameGenerator");
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public boolean shouldRegenerate(boolean sameModule) {
        if (!this.alreadyRegenerated && sameModule && !this.capturedOuterRegenerated && !this.needReification && !this.capturesAnonymousObjectThatMustBeRegenerated) {
            Collection<FunctionalArgument> collectionValues = this.functionalArguments.values();
            if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    if (((FunctionalArgument) it.next()) != NonInlineArgumentForInlineSuspendParameter.INLINE_LAMBDA_AS_VARIABLE) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public boolean canRemoveAfterTransformation() {
        return !this.isStaticOrigin;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.TransformationInfo
    public ObjectTransformer<?> createTransformer(InliningContext inliningContext, boolean sameModule, String continuationClassName) {
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        return new AnonymousObjectTransformer(this, inliningContext, sameModule, continuationClassName);
    }
}
