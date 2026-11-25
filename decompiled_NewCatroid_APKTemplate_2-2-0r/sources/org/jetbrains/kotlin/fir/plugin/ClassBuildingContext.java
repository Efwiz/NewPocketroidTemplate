package org.jetbrains.kotlin.fir.plugin;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOriginKt;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirOuterClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u0014\u001a\u00020\u0002H\u0016J \u0010\u0015\u001a\u00020\u00162\u0018\u0010\u0017\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0004\u0012\u00020\u00130\u0010J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0013R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000e\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0004\u0012\u00020\u00130\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/ClassBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "superTypeProviders", "", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "superType", "", "typeProvider", ModuleXmlParser.TYPE, "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassBuildingContext extends DeclarationBuildingContext<FirRegularClass> {
    private final ClassId classId;
    private final ClassKind classKind;
    private final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> superTypeProviders;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassBuildingContext(FirSession session, GeneratedDeclarationKey key, FirClassSymbol<?> firClassSymbol, ClassId classId, ClassKind classKind) {
        super(session, key, firClassSymbol, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        this.classId = classId;
        this.classKind = classKind;
        this.superTypeProviders = new ArrayList();
    }

    public final void superType(final ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.superTypeProviders.add(new Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.ClassBuildingContext.superType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameterRef> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return type;
            }
        });
    }

    public final void superType(Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> typeProvider) {
        Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
        this.superTypeProviders.add(typeProvider);
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    public FirRegularClass build() {
        FirRegularClassBuilder firRegularClassBuilder = new FirRegularClassBuilder();
        firRegularClassBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firRegularClassBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
        firRegularClassBuilder.setOrigin(FirDeclarationOriginKt.getOrigin(getKey()));
        firRegularClassBuilder.setClassKind(this.classKind);
        firRegularClassBuilder.setScopeProvider(FirKotlinScopeProviderKt.getKotlinScopeProvider(getSession()));
        firRegularClassBuilder.setStatus(generateStatus());
        Name shortClassName = this.classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        firRegularClassBuilder.setName(shortClassName);
        firRegularClassBuilder.setSymbol(new FirRegularClassSymbol(this.classId));
        if (firRegularClassBuilder.getStatus().isInner()) {
            if (getOwner() == null) {
                throw new IllegalArgumentException("Inner class must have owner".toString());
            }
            List<FirTypeParameterSymbol> typeParameterSymbols = getOwner().getTypeParameterSymbols();
            List<FirTypeParameterRef> typeParameters = firRegularClassBuilder.getTypeParameters();
            for (FirTypeParameterSymbol firTypeParameterSymbol : typeParameterSymbols) {
                FirOuterClassTypeParameterRefBuilder firOuterClassTypeParameterRefBuilder = new FirOuterClassTypeParameterRefBuilder();
                firOuterClassTypeParameterRefBuilder.setSymbol(firTypeParameterSymbol);
                typeParameters.add(firOuterClassTypeParameterRefBuilder.build());
            }
        }
        List<DeclarationBuildingContext.TypeParameterData> typeParameters2 = getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
        Iterator<T> it = typeParameters2.iterator();
        while (it.hasNext()) {
            arrayList.add(generateTypeParameter((DeclarationBuildingContext.TypeParameterData) it.next(), firRegularClassBuilder.getSymbol()));
        }
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addAll(firRegularClassBuilder.getTypeParameters(), arrayList2);
        initTypeParameterBounds(firRegularClassBuilder.getTypeParameters(), arrayList2);
        if (this.superTypeProviders.isEmpty()) {
            firRegularClassBuilder.getSuperTypeRefs().add(getSession().getBuiltinTypes().getAnyType());
        } else {
            List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> list = this.superTypeProviders;
            List<FirTypeRef> superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                Function1 function1 = (Function1) it2.next();
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setType((ConeKotlinType) function1.invoke(firRegularClassBuilder.getTypeParameters()));
                superTypeRefs.add(firResolvedTypeRefBuilder.mo6809build());
            }
        }
        FirRegularClass firRegularClassMo6809build = firRegularClassBuilder.mo6809build();
        FirClassSymbol<?> owner = getOwner();
        if (owner != null && owner.getClassId().isLocal()) {
            ClassMembersKt.setContainingClassForLocalAttr(firRegularClassMo6809build, getOwner().getLookupTag());
        }
        return firRegularClassMo6809build;
    }
}
