package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DataClassResolver;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: CallableClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0002\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001aB\u0010\r\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f\u001a.\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016\u001a4\u0010\u0017\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000f\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"COMPILED_DEFAULT_INITIALIZER", "", "createConstructorStub", "", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "constructorProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "outerContext", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "createDeclarationsStubs", "functionProtos", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "propertyProtos", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "createPackageDeclarationsStubs", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Package;", "packageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "createTypeAliasesStubs", "typeAliasesProtos", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "shouldSkip", "", OverlayablePolicy.NAME_flags, "", "name", "Lorg/jetbrains/kotlin/name/Name;", "decompiler-to-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallableClsStubBuilderKt {
    public static final String COMPILED_DEFAULT_INITIALIZER = "COMPILED_CODE";

    /* JADX INFO: compiled from: CallableClsStubBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.MemberKind.values().length];
            try {
                iArr[ProtoBuf.MemberKind.FAKE_OVERRIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.MemberKind.DELEGATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.MemberKind.SYNTHESIZED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final void createPackageDeclarationsStubs(StubElement<? extends PsiElement> parentStub, ClsStubBuilderContext outerContext, ProtoContainer.Package protoContainer, ProtoBuf.Package packageProto) {
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        ProtoContainer.Package r5 = protoContainer;
        List<ProtoBuf.Function> functionList = packageProto.getFunctionList();
        Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
        List<ProtoBuf.Property> propertyList = packageProto.getPropertyList();
        Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
        createDeclarationsStubs(parentStub, outerContext, r5, functionList, propertyList);
        List<ProtoBuf.TypeAlias> typeAliasList = packageProto.getTypeAliasList();
        Intrinsics.checkNotNullExpressionValue(typeAliasList, "getTypeAliasList(...)");
        createTypeAliasesStubs(parentStub, outerContext, r5, typeAliasList);
    }

    public static final void createDeclarationsStubs(StubElement<? extends PsiElement> parentStub, ClsStubBuilderContext outerContext, ProtoContainer protoContainer, List<ProtoBuf.Function> functionProtos, List<ProtoBuf.Property> propertyProtos) {
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(functionProtos, "functionProtos");
        Intrinsics.checkNotNullParameter(propertyProtos, "propertyProtos");
        for (ProtoBuf.Property property : propertyProtos) {
            if (!shouldSkip(property.getFlags(), NameResolverUtilKt.getName(outerContext.getNameResolver(), property.getName()))) {
                new PropertyClsStubBuilder(parentStub, outerContext, protoContainer, property).build();
            }
        }
        for (ProtoBuf.Function function : functionProtos) {
            if (!shouldSkip(function.getFlags(), NameResolverUtilKt.getName(outerContext.getNameResolver(), function.getName()))) {
                new FunctionClsStubBuilder(parentStub, outerContext, protoContainer, function).build();
            }
        }
    }

    public static final void createTypeAliasesStubs(StubElement<? extends PsiElement> parentStub, ClsStubBuilderContext outerContext, ProtoContainer protoContainer, List<ProtoBuf.TypeAlias> typeAliasesProtos) {
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        Intrinsics.checkNotNullParameter(typeAliasesProtos, "typeAliasesProtos");
        Iterator<ProtoBuf.TypeAlias> it = typeAliasesProtos.iterator();
        while (it.hasNext()) {
            TypeAliasClsStubBuildingKt.createTypeAliasStub(parentStub, it.next(), protoContainer, outerContext);
        }
    }

    public static final void createConstructorStub(StubElement<? extends PsiElement> parentStub, ProtoBuf.Constructor constructorProto, ClsStubBuilderContext outerContext, ProtoContainer protoContainer) {
        Intrinsics.checkNotNullParameter(parentStub, "parentStub");
        Intrinsics.checkNotNullParameter(constructorProto, "constructorProto");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(protoContainer, "protoContainer");
        new ConstructorClsStubBuilder(parentStub, outerContext, protoContainer, constructorProto).build();
    }

    private static final boolean shouldSkip(int i, Name name2) {
        ProtoBuf.MemberKind memberKind = Flags.MEMBER_KIND.get(i);
        int i2 = memberKind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[memberKind.ordinal()];
        if (i2 == 1 || i2 == 2) {
            return true;
        }
        return (i2 != 3 || DataClassResolver.INSTANCE.isComponentLike(name2) || CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.EQUALS, StandardNames.HASHCODE_NAME, OperatorNameConventions.TO_STRING}).contains(name2)) ? false : true;
    }
}
