package me.kfricilone.taylir.common.mlir.exprs.cst;

import lombok.Value;
import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.ExprVisitor;
import me.kfricilone.taylir.common.mlir.exprs.CstExpr;

/**
 * Created by Kyle Fricilone on Dec 06, 2018.
 */
@Value
public class LongCstExpr extends CstExpr
{

	/**
	 * The long constant
	 */
	private final long cst;

	/**
	 * Calls the corresponding visitor method
	 *
	 * @param visitor The expr visitor
	 */
	@Override
	public void accept(ExprVisitor visitor)
	{
		visitor.visitLongCstExpr(this);
	}

	/**
	 * Creates a copy of a long constant expression
	 *
	 * @return The copy
	 */
	@Override
	public Expr copy()
	{
		return new LongCstExpr(cst);
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
