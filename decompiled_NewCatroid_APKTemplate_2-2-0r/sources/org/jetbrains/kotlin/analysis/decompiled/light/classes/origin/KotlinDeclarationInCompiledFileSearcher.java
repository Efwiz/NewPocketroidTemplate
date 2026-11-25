package org.jetbrains.kotlin.analysis.decompiled.light.classes.origin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.ByDescriptorIndexerKt;
import org.jetbrains.kotlin.asJava.elements.KtLightAnnotationsValuesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtClassOrObjectKt;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinAnnotationEntryStubImpl;
import org.jetbrains.kotlin.type.MapPsiToAsmDesc;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: KotlinDeclarationInCompiledFileSearcher.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 '2\u00020\u0001:\u0001'B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0002J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u0018\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J0\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u000b\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0004J\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u001bJ\"\u0010\u001e\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H&J\u0014\u0010!\u001a\u0004\u0018\u00010\u001d2\b\u0010\"\u001a\u0004\u0018\u00010\u0015H\u0002J\u0014\u0010#\u001a\u00020\u0004*\u00020\u000e2\u0006\u0010$\u001a\u00020%H\u0002J\u0014\u0010&\u001a\u00020\u0004*\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/KotlinDeclarationInCompiledFileSearcher;", "", "()V", "areTypesTheSame", "", "ktTypeRef", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "psiType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "varArgs", "doParametersMatch", "member", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "ktNamedFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "doPropertyMatch", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "setter", "doTypeParameters", "findByStubs", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "relativeClassName", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMember;", "memberName", "", "findDeclarationInCompiledFile", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "getJvmName", "declaration", "isSuspendFunction", "memberParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameterList;", "isTheSame", "Companion", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinDeclarationInCompiledFileSearcher {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract KtDeclaration findDeclarationInCompiledFile(KtClsFile file, PsiMember member, MemberSignature signature);

    public final KtDeclaration findDeclarationInCompiledFile(KtClsFile file, PsiMember member) {
        MemberSignature memberSignatureFromMethodNameAndDesc;
        String str;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(member, "member");
        if (member instanceof PsiField) {
            MapPsiToAsmDesc mapPsiToAsmDesc = MapPsiToAsmDesc.INSTANCE;
            PsiField psiField = (PsiField) member;
            PsiType type = psiField.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            String strTypeDesc = mapPsiToAsmDesc.typeDesc(type);
            MemberSignature.Companion companion = MemberSignature.INSTANCE;
            String myName = psiField.getMyName();
            Intrinsics.checkNotNullExpressionValue(myName, "getName(...)");
            memberSignatureFromMethodNameAndDesc = companion.fromFieldNameAndDesc(myName, strTypeDesc);
        } else if (member instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) member;
            String strMethodDesc = MapPsiToAsmDesc.INSTANCE.methodDesc(psiMethod);
            if (psiMethod.isConstructor()) {
                str = "<init>";
            } else {
                String myName2 = psiMethod.getMyName();
                Intrinsics.checkNotNullExpressionValue(myName2, "getName(...)");
                str = myName2;
            }
            memberSignatureFromMethodNameAndDesc = MemberSignature.INSTANCE.fromMethodNameAndDesc(str, strMethodDesc);
        } else {
            memberSignatureFromMethodNameAndDesc = null;
        }
        if (memberSignatureFromMethodNameAndDesc == null) {
            return null;
        }
        return findDeclarationInCompiledFile(file, member, memberSignatureFromMethodNameAndDesc);
    }

    /* JADX WARN: Code duplicated, block: B:114:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:180:0x01eb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:181:0x01f7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:184:0x016a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:185:0x016a A[SYNTHETIC] */
    protected final KtDeclaration findByStubs(KtClsFile file, List<Name> relativeClassName, PsiMember member, String memberName) {
        Boolean bool;
        List<KtConstructor<?>> allConstructors;
        KtDeclaration ktDeclaration;
        List<KtDeclaration> declarations;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(relativeClassName, "relativeClassName");
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(memberName, "memberName");
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) file.getDeclarations());
        Object obj = null;
        KtClassOrObject ktClassOrObject = objSingleOrNull instanceof KtClassOrObject ? (KtClassOrObject) objSingleOrNull : null;
        KtClsFile ktClsFile = ktClassOrObject != null ? ktClassOrObject : file;
        boolean z = false;
        if (!relativeClassName.isEmpty()) {
            for (Name name2 : relativeClassName) {
                if (ktClsFile == null || (declarations = ktClsFile.getDeclarations()) == null) {
                    ktDeclaration = null;
                } else {
                    Intrinsics.checkNotNull(declarations);
                    Iterator<T> it = declarations.iterator();
                    Object obj2 = null;
                    boolean z2 = false;
                    while (true) {
                        if (!it.hasNext()) {
                            if (!z2) {
                                break;
                            }
                            break;
                        }
                        Object next = it.next();
                        KtDeclaration ktDeclaration2 = (KtDeclaration) next;
                        if ((ktDeclaration2 instanceof KtClassOrObject) && Intrinsics.areEqual(((KtClassOrObject) ktDeclaration2).getMyName(), name2.asString())) {
                            if (!z2) {
                                z2 = true;
                                obj2 = next;
                            }
                        }
                        obj2 = null;
                        break;
                    }
                    ktDeclaration = (KtDeclaration) obj2;
                }
                ktClsFile = ktDeclaration instanceof KtClassOrObject ? (KtClassOrObject) ktDeclaration : null;
            }
        }
        if (ktClsFile == null) {
            return null;
        }
        boolean z3 = member instanceof PsiMethod;
        if (z3) {
            PsiMethod psiMethod = (PsiMethod) member;
            if (psiMethod.isConstructor()) {
                if (!(ktClsFile instanceof KtClassOrObject)) {
                    ktClsFile = null;
                }
                KtClassOrObject ktClassOrObject2 = (KtClassOrObject) ktClsFile;
                if (ktClassOrObject2 != null) {
                    if (!Intrinsics.areEqual(ktClassOrObject2.getMyName(), memberName)) {
                        ktClassOrObject2 = null;
                    }
                    if (ktClassOrObject2 != null && (allConstructors = KtClassOrObjectKt.getAllConstructors(ktClassOrObject2)) != null) {
                        for (Object obj3 : allConstructors) {
                            if (doParametersMatch(psiMethod, (KtConstructor) obj3)) {
                                obj = obj3;
                                break;
                            }
                        }
                        obj = (KtConstructor) obj;
                    }
                }
                return (KtDeclaration) obj;
            }
        }
        List<KtDeclaration> declarations2 = ktClsFile.getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations2, "getDeclarations(...)");
        if (z3) {
            SmartList smartList = new SmartList(memberName);
            if (JvmAbi.isGetterName(memberName) && !PsiType.VOID.equals(((PsiMethod) member).getReturnType())) {
                Name nameIdentifier = Name.identifier(memberName);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                Name namePropertyNameByGetMethodName = PropertiesConventionUtilKt.propertyNameByGetMethodName(nameIdentifier);
                if (namePropertyNameByGetMethodName != null) {
                    smartList.add(namePropertyNameByGetMethodName.getIdentifier());
                }
                bool = false;
            } else if (JvmAbi.isSetterName(memberName) && PsiType.VOID.equals(((PsiMethod) member).getReturnType())) {
                Name nameIdentifier2 = Name.identifier(memberName);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                Iterator<T> it2 = PropertiesConventionUtilKt.propertyNamesBySetMethodName(nameIdentifier2).iterator();
                while (it2.hasNext()) {
                    smartList.add(((Name) it2.next()).getIdentifier());
                }
                bool = true;
            } else {
                bool = null;
            }
            for (Object obj4 : declarations2) {
                KtDeclaration ktDeclaration3 = (KtDeclaration) obj4;
                String jvmName = getJvmName(ktDeclaration3);
                if (ktDeclaration3 instanceof KtNamedFunction) {
                    if (smartList.contains(jvmName)) {
                        Intrinsics.checkNotNull(ktDeclaration3);
                        if (doParametersMatch((PsiMethod) member, (KtFunction) ktDeclaration3)) {
                            obj = obj4;
                            break;
                        }
                    } else {
                        continue;
                    }
                } else if (ktDeclaration3 instanceof KtProperty) {
                    KtProperty ktProperty = (KtProperty) ktDeclaration3;
                    String jvmName2 = getJvmName(ktProperty.getGetter());
                    String jvmName3 = getJvmName(ktProperty.getSetter());
                    if (bool != null) {
                        if (bool.booleanValue()) {
                            jvmName2 = jvmName3;
                        }
                        if (jvmName2 != null) {
                            jvmName = jvmName2;
                        }
                        if (smartList.contains(jvmName)) {
                            Intrinsics.checkNotNull(ktDeclaration3);
                            if (doPropertyMatch((PsiMethod) member, ktProperty, bool.booleanValue())) {
                                obj = obj4;
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        if (smartList.contains(jvmName2)) {
                            Intrinsics.checkNotNull(ktDeclaration3);
                            if (!doPropertyMatch((PsiMethod) member, ktProperty, false)) {
                                if (smartList.contains(jvmName3)) {
                                    Intrinsics.checkNotNull(ktDeclaration3);
                                    if (doPropertyMatch((PsiMethod) member, ktProperty, true)) {
                                    }
                                } else {
                                    continue;
                                }
                            }
                            obj = obj4;
                            break;
                        }
                        if (smartList.contains(jvmName3)) {
                            Intrinsics.checkNotNull(ktDeclaration3);
                            if (doPropertyMatch((PsiMethod) member, ktProperty, true)) {
                                obj = obj4;
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                } else {
                    continue;
                }
            }
            return (KtDeclaration) obj;
        }
        if (member instanceof PsiField) {
            if ((ktClsFile instanceof KtObjectDeclaration) && Intrinsics.areEqual(memberName, JvmAbi.INSTANCE_FIELD)) {
                return (KtDeclaration) ktClsFile;
            }
            Object obj5 = null;
            for (Object obj6 : declarations2) {
                KtDeclaration ktDeclaration4 = (KtDeclaration) obj6;
                if (!(ktDeclaration4 instanceof KtNamedFunction) && Intrinsics.areEqual(ktDeclaration4.getMyName(), memberName)) {
                    if (z) {
                        return (KtDeclaration) obj;
                    }
                    obj5 = obj6;
                    z = true;
                }
            }
            if (z) {
                obj = obj5;
            }
            return (KtDeclaration) obj;
        }
        Object obj7 = null;
        for (Object obj8 : declarations2) {
            if (Intrinsics.areEqual(((KtDeclaration) obj8).getMyName(), memberName)) {
                if (z) {
                    return (KtDeclaration) obj;
                }
                obj7 = obj8;
                z = true;
            }
        }
        if (z) {
            obj = obj7;
        }
        return (KtDeclaration) obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final String getJvmName(KtDeclaration declaration) {
        Object next;
        Map<Name, ConstantValue<?>> valueArguments;
        KtConstructorCalleeExpression calleeExpression;
        KtSimpleNameExpression constructorReferenceExpression;
        ConstantValue<?> constantValue = null;
        if (declaration == null) {
            return null;
        }
        List<KtAnnotationEntry> annotationEntries = declaration.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        Iterator<T> it = annotationEntries.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            calleeExpression = ((KtAnnotationEntry) next).getCalleeExpression();
        } while (!Intrinsics.areEqual((calleeExpression == null || (constructorReferenceExpression = calleeExpression.getConstructorReferenceExpression()) == null) ? null : constructorReferenceExpression.getReferencedName(), JvmNames.INSTANCE.getJVM_NAME_SHORT()));
        KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) next;
        if (ktAnnotationEntry != null) {
            T stub = ktAnnotationEntry.getStub();
            KotlinAnnotationEntryStubImpl kotlinAnnotationEntryStubImpl = stub instanceof KotlinAnnotationEntryStubImpl ? (KotlinAnnotationEntryStubImpl) stub : null;
            if (kotlinAnnotationEntryStubImpl != null && (valueArguments = kotlinAnnotationEntryStubImpl.getValueArguments()) != null) {
                constantValue = valueArguments.get(Name.identifier("name"));
            }
            if (constantValue instanceof StringValue) {
                return ((StringValue) constantValue).getValue();
            }
        }
        return declaration.getMyName();
    }

    private final boolean doPropertyMatch(PsiMethod member, KtProperty property, boolean setter) {
        ArrayList arrayList = new ArrayList();
        List<KtContextReceiver> contextReceivers = property.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            Intrinsics.checkNotNull(ktTypeReferenceTypeReference);
            arrayList.add(ktTypeReferenceTypeReference);
        }
        KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = property.mo7393getReceiverTypeReference();
        if (ktTypeReferenceMo7393getReceiverTypeReference != null) {
            arrayList.add(ktTypeReferenceMo7393getReceiverTypeReference);
        }
        KtTypeReference ktTypeReferenceMo7396getTypeReference = property.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference != null) {
            arrayList.add(ktTypeReferenceMo7396getTypeReference);
        }
        ArrayList arrayList2 = new ArrayList();
        PsiParameter[] parameters = member.getParameterList().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        for (PsiParameter psiParameter : parameters) {
            PsiType type = psiParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            arrayList2.add(type);
        }
        if (!setter) {
            PsiType returnType = member.getReturnType();
            if (returnType == null) {
                return false;
            }
            arrayList2.add(returnType);
        }
        if (arrayList.size() != arrayList2.size()) {
            return false;
        }
        for (Pair pair : CollectionsKt.zip(arrayList, arrayList2)) {
            if (!areTypesTheSame((KtTypeReference) pair.component1(), (PsiType) pair.component2(), false)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean doParametersMatch(PsiMethod member, KtFunction ktNamedFunction) {
        Object[] objArr;
        KtSimpleNameExpression constructorReferenceExpression;
        int size;
        if (!doTypeParameters(member, ktNamedFunction)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        List<KtContextReceiver> contextReceivers = ktNamedFunction.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            KtTypeReference ktTypeReferenceTypeReference = ((KtContextReceiver) it.next()).typeReference();
            Intrinsics.checkNotNull(ktTypeReferenceTypeReference);
            arrayList.add(ktTypeReferenceTypeReference);
        }
        KtTypeReference receiverTypeReference = ktNamedFunction.mo7393getReceiverTypeReference();
        if (receiverTypeReference != null) {
            arrayList.add(receiverTypeReference);
        }
        List<KtParameter> valueParameters = ktNamedFunction.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        PsiParameterList parameterList = member.getParameterList();
        Intrinsics.checkNotNullExpressionValue(parameterList, "getParameterList(...)");
        int parametersCount = parameterList.getParametersCount() - (isSuspendFunction(ktNamedFunction, parameterList) ? 1 : 0);
        List<KtAnnotationEntry> annotationEntries = ktNamedFunction.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        List<KtAnnotationEntry> list = annotationEntries;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    objArr = false;
                    break;
                }
                KtConstructorCalleeExpression calleeExpression = ((KtAnnotationEntry) it2.next()).getCalleeExpression();
                if (Intrinsics.areEqual((calleeExpression == null || (constructorReferenceExpression = calleeExpression.getConstructorReferenceExpression()) == null) ? null : constructorReferenceExpression.getReferencedName(), JvmNames.INSTANCE.getJVM_OVERLOADS_FQ_NAME().shortName().asString())) {
                    objArr = true;
                    break;
                }
            }
        } else {
            objArr = false;
            break;
        }
        if (objArr == true) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : valueParameters) {
                if (((KtParameter) obj).hasDefaultValue()) {
                    arrayList2.add(obj);
                }
            }
            size = arrayList2.size() - ((valueParameters.size() + arrayList.size()) - parametersCount);
        } else {
            size = 0;
        }
        int i = 0;
        for (KtParameter ktParameter : valueParameters) {
            if (objArr != false && ktParameter.hasDefaultValue()) {
                if (i < size) {
                    i++;
                }
            }
            KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
            Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference);
            arrayList.add(ktTypeReferenceMo7396getTypeReference);
        }
        if (parametersCount != arrayList.size()) {
            return false;
        }
        PsiParameter[] parameters = parameterList.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        PsiParameter[] psiParameterArr = parameters;
        ArrayList arrayList3 = new ArrayList(psiParameterArr.length);
        for (PsiParameter psiParameter : psiParameterArr) {
            arrayList3.add(psiParameter.getType());
        }
        for (Pair pair : CollectionsKt.zip(arrayList3, arrayList)) {
            PsiType psiType = (PsiType) pair.component1();
            KtTypeReference ktTypeReference = (KtTypeReference) pair.component2();
            Intrinsics.checkNotNull(psiType);
            PsiElement parent = ktTypeReference.getMyParent();
            KtParameter ktParameter2 = parent instanceof KtParameter ? (KtParameter) parent : null;
            if (!areTypesTheSame(ktTypeReference, psiType, ktParameter2 != null && ktParameter2.isVarArg())) {
                return false;
            }
        }
        return true;
    }

    private final boolean isSuspendFunction(KtFunction ktFunction, PsiParameterList psiParameterList) {
        PsiType type;
        KtModifierList modifierList = ktFunction.getModifierList();
        if (modifierList != null && PsiUtilsKt.hasSuspendModifier(modifierList) && !psiParameterList.isEmpty()) {
            int parametersCount = psiParameterList.getParametersCount();
            String strAsString = StandardNames.CONTINUATION_INTERFACE_FQ_NAME.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            PsiType psiTypePsiType$default = KtLightAnnotationsValuesKt.psiType$default(strAsString, ktFunction, false, 4, null);
            PsiParameter parameter = psiParameterList.getParameter(parametersCount - 1);
            if (parameter != null && (type = parameter.getType()) != null) {
                return isTheSame(type, psiTypePsiType$default);
            }
        }
        return false;
    }

    private final boolean doTypeParameters(PsiMethod member, KtFunction ktNamedFunction) {
        if (member.getTypeParameters().length != ktNamedFunction.getTypeParameters().size()) {
            return false;
        }
        List<KtTypeConstraint> typeConstraints = ktNamedFunction.getTypeConstraints();
        Intrinsics.checkNotNullExpressionValue(typeConstraints, "getTypeConstraints(...)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : typeConstraints) {
            KtSimpleNameExpression subjectTypeParameterName = ((KtTypeConstraint) obj).getSubjectTypeParameterName();
            String referencedName = subjectTypeParameterName != null ? subjectTypeParameterName.getReferencedName() : null;
            Object obj2 = linkedHashMap.get(referencedName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(referencedName, obj2);
            }
            ((List) obj2).add(obj);
        }
        PsiTypeParameter[] typeParameters = member.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        PsiTypeParameter[] psiTypeParameterArr = typeParameters;
        List<KtTypeParameter> typeParameters2 = ktNamedFunction.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        List<KtTypeParameter> list = typeParameters2;
        int length = psiTypeParameterArr.length;
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), length));
        int i = 0;
        for (Object obj3 : list) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            KtTypeParameter ktTypeParameter = (KtTypeParameter) obj3;
            PsiTypeParameter psiTypeParameter = psiTypeParameterArr[i];
            if (!Intrinsics.areEqual(String.valueOf(psiTypeParameter.getMyName()), ktTypeParameter.getMyName())) {
                return false;
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = arrayList2;
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, ktTypeParameter.getExtendsBound());
            List list2 = (List) linkedHashMap.get(ktTypeParameter.getMyName());
            if (list2 != null) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, ((KtTypeConstraint) it.next()).getBoundTypeReference());
                }
            }
            PsiClassType[] extendsListTypes = psiTypeParameter.getExtendsListTypes();
            Intrinsics.checkNotNullExpressionValue(extendsListTypes, "getExtendsListTypes(...)");
            if (arrayList2.size() != extendsListTypes.length) {
                return false;
            }
            int length2 = extendsListTypes.length;
            ArrayList arrayList4 = arrayList2;
            ArrayList arrayList5 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(arrayList4, 10), length2));
            int i3 = 0;
            for (Object obj4 : arrayList4) {
                if (i3 >= length2) {
                    break;
                }
                int i4 = i3 + 1;
                PsiClassType psiClassType = extendsListTypes[i3];
                Intrinsics.checkNotNull(psiClassType);
                if (!areTypesTheSame((KtTypeReference) obj4, psiClassType, false)) {
                    return false;
                }
                arrayList5.add(Unit.INSTANCE);
                i3 = i4;
            }
            arrayList.add(arrayList5);
            i = i2;
        }
        return true;
    }

    private final boolean areTypesTheSame(KtTypeReference ktTypeRef, PsiType psiType, boolean varArgs) {
        boolean z;
        KtTypeElement typeElement = ktTypeRef.getTypeElement();
        KtDeclarationModifierList[] allModifierLists = ByDescriptorIndexerKt.getAllModifierLists(ktTypeRef);
        int length = allModifierLists.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (PsiUtilsKt.hasSuspendModifier(allModifierLists[i])) {
                z = true;
                break;
            }
            i++;
        }
        String qualifiedName = ByDescriptorIndexerKt.getQualifiedName(typeElement, z);
        if (qualifiedName == null) {
            return false;
        }
        if (psiType instanceof PsiArrayType) {
            PsiArrayType psiArrayType = (PsiArrayType) psiType;
            if (!(psiArrayType.getComponentType() instanceof PsiPrimitiveType)) {
                if (!Intrinsics.areEqual(qualifiedName, StandardNames.FqNames.array.asString())) {
                    if (!varArgs) {
                        return false;
                    }
                    PsiType componentType = psiArrayType.getComponentType();
                    Intrinsics.checkNotNullExpressionValue(componentType, "getComponentType(...)");
                    if (!areTypesTheSame(ktTypeRef, componentType, false)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return isTheSame(psiType, KtLightAnnotationsValuesKt.psiType$default(qualifiedName, ktTypeRef, false, 4, null));
    }

    private final boolean isTheSame(PsiType psiType, PsiType psiType2) {
        String canonicalText = psiType.getCanonicalText();
        Intrinsics.checkNotNullExpressionValue(canonicalText, "getCanonicalText(...)");
        int length = canonicalText.length();
        for (int i = 0; i < length; i++) {
            if (canonicalText.charAt(i) == '<') {
                canonicalText = canonicalText.substring(0, i);
                Intrinsics.checkNotNullExpressionValue(canonicalText, "substring(...)");
                break;
            }
        }
        return Intrinsics.areEqual(canonicalText, psiType2.getCanonicalText());
    }

    /* JADX INFO: compiled from: KotlinDeclarationInCompiledFileSearcher.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/KotlinDeclarationInCompiledFileSearcher$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/KotlinDeclarationInCompiledFileSearcher;", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinDeclarationInCompiledFileSearcher getInstance() {
            Object service = ApplicationManager.getApplication().getService(KotlinDeclarationInCompiledFileSearcher.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (KotlinDeclarationInCompiledFileSearcher) service;
        }
    }
}
