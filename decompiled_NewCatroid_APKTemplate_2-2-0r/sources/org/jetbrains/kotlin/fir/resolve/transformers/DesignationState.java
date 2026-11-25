package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DesignationState.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B%\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0003R\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState;", "", "firstDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "designation", "", "targetClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Ljava/util/Iterator;Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)V", "<set-?>", "", "classLocated", "getClassLocated", "()Z", "currentElement", "getFirstDeclaration", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getTargetClass", "()Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "shouldSkipClass", "declaration", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DesignationState {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean classLocated;
    private FirDeclaration currentElement;
    private final Iterator<FirDeclaration> designation;
    private final FirDeclaration firstDeclaration;
    private final FirClassLikeDeclaration targetClass;

    public /* synthetic */ DesignationState(FirDeclaration firDeclaration, Iterator it, FirClassLikeDeclaration firClassLikeDeclaration, DefaultConstructorMarker defaultConstructorMarker) {
        this(firDeclaration, it, firClassLikeDeclaration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private DesignationState(FirDeclaration firDeclaration, Iterator<? extends FirDeclaration> it, FirClassLikeDeclaration firClassLikeDeclaration) {
        this.firstDeclaration = firDeclaration;
        this.designation = it;
        this.targetClass = firClassLikeDeclaration;
    }

    public final FirDeclaration getFirstDeclaration() {
        return this.firstDeclaration;
    }

    public final FirClassLikeDeclaration getTargetClass() {
        return this.targetClass;
    }

    /* JADX INFO: compiled from: DesignationState.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\t0\b2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "designationMapForLocalClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "includeFile", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final DesignationState create(FirRegularClassSymbol symbol, Map<FirClassLikeDeclaration, ? extends FirClassLikeDeclaration> designationMapForLocalClasses, boolean includeFile) {
            List listBuild;
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(designationMapForLocalClasses, "designationMapForLocalClasses");
            FirRegularClass firRegularClass = (FirRegularClass) symbol.getFir();
            FirRegularClass firRegularClass2 = firRegularClass;
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (firRegularClass2.getSymbol().getClassId().isLocal()) {
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                FirClassLikeDeclaration firClassLikeDeclaration = firRegularClass2;
                do {
                    listCreateListBuilder.add(firClassLikeDeclaration);
                    firClassLikeDeclaration = designationMapForLocalClasses.get(firClassLikeDeclaration);
                    if (firClassLikeDeclaration == null) {
                        break;
                    }
                    if (firClassLikeDeclaration instanceof FirAnonymousObject) {
                        firClassLikeDeclaration = null;
                    }
                } while (firClassLikeDeclaration != null);
                CollectionsKt.reverse(listCreateListBuilder);
                listBuild = CollectionsKt.build(listCreateListBuilder);
            } else {
                List listCreateListBuilder2 = CollectionsKt.createListBuilder();
                FirProvider firProvider = FirProviderKt.getFirProvider(firRegularClass.getModuleData().getSession());
                Sequence sequenceGenerateSequence = SequencesKt.generateSequence(symbol.getClassId(), new Function1<ClassId, ClassId>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.DesignationState$Companion$create$designation$2$outerClasses$1
                    @Override // kotlin.jvm.functions.Function1
                    public final ClassId invoke(ClassId classId) {
                        Intrinsics.checkNotNullParameter(classId, "classId");
                        return classId.getOuterClassId();
                    }
                });
                ArrayList arrayList = new ArrayList();
                Iterator it = sequenceGenerateSequence.iterator();
                while (it.hasNext()) {
                    arrayList.add(firProvider.getFirClassifierByFqName((ClassId) it.next()));
                }
                ArrayList arrayList2 = arrayList;
                FirFile firClassifierContainerFileIfAny = firProvider.getFirClassifierContainerFileIfAny(firRegularClass.getSymbol());
                if (firClassifierContainerFileIfAny == null) {
                    throw new IllegalArgumentException(("Containing file was not found for\n" + UtilsKt.render(firRegularClass)).toString());
                }
                if (includeFile) {
                    listCreateListBuilder2.add(firClassifierContainerFileIfAny);
                }
                CollectionsKt.addAll(listCreateListBuilder2, CollectionsKt.asReversed(CollectionsKt.filterNotNull(arrayList2)));
                listBuild = CollectionsKt.build(listCreateListBuilder2);
            }
            if (listBuild.isEmpty()) {
                return null;
            }
            return new DesignationState((FirDeclaration) CollectionsKt.first(listBuild), listBuild.iterator(), firRegularClass2, defaultConstructorMarker);
        }
    }

    public final boolean getClassLocated() {
        return this.classLocated;
    }

    public final boolean shouldSkipClass(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.classLocated) {
            return !Intrinsics.areEqual(declaration, this.targetClass);
        }
        if (this.currentElement == null && this.designation.hasNext()) {
            this.currentElement = this.designation.next();
        }
        boolean zAreEqual = Intrinsics.areEqual(this.currentElement, declaration);
        if (zAreEqual) {
            if (Intrinsics.areEqual(this.currentElement, this.targetClass)) {
                this.classLocated = true;
            }
            this.currentElement = null;
        }
        return !zAreEqual;
    }
}
