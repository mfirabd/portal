package org.portalengine.portal.FileLink;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileLinkService {
	
	@Autowired
	private FileLinkRepository repo;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Value("${portal.uploadroot}")
	private String uploadroot;
	
	public FileLinkRepository getRepo() {
		return repo;
	}

	public void setRepo(FileLinkRepository repo) {
		this.repo = repo;
	}

	@Autowired
	public FileLinkService() {
	}
	
	public Resource getResource(FileLink filelink) {
		Resource dresource = resourceLoader.getResource("file:" + filelink.getPath());
		return dresource;
	}
	
	public File getFile(FileLink filelink) {
		File file = new File(filelink.getPath());
		return file;
	}
	
	public String SaveTmpFile(MultipartFile file) {
		String filepath=null;
		if(file.getOriginalFilename().length()>0) {
			String targetpath = uploadroot + "/tmp";			
			File fpath = new File(targetpath);			
			if(!fpath.exists()) {
				fpath.mkdirs();
			}
			filepath = targetpath + "/" + file.getOriginalFilename();
			try {
				file.transferTo(new File(filepath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return filepath;
	}
	
	public FileLink SaveFile(MultipartFile file, FileLink filelink) {
		if(file.getOriginalFilename().length()>0) {
			String targetpath = uploadroot + "/" + filelink.getModule();			
			File fpath = new File(targetpath);			
			if(!fpath.exists()) {
				fpath.mkdirs();
			}
			String filepath = targetpath + "/" + file.getOriginalFilename();
			try {
				System.out.println("Filepath:" + filepath);
				file.transferTo(new File(filepath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			filelink.setName(file.getOriginalFilename());
			filelink.setPath(filepath);
		}
		else {
			if(filelink.getId()!=null) {
				FileLink cfile = repo.getOne(filelink.getId());
				if(cfile!=null) {
					filelink.setName(cfile.getName());
					filelink.setPath(cfile.getPath());
				}
			}
		}
		return filelink;
	}

}