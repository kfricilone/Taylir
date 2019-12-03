package me.kfricilone.taylir.java.arch;

import me.kfricilone.taylir.java.arch.util.JarUtil;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyle Fricilone on Nov 13, 2019.
 */
public class Classpath
{

	private final Jdk jdk;
	private final Map<String, ClassNode> classes;

	public Classpath(Jdk jdk, List<File> files)
	{
		this.jdk = jdk;
		this.classes = new HashMap<>();

		for (File f : files)
		{
			classes.putAll(JarUtil.loadHeaders(f));
		}
	}

	public ClassNode resolve(String name)
	{
		if (classes.containsKey(name))
		{
			return classes.get(name);
		}

		return jdk.resolve(name);
	}

}
