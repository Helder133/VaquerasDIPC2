/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.services.reporte;

import ipc2_vaqueras.vaquerasdipc2.db.DBConnection;
import ipc2_vaqueras.vaquerasdipc2.exceptions.UserDataInvalidException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class ReporteService {
    // admin sistemas 
    public byte[] reporteDeGanancia(String fechaInico, String fechaFinal) throws SQLException, JRException, UserDataInvalidException {
        if (!validarFecha(fechaInico, fechaFinal)) {
            throw new UserDataInvalidException("Fechas requeridas");
        }
        LocalDate inicio = LocalDate.parse(fechaInico);
        LocalDate fin = LocalDate.parse(fechaFinal);
        
        Map<String, Object> params = new HashMap<>();
        params.put("fechaInicio", java.sql.Date.valueOf(inicio));
        params.put("fechaFin", java.sql.Date.valueOf(fin));
        
        return generarPDF("Ganancia_Global.jasper", params);
    }

    private byte[] generarPDF(String nombreArchivo, Map<String, Object> params) throws SQLException, JRException {
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            InputStream reporteStream = getClass().getResourceAsStream("/reporte/"+nombreArchivo);
            if (reporteStream == null) {
                throw new JRException("Reporte no encontrado: "+ nombreArchivo);
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,connection);
            
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } 
    }

    private boolean validarFecha(String fechaInico, String fechaFinal) {
        return StringUtils.isNotBlank(fechaFinal)
                && StringUtils.isNotBlank(fechaInico);
    }
    
}
