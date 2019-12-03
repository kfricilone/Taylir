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

package me.kfricilone.taylir.common.mlir.stmts;

import lombok.EqualsAndHashCode;
import lombok.Value;
import me.kfricilone.taylir.common.mlir.Expr;
import me.kfricilone.taylir.common.mlir.Stmt;
import me.kfricilone.taylir.common.mlir.StmtVisitor;

/**
 * Created by Kyle Fricilone on Feb 21, 2019.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ExprStmt extends Stmt
{

	/**
	 * The expr to be popped by this statement
	 */
	private final Expr value;

	public ExprStmt(Expr value)
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
		visitor.visitExprStmt(this);
	}

	/**
	 * Creates a copy of an expression
	 *
	 * @return The copy
	 */
	@Override
	public Stmt copy()
	{
		return new ExprStmt(value.copy());
	}

	/**
	 * Formats the expr to a pseudocode representation.
	 *
	 * @return The pseudocode representation
	 */
	@Override
	public String toPseudocode()
	{
		return value.toPseudocode();
	}
}
