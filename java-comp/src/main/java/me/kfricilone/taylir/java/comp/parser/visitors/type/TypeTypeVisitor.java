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

package me.kfricilone.taylir.java.comp.parser.visitors.type;

import me.kfricilone.taylir.java.comp.CompilationContext;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;
import org.objectweb.asm.Type;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class TypeTypeVisitor extends JavaParserBaseVisitor<Type>
{

	private static final PrimitiveTypeVisitor primVisitor = new PrimitiveTypeVisitor();

	private final CompilationContext cctx;

	public TypeTypeVisitor(CompilationContext cctx)
	{
		this.cctx = cctx;
	}

	@Override
	public Type visitTypeType(JavaParser.TypeTypeContext ctx)
	{

		Type type;

		if (ctx.classOrInterfaceType() != null)
		{
			JavaParser.ClassOrInterfaceTypeContext tctx = ctx.classOrInterfaceType();
			StringBuilder bldr = new StringBuilder(tctx.IDENTIFIER(0).getText());

			if (tctx.IDENTIFIER().size() > 1)
			{
				for (int i = 1; i < tctx.IDENTIFIER().size(); i++)
				{
					bldr.append("/").append(tctx.IDENTIFIER(i));
				}
			}

			String name = cctx.resolveName(bldr.toString());
			type = Type.getObjectType(name);
		}

		else
		{
			type = ctx.primitiveType().accept(primVisitor);
		}

		int dims = ctx.LBRACK().size();

		StringBuilder bldr = new StringBuilder();
		for (int i = 0; i < dims; i++)
		{
			bldr.append("[");
		}

		bldr.append(type.getDescriptor());

		return Type.getType(bldr.toString());
	}

}
