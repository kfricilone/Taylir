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

package me.kfricilone.taylir.java.comp.parser.visitors.cls;

import me.kfricilone.taylir.java.comp.CompilationContext;
import me.kfricilone.taylir.java.comp.ast.impl.ClassDeclNode;
import me.kfricilone.taylir.java.comp.ast.impl.MemberDeclNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class ClassDeclVisitor extends JavaParserBaseVisitor<ClassDeclNode>
{

	private final ClassBodyDeclVisitor memberVisitor;

	public ClassDeclVisitor(CompilationContext cctx)
	{
		memberVisitor = new ClassBodyDeclVisitor(cctx);
	}

	@Override
	public ClassDeclNode visitClassDeclaration(JavaParser.ClassDeclarationContext ctx)
	{

		String name = ctx.IDENTIFIER().getText();

		List<MemberDeclNode> members = new ArrayList<>();
		JavaParser.ClassBodyContext bctx = ctx.classBody();
		if (bctx.classBodyDeclaration() != null)
		{
			List<JavaParser.ClassBodyDeclarationContext> mctxs = bctx.classBodyDeclaration();
			for (int i = 0; i < mctxs.size(); i++)
			{
				JavaParser.ClassBodyDeclarationContext mctx = mctxs.get(i);
				members.add(mctx.accept(memberVisitor));
			}
		}


		return new ClassDeclNode(name, members);
	}

}
