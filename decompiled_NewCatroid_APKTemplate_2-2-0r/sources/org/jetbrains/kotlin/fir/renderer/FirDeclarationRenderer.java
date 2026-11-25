package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.impl.FirErrorConstructor;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: FirDeclarationRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0015\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0000¢\u0006\u0002\b\u001cJ\f\u0010\u001d\u001a\u00020\u0018*\u00020\u001aH\u0014R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "", "localVariablePrefix", "", "(Ljava/lang/String;)V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "resolvePhaseRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;", "getResolvePhaseRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;", "typeRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "getTypeRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "render", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "renderPhaseAndAttributes", "renderPhaseAndAttributes$tree", "renderDeclarationAttributes", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirDeclarationRenderer {
    public FirRendererComponents components;
    private final String localVariablePrefix;

    /* JADX WARN: Multi-variable type inference failed */
    public FirDeclarationRenderer() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    protected void renderDeclarationAttributes(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
    }

    public FirDeclarationRenderer(String localVariablePrefix) {
        Intrinsics.checkNotNullParameter(localVariablePrefix, "localVariablePrefix");
        this.localVariablePrefix = localVariablePrefix;
    }

    public /* synthetic */ FirDeclarationRenderer(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "l" : str);
    }

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

    private final FirResolvePhaseRenderer getResolvePhaseRenderer() {
        return getComponents$tree().getResolvePhaseRenderer();
    }

    private final ConeTypeRenderer getTypeRenderer() {
        return getComponents$tree().getTypeRenderer();
    }

    public final void render(FirDeclaration declaration) {
        String string;
        String str;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        renderPhaseAndAttributes$tree(declaration);
        if (declaration instanceof FirConstructor) {
            ConeSimpleKotlinType dispatchReceiverType = ((FirConstructor) declaration).getDispatchReceiverType();
            if (dispatchReceiverType != null) {
                getTypeRenderer().render((ConeKotlinType) dispatchReceiverType);
                getPrinter().print(".");
            }
            if (declaration instanceof FirErrorConstructor) {
                getPrinter().print("error_");
            }
            getPrinter().print(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);
            return;
        }
        FirPrinter printer = getPrinter();
        if (declaration instanceof FirRegularClass) {
            string = StringsKt.replace$default(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(((FirRegularClass) declaration).getClassKind().name()), "_", " ", false, 4, (Object) null);
        } else if (declaration instanceof FirTypeAlias) {
            string = "typealias";
        } else {
            String str2 = "";
            if (declaration instanceof FirAnonymousFunction) {
                StringBuilder sb = new StringBuilder();
                FirLabel label = ((FirAnonymousFunction) declaration).getLabel();
                if (label != null && (str = label.getName() + '@') != null) {
                    str2 = str;
                }
                string = sb.append(str2).append("fun").toString();
            } else if (declaration instanceof FirSimpleFunction) {
                string = "fun";
            } else if (declaration instanceof FirProperty) {
                FirProperty firProperty = (FirProperty) declaration;
                if (Intrinsics.areEqual((Object) ClassMembersKt.isCatchParameter(firProperty), (Object) true)) {
                    string = "";
                } else {
                    string = (firProperty.getIsLocal() ? this.localVariablePrefix : "") + (firProperty.getIsVal() ? "val" : PsiKeyword.VAR);
                }
            } else if (declaration instanceof FirPropertyAccessor) {
                string = ((FirPropertyAccessor) declaration).getIsGetter() ? "get" : FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
            } else if (declaration instanceof FirField) {
                string = "field";
            } else {
                string = declaration instanceof FirEnumEntry ? "enum entry" : "unknown";
            }
        }
        printer.print(string);
    }

    public final void renderPhaseAndAttributes$tree(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirResolvePhaseRenderer resolvePhaseRenderer = getResolvePhaseRenderer();
        if (resolvePhaseRenderer != null) {
            resolvePhaseRenderer.render(declaration);
        }
        renderDeclarationAttributes(declaration);
    }
}
