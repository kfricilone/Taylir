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
import me.kfricilone.taylir.common.mlir.exprs.cst.StringCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.field.StaticFieldExpr;
import me.kfricilone.taylir.common.mlir.exprs.invoke.InvokeVirtualExpr;
import me.kfricilone.taylir.java.comp.CompilationContext;
import me.kfricilone.taylir.java.comp.ast.impl.MethodCallNode;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.JavaParserBaseVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;

/**
 * Created by Kyle Fricilone on Nov 11, 2019.
 */
public class ExpressionVisitor extends JavaParserBaseVisitor<Expr>
{

	private final CompilationContext cctx;
	private final PrimaryVisitor primVisitor;
	private final MethodCallVisitor callVisitor;

	public ExpressionVisitor(CompilationContext cctx)
	{
		this.cctx = cctx;
		primVisitor = new PrimaryVisitor(this);
		callVisitor = new MethodCallVisitor(this);
	}

	@Override
	public Expr visitPrimaryExpression(JavaParser.PrimaryExpressionContext ctx)
	{
		return ctx.primary().accept(primVisitor);
	}

	@Override
	public Expr visitDotExpression(JavaParser.DotExpressionContext ctx)
	{
		Expr expr = ctx.expression().accept(this);

		if (ctx.IDENTIFIER() != null)
		{
			String s = ctx.IDENTIFIER().getText();
			if (expr instanceof StringCstExpr)
			{
				String owner = ((StringCstExpr) expr).getCst();
				String desc = resolveField(owner, s, true);

				return new StaticFieldExpr(cctx.resolveName(owner), s, desc);
			}
		}

		else if (ctx.methodCall() != null && expr instanceof StaticFieldExpr)
		{
			StaticFieldExpr field = (StaticFieldExpr) expr;
			MethodCallNode call = ctx.methodCall().accept(callVisitor);

			Type type = Type.getType(field.getDesc());
			String owner = type.getInternalName();

			String desc = "(Ljava/lang/String;)V";//resolveMethod(owner, call.getName(), false);

			return new InvokeVirtualExpr(owner, call.getName(), desc, field, call.getExprs());
		}

		return null;
	}

	private String resolveField(String owner, String name, boolean stat)
	{
		FieldNode fn = cctx.findFieldFirst(owner, name, stat);
		return fn.desc;
	}

	/*private String resolveMethod(String owner, String name, boolean stat)
	{
		MethodNode mn = cctx.findMethodFirst(owner, name, stat);
		return mn.desc;
	}*/

}
