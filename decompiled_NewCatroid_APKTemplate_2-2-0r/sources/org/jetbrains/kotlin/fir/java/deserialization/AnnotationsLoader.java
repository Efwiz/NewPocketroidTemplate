package org.jetbrains.kotlin.fir.java.deserialization;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.SpecialJvmAnnotations;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.deserialization.FirEnumEntryDeserializerAccessUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirEnumEntryDeserializedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationArgumentMappingBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirClassReferenceExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirEnumEntryDeserializedAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirGetClassCallBuilder;
import org.jetbrains.kotlin.fir.java.JavaUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;

/* JADX INFO: compiled from: AnnotationsLoader.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001)B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fH\u0002J8\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\u0017H\u0002J%\u0010\u0018\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0000¢\u0006\u0002\b\u0019J)\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001f0\u001eH\u0000¢\u0006\u0002\b J\u0018\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\rH\u0002J\u0018\u0010%\u001a\u00020&2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\rH\u0002J\f\u0010'\u001a\u00020(*\u00020\nH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/AnnotationsLoader;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;)V", "isRepeatableWithImplicitContainer", "", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "argumentMap", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "loadAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "result", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "enumEntryReferenceCreator", "Lkotlin/Function2;", "loadAnnotationIfNotSpecial", "loadAnnotationIfNotSpecial$java", "loadAnnotationMethodDefaultValue", "methodSignature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "consumeResult", "Lkotlin/Function1;", "", "loadAnnotationMethodDefaultValue$java", "toEnumEntryReferenceExpressionUnresolved", "Lorg/jetbrains/kotlin/fir/expressions/FirEnumEntryDeserializedAccessExpression;", "classId", "name", "toEnumEntryReferenceExpressionWithResolve", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "toDefaultResolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "AnnotationsLoaderVisitorImpl", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsLoader {
    private final KotlinClassFinder kotlinClassFinder;
    private final FirSession session;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AnnotationsLoader.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b¢\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u0012\u0010\u000e\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\"\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0015H&J\u001c\u0010\u0016\u001a\u00020\u00172\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u0004H\u0016J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0016J\u001a\u0010\u001c\u001a\u00020\u00172\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u00020\u001dH\u0016J\"\u0010\u001e\u001a\u00020\u00172\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u0005H\u0016J\u001a\u0010!\u001a\u00020\u00172\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\"\u001a\u00020\u0006H&J\f\u0010#\u001a\u00020$*\u00020\u001dH\u0002R#\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/AnnotationsLoader$AnnotationsLoaderVisitorImpl;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "enumEntryReferenceCreator", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/java/deserialization/AnnotationsLoader;Lkotlin/jvm/functions/Function2;)V", "getEnumEntryReferenceCreator", "()Lkotlin/jvm/functions/Function2;", "visitNullNames", "", "getVisitNullNames", "()Z", "createConstant", "value", "", "guessArrayTypeIfNeeded", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "name", "arrayOfElements", "", "visit", "", "visitAnnotation", "classId", "visitArray", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnum", "enumClassId", "enumEntryName", "visitExpression", "expr", "toFirClassReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirClassReferenceExpression;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    abstract class AnnotationsLoaderVisitorImpl implements KotlinJvmBinaryClass.AnnotationArgumentVisitor {
        private final Function2<ClassId, Name, FirExpression> enumEntryReferenceCreator;
        final /* synthetic */ AnnotationsLoader this$0;

        public abstract boolean getVisitNullNames();

        public abstract FirTypeRef guessArrayTypeIfNeeded(Name name2, List<? extends FirExpression> arrayOfElements);

        public abstract void visitExpression(Name name2, FirExpression expr);

        /* JADX WARN: Multi-variable type inference failed */
        public AnnotationsLoaderVisitorImpl(AnnotationsLoader annotationsLoader, Function2<? super ClassId, ? super Name, ? extends FirExpression> enumEntryReferenceCreator) {
            Intrinsics.checkNotNullParameter(enumEntryReferenceCreator, "enumEntryReferenceCreator");
            this.this$0 = annotationsLoader;
            this.enumEntryReferenceCreator = enumEntryReferenceCreator;
        }

        public final Function2<ClassId, Name, FirExpression> getEnumEntryReferenceCreator() {
            return this.enumEntryReferenceCreator;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visit(Name name2, Object value) {
            visitExpression(name2, createConstant(value));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FirClassReferenceExpression toFirClassReferenceExpression(ClassLiteralValue classLiteralValue) {
            FirResolvedTypeRef defaultResolvedTypeRef = this.this$0.toDefaultResolvedTypeRef(TypeConstructionUtilsKt.toLookupTag(classLiteralValue.getClassId()));
            FirClassReferenceExpressionBuilder firClassReferenceExpressionBuilder = new FirClassReferenceExpressionBuilder();
            firClassReferenceExpressionBuilder.setClassTypeRef(defaultResolvedTypeRef);
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getKClass(), new ConeKotlinType[]{defaultResolvedTypeRef.getType()}, false, null, 4, null));
            firClassReferenceExpressionBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6810build());
            return firClassReferenceExpressionBuilder.mo6810build();
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visitEnum(Name name2, ClassId enumClassId, Name enumEntryName) {
            Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
            Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
            if (name2 != null || getVisitNullNames()) {
                visitExpression(name2, this.enumEntryReferenceCreator.invoke(enumClassId, enumEntryName));
            }
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name2) {
            if (name2 != null || getVisitNullNames()) {
                return new AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitArray$1(this, this.this$0, name2);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(final Name name2, ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            if (name2 == null && !getVisitNullNames()) {
                return null;
            }
            final ArrayList arrayList = new ArrayList();
            final KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorLoadAnnotation = this.this$0.loadAnnotation(classId, arrayList, this.enumEntryReferenceCreator);
            return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, name2, arrayList) { // from class: org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader$AnnotationsLoaderVisitorImpl$visitAnnotation$1
                private final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor $$delegate_0;
                final /* synthetic */ List<FirAnnotation> $list;
                final /* synthetic */ Name $name;
                final /* synthetic */ AnnotationsLoader.AnnotationsLoaderVisitorImpl this$0;

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visit(Name name3, Object value) {
                    this.$$delegate_0.visit(name3, value);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name name3, ClassId classId2) {
                    Intrinsics.checkNotNullParameter(classId2, "classId");
                    return this.$$delegate_0.visitAnnotation(name3, classId2);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name3) {
                    return this.$$delegate_0.visitArray(name3);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitClassLiteral(Name name3, ClassLiteralValue value) {
                    Intrinsics.checkNotNullParameter(value, "value");
                    this.$$delegate_0.visitClassLiteral(name3, value);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitEnum(Name name3, ClassId enumClassId, Name enumEntryName) {
                    Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                    Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                    this.$$delegate_0.visitEnum(name3, enumClassId, enumEntryName);
                }

                {
                    this.this$0 = this;
                    this.$name = name2;
                    this.$list = arrayList;
                    this.$$delegate_0 = this.$visitor;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitEnd() {
                    this.$visitor.visitEnd();
                    this.this$0.visitExpression(this.$name, (FirExpression) CollectionsKt.single((List) this.$list));
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FirExpression createConstant(Object value) {
            return JavaUtilsKt.createConstantOrError$default(value, this.this$0.session, null, 2, null);
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visitClassLiteral(Name name2, ClassLiteralValue value) {
            Intrinsics.checkNotNullParameter(value, "value");
            FirGetClassCallBuilder firGetClassCallBuilder = new FirGetClassCallBuilder();
            FirClassReferenceExpression firClassReferenceExpression = toFirClassReferenceExpression(value);
            firGetClassCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firClassReferenceExpression));
            firGetClassCallBuilder.setTypeRef(firClassReferenceExpression.getTypeRef());
            Unit unit = Unit.INSTANCE;
            visitExpression(name2, firGetClassCallBuilder.mo6810build());
        }
    }

    public AnnotationsLoader(FirSession session, KotlinClassFinder kotlinClassFinder) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.session = session;
        this.kotlinClassFinder = kotlinClassFinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(final ClassId annotationClassId, final List<FirAnnotation> result, Function2<? super ClassId, ? super Name, ? extends FirExpression> enumEntryReferenceCreator) {
        final ConeClassLikeLookupTagImpl lookupTag = TypeConstructionUtilsKt.toLookupTag(annotationClassId);
        return new AnnotationsLoaderVisitorImpl(enumEntryReferenceCreator) { // from class: org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.loadAnnotation.1
            private final Map<Name, FirExpression> argumentMap = new LinkedHashMap();
            private final ScopeSession scopeSession = new ScopeSession();
            private final boolean visitNullNames;

            @Override // org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.AnnotationsLoaderVisitorImpl
            public void visitExpression(Name name2, FirExpression expr) {
                Intrinsics.checkNotNullParameter(expr, "expr");
                if (name2 != null) {
                    this.argumentMap.put(name2, expr);
                }
            }

            @Override // org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.AnnotationsLoaderVisitorImpl
            public boolean getVisitNullNames() {
                return this.visitNullNames;
            }

            /* JADX WARN: Code duplicated, block: B:26:0x0061  */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.AnnotationsLoaderVisitorImpl
            public FirTypeRef guessArrayTypeIfNeeded(Name name2, List<? extends FirExpression> arrayOfElements) {
                FirRegularClassSymbol regularClassSymbolByClassId;
                FirTypeScope firTypeScopeUnsubstitutedScope;
                ImportedFromObjectOrStaticData importedFromObjectOrStaticData;
                FirCallableDeclaration original;
                Intrinsics.checkNotNullParameter(arrayOfElements, "arrayOfElements");
                if (name2 == null || (regularClassSymbolByClassId = FirSymbolProviderKt.getRegularClassSymbolByClassId(FirSymbolProviderKt.getSymbolProvider(AnnotationsLoader.this.session), annotationClassId)) == null) {
                    return null;
                }
                FirRegularClassSymbol firRegularClassSymbol = regularClassSymbolByClassId;
                FirDeclarationOrigin origin = firRegularClassSymbol.getOrigin();
                if ((origin instanceof FirDeclarationOrigin.Java) || Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) {
                    firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) regularClassSymbolByClassId, AnnotationsLoader.this.session, this.scopeSession, false, (FirResolvePhase) null);
                } else {
                    E fir = firRegularClassSymbol.getFir();
                    FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
                    if (firCallableDeclaration == null || (importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration)) == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || !DeclarationUtilsKt.isJavaOrEnhancement(original)) {
                        firTypeScopeUnsubstitutedScope = FirDeclaredMemberScopeProviderKt.declaredMemberScope(regularClassSymbolByClassId, AnnotationsLoader.this.session, (FirResolvePhase) null);
                    } else {
                        firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) regularClassSymbolByClassId, AnnotationsLoader.this.session, this.scopeSession, false, (FirResolvePhase) null);
                    }
                }
                FirVariableSymbol firVariableSymbol = (FirVariableSymbol) CollectionsKt.firstOrNull((List) FirScopeKt.getProperties(firTypeScopeUnsubstitutedScope, name2));
                return firVariableSymbol != null ? firVariableSymbol.getResolvedReturnTypeRef() : null;
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnd() {
                if (AnnotationsLoader.this.isRepeatableWithImplicitContainer(lookupTag, this.argumentMap)) {
                    return;
                }
                List<FirAnnotation> list = result;
                AnnotationsLoader annotationsLoader = AnnotationsLoader.this;
                ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl = lookupTag;
                FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
                firAnnotationBuilder.setAnnotationTypeRef(annotationsLoader.toDefaultResolvedTypeRef(coneClassLikeLookupTagImpl));
                FirAnnotationArgumentMappingBuilder firAnnotationArgumentMappingBuilder = new FirAnnotationArgumentMappingBuilder();
                firAnnotationArgumentMappingBuilder.getMapping().putAll(this.argumentMap);
                firAnnotationBuilder.setArgumentMapping(firAnnotationArgumentMappingBuilder.build());
                list.add(firAnnotationBuilder.mo6810build());
            }
        };
    }

    public final KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotationMethodDefaultValue$java(final MemberSignature methodSignature, final Function1<? super FirExpression, Unit> consumeResult) {
        Intrinsics.checkNotNullParameter(methodSignature, "methodSignature");
        Intrinsics.checkNotNullParameter(consumeResult, "consumeResult");
        final AnnotationsLoader$loadAnnotationMethodDefaultValue$2 annotationsLoader$loadAnnotationMethodDefaultValue$2 = new AnnotationsLoader$loadAnnotationMethodDefaultValue$2(this);
        return new AnnotationsLoaderVisitorImpl(this, methodSignature, consumeResult, annotationsLoader$loadAnnotationMethodDefaultValue$2) { // from class: org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader$loadAnnotationMethodDefaultValue$1
            final /* synthetic */ Function1<FirExpression, Unit> $consumeResult;
            final /* synthetic */ MemberSignature $methodSignature;
            private FirExpression defaultValue;
            private final boolean visitNullNames;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(this, annotationsLoader$loadAnnotationMethodDefaultValue$2);
                this.visitNullNames = true;
            }

            public final FirExpression getDefaultValue() {
                return this.defaultValue;
            }

            public final void setDefaultValue(FirExpression firExpression) {
                this.defaultValue = firExpression;
            }

            @Override // org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.AnnotationsLoaderVisitorImpl
            public void visitExpression(Name name2, FirExpression expr) {
                Intrinsics.checkNotNullParameter(expr, "expr");
                this.defaultValue = expr;
            }

            @Override // org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.AnnotationsLoaderVisitorImpl
            public boolean getVisitNullNames() {
                return this.visitNullNames;
            }

            @Override // org.jetbrains.kotlin.fir.java.deserialization.AnnotationsLoader.AnnotationsLoaderVisitorImpl
            public FirTypeRef guessArrayTypeIfNeeded(Name name2, List<? extends FirExpression> arrayOfElements) {
                ClassId classIdResolveNameByInternalName;
                PrimitiveType primitiveType;
                FqName typeFqName;
                Intrinsics.checkNotNullParameter(arrayOfElements, "arrayOfElements");
                String strRemovePrefix = StringsKt.removePrefix(StringsKt.substringAfterLast$default(this.$methodSignature.getSignature(), ')', (String) null, 2, (Object) null), (CharSequence) "[");
                JvmPrimitiveType byDesc = JvmPrimitiveType.getByDesc(strRemovePrefix);
                if (byDesc == null || (primitiveType = byDesc.getPrimitiveType()) == null || (typeFqName = primitiveType.getTypeFqName()) == null || (classIdResolveNameByInternalName = ClassId.topLevel(typeFqName)) == null) {
                    classIdResolveNameByInternalName = FileBasedKotlinClass.resolveNameByInternalName(StringsKt.removeSuffix(StringsKt.removePrefix(strRemovePrefix, (CharSequence) "L"), (CharSequence) ";"), new FileBasedKotlinClass.InnerClassesInfo());
                }
                Intrinsics.checkNotNull(classIdResolveNameByInternalName);
                return TypeUtilsKt.toFirResolvedTypeRef$default(ArrayUtilsKt.createOutArrayType$default(TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(classIdResolveNameByInternalName), new ConeTypeProjection[0], false, null, 4, null), false, false, 3, null), null, null, 3, null);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnd() {
                FirExpression firExpression = this.defaultValue;
                if (firExpression != null) {
                    this.$consumeResult.invoke(firExpression);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isRepeatableWithImplicitContainer(ConeClassLikeLookupTag lookupTag, Map<Name, ? extends FirExpression> argumentMap) {
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass;
        if (!Intrinsics.areEqual(lookupTag.getClassId(), SpecialJvmAnnotations.INSTANCE.getJAVA_LANG_ANNOTATION_REPEATABLE())) {
            return false;
        }
        FirExpression firExpression = argumentMap.get(StandardClassIds.Annotations.ParameterNames.INSTANCE.getValue());
        FirGetClassCall firGetClassCall = firExpression instanceof FirGetClassCall ? (FirGetClassCall) firExpression : null;
        if (firGetClassCall == null) {
            return false;
        }
        FirExpression argument = firGetClassCall.getArgument();
        FirClassReferenceExpression firClassReferenceExpression = argument instanceof FirClassReferenceExpression ? (FirClassReferenceExpression) argument : null;
        if (firClassReferenceExpression == null) {
            return false;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firClassReferenceExpression.getClassTypeRef());
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        if (coneClassLikeType == null) {
            return false;
        }
        ClassId classId = coneClassLikeType.getLookupTag().getClassId();
        return classId.getOuterClassId() != null && Intrinsics.areEqual(classId.getShortClassName().asString(), JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME) && (kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, classId, MetadataHelpersKt.toMetadataVersion(FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.session).getLanguageVersion()))) != null && SpecialJvmAnnotations.INSTANCE.isAnnotatedWithContainerMetaAnnotation(kotlinJvmBinaryClassFindKotlinClass);
    }

    public final KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotationIfNotSpecial$java(ClassId annotationClassId, List<FirAnnotation> result) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(result, "result");
        if (SpecialJvmAnnotations.INSTANCE.getSPECIAL_ANNOTATIONS().contains(annotationClassId)) {
            return null;
        }
        return loadAnnotation(annotationClassId, result, new AnnotationsLoader$loadAnnotationIfNotSpecial$1(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirPropertyAccessExpression toEnumEntryReferenceExpressionWithResolve(ClassId classId, Name name2) {
        return FirEnumEntryDeserializerAccessUtilKt.toQualifiedPropertyAccessExpression(toEnumEntryReferenceExpressionUnresolved(classId, name2), this.session);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirResolvedTypeRef toDefaultResolvedTypeRef(ConeClassLikeLookupTag coneClassLikeLookupTag) {
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassType$default(coneClassLikeLookupTag, new ConeTypeProjection[0], false, null, 4, null));
        return firResolvedTypeRefBuilder.mo6810build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirEnumEntryDeserializedAccessExpression toEnumEntryReferenceExpressionUnresolved(ClassId classId, Name name2) {
        FirEnumEntryDeserializedAccessExpressionBuilder firEnumEntryDeserializedAccessExpressionBuilder = new FirEnumEntryDeserializedAccessExpressionBuilder();
        firEnumEntryDeserializedAccessExpressionBuilder.setEnumClassId(classId);
        firEnumEntryDeserializedAccessExpressionBuilder.setEnumEntryName(name2);
        return firEnumEntryDeserializedAccessExpressionBuilder.mo6810build();
    }
}
