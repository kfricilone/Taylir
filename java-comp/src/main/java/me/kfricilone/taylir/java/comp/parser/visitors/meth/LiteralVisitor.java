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
import me.kfricilone.taylir.common.mlir.exprs.cst.CharCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.DoubleCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.FloatCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.IntCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.LongCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.StringCstExpr;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;

import static me.kfricilone.taylir.java.comp.util.NumberUtil.decodeInt;
import static me.kfricilone.taylir.java.comp.util.NumberUtil.decodeLong;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class LiteralVisitor extends JavaParserBaseVisitor<Expr>
{

	@Override
	public Expr visitLiteral(JavaParser.LiteralContext ctx)
	{
		if (ctx.integerLiteral() != null)
		{
			return ctx.integerLiteral().accept(this);
		}

		else if (ctx.floatLiteral() != null)
		{
			return ctx.floatLiteral().accept(this);
		}

		else if (ctx.CHAR_LITERAL() != null)
		{
			char c = ctx.CHAR_LITERAL().getText().charAt(1);
			return new CharCstExpr(c);
		}

		else if (ctx.STRING_LITERAL() != null)
		{
			String s = ctx.STRING_LITERAL().getText();
			return new StringCstExpr(s.substring(1, s.length() - 1));
		}

		return null;
	}

	@Override
	public Expr visitIntegerLiteral(JavaParser.IntegerLiteralContext ctx)
	{
		String s;
		if (ctx.DECIMAL_LITERAL() != null)
		{
			s = ctx.DECIMAL_LITERAL().getText();
		}

		else if (ctx.HEX_LITERAL() != null)
		{
			s = ctx.HEX_LITERAL().getText();
		}

		else if (ctx.OCT_LITERAL() != null)
		{
			s = ctx.OCT_LITERAL().getText();
		}

		else
		{
			s = ctx.BINARY_LITERAL().getText();
		}

		if (s.endsWith("l") || s.endsWith("L"))
		{
			s = s.substring(0, s.length() - 1);
			return new LongCstExpr(decodeLong(s));
		}

		return new IntCstExpr(decodeInt(s));
	}

	@Override
	public Expr visitFloatLiteral(JavaParser.FloatLiteralContext ctx)
	{
		String s;
		if (ctx.FLOAT_LITERAL() != null)
		{
			s = ctx.FLOAT_LITERAL().getText();
		}

		else
		{
			s = ctx.HEX_FLOAT_LITERAL().getText();
		}

		if (s.endsWith("d") || s.endsWith("D"))
		{
			s = s.substring(0, s.length() - 1);
			return new DoubleCstExpr(Double.longBitsToDouble(decodeLong(s)));
		}

		else if (s.endsWith("f") || s.endsWith("F"))
		{
			s = s.substring(0, s.length() - 1);
		}

		return new FloatCstExpr(Float.intBitsToFloat(decodeInt(s)));
	}

}
