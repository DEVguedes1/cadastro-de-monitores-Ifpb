package view.style;

import java.awt.Color;

/**
 * Paleta de Cores oficial do sistema (Design System).
 * <p>
 * Esta classe centraliza todas as constantes de cores utilizadas na aplicação,
 * garantindo consistência visual (Identidade Visual) em todas as telas.
 * O esquema de cores segue um padrão "Flat Design" moderno.
 * * @author Seu Nome
 * @version 1.0
 */
public class Cores {
    
    /** Cor de fundo da barra lateral (Sidebar) e cabeçalhos. (Roxo Suave) */
    public static final Color ROXO_SIDEBAR = new Color(110, 100, 240);
    
    /** Variação mais escura do roxo, usada para efeitos de hover ou seleção. */
    public static final Color ROXO_ESCURO = new Color(80, 70, 200);
    
    /** Cor de fundo da área de conteúdo principal. (Off-White / Cinza muito claro) */
    public static final Color FUNDO_CLARO = new Color(245, 247, 251);
    
    /** Cor padrão para textos principais e títulos. (Cinza Escuro) */
    public static final Color TEXTO_ESCURO = new Color(50, 50, 50);
    
    /** Cor branca pura, usada em textos sobre fundos escuros e fundos de cards. */
    public static final Color BRANCO = Color.WHITE;

    // --- Cores Semânticas (Botões de Ação) ---
    
    /** Indica sucesso, criação ou confirmação. (Verde) */
    public static final Color VERDE = new Color(46, 204, 113);
    
    /** Indica perigo, erro, exclusão ou cancelamento. (Vermelho) */
    public static final Color VERMELHO = new Color(231, 76, 60);
    
    /** Indica ações informativas, neutras ou de navegação secundária. (Azul) */
    public static final Color AZUL = new Color(52, 152, 219);
    
    /** Indica edição, atenção ou alteração de estado. (Laranja) */
    public static final Color LARANJA = new Color(243, 156, 18);
    
    /** Cor neutra para elementos desabilitados ou secundários. */
    public static final Color CINZA = new Color(149, 165, 166);
}