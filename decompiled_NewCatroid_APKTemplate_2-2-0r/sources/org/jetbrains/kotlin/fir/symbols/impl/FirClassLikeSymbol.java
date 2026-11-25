package org.jetbrains.kotlin.fir.symbols.impl;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.declarations.DeprecationsPerUseSite;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirVersionRequirementsTableKeyKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirClassLikeSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u000f\b\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020 H&J\b\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\u0082\u0001\u0002#$¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "rawStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getRawStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "resolvedStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "getResolvedStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "typeParameterSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getTypeParameterSymbols", "()Ljava/util/List;", "getOwnDeprecation", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "toLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "toString", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirClassLikeSymbol<D extends FirClassLikeDeclaration> extends FirClassifierSymbol<D> implements ClassLikeSymbolMarker {
    private final ClassId classId;

    public /* synthetic */ FirClassLikeSymbol(ClassId classId, DefaultConstructorMarker defaultConstructorMarker) {
        this(classId);
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol
    /* JADX INFO: renamed from: toLookupTag */
    public abstract ConeClassLikeLookupTag getLookupTag();

    public final ClassId getClassId() {
        return this.classId;
    }

    private FirClassLikeSymbol(ClassId classId) {
        super(null);
        this.classId = classId;
    }

    public final Name getName() {
        Name shortClassName = this.classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        return shortClassName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
    public final DeprecationsPerUseSite getOwnDeprecation(LanguageVersionSettings languageVersionSettings) {
        List<VersionRequirement> versionRequirements;
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (getAnnotations().isEmpty() && ((versionRequirements = FirVersionRequirementsTableKeyKt.getVersionRequirements(getFir())) == null || versionRequirements.isEmpty())) {
            return null;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        return ((FirClassLikeDeclaration) getFir()).getDeprecationsProvider().getDeprecationsInfo(languageVersionSettings);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirDeclarationStatus getRawStatus() {
        return ((FirClassLikeDeclaration) getFir()).getStatus();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirResolvedDeclarationStatus getResolvedStatus() {
        return UtilsKt.resolvedStatus((FirMemberDeclaration) getFir());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirTypeParameterSymbol> getTypeParameterSymbols() {
        List<FirTypeParameterRef> typeParameters = ((FirClassLikeDeclaration) getFir()).getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameterRef) it.next()).getSymbol());
        }
        return arrayList;
    }

    public String toString() {
        return Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + StringUtil.SPACE + this.classId.asString();
    }
}
