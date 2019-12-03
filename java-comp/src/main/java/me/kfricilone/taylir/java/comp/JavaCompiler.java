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
import lombok.extern.log4j.Log4j2;
import me.kfricilone.taylir.java.arch.JavaArchitecture;
import me.kfricilone.taylir.java.comp.ast.impl.TypeDeclNode;
import me.kfricilone.taylir.java.comp.parser.JavaLexer;
import me.kfricilone.taylir.java.comp.parser.JavaParser;
import me.kfricilone.taylir.java.comp.parser.visitors.CompilationVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Kyle Fricilone on Jun 12, 2018.
 */
@Log4j2
@AllArgsConstructor
public class JavaCompiler
{

	private final JavaArchitecture architecture;
	private final boolean optimize;
	private final boolean obfuscate;

	public void compile(File src)
	{
		compileFile(src);
	}

	private void compileFile(File file)
	{
		try (FileInputStream fis = new FileInputStream(file))
		{
			CharStream input = CharStreams.fromStream(fis);
			JavaLexer lexer = new JavaLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			JavaParser parser = new JavaParser(tokens);
			ParseTree tree = parser.compilationUnit();

			CompilationVisitor visitor = new CompilationVisitor(architecture.getClasspath());
			TypeDeclNode type = tree.accept(visitor);

			System.out.println(type.toPseudocode());

			/*ClassNode cn = CodeGenerator.generate(type);

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cn.accept(writer);

			try (FileOutputStream fos = new FileOutputStream(new File("Test.class")))
			{
				fos.write(writer.toByteArray());
			}*/
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
