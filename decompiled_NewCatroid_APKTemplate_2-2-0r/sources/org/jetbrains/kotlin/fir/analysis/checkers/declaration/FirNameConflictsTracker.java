package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponent;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirConflictsDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J:\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0011H\u0016R9\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006`\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirNameConflictsTracker;", "Lorg/jetbrains/kotlin/fir/FirNameConflictsTrackerComponent;", "()V", "redeclaredClassifiers", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/ClassId;", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirNameConflictsTracker$ClassifierWithFile;", "Lkotlin/collections/HashMap;", "getRedeclaredClassifiers", "()Ljava/util/HashMap;", "registerClassifierRedeclaration", "", "classId", "newSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "newSymbolFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "prevSymbol", "prevSymbolFile", "ClassifierWithFile", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNameConflictsTracker extends FirNameConflictsTrackerComponent {
    private final HashMap<ClassId, Set<ClassifierWithFile>> redeclaredClassifiers = new HashMap<>();

    /* JADX INFO: compiled from: FirConflictsDeclarationChecker.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J#\u0010\r\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirNameConflictsTracker$ClassifierWithFile;", "", "classifier", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "getClassifier", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ClassifierWithFile {
        private final FirClassLikeSymbol<?> classifier;
        private final FirFile file;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ClassifierWithFile copy$default(ClassifierWithFile classifierWithFile, FirClassLikeSymbol firClassLikeSymbol, FirFile firFile, int i, Object obj) {
            if ((i & 1) != 0) {
                firClassLikeSymbol = classifierWithFile.classifier;
            }
            if ((i & 2) != 0) {
                firFile = classifierWithFile.file;
            }
            return classifierWithFile.copy(firClassLikeSymbol, firFile);
        }

        public final FirClassLikeSymbol<?> component1() {
            return this.classifier;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final FirFile getFile() {
            return this.file;
        }

        public final ClassifierWithFile copy(FirClassLikeSymbol<?> classifier, FirFile file) {
            Intrinsics.checkNotNullParameter(classifier, "classifier");
            return new ClassifierWithFile(classifier, file);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClassifierWithFile)) {
                return false;
            }
            ClassifierWithFile classifierWithFile = (ClassifierWithFile) other;
            return Intrinsics.areEqual(this.classifier, classifierWithFile.classifier) && Intrinsics.areEqual(this.file, classifierWithFile.file);
        }

        public int hashCode() {
            int iHashCode = this.classifier.hashCode() * 31;
            FirFile firFile = this.file;
            return iHashCode + (firFile == null ? 0 : firFile.hashCode());
        }

        public String toString() {
            return "ClassifierWithFile(classifier=" + this.classifier + ", file=" + this.file + ')';
        }

        public ClassifierWithFile(FirClassLikeSymbol<?> classifier, FirFile firFile) {
            Intrinsics.checkNotNullParameter(classifier, "classifier");
            this.classifier = classifier;
            this.file = firFile;
        }

        public final FirClassLikeSymbol<?> getClassifier() {
            return this.classifier;
        }

        public final FirFile getFile() {
            return this.file;
        }
    }

    public final HashMap<ClassId, Set<ClassifierWithFile>> getRedeclaredClassifiers() {
        return this.redeclaredClassifiers;
    }

    @Override // org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponent
    public void registerClassifierRedeclaration(ClassId classId, FirClassLikeSymbol<?> newSymbol, FirFile newSymbolFile, FirClassLikeSymbol<?> prevSymbol, FirFile prevSymbolFile) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(newSymbol, "newSymbol");
        Intrinsics.checkNotNullParameter(newSymbolFile, "newSymbolFile");
        Intrinsics.checkNotNullParameter(prevSymbol, "prevSymbol");
        HashMap<ClassId, Set<ClassifierWithFile>> map = this.redeclaredClassifiers;
        LinkedHashSet linkedHashSetLinkedSetOf = SetsKt.linkedSetOf(new ClassifierWithFile(newSymbol, newSymbolFile), new ClassifierWithFile(prevSymbol, prevSymbolFile));
        final AnonymousClass1 anonymousClass1 = new Function2<Set<? extends ClassifierWithFile>, Set<? extends ClassifierWithFile>, Set<? extends ClassifierWithFile>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNameConflictsTracker.registerClassifierRedeclaration.1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Set<? extends ClassifierWithFile> invoke(Set<? extends ClassifierWithFile> set, Set<? extends ClassifierWithFile> set2) {
                return invoke2((Set<ClassifierWithFile>) set, (Set<ClassifierWithFile>) set2);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Set<ClassifierWithFile> invoke2(Set<ClassifierWithFile> a, Set<ClassifierWithFile> b) {
                Intrinsics.checkNotNullParameter(a, "a");
                Intrinsics.checkNotNullParameter(b, "b");
                return SetsKt.plus((Set) a, (Iterable) b);
            }
        };
        map.merge(classId, linkedHashSetLinkedSetOf, new BiFunction() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirNameConflictsTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return FirNameConflictsTracker.registerClassifierRedeclaration$lambda$0(anonymousClass1, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set registerClassifierRedeclaration$lambda$0(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj, obj2);
    }
}
