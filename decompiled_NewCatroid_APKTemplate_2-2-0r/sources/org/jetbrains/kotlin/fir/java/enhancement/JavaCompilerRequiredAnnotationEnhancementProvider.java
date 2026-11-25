package org.jetbrains.kotlin.fir.java.enhancement;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.CompilerRequiredAnnotationEnhancementProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: JavaCompilerRequiredAnnotationEnhancementProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/JavaCompilerRequiredAnnotationEnhancementProvider;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationEnhancementProvider;", "()V", "enhance", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "enumSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "enumEntrySymbol", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaCompilerRequiredAnnotationEnhancementProvider extends CompilerRequiredAnnotationEnhancementProvider {
    public static final JavaCompilerRequiredAnnotationEnhancementProvider INSTANCE = new JavaCompilerRequiredAnnotationEnhancementProvider();

    private JavaCompilerRequiredAnnotationEnhancementProvider() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.CompilerRequiredAnnotationEnhancementProvider
    public FirEnumEntrySymbol enhance(FirClassSymbol<?> enumSymbol, FirEnumEntrySymbol enumEntrySymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(enumSymbol, "enumSymbol");
        Intrinsics.checkNotNullParameter(enumEntrySymbol, "enumEntrySymbol");
        Intrinsics.checkNotNullParameter(session, "session");
        E fir = enumSymbol.getFir();
        FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
        if (firRegularClass == null || !Intrinsics.areEqual(enumSymbol.getClassId(), StandardClassIds.Annotations.Java.INSTANCE.getElementType())) {
            return enumEntrySymbol;
        }
        FirVariableSymbol<?> firVariableSymbolEnhancedProperty = new FirSignatureEnhancement(firRegularClass, session, new Function1<FirSimpleFunction, List<? extends FirCallableDeclaration>>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.JavaCompilerRequiredAnnotationEnhancementProvider.enhance.1
            @Override // kotlin.jvm.functions.Function1
            public final List<FirCallableDeclaration> invoke(FirSimpleFunction $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                return CollectionsKt.emptyList();
            }
        }).enhancedProperty(enumEntrySymbol, enumEntrySymbol.getName());
        Intrinsics.checkNotNull(firVariableSymbolEnhancedProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol");
        return (FirEnumEntrySymbol) firVariableSymbolEnhancedProperty;
    }
}
