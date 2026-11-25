package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.stubs.ConstantValueKind;
import org.jetbrains.kotlin.psi.stubs.KotlinConstantExpressionStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantExpressionStubImpl;

/* JADX INFO: compiled from: KtConstantExpressionElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000 \u00162\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0016B\u000f\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u001e\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\f\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\nH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstantExpressionElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstantExpressionStub;", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "debugName", "", "(Ljava/lang/String;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "shouldCreateStub", "", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "Companion"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtConstantExpressionElementType extends KtStubElementType<KotlinConstantExpressionStub, KtConstantExpression> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtConstantExpression) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtConstantExpressionElementType(String debugName) {
        super(debugName, KtConstantExpression.class, KotlinConstantExpressionStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType, org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public boolean shouldCreateStub(ASTNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        ASTNode treeParent = node.getTreeParent();
        if (treeParent != null && Intrinsics.areEqual(treeParent.getElementType(), KtStubElementTypes.VALUE_ARGUMENT)) {
            return super.shouldCreateStub(node);
        }
        return false;
    }

    public KotlinConstantExpressionStub createStub(KtConstantExpression psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        IElementType elementType = psi.getNode().getElementType();
        KtConstantExpressionElementType ktConstantExpressionElementType = elementType instanceof KtConstantExpressionElementType ? (KtConstantExpressionElementType) elementType : null;
        if (ktConstantExpressionElementType == null) {
            throw new IllegalStateException("Stub element type is expected for constant");
        }
        String text = psi.getText();
        if (text == null) {
            text = "";
        }
        ConstantValueKind constantValueKindConstantElementTypeToKind = INSTANCE.constantElementTypeToKind(ktConstantExpressionElementType);
        StringRef stringRefFromString = StringRef.fromString(text);
        Intrinsics.checkNotNullExpressionValue(stringRefFromString, "fromString(...)");
        return new KotlinConstantExpressionStubImpl(parentStub, ktConstantExpressionElementType, constantValueKindConstantElementTypeToKind, stringRefFromString);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinConstantExpressionStub stub, StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        dataStream.writeInt(stub.getKind().ordinal());
        dataStream.writeName(stub.value());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinConstantExpressionStub deserialize(StubInputStream dataStream, StubElement<?> parentStub) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        int i = dataStream.readInt();
        StringRef name2 = dataStream.readName();
        if (name2 == null) {
            name2 = StringRef.fromString("");
        }
        ConstantValueKind constantValueKind = ConstantValueKind.values()[i];
        KtConstantExpressionElementType ktConstantExpressionElementTypeKindToConstantElementType = INSTANCE.kindToConstantElementType(constantValueKind);
        Intrinsics.checkNotNull(name2);
        return new KotlinConstantExpressionStubImpl(parentStub, ktConstantExpressionElementTypeKindToConstantElementType, constantValueKind, name2);
    }

    /* JADX INFO: compiled from: KtConstantExpressionElementType.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstantExpressionElementType$Companion;", "", "()V", "constantElementTypeToKind", "Lorg/jetbrains/kotlin/psi/stubs/ConstantValueKind;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtConstantExpressionElementType;", "kindToConstantElementType", Namer.METADATA_CLASS_KIND, "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: KtConstantExpressionElementType.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ConstantValueKind.values().length];
                try {
                    iArr[ConstantValueKind.NULL.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ConstantValueKind.BOOLEAN_CONSTANT.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ConstantValueKind.FLOAT_CONSTANT.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ConstantValueKind.CHARACTER_CONSTANT.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ConstantValueKind.INTEGER_CONSTANT.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KtConstantExpressionElementType kindToConstantElementType(ConstantValueKind kind) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == 1) {
                KtConstantExpressionElementType NULL = KtStubElementTypes.NULL;
                Intrinsics.checkNotNullExpressionValue(NULL, "NULL");
                return NULL;
            }
            if (i == 2) {
                KtConstantExpressionElementType BOOLEAN_CONSTANT = KtStubElementTypes.BOOLEAN_CONSTANT;
                Intrinsics.checkNotNullExpressionValue(BOOLEAN_CONSTANT, "BOOLEAN_CONSTANT");
                return BOOLEAN_CONSTANT;
            }
            if (i == 3) {
                KtConstantExpressionElementType FLOAT_CONSTANT = KtStubElementTypes.FLOAT_CONSTANT;
                Intrinsics.checkNotNullExpressionValue(FLOAT_CONSTANT, "FLOAT_CONSTANT");
                return FLOAT_CONSTANT;
            }
            if (i == 4) {
                KtConstantExpressionElementType CHARACTER_CONSTANT = KtStubElementTypes.CHARACTER_CONSTANT;
                Intrinsics.checkNotNullExpressionValue(CHARACTER_CONSTANT, "CHARACTER_CONSTANT");
                return CHARACTER_CONSTANT;
            }
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            KtConstantExpressionElementType INTEGER_CONSTANT = KtStubElementTypes.INTEGER_CONSTANT;
            Intrinsics.checkNotNullExpressionValue(INTEGER_CONSTANT, "INTEGER_CONSTANT");
            return INTEGER_CONSTANT;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ConstantValueKind constantElementTypeToKind(KtConstantExpressionElementType elementType) {
            if (Intrinsics.areEqual(elementType, KtStubElementTypes.NULL)) {
                return ConstantValueKind.NULL;
            }
            if (Intrinsics.areEqual(elementType, KtStubElementTypes.BOOLEAN_CONSTANT)) {
                return ConstantValueKind.BOOLEAN_CONSTANT;
            }
            if (Intrinsics.areEqual(elementType, KtStubElementTypes.INTEGER_CONSTANT)) {
                return ConstantValueKind.INTEGER_CONSTANT;
            }
            if (Intrinsics.areEqual(elementType, KtStubElementTypes.FLOAT_CONSTANT)) {
                return ConstantValueKind.FLOAT_CONSTANT;
            }
            if (Intrinsics.areEqual(elementType, KtStubElementTypes.CHARACTER_CONSTANT)) {
                return ConstantValueKind.CHARACTER_CONSTANT;
            }
            throw new IllegalStateException("Unknown constant node type: " + elementType);
        }
    }
}
