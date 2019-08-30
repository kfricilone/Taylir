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
