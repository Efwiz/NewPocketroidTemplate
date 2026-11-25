package org.jetbrains.kotlin.fir.declarations.comparators;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirErrorProperty;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.types.FirTypeRefComparator;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirMemberDeclarationComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/comparators/FirMemberDeclarationComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "Lkotlin/Comparator;", "()V", "compare", "", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "TypeAndNameComparator", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMemberDeclarationComparator implements Comparator<FirMemberDeclaration> {
    public static final FirMemberDeclarationComparator INSTANCE = new FirMemberDeclarationComparator();

    private FirMemberDeclarationComparator() {
    }

    /* JADX INFO: compiled from: FirMemberDeclarationComparator.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/comparators/FirMemberDeclarationComparator$TypeAndNameComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "Lkotlin/Comparator;", "()V", "priority", "", "getPriority", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)I", "compare", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeAndNameComparator implements Comparator<FirMemberDeclaration> {
        public static final TypeAndNameComparator INSTANCE = new TypeAndNameComparator();

        private TypeAndNameComparator() {
        }

        private final int getPriority(FirMemberDeclaration firMemberDeclaration) {
            if (firMemberDeclaration instanceof FirEnumEntry) {
                return 7;
            }
            if (firMemberDeclaration instanceof FirConstructor) {
                return 6;
            }
            if (firMemberDeclaration instanceof FirProperty) {
                return 5;
            }
            if (firMemberDeclaration instanceof FirField) {
                return 4;
            }
            if (firMemberDeclaration instanceof FirFunction) {
                return 3;
            }
            if (firMemberDeclaration instanceof FirClass) {
                return 2;
            }
            if (firMemberDeclaration instanceof FirTypeAlias) {
                return 1;
            }
            if ((firMemberDeclaration instanceof FirErrorProperty) || (firMemberDeclaration instanceof FirValueParameter) || (firMemberDeclaration instanceof FirBackingField)) {
                return 0;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // java.util.Comparator
        public int compare(FirMemberDeclaration a, FirMemberDeclaration b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            int priority = getPriority(a) - getPriority(b);
            if (priority != 0) {
                return priority;
            }
            if (a instanceof FirEnumEntry) {
                if (b instanceof FirEnumEntry) {
                    return 0;
                }
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            return FirDeclarationUtilKt.getNameOrSpecialName(a).compareTo(FirDeclarationUtilKt.getNameOrSpecialName(b));
        }
    }

    @Override // java.util.Comparator
    public int compare(FirMemberDeclaration a, FirMemberDeclaration b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if ((a instanceof FirCallableDeclaration) && (b instanceof FirCallableDeclaration)) {
            return FirCallableDeclarationComparator.INSTANCE.compare((FirCallableDeclaration) a, (FirCallableDeclaration) b);
        }
        int iCompare = TypeAndNameComparator.INSTANCE.compare(a, b);
        if (iCompare != 0) {
            return iCompare;
        }
        if (a instanceof FirClass) {
            if (!(b instanceof FirClass)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            String strAsString = FirDeclarationUtilKt.getClassId((FirClassLikeDeclaration) a).getPackageFqName().asString();
            String strAsString2 = FirDeclarationUtilKt.getClassId((FirClassLikeDeclaration) b).getPackageFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            return strAsString.compareTo(strAsString2);
        }
        if (a instanceof FirTypeAlias) {
            if (!(b instanceof FirTypeAlias)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            return FirTypeRefComparator.INSTANCE.compare(((FirTypeAlias) a).getExpandedTypeRef(), ((FirTypeAlias) b).getExpandedTypeRef());
        }
        throw new IllegalStateException(("Unsupported member declaration comparison: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
    }
}
