package org.jetbrains.kotlin.com.intellij.psi.scope.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Objects;
import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCapturedWildcardType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.infos.ClassCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.JavaScopeProcessorEvent;
import org.jetbrains.kotlin.com.intellij.psi.scope.MethodProcessorSetupFailedException;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodsProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiScopesUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiScopesUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "entrance";
                break;
            case 2:
            case 6:
            case 8:
            case 10:
            case 12:
            case 16:
            default:
                objArr[0] = "processor";
                break;
            case 4:
            case 7:
                objArr[0] = "state";
                break;
            case 5:
                objArr[0] = "thisElement";
                break;
            case 9:
            case 11:
                objArr[0] = "ref";
                break;
            case 13:
                objArr[0] = "call";
                break;
            case 14:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 15:
                objArr[0] = "qualifierResult";
                break;
            case 17:
                objArr[0] = "methodCall";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/scope/util/PsiScopesUtil";
        switch (i) {
            case 5:
            case 6:
            case 7:
                objArr[2] = "walkChildrenScopes";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "resolveAndWalk";
                break;
            case 12:
            case 13:
                objArr[2] = "setupAndRunProcessor";
                break;
            case 14:
                objArr[2] = "processQualifierType";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "processQualifierResult";
                break;
            default:
                objArr[2] = "treeWalkUp";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private PsiScopesUtil() {
    }

    public static boolean treeWalkUp(PsiScopeProcessor psiScopeProcessor, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        return treeWalkUp(psiScopeProcessor, psiElement, psiElement2, ResolveState.initial());
    }

    public static boolean treeWalkUp(PsiScopeProcessor psiScopeProcessor, PsiElement psiElement, PsiElement psiElement2, ResolveState resolveState) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (!psiElement.isValid()) {
            LOG.error((Throwable) new PsiInvalidElementAccessException(psiElement));
        }
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        String name2 = nameHint == null ? null : nameHint.getName(resolveState);
        PsiElement context = psiElement;
        PsiElement psiElement3 = context;
        while (context != null) {
            ProgressIndicatorProvider.checkCanceled();
            if (context instanceof PsiClass) {
                psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.SET_CURRENT_FILE_CONTEXT, context);
            }
            if (!context.processDeclarations(psiScopeProcessor, resolveState, psiElement3, psiElement)) {
                return false;
            }
            if (context == psiElement2) {
                return true;
            }
            psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.EXIT_LEVEL, context);
            psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.CHANGE_LEVEL, null);
            psiElement3 = context;
            context = context instanceof PsiClassImpl ? ((PsiClassImpl) context).getContext(name2) : context.getContext();
        }
        return true;
    }

    public static boolean walkChildrenScopes(PsiElement psiElement, PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement2, PsiElement psiElement3) {
        PsiElement prevSibling;
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(6);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(7);
        }
        if (psiElement2 == null || psiElement2.getLightParent() != psiElement) {
            prevSibling = null;
        } else {
            prevSibling = psiElement2.getPrevSibling();
            if (prevSibling == null) {
                return true;
            }
        }
        if (prevSibling == null) {
            prevSibling = psiElement.getLastChild();
        }
        while (prevSibling != null) {
            if (!prevSibling.processDeclarations(psiScopeProcessor, resolveState, null, psiElement3)) {
                return false;
            }
            prevSibling = prevSibling.getPrevSibling();
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void processTypeDeclarations(PsiType psiType, PsiElement psiElement, PsiScopeProcessor psiScopeProcessor) {
        if (psiType instanceof PsiArrayType) {
            PsiClass arrayClass = JavaPsiFacade.getElementFactory(psiElement.getProject()).getArrayClass(PsiUtil.getLanguageLevel(psiElement));
            PsiTypeParameter[] typeParameters = arrayClass.getTypeParameters();
            PsiSubstitutor psiSubstitutorPut = PsiSubstitutor.EMPTY;
            if (typeParameters.length > 0) {
                psiSubstitutorPut = psiSubstitutorPut.put(typeParameters[0], ((PsiArrayType) psiType).getComponentType());
            }
            arrayClass.processDeclarations(psiScopeProcessor, ResolveState.initial().put(PsiSubstitutor.KEY, psiSubstitutorPut), arrayClass, psiElement);
            return;
        }
        if (psiType instanceof PsiIntersectionType) {
            for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
                processTypeDeclarations(psiType2, psiElement, psiScopeProcessor);
            }
            return;
        }
        if (psiType instanceof PsiDisjunctionType) {
            processTypeDeclarations(((PsiDisjunctionType) psiType).getLeastUpperBound(), psiElement, psiScopeProcessor);
            return;
        }
        if (psiType instanceof PsiCapturedWildcardType) {
            PsiType psiTypeConvertToTypeParameter = convertToTypeParameter((PsiCapturedWildcardType) psiType, psiElement);
            if (psiTypeConvertToTypeParameter != null) {
                processTypeDeclarations(psiTypeConvertToTypeParameter, psiElement, psiScopeProcessor);
                return;
            }
            return;
        }
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(psiType);
        PsiElement psiElement2 = (PsiClass) classResolveResultResolveGenericsClassInType.getElement();
        if (psiElement2 != null) {
            psiElement2.processDeclarations(psiScopeProcessor, ResolveState.initial().put(PsiSubstitutor.KEY, classResolveResultResolveGenericsClassInType.getSubstitutor()), psiElement2, psiElement);
        }
    }

    public static boolean resolveAndWalk(PsiScopeProcessor psiScopeProcessor, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiElement psiElement) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(8);
        }
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(9);
        }
        return resolveAndWalk(psiScopeProcessor, psiJavaCodeReferenceElement, psiElement, false);
    }

    public static boolean resolveAndWalk(PsiScopeProcessor psiScopeProcessor, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiElement psiElement, boolean z) {
        PsiType type;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(10);
        }
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(11);
        }
        PsiElement qualifier = psiJavaCodeReferenceElement.getQualifier();
        if (psiJavaCodeReferenceElement.getReferenceNameElement() == null) {
            return true;
        }
        if (qualifier != null) {
            PsiSubstitutor substitutor = PsiSubstitutor.EMPTY;
            boolean z2 = qualifier instanceof PsiExpression;
            PsiElement element = null;
            if (z2 || (qualifier instanceof PsiJavaCodeReferenceElement) || (qualifier instanceof PsiTypeElement)) {
                if (z2) {
                    type = ((PsiExpression) qualifier).getType();
                    processTypeDeclarations(type, psiJavaCodeReferenceElement, psiScopeProcessor);
                } else if (qualifier instanceof PsiTypeElement) {
                    type = ((PsiTypeElement) qualifier).getType();
                    processTypeDeclarations(type, psiJavaCodeReferenceElement, psiScopeProcessor);
                } else {
                    type = null;
                }
                if (type == null && (qualifier instanceof PsiJavaCodeReferenceElement)) {
                    PsiJavaCodeReferenceElement psiJavaCodeReferenceElement2 = (PsiJavaCodeReferenceElement) qualifier;
                    JavaResolveResult javaResolveResultAdvancedResolve = psiJavaCodeReferenceElement2.advancedResolve(z);
                    PsiElement element2 = javaResolveResultAdvancedResolve.getElement();
                    substitutor = javaResolveResultAdvancedResolve.getSubstitutor();
                    if (element2 instanceof PsiVariable) {
                        PsiType psiTypeSubstitute = substitutor.substitute(((PsiVariable) element2).getType());
                        if (psiTypeSubstitute instanceof PsiClassType) {
                            PsiClassType.ClassResolveResult classResolveResultResolveGenerics = ((PsiClassType) psiTypeSubstitute).resolveGenerics();
                            element = classResolveResultResolveGenerics.getElement();
                            substitutor = substitutor.putAll(classResolveResultResolveGenerics.getSubstitutor());
                        }
                    } else if (element2 instanceof PsiMethod) {
                        PsiType psiTypeSubstitute2 = substitutor.substitute(((PsiMethod) element2).getReturnType());
                        if (psiTypeSubstitute2 instanceof PsiClassType) {
                            PsiClassType.ClassResolveResult classResolveResultResolveGenerics2 = ((PsiClassType) psiTypeSubstitute2).resolveGenerics();
                            PsiElement element3 = classResolveResultResolveGenerics2.getElement();
                            substitutor = substitutor.putAll(classResolveResultResolveGenerics2.getSubstitutor());
                            element = element3;
                        }
                        PsiType[] typeParameters = psiJavaCodeReferenceElement2.getTypeParameters();
                        if (element instanceof PsiClass) {
                            substitutor = substitutor.putAll((PsiClass) element, typeParameters);
                        }
                    } else {
                        if (element2 instanceof PsiClass) {
                            psiScopeProcessor.handleEvent(JavaScopeProcessorEvent.START_STATIC, null);
                        }
                        element = element2;
                    }
                }
            }
            if (element != null) {
                return element.processDeclarations(psiScopeProcessor, ResolveState.initial().put(PsiSubstitutor.KEY, substitutor), element, psiJavaCodeReferenceElement);
            }
            return true;
        }
        return treeWalkUp(psiScopeProcessor, psiJavaCodeReferenceElement, psiElement);
    }

    public static void setupAndRunProcessor(MethodsProcessor methodsProcessor, PsiCallExpression psiCallExpression, boolean z) throws MethodProcessorSetupFailedException {
        PsiEnumConstantInitializer initializingClass;
        if (methodsProcessor == null) {
            $$$reportNull$$$0(12);
        }
        if (psiCallExpression == null) {
            $$$reportNull$$$0(13);
        }
        if (psiCallExpression instanceof PsiMethodCallExpression) {
            PsiMethodCallExpression psiMethodCallExpression = (PsiMethodCallExpression) psiCallExpression;
            PsiReferenceExpression methodExpression = psiMethodCallExpression.getMethodExpression();
            methodsProcessor.setArgumentList(psiMethodCallExpression.getArgumentList());
            methodsProcessor.obtainTypeArguments(psiMethodCallExpression);
            if (!methodExpression.isQualified() || (methodExpression.getReferenceNameElement() instanceof PsiKeyword)) {
                PsiElement referenceNameElement = methodExpression.getReferenceNameElement();
                if (referenceNameElement == null) {
                    return;
                }
                if (referenceNameElement instanceof PsiKeyword) {
                    PsiKeyword psiKeyword = (PsiKeyword) referenceNameElement;
                    if (psiKeyword.getTokenType() == JavaTokenType.THIS_KEYWORD) {
                        PsiClass contextClass = JavaResolveUtil.getContextClass(psiMethodCallExpression);
                        if (contextClass == null) {
                            throw new MethodProcessorSetupFailedException("Can't resolve class for this expression");
                        }
                        methodsProcessor.setIsConstructor(true);
                        methodsProcessor.setAccessClass(contextClass);
                        contextClass.processDeclarations(methodsProcessor, ResolveState.initial(), null, psiCallExpression);
                        if (z) {
                            processDummyConstructor(methodsProcessor, contextClass);
                            return;
                        }
                        return;
                    }
                    if (psiKeyword.getTokenType() == JavaTokenType.SUPER_KEYWORD) {
                        PsiClass contextClass2 = JavaResolveUtil.getContextClass(psiMethodCallExpression);
                        if (contextClass2 == null) {
                            throw new MethodProcessorSetupFailedException("Can't resolve class for super expression");
                        }
                        PsiClass superClass = contextClass2.getSuperClass();
                        if (superClass != null) {
                            PsiSubstitutor psiSubstitutorPutAll = PsiSubstitutor.EMPTY;
                            ArrayList arrayList = new ArrayList();
                            PsiClass superClass2 = superClass;
                            do {
                                if (superClass2 != null) {
                                    arrayList.add(TypeConversionUtil.getSuperClassSubstitutor(superClass2, contextClass2, PsiSubstitutor.EMPTY));
                                }
                                if (contextClass2.hasModifierProperty("static")) {
                                    break;
                                }
                                contextClass2 = JavaResolveUtil.getContextClass(contextClass2);
                                if (contextClass2 != null) {
                                    superClass2 = contextClass2.getSuperClass();
                                }
                            } while (contextClass2 != null);
                            for (int size = arrayList.size() - 1; size >= 0; size--) {
                                psiSubstitutorPutAll = psiSubstitutorPutAll.putAll((PsiSubstitutor) arrayList.get(size));
                            }
                            methodsProcessor.setIsConstructor(true);
                            methodsProcessor.setAccessClass(null);
                            PsiElement[] constructors = superClass.getConstructors();
                            ResolveState resolveStatePut = ResolveState.initial().put(PsiSubstitutor.KEY, psiSubstitutorPutAll);
                            for (PsiElement psiElement : constructors) {
                                if (!methodsProcessor.execute(psiElement, resolveStatePut)) {
                                    return;
                                }
                            }
                            if (z) {
                                processDummyConstructor(methodsProcessor, superClass);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    LOG.error("Unknown name element " + referenceNameElement + " in reference " + methodExpression.getText() + "(" + methodExpression + Constant.AFTER_QUTO);
                    return;
                }
                if (referenceNameElement instanceof PsiIdentifier) {
                    methodsProcessor.setIsConstructor(false);
                    methodsProcessor.setName(referenceNameElement.getText());
                    methodsProcessor.setAccessClass(null);
                    resolveAndWalk(methodsProcessor, methodExpression, null);
                    return;
                }
                LOG.error("Unknown name element " + referenceNameElement + " in reference " + methodExpression.getText() + "(" + methodExpression + Constant.AFTER_QUTO);
                return;
            }
            PsiElement referenceNameElement2 = psiMethodCallExpression.getMethodExpression().getReferenceNameElement();
            PsiManager manager = psiCallExpression.getManager();
            PsiElement qualifier = methodExpression.getQualifier();
            if (referenceNameElement2 == null) {
                throw new MethodProcessorSetupFailedException("Can't resolve method name for this expression");
            }
            if ((referenceNameElement2 instanceof PsiIdentifier) && (qualifier instanceof PsiExpression)) {
                PsiType type = ((PsiExpression) qualifier).getType();
                if (type != null && (qualifier instanceof PsiReferenceExpression)) {
                    PsiElement psiElementResolve = ((PsiReferenceExpression) qualifier).resolve();
                    if ((psiElementResolve instanceof PsiEnumConstant) && (initializingClass = ((PsiEnumConstant) psiElementResolve).getInitializingClass()) != null && getOverridingMethod(initializingClass, psiMethodCallExpression.getMethodExpression().getReferenceName()) != null) {
                        processQualifierResult(new ClassCandidateInfo(initializingClass, PsiSubstitutor.EMPTY), methodsProcessor, psiMethodCallExpression);
                        return;
                    }
                }
                if (type == null) {
                    if (qualifier instanceof PsiJavaCodeReferenceElement) {
                        JavaResolveResult javaResolveResultAdvancedResolve = ((PsiJavaCodeReferenceElement) qualifier).advancedResolve(false);
                        if (javaResolveResultAdvancedResolve.getElement() instanceof PsiClass) {
                            methodsProcessor.handleEvent(JavaScopeProcessorEvent.START_STATIC, null);
                            processQualifierResult(javaResolveResultAdvancedResolve, methodsProcessor, psiMethodCallExpression);
                            return;
                        }
                        return;
                    }
                    throw new MethodProcessorSetupFailedException("Cant determine qualifier type!");
                }
                if (type instanceof PsiDisjunctionType) {
                    processQualifierType(((PsiDisjunctionType) type).getLeastUpperBound(), methodsProcessor, manager, psiMethodCallExpression);
                    return;
                }
                if (type instanceof PsiCapturedWildcardType) {
                    PsiType psiTypeConvertToTypeParameter = convertToTypeParameter((PsiCapturedWildcardType) type, psiMethodCallExpression);
                    if (psiTypeConvertToTypeParameter != null) {
                        processQualifierType(psiTypeConvertToTypeParameter, methodsProcessor, manager, psiMethodCallExpression);
                        return;
                    }
                    return;
                }
                processQualifierType(type, methodsProcessor, manager, psiMethodCallExpression);
                return;
            }
            LOG.error(RefDirectory.SYMREF + methodExpression + " (" + methodExpression.getClass() + "), ref.getReferenceNameElement()=" + methodExpression.getReferenceNameElement() + "; methodCall.getMethodExpression().getReferenceNameElement()=" + psiMethodCallExpression.getMethodExpression().getReferenceNameElement() + "; qualifier=" + qualifier);
            return;
        }
        LOG.assertTrue(psiCallExpression instanceof PsiNewExpression);
        PsiNewExpression psiNewExpression = (PsiNewExpression) psiCallExpression;
        PsiJavaCodeReferenceElement classOrAnonymousClassReference = psiNewExpression.getClassOrAnonymousClassReference();
        if (classOrAnonymousClassReference == null) {
            throw new MethodProcessorSetupFailedException("Cant get reference to class in new expression");
        }
        JavaResolveResult javaResolveResultAdvancedResolve2 = classOrAnonymousClassReference.advancedResolve(false);
        PsiClass psiClass = (PsiClass) javaResolveResultAdvancedResolve2.getElement();
        if (psiClass == null) {
            throw new MethodProcessorSetupFailedException("Cant resolve class in new expression");
        }
        methodsProcessor.setIsConstructor(true);
        methodsProcessor.setAccessClass(psiClass);
        methodsProcessor.setArgumentList(psiNewExpression.getArgumentList());
        methodsProcessor.obtainTypeArguments(psiNewExpression);
        psiClass.processDeclarations(methodsProcessor, ResolveState.initial().put(PsiSubstitutor.KEY, javaResolveResultAdvancedResolve2.getSubstitutor()), null, psiCallExpression);
        if (z) {
            processDummyConstructor(methodsProcessor, psiClass);
        }
    }

    private static PsiType convertToTypeParameter(PsiCapturedWildcardType psiCapturedWildcardType, PsiElement psiElement) {
        GlobalSearchScope resolveScope = psiElement.getResolveScope();
        PsiType psiTypeCorrectType = PsiClassImplUtil.correctType(psiCapturedWildcardType.getUpperBound(), resolveScope);
        while (psiTypeCorrectType instanceof PsiCapturedWildcardType) {
            psiTypeCorrectType = PsiClassImplUtil.correctType(((PsiCapturedWildcardType) psiTypeCorrectType).getUpperBound(), resolveScope);
        }
        if (psiTypeCorrectType instanceof PsiArrayType) {
            return psiTypeCorrectType;
        }
        if (psiTypeCorrectType != null) {
            return InferenceSession.createTypeParameterTypeWithUpperBound(psiTypeCorrectType, psiElement);
        }
        return null;
    }

    public static PsiMethod getOverridingMethod(PsiAnonymousClass psiAnonymousClass, String str) {
        PsiClass psiClassResolveClassInType;
        PsiMethod[] psiMethodArrFindMethodsByName = psiAnonymousClass.findMethodsByName(str, false);
        if (psiMethodArrFindMethodsByName.length <= 0 || (psiClassResolveClassInType = PsiUtil.resolveClassInType(psiAnonymousClass.getBaseClassType())) == null || hasCovariantOverridingOrNotPublic(psiClassResolveClassInType, psiMethodArrFindMethodsByName)) {
            return null;
        }
        for (PsiMethod psiMethod : psiMethodArrFindMethodsByName) {
            if (psiMethod.findSuperMethods(psiClassResolveClassInType).length > 0) {
                return psiMethod;
            }
        }
        return null;
    }

    private static boolean hasCovariantOverridingOrNotPublic(PsiClass psiClass, PsiMethod[] psiMethodArr) {
        for (PsiMethod psiMethod : psiMethodArr) {
            PsiType returnType = psiMethod.getReturnType();
            for (PsiMethod psiMethod2 : psiMethod.findSuperMethods(psiClass)) {
                if (!Comparing.equal(returnType, psiMethod2.getReturnType()) || !psiMethod2.hasModifierProperty("public")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean processQualifierType(PsiType psiType, MethodsProcessor methodsProcessor, PsiManager psiManager, PsiMethodCallExpression psiMethodCallExpression) throws MethodProcessorSetupFailedException {
        if (psiType == null) {
            $$$reportNull$$$0(14);
        }
        PsiUtil.ensureValidType(psiType);
        if (psiType instanceof PsiClassType) {
            return processQualifierResult(((PsiClassType) psiType).resolveGenerics(), methodsProcessor, psiMethodCallExpression);
        }
        if (psiType instanceof PsiArrayType) {
            return processQualifierResult(JavaPsiFacade.getElementFactory(psiManager.getProject()).getArrayClassType(((PsiArrayType) psiType).getComponentType(), PsiUtil.getLanguageLevel(psiMethodCallExpression)).resolveGenerics(), methodsProcessor, psiMethodCallExpression);
        }
        if (!(psiType instanceof PsiIntersectionType)) {
            return true;
        }
        for (PsiType psiType2 : ((PsiIntersectionType) psiType).getConjuncts()) {
            if (!processQualifierType(psiType2, methodsProcessor, psiManager, psiMethodCallExpression)) {
                return false;
            }
        }
        return true;
    }

    private static boolean processQualifierResult(JavaResolveResult javaResolveResult, MethodsProcessor methodsProcessor, PsiMethodCallExpression psiMethodCallExpression) throws MethodProcessorSetupFailedException {
        if (javaResolveResult == null) {
            $$$reportNull$$$0(15);
        }
        if (methodsProcessor == null) {
            $$$reportNull$$$0(16);
        }
        if (psiMethodCallExpression == null) {
            $$$reportNull$$$0(17);
        }
        PsiElement element = javaResolveResult.getElement();
        if (element == null) {
            throw new MethodProcessorSetupFailedException("Cant determine qualifier class!");
        }
        if (element instanceof PsiTypeParameter) {
            methodsProcessor.setAccessClass((PsiClass) element);
        } else if (element instanceof PsiClass) {
            PsiExpression qualifierExpression = psiMethodCallExpression.getMethodExpression().getQualifierExpression();
            if (!(qualifierExpression instanceof PsiSuperExpression)) {
                methodsProcessor.setAccessClass((PsiClass) PsiUtil.getAccessObjectClass(qualifierExpression).getElement());
            } else if (((PsiSuperExpression) qualifierExpression).getQualifier() != null && PsiUtil.isLanguageLevel8OrHigher(qualifierExpression)) {
                PsiClass psiClass = (PsiClass) element;
                if (CommonClassNames.JAVA_LANG_CLONEABLE.equals(psiClass.getQualifiedName()) && psiClass.isInterface()) {
                    methodsProcessor.setAccessClass(psiClass);
                }
            }
        }
        methodsProcessor.setIsConstructor(false);
        methodsProcessor.setName(psiMethodCallExpression.getMethodExpression().getReferenceName());
        return element.processDeclarations(methodsProcessor, ResolveState.initial().put(PsiSubstitutor.KEY, javaResolveResult.getSubstitutor()), psiMethodCallExpression, psiMethodCallExpression);
    }

    private static void processDummyConstructor(MethodsProcessor methodsProcessor, PsiClass psiClass) {
        if (psiClass instanceof PsiAnonymousClass) {
            return;
        }
        try {
            if (psiClass.getConstructors().length != 0) {
                return;
            }
            PsiMethod psiMethodCreateConstructor = JavaPsiFacade.getElementFactory(psiClass.getProject()).createConstructor();
            PsiIdentifier psiIdentifierMo7392getNameIdentifier = psiClass.mo7392getNameIdentifier();
            if (psiIdentifierMo7392getNameIdentifier != null) {
                ((PsiIdentifier) Objects.requireNonNull(psiMethodCreateConstructor.mo7392getNameIdentifier())).replace(psiIdentifierMo7392getNameIdentifier);
            }
            methodsProcessor.forceAddResult(psiMethodCreateConstructor);
        } catch (IncorrectOperationException e) {
            LOG.error((Throwable) e);
        }
    }
}
