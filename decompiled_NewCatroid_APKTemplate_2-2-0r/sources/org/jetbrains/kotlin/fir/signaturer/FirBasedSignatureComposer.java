package org.jetbrains.kotlin.fir.signaturer;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaTrack;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.backend.CodeFragmentConversionData;
import org.jetbrains.kotlin.fir.backend.CodeFragmentUtilsKt;
import org.jetbrains.kotlin.fir.backend.FirMangler;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirBasedSignatureComposer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002-.B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\tH\u0002J$\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014J&\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u0012J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u0019J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u001aJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u001bJ$\u0010\u001c\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0016\u001a\u00020\u001d2\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0018\u001a\u00020\u0012H\u0002J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u000eJ\u001c\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#2\u0006\u0010\u0016\u001a\u00020\u001dH\u0002J\u0010\u0010&\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u001dH\u0002J\u0014\u0010'\u001a\u00020(*\u00020\u001d2\u0006\u0010)\u001a\u00020\nH\u0002J\f\u0010*\u001a\u00020+*\u00020\u0010H\u0002J\f\u0010,\u001a\u00020+*\u00020\u0010H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;", "", "mangler", "Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "(Lorg/jetbrains/kotlin/fir/backend/FirMangler;)V", "getMangler", "()Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "signatureCache", "", "Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer$FirDeclarationWithParentId;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "calculatePublicSignature", "declarationWithParentId", "composeAccessorSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isSetter", "", "containingClass", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "composeSignature", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "forceExpect", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "composeSignatureImpl", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "composeTypeParameterSignature", "index", "", "containerSignature", "computeSignatureHashAndDescriptionFor", "Lkotlin/Pair;", "", "", "isTopLevelPrivate", "fakeFileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "commonSignature", "getterOrDefault", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "setterOrDefault", "FirDeclarationWithParentId", "SignatureBuilder", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBasedSignatureComposer {
    private final FirMangler mangler;
    private final Map<FirDeclarationWithParentId, IdSignature.CommonSignature> signatureCache;

    /* JADX INFO: compiled from: FirBasedSignatureComposer.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer$FirDeclarationWithParentId;", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "forceExpect", "", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/name/ClassId;Z)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getDeclaration", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getForceExpect", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FirDeclarationWithParentId {
        private final ClassId classId;
        private final FirDeclaration declaration;
        private final boolean forceExpect;

        public static /* synthetic */ FirDeclarationWithParentId copy$default(FirDeclarationWithParentId firDeclarationWithParentId, FirDeclaration firDeclaration, ClassId classId, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                firDeclaration = firDeclarationWithParentId.declaration;
            }
            if ((i & 2) != 0) {
                classId = firDeclarationWithParentId.classId;
            }
            if ((i & 4) != 0) {
                z = firDeclarationWithParentId.forceExpect;
            }
            return firDeclarationWithParentId.copy(firDeclaration, classId, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FirDeclaration getDeclaration() {
            return this.declaration;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ClassId getClassId() {
            return this.classId;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getForceExpect() {
            return this.forceExpect;
        }

        public final FirDeclarationWithParentId copy(FirDeclaration declaration, ClassId classId, boolean forceExpect) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return new FirDeclarationWithParentId(declaration, classId, forceExpect);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FirDeclarationWithParentId)) {
                return false;
            }
            FirDeclarationWithParentId firDeclarationWithParentId = (FirDeclarationWithParentId) other;
            return Intrinsics.areEqual(this.declaration, firDeclarationWithParentId.declaration) && Intrinsics.areEqual(this.classId, firDeclarationWithParentId.classId) && this.forceExpect == firDeclarationWithParentId.forceExpect;
        }

        public int hashCode() {
            int iHashCode = this.declaration.hashCode() * 31;
            ClassId classId = this.classId;
            return ((iHashCode + (classId == null ? 0 : classId.hashCode())) * 31) + Boolean.hashCode(this.forceExpect);
        }

        public String toString() {
            return "FirDeclarationWithParentId(declaration=" + this.declaration + ", classId=" + this.classId + ", forceExpect=" + this.forceExpect + ')';
        }

        public FirDeclarationWithParentId(FirDeclaration declaration, ClassId classId, boolean z) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            this.declaration = declaration;
            this.classId = classId;
            this.forceExpect = z;
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        public final FirDeclaration getDeclaration() {
            return this.declaration;
        }

        public final boolean getForceExpect() {
            return this.forceExpect;
        }
    }

    public FirBasedSignatureComposer(FirMangler mangler) {
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        this.mangler = mangler;
        this.signatureCache = new LinkedHashMap();
    }

    public final FirMangler getMangler() {
        return this.mangler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<Long, String> computeSignatureHashAndDescriptionFor(FirDeclaration declaration) {
        FirMangler firMangler = this.mangler;
        String strSignatureString = firMangler.signatureString(declaration, false);
        return TuplesKt.to(Long.valueOf(firMangler.getHashMangle(strSignatureString)), strSignatureString);
    }

    public final IdSignature composeSignature(FirClassLikeDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return composeSignatureImpl(declaration, null, false);
    }

    public static /* synthetic */ IdSignature composeSignature$default(FirBasedSignatureComposer firBasedSignatureComposer, FirCallableDeclaration firCallableDeclaration, ConeClassLikeLookupTag coneClassLikeLookupTag, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            coneClassLikeLookupTag = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return firBasedSignatureComposer.composeSignature(firCallableDeclaration, coneClassLikeLookupTag, z);
    }

    public final IdSignature composeSignature(FirCallableDeclaration declaration, ConeClassLikeLookupTag containingClass, boolean forceExpect) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return composeSignatureImpl(declaration, containingClass, forceExpect);
    }

    public final IdSignature composeSignature(FirScript declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return composeSignatureImpl(declaration, null, false);
    }

    public final IdSignature composeSignature(FirCodeFragment declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return composeSignatureImpl(declaration, null, false);
    }

    public final IdSignature composeTypeParameterSignature(int index, IdSignature containerSignature) {
        if (containerSignature == null) {
            return null;
        }
        return new IdSignature.CompositeSignature(containerSignature, new IdSignature.LocalSignature(MangleConstant.TYPE_PARAMETER_MARKER_NAME, Long.valueOf(index), null));
    }

    public static /* synthetic */ IdSignature composeAccessorSignature$default(FirBasedSignatureComposer firBasedSignatureComposer, FirProperty firProperty, boolean z, ConeClassLikeLookupTag coneClassLikeLookupTag, int i, Object obj) {
        if ((i & 4) != 0) {
            coneClassLikeLookupTag = null;
        }
        return firBasedSignatureComposer.composeAccessorSignature(firProperty, z, coneClassLikeLookupTag);
    }

    public final IdSignature composeAccessorSignature(FirProperty property, boolean isSetter, ConeClassLikeLookupTag containingClass) {
        IdSignature.CommonSignature commonSignature;
        FirPropertyAccessor firPropertyAccessor;
        Intrinsics.checkNotNullParameter(property, "property");
        IdSignature idSignatureComposeSignature$default = composeSignature$default(this, property, containingClass, false, 4, null);
        IdSignature.FileSignature fileSignature = null;
        if (idSignatureComposeSignature$default instanceof IdSignature.CompositeSignature) {
            IdSignature.CompositeSignature compositeSignature = (IdSignature.CompositeSignature) idSignatureComposeSignature$default;
            IdSignature inner = compositeSignature.getInner();
            commonSignature = inner instanceof IdSignature.CommonSignature ? (IdSignature.CommonSignature) inner : null;
            if (commonSignature == null) {
                return null;
            }
            IdSignature container = compositeSignature.getContainer();
            IdSignature.FileSignature fileSignature2 = container instanceof IdSignature.FileSignature ? (IdSignature.FileSignature) container : null;
            if (fileSignature2 == null) {
                return null;
            }
            fileSignature = fileSignature2;
        } else {
            if (!(idSignatureComposeSignature$default instanceof IdSignature.CommonSignature)) {
                return null;
            }
            commonSignature = (IdSignature.CommonSignature) idSignatureComposeSignature$default;
        }
        if (isSetter) {
            firPropertyAccessor = setterOrDefault(property);
        } else {
            firPropertyAccessor = getterOrDefault(property);
        }
        Pair<Long, String> pairComputeSignatureHashAndDescriptionFor = computeSignatureHashAndDescriptionFor(firPropertyAccessor);
        IdSignature.AccessorSignature accessorSignature = new IdSignature.AccessorSignature(commonSignature, new IdSignature.CommonSignature(commonSignature.getPackageFqName(), commonSignature.getDeclarationFqName() + '.' + FirManglerUtilKt.getIrName(firPropertyAccessor), Long.valueOf(pairComputeSignatureHashAndDescriptionFor.component1().longValue()), commonSignature.getMask(), pairComputeSignatureHashAndDescriptionFor.component2()));
        if (fileSignature != null) {
            return new IdSignature.CompositeSignature(fileSignature, accessorSignature);
        }
        return accessorSignature;
    }

    private final IdSignature composeSignatureImpl(FirDeclaration declaration, ConeClassLikeLookupTag containingClass, boolean forceExpect) {
        ClassId classId;
        ClassId classId2;
        if ((declaration instanceof FirAnonymousObject) || (declaration instanceof FirAnonymousFunction)) {
            return null;
        }
        if ((declaration instanceof FirRegularClass) && FirDeclarationUtilKt.getClassId((FirClassLikeDeclaration) declaration).isLocal()) {
            return null;
        }
        if (declaration instanceof FirCallableDeclaration) {
            if (Intrinsics.areEqual(((FirMemberDeclaration) declaration).getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                return null;
            }
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableDeclaration) declaration);
            if ((coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null && (classId2 = coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId()) != null && classId2.isLocal()) || (containingClass != null && (classId = containingClass.getClassId()) != null && classId.isLocal())) {
                return null;
            }
        }
        FirDeclarationWithParentId firDeclarationWithParentId = new FirDeclarationWithParentId(declaration, containingClass != null ? containingClass.getClassId() : null, forceExpect);
        Map<FirDeclarationWithParentId, IdSignature.CommonSignature> map = this.signatureCache;
        IdSignature.CommonSignature commonSignatureCalculatePublicSignature = map.get(firDeclarationWithParentId);
        if (commonSignatureCalculatePublicSignature == null) {
            commonSignatureCalculatePublicSignature = calculatePublicSignature(firDeclarationWithParentId);
            map.put(firDeclarationWithParentId, commonSignatureCalculatePublicSignature);
        }
        IdSignature.CommonSignature commonSignature = commonSignatureCalculatePublicSignature;
        if (isTopLevelPrivate(declaration)) {
            return new IdSignature.CompositeSignature(fakeFileSignature(declaration, commonSignature), commonSignature);
        }
        return commonSignature;
    }

    private final IdSignature.CommonSignature calculatePublicSignature(FirDeclarationWithParentId declarationWithParentId) {
        FqName packageName;
        String strAsString;
        FqName relativeClassName;
        FqName fqNameChild;
        FirDeclaration declaration = declarationWithParentId.getDeclaration();
        ClassId classId = declarationWithParentId.getClassId();
        SignatureBuilder signatureBuilder = new SignatureBuilder(declarationWithParentId.getForceExpect());
        try {
            declaration.accept(signatureBuilder, null);
            if (declaration instanceof FirRegularClass) {
                ClassId classId2 = FirDeclarationUtilKt.getClassId((FirClassLikeDeclaration) declaration);
                String strAsString2 = classId2.getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                String strAsString3 = classId2.getRelativeClassName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                return new IdSignature.CommonSignature(strAsString2, strAsString3, signatureBuilder.getHashId(), signatureBuilder.getMask(), signatureBuilder.getDescription());
            }
            if (declaration instanceof FirTypeAlias) {
                ClassId classId3 = ((FirTypeAlias) declaration).getSymbol().getClassId();
                String strAsString4 = classId3.getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                String strAsString5 = classId3.getRelativeClassName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString5, "asString(...)");
                return new IdSignature.CommonSignature(strAsString4, strAsString5, signatureBuilder.getHashId(), signatureBuilder.getMask(), signatureBuilder.getDescription());
            }
            if (declaration instanceof FirCallableDeclaration) {
                if (classId == null) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableDeclaration) declaration);
                    classId = coneClassLikeLookupTagContainingClassLookupTag != null ? coneClassLikeLookupTagContainingClassLookupTag.getClassId() : null;
                }
                if (classId == null || (packageName = classId.getPackageFqName()) == null) {
                    packageName = ((FirCallableDeclaration) declaration).getSymbol().getCallableId().getPackageName();
                }
                Intrinsics.checkNotNull(packageName);
                Name irName = FirManglerUtilKt.getIrName((FirCallableDeclaration) declaration);
                String strAsString6 = packageName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString6, "asString(...)");
                if (classId == null || (relativeClassName = classId.getRelativeClassName()) == null || (fqNameChild = relativeClassName.child(irName)) == null || (strAsString = fqNameChild.asString()) == null) {
                    strAsString = irName.asString();
                }
                String str = strAsString;
                Intrinsics.checkNotNull(str);
                return new IdSignature.CommonSignature(strAsString6, str, signatureBuilder.getHashId(), signatureBuilder.getMask(), signatureBuilder.getDescription());
            }
            if (declaration instanceof FirScript) {
                FirScript firScript = (FirScript) declaration;
                String strAsString7 = firScript.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString7, "asString(...)");
                String strAsString8 = firScript.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString8, "asString(...)");
                return new IdSignature.CommonSignature(strAsString7, strAsString8, signatureBuilder.getHashId(), signatureBuilder.getMask(), signatureBuilder.getDescription());
            }
            if (declaration instanceof FirCodeFragment) {
                CodeFragmentConversionData conversionData = CodeFragmentUtilsKt.getConversionData((FirCodeFragment) declaration);
                String strAsString9 = conversionData.getClassId().getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString9, "asString(...)");
                String strAsString10 = conversionData.getClassId().getRelativeClassName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString10, "asString(...)");
                return new IdSignature.CommonSignature(strAsString9, strAsString10, signatureBuilder.getHashId(), signatureBuilder.getMask(), null);
            }
            throw new IllegalStateException(("Unsupported FIR declaration in signature composer: " + UtilsKt.render(declaration)).toString());
        } catch (Throwable th) {
            throw new IllegalStateException("Error while composing signature for " + UtilsKt.render(declaration), th);
        }
    }

    /* JADX INFO: compiled from: FirBasedSignatureComposer.kt */
    @Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0005H\u0002J\u0010\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001a\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\"\u001a\u00020\u00022\u0006\u0010#\u001a\u00020$2\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010%\u001a\u00020\u00022\u0006\u0010&\u001a\u00020'2\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010(\u001a\u00020\u00022\u0006\u0010)\u001a\u00020*2\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010+\u001a\u00020\u00022\u0006\u0010,\u001a\u00020-2\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010.\u001a\u00020\u00022\u0006\u0010/\u001a\u0002002\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00101\u001a\u00020\u00022\u0006\u00102\u001a\u0002032\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00104\u001a\u00020\u00022\u0006\u00105\u001a\u0002062\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00107\u001a\u00020\u00022\u0006\u00108\u001a\u0002092\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010:\u001a\u00020\u00022\u0006\u0010;\u001a\u00020<2\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer$SignatureBuilder;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "", "", "forceExpect", "", "(Lorg/jetbrains/kotlin/fir/signaturer/FirBasedSignatureComposer;Z)V", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "hashId", "", "getHashId", "()Ljava/lang/Long;", "setHashId", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "mask", "getMask", "()J", "setMask", "(J)V", "setExpected", "f", "setHashAndDescriptionFor", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "visitCodeFragment", "codeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "data", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class SignatureBuilder extends FirVisitor<Unit, Object> {
        private String description;
        private final boolean forceExpect;
        private Long hashId;
        private long mask;

        /* JADX INFO: renamed from: visitCodeFragment, reason: avoid collision after fix types in other method */
        public void visitCodeFragment2(FirCodeFragment codeFragment, Object data) {
            Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        }

        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
        public void visitScript2(FirScript script, Object data) {
            Intrinsics.checkNotNullParameter(script, "script");
        }

        public SignatureBuilder(boolean z) {
            this.forceExpect = z;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitCodeFragment(FirCodeFragment firCodeFragment, Object obj) {
            visitCodeFragment2(firCodeFragment, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitConstructor(FirConstructor firConstructor, Object obj) {
            visitConstructor2(firConstructor, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitElement(FirElement firElement, Object obj) {
            visitElement2(firElement, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitEnumEntry(FirEnumEntry firEnumEntry, Object obj) {
            visitEnumEntry2(firEnumEntry, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitField(FirField firField, Object obj) {
            visitField2(firField, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitProperty(FirProperty firProperty, Object obj) {
            visitProperty2(firProperty, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitRegularClass(FirRegularClass firRegularClass, Object obj) {
            visitRegularClass2(firRegularClass, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitScript(FirScript firScript, Object obj) {
            visitScript2(firScript, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction(FirSimpleFunction firSimpleFunction, Object obj) {
            visitSimpleFunction2(firSimpleFunction, obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeAlias(FirTypeAlias firTypeAlias, Object obj) {
            visitTypeAlias2(firTypeAlias, obj);
            return Unit.INSTANCE;
        }

        public final Long getHashId() {
            return this.hashId;
        }

        public final void setHashId(Long l) {
            this.hashId = l;
        }

        public final String getDescription() {
            return this.description;
        }

        public final void setDescription(String str) {
            this.description = str;
        }

        public final long getMask() {
            return this.mask;
        }

        public final void setMask(long j) {
            this.mask = j;
        }

        private final void setHashAndDescriptionFor(FirDeclaration declaration) {
            Pair pairComputeSignatureHashAndDescriptionFor = FirBasedSignatureComposer.this.computeSignatureHashAndDescriptionFor(declaration);
            this.hashId = (Long) pairComputeSignatureHashAndDescriptionFor.getFirst();
            this.description = (String) pairComputeSignatureHashAndDescriptionFor.getSecond();
        }

        private final void setExpected(boolean f) {
            this.mask |= IdSignature.Flags.IS_EXPECT.encode(f || this.forceExpect);
        }

        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(FirElement element, Object data) {
            Intrinsics.checkNotNullParameter(element, "element");
            throw new NotImplementedError("An operation is not implemented: Should not be here");
        }

        /* JADX INFO: renamed from: visitRegularClass, reason: avoid collision after fix types in other method */
        public void visitRegularClass2(FirRegularClass regularClass, Object data) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            setExpected(regularClass.getStatus().isExpect());
        }

        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
        public void visitTypeAlias2(FirTypeAlias typeAlias, Object data) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            setExpected(typeAlias.getStatus().isExpect());
        }

        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
        public void visitConstructor2(FirConstructor constructor, Object data) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            setHashAndDescriptionFor(constructor);
            setExpected(constructor.getStatus().isExpect());
        }

        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public void visitSimpleFunction2(FirSimpleFunction simpleFunction, Object data) {
            Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            setHashAndDescriptionFor(simpleFunction);
            setExpected(simpleFunction.getStatus().isExpect());
        }

        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public void visitProperty2(FirProperty property, Object data) {
            Intrinsics.checkNotNullParameter(property, "property");
            setHashAndDescriptionFor(property);
            setExpected(property.getStatus().isExpect());
        }

        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
        public void visitField2(FirField field, Object data) {
            Intrinsics.checkNotNullParameter(field, "field");
            setHashAndDescriptionFor(field);
            setExpected(field.getStatus().isExpect());
        }

        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
        public void visitEnumEntry2(FirEnumEntry enumEntry, Object data) {
            Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            setExpected(enumEntry.getStatus().isExpect());
        }
    }

    private final boolean isTopLevelPrivate(FirDeclaration declaration) {
        return FirVisibilityCheckerKt.getOwnerLookupTag(declaration.getSymbol()) == null && (declaration instanceof FirMemberDeclaration) && Intrinsics.areEqual(((FirMemberDeclaration) declaration).getStatus().getVisibility(), Visibilities.Private.INSTANCE);
    }

    private final IdSignature.FileSignature fakeFileSignature(FirDeclaration firDeclaration, IdSignature.CommonSignature commonSignature) {
        return new IdSignature.FileSignature(firDeclaration, new FqName(commonSignature.getPackageFqName() + '.' + commonSignature.getDeclarationFqName()), "<unknown>");
    }

    private final FirPropertyAccessor getterOrDefault(FirProperty firProperty) {
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter != null) {
            return getter;
        }
        return new FirDefaultPropertyGetter(null, firProperty.getModuleData(), firProperty.getOrigin(), firProperty.getReturnTypeRef(), firProperty.getStatus().getVisibility(), firProperty.getSymbol(), null, null, null, null, XKeySym.XK_Amacron, null);
    }

    private final FirPropertyAccessor setterOrDefault(FirProperty firProperty) {
        FirPropertyAccessor setter = firProperty.getSetter();
        if (setter != null) {
            return setter;
        }
        return new FirDefaultPropertySetter(null, firProperty.getModuleData(), firProperty.getOrigin(), firProperty.getReturnTypeRef(), firProperty.getStatus().getVisibility(), firProperty.getSymbol(), null, null, null, null, null, 1984, null);
    }
}
