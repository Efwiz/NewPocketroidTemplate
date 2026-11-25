package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: SamWrapperClasses.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u001c\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SamWrapperClasses;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "samInterfaceToWrapperClass", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/codegen/SamWrapperClasses$WrapperKey;", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lkotlin/collections/HashMap;", "getSamWrapperClass", "samType", "Lorg/jetbrains/kotlin/backend/common/SamType;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "expressionCodegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "contextDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isInsideInlineLambdaContext", "", "context", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "WrapperKey", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamWrapperClasses {
    private final HashMap<WrapperKey, Type> samInterfaceToWrapperClass;
    private final GenerationState state;

    public SamWrapperClasses(GenerationState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
        this.samInterfaceToWrapperClass = new HashMap<>();
    }

    /* JADX INFO: compiled from: SamWrapperClasses.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SamWrapperClasses$WrapperKey;", "", "samType", "Lorg/jetbrains/kotlin/backend/common/SamType;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "insideInline", "", "(Lorg/jetbrains/kotlin/backend/common/SamType;Lorg/jetbrains/kotlin/psi/KtFile;Z)V", "getFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getInsideInline", "()Z", "getSamType", "()Lorg/jetbrains/kotlin/backend/common/SamType;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class WrapperKey {
        private final KtFile file;
        private final boolean insideInline;
        private final SamType samType;

        public static /* synthetic */ WrapperKey copy$default(WrapperKey wrapperKey, SamType samType, KtFile ktFile, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                samType = wrapperKey.samType;
            }
            if ((i & 2) != 0) {
                ktFile = wrapperKey.file;
            }
            if ((i & 4) != 0) {
                z = wrapperKey.insideInline;
            }
            return wrapperKey.copy(samType, ktFile, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final SamType getSamType() {
            return this.samType;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtFile getFile() {
            return this.file;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getInsideInline() {
            return this.insideInline;
        }

        public final WrapperKey copy(SamType samType, KtFile file, boolean insideInline) {
            Intrinsics.checkNotNullParameter(samType, "samType");
            Intrinsics.checkNotNullParameter(file, "file");
            return new WrapperKey(samType, file, insideInline);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof WrapperKey)) {
                return false;
            }
            WrapperKey wrapperKey = (WrapperKey) other;
            return Intrinsics.areEqual(this.samType, wrapperKey.samType) && Intrinsics.areEqual(this.file, wrapperKey.file) && this.insideInline == wrapperKey.insideInline;
        }

        public int hashCode() {
            return (((this.samType.hashCode() * 31) + this.file.hashCode()) * 31) + Boolean.hashCode(this.insideInline);
        }

        public String toString() {
            return "WrapperKey(samType=" + this.samType + ", file=" + this.file + ", insideInline=" + this.insideInline + ')';
        }

        public WrapperKey(SamType samType, KtFile file, boolean z) {
            Intrinsics.checkNotNullParameter(samType, "samType");
            Intrinsics.checkNotNullParameter(file, "file");
            this.samType = samType;
            this.file = file;
            this.insideInline = z;
        }

        public final KtFile getFile() {
            return this.file;
        }

        public final boolean getInsideInline() {
            return this.insideInline;
        }

        public final SamType getSamType() {
            return this.samType;
        }
    }

    public final Type getSamWrapperClass(SamType samType, KtFile file, ExpressionCodegen expressionCodegen, CallableMemberDescriptor contextDescriptor) {
        Intrinsics.checkNotNullParameter(samType, "samType");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(expressionCodegen, "expressionCodegen");
        Intrinsics.checkNotNullParameter(contextDescriptor, "contextDescriptor");
        MethodContext methodContext = expressionCodegen.context;
        boolean zIsInPublicInlineScope = InlineUtil.isInPublicInlineScope(methodContext.getContextDescriptor());
        HashMap<WrapperKey, Type> map = this.samInterfaceToWrapperClass;
        WrapperKey wrapperKey = new WrapperKey(samType, file, zIsInPublicInlineScope);
        Type typeGenWrapper = map.get(wrapperKey);
        if (typeGenWrapper == null) {
            typeGenWrapper = new SamWrapperCodegen(this.state, samType, expressionCodegen.getParentCodegen(), methodContext, zIsInPublicInlineScope).genWrapper(file, contextDescriptor);
            Intrinsics.checkNotNullExpressionValue(typeGenWrapper, "genWrapper(...)");
            map.put(wrapperKey, typeGenWrapper);
        }
        return typeGenWrapper;
    }
}
