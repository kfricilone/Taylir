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

package me.kfricilone.taylir.java.comp.util;

/**
 * Created by Kyle Fricilone on Nov 27, 2019.
 */
public class NumberUtil
{

	public static Integer decodeInt(String nm) throws NumberFormatException
	{
		int radix = 10;
		int index = 0;
		boolean negative = false;
		Integer result;

		if (nm.length() == 0)
		{
			throw new NumberFormatException("Zero length string");
		}

		char firstChar = nm.charAt(0);
		if (firstChar == '-')
		{
			negative = true;
			index++;
		}

		else if (firstChar == '+')
		{
			index++;
		}

		if (nm.startsWith("0x", index) || nm.startsWith("0X", index))
		{
			index += 2;
			radix = 16;
		}

		else if (nm.startsWith("0b", index) || nm.startsWith("0B", index))
		{
			index += 2;
			radix = 2;
		}

		else if (nm.startsWith("#", index))
		{
			index ++;
			radix = 16;
		}

		else if (nm.startsWith("0", index) && nm.length() > 1 + index)
		{
			index ++;
			radix = 8;
		}

		if (nm.startsWith("-", index) || nm.startsWith("+", index))
		{
			throw new NumberFormatException("Sign character in wrong position");
		}

		try
		{
			result = Integer.valueOf(nm.substring(index), radix);
			result = negative ? Integer.valueOf(-result) : result;
		}

		catch (NumberFormatException e)
		{
			String constant = negative ? "-" + nm.substring(index) : nm.substring(index);
			result = Integer.valueOf(constant, radix);
		}

		return result;
	}

	public static Long decodeLong(String nm) throws NumberFormatException
	{
		int radix = 10;
		int index = 0;
		boolean negative = false;
		Long result;

		if (nm.length() == 0)
		{
			throw new NumberFormatException("Zero length string");
		}

		char firstChar = nm.charAt(0);
		if (firstChar == '-')
		{
			negative = true;
			index++;
		}

		else if (firstChar == '+')
		{
			index++;
		}

		// Handle radix specifier, if present
		if (nm.startsWith("0x", index) || nm.startsWith("0X", index))
		{
			index += 2;
			radix = 16;
		}

		else if (nm.startsWith("#", index))
		{
			index++;
			radix = 16;
		}

		else if (nm.startsWith("0", index) && nm.length() > 1 + index)
		{
			index++;
			radix = 8;
		}

		if (nm.startsWith("-", index) || nm.startsWith("+", index))
		{
			throw new NumberFormatException("Sign character in wrong position");
		}

		try
		{
			result = Long.valueOf(nm.substring(index), radix);
			result = negative ? Long.valueOf(-result) : result;
		}

		catch (NumberFormatException e)
		{
			String constant = negative ? "-" + nm.substring(index) : nm.substring(index);
			result = Long.valueOf(constant, radix);
		}

		return result;
	}

}
