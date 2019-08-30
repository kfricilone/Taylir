package me.kfricilone.taylir.common.mlir.exprs.cst;

import lombok.EqualsAndHashCode;
import lombok.Value;
import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.ExprVisitor;
import me.kfricilone.taylir.common.mlir.exprs.CstExpr;

/**
 * Created by Kyle Fricilone on Dec 06, 2018.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class IntCstExpr extends CstExpr
{

	/**
	 * The int constant
	 */
	private final int cst;

	/**
	 * Calls the corresponding visitor method
	 *
	 * @param visitor The expr visitor
	 */
	@Override
	public void accept(ExprVisitor visitor)
	{
		visitor.visitIntCstExpr(this);
	}

	/**
	 * Creates a copy of an int constant expression
	 *
	 * @return The copy
	 */
	@Override
	public Expr copy()
	{
		return new IntCstExpr(cst);
	}

	/**
	 * Formats the expr to a pseudocode representation.
	 *
	 * @return The pseudocode representation
	 */
	@Override
	public String toPseudocode()
	{
		return String.valueOf(cst);
	}
}
