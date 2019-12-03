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

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import lombok.Builder;
import lombok.Getter;
import me.kfricilone.taylir.java.arch.Classpath;
import me.kfricilone.taylir.java.arch.JavaArchitecture;
import me.kfricilone.taylir.java.arch.Jdk;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle Fricilone on Jun 12, 2018.
 */
public class Main
{

	public static void main(String[] args) throws Exception
	{
		Arguments arguments = buildArguments(args);

		Classpath cp = new Classpath(new Jdk(arguments.getJdk()), arguments.getCp());
		JavaArchitecture arch = new JavaArchitecture(arguments.isDebugInfo(), cp);

		JavaCompiler compiler = new JavaCompiler(arch, arguments.isOptimize(), arguments.isObfuscate());
		compiler.compile(arguments.getSrc());
	}

	private static Arguments buildArguments(String[] args) throws IOException
	{
		Arguments.ArgumentsBuilder bldr = Arguments.builder();
		OptionParser parser = new OptionParser();

		OptionSpec<Void> helpSpec = parser.accepts("help", "Print a synopsis of standard options").forHelp();
		OptionSpec<Void> debugSpec = parser.accepts("debugInfo", "Generate all debugging info");
		OptionSpec<Void> verbSpec = parser.accepts("verbose", "Output messages about what the compiler is doing");
		OptionSpec<Void> optSpec = parser.accepts("optimize", "Generate optimized code");
		OptionSpec<Void> obfSpec = parser.accepts("obfuscate", "Generate obfuscated code");

		OptionSpec<File> jdkSpec = parser.accepts("jdk", "Specify where to find java home")
			.withRequiredArg()
			.ofType(File.class);

		OptionSpec<File> cpSpec = parser.accepts("cp", "Specify where to find user class files and annotation processors")
			.withRequiredArg()
			.withValuesSeparatedBy(';')
			.ofType(File.class);

		OptionSpec<File> outSpec = parser.accepts("out", "Specify where to place generated class files")
			.withRequiredArg()
			.ofType(File.class);


		OptionSpec<File> srcSpec = parser.nonOptions("Specify where to find source files")
			.ofType(File.class);


		OptionSet set = parser.parse(args);
		if (set.has(helpSpec))
		{
			System.out.println("Usage: java-comp <options> <source files>");
			System.out.println("Where options include:\n");
			parser.printHelpOn(System.out);
			System.exit(0);
		}

		if (set.has(verbSpec))
		{
			Configurator.setRootLevel(Level.DEBUG);
		}

		if (set.has(debugSpec))
		{
			bldr.debugInfo(true);
		}

		if (set.has(optSpec))
		{
			bldr.optimize(true);
		}

		if (set.has(obfSpec))
		{
			bldr.obfuscate(true);
		}

		if (set.has(cpSpec))
		{
			bldr.cp(set.valuesOf(cpSpec));
		}

		if (set.has(jdkSpec))
		{
			bldr.jdk(set.valueOf(jdkSpec));
		}

		if (set.has(outSpec))
		{
			bldr.output(set.valueOf(outSpec));
		}

		bldr.src(set.valueOf(srcSpec));

		return bldr.build();
	}

	@Builder
	@Getter
	static class Arguments
	{

		private final File src;

		@Builder.Default
		private final File output = new File("/bin");

		@Builder.Default
		private final List<File> cp = new ArrayList<>();

		@Builder.Default
		private final File jdk = new File(System.getProperty("java.home"));

		@Builder.Default
		private final boolean debugInfo = false;

		@Builder.Default
		private final boolean optimize = false;

		@Builder.Default
		private final boolean obfuscate = false;

	}

}
