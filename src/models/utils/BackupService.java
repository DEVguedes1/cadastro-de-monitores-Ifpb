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

    // --- 1. Backup Local AUTOMÁTICO (Ao Sair) ---
    // Agora ele salva sempre com o mesmo nome, substituindo o anterior.
    // Isso resolve o seu problema de encher a memória!
    public static void realizarBackupLocal() throws IOException {
        criarZip(new File(PASTA_BACKUP_PADRAO), "Backup_Automatico.zip");
    }

    // --- 2. Backup Manual / Nuvem ---
    // Esse continua gerando com DATA, pois é um backup "forçado" pelo usuário
    public static String realizarBackupEmPasta(File pastaDestino) throws IOException {
        return criarZip(pastaDestino, null); // Null = Gera nome com data
    }

    // Método auxiliar inteligente
    private static String criarZip(File pastaDestino, String nomeFixo) throws IOException {
        if (!pastaDestino.exists()) {
            pastaDestino.mkdirs();
        }

        File origem = new File(ARQUIVO_ORIGEM);
        if (!origem.exists()) throw new IOException("Sem dados no sistema para salvar.");

        String nomeArquivo;
        
        if (nomeFixo != null) {
            // Se passou um nome fixo, usa ele (Sobrescreve o anterior)
            nomeArquivo = nomeFixo;
        } else {
            // Se não passou nome, gera com Data/Hora (Histórico)
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            nomeArquivo = "Backup_" + LocalDateTime.now().format(fmt) + ".zip";
        }

        File arquivoFinal = new File(pastaDestino, nomeArquivo);

        // O FileOutputStream por padrão SOBRESCREVE o arquivo se ele já existir.
        // Então isso aqui atualiza o ZIP antigo automaticamente!
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