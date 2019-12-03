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

package me.kfricilone.taylir.java.comp.ast.impl;

import lombok.Value;
import me.kfricilone.taylir.java.comp.ast.types.MemberNode;
import org.objectweb.asm.Type;

import java.util.List;

/**
 * Created by Kyle Fricilone on Nov 23, 2019.
 */
@Value
public class MethodDeclNode extends MemberNode
{

	private final Type type;
	private final String name;
	private final List<ParameterNode> parameters;
	private final List<String> throwsList;
	private final MethodBodyNode body;

	@Override
	public String toPseudocode()
	{
		StringBuilder bldr = new StringBuilder();
		bldr.append(type.getClassName()).append(" ");
		bldr.append(name).append("(");

		if (parameters.size() > 0)
		{
			StringBuilder params = new StringBuilder();
			for (int i = 0; i < parameters.size(); i++)
			{
				ParameterNode param = parameters.get(i);
				params.append(param.toPseudocode()).append(", ");
			}

			bldr.append(params.substring(0, params.length() - 2));
		}

		return bldr.append(")\n").append(body.toPseudocode()).toString();
	}

	public String getDescriptor()
	{
		Type[] params = new Type[parameters.size()];
		for (int i = 0; i < parameters.size(); i++)
		{
			ParameterNode param = parameters.get(i);
			params[i] = param.getType();
		}

		return Type.getMethodDescriptor(type, params);
	}
}
