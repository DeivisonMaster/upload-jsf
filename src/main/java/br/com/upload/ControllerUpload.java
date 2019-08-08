package br.com.upload;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class ControllerUpload implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UploadedFile file;
	
	
	public void upload() {
		try {
			if(file != null) {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/upload?user=master&password=matos132739");
				
				PreparedStatement ps = connection.prepareStatement("INSERT INTO imagem (img) VALUES (?)");
				ps.setBinaryStream(1, file.getInputstream());
				ps.execute();
				
				ps.close();
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}

}


















