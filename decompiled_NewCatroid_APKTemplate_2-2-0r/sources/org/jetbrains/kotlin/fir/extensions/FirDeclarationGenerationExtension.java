package org.jetbrains.kotlin.fir.extensions;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.caches.FirLazyValue;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 62\u00020\u0001:\u000267B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\n\u0010\u001c\u001a\u00060\u001dj\u0002`\u001eH\u0016J&\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u001a2\u0006\u0010!\u001a\u00020\u00102\u000e\u0010\u001c\u001a\n\u0018\u00010\u001dj\u0004\u0018\u0001`\u001eH\u0016J.\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010#2\n\u0010$\u001a\u0006\u0012\u0002\b\u00030%2\u0006\u0010\t\u001a\u00020&2\n\u0010\u001c\u001a\u00060'j\u0002`(H\u0016J&\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u001a2\u0006\u0010!\u001a\u00020\u00102\u000e\u0010\u001c\u001a\n\u0018\u00010\u001dj\u0004\u0018\u0001`\u001eH\u0016J\u0016\u0010+\u001a\b\u0012\u0002\b\u0003\u0018\u00010#2\u0006\u0010,\u001a\u00020\u0016H\u0016J&\u0010-\u001a\b\u0012\u0004\u0012\u00020&0\u000f2\n\u0010.\u001a\u0006\u0012\u0002\b\u00030%2\n\u0010\u001c\u001a\u00060\u001dj\u0002`\u001eH\u0016J&\u0010/\u001a\b\u0012\u0004\u0012\u00020&0\u000f2\n\u0010.\u001a\u0006\u0012\u0002\b\u00030%2\n\u0010\u001c\u001a\u00060'j\u0002`(H\u0016J\u000e\u00100\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u000e\u00101\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fH\u0016J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR(\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R(\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u000f0\u000e8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u0012\u001a\u0004\b\u0018\u0010\u0014¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "topLevelCallableIdsCache", "Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "", "Lorg/jetbrains/kotlin/name/CallableId;", "getTopLevelCallableIdsCache$annotations", "()V", "getTopLevelCallableIdsCache", "()Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "topLevelClassIdsCache", "Lorg/jetbrains/kotlin/name/ClassId;", "getTopLevelClassIdsCache$annotations", "getTopLevelClassIdsCache", "generateConstructors", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "context", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;", "Lorg/jetbrains/kotlin/fir/extensions/MemberGenerationContext;", "generateFunctions", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "callableId", "generateNestedClassLikeDeclaration", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;", "Lorg/jetbrains/kotlin/fir/extensions/NestedClassGenerationContext;", "generateProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "generateTopLevelClassLikeDeclaration", "classId", "getCallableNamesForClass", "classSymbol", "getNestedClassifiersNames", "getTopLevelCallableIds", "getTopLevelClassIds", "hasPackage", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Companion", "Factory", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDeclarationGenerationExtension extends FirExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirExtensionPointName NAME = new FirExtensionPointName("ExistingClassModification");
    private final KClass<? extends FirExtension> extensionType;
    private final FirLazyValue<Set<CallableId>> topLevelCallableIdsCache;
    private final FirLazyValue<Set<ClassId>> topLevelClassIdsCache;

    /* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory extends FirExtension.Factory<FirDeclarationGenerationExtension> {
    }

    @FirExtensionApiInternals
    public static /* synthetic */ void getTopLevelCallableIdsCache$annotations() {
    }

    @FirExtensionApiInternals
    public static /* synthetic */ void getTopLevelClassIdsCache$annotations() {
    }

    public FirClassLikeSymbol<?> generateNestedClassLikeDeclaration(FirClassSymbol<?> owner, Name name2, DeclarationGenerationContext.Nested context) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    public FirClassLikeSymbol<?> generateTopLevelClassLikeDeclaration(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return null;
    }

    public boolean hasPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return false;
    }

    /* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension$Companion;", "", "()V", "NAME", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getNAME", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionPointName getNAME() {
            return FirDeclarationGenerationExtension.NAME;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDeclarationGenerationExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.extensionType = Reflection.getOrCreateKotlinClass(FirDeclarationGenerationExtension.class);
        this.topLevelClassIdsCache = FirCachesFactoryKt.getFirCachesFactory(session).createLazyValue(new Function0<Set<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension$topLevelClassIdsCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends ClassId> invoke() {
                return this.this$0.getTopLevelClassIds();
            }
        });
        this.topLevelCallableIdsCache = FirCachesFactoryKt.getFirCachesFactory(session).createLazyValue(new Function0<Set<? extends CallableId>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension$topLevelCallableIdsCache$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends CallableId> invoke() {
                return this.this$0.getTopLevelCallableIds();
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final FirExtensionPointName getName() {
        return NAME;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final KClass<? extends FirExtension> getExtensionType() {
        return this.extensionType;
    }

    public List<FirNamedFunctionSymbol> generateFunctions(CallableId callableId, DeclarationGenerationContext.Member context) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        return CollectionsKt.emptyList();
    }

    public List<FirPropertySymbol> generateProperties(CallableId callableId, DeclarationGenerationContext.Member context) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        return CollectionsKt.emptyList();
    }

    public List<FirConstructorSymbol> generateConstructors(DeclarationGenerationContext.Member context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return CollectionsKt.emptyList();
    }

    public Set<Name> getCallableNamesForClass(FirClassSymbol<?> classSymbol, DeclarationGenerationContext.Member context) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        return SetsKt.emptySet();
    }

    public Set<Name> getNestedClassifiersNames(FirClassSymbol<?> classSymbol, DeclarationGenerationContext.Nested context) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        return SetsKt.emptySet();
    }

    public Set<CallableId> getTopLevelCallableIds() {
        return SetsKt.emptySet();
    }

    public Set<ClassId> getTopLevelClassIds() {
        return SetsKt.emptySet();
    }

    public final FirLazyValue<Set<ClassId>> getTopLevelClassIdsCache() {
        return this.topLevelClassIdsCache;
    }

    public final FirLazyValue<Set<CallableId>> getTopLevelCallableIdsCache() {
        return this.topLevelCallableIdsCache;
    }
}
