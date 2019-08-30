package me.kfricilone.taylir.common.mlir.exprs.invoke;

import lombok.EqualsAndHashCode;
import lombok.Value;
import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.ExprVisitor;
import me.kfricilone.taylir.common.mlir.exprs.InvokeExpr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Jan 16, 2019.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class InvokeStaticExpr extends InvokeExpr
{

	/**
	 * The owner of the invoked method
	 */
	private final String owner;

	/**
	 * The nme of the invoked method
	 */
	private final String name;

	/**
	 * The descriptor of the invoked method
	 */
	private final String descriptor;

	/**
	 * The arguments of the invoke expression
	 */
	private final List<Expr> arguments;

	public InvokeStaticExpr(String owner, String name, String descriptor, List<Expr> args)
	{
		this.owner = owner;
		this.name = name;
		this.descriptor = descriptor;
		this.arguments = args;

		getChildren().addAll(args);
	}

	/**
	 * Calls the corresponding visitor method
	 *
	 * @param visitor The expr visitor
	 */
	@Override
	public void accept(ExprVisitor visitor)
	{
		arguments.forEach(a -> a.accept(visitor));
		visitor.visitInvokeStaticExpr(this);
	}

	/**
	 * Creates a copy of an expression
	 *
	 * @return The copy
	 */
	@Override
	public Expr copy()
	{
		List<Expr> copy = new ArrayList<>();
		arguments.forEach(a -> copy.add(a.copy()));
		return new InvokeStaticExpr(owner, name, descriptor, copy);
	}

	/**
	 * Formats the expr to a pseudocode representation.
	 *
	 * @return The pseudocode representation
	 */
	@Override
	public String toPseudocode()
	{
		StringBuilder bldr = new StringBuilder();
		bldr.append(owner).append(".").append(name).append("(");

		if (!arguments.isEmpty())
		{
			arguments.forEach(a ->
			{
				if (a.getChildren().size() > 0)
				{
					bldr.append("(").append(a.toPseudocode()).append("), ");
				}
				else
				{
					bldr.append(a.toPseudocode()).append(", ");
				}
			});

			bldr.delete(bldr.length() - 2, bldr.length());
		}

		return bldr.append(")").toString();
	}
}