/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.grinder.util;

import static org.ngrinder.common.util.Preconditions.checkNotNull;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

/**
 * Grinder classpath optimization class
 * 
 * @author JunHo Yoon
 * @since 3.0
 */
public class GrinderClassPathUtils {
	/**
	 * Construct classPath for grinder from given classpath string
	 * 
	 * @param classPath
	 *            classpath string
	 * @param m_logger
	 *            logger
	 * @return classpath optimized for grinder.
	 */
	public static String filterClassPath(String classPath, Logger logger) {
		List<String> classPathList = new ArrayList<String>();
		for (String eachClassPath : checkNotNull(classPath).split(File.pathSeparator)) {
			String name = FilenameUtils.getName(eachClassPath);
			// Exclude not necessary jars..
			if ("jar".equals(FilenameUtils.getExtension(name))
							&& (name.contains("ngrinder") || name.contains("spring"))) {
				continue;
			}
			// Include necessary jars..
			if (name.contains("grinder") || name.contains("asm") || name.contains("picocontainer")
							|| name.contains("jython") || name.contains("slf4j-api")
							|| name.contains("logback") || name.contains("jsr173")
							|| name.contains("xmlbeans") || name.contains("stax-api")) {
				logger.trace("classpath :" + eachClassPath);
				classPathList.add(eachClassPath);
			}
		}
		return StringUtils.join(classPathList, File.pathSeparator);
	}

	/**
	 * Construct classPath from current classLoader
	 * 
	 * @param m_logger
	 *            logger
	 * @return classpath optimized for grinder.
	 */
	public static String buildClasspathBasedOnCurrentClassLoader(Logger logger) {
		URL[] urLs = ((URLClassLoader) GrinderClassPathUtils.class.getClassLoader()).getURLs();
		StringBuilder builder = new StringBuilder();
		for (URL each : urLs) {
			builder.append(each.getFile()).append(File.pathSeparator);
		}
		return GrinderClassPathUtils.filterClassPath(builder.toString(), logger);
	}
}