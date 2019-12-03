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
import me.kfricilone.taylir.java.comp.ast.impl.MemberDeclNode;
import me.kfricilone.taylir.java.comp.ast.types.MemberNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;
import me.kfricilone.taylir.java.comp.parser.visitors.mod.ModifierVisitor;

import java.util.List;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class ClassBodyDeclVisitor extends JavaParserBaseVisitor<MemberDeclNode>
{

	private static final ModifierVisitor modifierVisitor = new ModifierVisitor();

	private final MemberDeclVisitor memberVisitor;

	public ClassBodyDeclVisitor(CompilationContext cctx)
	{
		memberVisitor = new MemberDeclVisitor(cctx);
	}

	@Override
	public MemberDeclNode visitClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx)
	{


		if (ctx.memberDeclaration() != null)
		{
			int access = 0;
			List<JavaParser.ModifierContext> modifiers = ctx.modifier();
			for (int i = 0; i < modifiers.size(); i++)
			{
				JavaParser.ModifierContext modifier = modifiers.get(i);
				access |= modifier.accept(modifierVisitor);
			}

			MemberNode memberNode = ctx.memberDeclaration().accept(memberVisitor);

			return new MemberDeclNode(access, memberNode);
		}

		return null;
	}

}
