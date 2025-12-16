/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ipc2_vaqueras.vaquerasdipc2.archivo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author helder
 */
public class Archivo {

    private static final String PATH = "/home/helder/IPC_2/vaqueras diciembre/VaquerasDIPC2/multimedia";

    public String guardarArchivo(InputStream inputStream, String nombreArchivo, String subFolder) throws IOException {
        
        if (inputStream == null || StringUtils.isBlank(nombreArchivo) || StringUtils.isBlank(subFolder)) {
            return null;
        }
        
        //Ruta del directorio donde se suardan las imagenes
        Path ruta = Paths.get(PATH, subFolder);
        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }
        
        //Secambia el nombre por un nombre unico que se genera aleatoriamente agregandole fecha y hora 
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
        String nuevoNombre = fechaHora + "_" + nombreArchivo.replaceAll("\\s+", "_");
        
        //Ruta completa 
        Path rutaArchivo = ruta.resolve(nuevoNombre);
        System.out.println(rutaArchivo.toString());
        
        //Guardar
        Files.copy(inputStream, rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
        
        //devolver ruta relativa para guardar en la base de datos
        return subFolder + File.separator + nuevoNombre;
    }
    
    public void eliminarArchivo(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }

        try {
            Path absolutePath = Paths.get(PATH, path);
            Files.deleteIfExists(absolutePath);
        } catch (IOException e) {
            System.err.println("No se pudo eliminar el archivo: " + path);
        }
    }
    
}
