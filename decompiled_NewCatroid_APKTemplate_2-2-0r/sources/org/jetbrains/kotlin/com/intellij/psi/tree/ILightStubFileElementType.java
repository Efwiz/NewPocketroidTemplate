package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.LightPsiParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.stubs.LightStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public class ILightStubFileElementType<T extends PsiFileStub> extends IStubFileElementType<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public ILightStubFileElementType(Language language) {
        super(language);
    }

    public ILightStubFileElementType(String str, Language language) {
        super(str, language);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType
    public LightStubBuilder getBuilder() {
        return new LightStubBuilder();
    }

    public FlyweightCapableTreeStructure<LighterASTNode> parseContentsLight(ASTNode aSTNode) {
        Project project = aSTNode.getPsi().getProject();
        PsiBuilder psiBuilderCreateBuilder = PsiBuilderFactory.getInstance().createBuilder(project, aSTNode);
        PsiParser psiParserCreateParser = LanguageParserDefinitions.INSTANCE.forLanguage(getLanguage()).createParser(project);
        if (psiParserCreateParser instanceof LightPsiParser) {
            ((LightPsiParser) psiParserCreateParser).parseLight(this, psiBuilderCreateBuilder);
        } else {
            psiParserCreateParser.parse(this, psiBuilderCreateBuilder);
        }
        return psiBuilderCreateBuilder.getLightTree();
    }
}
