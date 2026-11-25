package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.ChangesEither;
import org.jetbrains.kotlin.incremental.LookupSymbol;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0000\u001a \u0010\u0006\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0000¨\u0006\u000b"}, d2 = {"toChangesEither", "Lorg/jetbrains/kotlin/incremental/ChangesEither$Known;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbolSet;", "toLookupSymbol", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ProgramSymbol;", "toProgramSymbolSet", "", "allClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProgramSymbolKt {
    public static final LookupSymbol toLookupSymbol(ProgramSymbol programSymbol) {
        Intrinsics.checkNotNullParameter(programSymbol, "<this>");
        if (programSymbol instanceof ClassSymbol) {
            FqName fqNameAsSingleFqName = ((ClassSymbol) programSymbol).getClassId().asSingleFqName();
            String strAsString = fqNameAsSingleFqName.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String strAsString2 = fqNameAsSingleFqName.parent().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            return new LookupSymbol(strAsString, strAsString2);
        }
        if (programSymbol instanceof ClassMember) {
            ClassMember classMember = (ClassMember) programSymbol;
            String memberName = classMember.getMemberName();
            String strAsString3 = classMember.getClassId().asSingleFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
            return new LookupSymbol(memberName, strAsString3);
        }
        if (!(programSymbol instanceof PackageMember)) {
            throw new NoWhenBranchMatchedException();
        }
        PackageMember packageMember = (PackageMember) programSymbol;
        String memberName2 = packageMember.getMemberName();
        String strAsString4 = packageMember.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
        return new LookupSymbol(memberName2, strAsString4);
    }

    public static final ProgramSymbolSet toProgramSymbolSet(Collection<LookupSymbol> collection, Iterable<? extends AccessibleClassSnapshot> allClasses) {
        Set<String> constantNames;
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(allClasses, "allClasses");
        LookupSymbolSet lookupSymbolSet = new LookupSymbolSet(collection);
        ProgramSymbolSet.Collector collector = new ProgramSymbolSet.Collector();
        for (AccessibleClassSnapshot accessibleClassSnapshot : allClasses) {
            if (accessibleClassSnapshot instanceof RegularKotlinClassSnapshot ? true : accessibleClassSnapshot instanceof JavaClassSnapshot) {
                if (lookupSymbolSet.contains(toLookupSymbol(new ClassSymbol(accessibleClassSnapshot.getClassId())))) {
                    collector.addClass(accessibleClassSnapshot.getClassId());
                }
                FqName fqNameAsSingleFqName = accessibleClassSnapshot.getClassId().asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                collector.addClassMembers(accessibleClassSnapshot.getClassId(), lookupSymbolSet.getLookupNamesInScope(fqNameAsSingleFqName));
            } else {
                boolean z = accessibleClassSnapshot instanceof PackageFacadeKotlinClassSnapshot;
                if (z ? true : accessibleClassSnapshot instanceof MultifileClassKotlinClassSnapshot) {
                    FqName packageFqName = accessibleClassSnapshot.getClassId().getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    Set<String> lookupNamesInScope = lookupSymbolSet.getLookupNamesInScope(packageFqName);
                    if (!lookupNamesInScope.isEmpty()) {
                        if (z) {
                            constantNames = ((PackageFacadeKotlinClassSnapshot) accessibleClassSnapshot).getPackageMemberNames();
                        } else {
                            Intrinsics.checkNotNull(accessibleClassSnapshot, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.classpathDiff.MultifileClassKotlinClassSnapshot");
                            constantNames = ((MultifileClassKotlinClassSnapshot) accessibleClassSnapshot).getConstantNames();
                        }
                        FqName packageFqName2 = accessibleClassSnapshot.getClassId().getPackageFqName();
                        Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                        collector.addPackageMembers(packageFqName2, CollectionsKt.intersect(constantNames, lookupNamesInScope));
                    }
                }
            }
        }
        return collector.getResult();
    }

    public static final ChangesEither.Known toChangesEither(ProgramSymbolSet programSymbolSet) {
        FqName packageFqName;
        Intrinsics.checkNotNullParameter(programSymbolSet, "<this>");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (ProgramSymbol programSymbol : programSymbolSet.asSequence()) {
            linkedHashSet.add(toLookupSymbol(programSymbol));
            if (programSymbol instanceof ClassSymbol) {
                packageFqName = ((ClassSymbol) programSymbol).getClassId().asSingleFqName();
            } else if (programSymbol instanceof ClassMember) {
                packageFqName = ((ClassMember) programSymbol).getClassId().asSingleFqName();
            } else {
                if (!(programSymbol instanceof PackageMember)) {
                    throw new NoWhenBranchMatchedException();
                }
                packageFqName = ((PackageMember) programSymbol).getPackageFqName();
            }
            Intrinsics.checkNotNull(packageFqName);
            linkedHashSet2.add(packageFqName);
        }
        return new ChangesEither.Known(linkedHashSet, linkedHashSet2);
    }
}
