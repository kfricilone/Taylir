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
public class InvokeVirtualExpr extends InvokeExpr
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
	 * The object instance to call
	 */
	private final Expr instance;

	/**
	 * The arguments of the invoke expression
	 */
	private final List<Expr> arguments;

	public InvokeVirtualExpr(String owner, String name, String descriptor, Expr instance, List<Expr> args)
	{
		this.owner = owner;
		this.name = name;
		this.descriptor = descriptor;
		this.instance = instance;
		this.arguments = args;

		getChildren().add(instance);
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
		visitor.visitInvokeVirtualExpr(this);
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
		return new InvokeVirtualExpr(owner, name, descriptor, instance.copy(), copy);
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
		bldr.append(instance.toPseudocode()).append(".").append(name).append("(");

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
