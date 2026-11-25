package org.jetbrains.kotlin.codegen.context;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.FieldInfo;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.scripting.resolve.LazyScriptClassMemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ScriptContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u000eJ\u0010\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010%\u001a\u00020\u000eJ\u001a\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010)2\u0006\u0010+\u001a\u00020\tH\u0016J\u000e\u0010,\u001a\u00020$2\u0006\u0010%\u001a\u00020\u000eJ\u000e\u0010-\u001a\u00020'2\u0006\u0010%\u001a\u00020\u000eJ\u000e\u0010.\u001a\u00020$2\u0006\u0010\u0004\u001a\u00020\u0005J\b\u0010/\u001a\u00020$H\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u00078F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0012R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a8F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/codegen/context/ScriptContext;", "Lorg/jetbrains/kotlin/codegen/context/ScriptLikeContext;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "earlierScripts", "", "contextDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "(Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/codegen/context/CodegenContext;)V", "ctorExplicitParametersStart", "", "ctorImplicitReceiversParametersStart", "ctorProvidedPropertiesParametersStart", "getEarlierScripts", "()Ljava/util/List;", "lastStatement", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getLastStatement", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "receiverDescriptors", "getReceiverDescriptors", "resultFieldInfo", "Lorg/jetbrains/kotlin/codegen/FieldInfo;", "getResultFieldInfo", "()Lorg/jetbrains/kotlin/codegen/FieldInfo;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "getScript", "()Lorg/jetbrains/kotlin/psi/KtScript;", "getScriptDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "getImplicitReceiverName", "", "index", "getImplicitReceiverType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getOuterReceiverExpression", "Lorg/jetbrains/kotlin/codegen/StackValue;", "prefix", "thisOrOuterClass", "getProvidedPropertyName", "getProvidedPropertyType", "getScriptFieldName", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptContext extends ScriptLikeContext {
    private final int ctorExplicitParametersStart;
    private final int ctorImplicitReceiversParametersStart;
    private final int ctorProvidedPropertiesParametersStart;
    private final List<ScriptDescriptor> earlierScripts;
    private final KtExpression lastStatement;
    private final KtScript script;
    private final ScriptDescriptor scriptDescriptor;

    public final ScriptDescriptor getScriptDescriptor() {
        return this.scriptDescriptor;
    }

    public final List<ScriptDescriptor> getEarlierScripts() {
        return this.earlierScripts;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ScriptContext(KotlinTypeMapper typeMapper, ScriptDescriptor scriptDescriptor, List<? extends ScriptDescriptor> earlierScripts, ClassDescriptor contextDescriptor, CodegenContext<?> codegenContext) {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        super(typeMapper, contextDescriptor, codegenContext);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
        Intrinsics.checkNotNullParameter(earlierScripts, "earlierScripts");
        Intrinsics.checkNotNullParameter(contextDescriptor, "contextDescriptor");
        this.scriptDescriptor = scriptDescriptor;
        this.earlierScripts = earlierScripts;
        KtScript ktScript = (KtScript) DescriptorToSourceUtils.getSourceFromDescriptor(scriptDescriptor);
        if (ktScript == null) {
            throw new IllegalStateException(("Declaration should be present for script: " + scriptDescriptor).toString());
        }
        this.script = ktScript;
        List<KtDeclaration> declarations = ktScript.getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
        KtDeclaration ktDeclaration = (KtDeclaration) CollectionsKt.lastOrNull((List) declarations);
        if (ktDeclaration instanceof KtAnonymousInitializer) {
            this.lastStatement = ((KtAnonymousInitializer) ktDeclaration).getBody();
        } else {
            this.lastStatement = null;
        }
        int size = 0;
        int i = scriptDescriptor.getEarlierScriptsConstructorParameter() == null ? 0 : 1;
        this.ctorExplicitParametersStart = i;
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(scriptDescriptor);
        if (superClassNotAny != null && (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = superClassNotAny.mo6347getUnsubstitutedPrimaryConstructor()) != null && (valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters()) != null) {
            size = valueParameters.size();
        }
        int i2 = i + size;
        this.ctorImplicitReceiversParametersStart = i2;
        this.ctorProvidedPropertiesParametersStart = i2 + scriptDescriptor.getImplicitReceivers().size();
    }

    public final KtExpression getLastStatement() {
        return this.lastStatement;
    }

    public final FieldInfo getResultFieldInfo() {
        Type typeMapType$default;
        PropertyDescriptor resultValue = this.scriptDescriptor.getResultValue();
        if (resultValue == null) {
            return null;
        }
        String identifier = resultValue.getShortName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        KotlinType returnType = resultValue.getReturnType();
        if (returnType == null || (typeMapType$default = KotlinTypeMapper.mapType$default(getState().getTypeMapper(), returnType, null, null, 6, null)) == null) {
            typeMapType$default = AsmTypes.OBJECT_TYPE;
        }
        return FieldInfo.createForHiddenField(getState().getTypeMapper().mapClass(this.scriptDescriptor), typeMapType$default, resultValue.getReturnType(), identifier);
    }

    public final KtScript getScript() {
        return this.script;
    }

    public final String getImplicitReceiverName(int index) {
        String identifier = this.scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor().getValueParameters().get(this.ctorImplicitReceiversParametersStart + index).getShortName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return identifier;
    }

    public final Type getImplicitReceiverType(int index) {
        ValueParameterDescriptor valueParameterDescriptor = this.scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor().getValueParameters().get(this.ctorImplicitReceiversParametersStart + index);
        KotlinTypeMapper typeMapper = getState().getTypeMapper();
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null);
    }

    public final String getProvidedPropertyName(int index) {
        String identifier = this.scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor().getValueParameters().get(this.ctorProvidedPropertiesParametersStart + index).getShortName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return identifier;
    }

    public final Type getProvidedPropertyType(int index) {
        KotlinTypeMapper typeMapper = getTypeMapper();
        KotlinType type = this.scriptDescriptor.getScriptProvidedProperties().get(index).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null);
    }

    @Override // org.jetbrains.kotlin.codegen.context.ScriptLikeContext
    public StackValue getOuterReceiverExpression(StackValue prefix, ClassDescriptor thisOrOuterClass) {
        StackValue.Field field;
        Intrinsics.checkNotNullParameter(thisOrOuterClass, "thisOrOuterClass");
        if (Intrinsics.areEqual(thisOrOuterClass.getContainingModule(), this.scriptDescriptor)) {
            if (prefix != null) {
                return prefix;
            }
            StackValue.Local LOCAL_0 = StackValue.LOCAL_0;
            Intrinsics.checkNotNullExpressionValue(LOCAL_0, "LOCAL_0");
            return LOCAL_0;
        }
        int i = 0;
        for (Object obj : getReceiverDescriptors()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (Intrinsics.areEqual((ClassDescriptor) obj, thisOrOuterClass)) {
                Type implicitReceiverType = getImplicitReceiverType(i);
                if (implicitReceiverType != null) {
                    Type typeMapType = getTypeMapper().mapType(this.scriptDescriptor);
                    String implicitReceiverName = getImplicitReceiverName(i);
                    if (prefix == null) {
                        prefix = StackValue.LOCAL_0;
                    }
                    field = StackValue.field(implicitReceiverType, typeMapType, implicitReceiverName, false, prefix);
                } else {
                    field = null;
                }
                if (field != null) {
                    return field;
                }
                throw new IllegalStateException(("Invalid script receiver: " + DescriptorUtilsKt.getFqNameSafe(thisOrOuterClass)).toString());
            }
            i = i2;
        }
        throw new IllegalStateException(("Script receiver not found: " + DescriptorUtilsKt.getFqNameSafe(thisOrOuterClass)).toString());
    }

    public final List<ClassDescriptor> getReceiverDescriptors() {
        List<ClassDescriptor> implicitReceivers = this.scriptDescriptor.getImplicitReceivers();
        Intrinsics.checkNotNullExpressionValue(implicitReceivers, "getImplicitReceivers(...)");
        return implicitReceivers;
    }

    public final String getScriptFieldName(ScriptDescriptor scriptDescriptor) {
        Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
        int iIndexOf = this.earlierScripts.indexOf(scriptDescriptor);
        if (iIndexOf >= 0) {
            return "script$" + (iIndexOf + 1);
        }
        return LazyScriptClassMemberScope.IMPORTED_SCRIPT_PARAM_NAME_PREFIX + scriptDescriptor.getShortName().getIdentifier();
    }

    @Override // org.jetbrains.kotlin.codegen.context.ClassContext
    public String toString() {
        return "Script: " + ((ClassDescriptor) getContextDescriptor()).getShortName().asString();
    }
}
