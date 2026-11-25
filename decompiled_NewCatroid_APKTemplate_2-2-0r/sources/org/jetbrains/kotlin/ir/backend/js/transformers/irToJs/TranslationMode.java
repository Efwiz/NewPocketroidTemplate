package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0086\u0081\u0002\u0018\u0000 \u00162\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0016B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;", "", "production", "", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "minimizedMemberNames", "(Ljava/lang/String;IZLorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;Z)V", "getGranularity", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "getMinimizedMemberNames", "()Z", "getProduction", "FULL_DEV", "FULL_PROD", "FULL_PROD_MINIMIZED_NAMES", "PER_MODULE_DEV", "PER_MODULE_PROD", "PER_MODULE_PROD_MINIMIZED_NAMES", "PER_FILE_DEV", "PER_FILE_PROD", "PER_FILE_PROD_MINIMIZED_NAMES", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TranslationMode {
    FULL_DEV(false, JsGenerationGranularity.WHOLE_PROGRAM, false),
    FULL_PROD(true, JsGenerationGranularity.WHOLE_PROGRAM, false),
    FULL_PROD_MINIMIZED_NAMES(true, JsGenerationGranularity.WHOLE_PROGRAM, true),
    PER_MODULE_DEV(false, JsGenerationGranularity.PER_MODULE, false),
    PER_MODULE_PROD(true, JsGenerationGranularity.PER_MODULE, false),
    PER_MODULE_PROD_MINIMIZED_NAMES(true, JsGenerationGranularity.PER_MODULE, true),
    PER_FILE_DEV(false, JsGenerationGranularity.PER_FILE, false),
    PER_FILE_PROD(true, JsGenerationGranularity.PER_FILE, false),
    PER_FILE_PROD_MINIMIZED_NAMES(true, JsGenerationGranularity.PER_FILE, true);

    private final JsGenerationGranularity granularity;
    private final boolean minimizedMemberNames;
    private final boolean production;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<TranslationMode> getEntries() {
        return $ENTRIES;
    }

    TranslationMode(boolean z, JsGenerationGranularity jsGenerationGranularity, boolean z2) {
        this.production = z;
        this.granularity = jsGenerationGranularity;
        this.minimizedMemberNames = z2;
    }

    public final boolean getProduction() {
        return this.production;
    }

    public final JsGenerationGranularity getGranularity() {
        return this.granularity;
    }

    public final boolean getMinimizedMemberNames() {
        return this.minimizedMemberNames;
    }

    /* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode$Companion;", "", "()V", "fromFlags", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/TranslationMode;", "production", "", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "minimizedMemberNames", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: IrModuleToJsTransformer.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[JsGenerationGranularity.values().length];
                try {
                    iArr[JsGenerationGranularity.PER_MODULE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[JsGenerationGranularity.PER_FILE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[JsGenerationGranularity.WHOLE_PROGRAM.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TranslationMode fromFlags(boolean production, JsGenerationGranularity granularity, boolean minimizedMemberNames) {
            Intrinsics.checkNotNullParameter(granularity, "granularity");
            int i = WhenMappings.$EnumSwitchMapping$0[granularity.ordinal()];
            if (i == 1) {
                if (!production) {
                    return TranslationMode.PER_MODULE_DEV;
                }
                if (minimizedMemberNames) {
                    return TranslationMode.PER_MODULE_PROD_MINIMIZED_NAMES;
                }
                return TranslationMode.PER_MODULE_PROD;
            }
            if (i == 2) {
                if (!production) {
                    return TranslationMode.PER_FILE_DEV;
                }
                if (minimizedMemberNames) {
                    return TranslationMode.PER_FILE_PROD_MINIMIZED_NAMES;
                }
                return TranslationMode.PER_FILE_PROD;
            }
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            if (!production) {
                return TranslationMode.FULL_DEV;
            }
            if (minimizedMemberNames) {
                return TranslationMode.FULL_PROD_MINIMIZED_NAMES;
            }
            return TranslationMode.FULL_PROD;
        }
    }
}
