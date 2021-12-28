package cafeteria;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase encargada de configurar los elementos de la interfaz gráfica del
 * programa. Contiene el método que inicia la ejecución del programa. 
 * @author Geny Dávila
 * @version 2.2 
 */
public final class Main {
    static volatile boolean pausa;
    static volatile boolean ejecutando;
    private Simulacion s;
    private JFrame frame;
    private JPanel panelControl;
    private JButton btnPausa;
    private JButton btnReinicio;
    private JLabel tiempo;
    private JLabel mensaje;
    private Thread ciclo;
    private Tiempo t;
    
    /**
     * Constructor
     */
    public Main(){
        pausa = false;
        ejecutando = true;
        frame = new JFrame("Cafetería");
        s = new Simulacion();
        panelControl = new JPanel();
        btnPausa = new JButton("Pausa");
        btnReinicio = new JButton("Detener");
        
        // Botón para pausar/reanudar la simulación
        btnPausa.setBackground(Color.black);
        btnPausa.setToolTipText("Pausar la simulación");
        btnPausa.setForeground(Color.white);
        btnPausa.setFocusPainted(false);
        
        // Botón para detener/iniciar la simulación
        btnReinicio.setBackground(Color.black);
        btnReinicio.setToolTipText("Detener/Iniciar la simulación");
        btnReinicio.setForeground(Color.white);
        btnReinicio.setFocusPainted(false);
        
        // Mensajes con información debajo del panel de la simulación
        tiempo = new JLabel("Tiempo: 00:00");
        tiempo.setToolTipText("Tiempo transcurrido");
        mensaje = new JLabel("Ejecutando");
        mensaje.setToolTipText("Estado de la simulación");
        
        ciclo = new Thread(s);  // ciclo para la simulación
        t = new Tiempo();       // objeto para el conteo de tiempo
    }
    
    /**
     * Método que organiza los elementos que se muestran en la ventana de la 
     * simulación
     */
    public void configurarVentana(){
        frame.setResizable(false);
        frame.add(s, BorderLayout.CENTER);
        panelControl.setLayout(new GridLayout(1,4));
        panelControl.setBackground(Color.BLACK);
        
        // Mensaje con el tiempo transcurrido
        JPanel aux1 = new JPanel();
        tiempo.setForeground(Color.WHITE);
        aux1.setOpaque(false);
        aux1.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        aux1.add(tiempo);
        panelControl.add(aux1);
        
        // Botón para pausar la simulación
        JPanel aux2 = new JPanel();
        aux2.setOpaque(false);
        aux2.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
        aux2.add(btnPausa);
        panelControl.add(aux2);
        
        // Botón para detener la simulación
        JPanel aux3 = new JPanel();
        aux3.setOpaque(false);
        aux3.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
        aux3.add(btnReinicio);
        panelControl.add(aux3);
        
        // Mensaje que muestra el estado de la simulación
        JPanel aux4 = new JPanel();
        aux4.setOpaque(false);
        mensaje.setForeground(Color.WHITE);
        aux4.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
        aux4.add(mensaje);
        panelControl.add(aux4);
        
        frame.add(panelControl, BorderLayout.SOUTH);
        s.reloj = t.getReloj(); // crear otra referencia al obj. Reloj, para modificar sus valores desde Tiempo y mostrarlo en Simulacion
        ciclo.start();          // iniciar la simulación
        t.setEtiqueta(tiempo);  // crear otra referencia a la etiqueta de tiempo para modificarla desde el objeto Tiempo
        t.iniciarTiempo();      // iniciar el conteo del tiempo
        
        btnPausa.addActionListener((ActionEvent e) -> {
            pausa = !pausa; // negar el valor de la variable al hacer clic
            if(!pausa){     
                // si la simulación estaba en pausa y se vuelve a hacer clic en el botón
                s.reanudarSimulacion();
                btnPausa.setText("Pausa");
                mensaje.setText("En ejecución");
                t.iniciarTiempo();
            }else{
                btnPausa.setText("Reanudar");
                mensaje.setText("En pausa");
                t.detenerTiempo();
            }
        });

        btnReinicio.addActionListener((ActionEvent e)->{                    
            if(ejecutando){
                // desbloquear el hilo para limpiar correctamente la pantalla 
                // cuando está en pausa y despues se detiene el ciclo
                if(pausa){
                    s.reanudarSimulacion();
                }
                ejecutando = false;
                btnPausa.setEnabled(false);
                btnReinicio.setText("Iniciar");                     
                ciclo = null;
                t.detenerTiempo();
                t.reiniciarContador();
                mensaje.setText("Simulación detenida");
            }else{
                ejecutando = true;
                s.reiniciarValores();
                ciclo = new Thread(s);
                ciclo.start();
                btnReinicio.setText("Detener");
                btnPausa.setEnabled(true);
                pausa = false;
                btnPausa.setText("Pausa");
                mensaje.setText("En ejecución");
                t.iniciarTiempo();
                new Audio().start();
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon(getClass().getResource("/sprites/icono.png")).getImage());
        
        new Audio().start();
    }
    
    /**
     * Método encargado de iniciar la ejecución del programa
     * @param args parámetros recibidos por consola, no utilizados en este
     * programa
     */
    public static void main(String []args){
        Main m = new Main();
        m.configurarVentana();
    }
}
