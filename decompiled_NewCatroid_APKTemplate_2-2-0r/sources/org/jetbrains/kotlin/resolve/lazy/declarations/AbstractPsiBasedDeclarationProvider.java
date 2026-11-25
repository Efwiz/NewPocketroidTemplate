package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.collect.ArrayListMultimap;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassInfoUtil;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: AbstractPsiBasedDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0001(B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0007H$J\u001a\u0010\n\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u0010j\b\u0012\u0004\u0012\u00020\u000e`\u0011H\u0016J*\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00190\u0018H\u0016J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00132\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00132\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\r\u0010%\u001a\u00020&H\u0000¢\u0006\u0002\b'R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "index", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider$Index;", "doCreateIndex", "", "getClassOrObjectDeclarations", "", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getDeclarationNames", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getDeclarations", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getDestructuringDeclarationsEntries", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "getFunctionDeclarations", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getPropertyDeclarations", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getScriptDeclarations", "", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "getTypeAliasDeclarations", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "toInfoString", "", "toInfoString$frontend", "Index", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractPsiBasedDeclarationProvider implements DeclarationProvider {
    private final NotNullLazyValue<Index> index;

    protected abstract void doCreateIndex(Index index);

    public AbstractPsiBasedDeclarationProvider(StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.index = storageManager.createLazyValue(new Function0<Index>() { // from class: org.jetbrains.kotlin.resolve.lazy.declarations.AbstractPsiBasedDeclarationProvider$index$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AbstractPsiBasedDeclarationProvider.Index invoke() {
                AbstractPsiBasedDeclarationProvider.Index index = new AbstractPsiBasedDeclarationProvider.Index();
                this.this$0.doCreateIndex(index);
                return index;
            }
        });
    }

    /* JADX INFO: compiled from: AbstractPsiBasedDeclarationProvider.kt */
    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0005J\b\u0010&\u001a\u00020'H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007Ra\u0010\b\u001aR\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u000b*\b\u0012\u0002\b\u0003\u0018\u00010\f0\f \u000b*(\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u000b*\b\u0012\u0002\b\u0003\u0018\u00010\f0\f\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eRQ\u0010\u000f\u001aB\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00100\u0010 \u000b* \u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00100\u0010\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eRQ\u0010\u0012\u001aB\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00130\u0013 \u000b* \u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00130\u0013\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR!\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\n0\u0016j\b\u0012\u0004\u0012\u00020\n`\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019RQ\u0010\u001a\u001aB\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u001b0\u001b \u000b* \u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u001b0\u001b\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000eRQ\u0010\u001d\u001aB\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u001e0\u001e \u000b* \u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u001e0\u001e\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000eRQ\u0010 \u001aB\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010!0! \u000b* \u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n\u0012\f\u0012\n \u000b*\u0004\u0018\u00010!0!\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u000e¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider$Index;", "", "()V", "allDeclarations", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getAllDeclarations", "()Ljava/util/ArrayList;", "classesAndObjects", "Lorg/jetbrains/kotlin/com/google/common/collect/ArrayListMultimap;", "Lorg/jetbrains/kotlin/name/Name;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo;", "getClassesAndObjects", "()Lcom/google/common/collect/ArrayListMultimap;", "destructuringDeclarationsEntries", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "getDestructuringDeclarationsEntries", "functions", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getFunctions", "names", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getNames", "()Ljava/util/HashSet;", "properties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getProperties", "scripts", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "getScripts", "typeAliases", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "getTypeAliases", "putToIndex", "", "declaration", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class Index {
        private final ArrayList<KtDeclaration> allDeclarations = new ArrayList<>();
        private final ArrayListMultimap<Name, KtNamedFunction> functions = ArrayListMultimap.create();
        private final ArrayListMultimap<Name, KtProperty> properties = ArrayListMultimap.create();
        private final ArrayListMultimap<Name, KtClassOrObjectInfo<?>> classesAndObjects = ArrayListMultimap.create();
        private final ArrayListMultimap<Name, KtScriptInfo> scripts = ArrayListMultimap.create();
        private final ArrayListMultimap<Name, KtTypeAlias> typeAliases = ArrayListMultimap.create();
        private final ArrayListMultimap<Name, KtDestructuringDeclarationEntry> destructuringDeclarationsEntries = ArrayListMultimap.create();
        private final HashSet<Name> names = new HashSet<>();

        public final ArrayList<KtDeclaration> getAllDeclarations() {
            return this.allDeclarations;
        }

        public final ArrayListMultimap<Name, KtNamedFunction> getFunctions() {
            return this.functions;
        }

        public final ArrayListMultimap<Name, KtProperty> getProperties() {
            return this.properties;
        }

        public final ArrayListMultimap<Name, KtClassOrObjectInfo<?>> getClassesAndObjects() {
            return this.classesAndObjects;
        }

        public final ArrayListMultimap<Name, KtScriptInfo> getScripts() {
            return this.scripts;
        }

        public final ArrayListMultimap<Name, KtTypeAlias> getTypeAliases() {
            return this.typeAliases;
        }

        public final ArrayListMultimap<Name, KtDestructuringDeclarationEntry> getDestructuringDeclarationsEntries() {
            return this.destructuringDeclarationsEntries;
        }

        public final HashSet<Name> getNames() {
            return this.names;
        }

        public final void putToIndex(KtDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if ((declaration instanceof KtAnonymousInitializer) || (declaration instanceof KtSecondaryConstructor)) {
                return;
            }
            this.allDeclarations.add(declaration);
            if (declaration instanceof KtNamedFunction) {
                this.functions.put(KtPsiUtilKt.safeNameForLazyResolve((KtNamedDeclaration) declaration), declaration);
            } else if (declaration instanceof KtProperty) {
                this.properties.put(KtPsiUtilKt.safeNameForLazyResolve((KtNamedDeclaration) declaration), declaration);
            } else if (declaration instanceof KtTypeAlias) {
                this.typeAliases.put(KtPsiUtilKt.safeNameForLazyResolve(((KtTypeAlias) declaration).getNameAsName()), declaration);
            } else if (declaration instanceof KtClassOrObject) {
                KtClassOrObject ktClassOrObject = (KtClassOrObject) declaration;
                this.classesAndObjects.put(KtPsiUtilKt.safeNameForLazyResolve(ktClassOrObject.getNameAsName()), KtClassInfoUtil.createClassOrObjectInfo(ktClassOrObject));
            } else if (declaration instanceof KtScript) {
                KtScript ktScript = (KtScript) declaration;
                this.scripts.put(new KtScriptInfo(ktScript).getScript().getNameAsName(), new KtScriptInfo(ktScript));
            } else if (declaration instanceof KtDestructuringDeclaration) {
                for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : ((KtDestructuringDeclaration) declaration).getEntries()) {
                    Name nameSafeNameForLazyResolve = KtPsiUtilKt.safeNameForLazyResolve(ktDestructuringDeclarationEntry.getNameAsName());
                    this.destructuringDeclarationsEntries.put(nameSafeNameForLazyResolve, ktDestructuringDeclarationEntry);
                    this.names.add(nameSafeNameForLazyResolve);
                }
            } else if (!(declaration instanceof KtParameter)) {
                throw new IllegalArgumentException("Unknown declaration: " + declaration);
            }
            if (declaration instanceof KtNamedDeclaration) {
                this.names.add(KtPsiUtilKt.safeNameForLazyResolve((KtNamedDeclaration) declaration));
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("allDeclarations: ");
            ArrayList<KtDeclaration> arrayList = this.allDeclarations;
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = arrayList.iterator();
            while (it.hasNext()) {
                String name2 = ((KtDeclaration) it.next()).getName();
                if (name2 != null) {
                    arrayList2.add(name2);
                }
            }
            return sb.append(arrayList2).toString();
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public HashSet<Name> getDeclarationNames() {
        return this.index.invoke().getNames();
    }

    public final String toInfoString$frontend() {
        return this + ": " + this.index.invoke();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtDeclaration> getDeclarations(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        ArrayList<KtDeclaration> allDeclarations = this.index.invoke().getAllDeclarations();
        if (!Intrinsics.areEqual(kindFilter, DescriptorKindFilter.CLASSIFIERS)) {
            return allDeclarations;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : allDeclarations) {
            KtDeclaration ktDeclaration = (KtDeclaration) obj;
            if ((ktDeclaration instanceof KtClassOrObject) || (ktDeclaration instanceof KtTypeAlias)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtNamedFunction> getFunctionDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List list = this.index.invoke().getFunctions().get((Object) KtPsiUtilKt.safeNameForLazyResolve(name2));
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return CollectionsKt.toList(list);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtProperty> getPropertyDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List list = this.index.invoke().getProperties().get((Object) KtPsiUtilKt.safeNameForLazyResolve(name2));
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return CollectionsKt.toList(list);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtDestructuringDeclarationEntry> getDestructuringDeclarationsEntries(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List list = this.index.invoke().getDestructuringDeclarationsEntries().get((Object) KtPsiUtilKt.safeNameForLazyResolve(name2));
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return CollectionsKt.toList(list);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtClassOrObjectInfo<?>> getClassOrObjectDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List list = this.index.invoke().getClassesAndObjects().get((Object) KtPsiUtilKt.safeNameForLazyResolve(name2));
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return list;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public List<KtScriptInfo> getScriptDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List<KtScriptInfo> list = this.index.invoke().getScripts().get((Object) KtPsiUtilKt.safeNameForLazyResolve(name2));
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return list;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider
    public Collection<KtTypeAlias> getTypeAliasDeclarations(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List list = this.index.invoke().getTypeAliases().get((Object) KtPsiUtilKt.safeNameForLazyResolve(name2));
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        return list;
    }
}
