package me.kfricilone.taylir.common.mlir.exprs.invoke;

import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.exprs.ArithmeticExpr;
import me.kfricilone.taylir.common.mlir.exprs.BitwiseExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.IntCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.StringCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.var.IntVarExpr;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Jan 16, 2019.
 */
public class InvokeVirtualExprTest
{

	@Test
	public void testPseudocode()
	{
		IntCstExpr intCst = new IntCstExpr(47);
		StringCstExpr stringCst = new StringCstExpr("game world");
		ArithmeticExpr arith = new ArithmeticExpr(ArithmeticExpr.Operator.ADD, new IntVarExpr(3), new IntCstExpr(32));
		BitwiseExpr bitwise = new BitwiseExpr(BitwiseExpr.Operator.XOR, arith, new IntCstExpr(0xFFFF));

		List<Expr> args = new ArrayList<>();
		args.add(intCst);
		args.add(stringCst);
		args.add(bitwise);

		InvokeStaticExpr invoke = new InvokeStaticExpr("class6", "method2", "(ILjava/lang/String;I)Lclass6;", args);
		InvokeVirtualExpr invoke2 = new InvokeVirtualExpr("class6", "method3", "()V", invoke, new ArrayList<>());

		System.out.println(invoke2);
		System.out.println(invoke2.toPseudocode());
	}

}
