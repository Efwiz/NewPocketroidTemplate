package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirPartialModifierRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirPartialModifierRenderer;", "Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "()V", "renderModifiers", "", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "memberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPartialModifierRenderer extends FirModifierRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.FirModifierRenderer
    public void renderModifiers(FirBackingField backingField) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirModifierRenderer
    public void renderModifiers(FirPropertyAccessor propertyAccessor) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirModifierRenderer
    public void renderModifiers(FirConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirConstructor firConstructor = constructor;
        if (firConstructor.getStatus().isExpect()) {
            renderModifier("expect");
        }
        if (firConstructor.getStatus().isActual()) {
            renderModifier("actual");
        }
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirModifierRenderer
    public void renderModifiers(FirAnonymousFunction anonymousFunction) {
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        if (anonymousFunction.getStatus().isSuspend()) {
            renderModifier("suspend");
        }
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirModifierRenderer
    public void renderModifiers(FirMemberDeclaration memberDeclaration) {
        Intrinsics.checkNotNullParameter(memberDeclaration, "memberDeclaration");
        if (memberDeclaration.getStatus().isExpect()) {
            renderModifier("expect");
        }
        if (memberDeclaration.getStatus().isActual()) {
            renderModifier("actual");
        }
        if (memberDeclaration.getStatus().isStatic()) {
            renderModifier("static");
        }
        if (memberDeclaration.getStatus().isInner()) {
            renderModifier("inner");
        }
        if (memberDeclaration.getStatus().isCompanion()) {
            renderModifier("companion");
        }
        if (memberDeclaration.getStatus().isData()) {
            renderModifier("data");
        }
        boolean z = (memberDeclaration instanceof FirRegularClass) && ((FirRegularClass) memberDeclaration).getClassKind() == ClassKind.INTERFACE && (memberDeclaration.getOrigin() instanceof FirDeclarationOrigin.Java);
        if (memberDeclaration.getStatus().isFun() && !z) {
            renderModifier("fun");
        }
        if (memberDeclaration.getStatus().isSuspend()) {
            renderModifier("suspend");
        }
    }
}
