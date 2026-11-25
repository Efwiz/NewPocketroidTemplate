package org.jetbrains.kotlin.fir.resolve;

import java.util.EnumMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;

/* JADX INFO: compiled from: FirRegularTowerDataContexts.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fBc\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\fB7\b\u0002\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00030\u000e\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u0010\u001a\u00020\u000f¢\u0006\u0002\u0010\u0011J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0003J\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0003J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u000fR\u0011\u0010\u0010\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00030\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts;", "", "regular", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "forClassHeaderAnnotations", "forNestedClasses", "forCompanionObject", "forConstructorHeaders", "forEnumEntries", "primaryConstructorPureParametersScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "primaryConstructorAllParametersScope", "(Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;)V", "modeMap", "Ljava/util/EnumMap;", "Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;", "activeMode", "(Ljava/util/EnumMap;Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;)V", "getActiveMode", "()Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;", "currentContext", "getCurrentContext", "()Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "getPrimaryConstructorAllParametersScope", "()Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "getPrimaryConstructorPureParametersScope", "replaceAndSetActiveRegularContext", "newContext", "replaceCurrentlyActiveContext", "replaceTowerDataMode", "newMode", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRegularTowerDataContexts {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirTowerDataMode activeMode;
    private final EnumMap<FirTowerDataMode, FirTowerDataContext> modeMap;
    private final FirLocalScope primaryConstructorAllParametersScope;
    private final FirLocalScope primaryConstructorPureParametersScope;

    private FirRegularTowerDataContexts(EnumMap<FirTowerDataMode, FirTowerDataContext> enumMap, FirLocalScope firLocalScope, FirLocalScope firLocalScope2, FirTowerDataMode firTowerDataMode) {
        this.modeMap = enumMap;
        this.primaryConstructorPureParametersScope = firLocalScope;
        this.primaryConstructorAllParametersScope = firLocalScope2;
        this.activeMode = firTowerDataMode;
    }

    public final FirLocalScope getPrimaryConstructorPureParametersScope() {
        return this.primaryConstructorPureParametersScope;
    }

    public final FirLocalScope getPrimaryConstructorAllParametersScope() {
        return this.primaryConstructorAllParametersScope;
    }

    public final FirTowerDataMode getActiveMode() {
        return this.activeMode;
    }

    public /* synthetic */ FirRegularTowerDataContexts(FirTowerDataContext firTowerDataContext, FirTowerDataContext firTowerDataContext2, FirTowerDataContext firTowerDataContext3, FirTowerDataContext firTowerDataContext4, FirTowerDataContext firTowerDataContext5, FirTowerDataContext firTowerDataContext6, FirLocalScope firLocalScope, FirLocalScope firLocalScope2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firTowerDataContext, (i & 2) != 0 ? null : firTowerDataContext2, (i & 4) != 0 ? null : firTowerDataContext3, (i & 8) != 0 ? null : firTowerDataContext4, (i & 16) != 0 ? null : firTowerDataContext5, (i & 32) != 0 ? null : firTowerDataContext6, (i & 64) != 0 ? null : firLocalScope, (i & 128) == 0 ? firLocalScope2 : null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirRegularTowerDataContexts(FirTowerDataContext regular, FirTowerDataContext firTowerDataContext, FirTowerDataContext firTowerDataContext2, FirTowerDataContext firTowerDataContext3, FirTowerDataContext firTowerDataContext4, FirTowerDataContext firTowerDataContext5, FirLocalScope firLocalScope, FirLocalScope firLocalScope2) {
        this(INSTANCE.enumMap(regular, firTowerDataContext, firTowerDataContext2, firTowerDataContext3, firTowerDataContext4, firTowerDataContext5), firLocalScope, firLocalScope2, FirTowerDataMode.REGULAR);
        Intrinsics.checkNotNullParameter(regular, "regular");
    }

    public final FirTowerDataContext getCurrentContext() {
        return this.modeMap.get(this.activeMode);
    }

    public final FirRegularTowerDataContexts replaceCurrentlyActiveContext(FirTowerDataContext newContext) {
        Intrinsics.checkNotNullParameter(newContext, "newContext");
        EnumMap enumMap = new EnumMap(FirTowerDataMode.class);
        enumMap.putAll(this.modeMap);
        enumMap.put(this.activeMode, newContext);
        return new FirRegularTowerDataContexts(enumMap, this.primaryConstructorPureParametersScope, this.primaryConstructorAllParametersScope, this.activeMode);
    }

    public final FirRegularTowerDataContexts replaceTowerDataMode(FirTowerDataMode newMode) {
        Intrinsics.checkNotNullParameter(newMode, "newMode");
        return newMode == this.activeMode ? this : new FirRegularTowerDataContexts(this.modeMap, this.primaryConstructorPureParametersScope, this.primaryConstructorAllParametersScope, newMode);
    }

    public final FirRegularTowerDataContexts replaceAndSetActiveRegularContext(FirTowerDataContext newContext) {
        Intrinsics.checkNotNullParameter(newContext, "newContext");
        EnumMap enumMap = new EnumMap(FirTowerDataMode.class);
        enumMap.putAll(this.modeMap);
        enumMap.put(FirTowerDataMode.REGULAR, newContext);
        return new FirRegularTowerDataContexts(enumMap, this.primaryConstructorPureParametersScope, this.primaryConstructorAllParametersScope, FirTowerDataMode.REGULAR);
    }

    /* JADX INFO: compiled from: FirRegularTowerDataContexts.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JN\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006H\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirRegularTowerDataContexts$Companion;", "", "()V", "enumMap", "Ljava/util/EnumMap;", "Lorg/jetbrains/kotlin/fir/resolve/FirTowerDataMode;", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "regular", "forClassHeaderAnnotations", "forNestedClasses", "forCompanionObject", "forConstructorHeaders", "forEnumEntries", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final EnumMap<FirTowerDataMode, FirTowerDataContext> enumMap(FirTowerDataContext regular, FirTowerDataContext forClassHeaderAnnotations, FirTowerDataContext forNestedClasses, FirTowerDataContext forCompanionObject, FirTowerDataContext forConstructorHeaders, FirTowerDataContext forEnumEntries) {
            EnumMap<FirTowerDataMode, FirTowerDataContext> enumMap = new EnumMap<>(FirTowerDataMode.class);
            EnumMap<FirTowerDataMode, FirTowerDataContext> enumMap2 = enumMap;
            enumMap2.put(FirTowerDataMode.REGULAR, regular);
            enumMap2.put(FirTowerDataMode.CLASS_HEADER_ANNOTATIONS, forClassHeaderAnnotations);
            enumMap2.put(FirTowerDataMode.NESTED_CLASS, forNestedClasses);
            enumMap2.put(FirTowerDataMode.COMPANION_OBJECT, forCompanionObject);
            enumMap2.put(FirTowerDataMode.CONSTRUCTOR_HEADER, forConstructorHeaders);
            enumMap2.put(FirTowerDataMode.ENUM_ENTRY, forEnumEntries);
            return enumMap;
        }
    }
}
