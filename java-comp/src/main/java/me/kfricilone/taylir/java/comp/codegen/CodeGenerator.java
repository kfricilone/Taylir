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

package me.kfricilone.taylir.java.comp.codegen;

import me.kfricilone.taylir.java.comp.ast.impl.ClassDeclNode;
import me.kfricilone.taylir.java.comp.ast.impl.MemberDeclNode;
import me.kfricilone.taylir.java.comp.ast.impl.MethodBodyNode;
import me.kfricilone.taylir.java.comp.ast.impl.MethodDeclNode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import me.kfricilone.taylir.java.comp.ast.impl.TypeDeclNode;

/**
 * Created by Kyle Fricilone on Dec 02, 2019.
 */
public class CodeGenerator
{

	public static ClassNode generate(TypeDeclNode node)
	{
		ClassDeclNode clsNode = (ClassDeclNode) node.getType();

		ClassNode cn = new ClassNode();
		cn.visit(Opcodes.V1_8, node.getAccess(), clsNode.getName(), null, "java/lang/Object", new String[] {});

		for (MemberDeclNode memNode : clsNode.getMembers())
		{
			MethodDeclNode methNode = (MethodDeclNode) memNode.getMember();

			MethodVisitor mv = cn.visitMethod(memNode.getAccess(), methNode.getName(), methNode.getDescriptor(), null, new String[] {});

			MethodBodyNode body = methNode.getBody();
			if (body.getStatements().size() > 0)
			{
				StmtGenVisitor stmtGen = new StmtGenVisitor(mv);
				body.getStatements().forEach(s -> s.accept(stmtGen));
				mv.visitInsn(Opcodes.RETURN);
			}
		}

		return cn;
	}

}
