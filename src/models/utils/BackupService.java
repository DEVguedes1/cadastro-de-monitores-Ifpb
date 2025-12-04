package models.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupService {

    private static final String ARQUIVO_ORIGEM = "central.xml";
    private static final String PASTA_BACKUP = "backups";

    public static String realizarBackup() throws IOException {
        // 1. Cria a pasta de backups se não existir
        File pasta = new File(PASTA_BACKUP);
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        // 2. Verifica se o XML existe
        File origem = new File(ARQUIVO_ORIGEM);
        if (!origem.exists()) {
            throw new IOException("Arquivo de dados não encontrado para backup.");
        }

        // 3. Define o nome do arquivo com data/hora (ex: backup_2025-12-04_10-30-00.zip)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String nomeArquivo = "backup_" + LocalDateTime.now().format(fmt) + ".zip";
        File destino = new File(pasta, nomeArquivo);

        // 4. Compacta o XML em ZIP para economizar espaço
        compactarParaZip(origem, destino);

        return destino.getAbsolutePath();
    }

    private static void compactarParaZip(File origem, File destino) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destino);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(origem)) {

            ZipEntry zipEntry = new ZipEntry(origem.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
            
            zos.closeEntry();
        }
    }
}