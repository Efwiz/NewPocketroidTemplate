package org.jetbrains.kotlin.fir;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.builder.FirEmptyExpressionBlockBuilderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: EnumClassUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a6\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a6\u0010\u0010\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a6\u0010\u0011\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f¨\u0006\u0012"}, d2 = {"createStatus", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl;", "parentStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "generateEntriesGetter", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "classFqName", "makeExpect", "", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "generateValueOfFunction", "generateValuesFunction", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EnumClassUtilsKt {
    public static /* synthetic */ void generateValuesFunction$default(FirRegularClassBuilder firRegularClassBuilder, FirModuleData firModuleData, FqName fqName, FqName fqName2, boolean z, FirDeclarationOrigin firDeclarationOrigin, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            firDeclarationOrigin = FirDeclarationOrigin.Source.INSTANCE;
        }
        generateValuesFunction(firRegularClassBuilder, firModuleData, fqName, fqName2, z2, firDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void generateValuesFunction(FirRegularClassBuilder firRegularClassBuilder, FirModuleData moduleData, FqName packageFqName, FqName classFqName, boolean z, FirDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        Intrinsics.checkNotNullParameter(origin, "origin");
        KtSourceElement source = firRegularClassBuilder.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.EnumGeneratedDeclaration.INSTANCE, 0, 0, 6, null) : null;
        List<FirDeclaration> declarations = firRegularClassBuilder.getDeclarations();
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(ktSourceElementFakeElement$default);
        firSimpleFunctionBuilder.setOrigin(origin);
        firSimpleFunctionBuilder.setModuleData(moduleData);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getArray()), new ConeClassLikeTypeImpl[]{new ConeClassLikeTypeImpl(firRegularClassBuilder.getSymbol().getLookupTag(), new ConeTypeProjection[0], false, null, 8, null)}, false, null, 8, 0 == true ? 1 : 0));
        firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        firSimpleFunctionBuilder.setName(StandardNames.ENUM_VALUES);
        FirDeclarationStatusImpl firDeclarationStatusImplCreateStatus = createStatus(firRegularClassBuilder.getStatus());
        firDeclarationStatusImplCreateStatus.setStatic(true);
        firDeclarationStatusImplCreateStatus.setExpect(z);
        firSimpleFunctionBuilder.setStatus(firDeclarationStatusImplCreateStatus);
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(packageFqName, classFqName, StandardNames.ENUM_VALUES, null, 8, null)));
        firSimpleFunctionBuilder.setResolvePhase(firRegularClassBuilder.getResolvePhase());
        FirBlock firBlockBuildEmptyExpressionBlock = FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
        firBlockBuildEmptyExpressionBlock.replaceTypeRef(firSimpleFunctionBuilder.getReturnTypeRef());
        firSimpleFunctionBuilder.setBody(firBlockBuildEmptyExpressionBlock);
        FirSimpleFunction firSimpleFunctionBuild = firSimpleFunctionBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firSimpleFunctionBuild, firRegularClassBuilder.getSymbol().getLookupTag());
        declarations.add(firSimpleFunctionBuild);
    }

    public static /* synthetic */ void generateValueOfFunction$default(FirRegularClassBuilder firRegularClassBuilder, FirModuleData firModuleData, FqName fqName, FqName fqName2, boolean z, FirDeclarationOrigin firDeclarationOrigin, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            firDeclarationOrigin = FirDeclarationOrigin.Source.INSTANCE;
        }
        generateValueOfFunction(firRegularClassBuilder, firModuleData, fqName, fqName2, z2, firDeclarationOrigin);
    }

    public static final void generateValueOfFunction(FirRegularClassBuilder firRegularClassBuilder, FirModuleData moduleData, FqName packageFqName, FqName classFqName, boolean z, FirDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        Intrinsics.checkNotNullParameter(origin, "origin");
        KtSourceElement source = firRegularClassBuilder.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.EnumGeneratedDeclaration.INSTANCE, 0, 0, 6, null) : null;
        List<FirDeclaration> declarations = firRegularClassBuilder.getDeclarations();
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(ktSourceElementFakeElement$default);
        firSimpleFunctionBuilder.setOrigin(origin);
        firSimpleFunctionBuilder.setModuleData(moduleData);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
        int i = 8;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z2 = false;
        ConeAttributes coneAttributes = null;
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(firRegularClassBuilder.getSymbol().getLookupTag(), new ConeTypeProjection[0], z2, coneAttributes, i, defaultConstructorMarker));
        firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        firSimpleFunctionBuilder.setName(StandardNames.ENUM_VALUE_OF);
        FirDeclarationStatusImpl firDeclarationStatusImplCreateStatus = createStatus(firRegularClassBuilder.getStatus());
        firDeclarationStatusImplCreateStatus.setStatic(true);
        firDeclarationStatusImplCreateStatus.setExpect(z);
        firSimpleFunctionBuilder.setStatus(firDeclarationStatusImplCreateStatus);
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(packageFqName, classFqName, StandardNames.ENUM_VALUE_OF, null, 8, null)));
        List<FirValueParameter> valueParameters = firSimpleFunctionBuilder.getValueParameters();
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        firValueParameterBuilder.setSource(ktSourceElementFakeElement$default);
        firValueParameterBuilder.setContainingFunctionSymbol(firSimpleFunctionBuilder.getSymbol());
        firValueParameterBuilder.setOrigin(origin);
        firValueParameterBuilder.setModuleData(moduleData);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder2.setSource(ktSourceElementFakeElement$default);
        firResolvedTypeRefBuilder2.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getString()), new ConeTypeProjection[0], z2, coneAttributes, i, defaultConstructorMarker));
        firValueParameterBuilder.setReturnTypeRef(firResolvedTypeRefBuilder2.mo6809build());
        firValueParameterBuilder.setName(StandardNames.DEFAULT_VALUE_PARAMETER);
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(StandardNames.DEFAULT_VALUE_PARAMETER));
        firValueParameterBuilder.setCrossinline(false);
        firValueParameterBuilder.setNoinline(false);
        firValueParameterBuilder.setVararg(false);
        firValueParameterBuilder.setResolvePhase(firRegularClassBuilder.getResolvePhase());
        valueParameters.add(firValueParameterBuilder.mo6809build());
        firSimpleFunctionBuilder.setResolvePhase(firRegularClassBuilder.getResolvePhase());
        FirBlock firBlockBuildEmptyExpressionBlock = FirEmptyExpressionBlockBuilderKt.buildEmptyExpressionBlock();
        firBlockBuildEmptyExpressionBlock.replaceTypeRef(firSimpleFunctionBuilder.getReturnTypeRef());
        firSimpleFunctionBuilder.setBody(firBlockBuildEmptyExpressionBlock);
        FirSimpleFunction firSimpleFunctionBuild = firSimpleFunctionBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firSimpleFunctionBuild, firRegularClassBuilder.getSymbol().getLookupTag());
        declarations.add(firSimpleFunctionBuild);
    }

    public static /* synthetic */ void generateEntriesGetter$default(FirRegularClassBuilder firRegularClassBuilder, FirModuleData firModuleData, FqName fqName, FqName fqName2, boolean z, FirDeclarationOrigin firDeclarationOrigin, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            firDeclarationOrigin = FirDeclarationOrigin.Source.INSTANCE;
        }
        generateEntriesGetter(firRegularClassBuilder, firModuleData, fqName, fqName2, z2, firDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void generateEntriesGetter(FirRegularClassBuilder firRegularClassBuilder, FirModuleData moduleData, FqName packageFqName, FqName classFqName, boolean z, FirDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        Intrinsics.checkNotNullParameter(origin, "origin");
        KtSourceElement source = firRegularClassBuilder.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.EnumGeneratedDeclaration.INSTANCE, 0, 0, 6, null) : null;
        List<FirDeclaration> declarations = firRegularClassBuilder.getDeclarations();
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(ktSourceElementFakeElement$default);
        firPropertyBuilder.setVar(false);
        firPropertyBuilder.setLocal(false);
        firPropertyBuilder.setOrigin(origin);
        firPropertyBuilder.setModuleData(moduleData);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getEnumEntries()), new ConeClassLikeTypeImpl[]{new ConeClassLikeTypeImpl(firRegularClassBuilder.getSymbol().getLookupTag(), new ConeTypeProjection[0], false, null, 8, null)}, false, null, 8, 0 == true ? 1 : 0));
        firPropertyBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        firPropertyBuilder.setName(StandardNames.ENUM_ENTRIES);
        FirDeclarationStatusImpl firDeclarationStatusImplCreateStatus = createStatus(firRegularClassBuilder.getStatus());
        firDeclarationStatusImplCreateStatus.setStatic(true);
        firDeclarationStatusImplCreateStatus.setExpect(z);
        firPropertyBuilder.setStatus(firDeclarationStatusImplCreateStatus);
        firPropertyBuilder.setSymbol(new FirPropertySymbol(new CallableId(packageFqName, classFqName, StandardNames.ENUM_ENTRIES, null, 8, null)));
        firPropertyBuilder.setResolvePhase(firRegularClassBuilder.getResolvePhase());
        FirDefaultPropertyGetter firDefaultPropertyGetter = new FirDefaultPropertyGetter(ktSourceElementFakeElement$default != null ? KtSourceElementKt.fakeElement$default(ktSourceElementFakeElement$default, KtFakeSourceElementKind.EnumGeneratedDeclaration.INSTANCE, 0, 0, 6, null) : null, moduleData, origin, UtilsKt.copyWithNewSourceKind(firPropertyBuilder.getReturnTypeRef(), KtFakeSourceElementKind.EnumGeneratedDeclaration.INSTANCE), Visibilities.Public.INSTANCE, firPropertyBuilder.getSymbol(), null, null, null, firRegularClassBuilder.getResolvePhase(), XKeySym.XK_Racute, null);
        FirDeclarationStatusImpl firDeclarationStatusImplCreateStatus2 = createStatus(firRegularClassBuilder.getStatus());
        firDeclarationStatusImplCreateStatus2.setStatic(true);
        firDefaultPropertyGetter.setStatus(firDeclarationStatusImplCreateStatus2);
        firPropertyBuilder.setGetter(firDefaultPropertyGetter);
        FirProperty firPropertyBuild = firPropertyBuilder.mo6809build();
        ClassMembersKt.setContainingClassForStaticMemberAttr(firPropertyBuild, firRegularClassBuilder.getSymbol().getLookupTag());
        declarations.add(firPropertyBuild);
    }

    private static final FirDeclarationStatusImpl createStatus(FirDeclarationStatus firDeclarationStatus) {
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = firDeclarationStatus instanceof FirResolvedDeclarationStatusImpl ? (FirResolvedDeclarationStatusImpl) firDeclarationStatus : null;
        EffectiveVisibility effectiveVisibility = firResolvedDeclarationStatusImpl != null ? firResolvedDeclarationStatusImpl.getEffectiveVisibility() : null;
        if (effectiveVisibility != null) {
            return new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL, effectiveVisibility);
        }
        return new FirDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL);
    }
}
