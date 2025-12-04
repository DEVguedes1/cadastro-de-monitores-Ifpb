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
    private static final String PASTA_BACKUP_PADRAO = "backups";

    // Backup Local Padrão (Para o botão Sair)
    public static void realizarBackupLocal() throws IOException {
        criarZip(new File(PASTA_BACKUP_PADRAO));
    }

    // --- NOVO: Backup em Pasta Específica (Para Google Drive/Dropbox) ---
    public static String realizarBackupEmPasta(File pastaDestino) throws IOException {
        return criarZip(pastaDestino);
    }

    // Método auxiliar que cria o ZIP
    private static String criarZip(File pastaDestino) throws IOException {
        // Garante que a pasta existe
        if (!pastaDestino.exists()) {
            pastaDestino.mkdirs();
        }

        File origem = new File(ARQUIVO_ORIGEM);
        if (!origem.exists()) throw new IOException("Sem dados no sistema para salvar.");

        // Nome do arquivo: Backup_Sismon_DATA_HORA.zip
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String nomeArquivo = "Backup_Sismon_" + LocalDateTime.now().format(fmt) + ".zip";
        File arquivoFinal = new File(pastaDestino, nomeArquivo);

        // Compactação
        try (FileOutputStream fos = new FileOutputStream(arquivoFinal);
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
        
        return arquivoFinal.getAbsolutePath();
    }
}