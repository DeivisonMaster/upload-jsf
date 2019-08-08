package br.com.upload;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/********************** REFATORAR *****************************/
@Named
@SessionScoped
public class ControllerDownload implements Serializable {
	private static final long serialVersionUID = 1L;

	private StreamedContent file;
	private int codigo;

	
	public void download() {
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/upload?user=master&password=matos132739");

			PreparedStatement ps = connection.prepareStatement("SELECT img FROM imagem WHERE id = (?)");
			ps.setInt(1, codigo);

			rs = ps.executeQuery();

			while (rs.next()) {
				InputStream inputStream = rs.getBinaryStream("img");
				file = new DefaultStreamedContent(inputStream, "image", "download.jpg");
			}

			ps.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void visualizar() {
		ResultSet rs;

		try {
			byte[] bytes = null;
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/upload?user=master&password=matos132739");

			PreparedStatement ps = connection.prepareStatement("SELECT img FROM imagem WHERE id = (?)");
			ps.setInt(1, codigo);

			rs = ps.executeQuery();

			while (rs.next()) {
				bytes = rs.getBytes("img");
			}
			connection.close();
			
			FacesMessage message = new FacesMessage("Sucesso!", "Imagem baixada");
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.getOutputStream().write(bytes);
			response.getOutputStream().close();
			
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
