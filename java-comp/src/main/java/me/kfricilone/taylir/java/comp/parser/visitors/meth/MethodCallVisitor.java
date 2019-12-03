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

import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.java.comp.ast.impl.MethodCallNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class MethodCallVisitor extends JavaParserBaseVisitor<MethodCallNode>
{

	private final ExpressionVisitor exprVisitor;

	public MethodCallVisitor(ExpressionVisitor exprVisitor)
	{
		this.exprVisitor = exprVisitor;
	}

	@Override
	public MethodCallNode visitMethodCall(JavaParser.MethodCallContext ctx)
	{

		List<Expr> expressions = new ArrayList<>();
		if (ctx.expressionList() != null)
		{
			JavaParser.ExpressionListContext lctx = ctx.expressionList();
			List<JavaParser.ExpressionContext> ectxs = lctx.expression();
			for (int i = 0; i < ectxs.size(); i++)
			{
				JavaParser.ExpressionContext ectx = ectxs.get(i);
				expressions.add(ectx.accept(exprVisitor));
			}
		}

		if (ctx.IDENTIFIER() != null)
		{
			String name = ctx.IDENTIFIER().getText();
			return new MethodCallNode(name, expressions);
		}

		return null;
	}


}
