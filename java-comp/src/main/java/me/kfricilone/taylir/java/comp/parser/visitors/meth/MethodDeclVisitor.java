/*
 * Copyright (c) 2018-2019, Kyle Fricilone <kfricilone@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package me.kfricilone.taylir.java.comp.parser.visitors.meth;

import me.kfricilone.taylir.java.comp.CompilationContext;
import me.kfricilone.taylir.java.comp.ast.impl.MethodBodyNode;
import me.kfricilone.taylir.java.comp.ast.impl.MethodDeclNode;
import me.kfricilone.taylir.java.comp.ast.impl.ParameterNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;
import me.kfricilone.taylir.java.comp.parser.visitors.type.TypeTypeOrVoidVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class MethodDeclVisitor extends JavaParserBaseVisitor<MethodDeclNode>
{

	private final TypeTypeOrVoidVisitor typeVisitor;
	private final FormalParameterListVisitor paramVisitor;
	private final MethodBodyVisitor bodyVisitor;

	public MethodDeclVisitor(CompilationContext cctx)
	{
		typeVisitor = new TypeTypeOrVoidVisitor(cctx);
		paramVisitor = new FormalParameterListVisitor(cctx);
		bodyVisitor = new MethodBodyVisitor(cctx);
	}

	@Override
	public MethodDeclNode visitMethodDeclaration(JavaParser.MethodDeclarationContext ctx)
	{

		Type type = ctx.typeTypeOrVoid().accept(typeVisitor);
		String name = ctx.IDENTIFIER().getText();

		List<ParameterNode> parameters = new ArrayList<>();
		JavaParser.FormalParametersContext fctxs = ctx.formalParameters();
		if (fctxs.formalParameterList() != null)
		{
			JavaParser.FormalParameterListContext lctx = fctxs.formalParameterList();
			parameters.addAll(lctx.accept(paramVisitor));
		}

		List<String> throwsList = new ArrayList<>();
		if (ctx.qualifiedNameList() != null)
		{
			List<JavaParser.QualifiedNameContext> qctxs = ctx.qualifiedNameList().qualifiedName();
			for (int i = 0; i < qctxs.size(); i++)
			{
				throwsList.add(qctxs.get(i).getText());
			}
		}

		MethodBodyNode body = ctx.methodBody().accept(bodyVisitor);

		return new MethodDeclNode(type, name, parameters, throwsList, body);
	}

}
