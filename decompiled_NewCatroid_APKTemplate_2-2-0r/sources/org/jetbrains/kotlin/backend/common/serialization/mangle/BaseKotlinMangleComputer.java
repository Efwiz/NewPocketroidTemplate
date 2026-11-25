package org.jetbrains.kotlin.backend.common.serialization.mangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: BaseKotlinMangleComputer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0010\f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0014\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0004*\b\b\u0002\u0010\u0005*\u00020\u0006*\b\b\u0003\u0010\u0007*\u0002H\u0001*\b\b\u0004\u0010\b*\u0002H\u0001*\b\b\u0005\u0010\t*\u0002H\u0001*\u0004\b\u0006\u0010\n2\b\u0012\u0004\u0012\u0002H\u00010\u000bB#\u0012\n\u0010\f\u001a\u00060\rj\u0002`\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J\b\u0010\u001f\u001a\u00020\u0012H\u0014J\u0015\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00028\u0005H\u0014¢\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00028\u0000¢\u0006\u0002\u0010&J\u0015\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00028\u0004H\u0014¢\u0006\u0002\u0010*J\u001b\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00010,2\u0006\u0010!\u001a\u00028\u0005H$¢\u0006\u0002\u0010-J\u0015\u0010.\u001a\u00028\u00042\u0006\u0010/\u001a\u00028\u0002H$¢\u0006\u0002\u00100J\u0017\u00101\u001a\u0004\u0018\u00018\u00012\u0006\u0010!\u001a\u00028\u0005H$¢\u0006\u0002\u00102J\u001d\u00103\u001a\u00020(2\u0006\u0010/\u001a\u00028\u00022\u0006\u00104\u001a\u00028\u0004H$¢\u0006\u0002\u00105J\u0017\u00106\u001a\u0004\u0018\u00018\u00012\u0006\u0010!\u001a\u00028\u0005H$¢\u0006\u0002\u00102J\u0015\u00107\u001a\u00020$2\u0006\u0010/\u001a\u00028\u0002H$¢\u0006\u0002\u00108J)\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020;0:2\u0006\u0010!\u001a\u00028\u00052\u0006\u00104\u001a\u00028\u0000H$¢\u0006\u0002\u0010<J\u0015\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00028\u0006H$¢\u0006\u0002\u0010@J\u0015\u0010A\u001a\u00028\u00012\u0006\u0010B\u001a\u00028\u0003H$¢\u0006\u0002\u00102J\u001b\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00030,2\u0006\u0010!\u001a\u00028\u0005H$¢\u0006\u0002\u0010-J\u0015\u0010D\u001a\u00020\u00122\u0006\u0010E\u001a\u00028\u0001H$¢\u0006\u0002\u0010FJ\u0015\u0010G\u001a\u00020\u00122\u0006\u0010B\u001a\u00028\u0003H$¢\u0006\u0002\u0010\"J)\u0010H\u001a\u00020I2\n\u0010J\u001a\u00060\rj\u0002`\u000e2\u0006\u0010E\u001a\u00028\u00012\u0006\u0010K\u001a\u00028\u0006H$¢\u0006\u0002\u0010LJ)\u0010M\u001a\u00020I2\n\u0010J\u001a\u00060\rj\u0002`\u000e2\u0006\u0010E\u001a\u00028\u00012\u0006\u0010K\u001a\u00028\u0006H\u0004¢\u0006\u0002\u0010LJ1\u0010N\u001a\u00020I2\n\u0010O\u001a\u00060\rj\u0002`\u000e2\u0006\u0010P\u001a\u00028\u00022\u0006\u0010Q\u001a\u00020(2\u0006\u0010K\u001a\u00028\u0006H\u0004¢\u0006\u0002\u0010RJ!\u0010S\u001a\u00020I2\u0006\u0010E\u001a\u00028\u00012\n\u0010J\u001a\u00060\rj\u0002`\u000eH\u0014¢\u0006\u0002\u0010TJ)\u0010U\u001a\u00020I2\n\u0010V\u001a\u00060\rj\u0002`\u000e2\u0006\u0010P\u001a\u00028\u00032\u0006\u0010K\u001a\u00028\u0006H\u0004¢\u0006\u0002\u0010WJ\u0015\u0010X\u001a\u00020$2\u0006\u0010%\u001a\u00028\u0000H$¢\u0006\u0002\u0010&J\u0018\u0010Y\u001a\u00020I*\u00060\rj\u0002`\u000e2\u0006\u0010Z\u001a\u00020[H\u0004J\u0018\u0010Y\u001a\u00020I*\u00060\rj\u0002`\u000e2\u0006\u0010\\\u001a\u00020$H\u0004J\u0018\u0010]\u001a\u00020I*\u00060\rj\u0002`\u000e2\u0006\u0010Z\u001a\u00020[H\u0004J\u0018\u0010]\u001a\u00020I*\u00060\rj\u0002`\u000e2\u0006\u0010^\u001a\u00020(H\u0004J\u0018\u0010]\u001a\u00020I*\u00060\rj\u0002`\u000e2\u0006\u0010\\\u001a\u00020$H\u0004J\u0013\u0010_\u001a\u0004\u0018\u00018\u0004*\u00028\u0000H$¢\u0006\u0002\u0010`J9\u0010a\u001a\u00020I*\u00028\u00052\u0006\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020\u00122\u0006\u0010e\u001a\u00020\u00122\u0006\u00104\u001a\u00028\u00002\u0006\u0010?\u001a\u00028\u0006H\u0004¢\u0006\u0002\u0010fJ1\u0010g\u001a\u00020I*\u00028\u00052\u0006\u0010d\u001a\u00020\u00122\u0006\u0010e\u001a\u00020\u00122\u0006\u0010h\u001a\u00028\u00002\u0006\u0010?\u001a\u00028\u0006H\u0002¢\u0006\u0002\u0010iJ\u0019\u0010j\u001a\u00020I*\u00028\u00002\u0006\u0010b\u001a\u00020$H\u0004¢\u0006\u0002\u0010kJ\u001d\u0010l\u001a\u00020I*\u00060\rj\u0002`\u000e2\u0006\u0010/\u001a\u00028\u0002H\u0004¢\u0006\u0002\u0010mJ\u0017\u0010n\u001a\b\u0012\u0004\u0012\u00020$0,*\u00028\u0005H\u0014¢\u0006\u0002\u0010-J\u0013\u0010o\u001a\u0004\u0018\u00010$*\u00028\u0005H\u0014¢\u0006\u0002\u0010&J\u0013\u0010p\u001a\u0004\u0018\u00010$*\u00028\u0005H\u0014¢\u0006\u0002\u0010&J\u0019\u0010q\u001a\u00020$*\u00028\u00052\u0006\u0010P\u001a\u00028\u0003H\u0014¢\u0006\u0002\u0010rJ\u0011\u0010s\u001a\u00020I*\u00028\u0000H$¢\u0006\u0002\u0010tJ\u0011\u0010u\u001a\u00020I*\u00028\u0000H$¢\u0006\u0002\u0010tJ\u0011\u0010v\u001a\u00020I*\u00028\u0000H\u0014¢\u0006\u0002\u0010tR\u0014\u0010\u0011\u001a\u00020\u0012X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\f\u001a\u00060\rj\u0002`\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u000f\u001a\u00020\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R$\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00028\u00040\u001bj\b\u0012\u0004\u0012\u00028\u0004`\u001cX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e¨\u0006w"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/BaseKotlinMangleComputer;", "Declaration", "", "Type", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "TypeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "ValueParameter", "TypeParameterContainer", "FunctionDeclaration", "Session", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "allowOutOfScopeTypeParameters", "", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;Z)V", "getAllowOutOfScopeTypeParameters", "()Z", "getBuilder", "()Ljava/lang/StringBuilder;", "getMode", "()Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "typeParameterContainers", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getTypeParameterContainers", "()Ljava/util/ArrayList;", "addReturnType", "addReturnTypeSpecialCase", "function", "(Ljava/lang/Object;)Z", "computeMangle", "", "declaration", "(Ljava/lang/Object;)Ljava/lang/String;", "getContainerIndex", "", "parent", "(Ljava/lang/Object;)I", "getContextReceiverTypes", "", "(Ljava/lang/Object;)Ljava/util/List;", "getEffectiveParent", "typeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)Ljava/lang/Object;", "getExtensionReceiverParameterType", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getIndexOfTypeParameter", "container", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;Ljava/lang/Object;)I", "getReturnType", "getTypeParameterName", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)Ljava/lang/String;", "getTypeParametersWithIndices", "", "Lkotlin/collections/IndexedValue;", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Iterable;", "getTypeSystemContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "session", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "getValueParameterType", "valueParameter", "getValueParameters", "isUnit", ModuleXmlParser.TYPE, "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "isVararg", "mangleType", "", "tBuilder", "declarationSiteSession", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Ljava/lang/Object;)V", "mangleTypeArguments", "mangleTypeParameter", "tpBuilder", "param", "index", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;ILjava/lang/Object;)V", "mangleTypePlatformSpecific", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Ljava/lang/StringBuilder;)V", "mangleValueParameter", "vpBuilder", "(Ljava/lang/StringBuilder;Ljava/lang/Object;Ljava/lang/Object;)V", "renderDeclaration", "appendName", "c", "", "s", "appendSignature", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "asTypeParameterContainer", "(Ljava/lang/Object;)Ljava/lang/Object;", "mangleFunction", "name", "Lorg/jetbrains/kotlin/name/Name;", "isConstructor", "isStatic", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/name/Name;ZZLjava/lang/Object;Ljava/lang/Object;)V", "mangleSignature", "typeParameterContainer", "(Ljava/lang/Object;ZZLjava/lang/Object;Ljava/lang/Object;)V", "mangleSimpleDeclaration", "(Ljava/lang/Object;Ljava/lang/String;)V", "mangleTypeParameterReference", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)V", "platformSpecificFunctionMarks", "platformSpecificFunctionName", "platformSpecificSuffix", "specialValueParamPrefix", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/String;", "visit", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "visitParent", "visitParentForFunctionMangling", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseKotlinMangleComputer<Declaration, Type extends KotlinTypeMarker, TypeParameter extends TypeParameterMarker, ValueParameter extends Declaration, TypeParameterContainer extends Declaration, FunctionDeclaration extends Declaration, Session> implements KotlinMangleComputer<Declaration> {
    private final boolean allowOutOfScopeTypeParameters;
    private final StringBuilder builder;
    private final MangleMode mode;
    private final ArrayList<TypeParameterContainer> typeParameterContainers;

    protected boolean addReturnType() {
        return false;
    }

    protected boolean addReturnTypeSpecialCase(FunctionDeclaration function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return false;
    }

    protected abstract TypeParameterContainer asTypeParameterContainer(Declaration declaration);

    protected abstract List<Type> getContextReceiverTypes(FunctionDeclaration function);

    protected abstract TypeParameterContainer getEffectiveParent(TypeParameter typeParameter);

    protected abstract Type getExtensionReceiverParameterType(FunctionDeclaration function);

    protected abstract int getIndexOfTypeParameter(TypeParameter typeParameter, TypeParameterContainer container);

    protected abstract Type getReturnType(FunctionDeclaration function);

    protected abstract String getTypeParameterName(TypeParameter typeParameter);

    protected abstract Iterable<IndexedValue<TypeParameter>> getTypeParametersWithIndices(FunctionDeclaration function, Declaration container);

    protected abstract TypeSystemContext getTypeSystemContext(Session session);

    protected abstract Type getValueParameterType(ValueParameter valueParameter);

    protected abstract List<ValueParameter> getValueParameters(FunctionDeclaration function);

    protected abstract boolean isUnit(Type type);

    protected abstract boolean isVararg(ValueParameter valueParameter);

    protected abstract void mangleType(StringBuilder tBuilder, Type type, Session declarationSiteSession);

    protected void mangleTypePlatformSpecific(Type type, StringBuilder tBuilder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
    }

    protected String platformSpecificFunctionName(FunctionDeclaration functiondeclaration) {
        Intrinsics.checkNotNullParameter(functiondeclaration, "<this>");
        return null;
    }

    protected String platformSpecificSuffix(FunctionDeclaration functiondeclaration) {
        Intrinsics.checkNotNullParameter(functiondeclaration, "<this>");
        return null;
    }

    protected abstract String renderDeclaration(Declaration declaration);

    protected abstract void visit(Declaration declaration);

    protected abstract void visitParent(Declaration declaration);

    public BaseKotlinMangleComputer(StringBuilder builder, MangleMode mode, boolean z) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.builder = builder;
        this.mode = mode;
        this.allowOutOfScopeTypeParameters = z;
        this.typeParameterContainers = new ArrayList<>(4);
    }

    public /* synthetic */ BaseKotlinMangleComputer(StringBuilder sb, MangleMode mangleMode, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sb, mangleMode, (i & 4) != 0 ? false : z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final StringBuilder getBuilder() {
        return this.builder;
    }

    protected final MangleMode getMode() {
        return this.mode;
    }

    protected final boolean getAllowOutOfScopeTypeParameters() {
        return this.allowOutOfScopeTypeParameters;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ArrayList<TypeParameterContainer> getTypeParameterContainers() {
        return this.typeParameterContainers;
    }

    protected String specialValueParamPrefix(FunctionDeclaration functiondeclaration, ValueParameter param) {
        Intrinsics.checkNotNullParameter(functiondeclaration, "<this>");
        Intrinsics.checkNotNullParameter(param, "param");
        return "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void appendName(StringBuilder sb, String s) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        if (this.mode.getFqn()) {
            sb.append(s);
        }
    }

    protected final void appendName(StringBuilder sb, char c) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        if (this.mode.getFqn()) {
            sb.append(c);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void appendSignature(StringBuilder sb, String s) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        if (this.mode.getSignature()) {
            sb.append(s);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void appendSignature(StringBuilder sb, char c) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        if (this.mode.getSignature()) {
            sb.append(c);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void appendSignature(StringBuilder sb, int i) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        if (this.mode.getSignature()) {
            sb.append(i);
        }
    }

    protected void visitParentForFunctionMangling(Declaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "<this>");
        visitParent(declaration);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
    public final String computeMangle(Declaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        visit(declaration);
        String string = this.builder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void mangleSimpleDeclaration(Declaration declaration, String name2) {
        Intrinsics.checkNotNullParameter(declaration, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        int length = this.builder.length();
        visitParent(declaration);
        if (this.builder.length() != length) {
            appendName(this.builder, '.');
        }
        appendName(this.builder, name2);
    }

    protected List<String> platformSpecificFunctionMarks(FunctionDeclaration functiondeclaration) {
        Intrinsics.checkNotNullParameter(functiondeclaration, "<this>");
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void mangleFunction(FunctionDeclaration functiondeclaration, Name name2, boolean z, boolean z2, Declaration container, Session session) {
        Intrinsics.checkNotNullParameter(functiondeclaration, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(container, "container");
        Object objAsTypeParameterContainer = asTypeParameterContainer(container);
        if (objAsTypeParameterContainer != null) {
            this.typeParameterContainers.add((TypeParameterContainer) objAsTypeParameterContainer);
        }
        visitParentForFunctionMangling(container);
        appendName(this.builder, '#');
        String strPlatformSpecificFunctionName = platformSpecificFunctionName(functiondeclaration);
        if (strPlatformSpecificFunctionName != null) {
            this.builder.append(strPlatformSpecificFunctionName);
        } else {
            this.builder.append(name2.asString());
            mangleSignature(functiondeclaration, z, z2, container, session);
        }
    }

    private final void mangleSignature(final FunctionDeclaration functiondeclaration, boolean z, boolean z2, Declaration declaration, final Session session) {
        if (this.mode.getSignature()) {
            if (!z && z2) {
                appendSignature(this.builder, MangleConstant.STATIC_MEMBER_MARK);
            }
            Iterator<T> it = platformSpecificFunctionMarks(functiondeclaration).iterator();
            while (it.hasNext()) {
                appendSignature(this.builder, (String) it.next());
            }
            for (Type type : getContextReceiverTypes(functiondeclaration)) {
                appendSignature(this.builder, '!');
                mangleType(this.builder, type, session);
            }
            KotlinTypeMarker extensionReceiverParameterType = getExtensionReceiverParameterType(functiondeclaration);
            if (extensionReceiverParameterType != null) {
                appendSignature(this.builder, '@');
                mangleType(this.builder, extensionReceiverParameterType, session);
            }
            MangleUtilsKt.collectForMangler(getValueParameters(functiondeclaration), this.builder, MangleConstant.VALUE_PARAMETERS, new Function2<StringBuilder, ValueParameter, Unit>(this) { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer.mangleSignature.4
                final /* synthetic */ BaseKotlinMangleComputer<Declaration, Type, TypeParameter, ValueParameter, TypeParameterContainer, FunctionDeclaration, Session> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, Object obj) {
                    invoke2(sb, obj);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(StringBuilder collectForMangler, ValueParameter it2) {
                    Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                    Intrinsics.checkNotNullParameter(it2, "it");
                    BaseKotlinMangleComputer<Declaration, Type, TypeParameter, ValueParameter, TypeParameterContainer, FunctionDeclaration, Session> baseKotlinMangleComputer = this.this$0;
                    baseKotlinMangleComputer.appendSignature(collectForMangler, baseKotlinMangleComputer.specialValueParamPrefix(functiondeclaration, it2));
                    this.this$0.mangleValueParameter(collectForMangler, it2, session);
                }
            });
            MangleUtilsKt.collectForMangler(getTypeParametersWithIndices(functiondeclaration, declaration), this.builder, MangleConstant.TYPE_PARAMETERS, new Function2<StringBuilder, IndexedValue<? extends TypeParameter>, Unit>(this) { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer.mangleSignature.5
                final /* synthetic */ BaseKotlinMangleComputer<Declaration, Type, TypeParameter, ValueParameter, TypeParameterContainer, FunctionDeclaration, Session> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, Object obj) {
                    invoke(sb, (IndexedValue) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(StringBuilder collectForMangler, IndexedValue<? extends TypeParameter> it2) {
                    Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                    Intrinsics.checkNotNullParameter(it2, "it");
                    this.this$0.mangleTypeParameter(collectForMangler, it2.getValue(), it2.getIndex(), session);
                }
            });
            KotlinTypeMarker returnType = getReturnType(functiondeclaration);
            if (returnType != null && !z && !isUnit(returnType) && (addReturnType() || addReturnTypeSpecialCase(functiondeclaration))) {
                mangleType(this.builder, returnType, session);
            }
            String strPlatformSpecificSuffix = platformSpecificSuffix(functiondeclaration);
            if (strPlatformSpecificSuffix != null) {
                appendSignature(this.builder, MangleConstant.PLATFORM_FUNCTION_MARKER);
                appendSignature(this.builder, strPlatformSpecificSuffix);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void mangleTypeParameterReference(StringBuilder sb, TypeParameter typeParameter) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Object effectiveParent = getEffectiveParent(typeParameter);
        int containerIndex = getContainerIndex(effectiveParent);
        if (!this.allowOutOfScopeTypeParameters && containerIndex < 0) {
            throw new IllegalArgumentException(("No container found for type parameter '" + getTypeParameterName(typeParameter) + "' of '" + renderDeclaration(effectiveParent) + '\'').toString());
        }
        appendSignature(sb, containerIndex);
        appendSignature(sb, ':');
        appendSignature(sb, getIndexOfTypeParameter(typeParameter, effectiveParent));
    }

    protected int getContainerIndex(TypeParameterContainer parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return this.typeParameterContainers.indexOf(parent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void mangleTypeParameter(StringBuilder tpBuilder, TypeParameter param, int index, final Session declarationSiteSession) {
        Intrinsics.checkNotNullParameter(tpBuilder, "tpBuilder");
        Intrinsics.checkNotNullParameter(param, "param");
        TypeSystemContext typeSystemContext = getTypeSystemContext(declarationSiteSession);
        appendSignature(tpBuilder, index);
        appendSignature(tpBuilder, (char) 167);
        MangleUtilsKt.collectForMangler(typeSystemContext.getUpperBounds(param), tpBuilder, MangleConstant.UPPER_BOUNDS, new Function2<StringBuilder, KotlinTypeMarker, Unit>(this) { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer$mangleTypeParameter$1$1
            final /* synthetic */ BaseKotlinMangleComputer<Declaration, Type, TypeParameter, ValueParameter, TypeParameterContainer, FunctionDeclaration, Session> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, KotlinTypeMarker kotlinTypeMarker) {
                invoke2(sb, kotlinTypeMarker);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder collectForMangler, KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                Intrinsics.checkNotNullParameter(it, "it");
                this.this$0.mangleType(collectForMangler, it, declarationSiteSession);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void mangleValueParameter(StringBuilder vpBuilder, ValueParameter param, Session declarationSiteSession) {
        Intrinsics.checkNotNullParameter(vpBuilder, "vpBuilder");
        Intrinsics.checkNotNullParameter(param, "param");
        mangleType(vpBuilder, getValueParameterType(param), declarationSiteSession);
        if (isVararg(param)) {
            appendSignature(vpBuilder, "...");
        }
    }

    protected final void mangleTypeArguments(StringBuilder tBuilder, Type type, final Session declarationSiteSession) {
        Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
        Intrinsics.checkNotNullParameter(type, "type");
        final TypeSystemContext typeSystemContext = getTypeSystemContext(declarationSiteSession);
        List listZip = CollectionsKt.zip(typeSystemContext.getArguments(type), typeSystemContext.getParameters(typeSystemContext.typeConstructor(type)));
        if (listZip.isEmpty()) {
            return;
        }
        MangleUtilsKt.collectForMangler(listZip, tBuilder, MangleConstant.TYPE_ARGUMENTS, new Function2<StringBuilder, Pair<? extends TypeArgumentMarker, ? extends TypeParameterMarker>, Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer$mangleTypeArguments$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, Pair<? extends TypeArgumentMarker, ? extends TypeParameterMarker> pair) {
                invoke2(sb, pair);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StringBuilder collectForMangler, Pair<? extends TypeArgumentMarker, ? extends TypeParameterMarker> pair) {
                Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                TypeArgumentMarker typeArgumentMarkerComponent1 = pair.component1();
                pair.component2();
                if (typeSystemContext.isStarProjection(typeArgumentMarkerComponent1)) {
                    this.appendSignature(collectForMangler, '*');
                    return;
                }
                TypeVariance variance = typeSystemContext.getVariance(typeArgumentMarkerComponent1);
                if (variance != TypeVariance.INV) {
                    this.appendSignature(collectForMangler, variance.getPresentation());
                    this.appendSignature(collectForMangler, '|');
                }
                BaseKotlinMangleComputer<Declaration, Type, TypeParameter, ValueParameter, TypeParameterContainer, FunctionDeclaration, Session> baseKotlinMangleComputer = this;
                KotlinTypeMarker type2 = typeSystemContext.getType(typeArgumentMarkerComponent1);
                Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type Type of org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer.mangleTypeArguments$lambda$8");
                baseKotlinMangleComputer.mangleType(collectForMangler, type2, declarationSiteSession);
            }
        });
    }
}
