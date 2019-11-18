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

package me.kfricilone.taylir.common.mlir;

import me.kfricilone.taylir.common.mlir.exprs.ArithmeticExpr;
import me.kfricilone.taylir.common.mlir.exprs.BitwiseExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.DoubleCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.FloatCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.IntCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.LongCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.StringCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.invoke.InvokeStaticExpr;
import me.kfricilone.taylir.common.mlir.exprs.invoke.InvokeVirtualExpr;
import me.kfricilone.taylir.common.mlir.exprs.var.IntVarExpr;

/**
 * Created by Kyle Fricilone on Jan 16, 2019.
 */
public class ExprVisitor
{

	/**
	 * Visits a double constant expression
	 *
	 * @param doubleCst The int constant
	 */
	public void visitDoubleCstExpr(DoubleCstExpr doubleCst)
	{

	}

	/**
	 * Visits a float constant expression
	 *
	 * @param floatCst The int constant
	 */
	public void visitFloatCstExpr(FloatCstExpr floatCst)
	{

	}

	/**
	 * Visits an int constant expression
	 *
	 * @param intCst The int constant
	 */
	public void visitIntCstExpr(IntCstExpr intCst)
	{

	}

	/**
	 * Visits a long constant expression
	 *
	 * @param longCst The int constant
	 */
	public void visitLongCstExpr(LongCstExpr longCst)
	{

	}

	/**
	 * Visits a string constant expression
	 *
	 * @param stringCst The int constant
	 */
	public void visitStringCstExpr(StringCstExpr stringCst)
	{

	}

	/**
	 * Visits a static invoke expresison
	 *
	 * @param invokeStatic The static invoke
	 */
	public void visitInvokeStaticExpr(InvokeStaticExpr invokeStatic)
	{

	}

	/**
	 * Visits a virtual invoke expresison
	 *
	 * @param invokeVirtual The virtual invoke
	 */
	public void visitInvokeVirtualExpr(InvokeVirtualExpr invokeVirtual)
	{

	}

	/**
	 * Visits an arithmetic expresison
	 *
	 * @param arithmetic The arithmetic operation
	 */
	public void visitArithmeticExpr(ArithmeticExpr arithmetic)
	{

	}

	/**
	 * Visits a bitwise expresison
	 *
	 * @param bitwise The bitwise operation
	 */
	public void visitBitwiseExpr(BitwiseExpr bitwise)
	{

	}

	/**
	 * Visits an int var expression
	 *
	 * @param intVar The int var
	 */
	public void visitIntVarExpr(IntVarExpr intVar)
	{

	}

}
