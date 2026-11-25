package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirGeneratedDeclarationsUtilsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.caches.FirLazyValue;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.GeneratedDeclarationValidationKt;
import org.jetbrains.kotlin.fir.extensions.DeclarationGenerationContext;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirGeneratedScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0003'()B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J-\u0010\u0012\u001a\u0004\u0018\u00010\n2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\t2\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0000¢\u0006\u0002\b\u0018J%\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000¢\u0006\u0002\b\u001cJ\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\tH\u0002JP\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0!2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\t2'\u0010#\u001a#\u0012\u0004\u0012\u00020\u001f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0%0$¢\u0006\u0002\b&H\u0082\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u0007\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\u0004\u0012\u00020\n\u0012\u000e\u0012\f\u0012\b\u0012\u00060\fj\u0002`\r0\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u000e\u001a\"\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\u0004\u0012\u00020\u000f\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0010j\u0002`\u00110\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "callableStorageByClass", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$CallableStorage;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$StorageContext;", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;", "Lorg/jetbrains/kotlin/fir/extensions/MemberGenerationContext;", "classifierStorageByClass", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$ClassifierStorage;", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;", "Lorg/jetbrains/kotlin/fir/extensions/NestedClassGenerationContext;", "getCallableStorage", "classSymbol", "regularDeclaredScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScope;", "scopeForGeneratedClass", "", "getCallableStorage$providers", "getClassifierStorage", "regularNestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "getClassifierStorage$providers", "getExtensionsForClass", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "groupExtensionsByName", "", "Lorg/jetbrains/kotlin/name/Name;", "nameExtractor", "Lkotlin/Function2;", "", "Lkotlin/ExtensionFunctionType;", "CallableStorage", "ClassifierStorage", "StorageContext", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirGeneratedMemberDeclarationsStorage implements FirSessionComponent {
    private final FirCachesFactory cachesFactory;
    private final FirCache<FirClassSymbol<?>, CallableStorage, StorageContext<DeclarationGenerationContext.Member>> callableStorageByClass;
    private final FirCache<FirClassSymbol<?>, ClassifierStorage, StorageContext<DeclarationGenerationContext.Nested>> classifierStorageByClass;
    private final FirSession session;

    public FirGeneratedMemberDeclarationsStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.callableStorageByClass = firCachesFactory.createCache(new Function2<FirClassSymbol<?>, StorageContext<DeclarationGenerationContext.Member>, CallableStorage>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$callableStorageByClass$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FirGeneratedMemberDeclarationsStorage.CallableStorage invoke(FirClassSymbol<?> firClassSymbol, FirGeneratedMemberDeclarationsStorage.StorageContext<DeclarationGenerationContext.Member> storageContext) {
                Intrinsics.checkNotNullParameter(firClassSymbol, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(storageContext, "<name for destructuring parameter 1>");
                return new FirGeneratedMemberDeclarationsStorage.CallableStorage(this.this$0.cachesFactory, storageContext.component1(), storageContext.component2());
            }
        });
        this.classifierStorageByClass = firCachesFactory.createCache(new Function2<FirClassSymbol<?>, StorageContext<DeclarationGenerationContext.Nested>, ClassifierStorage>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$classifierStorageByClass$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FirGeneratedMemberDeclarationsStorage.ClassifierStorage invoke(FirClassSymbol<?> classSymbol, FirGeneratedMemberDeclarationsStorage.StorageContext<DeclarationGenerationContext.Nested> storageContext) {
                Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
                Intrinsics.checkNotNullParameter(storageContext, "<name for destructuring parameter 1>");
                return new FirGeneratedMemberDeclarationsStorage.ClassifierStorage(this.this$0.cachesFactory, classSymbol, storageContext.component1(), storageContext.component2());
            }
        });
    }

    public final CallableStorage getCallableStorage$providers(FirClassSymbol<?> classSymbol, FirClassDeclaredMemberScope regularDeclaredScope, boolean scopeForGeneratedClass) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        DeclarationGenerationContext.Member member = new DeclarationGenerationContext.Member(classSymbol, regularDeclaredScope);
        List<FirDeclarationGenerationExtension> extensionsForClass = getExtensionsForClass(classSymbol);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (FirDeclarationGenerationExtension firDeclarationGenerationExtension : extensionsForClass) {
            for (Name name2 : firDeclarationGenerationExtension.getCallableNamesForClass(classSymbol, member)) {
                ArrayList arrayList = (List) linkedHashMap.get(name2);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(name2, arrayList);
                }
                arrayList.add(firDeclarationGenerationExtension);
            }
        }
        if (!linkedHashMap.isEmpty() || scopeForGeneratedClass) {
            return this.callableStorageByClass.getValue(classSymbol, new StorageContext<>(member, linkedHashMap));
        }
        return null;
    }

    public final ClassifierStorage getClassifierStorage$providers(FirClassSymbol<?> classSymbol, FirNestedClassifierScope regularNestedClassifierScope) {
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        DeclarationGenerationContext.Nested nested = new DeclarationGenerationContext.Nested(classSymbol, regularNestedClassifierScope);
        List<FirDeclarationGenerationExtension> extensionsForClass = getExtensionsForClass(classSymbol);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (FirDeclarationGenerationExtension firDeclarationGenerationExtension : extensionsForClass) {
            for (Name name2 : firDeclarationGenerationExtension.getNestedClassifiersNames(classSymbol, nested)) {
                ArrayList arrayList = (List) linkedHashMap.get(name2);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(name2, arrayList);
                }
                arrayList.add(firDeclarationGenerationExtension);
            }
        }
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        return this.classifierStorageByClass.getValue(classSymbol, new StorageContext<>(nested, linkedHashMap));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirGeneratedScopes.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B'\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005¢\u0006\u0002\u0010\tJ\u000e\u0010\u000f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\rJ\u001b\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005HÆ\u0003J:\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005HÆ\u0001¢\u0006\u0002\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R#\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\r¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$StorageContext;", "C", "", "generationContext", "extensionsByName", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "(Ljava/lang/Object;Ljava/util/Map;)V", "getExtensionsByName", "()Ljava/util/Map;", "getGenerationContext", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/util/Map;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$StorageContext;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class StorageContext<C> {
        private final Map<Name, List<FirDeclarationGenerationExtension>> extensionsByName;
        private final C generationContext;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ StorageContext copy$default(StorageContext storageContext, Object obj, Map map, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = storageContext.generationContext;
            }
            if ((i & 2) != 0) {
                map = storageContext.extensionsByName;
            }
            return storageContext.copy(obj, map);
        }

        public final C component1() {
            return this.generationContext;
        }

        public final Map<Name, List<FirDeclarationGenerationExtension>> component2() {
            return this.extensionsByName;
        }

        public final StorageContext<C> copy(C generationContext, Map<Name, ? extends List<? extends FirDeclarationGenerationExtension>> extensionsByName) {
            Intrinsics.checkNotNullParameter(extensionsByName, "extensionsByName");
            return new StorageContext<>(generationContext, extensionsByName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof StorageContext)) {
                return false;
            }
            StorageContext storageContext = (StorageContext) other;
            return Intrinsics.areEqual(this.generationContext, storageContext.generationContext) && Intrinsics.areEqual(this.extensionsByName, storageContext.extensionsByName);
        }

        public int hashCode() {
            C c = this.generationContext;
            return ((c == null ? 0 : c.hashCode()) * 31) + this.extensionsByName.hashCode();
        }

        public String toString() {
            return "StorageContext(generationContext=" + this.generationContext + ", extensionsByName=" + this.extensionsByName + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public StorageContext(C c, Map<Name, ? extends List<? extends FirDeclarationGenerationExtension>> extensionsByName) {
            Intrinsics.checkNotNullParameter(extensionsByName, "extensionsByName");
            this.generationContext = c;
            this.extensionsByName = extensionsByName;
        }

        public final C getGenerationContext() {
            return this.generationContext;
        }

        public final Map<Name, List<FirDeclarationGenerationExtension>> getExtensionsByName() {
            return this.extensionsByName;
        }
    }

    /* JADX INFO: compiled from: FirGeneratedScopes.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\b¢\u0006\u0002\u0010\fJ\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00170\nH\u0002J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001c0\n2\u0006\u0010%\u001a\u00020\tH\u0002J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020!0\n2\u0006\u0010%\u001a\u00020\tH\u0002R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\n0\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R+\u0010\u001a\u001a\u001c\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R+\u0010 \u001a\u001c\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001b¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001f¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$CallableStorage;", "", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "generationContext", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;", "Lorg/jetbrains/kotlin/fir/extensions/MemberGenerationContext;", "extensionsByCallableName", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "(Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;Ljava/util/Map;)V", "allCallableNames", "", "getAllCallableNames", "()Ljava/util/Set;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getClassSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "constructorCache", "Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getConstructorCache", "()Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "functionCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "", "getFunctionCache", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "propertyCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getPropertyCache", "generateConstructors", "generateMemberFunctions", "name", "generateMemberProperties", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CallableStorage {
        private final FirLazyValue<List<FirConstructorSymbol>> constructorCache;
        private final Map<Name, List<FirDeclarationGenerationExtension>> extensionsByCallableName;
        private final FirCache functionCache;
        private final DeclarationGenerationContext.Member generationContext;
        private final FirCache propertyCache;

        /* JADX WARN: Multi-variable type inference failed */
        public CallableStorage(FirCachesFactory cachesFactory, DeclarationGenerationContext.Member generationContext, Map<Name, ? extends List<? extends FirDeclarationGenerationExtension>> extensionsByCallableName) {
            Intrinsics.checkNotNullParameter(cachesFactory, "cachesFactory");
            Intrinsics.checkNotNullParameter(generationContext, "generationContext");
            Intrinsics.checkNotNullParameter(extensionsByCallableName, "extensionsByCallableName");
            this.generationContext = generationContext;
            this.extensionsByCallableName = extensionsByCallableName;
            this.functionCache = cachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$CallableStorage$special$$inlined$createCache$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    return invoke((Name) obj, (Void) obj2);
                }

                public final List<? extends FirNamedFunctionSymbol> invoke(Name key, Void r2) {
                    Intrinsics.checkNotNullParameter(key, "key");
                    return this.this$0.generateMemberFunctions(key);
                }
            });
            this.propertyCache = cachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$CallableStorage$special$$inlined$createCache$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    return invoke((Name) obj, (Void) obj2);
                }

                public final List<? extends FirPropertySymbol> invoke(Name key, Void r2) {
                    Intrinsics.checkNotNullParameter(key, "key");
                    return this.this$0.generateMemberProperties(key);
                }
            });
            this.constructorCache = cachesFactory.createLazyValue(new Function0<List<? extends FirConstructorSymbol>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$CallableStorage$constructorCache$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends FirConstructorSymbol> invoke() {
                    return this.this$0.generateConstructors();
                }
            });
        }

        public final FirCache getFunctionCache() {
            return this.functionCache;
        }

        public final FirCache getPropertyCache() {
            return this.propertyCache;
        }

        public final FirLazyValue<List<FirConstructorSymbol>> getConstructorCache() {
            return this.constructorCache;
        }

        public final Set<Name> getAllCallableNames() {
            return this.extensionsByCallableName.keySet();
        }

        private final FirClassSymbol<?> getClassSymbol() {
            return this.generationContext.getOwner();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<FirNamedFunctionSymbol> generateMemberFunctions(Name name2) {
            if (Intrinsics.areEqual(name2, SpecialNames.INIT)) {
                return CollectionsKt.emptyList();
            }
            List<FirDeclarationGenerationExtension> listEmptyList = this.extensionsByCallableName.get(name2);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = listEmptyList.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, ((FirDeclarationGenerationExtension) it.next()).generateFunctions(new CallableId(getClassSymbol().getClassId(), name2), this.generationContext));
            }
            ArrayList arrayList2 = arrayList;
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                GeneratedDeclarationValidationKt.validate(((FirNamedFunctionSymbol) it2.next()).getFir());
            }
            return arrayList2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<FirPropertySymbol> generateMemberProperties(Name name2) {
            if (Intrinsics.areEqual(name2, SpecialNames.INIT)) {
                return CollectionsKt.emptyList();
            }
            List<FirDeclarationGenerationExtension> listEmptyList = this.extensionsByCallableName.get(name2);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = listEmptyList.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, ((FirDeclarationGenerationExtension) it.next()).generateProperties(new CallableId(getClassSymbol().getClassId(), name2), this.generationContext));
            }
            ArrayList arrayList2 = arrayList;
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                GeneratedDeclarationValidationKt.validate(((FirPropertySymbol) it2.next()).getFir());
            }
            return arrayList2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<FirConstructorSymbol> generateConstructors() {
            List<FirDeclarationGenerationExtension> listEmptyList = this.extensionsByCallableName.get(SpecialNames.INIT);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = listEmptyList.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, ((FirDeclarationGenerationExtension) it.next()).generateConstructors(this.generationContext));
            }
            ArrayList arrayList2 = arrayList;
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                GeneratedDeclarationValidationKt.validate(((FirConstructorSymbol) it2.next()).getFir());
            }
            return arrayList2;
        }
    }

    /* JADX INFO: compiled from: FirGeneratedScopes.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\n\u0010\u0006\u001a\u00060\u0007j\u0002`\b\u0012\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\n¢\u0006\u0002\u0010\u000eJ\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u001a\u001a\u00020\u000bH\u0002R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\u0013\u001a\u0018\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00060\u0007j\u0002`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$ClassifierStorage;", "", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "generationContext", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;", "Lorg/jetbrains/kotlin/fir/extensions/NestedClassGenerationContext;", "extensionsByClassifierName", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "(Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;Ljava/util/Map;)V", "allClassifierNames", "", "getAllClassifierNames", "()Ljava/util/Set;", "classifiersCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "", "getClassifiersCache", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "generateNestedClassifier", "name", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassifierStorage {
        private final FirClassSymbol<?> classSymbol;
        private final FirCache classifiersCache;
        private final Map<Name, List<FirDeclarationGenerationExtension>> extensionsByClassifierName;
        private final DeclarationGenerationContext.Nested generationContext;

        /* JADX WARN: Multi-variable type inference failed */
        public ClassifierStorage(FirCachesFactory cachesFactory, FirClassSymbol<?> classSymbol, DeclarationGenerationContext.Nested generationContext, Map<Name, ? extends List<? extends FirDeclarationGenerationExtension>> extensionsByClassifierName) {
            Intrinsics.checkNotNullParameter(cachesFactory, "cachesFactory");
            Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
            Intrinsics.checkNotNullParameter(generationContext, "generationContext");
            Intrinsics.checkNotNullParameter(extensionsByClassifierName, "extensionsByClassifierName");
            this.classSymbol = classSymbol;
            this.generationContext = generationContext;
            this.extensionsByClassifierName = extensionsByClassifierName;
            this.classifiersCache = cachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$ClassifierStorage$special$$inlined$createCache$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    return invoke((Name) obj, (Void) obj2);
                }

                public final FirRegularClassSymbol invoke(Name key, Void r2) {
                    Intrinsics.checkNotNullParameter(key, "key");
                    return this.this$0.generateNestedClassifier(key);
                }
            });
        }

        public final FirCache getClassifiersCache() {
            return this.classifiersCache;
        }

        public final Set<Name> getAllClassifierNames() {
            return this.extensionsByClassifierName.keySet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final FirRegularClassSymbol generateNestedClassifier(Name name2) {
            FirRegularClassSymbol companionObjectSymbol;
            FirClassSymbol<?> firClassSymbol = this.classSymbol;
            if ((firClassSymbol instanceof FirRegularClassSymbol) && (companionObjectSymbol = ((FirRegularClassSymbol) firClassSymbol).getCompanionObjectSymbol()) != null && companionObjectSymbol.getOrigin().getGenerated() && Intrinsics.areEqual(companionObjectSymbol.getClassId().getShortClassName(), name2)) {
                return companionObjectSymbol;
            }
            List<FirDeclarationGenerationExtension> list = this.extensionsByClassifierName.get(name2);
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (FirDeclarationGenerationExtension firDeclarationGenerationExtension : list) {
                FirClassLikeSymbol<?> firClassLikeSymbolGenerateNestedClassLikeDeclaration = firDeclarationGenerationExtension.generateNestedClassLikeDeclaration(this.classSymbol, name2, this.generationContext);
                if (firClassLikeSymbolGenerateNestedClassLikeDeclaration != null) {
                    FirGeneratedDeclarationsUtilsKt.setOwnerGenerator((FirClassLikeDeclaration) firClassLikeSymbolGenerateNestedClassLikeDeclaration.getFir(), firDeclarationGenerationExtension);
                } else {
                    firClassLikeSymbolGenerateNestedClassLikeDeclaration = null;
                }
                if (firClassLikeSymbolGenerateNestedClassLikeDeclaration != null) {
                    arrayList.add(firClassLikeSymbolGenerateNestedClassLikeDeclaration);
                }
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            if (size == 0) {
                return null;
            }
            if (size != 1) {
                throw new IllegalStateException(StringsKt.trimIndent("\n                     Multiple plugins generated nested class with same name " + name2 + " for class " + this.classSymbol.getClassId() + ":\n                    " + CollectionsKt.joinToString$default(arrayList2, "\n", null, null, 0, null, new Function1<FirClassLikeSymbol<?>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirGeneratedMemberDeclarationsStorage$ClassifierStorage$generateNestedClassifier$generatedClass$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(FirClassLikeSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return UtilsKt.render(it.getFir());
                    }
                }, 30, null) + "\n                ").toString());
            }
            FirClassLikeSymbol firClassLikeSymbol = (FirClassLikeSymbol) CollectionsKt.first((List) arrayList2);
            if (firClassLikeSymbol instanceof FirRegularClassSymbol) {
                return (FirRegularClassSymbol) firClassLikeSymbol;
            }
            throw new IllegalArgumentException("Only regular class are allowed as nested classes".toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<FirDeclarationGenerationExtension> getExtensionsForClass(FirClassSymbol<?> classSymbol) {
        if (this.session != classSymbol.getModuleData().getSession()) {
            throw new IllegalArgumentException(("Class " + classSymbol + " is declared in " + classSymbol.getModuleData().getSession() + ", but generated storage for it taken from " + this.session).toString());
        }
        if (classSymbol.getOrigin().getGenerated()) {
            FirDeclarationGenerationExtension ownerGenerator = FirGeneratedDeclarationsUtilsKt.getOwnerGenerator((FirClassLikeDeclaration) classSymbol.getFir());
            Intrinsics.checkNotNull(ownerGenerator);
            return CollectionsKt.listOf(ownerGenerator);
        }
        return FirDeclarationGenerationExtensionKt.getDeclarationGenerators(FirExtensionServiceKt.getExtensionService(this.session));
    }
}
