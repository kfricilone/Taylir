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

package me.kfricilone.taylir.java.comp.codegen;

import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.StmtVisitor;
import me.kfricilone.taylir.common.mlir.exprs.InvokeExpr;
import me.kfricilone.taylir.common.mlir.stmts.ExprStmt;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Created by Kyle Fricilone on Dec 02, 2019.
 */
public class StmtGenVisitor extends StmtVisitor
{

	private final MethodVisitor methVisitor;
	private final ExprGenVisitor exprVisitor;

	public StmtGenVisitor(MethodVisitor methVisitor)
	{
		this.methVisitor = methVisitor;
		this.exprVisitor = new ExprGenVisitor(methVisitor);
	}

	@Override
	public void visitExprStmt(ExprStmt exprStmt)
	{
		Expr expr = exprStmt.getValue();
		expr.accept(exprVisitor);

		if (expr instanceof InvokeExpr)
		{
			InvokeExpr invokeExpr = (InvokeExpr) expr;

			String desc = invokeExpr.getDescriptor();
			Type type = Type.getType(desc);
			Type returnType = type.getReturnType();

			if (!returnType.equals(Type.VOID_TYPE))
			{
				if (returnType.equals(Type.LONG_TYPE) || returnType.equals(Type.DOUBLE_TYPE))
				{
					methVisitor.visitInsn(Opcodes.POP2);
				}

				else
				{
					methVisitor.visitInsn(Opcodes.POP);
				}
			}
		}
	}

}
