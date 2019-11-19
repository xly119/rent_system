package priv.xly.rentsys.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@PropertySource({ "application.properties" })
@RestController
public class OneImgUpload {

	@Value("${web.upload-path}")
	public String filePath;
	@Value("${web.swiper-maxsize}")
	public int MAXSIZE;

	public Map<String, String> saveFile(MultipartFile file) throws Exception {

		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		long fileSize = file.getSize();
		int maxSize = this.MAXSIZE;
		if (fileSize > maxSize) {
			throw new Exception("大小超过限制");
		}
		fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		file.transferTo(dest);
		Map<String, String> data = new HashMap<String, String>();
		data.put("name", fileName);
		return data;
	}

}
