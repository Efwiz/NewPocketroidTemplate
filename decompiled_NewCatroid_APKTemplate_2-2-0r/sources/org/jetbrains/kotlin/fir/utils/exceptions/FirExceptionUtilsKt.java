package org.jetbrains.kotlin.fir.utils.exceptions;

import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirElementWithResolveState;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.renderer.ConeTypeRendererForDebugging;
import org.jetbrains.kotlin.fir.renderer.FirDeclarationRendererWithAttributes;
import org.jetbrains.kotlin.fir.renderer.FirFileAnnotationsContainerRenderer;
import org.jetbrains.kotlin.fir.renderer.FirRenderer;
import org.jetbrains.kotlin.fir.renderer.FirResolvePhaseRenderer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: firExceptionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a\u001a\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t\u001a\u001c\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a\u001e\u0010\r\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f\u001a \u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000f\u001a\u001c\u0010\u0011\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u001a\u001c\u0010\u0014\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016¨\u0006\u0017"}, d2 = {"withConeTypeEntry", "", "Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder;", "name", "", "coneType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "withFirEntry", "fir", "Lorg/jetbrains/kotlin/fir/FirElement;", "withFirLookupTagEntry", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "withFirSymbolEntry", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "withFirSymbolIdEntry", "withModuleDataEntry", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "withSourceEntry", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirExceptionUtilsKt {
    public static final void withFirEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, FirElement fir) {
        KtSourceElementKind kind;
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(fir, "fir");
        exceptionAttachmentBuilder.withEntry(name2, fir, new Function1<FirElement, String>() { // from class: org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt.withFirEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                FirResolvePhaseRenderer firResolvePhaseRenderer = new FirResolvePhaseRenderer();
                return FirRenderer.renderElementAsString$default(new FirRenderer(null, null, null, null, null, null, new FirDeclarationRendererWithAttributes(), null, null, null, null, firResolvePhaseRenderer, null, null, null, null, new FirFileAnnotationsContainerRenderer(), 63423, null), it, false, 2, null);
            }
        });
        String str = name2 + "ElementKind";
        KtSourceElement source = fir.getSource();
        exceptionAttachmentBuilder.withEntry(str, (source == null || (kind = source.getKind()) == null) ? null : Reflection.getOrCreateKotlinClass(kind.getClass()).getSimpleName());
        if (fir instanceof FirElementWithResolveState) {
            withModuleDataEntry(exceptionAttachmentBuilder, name2 + "ModuleData", ((FirElementWithResolveState) fir).getModuleData());
        }
        withSourceEntry(exceptionAttachmentBuilder, name2 + PngChunkTextVar.KEY_Source, fir.getSource());
    }

    public static final void withFirSymbolIdEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (firBasedSymbol instanceof FirClassifierSymbol) {
            withFirLookupTagEntry(exceptionAttachmentBuilder, name2, ((FirClassifierSymbol) firBasedSymbol).getLookupTag());
        } else if (firBasedSymbol instanceof FirCallableSymbol) {
            exceptionAttachmentBuilder.withEntry(name2, ((FirCallableSymbol) firBasedSymbol).getCallableId().toString());
        } else {
            exceptionAttachmentBuilder.withEntry(name2, String.valueOf(firBasedSymbol));
        }
    }

    public static final void withFirLookupTagEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, ConeClassifierLookupTag coneClassifierLookupTag) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        exceptionAttachmentBuilder.withEntry(name2, coneClassifierLookupTag, new Function1<ConeClassifierLookupTag, String>() { // from class: org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt.withFirLookupTagEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ConeClassifierLookupTag tag) {
                Intrinsics.checkNotNullParameter(tag, "tag");
                if (tag instanceof ConeClassLikeLookupTag) {
                    String strAsString = ((ConeClassLikeLookupTag) tag).getClassId().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    return strAsString;
                }
                String strAsString2 = tag.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                return strAsString2;
            }
        });
    }

    public static final void withSourceEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        exceptionAttachmentBuilder.withEntry(name2, ktSourceElement, new Function1<KtSourceElement, String>() { // from class: org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt.withSourceEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(KtSourceElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getElementTextInContextForDebug();
            }
        });
    }

    public static final void withModuleDataEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        exceptionAttachmentBuilder.withEntry(name2, firModuleData, new Function1<FirModuleData, String>() { // from class: org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt.withModuleDataEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(FirModuleData module) {
                Intrinsics.checkNotNullParameter(module, "module");
                StringBuilder sb = new StringBuilder();
                sb.append("Name: " + module.getName() + ", ");
                sb.append("Platform: " + module.getPlatform());
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
    }

    public static final void withFirSymbolEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        withFirEntry(exceptionAttachmentBuilder, name2 + "Fir", symbol.getFir());
    }

    public static final void withConeTypeEntry(ExceptionAttachmentBuilder exceptionAttachmentBuilder, String name2, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        exceptionAttachmentBuilder.withEntry(name2, coneKotlinType, new Function1<ConeKotlinType, String>() { // from class: org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt.withConeTypeEntry.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                StringBuilder sb = new StringBuilder();
                new ConeTypeRendererForDebugging(sb).render(it);
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
    }
}
