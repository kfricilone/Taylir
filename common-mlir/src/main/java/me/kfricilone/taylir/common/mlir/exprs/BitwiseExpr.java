package me.kfricilone.taylir.common.mlir.exprs;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.ExprVisitor;
import me.kfricilone.taylir.common.mlir.exprs.cst.IntCstExpr;
import me.kfricilone.taylir.common.mlir.exprs.cst.LongCstExpr;

import java.util.Locale;

/**
 * Created by Kyle Fricilone on Jan 16, 2019.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class BitwiseExpr extends Expr
{

	@Getter
	@AllArgsConstructor
	public enum Operator
	{
		/**
		 * Shift Left expression
		 */
		SHL("<<"),

		/**
		 * Shift Right expression
		 */
		SHR(">>"),

		/**
		 * Unsigned Shift Right expression
		 */
		USHR(">>>"),

		/**
		 * AND expression
		 */
		AND("&"),

		/**
		 * Inclusive OR expression
		 */
		OR("|"),

		/**
		 * Exclusive OR expression
		 */
		XOR("^"),

		/**
		 * NOT expression
		 */
		NOT("~");

		private final String identifier;
	}

	/**
	 * The operator of the expression
	 */
	private final Operator operator;

	/**
	 * The left side expression
	 */
	private final Expr left;

	/**
	 * The right side expression
	 */
	private final Expr right;

	public BitwiseExpr(Operator operator, Expr left, Expr right)
	{
		this.operator = operator;
		this.left = left;
		this.right = right;

		getChildren().add(left);
		getChildren().add(right);
	}

	/**
	 * Calls the corresponding visitor method
	 *
	 * @param visitor The expr visitor
	 */
	@Override
	public void accept(ExprVisitor visitor)
	{
		left.accept(visitor);
		right.accept(visitor);
		visitor.visitBitwiseExpr(this);
	}

	/**
	 * Creates a copy of an expression
	 *
	 * @return The copy
	 */
	@Override
	public Expr copy()
	{
		return new BitwiseExpr(operator, left.copy(), right.copy());
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
		if (left.getChildren().size() > 0)
		{
			bldr.append("(").append(left.toPseudocode()).append(")");
		}
		else
		{
			if (left instanceof IntCstExpr)
			{
				bldr.append("0x").append(Integer.toHexString(((IntCstExpr) left).getCst()).toUpperCase(Locale.ENGLISH));
			}

			else if (left instanceof LongCstExpr)
			{
				bldr.append("0x").append(Long.toHexString(((LongCstExpr) left).getCst()).toUpperCase(Locale.ENGLISH));
			}
			else
			{
				bldr.append(left.toPseudocode());
			}
		}

		bldr.append(" ").append(operator.identifier).append(" ");

		if (right.getChildren().size() > 0)
		{
			bldr.append("(").append(right.toPseudocode()).append(")");
		}
		else
		{
			if (right instanceof IntCstExpr)
			{
				bldr.append("0x").append(Integer.toHexString(((IntCstExpr) right).getCst()).toUpperCase(Locale.ENGLISH));
			}

			else if (right instanceof LongCstExpr)
			{
				bldr.append("0x").append(Long.toHexString(((LongCstExpr) right).getCst()).toUpperCase(Locale.ENGLISH));
			}
			else
			{
				bldr.append(right.toPseudocode());
			}
		}

		return bldr.toString();
	}
}
