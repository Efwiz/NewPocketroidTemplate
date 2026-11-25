package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;

/* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000!\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\n\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0013\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0005¢\u0006\u0002\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data", "", "currentClass", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;", "isInInlineFunction", "", "(Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;Z)V", "getCurrentClass", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$ScopeWithCounter;", "()Z", "withCurrentClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data;", "withInline", "isInline", "(Z)Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data {
    private final LocalDeclarationsLowering.ScopeWithCounter currentClass;
    private final boolean isInInlineFunction;

    public LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data(LocalDeclarationsLowering.ScopeWithCounter scopeWithCounter, boolean z) {
        this.currentClass = scopeWithCounter;
        this.isInInlineFunction = z;
    }

    public final LocalDeclarationsLowering.ScopeWithCounter getCurrentClass() {
        return this.currentClass;
    }

    /* JADX INFO: renamed from: isInInlineFunction, reason: from getter */
    public final boolean getIsInInlineFunction() {
        return this.isInInlineFunction;
    }

    public final LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data withCurrentClass(IrClass currentClass) {
        Intrinsics.checkNotNullParameter(currentClass, "currentClass");
        return new LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data(new LocalDeclarationsLowering.ScopeWithCounter(currentClass), this.isInInlineFunction);
    }

    public final LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data withInline(boolean isInline) {
        return (!isInline || this.isInInlineFunction) ? this : new LocalDeclarationsLowering$LocalDeclarationsTransformer$collectLocalDeclarations$Data(this.currentClass, true);
    }
}
