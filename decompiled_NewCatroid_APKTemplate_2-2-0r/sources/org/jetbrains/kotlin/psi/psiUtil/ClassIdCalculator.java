package org.jetbrains.kotlin.psi.psiUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtClassLikeDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: ClassIdCalculator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi/psiUtil/ClassIdCalculator;", "", "()V", "calculateClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "declaration", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassIdCalculator {
    public static final ClassIdCalculator INSTANCE = new ClassIdCalculator();

    private ClassIdCalculator() {
    }

    public final ClassId calculateClassId(KtClassLikeDeclaration declaration) {
        KtFile ktFile;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ArrayList arrayList = new ArrayList();
        Iterator<PsiElement> it = PsiUtilsKt.getParentsWithSelf(declaration).iterator();
        while (true) {
            if (!it.hasNext()) {
                ktFile = null;
                break;
            }
            PsiElement next = it.next();
            if (next instanceof KtEnumEntry) {
                return null;
            }
            if (next instanceof KtClassLikeDeclaration) {
                arrayList.add(next);
            } else {
                if (next instanceof KtFile) {
                    ktFile = (KtFile) next;
                    break;
                }
                if (next instanceof KtScript) {
                    continue;
                } else {
                    if (next instanceof KtDeclaration ? true : next instanceof KtObjectLiteralExpression) {
                        return null;
                    }
                }
            }
        }
        if (ktFile == null) {
            return null;
        }
        List listReversed = CollectionsKt.reversed(arrayList);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listReversed, 10));
        Iterator it2 = listReversed.iterator();
        while (it2.hasNext()) {
            String name2 = ((KtClassLikeDeclaration) it2.next()).getMyName();
            if (name2 == null) {
                name2 = SpecialNames.NO_NAME_PROVIDED.asString();
            }
            arrayList2.add(name2);
        }
        FqName fqNameFromSegments = FqName.fromSegments(arrayList2);
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        return new ClassId(ktFile.getPackageFqName(), fqNameFromSegments, false);
    }
}
