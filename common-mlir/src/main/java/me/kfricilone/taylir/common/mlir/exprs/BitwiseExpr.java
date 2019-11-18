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
