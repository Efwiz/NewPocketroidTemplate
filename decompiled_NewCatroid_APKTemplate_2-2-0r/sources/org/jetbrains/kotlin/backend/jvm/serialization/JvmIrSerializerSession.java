package org.jetbrains.kotlin.backend.jvm.serialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.backend.common.serialization.CompatibilityMode;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmIrSerializerSession.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u001c\u001a\u00020\u001dJ\n\u0010\u001e\u001a\u00020\u001f*\u00020 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/JvmIrSerializerSession;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer;", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "declarationTable", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "mode", "Lorg/jetbrains/kotlin/config/JvmSerializeIrMode;", "fileClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "skipExpects", "", "(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;Ljava/util/Map;Lorg/jetbrains/kotlin/config/JvmSerializeIrMode;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Z)V", "serializeAuxTables", "", "proto", "Lorg/jetbrains/kotlin/backend/jvm/serialization/proto/JvmIr$ClassOrFile$Builder;", "serializeJvmIrFile", "Lorg/jetbrains/kotlin/backend/jvm/serialization/proto/JvmIr$ClassOrFile;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "serializeTopLevelClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "toProto", "Lorg/jetbrains/kotlin/backend/jvm/serialization/proto/JvmIr$XStatementOrExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrSerializerSession extends IrFileSerializer {
    private final DeclarationTable declarationTable;
    private final FqName fileClassFqName;
    private final JvmSerializeIrMode mode;

    public /* synthetic */ JvmIrSerializerSession(IrMessageLogger irMessageLogger, DeclarationTable declarationTable, Map map, JvmSerializeIrMode jvmSerializeIrMode, FqName fqName, LanguageVersionSettings languageVersionSettings, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irMessageLogger, declarationTable, map, jvmSerializeIrMode, fqName, languageVersionSettings, (i & 64) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmIrSerializerSession(IrMessageLogger messageLogger, DeclarationTable declarationTable, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, JvmSerializeIrMode mode, FqName fileClassFqName, LanguageVersionSettings languageVersionSettings, boolean z) {
        super(messageLogger, declarationTable, expectDescriptorToSymbol, CompatibilityMode.INSTANCE.getCURRENT(), languageVersionSettings, mode == JvmSerializeIrMode.INLINE, z, false, CollectionsKt.emptyList());
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(declarationTable, "declarationTable");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(fileClassFqName, "fileClassFqName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.declarationTable = declarationTable;
        this.mode = mode;
        this.fileClassFqName = fileClassFqName;
        JvmSerializeIrMode jvmSerializeIrMode = JvmSerializeIrMode.NONE;
    }

    public final JvmIr.ClassOrFile serializeJvmIrFile(final IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final JvmIr.ClassOrFile.Builder builderNewBuilder = JvmIr.ClassOrFile.newBuilder();
        this.declarationTable.inFile(irFile, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIrSerializerSession.serializeJvmIrFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                List<IrDeclaration> declarations = irFile.getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (!(((IrDeclaration) obj) instanceof IrClass)) {
                        arrayList.add(obj);
                    }
                }
                final JvmIrSerializerSession jvmIrSerializerSession = this;
                final JvmIr.ClassOrFile.Builder builder = builderNewBuilder;
                final Ref.BooleanRef booleanRef2 = booleanRef;
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    JvmIrSerializerSessionKt.forEveryDeclarationToSerialize((IrDeclaration) it.next(), jvmIrSerializerSession.mode, new Function1<IrDeclaration, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIrSerializerSession$serializeJvmIrFile$1$2$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrDeclaration irDeclaration) {
                            invoke2(irDeclaration);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrDeclaration declaration) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            builder.addDeclaration(jvmIrSerializerSession.serializeDeclaration(declaration));
                            booleanRef2.element = true;
                        }
                    });
                }
            }
        });
        if (!booleanRef.element) {
            return null;
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        serializeAuxTables(builderNewBuilder);
        builderNewBuilder.setFileFacadeFqName(this.fileClassFqName.asString());
        return builderNewBuilder.build();
    }

    public final JvmIr.ClassOrFile serializeTopLevelClass(final IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        final JvmIr.ClassOrFile.Builder builderNewBuilder = JvmIr.ClassOrFile.newBuilder();
        DeclarationTable declarationTable = this.declarationTable;
        IrDeclarationParent parent = irClass.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        declarationTable.inFile((IrFile) parent, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIrSerializerSession.serializeTopLevelClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                IrClass irClass2 = irClass;
                JvmSerializeIrMode jvmSerializeIrMode = this.mode;
                final JvmIr.ClassOrFile.Builder builder = builderNewBuilder;
                final JvmIrSerializerSession jvmIrSerializerSession = this;
                JvmIrSerializerSessionKt.forEveryDeclarationToSerialize(irClass2, jvmSerializeIrMode, new Function1<IrDeclaration, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.JvmIrSerializerSession.serializeTopLevelClass.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(IrDeclaration irDeclaration) {
                        invoke2(irDeclaration);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrDeclaration declaration) {
                        Intrinsics.checkNotNullParameter(declaration, "declaration");
                        builder.addDeclaration(jvmIrSerializerSession.serializeDeclaration(declaration));
                    }
                });
            }
        });
        Intrinsics.checkNotNull(builderNewBuilder);
        serializeAuxTables(builderNewBuilder);
        builderNewBuilder.setFileFacadeFqName(this.fileClassFqName.asString());
        return builderNewBuilder.build();
    }

    private final void serializeAuxTables(JvmIr.ClassOrFile.Builder proto) {
        Iterator<T> it = getProtoTypeArray().iterator();
        while (it.hasNext()) {
            proto.addType((IrType) it.next());
        }
        Iterator<T> it2 = getProtoIdSignatureArray().iterator();
        while (it2.hasNext()) {
            proto.addSignature((IdSignature) it2.next());
        }
        Iterator<T> it3 = getProtoStringArray().iterator();
        while (it3.hasNext()) {
            proto.addString((String) it3.next());
        }
        Iterator<T> it4 = getProtoBodyArray().iterator();
        while (it4.hasNext()) {
            proto.addBody(toProto((IrFileSerializer.XStatementOrExpression) it4.next()));
        }
        Iterator<T> it5 = getProtoDebugInfoArray().iterator();
        while (it5.hasNext()) {
            proto.addDebugInfo((String) it5.next());
        }
    }

    public final JvmIr.XStatementOrExpression toProto(IrFileSerializer.XStatementOrExpression xStatementOrExpression) {
        Intrinsics.checkNotNullParameter(xStatementOrExpression, "<this>");
        if (xStatementOrExpression instanceof IrFileSerializer.XStatementOrExpression.XStatement) {
            JvmIr.XStatementOrExpression xStatementOrExpressionBuild = JvmIr.XStatementOrExpression.newBuilder().setStatement(xStatementOrExpression.getProto()).build();
            Intrinsics.checkNotNullExpressionValue(xStatementOrExpressionBuild, "build(...)");
            return xStatementOrExpressionBuild;
        }
        if (!(xStatementOrExpression instanceof IrFileSerializer.XStatementOrExpression.XExpression)) {
            throw new NoWhenBranchMatchedException();
        }
        JvmIr.XStatementOrExpression xStatementOrExpressionBuild2 = JvmIr.XStatementOrExpression.newBuilder().setExpression(xStatementOrExpression.getProto()).build();
        Intrinsics.checkNotNullExpressionValue(xStatementOrExpressionBuild2, "build(...)");
        return xStatementOrExpressionBuild2;
    }
}
