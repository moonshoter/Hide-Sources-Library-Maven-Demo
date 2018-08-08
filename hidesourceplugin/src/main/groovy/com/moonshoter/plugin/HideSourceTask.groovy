package com.moonshoter.plugin

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.ConstructorDeclaration
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.expr.FieldAccessExpr
import com.github.javaparser.ast.expr.MethodCallExpr
import com.github.javaparser.ast.expr.NameExpr
import com.github.javaparser.ast.expr.StringLiteralExpr
import com.github.javaparser.ast.stmt.BlockStmt
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import org.gradle.jvm.tasks.Jar

/**
 * @author moonshoter
 */
public class HideSourceTask extends Jar {

    HideSourceTask() {
        super()
        group = 'artifacts'
        classifier = "sources-hide"
        filter(CodeFilterReader.class)
    }

    static class CodeFilterReader extends FilterReader {

        CodeFilterReader(Reader reader) {
            super(reader)
            CompilationUnit compilationUnit = JavaParser.parse(reader)
            compilationUnit.accept(new MethodVisitor(), null)
            String codeAfterHide = compilationUnit.toString()
            this.in = new StringReader(codeAfterHide)
            reader.close()
        }


        private static class MethodVisitor extends VoidVisitorAdapter<Void> {

            @Override
            void visit(MethodDeclaration n, Void arg) {
                //清除原数据
                n.removeBody()
                //修改
                BlockStmt block = new BlockStmt()
                n.setBody(block)
                NameExpr clazz = new NameExpr("System")
                FieldAccessExpr field = new FieldAccessExpr(clazz, "out")
                MethodCallExpr call = new MethodCallExpr(field, "println")
                call.addArgument(new StringLiteralExpr("Some Unspoken Thing~~"))
                block.addStatement(call)

            }

            @Override
            void visit(ConstructorDeclaration n, Void arg) {
                if (n.body != null) {
                    n.body.statements.clear()
                }
                //修改
                BlockStmt block = new BlockStmt()
                n.body = block
                NameExpr clazz = new NameExpr("System")
                FieldAccessExpr field = new FieldAccessExpr(clazz, "out")
                MethodCallExpr call = new MethodCallExpr(field, "println")
                call.addArgument(new StringLiteralExpr("Some Unspoken Thing~~"))
                block.addStatement(call)
            }


        }


    }


}
