package me.kfricilone.taylir.java.arch;

import me.kfricilone.taylir.java.arch.util.JarUtil;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.util.Map;

/**
 * Created by Kyle Fricilone on Nov 13, 2019.
 */
public class Jdk
{

	private final Map<String, ClassNode> classes;

	public Jdk(File path)
	{
		this.classes = JarUtil.loadJdk(path);
	}

	public ClassNode resolve(String name)
	{
		if (classes.containsKey(name))
		{
			return classes.get(name);
		}

		return null;
	}

}
