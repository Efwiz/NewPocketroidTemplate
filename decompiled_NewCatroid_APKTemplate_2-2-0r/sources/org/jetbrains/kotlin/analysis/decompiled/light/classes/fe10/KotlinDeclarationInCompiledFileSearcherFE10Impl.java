package org.jetbrains.kotlin.analysis.decompiled.light.classes.fe10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.KotlinDeclarationInCompiledFileSearcher;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KotlinDeclarationInCompiledFileSearcherFE10Impl.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/fe10/KotlinDeclarationInCompiledFileSearcherFE10Impl;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/KotlinDeclarationInCompiledFileSearcher;", "()V", "findDeclarationInCompiledFile", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "member", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMember;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "light-classes-for-decompiled-fe10"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinDeclarationInCompiledFileSearcherFE10Impl extends KotlinDeclarationInCompiledFileSearcher {
    @Override // org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.KotlinDeclarationInCompiledFileSearcher
    public KtDeclaration findDeclarationInCompiledFile(KtClsFile file, PsiMember member, MemberSignature signature) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(signature, "signature");
        List listReversed = CollectionsKt.reversed(CollectionsKt.dropLast(SequencesKt.toList(SequencesKt.generateSequence(member.mo6092getContainingClass(), new Function1<PsiClass, PsiClass>() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.fe10.KotlinDeclarationInCompiledFileSearcherFE10Impl$findDeclarationInCompiledFile$relativeClassName$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiClass invoke(PsiClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.mo6092getContainingClass();
            }
        })), 1));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listReversed, 10));
        Iterator it = listReversed.iterator();
        while (it.hasNext()) {
            String name2 = ((PsiClass) it.next()).getMyName();
            Intrinsics.checkNotNull(name2);
            arrayList.add(Name.identifier(name2));
        }
        ArrayList arrayList2 = arrayList;
        String name3 = member.getMyName();
        if (name3 == null) {
            return null;
        }
        return findByStubs(file, arrayList2, member, name3);
    }
}
