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

package me.kfricilone.taylir.java.comp.parser.visitors;

import me.kfricilone.taylir.java.comp.CompilationContext;
import me.kfricilone.taylir.java.comp.ast.impl.ClassDeclNode;
import me.kfricilone.taylir.java.comp.ast.impl.TypeDeclNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;
import me.kfricilone.taylir.java.comp.parser.visitors.cls.ClassDeclVisitor;
import me.kfricilone.taylir.java.comp.parser.visitors.mod.ClassOrInterfaceModifierVisitor;

import java.util.List;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class TypeDeclVisitor extends JavaParserBaseVisitor<TypeDeclNode>
{

	private static final ClassOrInterfaceModifierVisitor modifierVisitor = new ClassOrInterfaceModifierVisitor();

	private final ClassDeclVisitor classVisitor;

	public TypeDeclVisitor(CompilationContext cctx)
	{
		classVisitor = new ClassDeclVisitor(cctx);
	}

	@Override
	public TypeDeclNode visitTypeDeclaration(JavaParser.TypeDeclarationContext ctx)
	{

		int access = 0;
		List<JavaParser.ClassOrInterfaceModifierContext> modifiers = ctx.classOrInterfaceModifier();
		for (int i = 0; i < modifiers.size(); i++)
		{
			JavaParser.ClassOrInterfaceModifierContext mctx = modifiers.get(i);
			access |= mctx.accept(modifierVisitor);
		}

		if (ctx.classDeclaration() != null)
		{
			ClassDeclNode cls = ctx.classDeclaration().accept(classVisitor);
			return new TypeDeclNode(access, cls);
		}

		return null;
	}

}
