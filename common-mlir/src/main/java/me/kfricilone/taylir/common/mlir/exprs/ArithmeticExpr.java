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

/**
 * Created by Kyle Fricilone on Jan 16, 2019.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ArithmeticExpr extends Expr
{

	@Getter
	@AllArgsConstructor
	public enum Operator
	{
		/**
		 * Addition expression
		 */
		ADD("+"),

		/**
		 * Subtraction expression
		 */
		SUB("-"),

		/**
		 * Multiplication expression
		 */
		MULT("*"),

		/**
		 * Division expression
		 */
		DIV("/"),

		/**
		 * Remainder expression
		 */
		REM("%");

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

	public ArithmeticExpr(Operator operator, Expr left, Expr right)
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
		visitor.visitArithmeticExpr(this);
	}

	/**
	 * Creates a copy of an expression
	 *
	 * @return The copy
	 */
	@Override
	public Expr copy()
	{
		return new ArithmeticExpr(operator, left.copy(), right.copy());
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
			bldr.append(left.toPseudocode());
		}

		bldr.append(" ").append(operator.identifier).append(" ");

		if (right.getChildren().size() > 0)
		{
			bldr.append("(").append(right.toPseudocode()).append(")");
		}
		else
		{
			bldr.append(right.toPseudocode());
		}

		return bldr.toString();
	}
}
