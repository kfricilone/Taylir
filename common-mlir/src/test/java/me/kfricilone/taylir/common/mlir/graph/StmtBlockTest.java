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

package me.kfricilone.taylir.common.mlir.graph;

import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.exprs.ArithmeticExpr;
import me.kfricilone.taylir.common.mlir.exprs.BitwiseExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.IntCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.StringCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.invoke.InvokeStaticExpr;
import me.kfricilone.taylir.common.mlir.exprs.var.IntVarExpr;
import me.kfricilone.taylir.common.mlir.stmts.PopStmt;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Jan 16, 2019.
 */
public class StmtBlockTest
{

	@Test
	public void testHashCode()
	{
		IntCstExpr intCst = new IntCstExpr(47);
		StringCstExpr stringCst = new StringCstExpr("game world");
		ArithmeticExpr arith = new ArithmeticExpr(ArithmeticExpr.Operator.ADD, new IntVarExpr(3), new IntCstExpr(32));
		BitwiseExpr bitwise = new BitwiseExpr(BitwiseExpr.Operator.XOR, arith, new IntCstExpr(0xFFFF));

		List<Expr> args = new ArrayList<>();
		args.add(intCst);
		args.add(stringCst);
		args.add(bitwise);

		InvokeStaticExpr invoke = new InvokeStaticExpr("class6", "method2", "(ILjava/lang/String;I)V", args);

		intCst = new IntCstExpr(99);

		args = new ArrayList<>();
		args.add(intCst);
		args.add(invoke);

		InvokeStaticExpr invoke2 = new InvokeStaticExpr("class2", "method8", "(ILjava/lang/String;I)V", args);
		PopStmt pop = new PopStmt(invoke2);
		PopStmt pop2 = (PopStmt) pop.copy();

		StmtBlock block = new StmtBlock(0);
		block.getStatements().add(pop);
		block.getStatements().add(pop2);

		StmtBlock block2 = new StmtBlock(0);
		block2.getStatements().add(pop2);

		System.out.println((block.hashCode() == block2.hashCode()) + ", " + block.equals(block2));
	}

}
