package org.jetbrains.kotlin.fir.plugin;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ClassBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0019\b\u0002\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b\u001aK\u0010\f\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0019\b\u0002\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b\u001a?\u0010\u0011\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0019\b\u0002\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\b\u000b¨\u0006\u0014"}, d2 = {"createCompanionObject", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "config", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/plugin/ClassBuildingContext;", "", "Lkotlin/ExtensionFunctionType;", "createNestedClass", "name", "Lorg/jetbrains/kotlin/name/Name;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "createTopLevelClass", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "plugin-utils"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassBuildingContextKt {
    public static /* synthetic */ FirRegularClass createTopLevelClass$default(FirExtension firExtension, ClassId classId, GeneratedDeclarationKey generatedDeclarationKey, ClassKind classKind, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            classKind = ClassKind.CLASS;
        }
        if ((i & 8) != 0) {
            function1 = new Function1<ClassBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ClassBuildingContextKt.createTopLevelClass.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ClassBuildingContext classBuildingContext) {
                    Intrinsics.checkNotNullParameter(classBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ClassBuildingContext classBuildingContext) {
                    invoke2(classBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createTopLevelClass(firExtension, classId, generatedDeclarationKey, classKind, function1);
    }

    public static final FirRegularClass createTopLevelClass(FirExtension firExtension, ClassId classId, GeneratedDeclarationKey key, ClassKind classKind, Function1<? super ClassBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(config, "config");
        ClassBuildingContext classBuildingContext = new ClassBuildingContext(firExtension.getSession(), key, null, classId, classKind);
        config.invoke(classBuildingContext);
        return classBuildingContext.build();
    }

    public static /* synthetic */ FirRegularClass createNestedClass$default(FirExtension firExtension, FirClassSymbol firClassSymbol, Name name2, GeneratedDeclarationKey generatedDeclarationKey, ClassKind classKind, Function1 function1, int i, Object obj) {
        if ((i & 8) != 0) {
            classKind = ClassKind.CLASS;
        }
        ClassKind classKind2 = classKind;
        if ((i & 16) != 0) {
            function1 = new Function1<ClassBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ClassBuildingContextKt.createNestedClass.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ClassBuildingContext classBuildingContext) {
                    Intrinsics.checkNotNullParameter(classBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ClassBuildingContext classBuildingContext) {
                    invoke2(classBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createNestedClass(firExtension, firClassSymbol, name2, generatedDeclarationKey, classKind2, function1);
    }

    public static final FirRegularClass createNestedClass(FirExtension firExtension, final FirClassSymbol<?> owner, Name name2, GeneratedDeclarationKey key, ClassKind classKind, Function1<? super ClassBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(config, "config");
        FirSession session = firExtension.getSession();
        ClassId classIdCreateNestedClassId = owner.getClassId().createNestedClassId(name2);
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
        ClassBuildingContext classBuildingContext = new ClassBuildingContext(session, key, owner, classIdCreateNestedClassId, classKind);
        config.invoke(classBuildingContext);
        classBuildingContext.status(new Function1<FirResolvedDeclarationStatusImpl, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ClassBuildingContextKt$createNestedClass$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl) {
                invoke2(firResolvedDeclarationStatusImpl);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirResolvedDeclarationStatusImpl status) {
                Intrinsics.checkNotNullParameter(status, "$this$status");
                status.setExpect(owner.getRawStatus().isExpect());
            }
        });
        return classBuildingContext.build();
    }

    public static /* synthetic */ FirRegularClass createCompanionObject$default(FirExtension firExtension, FirClassSymbol firClassSymbol, GeneratedDeclarationKey generatedDeclarationKey, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1<ClassBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ClassBuildingContextKt.createCompanionObject.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ClassBuildingContext classBuildingContext) {
                    Intrinsics.checkNotNullParameter(classBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ClassBuildingContext classBuildingContext) {
                    invoke2(classBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createCompanionObject(firExtension, firClassSymbol, generatedDeclarationKey, function1);
    }

    public static final FirRegularClass createCompanionObject(FirExtension firExtension, final FirClassSymbol<?> owner, GeneratedDeclarationKey key, Function1<? super ClassBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(config, "config");
        ClassId classIdCreateNestedClassId = owner.getClassId().createNestedClassId(SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
        Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
        ClassBuildingContext classBuildingContext = new ClassBuildingContext(firExtension.getSession(), key, owner, classIdCreateNestedClassId, ClassKind.OBJECT);
        config.invoke(classBuildingContext);
        classBuildingContext.setModality(Modality.FINAL);
        classBuildingContext.status(new Function1<FirResolvedDeclarationStatusImpl, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ClassBuildingContextKt$createCompanionObject$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl) {
                invoke2(firResolvedDeclarationStatusImpl);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirResolvedDeclarationStatusImpl status) {
                Intrinsics.checkNotNullParameter(status, "$this$status");
                status.setCompanion(true);
                status.setExpect(owner.getRawStatus().isExpect());
            }
        });
        return classBuildingContext.build();
    }
}
