package org.jetbrains.kotlin.fir.java;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import okhttp3.HttpUrl;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.DummyHolderViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.ClsStubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassReferenceListElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiTypeParameterListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiTypeParameterStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor;
import org.jetbrains.kotlin.fir.resolve.transformers.SupertypeComputationSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirJavaElementFinder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u001a$\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a/\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0016H\u0002¢\u0006\u0002\u0010\u0017\u001a4\u0010\u0018\u001a\u00020\u00032\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u001e\u0010\u0019\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00160\u001b0\u001aH\u0002\u001a.\u0010\u001c\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001a2\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\u0012\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\u001a*\u00020\u0006H\u0002\u001a\u001a\u0010\u0002\u001a\u00020\u0003*\u00020\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002\u001a\u0014\u0010$\u001a\u00020\u0001*\u00020%2\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\u0014\u0010&\u001a\u00020\u0001*\u00020%2\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\u0014\u0010&\u001a\u00020\u0001*\u00020'2\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\u0014\u0010&\u001a\u00020\u0001*\u00020(2\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\f\u0010)\u001a\u00020**\u00020\u001fH\u0002\u001a\u001a\u0010+\u001a\b\u0012\u0004\u0012\u00020!0\u001a*\u00020\u001f2\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\u0016\u0010,\u001a\u0004\u0018\u00010\u001f*\u00020%2\u0006\u0010\"\u001a\u00020\u0006H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"ERROR_TYPE_STUB", "", "collectAllDependentSourceSessionsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/FirSession;", "dependencies", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "createJavaFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiJavaFileStub;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "newReferenceList", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/JavaClassReferenceListElementType;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", SpecTypePair.NAME_types, "", "(Lcom/intellij/psi/impl/java/stubs/JavaClassReferenceListElementType;Lcom/intellij/psi/stubs/StubElement;[Ljava/lang/String;)V", "newTypeParameterList", "parameters", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Pair;", "addSupertypesReferencesLists", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/impl/PsiClassStubImpl;", "firRegularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "session", "collectAllDependentSourceSessions", "mapToCanonicalNoExpansionString", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "mapToCanonicalString", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "packFlags", "", "resolveSupertypesOnAir", "toFirClass", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJavaElementFinderKt {

    /* JADX INFO: compiled from: FirJavaElementFinder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Modality.values().length];
            try {
                iArr[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Modality.ABSTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionKind.values().length];
            try {
                iArr2[ProjectionKind.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProjectionKind.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProjectionKind.INVARIANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<FirTypeRef> resolveSupertypesOnAir(FirRegularClass firRegularClass, FirSession firSession) {
        FirSupertypeResolverVisitor firSupertypeResolverVisitor = new FirSupertypeResolverVisitor(firSession, new SupertypeComputationSession(), new ScopeSession(), null, null, null, null, 120, null);
        FirFile firClassifierContainerFile = FirProviderKt.getFirProvider(firSession).getFirClassifierContainerFile(firRegularClass.getSymbol());
        FirFile useSiteFile = firSupertypeResolverVisitor.getUseSiteFile();
        try {
            firSupertypeResolverVisitor.setUseSiteFile(firClassifierContainerFile);
            return firSupertypeResolverVisitor.resolveSpecificClassLikeSupertypes(firRegularClass, firRegularClass.getSuperTypeRefs());
        } finally {
            firSupertypeResolverVisitor.setUseSiteFile(useSiteFile);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<FirSession> collectAllDependentSourceSessions(FirSession firSession) {
        ArrayList arrayList = new ArrayList();
        collectAllDependentSourceSessionsTo(firSession, arrayList);
        return arrayList;
    }

    private static final void collectAllDependentSourceSessionsTo(FirSession firSession, List<FirSession> list) {
        FirModuleData moduleData = FirModuleDataKt.getModuleData(firSession);
        collectAllDependentSourceSessionsTo(list, moduleData.getDependencies());
        collectAllDependentSourceSessionsTo(list, moduleData.getFriendDependencies());
        collectAllDependentSourceSessionsTo(list, moduleData.getDependsOnDependencies());
    }

    private static final void collectAllDependentSourceSessionsTo(List<FirSession> list, Collection<? extends FirModuleData> collection) {
        Iterator<? extends FirModuleData> it = collection.iterator();
        while (it.hasNext()) {
            FirSession session = it.next().getSession();
            if (session.getKind() == FirSession.Kind.Source) {
                list.add(session);
                collectAllDependentSourceSessionsTo(session, list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int packFlags(FirRegularClass firRegularClass) {
        int i;
        int i2;
        FirRegularClass firRegularClass2 = firRegularClass;
        Visibility visibility = firRegularClass2.getStatus().getVisibility();
        if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE)) {
            i = 2;
        } else if (Intrinsics.areEqual(visibility, Visibilities.Protected.INSTANCE)) {
            i = 4;
        } else {
            i = Intrinsics.areEqual(visibility, Visibilities.Public.INSTANCE) ? 1 : 4096;
        }
        Modality modality = firRegularClass2.getStatus().getModality();
        int i3 = modality == null ? -1 : WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
        if (i3 != 1) {
            i2 = i3 != 2 ? 0 : 1024;
        } else {
            i2 = 16;
        }
        int i4 = i | i2;
        return (!FirDeclarationUtilKt.getClassId(firRegularClass).isNestedClass() || firRegularClass2.getStatus().isInner()) ? i4 : i4 | 8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addSupertypesReferencesLists(PsiClassStubImpl<?> psiClassStubImpl, FirRegularClass firRegularClass, List<? extends FirTypeRef> list, FirSession firSession) {
        FirRegularClass firClass;
        List<? extends FirTypeRef> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (!(((FirTypeRef) it.next()) instanceof FirResolvedTypeRef)) {
                    throw new IllegalArgumentException(("Supertypes for light class " + psiClassStubImpl.getQualifiedName() + " are being added too early").toString());
                }
            }
        }
        boolean z = firRegularClass.getClassKind() == ClassKind.INTERFACE;
        ArrayList arrayList = new ArrayList();
        String str = null;
        for (FirTypeRef firTypeRef : list) {
            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null && (firClass = toFirClass(coneClassLikeType, firSession)) != null) {
                String strMapToCanonicalString = mapToCanonicalString(coneClassLikeType, firSession);
                if (z || firClass.getClassKind() == ClassKind.INTERFACE) {
                    arrayList.add(strMapToCanonicalString);
                } else {
                    str = strMapToCanonicalString;
                }
            }
        }
        if (psiClassStubImpl.isInterface()) {
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty() && psiClassStubImpl.isAnnotationType()) {
                arrayList.remove(CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION);
            }
            JavaClassReferenceListElementType EXTENDS_LIST = JavaStubElementTypes.EXTENDS_LIST;
            Intrinsics.checkNotNullExpressionValue(EXTENDS_LIST, "EXTENDS_LIST");
            PsiClassStubImpl<?> psiClassStubImpl2 = psiClassStubImpl;
            String[] stringArray = ArrayUtil.toStringArray(arrayList2);
            Intrinsics.checkNotNullExpressionValue(stringArray, "toStringArray(...)");
            newReferenceList(EXTENDS_LIST, psiClassStubImpl2, stringArray);
            JavaClassReferenceListElementType IMPLEMENTS_LIST = JavaStubElementTypes.IMPLEMENTS_LIST;
            Intrinsics.checkNotNullExpressionValue(IMPLEMENTS_LIST, "IMPLEMENTS_LIST");
            String[] EMPTY_STRING_ARRAY = ArrayUtil.EMPTY_STRING_ARRAY;
            Intrinsics.checkNotNullExpressionValue(EMPTY_STRING_ARRAY, "EMPTY_STRING_ARRAY");
            newReferenceList(IMPLEMENTS_LIST, psiClassStubImpl2, EMPTY_STRING_ARRAY);
            return;
        }
        if (str == null || Intrinsics.areEqual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, str) || (psiClassStubImpl.isEnum() && Intrinsics.areEqual("java/lang/Enum", str))) {
            JavaClassReferenceListElementType EXTENDS_LIST2 = JavaStubElementTypes.EXTENDS_LIST;
            Intrinsics.checkNotNullExpressionValue(EXTENDS_LIST2, "EXTENDS_LIST");
            String[] EMPTY_STRING_ARRAY2 = ArrayUtil.EMPTY_STRING_ARRAY;
            Intrinsics.checkNotNullExpressionValue(EMPTY_STRING_ARRAY2, "EMPTY_STRING_ARRAY");
            newReferenceList(EXTENDS_LIST2, psiClassStubImpl, EMPTY_STRING_ARRAY2);
        } else {
            JavaClassReferenceListElementType EXTENDS_LIST3 = JavaStubElementTypes.EXTENDS_LIST;
            Intrinsics.checkNotNullExpressionValue(EXTENDS_LIST3, "EXTENDS_LIST");
            newReferenceList(EXTENDS_LIST3, psiClassStubImpl, new String[]{str});
        }
        JavaClassReferenceListElementType IMPLEMENTS_LIST2 = JavaStubElementTypes.IMPLEMENTS_LIST;
        Intrinsics.checkNotNullExpressionValue(IMPLEMENTS_LIST2, "IMPLEMENTS_LIST");
        String[] stringArray2 = ArrayUtil.toStringArray(arrayList);
        Intrinsics.checkNotNullExpressionValue(stringArray2, "toStringArray(...)");
        newReferenceList(IMPLEMENTS_LIST2, psiClassStubImpl, stringArray2);
    }

    private static final void newReferenceList(JavaClassReferenceListElementType javaClassReferenceListElementType, StubElement<?> stubElement, String[] strArr) {
        new PsiClassReferenceListStubImpl(javaClassReferenceListElementType, stubElement, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void newTypeParameterList(StubElement<?> stubElement, List<? extends Pair<String, String[]>> list) {
        PsiTypeParameterListStubImpl psiTypeParameterListStubImpl = new PsiTypeParameterListStubImpl(stubElement);
        for (Pair<String, String[]> pair : list) {
            PsiTypeParameterStubImpl psiTypeParameterStubImpl = new PsiTypeParameterStubImpl(psiTypeParameterListStubImpl, pair.first);
            JavaClassReferenceListElementType EXTENDS_BOUND_LIST = JavaStubElementTypes.EXTENDS_BOUND_LIST;
            Intrinsics.checkNotNullExpressionValue(EXTENDS_BOUND_LIST, "EXTENDS_BOUND_LIST");
            String[] second = pair.second;
            Intrinsics.checkNotNullExpressionValue(second, "second");
            newReferenceList(EXTENDS_BOUND_LIST, psiTypeParameterStubImpl, second);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiJavaFileStub createJavaFileStub(final FqName fqName, PsiManager psiManager) {
        final PsiJavaFileStubImpl psiJavaFileStubImpl = new PsiJavaFileStubImpl(fqName.asString(), true);
        psiJavaFileStubImpl.setPsiFactory(ClsStubPsiFactory.INSTANCE);
        final DummyHolderViewProvider dummyHolderViewProvider = new DummyHolderViewProvider(psiManager);
        psiJavaFileStubImpl.setPsi(new ClsFileImpl(fqName, dummyHolderViewProvider) { // from class: org.jetbrains.kotlin.fir.java.FirJavaElementFinderKt$createJavaFileStub$fakeFile$1
            final /* synthetic */ FqName $packageFqName;

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            /* JADX INFO: renamed from: isPhysical */
            public boolean getIsPhysical() {
                return false;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(dummyHolderViewProvider);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl
            /* JADX INFO: renamed from: getStub, reason: from getter */
            public PsiJavaFileStubImpl get$javaFileStub() {
                return this.$javaFileStub;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner, org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
            public String getPackageName() {
                String strAsString = this.$packageFqName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        return psiJavaFileStubImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirRegularClass toFirClass(ConeClassLikeType coneClassLikeType, FirSession firSession) {
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, firSession, null, 2, null).getLookupTag(), firSession);
        FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        FirClassLikeDeclaration firClassLikeDeclaration = firClassSymbol != null ? (FirClassLikeDeclaration) firClassSymbol.getFir() : null;
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclaration;
        }
        return null;
    }

    private static final String mapToCanonicalString(ConeKotlinType coneKotlinType, FirSession firSession) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            return mapToCanonicalString((ConeClassLikeType) coneKotlinType, firSession);
        }
        if (!(coneKotlinType instanceof ConeTypeVariableType ? true : coneKotlinType instanceof ConeFlexibleType ? true : coneKotlinType instanceof ConeCapturedType ? true : coneKotlinType instanceof ConeDefinitelyNotNullType ? true : coneKotlinType instanceof ConeIntersectionType ? true : coneKotlinType instanceof ConeStubType ? true : coneKotlinType instanceof ConeIntegerLiteralType)) {
            if (!(coneKotlinType instanceof ConeLookupTagBasedType)) {
                throw new NoWhenBranchMatchedException();
            }
            String strAsString = ((ConeLookupTagBasedType) coneKotlinType).getLookupTag().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unexpected type: " + coneKotlinType.getClass(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, coneKotlinType);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    private static final String mapToCanonicalString(ConeClassLikeType coneClassLikeType, FirSession firSession) {
        return coneClassLikeType instanceof ConeErrorType ? CommonClassNames.JAVA_LANG_OBJECT : mapToCanonicalNoExpansionString(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, firSession, null, 2, null), firSession);
    }

    private static final String mapToCanonicalNoExpansionString(ConeClassLikeType coneClassLikeType, final FirSession firSession) {
        boolean zAreEqual = Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getArray());
        String strMapToCanonicalString = CommonClassNames.JAVA_LANG_OBJECT;
        if (zAreEqual) {
            StringBuilder sb = new StringBuilder();
            ConeTypeProjection coneTypeProjection = coneClassLikeType.getTypeArguments()[0];
            if (!(coneTypeProjection instanceof ConeStarProjection) && (coneTypeProjection instanceof ConeKotlinTypeProjection)) {
                if (coneTypeProjection.getKind() == ProjectionKind.IN) {
                    strMapToCanonicalString = CommonClassNames.JAVA_LANG_VOID;
                } else {
                    ConeKotlinType type = ((ConeKotlinTypeProjection) coneTypeProjection).getType();
                    Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
                    strMapToCanonicalString = mapToCanonicalString((ConeClassLikeType) type, firSession);
                }
            }
            return sb.append(strMapToCanonicalString).append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI).toString();
        }
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeContext.typeConstructor((SimpleTypeMarker) coneClassLikeType);
        PrimitiveType primitiveType = typeContext.getPrimitiveType(typeConstructorMarkerTypeConstructor);
        if (primitiveType != null) {
            String strAsString = JvmPrimitiveType.get(primitiveType).getWrapperFqName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        PrimitiveType primitiveArrayType = typeContext.getPrimitiveArrayType(typeConstructorMarkerTypeConstructor);
        if (primitiveArrayType != null) {
            return JvmPrimitiveType.get(primitiveArrayType).getJavaKeywordName() + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        FqNameUnsafe classFqNameUnsafe = typeContext.getClassFqNameUnsafe(typeConstructorMarkerTypeConstructor);
        if (classFqNameUnsafe == null) {
            return CommonClassNames.JAVA_LANG_OBJECT;
        }
        ClassId classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(classFqNameUnsafe);
        FqName fqNameAsSingleFqName = classIdMapKotlinToJava != null ? classIdMapKotlinToJava.asSingleFqName() : null;
        if (fqNameAsSingleFqName != null) {
            Intrinsics.checkNotNull(fqNameAsSingleFqName);
            classFqNameUnsafe = fqNameAsSingleFqName;
        }
        StringBuilder sbAppend = new StringBuilder().append(classFqNameUnsafe);
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        ConeTypeProjection[] coneTypeProjectionArr = Boolean.valueOf((typeArguments.length == 0) ^ true).booleanValue() ? typeArguments : null;
        String strJoinToString$default = coneTypeProjectionArr != null ? ArraysKt.joinToString$default(coneTypeProjectionArr, ", ", "<", ">", 0, (CharSequence) null, new Function1<ConeTypeProjection, CharSequence>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaElementFinderKt$mapToCanonicalNoExpansionString$1$4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ConeTypeProjection it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FirJavaElementFinderKt.mapToCanonicalString(it, firSession);
            }
        }, 24, (Object) null) : null;
        if (strJoinToString$default == null) {
            strJoinToString$default = "";
        }
        return sbAppend.append(strJoinToString$default).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String mapToCanonicalString(ConeTypeProjection coneTypeProjection, FirSession firSession) {
        String str;
        if (coneTypeProjection instanceof ConeStarProjection) {
            return TypeDescription.Generic.OfWildcardType.SYMBOL;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
            int i = WhenMappings.$EnumSwitchMapping$1[coneTypeProjection.getKind().ordinal()];
            if (i == 1) {
                throw new IllegalStateException("Should be handled in the case above".toString());
            }
            if (i == 2) {
                str = PsiWildcardType.SUPER_PREFIX;
            } else if (i == 3) {
                str = PsiWildcardType.EXTENDS_PREFIX;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                str = "";
            }
            return str + mapToCanonicalString(((ConeKotlinTypeProjection) coneTypeProjection).getType(), firSession);
        }
        return CommonClassNames.JAVA_LANG_OBJECT;
    }
}
