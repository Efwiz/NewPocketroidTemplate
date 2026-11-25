package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.libraries.CustomBrickDefinition;
import org.catrobat.catroid.libraries.LibraryManager;
import org.catrobat.catroid.libraries.LoadedLibrary;
import org.catrobat.catroid.utils.lunoscript.Interpreter;
import org.catrobat.catroid.utils.lunoscript.LunoRuntimeError;
import org.catrobat.catroid.utils.lunoscript.LunoValue;
import org.catrobat.catroid.utils.lunoscript.PauseExecutionSignal;
import org.catrobat.catroid.utils.lunoscript.Token;
import org.catrobat.catroid.utils.lunoscript.TokenType;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CustomAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CustomAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/content/actions/CustomAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "definition", "Lorg/catrobat/catroid/libraries/CustomBrickDefinition;", "getDefinition", "()Lorg/catrobat/catroid/libraries/CustomBrickDefinition;", "setDefinition", "(Lorg/catrobat/catroid/libraries/CustomBrickDefinition;)V", "parameterFormulas", "", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getParameterFormulas", "()Ljava/util/List;", "setParameterFormulas", "(Ljava/util/List;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomAction extends TemporalAction {
    public CustomBrickDefinition definition;
    public List<? extends Formula> parameterFormulas;
    public Scope scope;

    public final Scope getScope() {
        Scope scope = this.scope;
        if (scope != null) {
            return scope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        return null;
    }

    public final void setScope(Scope scope) {
        Intrinsics.checkNotNullParameter(scope, "<set-?>");
        this.scope = scope;
    }

    public final CustomBrickDefinition getDefinition() {
        CustomBrickDefinition customBrickDefinition = this.definition;
        if (customBrickDefinition != null) {
            return customBrickDefinition;
        }
        Intrinsics.throwUninitializedPropertyAccessException("definition");
        return null;
    }

    public final void setDefinition(CustomBrickDefinition customBrickDefinition) {
        Intrinsics.checkNotNullParameter(customBrickDefinition, "<set-?>");
        this.definition = customBrickDefinition;
    }

    public final List<Formula> getParameterFormulas() {
        List list = this.parameterFormulas;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("parameterFormulas");
        return null;
    }

    public final void setParameterFormulas(List<? extends Formula> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.parameterFormulas = list;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        LoadedLibrary loadedLibrary = LibraryManager.INSTANCE.getLoadedLibrary(getDefinition().getOwnerLibraryId());
        if (loadedLibrary == null) {
            Log.e("CustomAction", "Библиотека " + getDefinition().getOwnerLibraryId() + " не найдена для блока " + getDefinition().getId());
            return;
        }
        Interpreter interpreter = loadedLibrary.getInterpreter();
        try {
            LunoValue lunoValue = interpreter.getGlobals().get(new Token(TokenType.IDENTIFIER, getDefinition().getLunoFunctionName(), null, -1, -1));
            LunoValue.Callable callable = lunoValue instanceof LunoValue.Callable ? (LunoValue.Callable) lunoValue : null;
            if (callable == null) {
                throw new LunoRuntimeError("Функция '" + getDefinition().getLunoFunctionName() + "' не найдена в библиотеке", 0, null, 6, null);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new LunoValue.NativeObject(getScope().getSprite()));
            Iterator<T> it = getParameterFormulas().iterator();
            while (it.hasNext()) {
                arrayList.add(LunoValue.INSTANCE.fromKotlin(((Formula) it.next()).interpretObject(getScope())));
            }
            callable.call(interpreter, arrayList, new Token(TokenType.EOF, "", null, -1, -1));
        } catch (LunoRuntimeError e) {
            Log.e("CustomAction", "Ошибка выполнения скрипта из блока " + getDefinition().getId() + ": " + e.getMessage(), e);
        } catch (PauseExecutionSignal unused) {
        }
    }
}
