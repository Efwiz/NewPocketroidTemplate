package org.jetbrains.kotlin.ir.backend.js.ic;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfig;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: HashCalculatorForIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u001b\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHasher;", "", "()V", "hashCalculator", "Lorg/jetbrains/kotlin/ir/backend/js/ic/HashCalculatorForIC;", "calculateConfigHash", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "config", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "calculateConfigHash-TVZkk9w", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "calculateIrAnnotationContainerHash", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "calculateIrAnnotationContainerHash-TVZkk9w", "(Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "calculateIrFunctionHash", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "calculateIrFunctionHash-TVZkk9w", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "calculateIrSymbolHash", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "calculateIrSymbolHash-TVZkk9w", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ICHasher {
    private final HashCalculatorForIC hashCalculator = new HashCalculatorForIC();

    /* JADX INFO: renamed from: calculateConfigHash-TVZkk9w, reason: not valid java name */
    public final Hash128Bits m7117calculateConfigHashTVZkk9w(CompilerConfiguration config) {
        Intrinsics.checkNotNullParameter(config, "config");
        HashCalculatorForIC hashCalculatorForIC = this.hashCalculator;
        String VERSION = KotlinCompilerVersion.VERSION;
        Intrinsics.checkNotNullExpressionValue(VERSION, "VERSION");
        hashCalculatorForIC.update(VERSION);
        this.hashCalculator.updateConfigKeys(config, CollectionsKt.listOf((Object[]) new CompilerConfigurationKey[]{JSConfigurationKeys.SOURCE_MAP, JSConfigurationKeys.META_INFO, JSConfigurationKeys.DEVELOPER_MODE, JSConfigurationKeys.USE_ES6_CLASSES, JSConfigurationKeys.GENERATE_POLYFILLS, JSConfigurationKeys.GENERATE_DTS, JSConfigurationKeys.PROPERTY_LAZY_INITIALIZATION, JSConfigurationKeys.GENERATE_INLINE_ANONYMOUS_FUNCTIONS, JSConfigurationKeys.GENERATE_STRICT_IMPLICIT_EXPORT, JSConfigurationKeys.OPTIMIZE_GENERATED_JS}), new Function1<Boolean, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.ICHasher$calculateConfigHash$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                this.this$0.hashCalculator.update(z ? 1 : 0);
            }
        });
        this.hashCalculator.updateConfigKeys(config, CollectionsKt.listOf((Object[]) new CompilerConfigurationKey[]{JSConfigurationKeys.SOURCE_MAP_EMBED_SOURCES, JSConfigurationKeys.SOURCEMAP_NAMES_POLICY, JSConfigurationKeys.MODULE_KIND, JSConfigurationKeys.ERROR_TOLERANCE_POLICY}), new Function1<Enum<?>, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.ICHasher$calculateConfigHash$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Enum<?> r1) {
                invoke2(r1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Enum<?> value) {
                Intrinsics.checkNotNullParameter(value, "value");
                this.this$0.hashCalculator.update(value.ordinal());
            }
        });
        this.hashCalculator.updateConfigKeys(config, CollectionsKt.listOf(JSConfigurationKeys.SOURCE_MAP_PREFIX), new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.ICHasher$calculateConfigHash$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String value) {
                Intrinsics.checkNotNullParameter(value, "value");
                this.this$0.hashCalculator.update(value);
            }
        });
        this.hashCalculator.updateConfigKeys(config, CollectionsKt.listOf(PartialLinkageConfig.INSTANCE.getKEY()), new Function1<PartialLinkageConfig, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.ICHasher$calculateConfigHash$4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PartialLinkageConfig partialLinkageConfig) {
                invoke2(partialLinkageConfig);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PartialLinkageConfig value) {
                Intrinsics.checkNotNullParameter(value, "value");
                this.this$0.hashCalculator.update(value.getMode().ordinal());
                this.this$0.hashCalculator.update(value.getLogLevel().ordinal());
            }
        });
        this.hashCalculator.update(CommonConfigurationKeysKt.getLanguageVersionSettings(config).toString());
        return this.hashCalculator.m7106finalizeAndGetHashYddAqs();
    }

    /* JADX INFO: renamed from: calculateIrFunctionHash-TVZkk9w, reason: not valid java name */
    public final Hash128Bits m7119calculateIrFunctionHashTVZkk9w(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.hashCalculator.update(function);
        return this.hashCalculator.m7106finalizeAndGetHashYddAqs();
    }

    /* JADX INFO: renamed from: calculateIrAnnotationContainerHash-TVZkk9w, reason: not valid java name */
    public final Hash128Bits m7118calculateIrAnnotationContainerHashTVZkk9w(IrAnnotationContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        this.hashCalculator.updateAnnotationContainer(container);
        return this.hashCalculator.m7106finalizeAndGetHashYddAqs();
    }

    /* JADX INFO: renamed from: calculateIrSymbolHash-TVZkk9w, reason: not valid java name */
    public final Hash128Bits m7120calculateIrSymbolHashTVZkk9w(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.hashCalculator.updateSymbol(symbol);
        return this.hashCalculator.m7106finalizeAndGetHashYddAqs();
    }
}
