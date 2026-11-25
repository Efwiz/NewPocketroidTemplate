package org.jetbrains.kotlin.cfg.pseudocode;

import java.util.Collection;
import org.jetbrains.kotlin.cfg.ControlFlowProcessor;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.VariableDeclarationInstruction;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.PropertyImportedFromObject;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes2.dex */
public class PseudocodeUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 9:
            case 11:
                objArr[0] = "bindingContext";
                break;
            case 2:
            default:
                objArr[0] = "declaration";
                break;
            case 4:
                objArr[0] = "languageVersionSettings";
                break;
            case 5:
                objArr[0] = "org/jetbrains/kotlin/cfg/pseudocode/PseudocodeUtil";
                break;
            case 6:
            case 8:
            case 10:
                objArr[0] = "instruction";
                break;
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/cfg/pseudocode/PseudocodeUtil";
        } else {
            objArr[1] = "generatePseudocode";
        }
        switch (i) {
            case 5:
                break;
            case 6:
            case 7:
                objArr[2] = "extractVariableDescriptorFromReference";
                break;
            case 8:
            case 9:
                objArr[2] = "extractVariableDescriptorIfAny";
                break;
            case 10:
            case 11:
                objArr[2] = "isThisOrNoDispatchReceiver";
                break;
            default:
                objArr[2] = "generatePseudocode";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    public static Pseudocode generatePseudocode(KtDeclaration ktDeclaration, BindingContext bindingContext) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(1);
        }
        return generatePseudocode(ktDeclaration, bindingContext, LanguageVersionSettingsImpl.DEFAULT);
    }

    public static Pseudocode generatePseudocode(KtDeclaration ktDeclaration, final BindingContext bindingContext, LanguageVersionSettings languageVersionSettings) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(2);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(3);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(4);
        }
        Pseudocode pseudocodeGeneratePseudocode = new ControlFlowProcessor(new BindingTrace() { // from class: org.jetbrains.kotlin.cfg.pseudocode.PseudocodeUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
                Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
                if (i == 2 || i == 3) {
                    objArr[0] = "expression";
                } else if (i != 4) {
                    objArr[0] = "org/jetbrains/kotlin/cfg/pseudocode/PseudocodeUtil$1";
                } else {
                    objArr[0] = "diagnostic";
                }
                if (i == 1) {
                    objArr[1] = "getKeys";
                } else if (i == 2 || i == 3 || i == 4) {
                    objArr[1] = "org/jetbrains/kotlin/cfg/pseudocode/PseudocodeUtil$1";
                } else {
                    objArr[1] = "getBindingContext";
                }
                if (i == 2) {
                    objArr[2] = "getType";
                } else if (i == 3) {
                    objArr[2] = "recordType";
                } else if (i == 4) {
                    objArr[2] = "report";
                }
                String str2 = String.format(str, objArr);
                if (i != 2 && i != 3 && i != 4) {
                    throw new IllegalStateException(str2);
                }
                throw new IllegalArgumentException(str2);
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            public <K> void record(WritableSlice<K, Boolean> writableSlice, K k) {
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            public <K, V> void record(WritableSlice<K, V> writableSlice, K k, V v) {
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            public void recordType(KtExpression ktExpression, KotlinType kotlinType) {
                if (ktExpression == null) {
                    $$$reportNull$$$0(3);
                }
            }

            @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
            public void report(Diagnostic diagnostic) {
                if (diagnostic == null) {
                    $$$reportNull$$$0(4);
                }
            }

            @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
            public boolean wantsDiagnostics() {
                return false;
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            /* JADX INFO: renamed from: getBindingContext */
            public BindingContext getContext() {
                BindingContext bindingContext2 = bindingContext;
                if (bindingContext2 == null) {
                    $$$reportNull$$$0(0);
                }
                return bindingContext2;
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
                return (V) bindingContext.get(readOnlySlice, k);
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
                Collection<K> keys = bindingContext.getKeys(writableSlice);
                if (keys == null) {
                    $$$reportNull$$$0(1);
                }
                return keys;
            }

            @Override // org.jetbrains.kotlin.resolve.BindingTrace
            public KotlinType getType(KtExpression ktExpression) {
                if (ktExpression == null) {
                    $$$reportNull$$$0(2);
                }
                return bindingContext.getType(ktExpression);
            }
        }, languageVersionSettings).generatePseudocode(ktDeclaration);
        if (pseudocodeGeneratePseudocode == null) {
            $$$reportNull$$$0(5);
        }
        return pseudocodeGeneratePseudocode;
    }

    public static VariableDescriptor extractVariableDescriptorFromReference(Instruction instruction, BindingContext bindingContext) {
        if (instruction == null) {
            $$$reportNull$$$0(6);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(7);
        }
        if (!(instruction instanceof AccessValueInstruction) || (((AccessValueInstruction) instruction).getElement() instanceof KtDeclaration)) {
            return null;
        }
        VariableDescriptor variableDescriptorExtractVariableDescriptorIfAny = extractVariableDescriptorIfAny(instruction, bindingContext);
        return variableDescriptorExtractVariableDescriptorIfAny instanceof PropertyImportedFromObject ? ((PropertyImportedFromObject) variableDescriptorExtractVariableDescriptorIfAny).getCallableFromObject() : variableDescriptorExtractVariableDescriptorIfAny;
    }

    public static VariableDescriptor extractVariableDescriptorIfAny(Instruction instruction, BindingContext bindingContext) {
        if (instruction == null) {
            $$$reportNull$$$0(8);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(9);
        }
        if (instruction instanceof VariableDeclarationInstruction) {
            return BindingContextUtils.variableDescriptorForDeclaration((DeclarationDescriptor) bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ((VariableDeclarationInstruction) instruction).getVariableDeclarationElement()));
        }
        if (!(instruction instanceof AccessValueInstruction)) {
            return null;
        }
        AccessTarget target = ((AccessValueInstruction) instruction).getTarget();
        if (target instanceof AccessTarget.Declaration) {
            return ((AccessTarget.Declaration) target).getDescriptor();
        }
        if (target instanceof AccessTarget.Call) {
            return BindingContextUtils.variableDescriptorForDeclaration(((AccessTarget.Call) target).getResolvedCall().getResultingDescriptor());
        }
        return null;
    }

    public static boolean isThisOrNoDispatchReceiver(AccessValueInstruction accessValueInstruction, BindingContext bindingContext) {
        if (accessValueInstruction == null) {
            $$$reportNull$$$0(10);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(11);
        }
        if (accessValueInstruction.getReceiverValues().isEmpty()) {
            return true;
        }
        AccessTarget target = accessValueInstruction.getTarget();
        if (target instanceof AccessTarget.BlackBox) {
            return false;
        }
        return ResolvedCallUtilKt.hasThisOrNoDispatchReceiver(((AccessTarget.Call) target).getResolvedCall(), bindingContext);
    }
}
