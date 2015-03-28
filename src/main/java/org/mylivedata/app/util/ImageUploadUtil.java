package org.mylivedata.app.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadUtil implements ServletContextAware {

	private final Logger LOGGER = LoggerFactory.getLogger(ImageUploadUtil.class);
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

    public void saveImage(String filename, MultipartFile image) throws IOException {
		try {
            Path realPath = Paths.get(servletContext.getRealPath("/"));
            File file = new File(realPath.getParent().getParent() + "/src/main/webapp/resources/" + filename);
			if (!file.exists()) {
				FileUtils.writeByteArrayToFile(file, image.getBytes());
                FileUtils.copyFile(file, new File(servletContext.getRealPath("/") + "/resources/" + filename));
			} else {
				LOGGER.error("File with this name already exists");
				throw new IOException("global.fileExist");
			}
		} catch (IOException e) {
			LOGGER.error("Cannot upload user avatar " + e);
            throw e;
		}
	}
}
