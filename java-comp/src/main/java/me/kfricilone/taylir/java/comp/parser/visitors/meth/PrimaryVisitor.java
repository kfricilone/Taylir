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
import me.kfricilone.taylir.common.mlir.exprs.KeywordExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.StringCstExpr;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class PrimaryVisitor extends JavaParserBaseVisitor<Expr>
{

	private static final LiteralVisitor litVisitor = new LiteralVisitor();

	private final ExpressionVisitor exprVisitor;

	public PrimaryVisitor(ExpressionVisitor exprVisitor)
	{
		this.exprVisitor = exprVisitor;
	}

	@Override
	public Expr visitPrimary(JavaParser.PrimaryContext ctx)
	{
		if (ctx.LPAREN() != null)
		{
			return ctx.expression().accept(exprVisitor);
		}

		else if (ctx.THIS() != null)
		{
			return new KeywordExpr(KeywordExpr.Keyword.THIS);
		}

		else if (ctx.SUPER() != null)
		{
			return new KeywordExpr(KeywordExpr.Keyword.SUPER);
		}

		else if (ctx.literal() != null)
		{
			return ctx.literal().accept(litVisitor);
		}

		else if (ctx.IDENTIFIER() != null)
		{
			return new StringCstExpr(ctx.IDENTIFIER().getText());
		}

		return null;
	}


}
