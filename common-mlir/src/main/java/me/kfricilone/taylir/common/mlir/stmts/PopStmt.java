package me.kfricilone.taylir.common.mlir.stmts;

import lombok.EqualsAndHashCode;
import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.Stmt;
import me.kfricilone.taylir.common.mlir.StmtVisitor;

/**
 * Created by Kyle Fricilone on Feb 21, 2019.
 */
@EqualsAndHashCode(callSuper = false)
public class PopStmt extends Stmt
{

	/**
	 * The expr to be popped by this statement
	 */
	private final Expr value;

	public PopStmt(Expr value)
	{
		this.value = value;

		getChildren().add(value);
	}

	/**
	 * Calls the corresponding visitor method
	 *
	 * @param visitor The statement visitor
	 */
	@Override
	public void accept(StmtVisitor visitor)
	{

	}

	/**
	 * Creates a copy of an expression
	 *
	 * @return The copy
	 */
	@Override
	public Stmt copy()
	{
		return new PopStmt(value.copy());
	}

	/**
	 * Formats the expr to a pseudocode representation.
	 *
	 * @return The pseudocode representation
	 */
	@Override
	public String toPseudocode()
	{
		return "pop(" + value.toPseudocode() + ")";
	}
}
