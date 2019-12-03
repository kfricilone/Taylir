package me.kfricilone.taylir.java.arch.util;

import lombok.extern.log4j.Log4j2;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.objectweb.asm.ClassReader.SKIP_CODE;
import static org.objectweb.asm.ClassReader.SKIP_DEBUG;
import static org.objectweb.asm.ClassReader.SKIP_FRAMES;

/**
 * Created by Kyle Fricilone on Nov 13, 2019.
 */
@Log4j2
public class JarUtil
{

	public static Map<String, ClassNode> loadCode(File f)
	{
		log.debug("Loading code : " + f.toString());

		Map<String, ClassNode> classes = new HashMap<>();

		try (JarFile jf = new JarFile(f))
		{
			Enumeration<JarEntry> enumeration = jf.entries();
			while (enumeration.hasMoreElements())
			{
				JarEntry next = enumeration.nextElement();
				if (next.getName().endsWith(".class"))
				{
					ClassReader reader = new ClassReader(jf.getInputStream(next));
					ClassNode node = new ClassNode();
					reader.accept(node, SKIP_DEBUG);
					classes.put(node.name, node);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return classes;
	}

	public static Map<String, ClassNode> loadHeaders(File f)
	{
		log.debug("Loading header : " + f.toString());

		Map<String, ClassNode> classes = new HashMap<>();

		try (JarFile jf = new JarFile(f))
		{
			Enumeration<JarEntry> enumeration = jf.entries();
			while (enumeration.hasMoreElements())
			{
				JarEntry next = enumeration.nextElement();
				if (next.getName().endsWith(".class"))
				{
					ClassReader reader = new ClassReader(jf.getInputStream(next));
					ClassNode node = new ClassNode();
					reader.accept(node, SKIP_DEBUG | SKIP_CODE | SKIP_FRAMES);
					classes.put(node.name, node);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return classes;
	}

	public static Map<String, ClassNode> loadJdk(File f)
	{
		log.debug("Loading jdk : " + f.toString());

		Map<String, ClassNode> classes = new HashMap<>();

		try
		{
			Files.walk(Paths.get(f.toString(), "lib")).filter(p -> p.toString().endsWith(".jar")).forEach(p ->
			{
				classes.putAll(loadHeaders(p.toFile()));
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return classes;
	}

}
