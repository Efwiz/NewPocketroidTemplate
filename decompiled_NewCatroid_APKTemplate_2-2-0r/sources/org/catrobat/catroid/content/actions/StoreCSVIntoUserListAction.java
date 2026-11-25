package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserList;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StoreCSVIntoUserListAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: StoreCSVIntoUserListAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\"\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0 0\u001f2\u0006\u0010!\u001a\u00020\"J\u0012\u0010#\u001a\u0004\u0018\u00010\u001b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0017\u0010$\u001a\u0004\u0018\u00010\"2\b\u0010\t\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010%J\u001c\u0010&\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0 \u0018\u00010\u001f2\u0006\u0010'\u001a\u00020(J\u0010\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020+H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006,"}, d2 = {"Lorg/catrobat/catroid/content/actions/StoreCSVIntoUserListAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "formulaCSVData", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormulaCSVData", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormulaCSVData", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "formulaColumnToExtract", "getFormulaColumnToExtract", "setFormulaColumnToExtract", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getUserList", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setUserList", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", "extractOrReplaceSeperator", "", "csvData", "", "insertColumnValuesIntoUserList", "", "allRows", "", "", "columnToExtract", "", "interpretCSVData", "interpretColumnToExtract", "(Lorg/catrobat/catroid/formulaeditor/Formula;)Ljava/lang/Integer;", "readCSVIntoList", "csvReader", "Lcom/opencsv/CSVReader;", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StoreCSVIntoUserListAction extends TemporalAction {
    private Formula formulaCSVData;
    private Formula formulaColumnToExtract;
    private Scope scope;
    private UserList userList;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFormulaColumnToExtract() {
        return this.formulaColumnToExtract;
    }

    public final void setFormulaColumnToExtract(Formula formula) {
        this.formulaColumnToExtract = formula;
    }

    public final Formula getFormulaCSVData() {
        return this.formulaCSVData;
    }

    public final void setFormulaCSVData(Formula formula) {
        this.formulaCSVData = formula;
    }

    public final UserList getUserList() {
        return this.userList;
    }

    public final void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Integer numInterpretColumnToExtract;
        UserList userList = this.userList;
        if (userList == null) {
            return;
        }
        if (userList != null) {
            userList.reset();
        }
        String strInterpretCSVData = interpretCSVData(this.formulaCSVData);
        if (strInterpretCSVData == null || (numInterpretColumnToExtract = interpretColumnToExtract(this.formulaColumnToExtract)) == null) {
            return;
        }
        int iIntValue = numInterpretColumnToExtract.intValue() - 1;
        CSVReader cSVReaderBuild = new CSVReaderBuilder(new StringReader(strInterpretCSVData)).withCSVParser(new CSVParserBuilder().withSeparator(extractOrReplaceSeperator(strInterpretCSVData)).build()).build();
        Intrinsics.checkNotNull(cSVReaderBuild);
        List<String[]> cSVIntoList = readCSVIntoList(cSVReaderBuild);
        if (cSVIntoList != null && cSVIntoList.size() > 0 && iIntValue >= 0) {
            insertColumnValuesIntoUserList(cSVIntoList, iIntValue);
        }
    }

    public final String interpretCSVData(Formula formulaCSVData) {
        if (formulaCSVData == null) {
            return null;
        }
        try {
            return formulaCSVData.interpretString(this.scope);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Couldn't interpret formula", e);
            return null;
        }
    }

    public final Integer interpretColumnToExtract(Formula formulaColumnToExtract) {
        if (formulaColumnToExtract == null) {
            return null;
        }
        try {
            return formulaColumnToExtract.interpretInteger(this.scope);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Couldn't interpret formula", e);
            return null;
        }
    }

    public final char extractOrReplaceSeperator(String csvData) {
        Intrinsics.checkNotNullParameter(csvData, "csvData");
        Matcher matcher = Pattern.compile("^(?:\".*?\"|[^,;]*?)([,;])").matcher(csvData);
        char cCharAt = matcher.find() ? matcher.group(1).charAt(0) : ',';
        if (cCharAt == ',' || cCharAt == ';') {
            return cCharAt;
        }
        return ',';
    }

    public final List<String[]> readCSVIntoList(CSVReader csvReader) {
        Intrinsics.checkNotNullParameter(csvReader, "csvReader");
        try {
            return csvReader.readAll();
        } catch (CsvException e) {
            Log.e(getClass().getSimpleName(), "Couldn't validate csv data", e);
            return null;
        } catch (IOException e2) {
            Log.e(getClass().getSimpleName(), "Couldn't read csv data", e2);
            return null;
        }
    }

    public final void insertColumnValuesIntoUserList(List<String[]> allRows, int columnToExtract) {
        UserList userList;
        Intrinsics.checkNotNullParameter(allRows, "allRows");
        boolean z = true;
        for (String[] strArr : allRows) {
            if (strArr.length <= columnToExtract) {
                UserList userList2 = this.userList;
                if (userList2 != null) {
                    userList2.addListItem("");
                }
            } else {
                UserList userList3 = this.userList;
                if (userList3 != null) {
                    userList3.addListItem(strArr[columnToExtract]);
                }
                z = false;
            }
        }
        if (!z || (userList = this.userList) == null) {
            return;
        }
        userList.reset();
    }
}
