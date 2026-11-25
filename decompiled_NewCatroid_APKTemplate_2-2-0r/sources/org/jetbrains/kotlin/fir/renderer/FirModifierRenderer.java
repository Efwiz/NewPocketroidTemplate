package org.jetbrains.kotlin.fir.renderer;

import io.netty.util.internal.StringUtil;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: FirModifierRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 '2\u00020\u0001:\u0001'B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH&J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020!H\u0016J\u0018\u0010\"\u001a\u00020\u0010*\u00020#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%H\u0014J\f\u0010&\u001a\u00020\u0010*\u00020\u0019H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "", "()V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "renderModifier", "", "modifier", "", "renderModifiers", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "memberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "asString", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "modalityAsString", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirModifierRenderer {
    private static final Set<Visibility> visibilitiesToRenderEffectiveSet = SetsKt.setOf((Object[]) new Visibility[]{Visibilities.Private.INSTANCE, Visibilities.PrivateToThis.INSTANCE, Visibilities.Internal.INSTANCE, Visibilities.Protected.INSTANCE, Visibilities.Public.INSTANCE, Visibilities.Local.INSTANCE});
    public FirRendererComponents components;

    public abstract void renderModifiers(FirAnonymousFunction anonymousFunction);

    public abstract void renderModifiers(FirBackingField backingField);

    public abstract void renderModifiers(FirConstructor constructor);

    public abstract void renderModifiers(FirMemberDeclaration memberDeclaration);

    public abstract void renderModifiers(FirPropertyAccessor propertyAccessor);

    public final FirRendererComponents getComponents$tree() {
        FirRendererComponents firRendererComponents = this.components;
        if (firRendererComponents != null) {
            return firRendererComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    public final void setComponents$tree(FirRendererComponents firRendererComponents) {
        Intrinsics.checkNotNullParameter(firRendererComponents, "<set-?>");
        this.components = firRendererComponents;
    }

    protected final FirPrinter getPrinter() {
        return getComponents$tree().getPrinter();
    }

    public void renderModifiers(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        if (valueParameter.getIsCrossinline()) {
            renderModifier("crossinline");
        }
        if (valueParameter.getIsNoinline()) {
            renderModifier("noinline");
        }
        if (valueParameter.getIsVararg()) {
            renderModifier("vararg");
        }
    }

    public void renderModifiers(FirTypeParameter typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        if (typeParameter.getIsReified()) {
            renderModifier("reified");
        }
    }

    public void renderModifiers(FirFunctionTypeRef functionTypeRef) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        if (functionTypeRef.getIsSuspend()) {
            renderModifier("suspend");
        }
    }

    protected void renderModifier(String modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        getPrinter().print(modifier + StringUtil.SPACE);
    }

    public static /* synthetic */ String asString$default(FirModifierRenderer firModifierRenderer, Visibility visibility, EffectiveVisibility effectiveVisibility, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: asString");
        }
        if ((i & 1) != 0) {
            effectiveVisibility = null;
        }
        return firModifierRenderer.asString(visibility, effectiveVisibility);
    }

    protected String asString(Visibility visibility, EffectiveVisibility effectiveVisibility) {
        Intrinsics.checkNotNullParameter(visibility, "<this>");
        if (Intrinsics.areEqual(visibility, Visibilities.Unknown.INSTANCE)) {
            return "public?";
        }
        String string = visibility.toString();
        if (effectiveVisibility == null) {
            return string;
        }
        Visibility visibility2 = effectiveVisibility.toVisibility();
        if (Intrinsics.areEqual(visibility2, visibility)) {
            return string;
        }
        return (!(Intrinsics.areEqual(visibility2, Visibilities.Private.INSTANCE) && Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE)) && visibilitiesToRenderEffectiveSet.contains(visibility)) ? string + '[' + effectiveVisibility.getName() + ']' : string;
    }

    protected String modalityAsString(FirMemberDeclaration firMemberDeclaration) {
        String str;
        String strName;
        String lowerCaseAsciiOnly;
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Modality modality = firMemberDeclaration.getStatus().getModality();
        if (modality != null && (strName = modality.name()) != null && (lowerCaseAsciiOnly = CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(strName)) != null) {
            return lowerCaseAsciiOnly;
        }
        if ((firMemberDeclaration instanceof FirCallableDeclaration) && firMemberDeclaration.getStatus().isOverride()) {
            str = "open?";
        } else {
            str = "final?";
        }
        return str;
    }
}
