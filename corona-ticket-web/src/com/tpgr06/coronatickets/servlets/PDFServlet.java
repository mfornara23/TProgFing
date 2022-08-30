package com.tpgr06.coronatickets.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tpgr06.coronatickets.utils.ConfigUtils;

import tpgr06.coronatickets.ws.publicador.CoronaServices;
import tpgr06.coronatickets.ws.publicador.CoronaServicesService;
import tpgr06.coronatickets.ws.publicador.EspectaculoDTO;
import tpgr06.coronatickets.ws.publicador.EspectadorDTO;
import tpgr06.coronatickets.ws.publicador.NotFoundException_Exception;
import tpgr06.coronatickets.ws.publicador.PremioDTO;
import tpgr06.coronatickets.ws.publicador.UsuarioDTO;


@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoronaServices port;
	private static Pattern LTRIM = Pattern.compile("^\\s+");
	private static Pattern RTRIM = Pattern.compile("\\s+$");
	static String tomcatBase = System.getProperty("catalina.base");
	static String separator = File.separator;
	private static final String SAVING_PATH = String.format("%s"+separator+"webapps"+separator+"corona-ticket-web"+separator+"media"+separator+"img", tomcatBase);

	public void init() {
		CoronaServicesService service = new CoronaServicesService();
		port = service.getCoronaServicesPort();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nombreEspectaculo = RTRIM.matcher(LTRIM.matcher(request.getParameter("nombre_espec")).replaceAll("")).replaceAll("");
		String nickname = RTRIM.matcher(LTRIM.matcher((String) session.getAttribute("nickname")).replaceAll("")).replaceAll("");

		try {
			EspectaculoDTO espectaculoDTO = port.consultaEspectaculo(nombreEspectaculo);
			UsuarioDTO usuario = port.getUsuarioByNickname(nickname);
			if (usuario instanceof EspectadorDTO) {
				EspectadorDTO espectador = (EspectadorDTO) usuario;
				PremioDTO premio = espectador.getPremios().stream().filter(pre -> pre.getEspectaculo().equals(espectaculoDTO.getNombre())).findFirst().get();

				// Creating a PdfWriter
				String path = String.format("%s" + separator + "webapps" + separator + "corona-ticket-web" + separator + "media" + separator + "pdf", tomcatBase);
				PdfWriter writer = new PdfWriter(path + "Premio.pdf");
				// Creating a PdfDocument
				PdfDocument pdfDoc = new PdfDocument(writer);

				// Adding a new page
				pdfDoc.addNewPage();

				// Creating a Document
				Document document = new Document(pdfDoc);

				// Creating an ImageData object
				String logoPath = SAVING_PATH + separator + "logo.png";
				ImageData logo = ImageDataFactory.create(logoPath);
				logo.setWidth(120);
				logo.setHeight(90);

				// Creating the image
				Image img = new Image(logo);
				document.add(img);

				Paragraph title = new Paragraph("COMPROBANTE DE PREMIO");
				title.setBold();
				title.setFontSize(20);
				document.add(title);

				// Creating a table
				float[] pointColumnWidths = {250f, 250f};
				Table table = new Table(pointColumnWidths);


				// Nombre del espectador
				Cell cell1 = new Cell();
				cell1.add("Nombre del Espectador");
				table.addCell(cell1);
				Cell cell2 = new Cell();
				cell2.add(espectador.getNombre());
				table.addCell(cell2);

				// Nombre del espectaculo
				Cell cell3 = new Cell();
				cell3.add("Nombre del espectaculo");
				table.addCell(cell3);
				Cell cell4 = new Cell();
				cell4.add(espectaculoDTO.getNombre());
				table.addCell(cell4);

				// Populating row 3 and adding it to the table
				Cell cell5 = new Cell();
				cell5.add("Nombre de la funcion");
				table.addCell(cell5);
				Cell cell6 = new Cell();
				cell6.add(premio.getFuncion());
				table.addCell(cell6);

				// Populating row 4 and adding it to the table
				Cell cell7 = new Cell();
				cell7.add("Fecha de sorteo");
				table.addCell(cell7);
				Cell cell8 = new Cell();
				SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
				Date fechaVencimiento = premio.getFechaSorteo().toGregorianCalendar().getTime();
				String dateFormatted = fmt.format(premio.getFechaSorteo().toGregorianCalendar().getTime());
				cell8.add(dateFormatted + "  (tiene 30 dias para canjear)");
				table.addCell(cell8);

				// Populating row 5 and adding it to the table
				Cell cell9 = new Cell();
				cell9.add("Descripcion del premio");
				table.addCell(cell9);
				Cell cell10 = new Cell();
				cell10.add(premio.getDescripcion());
				table.addCell(cell10);

				// Adding Table to document
				document.add(table);

				// Closing the document
				document.close();
				System.out.println("PDF Created");

				// reads input file from an absolute path
				File downloadFile = new File(path + "Premio.pdf");
				FileInputStream inStream = new FileInputStream(downloadFile);

				// if you want to use a relative path to context root:
				String relativePath = getServletContext().getRealPath("");
				System.out.println("relativePath = " + relativePath);

				// obtains ServletContext
				ServletContext context = getServletContext();

				// gets MIME type of the file
				String mimeType = context.getMimeType(path);
				if (mimeType == null) {
					// set to binary type if MIME mapping not found
					mimeType = "application/octet-stream";
				}
				System.out.println("MIME type: " + mimeType);

				// modifies response
				response.setContentType(mimeType);
				response.setContentLength((int) downloadFile.length());

				// forces download
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
				response.setHeader(headerKey, headerValue);

				// obtains response's output stream
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inStream.close();
				outStream.close();
			}
		} catch (NotFoundException_Exception e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/errores/error-generico.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	

}
