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

package me.kfricilone.taylir.java.comp;

import lombok.AllArgsConstructor;
import me.kfricilone.taylir.java.arch.Classpath;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyle Fricilone on Dec 02, 2019.
 */
@AllArgsConstructor
public class CompilationContext
{

	private static final Map<String, String> DEFAULT_IMPORTS = new HashMap<>();

	static
	{
		DEFAULT_IMPORTS.put("System", "java/lang/System");
		DEFAULT_IMPORTS.put("String", "java/lang/String");
	}

	private final Classpath cp;
	private final Map<String, String> imports;

	public String resolveName(String name)
	{
		if (DEFAULT_IMPORTS.containsKey(name))
		{
			return DEFAULT_IMPORTS.get(name);
		}

		return imports.get(name);
	}

	public FieldNode findFieldFirst(String owner, String name, boolean stat)
	{
		List<FieldNode> fields = findFields(owner, name, stat);

		if (fields.isEmpty())
		{
			return null;
		}

		return fields.get(0);
	}

	public List<FieldNode> findFields(String owner, String name, boolean stat)
	{
		List<FieldNode> fields = new ArrayList<>();

		if (DEFAULT_IMPORTS.containsKey(owner))
		{
			owner = DEFAULT_IMPORTS.get(owner);
		}

		else if (imports.containsKey(owner))
		{
			owner = imports.get(owner);
		}

		ClassNode cn = cp.resolve(owner);
		if (cn != null)
		{
			fields.addAll(findFields(cn, name, stat));
		}

		return fields;
	}

	private List<FieldNode> findFields(ClassNode cn, String name, boolean stat)
	{
		List<FieldNode> fields = new ArrayList<>();

		for (FieldNode fn : cn.fields)
		{
			if (fn.name.equals(name) && Modifier.isStatic(fn.access) == stat)
			{
				fields.add(fn);
			}
		}

		return fields;
	}

	public MethodNode findMethodFirst(String owner, String name, boolean stat)
	{
		List<MethodNode> methods = findMethods(owner, name, stat);

		if (methods.isEmpty())
		{
			return null;
		}

		return methods.get(0);
	}

	public List<MethodNode> findMethods(String owner, String name, boolean stat)
	{
		List<MethodNode> methods = new ArrayList<>();

		if (DEFAULT_IMPORTS.containsKey(owner))
		{
			owner = DEFAULT_IMPORTS.get(owner);
		}

		else if (imports.containsKey(owner))
		{
			owner = imports.get(owner);
		}

		ClassNode cn = cp.resolve(owner);
		if (cn != null)
		{
			methods.addAll(findMethods(cn, name, stat));
		}

		return methods;
	}

	private List<MethodNode> findMethods(ClassNode cn, String name, boolean stat)
	{
		List<MethodNode> methods = new ArrayList<>();

		for (MethodNode mn : cn.methods)
		{
			if (mn.name.equals(name) && Modifier.isStatic(mn.access) == stat)
			{
				methods.add(mn);
			}
		}

		return methods;
	}
}
