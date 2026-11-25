package org.jetbrains.kotlin.analysis.providers.impl.declarationProviders;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: FileBasedKotlinDeclarationProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00102\u0006\u0010\u0017\u001a\u00020\u0012H\u0016J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\u0006\u0010#\u001a\u00020$H\u0016J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\r2\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J#\u0010'\u001a\b\u0012\u0004\u0012\u0002H(0\u0010\"\n\b\u0000\u0010(\u0018\u0001*\u00020)2\u0006\u0010#\u001a\u00020$H\u0082\bJ#\u0010*\u001a\b\u0012\u0004\u0012\u00020&0\r\"\n\b\u0000\u0010(\u0018\u0001*\u00020+2\u0006\u0010\u0014\u001a\u00020\u0012H\u0082\bJ+\u0010\n\u001a\b\u0012\u0004\u0012\u0002H(0\u0010\"\n\b\u0000\u0010(\u0018\u0001*\u00020+2\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010,\u001a\u00020&H\u0082\bJ\u0016\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00102\u0006\u0010#\u001a\u00020$H\u0016J\u0016\u0010/\u001a\b\u0012\u0004\u0012\u00020&0\r2\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J\u0016\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00102\u0006\u0010#\u001a\u00020$H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/declarationProviders/FileBasedKotlinDeclarationProvider;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinDeclarationProvider;", "kotlinFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/psi/KtFile;)V", "getKotlinFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "topLevelDeclarations", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getTopLevelDeclarations", "()Lkotlin/sequences/Sequence;", "computePackageSetWithTopLevelCallableDeclarations", "", "", "findFilesForFacade", "", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findFilesForFacadeByPackage", "packageFqName", "findFilesForScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "scriptFqName", "findInternalFilesForFacade", "getAllClassesByClassId", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllTypeAliasesByClassId", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "getClassLikeDeclarationByClassId", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "getClassLikeDeclarationsByClassId", "getTopLevelCallableFiles", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelCallables", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "getTopLevelDeclarationNames", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "name", "getTopLevelFunctions", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getTopLevelKotlinClassLikeDeclarationNamesInPackage", "getTopLevelProperties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileBasedKotlinDeclarationProvider extends KotlinDeclarationProvider {
    private final KtFile kotlinFile;

    public FileBasedKotlinDeclarationProvider(KtFile kotlinFile) {
        Intrinsics.checkNotNullParameter(kotlinFile, "kotlinFile");
        this.kotlinFile = kotlinFile;
    }

    public final KtFile getKotlinFile() {
        return this.kotlinFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence<KtDeclaration> getTopLevelDeclarations() {
        return SequencesKt.sequence(new FileBasedKotlinDeclarationProvider$topLevelDeclarations$1(this, null));
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public KtClassLikeDeclaration getClassLikeDeclarationByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return (KtClassLikeDeclaration) SequencesKt.firstOrNull(getClassLikeDeclarationsByClassId(classId));
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtClassOrObject> getAllClassesByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Sequence sequenceFilter = SequencesKt.filter(getClassLikeDeclarationsByClassId(classId), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.declarationProviders.FileBasedKotlinDeclarationProvider$getAllClassesByClassId$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtClassOrObject);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.toList(sequenceFilter);
    }

    private final Sequence<KtClassLikeDeclaration> getClassLikeDeclarationsByClassId(ClassId classId) {
        if (classId.isLocal()) {
            return SequencesKt.emptySequence();
        }
        if (!Intrinsics.areEqual(this.kotlinFile.getPackageFqName(), classId.getPackageFqName())) {
            return SequencesKt.emptySequence();
        }
        return SequencesKt.sequence(new AnonymousClass1(classId, this, null));
    }

    /* JADX INFO: compiled from: FileBasedKotlinDeclarationProvider.kt */
    @Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\u008a\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J(\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"org/jetbrains/kotlin/analysis/providers/impl/declarationProviders/FileBasedKotlinDeclarationProvider$getClassLikeDeclarationsByClassId$Task", "", "chunks", "", "Lorg/jetbrains/kotlin/name/Name;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "(Ljava/util/List;Lorg/jetbrains/kotlin/psi/KtElement;)V", "getChunks", "()Ljava/util/List;", "getElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/util/List;Lorg/jetbrains/kotlin/psi/KtElement;)Lorg/jetbrains/kotlin/analysis/providers/impl/declarationProviders/FileBasedKotlinDeclarationProvider$getClassLikeDeclarationsByClassId$Task;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Task {
        private final List<Name> chunks;
        private final KtElement element;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Task copy$default(Task task, List list, KtElement ktElement, int i, Object obj) {
            if ((i & 1) != 0) {
                list = task.chunks;
            }
            if ((i & 2) != 0) {
                ktElement = task.element;
            }
            return task.copy(list, ktElement);
        }

        public final List<Name> component1() {
            return this.chunks;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtElement getElement() {
            return this.element;
        }

        public final Task copy(List<Name> chunks, KtElement element) {
            Intrinsics.checkNotNullParameter(chunks, "chunks");
            Intrinsics.checkNotNullParameter(element, "element");
            return new Task(chunks, element);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Task)) {
                return false;
            }
            Task task = (Task) other;
            return Intrinsics.areEqual(this.chunks, task.chunks) && Intrinsics.areEqual(this.element, task.element);
        }

        public int hashCode() {
            return (this.chunks.hashCode() * 31) + this.element.hashCode();
        }

        public String toString() {
            return "Task(chunks=" + this.chunks + ", element=" + this.element + ')';
        }

        public Task(List<Name> chunks, KtElement element) {
            Intrinsics.checkNotNullParameter(chunks, "chunks");
            Intrinsics.checkNotNullParameter(element, "element");
            this.chunks = chunks;
            this.element = element;
        }

        public final List<Name> getChunks() {
            return this.chunks;
        }

        public final KtElement getElement() {
            return this.element;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.analysis.providers.impl.declarationProviders.FileBasedKotlinDeclarationProvider$getClassLikeDeclarationsByClassId$1, reason: invalid class name */
    /* JADX INFO: compiled from: FileBasedKotlinDeclarationProvider.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.analysis.providers.impl.declarationProviders.FileBasedKotlinDeclarationProvider$getClassLikeDeclarationsByClassId$1", f = "FileBasedKotlinDeclarationProvider.kt", i = {0, 0}, l = {70}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "tasks"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super KtClassLikeDeclaration>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ClassId $classId;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ FileBasedKotlinDeclarationProvider this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ClassId classId, FileBasedKotlinDeclarationProvider fileBasedKotlinDeclarationProvider, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$classId = classId;
            this.this$0 = fileBasedKotlinDeclarationProvider;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$classId, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super KtClassLikeDeclaration> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            ArrayDeque arrayDeque;
            SequenceScope sequenceScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                arrayDeque = new ArrayDeque();
                List<Name> listPathSegments = this.$classId.getRelativeClassName().pathSegments();
                Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
                Iterator it = this.this$0.getTopLevelDeclarations().iterator();
                while (it.hasNext()) {
                    arrayDeque.addLast(new Task(listPathSegments, (KtDeclaration) it.next()));
                }
                arrayDeque.add(new Task(listPathSegments, this.this$0.getKotlinFile()));
                sequenceScope = sequenceScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                arrayDeque = (ArrayDeque) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (!arrayDeque.isEmpty()) {
                Task task = (Task) arrayDeque.removeFirst();
                List<Name> listComponent1 = task.component1();
                KtElement element = task.getElement();
                listComponent1.isEmpty();
                if ((element instanceof KtNamedDeclaration) && Intrinsics.areEqual(((KtNamedDeclaration) element).getNameAsName(), listComponent1.get(0))) {
                    if (listComponent1.size() == 1) {
                        KtClassLikeDeclaration ktClassLikeDeclaration = element instanceof KtClassLikeDeclaration ? (KtClassLikeDeclaration) element : null;
                        this.L$0 = sequenceScope;
                        this.L$1 = arrayDeque;
                        this.label = 1;
                        if (CollectionsKt.yieldIfNotNull(sequenceScope, ktClassLikeDeclaration, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else if (element instanceof KtDeclarationContainer) {
                        List<Name> listSubList = listComponent1.subList(1, listComponent1.size());
                        for (KtDeclaration ktDeclaration : ((KtDeclarationContainer) element).getDeclarations()) {
                            Intrinsics.checkNotNull(ktDeclaration);
                            arrayDeque.addLast(new Task(listSubList, ktDeclaration));
                        }
                    }
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtTypeAlias> getAllTypeAliasesByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Sequence sequenceFilter = SequencesKt.filter(getClassLikeDeclarationsByClassId(classId), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.declarationProviders.FileBasedKotlinDeclarationProvider$getAllTypeAliasesByClassId$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtTypeAlias);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.toList(sequenceFilter);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> getTopLevelCallableFiles(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        Iterator<T> it = getTopLevelProperties(callableId).iterator();
        while (it.hasNext()) {
            setCreateSetBuilder.add(((KtProperty) it.next()).getContainingKtFile());
        }
        Set set = setCreateSetBuilder;
        Iterator<T> it2 = getTopLevelFunctions(callableId).iterator();
        while (it2.hasNext()) {
            set.add(((KtNamedFunction) it2.next()).getContainingKtFile());
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findFilesForFacadeByPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (!Intrinsics.areEqual(this.kotlinFile.getPackageFqName(), packageFqName)) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        return kotlin.collections.CollectionsKt.listOf(this.kotlinFile);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findFilesForFacade(FqName facadeFqName) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        if (!Intrinsics.areEqual(JvmFileClassUtilKt.getJavaFileFacadeFqName(this.kotlinFile), facadeFqName)) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        Iterator<KtDeclaration> it = getTopLevelDeclarations().iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof KtClassLikeDeclaration)) {
                return kotlin.collections.CollectionsKt.listOf(this.kotlinFile);
            }
        }
        return kotlin.collections.CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtFile> findInternalFilesForFacade(FqName facadeFqName) {
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        return kotlin.collections.CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<String> computePackageSetWithTopLevelCallableDeclarations() {
        String strAsString = this.kotlinFile.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return SetsKt.setOf(strAsString);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtScript> findFilesForScript(FqName scriptFqName) {
        Intrinsics.checkNotNullParameter(scriptFqName, "scriptFqName");
        KtScript script = this.kotlinFile.getScript();
        if (script == null || !Intrinsics.areEqual(script.mo7391getFqName(), scriptFqName)) {
            script = null;
        }
        return kotlin.collections.CollectionsKt.listOfNotNull(script);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<Name> getTopLevelKotlinClassLikeDeclarationNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (!Intrinsics.areEqual(this.kotlinFile.getPackageFqName(), packageFqName)) {
            return SetsKt.emptySet();
        }
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        for (KtDeclaration ktDeclaration : getTopLevelDeclarations()) {
            if (ktDeclaration instanceof KtClassLikeDeclaration) {
                CollectionsKt.addIfNotNull(setCreateSetBuilder, ((KtNamedDeclaration) ktDeclaration).getNameAsName());
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtProperty> getTopLevelProperties(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        if (callableId.getClassId() != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FqName packageName = callableId.getPackageName();
        Name callableName = callableId.getCallableName();
        if (!Intrinsics.areEqual(this.kotlinFile.getPackageFqName(), packageName)) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        List listCreateListBuilder = kotlin.collections.CollectionsKt.createListBuilder();
        for (KtDeclaration ktDeclaration : getTopLevelDeclarations()) {
            if ((ktDeclaration instanceof KtProperty) && Intrinsics.areEqual(((KtNamedDeclaration) ktDeclaration).getNameAsName(), callableName)) {
                listCreateListBuilder.add(ktDeclaration);
            }
        }
        return kotlin.collections.CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Collection<KtNamedFunction> getTopLevelFunctions(CallableId callableId) {
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        if (callableId.getClassId() != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FqName packageName = callableId.getPackageName();
        Name callableName = callableId.getCallableName();
        if (!Intrinsics.areEqual(this.kotlinFile.getPackageFqName(), packageName)) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        List listCreateListBuilder = kotlin.collections.CollectionsKt.createListBuilder();
        for (KtDeclaration ktDeclaration : getTopLevelDeclarations()) {
            if ((ktDeclaration instanceof KtNamedFunction) && Intrinsics.areEqual(((KtNamedDeclaration) ktDeclaration).getNameAsName(), callableName)) {
                listCreateListBuilder.add(ktDeclaration);
            }
        }
        return kotlin.collections.CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinDeclarationProvider
    public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (!Intrinsics.areEqual(this.kotlinFile.getPackageFqName(), packageFqName)) {
            return SetsKt.emptySet();
        }
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        for (KtDeclaration ktDeclaration : getTopLevelDeclarations()) {
            if (ktDeclaration instanceof KtCallableDeclaration) {
                CollectionsKt.addIfNotNull(setCreateSetBuilder, ((KtNamedDeclaration) ktDeclaration).getNameAsName());
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }
}
