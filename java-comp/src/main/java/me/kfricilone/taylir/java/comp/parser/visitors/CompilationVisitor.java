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

import lombok.AllArgsConstructor;
import me.kfricilone.taylir.java.arch.Classpath;
import me.kfricilone.taylir.java.comp.CompilationContext;
import me.kfricilone.taylir.java.comp.ast.impl.TypeDeclNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
@AllArgsConstructor
public class CompilationVisitor extends JavaParserBaseVisitor<TypeDeclNode>
{

	private static ImportDeclVisitor importVisitor = new ImportDeclVisitor();

	private final Classpath cp;

	@Override
	public TypeDeclNode visitCompilationUnit(JavaParser.CompilationUnitContext ctx)
	{

		Map<String, String> imports = new HashMap<>();
		if (ctx.importDeclaration() != null)
		{
			List<JavaParser.ImportDeclarationContext> ictxs = ctx.importDeclaration();
			for (int i = 0; i < ictxs.size(); i++)
			{
				JavaParser.ImportDeclarationContext ictx = ictxs.get(i);

				String qualifiedName = ictx.accept(importVisitor);
				String name = qualifiedName;

				if (name.contains("."))
				{
					int index = name.lastIndexOf(".");
					name = name.substring(index, name.length());
				}

				imports.put(qualifiedName.replace(".", "/"), name);
			}
		}

		CompilationContext cctx = new CompilationContext(cp, imports);
		TypeDeclVisitor typeVisitor = new TypeDeclVisitor(cctx);

		JavaParser.TypeDeclarationContext tctx = ctx.typeDeclaration(0);


		return tctx.accept(typeVisitor);
	}

}
